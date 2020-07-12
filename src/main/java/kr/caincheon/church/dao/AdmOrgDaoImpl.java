// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AdmOrgDaoImpl.java

package kr.caincheon.church.dao;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import kr.caincheon.church.common.base.CommonDao;
import kr.caincheon.church.common.base.CommonException;
import kr.caincheon.church.common.utils.UtilString;

// Referenced classes of package kr.caincheon.church.dao:
//            AdmOrgDao


@Repository("admOrgDao")
public class AdmOrgDaoImpl extends CommonDao
    implements AdmOrgDao
{
    private final Logger _logger = Logger.getLogger(getClass());
    
	@Override
    public List orgList(Map _params)
    {
		D(_logger, Thread.currentThread().getStackTrace(), "DAO orgList() Called.[params:"+_params+"]" );
		
        String query="";
        List result=null;
        
        try {
            query = "SELECT ROW_NUMBER() OVER(ORDER BY A.depart_idx) RNUM,"
            		//+ "        fnGetDepartCodeLevel(A.depart_code) AS CODE_LEVEL,"
            		+ "        fnGetDepartCodeLevel(A.depart_idx) AS CODE_LEVEL,"
            		+ "        A.depart_idx,"
            		+ "       CASE WHEN fnGetDepartCodeLevel(A.depart_idx) = 1 THEN A.NAME"
            		+ "            ELSE B.NAME"
            		+ "       END AS NAME1,"
            		+ "       CASE WHEN fnGetDepartCodeLevel(A.depart_idx) = 1 THEN ''"
            		+ "            WHEN fnGetDepartCodeLevel(A.depart_idx) = 2 THEN A.NAME"
            		+ "            ELSE C.NAME"
            		+ "       END AS NAME2,"
            		+ "       CASE WHEN fnGetDepartCodeLevel(A.depart_idx) = 1 THEN ''"
            		+ "            WHEN fnGetDepartCodeLevel(A.depart_idx) = 2 THEN ''"
            		+ "            ELSE A.NAME"
            		+ "       END AS NAME3,"
            		//+ "       A.WELFARECODE, D.CODETEXT"
            		+ "       ' ' AS WELFARECODE, ' ' AS CODETEXT"
            		+ "   FROM DEPARTMENT A"
            		+ "   LEFT OUTER JOIN DEPARTMENT B on LEFT(A.depart_idx,1)+'0000' = B.depart_idx"
            		+ "   LEFT OUTER JOIN DEPARTMENT C on LEFT(A.depart_idx,2)+'000' = C.depart_idx"
            		//+ "   LEFT OUTER JOIN CODE D on D.tableName='DEPARTMENT' AND D.columnName='welfareCode' AND D.CODEVALUE = A.WELFARECODE"
            		+ "   WHERE A.depart_code <>'' ";
            
            query = "SELECT ROW_NUMBER() OVER(ORDER BY A.NAME) RNUM "
            		+ ", A.DEPART_IDX, A.NAME, A.ORG_IDX "
            		+ ", CASE WHEN O.LV2='00'  AND O.LV3='000' THEN '1' "
            		+ "       WHEN O.LV2<>'00' AND O.LV3='000' THEN '2' "
            		+ "		  ELSE '3' "
            		+ "  END AS ORG_LEVEL "
            		+ "FROM DEPARTMENT A "
            		+ "LEFT OUTER JOIN ORG_HIERARCHY O ON O.ORG_IDX=A.ORG_IDX ";
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
    public int orgListCount(Map _params)
    {
        int result=0;
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO orgListCount() Called.[params:"+_params+"]" );
        
        String b_idx = UtilString.pnull(_params.get("b_idx"), "ALL");
        String query = "";
        String schTextGubun = UtilString.pnull(_params.get("schTextGubun"));
        String schText = UtilString.pnull(_params.get("schText"));
        String strWhere = "";
        
        if(schText.length() > 0 && !schTextGubun.equals("admin_depart"))
            strWhere = " AND A."+schTextGubun+" LIKE '%"+schText+"%' ";
        
        try {
            if(schText.length() > 0)
            {
                if(schTextGubun.equals("admin_deaprt"))
                    query = (new StringBuilder("SELECT count(1)   FROM (select ROW_NUMBER() OVER(ORDER BY a.adm_group,a.adm_id) RNUM,\t\t\t   a.adm_id, a.adm_name"
                    		+ ",min(b.depart_code) AS depart_code, a.adm_group\t\t "
                    		+ " from ADMMEMBER a\t\t  left outer join ADMMEMBER_DEPART b on a.adm_id = b.adm_id  "
                    		+ " and b.depart_code in (select depart_code from DEPARTMENT where name like '%") + schText+"%') where 1 = 1 and b.depart_code is not null group by a.adm_group,a.adm_id, a.adm_name) A ").toString();
                else
                    query = (new StringBuilder("SELECT count(1)   FROM (select ROW_NUMBER() OVER(ORDER BY a.adm_group,a.adm_id) RNUM, a.adm_id, a.adm_name"
                    		+ ",min(b.depart_code) AS depart_code, a.adm_group  from ADMMEMBER a left outer join ADMMEMBER_DEPART b on a.adm_id = b.adm_id where 1 = 1 ")+strWhere+"\t\t group by a.adm_group,a.adm_id, a.adm_name) A ").toString();
            } else {
                query = "SELECT count(1)   FROM (select ROW_NUMBER() OVER(ORDER BY a.adm_group,a.adm_id) RNUM,  a.adm_id, a.adm_name"
                		+ ",min(b.depart_code) AS depart_code, a.adm_group from ADMMEMBER a  left outer join ADMMEMBER_DEPART b on a.adm_id = b.adm_id  where 1 = 1 group by a.adm_group,a.adm_id, a.adm_name) A ";
            }
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
    public List welfareCodeList(Map _params)
        throws CommonException
    {
        String query="";
        List result=null;
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO welfareCodeList() Called.[params:"+_params+"]" );
        
        try {
            //query = "SELECT codeValue, codeText FROM CODE  WHERE tableName='DEPARTMENT' AND columnName='welfareCode' AND useYN='Y'  ORDER BY codeValue ";
            query = "SELECT CODE_INST AS CODEVALUE, NAME AS CODETEXT FROM CODE_INSTANCE WHERE CODE='000005' AND USE_YN='Y' AND DEL_YN='N' ORDER BY ORDER_NO, CODE_INST ";
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
    public String admOrgManage(Map _params)
        throws CommonException
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO admOrgManage() Called.[params:"+_params+"]" );
    	
    	if(_params != null || _params.size() > -1)
    		throw new CommonException("서비스가 처리되 않았습니다.", "ERR-D014");
    	
        String result = "";
        String sql = "";
        
        CallableStatement cstmt = null;
        try
        {
            String query = "{call pcManOrgCode(?,?,?,?,?,?,?)}";
            cstmt = getConnection().prepareCall(query);
            cstmt.setString(1, UtilString.pnull(_params.get("query_type")));
            cstmt.setString(2, UtilString.pnull(_params.get("p_level")));
            if(UtilString.pnull(_params.get("query_type")).equals("U"))
            {
                cstmt.setString(3, UtilString.pnull(_params.get("p_orgCode")));
                cstmt.setString(4, UtilString.pnull(_params.get("p_orgName")));
                cstmt.setString(5, UtilString.pnull(_params.get("p_orgCode3")));
                cstmt.setString(6, UtilString.pnull(_params.get("welfareCode2")));
            } else {
                cstmt.setString(3, UtilString.pnull(_params.get("p_code")));
                cstmt.setString(4, UtilString.pnull(_params.get("p_name")));
                cstmt.setString(5, UtilString.pnull(_params.get("p_code3")));
                cstmt.setString(6, UtilString.pnull(_params.get("welfareCode")));
            }
            cstmt.registerOutParameter(7, 12);
            cstmt.execute();
            result = cstmt.getString(7);
            _logger.error((new StringBuilder("admOrgManage Result:")).append(result).toString());
        }
        catch(SQLException e)
        {
            throw new CommonException(e.getMessage(), "ERR-D013", e);
        } finally {
            if(cstmt != null) try { cstmt.close();} catch ( Exception e ) { e.printStackTrace(); }
        	free();
    	}
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Result:"+result );
        
        return result;
    }


}
