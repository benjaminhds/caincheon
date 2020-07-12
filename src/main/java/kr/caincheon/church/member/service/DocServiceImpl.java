// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DocServiceImpl.java

package kr.caincheon.church.member.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import kr.caincheon.church.common.base.CommonException;
import kr.caincheon.church.common.base.CommonService;
import kr.caincheon.church.common.base.Const;
import kr.caincheon.church.common.email.EmailBean;
import kr.caincheon.church.common.email.EmailSenderOnSpring;
import kr.caincheon.church.common.utils.FileUtils;
import kr.caincheon.church.common.utils.UtilFile;
import kr.caincheon.church.dao.DocDao;

// Referenced classes of package kr.caincheon.church.service:
//            DocService

@Service("docService")
public class DocServiceImpl extends CommonService
    implements DocService
{

    private final Logger _logger = Logger.getLogger(getClass());
    
    @Resource(name="docDao")
    private DocDao docDao;
    
    @Resource(name="emailSenderOnSpring")
    private EmailSenderOnSpring emailSenderOnSpring;
    
    private static final String IMG_URI = "upload/communitydoctrine/";
    
    @Override
    public List docList(Map _params)
    {
        return docDao.docList(_params);
    }

    @Override
    public int docListCount(Map _params)
    {
        return docDao.docListCount(_params);
    }

    @Override
    public Map docContents(Map _params)
    {
        return docDao.docContents(_params);
    }

    @Override
    public boolean docDelete(Map _params)
    {
        return docDao.docDelete(_params);
    }
    
    // 필수값이 있는지 여부
    private boolean hasMustFields(Map _params) {
    	if( !_params.containsKey("member_type") || !_params.containsKey("sex") || !_params.containsKey("church_idx") 
    			|| !_params.containsKey("birth_type") || !_params.containsKey("m_tel") || !_params.containsKey("email")  
    			) {
    		return false;
    	}
    	return true;
    }
    
    @Override
    public boolean docInsert(Map _params, HttpServletRequest request)
        throws CommonException
    {
    	// field check
    	if(!hasMustFields(_params)) {
    		throw new CommonException("필수 항목 누락입니다.");
    	}
    	
    	// file
        List list = null;
        try
        {
        	_params.put("CONTEXT_URI_PATH", "/"+IMG_URI);
        	_params.put("CONTEXT_ROOT_PATH", request.getSession().getServletContext().getRealPath("/") + IMG_URI);
            list = FileUtils.getInstance().parseInsertFileInfo(_params, request);
            if(list.size() > 0)
            {
                _params.put("filename", ((Map)list.get(0)).get("ORIGINAL_FILE_NAME"));
                _params.put("filesize", ((Map)list.get(0)).get("FILE_SIZE"));
                _params.put("strfilename", ((Map)list.get(0)).get("STORED_FILE_NAME"));
            }
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR", e);
        }
        // db
        boolean success = false;
		try {
			success = docDao.docInsert(_params);
		
			D(_logger, Thread.currentThread().getStackTrace(), "success ? "+success);
        
	        // send mail
	        if(!success) {
	        	throw new CommonException("신청서 저장에 실패 하였습니다. 작성한 내용을 확인 해 주세요.");
	        } else {
            	// 지정한 HTML 을 읽은 후 메일 내용으로 사용
            	String fileNm = pnull((Map)_params.get(SESSION_MAP), "CONTEXT_ROOT") + "/community/html/email_form_6.html";
            	String html   = UtilFile.readAllLines(fileNm);
            	
            	// in the parameters
            	String email = pnull(_params.get("email"));
                String name  = pnull(_params.get("name"));
                html = html.replace("$NAME", name);
                
                //
                EmailBean bean = new EmailBean();
                bean.setTo(email);
                bean.setFrom(Const.EMAIL_SENDER_ADDR_COMMUNICATION_DOCTRINE);
                bean.setFromName("통신교리 담당자");
                bean.setSubject("[천주교 인천교구] 통신교리 신청 안내 메일입니다.");
                bean.setContent(html);
                //bean.setContent(EmailConst.getEMAIL_CONTENTS_DOCTRINE_REGISTER(email, name));
                try {
					emailSenderOnSpring.SendEmail(bean);
				} catch (Exception e) {
					throw new CommonException("신청서 접수는 완료되었으나, 메일 발송은 실패했습니다. 관리자에게 에러 메시지를 전달해 주세요.["+e.getMessage()+"]", e);
				}
	        }
	        
		} catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "[ERROR]" + e.getMessage(), e);
        	throw new CommonException(e.getMessage(), e);
        }
		
        return success;
    }

    @Override
    public boolean docModify(Map _params, HttpServletRequest request)
        throws CommonException
    {
        List list = null;
        try
        {
        	_params.put("CONTEXT_URI_PATH", "/"+IMG_URI);
        	_params.put("CONTEXT_ROOT_PATH", request.getSession().getServletContext().getRealPath("/") + IMG_URI);
            list = FileUtils.getInstance().parseInsertFileInfo(_params, request);
            if(list.size() > 0)
            {
                _params.put("filename", ((Map)list.get(0)).get("ORIGINAL_FILE_NAME"));
                _params.put("filesize", ((Map)list.get(0)).get("FILE_SIZE"));
                _params.put("strfilename", ((Map)list.get(0)).get("STORED_FILE_NAME"));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        boolean success = docDao.docModify(_params);
        
        D(_logger, Thread.currentThread().getStackTrace(), "{success:"+success+"}");
        D(_logger, Thread.currentThread().getStackTrace(), "{approve_yn:"+pnull(_params, "approve_yn")+"}");
        D(_logger, Thread.currentThread().getStackTrace(), "{adminYn:"+pnull(_params, "adminYn")+"}");
        
//        if(success && pnull(_params.get("approve_yn")).equals("Y"))
//            if(!pnull(_params.get("adminYn")).equals("Y"));
        
        return success;
    }


}
