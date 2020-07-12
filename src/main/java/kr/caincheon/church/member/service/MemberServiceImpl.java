// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MemberServiceImpl.java

package kr.caincheon.church.member.service;

import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.base.CommonException;
import kr.caincheon.church.common.base.CommonService;
import kr.caincheon.church.common.email.EmailBean;
import kr.caincheon.church.common.email.EmailConst;
import kr.caincheon.church.common.email.EmailSenderOnSpring;
import kr.caincheon.church.dao.InqDao;
import kr.caincheon.church.dao.MemberDao;

// Referenced classes of package kr.caincheon.church.service:
//            MemberService

@Service("memberService")
public class MemberServiceImpl extends CommonService
    implements MemberService
{

    private final Logger _logger = Logger.getLogger(getClass());
    
    /*
     * 회원데이블 관리
     */
    @Resource(name="memberDao")
    private MemberDao memberDao;

    /*
     * 나누고싶은이야기(1:1상담글) 테이블 관리
     */
    @Resource(name="inqDao")
    private InqDao inqDao;
    
    @Resource(name="emailSenderOnSpring")
    private EmailSenderOnSpring emailSenderOnSpring;
    
    /*
     * 
     */
    private void MeminfoAdditionalHanddle(Map m) throws Exception
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "Service Called. [m:"+m+"]" );
    	
    	// case 1
        String tel = pnull(m.get("TEL"));
        int len = tel.length();
        if(len > 0)
        {
        	try {
        		if(tel.indexOf("02") == 0) {
	                m.put("TEL1", tel.substring(0, 2));
	                m.put("TEL2", tel.substring(2, len - 4));
	                m.put("TEL3", tel.substring(len - 4));
	            } else if(tel.indexOf("032") == 0 || tel.indexOf("031") == 0 || tel.indexOf("01") == 0) {
	                m.put("TEL1", tel.substring(0, 3));//앞의 3자리
	                m.put("TEL2", tel.substring(3, len - 4));//중간남은거
	                m.put("TEL3", tel.substring(len - 4));//마지막 4자리
	            } else {
            		if(len>3) m.put("TEL1", tel.substring(0, 3));
            		if(len - 4 > 3) m.put("TEL2", tel.substring(3, len - 4));
            		if(len - 4 > 0) m.put("TEL3", tel.substring(len - 4));
	            }
        	} catch (Exception e) {
				//e.printStackTrace();
			}
        }
        
        // case 2
        String festival = pnull(m.get("FESTIVALDAY"));
        if(festival.length() == 4)
        {
            m.put("festivalM", festival.substring(0, 2));
            m.put("festivalD", festival.substring(2));
        }
    }

    @Override
    public Map login(Map _params)
        throws CommonException, Exception
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "Service Called. [params:"+_params+"]" );
    	
    	Map m = memberDao.login(_params);
        MeminfoAdditionalHanddle(m);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Service return. [m:"+m+"]" );
        return m;
    }

    /**
     * 회원가입 데이터를 저장하고, 메일을 발송한다.
     */
    @Override
    public boolean join(Map _params)
        throws CommonException, Exception
    {
        boolean isInserted = false;

        try {
        	isInserted = memberDao.join(_params);
        	if( !isInserted ) {
            	throw new CommonException("회원가입 정보를 저장하지 못했습니다. 입력 정보를 다시 확인해 주세요.");
            }
        	
        	//sns로 이메일을 받아온 것(login_type=2)이 아니라면, email 인증을 해야 함.
        	String logintp = pnull(_params, "login_type", "1");
        	if(!"2".equals(logintp)) {
	            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
	            EmailBean bean = new EmailBean();
	            bean.setTo(pnull(_params, "id"));
	            bean.setSubject("[천주교 인천교구] 회원가입 이메일 인증 메일입니다.");
	            bean.setContent(EmailConst.getEMAIL_CONTENTS_MEMBER_JOIN(bean.getTo(), uuid));
	            try {
					emailSenderOnSpring.SendEmail(bean);
				} catch (Exception e) {
					throw new CommonException("회원가입 인증 메일 발송이 실패했습니다. 교구청 전산부에 문의 해 주세요.["+e.getMessage()+"]", e);
				}
        	}
            
        } catch(Exception e) {
        	memberDao.joinCancel(pnull(_params,"id"));
            throw new CommonException(e.getMessage(), e);
        }
        
        return isInserted;
    }
    
    @Override
    public boolean joinFinish(Map _params)
        throws CommonException, Exception
    {
        return memberDao.joinFinish(_params);
    }

    @Override
    public Map memberDormantClearRequest(Map _params)
        throws CommonException, Exception
    {
    	// TODO stoken 은 별도 저장후 인증 필요.
    	String stoken = UUID.randomUUID().toString().replaceAll("-", "");
        Map m = memberDao.memberDormantClearRequest(_params);
        
        try {
            EmailBean bean = new EmailBean();
            bean.setTo(pnull(m, "ID"));
            bean.setSubject("[천주교 인천교구] 휴면계정 해제 인증 메일입니다.");
            bean.setContent(EmailConst.getEMAIL_CONTENTS_DORMANTCLEAR(bean.getTo(), stoken));
            emailSenderOnSpring.SendEmail(bean);
        } catch(Exception e) {
            throw new CommonException(e.getMessage(), e);
        }
        return m;
    }

    @Override
    public boolean memberDormantClearConfirm(Map _params)
        throws CommonException, Exception
    {
        return memberDao.memberDormantClearConfirm(_params);
    }

    @Override
    public Map memberFindId(Map _params)
        throws CommonException, Exception
    {
        Map m = memberDao.memberFindId(_params);
        MeminfoAdditionalHanddle(m);
        return m;
    }

    @Override
    public Map memberFindPwd(Map _params)
        throws CommonException, Exception
    {
        _params.put("id", _params.remove("email"));
        Map m = memberDao.memberFindPwd(_params);
        try
        {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String pwd = pnull(m.get("PASSWORD"));
            EmailBean bean = new EmailBean();
            bean.setTo(pnull(m, "ID"));
            bean.setSubject("[천주교 인천교구] 계정 아이디/암호 찾기 메일입니다.");
            bean.setContent(EmailConst.getEMAIL_CONTENTS_FIND_PWD(bean.getTo(), pwd, uuid));
            emailSenderOnSpring.SendEmail(bean);
        }
        catch(Exception e)
        {
            throw new CommonException(e.getMessage(), e);
        }
        return m;
    }

    @Override
    public Map memberModifyInformation(Map _params)
        throws CommonException, Exception
    {
        Map m = memberDao.memberModifyInformation(_params);
        MeminfoAdditionalHanddle(m);
        return m;
    }
    
    /**
     * 회원이 나의정보에서 탈퇴버튼을 누를때 처리
     * - 회원 탈퇴 : 탈퇴처리(Flag is on) 및 나누고싶은이야기 탈퇴처리(Flag is on)
     * (non-Javadoc)
     * @see kr.caincheon.church.member.service.MemberService#memberLeave(java.util.Map)
     */
    @Override
    public boolean memberLeave(Map _params)
        throws CommonException, Exception
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "Service Called.[params:"+_params+"]" );

    	// 탈퇴
    	CommonDaoDTO dto = memberDao.memberLeave(_params);
        
    	// 나누고싶은 이야기 글 삭제flag on('Y')
    	String memId = getParameter(_params, "id");
    	String renameId = String.valueOf(dto.otherData); // 탈퇴시 변경한 ID로 그 사람의 글쓴이의 ID로 동일하게 변경
    	int i = inqDao.inqMemberLeave(memId, renameId);
    	
        D(_logger, Thread.currentThread().getStackTrace(), "Service Result dto: "+dto+", inqDao Updated Count:"+i+"}]" );
        
        return  dto.isBool;
    }

    @Override
    public Map selectMemberInfo(Map _params)
        throws CommonException, Exception
    {
        return memberDao.selectMemberInfo(_params);
    }

}
