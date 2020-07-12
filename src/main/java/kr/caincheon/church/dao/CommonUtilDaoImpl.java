package kr.caincheon.church.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import kr.caincheon.church.common.base.CommonDao;
import kr.caincheon.church.common.base.CommonException;


@Repository("commonUtilDao")
public class CommonUtilDaoImpl extends CommonDao implements CommonUtilDao
{

	private final Logger _logger = Logger.getLogger(getClass());
	
	/**
	 * 
	 */
	@Override
    public List getDepartList(String depth) throws Exception 
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params: depth="+depth+"]" );
    	
        List result = null;
        String query = "";
        
        try
        {
        	String where = "";
        	if("1".equals(depth))
                query = where + " AND O.LV3='000' AND O.LV2='00' ";
            else
            if("2".equals(depth))
            	query = where + " AND O.LV3='000' AND O.LV2<>'00' ";
            else
            	query = where + " AND O.LV3<>'000' AND O.LV2<>'00' ";
            
            query = " SELECT ROW_NUMBER() OVER(ORDER BY A.DEPART_IDX) RNUM"
            		+ " , A.DEPART_IDX, A.NAME, A.ORG_IDX"
            		+ " , CASE WHEN O.LV2='00' AND O.LV3='000' THEN '1'"
            		+ "        WHEN O.LV2<>'00' AND O.LV3='000' THEN '2'"
            		+ "		ELSE '3'"
            		+ "   END  AS ORG_LEVEL"
            		+ " FROM DEPARTMENT A "
            		+ " LEFT OUTER JOIN ORG_HIERARCHY O ON O.ORG_IDX=A.ORG_IDX"
            		+ " WHERE 1=1 " + where ;
            result = super.executeQueryList(query);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        } finally {
        	free();
    	}
        return result;
    }

	@Override
    public List getDGroupCodeList(String lv1) throws Exception 
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params: ORG_HIERARCHY.LV1="+lv1+"]" );
    	
        List result = null;
        String query = "";
        
        try
        {
        	query = "SELECT ORG_IDX AS VALUE, NAME AS DKEY"
        			+ ", ORDERNO AS D_GROUP_POS, DEL_YN AS USEYN, DEL_YN AS DISPLAYYN "
        			+ " FROM ORG_HIERARCHY "
        			+ " WHERE LV3='000' AND LV2<>'00' "
        			+ (lv1 != null && lv1.length() > 0  ? " AND LV1='"+lv1+"' " : "")
        			+ " ORDER BY 1 ";
            result = super.executeQueryList(query);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        } finally {
        	free();
    	}
        return result;
    }

    
    

    @Resource(name="admPosDao")
    private AdmPosDao admPosDao;

	@Override
    public List getPosList(String depth) throws Exception 
    {
    	// OLD Code
        List<Map> resultList = null;
    	// New Code
        Map params = new HashMap();
        try {
			resultList = admPosDao.posList(params);
			
			// column name change
			if(resultList!=null && resultList.size()>0) {
				for(Map row : resultList) {
					pchange(row, "NAME2", "POS_NAME");
				}
			}
			
		} catch (CommonException e) {
			resultList = new ArrayList();
			e.printStackTrace();
		}
        return resultList;
    }

    
    /* 지구코드 목록을 조회하여 리턴 */
	@Override
    public List getCodeList(String code) throws Exception {

		D(_logger, Thread.currentThread().getStackTrace(), "DAO getCodeList(pCode) Called.[pCode:"+code+"]" );
		
        String query="";
        List result=null;
        
        try {
            query = "SELECT * FROM CODE_MASTER "
            		+ (code==null || code.length() == 0 ? "" : " WHERE NAME like '%"+code+"%'");
            query += " ORDER BY CODE ASC ";
            result = super.executeQueryList(query);
            
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        	throw e;
        } finally {
        	free();
    	}
        
        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+result );
        
        return result;
    }

    /* 지구코드 목록을 조회하여 리턴 */
	@Override
    public List getCodeInstanceList(String code, String isOrderbyName) throws Exception {

		D(_logger, Thread.currentThread().getStackTrace(), "DAO getCodeInstanceList(pCode) Called.[pCode:"+code+"]" );
		
        String query="";
        List<Map<String,Object>> result=null;
        
        try {
            query = "SELECT /* 공통코드의 코드인스턴스 조회 */ * FROM CODE_INSTANCE "
            		+ " WHERE CODE='"+code+"' "
            		+ " AND USE_YN='Y' "
            		+ " ORDER BY "+ (isOrderbyName==null||isOrderbyName.length()==0 ? " CODE_INST ASC ":isOrderbyName);
            result = super.executeQueryList(query);
            
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        	throw e;
        } finally {
        	free();
    	}
        
        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+result );
        
        return result;
    }
}
