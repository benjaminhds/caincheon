package kr.caincheon.church.admin.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import kr.caincheon.church.common.base.CommonDao;
import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.base.CommonException;
import kr.caincheon.church.common.utils.UtilString;

/**
 * 공통코드(코드테이블과 & 코드인스턴스테이블)를 관리하는 DAO
 * - method 가 inquireXXXXX로 시작되는 메서드는 모두 front 에서 사용하기 위한 메서드이고, 그 외에는 관리자에서 호출되는 메서드로 구분됨.
 * @author benjamin
 */
@Repository("codeDao")
public class CodeDaoImpl extends CommonDao implements CodeDao
{

    private final Logger _logger = Logger.getLogger(getClass());
    

    //=================== innert method =======================
    
    
    //=================== 코드 관리 =======================
    
    @Override
	public int insertCode(Map map) throws CommonException, Exception {
		int rtVal = 0;
		// params
		//checkBasic(map);
		String code   = map.get("code").toString();
		String name   = map.get("name").toString();
		String type   = pnull(map, "type", "C");
		String del_yn = pnull(map, "del_yn", "N");
		String use_yn = pnull(map, "use_yn", "Y");
		String upd_id = pnull(map, "upd_id", "admin");
		String eng_name=pnull(map, "eng_name", "");
		
		// syntax
		String sql = "INSERT INTO CODE_MASTER "
				+ " (CODE, NAME, USE_YN, DEL_YN, TYPE, INS_DATE, UPD_ID, ENG_NAME) "
				+ " VALUES('"+code+"', '"+name+"', '"+use_yn+"', '"+del_yn+"', '"+type+"', CONVERT( CHAR(8), CURRENT_TIMESTAMP, 112), '"+upd_id+"', '"+eng_name+"') "
				;
		
		// debug
		D(_logger, Thread.currentThread().getStackTrace(), "sql="+sql);
		
		// execute
		try {
			rtVal = executeUpdate(sql);
		} catch (SQLException e) { 
			throw new CommonException(e.getMessage(), "INSERT ERROR", e);
		}
		return rtVal;
	}
    
    @Override
	public int updateCode(Map map) throws CommonException, Exception {
		int rtVal = 0;
		// params
		//checkBasic(map);
		String code   = map.get("code").toString();
		String name   = map.get("name").toString();
		String type   = pnull(map, "type", "").toUpperCase();
		String use_yn = pnull(map, "use_yn", "Y").toUpperCase();
		String del_yn = pnull(map, "del_yn", "Y").toUpperCase();
		String upd_id = pnull(map, "upd_id", "admin");
		String eng_name= pnull(map, "eng_name");
		
		// syntax
		String sql = "UPDATE CODE_MASTER SET "
				+ "  NAME='"+name+"'"
				+ ", TYPE='"+type+"'"
				+ ", USE_YN='"+use_yn+"'"
				+ ", ENG_NAME='"+eng_name+"'"
				+ ", DEL_YN='"+del_yn+"'"
				+ ", UPD_DATE=CONVERT( CHAR(8), CURRENT_TIMESTAMP, 112), UPD_ID='"+upd_id+"' "
				+ " WHERE CODE = '"+code+"'"
				;
		
		// debug
		D(_logger, Thread.currentThread().getStackTrace(), "sql="+sql);
		
		// execute
		try {
			rtVal = executeUpdate(sql);
		} catch (SQLException e) { 
			throw new CommonException(e.getMessage(), "UPDATE ERROR: "+e.getErrorCode(), e);
		}
		
		D(_logger, Thread.currentThread().getStackTrace(), "return ="+rtVal);
		
		return rtVal;
	}
    
    @Override
	public int deleteCode(Map map) throws CommonException, Exception {
		int rtVal = 0;
		// params
		//checkBasic(map);
		String code  = map.get("code").toString();
		String upd_id= pnull(map, "upd_id", "admin");
		// syntax
		String sql = "UPDATE CODE_MASTER SET "
				+ "  DEL_YN='Y'"
				+ ", USE_YN='N'"
				+ ", DEL_DATE=CONVERT( CHAR(8), CURRENT_TIMESTAMP, 112), UPD_ID='"+upd_id+"' "
				+ " WHERE CODE = '"+code+"'"
				;
		// execute
		try {
			rtVal = executeUpdate(sql);
		} catch (SQLException e) { 
			throw new CommonException(e.getMessage(), "UPDATE ERROR", e);
		}
		return rtVal;
	}
    
    @Override
	public CommonDaoDTO selectCodes(Map map) throws CommonException, Exception {
		// param
		String code  = pnull(map, "code", "");
		String type  = pnull(map, "type", "");
		String useyn = pnull(map, "use_yn", "");
		String delyn = pnull(map, "del_yn", "");
		// sql
		String sql = "select * from CODE_MASTER";
		String where = " WHERE 1=1 "
				+ (code.length()==0 ? "" : " AND code='"+code+"' ")
				+ (type.length()==0 ? "" : " AND type='"+type+"' ")
				+ (useyn.length()==0 ? "" : " AND use_yn='"+useyn+"' ")
				+ (delyn.length()==0 ? "" : " AND del_yn='"+delyn+"' ")
				;
		// exeucte
		CommonDaoDTO dto = new CommonDaoDTO();
		try {
			dto.daoList = executeQueryList(sql+where);
			dto.daoTotalCount = executeCount(sql+where);
		} catch (SQLException e) { 
			throw new CommonException(e.getMessage(), "SELECT ERROR", e);
		}
		return dto;
	}

//	/* 마지막 코드번호를 리턴 */
//    @Override
//	public String selectLastCode() throws CommonException, Exception {
//    	String rtVal="";
//    	return rtVal;
//	}
//	/* 마지막 다음 코드번호를 리턴 */
//    @Override
//	public String selectLastNextCode() throws CommonException, Exception {
//    	String rtVal="";
//    	return rtVal;
//	}
	
	
	//=================== 코드 인스턴스 관리 =======================
	
    // 코드 인스턴스 등록
    @Override
	public int insertCodeInstance(Map _params) throws CommonException, Exception {
		int rtVal = 0;
		// params
		//checkBasic(map);
		String code      = _params.get("code").toString().replaceAll("[^0-9]","");
		String code_inst = pnull(_params, "code_inst").replaceAll("[^0-9]","");
		String name      = _params.get("name").toString().replaceAll("'","\'");
		String use_yn    = pnull(_params, "use_yn", "Y");
		String del_yn    = pnull(_params, "del_yn", "N");
		String memo      = pnull(_params, "memo");
		String memo2     = pnull(_params, "memo2");
		String orderno   = pnull(_params, "orderno", "").replaceAll("[^0-9]", "[^0-9]");
		String upd_id    = pnull(_params, "upd_id", "admin");
		
		if( !(use_yn.equalsIgnoreCase("Y") || use_yn.equalsIgnoreCase("N")) ) {
			use_yn = use_yn.equalsIgnoreCase("on") ? "Y":"N";
		}
		if( !(del_yn.equalsIgnoreCase("Y") || del_yn.equalsIgnoreCase("N")) ) {
			del_yn = del_yn.equalsIgnoreCase("on") ? "Y":"N";
		}
		
		// syntax
		String query = "INSERT INTO CODE_INSTANCE "
				+ " (CODE, CODE_INST, NAME, USE_YN, DEL_YN"
				+ ", order_no, memo , memo2 "
				+ ", INS_DATE, UPD_ID) "
				+ " VALUES('"+code+"', '"+code_inst+"', '"+name+"', '"+use_yn+"', '"+del_yn+"'"
				+ ", '"+orderno+"', ?, ?"
				+ ", CONVERT( CHAR(8), CURRENT_TIMESTAMP, 112), '"+upd_id+"') ";
		// execute
		try {
			rtVal = executeUpdatePreparedStatement(query, getLinkedHashMap(memo, memo2));
			
		} catch (SQLException e) { 
			throw new CommonException(e.getMessage(), "INSERT ERROR", e);
		}
		return rtVal;
	}
    
    // 코드 인스턴스 수정
    @Override
	public int updateCodeInstance(Map map) throws CommonException, Exception {
		int rtVal = 0;
		D(_logger, Thread.currentThread().getStackTrace(), "map = " + map);
		// params
		//checkBasic(map);
		String code          = pnull(map, "code").replaceAll("[^0-9]", "[^0-9]");
		String code_inst     = pnull(map, "code_inst").replaceAll("[^0-9]", "");
		String code_inst_aft = pnull(map, "code_inst_aft").replaceAll("[^0-9]", ""); // 코드인스턴스를 바뀌면 이 값이 변경된 코드인스턴스로 값이 들어 온다. 그 외에는 값이 안 들어 온다
		String name          = pnull(map, "name").replaceAll("'", "\'");
		String use_yn        = pnull(map, "use_yn");
		String del_yn        = pnull(map, "del_yn");
		String orderno       = pnull(map, "orderno").replaceAll("[^0-9]", "");
		String memo          = pnull(map, "memo");
		String memo2         = pnull(map, "memo2");
		String upd_id        = pnull(map, "upd_id", "admin");

		if( !(use_yn.equalsIgnoreCase("Y") || use_yn.equalsIgnoreCase("N")) ) {
			use_yn = use_yn.equalsIgnoreCase("on") ? "Y":"N";
		}
		if( !(del_yn.equalsIgnoreCase("Y") || del_yn.equalsIgnoreCase("N")) ) {
			del_yn = del_yn.equalsIgnoreCase("on") ? "Y":"N";
		}
		
		// checking dup
		if(code_inst_aft.length()>0 && !code_inst_aft.equals(code_inst)) {
			if(executeCount("SELECT CODE, CODE_INST, NAME FROM CODE_INSTANCE WHERE CODE = '"+code+"' AND CODE_INST='"+code_inst_aft+"' ", true) > 0 ) {
				throw new CommonException("This code already exists.");
			}
		}
		
		// syntax TODO prepareStatement로 변경을 권고..bjm
		String query = "UPDATE CODE_INSTANCE SET "
				+ " NAME='"+name+"'"
				+ (code_inst_aft.length()==0 ? "" : ", CODE_INST='"+code_inst_aft+"'")
				+ (use_yn.length()==0 ? "" : ", USE_YN='"+use_yn+"'")
				+ (del_yn.length()==0 ? "" : ", DEL_YN='"+del_yn+"'")
				+ (orderno.length()==0 ? "" : ", ORDER_NO='"+orderno+"'")
				+ ", MEMO=? "
				+ ", MEMO2=? "
				+ ", UPD_DATE=CONVERT( CHAR(8), CURRENT_TIMESTAMP, 112), UPD_ID='"+upd_id+"' "
				+ " WHERE CODE = '"+code+"' AND CODE_INST='"+code_inst+"'"   
				;
		// execute
		try {
			//rtVal = executeUpdate(query);
			rtVal = executeUpdatePreparedStatement(query, getLinkedHashMap(memo, memo2));
		} catch (SQLException e) { 
			throw new CommonException(e.getMessage(), "UPDATE ERROR", e);
		}
		return rtVal;
	}
    
    // 코드 인스턴스 삭제(삭제 플래그 설정)
    @Override
	public int deleteCodeInstance(Map map) throws CommonException, Exception {
		int rtVal = 0;
		// params
		//checkBasic(map);
		String code      = map.get("code").toString().replaceAll("[^0-9]", "[^0-9]");
		String code_inst = map.get("code_inst").toString().replaceAll("[^0-9]", "[^0-9]");
		String del_yn    = pnull(map, "del_yn", "Y");
		String upd_id    = pnull(map, "upd_id", "admin");
		
		del_yn = del_yn.equalsIgnoreCase("on") ? "Y":"N";
		
		// syntax
		String sql = "UPDATE CODE_INSTANCE SET "
				+ "  DEL_YN='"+del_yn+"'"
				+ ", DEL_DATE=CONVERT( CHAR(8), CURRENT_TIMESTAMP, 112), UPD_ID='"+upd_id+"' "
				+ " WHERE CODE = '"+code+"' AND CODE_INST='"+code_inst+"'"
				;
		// execute
		try {
			rtVal = executeUpdate(sql);
		} catch (SQLException e) { 
			throw new CommonException(e.getMessage(), "UPDATE ERROR", e);
		}
		return rtVal;
	}
    
    // 코드 인스턴스 조회 
    @Override
	public CommonDaoDTO selectCodeInstances(Map map) throws CommonException, Exception {
    	D(_logger, Thread.currentThread().getStackTrace(), "dao called: "+map);
    	
		// param
    	String code  = pnull(map, "code", "");
		String useyn = pnull(map, "use_yn", "");
		String delyn = pnull(map, "del_yn", "");
		String code_inst = pnull(map, "code_inst", "");
		// sql
		String orderby = " ORDER BY " + pnull(map, "orderby", "CODE_INST ASC");
		String sql = "SELECT * FROM CODE_INSTANCE";
		String where = " WHERE 1=1 "
				+ (code.length()==0 ? "" : " AND code='"+code+"' ")
				+ (code_inst.length()==0 ? "" : " AND code_inst='"+code_inst+"' ")
				+ (useyn.length()==0 ? "" : " AND use_yn='"+useyn+"' ")
				+ (delyn.length()==0 ? "" : " AND del_yn='"+delyn+"' ")
				;
		// exeucte
		CommonDaoDTO dto = new CommonDaoDTO();
		try {
			dto.daoList = executeQueryList(sql+where+orderby);
			dto.daoTotalCount = executeCount(sql+where);
		} catch (SQLException e) { 
			throw new CommonException(e.getMessage(), "SELECT ERROR", e);
		}
		
		D(_logger, Thread.currentThread().getStackTrace(), "dao return: " + dto);
		
		return dto;
	}
    

	/* 마지막 코드번호를 리턴 */
    @Override
	public String selectLastCodeInst() throws CommonException, Exception {
    	String rtVal="";
    	return rtVal;
	}
	/* 마지막 다음 코드번호를 리턴 */
    @Override
	public String selectLastNextInstCode() throws CommonException, Exception {
    	String rtVal="";
    	return rtVal;
	}
	
	
	//=================== 코드 조회 =======================
    @Override
	public List<Map> inquireCodes(Map map) throws CommonException, Exception {
		// param
		String code      = pnull(map, "code", "");//code_master
		String name      = pnull(map, "name", "");//code_master
		
		String code_inst = pnull(map, "code_inst", "");//code_instance
		String inst_name = pnull(map, "inst_name", "");//code_instance
		
		// sql
		String sql = "SELECT M.CODE, M.NAME, M.TYPE"
				+ ", I.CODE_INST, I.NAME, I.ORDER_NO, I.MEMO"
				+ " FROM CODE_MASTER M, CODE_INSTANCE I "
				+ " WHERE M.CODE = I.CODE "
				+ " AND M.USE_YN = 'Y' AND M.DEL_YN='N' "
				+ " AND I.USE_YN = 'Y' AND I.DEL_YN='N' "
				+ (code.length()==0 ? "" : " AND I.CODE='"+code+"' ")
				+ (name.length()==0 ? "" : " AND M.NAME like '%"+name+"%' ")
				
				+ (code_inst.length()==0 ? "" : " AND I.CODE_INST='"+code_inst+"' ")
				+ (inst_name.length()==0 ? "" : " AND I.NAME like '%"+inst_name+"%' ")
				;
		
		// exeucte
		List rtList = null;
		try {
			rtList = executeQueryList(sql);			 
		} catch (SQLException e) { 
			throw new CommonException(e.getMessage(), "SELECT ERROR", e);
		}
		return rtList;
	}
	
    @Override
	public List<Map> inquireCodeInstance(String[] code) throws CommonException, Exception {
		// param
		String codes = UtilString.toString(code, "','");
		
		// sql
		String sql = "SELECT M.CODE, M.NAME, M.TYPE"
				+ ", I.CODE_INST, I.NAME, I.ORDER_NO, I.MEMO"
				+ " FROM CODE_MASTER M, CODE_INSTANCE I "
				+ " WHERE M.CODE = I.CODE "
				+ " AND M.USE_YN = 'Y' AND M.DEL_YN='N' "
				+ " AND I.USE_YN = 'Y' AND I.DEL_YN='N' "
				+ " AND M.CODE in ('"+ codes +"')"
				;
		
		// exeucte
		List rtList = null;
		try {
			rtList = executeQueryList(sql);			 
		} catch (SQLException e) { 
			throw new CommonException(e.getMessage(), "SELECT ERROR", e);
		}
		return rtList;
	}
	

	/** 코드인스턴스의 메모값을 조회 */
	@Override
	public List<Map> selectedAreaCodeMapPolygonPath(Map params) throws CommonException, Exception {
		// param
		String code      = pnull(params, "code");
		String code_inst = pnull(params, "code_inst");
		
		// sql
		String sql = "SELECT CODE, CODE_INST, NAME, ISNULL(MEMO,'') AS MEMO , ISNULL(MEMO2,'') AS MEMO2 "
				+ " FROM CODE_INSTANCE "
				+ " WHERE CODE='"+ code +"' AND CODE_INST = '"+code_inst+"'"
				;
		
		// exeucte
		List rtList = null;
		try {
			rtList = executeQueryList(sql);
		} catch (SQLException e) { 
			throw new CommonException(e.getMessage(), "SELECT ERROR", e);
		}
		return rtList ;
	}
	
}
