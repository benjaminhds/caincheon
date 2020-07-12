// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EuchaServiceImpl.java

package kr.caincheon.church.member.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import kr.caincheon.church.common.base.CommonException;
import kr.caincheon.church.common.email.EmailBean;
import kr.caincheon.church.common.email.EmailConst;
import kr.caincheon.church.common.email.EmailSenderOnSpring;
import kr.caincheon.church.common.utils.UtilString;
import kr.caincheon.church.dao.EuchaDao;

// Referenced classes of package kr.caincheon.church.service:
//            EuchaService
/**
 * 호출되는 URL이 없는 Controller에서 호출됨
 * @author benjamin
 * @deprecated
 */
@Service("euchaService")
public class EuchaServiceImpl
    implements EuchaService
{

    public List euchaList(Map _params)
    {
        return euchaDao.euchaList(_params);
    }

    public int euchaListCount(Map _params)
    {
        return euchaDao.euchaListCount(_params);
    }

    public Map euchaContents(Map _params)
    {
        Map result = euchaDao.euchaContents(_params);
        List slaveList = (List)result.get("slave");
        for(int i = 0; i < slaveList.size(); i++)
        {
            Map tmp = (Map)slaveList.get(i);
            if(++i < 10)
            {
                result.put((new StringBuilder("s_eucha_sno0")).append(i).toString(), tmp.get("EUCHA_SNO"));
                result.put((new StringBuilder("s_gubun0")).append(i).toString(), tmp.get("GUBUN"));
                result.put((new StringBuilder("s_id0")).append(i).toString(), tmp.get("ID"));
                result.put((new StringBuilder("s_name0")).append(i).toString(), tmp.get("NAME"));
                result.put((new StringBuilder("s_baptismal_name0")).append(i).toString(), tmp.get("BAPTISMAL_NAME"));
                result.put((new StringBuilder("s_birthday0")).append(i).toString(), tmp.get("BIRTHDAY"));
                result.put((new StringBuilder("s_m_tel0")).append(i).toString(), tmp.get("M_TEL"));
                result.put((new StringBuilder("s_before_number0")).append(i).toString(), tmp.get("BEFORE_NUMBER"));
                result.put((new StringBuilder("s_order_name0")).append(i).toString(), tmp.get("ORDER_NAME"));
            } else
            {
                result.put((new StringBuilder("s_eucha_sno")).append(i).toString(), tmp.get("EUCHA_SNO"));
                result.put((new StringBuilder("s_gubun")).append(i).toString(), tmp.get("GUBUN"));
                result.put((new StringBuilder("s_id")).append(i).toString(), tmp.get("ID"));
                result.put((new StringBuilder("s_name")).append(i).toString(), tmp.get("NAME"));
                result.put((new StringBuilder("s_baptismal_name")).append(i).toString(), tmp.get("BAPTISMAL_NAME"));
                result.put((new StringBuilder("s_birthday")).append(i).toString(), tmp.get("BIRTHDAY"));
                result.put((new StringBuilder("s_m_tel")).append(i).toString(), tmp.get("M_TEL"));
                result.put((new StringBuilder("s_before_number")).append(i).toString(), tmp.get("BEFORE_NUMBER"));
                result.put((new StringBuilder("s_order_name")).append(i).toString(), tmp.get("ORDER_NAME"));
            }
        }

        result.remove("slave");
        return result;
    }

    /**
     * 
     * @deprecated
     */
    public boolean euchaInsert(Map _params)
        throws CommonException
    {
        boolean success = euchaDao.euchaInsert(_params);
        if(success)
            try
            {
                String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                EmailBean bean = new EmailBean();
                bean.setTo(UtilString.pnull(_params.get("id")));
                bean.setSubject("[천주교 인천교구] 성체분배권 신청 메일입니다.");
                bean.setContent(EmailConst.getEMAIL_CONTENTS_EUCHA_REGISTER(bean.getTo(), uuid));
                emailSenderOnSpring.SendEmail(bean);
            }
            catch(Exception e)
            {
                throw new CommonException(e.getMessage(), e);
            }
        return success;
    }

    public boolean euchaModify(Map _params)
    {
        return euchaDao.euchaModify(_params);
    }

    public boolean euchaDelete(Map _params)
    {
        return euchaDao.euchaDelete(_params);
    }

    private final Logger _logger = Logger.getLogger(getClass());

    @Resource(name="euchaDao")
    private EuchaDao euchaDao;
    
    @Resource(name="emailSenderOnSpring")
    private EmailSenderOnSpring emailSenderOnSpring;
}
