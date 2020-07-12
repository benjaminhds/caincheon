package kr.caincheon.church.father.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import kr.caincheon.church.common.base.CommonDao;
import kr.caincheon.church.common.base.CommonException;
import kr.caincheon.church.common.utils.UtilDate;


@Repository("fatherCalDao")
public class FatherCalDaoImpl extends CommonDao {

	private final Logger _logger = Logger.getLogger(getClass());

	/** 달력조회 SQL - 특정년월을 가지고 replaceAll 해야 함(DIARY_SQL.replaceALL("yyyymm", "201709"))  */
	public final String DIARY_SQL = ""
			+"WITH T AS ( "
			+" SELECT DATEADD(d, 0, 'yyyymm'+'01') DT  "
    		+"   UNION ALL "
    		+"  SELECT DATEADD(D, 1, DT) DT FROM T WHERE DT + 1 < DATEADD(M, 1, 'yyyymm'+'01') "
    		+") "
    		+" SELECT [1] 일 "
    		+"      , [2] 월 "
    		+"      , [3] 화 "
    		+"      , [4] 수 "
    		+"      , [5] 목 "
    		+"      , [6] 금 "
    		+"      , [7] 토 "
    		+"  FROM (SELECT DATEPART(D,  DT) D  "
    		+"             , DATEPART(W,  DT) W  "
    		+"             , DATEPART(WW, DT) WW "
    		+"          FROM T "
    		+" ) A "
    		+" PIVOT( MIN(D) FOR W IN "
    		+"        ([1], [2], [3], [4], [5], [6], [7]) "
    		+" ) A "
    		;
	
	/*
	 * 사제 서품일/축일을 달력과 연동한 목록 조회
	 */
	public Map selectFathersByDiary(Map _params) throws CommonException
    {
		D(_logger, Thread.currentThread().getStackTrace(), "DAO selectFathersByDiary Called.[params:"+_params+"]" );
		
		Map rtMap = new HashMap();

		// parameter
        String srch_ym = pnull(_params.get("srch_ym")); // YYYYMM
        if(srch_ym.length() == 0)
            srch_ym = UtilDate.getDateFormat("yyyyMM");
        else if(srch_ym.length() != 6)
            throw new CommonException("조회하는 년화의 데이터 형식이 맞지 않음 [TYPE:YYYY-MM, "+srch_ym+"]");
        
        // set
        _params.put("srch_ym", srch_ym);
        
        // 빈달력 쿼리
        String query = "";
        List result = null;
        try
        {
        	// 지정 년월의 달력 조회
            List list = executeQueryList(DIARY_SQL.replaceAll("yyyymm", srch_ym));
            rtMap.put("DIARY", list);
        } catch(Exception e) {
            _logger.error("ERROR SQL1:"+lastSQL, e);
        }
        
        //  지정 월에 해당하는 사제 목록 조회
        String srch_mm = srch_ym.substring(4); // MM
        try
        {
        	
            query =   " SELECT * FROM ( "
            		+ " 	SELECT /* 축일 */ ROW_NUMBER() OVER(ORDER BY ONUM DESC) RONUM, 'N' TP, P_IDX, ONUM, NAME, CHRISTIAN_NAME, NAME+'('+CHRISTIAN_NAME+')' NAME2 "
            		+ " 	, NEW_BIRTHDAY BIRTHDAY "
            		+ " 	, RIGHT(NEW_BIRTHDAY,2) BIRTHDAY_DAY "
            		+ " 	FROM PRIEST WHERE NEW_BIRTHDAY LIKE '"+srch_mm+"-%' "
            		+ " 	UNION ALL "
            		+ " 	SELECT /* 서품일 */ ROW_NUMBER() OVER(ORDER BY ONUM DESC) RONUM, 'P' TP, P_IDX, ONUM, NAME, CHRISTIAN_NAME, NAME+'('+CHRISTIAN_NAME+')' NAME2 "
            		+ " 	, CONVERT(VARCHAR(10), P_BIRTHDAY, 120) BIRTHDAY "
            		+ " 	, CONVERT(VARCHAR(2), P_BIRTHDAY, 104) BIRTHDAY_DAY "
            		+ " 	FROM PRIEST WHERE CONVERT(VARCHAR(2), P_BIRTHDAY, 110) = '"+srch_mm+"' "
            		+ " ) X "
            		+ " ORDER BY TP, BIRTHDAY_DAY, ONUM"
            		;
            
            List list = executeQueryList(query);
            Map<Integer, Map>  schedule = new LinkedHashMap<Integer, Map>();
            for(int i = 0; i < list.size(); i++) {
            	Map<String, Object> row = (LinkedHashMap<String, Object>)list.get(i);
            	
            	Integer iDay = new Integer(pnull(row, "BIRTHDAY_DAY"));
            	
            	// indiviual
            	String p_idx = pnull(row, "P_IDX");
            	String name  = pnull(row, "NAME2");
            	
            	String linkF = "", linkB = "";
            	if("N".equals(pnull(row, "TP"))) {
            		linkF = "<font color='red'>";
            		linkB = "</font>";
            	}
            	name = linkF + name + linkB;
            	
            	if(schedule.containsKey(iDay))
            		schedule.get(iDay).put(p_idx, name);
            	else {
            		Map entry = new LinkedHashMap();
            		entry.put(p_idx, name);
            		schedule.put(iDay, entry);
            	}
            }
            rtMap.put("PRIEST", schedule);
        } catch(Exception e) {
        	_logger.error("ERROR SQL2:"+lastSQL, e);
        } finally {
        	free();
        }

        D(_logger, Thread.currentThread().getStackTrace(), "DAO selectFathersByDiary Result:"+result );
        return rtMap;
    }
	
	
}
