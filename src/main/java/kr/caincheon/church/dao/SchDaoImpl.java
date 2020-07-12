// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SchDaoImpl.java

package kr.caincheon.church.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import kr.caincheon.church.common.base.CommonDao;
import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.utils.UtilDate;
import kr.caincheon.church.schedule.GyoguScheduleUtil;

// Referenced classes of package kr.caincheon.church.dao:
//            SchDao

@Repository("schDao")
public class SchDaoImpl extends CommonDao
    implements SchDao
{

	private final String GUBUN_CASE_END_SQL  = " CASE WHEN GUBUN='전체' THEN '1' WHEN GUBUN='교구장' THEN '2' WHEN GUBUN='총대리' THEN '3' "
			                                       + "WHEN GUBUN='교구' THEN '4' "
			                                       + "ELSE '5' "
			                                       + "END AS GUBUN_CODE ";

	private final String GUBUN_CASE_END_SQL2 = " CASE WHEN GUBUN='전체' THEN '1' WHEN GUBUN='교구장' THEN '2' WHEN GUBUN='총대리' THEN '3' "
			                                       + "WHEN GUBUN='교구' THEN '4' "
			                                       + "ELSE '5' "
			                                       + "END AS GUBUN_CODE ";
	
    private final Logger _logger = Logger.getLogger(getClass());
    
	@Override
    public CommonDaoDTO schList(Map _params)
    {
		D(_logger, Thread.currentThread().getStackTrace(), "DAO schList Called.[params:"+_params+"]" );
		
		// return DTO
		CommonDaoDTO dto = new CommonDaoDTO();
		
		//
        String whereStr = "";
        
        //
        setPaging(_params);
//      int pageNo   = ipnull(_params, "pageNo", 1);
//      int pageSize = ipnull(_params, "pageSize", 20);
//      int startRnum = (pageNo - 1) * pageSize + 1;
//      int endRnum = pageNo * pageSize;
        
        String s_search = "";
        String searchText = pnull(_params.get("searchText"));
        String gubun = pnull(_params,"gubun");
        if(gubun.length() > 0) {
        	if("1".equals(gubun)) 
        		whereStr = "";
        	else if("5".equals(gubun)) 
        		whereStr = " AND (gubun is null or gubun = '') ";
        	else 
        		whereStr = " AND gubun = '"+GyoguScheduleUtil.convert2GubunText(gubun)+"' ";
        }
        if(searchText != null && searchText.length() > 0)
        {
            s_search = pnull(_params.get("s_search"));
            if(s_search.equals("all"))
                whereStr += " AND ( TITLE LIKE '%"+searchText+"%' OR CONTENT LIKE '%"+searchText+"%') ";
            else if(s_search.equals("title"))
                whereStr += " AND TITLE LIKE '%"+searchText+"%' ";
            else if(s_search.equals("contents"))
                whereStr += " AND CONTENT LIKE '%"+searchText+"%' ";
        }
        
        String query = "";
        try
        {
            query = "SELECT /* old sql */ * FROM (SELECT ROW_NUMBER() OVER(ORDER BY START_DATE DESC) RNUM, s_idx, gubun"
            		+ ", org_idx"
            		+ ", CASE WHEN gubun='없음' and isnull(org_idx,'')='' THEN '교구'  ELSE fnGetOrgName(org_idx) END AS org_idx_name"
            		+ ", title, convert(char(10),start_date,120) as start_date, convert(char(10),end_date,120) as end_date, regdate "
            		+ " FROM SCHEDULE "
            		+ " WHERE 1=1 "+whereStr+" )A "
            		+ " WHERE RNUM BETWEEN "+startRnum+" AND "+endRnum
            		;
            
            // new sql
            String basicSQL = "SELECT ROW_NUMBER() OVER(ORDER BY START_DATE DESC) RNUM, S_IDX, GUBUN, ORG_IDX, TITLE, displayYN"
            		+ ", CONVERT(CHAR(10),START_DATE,120) AS START_DATE, CONVERT(CHAR(10),END_DATE,120) AS END_DATE, REGDATE"
            		+ ", "+GUBUN_CASE_END_SQL
            		+ " FROM SCHEDULE S "
            		+ " WHERE 1=1 "+whereStr
            		;
            
            query = "SELECT A.* "
            		+ ", O.NAME AS ORG_NAME "
            		+ " FROM ( "+ basicSQL +" ) A "
            		+ " LEFT OUTER JOIN ORG_HIERARCHY O ON O.ORG_IDX = A.ORG_IDX "
            		+ " WHERE RNUM BETWEEN "+startRnum+" AND "+endRnum
            		+ " ORDER BY START_DATE DESC "
            		;
            // new list
            dto.daoList = executeQueryList(query);
            // new count
            dto.daoTotalCount = executeCount(basicSQL, true);
            // paging
            dto.paging = getPageing(dto.daoTotalCount);
            
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
            free();
        }
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO DTO Result:"+dto );
        
        return dto;
    }


//	@Override
//    public int schListCount(Map _params)
//    {
//		D(_logger, Thread.currentThread().getStackTrace(), "DAO schListCount Called.[params:"+_params+"]" );
//		
//        String whereStr = "";
//        int result;
//        
//        
//        String s_search = "";
//        String searchText = pnull(_params.get("searchText"));
//        
//        String gubun_code = pnull(_params.get("gubun_code"));
//        if(gubun_code.equals("1"))
//            whereStr = " AND gubun='전체' ";
//        else if(gubun_code.equals("2"))
//            whereStr = " AND gubun='교구장' ";
//        else if(gubun_code.equals("3"))
//            whereStr = " AND gubun='총대리' ";
//        else 
//            whereStr = " AND gubun='부서' ";
//        
//        if(searchText != null && searchText.length() > 0 && _params.get("searchGubun") != null)
//        {
//            s_search = pnull(_params.get("s_search"));
//            searchText = pnull(_params.get("searchText"));
//            if(s_search.equals("all"))
//                whereStr += " AND ( TITLE LIKE '%"+searchText+"%' OR CONTENT LIKE '%"+searchText+"%') ";
//            else if(s_search.equals("title"))
//                whereStr += " AND TITLE LIKE '%"+searchText+"%' ";
//            else if(s_search.equals("contents"))
//                whereStr += " AND CONTENT LIKE '%"+searchText+"%' ";
//        }
//        result = 0;
//        String query = "";
//        try
//        {
//            query = "SELECT COUNT(1) FROM SCHEDULE  WHERE 1=1 "+whereStr;
//            result = super.executeCount(query, false);
//        } catch(Exception e) {
//        	E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
//        } finally {
//            free();
//        }
//        
//        D(_logger, Thread.currentThread().getStackTrace(), "DAO Result:"+result );
//        return result;
//    }

	/*
	 * 교구일정 조회
	 * (non-Javadoc)
	 * @see kr.caincheon.church.dao.SchDao#schContents(java.util.Map)
	 */
	@Override
    public Map schContents(Map _params)
    {
		D(_logger, Thread.currentThread().getStackTrace(), "DAO schContents Called.[params:"+_params+"]" );
		
		Map result = schView(_params);
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Result:"+result );
        
        return result;
    }

	/*
	 * 주관부서 정보 가지고 오기
	 * - 늦은 요건 제공(12-19)으로 추가 로직 반영
	 */
	private String selectSuperviser(String org_idx, String gubun) throws Exception {
		if(org_idx.length()==0) return "";
		String query = "SELECT "
    			+ "CASE WHEN O.NAME IN ('총대리','교구장') THEN O.NAME"
    			+ "     WHEN CHARINDEX('처', O.NAME)=LEN(O.NAME) OR CHARINDEX('실', O.NAME)=LEN(O.NAME) OR CHARINDEX('국', O.NAME)=LEN(O.NAME) OR CHARINDEX('부', O.NAME)=LEN(O.NAME) OR CHARINDEX('과', O.NAME)=LEN(O.NAME) THEN '부서' "
    			+ "     ELSE '교구' "
    			+ "END GUBUN "
    			+ "FROM ORG_HIERARCHY O WHERE LV1='01' AND ORG_IDX="+org_idx;
    	Object gb=null;
		try {
			gb = executeColumnOne(query, 1);
		} catch (Exception e) {
			throw e;
		}
    	return gb==null ? GyoguScheduleUtil.convert2GubunText(gubun):gb.toString();
	}
	
	/*
	 * (non-Javadoc)
	 * @see kr.caincheon.church.dao.SchDao#schInsert(java.util.Map)
	 */
	@Override
    public boolean schInsert(Map _params)
    {
		D(_logger, Thread.currentThread().getStackTrace(), "DAO schInsert Called.[params:"+_params+"]" );
		
        boolean result = false;
        PreparedStatement preparedStatement;
        String gubun = pnull(_params,"gubun");
        String org_idx = pnull(_params.get("org_idx"));
        String title = pnull(_params.get("title"));
        String content = pnull(_params.get("content"));
        String start_date = pnull(_params.get("start_date"));
        String end_date = pnull(_params.get("end_date"));
        String displayYN = pnull(_params.get("displayYN"));
        if(!displayYN.equals("Y")) displayYN="N";
        preparedStatement = null;
        String query = "";
        try
        {
        	/* 12-19 추가 로직 반영 */
        	String gubun_name = "";
        	if("1".equals(gubun)) {
        		gubun_name = "전체";
        	} else if("2".equals(gubun)) {
        		gubun_name = "교구장";
        	} else if("3".equals(gubun)) {
        		gubun_name = "총대리";
        	} else {
	        	gubun_name = selectSuperviser(org_idx, gubun);
        	}
        	
            query = "INSERT INTO SCHEDULE  (title, start_date, end_date, content, org_idx, regdate, gubun, displayYN)  VALUES (?, ?, ?, ?, ?, getdate(), ?, ?) ";
            preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, start_date);
            preparedStatement.setString(3, end_date);
            preparedStatement.setString(4, content);
            preparedStatement.setString(5, org_idx);
            preparedStatement.setString(6, gubun_name);
            preparedStatement.setString(7, displayYN);
            int i = preparedStatement.executeUpdate();
            result = i == 1 ? true : false;
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        	if(preparedStatement != null) try { preparedStatement.close(); } catch(SQLException e) { e.printStackTrace(); }
            
            free();
        }

        D(_logger, Thread.currentThread().getStackTrace(), "DAO Result:"+result );
        
        return result;
    }

	@Override
    public boolean schModify(Map _params)
    {
		D(_logger, Thread.currentThread().getStackTrace(), "DAO schModify Called.[params:"+_params+"]" );
		
        boolean result = false;
        String org_idx = pnull(_params.get("org_idx"));
        String title = pnull(_params.get("title"));
        String content = pnull(_params.get("content"));
        String start_date = pnull(_params.get("start_date"));
        String end_date = pnull(_params.get("end_date"));
        String displayYN = pnull(_params.get("displayYN"));
        
        PreparedStatement preparedStatement = null;
        
        String s_idx = pnull(_params.get("s_idx"));
        String gubun = pnull(_params,"gubun");
        
        String query = "";
        try
        {
        	/* 12-19 추가 로직 반영 */
        	String gubun_name = "";
        	if("1".equals(gubun)) {
        		gubun_name = "전체";
        	} else if("2".equals(gubun)) {
        		gubun_name = "교구장";
        	} else if("3".equals(gubun)) {
        		gubun_name = "총대리";
        	} else {
	        	gubun_name = selectSuperviser(org_idx, gubun);
        	}
        	
            query = "UPDATE SCHEDULE  SET title=?, start_date=?, end_date=?,  content=?, org_idx=?, gubun=?, displayYN=?  WHERE s_idx = ? ";
            preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, start_date);
            preparedStatement.setString(3, end_date);
            preparedStatement.setString(4, content);
            preparedStatement.setString(5, org_idx);
            preparedStatement.setString(6, gubun_name);
            preparedStatement.setString(7, displayYN);
            preparedStatement.setInt(8, Integer.parseInt(s_idx));
            int i = preparedStatement.executeUpdate();
            result = i == 1 ? true : false;
            if(i > 1) throw new Exception("Update Result Count is a `"+i+"`. Update Count is only one."); 
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        }  finally {
        	if(preparedStatement != null) try { preparedStatement.close(); } catch(SQLException e) { e.printStackTrace(); }
            free();
        }
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Result:"+result );
        
        return result;
    }


	@Override
    public boolean schDelete(Map _params)
    {
		D(_logger, Thread.currentThread().getStackTrace(), "DAO schDelete Called.[params:"+_params+"]" );
		
        boolean result = false;
        String query="";
        
        try {
            query = "delete from SCHEDULE where s_idx = "+pnull(_params.get("s_idx"));
            int i = executeUpdate(query);
            result = i == 0 ? false : true;
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        }  finally {
            free();
        }

        D(_logger, Thread.currentThread().getStackTrace(), "DAO Result:"+result );
        
        return result;
    }

	/*
	 * 빈달력 쿼리 && 일정 쿼리
	 * (non-Javadoc)
	 * @see kr.caincheon.church.dao.SchDao#scheduleByDiary(java.util.Map)
	 */
	@Override
    public Map scheduleByDiary(Map _params)
        throws Exception
    {
		D(_logger, Thread.currentThread().getStackTrace(), "DAO scheduleByDiary Called.[params:"+_params+"]" );
		
        String srch_ym = pnull(_params.get("srch_ym"));
        String query = "", query0 = "";
        Map rtMap = new HashMap();
        
        if(srch_ym.length() == 0)
            srch_ym = UtilDate.getDateFormat("yyyy-MM");
        else if(srch_ym.length() != 7)
            throw new Exception("조회하는 년화의 데이터 형식이 맞지 않음 [TYPE:YYYY-MM, "+srch_ym+"]");
        
        // 빈달력 쿼리
        List result = null;
        try
        {
            String yyyymm = srch_ym.replace("-", "").replace(".", "");
            query = " WITH T AS (  SELECT DATEADD(d, 0, '"+yyyymm+"'+'01') DT  "
            		+"  UNION ALL "
            		+" SELECT DATEADD(D, 1, DT) DT "
            		+"   FROM T "
            		+"  WHERE DT + 1 < DATEADD(M, 1, '"+yyyymm+"'+'01') "
            		+" ) "
            		+" SELECT [1] 일 "
            		+"      , [2] 월 "
            		+"      , [3] 화 "
            		+"      , [4] 수 "
            		+"      , [5] 목 "
            		+"      , [6] 금 "
            		+"      , [7] 토 "
            		+"   FROM (SELECT DATEPART(D,  DT) D  "
            		+"              , DATEPART(W,  DT) W  "
            		+"              , DATEPART(WW, DT) WW "
            		+"           FROM T "
            		+"         ) A "
            		+"  PIVOT( MIN(D) FOR W IN "
            		+"         ([1], [2], [3], [4], [5], [6], [7]) ) A ";
            List list = executeQueryList(query);
            rtMap.put("DIARY", list);
        }
        catch(Exception e)
        {
            _logger.error((new StringBuilder("ERROR SQL1:")).append(query).toString(), e);
        } finally {
        	free();
        }
        
        //  해당 월의 일정 쿼리
        String whereStr = "";
        String searchGubun = "";
        String searchText = pnull(_params.get("searchText"));
        
        if(searchText != null && searchText.length() > 0 && _params.get("searchGubun") != null)
        {
            searchGubun = pnull(_params.get("searchGubun"));
            if(searchGubun.equals("title")) {
                searchText = pnull(_params.get("searchText"));
                whereStr += " AND TITLE LIKE '%"+searchText+"%' ";
            } else if(searchGubun.equals("content")) {
                searchText = pnull(_params.get("searchText"));
                whereStr += " AND CONTENT LIKE '%"+searchText+"%' ";
            }
        }
        
        try
        {
            query0 = " SELECT A.H_DATE, S_IDX, TITLE"
            		+ ", CONVERT( CHAR(10), START_DATE, 120) S_DATE"
            		+ ", CAST(SUBSTRING(A.H_DATE, 9, 10)  AS INT) S_DAY"
            		+ ", CONVERT( CHAR(10), END_DATE, 120) E_DATE"
            		+ ", ORG_IDX, displayYN"
            		+ " FROM "
            		+ " (SELECT CONVERT(VARCHAR(10),H_DATE,120) H_DATE FROM HOLIDAY  WHERE CONVERT(VARCHAR(7),H_DATE,120) = '"+srch_ym+"') A "
            		+ " LEFT OUTER JOIN SCHEDULE B  ON A.H_DATE BETWEEN CONVERT( CHAR(10), B.START_DATE, 120) AND CONVERT( CHAR(10), B.END_DATE, 120) "
            		+ " WHERE GUBUN IN('전체', '교구장') AND CONVERT( CHAR(7), START_DATE, 120)='"+srch_ym+"' "+whereStr
            		;
            
            query = "SELECT S_DATE, S_IDX, displayYN"
            		+ ", CASE WHEN S_DATE=MAX(H_DATE) THEN TITLE ELSE TITLE +'(~'+ MAX(H_DATE)+')' END AS TITLE"
            		+ " FROM ("
            		+ query0
            		+ ") XX "
            		+ " GROUP BY S_DATE, S_IDX, displayYN, TITLE"
            		+ " ORDER BY S_DATE ASC"
            		;
            
            List list = executeQueryList(query);
            Map schedule = new HashMap();
            for(int i = 0; i < list.size(); i++)
            {
                Map row = (Map)list.get(i);
                String YN = pnull(row, "DISPLAYYN");
                String t  = pnull(row, "S_DATE");
                t = t.substring(t.lastIndexOf("-")+1);
                //Integer iDay = new Integer(UtilInt.pint(row.get("S_DAY")));
                Integer iDay = new Integer(t);
                
                System.out.println(">>>>"+row);
                
                HashMap ent = new HashMap();
                if("N".equalsIgnoreCase(YN))
                	ent.put(row.get("S_IDX"), "<font color=\"darkgray\">"+row.get("TITLE")+"</font>");
                else
                	ent.put(row.get("S_IDX"), row.get("TITLE"));
                
                if(schedule.containsKey(iDay))
                    ((HashMap)schedule.get(iDay)).putAll(ent);
                else
                    schedule.put(iDay, ent);
            }

            rtMap.put("SCHEDULE", schedule);
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL2:"+query, e);
        } finally {
        	free();
        }

        D(_logger, Thread.currentThread().getStackTrace(), "DAO Result:"+result );
        return rtMap;
    }

	/*
	 * 교구일정 조회
	 * (non-Javadoc)
	 * @see kr.caincheon.church.dao.SchDao#schView(java.util.Map)
	 */
	@Override
    public Map schView(Map _params)
    {
		D(_logger, Thread.currentThread().getStackTrace(), "DAO schView Called.[params:"+_params+"]" );
		
        Map result = new HashMap();
        String s_idx = pnull(_params.get("s_idx"));
        String query="";
        try
        {
            query = " SELECT S_IDX, TITLE, CONTENT, DISPLAYYN, GUBUN, ORG_IDX"
            		+ ", CONVERT(CHAR(10), START_DATE, 120) START_DATE, CONVERT(CHAR(10), END_DATE, 120) END_DATE, CONVERT(CHAR(10), REGDATE, 120) REGDATE"
            		+ ", "+GUBUN_CASE_END_SQL
            		+ " FROM SCHEDULE WHERE S_IDX='"+s_idx+"'";
            result = super.executeQueryMap(query);
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
            free();
        }

        D(_logger, Thread.currentThread().getStackTrace(), "DAO Result:"+result );
        return result;
    }

	/*
	 * 교구일정 조회
	 * (non-Javadoc)
	 * @see kr.caincheon.church.dao.SchDao#schCView(java.util.Map)
	 */
	@Override
    public Map schCView(Map _params)
    {
		D(_logger, Thread.currentThread().getStackTrace(), "DAO schCView Called.[params:"+_params+"]" );
		
        Map result = schView(_params);
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Result:"+result );
        return result;
    }

	
    
}
