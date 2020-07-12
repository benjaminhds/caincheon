// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MsgDaoImpl.java

package kr.caincheon.church.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import kr.caincheon.church.common.base.CommonDao;

// Referenced classes of package kr.caincheon.church.dao:
//            MsgDao

@Repository("msgDao")
public class MsgDaoImpl extends CommonDao
    implements MsgDao
{


	@Override
	public List msgList(Map _params)
    {
		List result = null;
        
		D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );

        String type   = pnull(_params.get("type"));
        String query  = "", strWhere = "";
        String schTextGubun = pnull(_params.get("schTextGubun"));
        String schText = pnull(_params.get("schText"));
        int pageNo    = Integer.parseInt(pnull(_params.get("pageNo"), "1"));
        int pageSize  = Integer.parseInt(pnull(_params.get("pageSize"), "20"));
        int startRnum = (pageNo - 1) * pageSize + 1;
        int endRnum   = pageNo * pageSize;
        
        if(schText.length() > 0)
            if(schTextGubun.equals("all"))
                strWhere = " AND (TITLE LIKE '%"+schText+"%' OR CONTENT LIKE '%"+schText+"%')";
            else
                strWhere = " AND "+schTextGubun+" LIKE '%"+schText+"%' ";
        
        try
        {
            if(type.equals("ALL"))
                query = "SELECT * FROM "
                		+ "( SELECT ROW_NUMBER() OVER(ORDER BY mdate DESC) RNUM,  TYPE, M_IDX, TITLE, SUB_TITLE, CONTENT"
                		+ ", convert(char(10),  REGDATE, 120) REGDATE,  CASE WHEN convert(char(10),  MDATE, 120) = '1900-01-01' THEN '' ELSE convert(char(10),  MDATE, 120) END MDATE "
                		+ " FROM MESSAGE "
                		+ " WHERE ISNULL(displayYN,'Y')<>'N' and TYPE <> '9' "+strWhere+""
                		+ " ) A "
                		+ " WHERE RNUM BETWEEN "+startRnum+" AND "+endRnum;
            else
                query = "SELECT * FROM "
                		+ "( SELECT ROW_NUMBER() OVER(ORDER BY mdate DESC) RNUM,  TYPE, M_IDX, TITLE, SUB_TITLE, CONTENT"
                		+ ", convert(char(10),  REGDATE, 120) REGDATE,  CASE WHEN convert(char(10),  MDATE, 120) = '1900-01-01' THEN '' ELSE convert(char(10),  MDATE, 120) END MDATE "
                		+ " FROM MESSAGE "
                		+ " WHERE ISNULL(displayYN,'Y')<>'N' and TYPE = '"+_params.get("type")+"' "+strWhere
                		+" ) A "
                		+ " WHERE RNUM BETWEEN "+startRnum+" AND "+endRnum;
            result = super.executeQueryList(query);
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
        	free();
    	}
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Result:"+result );
        return result;
    }

	@Override
    public int msgListCount(Map _params)
    {
        String type = _params.get("type").toString();
        String strWhere;
        int result=0;
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        String query = "";
        String schTextGubun = pnull(_params.get("schTextGubun"));
        String schText = pnull(_params.get("schText"));
        strWhere = "";
        
        if(schText.length() > 0) {
            if(schTextGubun.equals("all"))
                strWhere = " AND (TITLE LIKE '%"+schText+"%' OR CONTENT LIKE '%"+schText+"%')";
            else
                strWhere = " AND "+schTextGubun+" LIKE '%"+schText+"%' ";
        }
        
        try
        {
            if(type.equals("ALL"))
                query = "SELECT COUNT(1) FROM MESSAGE WHERE ISNULL(displayYN,'Y')<>'N' and TYPE <> '9' "+strWhere;
            else
                query = "SELECT COUNT(1) FROM MESSAGE WHERE ISNULL(displayYN,'Y')<>'N' and TYPE='"+type+"' "+strWhere;
            result = super.executeCount(query, false);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        } finally {
        	free();
    	}
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Result:"+result );
        return result;
    }

	/*
	 * front에서 메시지 보기
	 */
	@Override
    public Map msgContents(Map _params)
    {
		D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
		
        Map result;
        String type;
        String m_idx;
        result = new HashMap();
        type = pnull(_params.get("type"));
        m_idx = pnull(_params.get("m_idx"));
        
        // controller에서 임의 세팅한 변수로 구분
        String LAST_ONE_VIEW = pnull(_params, "LAST_ONE_VIEW");
        
        try
        {
            String whereStr = " AND IS_VIEW='Y'";
            String query = "SELECT TYPE, M_IDX, TITLE, SUB_TITLE, CONTENT, CONVERT(CHAR(10),  REGDATE, 120) REGDATE, CONVERT(CHAR(10),  MDATE, 120) MDATE"
            				+ ", (SELECT MAX(M_IDX) FROM MESSAGE X WHERE X.TYPE='"+type+"' AND X.M_IDX < A.M_IDX) AS before_p_idx"
            				+ ", (SELECT MIN(M_IDX) FROM MESSAGE X WHERE X.TYPE='"+type+"' AND X.M_IDX > A.M_IDX) AS next_p_idx "
            				+ " FROM MESSAGE A "
            				+ ("TRUE".equals(LAST_ONE_VIEW) 
            						? " WHERE TYPE='"+type+"' AND M_IDX = (SELECT MAX(M_IDX) FROM MESSAGE WHERE TYPE='"+type+"' AND ISNULL(displayYN,'Y')='Y' )" 
            						: " WHERE TYPE='"+type+"' AND M_IDX='"+m_idx+"'" )
            				;
            result = super.executeQueryMap(query);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        } finally {
        	free();
    	}
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Result:"+result );
        return result;
    }

	@Override
    public List admMsgList(Map _params)
    {
        String whereStr="";
        List result=null;
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        int pageNo = Integer.parseInt(pnull(_params.get("pageNo"), "1"));
        int startRnum = (pageNo - 1) * 10 + 1;
        int endRnum = pageNo * 10;
        String query = "";
        String s_type = "";
        String s_search = "";
        String searchText = "";
        
        s_type = pnull(_params.get("s_type"));
        searchText = pnull(_params.get("searchText"));
        
        if(s_type != null && s_type.length() > 0 && !s_type.equals("all"))
            whereStr = (new StringBuilder(" AND type='")+s_type+"'").toString();
        
        if(searchText != null && searchText.length() > 0)
        {
            s_search = pnull(_params.get("s_search"));
            if(s_search.equals("all"))
                whereStr = (new StringBuilder(String.valueOf(whereStr))+" AND ( TITLE LIKE '%"+searchText+"%' OR SUB_TITLE LIKE '%"+searchText+"%' OR CONTENT LIKE '%"+searchText+"%') ").toString();
            else
            if(s_search.equals("title"))
                whereStr = (new StringBuilder(String.valueOf(whereStr))+" AND ( TITLE LIKE '%"+searchText+"%' OR SUB_TITLE LIKE '%"+searchText+"%') ").toString();
            else
            if(s_search.equals("contents"))
                whereStr = (new StringBuilder(String.valueOf(whereStr))+" AND CONTENT LIKE '%"+searchText+"%' ").toString();
        }
        
        
        try
        {
            query = " SELECT * FROM  (SELECT ROW_NUMBER() OVER(ORDER BY mdate DESC) RNUM, TYPE, displayYN AS IS_VIEW"
            		+ ",  M_IDX, TITLE, SUB_TITLE, CONTENT"
            		+ ", CASE WHEN TYPE='1' THEN '\uAD50\uC11C'  WHEN TYPE='2' THEN '\uC11C\uD55C' WHEN TYPE='3' THEN '\uB2F4\uD654\uBB38' END AS TYPE_TEXT"
            		+ ", CASE WHEN convert(char(10),  MDATE, 120) = '1900-01-01' THEN '' ELSE convert(char(10),  MDATE, 120) END MDATE "
            		+ " FROM MESSAGE  WHERE TYPE <> '9' " + whereStr + " ) A "
            		+ " WHERE RNUM BETWEEN "+startRnum+" AND "+endRnum;
            result = super.executeQueryList(query);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        } finally {
        	free();
    	}
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Result:"+result );
        
        return result;
    }

	@Override
    public int admMsgListCount(Map _params)
    {
        String whereStr="";
        int result;
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        String query = "";
        String s_type = pnull(_params.get("s_type"));
        String s_search = pnull(_params.get("s_search"));
        String searchText = pnull(_params.get("searchText"));
        
        if(s_type != null && s_type.length() > 0 && !s_type.equals("all"))
            whereStr = (new StringBuilder(" AND type='")+s_type+"'").toString();
        
        if(searchText != null && searchText.length() > 0)
        {
            
            if(s_search.equals("all"))
                whereStr += " AND ( TITLE LIKE '%"+searchText+"%' OR SUB_TITLE LIKE '%"+searchText+"%' OR CONTENT LIKE '%"+searchText+"%') ";
            else if(s_search.equals("title"))
                whereStr +=" AND ( TITLE LIKE '%"+searchText+"%' OR SUB_TITLE LIKE '%"+searchText+"%') ";
            else if(s_search.equals("contents"))
                whereStr +=" AND CONTENT LIKE '%"+searchText+"%' ";
        }
        
        result = 0;
        
        try
        {
            query  = " SELECT COUNT(1) FROM MESSAGE WHERE TYPE <> '9' "+whereStr;
            result = super.executeCount(query, false);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        } finally {
        	free();
    	}
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Result:"+result );
        return result;
    }

	@Override
    public Map admMsgContents(Map _params)
    {
		D(_logger, Thread.currentThread().getStackTrace(), "DAO Called." );
		
        Map result = new HashMap();
        String m_idx = pnull(_params.get("m_idx"));
        try
        {
            String query = " SELECT M_IDX, TYPE, TITLE, SUB_TITLE, CONTENT"
	            		+ ", CASE WHEN CONVERT(CHAR(10),  MDATE, 120) = '1900-01-01' THEN '' ELSE CONVERT(CHAR(10),  MDATE, 120) END MDATE"
	            		+ ", NOTICEYN, DISPLAYYN "
	            		+ " FROM MESSAGE "
            			+ " WHERE M_IDX='"+m_idx+"'";
            result = super.executeQueryMap(query);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        } finally {
        	free();
    	}
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Result:"+result );
        return result;
    }

	@Override
    public boolean msgInsert(Map _params)
    {
        boolean bReturn;
        String type;
        String noticeYN;
        String title;
        String sub_title;
        String content;
        String mdate;
        String displayYN;
        Connection connection;
        PreparedStatement preparedStatement;
        bReturn = true;
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called." );
        
        type = pnull(_params.get("type"));
        noticeYN = pnull(_params.get("noticeYN"));
        title = pnull(_params.get("title"));
        sub_title = pnull(_params.get("sub_title"));
        content = pnull(_params.get("content"));
        mdate = pnull(_params.get("mdate"));
        displayYN = pnull(_params.get("displayYN"));
        connection = null;
        preparedStatement = null;
        int i = 0 ;
        try
        {
        	if(connection==null || connection.isClosed()) connection = dataSource.getConnection();
            String query = "INSERT INTO MESSAGE  (type, title, sub_title, content, regdate, mdate, noticeYN, displayYN)  VALUES (?, ?, ?, ?, getdate(), ?, ?, ?) ";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, type);
            preparedStatement.setString(2, title);
            preparedStatement.setString(3, sub_title);
            preparedStatement.setString(4, content);
            preparedStatement.setString(5, mdate);
            preparedStatement.setString(6, noticeYN);
            preparedStatement.setString(7, displayYN);
            i = preparedStatement.executeUpdate();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            bReturn = false;
        } finally {
        	
        	if(preparedStatement != null) try { preparedStatement.close(); } catch ( Exception e ) { e.printStackTrace(); }
            if(connection != null) try { connection.close();} catch ( Exception e ) { e.printStackTrace(); }
        	free();
    	}
        return bReturn && i > 0;
    }

	@Override
    public boolean msgModify(Map _params)
    {
		
        boolean bReturn;
        String m_idx;
        String type;
        String noticeYN;
        String title;
        String sub_title;
        String content;
        String mdate;
        String displayYN;
        Connection connection;
        PreparedStatement preparedStatement;
        bReturn = true;
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called." );
        
        m_idx = pnull(_params.get("m_idx"));
        type = pnull(_params.get("type"));
        noticeYN = pnull(_params.get("noticeYN"));
        title = pnull(_params.get("title"));
        sub_title = pnull(_params.get("sub_title"));
        content = pnull(_params.get("content"));
        mdate = pnull(_params.get("mdate"));
        displayYN = pnull(_params.get("displayYN"));
        connection = null;
        preparedStatement = null;
        int i = 0 ;
        try
        {
        	if(connection==null || connection.isClosed()) connection = dataSource.getConnection();
            String query = "UPDATE MESSAGE  SET type=?, title=?, sub_title=?,  content=?, mdate=?, noticeYN=?, displayYN=?  WHERE m_idx = ? ";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, type);
            preparedStatement.setString(2, title);
            preparedStatement.setString(3, sub_title);
            preparedStatement.setString(4, content);
            preparedStatement.setString(5, mdate);
            preparedStatement.setString(6, noticeYN);
            preparedStatement.setString(7, displayYN);
            preparedStatement.setInt(8, Integer.parseInt(m_idx));
            i = preparedStatement.executeUpdate();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            bReturn = false;
        } finally {
        	
        	if(preparedStatement != null) try { preparedStatement.close(); } catch ( Exception e ) { e.printStackTrace(); }
            if(connection != null) try { connection.close();} catch ( Exception e ) { e.printStackTrace(); }
        	free();
    	}
        return bReturn && i > 0;
    }

	@Override
    public boolean msgDelete(Map _params)
    {
        boolean bReturn;
        String m_idx;
        Connection connection;
        PreparedStatement preparedStatement;
        bReturn = true;
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        m_idx = pnull(_params.get("m_idx"));
        connection = null;
        preparedStatement = null;
        int i = 0 ;
        try
        {
        	if(connection==null || connection.isClosed()) connection = dataSource.getConnection();
            String query = "delete from MESSAGE where m_idx = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, Integer.parseInt(m_idx));
            i = preparedStatement.executeUpdate();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            bReturn = false;
        } finally {
        	
        	if(preparedStatement != null) try { preparedStatement.close(); } catch ( Exception e ) { e.printStackTrace(); }
            if(connection != null) try { connection.close();} catch ( Exception e ) { e.printStackTrace(); }
        	free();
    	}
        return bReturn && i > 0;
    }

    private final Logger _logger = Logger.getLogger(getClass());
}
