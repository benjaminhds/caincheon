// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AdmBonNoticeServiceImpl.java

package kr.caincheon.church.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import kr.caincheon.church.common.base.CommonException;
import kr.caincheon.church.common.utils.FileUtils;
import kr.caincheon.church.dao.AdmBonNoticeDao;

// Referenced classes of package kr.caincheon.church.service:
//            AdmBonNoticeService

@Service("admBonNoticeService")
public class AdmBonNoticeServiceImpl
    implements AdmBonNoticeService
{

	/*  */
	@Resource(name="admBonNoticeDao")
	private AdmBonNoticeDao admBonNoticeDao;

	@Override
    public List admBonNoticeList(Map _params)
    {
        return admBonNoticeDao.admBonNoticeList(_params);
    }

    @Override
    public int admBonNoticeListCount(Map _params)
    {
        return admBonNoticeDao.admBonNoticeListCount(_params);
    }

    @Override
    public Map admBonNoticeContents(Map _params)
        throws CommonException
    {
        return admBonNoticeDao.admBonNoticeContents(_params);
    }

    @Override
    public boolean admBonNoticeInsert(Map _params, HttpServletRequest request)
        throws CommonException
    {
        List list = null;
        try
        {
        	_params.put("CONTEXT_URI_PATH", "/upload/church_notice/");
        	_params.put("CONTEXT_ROOT_PATH", request.getSession().getServletContext().getRealPath("/") + "upload/church_notice/");
            list = FileUtils.getInstance().parseInsertFileInfo(_params, request);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return admBonNoticeDao.admBonNoticeInsert(_params, list);
    }

    @Override
    public boolean admBonNoticeModify(Map _params, HttpServletRequest request)
        throws CommonException
    {
        List list = null;
        try
        {
        	_params.put("CONTEXT_URI_PATH", "/upload/church_notice/");
        	_params.put("CONTEXT_ROOT_PATH", request.getSession().getServletContext().getRealPath("/") + "upload/church_notice/");
            list = FileUtils.getInstance().parseInsertFileInfo(_params, request);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return admBonNoticeDao.admBonNoticeModify(_params, list);
    }

    @Override
    public boolean admBonNoticeDelete(Map _params)
        throws CommonException
    {
        return admBonNoticeDao.admBonNoticeDelete(_params);
    }

    @Override
    public List _1x00xList(Map _params)
    {
        return admBonNoticeDao._1x00xList(_params);
    }

    private final Logger _logger = Logger.getLogger(getClass());
    
}
