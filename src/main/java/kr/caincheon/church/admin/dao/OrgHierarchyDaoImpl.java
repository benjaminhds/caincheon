package kr.caincheon.church.admin.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import kr.caincheon.church.common.base.CommonDao;
import kr.caincheon.church.common.base.CommonException;
import kr.caincheon.church.common.utils.UtilInt;
import kr.caincheon.church.common.utils.UtilString;

/**
 * 공통코드(코드테이블과 & 코드인스턴스테이블)를 관리하는 DAO
 * - method 가 inquireXXXXX로 시작되는 메서드는 모두 front 에서 사용하기 위한 메서드이고, 그 외에는 관리자에서 호출되는 메서드로 구분됨.
 * @author benjamin
 */
@Repository("orgHierarchyDao")
public class OrgHierarchyDaoImpl extends CommonDao implements OrgHierarchyDao
{

    private final Logger _logger = Logger.getLogger(getClass());
    

    //=================== innert method =======================
    

    
    
    //=================== 조직 코드 관리 =======================
    
    
    @Override
	public int insertOrgHierarchy(Map params) throws CommonException {
    	D(_logger, Thread.currentThread().getStackTrace(), " called..." + params);
    	
		int rtVal = 0;
		// params
		String lv1  = pnull(params, "lv1"), maxLv1="";
		String lv2  = pnull(params, "lv2", "00");
		String lv3  = pnull(params, "lv3", "000");
		String name = pnull(params, "name");
		String memo = pnull(params, "memo");
		int orderno = 1;
		
		if(lv1.equals("") && name.length()==0) throw new CommonException("1레벨 코드와 조직명이 선택되지 않았습니다.[lv1 & name is null]", "ERR-800",null);
		if(lv1.equals("")) throw new CommonException("1레벨 코드 값이 없습니다.[lv1 is null]", "ERR-801",null);
		if(name.length()==0) throw new CommonException("조직명이 입력되지 않았습니다.[name is null]", "ERR-802",null);
		
		// syntax
		String sql = "";
		try {
			int max = 0;
			Object maxObj = null;
			if(lv2.equals("00") && lv3.equals("000")) {
				if(lv1.equals("00")) {
					// 1-Level 조직에 추가하기 위해 코드 max 코드 조회
					maxObj = executeColumnMax( "ORG_HIERARCHY" , "LV1", "WHERE 1=1");
					max = Integer.parseInt(maxObj==null ? "0": maxObj.toString()) + 1;
					maxLv1 = max < 10 ? "0"+max : ""+max;
					// 사전 검증
					if(executeCount("SELECT CODE_INST, NAME FROM CODE_INSTANCE WHERE CODE='000001' AND CODE_INST='"+maxLv1+"' ", true) < 1) {
						throw new CommonException("사전에 조직분류 코드(`000001`)에 코드인스턴스가 추가 되어야 합니다.");
					}
				} else {
					// 2-Level 조직에 추가하기 위해 코드 max 코드 조회
					maxObj = executeColumnMax( "ORG_HIERARCHY" , "LV2", "WHERE LV1='"+lv1+"'");
					max = Integer.parseInt(maxObj==null ? "0": maxObj.toString()) + 1;
					lv2 = max < 10 ? "0"+max : ""+max;
					
				}
			
			} else if(!lv1.equals("00") && !lv2.equals("00") && lv3.equals("000")) {
				// 3-Level 조직에 추가하기 위해 코드 max 코드 조회
				maxObj = executeColumnMax( "ORG_HIERARCHY" , "LV3", "WHERE LV1='"+lv1+"' AND LV2='"+lv2+"' ");
				max = Integer.parseInt(maxObj==null ? "0": maxObj.toString()) + 1;
				lv3 = max < 10 ? "00"+max : (max < 100 ? "0"+max : ""+max);
			}
			orderno = max;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CommonException(e.getMessage(), e);
		}
		
		// syntax
		sql = "INSERT INTO ORG_HIERARCHY "
			+ " (ORG_IDX, ORDERNO, LV1, LV2, LV3, NAME, MEMO, INS_DATE) "
			+ " VALUES ( "
			//+ " NEXT VALUE FOR SEQ_ORG_HIERARCHY "
			+ " (SELECT MAX(ISNULL(ORG_IDX,0))+1 FROM ORG_HIERARCHY) "
			+ ", '"+orderno+"', '"+lv1+"', '"+lv2+"', '"+lv3+"', '"+name+"', '"+memo+"', CONVERT( CHAR(8), CURRENT_TIMESTAMP, 112) "
			+ ") ";

		// execute
		try {
			int x = 0;
			rtVal = executeUpdate(sql);
			// if LV 1 insert, 공통코드의 이름과 sync 
			if("00".equals(lv2) && "000".equals(lv3) && !"00".equals(lv1)) {
				int orderno2 = Integer.parseInt(maxLv1);
				x = executeUpdate("INSERT INTO CODE_INSTANCE(CODE, CODE_INST, NAME, USE_YN, DEL_YN, ORDER_NO, MEMO, INS_DATE, UPD_DATE, DEL_DATE, UPD_ID) "
						+ "VALUES "
						+ "( '000001', '"+maxLv1+"', NAME='"+name+"', 'Y', 'N', "+orderno2+", '', CONVERT( CHAR(8), CURRENT_TIMESTAMP, 112), '99991231', '99991231', 'admin' ");
				I(_logger, Thread.currentThread().getStackTrace(), " 공통코드 CODE='000001' 데이터 신규, 결과 1건 맞아 ? "+x+"건.");
			}
			
			if(rtVal > 0 ) {
				// org_idx 조회
				int x_org_idx = executeCount("SELECT ORG_IDX FROM ORG_HIERARCHY WHERE NAME='"+name+"' AND LV1='"+lv1+"' AND LV2='"+lv2+"' AND LV3='"+lv3+"'", false); 
				
				int depth = !"000".equals(lv3) ? 3 : (!"00".equals(lv2) ? 2:1) ;
				if ("01".equals(lv1)) {
					// 교구청 : 01 -> DEPARTMENT
					sql = "INSERT INTO DEPARTMENT /* 교구청마스터 */ "
						+ " (DEPART_IDX, NAME, ORG_IDX) "
						+ " VALUES ( "
						//+ " (SELECT TOP 1 ORG_IDX FROM DEPARTMENT WHERE LV1='"+lv1+"' AND LV2='"+lv2+"' AND LV3='"+lv3+"' AND NAME='"+name+"')"
						+ " (SELECT ISNULL(MAX(DEPART_IDX),0) + 1 FROM DEPARTMENT)"
						+ ", '"+name+"'"
						+ ", '"+x_org_idx+"' ) ";
					
				} else if ("02".equals(lv1)) {
					// 본당 : 02 -> CHURCH
					sql = "INSERT INTO CHURCH /* 본당마스터 */ "
							+ " (CHURCH_IDX, NAME, ORG_IDX) "
							+ " VALUES ( "
							+ " (SELECT ISNULL(MAX(CHURCH_IDX),0) + 1 FROM CHURCH)"
							+ ", '"+name+"'"
							+ ", '"+x_org_idx+"' ) ";
					
				} else {
					// 그 외 : 03 ~ 09 -> ORGANIZATION
					sql = "INSERT INTO ORGANIZATION /* 기타조직(기관/단체/수도회) 마스터 */ "
							+ " (O_IDX, NAME, ORG_IDX) "
							+ " VALUES ( "
							+ " (SELECT ISNULL(MAX(O_IDX),0) + 1 FROM ORGANIZATION)"
							+ ", '"+name+"'"
							+ ", '"+x_org_idx+"' ) ";
					
				}

				try {
					x = executeUpdate(sql); // table sync
					I(_logger, Thread.currentThread().getStackTrace(), " 조직마스터 데이터 추가, x:"+x);
				} catch (Exception e) {
					throw new CommonException("교구청 조직이 추가되지 못했습니다.["+e.getMessage()+"]", "");
				}
			}
			
			
		} catch (SQLException e) { 
			throw new CommonException(e.getMessage(), "INSERT ERROR", e);
		}
		return rtVal;
	}
	
    @Override
    public int updateOrgHierarchy(Map params) throws CommonException, Exception {
		int rtVal = 0;
		
		D(_logger, Thread.currentThread().getStackTrace(), "params..." + params);
		
		// params
		String lv1    = pnull(params, "lv1", "");
		String lv2    = pnull(params, "lv2", "00");
		String lv3    = pnull(params, "lv3", "000");
		String name   = pnull(params, "name").replaceAll("'", "\'");
		String memo   = pnull(params, "memo", "").replaceAll("'", "\'");
		String del_yn = pnull(params, "del_yn", "");
		
		String idx    = pnull(params, "idx");
		if(idx.length() == 0) {
			throw new CommonException("A PK is empty.", "ERR-900",null);
		}
		// duplication checking
		String sql = "";
		if( lv1.length()>0 && lv2.length()>0 && lv3.length()>0 ) {
			sql = "SELECT COUNT(*) FROM ORG_HIERARCHY WHERE lv1='"+lv1+"' and lv2='"+lv2+"' and lv3='"+lv3+"' and ORG_IDX != "+idx;
			if(executeCount(sql, false) > 0) {
				D(Thread.currentThread().getStackTrace(), "ORG Code is a duplication.");
				throw new CommonException("조직코드 중복입니다.", "ERR-100",null);
			}
			D(Thread.currentThread().getStackTrace(), "ORG Code is not a duplication.");
		}
		
		// syntax
		sql = "UPDATE ORG_HIERARCHY SET "
			+ "UPD_DATE=CONVERT( CHAR(8), CURRENT_TIMESTAMP, 112) "
			+ (name.length()==0 ? "":", name='"+name+"'")
			+ (lv1.length()==0 ? "":", lv1='"+lv1+"'")
			+ (lv2.length()==0 ? "":", lv2='"+lv2+"'")
			+ (lv3.length()==0 ? "":", lv3='"+lv3+"'")
			+ (del_yn.length()==0 ? "":", del_yn='"+del_yn+"'")
			+ (memo.length()==0 ? "":", memo='"+memo+"'")
			+ " WHERE ORG_IDX = "+idx
			;
		// execute
		try {
			Map preRow = executeQueryMap("SELECT * FROM ORG_HIERARCHY WHERE ORG_IDX = "+idx);// select data before change
			rtVal = executeUpdate(sql);//Change a data
			I(Thread.currentThread().getStackTrace(), "ORG Code Data Update Result Count : " + rtVal + "건.");
			
			// compare the data
			String pName = pnull(preRow, "NAME");
			// if LV 1 update,,,, 공통코드의 이름과 sync 
			if("00".equals(lv2) && "000".equals(lv3) && !"00".equals(lv1) && !pName.equals(name)) {
				int x = executeUpdate("UPDATE CODE_INSTANCE "
						+ " SET NAME='"+name+"' , UPD_DATE=CONVERT( CHAR(8), CURRENT_TIMESTAMP, 112) "
						+ " WHERE CODE='000001' AND CODE_INST='"+lv1+"' ");
				I(_logger, Thread.currentThread().getStackTrace(), " 공통코드 CODE='000001' 데이터 변경, 결과 1건 맞아 ? "+x+"건.");
			}
			
			// 조직의 LV1에 따라 원장도 수정 (조직명)
			String s = "";
			if ("01".equals(lv1)) {
				// 교구청 : 01 -> DEPARTMENT
				s = "UPDATE DEPARTMENT /* 교구청마스터 */ SET NAME='"+name+"' where org_idx="+idx;
			} else if ("02".equals(lv1)) {
				// 본당 : 02 -> CHURCH
				s = "UPDATE CHURCH /* 본당마스터 */ SET NAME='"+name+"' where org_idx="+idx;
			} else {
				// 그 외 : 03 ~ 09 -> ORGANIZATION
				s = "UPDATE ORGANIZATION /* 기타조직(기관/단체/수도회) 마스터 */ SET NAME='"+name+"' where org_idx="+idx;
			}
			try {
				int x = executeUpdate(s); // table sync
				I(_logger, Thread.currentThread().getStackTrace(), " 교구조직마스터(LV1:"+lv1+") 데이터 변경, 결과 1건 맞아 ? "+x+"건.");
			} catch (Exception e) {
				throw new CommonException("조직 마스터 정보가 동기화되지 못했습니다.["+e.getMessage()+"]", "");
			}

		} catch (SQLException e) { 
			throw new CommonException(e.getMessage(), "UPDATE ERROR", e);
		}
		return rtVal;
	}

    @Override
	public int deleteOrgHierarchy(Map params) throws CommonException {
    	D(_logger, Thread.currentThread().getStackTrace(), " called..." + params);
    	
		int rtVal = 0;
		// params
		String org_idx = params.get("idx").toString();
		// syntax
		String sql = "UPDATE ORG_HIERARCHY SET "
				+ "  UPD_DATE=CONVERT( CHAR(8), CURRENT_TIMESTAMP, 112) "
				+ ", DEL_DATE=CONVERT( CHAR(8), CURRENT_TIMESTAMP, 112) "
				+ ", DEL_YN='Y' "
				+ " WHERE ORG_IDX = '"+org_idx+"'"
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
	public int deleteRecoveryOrgHierarchy(Map params) throws CommonException {
    	D(_logger, Thread.currentThread().getStackTrace(), " called..." + params);
    	
		int rtVal = 0;
		// params
		String org_idx = params.get("idx").toString();
		// syntax
		String sql = "UPDATE ORG_HIERARCHY SET "
				+ " UPD_DATE=CONVERT( CHAR(8), CURRENT_TIMESTAMP, 112) "
				+ ", DEL_DATE='99991231' "
				+ ", DEL_YN='N' "
				+ " WHERE ORG_IDX = '"+org_idx+"'"
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
	public int selectOrgHierarchyTotalcount(Map params) throws CommonException {
    	D(_logger, Thread.currentThread().getStackTrace(), " called..." + params);
    	
		// param
		String name  = UtilString.pnull(params, "name", "");
		String memo  = UtilString.pnull(params, "memo", "");
		String delyn = UtilString.pnull(params, "del_yn", "");
		// sql
		String sql = "SELECT * FROM ORG_HIERARCHY";
		String where = " WHERE 1=1 "
				+ (name.length()==0 ? "" : " AND NAME like '%"+name+"%' ")
				+ (memo.length()==0 ? "" : " AND MEMO like '%"+memo+"%' ")
				+ (delyn.length()==0 ? "" : " AND DEL_YN = '"+delyn+"' ")
				+ " ORDER BY ORG_IDX ASC, ORDERNO ASC "
				;
		// exeucte
		int rtList = 0;
		try {
			rtList = executeCount(sql + where);
		} catch (SQLException e) { 
			throw new CommonException(e.getMessage(), "SELECT ERROR", e);
		}
		return rtList;
	}
    
    @Override
    public List<Map> selectOrgHierarchyGroupby(int level, String whereLv1InCaseLv1) throws CommonException {
    	D(_logger, Thread.currentThread().getStackTrace(), " called. PARAMS => level="+level+", whereLv1InCaseLv1="+whereLv1InCaseLv1);
    	
    	// sql
    	String sql = "";
    	if(level==1) {
    		// LV2='00' and LV3='000'
    		sql = " SELECT /* ORG LV-1 Query */ O.ORG_IDX, G.*, O.DEL_YN, O.NAME "
    				+ " FROM ( SELECT LV1, COUNT(*)-1 AS CHILD_COUNT "
    				+ " 		FROM ORG_HIERARCHY G "
    				+ " 		WHERE LV3='000' "
    				+ (whereLv1InCaseLv1!=null && whereLv1InCaseLv1.length()>0 ? " AND LV1='"+whereLv1InCaseLv1+"' ":"")
    				+ " 		GROUP BY LV1 ) G "
    				+ " LEFT OUTER JOIN ( SELECT ORG_IDX, LV1, NAME, DEL_YN FROM ORG_HIERARCHY WHERE LV2='00' ) O ON O.LV1=G.LV1 " 
    				+ " WHERE O.ORG_IDX IS NOT NULL  "
    				+ " ORDER BY G.LV1 "
    				;
    	} else if(level==8) { // call from front service
    		// LV2!='00'' and LV3='000'
			sql = "SELECT /* ORG LV-1&LV-2 Query */ O.ORG_IDX, O.NAME, G.LV1, G.LV2, G.CHILD_COUNT, O.DEL_YN "
					+ " FROM ("
					+ "  SELECT LV1, LV2, COUNT(*) CHILD_COUNT"
					+ "  FROM ORG_HIERARCHY"
					+ (whereLv1InCaseLv1!=null && whereLv1InCaseLv1.length()>0 ? " WHERE LV1='"+whereLv1InCaseLv1+"' ":"")
					+ "  GROUP BY LV1, LV2"
					+ " ) G"
					+ " LEFT OUTER JOIN ( "
						+ " SELECT ORG_IDX, LV1, LV2, NAME, DEL_YN "
						+ " FROM ORG_HIERARCHY "
						+ " WHERE LV3!='000'"
						+ (whereLv1InCaseLv1!=null && whereLv1InCaseLv1.length()>0 ? " AND LV1='"+whereLv1InCaseLv1+"' ":"")
						+ ") O"
					+ " ON O.LV1=G.LV1 AND  O.LV2=G.LV2 "
					+ " WHERE O.ORG_IDX IS NOT NULL AND O.DEL_YN = 'N' "
					+ " ORDER BY G.LV1, G.LV2 "
					;
    	} else {
    		// LV2!='00'' and LV3='000'
			sql = "SELECT /* ORG LV-2 Query */ O.ORG_IDX, O.NAME, G.LV1, G.LV2, G.CHILD_COUNT, O.DEL_YN "
					+ " FROM ("
					+ "  SELECT LV1, LV2, COUNT(*) CHILD_COUNT"
					+ "  FROM ORG_HIERARCHY"
					+ (whereLv1InCaseLv1!=null && whereLv1InCaseLv1.length()>0 ? " WHERE LV1='"+whereLv1InCaseLv1+"' ":"")
					+ "  GROUP BY LV1, LV2"
					+ " ) G"
					+ " LEFT OUTER JOIN ( "
						+ " SELECT ORG_IDX, LV1, LV2, NAME, DEL_YN "
						+ " FROM ORG_HIERARCHY "
						+ " WHERE LV3='000'"
						+ (whereLv1InCaseLv1!=null && whereLv1InCaseLv1.length()>0 ? " AND LV1='"+whereLv1InCaseLv1+"' ":"")
						+ ") O"
					+ " ON O.LV1=G.LV1 AND  O.LV2=G.LV2"
					+ " WHERE O.ORG_IDX IS NOT NULL  "
					+ " ORDER BY G.LV1, G.LV2 "
					;
    	}
		
		// exeucte
		List rtList = null;
		try {
			rtList = executeQueryList(sql);
		} catch (SQLException e) { 
			throw new CommonException(e.getMessage(), "SELECT ERROR", e);
		}
		D(_logger, Thread.currentThread().getStackTrace(), " rtList("+level+") : " + rtList);
		return rtList;
    }
    
    @Override
	public List<Map> selectOrgHierarchy(Map params) throws CommonException {
    	D(_logger, Thread.currentThread().getStackTrace(), " called..." + params);
		// param
    	String columns = pnull(params, "columns", "O.*, (LEN(O.LV1 + O.LV2 + O.LV3)/2) AS NODE_LEVEL");
    	String name    = pnull(params, "name", "");
		String memo    = pnull(params, "memo", "");
		String delyn   = pnull(params, "del_yn", "");
		String lv1     = pnull(params, "lv1", "");
		String lv2     = pnull(params, "lv2", "");
		String lv3     = pnull(params, "lv3", "");
		String search  = pnull(params, "searchText", "");
		String orgorderby = pnull(params, "ORG_HIERARCHY_ORDERBY", " NAME ASC, O.LV1 ASC, O.LV2 ASC, O.LV3 ASC, O.ORDERNO ASC ");
		
		// sql :: LV3!='000' 은 카테고리 이다.
		String sql = "SELECT " + columns
				+ " , C.CHURCH_IDX, D.DEPART_IDX "
				+ " FROM ORG_HIERARCHY O "
				+ " LEFT OUTER JOIN CHURCH C ON C.ORG_IDX = O.ORG_IDX"
				+ " LEFT OUTER JOIN DEPARTMENT D ON D.ORG_IDX = O.ORG_IDX"
				+ " WHERE 1=1 " 
				+ (lv1.length()==0 ? " AND O.LV3!='000' " : " AND O.LV1='"+lv1+"' ")
				+ (lv2.length()==0 ? "" : " AND O.LV2='"+lv2+"' ")
				+ (lv3.length()==0 ? "" : " AND O.LV3='"+lv3+"' ")
				+ (name.length()==0 ? "" : " AND O.NAME like '%"+name+"%' ")
				+ (memo.length()==0 ? "" : " AND O.MEMO like '%"+memo+"%' ")
				+ (delyn.length()==0 ? "" : " AND O.DEL_YN = '"+delyn+"' ")
				+ (search.length()==0 ? "" : " AND O.NAME LIKE '%"+search+"%' ")
				+ " ORDER BY " + orgorderby
				;
		
		// exeucte
		List rtList = null;
		try {
			rtList = executeQueryList(sql );
		} catch (SQLException e) { 
			throw new CommonException(e.getMessage(), "SELECT ERROR", e);
		}
		D(_logger, Thread.currentThread().getStackTrace(), " called..." + rtList);
		return rtList;
	}
	
    @Override
	public List<Map> selectOrgHierarchyPaging(Map params) throws CommonException {
    	D(_logger, Thread.currentThread().getStackTrace(), " called..." + params);
    	
		// param
		String lv1  = UtilString.pnull(params, "lv1", "");
		String name  = UtilString.pnull(params, "name", "");
		String delyn = UtilString.pnull(params, "del_yn", "");
		
		// paging
		int pageNo = UtilInt.pint(params.get("pageNo"), 1);
        int pageSize = UtilInt.pint(params.get("pageSize"), 20);
        int startRnum = (pageNo - 1) * pageSize + 1;
        int endRnum = pageNo * pageSize;
		
		// sql : 1개를 더 조회하는 이유는 다음 페이지가 있는지 여부를 바로 알 수 있게 하기 위해서..(ORG_IDX 기준 심플 페이징)
		String sql = "select TOP "+(pageSize+1)+" * from ORG_HIERARCHY";
		String where = " WHERE 1=1 "
				+ (name.length()==0 ? "" : " AND NAME like '%"+name+"%' ")
				+ (delyn.length()==0 ? "" : " AND DEL_YN = '"+delyn+"' ")
				+ (lv1.length()==0 ? "" : " AND LV1 = '"+lv1+"' ")
				+ " ORG_IDX > " + (startRnum - 1)
				+ " ORDER BY ORG_IDX ASC, ORDERNO ASC "
				;
		// exeucte
		List rtList = null;
		try {
			rtList = executeQueryList(sql + where);
		} catch (SQLException e) { 
			throw new CommonException(e.getMessage(), "SELECT ERROR", e);
		}
		return rtList;
	}
	
    @Override
	public int totalCount() throws CommonException  {
		int t = 0;
		try {
			t = super.executeCount();
		} catch (SQLException e) {
			throw new CommonException(e.getMessage(), "ERR-SQL001", e);
		}
		return t;
	}
	
    
}
