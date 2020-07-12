// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CanaServiceImpl.java

package kr.caincheon.church.community.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import kr.caincheon.church.common.base.CommonService;
import kr.caincheon.church.dao.EngageDao;

// Referenced classes of package kr.caincheon.church.service:
//            CanaService

@Service("canaService")
public class CanaServiceImpl extends CommonService
    implements CanaService
{

    private final Logger _logger = Logger.getLogger(getClass());
    
    @Resource(name="engageDao")
    private EngageDao engageDao;

    /**
     * front side :: '약혼자주말 안내' 페이지에서 '일정' 조회 
     * (non-Javadoc)
     * @see kr.caincheon.church.community.service.CanaService#engageGuide(java.util.Map)
     */
    @Override
    public Map engageGuide(Map _params)
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "Call Service :: "+_params  );
    	
    	Map<String, String> rtMap = new HashMap<String,String>();
    	List<Map> list = engageDao.engageGuide(_params);
    	
    	int i = 1;
    	for(Map row : list) {
    		rtMap.put("FDATE"+ i , pnull(row.get("FKEY")) );
    		rtMap.put("TDATE"+ i , pnull(row.get("TKEY")) );
    		i++;
    	}
    	rtMap.put("COUNT", String.valueOf(i - 1));
    	
    	D(_logger, Thread.currentThread().getStackTrace(), "Return Service :: "+rtMap  );
    	
        return rtMap;
    }

   
}
