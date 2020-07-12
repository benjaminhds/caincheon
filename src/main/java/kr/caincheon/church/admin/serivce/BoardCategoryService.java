package kr.caincheon.church.admin.serivce;

import java.util.Map;

import kr.caincheon.church.common.base.CommonException;

public interface BoardCategoryService
{
	
	// 보드 코드 관리
    /** mode in {I,U,D}에 따라서 CUD를 처리하는 메서드 */
    public int iudBoard(Map params) throws CommonException, Exception;
    
    /** 코드를 리턴 */
    public void listBoard(Map params) throws CommonException, Exception;
   	
	
	
	// 보드 카테고리 코드 관리
    /** mode in {I,U,D}에 따라서 CUD를 처리하는 메서드 */
    public int iudBoardCategory(Map params) throws CommonException, Exception;

    /** 코드를 리턴 */
    public void listBoardCategory(Map params) throws CommonException, Exception;
   
    
}
