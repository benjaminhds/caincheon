// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DepartdcServiceImpl.java

package kr.caincheon.church.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import kr.caincheon.church.admin.dao.OrgHierarchyDao;
import kr.caincheon.church.common.base.CommonException;
import kr.caincheon.church.common.base.CommonService;
import kr.caincheon.church.common.base.Const;
import kr.caincheon.church.dao.DepartdcDao;

// Referenced classes of package kr.caincheon.church.service:
//            DepartdcService

@Service("departdcService")
public class DepartdcServiceImpl extends CommonService 
    implements DepartdcService
{

	/* bjm modify */
    @Resource(name="orgHierarchyDao")
    private OrgHierarchyDao orgHierarchyDao;
    

    public List<Map> departdcList(Map _params) throws CommonException
    {
    	List<Map> rtList = null;
    	
    	/* bjm modify */
    	// old routine
    	//rtList = departdcDao.departdcList(_params);
    	//new routine
    	Map<String, String> where = new HashMap<String, String>();
    	where.putAll(_params); // 복제
    	where.put("lv1", "01"); // 교구청 코드
    	try {
			rtList = orgHierarchyDao.selectOrgHierarchy(where);
			if(rtList!=null) {
				for(Map row : rtList) {
					if( !row.containsKey("DEPART_CODE") ) {
						row.put("DEPART_CODE", row.get("LV3"));
					}
				}
			}
    	} catch ( CommonException e) {
    		throw e;
		} catch (Exception e) {
			throw new CommonException(e.getMessage(), "ERR-800", e);
		}
    	
        return rtList;
    }

    public int departdcListCount(Map _params)
    {
        return departdcDao.departdcListCount(_params);
    }

    public Map departdcContents(Map _params) throws CommonException
    {
    	// Org Hierarchy
    	List<Map> listG1 = null;
    	List<Map> listG2 = null;
		try {
			listG1 = orgHierarchyDao.selectOrgHierarchyGroupby(1, "01");
			listG2 = orgHierarchyDao.selectOrgHierarchyGroupby(2, "01");
//			for(Map row : listG2){
//				if(pnull(row, "LV1").equals("01")) {/* 교구청 조직만 */
//					listG.add(row);
//				}
//			}
//			listG2.clear();
//			listG2 = null;
    	} catch (CommonException e) {
    		throw e;
		} catch (Exception e) {
			throw new CommonException("조직의 상하관계를 조회하지 못했습니다.", "EXPT-000", e);
		}
		
    	// Org information
    	Map rtMap = departdcDao.departdcContents(_params);
    	
    	// return
    	rtMap.put(Const.ADM_MAPKEY_GROUPLIST+"1", listG1);
		rtMap.put(Const.ADM_MAPKEY_GROUPLIST+"2", listG2);
		
        return rtMap;
    }

    public List<Map> priestList(Map _params)
    {
        return departdcDao.priestList(_params);
    }

    public List<Map> departdcViewGetPriestList(Map _params)
    {
        return departdcDao.departdcViewGetPriestList(_params);
    }

    public List<Map> _1x000List(Map _params)
    {
        return departdcDao._1x000List(_params);
    }

    public boolean departdcInsert(Map _params)
    {
        return departdcDao.departdcInsert(_params);
    }

    public boolean departdcModify(Map _params) throws CommonException
    {
        return departdcDao.departdcModify(_params);
    }

    public boolean departdcDelete(Map _params)
    {
        return departdcDao.departdcDelete(_params);
    }

    private final Logger _logger = Logger.getLogger(getClass());
    
    @Resource(name="departdcDao")
    private DepartdcDao departdcDao;
}
