package kr.caincheon.church.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import kr.caincheon.church.common.base.CommonGetSptringBean;
import kr.caincheon.church.dao.TempleDao;

/**
 * 
 * @author benjamin
 */
@Service("templeUtil")
public class CAGiguInfoUtil
{

	private final Logger _logger = Logger.getLogger(getClass());
	
    /* CHURCH 조회 */
	//@Autowired
    @Resource(name="templeDao")
    private TempleDao templeDao;
    
    /**/
    private CAGiguInfoUtil() {
    }
    
    
    private TempleDao getTempleDao() {
    	if(templeDao==null) {
    		templeDao = (TempleDao)CommonGetSptringBean.getInstance().getBean("templeDao");
    	}
    	return templeDao;
    }
    
    private static class TempleUtilSingle {
    	private static CAGiguInfoUtil INSTANCE;
    	private static synchronized CAGiguInfoUtil getInstance() {
    		if(INSTANCE==null) {
    			INSTANCE = new CAGiguInfoUtil();
    		}
    		return INSTANCE;
    	}
    }
    
    /* single-tone */
    public static CAGiguInfoUtil getInstance() {
    	return TempleUtilSingle.getInstance();
    }

    /* 지구코드 메모리 캐시 */
    private static LinkedHashMap mGiguCodeMap = null;
    private static ArrayList mGiguCodeList = null;
    private static ArrayList mGiguNameList = null;

	/**
	 * 지구코드 맵을 리턴
	 * @return Map
	 */
    public synchronized List getRegionCodeList() {
    	return mGiguCodeList;
    }
	/**
	 * 지구코드 맵을 리턴
	 * @return Map
	 */
    public synchronized List getRegionNameList() {
    	return mGiguNameList;
    }
    
	/**
	 * 지구코드 맵을 리턴
	 * @return Map
	 */
    public synchronized Map getRegionList(boolean isMixedKeyVal) 
    {
        if(mGiguCodeMap==null) {
        	mGiguCodeMap  = new LinkedHashMap();
        	mGiguCodeList = new ArrayList();
        	mGiguNameList = new ArrayList();
        	
        	// dao call
        	List rows = null;
        	try {
        		String tmp = " ORDER_NO ASC ";//정렬순서로 정렬
				rows = getTempleDao().selectGiguCodeList(tmp);
				
				for(int i = 0, i2 = rows.size() ; i < i2 ; i++) {
	        		Map row = (Map)rows.get(i);
	        		
	        		String isUse = String.valueOf(row.get("USE_YN"));
	        		String isDel = String.valueOf(row.get("DEL_YN"));
	        		
	        		_logger.debug(" >>> getRegionList() :: " + isUse +","+isDel + " -> {" + row.get("CODE_INST") + "," + row.get("NAME") + "}");
	        		
	        		if( "Y".equals(isUse.toUpperCase()) && "N".equals(isDel.toUpperCase()) ) {
	        			String cd = String.valueOf(row.get("CODE_INST"));
	            		String nm = String.valueOf(row.get("NAME"));
	        			
		        		mGiguCodeMap.put(cd, nm);
		        		mGiguCodeMap.put(nm, cd);
		        		
		        		mGiguCodeList.add(cd);
		        		mGiguNameList.add(nm);
	        		}
	        	}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
        
        if(!isMixedKeyVal) {
        	LinkedHashMap rtMap = new LinkedHashMap();
        	for (int i = 0, i2 = mGiguCodeList.size(); i < i2; i++) {
        		rtMap.put(mGiguCodeList.get(i), mGiguNameList.get(i));
			}
        	_logger.debug(" >>> getRegionList(false) :: return  " + mGiguCodeMap);
        	return rtMap;
        }
        _logger.debug(" >>> getRegionList(true) :: return  " + mGiguCodeMap);
        
        return mGiguCodeMap;
    }

    /**
     * @param regionCode
     */
    public String getRegionName(String regionCode_or_Name)
    {
        return (String)getRegionList(true).get(regionCode_or_Name);
    }

    
    /* */
    private static LinkedHashMap totalGigus = null;
    
    /* 지구별 본당 목록인데, 본당명(키), 본당idx는(값)인 map을 리턴  */
    public synchronized LinkedHashMap listMailhallInRegion(boolean isRegione)
    {
    	_logger.debug(" >> totalGigus = " + totalGigus);
    	
    	if(totalGigus==null) {
    		totalGigus = new LinkedHashMap();
    		
    		List rows;
			try {
				rows = getTempleDao().selectChurchListInGigu(null);
				for(int i = 0, i2 = rows.size() ; i < i2 ; i++) {
	        		Map row = (Map)rows.get(i);
	        		String giguName = String.valueOf(row.get("GIGU_NAME"));
	        		String giguCode = String.valueOf(row.get("GIGU_CODE"));
	        		String cIdx  = String.valueOf(row.get("CHURCH_IDX"));
	        		String cName = String.valueOf(row.get("CHURCH_NAME"));
	        		
	        		if(isRegione && (giguName!=null || "null".equals(giguName))) {
		        		Object ggo = totalGigus.get(giguName);
		        		if(ggo == null) {
		        			LinkedHashMap gg = new LinkedHashMap();
		        			gg.put(cName, cIdx);
		        			totalGigus.put(giguName, gg);
		        		} else {
		        			((LinkedHashMap)ggo).put(cName, cIdx);
		        		}
		        		
	        		} else {
	        			totalGigus.put(cName, cIdx);
	        		}
	    		}
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    	
    	_logger.debug(" >> totalGigus = " + totalGigus);
    	
    	return totalGigus;
    	
    }

    /**
     * @param regionCode
     */
    public CAGiguInfoVO findFirstChurchInGigu(String giguName)
    {
        CAGiguInfoVO vo = new CAGiguInfoVO();
        vo.GIGU_NAME = giguName;
        LinkedHashMap rows = listMailhallInRegion(true);
        for(Iterator itr = rows.keySet().iterator(); itr.hasNext();)
        {
            String giguNm = itr.next().toString();
            if(giguNm.equals(giguName))
            {
                LinkedHashMap row = (LinkedHashMap)rows.get(giguNm);
                Iterator ir = row.keySet().iterator();
                String k = ir.next().toString();
                vo.CHURCH_IDX = row.get(k).toString();
                vo.CHURCH_NAME = k;
                break;
            }
        }

        return vo;
    }

    /* UI에서 tab(지구코드)을 누르면, 해당 지구의 본당 목록 */
    public String getChurchListByTabCode(int tabCode)
    {
        String x = "";
        switch(tabCode)
        {
        case 1: // '\001'
            x = "14008,14077,14091,14036,14087";
            break;

        case 2: // '\002'
            x = "14076,14069,14100,14120,14096,14114,14070,14123,14083,14071,14080,14084";
            break;

        case 3: // '\003'
            x = "14009,14099,14073,14110,14041,14075,14049";
            break;

        case 4: // '\004'
            x = "14012,14031,14039,14038,14042,14043,14044,14045,14046,14108";
            break;

        case 5: // '\005'
            x = "14005,14006,14010,14011,14121,14081,14109,14014,14115,14015,14078,14111,14124,14103,14112";
            break;

        case 6: // '\006'
            x = "14007,14093,14021,14017,14018,14019,14020,14023,14022,14092,14032,14040";
            break;

        case 7: // '\007'
            x = "14003,14002,14004,14079,14024,14996,14097,14025,14113,14037,14098,14116";
            break;

        case 8: // '\b'
            x = "14013,14026,14119,14033,14027,14102,14086";
            break;

        case 9: // '\t'
            x = "14001,14095,14085,14016,14029,14028,14030,14082,14104,14034,14072,14035,14094,14047,14048";
            break;

        case 10: // '\n'
            x = "14089,14052,14054,14055,14117,14106,14056,14057,14059,14060";
            break;

        case 11: // '\013'
            x = "14050,14051,14053,14090,14058,14061,14062,14107,14105,14064,14063";
            break;

        case 12: // '\f'
            x = "14068,14065,14074,14066,14101,14088,14067";
            break;

        case 14: // '\016'
            x = "14997";
            break;
        }
        return x;
    }
}
