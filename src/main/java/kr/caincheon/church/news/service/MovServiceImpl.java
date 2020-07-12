// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MovServiceImpl.java

package kr.caincheon.church.news.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.base.CommonService;
import kr.caincheon.church.dao.MovDao;

// Referenced classes of package kr.caincheon.church.service:
//            MovService

@Service("movService")
public class MovServiceImpl extends CommonService
    implements MovService
{
    private final Logger _logger = Logger.getLogger(getClass());
    
    @Resource(name="movDao")
    private MovDao movDao;
    
    /*
     * 
     * (non-Javadoc)
     * @see kr.caincheon.church.news.service.MovService#movList(java.util.Map)
     */
    @Override
    public CommonDaoDTO movList(Map _params)
    {
    	CommonDaoDTO dto = new CommonDaoDTO();
    	
        movDao.movList(_params, dto);
        
        return dto;
    }

    /*
     * 교구앨범 메인홈페에 서비스되는 OP :: 교구영상
     * (non-Javadoc)
     * @see kr.caincheon.church.news.service.MovService#movList_Main(java.util.Map)
     */
    @Override
    public CommonDaoDTO movList_Main(Map _params)
    {
    	return youtubeList(_params, "1", "5");
    }

    /*
     * 교구영상 :: 교구영상은 youtube.com에 등록된 영상으로 그 영상의 썸네일을 이용하는 방법을 적용하여 동영상 목록을 조회한다.
     */
    @Override
    public CommonDaoDTO youtubeList(Map _params, String pageNo, String pageSize) {
    	
    	CommonDaoDTO dto = new CommonDaoDTO();
    	
    	Map movParams = cloneParams(_params);
    	movParams.put("pageNo", pageNo);
    	movParams.put("pageSize", pageSize);
    	movDao.youtubeList(movParams, dto);
    	
    	// youtube 동영상에서 표지썸네일 이미지 데이터 생성 : CKEditor의 Embed에서 youtube url만으로 자동 생성되는 html tag에서 url를 추출하여 형식에 맞는 Thumbnail URL 만들기 
    	// data content -> <div data-oembed-url="https://www.youtube.com/embed/uUmwN-XI_Wk">  <div style="max-width:320px;ma................   
    	// youtube thumbnail url -> http://img.youtube.com/vi/[동영상 ID값]/[이미지형식].jpg
    	String oembedurl = "data-oembed-url";
    	int oembedurl_len = oembedurl.length(), basePos = 0;
    	for(int i=0, i2=dto.daoList.size() ; i<i2 ; i++) {
    		Map row = (Map)dto.daoList.get(i);
    		String content = pnull(row, "CONTENT");
    		int iPos = content.indexOf(oembedurl);
    		if(iPos > -1) {
    			String tmp = content.substring(iPos+oembedurl_len+2, content.indexOf("\">"));
    			tmp = tmp.replace("\"", "").replace("<", "").replace(">", "");
    			//iPos = tmp.indexOf("www.youtube.com/embed/") + 22; basePos = 22;
    			iPos = tmp.lastIndexOf("/")+1; basePos = 1;
    			if( iPos > basePos) {
    				tmp = tmp.substring(iPos);
    				tmp = "http://img.youtube.com/vi/"+tmp+"/0.jpg";
    				row.put("YOUTUBE_THUMBNAIL_URL", tmp);
    			}
    		}
    	}
    	
        return dto; 
    }

    /*
     * (non-Javadoc)
     * @see kr.caincheon.church.news.service.MovService#movContents(java.util.Map)
     */
    @Override
    public Map movContents(Map _params)
    {
        return movDao.movContents(_params);
    }

    /*
     * (non-Javadoc)
     * @see kr.caincheon.church.news.service.MovService#movDownloads(java.util.Map)
     */
    @Override
    public CommonDaoDTO movDownloads(Map _params)
    {
    	CommonDaoDTO dto = new CommonDaoDTO();
    	
        movDao.movDownloads(_params, dto);
        
        return dto;
    }


}
