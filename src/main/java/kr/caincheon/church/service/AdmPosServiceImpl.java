// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AdmPosServiceImpl.java

package kr.caincheon.church.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import kr.caincheon.church.common.base.CommonException;
import kr.caincheon.church.common.utils.UtilString;
import kr.caincheon.church.dao.AdmPosDao;

// Referenced classes of package kr.caincheon.church.service:
//            AdmPosService

@Service("admPosService")
public class AdmPosServiceImpl
    implements AdmPosService
{

    public AdmPosServiceImpl()
    {
    }

    private void MeminfoAdditionalHanddle(Map m)
    {
        String tel = UtilString.pnull(m.get("TEL"));
        if(tel.length() > 0)
        {
            int len = tel.length();
            if(tel.indexOf("02") == 0)
            {
                m.put("TEL1", tel.substring(0, 2));
                m.put("TEL2", tel.substring(2, len - 4));
                m.put("TEL3", tel.substring(len - 4));
            } else
            {
                m.put("TEL1", tel.substring(0, 3));
                m.put("TEL2", tel.substring(3, len - 4));
                m.put("TEL3", tel.substring(len - 4));
            }
        }
        String festival = UtilString.pnull(m.get("FESTIVALDAY"));
        if(festival.length() == 4)
        {
            m.put("festivalM", festival.substring(0, 2));
            m.put("festivalD", festival.substring(2));
        }
    }

    public List posList(Map _params)
        throws CommonException
    {
        return admPosDao.posList(_params);
    }

    public int posListCount(Map _params)
        throws CommonException
    {
        return admPosDao.posListCount(_params);
    }

    public String admPosManage(Map _params)
        throws CommonException
    {
        return admPosDao.admPosManage(_params);
    }

    private final Logger _logger = Logger.getLogger(getClass());
    
    @Resource(name="admPosDao")
    private AdmPosDao admPosDao;
}
