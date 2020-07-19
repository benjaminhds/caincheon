package kr.caincheon.church.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import kr.caincheon.church.common.base.CommonDao;
import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.base.CommonException;

/**
 * 회원 가입/변경/탈퇴 등 관리하는 DAO
 * @author benjamin
 */
@Repository("memberDao")
public class MemberDaoImpl extends CommonDao
    implements MemberDao
{

	private final Logger _logger = Logger.getLogger(getClass());

	/**
	 * 회원정보 조회
	 * (non-Javadoc)
	 * @see kr.caincheon.church.dao.MemberDao#selectMemberInfo(java.util.Map)
	 */
	@Override
	public Map selectMemberInfo(Map _params)
        throws CommonException
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
        String sql;
        Map result = null;
        sql = "SELECT A.* /*교구게시판 2, 공동체소식 4::ORG_IDX컬럼으로 매핑되어야 정상임, 현업요구:이메일로 매핑해 주세요.*/ "
        	+ ",(SELECT NAME FROM CHURCH WHERE CHURCH_IDX = A.CHURCH_IDX) AS CHRUCH_NAME "
        	//+ ",(SELECT NAME FROM ORG_HIERARCHY WHERE ORG_IDX = A.ORG_IDX) AS ORG_NAME "
        	+ ", CASE WHEN GROUPGUBUN IN ('2','4') THEN D.DEPART_IDX ELSE NULL END AS DEPART_IDX "
        	+ ", CASE WHEN GROUPGUBUN IN ('2','4') THEN D.NAME ELSE NULL END AS DEPART_NAME "
        	+ " FROM MEMBER A "
        	+ " LEFT OUTER JOIN DEPARTMENT D ON D.MAIL = A.ID "
        	+ " WHERE 1=1 ";
        
        if(_params.get("id") != null)
            sql += " AND A.ID='"+pnull(_params, "id")+"' ";
        if(_params.get("name") != null)
        	sql += " AND A.NAME='"+pnull(_params, "name")+"' ";
        if(_params.get("baptismalname") != null)
        	sql += " AND A.BAPTISMALNAME='"+pnull(_params, "baptismalname")+"' ";
        
        try {
        	result = super.executeQueryMap(sql);
        } catch(SQLException e) {
            throw new CommonException("조건에 맞는 데이터가 존재하지 않습니다. 다시 입력해 주세요.", e, "ERR-D001");
        } finally {
        	free();
    	}
        
        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+result );
        
        return result;
    }

	/*
	 * 회원 정보 얻어오기
	 */
    private Map selectMemberInfo(String id_is_a_email, boolean isThrowNotMatched)
        throws CommonException
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params {id:"+id_is_a_email+"}]" );
    	
    	Map result = null;
    	
        Map m = new HashMap();
        m.put("id", id_is_a_email);
        
        if(isThrowNotMatched)
            result = selectMemberInfo(m);
        else
            try {
                result = selectMemberInfo(m);
            } catch(CommonException e) { e.printStackTrace(); }
        
        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+result );
        
        return result;
    }

    /*
     * front::로그인하기
     * (non-Javadoc)
     * @see kr.caincheon.church.dao.MemberDao#login(java.util.Map)
     */
    @Override
	public Map login(Map _params)
        throws CommonException
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
    	
    	String userId = pnull(_params,"id");
        String sql = "";
        Map result = null;
        try {
            result = selectMemberInfo(userId, true);
        } catch(CommonException e) {
            throw e;
        } catch(Exception e) {
            throw new CommonException(e.getMessage(), e);
        }
        
        String p_attr = pnull(_params.get("pw"));
        String r_attr = pnull(result.get("PASSWORD"));
        
        if(result==null || result.size() == 0)
            throw new CommonException("ID[id="+userId+"]가 존재하지 않습니다.", "ERR-D001");
        
        if(!p_attr.equals(r_attr))
            throw new CommonException("암호가 맞지 않습니다. 다시 입력해 주세요.", "ERR-D002", result);
        
        if(!"Y".equals(pnull(result.get("MAILCONFIRMYN"))))
            throw new CommonException("메일 인증을 받지 않은 사용자로 메일 인증을 먼저 받으셔야 로그인이  가능합니다.", "ERR-D003", result);
        
        if(!"N".equals(pnull(result.get("OUTYN"))))
            throw new CommonException("이미 탈퇴한 사용자입니다.", "ERR-D004", result);
        
        if(!"N".equals(pnull(result.get("DORMANTYN"))))
            throw new CommonException("휴면 처리된 계정입니다. 휴면 해지 신청을 해 주세요.", "ERR-D005", result);
        
        // 
        try {
        	sql = "UPDATE MEMBER SET LASTLOGINDT=GETDATE() WHERE ID='"+userId+"' ";
            executeUpdate(sql);
        } catch(SQLException e) {
//            _logger.error("SQL \uC624\uB958:"+e.getMessage());
//            _logger.error("SQL \uC624\uB958:"+sql);
            throw new CommonException(e.getMessage(), e, "ERR-D010", result);
        }
        
        // 부서관리자여부 조회
        sql = "SELECT D.ORG_IDX FROM DEPARTMENT D "
        		+ " LEFT OUTER JOIN ORG_HIERARCHY H ON H.ORG_IDX = D.ORG_IDX "
        		+ " WHERE MAIL='"+userId+"'";
        try {
        	Map<String, Object> row = executeQueryMap(sql);
        	String org_idx = pnull(row, "ORG_IDX", "");
        	if(org_idx.length() > 0) { 
        		result.put("ORG_IDX", org_idx);
        	}
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+result );
        
        return result;
    }

    /*
     * front::회원가입 단계에서 첫단계 기본 정보 입력
     * (non-Javadoc)
     * @see kr.caincheon.church.dao.MemberDao#join(java.util.Map)
     */
    @Override
	public boolean join(Map _params)
        throws CommonException
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
    	String sql = "";
        String userId = pnull(_params,"id");
        Map row = selectMemberInfo(userId, false);
        if(row != null && row.size() > 0)
            throw new CommonException("이미 사용중인 이메일 주소입니다.", "");
        
        String festival = pnull(_params.get("festivalM")) + pnull(_params.get("festivalD"));
        String tel      = pnull(_params.get("tel1")) + pnull(_params.get("tel2")) + pnull(_params.get("tel3"));
        String org_idx  = pnull(_params.get("org_idx"));
        
        // sns로그인으로 가입시,,,,email 이 완료로 가늠한다.
        String logintp = pnull(_params, "login_type", "1");
        String mailauthyn = ("2".equals(logintp) ? "Y":"N");
        
        //
        int rtCnt = 0;
        sql = "INSERT INTO MEMBER (ID, LOGINTYPE, PASSWORD, NAME, BAPTISMALNAME, MAILCONFIRMYN, MEMBERTYPE, CHURCH_IDX"
        		+ ", FESTIVALDAY, TEL, OUTYN, DORMANTYN, LASTLOGINDT, UPDATEDT"
//        		+ (org_idx.length()==0 ? "":", ORG_IDX")
        		+ ") VALUES ('"+pnull(_params,"id")+"', '"+logintp+"', '"+pnull(_params,"password")+"'"
        		+", '"+pnull(_params,"name")+"', '"+pnull(_params,"baptismal_name")+"', '"+mailauthyn+"', '"+pnull(_params,"member_type")+"'"
        		+", "+pnull(_params,"church_idx","null")+", '"+festival+"', '"+tel+"', 'N', 'N', null, null"
//        		+ (org_idx.length()==0 ? ", null":", "+org_idx+"")
        		+") ";
        
        try {
            rtCnt = executeUpdate(sql);
        } catch(SQLException e) {
            _logger.error("SQL \uC624\uB958:"+sql);
            _logger.error("SQL \uC624\uB958:"+e.getMessage());
            throw new CommonException(e.getMessage(), e);
        }
        return rtCnt == 1;
    }

    /* 
     * 회원가입 실패 : 실패시 아이디를 delete한다.
     * (non-Javadoc)
     * @see kr.caincheon.church.dao.MemberDao#joinCancel(java.lang.String)
     */
	@Override
	public int joinCancel(String delId) throws CommonException {
        try {
			return executeUpdate("DELETE FROM MEMBER WHERE WHERE ID='"+delId+"' ");
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return -1;
	}

    /*
     * front::회원가입 단계에서 마지막 이메일 검증 단계 처리
     * (non-Javadoc)
     * @see kr.caincheon.church.dao.MemberDao#joinFinish(java.util.Map)
     */
    @Override
	public boolean joinFinish(Map _params)
        throws CommonException
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
        int rtCnt = 0;
        String sql = "UPDATE MEMBER SET MAILCONFIRMYN='Y', UPDATEDT=GETDATE() WHERE ID='"+pnull(_params.get("id"))+"' ";
        try {
            rtCnt = executeUpdate(sql);
        } catch(SQLException e) {
            _logger.error("SQL \uC624\uB958:"+e.getMessage());
            _logger.error("SQL \uC624\uB958:"+sql);
            throw new CommonException(e.getMessage(), e);
        }
        return rtCnt == 1;
    }

    /*
     * front::회원휴면계정 처리
     * (non-Javadoc)
     * @see kr.caincheon.church.dao.MemberDao#memberDormantClearRequest(java.util.Map)
     */
    @Override
	public Map memberDormantClearRequest(Map _params)
        throws CommonException
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
    	//
        Map result = selectMemberInfo(pnull(_params,"id"), true);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+result );
        
        return result;
    }

    /*
     * front::회원휴면계정 복구처리
     * (non-Javadoc)
     * @see kr.caincheon.church.dao.MemberDao#memberDormantClearConfirm(java.util.Map)
     */
    @Override
	public boolean memberDormantClearConfirm(Map _params)
        throws CommonException
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
        int rtCnt = 0;
        // 7일간 유효하도록, 7일이후 로그인을 안 하면, 다시 휴면되도록
        String sql = "UPDATE MEMBER SET DORMANTYN='N', UPDATEDT=GETDATE(), lastLoginDT=DATEADD(DAY, -358, GETDATE())"
        		   + " WHERE ID='"+pnull(_params.get("id"))+"' ";
        try {
            rtCnt = executeUpdate(sql);
        }
        catch(SQLException e)
        {
            _logger.error("SQL \uC624\uB958:"+e.getMessage());
            _logger.error("SQL \uC624\uB958:"+sql);
            throw new CommonException(e.getMessage(), e);
        }
        return rtCnt == 1;
    }

    /*
     * front::회원 아이디찾기
     * (non-Javadoc)
     * @see kr.caincheon.church.dao.MemberDao#memberFindId(java.util.Map)
     */
    @Override
	public Map memberFindId(Map _params)
        throws CommonException
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
        Map result = selectMemberInfo(_params);
        if(result == null || result.size() < 1)
            throw new CommonException("일치하는 사용자가 없습니다.", "");
        
        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+result );
        
        return result;
    }

    /*
     * front::회원암호찾기
     * (non-Javadoc)
     * @see kr.caincheon.church.dao.MemberDao#memberFindPwd(java.util.Map)
     */
    @Override
	public Map memberFindPwd(Map _params)
        throws CommonException
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
        Map result = selectMemberInfo(_params);
        if(result == null || result.size() < 1)
            throw new CommonException("ID가 일치하는 사용자가 없습니다.", "");
        
        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+result );
        return result;
    }

    /*
     * front::회원정보 변경
     * (non-Javadoc)
     * @see kr.caincheon.church.dao.MemberDao#memberModifyInformation(java.util.Map)
     */
    @Override
	public Map memberModifyInformation(Map _params)
        throws CommonException
    {//pnull(_params,"
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
        int rtCnt = 0;
        String sql = "";
        Map result = new HashMap();
        try
        {
            result = selectMemberInfo(pnull(_params,"id"), true);
            if(result == null || result.size() < 1)
                throw new CommonException("ID가 일치하는 사용자가 없습니다.", "ERR-D011");
            
            String festival = pnull(_params, "festivalM")+pnull(_params, "festivalD");
            String tel      = pnull(_params, "tel1")+pnull(_params, "tel2")+pnull(_params, "tel3");
            String org_idx  = pnull(_params, "org_idx");
            String church_idx   = pnull(_params, "church_idx");
            String incheonGyugo = pnull(_params, "is_incheon_gyugo");
            
            sql = "UPDATE MEMBER SET "
            		+ " NAME='"+pnull(_params,"name")+"' "
            		+ ", PASSWORD='"+pnull(_params,"pw")+"' "
            		+ ", MEMBERTYPE='"+pnull(_params,"member_type")+"' "
            		+ ", BAPTISMALNAME='"+pnull(_params,"baptismal_name")+"' "
            		+ (church_idx.length()  == 0 ? "":", CHURCH_IDX="+church_idx)
            		+ (org_idx.length()     == 0 ? "":", ORG_IDX="+org_idx)
            		+ (festival.length()    == 0 ? "":", FESTIVALDAY='"+festival+"' ")
            		+ (tel.length()         == 0 ? "":", TEL='"+tel+"' ")
            		+ (incheonGyugo.length()== 0 ? "":", IS_INCHEON_GYUGO='"+incheonGyugo+"'")
            		+ ", UPDATEDT=GETDATE() "
            		+ " WHERE ID='"+pnull(_params,"id")+"' ";
            
            rtCnt = executeUpdate(sql);
            
            result = selectMemberInfo(pnull(_params,"id"), true);
        }
        catch(SQLException e)
        {
            _logger.error("SQL \uC624\uB958:"+e.getMessage());
            _logger.error("SQL \uC624\uB958:"+sql);
            throw new CommonException(e.getMessage(), "ERR-D013", e);
        }
        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+result );
        
        return result;
    }
    
    /**
     * 회원이 나의정보에서 탈퇴버튼을 누를때 처리
     * - 회원 탈퇴 : 탈퇴처리(Flag is on) 및 나누고싶은이야기 탈퇴처리(Flag is on)
     * (non-Javadoc)
     * @see kr.caincheon.church.dao.MemberDao#memberLeave(java.util.Map)
     */
    @Override
	public CommonDaoDTO memberLeave(Map _params) throws CommonException
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
    	CommonDaoDTO dto = new CommonDaoDTO();
        
        String sql = "";
        
        try {
        	// 변경ID가 중복되는지..
        	String id = pnull(_params, "id");
        	int i = 0;
        	while(executeCount("select count(1) from MEMBER where id='XU"+ i +"_"+id+"' ", false) > 0) {
        		i++;
        	}
        	
        	// 중복이 안되는 ID로 변경
        	String renameId = "XU"+ i +"_"+id;
        	sql = "UPDATE MEMBER SET ID='"+ renameId +"', OUTYN='Y', UPDATEDT=GETDATE(), OUT_DATE=GETDATE() WHERE ID='"+id+"' ";
            int rtCnt = executeUpdate(sql);
            
            dto.isBool = rtCnt == 1 ;
            dto.otherData = renameId;
            
        } catch(SQLException e) {
            _logger.error("SQL \uC624\uB958:"+e.getMessage());
            _logger.error("SQL \uC624\uB958:"+sql);
            throw new CommonException(e.getMessage(), e);
        }
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Result DTO:"+dto );
        
        return dto;
    }

    
}
