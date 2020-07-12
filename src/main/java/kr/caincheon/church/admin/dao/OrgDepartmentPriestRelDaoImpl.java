package kr.caincheon.church.admin.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import kr.caincheon.church.common.base.CommonDao;
import kr.caincheon.church.common.base.CommonException;

/**
 * 임지 발령 정보를 관리
 * @author benjamin
 */
@Repository("orgDepartmentPriestRelDao")
public class OrgDepartmentPriestRelDaoImpl extends CommonDao implements OrgDepartmentPriestRelDao
{

    private final Logger _logger = Logger.getLogger(getClass());
    
    //=================== 코드 관리 =======================
    
    @Override
	public int insert(Map map) throws CommonException, Exception {
		int rtVal = 0;
		// params
		//checkBasic(map);
		String org_idx    = map.get("org_idx").toString();
		String p_idx      = map.get("p_idx").toString();
		String p_position = pnull(map, "p_position", "(select from )");
		
		
		
		// syntax
		String sql = "INSERT INTO ORG_DEPARTMENT_PRIEST_REL "
				+ " ( IDX, ORG_IDX, P_IDX, P_POSITION, INS_DATE, APPOINTMENT_START_DATE ) VALUES ( "
				+ " ( SELECT ISNULL(MAX(IDX),0)+1 FROM NEWCAINCHEON.ORG_DEPARTMENT_PRIEST_REL ) "
				+ ", " + org_idx + ", " + p_idx+ ", " + p_position
				+ ", CONVERT( CHAR(8), CURRENT_TIMESTAMP, 112)"
				+ ", CONVERT( CHAR(8), CURRENT_TIMESTAMP, 112)"
				+ ") ";
		
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
	public int update(Map map) throws CommonException, Exception {
		int rtVal = 0;
		// params
		//checkBasic(map);
		String code  = map.get("code").toString();
		String name  = map.get("name").toString();
		String type  = pnull(map, "type", "");
		String use_yn= pnull(map, "use_yn", "Y");
		String upd_id= pnull(map, "upd_id", "admin");
		use_yn = use_yn.equalsIgnoreCase("on") ? "Y":"N";
		// syntax
		String sql = "UPDATE CODE_MASTER SET "
				+ " NAME='"+name+"'"
				+ ", USE_YN='"+use_yn+"'"
				+ (type.length()==0 ? "":", TYPE='"+type+"'")
				+ ", UPD_DATE=CONVERT( CHAR(8), CURRENT_TIMESTAMP, 112), UPD_ID='"+upd_id+"' "
				+ " WHERE CODE = '"+code+"'"
				;
		
		// debug
		D(_logger, Thread.currentThread().getStackTrace(), "sql="+sql);
		
		// execute
		try {
			rtVal = executeUpdate(sql);
		} catch (SQLException e) { 
			throw new CommonException(e.getMessage(), "UPDATE ERROR", e);
		}
		return rtVal;
	}
    
    @Override
	public int delete(Map map) throws CommonException, Exception {
		int rtVal = 0;
		// params
		//checkBasic(map);
		String code  = map.get("code").toString();
		String del_yn= "Y";
		String upd_id= pnull(map, "upd_id", "admin");
		// syntax
		String sql = "UPDATE CODE_MASTER SET "
				+ "  DEL_YN='"+del_yn+"'"
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
	public List<Map> select(Map map) throws CommonException, Exception {
		// param
		String delyn = pnull(map, "del_yn", "");
		// sql
		String sql = "SELECT * FROM ORG_DEPARTMENT_PRIEST_REL";
		String where = " WHERE 1=1 "
				+ (delyn.length()==0 ? "" : " AND del_yn='"+delyn+"' ")
				;
		// exeucte
		List rtList = null;
		try {
			rtList = executeQueryList(sql+where);
		} catch (SQLException e) { 
			throw new CommonException(e.getMessage(), "SELECT ERROR", e);
		}
		return rtList;
	}
    
    public int selectCount(Map params) throws CommonException, Exception {
    	int rtVal = 0;
    	
    	return rtVal;
    }
    public int selectCount() throws CommonException, Exception {
    	return executeCount(lastSQL, true);
    }
}
