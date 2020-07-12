// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DgroupDaoImpl.java

package kr.caincheon.church.dao;

import java.io.PrintStream;
import java.sql.*;
import java.util.*;
import javax.sql.DataSource;

import kr.caincheon.church.common.base.CommonDao;
import kr.caincheon.church.common.utils.UtilString;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

// Referenced classes of package kr.caincheon.church.dao:
//            DgroupDao
/**
 * 지구코드 괸리 (OLD CODE)
 * 
 * @deprecated - 공통코드로 전환되면서, 제거 대상임
 */


@Repository("dgroupDao")
public class DgroupDaoImpl extends CommonDao
    implements DgroupDao
{


    public List dgroupList(Map _params)
    {
        int startRnum;
        int endRnum;
        String whereStr;
        List result;
        ResultSet rs;
        _logger.debug(" .... This is a DAO ....");
        System.out.println((new StringBuilder(" .... This is a DAO .... dataSource=")).append(dataSource).toString());
        _logger.debug((new StringBuilder("_params=")).append(_params.toString()).toString());
        int pageNo = Integer.parseInt(UtilString.pnull(_params.get("pageNo"), "1"));
        startRnum = (pageNo - 1) * 10 + 1;
        endRnum = pageNo * 10;
        String searchText = "";
        whereStr = "";
        searchText = UtilString.pnull(_params.get("searchText"));
        if(searchText != null && searchText.length() > 0)
            whereStr = (new StringBuilder(String.valueOf(whereStr))).append(" AND d_group_name LIKE '%").append(searchText).append("%'").toString();
        _logger.debug((new StringBuilder("whereStr=")).append(whereStr).toString());
        result = new ArrayList();
        rs = null;
        String query = "";
        try
        {
            query = (new StringBuilder("SELECT * FROM  (SELECT ROW_NUMBER() OVER(ORDER BY CONVERT(INT,gigu_code) asc) RNUM, gigu_code, d_group_name, d_group_pos, useYN, displayYN,  CONVERT(char(10),  registDT, 120) registDT, CONVERT(char(10),  updateDT, 120) updateDT\t FROM newcaincheon.newcaincheon.DEPARTMENT_GROUP WHERE 1=1 ")).append(whereStr).append(") ROWS ").append(" WHERE RNUM BETWEEN ").append(startRnum).append(" AND ").append(endRnum).toString();
            HashMap rsMap;
            for(rs = super.executeQuery(query); rs.next(); result.add(rsMap))
            {
                rsMap = new HashMap();
                rsMap.put("RNUM", Integer.valueOf(rs.getInt(1)));
                rsMap.put("gigu_code", rs.getString(2));
                rsMap.put("d_group_name", rs.getString(3));
                rsMap.put("d_group_pos", rs.getString(4));
                rsMap.put("useYN", rs.getString(5));
                rsMap.put("displayYN", rs.getString(6));
                rsMap.put("REGISTDT", rs.getString(7));
                rsMap.put("UPDATEDT", rs.getString(8));
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

    public int dgroupListCount(Map _params)
    {
        String whereStr;
        int result;
        ResultSet rs;
        _logger.debug(" .... This is a DAO ....");
        System.out.println((new StringBuilder(" .... This is a DAO .... dataSource=")).append(dataSource).toString());
        String searchText = "";
        whereStr = "";
        searchText = UtilString.pnull(_params.get("searchText"));
        if(searchText != null && searchText.length() > 0)
            whereStr = (new StringBuilder(String.valueOf(whereStr))).append(" AND d_group_name LIKE '%").append(searchText).append("%'").toString();
        _logger.debug((new StringBuilder("whereStr=")).append(whereStr).toString());
        result = 0;
        rs = null;
        String query = "";
        try
        {
            query = (new StringBuilder("SELECT COUNT(1)   FROM newcaincheon.newcaincheon.DEPARTMENT_GROUP  WHERE 1=1 ")).append(whereStr).toString();
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

    public Map dgroupContents(Map _params)
    {
        Map result;
        ResultSet rs;
        String query_type;
        String gigu_code;
        _logger.info((new StringBuilder("_params=")).append(_params.toString()).toString());
        result = new HashMap();
        rs = null;
        query_type = UtilString.pnull(_params.get("query_type"));
        gigu_code = UtilString.pnull(_params.get("gigu_code"));
        String query = "";
        String whereStr = "";
        try
        {
            if(query_type.equals("modify"))
            {
                whereStr = (new StringBuilder(" WHERE gigu_code = '")).append(gigu_code).append("'").toString();
                query = (new StringBuilder("SELECT gigu_code, d_group_name, d_group_pos, useYN, displayYN,  CONVERT(CHAR(10), REGISTDT, 120) REGISTDT, CONVERT(CHAR(10), UPDATEDT, 120) UPDATEDT  FROM newcaincheon.newcaincheon.DEPARTMENT_GROUP ")).append(whereStr).toString();
                _logger.info((new StringBuilder("query=")).append(query).toString());
                rs = super.executeQuery(query);
                if(rs.next())
                {
                    result.put("gigu_code", rs.getString(1));
                    result.put("d_group_name", rs.getString(2));
                    result.put("d_group_pos", rs.getString(3));
                    result.put("useYN", rs.getString(4));
                    result.put("displayYN", rs.getString(5));
                    result.put("REGISTDT", rs.getString(6));
                    result.put("UPDATEDT", rs.getString(7));
                }
            } else
            if(query_type.equals("insert"))
            {
                query = "SELECT ISNULL(MAX(convert(int,gigu_code)) + 1, 1) insert_temp_code FROM newcaincheon.newcaincheon.DEPARTMENT_GROUP ";
                _logger.info((new StringBuilder("query=")).append(query).toString());
                rs = super.executeQuery(query);
                if(rs.next())
                    result.put("insert_temp_code", rs.getString(1));
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

    public boolean dgroupInsert(Map _params)
    {
        boolean bReturn;
        String d_group_name;
        String d_group_pos;
        String displayYN;
        Connection connection;
        PreparedStatement preparedStatement;
        bReturn = true;
        _logger.info((new StringBuilder(" .... This is a DAO ....dataSource=")).append(dataSource).toString());
        _logger.info((new StringBuilder("_params=")).append(_params.toString()).toString());
        d_group_name = UtilString.pnull(_params.get("d_group_name"));
        d_group_pos = UtilString.pnull(_params.get("d_group_pos"));
        displayYN = UtilString.pnull(_params.get("displayYN"));
        connection = null;
        preparedStatement = null;
        try
        {
        	if(connection==null || connection.isClosed()) connection = dataSource.getConnection();
            String query = "INSERT INTO newcaincheon.newcaincheon.DEPARTMENT_GROUP  (gigu_code, d_group_name, d_group_pos, useYN, displayYN, registDT, updateDT)  SELECT ISNULL(MAX(convert(int,gigu_code)) + 1, 1), ?, ?, 'Y', ?, getdate(),getdate() FROM newcaincheon.newcaincheon.DEPARTMENT_GROUP ";
            _logger.info((new StringBuilder("query=")).append(query).toString());
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, d_group_name);
            preparedStatement.setString(2, d_group_pos);
            preparedStatement.setString(3, displayYN);
            int i = preparedStatement.executeUpdate();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            bReturn = false;
        }  finally {
        	
        	if(preparedStatement != null) try { preparedStatement.close(); } catch ( Exception e ) { e.printStackTrace(); }
            if(connection != null) try { connection.close();} catch ( Exception e ) { e.printStackTrace(); }
        	free();
    	}
        return bReturn;
    }

    public boolean dgroupModify(Map _params)
    {
        boolean bReturn;
        String gigu_code;
        String d_group_name;
        String d_group_pos;
        String displayYN;
        Connection connection;
        PreparedStatement preparedStatement;
        bReturn = true;
        _logger.info((new StringBuilder(" .... This is a DAO (modify)....dataSource=")).append(dataSource).toString());
        _logger.info((new StringBuilder("_params=")).append(_params.toString()).toString());
        gigu_code = UtilString.pnull(_params.get("gigu_code"));
        d_group_name = UtilString.pnull(_params.get("d_group_name"));
        d_group_pos = UtilString.pnull(_params.get("d_group_pos"));
        displayYN = UtilString.pnull(_params.get("displayYN"));
        connection = null;
        preparedStatement = null;
        try
        {
        	if(connection==null || connection.isClosed()) connection = dataSource.getConnection();
            String query = "UPDATE newcaincheon.newcaincheon.DEPARTMENT_GROUP  SET d_group_name=?, d_group_pos=?, useYN='Y', displayYN=?, updateDT=getdate()  WHERE gigu_code = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, d_group_name);
            preparedStatement.setString(2, d_group_pos);
            preparedStatement.setString(3, displayYN);
            preparedStatement.setString(4, gigu_code);
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

    public boolean dgroupDelete(Map _params)
    {
        boolean bReturn;
        String gigu_code;
        Connection connection;
        PreparedStatement preparedStatement;
        bReturn = true;
        _logger.info((new StringBuilder(" .... This is a DAO ....dataSource=")).append(dataSource).toString());
        _logger.info((new StringBuilder("INQUIRE_APPLY Delete..._params=")).append(_params.toString()).toString());
        gigu_code = UtilString.pnull(_params.get("gigu_code"));
        connection = null;
        preparedStatement = null;
        int i = 0;
        try
        {
        	if(connection==null || connection.isClosed()) connection = dataSource.getConnection();
            String query = "delete from newcaincheon.newcaincheon.DEPARTMENT_GROUP where gigu_code = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, gigu_code);
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
