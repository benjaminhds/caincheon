// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EmailServiceImpl.java

package kr.caincheon.church.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import kr.caincheon.church.dao.EmailDao;

// Referenced classes of package kr.caincheon.church.service:
//            EmailService

@Service("emailService")
public class EmailServiceImpl
    implements EmailService
{


    public List emailList(Map _params)
    {
        return emailDao.emailList(_params);
    }

    public int emailListCount(Map _params)
    {
        return emailDao.emailListCount(_params);
    }

    public Map emailContents(Map _params)
    {
        return emailDao.emailContents(_params);
    }

    public boolean emailInsert(Map _params)
    {
        return emailDao.emailInsert(_params);
    }

    public boolean emailModify(Map _params)
    {
        return emailDao.emailModify(_params);
    }

    public boolean emailDelete(Map _params)
    {
        return emailDao.emailDelete(_params);
    }

    private final Logger _logger = Logger.getLogger(getClass());
    
    @Resource(name="emailDao")
    private EmailDao emailDao;
}
