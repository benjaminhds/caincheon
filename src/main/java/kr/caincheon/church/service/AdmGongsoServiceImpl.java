// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AdmGongsoServiceImpl.java

package kr.caincheon.church.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.base.CommonException;
import kr.caincheon.church.common.base.CommonService;
import kr.caincheon.church.common.utils.FileUtils;
import kr.caincheon.church.dao.AdmBonNoticeDao;
import kr.caincheon.church.dao.AdmGongsoDao;

// Referenced classes of package kr.caincheon.church.service:
//            AdmGongsoService

@Service("admGongsoService")
public class AdmGongsoServiceImpl extends CommonService
    implements AdmGongsoService
{

    private final Logger _logger = Logger.getLogger(getClass());
    //private FileUtils fileUtils;
    
    @Resource(name="admGongsoDao")
    private AdmGongsoDao admGongsoDao;

	@Resource(name="admBonNoticeDao")
    private AdmBonNoticeDao admBonNoticeDao;
	
	@Override
    public CommonDaoDTO admGongsoList(Map _params)
    {
		CommonDaoDTO dto = admGongsoDao.admGongsoList(_params);
		dto.setPaging(ipnull(_params, "pageNo", 1),  ipnull(_params, "pageSize", 20));
		return dto;
    }

	@Override
    public CommonDaoDTO admGongsoContents(Map _params)
        throws CommonException
    {
		CommonDaoDTO dto = new CommonDaoDTO();
		
		// 공소
		dto.daoDetailContent = admGongsoDao.admGongsoContents(_params);
		// 조직도
		dto.orgList = admBonNoticeDao._1x00xList(_params);
		// 공소미사 :: WT=월, WEEK_KEY=1, G_IDX=null, WEEK=null, MNAME=
		admGongsoDao.admGMissaList(_params, dto, true);
		if( dto.otherList.size() > 0 ) { // tag convert
			for(int i=0, i2=dto.otherList.size() ; i<i2; i++) {
				Map row = (Map)dto.otherList.get(i);
				String gidx  = pnull(row, "G_IDX");
				String week  = pnull(row, "WEEK");
				String mname = pnull(row, "MNAME");
				
				mname = mname.replaceAll("&lt;", "<")
						.replaceAll("&gt;", ">")
						.replaceAll("<span data-id=", "<span onclick=\"deleteMissa("+gidx+",this,"+week+")\" data-id=")
						;
				row.put("MNAME", mname);
			}
		}
		
		
        return dto;
    }
	
	@Override
    public boolean admGongsoInsert(Map _params)
        throws CommonException
    {
        return admGongsoDao.admGongsoInsert(_params);
    }

	@Override
    public boolean admGongsoModify(Map _params)
        throws CommonException
    {
        return admGongsoDao.admGongsoModify(_params);
    }

	@Override
    public boolean admGongsoDelete(Map _params)
        throws CommonException
    {
        return admGongsoDao.admGongsoDelete(_params);
    }

	@Override
    public String admGongsoMissaManage(Map _params)
        throws CommonException
    {
        return admGongsoDao.admGongsoMissaManage(_params);
    }

}
