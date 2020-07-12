package kr.caincheon.church.admin.dao;

import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import kr.caincheon.church.common.base.CommonDao;
import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.base.CommonExecuteQuery;
import kr.caincheon.church.common.base.Const;


@Repository("nBoardDao")
public class NBoardDaoImpl extends CommonDao implements NBoardDao {

	private final Logger _logger = Logger.getLogger(getClass());
	
	//
	private String getSQLSelectNBoard(Map _params, boolean isViewPage, int attachedFileMaxCount) throws Exception {
		throw new Exception("[주의] NBoard 호출불가 메서드가 호출됨. MBoard 관련 메서드로 대치되어야 함.");
	}
	private int executeInsertNBoard(Map _params, List uploadedFiles, String clz) throws Exception {
		throw new Exception("[주의] NBoard 호출불가 메서드가 호출됨. MBoard 관련 메서드로 대치되어야 함.");
	}
	
	/**
	 * 
	 * @deprecated
	 */
	@Override
	public void nboardList(CommonDaoDTO dto, Map _params, String left_menu_data_pg, int attachedFileMaxCount) throws Exception {
		
		String query = "", queryList="";
		
		D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+", menu="+left_menu_data_pg+"]" );
		
		// paging
		int pageNo    = ipnull(_params, "pageNo", 1);
		int pageSize  = ipnull(_params, "pageSize", 10);
		int startRnum = (pageNo - 1) * pageSize + 1;
		int endRnum   = pageNo * pageSize;
		
		// Query Combination
		try {
			boolean isViewPage = false;
			query   = getSQLSelectNBoard(_params, isViewPage, attachedFileMaxCount);
			
			// total count
			dto.daoTotalCount = super.executeCount("SELECT COUNT(1) FROM ( " + query + " ) A ", false);
			
			
			// list 
			queryList = "SELECT B.NAME AS CATEGORY_NAME, ISNULL(C.NAME,'') AS CHURCH_NAME, ISNULL(D.NAME,'') AS DEPART_NAME, ISNULL(O.NAME, '') AS ORG_NAME"
					+ ", A.* FROM ( "
					+ query
					+ " ) A "
					+ " LEFT OUTER JOIN NBOARD_CATEGORY B ON B.C_IDX      = A.C_IDX      AND B.B_IDX=A.B_IDX AND B.IS_USE='Y' "
					+ " LEFT OUTER JOIN ORG_HIERARCHY   O ON O.ORG_IDX    = A.DEPART_IDX "
					+ " LEFT OUTER JOIN CHURCH          C ON C.CHURCH_IDX = A.CHURCH_IDX " 
					+ " LEFT OUTER JOIN DEPARTMENT      D ON D.DEPART_IDX = A.DEPART_IDX "
					
					+ " WHERE RNUM BETWEEN " + startRnum + " AND " + endRnum
					+ " ORDER BY A.NOTICE_TYPE, A.REGDATE DESC, A.BL_IDX DESC ";
			
			dto.daoList = super.executeQueryList(queryList);

		} catch (Exception e) {
			_E(_logger, Thread.currentThread().getStackTrace(), "SQL ERROR:"+e.getMessage()+"]", e );
			throw e;
		} finally {
			free();
		}
		
		D(_logger, Thread.currentThread().getStackTrace(), "DAO Result.[DTO:"+dto+"]" );
	}


	/**
	 * 
	 * @deprecated
	 */
	@Override
	public void nboardContents(CommonDaoDTO dto, Map _params, int attachedFileMaxCount)  throws Exception  {
		
		String query = "";
		try {
			boolean isViewPage = true;
			query = "SELECT * FROM ( " + getSQLSelectNBoard(_params, isViewPage, attachedFileMaxCount) + " ) A ";
			dto.daoDetailContent = super.executeQueryMap(query);
			
			// super admin except
			Object oMap = _params.get(SESSION_MAP);
			String admMemId = pnull(  (Map)oMap, Const.SESSION_KEY_ADM_MEM_ID );
			if( !"admin".equalsIgnoreCase(admMemId) ) {
				super.executeUpdate("UPDATE NBOARD SET HIT=HIT+1 WHERE BL_IDX='"+pnull(_params, "bl_idx")+"'");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			free();
		}
		
		D(_logger, Thread.currentThread().getStackTrace(), "DAO Result.[DTO:"+dto+"]" );
	}

	/*
	 * 관리자 이름을 조회하기
	 */
	private Map getMember(Map _params)  throws Exception {
		Map result = null;
		try {
			String query = "SELECT ADM_NAME AS NAME FROM ADMMEMBER WHERE ADM_ID='"+_params.get("user_id")+"' ";
			result = super.executeQueryMap(query);
		} catch (Exception e) {
			throw e;
		} finally {
		}
		if(result==null) result = new HashMap();
		return result;
	}

	/*
	 * NBOARD 등록
	 */
	@Override
	public boolean nboardInsert(Map _params, List uploadedFiles)  throws Exception {
		D(_logger, Thread.currentThread().getStackTrace(), "DAO nboardInsert Called.[params:"+_params+"]" );
		
		int r=-1;
		try {
			// 게시글 등록 & 첨부파일 등록 
			r = executeInsertNBoard( _params,  uploadedFiles, getClass().getSimpleName()+".nboardInsert()");
			// 첨부파일 등록
//			String registerId = pnull(getMember(_params), "NAME");
//			insertNboardUpload( _params,  uploadedFiles, registerId);
		} catch (Exception e) {
			throw e;
		} finally {
			free();
		}
		
		D(_logger, Thread.currentThread().getStackTrace(), "DAO nboardInsert Return.[result:"+r+"]" );
		return r > 0 ? true : false;
	}

	/*
	 * NBOARD 게시물 수정
	 * (non-Javadoc)
	 * @see kr.caincheon.church.admin.dao.NBoardDao#nboardModify(java.util.Map, java.util.List)
	 */
	@Override
	public boolean nboardModify(Map _params, List uploadedFiles)  throws Exception {
		int rn = 0, rn1 = 0, rn2 = 0, rn3 = 0, rn4 = 0;
		
		PreparedStatement preparedStatement = null;
		boolean bReturn = true;
		
		D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+", uploadedFiles:"+uploadedFiles+"]" );
		
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
		} else {
			registerNm = pnull(getMember(_params), "NAME", updateId);
		}

		// delete old files
		CommonExecuteQuery.deleteAttachedFilesNboardUpload(this, _params);
		
		// new files upload
		if(uploadedFiles.size() > 0) {
			CommonExecuteQuery.insertAttachedFilesNboardUpload(this, _params,  uploadedFiles, registerNm);
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
			
			preparedStatement = getPreparedStatement(query);
			preparedStatement.setString(i++,  pnull(_params, "title"));
			preparedStatement.setString(i++,  pnull(_params, "contents"));
			preparedStatement.setString(i++,  updateId);
			//preparedStatement.setString(i++,  registerNm);
			preparedStatement.setString(i++,  pnull(_params, "is_notice", "N"));
			preparedStatement.setString(i++,  pnull(_params, "event_date"));
			preparedStatement.setString(i++,  pnull(_params, "down_level", "A"));
			preparedStatement.setString(i++,  pnull(_params, "bl_idx"));
			rn3 = preparedStatement.executeUpdate();

			D(_logger, Thread.currentThread().getStackTrace(), "query executed result count rn3 : "+rn3 );
			
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
			free();
		}

		rn = rn1 + rn2 + rn3 + rn4;
		return rn >= 1 && bReturn;
	}

	/*
	 * NBOARD 게시물 삭제
	 * (non-Javadoc)
	 * @see kr.caincheon.church.admin.dao.NBoardDao#nboardDelete(java.util.Map)
	 */
	@Override
	public boolean nboardDelete(Map _params)  throws Exception {
		int rn = 0;
		String bl_idx = pnull(_params, "bl_idx", "0");

		D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );

		// 게시물 삭제
		try {
			rn = executeUpdate("DELETE FROM NBOARD WHERE BL_IDX="+bl_idx);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}

		
		// 첨부 파일 삭제
		CommonExecuteQuery.deleteAttachedFilesNboardUpload(this, _params);
		
		// 첨부 데이터 삭제
		try {
			rn += executeUpdate("DELETE FROM NBOARD_UPLOAD WHERE BL_IDX="+bl_idx);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			free();
		}

		return rn >= 1;
	}

}
