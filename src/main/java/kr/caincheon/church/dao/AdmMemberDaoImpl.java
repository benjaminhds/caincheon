// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AdmMemberDaoImpl.java

package kr.caincheon.church.dao;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import kr.caincheon.church.common.base.CommonDao;
import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.base.CommonException;

// Referenced classes of package kr.caincheon.church.dao:
//            AdmMemberDao

/**
 * - 이것을 사용하는 것은 수퍼관리자(admin)의 login시 사용하는 ADMMEMBER를 사용하는 기능들은 모두 제거되어야 함. (date : 2017.10.10)
 * TODO MEMBER를 사용하는 기능은 검토가 필요 (date : 2017.10.10)
 * 
 * @author benjamin
 */
@Repository("admMemberDao")
public class AdmMemberDaoImpl extends CommonDao
    implements AdmMemberDao
{

	private final Logger _logger = Logger.getLogger(getClass());
    
	/*
	 * 
	 */
    private Map selectAdmmemberInfo(Map _params)
        throws CommonException
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
        Map result=null;
        String sql = "SELECT * FROM ADMMEMBER WHERE 1=1  AND ADM_ID='"+_params.get("id")+"' ";
        
        try {
            result = super.executeQueryMap(sql);
        } catch(Exception e) {
            _logger.error("["+Thread.currentThread().getStackTrace()[1].getLineNumber()+"]ERROR SQL:"+sql, e);
            throw new CommonException("조건에 맞는 데이터가 존재하지 않습니다. 다시 입력해 주세요.", "ERR-D001");
        } finally {
        	free();
        }
        
        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+result );
        
        return result;
    }

    /*
     * 
     */
    private Map selectAdmmemberInfo(String id_is_a_email)
        throws CommonException
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:{id_is_a_email:"+id_is_a_email+"}]" );
    	
        Map result = selectAdmmemberInfo(id_is_a_email, true);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+result );
        
        return result;
    }

    /*
     * 
     */
    private Map selectAdmmemberInfo(String id_is_a_email, boolean isThrowNotMatched)
        throws CommonException
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params {id_is_a_email:"+id_is_a_email+", isThrowNotMatched:"+isThrowNotMatched+"}]" );
    	
    	Map m = new HashMap();
        m.put("id", id_is_a_email);
        
        Map result = null;
        
        if(isThrowNotMatched)
            result = selectAdmmemberInfo(m);
        else
            try {
                result = selectAdmmemberInfo(m);
            } catch(CommonException e) { e.printStackTrace(); }
        
        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+result );
        
        return result;
    }

    @Override
    public Map admMemberLogin(Map _params)
        throws CommonException
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
        String sql = "";
        Map result = null;
        try {
            result = selectAdmmemberInfo(pnull(_params.get("pid")));
        } catch(Exception e1) {
            throw new CommonException("ID가 존재하지 않습니다.", "ERR-D000");
        }
        
        String p_attr = pnull(_params.get("ppw"));
        String r_attr = pnull(result.get("ADM_PASSWORD"));
        
        if(!p_attr.equals(r_attr))
            throw new CommonException("암호가 맞지 않습니다. 다시 입력해 주세요.", "ERR-D002", result);
        
        // 로그인 시간 기록
        try {
        	sql = "UPDATE ADMMEMBER SET LASTLOGINDT=GETDATE() WHERE ADM_ID='"+pnull(_params.get("pid"))+"' ";
            executeUpdate(sql);
        }
        catch(SQLException e)
        {
            throw new CommonException(e.getMessage(), e, "ERR-D010", result);
        } finally {
        	free();
        }
        
        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+result );
        
        return result;
    }

    @Override
    public String admMemberSelectId(Map _params)
        throws CommonException
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
        String result="";
        String sql = "SELECT ADM_ID FROM ADMMEMBER WHERE 1=1  AND ADM_ID='"+_params.get("adm_id")+"' ";
        
        try
        {
            Map m = super.executeQueryMap(sql);
            result = pnull(m, "ADM_ID");
            m.clear();
            m = null;
        }
        catch(SQLException e)
        {
            throw new CommonException("회원 아이디 검색 오류.", "ERR-D001");
        } finally {
            free();
        }
        
        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+result );
        
        return result;
    }

    @Override
    public List admMemberDepartCodeList(Map _params)
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
        String query;
        List result = null;
        
        query = "";
        result = null;
        
        try {
            query = "SELECT ROW_NUMBER() over(order by b.insertDT) as RNUM, b.depart_code, c.name    "
            		+ " from ADMMEMBER a    "
            		+ " left outer join admmember_DEPART b on a.adm_id = b.adm_id       "
            		+ " left outer join DEPARTMENT c on b.depart_idx = c.depart_code "
            		+ " where a.adm_id= '"+pnull(_params.get("adm_id"))+"'" ;
            result = super.executeQueryList(query);
        } catch(Exception e) {
            _logger.error("ERROR SQL:"+query, e);
        } finally {
            free();
        }
        
        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+result );
        
        return result;
    }

    @Override
    public Map admMemberView(Map _params)
	    throws CommonException
	{
		D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
		
	    Map result = null;
	    String adm_id = pnull(_params, "adm_id");
	    String sql = "SELECT A.*, (SELECT TOP 1 DEPART_CODE FROM ADMMEMBER_DEPART WHERE adm_id=A.adm_id) AS DEPART_CODE "
	    		+ " FROM ADMMEMBER A  WHERE 1=1 ";
	    
	    if(adm_id.length() > 0)
	        sql = sql + " AND A.ADM_ID='"+adm_id+"' ";
	    
	    try
	    {
	        result = super.executeQueryMap(sql);
	    }
	    catch(SQLException e)
	    {
	        throw new CommonException("조건에 맞는 데이터가 존재하지 않습니다. 다시 입력해 주세요.", "ERR-D001");
	    } finally {
	    	free();
	    }
	    
	    D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+result );
	    
	    return result;
	}

    @Override
    public boolean admMemberInsert(Map _params)
        throws CommonException
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
        int rn = 0;
        PreparedStatement preparedStatement = null;
        boolean bReturn = true;
        
        String query = "";
        
        Map dupAdminInfo = admMemberView(_params);
        if(!pnull(dupAdminInfo.get("ADM_ID")).equals(""))
            throw new CommonException("이미 등록되어 있는 아이디입니다.", "ERR-D001");
        try
        {
            query = "INSERT INTO ADMMEMBER  (adm_id,adm_group,adm_name,adm_password,adm_useYN,  insertDT,userid )  VALUES (?, ?, ?, ?, ?,  GETDATE(), ?)";
            preparedStatement = getPreparedStatement(query);
            preparedStatement.setString(1, pnull(_params.get("adm_id")));
            preparedStatement.setString(2, pnull(_params.get("adm_group"), "1"));
            preparedStatement.setString(3, pnull(_params.get("adm_name")));
            preparedStatement.setString(4, pnull(_params.get("password")));
            preparedStatement.setString(5, pnull(_params.get("adm_useYN"), "Y"));
            preparedStatement.setString(6, pnull(_params.get("userid")));
            rn = preparedStatement.executeUpdate();
            

        } catch(Exception e) {
            _logger.error("["+Thread.currentThread().getStackTrace()[1].getLineNumber()+"]ERROR SQL:"+query, e);
        } finally {
        	if(preparedStatement != null) try { preparedStatement.close(); } catch(SQLException e) { e.printStackTrace(); }
            free();
        }
        
        String depart_code = "";
        int departCodeCnt = Integer.parseInt(pnull(_params.get("departCodeCnt"), "0"));
        int departCodeCommitCnt = 0;
        try
        {
        	if(departCodeCnt <= 0) throw new CommonException("등록된 부서가 없습니다.", "ERR-900");
        	
            query = "INSERT INTO ADMMEMBER_DEPART  (adm_id, depart_code, depart_mainYN, insertDT, userid)  values(?, ?, ?, getdate(), ?) ";
            preparedStatement = getPreparedStatement(query);
            for(int i = 0; i < departCodeCnt; i++)
            {
                if(pnull(_params.get("departCode3"+i + 1).toString()).equals(""))
                {
                    if(pnull(_params.get("departCode2"+i + 1).toString()).equals(""))
                    {
                        if(pnull(_params.get("departCode1"+i + 1).toString()).equals(""))
                            depart_code = "";
                        else
                            depart_code = pnull(_params.get("departCode1"+i + 1).toString());
                    } else {
                        depart_code = pnull(_params.get("departCode2"+i + 1).toString());
                    }
                } else {
                    depart_code = pnull(_params.get("departCode3"+i + 1).toString());
                }
                preparedStatement.setString(1, pnull(_params.get("adm_id")));
                preparedStatement.setString(2, depart_code);
                preparedStatement.setString(3, pnull(_params.get("depart_mainYN"), "N"));
                preparedStatement.setString(4, pnull(_params.get("userid")));
                rn += preparedStatement.executeUpdate();
            }

        } catch(Exception e) {
            _logger.error("["+Thread.currentThread().getStackTrace()[1].getLineNumber()+"]ERROR SQL:"+query, e);
        } finally {
        	if(preparedStatement != null) try { preparedStatement.close(); } catch(SQLException e) { e.printStackTrace(); }
            free();
        }
        boolean result = rn>=1;
        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+result );
        return result;
    }

    @Override
    public boolean admMemberModify(Map _params)
        throws CommonException
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
        int rn = 0;
        boolean bReturn = true;
        String query="";

        String sql = "DELETE FROM ADMMEMBER_DEPART WHERE ADM_ID='"+pnull(_params.get("adm_id"))+"' ";
        
        try
        {
            rn = executeUpdate(sql);
        }
        catch(SQLException e)
        {
            throw new CommonException(e.getMessage(), e);
        }
        
        try
        {
            query = "UPDATE ADMMEMBER SET "
            		+ " adm_name='"+pnull(_params,"adm_name")+"',  adm_password='"+pnull(_params,"password")+"',  updateDT=getdate() "
            		+ " WHERE adm_id='"+pnull(_params,"adm_id")+" ";
            rn = executeUpdate(query);

        } catch(Exception e) {
            _logger.error("["+Thread.currentThread().getStackTrace()[1].getLineNumber()+"]ERROR SQL:"+query, e);
        } finally {
            free();
        }
        
        PreparedStatement preparedStatement=null;
        String depart_code = "";
        int departCodeCnt = Integer.parseInt(pnull(_params.get("departCodeCnt"), "0"));
        int departCodeCommitCnt = 0;
            
        try
        {
        	if(departCodeCnt <= 0) throw new CommonException("등록된 부서가 없습니다.", "ERR-900");
        	
            query = "INSERT INTO ADMMEMBER_DEPART  (adm_id, depart_code, depart_mainYN, insertDT, userid)  values(?, ?, ?, getdate(), ?) ";
            preparedStatement = getPreparedStatement(query);
            for(int i = 0; i < departCodeCnt; i++)
            {
                if(pnull(_params.get("departCode3"+i + 1).toString()).equals(""))
                {
                    if(pnull(_params.get("departCode2"+i + 1).toString()).equals(""))
                    {
                        if(pnull(_params.get("departCode1"+i + 1).toString()).equals(""))
                            depart_code = "";
                        else
                            depart_code = pnull(_params.get("departCode1"+i + 1).toString());
                    } else
                    {
                        depart_code = pnull(_params.get("departCode2"+i + 1).toString());
                    }
                } else
                {
                    depart_code = pnull(_params.get("departCode3"+i + 1).toString());
                }
                preparedStatement.setString(1, pnull(_params.get("adm_id")));
                preparedStatement.setString(2, depart_code);
                preparedStatement.setString(3, pnull(_params.get("depart_mainYN"), "N"));
                preparedStatement.setString(4, pnull(_params.get("userid")));
                rn += preparedStatement.executeUpdate();
            }

        } catch(Exception e) {
            _logger.error("["+Thread.currentThread().getStackTrace()[1].getLineNumber()+"]ERROR SQL:"+query, e);
        } finally {
        	if(preparedStatement != null) try { preparedStatement.close(); } catch(SQLException e) { e.printStackTrace(); }
            free();
        }
        
        return rn >= 1;
    }

    @Override
    public boolean admMemberDelete(Map _params)
        throws CommonException
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
        int rtCnt = 0;
        String sql = "DELETE FROM ADMMEMBER_DEPART WHERE ADM_ID='"+pnull(_params.get("adm_id"))+"' ";
        try
        {
            rtCnt = executeUpdate(sql);
            sql = "DELETE FROM ADMMEMBER WHERE ADM_ID='"+pnull(_params.get("adm_id"))+"' ";
            rtCnt += executeUpdate(sql);
        }
        catch(SQLException e)
        {
            _logger.error("SQL \uC624\uB958:"+e.getMessage());
            _logger.error("SQL \uC624\uB958:"+sql);
            throw new CommonException(e.getMessage(), e);
        }
        return rtCnt > 0;
    }

    @Override
    public String admMemberAuthManage(Map _params)
	    throws CommonException
	{
		D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
		
	    String result = "";
	    String sql = "";
	    CallableStatement cstmt = null;
	    try
	    {
	        String query = "{call pcManAdmMemberAuth(?,?,?,?)}";
	        cstmt = getConnection().prepareCall(query);
	        cstmt.setString(1, pnull(_params.get("query_type")));
	        cstmt.setString(2, pnull(_params.get("id")));
	        cstmt.setString(3, pnull(_params.get("menu")));
	        cstmt.registerOutParameter(4, 12);
	        cstmt.execute();
	        result = cstmt.getString(4);
	    }
	    catch(SQLException e)
	    {
	        throw new CommonException(e.getMessage(), "ERR-D013", e);
	    }
	    return result;
	}

    @Override
    public List admMemberNonList(Map _params)
	{
		D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
		
	    String query="", strWhere="";
	    List result = null;
	
	    String searchText = pnull(_params.get("searchText"));
	    
	    if(searchText.length() > 0)
	        strWhere = " AND a.adm_name LIKE '%"+searchText+"%' ";
	    
	    try
	    {
	        query = "SELECT A.ADM_ID, A.ADM_NAME  FROM ADMMEMBER A "
	        		+ " LEFT OUTER JOIN ADMMEMBER_AUTH B ON A.adm_id = B.ADM_ID AND B.ADM_MENU = '"+pnull(_params, "menu")+"' "
	                + " WHERE 1 = 1 " + strWhere + " AND B.ADM_MENU IS NULL "
	                ;
	        result = super.executeQueryList(query);
	
	    } catch(Exception e) {
	        _logger.error("["+Thread.currentThread().getStackTrace()[1].getLineNumber()+"]ERROR SQL:"+query, e);
	    } finally {
	        free();
	    }
	    D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+result );
	    return result;
	}

    @Override
    public List admMemberAuthList(Map _params)
	{
		D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
		
	    String query;
	    String strWhere;
	    List result = null;
	
	    query = "";
	    String searchText = pnull(_params.get("searchText2"));
	    strWhere = "";
	    if(searchText.length() > 0)
	        strWhere = " AND a.adm_name LIKE '%"+searchText+"%' ";
	    result = null;
	    try
	    {
	        query = "SELECT A.ADM_ID, A.ADM_NAME "
	        		+ " FROM ADMMEMBER A "
	        		+ " LEFT OUTER JOIN ADMMEMBER_AUTH B ON A.adm_id = B.ADM_ID AND B.ADM_MENU = '"+pnull(_params,"menu")+"' "
	        		+ " WHERE 1 = 1 "+strWhere+" AND B.ADM_MENU IS NOT NULL ";
	        result = super.executeQueryList(query);
	
	    } catch(Exception e) {
	        _logger.error("["+Thread.currentThread().getStackTrace()[1].getLineNumber()+"]ERROR SQL:"+query, e);
	    } finally {
	        free();
	    }
	    D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+result );
	    return result;
	}

    @Override
    public List inquireMemberList(Map _params)
	    {
	    	
	        String query="";
	        String schTextGubun = pnull(_params.get("schTextGubun"));
	        String schText  = pnull(_params.get("schText"));
	        String strWhere ="";
	        List result = null;
	        
	        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
	        
	        int pageNo    = ipnull(_params, "pageNo", 1);
	        int pageSize  = ipnull(_params, "pageSize", 20);
	        int startRnum = (pageNo - 1) * pageSize + 1;
	        int endRnum   = pageNo * pageSize;
	        
	        if(schText.length() > 0 && !schTextGubun.equals("admin_depart"))
	            strWhere = " AND a."+schTextGubun+" LIKE '%"+schText+"%' ";
	        
	        try
	        {
	        	// old logic
	//            if(schText.length() > 0)
	//            {
	//                if(schTextGubun.equals("adm_deaprt"))
	//                    query = "SELECT A.*, c.name  FROM "
	//                    		+ "(select ROW_NUMBER() OVER(ORDER BY a.adm_group,a.adm_id) RNUM,"
	//                    		+ "        a.adm_id, a.adm_name,min(b.depart_code) AS depart_code, a.adm_group"
	//                    		+ " from ADMMEMBER a"
	//                    		+ "  left outer join ADMMEMBER_DEPART b on a.adm_id = b.adm_id"
	//                    		+ " and b.depart_code in (select depart_code from DEPARTMENT where name like '%"+schText+"%') "
	//                    		+ " where 1 = 1 "
	//                    		+ " group by a.adm_group,a.adm_id, a.adm_name) A ")
	//                    		.append("  left outer join DEPARTMENT c on A.depart_code = c.depart_code "
	//                    			+ " WHERE RNUM BETWEEN "+startRnum+" AND "+endRnum).toString());
	//                else
	//                    query = "SELECT A.*, c.name  FROM (select ROW_NUMBER() OVER(ORDER BY a.adm_group,a.adm_id) RNUM,"
	//                    		+ "   a.adm_id, a.adm_name,min(b.depart_code) AS depart_code, a.adm_group"
	//                    		+ "  from ADMMEMBER a"
	//                    		+ "  left outer join ADMMEMBER_DEPART b on a.adm_id = b.adm_id "
	//                    		+ " where 1 = 1 "
	//                    		+ " group by a.adm_group,a.adm_id, a.adm_name) A "
	//                    		+ "left outer join DEPARTMENT c on A.depart_code = c.depart_code "
	//                    		+ " WHERE RNUM BETWEEN "+startRnum+" AND "+endRnum).toString());
	//            } else {
	//                query = "SELECT A.*, c.name  FROM ("
	//                		+ " select ROW_NUMBER() OVER(ORDER BY a.adm_group,a.adm_id) RNUM,"
	//                		+ "   a.adm_id, a.adm_name,min(b.depart_code) AS depart_code, a.adm_group"
	//                		+ " from ADMMEMBER a"
	//                		+ " left outer join ADMMEMBER_DEPART b on a.adm_id = b.adm_id "
	//                		+ " where 1 = 1 "
	//                		+ " group by a.adm_group,a.adm_id, a.adm_name"
	//                		+ ") A "
	//                		+ " left outer join DEPARTMENT c on A.depart_code = c.depart_code  "
	//                		+ " WHERE RNUM BETWEEN "+startRnum+" AND "+endRnum;
	//            }
	//          
	        	// new logic
	            String t = "";
	            if(schText.length() > 0) {
	            	if(schTextGubun.equals("adm_id")) t += " AND ID like '%"+ schText +"%'";
	            	else if(schTextGubun.equals("adm_name")) t += " AND NAME like '%"+ schText +"%'";
	            	else if(schTextGubun.equals("adm_deaprt")) t += " AND ID IN (SELECT CHURCH_IDX FROM CHURCH WHERE NAME LIKE '%"+ schText +"%') ";
	            }
	            query = "SELECT ROW_NUMBER() OVER(ORDER BY M.NAME) RNUM, "
	            		+ "	C.NAME CHURCH_NAME, M.* "
	            		+ "	FROM MEMBER M "
	            		+ "	LEFT OUTER JOIN CHURCH  C ON C.CHURCH_IDX = M.CHURCH_IDX "
	            		+ "	WHERE M.ADMIN_YN='Y' "
	            		+ (t.length() > 0 ? t : "")
	            		;
	            
	            result = executeQueryList(query);
	
	        } catch(Exception e) {
	            e.printStackTrace();
	        } finally {
	            free();
	        }
	        
	        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+result );
	        
	        return result;
	    }

    @Override
    public int inquireMemberListCount(Map _params)
	    {
	        int result = 0;
	
	        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
	        
	        String schTextGubun = pnull(_params.get("schTextGubun"));
	        String schText = pnull(_params.get("schText"));
	        String b_idx = pnull(_params.get("b_idx"), "ALL");
	        String query = "", strWhere = "";
	        
	        if(schText.length() > 0 && !schTextGubun.equals("admin_depart"))
	            strWhere = " AND a."+schTextGubun+" LIKE '%"+schText+"%' ";
	        try
	        {
	//            if(schText.length() > 0) {
	//                if(schTextGubun.equals("admin_deaprt"))
	//                    query = "select ROW_NUMBER() OVER(ORDER BY a.adm_group,a.adm_id) RNUM, a.adm_id, a.adm_name,min(b.depart_code) AS depart_code, a.adm_group"
	//                    		+ " from ADMMEMBER a   "
	//                    		+ " left outer join ADMMEMBER_DEPART b on a.adm_id = b.adm_id and b.depart_code in (select depart_idx from DEPARTMENT where name like '%"+schText+"%')"
	//                    		+ " where 1 = 1 and b.depart_code is not null group by a.adm_group,a.adm_id, a.adm_name";
	//                else
	//                    query = "select ROW_NUMBER() OVER(ORDER BY a.adm_group,a.adm_id) RNUM, a.adm_id, a.adm_name,min(b.depart_code) AS depart_code, a.adm_group"
	//                    		+ " from ADMMEMBER a   "
	//                    		+ " left outer join ADMMEMBER_DEPART b on a.adm_id = b.adm_id    where 1 = 1 "+strWhere+" group by a.adm_group,a.adm_id, a.adm_name";
	//            } else {
	//                query = "select ROW_NUMBER() OVER(ORDER BY a.adm_group,a.adm_id) RNUM, a.adm_id, a.adm_name,min(b.depart_code) AS depart_code, a.adm_group"
	//                		+ " from ADMMEMBER a   "
	//                		+ " left outer join ADMMEMBER_DEPART b on a.adm_id = b.adm_id    where 1 = 1    group by a.adm_group,a.adm_id, a.adm_name";
	//            }
	
	        	// new logic
	            String t = "";
	            if(schText.length() > 0) {
	            	if(schTextGubun.equals("adm_id")) t += " AND ID like '%"+ schText +"%'";
	            	else if(schTextGubun.equals("adm_name")) t += " AND NAME like '%"+ schText +"%'";
	            	else if(schTextGubun.equals("adm_deaprt")) t += " AND ID IN (SELECT CHURCH_IDX FROM CHURCH WHERE NAME LIKE '%"+ schText +"%') ";
	            }
	            query = "SELECT ROW_NUMBER() OVER(ORDER BY M.NAME) RNUM, "
	            		+ "	C.NAME CHURCH_NAME, M.* "
	            		+ "	FROM MEMBER M "
	            		+ "	LEFT OUTER JOIN CHURCH  C ON C.CHURCH_IDX = M.CHURCH_IDX "
	            		+ "	WHERE M.ADMIN_YN='Y' "
	            		+ (t.length() > 0 ? t : "")
	            		;
	            
	            query = "SELECT COUNT(1) FROM ( " + query + " ) A ";
	            result = executeCount(query, false);
	
	        } catch(Exception e) {
	            e.printStackTrace();
	        } finally {
	            free();
	        }
	        
	        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+result );
	        
	        return result;
	    }

	/**
	 * 회원 목록 조회
	 */
	@Override
	public CommonDaoDTO selectMemberList(Map _params)
	{
		CommonDaoDTO dto = new CommonDaoDTO();
		
	    String query="";
	    String strWhere="";
//	    List result = null;
	
	    D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
	    
	    int pageNo = ipnull(_params, "pageNo", 1);
	    int pageSize = ipnull(_params, "pageSize", 10);
	    int startRnum = (pageNo - 1) * pageSize + 1;
	    int endRnum = pageNo * pageSize;
	    String memberType = pnull(_params.get("memberType"));
	    String schTextGubun = pnull(_params.get("schTextGubun"));
	    String schText = pnull(_params.get("schText"));
	    
	    if(schText.length() > 0)
	        strWhere = " AND "+schTextGubun+" LIKE '%"+schText+"%' ";
	    if(memberType.length() > 0) {
	    	if(memberType.equals("4"))
	            strWhere = strWhere+" AND GROUPGUBUN = '3' ";
	    	else if(memberType.equals("5"))
	            strWhere = strWhere+" AND GROUPGUBUN in ('2','4') ";
	    	else if(memberType.equals("6")) // 휴면계정
	            strWhere = strWhere+" AND dormantYN='Y' ";
	        else
	            strWhere = strWhere+" AND MEMBERTYPE = '"+memberType+"' ";
	    }
	    /*
	     + "   , CASE WHEN GROUPGUBUN='1' THEN '일반회원' "
	        		+ "          WHEN GROUPGUBUN='2' THEN '교구게시판관리'" --> 부서 5
	        		+ "          WHEN GROUPGUBUN='3' THEN '본당게시판관리'" --> 성당대표 4
	        		+ "          WHEN GROUPGUBUN='4' THEN '공동체소식관리'" --> 부서 5
	     */
	    try {
	    	// list
	        query = "SELECT A.*, B.NAME AS MEMBERTYPENAME, ' '/*C.CODETEXT*/ AS GROUPTYPENAME "
	        		+ " FROM ( "
	        		+ "    SELECT ROW_NUMBER() OVER(ORDER BY NAME) RNUM, ID, NAME, BAPTISMALNAME, MEMBERTYPE, WRITEYN, IS_INCHEON_GYUGO, GROUPGUBUN, LASTLOGINDT, OUTYN, DORMANTYN "
	        		+ "    FROM MEMBER WHERE 1=1 /*AND OUTYN='N'*/ "+strWhere
	        		+ " ) A "
	        		+ " LEFT OUTER JOIN CODE_INSTANCE B ON B.CODE = '000008' AND B.CODE_INST = A.MEMBERTYPE"
	        		//+ " LEFT OUTER JOIN CODE B ON B.TABLENAME = 'MEMBER' AND B.columnName='MEMBERTYPE' AND A.MEMBERTYPE = B.CODEVALUE "
	        		//+ " LEFT OUTER JOIN CODE C ON C.TABLENAME = 'MEMBER' AND C.columnName='GROUPTYPE' AND A.GROUPTYPE = C.CODEVALUE "
	        		+ " WHERE RNUM BETWEEN "+startRnum+" AND "+endRnum;
	        
	        dto.daoList = super.executeQueryList(query);
	
	        // count
	        try {
		        query = "SELECT A.*, B.NAME AS MEMBERTYPENAME, ' '/*C.CODETEXT*/ AS GROUPTYPENAME "
		        		+ " FROM ( "
		        		+ "    SELECT ROW_NUMBER() OVER(ORDER BY NAME) RNUM, ID, NAME, BAPTISMALNAME, MEMBERTYPE, WRITEYN, IS_INCHEON_GYUGO, GROUPGUBUN, LASTLOGINDT, OUTYN "
		        		+ "    FROM MEMBER WHERE 1=1 /*AND OUTYN='N'*/ "+strWhere
		        		+ " ) A "
		        		+ " LEFT OUTER JOIN CODE_INSTANCE B ON B.CODE = '000008' AND B.CODE_INST = A.MEMBERTYPE"
		        		;
		        
		        dto.daoTotalCount = super.executeCount(query, true);
		        dto.setPaging(pageNo, pageSize);
		
		    } catch(Exception e) {
		        _logger.error("["+Thread.currentThread().getStackTrace()[1].getLineNumber()+"]ERROR SQL:"+query, e);
		    } finally {
		        free();
		    }
	        
	        
	    } catch(Exception e) {
	        _E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
	    } finally {
	        super.free();
	    }
	    D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+dto);
	    
	    return dto;
	}

//	@Override
//    public int selectMemberListCount(Map _params)
//	{
//	    	    
//	    D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
//
//	    int result=0;
//	    String strWhere = "";
//	    String query    = "";
//	    
//	    String memberType   = pnull(_params.get("memberType"));
//	    String schTextGubun = pnull(_params.get("schTextGubun"));
//	    String schText      = pnull(_params.get("schText"));
//
//	    
//	    if(schText.length() > 0)
//	        strWhere = " AND "+schTextGubun+" LIKE '%"+schText+"%' ";
//	    if(memberType.length() > 0)
//	        if(memberType.equals("4") || memberType.equals("5"))
//	            strWhere += " AND groupType = '"+memberType+"' ";
//	        else
//	            strWhere += " AND memberType = '"+memberType+"' ";
//	    try
//	    {
//	        query = "SELECT COUNT(1)   FROM ( "
//	        		+ " SELECT ROW_NUMBER() OVER(ORDER BY ID, NAME) RNUM, ID, NAME, BAPTISMALNAME, MEMBERTYPE, IS_INCHEON_GYUGO "
//	        		+ " FROM MEMBER "
//	        		+ " WHERE 1 = 1 /*AND OUTYN='N'*/ "+strWhere
//	        		+ " ) A ";
//	        result = super.executeCount(query, false);
//	
//	    } catch(Exception e) {
//	        _logger.error("["+Thread.currentThread().getStackTrace()[1].getLineNumber()+"]ERROR SQL:"+query, e);
//	    } finally {
//	        free();
//	    }
//	    return result;
//	}

	@Override
    public List selectMemberListForExceldownload(Map _params)
	{
	    String strWhere="", query="";
	    List result = null;
	    
	    D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
	    
	    String schTextGubun = pnull(_params.get("schTextGubun"));
	    String schText = pnull(_params.get("schText"));
	
	    if(schText.length() > 0)
	        strWhere = " AND "+schTextGubun+" LIKE '%"+schText+"%' ";
	    
	    try {
	        query = "SELECT A.* "
	        		+ " FROM ( "
	        		+ "   SELECT ROW_NUMBER() OVER(ORDER BY ID, NAME) RNUM, ID, NAME, BAPTISMALNAME, IS_INCHEON_GYUGO, LASTLOGINDT, OUTYN "
	        		+ "   , CASE WHEN MEMBERTYPE='1' THEN '신자' " /* CODE_INSTANCE B where B.CODE = '000008' */
	        		+ "          WHEN MEMBERTYPE='2' THEN '예비신자'"
	        		+ "          WHEN MEMBERTYPE='3' THEN '비신자'"
	        		+ "          WHEN MEMBERTYPE='4' THEN '본당&부서'"
	        		+ "     END MEMBERTYPENAME, MEMBERTYPE "
	        		+ "   , CASE WHEN GROUPGUBUN='1' THEN '일반회원' "
	        		+ "          WHEN GROUPGUBUN='2' THEN '교구게시판관리'"
	        		+ "          WHEN GROUPGUBUN='3' THEN '본당게시판관리'"
	        		+ "          WHEN GROUPGUBUN='4' THEN '공동체소식관리'"
	        		+ "     END GROUPTYPENAME, GROUPGUBUN "
	        		+ "   FROM MEMBER WHERE 1=1 /*AND OUTYN='N'*/ "+strWhere+""
	        		+ " ) A "
	        		//+ " LEFT OUTER JOIN CODE B ON B.TABLENAME = 'MEMBER' AND B.columnName='MEMBERTYPE' AND A.MEMBERTYPE = B.CODEVALUE "
	        		//+ " LEFT OUTER JOIN CODE C ON C.TABLENAME = 'MEMBER' AND C.columnName='GROUPTYPE' AND A.GROUPTYPE = C.CODEVALUE"
	        		+ " WHERE 1 = 1"
	        		;
	        result = super.executeQueryList(query);
	        
	    } catch(Exception e) {
	    	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
	    } finally {
	    	free();
	    }
	    D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+result );
	    
	    return result;
	}

	@Override
    public Map selectMemberView(Map _params)
	    throws CommonException
	{
		D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
		
	    Map result=null;
	    String query = "SELECT * FROM MEMBER WHERE 1=1 ";
	    if(_params.get("id") != null)
	        query = query+" AND ID='"+_params.get("id")+"' ";
	    
	    try
	    {
	    	result = super.executeQueryMap(query);
	    }
	    catch(SQLException e)
	    {
	    	_logger.error("["+Thread.currentThread().getStackTrace()[1].getLineNumber()+"]ERROR SQL:"+query, e);
	        throw new CommonException("조건에 맞는 데이터가 존재하지 않습니다. 다시 입력해 주세요.", "ERR-D001");
	    } finally {
	        free();
	    }
	    D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+result );
	    return result;
	}

	@Override
    public String selectMemberId(Map _params)
        throws CommonException
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
        String query = "";
        ResultSet rs = null;
        String result ="";
        query = "SELECT ID FROM MEMBER WHERE 1=1 ";
        if(_params.get("id") != null)
            query = String.valueOf(query)+" AND ID='"+_params.get("id")+"' ";
        try
        {
            rs = super.executeQuery(query);
            if(rs.next())
                result = pnull(rs.getString(1));
        
	    } catch(Exception e) {
	        _logger.error("["+Thread.currentThread().getStackTrace()[1].getLineNumber()+"]ERROR SQL:"+query, e);
	        throw new CommonException("조건에 맞는 데이터가 존재하지 않습니다. 다시 입력해 주세요.", "ERR-D001");
	    } finally {
	    	if(rs != null) try { rs.close(); } catch(SQLException e) { e.printStackTrace(); }
	        free();
	    }
        return result;
    }

	@Override
    public boolean insertMember(Map _params)
        throws CommonException
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
        String sql = "";
        Map row = selectMemberView(_params);
        if(row != null && row.size() > 0)
            throw new CommonException("이미 사용중인 이메일 주소입니다.", "");
        
        String festival = pnull(_params.get("festivalM")) + pnull(_params.get("festivalD"));
        int rtCnt = 0;
        
        sql = "INSERT INTO MEMBER (ID, LOGINTYPE, PASSWORD, NAME, BAPTISMALNAME "
        		+ ", MAILCONFIRMYN, MEMBERTYPE, CHURCH_IDX, FESTIVALDAY "
        		+ ", TEL, OUTYN, DORMANTYN, IS_INCHEON_GYUGO, WRITEYN "
        		+ ", GROUPGUBUN, LASTLOGINDT, UPDATEDT "
        		+ ") VALUES ("
        		+ "'"+pnull(_params, "id")+"'"
        		+ ", '1', '"+pnull(_params, "password")+"' , '"+pnull(_params, "name")+"', '"+pnull(_params, "baptismalname")+"'"
        		+ ", 'Y', '"+pnull(_params, "memberType")+"', '"+pnull(_params, "church_idx")+"', '"+festival+"'"
        		+ ", '"+pnull(_params, "tel")+"', 'N', 'N', '"+pnull(_params, "isIncheonGyugo")+"', '"+pnull(_params, "writeYN")+"'"
        		+ ", '"+pnull(_params, "groupGubun")+"', null, GETDATE()"
        		+ ") ";
        try
        {
            rtCnt = executeUpdate(sql);
        }
        catch(SQLException e)
        {
            _logger.error("SQL \uC624\uB958:"+sql);
            _logger.error("SQL \uC624\uB958:"+e.getMessage());
            throw new CommonException(e.getMessage(), e);
        }
        return rtCnt == 1;
    }

	@Override
    public boolean updateMember(Map _params)
        throws CommonException
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
        int rtCnt = 0;
        String sql = "";
        try
        {
        	
        	String dormantYN = pnull(_params, "dormantYN", "N");
        	
            sql = "UPDATE MEMBER SET  "
            		+ " NAME='"+pnull(_params, "name")+"'"
            		+ ", MEMBERTYPE='"+pnull(_params, "memberType", "1")+"'"
            		+ ", PASSWORD='"+pnull(_params, "password")+"'"
            		+ ", BAPTISMALNAME='"+pnull(_params, "baptismalname", "")+"'"
            		+ ", CHURCH_IDX='"+pnull(_params, "church_idx")+"'"
            		+ ", GROUPTYPE='"+pnull(_params, "groupType", "1")+"'"
            		+ ", TEL='"+pnull(_params, "tel")+"'"
            		+ ", WRITEYN='"+pnull(_params, "writeYN", "N")+"'"
            		+ ( dormantYN.length() == 1 ? ", DORMANTYN='"+dormantYN+"'" : "")
            		+ ", FESTIVALDAY='"+pnull(_params, "festivalM")+pnull(_params, "festivalD")+"' "
            		+ ", GROUPGUBUN='"+pnull(_params, "groupGubun", "1")+"'"
            		+ ", MAILCONFIRMYN='"+pnull(_params, "mailConfirmYN","N")+"'"
            		+ ", IS_INCHEON_GYUGO='"+pnull(_params, "isIncheonGyugo")+"'"
            		+ ", UPDATEDT=GETDATE() "
            		+ " WHERE ID='"+pnull(_params, "id")+"' "
            		;
            rtCnt = executeUpdate(sql);
        }
        catch(SQLException e)
        {
            _logger.error("SQL \uC624\uB958:"+e.getMessage());
            _logger.error("SQL \uC624\uB958:"+sql);
            throw new CommonException(e.getMessage(), "ERR-D013", e);
        }
        return rtCnt > 0;
    }

	@Override
    public boolean deleteMember(Map _params)
        throws CommonException
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
        int rtCnt = 0;
        String sql = "";
        try
        {
        	// 변경ID가 중복되는지..
        	String id = pnull(_params, "id");
        	int i = 0;
        	while(executeCount("select count(1) from MEMBER where id='XA"+ i +"_"+id+"' ", false) > 0) {
        		i++;
        	}
        	// 중복이 안되는 ID로 변경
            sql = "UPDATE MEMBER SET ID='XA"+ i +"_"+id+"', OUTYN='Y', UPDATEDT=GETDATE(), OUT_DATE=GETDATE() WHERE ID='"+id+"' ";
            rtCnt = executeUpdate(sql);
        }
        catch(SQLException e)
        {
            _logger.error("SQL \uC624\uB958:"+e.getMessage());
            _logger.error("SQL \uC624\uB958:"+sql);
            throw new CommonException(e.getMessage(), "ERR-D013", e);
        }
        return rtCnt > 0;
    }


	@Override
    public boolean restoreMember(Map _params)
        throws CommonException
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
        int rtCnt = 0;
        String sql = "";
        try
        {
            sql = "UPDATE MEMBER SET ID=SUBSTRING(ID, CHARINDEX('_',ID)+1, 100)"
            		+ ", OUTYN='N'"
            		+ ", UPDATEDT=GETDATE()"
            		+ ", OUT_DATE=null "
            		+ " WHERE ID='"+pnull(_params, "id")+"' ";
            rtCnt = executeUpdate(sql);
        }
        catch(SQLException e)
        {
            _logger.error("SQL \uC624\uB958:"+e.getMessage());
            _logger.error("SQL \uC624\uB958:"+sql);
            throw new CommonException(e.getMessage(), "ERR-D014", e);
        }
        return rtCnt > 0;
    }
	
	/** 휴면계정 목록 조회 */
	@Override
    public List<Map> listDormantMembers() throws CommonException {
    	List result=null;
		try {
			result = executeQueryList("SELECT id,name,baptismalName,lastLoginDT FROM MEMBER "
					                + " WHERE LASTLOGINDT < DATEADD(YEAR, -1, GETDATE())  AND dormantYN<>'Y' AND outYN='N' ");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			free();//별도 free() 호출
		}
    	return result;
    }
    
	/** 휴먼계정 휴면Flag 설정(on)하기 */
    public void updateDormantMember(String email) throws CommonException {
    	try {
			executeUpdate("update MEMBER set dormantYN='Y' where id='"+email+"'");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			free();//별도 free() 호출
		}
    }
	
    
    public void daoClose() {
    	super.free();
    }
}
