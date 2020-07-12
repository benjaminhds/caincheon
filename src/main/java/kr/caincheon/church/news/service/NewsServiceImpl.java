// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NewsServiceImpl.java

package kr.caincheon.church.news.service;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import kr.caincheon.church.admin.dao.NBoardDao;
import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.base.CommonException;
import kr.caincheon.church.common.base.CommonService;
import kr.caincheon.church.dao.NewsDao;

// Referenced classes of package kr.caincheon.church.service:
//            NewsService

@Service("newsService")
public class NewsServiceImpl extends CommonService
    implements NewsService
{
    private final Logger _logger = Logger.getLogger(getClass());
    
    @Resource(name="newsDao")
    private NewsDao newsDao;

    @Resource(name="nBoardDao")
    private NBoardDao nBoardDao;
    
    private static final String menu = "news";
    
    @Override
    public String getBoardCategory(Map _params)
    {
        return newsDao.getBoardCategory(_params);
    }

    @Override
    public CommonDaoDTO newsList(Map _params)
    {
    	pnullPutBidxAll(_params, "board_02");
    	CommonDaoDTO dto = newsDao.newsList(_params);
    	return dto;
    }

    @Override
    public Map newsContents(Map _params) throws CommonException
    {
    	CommonDaoDTO dto = new CommonDaoDTO();
    	try {
			 nBoardDao.nboardContents(dto, _params, 5);
		} catch (Exception e) {
			throw new CommonException("NBoard를 조회하지 못했습니다.", "EXPT-200", e.getMessage());
		}
    	return dto.daoDetailContent;
    	//return nBoardDao.nboardContents(_params, "news", 5);
        //return newsDao.newsContents(_params);
    }

    @Override
    public CommonDaoDTO schList(Map _params)
    {
    	CommonDaoDTO dto = new CommonDaoDTO();
    	pnullPutBidxAll(_params, "board_02");
        newsDao.schList(_params, dto);
        dto.setPaging(ipnull(_params, "pageNo", 1), ipnull(_params, "pageSize", 20));
        return dto;
    }

    @Override
    public Map schContents(Map _params)
    {
        return newsDao.schContents(_params);
    }

}
