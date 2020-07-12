package kr.caincheon.church.admin.dao;

import java.sql.SQLException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import kr.caincheon.church.common.base.CommonDao;
import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.base.CommonException;

/**
 * 게시판 카테고리 관리 
 * @author benjamin
 */
@Repository("boardCategoryDao")
public class BoardCategoryDaoImpl extends CommonDao implements BoardCategoryDao
{

    private final Logger _logger = Logger.getLogger(getClass());


    //============= 게시판코드(b_idx) management ==================

	// 보드 코드 관리
    public int insertBoard(Map params) throws CommonException, Exception {
		D(_logger, Thread.currentThread().getStackTrace(), "..params = " + params);
		
		// Params
		//String b_idx  = pnull(params, "b_idx"); // 게시판코드(PK), IDENTITY(1,1)
		String b_nm   = pnull(params, "b_nm");
		String b_type = pnull(params, "b_type"); 
		String useyn_comment       = pnull(params, "useyn_comment", "N").toUpperCase();
		String useyn_secret        = pnull(params, "useyn_secret", "N").toUpperCase();
		String useyn_comment_secret= pnull(params, "useyn_comment_secret", "N").toUpperCase();
		String useyn_attachement   = pnull(params, "useyn_attachement", "N").toUpperCase();
		String useyn_login         = pnull(params, "useyn_login", "N").toUpperCase();
		String useyn_download_perm = pnull(params, "useyn_download_perm", "N").toUpperCase();
		
		//
		int rtVal = 0;
		// syntax
		String sql = "INSERT INTO MBOARD_MNGT (B_NM, B_TYPE, REG_DT, UPD_DT"
				+ ", USEYN_COMMENT, USEYN_SECRET, USEYN_COMMENT_SECRET, USEYN_ATTACHEMENT"
				+ ", USEYN_LOGIN, USEYN_DOWNLOAD_PERM) ";
		sql += " VALUES ('"+b_nm+"', '"+b_type+"', GETDATE(), NULL "
				+ ", '"+useyn_comment+"'"
				+ ", '"+useyn_secret+"'"
				+ ", '"+useyn_comment_secret+"'"
				+ ", '"+useyn_attachement+"'"
				+ ", '"+useyn_login+"'"
				+ ", '"+useyn_download_perm+"'"
				+ ") "
				;
		
		// execute
		try {
			rtVal = executeUpdate(sql);
		} catch (SQLException e) { 
			throw new CommonException(e.getMessage(), "INSERT ERROR", e);
		}
		D(_logger, Thread.currentThread().getStackTrace(), "DAO Result.["+rtVal+"]" );
		return rtVal;
    }
    
    public int updateBoard(Map params) throws CommonException, Exception {
    	D(_logger, Thread.currentThread().getStackTrace(), "..params = " + params);
		
		// Params
		String b_idx  = pnull(params, "b_idx"); // 게시판코드(PK), IDENTITY(1,1)
		String b_nm   = pnull(params, "b_nm");
		String b_type = pnull(params, "b_type"); 
		String useyn_comment       = pnull(params, "useyn_comment", "N").toUpperCase();
		String useyn_secret        = pnull(params, "useyn_secret", "N").toUpperCase();
		String useyn_comment_secret= pnull(params, "useyn_comment_secret", "N").toUpperCase();
		String useyn_attachement   = pnull(params, "useyn_attachement", "N").toUpperCase();
		String useyn_login         = pnull(params, "useyn_login", "N").toUpperCase();
		String useyn_download_perm = pnull(params, "useyn_download_perm", "N").toUpperCase();
		
		//
		int rtVal = 0;
		// syntax
		String sql = "UPDATE MBOARD_MNGT SET "
				+ " B_NM='"+b_nm+"'"
				+ ", B_TYPE='"+b_type+"', UPD_DT=GETDATE()"
				+ ", USEYN_COMMENT='"+useyn_comment+"'"
				+ ", USEYN_SECRET='"+useyn_secret+"'"
				+ ", USEYN_COMMENT_SECRET='"+useyn_comment_secret+"'"
				+ ", USEYN_ATTACHEMENT='"+useyn_attachement+"'"
				+ ", USEYN_LOGIN='"+useyn_login+"'"
				+ ", USEYN_DOWNLOAD_PERM='"+useyn_download_perm+"'"
				+ " WHERE B_IDX="+b_idx
				;
		
		// execute
		try {
			rtVal = executeUpdate(sql);
		} catch (SQLException e) { 
			throw new CommonException(e.getMessage(), "UPDATE ERROR", e);
		}
		D(_logger, Thread.currentThread().getStackTrace(), "DAO Result.["+rtVal+"]" );
		return rtVal;
    }
    
    public int deleteBoard(Map params) throws CommonException, Exception {
    	return -1;
    }
    
    /** 코드를 리턴 */
    public CommonDaoDTO selectBoard(Map params) throws CommonException, Exception {

		D(_logger, Thread.currentThread().getStackTrace(), "..params = " + params);
		
		// return
		CommonDaoDTO dto = new CommonDaoDTO();
		
		// param
		String b_idx  = pnull(params, "b_idx"); // 게시판코드(FK)
		String b_nm   = pnull(params, "b_nm");
		String c_idx  = pnull(params, "c_idx");
		
		// sql
		String sql = "SELECT M.* FROM MBOARD_MNGT M, MBOARD_CATEGORY C "
				+ " WHERE M.B_IDX=C.B_IDX "
				+ (b_nm.length()  ==0 ? "" : " AND M.B_NM like '%"+b_nm+"%' ")
				+ (b_idx.length() ==0 ? "" : " AND M.B_IDX='"+b_idx+"' ")
				+ (c_idx.length() ==0 ? "" : " AND C.C_IDX='"+c_idx+"' ")
				;
		String orderby = " ORDER BY M.B_IDX ASC, C.C_IDX ASC ";
		
		// 
		sql = "SELECT M.* FROM MBOARD_MNGT M "
			+ " WHERE 1=1 "
			+ (b_nm.length()  ==0 ? "" : " AND M.B_NM like '%"+b_nm+"%' ")
			+ (b_idx.length() ==0 ? "" : " AND M.B_IDX='"+b_idx+"' ")
			;
		orderby = " ORDER BY M.B_IDX ASC ";
		
		// exeucte
		try {
			dto.daoTotalCount = executeCount(sql, true);
			dto.daoList = executeQueryList(sql+orderby);
			
		} catch (SQLException e) { 
			e.printStackTrace();
			throw new CommonException(e.getMessage(), "SELECT ERROR", e);
		}
		D(_logger, Thread.currentThread().getStackTrace(), "DAO Result.["+dto.daoList+"]" );
		
		return dto;
    }
    

    //============= 게시판코드 카테고리(c_idx) management ==================
    
	@Override
	public int insertBoardCategory(Map params) throws CommonException, Exception {
		
		D(_logger, Thread.currentThread().getStackTrace(), "..params = " + params);
		
		// Params
		String b_idx  = pnull(params, "b_idx"); // 게시판코드(FK)
		String c_idx  = pnull(params, "c_idx"); // 게시판에서 사용하는 분류코드
		String name   = pnull(params, "name");  // 게시판에서 사용하는 분류코드명
		String is_use = pnull(params, "is_use", "Y").toUpperCase();
		
		//180120
//		if(c_idx.length()==0) c_idx = "(SELECT ISNULL(MAX(C_IDX),0)+1 FROM MBOARD_CATEGORY)";
		
		//
		int rtVal = 0;
		// syntax
		String sql = "INSERT INTO MBOARD_CATEGORY (C_IDX, NAME, IS_USE, B_IDX) VALUES ("+c_idx+", '"+name+"', '"+is_use+"', '"+b_idx+"') ";
		sql = "INSERT INTO MBOARD_CATEGORY (NAME, IS_USE, B_IDX) VALUES ('"+name+"', '"+is_use+"', '"+b_idx+"') "; // IDENTITY(1,1) 때문에 c_idx 컬럼 제외
		
		// execute
		try {
			rtVal = executeUpdate(sql);
		} catch (SQLException e) { 
			throw new CommonException(e.getMessage(), "INSERT ERROR", e);
		}
		D(_logger, Thread.currentThread().getStackTrace(), "DAO Result.["+rtVal+"]" );
		return rtVal;
	}

	@Override
	public int updateBoardCategory(Map params) throws CommonException, Exception {
		
		D(_logger, Thread.currentThread().getStackTrace(), "..params = " + params);
		
		// Params
		String b_idx  = pnull(params, "b_idx"); // 게시판코드(FK)
		String name   = pnull(params, "name");
		String c_idx  = pnull(params, "c_idx");
		String is_use = pnull(params, "is_use").toUpperCase();
		if( !("Y".equalsIgnoreCase(is_use) || "N".equalsIgnoreCase(is_use)) ) {
			throw new CommonException("IS_USE Column must has a Y or N.", "PARAM CHECK ERROR");
		}
		//
		int rtVal = 0;
		// syntax
		String sql = "UPDATE MBOARD_CATEGORY SET "
				+ " NAME='"+name+"' "
				+ " " + (is_use.length()==0 ? "" : ", IS_USE='"+is_use+"' ")
				+ " WHERE B_IDX=" + b_idx + " AND C_IDX=" + c_idx
				;
		
		// execute
		try {
			rtVal = executeUpdate(sql);
		} catch (SQLException e) { 
			throw new CommonException(e.getMessage(), "UPDATE ERROR", e);
		}
		D(_logger, Thread.currentThread().getStackTrace(), "DAO Result.["+rtVal+"]" );
		return rtVal;
	}

	@Override
	public int deleteBoardCategory(Map params) throws CommonException, Exception {
		
		D(_logger, Thread.currentThread().getStackTrace(), "..params = " + params);
		
		// Params
		//String is_use = pnull(params, "is_use").toUpperCase();
		String b_idx = pnull(params, "b_idx"); // 게시판코드(FK)
		String c_idx = pnull(params, "c_idx");
		if( "".equalsIgnoreCase(b_idx) || "".equalsIgnoreCase(c_idx) ) {
			throw new CommonException("The b_idx & c_idx value is neccerssary value.", "PARAM CHECK ERROR");
		}
		
		//
		int rtVal = 0;
		// syntax :: TODO 삭제가 아닌 flag를 N으로 설정했는데, 고객의 확인이 필요함.
		String sql = "UPDATE MBOARD_CATEGORY SET IS_USE='N' "
				+ " WHERE B_IDX = " + b_idx + " AND C_IDX = " + c_idx
				;
		
		// execute
		try {
			rtVal = executeUpdate(sql);
		} catch (SQLException e) { 
			throw new CommonException(e.getMessage(), "DELETE ERROR", e);
		}
		D(_logger, Thread.currentThread().getStackTrace(), "DAO Result.["+rtVal+"]" );
		return rtVal;
	}


	@Override
	public CommonDaoDTO selectBoardCategory(Map params) throws CommonException, Exception {
		
		D(_logger, Thread.currentThread().getStackTrace(), "..params = " + params);
		
		// return
		CommonDaoDTO dto = new CommonDaoDTO();
		
		// param
		String b_idx  = pnull(params, "b_idx"); // 게시판코드(FK)
		String name   = pnull(params, "name");
		String c_idx  = pnull(params, "c_idx");
		String is_use = pnull(params, "is_use","").toUpperCase();
		// sql
		String sql = "SELECT * FROM MBOARD_CATEGORY WHERE 1=1 "
				+ (name.length()  ==0 ? "" : " AND NAME='"+name+"' ")
				+ (is_use.length()==0 ? "" : " AND IS_USE='"+is_use+"' ")
				+ (b_idx.length() ==0 ? "" : " AND B_IDX='"+b_idx+"' ")
				+ (c_idx.length() ==0 ? "" : " AND C_IDX='"+c_idx+"' ")
				;
		
		// exeucte
		try {
			dto.daoList = executeQueryList(sql);
			dto.daoTotalCount = executeCount(sql, true);
		} catch (SQLException e) { 
			e.printStackTrace();
			throw new CommonException(e.getMessage(), "SELECT ERROR", e);
		}
		D(_logger, Thread.currentThread().getStackTrace(), "DAO Result.["+dto.daoList+"]" );
		
		return dto;
	}
	
	
    //=================== innert method =======================
    	
}
