package kr.caincheon.church.admin.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.base.CommonException;

/**
 * 공통코드(코드테이블과 & 코드인스턴스테이블)를 관리하는 DAO I/F
 * - method 가 inquireXXXXX로 시작되는 메서드는 모두 front 에서 사용하기 위한 메서드이고, 그 외에는 관리자에서 호출되는 메서드로 구분됨.
 * @author benjamin
 */
public interface CodeDao
{

	// management a code_master 
	public int insertCode(Map params) throws CommonException, Exception;
	public int updateCode(Map params) throws CommonException, Exception;
	public int deleteCode(Map params) throws CommonException, Exception;
	public CommonDaoDTO selectCodes(Map params) throws CommonException, Exception;
//	/* 마지막 코드번호를 리턴 */
//	public String selectLastCode() throws CommonException, Exception;
//	/* 마지막 다음 코드번호를 리턴 */
//	public String selectLastNextCode() throws CommonException, Exception;
	
	
	
    // management a code_instance
	public int insertCodeInstance(Map params) throws CommonException, Exception;
	public int updateCodeInstance(Map params) throws CommonException, Exception;
	public int deleteCodeInstance(Map params) throws CommonException, Exception;
	public CommonDaoDTO selectCodeInstances(Map params) throws CommonException, Exception;
	/* 마지막 코드번호를 리턴 */
	public String selectLastCodeInst() throws CommonException, Exception;
	/* 마지막 다음 코드번호를 리턴 */
	public String selectLastNextInstCode() throws CommonException, Exception;
	
    // select for front service & etc
	public List<Map> inquireCodes(Map params) throws CommonException, Exception;
	public List<Map> inquireCodeInstance(String[] params) throws CommonException, Exception;
	
	// 마지막 실행된 SQL을 기반으로 COUNT 쿼리를 실행
	//public int executeCount() throws SQLException, Exception;
	
    /** 코드인스턴스의 메모값을 조회 */
    public List<Map> selectedAreaCodeMapPolygonPath(Map params) throws CommonException, Exception;
}
