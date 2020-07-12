// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MgzServiceImpl.java

package kr.caincheon.church.news.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import kr.caincheon.church.dao.MgzDao;

// Referenced classes of package kr.caincheon.church.service:
//            MgzService

@Service("mgzService")
public class MgzServiceImpl
    implements MgzService
{

    public MgzServiceImpl()
    {
    }

    public List mgzList(Map _params)
    {
        return mgzDao.mgzList(_params);
    }

    public int mgzListCount(Map _params)
    {
        return mgzDao.mgzListCount(_params);
    }

    public Map mgzContents(Map _params)
    {
        return mgzDao.mgzContents(_params);
    }

    public String mgzMaxNo(Map _params)
    {
        return mgzDao.mgzMaxNo(_params);
    }

    public String mgzTitle(Map _params)
    {
        return mgzDao.mgzTitle(_params);
    }

    public boolean mgzInsert(Map _params)
    {
        return mgzDao.mgzInsert(_params);
    }

    public boolean mgzModify(Map _params)
    {
        return mgzDao.mgzModify(_params);
    }

    public boolean mgzDelete(Map _params)
    {
        return mgzDao.mgzDelete(_params);
    }

    private final Logger _logger = Logger.getLogger(getClass());
    
    @Resource(name="mgzDao")
    private MgzDao mgzDao;
}
