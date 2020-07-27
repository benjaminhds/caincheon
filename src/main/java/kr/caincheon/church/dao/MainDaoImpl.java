// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MainDaoImpl.java

package kr.caincheon.church.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import kr.caincheon.church.common.base.CommonDao;

// Referenced classes of package kr.caincheon.church.dao:
//            MainDao

@Repository("mainDao")
public class MainDaoImpl extends CommonDao
    implements MainDao
{

	private final Logger _logger = Logger.getLogger(getClass());
	
	@Override
    public Map todayContents()
    {
        Map result;
        ResultSet rs;
        result = new HashMap();
        rs = null;
        try
        {
            String query = "SELECT CONVERT(CHAR(10),A.H_DATE,102), A.DESCRIPTION, A.TALK,  LEFT(CONVERT(CHAR(10),A.H_DATE,2),5) YYMM,  (datepart(ww, A.H_DATE) - datepart(ww, convert(datetime, left(convert(varchar, A.H_DATE, 112), 6) + '01', 112)) + 1) WEEK  FROM HOLIDAY A  WHERE CONVERT(CHAR(10),H_DATE,121) = CONVERT(CHAR(10),GETDATE(),121)";
            rs = super.executeQuery(query);
            if(rs.next())
            {
                result.put("H_DATE", rs.getString(1));
                result.put("DESCRIPTION", rs.getString(2));
                result.put("TALK", rs.getString(3));
                result.put("YYMM", rs.getString(4));
                result.put("WEEK", rs.getString(5));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        } finally {
        	freeResultSet(rs);
        	free();
    	}
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Result:"+result );
        return result;
    }

    @Override
    public List noticeList(String b_idx)
    {
        List result = new ArrayList();
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params -> b_idx:"+b_idx+"]" );
        
        String query = "";
        try {
        	String query_b_idx = "";
            if(b_idx.equals("ALL") || b_idx.length() == 0)
            	query_b_idx = " B_IDX IN (9, 12, 10, 12) ";
            else if(b_idx.length() > 0)
            	query_b_idx = " B_IDX IN ("+b_idx+") ";
            
            String topFixedSelect = 
            		  "SELECT TOP 2 '1' AS GUBUN, BL_IDX, B_IDX, TITLE,  REGDATE, C_IDX, CHURCH_IDX, DEPART_IDX"
            		+ " FROM NBOARD WHERE "+query_b_idx+" AND IS_VIEW='Y' AND IS_NOTICE='Y'"
            		+ " ORDER BY REGDATE DESC"
            		;
            String topFixedSelect2 = 
          		  "SELECT TOP 2 BL_IDX"
	          		+ " FROM NBOARD WHERE "+query_b_idx+" AND IS_VIEW='Y' AND IS_NOTICE='Y'"
	          		+ " ORDER BY REGDATE DESC"
	          		;
            
            query = "SELECT BL_IDX, A.B_IDX, A.TITLE"
            		+ ", ISNULL(B.NAME, '') AS CATEGORY_NAME, ISNULL(C.NAME, '') AS CHURCH_NAME, ISNULL(D.NAME, '') AS DEPART_NAME, A.GUBUN "
            		+ ", RIGHT(CONVERT(CHAR(8), A.REGDATE, 2),5) AS REGDATE"
            		+ ", DATEDIFF(DAY, REGDATE, GETDATE() ) AS DATEDIFF"
            		+ " FROM ( "
            		+   topFixedSelect
            		+ " UNION "
            		+ " ( SELECT TOP 4 '2' AS GUBUN,  BL_IDX, B_IDX, TITLE, REGDATE, C_IDX, CHURCH_IDX, DEPART_IDX"
            		+ " FROM NBOARD WHERE "+query_b_idx+" AND IS_VIEW='Y' AND IS_NOTICE='N' AND BL_IDX NOT IN ("+topFixedSelect2+") "
            		+ " ORDER BY REGDATE DESC "
            		+ " ) "
            		+ ") A "
            		+ " LEFT JOIN NBOARD_CATEGORY B ON A.C_IDX=B.C_IDX "
            		+ " LEFT JOIN CHURCH     C ON A.CHURCH_IDX=C.CHURCH_IDX "
            		+ " LEFT JOIN DEPARTMENT D ON A.DEPART_IDX=D.DEPART_IDX";
            
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
    public List parishList()
    {
        List result = new ArrayList();
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called." );
        String query = "";
        
        try
        {
        	// 삭제 대상 
        	query = "SELECT A.BL_IDX, A.B_IDX, CASE WHEN ISNULL(D.NAME, '')='' THEN '[\uAD50\uAD6C]'+ A.TITLE ELSE '['+ISNULL(D.NAME, '')+']'+A.TITLE END AS TITLE"
        			+ ", A.REGDATE, A.DATEDIFF, ISNULL(B.NAME, '') AS CATEGORY_NAME,  ISNULL(C.NAME, '') AS CHURCH_NAME,  ISNULL(D.NAME, '') AS DEPART_NAME, A.GUBUN  FROM "
        			+ "("
        			+ " SELECT TOP 2 '1' AS GUBUN, ROW_NUMBER() OVER(ORDER BY REGDATE DESC) RNUM, BL_IDX, B_IDX, TITLE,  RIGHT(CONVERT(char(8), REGDATE, 2),5) AS REGDATE,  DATEDIFF(day, REGDATE, getdate() ) AS DATEDIFF,  C_IDX, CHURCH_IDX, DEPART_IDX "
        			+ " FROM NBOARD "
        			+ " WHERE B_IDX='12' AND  IS_NOTICE='Y' "
        			+ " UNION "
        			+ " SELECT TOP 4 '2' AS GUBUN, ROW_NUMBER() OVER(ORDER BY REGDATE DESC) RNUM, BL_IDX, B_IDX, TITLE,  RIGHT(CONVERT(char(8), REGDATE, 2),5) AS REGDATE,  DATEDIFF(day, REGDATE, getdate() ) AS DATEDIFF,  C_IDX, CHURCH_IDX, DEPART_IDX "
        			+ " FROM NBOARD "
        			+ " WHERE B_IDX='12' AND IS_SECRET='N' AND IS_VIEW='Y' OR B_IDX='12' AND DEPART_IDX='0' AND IS_SECRET='N' AND IS_VIEW='Y'"
        			+ ") A "
        			+ " LEFT JOIN NBOARD_CATEGORY B ON A.C_IDX=B.C_IDX "
        			+ " LEFT JOIN CHURCH C ON A.CHURCH_IDX=C.CHURCH_IDX "
        			+ " LEFT JOIN DEPARTMENT D ON A.DEPART_IDX=D.DEPART_IDX";
        	// 신규 쿼리
        	query = "SELECT B.*  , M.B_NM, M.C_NM, M.C_IDX, O.NAME AS ORG_NM\r\n" + 
        			"FROM (\r\n" + 
        			"  SELECT TOP  2 ROW_NUMBER() OVER(ORDER BY B.REGDATE DESC) R$NUM, B.B_IDX, B.C_IDX, B.BL_IDX, B.TITLE, B.ORG_IDX, B.CHURCH_IDX, B.DEPART_IDX, B.REGDATE\r\n" + 
        			"  FROM MBOARD B, (SELECT B_IDX, B_NM, B_TYPE FROM MBOARD_MNGT WHERE B_NM='교구소식') M\r\n" + 
        			"  WHERE B.B_IDX = M.B_IDX AND B.IS_NOTICE='Y' AND B.IS_VIEW='Y'\r\n" + 
        			"  UNION ALL\r\n" + 
        			"  SELECT TOP  4 ROW_NUMBER() OVER(ORDER BY B.REGDATE DESC) R$NUM, B.B_IDX, B.C_IDX, B.BL_IDX, B.TITLE, B.ORG_IDX, B.CHURCH_IDX, B.DEPART_IDX, B.REGDATE\r\n" + 
        			"  FROM MBOARD B, (SELECT B_IDX, B_NM, B_TYPE FROM MBOARD_MNGT WHERE B_NM='교구소식') M\r\n" + 
        			"  WHERE B.B_IDX = M.B_IDX AND B.IS_NOTICE='N' AND B.IS_VIEW='Y'\r\n" + 
        			"  UNION ALL\r\n" + 
        			"  SELECT TOP 10 ROW_NUMBER() OVER(ORDER BY B.REGDATE DESC) R$NUM, B.B_IDX, B.C_IDX, B.BL_IDX, B.TITLE, B.ORG_IDX, B.CHURCH_IDX, B.DEPART_IDX, B.REGDATE\r\n" + 
        			"  FROM MBOARD B, (SELECT B_IDX, B_NM, B_TYPE FROM MBOARD_MNGT WHERE B_NM='교구앨범') M\r\n" + 
        			"  WHERE B.B_IDX = M.B_IDX AND B.IS_NOTICE='N' AND B.IS_VIEW='Y'\r\n" + 
        			"  UNION ALL\r\n" + 
        			"  SELECT TOP  5 ROW_NUMBER() OVER(ORDER BY B.REGDATE DESC) R$NUM, B.B_IDX, B.C_IDX, B.BL_IDX, B.TITLE, B.ORG_IDX, B.CHURCH_IDX, B.DEPART_IDX, B.REGDATE\r\n" + 
        			"  FROM MBOARD B, (SELECT B_IDX, B_NM, B_TYPE FROM MBOARD_MNGT WHERE B_NM='교구영상') M\r\n" + 
        			"  WHERE B.B_IDX = M.B_IDX AND B.IS_NOTICE='N' AND B.IS_VIEW='Y'\r\n" + 
        			"  UNION ALL\r\n" + 
        			"  SELECT TOP  5 ROW_NUMBER() OVER(ORDER BY B.REGDATE DESC) R$NUM, B.B_IDX, B.C_IDX, B.BL_IDX, B.TITLE, B.ORG_IDX, B.CHURCH_IDX, B.DEPART_IDX, B.REGDATE\r\n" + 
        			"  FROM MBOARD B, (SELECT B_IDX, B_NM, B_TYPE FROM MBOARD_MNGT WHERE B_NM='교회소식') M\r\n" + 
        			"  WHERE B.B_IDX = M.B_IDX AND B.IS_NOTICE='N' AND B.IS_VIEW='Y'\r\n" + 
        			"  UNION ALL\r\n" + 
        			"  SELECT TOP  5 ROW_NUMBER() OVER(ORDER BY B.REGDATE DESC) R$NUM, B.B_IDX, B.C_IDX, B.BL_IDX, B.TITLE, B.ORG_IDX, B.CHURCH_IDX, B.DEPART_IDX, B.REGDATE\r\n" + 
        			"  FROM MBOARD B, (SELECT B_IDX, B_NM, B_TYPE FROM MBOARD_MNGT WHERE B_NM='교구일정') M\r\n" + 
        			"  WHERE B.B_IDX = M.B_IDX AND B.IS_NOTICE='N' AND B.IS_VIEW='Y'\r\n" + 
        			"  UNION ALL\r\n" + 
        			"  SELECT TOP  5 ROW_NUMBER() OVER(ORDER BY B.REGDATE DESC) R$NUM, B.B_IDX, B.C_IDX, B.BL_IDX, B.TITLE, B.ORG_IDX, B.CHURCH_IDX, B.DEPART_IDX, B.REGDATE\r\n" + 
        			"  FROM MBOARD B, (SELECT B_IDX, B_NM, B_TYPE FROM MBOARD_MNGT WHERE B_NM='교구정동정') M\r\n" + 
        			"  WHERE B.B_IDX = M.B_IDX AND B.IS_NOTICE='N' AND B.IS_VIEW='Y'\r\n" + 
        			"  UNION ALL\r\n" + 
        			"  SELECT TOP  5 ROW_NUMBER() OVER(ORDER BY B.REGDATE DESC) R$NUM, B.B_IDX, B.C_IDX, B.BL_IDX, B.TITLE, B.ORG_IDX, B.CHURCH_IDX, B.DEPART_IDX, B.REGDATE\r\n" + 
        			"  FROM MBOARD B, (SELECT B_IDX, B_NM, B_TYPE FROM MBOARD_MNGT WHERE B_NM='인천주보') M\r\n" + 
        			"  WHERE B.B_IDX = M.B_IDX AND B.IS_NOTICE='N' AND B.IS_VIEW='Y'\r\n" + 
        			") B LEFT OUTER JOIN \r\n" + 
        			"  (SELECT M.B_IDX, M.B_NM, M.B_TYPE, C.C_IDX, C.NAME AS C_NM\r\n" + 
        			"   FROM MBOARD_MNGT M LEFT OUTER JOIN MBOARD_CATEGORY C ON M.B_IDX = C.B_IDX ) M ON B.B_IDX = M.B_IDX AND B.C_IDX = M.C_IDX\r\n" + 
        			"  LEFT OUTER JOIN ORG_HIERARCHY O  ON O.ORG_IDX = B.ORG_IDX"
        			;
        	result = super.executeQueryList(query);
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        	free();
    	}
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Result:"+result );
        return result;
    }

    /*
     * 이달의 사제
     * (non-Javadoc)
     * @see kr.caincheon.church.dao.MainDao#priestListOfThisMonth()
     */
    @Override
    public List priestListOfThisMonth()
    {
        List result = null;
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called." );
        String query = "";
        
        try {
        	// TOP3를 랜덤으로 추출하여 조회
        	query = "SELECT * /*이달의사제*/ FROM ( "
            		+ " SELECT TOP 3 P.P_IDX, P.ONUM, P.GUBUN, P.NAME, P.CHRISTIAN_NAME, ISNULL(P.PHRASE,'') AS PHRASE "
            		+ " FROM PRIEST P "
            		+ " INNER JOIN ORG_DEPARTMENT_PRIEST_REL R ON R.P_IDX=P.P_IDX AND R.ORG_IDX not in (254) /*정직제외*/ and MAIN_ASSIGN_YN='Y' "
            		+ " WHERE 1 = 1 AND GUBUN = '1' "
            		+ " AND NEW_BIRTHDAY like SUBSTRING(CONVERT(CHAR(7), getdate(), 120),6,7)+'%' "
            		+ " ORDER BY NEWID(), P.ONUM , P.P_IDX "
            		+ " ) A  ORDER BY ONUM, P_IDX ";
        	result = super.executeQueryList(query);
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        	free();
    	}
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Result:"+result );
        return result;
    }

    @Override
    public List parList_Main()
    {
        String query = "";
        List result = null;

        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called." );
        
        try
        {
        	query = "SELECT TOP(4) A.RNUM, A.B_IDX, A.NOTICE_TYPE, A.BL_IDX, A.TITLE, LEFT(CAST(A.CONTENT AS NVARCHAR(MAX)), 100) "
        			+ " FROM ("
        			+ "  SELECT ROW_NUMBER() OVER(ORDER BY NOTICE_TYPE, REGDATE DESC) RNUM, B_IDX,  NOTICE_TYPE, BL_IDX, TITLE, CONTENT, USER_ID, WRITER"
        			+ ", CONVERT(CHAR(10),  REGDATE, 120) REGDATE, HIT, C_IDX, CHURCH_IDX, DEPART_IDX, IS_SECRET"
        			+ ", (SELECT COUNT(*) FROM NBOARD_UPLOAD A3 WHERE A1.BL_IDX=A3.BL_IDX AND IS_USE='Y') FILE_CNT"
//        			+ ", (SELECT COUNT(*) FROM NBOARD_MEMO   A2 WHERE A1.BL_IDX=A2.BL_IDX) MEMO_CNT  "
        			+ " FROM "
        				+ "( SELECT TOP 2 '1' AS NOTICE_TYPE, A.* FROM  (SELECT top 2 *  FROM NBOARD  WHERE B_IDX = 61 AND  IS_NOTICE='Y' ORDER BY BL_IDX DESC) A "
        				+ " UNION ALL "
        				+ " SELECT '2' AS NOTICE_TYPE, * FROM NBOARD "
        				+ " WHERE B_IDX = 61 AND BL_IDX NOT IN (SELECT TOP 2 BL_IDX FROM NBOARD WHERE B_IDX = 61 AND  IS_NOTICE='Y' ORDER BY BL_IDX DESC)  "
        				+ ") A1 "
        			+ " ) A  LEFT JOIN NBOARD_CATEGORY B ON A.C_IDX=B.C_IDX ";
            result = super.executeQueryList(query);
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        	free();
    	}

        D(_logger, Thread.currentThread().getStackTrace(), "DAO Result:"+result );
        
        return result;
    }

    /*
     * 주별 교구 일정을 조회
     * (non-Javadoc)
     * @see kr.caincheon.church.dao.MainDao#schList_Main(java.util.Map)
     */
    @Override
    public Map<String , Object> schList_Main(Map _params)
    {
    	Map<String , Object> result = new HashMap<String, Object>();
    	
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called." );
        
        String query = "";
        
        // 전주 (-1), 전전주(-2), 다음주(+1), 다다음주(+2), 이번주(값없음 혹은 0)
        String srchweek = pnull(_params, "srchweek", "0");
        if(srchweek.indexOf("-")==-1) {
        	srchweek = "+"+srchweek;
        }
        
        try {
        	//
        	query = " SELECT ROW_NUMBER() OVER(ORDER BY START_DATE DESC) RNUM, S_IDX,GUBUN,TITLE,RIGHT(CONVERT(CHAR(8), START_DATE, 2),5) AS START_DATE "
            		+ " FROM SCHEDULE "
            		+ " WHERE START_DATE BETWEEN CONVERT(VARCHAR, GETDATE() + 7*(0"+srchweek+") - (DATEPART(DW, CONVERT(VARCHAR, GETDATE(), 112)) - 1), 112) "
            		+ "                      AND CONVERT(VARCHAR, GETDATE() + 7*(1"+srchweek+") - (DATEPART(DW, CONVERT(VARCHAR, GETDATE(), 112))), 112)"
					;
            query = "SELECT A.* FROM ("
            		+ query
            		+ ") A "
            		+ " WHERE RNUM BETWEEN 1 AND 4 "
            		+ " ORDER BY START_DATE ASC ";
            List list = super.executeQueryList(query);
            result.put("LIST", list);
            
            //
            query = "SELECT CONVERT(VARCHAR(6), CONVERT(datetime, CONVERT(VARCHAR, GETDATE() + 7*(0"+srchweek+") - (DATEPART(DW, CONVERT(VARCHAR, GETDATE(), 112)) - 1), 112), 112) , 2)"
            		+ " + CAST( "
            		+ "      (DAY(convert(varchar(10), CONVERT(VARCHAR, GETDATE() + 7*(0"+srchweek+") - (DATEPART(DW, CONVERT(VARCHAR, GETDATE(), 112)) - 1), 112) ,120)) "
            		+ "       + (DATEPART(dw, DATEADD (MONTH, DATEDIFF (MONTH, 0,convert(varchar(10), CONVERT(VARCHAR, GETDATE() + 7*(0"+srchweek+") "
            		+ "       - (DATEPART(DW, CONVERT(VARCHAR, GETDATE(), 112)) - 1), 112) ,120)), 0)) -1) -1)/7   +  1 "
            		+ "   AS VARCHAR) "
            		+ " + '주' AS THISWEEK ";
            Object cul = executeColumnOne(query, 1);
            result.put("WEEK", cul);
            
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        	free();
    	}
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Result:"+result );
        
        return result;
    }

    
}
