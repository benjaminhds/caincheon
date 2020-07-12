// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MarryServiceImpl.java

package kr.caincheon.church.member.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import kr.caincheon.church.common.base.CommonException;
import kr.caincheon.church.common.base.CommonService;
import kr.caincheon.church.common.base.Const;
import kr.caincheon.church.common.email.EmailConst;
import kr.caincheon.church.common.email.EmailSenderOnSpring;
import kr.caincheon.church.common.utils.UtilDate;
import kr.caincheon.church.common.utils.UtilFile;
import kr.caincheon.church.dao.MarryDao;

// Referenced classes of package kr.caincheon.church.service:
//            MarryService

@Service("marryService")
public class MarryServiceImpl extends CommonService
    implements MarryService
{

	private final Logger _logger = Logger.getLogger(getClass());
    
    @Resource(name="marryDao")
    private MarryDao marryDao;
    
    @Resource(name="emailSenderOnSpring")
    private EmailSenderOnSpring emailSenderOnSpring;

    
    @Override
    public List marryList(Map _params)
    {
        return marryDao.marryList(_params);
    }

    @Override
    public int marryListCount(Map _params)
    {
        return marryDao.marryListCount(_params);
    }

    @Override
    public Map marryContents(Map _params)
    {
        return marryDao.marryContents(_params);
    }
    
    /**
     * 카나혼 접수 시 접수 알림 메일
     */
    @Override
    public boolean marryInsert(Map _params) throws CommonException 
    {
    	_logger.debug(" service called marryInsert() :: " + _params);
        boolean success = false;
		try {
			success = marryDao.marryInsert(_params);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
        _logger.debug(" db save result ? :: " + success);
        
        if(success) {
            try {
            	// apply_type=1 && lecture_apply_day
            	// 메일 내용파일 읽기 & 변수 변경
            	String lecture_day = "" ;
            	String aptype = pnull(_params, "apply_type", "1") ;
            	String fileNm = pnull((Map)_params.get(SESSION_MAP), "CONTEXT_ROOT");
            	if("1".equals(aptype)) {
            		lecture_day = pnull(_params, "lecture_apply_day").replaceAll("-", "") ;
            		fileNm += "/community/html/email_form_1.html";//카나혼인강좌 신청
            	} else {
            		lecture_day = pnull(_params, "lecture_apply_day2").replaceAll("-", "") ;
            		fileNm += "/community/html/email_form_2.html";//약혼자주말 신청
            	}
            	
            	_logger.debug(" main fileNm ? :: " + fileNm);
            	_logger.debug(" main lecture_day ? :: " + lecture_day);
            	
            	// mail form html file load...
            	String htmlM  = UtilFile.readAllLines(fileNm);
            	htmlM = htmlM.replace("$YYYY", lecture_day.substring(0, 4))
        				.replace("$MM", lecture_day.substring(4, 6))
        				.replace("$DD", lecture_day.substring(6, 8));

            	// 메일 보내기
            	//String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            	String to = pnull(_params.get("email"))
            			, from = Const.EMAIL_SENDER_ADDR_CANAHONIN_LECTURE
            			, fromName = "카나혼인 담당자"
            			, subject = "[천주교 인천교구] 카나혼인강좌 신청 메일입니다."
            			//, content = html //EmailConst.getEMAIL_CONTENTS_MARRY_REGISTER(to, uuid)
            		;
            	
            	_logger.debug(" main to ? :: " + to);
            	_logger.debug(" main from ? :: " + from);
            	_logger.debug(" main subject ? :: " + subject);
            	
            	emailSenderOnSpring.SendEmail(to, from, fromName, subject, htmlM);
            } catch(Exception e) {
            	success = false;
                throw new CommonException(e.getMessage(), e);
            }
        } else {
        	success = false;
        }
        
        return success;
    }

    /**
     * 카나혼접수에 대한 승인 완료시 승인완료 알림 메일
     */
    @Override
    public boolean marryModify(Map _params)
        throws CommonException
    {
        boolean success = marryDao.marryModify(_params);
        if(success && pnull(_params.get("adminYn")).equals("Y")) {
            try {
                // 접수확인완료시에만 메일 발송
                if(pnull(_params.get("process_status")).equals("2")) {
                	
                	D(_logger, Thread.currentThread().getStackTrace(), "Admin Send Mail Start..." );
                	
                	String lecture_day = "", aptype = pnull(_params, "apply_type", "1") ;
                	if("1".equals(aptype)) {
                		lecture_day = pnull(_params, "lecture_apply_day").replaceAll("-", "") ;
                	} else {
                		lecture_day = pnull(_params, "lecture_apply_day2").replaceAll("-", "") ;
                	}
                	
                	// 메일 내용파일 읽기 & 변수 변경 :: 승인완료는 같은 폼
                	String fileNm = pnull((Map)_params.get(SESSION_MAP), "CONTEXT_ROOT") + "/community/html/email_form_3.html";
                	String html   = UtilFile.readAllLines(fileNm);
                	
                	//html = html.replace("$REPEATCOUNT", pnull(_params, "id", "1"));
                	html = html.replace("$YYYY", lecture_day.substring(0,4));
                	html = html.replace("$MM", lecture_day.substring(4,6));
                	html = html.replace("$DD", lecture_day.substring(6,8));
                	
                	//
                	String to = pnull(_params.get("email"))
                			, from = Const.EMAIL_SENDER_ADDR_CANAHONIN_LECTURE
                			, fromName = "카나혼인 담당자"
                			, subject = "[천주교 인천교구] 카나혼인강좌 승인 메일입니다."
                			, content = html // EmailConst.getEMAIL_CONTENTS_MARRY_APPLY(to, uuid)
                			;
                	
                    emailSenderOnSpring.SendEmail(to, from, fromName, subject, content);
                    
                    D(_logger, Thread.currentThread().getStackTrace(), "Admin Send Mail Ended..." );
                }
            } catch(Exception e) {
            	e.printStackTrace();
                throw new CommonException(e.getMessage(), e);
            }
        }
        return success;
    }

    @Override
    public boolean marryDelete(Map _params)
    {
        return marryDao.marryDelete(_params);
    }

    @Override
    public List marryLectureList(Map _params)
    {
        return marryDao.marryLectureList(_params);
    }

    @Override
    public List marryLectureList2(Map _params)
        throws CommonException
    {
        return marryDao.marryLectureList2(_params);
    }

    @Override
    public Map marryGuideContents(Map _params)
    {
        return marryDao.marryGuideContents(_params);
    }

    @Override
    public List marryDList(Map _params)
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "Service Called. [params:"+_params+"]" );
    	List list = marryDao.marryDList(_params);
    	
    	if(list!=null && list.size() > 0) {
	    	for(int i=0, i2=list.size() ; i<i2 ; i++) {
	    		Map row = (Map)list.get(i);
	    		String yoil = UtilDate.printYoilOfDay(pnull(row, "LECTURE_DATE"));
	    		row.put("YOIL", "("+yoil+")");
	    	}
    	}
    	
    	D(_logger, Thread.currentThread().getStackTrace(), "Service Return. : "+list );
        return list;
    }

    @Override
    public List marryTList(Map _params)
    {
        return marryDao.marryTList(_params);
    }

    /**
     * 카나혼인강좌 일일 일정 수정
     * (non-Javadoc)
     * @see kr.caincheon.church.member.service.MarryService#marryGTimeUpsert(java.util.Map)
     */
    @Override
    public boolean marryGTimeUpsert(Map _params)
        throws CommonException
    {
        return marryDao.marryGTimeUpsert(_params);
    }

    @Override
    public boolean marryGTimeDelete(Map _params)
        throws CommonException
    {
        return marryDao.marryGTimeDelete(_params);
    }

    
}
