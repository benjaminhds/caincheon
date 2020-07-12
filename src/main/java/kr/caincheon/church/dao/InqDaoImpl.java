package kr.caincheon.church.dao;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import kr.caincheon.church.common.base.CommonDao;
import kr.caincheon.church.common.base.CommonDaoDTO;

/**
 * 나누고싶은 이야기(1:1 문의하기) dao
 * @author benjamin
 */
@Repository("inqDao")
public class InqDaoImpl extends CommonDao implements InqDao
{
	
	private final Logger _logger = Logger.getLogger(getClass());
	

	/*
	 * (non-Javadoc)
	 * @see kr.caincheon.church.dao.InqDao#inqList(java.util.Map, boolean)
	 */
	@Override
    public CommonDaoDTO inqList(Map _params, boolean isAdmin)
    {
		D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
		CommonDaoDTO dto = new CommonDaoDTO();
		setPaging(_params);
		
        String user_id    = getParameter(_params, "id");
        String ADM_YN     = getParameter(_params, "ADM_YN");
        String searchText = getParameter(_params, "searchText");
        String delete_yn  = getParameter(_params, "delete_yn");
        
        String query = "", whereStr = "", searchGubun = "";
        
        // 접근자 처리
        if(isAdmin==false) { // 회원이라면,
        	whereStr = " AND DELETE_YN='N' ";
        } else { // 관리자는..
	        if(delete_yn.length() > 0) {
	            whereStr = " AND DELETE_YN='"+ delete_yn.substring(0,1).toUpperCase() +"' ";
	        }
        }
        
        // 검색 조건 처리
        if(searchText != null && searchText.length() > 0) {
            searchGubun = getParameter(_params, "searchGubun");
            
            if(searchGubun.equals("title"))
            	whereStr += " AND TITLE LIKE '%"+searchText+"%'";
            else if(searchGubun.equals("name"))
            	whereStr += " AND NAME LIKE '%"+searchText+"%'";
            else if(searchGubun.equals("contents"))
            	whereStr += " AND CONTENTS LIKE '%"+searchText+"%'";
        }
        
        try {
        	// sql
        	query = " SELECT ROW_NUMBER() OVER(ORDER BY APPLY_DAY DESC) RNUM"
        			+ ", INQUIRE_NO, ID, NAME, DELETE_YN, TITLE, CONTENTS, REPLY"
            		+ ", CASE WHEN LEN(ISNULL(RTRIM(LTRIM(REPLYTYPE)),'0'))=0 THEN '1' ELSE ISNULL(RTRIM(LTRIM(REPLYTYPE)),'0') END AS REPLYTYPE"
            		+ ", CASE WHEN replyType = '3' THEN '답변완료' ELSE '답변대기'  END AS REPLYTYPE_TEXT"
            		+ ", CONVERT(char(10),  APPLY_DAY, 120) APPLY_DAY "
            		+ " FROM INQUIRE_APPLY "
            		+ " WHERE 1 = 1 "+whereStr ;
        	
            if(!ADM_YN.equals("Y"))
                query += " AND ID='"+user_id+"' ";
            
        	// count
        	dto.daoTotalCount = executeCount(query, true);
        	dto.setPaging(pageNo, pageSize);
        	
        	// list
        	query = "SELECT * FROM (" + query+" ) ROWS WHERE RNUM BETWEEN "+startRnum+" AND "+endRnum;
            dto.daoList = super.executeQueryList(query);
            
            
        } catch(Exception e) {
        	e.printStackTrace();
            E(_logger, Thread.currentThread().getStackTrace(), "SQL> "+ lastSQL );
        } finally {
        	free();
    	}
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Result:"+dto );
        
        return dto;
    }

	/*
	 * (non-Javadoc)
	 * @see kr.caincheon.church.dao.InqDao#inqContents(java.util.Map, boolean)
	 */
	@Override
    public Map inqContents(Map _params, boolean isAdmin)
    {
        Map result = null;
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        String query = "";
        String user_id = getParameter(_params, "user_id");
        String inquire_no = getParameter(_params, "inquire_no");
        try
        {
        	// select
            query = "SELECT A.INQUIRE_NO, A.ID, A.NAME, A.DELETE_YN, A.TITLE, A.CONTENTS, A.REPLY, B.ID AS EMAIL"
            		+ ", A.DELETE_YN"
            		+ ", CASE WHEN LEN(ISNULL(RTRIM(LTRIM(A.REPLYTYPE)),'0'))=0 THEN '1' ELSE ISNULL(RTRIM(LTRIM(A.REPLYTYPE)),'0') END AS REPLYTYPE"
            		+ ", CONVERT(CHAR(10), A.APPLY_DAY, 120) APPLY_DAY "
            		+ " FROM INQUIRE_APPLY A "
            		+ ", MEMBER B "
            		+ " WHERE INQUIRE_NO="+inquire_no+" AND A.ID=B.ID ";
            
            // change to sub-query
            query = "SELECT A.INQUIRE_NO, A.ID, A.NAME, A.DELETE_YN, A.TITLE, A.CONTENTS, A.REPLY"
            		+ ", (SELECT ID FROM MEMBER B WHERE B.ID=A.ID) AS EMAIL"
            		+ ", A.DELETE_YN"
            		+ ", CASE WHEN LEN(ISNULL(RTRIM(LTRIM(A.REPLYTYPE)),'0'))=0 THEN '1' ELSE ISNULL(RTRIM(LTRIM(A.REPLYTYPE)),'0') END AS REPLYTYPE"
            		+ ", CONVERT(CHAR(10), A.APPLY_DAY, 120) APPLY_DAY "
            		+ " FROM INQUIRE_APPLY A "
            		+ " WHERE INQUIRE_NO="+inquire_no;
            
            result = super.executeQueryMap(query);
            D(_logger, Thread.currentThread().getStackTrace(), "DAO Query Data (bjm):"+result );
            
            // auto-status update
            int u = executeUpdate("UPDATE INQUIRE_APPLY SET REPLYTYPE='2' WHERE INQUIRE_NO="+inquire_no+" AND REPLYTYPE='1' ");
            D(_logger, Thread.currentThread().getStackTrace(), "REPLYTYPE='2' is Updated Result:"+(u==1 ? "true":"false("+u+" 1건 이어야 함.)")+"." );
        }
        catch(Exception e)
        {
        	e.printStackTrace();
            E(_logger, Thread.currentThread().getStackTrace(), "SQL> "+ lastSQL );
        } finally {
        	free();
    	}
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Result:"+result );
        return result;
    }

	/*
	 * (non-Javadoc)
	 * @see kr.caincheon.church.dao.InqDao#inqInsert(java.util.Map, boolean)
	 */
	@Override
    public boolean inqInsert(Map _params, boolean isAdmin)
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
        boolean bReturn=true;
        Map memberInfo = getMember(_params);
        if(memberInfo == null) return false;
        
        String user_id = getParameter(_params, "user_id");
        String title = getParameter(_params, "title");
        String contents = getParameter(_params, "contents");
        String memName = pnull(memberInfo, "NAME");
        
        int i = 0;
        try {
            String query = "INSERT INTO INQUIRE_APPLY "
            		+ " (ID, NAME, REPLYTYPE, TITLE, CONTENTS, DELETE_YN, APPLY_DAY, UPDATEDT) "
            		+ " VALUES (?, ?, '1', ?, ?, 'N', getdate(), getdate())";
            LinkedHashMap<String,Object> lmap = getLinkedHashMap(user_id, memName, title, contents);
            i = executeUpdatePreparedStatement(query, lmap);
            
        } catch(Exception e) {
        	e.printStackTrace();
            E(_logger, Thread.currentThread().getStackTrace(), e.getMessage() );
            bReturn = false;
        } finally {
        	free();
    	}
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Result:"+(bReturn && i > 0) );
        
        return bReturn && i > 0;
    }

	/*
	 * (non-Javadoc)
	 * @see kr.caincheon.church.dao.InqDao#inqModify(java.util.Map, boolean)
	 */
	@Override
    public boolean inqModify(Map _params, boolean isAdmin)
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
        boolean bReturn=true;
        
        String title      = getParameter(_params, "title");
        String contents   = getParameter(_params, "contents");
        String inquire_no = getParameter(_params, "inquire_no");
        String replyType  = getParameter(_params, "replyType");
        String reply      = getParameter(_params, "reply");
        
        int i = 0;
        try
        {
        	String query = "UPDATE INQUIRE_APPLY  SET "
            		+ " TITLE=?, CONTENTS=?,  REPLYTYPE=?, REPLY=?, UPDATEDT=getdate() "
            		+ " WHERE INQUIRE_NO = ?";
            
            LinkedHashMap<String,Object> lmap = getLinkedHashMap(title, contents, replyType, reply, inquire_no);
            i = executeUpdatePreparedStatement(query, lmap);
            
        } catch(Exception e) {
        	e.printStackTrace();
            E(_logger, Thread.currentThread().getStackTrace(), "SQL> "+ lastSQL );
            bReturn = false;
        } finally {
        	free();
    	}
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Result:"+(bReturn && i > 0) );
        
        return bReturn && i > 0;
    }

	/*
	 * (non-Javadoc)
	 * @see kr.caincheon.church.dao.InqDao#inqDelete(java.util.Map, boolean)
	 */
	@Override
    public boolean inqDelete(Map _params, boolean isAdmin)
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
        String inquire_no = getParameter(_params, "inquire_no");
        
        int i = 0;
        
        try {
            String query = "DELETE FROM INQUIRE_APPLY WHERE INQUIRE_NO = " + inquire_no;
            if(isAdmin) {
            	query = "UPDATE INQUIRE_APPLY "
            			+ " SET DELETE_YN='Y' "
            			+ " WHERE INQUIRE_NO = " + inquire_no;
            }
            i = executeUpdate(query);
            
        } catch(Exception e) {
        	e.printStackTrace();
            E(_logger, Thread.currentThread().getStackTrace(), "SQL> "+ lastSQL );
        } finally {
        	
        	free();
    	}
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Result:"+(i == 0) );
        
        return i == 0;
    }

	/*
	 * (non-Javadoc)
	 * @see kr.caincheon.church.dao.InqDao#inqAdmRecovery(String)
	 */
	@Override
    public boolean inqAdmRecovery(String inquire_no)
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:{inquire_no:"+inquire_no+"}]" );
        
        int i = 0;
        
        try {
            String query = "UPDATE INQUIRE_APPLY "
        			+ " SET DELETE_YN='N' "
        			+ " WHERE INQUIRE_NO = " + inquire_no;
            i = executeUpdate(query);
            
        } catch(Exception e) {
        	e.printStackTrace();
            E(_logger, Thread.currentThread().getStackTrace(), "SQL> "+ lastSQL );
        } finally {
        	free();
    	}
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Result:"+(i == 0) );
        
        return i == 0;
    }

	/*
	 * (non-Javadoc)
	 * @see kr.caincheon.church.dao.InqDao#getMember(java.util.Map)
	 */
	@Override
    public Map getMember(Map _params)
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
        Map result;
        
        String user_id;
        result = new HashMap();
        
        user_id = getParameter(_params, "user_id");
        try {
            String query = "SELECT NAME, BAPTISMALNAME, MEMBERTYPE, CHURCH_IDX, ID AS EMAIL "
            		+ " FROM MEMBER WHERE ID='"+user_id+"' ";
            result = super.executeQueryMap(query);
        } catch(Exception e) {
            e.printStackTrace();
            E(_logger, Thread.currentThread().getStackTrace(), "SQL> "+ lastSQL );
        } finally {
        	free();
    	}
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Result:"+result );
        
        return result;
    }

	
	/**
	 * 탈퇴시 계정의 모든 글을 삭제플래그(Y) 처리
	 * (non-Javadoc)
	 * @see kr.caincheon.church.dao.InqDao#inqMemberLeave(java.lang.String, java.lang.String)
	 */
	@Override
    public int inqMemberLeave(String memId, String renameId) {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:{memberId:"+memId+", renameId:"+renameId+"}]" );
    	int i = 0;
        try {
            String query = "UPDATE INQUIRE_APPLY "
        			+ " SET DELETE_YN='Y'"
        			+ ", ID='"+renameId+"'" 
        			+ ", UPDATEDT=getdate() "
        			+ " WHERE DELETE_YN='N' AND ID='" + memId + "'"
        			+ " /* 탈퇴(나누는이야기 : ID변경,DEL Flag ON) */ "
        			;
            i = executeUpdate(query);
            
        } catch(Exception e) {
        	e.printStackTrace();
            E(_logger, Thread.currentThread().getStackTrace(), "SQL> "+ lastSQL );
        } finally {
        	free();
    	}
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Member Leavered 1:1 Records Del Flag Updated:"+i );
        
        return i;
    }
    
}
