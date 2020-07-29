package kr.caincheon.church.common.base;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import kr.caincheon.church.admin.mboard.MBoardDAO;
import kr.caincheon.church.common.utils.ImageUtils;

/**
 * DAO를 위한 공통 OP를 제공한다.  
 * @author benjamin
 */
@Component
public class CommonExecuteQuery {
	
	//
	private final static Logger _logger = Logger.getLogger(CommonExecuteQuery.class);
	
	private static MBoardDAO mboardDao;
	
	static {
		mboardDao	=	new MBoardDAO();
	}
	/**
	 *  
	 */
	public static String executeCIdxsQuery(CommonDao parent, String b_idxs) throws CommonException {
		// 쿼리성능 개선을 위해, 서브쿼리가 아닌 별도 쿼리로 변경
		String b_idx_cause = " C_IDX IN (";
		
		List<Map<String, Object>> bidxs=null;
		String query = "";
		try {
			query = "SELECT C_IDX FROM NBOARD_CATEGORY WHERE B_IDX IN ("+b_idxs+") AND IS_USE='Y'";
			bidxs = parent.executeQueryList(query);
		} catch (SQLException e) {
			throw new CommonException(e.getMessage(), e).setSql(query);
		}
		for(int x = 0, x2 = bidxs.size() ; x < x2 ; x++) {
			Map<String, Object> row = bidxs.get(x);
			b_idx_cause = b_idx_cause + (x>0 ? ",":"") + parent.pnull(row.get("C_IDX")) ;
		}
		b_idx_cause += ") ";
		
		return b_idx_cause;
	}
	

	/**
	 * if return value is a false, then file not exist or path information is wrong.
	 * @param _params
	 * @return String - bu_idx
	 * @throws SQLException
	 */
	public static String deleteUploadedFile(CommonDao parent, String bl_idx, String strfilename, String CONTEXT_ROOT_PATH) throws SQLException {
		/*
		 * 아래와 같이 2개 함수를 동시에 호출하게 된다.
		 * 
		 * deleteUploadedFile(bl_idx, CONTEXT_ROOT_PATH);
		 * deleteUploadedFileDbRecord(bl_idx, strfilename, delFileName);
		 */
		String bu_idx = null;
		parent.D(_logger, Thread.currentThread().getStackTrace(), "EXE Query(1) old file delete option is on.");
		
		// file del
		Map<String, Object> delMap = parent.executeQueryMap( "SELECT BU_IDX, FILEPATH, STRFILENAME FROM NBOARD_UPLOAD WHERE BL_IDX=" + bl_idx + " AND STRFILENAME='" + strfilename +"'" ) ;
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
	}
	
	/**
	 * if return value is a false, then file not exist or path information is wrong.
	 * @param _params
	 * @return String - bu_idx
	 * @throws SQLException
	 */
	public static String deleteUploadedFileNew(Map params) throws SQLException {
		/*
		 * 썸네일 삭제
		 */
		String bu_idx = null;
		String uploadedFileRootPath = (String)params.get("CONTEXT_ROOT_PATH");
		
		// file del
		CommonDaoDTO dto = new CommonDaoDTO();
		
		dto	=	mboardDao.getUploadVo(params);
		
		Map<String, Object> delMap = dto.daoDetailContent;
		
		if(delMap!=null && delMap.size() > 1 ) {
			
			bu_idx	=	(String)delMap.get("BU_IDX");
			
			String dFilepath = (String)delMap.get("STRFILENAME");
			
			boolean isDel = ImageUtils.deleteFileWithThumbnail(uploadedFileRootPath, dFilepath);
			
			if(isDel) {
				_logger.debug("EXE Query(1) File is deleted.[" + dFilepath +"]");
			} else {
				_logger.debug("EXE Query(1) File is not exits.[" + dFilepath +"]");
			}
		}
		
		return bu_idx;
	}
	/**
	 * 
	 */
	public static int deleteUploadedFileDbRecord(CommonDao parent, String bl_idx, String strfilename) throws SQLException {
		String query = "DELETE FROM NBOARD_UPLOAD"
				+ "	WHERE BL_IDX="+bl_idx+" AND STRFILENAME='"+strfilename+"'";
		return parent.executeUpdate(query);
	}

	/**
	 * 
	 */
	public static int deleteUploadedFileDbRecord(CommonDao parent, String bu_idx) throws SQLException {
		String query = "DELETE FROM NBOARD_UPLOAD WHERE BU_IDX=" + bu_idx;
		return parent.executeUpdate(query);
	}
	
	/**
	 * 등록된 첨부 자료를 삭제한다.
	 */
	public static int deleteAttachedFilesNboardUpload(CommonDao parent, Map _params)  throws Exception {
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
	
	/**
	 * 등록된 첨부 자료를 삭제한다.
	 */
	public static void deleteAttachedFilesMboardUpload(Map _params)  throws Exception {
		
		int rn5 = 0;
		
		// files delete
		try {
			/*게시판 코드*/
			String bl_idx = (String)_params.get("i_sBlidx");
			
			/*고정 된 5개 파일 돌리기*/
			for(int i=1; i<6; i++) {
				
				/*삭제 파일 명*/
				String strfilename = (String)_params.get("delFile"+i);
				
				/*삭제할 파일이 있으면*/
				if (strfilename.length() > 0) {
					
					String bu_idx = deleteUploadedFileNew(_params);
					deleteUploadedFileDbRecordNew(_params);
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
	}
	
	private static void deleteUploadedFileDbRecordNew(Map _params) {
		mboardDao.deleteUpload(_params);
	}
	/**
	 * 첨부 파일을 등록한다.
	 */
	public static int insertAttachedFilesNboardUpload(CommonDao parent, Map _params, List uploadedFiles, String registerId)  throws Exception {
		
		parent.D(_logger, Thread.currentThread().getStackTrace(), "CommonNBoard Common Attached File List Insert .[uploaded file list:"+uploadedFiles+"]" );
		
		int rn4 = 0;
		PreparedStatement preparedStatement = null;
		String uploadedFileURI  = parent.pnull(_params, "CONTEXT_URI_PATH"); // "/upload/news/"
		//String uploadedFileRootPath = pnull(_params, "CONTEXT_ROOT_PATH");// "d:/newcaincheon/webapps/upload/news/"
		
		// file upload
		try {
			String query = "INSERT INTO NBOARD_UPLOAD (bu_idx, filepath, filesize, is_use,userid,filename,bl_idx,filetype,strfilename"
					+ ")  values("
					+ " (select ISNULL(MAX(BU_IDX), 0) + 1 from nboard_upload) "
					+ ", ?,?,?,?,?,?,NULL,?) ";
			
			parent.D(_logger, Thread.currentThread().getStackTrace(), "INSERT query :"+query );
			parent.D(_logger, Thread.currentThread().getStackTrace(), "INSERT query fileUploadedURI:"+uploadedFileURI );
			
			preparedStatement = parent.getPreparedStatement(query);
			for (int i = 0; i < uploadedFiles.size(); i++) {
				Map tmp = (Map) uploadedFiles.get(i);
				preparedStatement.setString(1, uploadedFileURI);
				preparedStatement.setString(2, parent.pnull(tmp.get("FILE_SIZE")));
				preparedStatement.setString(3, parent.pnull(tmp.get("IS_USE"), "Y"));
				preparedStatement.setString(4, registerId);
				preparedStatement.setString(5, parent.pnull(tmp.get("ORIGINAL_FILE_NAME")));
				preparedStatement.setString(6, parent.pnull(_params, "bl_idx"));
				preparedStatement.setString(7, parent.pnull(tmp.get("STORED_FILE_NAME")));
				int x = preparedStatement.executeUpdate();
				rn4 += x;
				parent.D(_logger, Thread.currentThread().getStackTrace(), "Inserted NBOARD_UPLOAD result: 현재건수:"+x+ ", 누적건수:"+rn4 );
			}

		} catch (Exception e) {
			throw e;
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			parent.commit();
		}
		return rn4;
	}
	
}
