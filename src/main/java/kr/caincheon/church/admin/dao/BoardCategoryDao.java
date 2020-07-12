package kr.caincheon.church.admin.dao;

import java.util.Map;

import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.base.CommonException;

/**
 * 공통코드(코드테이블과 & 코드인스턴스테이블)를 관리하는 DAO I/F
 * - method 가 inquireXXXXX로 시작되는 메서드는 모두 front 에서 사용하기 위한 메서드이고, 그 외에는 관리자에서 호출되는 메서드로 구분됨.
 * @author benjamin
 */
public interface BoardCategoryDao
{
	
	// 게시판 관리
    public int insertBoard(Map params) throws CommonException, Exception;
    
    public int updateBoard(Map params) throws CommonException, Exception;
    
    public int deleteBoard(Map params) throws CommonException, Exception;
    
    /** 코드를 리턴 */
    public CommonDaoDTO selectBoard(Map params) throws CommonException, Exception;
   	
    
    
	// 게시판 카테고리 관리 
	public int insertBoardCategory(Map params) throws CommonException, Exception;
	
	public int updateBoardCategory(Map params) throws CommonException, Exception;
	
	public int deleteBoardCategory(Map params) throws CommonException, Exception;
	
	public CommonDaoDTO selectBoardCategory(Map params) throws CommonException, Exception;

}
