package kr.caincheon.church.admin.mboard;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import kr.caincheon.church.admin.dao.CodeDao;
import kr.caincheon.church.common.base.CommonDao;
import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.base.CommonException;
import kr.caincheon.church.common.base.CommonExecuteQuery;
import kr.caincheon.church.common.base.CommonService;
import kr.caincheon.church.common.base.Const;
import kr.caincheon.church.common.utils.ImageUtils;
import kr.caincheon.church.common.utils.UtilString;


@Service("mBoardService")
public class MBoardService extends CommonService
{
	private final Logger L = Logger.getLogger(getClass());

	// 멀티게시판 조회
	@Resource(name="mBoardDAO")
	private MBoardDAO mBoardDAO;

	// 공통코드 조회
	@Resource(name="codeDao")
	private CodeDao codeDao;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void listBoard(Map params) throws CommonException, Exception {
		L.debug("..called param: " + params);
		
		// param
		String pageNo = pnull(params, "pageNo");
		String pageSize = pnull(params, "pageSize");
		
		// board list
		CommonDaoDTO dto = mBoardDAO.getMBoardList(params);

		// return
		params.put(Const.ADM_MAPKEY_LIST, dto.daoList);
		params.put(Const.ADM_MAPKEY_COUNT, dto.daoTotalCount);
		
		setPaging(params, pageNo, pageSize, dto.daoTotalCount);
		
		L.debug(".. end. rtn: " + dto );
		
	}
	
	/** 보드 관리 Content */
	public CommonDaoDTO boardViewContent(Map<String, Object> _params) throws CommonException, Exception {
		CommonDaoDTO rtDto = new CommonDaoDTO();
		
		try {
			/* 1. Board 마스터 정보
			 * 2. Board 카테고리 정보
			 * */
			rtDto	=	mBoardDAO.mboardContents(_params);
			
			_params.put(Const.ADM_MAPKEY_CONTENT, rtDto.daoDetailContent);
			_params.put(Const.ADM_MAPKEY_LIST_OTHERS, rtDto.otherList);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rtDto;
	}

	/*code instanct 조회*/
	public void getCodeInstance(Map<String, Object> _params) {
		
		CommonDaoDTO rtDto = new CommonDaoDTO();
		
		try {
			/*code 정보 조회*/
			rtDto	=	mBoardDAO.getCodeInstance(_params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		_params.put("code_list", rtDto.otherList);
	}
	//============= 게시판 management ==================
	/** 메스도에 다르게 */
	public void iudBoardMaster(Map params) throws CommonException, Exception {
		
		String mode = pnull(params, "mode");
		
		D(L, Thread.currentThread().getStackTrace(), mode+") ..called");
		
		if("i".equalsIgnoreCase(mode)) {
			mBoardDAO.insertBoard(params);
			
			this.insertCateGory(params);
		} else if("u".equalsIgnoreCase(mode)) {
			/*게시판 마스터 저장*/
			mBoardDAO.updateBoard(params);
			/*게시판 카테고리 이전 데이터 삭제*/
			mBoardDAO.deleteBoardCategory(params);
			/*게시판 카테고리 저장*/
			/*for(int i = 0; i < params.get("i_arrCgList"); i++) {
				
			}*/
			this.insertCateGory(params);
		} else if("d".equalsIgnoreCase(mode)) {
			mBoardDAO.deleteBoard(params);
			mBoardDAO.deleteBoardCategory(params);
		}
		
		D(L, Thread.currentThread().getStackTrace(), mode);
	}
	
	/*카테고리 넣기*/
	private void insertCateGory(Map params) {
		
		/*카테고리&이름*/
		String[] cgList	=	(String[]) params.get("i_arrCgList");
		String[] cgNameList	=	(String[]) params.get("i_arrCgNameList");
		
		Map tempDao = new HashMap();
		
		tempDao.put("i_sBidx",params.get("i_sBidx"));
		
		if(cgList != null) {
			for(int i=0; i < cgList.length; i++) {
				
				tempDao.put("i_sCIdx",cgList[i]);
				tempDao.put("i_sName",cgNameList[i]);
				
				mBoardDAO.insertBoardCategory(tempDao);
			}
		}
	}
	/*메인 메뉴 게시판 리스트*/
	public void getMenuBoardList(Map<String, Object> _params) {
		//메인 메뉴 게시판 리스트
		
		// board list
		CommonDaoDTO dto = mBoardDAO.getMenuBoardList(_params);

		// return
		_params.put(Const.ADM_MAPKEY_LIST, dto.daoList);
	}
	/*Normal Board List*/
	public CommonDaoDTO NormalboardList(Map<String, Object> _params) {
		// param
		String pageNo = pnull(_params, "pageNo");
		String pageSize = pnull(_params, "pageSize");
		
		// board list
		CommonDaoDTO dto = mBoardDAO.NormalboardList(_params);

		// return
		_params.put(Const.ADM_MAPKEY_LIST, dto.daoList);
		_params.put(Const.ADM_MAPKEY_COUNT, dto.daoTotalCount);
		
		setPaging(_params, pageNo, pageSize, dto.daoTotalCount);
		
		L.debug(".. end. rtn: " + dto );
		return dto;
	}

	public CommonDaoDTO postViewContent(Map<String, Object> _params) {
		CommonDaoDTO rtDto = new CommonDaoDTO();
		CommonDaoDTO attachDto = new CommonDaoDTO();
		
		try {
			/* 1. 보드 마스터
			 * */
			attachDto	=	mBoardDAO.postViewAttachContent(_params);
			rtDto		=	mBoardDAO.postViewContent(_params);
			
			_params.put("attachList", attachDto.daoList);
			_params.put(Const.ADM_MAPKEY_CONTENT, rtDto.daoDetailContent);
			_params.put(Const.ADM_MAPKEY_LIST_OTHERS, rtDto.otherList);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rtDto;
	}
	/*댓글 마스터 iud*/
	public void iudCommentMaster(Map<String, Object> params) {
		String mode = pnull(params, "mode");
		
		D(L, Thread.currentThread().getStackTrace(), mode+") ..called");
		
		if("i".equalsIgnoreCase(mode)) {
			mBoardDAO.insertComment(params);
		} else if("u".equalsIgnoreCase(mode)) {
			mBoardDAO.updateComment(params);
		} else if("d".equalsIgnoreCase(mode)) {
			mBoardDAO.deleteComment(params);
		}
		
		D(L, Thread.currentThread().getStackTrace(), mode);		// TODO Auto-generated method stub
		
	}
	/*댓글 리스트*/
	public CommonDaoDTO getCommentList(Map<String, Object> _params) {
		// param
		String pageNo = pnull(_params, "pageNo");
		String pageSize = pnull(_params, "pageSize");
		
		// board list
		CommonDaoDTO dto = mBoardDAO.commentList(_params);

		// return
		_params.put(Const.ADM_MAPKEY_LIST, dto.daoList);
		
		L.debug(".. end. rtn: " + dto );
		return dto;
	}
	/*게시글 crud*/
	public void iudPostsMaster(Map<String, Object> params, HttpServletRequest request) throws CommonException {
		
		String mode = request.getParameter("mode");
		
		D(L, Thread.currentThread().getStackTrace(), mode+") ..called");
		
		if("W".equalsIgnoreCase(mode)) {
			try {
				mBoardDAO.getIdBlIdx(params);
				
				/*게시글 마스터 저장*/
				mBoardDAO.insertPosts(params);

				/*첨부파일 저장*/
				List list = fileUploadProcessNew(params, request, "upload/"+params.get("i_sBidx"));
				
				/*파일 업로드 저장*/
				for(int a = 0; a < list.size(); a++) {
					mBoardDAO.insertUpload((Map)list.get(a));
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				throw new CommonException("MBoard에 등록하지 못했습니다.["+e.getMessage()+"]", "EXPT-300", e.getMessage());
			}
			
		} else if("u".equalsIgnoreCase(mode)) {
			mBoardDAO.updatePosts(params);
			
			/*첨부파일 저장*/
			List list = fileUploadProcessNew(params, request, "upload/"+params.get("i_sBidx"));
			
			// delete old files
			/*mBoardDAO.deleteAttachedFilesNboardUpload(this, _params);*/
				
			/*파일 업로드 저장*/
			for(int a = 0; a < list.size(); a++) {
				mBoardDAO.insertUpload((Map)list.get(a));
			}
			
		} else if("d".equalsIgnoreCase(mode)) {
			mBoardDAO.deletePosts(params);
		}
		
		D(L, Thread.currentThread().getStackTrace(), mode);		// TODO Auto-generated method stub
		
	}
	/*본당 리스트*/
	public void getOrgIdxList(Map<String, Object> params) {
		
		String mode = pnull(params, "mode");
		
		D(L, Thread.currentThread().getStackTrace(), mode+") ..called");
		
		// board list
		CommonDaoDTO dto = new CommonDaoDTO();
	
		// return
		if("O".equalsIgnoreCase(mode)) {
			dto = mBoardDAO.getOrgHirarchyList(params);
		} else if("D".equalsIgnoreCase(mode)) {
			dto = mBoardDAO.getDeptartMentList(params);
		} else if("E".equalsIgnoreCase(mode)) {
			dto = mBoardDAO.getOrganizationList(params);
		}
		
		params.put(Const.ADM_MAPKEY_LIST, dto.daoList);
		
	}

	public CommonDaoDTO albList(Map<String, Object> _params) {
		String pageNo = pnull(_params, "pageNo");
		String pageSize = pnull(_params, "pageSize");
		
		// board list
		CommonDaoDTO dto = mBoardDAO.albList(_params);

		// return
		_params.put(Const.ADM_MAPKEY_LIST, dto.daoList);
		
		setPaging(_params, pageNo, pageSize, dto.daoTotalCount);
		
		L.debug(".. end. rtn: " + dto );
		
		return dto;
	}

	public CommonDaoDTO getalbView(Map<String, Object> _params) {
		CommonDaoDTO rtDto = new CommonDaoDTO();
		CommonDaoDTO attachDto = new CommonDaoDTO();
		try {
			rtDto		=	mBoardDAO.getAlbView(_params);
			attachDto	=	mBoardDAO.postViewAttachContent(_params);
			
			_params.put("attachList", attachDto.daoList);
			_params.put(Const.ADM_MAPKEY_CONTENT, rtDto.daoDetailContent);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return rtDto;
	}
	/*
	 * 교구영상 :: 교구영상은 youtube.com에 등록된 영상으로 그 영상의 썸네일을 이용하는 방법을 적용하여 동영상 목록을 조회한다.
	 */
	
	public CommonDaoDTO youtubeList(Map _params, String pageNo, String pageSize) {
		
		CommonDaoDTO dto = new CommonDaoDTO();
		
		Map movParams = cloneParams(_params);
		
		movParams.put("pageNo", pageNo);
		movParams.put("pageSize", pageSize);
		
		mBoardDAO.youtubeList(movParams, dto);
		
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

	public void getMagazineList(Map<String, Object> _params) {
		String pageNo = pnull(_params, "pageNo");
		String pageSize = pnull(_params, "pageSize");
		
		// board list
		CommonDaoDTO dto = mBoardDAO.getMagazineList(_params);

		// return
		_params.put(Const.ADM_MAPKEY_LIST, dto.daoList);
		
		setPaging(_params, pageNo, pageSize, dto.daoTotalCount);
		
		L.debug(".. end. rtn: " + dto );
	}
	
	/**
	 * 등록된 첨부 자료를 삭제한다.
	 *//*
	public static int deleteAttachedFilesMboardUpload(CommonDao parent, Map _params)  throws Exception {
		int rn5 = 0;
		//String uploadedFileURI  = pnull(_params, "CONTEXT_URI_PATH"); // "/upload/news/"
		String uploadedFileRootPath = parent.pnull(_params, "CONTEXT_ROOT_PATH", parent.getSession(_params, "CONTEXT_ROOT"));// "d:/newcaincheon/webapps/upload/news/"

		// files delete
		try {
			String bl_idx = parent.pnull(_params, "bl_idx");
			for(int i=1; i<6; i++) {
				String strfilename = parent.pnull(_params, "delFile"+i);
				if (strfilename.length() > 0) {
					String bu_idx = deleteUploadedFile(parent, bl_idx, strfilename, uploadedFileRootPath);
					rn5 += deleteUploadedFileDbRecord(parent, bu_idx);
					//rn5 = deleteUploadedFileDbRecord(bl_idx, strfilename);
					parent.D(_logger, Thread.currentThread().getStackTrace(), "delete file ["+ i +"]. is del a file : "+(bu_idx==null ? "not exists":bu_idx) );
					parent.D(_logger, Thread.currentThread().getStackTrace(), "delete db ["+ i +"]. result count rn : "+rn5 );
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return rn5;
	}
	
	*//**
	 * if return value is a false, then file not exist or path information is wrong.
	 * @param _params
	 * @return String - bu_idx
	 * @throws SQLException
	 *//*
	public static String deleteUploadedFile(CommonDao parent, String bl_idx, String strfilename, String CONTEXT_ROOT_PATH) throws SQLException {
		
		 * 아래와 같이 2개 함수를 동시에 호출하게 된다.
		 * 
		 * deleteUploadedFile(bl_idx, CONTEXT_ROOT_PATH);
		 * deleteUploadedFileDbRecord(bl_idx, strfilename, delFileName);
		 
		String bu_idx = null;
		parent.D(_logger, Thread.currentThread().getStackTrace(), "EXE Query(1) old file delete option is on.");
		
        // file del
        Map<String, Object> delMap = parent.executeQueryMap(
        			"SELECT BU_IDX, FILEPATH, STRFILENAME FROM NBOARD_UPLOAD"
        			+ "WHERE BL_IDX=" + bl_idx + ""
        			+ "AND STRFILENAME='" + strfilename +"'" ) ;
        
        if(delMap!=null && delMap.size() > 1 ) {
        	bu_idx = parent.pnull(delMap, "BU_IDX");
        	String dFilepath = parent.pnull(delMap, "STRFILENAME");
        	boolean isDel = ImageUtils.deleteFileWithThumbnail(CONTEXT_ROOT_PATH, dFilepath);
        	if(isDel) {
        		parent.D(_logger, Thread.currentThread().getStackTrace(), "EXE Query(1) File is deleted.[" + dFilepath +"]");
        	} else {
        		parent.D(_logger, Thread.currentThread().getStackTrace(), "EXE Query(1) File is not exits.[" + dFilepath +"]");
        	}
        }
        return bu_idx;
	}*/
}
