// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HistoryServiceImpl.java

package kr.caincheon.church.intro.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.SystemPropertyUtils;

import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.base.CommonService;
import kr.caincheon.church.dao.HistoryDao;

// Referenced classes of package kr.caincheon.church.service:
//            HistoryService

@Service("historyService")
public class HistoryServiceImpl extends CommonService
    implements HistoryService
{
    private final Logger _logger = Logger.getLogger(getClass());
    
    @Resource(name="historyDao")
    private HistoryDao historyDao;

    public List historymList(Map _params) throws Exception
    {
        return historyDao.historymList(_params);
    }

    public int historymListCount(Map _params) throws Exception
    {
        return historyDao.historymListCount(_params);
    }

    public Map historymContents(Map _params) throws Exception
    {
        return historyDao.historymContents(_params);
    }

    public boolean historymInsert(Map _params) throws Exception
    {
        return historyDao.historymInsert(_params);
    }

    public boolean historymModify(Map _params) throws Exception
    {
        return historyDao.historymModify(_params);
    }

    public boolean historymDelete(Map _params) throws Exception
    {
        return historyDao.historymDelete(_params);
    }

    public Map historysContents(Map _params) throws Exception
    {
        return historyDao.historysContents(_params);
    }

    public boolean historysInsert(Map _params) throws Exception
    {
        return historyDao.historysInsert(_params);
    }

    public boolean historysModify(Map _params) throws Exception
    {
        return historyDao.historysModify(_params);
    }

    public boolean historysDelete(Map _params) throws Exception
    {
        return historyDao.historysDelete(_params);
    }

    public List categoryList(Map _params) throws Exception
    {
        return historyDao.categoryList(_params);
    }

    /**
     * 
     */
    public CommonDaoDTO categoryFullList(Map _params) throws Exception
    {
    	
    	CommonDaoDTO dto = new CommonDaoDTO();
    	
        // 
    	List<Map<String, Object>> categoryList = historyDao.categoryFullList(_params);
        
    	// 
    	LinkedHashMap<String, List> historyYears = new LinkedHashMap<String, List>(); 
    	//for(Map<String, Object> row : categoryList) {
    	for(int i=0, i2=categoryList.size(); i<i2; i++) {
    		Map row = categoryList.get(i);
    		String category_code = pnull(row, "CATEGORY_CODE");
    		
    		List years = historyDao.historyEventyearsOnCategory(category_code);
    		historyYears.put(category_code, years);
    	}
//    	String category_code = pnull(categoryList.get(0), "CATEGORY_CODE", "0") ;
        //List<Map<String, Object>> historyYearList = historyDao.historyEventyearsOnCategory("");
        
        // front에서 연혁 출력을 위한 로직
        _params.put("TMP_LIST", historyYears);
        
        // dto
        dto.daoList = categoryList ;
        dto.otherData = historyYears ;//map{categorycode,list[years]}
        dto.otherData1 = historyEventsList(_params, false);//map{categorycode_year, list[map]}
        
    	//List list = historyDao.categoryListSort(_params);
        return dto;
    }

    /**
     * isAdminCall true이면 , List를 리턴
     * isAdminCall false이면, LinkedHashMap을 리턴
     */
    @Override
    public Object historyEventsList(Map _params, boolean isAdminCall) throws Exception
    {
    	D(_logger, " ==>0. isAdminCall:"+isAdminCall+", " + _params);
    	
    	Object rtObject = null;
    	
    	if(isAdminCall) {
    		List rtList = historyDao.historyEventsList(_params, isAdminCall);
    		rtObject = rtList;
    		
    	} else {
    		LinkedHashMap<String, List> rtMap = new LinkedHashMap<String, List>();
    		rtObject = rtMap;
    		
    		Object oo = _params.remove("TMP_LIST");
    		D(_logger, " ==>1. "+(oo));
    		if(oo != null) {
    			Map _params2 = new HashMap<String, Object>();
    			_params2.putAll(_params2);
    			
    			Map<String,List<Map>> years = (Map<String,List<Map>>)oo;
    			
        		Iterator<String> itr = years.keySet().iterator();
        		while(itr.hasNext()) {
        			String categorycd = itr.next();
        			
        			List<Map> yyyys = years.get(categorycd);
        			for(int i = 0, i2 = yyyys.size(); i < i2 ; i++) {
	        			Map yearMap = (Map)yyyys.get(i);
	        			String year = pnull(yearMap, "EVENTYEAR");
	        			String cate = pnull(yearMap, "CATEGORY_CODE");
	        			_params2.put("Q_YEAR", year);
	        			_params2.put("CATEGORY_CODE", cate);
	        			List xList = historyDao.historyEventsList(_params2, isAdminCall);
	        			
	        			rtMap.put(cate+"_"+year, xList);
	        			
	        			D(_logger, " ==>2. "+(cate+"_"+year + ","+ xList));
        			}
        		}
        		_params2.clear();
    		}
    		
    		D(_logger, " ==>3. "+(rtMap));
    	}
    	
        return rtObject;
    }

    public int historyEventsListCount(Map _params) throws Exception
    {
        return historyDao.historyEventsListCount(_params);
    }

    /**
     * 특정 카테고리의 연도목록과 해당년도의 행사들을 조회한다.
     */
    public List historyYearList(Map _params, String category_code) throws Exception
    {
    	return historyDao.historyEventyearsOnCategory(category_code);
    }

}
