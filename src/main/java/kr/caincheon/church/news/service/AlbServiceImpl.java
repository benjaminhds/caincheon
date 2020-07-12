// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AlbServiceImpl.java

package kr.caincheon.church.news.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import kr.caincheon.church.admin.dao.OrgHierarchyDao;
import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.base.CommonService;
import kr.caincheon.church.common.utils.FileUtils;
import kr.caincheon.church.dao.AlbDao;

// Referenced classes of package kr.caincheon.church.service:
//            AlbService

@Service("albService")
public class AlbServiceImpl extends CommonService
    implements AlbService
{

    private final Logger _logger = Logger.getLogger(getClass());
    
    @Resource(name="albDao")
    private AlbDao albDao;
    
    @Resource(name="orgHierarchyDao")
    private OrgHierarchyDao orgHierarchyDao;
    
    
    
    private final String OLDBISHOP_ALBUM_PATH = "upload/bishop_oldalbum/"; // 역대교구장앨범 폴더 <-- "parish_album" 을 모두  "bishop_oldalbum"으로 변경
    private final String GYOGU_ALBUM_PATH     = "upload/gyogu_album/";     // 교구앰범 폴더
    
    /*
     * 앨범리스트 
     */
    @Override
    public List albList(Map _params) throws Exception
    {
        return albDao.albList(_params);
    }

    @Override
    public int albListCount(Map _params) throws Exception
    {
        return albDao.albListCount(_params);
    }

    @Override
    public Map albContents(Map _params) throws Exception
    {
        return albDao.albContents(_params);
    }

    @Override
    public List albDList(Map _params) throws Exception
    {
        return albDao.albDList(_params);
    }

    /*
     * 역대 교구장 앨범 
     */
    
    /*
     * 교구앨범 메인홈페에 서비스되는 OP :: 교구앨범
     * (non-Javadoc)
     * @see kr.caincheon.church.news.service.AlbService#albList_Main(java.util.Map)
     */
    @Override
    public List albList_Main(Map _params) throws Exception
    {
        return albDao.albList_Main(_params);
    }

    @Override
    public Map scheduleByDiary(Map _params) throws Exception
    {
        return albDao.scheduleByDiary(_params);
    }

    @Override
    public Map scheduleContents(Map _params) throws Exception
    {
        return albDao.scheduleContents(_params);
    }

    // 역대교구장 앨범
    @Override
    public List oldAlbList(Map _params) throws Exception
    {
        return albDao.oldAlbList(_params);
    }

    // 역대교구장 앨범 카운트
    @Override
    public int oldAlbListCount(Map _params) throws Exception
    {
        return albDao.oldAlbListCount(_params);
    }

    // front에서 앨범 보기
    @Override
    public Map oldAlbContents(Map _params) throws Exception
    {
        return albDao.oldAlbContents(_params);
    }

    @Override
    public boolean oldAlbInsert(Map _params, HttpServletRequest request) throws Exception
    {
        List list = null;
        try
        {
        	_params.put("CONTEXT_URI_PATH", "/"+OLDBISHOP_ALBUM_PATH);
        	_params.put("CONTEXT_ROOT_PATH", request.getSession().getServletContext().getRealPath("/") + OLDBISHOP_ALBUM_PATH);
            list = FileUtils.getInstance().parseInsertFileInfo(_params, request);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return albDao.oldAlbInsert(_params, list);
    }

    @Override
    public boolean oldAlbModify(Map _params, HttpServletRequest request) throws Exception
    {
        List list = null;
        try
        {
        	_params.put("CONTEXT_URI_PATH", "/"+OLDBISHOP_ALBUM_PATH);
        	_params.put("CONTEXT_ROOT_PATH", request.getSession().getServletContext().getRealPath("/") + OLDBISHOP_ALBUM_PATH);
            list = FileUtils.getInstance().parseInsertFileInfo(_params, request);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return albDao.oldAlbModify(_params, list);
    }

    @Override
    public boolean oldAlbDelete(Map _params) throws Exception
    {
        return albDao.oldAlbDelete(_params);
    }

    /** 조직리스트 */
    @Override
    public List _1x00xList(Map _params) throws Exception
    {
        //return albDao._1x00xList(_params);
    	List list   = null;
    	int  total  = 0;
    	try {
    		Map m = new HashMap();
    		m.put("lv1", "01");/* 교구 */
			list   = orgHierarchyDao.selectOrgHierarchy(m);
			total  = orgHierarchyDao.totalCount();
		} catch (Exception e) {
			_E(_logger, Thread.currentThread().getStackTrace(), "ERROR:"+e.getMessage(), e);
		}
    	
    	return list;
    }

    /*
     * 교구앨범 목록 조회
     * (non-Javadoc)
     * @see kr.caincheon.church.news.service.AlbService#albumList(java.util.Map)
     */
    @Override
    public CommonDaoDTO albumList(Map _params) throws Exception
    {
    	CommonDaoDTO dto = new CommonDaoDTO();
    	albDao.albumList(_params, dto);
    	dto.setPaging(ipnull(_params, "pageNo", 1), ipnull(_params, "pageSize", 20));
    	dto.orgList = _1x00xList(_params);
        return dto; 
    }

    @Override
    public Map albumContents(Map _params) throws Exception
    {
    	String query_type = pnull(_params, "query_type");
    	if("insert".equalsIgnoreCase(query_type)) {
    		return new HashMap();
    	} else if("modify".equalsIgnoreCase(query_type)) {
    		return albDao.albumContents(_params);
    	}
    	throw new Exception("잘못된 호출이고, query_type 이 누락되었습니다.");
    }

    @Override
    public boolean albumInsert(Map _params, HttpServletRequest request) throws Exception
    {
        List list = null;
        try
        {
        	_params.put("CONTEXT_URI_PATH", "/"+GYOGU_ALBUM_PATH);
        	_params.put("CONTEXT_ROOT_PATH", request.getSession().getServletContext().getRealPath("/") + GYOGU_ALBUM_PATH);
            list = FileUtils.getInstance().parseInsertFileInfo(_params, request);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return albDao.albumInsert(_params, list);
    }

    @Override
    public boolean albumModify(Map _params, HttpServletRequest request) throws Exception
    {
        List list = null;
        try
        {
        	_params.put("CONTEXT_URI_PATH", "/"+GYOGU_ALBUM_PATH);
        	_params.put("CONTEXT_ROOT_PATH", request.getSession().getServletContext().getRealPath("/") + GYOGU_ALBUM_PATH);
            list = FileUtils.getInstance().parseInsertFileInfo(_params, request);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return albDao.albumModify(_params, list);
    }

    @Override
    public boolean albumDelete(Map _params, HttpServletRequest request) throws Exception
    {
    	_params.put("CONTEXT_URI_PATH", "/"+GYOGU_ALBUM_PATH);
    	_params.put("CONTEXT_ROOT_PATH", request.getSession().getServletContext().getRealPath("/") + GYOGU_ALBUM_PATH);
    	
        return albDao.albumDelete(_params);
    }


}
