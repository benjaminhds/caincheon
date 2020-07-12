// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SchServiceImpl.java

package kr.caincheon.church.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import kr.caincheon.church.admin.dao.OrgHierarchyDao;
import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.base.CommonException;
import kr.caincheon.church.common.base.CommonService;
import kr.caincheon.church.dao.SchDao;

// Referenced classes of package kr.caincheon.church.service:
//            SchService

@Service("schService")
public class SchServiceImpl extends CommonService
    implements SchService
{

    private final Logger _logger = Logger.getLogger(getClass());
    
    @Resource(name="schDao")
    private SchDao schDao;
    
    @Resource(name="orgHierarchyDao")
    private OrgHierarchyDao orgHierarchyDao;
    
    @Override
    public CommonDaoDTO schList(Map _params)
    {
        return schDao.schList(_params);
    }

//    @Override
//    public int schListCount(Map _params)
//    {
//        return schDao.schListCount(_params);
//    }

    @Override
    public Map schContents(Map _params)
    {
        return schDao.schContents(_params);
    }

    @Override
    public boolean schInsert(Map _params)
    {
        return schDao.schInsert(_params);
    }

    @Override
    public boolean schModify(Map _params)
    {
        return schDao.schModify(_params);
    }

    @Override
    public boolean schDelete(Map _params)
    {
        return schDao.schDelete(_params);
    }

    @Override
    public Map scheduleByDiary(Map _params)
        throws Exception
    {
        return schDao.scheduleByDiary(_params);
    }

    @Override
    public List _1x00xList(Map _params)
    {
    	List rtList = null;
    	Map pm = new HashMap<String, String>();
		pm.put("lv1", pnull(_params, "LV1", "01' AND LV2!='00' AND LV3!='000"));/*교구청조직*/
		pnullPut(pm, "ORG_HIERARCHY_ORDERBY", " O.NAME ASC, O.LV1 ASC, O.LV2 ASC, O.LV3 ASC ");
		try {
			rtList = orgHierarchyDao.selectOrgHierarchy(pm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rtList;
    }

    @Override
    public Map schCView(Map _params)
    {
        return schDao.schCView(_params);
    }

    @Override
    public CommonDaoDTO schView(Map _params) throws Exception
    {
    	CommonDaoDTO dto = new CommonDaoDTO();
    	dto.daoDetailContent = schDao.schView(_params);
    	
    	Map pm = new HashMap<String, String>();
		pm.put("lv1", "01' AND LV2!='00' AND D.DEPART_IDX IS NOT NULL AND LV3!='000");
		pnullPut(pm, "ORG_HIERARCHY_ORDERBY", " O.NAME ASC, O.LV1 ASC, O.LV2 ASC, O.LV3 ASC ");
		try {
			dto.orgList = orgHierarchyDao.selectOrgHierarchy(pm);
		} catch (Exception e) {
			throw new CommonException("조직의 상하관계를 조회하지 못했습니다.", "EXPT-201", e);
		}
    	
        return dto; 
    }

}
