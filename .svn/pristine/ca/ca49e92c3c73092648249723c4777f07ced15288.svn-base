// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FaqServiceImpl.java

package kr.caincheon.church.intro.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import kr.caincheon.church.dao.FaqDao;

// Referenced classes of package kr.caincheon.church.service:
//            FaqService

@Service("faqService")
public class FaqServiceImpl
    implements FaqService
{

    public FaqServiceImpl()
    {
    }

    public List faqList(Map _params)
    {
        return faqDao.faqList(_params);
    }

    public int faqListCount(Map _params)
    {
        return faqDao.faqListCount(_params);
    }

    public Map faqContents(Map _params)
    {
        return faqDao.faqContents(_params);
    }

    public boolean faqInsert(Map _params)
    {
        return faqDao.faqInsert(_params);
    }

    public boolean faqModify(Map _params)
    {
        return faqDao.faqModify(_params);
    }

    public boolean faqDelete(Map _params)
    {
        return faqDao.faqDelete(_params);
    }

    private final Logger _logger = Logger.getLogger(getClass());
    
    @Resource(name="faqDao")
    private FaqDao faqDao;
}
