<<<<<<< HEAD
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HomeDaoImpl.java

package kr.caincheon.church.main;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import kr.caincheon.church.common.base.AbstractDAO;

/**
 * 
 * @version		1.0
 */
@Repository("homeDAO")
public class HomeDAO extends AbstractDAO
{
	private final Logger _logger = Logger.getLogger(getClass());

	/**
	 * 여러개의 게시판을 한번에 조회하는 쿼리
	 * @return List
	 */
	public Map getTodayContents() {
		Map result = null;
		
		result = selectOneRow("front.home.get_today_contents");
		
		return result;
	}

	/**
	 * 여러개의 게시판을 한번에 조회하는 쿼리
	 * @return List
	 */
	public List homeMultiboardLists() {
		List result = null;
		
		result = selectList("front.home.get_multi_board");
		
		return result;
	}
	
	//
    public List noticeList()
    {
        List result;
        ResultSet rs;
        _logger.debug(" .... This is a DAO ....");
        
        result = new ArrayList();
        rs = null;
        try
        {
            String query = "SELECT A.BL_IDX, A.B_IDX, A.TITLE, A.REGDATE, A.DATEDIFF,\t   ISNULL(B.NAME, '') AS CATEGORY_NAME, \t   ISNULL(C.NAME, '') AS CHURCH_NAME, \t   ISNULL(D.NAME, '') AS DEPART_NAME   FROM (SELECT TOP 4 BL_IDX, B_IDX, TITLE, \t\t\t   RIGHT(CONVERT(char(8), REGDATE, 2),5) AS REGDATE, \t\t\t   DATEDIFF(day, REGDATE, getdate() ) AS DATEDIFF, \t\t\t   C_IDX, CHURCH_IDX, DEPART_IDX           FROM newcaincheon.newcaincheon.NBOARD \t\t WHERE B_IDX='9' \t\t  AND IS_SECRET='N' \t\t  AND IS_VIEW='Y' OR B_IDX='12' \t\t  AND DEPART_IDX='0' AND IS_SECRET='N' AND IS_VIEW='Y' ORDER BY BL_IDX DESC) A LEFT JOIN newcaincheon.newcaincheon.NBOARD_CATEGORY B ON A.C_IDX=B.C_IDX  LEFT JOIN newcaincheon.newcaincheon.CHURCH C ON A.CHURCH_IDX=C.CHURCH_IDX  LEFT JOIN newcaincheon.newcaincheon.DEPARTMENT D ON A.DEPART_IDX=D.DEPART_IDX";
            HashMap notice;
//            for(rs = super.executeQuery(query); rs.next(); System.out.println(notice.toString()))
//            {
//                notice = new HashMap();
//                notice.put("BL_IDX", rs.getString(1));
//                notice.put("B_IDX", rs.getString(2));
//                notice.put("TITLE", rs.getString(3));
//                notice.put("REGDATE", rs.getString(4));
//                result.add(notice);
//            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        } finally {
//        	freeResultSet(rs);
//        	free();
    	}
        return result;
    }

    public List parishList()
    {
        List result;
        ResultSet rs;
        _logger.debug(" .... This is a DAO ....");
        
        result = new ArrayList();
        rs = null;
        try
        {
            String query = "SELECT A.BL_IDX, A.B_IDX, CASE WHEN ISNULL(D.NAME, '')='' THEN '[\uFFFD\uFFFD\uFFFD\uFFFD]'+ A.TITLE ELSE '['+ISNULL(D.NAME, '')+']'+A.TITLE END AS TITLE, A.REGDATE, A.DATEDIFF,\t   ISNULL(B.NAME, '') AS CATEGORY_NAME, \t   ISNULL(C.NAME, '') AS CHURCH_NAME, \t   ISNULL(D.NAME, '') AS DEPART_NAME   FROM (SELECT TOP 4 BL_IDX, B_IDX, TITLE, \t\t\t   RIGHT(CONVERT(char(8), REGDATE, 2),5) AS REGDATE, \t\t\t   DATEDIFF(day, REGDATE, getdate() ) AS DATEDIFF, \t\t\t   C_IDX, CHURCH_IDX, DEPART_IDX           FROM newcaincheon.newcaincheon.NBOARD \t\t WHERE B_IDX='12' \t\t  AND IS_SECRET='N' \t\t  AND IS_VIEW='Y' OR B_IDX='12' \t\t  AND DEPART_IDX='0' AND IS_SECRET='N' AND IS_VIEW='Y' ORDER BY BL_IDX DESC) A LEFT JOIN newcaincheon.newcaincheon.NBOARD_CATEGORY B ON A.C_IDX=B.C_IDX  LEFT JOIN newcaincheon.newcaincheon.CHURCH C ON A.CHURCH_IDX=C.CHURCH_IDX  LEFT JOIN newcaincheon.newcaincheon.DEPARTMENT D ON A.DEPART_IDX=D.DEPART_IDX";
            HashMap notice;
//            for(rs = super.executeQuery(query); rs.next(); result.add(notice))
//            {
//                notice = new HashMap();
//                notice.put("BL_IDX", rs.getString(1));
//                notice.put("B_IDX", rs.getString(2));
//                notice.put("TITLE", rs.getString(3));
//                notice.put("REGDATE", rs.getString(4));
//            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        } finally {
//        	freeResultSet(rs);
//        	free();
    	}
        return result;
    }

    
}
=======
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HomeDaoImpl.java

package kr.caincheon.church.main;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import kr.caincheon.church.common.base.AbstractDAO;

/**
 * 
 * @version		1.0
 */
@Repository("homeDAO")
public class HomeDAO extends AbstractDAO
{
	private final Logger _logger = Logger.getLogger(getClass());

	/**
	 * 여러개의 게시판을 한번에 조회하는 쿼리
	 * @return List
	 */
	public Map getTodayContents() {
		Map result = null;
		
		result = selectOneRow("front.home.get_today_contents");
		
		return result;
	}

	/**
	 * 여러개의 게시판을 한번에 조회하는 쿼리
	 * @return List
	 */
	public List homeMultiboardLists() {
		List result = null;
		
		result = selectList("front.home.get_multi_board");
		
		return result;
	}
	
	//
    public List noticeList()
    {
        List result;
        ResultSet rs;
        _logger.debug(" .... This is a DAO ....");
        
        result = new ArrayList();
        rs = null;
        try
        {
            String query = "SELECT A.BL_IDX, A.B_IDX, A.TITLE, A.REGDATE, A.DATEDIFF,\t   ISNULL(B.NAME, '') AS CATEGORY_NAME, \t   ISNULL(C.NAME, '') AS CHURCH_NAME, \t   ISNULL(D.NAME, '') AS DEPART_NAME   FROM (SELECT TOP 4 BL_IDX, B_IDX, TITLE, \t\t\t   RIGHT(CONVERT(char(8), REGDATE, 2),5) AS REGDATE, \t\t\t   DATEDIFF(day, REGDATE, getdate() ) AS DATEDIFF, \t\t\t   C_IDX, CHURCH_IDX, DEPART_IDX           FROM newcaincheon.newcaincheon.NBOARD \t\t WHERE B_IDX='9' \t\t  AND IS_SECRET='N' \t\t  AND IS_VIEW='Y' OR B_IDX='12' \t\t  AND DEPART_IDX='0' AND IS_SECRET='N' AND IS_VIEW='Y' ORDER BY BL_IDX DESC) A LEFT JOIN newcaincheon.newcaincheon.NBOARD_CATEGORY B ON A.C_IDX=B.C_IDX  LEFT JOIN newcaincheon.newcaincheon.CHURCH C ON A.CHURCH_IDX=C.CHURCH_IDX  LEFT JOIN newcaincheon.newcaincheon.DEPARTMENT D ON A.DEPART_IDX=D.DEPART_IDX";
            HashMap notice;
//            for(rs = super.executeQuery(query); rs.next(); System.out.println(notice.toString()))
//            {
//                notice = new HashMap();
//                notice.put("BL_IDX", rs.getString(1));
//                notice.put("B_IDX", rs.getString(2));
//                notice.put("TITLE", rs.getString(3));
//                notice.put("REGDATE", rs.getString(4));
//                result.add(notice);
//            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        } finally {
//        	freeResultSet(rs);
//        	free();
    	}
        return result;
    }

    public List parishList()
    {
        List result;
        ResultSet rs;
        _logger.debug(" .... This is a DAO ....");
        
        result = new ArrayList();
        rs = null;
        try
        {
            String query = "SELECT A.BL_IDX, A.B_IDX, CASE WHEN ISNULL(D.NAME, '')='' THEN '[\uFFFD\uFFFD\uFFFD\uFFFD]'+ A.TITLE ELSE '['+ISNULL(D.NAME, '')+']'+A.TITLE END AS TITLE, A.REGDATE, A.DATEDIFF,\t   ISNULL(B.NAME, '') AS CATEGORY_NAME, \t   ISNULL(C.NAME, '') AS CHURCH_NAME, \t   ISNULL(D.NAME, '') AS DEPART_NAME   FROM (SELECT TOP 4 BL_IDX, B_IDX, TITLE, \t\t\t   RIGHT(CONVERT(char(8), REGDATE, 2),5) AS REGDATE, \t\t\t   DATEDIFF(day, REGDATE, getdate() ) AS DATEDIFF, \t\t\t   C_IDX, CHURCH_IDX, DEPART_IDX           FROM newcaincheon.newcaincheon.NBOARD \t\t WHERE B_IDX='12' \t\t  AND IS_SECRET='N' \t\t  AND IS_VIEW='Y' OR B_IDX='12' \t\t  AND DEPART_IDX='0' AND IS_SECRET='N' AND IS_VIEW='Y' ORDER BY BL_IDX DESC) A LEFT JOIN newcaincheon.newcaincheon.NBOARD_CATEGORY B ON A.C_IDX=B.C_IDX  LEFT JOIN newcaincheon.newcaincheon.CHURCH C ON A.CHURCH_IDX=C.CHURCH_IDX  LEFT JOIN newcaincheon.newcaincheon.DEPARTMENT D ON A.DEPART_IDX=D.DEPART_IDX";
            HashMap notice;
//            for(rs = super.executeQuery(query); rs.next(); result.add(notice))
//            {
//                notice = new HashMap();
//                notice.put("BL_IDX", rs.getString(1));
//                notice.put("B_IDX", rs.getString(2));
//                notice.put("TITLE", rs.getString(3));
//                notice.put("REGDATE", rs.getString(4));
//            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        } finally {
//        	freeResultSet(rs);
//        	free();
    	}
        return result;
    }

    
}
>>>>>>> branch 'master' of https://github.com/benjaminhds/caincheon
