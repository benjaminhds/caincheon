// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EuchaDaoImpl.java

package kr.caincheon.church.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import kr.caincheon.church.common.base.CommonDao;
import kr.caincheon.church.common.utils.ResultSetHandler;
import kr.caincheon.church.common.utils.UtilString;

// Referenced classes of package kr.caincheon.church.dao:
//            EuchaDao
/**
 * 호출되는 URL이 없는 Controller에서 호출됨
 * @author benjamin
 * @deprecated
 */
@Repository("euchaDao")
public class EuchaDaoImpl extends CommonDao
    implements EuchaDao
{

    public EuchaDaoImpl()
    {
    }

    public List euchaList(Map _params)
    {
        String id;
        int startRnum;
        int endRnum;
        List result;
        ResultSet rs;
        _logger.info((new StringBuilder(" .... This is a DAO ....dataSource=")).append(dataSource).toString());
        _logger.info((new StringBuilder("_params=")).append(_params.toString()).toString());
        id = UtilString.pnull(_params.get("id"));
        int pageNo = Integer.parseInt(UtilString.pnull(_params.get("pageNo"), "1"));
        startRnum = (pageNo - 1) * 10 + 1;
        endRnum = pageNo * 10;
        result = new ArrayList();
        rs = null;
        String query = "";
        try
        {
            query = (new StringBuilder("SELECT * FROM  (SELECT ROW_NUMBER() OVER(ORDER BY E.APPLY_DAY DESC) RNUM, E.EUCHA_MID, E.ID, E.CHURCH_NAME, M.NAME, M.baptismalName, M.tel, E.APPROVE_YN,  CASE WHEN E.APPROVE_YN = 'Y' THEN '\uC2B9\uC778'  ELSE\t'\uBBF8\uC2B9\uC778'  END AS APPROVE_YN_TEXT  FROM newcaincheon.newcaincheon.MEMBER M, newcaincheon.newcaincheon.EUCHA_APPLY_MASTER E WHERE E.ID='")).append(id).append("' AND E.ID=M.ID) ROWS").append(" WHERE RNUM BETWEEN ").append(startRnum).append(" AND ").append(endRnum).toString();
            _logger.info((new StringBuilder("query=")).append(query).toString());
            HashMap rsMap;
            for(rs = super.executeQuery(query); rs.next(); result.add(rsMap))
            {
                rsMap = new HashMap();
                rsMap.put("RNUM", Integer.valueOf(rs.getInt(1)));
                rsMap.put("EUCHA_MID", rs.getString(2));
                rsMap.put("ID", rs.getString(3));
                rsMap.put("CHURCH_NAME", rs.getString(4));
                rsMap.put("NAME", rs.getString(5));
                rsMap.put("BAPTISMAL_NAME", rs.getString(6));
                rsMap.put("TEL", rs.getString(7));
                rsMap.put("APPROVE_YN", rs.getString(8));
                rsMap.put("APPROVE_YN_TEXT", rs.getString(9));
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

    public int euchaListCount(Map _params)
    {
        String id;
        int result;
        ResultSet rs;
        _logger.info((new StringBuilder(" .... This is a DAO ....dataSource=")).append(dataSource).toString());
        _logger.info((new StringBuilder("_params=")).append(_params.toString()).toString());
        id = UtilString.pnull(_params.get("id"));
        result = 0;
        rs = null;
        String query = "";
        try
        {
            query = (new StringBuilder("SELECT COUNT(1) FROM  (SELECT ROW_NUMBER() OVER(ORDER BY E.APPLY_DAY DESC) RNUM, E.EUCHA_MID, E.ID, E.CHURCH_NAME, M.NAME, M.baptismalName, M.tel, E.APPROVE_YN,  CASE WHEN E.APPROVE_YN = 'Y' THEN '\uC2B9\uC778'  ELSE\t'\uBBF8\uC2B9\uC778'  END AS APPROVE_YN_TEXT  FROM newcaincheon.newcaincheon.MEMBER M, newcaincheon.newcaincheon.EUCHA_APPLY_MASTER E WHERE E.ID='")).append(id).append("' AND E.ID=M.ID) ROWS").append(" WHERE 1 = 1 ").toString();
            _logger.info((new StringBuilder("query=")).append(query).toString());
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

    public Map euchaContents(Map _params)
    {
        Map result;
        ResultSet rs;
        String eucha_mid;
        _logger.info((new StringBuilder("_params=")).append(_params.toString()).toString());
        result = new HashMap();
        rs = null;
        String id = UtilString.pnull(_params.get("id"));
        eucha_mid = UtilString.pnull(_params.get("eucha_no"));
        try
        {
            String where = (new StringBuilder("AND EUCHA_MID = '")).append(eucha_mid).append("' AND E.ID=M.ID").toString();
            String query = (new StringBuilder("SELECT M.NAME, M.BAPTISMALNAME,M.ID AS EMAIL,M.TEL,  E.EUCHA_MID, 0 AS EUCHA_SNO, E.ID, E.APPROVE_YN, E.CHURCH_NAME, CONVERT(CHAR(10), E.APPLY_DAY, 120) APPLY_DAY, E.APPLY_NAME, E.ADMIN_COMMENT  FROM newcaincheon.newcaincheon.MEMBER M, newcaincheon.newcaincheon.EUCHA_APPLY_MASTER E WHERE 1 =1 ")).append(where).toString();
            _logger.info((new StringBuilder("query=")).append(query).toString());
            rs = super.executeQuery(query);
            result = ResultSetHandler.conversionFirstRow(rs);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        } finally {
        	freeResultSet(rs);
        	free();
    	}
        
        _logger.info((new StringBuilder("[MASTER QUERY] result=")).append(result.toString()).toString());
        List sresult = null;
        try
        {
            String where = (new StringBuilder("AND EUCHA_MID = '")).append(eucha_mid).append("'").toString();
            String query = (new StringBuilder("SELECT EUCHA_SNO, EUCHA_MID, MASTER_ID, GUBUN, ID, NAME, BAPTISMAL_NAME,  CONVERT(CHAR(10), BIRTHDAY, 120) BIRTHDAY, M_TEL, BEFORE_NUMBER, ORDER_NAME  FROM newcaincheon.newcaincheon.EUCHA_APPLY_SLAVE WHERE 1 = 1 ")).append(where).toString();
            _logger.info((new StringBuilder("query=")).append(query).toString());
            rs = super.executeQuery(query);
            sresult = ResultSetHandler.conversion(rs);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        } finally {
        	freeResultSet(rs);
        	free();
    	}
        _logger.info((new StringBuilder("[SLAVE QUERY] result=")).append(sresult.toString()).toString());
        result.put("slave", sresult);
        _logger.info((new StringBuilder("[QUERY] result=")).append(result.toString()).toString());
        return result;
    }

    public boolean euchaInsert(Map _params)
    {
        int rn;
        String master_id;
        String church_name;
        String apply_name;
        String admin_comment;
        Connection connection;
        PreparedStatement preparedStatement;
        String eucha_mid;
        boolean bReturn = true;
        rn = 0;
        _logger.info((new StringBuilder(" .... This is a DAO ....dataSource=")).append(dataSource).toString());
        _logger.info((new StringBuilder("_params=")).append(_params.toString()).toString());
        master_id = UtilString.pnull(_params.get("id"));
        Map memberInfo = getMember(_params);
        if(memberInfo == null)
            return false;
        church_name = UtilString.pnull(_params.get("church_name"));
        apply_name = UtilString.pnull(memberInfo.get("NAME"));
        admin_comment = "";
        connection = null;
        preparedStatement = null;
        UUID uuid = UUID.randomUUID();
        eucha_mid = uuid.toString();
        try
        {
        	if(connection==null || connection.isClosed()) connection = dataSource.getConnection();
            String query = "INSERT INTO newcaincheon.newcaincheon.EUCHA_APPLY_MASTER  (eucha_mid, id, approve_yn, church_name, apply_day, apply_name, admin_comment, updateDT)  VALUES (?, ?, 'N', ?, getdate(), ?, ?, getdate())";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, eucha_mid);
            preparedStatement.setString(2, master_id);
            preparedStatement.setString(3, church_name);
            preparedStatement.setString(4, apply_name);
            preparedStatement.setString(5, admin_comment);
            rn = preparedStatement.executeUpdate();
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
        
        try
        {
        	if(connection==null || connection.isClosed()) connection = dataSource.getConnection();
            String query = "INSERT INTO newcaincheon.newcaincheon.EUCHA_APPLY_SLAVE  (eucha_mid, master_id, gubun, id, name, baptismal_name, birthday, m_tel, before_number, order_name, updateDT)  VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, getdate())";
            preparedStatement = connection.prepareStatement(query);
            for(int i = 1; i <= 20; i++)
                if(i < 10)
                {
                    if(!UtilString.pnull(_params.get((new StringBuilder("s_gubun0")).append(i).toString())).equals(""))
                    {
                        preparedStatement.setString(1, eucha_mid);
                        preparedStatement.setString(2, master_id);
                        preparedStatement.setString(3, UtilString.pnull(_params.get((new StringBuilder("s_gubun0")).append(i).toString())));
                        preparedStatement.setString(4, UtilString.pnull(_params.get((new StringBuilder("s_id0")).append(i).toString())));
                        preparedStatement.setString(5, UtilString.pnull(_params.get((new StringBuilder("s_name0")).append(i).toString())));
                        preparedStatement.setString(6, UtilString.pnull(_params.get((new StringBuilder("s_baptismal_name0")).append(i).toString())));
                        preparedStatement.setString(7, UtilString.pnull(_params.get((new StringBuilder("s_birthday0")).append(i).toString())));
                        preparedStatement.setString(8, UtilString.pnull(_params.get((new StringBuilder("s_m_tel0")).append(i).toString())));
                        preparedStatement.setString(9, UtilString.pnull(_params.get((new StringBuilder("s_before_number0")).append(i).toString())));
                        preparedStatement.setString(10, UtilString.pnull(_params.get((new StringBuilder("s_order_name0")).append(i).toString())));
                        rn += preparedStatement.executeUpdate();
                    }
                } else
                if(!UtilString.pnull(_params.get((new StringBuilder("s_gubun")).append(i).toString())).equals(""))
                {
                    preparedStatement.setString(1, eucha_mid);
                    preparedStatement.setString(2, master_id);
                    preparedStatement.setString(3, UtilString.pnull(_params.get((new StringBuilder("s_gubun")).append(i).toString())));
                    preparedStatement.setString(4, UtilString.pnull(_params.get((new StringBuilder("s_id")).append(i).toString())));
                    preparedStatement.setString(5, UtilString.pnull(_params.get((new StringBuilder("s_name")).append(i).toString())));
                    preparedStatement.setString(6, UtilString.pnull(_params.get((new StringBuilder("s_baptismal_name")).append(i).toString())));
                    preparedStatement.setString(7, UtilString.pnull(_params.get((new StringBuilder("s_birthday")).append(i).toString())));
                    preparedStatement.setString(8, UtilString.pnull(_params.get((new StringBuilder("s_m_tel")).append(i).toString())));
                    preparedStatement.setString(9, UtilString.pnull(_params.get((new StringBuilder("s_before_number")).append(i).toString())));
                    preparedStatement.setString(10, UtilString.pnull(_params.get((new StringBuilder("s_order_name")).append(i).toString())));
                    rn += preparedStatement.executeUpdate();
                }

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
        return rn > 1;
    }

    public boolean euchaModify(Map _params)
    {
        int rn;
        String member_id;
        String church_name;
        Connection connection;
        PreparedStatement preparedStatement;
        boolean bReturn = true;
        rn = 0;
        _logger.info((new StringBuilder(" .... This is a DAO (modify)....dataSource=")).append(dataSource).toString());
        _logger.info((new StringBuilder("_params=")).append(_params.toString()).toString());
        member_id = UtilString.pnull(_params.get("id"));
        church_name = UtilString.pnull(_params.get("church_name"));
        String admin_comment = "";
        connection = null;
        preparedStatement = null;
        try
        {
        	if(connection==null || connection.isClosed()) connection = dataSource.getConnection();
            String query = "UPDATE newcaincheon.newcaincheon.EUCHA_APPLY_MASTER  SET church_name = ?,  apply_day = getdate(), updateDT = getdate()  WHERE eucha_mid = ? and id= ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, church_name);
            preparedStatement.setString(2, UtilString.pnull(_params.get("eucha_mid")));
            preparedStatement.setString(3, member_id);
            _logger.info((new StringBuilder("query=")).append(query).toString());
            rn = preparedStatement.executeUpdate();
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
        
        
        String eucha_sno = "1";
        try
        {
        	if(connection==null || connection.isClosed()) connection = dataSource.getConnection();
            String query = "UPDATE newcaincheon.newcaincheon.EUCHA_APPLY_SLAVE  SET gubun = ?, id = ?,  name = ?, baptismal_name = ?, birthday = ?, m_tel = ?,  before_number = ?, order_name = ?, updateDT = getdate()  WHERE eucha_mid = ? and eucha_sno = ?";
            preparedStatement = connection.prepareStatement(query);
            for(int i = 1; i <= 20; i++)
                if(i < 10)
                {
                    if(!UtilString.pnull(_params.get((new StringBuilder("s_gubun0")).append(i).toString())).equals(""))
                    {
                        preparedStatement.setString(1, UtilString.pnull(_params.get((new StringBuilder("s_gubun0")).append(i).toString())));
                        preparedStatement.setString(2, UtilString.pnull(_params.get((new StringBuilder("s_id0")).append(i).toString())));
                        preparedStatement.setString(3, UtilString.pnull(_params.get((new StringBuilder("s_name0")).append(i).toString())));
                        preparedStatement.setString(4, UtilString.pnull(_params.get((new StringBuilder("s_baptismal_name0")).append(i).toString())));
                        preparedStatement.setString(5, UtilString.pnull(_params.get((new StringBuilder("s_birthday0")).append(i).toString())));
                        preparedStatement.setString(6, UtilString.pnull(_params.get((new StringBuilder("s_m_tel0")).append(i).toString())));
                        preparedStatement.setString(7, UtilString.pnull(_params.get((new StringBuilder("s_before_number0")).append(i).toString())));
                        preparedStatement.setString(8, UtilString.pnull(_params.get((new StringBuilder("s_order_name0")).append(i).toString())));
                        preparedStatement.setString(9, UtilString.pnull(_params.get("eucha_mid")));
                        preparedStatement.setString(10, UtilString.pnull(_params.get((new StringBuilder("eucha_sno0")).append(i).toString())));
                        rn += preparedStatement.executeUpdate();
                    }
                } else
                if(!UtilString.pnull(_params.get((new StringBuilder("s_gubun")).append(i).toString())).equals(""))
                {
                    preparedStatement.setString(1, UtilString.pnull(_params.get((new StringBuilder("s_gubun")).append(i).toString())));
                    preparedStatement.setString(2, UtilString.pnull(_params.get((new StringBuilder("s_id")).append(i).toString())));
                    preparedStatement.setString(3, UtilString.pnull(_params.get((new StringBuilder("s_name")).append(i).toString())));
                    preparedStatement.setString(4, UtilString.pnull(_params.get((new StringBuilder("s_baptismal_name")).append(i).toString())));
                    preparedStatement.setString(5, UtilString.pnull(_params.get((new StringBuilder("s_birthday")).append(i).toString())));
                    preparedStatement.setString(6, UtilString.pnull(_params.get((new StringBuilder("s_m_tel")).append(i).toString())));
                    preparedStatement.setString(7, UtilString.pnull(_params.get((new StringBuilder("s_before_number")).append(i).toString())));
                    preparedStatement.setString(8, UtilString.pnull(_params.get((new StringBuilder("s_order_name")).append(i).toString())));
                    preparedStatement.setString(9, UtilString.pnull(_params.get("eucha_mid")));
                    preparedStatement.setString(10, UtilString.pnull(_params.get((new StringBuilder("eucha_sno")).append(i).toString())));
                    rn += preparedStatement.executeUpdate();
                }

            _logger.info((new StringBuilder("query=")).append(query).toString());
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
        return rn > 1;
    }

    public boolean euchaDelete(Map _params)
    {
        boolean bReturn;
        String eucha_mid;
        Connection connection;
        PreparedStatement preparedStatement;
        bReturn = true;
        _logger.info((new StringBuilder(" .... This is a DAO ....dataSource=")).append(dataSource).toString());
        _logger.info((new StringBuilder("euchaDelete..._params=")).append(_params.toString()).toString());
        eucha_mid = UtilString.pnull(_params.get("eucha_mid"));
        connection = null;
        preparedStatement = null;
        try
        {
        	if(connection==null || connection.isClosed()) connection = dataSource.getConnection();
            String query = "delete from newcaincheon.newcaincheon.EUCHA_APPLY_MASTER where eucha_mid = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, Integer.parseInt(eucha_mid));
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
        
        try
        {
        	if(connection==null || connection.isClosed()) connection = dataSource.getConnection();
            String query = "delete from newcaincheon.newcaincheon.EUCHA_APPLY_SLAVE where eucha_mid = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, Integer.parseInt(eucha_mid));
            int j = preparedStatement.executeUpdate();
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
            String query = (new StringBuilder("SELECT NAME, BAPTISMALNAME, MEMBERTYPE, CHURCH_IDX FROM newcaincheon.newcaincheon.MEMBER WHERE ID='")).append(UtilString.pnull(_params.get("id"))).append("' ").toString();
            rs = super.executeQuery(query);
            if(rs.next())
            {
                result.put("NAME", rs.getString(1));
                result.put("BAPTISMALNAME", rs.getString(2));
                result.put("MEMBERTYPE", rs.getString(3));
                result.put("CHURCH_IDX", rs.getString(4));
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

    private final Logger _logger = Logger.getLogger(getClass());
}
