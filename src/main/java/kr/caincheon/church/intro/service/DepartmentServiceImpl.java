// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DepartmentServiceImpl.java

package kr.caincheon.church.intro.service;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import kr.caincheon.church.common.base.CommonDaoMultiDTO;
import kr.caincheon.church.common.base.CommonService;
import kr.caincheon.church.common.utils.UtilString;
import kr.caincheon.church.dao.DepartmentDao;

// Referenced classes of package kr.caincheon.church.service:
//            DepartmentService

@Service("departmentService")
public class DepartmentServiceImpl extends CommonService
    implements DepartmentService
{

    private final Logger _logger = Logger.getLogger(getClass());
    
    @Resource(name="departmentDao")
    private DepartmentDao departmentDao;
    
    @Override
    public void selectDepartment(Map _params, CommonDaoMultiDTO dto)
        throws Exception
    {
    	String lv1 = pnull(_params.get("lv1"));
    	String lv2 = pnull(_params.get("lv2"));
    	String lv3 = pnull(_params.get("lv3"));
    	
    	if(lv2.length()>0) lv2 = lv2.replace("x", "%");
    	if(lv3.length()>0) lv3 = lv3.replace("xx", "x").replace("x", "%");
    	
        java.util.List<Map> rtList = departmentDao.selectDepartment( lv1, lv2, lv3 );
        dto.daoResult = rtList;
    }

    @Override
    public void freeDataSource() {
    	departmentDao.free();
    }

    /**
     * 통합검색 서비스 :: 교구청 ( Called by UnifySearchServiceImpl)
     * @see kr.caincheon.church.intro.service.DepartmentService#unifySearch(java.util.Map, java.sql.Connection)
     */
	@Override
	public List unifySearch(Map _params, Connection conn) throws Exception {
		D(_logger, Thread.currentThread().getStackTrace(), "Service Called. params:"+_params );
		
		List list = departmentDao.unifySearch(_params, conn);
		
		D(_logger, Thread.currentThread().getStackTrace(), "Service Returned. results:"+list );
		
		return list;
	}

    
    
    
}
