// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AdmMemberServiceImpl.java

package kr.caincheon.church.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.base.CommonException;
import kr.caincheon.church.common.base.CommonService;
import kr.caincheon.church.common.email.EmailBean;
import kr.caincheon.church.common.email.EmailConst;
import kr.caincheon.church.common.email.EmailSenderOnSpring;
import kr.caincheon.church.dao.AdmMemberDao;

// Referenced classes of package kr.caincheon.church.service:
//            AdmMemberService

@Service("admMemberService")
public class AdmMemberServiceImpl extends CommonService implements AdmMemberService
{

    private final Logger _logger = Logger.getLogger(getClass());
    
    @Resource(name="admMemberDao")
    private AdmMemberDao admMemberDao;
    
    private void MeminfoAdditionalHanddle(Map m) throws CommonException
    {
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
        String festival = pnull(m.get("FESTIVALDAY"));
        if(festival.length() == 4)
        {
            m.put("festivalM", festival.substring(0, 2));
            m.put("festivalD", festival.substring(2));
        }
    }

    @Override
    public Map login(Map _params)
        throws CommonException
    {
        Map m = admMemberDao.admMemberLogin(_params);
        return m;
    }

    @Override
    public List admMemberList(Map _params)throws CommonException
    {
        return admMemberDao.inquireMemberList(_params);
    }

    @Override
    public int admMemberListCount(Map _params)throws CommonException
    {
        return admMemberDao.inquireMemberListCount(_params);
    }

    @Override
    public Map admMemberView(Map _params) throws CommonException
    {
        return admMemberDao.admMemberView(_params);
    }

    @Override
    public String selectAdminMemberInfo(Map _params) throws CommonException
    {
        return admMemberDao.admMemberSelectId(_params);
    }

    @Override
    public List admMemberDepartCodeList(Map _params) throws CommonException
    {
        return admMemberDao.admMemberDepartCodeList(_params);
    }

    @Override
    public boolean admMemberInsert(Map _params) throws CommonException
    {
        return admMemberDao.admMemberInsert(_params);
    }

    @Override
    public boolean admMemberModify(Map _params) throws CommonException
    {
        return admMemberDao.admMemberModify(_params);
    }

    @Override
    public boolean admMemberDelete(Map _params) throws CommonException
    {
        return admMemberDao.admMemberDelete(_params);
    }

    @Override
    public List admMemberNonList(Map _params) throws CommonException
    {
        return admMemberDao.admMemberNonList(_params);
    }

    @Override
    public List admMemberAuthList(Map _params) throws CommonException
    {
        return admMemberDao.admMemberAuthList(_params);
    }

    @Override
    public String admMemberAuthManage(Map _params) throws CommonException
    {
        return admMemberDao.admMemberAuthManage(_params);
    }

    /*
     * 관리자 > 회원 목록 조회
     * (non-Javadoc)
     * @see kr.caincheon.church.service.AdmMemberService#selectMemberList(java.util.Map)
     */
    @Override
	public CommonDaoDTO selectMemberList(Map _params) throws CommonException
	{
    	D(_logger, Thread.currentThread().getStackTrace(), " Service Call");
    	
    	CommonDaoDTO list = admMemberDao.selectMemberList(_params);
    	
    	D(_logger, Thread.currentThread().getStackTrace(), " Service end.. ["+list+"]");
    	
	    return list;
	}

//    @Override
//	public int selectMemberListCount(Map _params) throws CommonException
//	{
//	    return admMemberDao.selectMemberListCount(_params);
//	}

    @Override
	public String selectMemberId(Map _params) throws CommonException
	{
	    return admMemberDao.selectMemberId(_params);
	}

    @Override
	public Map selectMemberView(Map _params)
	    throws CommonException
	{
	    Map m = admMemberDao.selectMemberView(_params);
	    MeminfoAdditionalHanddle(m);
	    return m;
	}

    @Override
	public boolean insertMember(Map _params)
	    throws CommonException
	{
	    return admMemberDao.insertMember(_params);
	}

    @Override
	public boolean updateMember(Map _params)
	    throws CommonException
	{
    	boolean rtBool = admMemberDao.updateMember(_params);
    	if(rtBool) {
    		// 관리자에 의해서 휴면해제시 메일 보내기
    		String old = pnull(_params, "old_DormantYN");
    		String now = pnull(_params, "dormantYN", "N");
    		if( "Y".equals(old) && !old.equals(now) ) {
    			
    		}
    	}
	    return rtBool ;
	}

    @Override
	public boolean deleteMember(Map _params)
	    throws CommonException
	{
	    return admMemberDao.deleteMember(_params);
	}

    @Override
	public boolean restoreMember(Map _params)
	    throws CommonException
	{
	    return admMemberDao.restoreMember(_params);
	}
    
    /*
     * spring mail send component
     */
    @Resource(name="emailSenderOnSpring")
    private EmailSenderOnSpring emailSenderOnSpring;
    
    /**
     * 휴면계정에 메일 발송 
     * @see kr.caincheon.church.service.AdmMemberService#admMemberSendmaildormant()
     */
    @Override
	public Map admMemberSendmaildormant() throws CommonException {
    	Map rtMap = new HashMap();
    	
    	// mail component
    	EmailBean mailBean = new EmailBean();
    	mailBean.setSubject("Alert:휴면계정입니다. 휴면을 해제하시려면 이메일로 해제신청을 해주시기 바랍니다.");
    	
    	// list
    	List<Map> list = admMemberDao.listDormantMembers();
    	for(Map row : list) {
    		// mail send
    		String email = row.get("ID").toString();
    		try
            {
                mailBean.setTo(email);
                mailBean.setContent(EmailConst.getEMAIL_CONTENTS_DORMANT(email));
                emailSenderOnSpring.SendEmail(mailBean);
                
                // update flag by on
                admMemberDao.updateDormantMember(email);
            }
            catch(Exception e)
            {
                throw new CommonException(e.getMessage(), e);
            }
    	}
    	rtMap.put("count", list.size());

    	// free resource
    	admMemberDao.daoClose();
    	
    	return rtMap;
    }
}
