// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AdmPosDaoImpl.java

package kr.caincheon.church.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import kr.caincheon.church.common.base.CommonDao;
import kr.caincheon.church.common.base.CommonException;

// Referenced classes of package kr.caincheon.church.dao:
//            AdmPosDao


@Repository("admPosDao")
public class AdmPosDaoImpl extends CommonDao
    implements AdmPosDao
{

	private final Logger _logger = Logger.getLogger(getClass());
	
    public List posList(Map _params)
    {
        String query = "";
        List result  = null;
        
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        try
        {
            query = "SELECT ROW_NUMBER() OVER(ORDER BY B.CODE_INST ASC) AS RNUM "
            		+ ", A.CODE AS CODE_LEVEL, B.CODE_INST AS POS_CODE, A.NAME AS NAME1 /*code_name*/, B.NAME AS NAME2 /*code_instance_name*/ "
            		+ ", B.USE_YN "
            		+ "FROM CODE_MASTER A "
            		+ "RIGHT OUTER JOIN CODE_INSTANCE B ON A.CODE = B.CODE "
            		+ "WHERE B.CODE = '000003' "
            		;
            result = super.executeQueryList(query);
            
            D(_logger, Thread.currentThread().getStackTrace(), "schText=" + result);
        }
        catch(Exception e)
        {
        	D(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL : " + query);
        } finally {
        	
        	free();
    	}
        
        return result;
    }

    public int posListCount(Map _params)
    {
    	int c = -1;
        try {
			c = executeCount("SELECT COUNT(*) FROM CODE_INSTANCE WHERE CODE = '000003' ", false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return c;
    }

    public String admPosManage(Map _params)
        throws CommonException
    {
        String result = "";
        String sql = "";
        Connection connection = null;
        CallableStatement cstmt = null;
        try
        {
        	if(connection==null || connection.isClosed()) connection = dataSource.getConnection();
            String query = "{call newcaincheon.newcaincheon.pcManPosCode(?,?,?,?,?)}"; // TODO pcManPosCode 프로시져를 삭제해야 함.
            cstmt = connection.prepareCall(query);
            cstmt.setString(1, pnull(_params.get("query_type")));
            cstmt.setString(2, pnull(_params.get("p_level")));
            
            if(pnull(_params.get("query_type")).equals("U"))
            {
                cstmt.setString(3, pnull(_params.get("p_orgCode")));
                cstmt.setString(4, pnull(_params.get("p_orgName")));
            } else
            {
                cstmt.setString(3, pnull(_params.get("p_code")));
                cstmt.setString(4, pnull(_params.get("p_name")));
            }
            cstmt.registerOutParameter(5, 12);
            cstmt.execute();
            result = cstmt.getString(5);
            
            _logger.info((new StringBuilder("admPosManage Result:")).append(result).toString());
        }
        catch(SQLException e)
        {
            _logger.error((new StringBuilder("SQL Error:")).append(e.getMessage()).toString());
            _logger.error((new StringBuilder("SQL Error:")).append(sql).toString());
            throw new CommonException(e.getMessage(), "ERR-D013", e);
        } finally {
        	if(cstmt != null) try { cstmt.close(); } catch ( Exception e ) { e.printStackTrace(); }
            if(connection != null) try { connection.close();} catch ( Exception e ) { e.printStackTrace(); }
        	free();
    	}
        return result;
    }

    
}
