// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CommonUtilServiceImpl.java

package kr.caincheon.church.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import kr.caincheon.church.dao.CommonUtilDao;

// Referenced classes of package kr.caincheon.church.service:
//            CommonUtilService

@Service("commonUtilService")
public class CommonUtilServiceImpl
    implements CommonUtilService
{

    public CommonUtilServiceImpl()
    {
    }

    public List getDepartList(String depth)
        throws Exception
    {
        return commonUtilDao.getDepartList(depth);
    }

    public List getDGroupCodeList(String depth)
        throws Exception
    {
        return commonUtilDao.getDGroupCodeList(depth);
    }

    public List getPosList(String depth)
        throws Exception
    {
        return commonUtilDao.getPosList(depth);
    }

    public void freeDataSource()
    {
    }

    private final Logger _logger = Logger.getLogger(getClass());
    
    @Resource(name="commonUtilDao")
    private CommonUtilDao commonUtilDao;
}
