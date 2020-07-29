package kr.caincheon.church.admin.mboard;

import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import kr.caincheon.church.common.base.AbstractDAO;
import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.base.Const;
import kr.caincheon.church.common.base.Paging;


/**
 * 
 * @author kyjo
 */
@Repository("mBoardDAO")
public class MBoardDAO extends AbstractDAO {

	private final Logger L = Logger.getLogger(getClass());
	
	/**
	 * 멀티보드를 조회하는 메서드
	 * @param dto
	 * @param _params
	 * @param left_menu_data_pg
	 * @param attachedFileMaxCount
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public CommonDaoDTO selectMboard(Map _params) throws Exception {
		
		String query = "", queryList="";
		
		L.debug("DAO Called. params: "+_params);
		//
		CommonDaoDTO dto = new CommonDaoDTO();
		
		// paging
		int pageNo    = ipnull(_params, "pageNo", 1);
		int pageSize  = ipnull(_params, "pageSize", 10);
		int startRnum = (pageNo - 1) * pageSize + 1;
		int endRnum   =  pageNo * pageSize;
		
		_params.put("startRnum", startRnum);
		_params.put("endRnum",   endRnum);
		
		// Query Combination
		try {
			Map rmap = selectPagingList("admin.mboard.getMBoardList", _params);
			dto.daoList = (List)rmap.get("List");
			dto.paging  = (Paging)rmap.get("Paging");
		} catch (Exception e) {
			L.error("SQL ERROR:"+e.getMessage()+"]");
			throw e;
		} finally {
		}
		
		L.debug("DAO Result.[DTO:"+dto+"]" );
		
		return dto;
	}
	
	
	@SuppressWarnings("rawtypes")
	public CommonDaoDTO mboardContents(Map _params) throws Exception  {
		
		CommonDaoDTO dto = new CommonDaoDTO();
		
		try {
			/*마스터 정보 가져오기*/
			Object MBoardView = selectOne("admin.mboard.getMboardView", _params);
			/*카테고리 리스트 가져오기*/
			dto.otherList	= selectList("admin.mboard.getCategory", _params);
			
			// super admin except
			if(MBoardView!=null) {
				dto.daoDetailContent = (Map)MBoardView;
			}
			
		} catch (Exception e) {
			throw e;
		} finally {
			
		}
		
		D(L, Thread.currentThread().getStackTrace(), "DAO Result:"+dto.daoDetailContent );
		
		return dto;
	}

	/*
	 * mboard 등록
	 * (non-Javadoc)
	 */
	@SuppressWarnings("rawtypes")
	public boolean mboardInsert(Map _params, List uploadedFiles)  throws Exception {
		L.debug("DAO mboardInsert Called.[params:"+_params+"]" );
		
		int r=-1;
		try {
			// 게시글 등록 & 첨부파일 등록 
			// 첨부파일 등록
			String registerId = pnull(_params, "NAME");
//			insertNboardUpload( _params,  uploadedFiles, registerId);
			selectOne("admin.sscard.selectToUpdatePrivatePoint", _params);
			
		} catch (Exception e) {
			throw e;
		} finally {
		}
		
		D(L, Thread.currentThread().getStackTrace(), "DAO mboardInsert Return.[result:"+r+"]" );
		return r > 0 ? true : false;
	}

	/*
	 * mboard 게시물 수정
	 * (non-Javadoc)
	 * @see kr.caincheon.church.admin.dao.NBoardDao#nboardModify(java.util.Map, java.util.List)
	 */
	@SuppressWarnings("rawtypes")
	public boolean mboardModify(Map _params, List uploadedFiles)  throws Exception {
		int rn = 0, rn1 = 0, rn2 = 0, rn3 = 0, rn4 = 0;
		
		PreparedStatement preparedStatement = null;
		boolean bReturn = true;
		
		D(L, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+", uploadedFiles:"+uploadedFiles+"]" );
		
		// 작성자
		String updateId = pnull(_params, "user_id");
		String registerNm = "";
		
		// from login session
		// 작성자 , 일반사용자라면, MEM_ID로 저장되고, super admin 이면 ADM_MEM_ID로 저장된다.
		Map _AdmMap = (Map)_params.get("__SESSION_MAP__");
		String writerId = pnull(_AdmMap, Const.SESSION_KEY_MEM_ID, pnull(_AdmMap, Const.SESSION_KEY_ADM_MEM_ID));
		String writerNm = pnull(_AdmMap, Const.SESSION_KEY_MEM_NM, pnull(_AdmMap, Const.SESSION_KEY_ADM_MEM_NM));
		if(writerId.length()>0) {
			updateId = writerId;
		}
		if(writerNm.length()>0) {
			registerNm = writerNm;
		}

		// delete old files
		//deleteAttachedFilesNboardUpload(_params);
		
		// new files upload
		if(uploadedFiles.size() > 0) {
			//insertAttachedFilesNboardUpload( _params,  uploadedFiles, registerNm);
		}
		
		// board update
		try {
			String depart_idx = pnull(_params, "depart_idx"); 
			String b_idx      = pnull(_params, "b_idx"); 
			String c_idx      = pnull(_params, "c_idx", "SELECT TOP 1 ISNULL(C_IDX,'') FROM NBOARD_CATEGORY WHERE B_IDX="+b_idx+" AND IS_USE='Y'"); 
			String is_view    = pnull(_params, "is_view"); 
			String church_idx = pnull(_params, "church_idx"); 
			int i = 1;
			//LinkedHashMap<String, String> lhm = new LinkedHashMap<String, String>();
			String query = "UPDATE NBOARD SET  title= ?, content = ?,  update_id = ?,  is_notice = ?, event_date = ?"
					+ ", down_level = ?, b_idx = " + b_idx
					+ ", upddate = getdate()"
					+ (is_view.length()    == 0 ? "" : ", is_view='"+is_view+"' ")
					+ (depart_idx.length() == 0 ? "" : ", depart_idx = "+depart_idx)
					+ (church_idx.length() == 0 ? "" : ", church_idx = "+church_idx)
					+ ", c_idx =(" + c_idx + ") "
					+ " WHERE bl_idx = ? ";
			
			//preparedStatement = getPreparedStatement(query);
			preparedStatement.setString(i++,  pnull(_params, "title"));
			preparedStatement.setString(i++,  pnull(_params, "contents"));
			preparedStatement.setString(i++,  updateId);
			//preparedStatement.setString(i++,  registerNm);
			preparedStatement.setString(i++,  pnull(_params, "is_notice", "N"));
			preparedStatement.setString(i++,  pnull(_params, "event_date"));
			preparedStatement.setString(i++,  pnull(_params, "down_level", "A"));
			preparedStatement.setString(i++,  pnull(_params, "bl_idx"));
			rn3 = preparedStatement.executeUpdate();

			D(L, Thread.currentThread().getStackTrace(), "query executed result count rn3 : "+rn3 );
			
		} catch (Exception e) {
			e.printStackTrace();
			bReturn = false;
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		rn = rn1 + rn2 + rn3 + rn4;
		return rn >= 1 && bReturn;
	}

	/*
	 * mboard 게시물 삭제
	 * (non-Javadoc)
	 * @see kr.caincheon.church.admin.dao.NBoardDao#nboardDelete(java.util.Map)
	 */
	@SuppressWarnings("rawtypes")
	public boolean mboardDelete(Map _params)  throws Exception {
		int rn = 0;
		String bl_idx = pnull(_params, "bl_idx", "0");

		D(L, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );

		// 게시물 삭제
		try {
//			rn = executeUpdate("DELETE FROM "+DB_OWNER+".NBOARD WHERE BL_IDX="+bl_idx);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}

		
		// 첨부 파일 삭제
		//List delFileList = executeQueryList("SELECT * FROM "+DB_OWNER+".NBOARD_UPLOAD WHERE BL_IDX="+bl_idx) ;
//		deleteAttachedFilesNboardUpload(_params);
		
		// 첨부 데이터 삭제
		try {
//			rn += executeUpdate("DELETE FROM "+DB_OWNER+".NBOARD_UPLOAD WHERE BL_IDX="+bl_idx);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}

		return rn >= 1;
	}

	/**
	 * 멀티보드를 조회하는 메서드
	 * @param dto
	 * @param params
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public CommonDaoDTO getMBoardList(Map params) throws Exception {
		
		L.debug("DAO Called. params: "+params);

		//DTO 만들기
		CommonDaoDTO dto = new CommonDaoDTO();
		
		// paging
		int pageNo    = ipnull(params, "pageNo", 1);
		int pageSize  = ipnull(params, "pageSize", 10);
		int startRnum = (pageNo - 1) * pageSize + 1;
		int endRnum   =  pageNo * pageSize;
		
		params.put("startRnum",  startRnum);
		params.put("endRnum",    endRnum);
		
		// Query Combination
		try {
			Map rmap = selectPagingList("admin.mboard.getMBoardList", params);
			dto.daoList = (List)rmap.get("List");
			dto.paging  = (Paging)rmap.get("Paging");

		} catch (Exception e) {
			L.error("SQL ERROR:"+e.getMessage()+"]");
			throw e;
		} finally {
		}
		
		L.debug("DAO Result.[DTO:"+dto+"]" );
		
		return dto;
	}
	
	/*코드 인스턴스 가져오기*/
	public CommonDaoDTO getCodeInstance(Map<String, Object> _params) {
		
		//DTO 만들기
		CommonDaoDTO dto = new CommonDaoDTO();
		
		try {		
			/*카테고리 리스트 가져오기*/
			dto.otherList	= selectList("admin.mboard.getCodeInstance", _params);
			
		} catch (Exception e) {
			L.error("SQL ERROR:"+e.getMessage()+"]");
			throw e; 
		}
		
		return dto;
	}
	
	/**
	 * 게시판 등록
	 * */
	public void insertBoard(Map params) {
		// TODO Auto-generated method stub
		insert("admin.mboard.insertMboardMaster",params);
	}
	/**
	 * 게시판 수정
	 * */
	public void updateBoard(Map params) {
		// TODO Auto-generated method stub
		update("admin.mboard.updateMboardMaster",params);
	}
	/**
	 * 게시판 삭제
	 * */
	public void deleteBoard(Map params) {
		delete("admin.mboard.deleteMbaord", params);
	}
	/**
	 * 게시판 별 카테고리 등록
	 * */
	public void insertBoardCategory(Map params) {
		/*다수 카테고리 insert*/
		insert("admin.mboard.insertMboardCategroy", params);
	}
	/**
	 * 게시판 별 카테고리 삭제
	 * */
	public void deleteBoardCategory(Map params) {
		/*게시판 다수 카테고리 delete*/
		delete("admin.mboard.deleteMboardCategroy", params);
	}
	/**
	 * 메뉴 게시판 리스트
	 * */
	public CommonDaoDTO getMenuBoardList(Map<String, Object> _params) {
		
		//DTO 만들기
		CommonDaoDTO dto = new CommonDaoDTO();
				
		try {
			List rmap = selectList("admin.mboard.getMenuBoardList", _params);
			dto.daoList = rmap;
			
		} catch (Exception e) {
			L.error("SQL ERROR:"+e.getMessage()+"]");
			throw e;
		}
		
		return dto;
	}

	/*일반 게시판 리스트*/
	public CommonDaoDTO NormalboardList(Map<String, Object> _params) {
		L.debug("DAO Called. params: "+_params);

		//DTO 만들기
		CommonDaoDTO dto = new CommonDaoDTO();
		
		// paging
		int pageNo    = ipnull(_params, "pageNo", 1);
		int pageSize  = ipnull(_params, "pageSize", 10);
		int startRnum = (pageNo - 1) * pageSize + 1;
		int endRnum   =  pageNo * pageSize;
		
		_params.put("startRnum",  startRnum);
		_params.put("endRnum",    endRnum);
		
		// Query Combination
		try {
			Map rmap = new HashMap();
			rmap	=	selectPagingList("admin.mboard.getNormalboardList", _params);
			
			dto.daoList = (List)rmap.get("List");
			dto.paging  = (Paging)rmap.get("Paging");
		} catch (Exception e) {
			L.error("SQL ERROR:"+e.getMessage()+"]");
			throw e;
		} finally {
		}
		
		L.debug("DAO Result.[DTO:"+dto+"]" );
		
		return dto;
	}


	public CommonDaoDTO postViewContent(Map<String, Object> _params) {
		CommonDaoDTO dto = new CommonDaoDTO();
		
		try {
			/*마스터 정보 가져오기*/
			Object postViewContent = selectOne("admin.mboard.postViewContent", _params);
			/*카테고리 리스트 가져오기*/
			dto.otherList	= selectList("admin.mboard.getCategory", _params);
			
			// super admin except
			if(postViewContent!=null) {
				dto.daoDetailContent = (Map)postViewContent;
			}
			
		} catch (Exception e) {
			throw e;
		} finally {
			
		}
		
		D(L, Thread.currentThread().getStackTrace(), "DAO Result:"+dto.daoDetailContent );
		
		return dto;
	}


	public void insertComment(Map<String, Object> params) {
		insert("admin.mboard.insertComment", params);
	}
	public void updateComment(Map<String, Object> params) {
		update("admin.mboard.updateComment", params);
	}
	public void deleteComment(Map<String, Object> params) {
		delete("admin.mboard.deleteComment", params);		
	}
	/*댓글 리스트*/
	public CommonDaoDTO commentList(Map<String, Object> _params) {
		L.debug("DAO Called. params: "+_params);

		//DTO 만들기
		CommonDaoDTO dto = new CommonDaoDTO();
		
		// Query Combination
		try {
			/*댓글 리스트 가져오기*/
			dto.daoList	= selectList("admin.mboard.getCommentList", _params);
			
		} catch (Exception e) {
			L.error("SQL ERROR:"+e.getMessage()+"]");
			throw e;
		} finally {
		}
		
		L.debug("DAO Result.[DTO:"+dto+"]" );
		
		return dto;
	}
	/*게시글 insert*/
	public void insertPosts(Map<String, Object> params) {
		insert("admin.mboard.insertPostsMaster",params);
	}


	public void updatePosts(Map<String, Object> params) {
		update("admin.mboard.updatePostsMaster",params);
	}


	public void deletePosts(Map<String, Object> params) {
		update("admin.mboard.deletePosts",params);
	}


	public CommonDaoDTO getOrgHirarchyList(Map<String, Object> params) {
		CommonDaoDTO dto = new CommonDaoDTO();
		
		try {		
			/*조직 리스트 가져오기*/
			dto.daoList	= selectList("admin.mboard.getOrgHirarchyList", params);
			
		} catch (Exception e) {
			L.error("SQL ERROR:"+e.getMessage()+"]");
			throw e; 
		}
		
		return dto;
	}


	public CommonDaoDTO getDeptartMentList(Map<String, Object> params) {
		CommonDaoDTO dto = new CommonDaoDTO();
		
		try {		
			/*부서 리스트 가져오기*/
			dto.daoList	= selectList("admin.mboard.getDeptartMentList", params);
			
		} catch (Exception e) {
			L.error("SQL ERROR:"+e.getMessage()+"]");
			throw e; 
		}
		
		return dto;
	}


	public CommonDaoDTO getOrganizationList(Map<String, Object> params) {
		//DTO 만들기
		CommonDaoDTO dto = new CommonDaoDTO();
		
		try {		
			/*기타조직 리스트 가져오기*/
			dto.daoList	= selectList("admin.mboard.getOrganizationList", params);
			
		} catch (Exception e) {
			L.error("SQL ERROR:"+e.getMessage()+"]");
			throw e; 
		}
		
		return dto;
	}

	public void insertUpload(Map params) {
		insert("admin.mboard.insertUpload",params);
	}

	public void deleteUpload(Map params) {
		delete("admin.mboard.deleteUpload",params);
	}


	public void getIdBlIdx(Map<String, Object> params) {
		Map rmap = new HashMap();	
		
		rmap	= (Map)selectOne("admin.mboard.getIdBlIdx",params);
		
		params.put("i_sBlidx", rmap.get("CNT"));
	}


	public CommonDaoDTO postViewAttachContent(Map<String, Object> _params) {
		//DTO 만들기
		CommonDaoDTO dto = new CommonDaoDTO();
		
		try {		
			/*기타조직 리스트 가져오기*/
			dto.daoList	= selectList("admin.mboard.getAttachList", _params);
			
		} catch (Exception e) {
			L.error("SQL ERROR:"+e.getMessage()+"]");
			throw e; 
		}
		
		return dto;
	}
	
	public CommonDaoDTO albList(Map<String, Object> _params) {
		L.debug("DAO Called. params: "+_params);

		//DTO 만들기
		CommonDaoDTO dto = new CommonDaoDTO();
		
		// paging
		int pageNo    = ipnull(_params, "pageNo", 1);
		int pageSize  = ipnull(_params, "pageSize", 10);
		int startRnum = (pageNo - 1) * pageSize + 1;
		int endRnum   =  pageNo * pageSize;
		
		_params.put("startRnum",  startRnum);
		_params.put("endRnum",    endRnum);
		
		// Query Combination
		try {
			Map rmap = new HashMap();
			rmap	=	selectPagingList("admin.mboard.getalbList", _params);
			
			dto.daoList = (List)rmap.get("List");
			dto.paging  = (Paging)rmap.get("Paging");
		} catch (Exception e) {
			L.error("SQL ERROR:"+e.getMessage()+"]");
			throw e;
		} finally {
		}

		L.debug("DAO Result.[DTO:"+dto+"]" );
		
		return dto;

	}

	public CommonDaoDTO getAlbView(Map<String, Object> _params) {
		CommonDaoDTO dto = new CommonDaoDTO();
		
		try {
			/*마스터 정보 가져오기*/
			Object albView = selectOne("admin.mboard.getAlbView", _params);
			
			// super admin except
			if(albView!=null) {
				dto.daoDetailContent = (Map)albView;
			}
			
		} catch (Exception e) {
			throw e;
		} finally {
			
		}
		
		D(L, Thread.currentThread().getStackTrace(), "DAO Result:"+dto.daoDetailContent );
		
		return dto;
	}
	
	public void youtubeList(Map params, CommonDaoDTO dto) {
		L.debug("DAO Called. params: "+params);
		// paging
		int pageNo    = ipnull(params, "pageNo", 1);
		int pageSize  = ipnull(params, "pageSize", 10);
		int startRnum = (pageNo - 1) * pageSize + 1;
		int endRnum   =  pageNo * pageSize;
		
		params.put("startRnum",	startRnum);
		params.put("endRnum",	endRnum);
		
		// Query Combination
		try {
			Map rmap = selectPagingList("admin.mboard.getalbList", params);
			dto.daoList = (List)rmap.get("List");
			dto.paging  = (Paging)rmap.get("Paging");
		} catch (Exception e) {
			L.error("SQL ERROR:"+e.getMessage()+"]");
			throw e;
		} finally {
		}
		
		L.debug("DAO Result.[DTO:"+dto+"]" );
		
	}


	public CommonDaoDTO getMagazineList(Map<String, Object> _params) {
		
		L.debug("DAO Called. params: "+_params);
		//
		CommonDaoDTO dto = new CommonDaoDTO();
		
		// paging
		int pageNo    = ipnull(_params, "pageNo", 1);
		int pageSize  = ipnull(_params, "pageSize", 10);
		int startRnum = (pageNo - 1) * pageSize + 1;
		int endRnum   =  pageNo * pageSize;
		
		_params.put("startRnum", startRnum);
		_params.put("endRnum",   endRnum);
		_params.put("orderby", "PUBDATE DESC");
		
		// Query Combination
		try {
			Map rmap = selectPagingList("admin.mboard.getMagazineList", _params);
			dto.daoList = (List)rmap.get("List");
			dto.paging  = (Paging)rmap.get("Paging");
		} catch (Exception e) {
			L.error("SQL ERROR:"+e.getMessage()+"]");
			throw e;
		} finally {
		}
		
		L.debug("DAO Result.[DTO:"+dto+"]" );
		
		return dto;
		
	}

	@SuppressWarnings("unchecked")
	public CommonDaoDTO getUploadVo(Map params) {
		
		CommonDaoDTO dto = new CommonDaoDTO();
		
		/*마스터 정보 가져오기*/
		Object albView = selectOne("admin.mboard.getUploadVo",params);
		
		// super admin except
		if(albView!=null) {
			dto.daoDetailContent = (Map)albView;
		}
		
		return dto;
	}
	/**
	 * 멀티보드를 조회하는 메서드
	 * @param dto
	 * @param params
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getCategoryLIst(Map params) throws Exception {
		
		L.debug("DAO Called. params: "+params);

		//DTO 만들기
		CommonDaoDTO dto = new CommonDaoDTO();
		
		// Query Combination
		try {
			dto.otherList =	selectList("admin.mboard.getCategory", params);
		} catch (Exception e) {
			L.error("SQL ERROR:"+e.getMessage()+"]");
			throw e;
		} finally {
		}
		
		L.debug("DAO Result.[DTO:"+dto+"]" );
		
		return dto.otherList;
	}
	
}
