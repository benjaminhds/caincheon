// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DgroupServiceImpl.java

package kr.caincheon.church.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import kr.caincheon.church.dao.DgroupDao;

// Referenced classes of package kr.caincheon.church.service:
//            DgroupService

/**
 * 
 * @author benjamin
 * @deprecated - 공통코드로 대체
 */

@Service("dgroupService")
public class DgroupServiceImpl
    implements DgroupService
{

    private final Logger _logger = Logger.getLogger(getClass());
    
    @Resource(name="dgroupDao")
    private DgroupDao dgroupDao;
    
    public List dgroupList(Map _params)
    {
        return dgroupDao.dgroupList(_params);
    }

    public int dgroupListCount(Map _params)
    {
        return dgroupDao.dgroupListCount(_params);
    }

    public Map dgroupContents(Map _params)
    {
        return dgroupDao.dgroupContents(_params);
    }

    public boolean dgroupInsert(Map _params)
    {
        return dgroupDao.dgroupInsert(_params);
    }

    public boolean dgroupModify(Map _params)
    {
        return dgroupDao.dgroupModify(_params);
    }

    public boolean dgroupDelete(Map _params)
    {
        return dgroupDao.dgroupDelete(_params);
    }

}
