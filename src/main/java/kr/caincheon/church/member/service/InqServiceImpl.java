package kr.caincheon.church.member.service;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.base.CommonException;
import kr.caincheon.church.common.base.CommonService;
import kr.caincheon.church.common.email.EmailSenderOnSpring;
import kr.caincheon.church.common.utils.UtilHTML;
import kr.caincheon.church.dao.InqDao;

/**
 * 나누고싶은 이야기(1:1 문의하기) service component
 * @author benjamin
 */
@Service("inqService")
public class InqServiceImpl extends CommonService
    implements InqService
{

    private final Logger _logger = Logger.getLogger(getClass());
    
    @Resource(name="inqDao")
    private InqDao inqDao;
    
    @Resource(name="emailSenderOnSpring")
    private EmailSenderOnSpring emailSenderOnSpring;
    
    /*
     * (non-Javadoc)
     * @see kr.caincheon.church.member.service.InqService#inqList(java.util.Map, boolean)
     */
    @Override
    public CommonDaoDTO inqList(Map _params, boolean isAdmin)
    {
        return inqDao.inqList(_params, isAdmin);
    }

    /*
     * (non-Javadoc)
     * @see kr.caincheon.church.member.service.InqService#inqContents(java.util.Map, boolean)
     */
    @Override
    public Map inqContents(Map _params, boolean isAdmin)
    {
        return inqDao.inqContents(_params, isAdmin);
    }

    /*
     * (non-Javadoc)
     * @see kr.caincheon.church.member.service.InqService#inqInsert(java.util.Map, boolean)
     */
    @Override
    public boolean inqInsert(Map _params, boolean isAdmin)
        throws CommonException
    {
        return inqDao.inqInsert(_params, isAdmin);
    }

    /*
     * (non-Javadoc)
     * @see kr.caincheon.church.member.service.InqService#inqModify(java.util.Map, boolean)
     */
    @Override
    public boolean inqModify(Map _params, boolean isAdmin)
    {
    	// focus ogic
    	String replyType = pnull(_params, "replyType");
    	String reply     = UtilHTML.removeHTML(pnull(_params, "reply"));
    	if("1".equals(replyType) && reply.length() > 2) {
    		_params.put("replyType", "2");
    	}
    	
        return inqDao.inqModify(_params, isAdmin);
    }

    /*
     * (non-Javadoc)
     * @see kr.caincheon.church.member.service.InqService#inqDelete(java.util.Map, boolean)
     */
    @Override
    public boolean inqDelete(Map _params, boolean isAdmin)
    {
    	String adm_delete_flag = getParameter(_params, "adm_delete_flag");
    	String delete_y        = getParameter(_params, "delete_y");
    	
    	if(isAdmin) {
    		if("Y".equals(adm_delete_flag)) {
    			return inqDao.inqAdmRecovery(getParameter(_params, "inquire_no"));
    		}
    	}
    	
        return inqDao.inqDelete(_params, isAdmin);
    }

}
