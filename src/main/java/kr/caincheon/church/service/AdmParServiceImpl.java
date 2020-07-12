// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OrgHierarchyServiceImpl.java

package kr.caincheon.church.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import kr.caincheon.church.common.CommonStorage;
import kr.caincheon.church.common.base.CommonException;
import kr.caincheon.church.common.base.CommonService;
import kr.caincheon.church.common.utils.FileUtils;
import kr.caincheon.church.dao.AdmParDao;

// Referenced classes of package kr.caincheon.church.service:
//            AdmParService

@Service("admParService")
public class AdmParServiceImpl extends CommonService
    implements AdmParService
{

    public AdmParServiceImpl()
    {
    }

    public List parList(Map _params)
    {
        return admParDao.parList(_params);
    }

    public int parListCount(Map _params)
    {
        return admParDao.parListCount(_params);
    }

    public Map parContents(Map _params)
        throws CommonException
    {
        return admParDao.parContents(_params);
    }

    public boolean parInsert(Map _params, HttpServletRequest request)
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
        return admParDao.admParInsert(_params, list);
    }

    public boolean parModify(Map _params, HttpServletRequest request)
        throws CommonException
    {
    	// captcha 검증 :: 관리자는 적용하지 않음.
//    	String captcha = pnull(_params, "captcha");
//    	if(captcha.length()==0) throw new CommonException("보안문자 누락입니다.", "ERR-700");
//    	if( !CommonStorage.getInstance().isExpreCaptcha( captcha ) ) {
//    		throw new CommonException("보안문자가 유효시간이 지났거나, 임의 생성된 보안문자입니다.", "ERR-701");
//    	}
    	
    	// 파일 업로드 & 정보를 list화
        List list = null;
        try {
        	_params.put("CONTEXT_URI_PATH", "/upload/church_notice/");
        	_params.put("CONTEXT_ROOT_PATH", request.getSession().getServletContext().getRealPath("/") + "upload/church_notice/");
        	
        	list = FileUtils.getInstance().parseInsertFileInfo(_params, request);
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        // db call
        return admParDao.admParModify(_params, list);
    }

    public boolean parDelete(Map _params)
        throws CommonException
    {
    	// captcha 검증
    	String captcha = pnull(_params, "captcha");
    	if(captcha.length()==0) throw new CommonException("보안문자 누락입니다.", "ERR-700");
    	if( !CommonStorage.getInstance().isExpreCaptcha( captcha ) ) {
    		throw new CommonException("보안문자가 유효시간이 지났거나, 임의 생성된 보안문자입니다.", "ERR-701");
    	}

        // db call
        return admParDao.admParDelete(_params);
    }

    private final Logger _logger = Logger.getLogger(getClass());
    
    
    @Resource(name="admParDao")
    private AdmParDao admParDao;
}
