package kr.caincheon.church.admin.code;

import java.sql.PreparedStatement;
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
 * @author benjamin
 */
@Repository("cdMBoardDAO")
public class CdMBoardDAO extends AbstractDAO {

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
			Map rmap = selectPagingList("admin.mboard.selectListPaging", _params);
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
	public Map mboardContents(Map _params) throws Exception  {
		
		Map rtmap = null;
		try {
			Object rtObj = selectOne("admin.sscard.selectToUpdatePrivatePoint", _params);
			
			// super admin except
			if(rtObj!=null) {
				rtmap = (Map)rtObj;
			}
			
		} catch (Exception e) {
			throw e;
		} finally {
			
		}
		
		D(L, Thread.currentThread().getStackTrace(), "DAO Result:"+rtmap );
		
		return rtmap;
	}

	/*
	 * mboard 등록
	 * (non-Javadoc)
	 * @see kr.caincheon.church.admin.dao.NBoardDao#nboardInsert(java.util.Map, java.util.List)
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

}
