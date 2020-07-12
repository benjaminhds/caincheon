// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EmailDaoImpl.java

package kr.caincheon.church.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import kr.caincheon.church.common.base.CommonDao;
import kr.caincheon.church.common.utils.UtilString;

// Referenced classes of package kr.caincheon.church.dao:
//            EmailDao

@Repository("emailDao")
public class EmailDaoImpl extends CommonDao
    implements EmailDao
{

    public List emailList(Map _params)
    {
        String whereStr;
        int startRnum;
        int endRnum;
        List result;
        ResultSet rs;
        _logger.debug(" .... This is a DAO ....");
        System.out.println((new StringBuilder(" .... This is a DAO .... dataSource=")).append(dataSource).toString());
        _logger.debug((new StringBuilder("_params=")).append(_params.toString()).toString());
        String searchGubun = "";
        String searchText = "";
        whereStr = "";
        int pageNo = Integer.parseInt(UtilString.pnull(_params.get("pageNo"), "1"));
        startRnum = (pageNo - 1) * 10 + 1;
        endRnum = pageNo * 10;
        if(_params.get("searchGubun") != null)
        {
            searchGubun = UtilString.pnull(_params.get("searchGubun"));
            if(searchGubun.equals("title"))
            {
                searchText = UtilString.pnull(_params.get("searchText"));
                whereStr = (new StringBuilder(String.valueOf(whereStr))).append(" AND TITLE LIKE '%").append(searchText).append("'").toString();
                _logger.debug((new StringBuilder("whereStr=")).append(whereStr).toString());
            } else
            if(searchGubun.equals("name"))
            {
                searchText = UtilString.pnull(_params.get("searchText"));
                whereStr = (new StringBuilder(String.valueOf(whereStr))).append(" AND NAME LIKE '%").append(searchText).append("'").toString();
                _logger.debug((new StringBuilder("whereStr=")).append(whereStr).toString());
            } else
            if(searchGubun.equals("contents"))
            {
                searchText = UtilString.pnull(_params.get("searchText"));
                whereStr = (new StringBuilder(String.valueOf(whereStr))).append(" AND CONTENTS LIKE '%").append(searchText).append("%'").toString();
                _logger.debug((new StringBuilder("whereStr=")).append(whereStr).toString());
            }
        }
        result = new ArrayList();
        rs = null;
        String query = "";
        try
        {
            query = (new StringBuilder("SELECT * FROM  (SELECT ROW_NUMBER() OVER(ORDER BY registDT DESC) RNUM, EMAIL_NO, ID, NAME,  CONVERT(char(16),  SEND_DATE_FROM, 120) SEND_DATE_FROM, CONVERT(char(16),  SEND_DATE_TO, 120) SEND_DATE_TO, TITLE, CONTENTS, USE_YN,  CASE WHEN use_yn = 'Y' THEN '\uC0AC\uC6A9'  ELSE\t'\uC911\uC9C0'  END AS USE_YN_TEXT , CONVERT(char(16),  registDT, 120) registDT, CONVERT(char(16),  updateDT, 120) updateDT,EMAIL_TYPE  FROM newcaincheon.newcaincheon.EMAIL_BOX WHERE 1= 1 ")).append(whereStr).append(" ) ROWS ").append(" WHERE RNUM BETWEEN ").append(startRnum).append(" AND ").append(endRnum).toString();
            HashMap rsMap;
            for(rs = super.executeQuery(query); rs.next(); result.add(rsMap))
            {
                rsMap = new HashMap();
                rsMap.put("RNUM", Integer.valueOf(rs.getInt(1)));
                rsMap.put("EMAIL_NO", Integer.valueOf(rs.getInt(2)));
                rsMap.put("ID", rs.getString(3));
                rsMap.put("NAME", rs.getString(4));
                rsMap.put("SEND_DATE_FROM", rs.getString(5));
                rsMap.put("SEND_DATE_TO", rs.getString(6));
                rsMap.put("TITLE", rs.getString(7));
                rsMap.put("CONTENTS", rs.getString(8));
                rsMap.put("USE_YN", rs.getString(9));
                rsMap.put("USE_YN_TEXT", rs.getString(10));
                rsMap.put("REGISTDT", rs.getString(11));
                rsMap.put("UPDATEDT", rs.getString(12));
                rsMap.put("EMAIL_TYPE", rs.getString(13));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        } finally {
        	freeResultSet(rs);
        	free();
    	}
        _logger.debug((new StringBuilder("result=")).append(result.toString()).toString());
        return result;
    }

    public int emailListCount(Map _params)
    {
        String whereStr;
        int result;
        ResultSet rs;
        _logger.debug(" .... This is a DAO ....");
        System.out.println((new StringBuilder(" .... This is a DAO .... dataSource=")).append(dataSource).toString());
        _logger.debug((new StringBuilder("_params=")).append(_params.toString()).toString());
        String searchGubun = "";
        String searchText = "";
        whereStr = "";
        if(_params.get("searchGubun") != null)
        {
            searchGubun = UtilString.pnull(_params.get("searchGubun"));
            if(searchGubun.equals("title"))
            {
                searchText = UtilString.pnull(_params.get("searchText"));
                whereStr = (new StringBuilder(String.valueOf(whereStr))).append(" AND TITLE LIKE '%").append(searchText).append("'").toString();
                _logger.debug((new StringBuilder("whereStr=")).append(whereStr).toString());
            } else
            if(searchGubun.equals("name"))
            {
                searchText = UtilString.pnull(_params.get("searchText"));
                whereStr = (new StringBuilder(String.valueOf(whereStr))).append(" AND NAME LIKE '%").append(searchText).append("'").toString();
                _logger.debug((new StringBuilder("whereStr=")).append(whereStr).toString());
            } else
            if(searchGubun.equals("contents"))
            {
                searchText = UtilString.pnull(_params.get("searchText"));
                whereStr = (new StringBuilder(String.valueOf(whereStr))).append(" AND CONTENTS LIKE '%").append(searchText).append("%'").toString();
                _logger.debug((new StringBuilder("whereStr=")).append(whereStr).toString());
            }
        }
        result = 0;
        rs = null;
        String query = "";
        try
        {
            query = (new StringBuilder("SELECT COUNT(1) FROM  (SELECT ROW_NUMBER() OVER(ORDER BY registDT DESC) RNUM, EMAIL_NO, ID, NAME,  CONVERT(char(16),  SEND_DATE_FROM, 120) SEND_DATE_FROM, CONVERT(char(16),  SEND_DATE_TO, 120) SEND_DATE_TO, TITLE, CONTENTS, USE_YN,  CASE WHEN use_yn = 'Y' THEN '\uC0AC\uC6A9'  ELSE\t'\uC911\uC9C0'  END AS USE_YN_TEXT , CONVERT(char(16),  registDT, 120) registDT, CONVERT(char(16),  updateDT, 120) updateDT\t FROM newcaincheon.newcaincheon.EMAIL_BOX WHERE 1= 1 ")).append(whereStr).append(" ) ROWS ").append(" WHERE 1 = 1").toString();
            rs = super.executeQuery(query);
            if(rs.next())
                result = rs.getInt(1);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        } finally {
        	freeResultSet(rs);
        	free();
    	}
        return result;
    }

    public Map emailContents(Map _params)
    {
        Map result;
        ResultSet rs;
        String query_type;
        String email_no;
        _logger.info((new StringBuilder("_params=")).append(_params.toString()).toString());
        result = new HashMap();
        rs = null;
        String id = UtilString.pnull(_params.get("id"));
        query_type = UtilString.pnull(_params.get("query_type"));
        email_no = UtilString.pnull(_params.get("email_no"));
        _params.put("member_id", id);
        Map memberInfo = getMember(_params);
        String whereStr = "", query="";
        try
        {
            if(query_type.equals("modify"))
            {
                whereStr = (new StringBuilder(" WHERE EMAIL_NO = '")).append(email_no).append("'").toString();
                query = (new StringBuilder("SELECT EMAIL_NO, ID, NAME, RCV_EMAIL, EMAIL_TYPE,  CONVERT(CHAR(16), SEND_DATE_FROM, 120) SEND_DATE_FROM, CONVERT(CHAR(16), SEND_DATE_TO, 120) SEND_DATE_TO,  TITLE, CONTENTS, USE_YN,  CONVERT(CHAR(16), REGISTDT, 120) REGISTDT, CONVERT(CHAR(16), UPDATEDT, 120) UPDATEDT  FROM newcaincheon.newcaincheon.EMAIL_BOX ")).append(whereStr).toString();
                _logger.info((new StringBuilder("query=")).append(query).toString());
                rs = super.executeQuery(query);
                if(rs.next())
                {
                    result.put("EMAIL_NO", Integer.valueOf(rs.getInt(1)));
                    result.put("ID", rs.getString(2));
                    result.put("NAME", rs.getString(3));
                    result.put("RCV_EMAIL", rs.getString(4));
                    result.put("EMAIL_TYPE", rs.getString(5));
                    result.put("SEND_DATE_FROM", rs.getString(6));
                    result.put("SEND_DATE_TO", rs.getString(7));
                    result.put("TITLE", rs.getString(8));
                    result.put("CONTENTS", rs.getString(9));
                    result.put("USE_YN", rs.getString(10));
                    result.put("REGISTDT", rs.getString(11));
                    result.put("UPDATEDT", rs.getString(12));
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        } finally {
        	freeResultSet(rs);
        	free();
    	}
        _logger.info((new StringBuilder("result=")).append(result.toString()).toString());
        return result;
    }

    public boolean emailInsert(Map _params)
    {
        boolean bReturn;
        String id;
        String title;
        String email_type;
        String send_date_from;
        String send_date_to;
        String rcv_email;
        String contents;
        Map memberInfo;
        Connection connection;
        PreparedStatement preparedStatement;
        bReturn = true;
        _logger.info((new StringBuilder(" .... This is a DAO ....dataSource=")).append(dataSource).toString());
        _logger.info((new StringBuilder("_params=")).append(_params.toString()).toString());
        id = UtilString.pnull(_params.get("id"));
        title = UtilString.pnull(_params.get("title"));
        email_type = UtilString.pnull(_params.get("email_type"));
        send_date_from = UtilString.pnull(_params.get("send_date_from"));
        send_date_to = UtilString.pnull(_params.get("send_date_to"));
        rcv_email = UtilString.pnull(_params.get("rcv_email"));
        contents = UtilString.pnull(_params.get("contents"));
        memberInfo = getMember(_params);
        connection = null;
        preparedStatement = null;
        try
        {
        	if(connection==null || connection.isClosed()) connection = dataSource.getConnection();
            String query = "INSERT INTO newcaincheon.newcaincheon.EMAIL_BOX  (id, name, rcv_email, email_type, send_date_from, send_date_to, title, contents, use_yn, registDT, updateDT)  VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, getdate(),getdate())";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, UtilString.pnull(memberInfo.get("NAME")));
            preparedStatement.setString(3, rcv_email);
            preparedStatement.setString(4, email_type);
            preparedStatement.setString(5, send_date_from);
            preparedStatement.setString(6, send_date_to);
            preparedStatement.setString(7, title);
            preparedStatement.setString(8, contents);
            preparedStatement.setString(9, "Y");
            int i = preparedStatement.executeUpdate();
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
        return bReturn;
    }

    public boolean emailModify(Map _params)
    {
        boolean bReturn;
        String id;
        String email_no;
        String title;
        String email_type;
        String send_date_from;
        String send_date_to;
        String rcv_email;
        String contents;
        Map memberInfo;
        Connection connection;
        PreparedStatement preparedStatement;
        bReturn = true;
        _logger.info((new StringBuilder(" .... This is a DAO (modify)....dataSource=")).append(dataSource).toString());
        _logger.info((new StringBuilder("_params=")).append(_params.toString()).toString());
        id = UtilString.pnull(_params.get("id"));
        email_no = UtilString.pnull(_params.get("email_no"));
        title = UtilString.pnull(_params.get("title"));
        email_type = UtilString.pnull(_params.get("email_type"));
        send_date_from = UtilString.pnull(_params.get("send_date_from"));
        send_date_to = UtilString.pnull(_params.get("send_date_to"));
        rcv_email = UtilString.pnull(_params.get("rcv_email"));
        contents = UtilString.pnull(_params.get("contents"));
        memberInfo = getMember(_params);
        connection = null;
        preparedStatement = null;
        try
        {
        	if(connection==null || connection.isClosed()) connection = dataSource.getConnection();
            String query = "UPDATE newcaincheon.newcaincheon.EMAIL_BOX  SET name=?, rcv_email=?, email_type=?, send_date_from=?, send_date_to=?,  title=?, contents=?, use_yn='Y', updateDT=getdate()  WHERE email_no = ? and id= ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, UtilString.pnull(memberInfo.get("NAME")));
            preparedStatement.setString(2, rcv_email);
            preparedStatement.setString(3, email_type);
            preparedStatement.setString(4, send_date_from);
            preparedStatement.setString(5, send_date_to);
            preparedStatement.setString(6, title);
            preparedStatement.setString(7, contents);
            preparedStatement.setInt(8, Integer.parseInt(email_no));
            preparedStatement.setString(9, id);
            _logger.info((new StringBuilder("query=")).append(query).toString());
            int i = preparedStatement.executeUpdate();
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
        return bReturn;
    }

    public boolean emailDelete(Map _params)
    {
        boolean bReturn;
        String email_no;
        Connection connection;
        PreparedStatement preparedStatement;
        bReturn = true;
        _logger.info((new StringBuilder(" .... This is a DAO ....dataSource=")).append(dataSource).toString());
        _logger.info((new StringBuilder("INQUIRE_APPLY Delete..._params=")).append(_params.toString()).toString());
        email_no = UtilString.pnull(_params.get("email_no"));
        connection = null;
        preparedStatement = null;
        try
        {
        	if(connection==null || connection.isClosed()) connection = dataSource.getConnection();
            String query = "delete from newcaincheon.newcaincheon.EMAIL_BOX where email_no = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, Integer.parseInt(email_no));
            int i = preparedStatement.executeUpdate();
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
        return bReturn;
    }

    public Map getMember(Map _params)
    {
        Map result;
        ResultSet rs;
        result = new HashMap();
        rs = null;
        try
        {
            String query = (new StringBuilder("SELECT ADM_NAME AS NAME FROM newcaincheon.newcaincheon.ADMMEMBER WHERE ADM_ID='")).append(_params.get("id")).append("' ").toString();
            rs = super.executeQuery(query);
            if(rs.next())
                result.put("NAME", rs.getString(1));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        } finally {
        	freeResultSet(rs);
        	free();
    	}
        _logger.debug((new StringBuilder("result=")).append(result.toString()).toString());
        return result;
    }

    private final Logger _logger = Logger.getLogger(getClass());
}
