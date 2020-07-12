// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NewsDaoImpl.java

package kr.caincheon.church.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import kr.caincheon.church.common.base.CommonDao;
import kr.caincheon.church.common.base.CommonDaoDTO;

/**
 * 
 * 
 */
@Repository("newsDao")
public class NewsDaoImpl extends CommonDao implements NewsDao
{

	/**
	 * 
	 * @deprecated
	 */
	@Override
    public String getBoardCategory(Map _params)
    {
		D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        String result="";
//        try {
//        	result = getNBoardCategory(_params);
//        } catch(CommonException e) {
//        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+e.getSql(), e);
//        } finally {
//        	free();
//    	}
        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+result+" ==> 호출 불가 메서드 호출됨." );
        
        return result;
    }

    // front에서 상단 고정 설정을 몇개 가져올까?
    int top_count = 5; // 5개만
    
    /**
     * front::뉴스 게시물 목록 조회
     * @deprecated
     */
	@Override
    public CommonDaoDTO newsList(Map _params)
    {
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        CommonDaoDTO dto = new CommonDaoDTO();
        
		String query = "", queryList="";
		
		// paging
		int pageNo    = ipnull(_params, "pageNo", 1);
		int pageSize  = ipnull(_params, "pageSize", 10);
		int startRnum = (pageNo - 1) * pageSize + 1;
		int endRnum   = pageNo * pageSize;
		
		// 
        pnullPut(_params, "b_idx", "ALL");
        _params.put("is_view", "Y"); // front에서는 Y만 보기
        
		// Query Combination
		try {
			int attachedFileMaxCount = 5;
			boolean isViewPage = false;
			query   = ""; //query   = getSQLSelectNBoard(_params, isViewPage, attachedFileMaxCount);
			
			// total count
			dto.daoTotalCount = super.executeCount("SELECT COUNT(1) FROM ( " + query + " ) A ", false);
			dto.setPaging(pageNo, pageSize);
			
			// list 
			queryList = "SELECT B.NAME AS CATEGORY_NAME, ISNULL(C.NAME,'') AS CHURCH_NAME, ISNULL(D.NAME,'') AS DEPART_NAME, ISNULL(O.NAME, '') AS ORG_NAME"
					+ ", A.* FROM ( "
					+ query
					+ " ) A "
					+ " LEFT OUTER JOIN NBOARD_CATEGORY B ON B.C_IDX      = A.C_IDX      AND B.B_IDX=A.B_IDX AND B.IS_USE='Y' "
					+ " LEFT OUTER JOIN ORG_HIERARCHY   O ON O.ORG_IDX    = A.DEPART_IDX "
					+ " LEFT OUTER JOIN CHURCH          C ON C.CHURCH_IDX = A.CHURCH_IDX " 
					+ " LEFT OUTER JOIN DEPARTMENT      D ON D.DEPART_IDX = A.DEPART_IDX "
					
					+ " WHERE RNUM BETWEEN " + startRnum + " AND " + endRnum
					+ " ORDER BY A.NOTICE_TYPE, A.REGDATE DESC, A.BL_IDX DESC ";
			
			dto.daoList = super.executeQueryList(queryList);

		} catch (Exception e) {
			_E(_logger, Thread.currentThread().getStackTrace(), "SQL ERROR:"+e.getMessage()+"]", e );
		} finally {
			free();
		}
		
		D(_logger, Thread.currentThread().getStackTrace(), "DAO Result.[DTO:"+dto+"]" );
		
		return dto;
    }

	/*
	 * front::뉴스 게시물 글 보기
	 * (non-Javadoc)
	 * @see kr.caincheon.church.dao.NewsDao#newsContents(java.util.Map)
	 */
	@Override
    public Map newsContents(Map _params)
    {
		D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
		
        Map result = new HashMap();
        String strWhere = "";
        String schTextGubun = pnull(_params.get("schTextGubun"));
        String schText      = pnull(_params.get("schText"));
        
        String b_idx        = pnull(_params.get("b_idx"));
        String bl_idx       = pnull(_params.get("bl_idx"));
        
        if(schText.length() > 0) {
            if(schTextGubun.equals("all"))
                strWhere = " AND (TITLE LIKE '%"+schText+"%' OR CONTENT LIKE '%"+schText+"%')";
            else
                strWhere = " AND "+schTextGubun+" LIKE '%"+schText+"%' ";
        }
        
        String query ="";
        try{
            query = "SELECT TITLE, CONTENT, USER_ID, WRITER, EMAIL, REGDATE, CHURCH_IDX, IS_VIEW, DEPART_IDX, C_IDX, IS_SECRET"
            		+ ", (SELECT FILENAME    FROM NBOARD_UPLOAD A3 WHERE A3.BU_IDX IN (SELECT MAX(BU_IDX) FROM NBOARD_UPLOAD A4 WHERE A.BL_IDX=A4.BL_IDX AND A4.FILETYPE is null) ) FILENAME"
            		+ ", (SELECT FILEPATH    FROM NBOARD_UPLOAD A3 WHERE A3.BU_IDX IN (SELECT MAX(BU_IDX) FROM NBOARD_UPLOAD A4 WHERE A.BL_IDX=A4.BL_IDX AND A4.FILETYPE is null) ) FILEPATH"
            		+ ", (SELECT STRFILENAME FROM NBOARD_UPLOAD A3 WHERE A3.BU_IDX IN (SELECT MAX(BU_IDX) FROM NBOARD_UPLOAD A4 WHERE A.BL_IDX=A4.BL_IDX AND A4.FILETYPE is null) ) STRFILENAME"
            		+ ", ISNULL((SELECT NAME FROM DEPARTMENT B WHERE A.DEPART_IDX=B.DEPART_IDX), '') as  DEPART_NAME"
            		+ ", ISNULL((SELECT NAME FROM CHURCH B WHERE A.CHURCH_IDX=B.CHURCH_IDX), '') as  CHURCH_NAME"
            		+ ", ISNULL((SELECT NAME FROM NBOARD_CATEGORY B WHERE A.C_IDX=B.C_IDX), '') as  CATEGORY_NAME"
            		+ ", HIT, IS_NOTICE, B_IDX"
            		+ ", (SELECT MAX(BL_IDX) FROM NBOARD X WHERE B_IDX=A.B_IDX "+strWhere+" AND X.BL_IDX < A.BL_IDX) AS before_bl_idx"
            		+ ", (SELECT MIN(BL_IDX) FROM NBOARD X WHERE B_IDX=A.B_IDX "+strWhere+" AND X.BL_IDX > A.BL_IDX) AS next_bl_idx "
            		+ " FROM NBOARD A "
            		+ " WHERE B_IDX='"+b_idx+"' AND BL_IDX='"+bl_idx+"'"
            		;
            result = super.executeQueryMap(query);
            
            if(result!=null && result.size() > 0) {
            	executeUpdate("UPDATE NBOARD SET hit=hit+1 WHERE B_IDX='"+b_idx+"' AND BL_IDX='"+bl_idx+"'");
            }
        } catch(Exception e) {
    		_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        	free();
    	}
        
        return result;
    }

	@Override
    public void schList(Map _params, CommonDaoDTO dto)
    {
		D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
		
        List result = null;
        
        String query="", strWhere="";
        int pageNo = ipnull(_params, "pageNo", 1);
        int pageSize = ipnull(_params, "pageSize", 20);
        int startRnum = (pageNo - 1) * pageSize + 1;
        int endRnum = pageNo * pageSize;
        
        String schTextGubun = pnull(_params.get("schTextGubun"));
        String schText = pnull(_params.get("schText"));
        String gubuncd = pnull(_params, "gubuncd");
        //String gubunnm = GyoguScheduleUtil.convert2GubunText(gubuncd);
        
        if(schText.length() > 0) {
            if(schTextGubun.equals("all"))
                strWhere = " AND (TITLE LIKE '%"+schText+"%' OR CONTENT LIKE '%"+schText+"%')";
            else
                strWhere = " AND "+schTextGubun+" LIKE '%"+schText+"%' ";
        }
        if(gubuncd.length()>0) {
        	int igb = Integer.parseInt(gubuncd);
        	switch(igb) {
        	case 1://전체
        		break;
        	case 2://전체+교구장
        		strWhere += " AND GUBUN IN ('전체','교구장') ";
        		break;
        	case 3://전체+총대리
        		strWhere += " AND GUBUN IN ('전체','총대리') ";
        		break;
        	case 4://교구
        		strWhere += " AND S.ORG_IDX IN (SELECT ORG_IDX FROM ORG_HIERARCHY WHERE LV1='01' AND LV2='00' AND LV3='000') ";
        		break;
        	case 5://부서
        		strWhere += " AND S.ORG_IDX IN (SELECT ORG_IDX FROM ORG_HIERARCHY WHERE LV1='01' AND LV2<>'00' AND LV3<>'000') ";
        		break;
        	}
        }
        
        try
        {
        	query = " SELECT ROW_NUMBER() OVER(ORDER BY S.START_DATE DESC) RNUM, S.S_IDX, S.GUBUN, S.TITLE"
        			+ ", CONVERT(CHAR(10),S.START_DATE,120) AS START_DATE, CONVERT(CHAR(10),S.END_DATE,120) AS END_DATE, S.REGDATE "
        			+ ", S.ORG_IDX, O.NAME AS ORG_NAME "
            		+ " FROM SCHEDULE S "
            		+ " LEFT OUTER JOIN ORG_HIERARCHY O ON O.ORG_IDX = S.ORG_IDX "
            		+ " WHERE displayYN='Y' "+strWhere 
            		;
        	dto.daoTotalCount = super.executeCount(query, true);
        	dto.setPaging(pageNo, pageSize);
        	
            query = "SELECT * FROM ("
            		+ query 
            		+ " ) A WHERE RNUM BETWEEN "+startRnum+" AND "+endRnum;
            dto.daoList = super.executeQueryList(query);
            
        } catch(Exception e) {
    		_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        	free();
    	}
        
        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+result );
        
    }

	@Override
    public Map schContents(Map _params) 
    {
		D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
		
        Map result = null;
        String s_idx   = pnull(_params.get("s_idx"));
        String gubuncd = pnull(_params.get("gubuncd"));
        //String gubunnm = GyoguScheduleUtil.convert2GubunText(gubuncd);
        
        String query = "", where = "";
        try {
        	//
        	switch(Integer.parseInt(gubuncd)) {
        	case 1:
        		break;
        	case 2:
        		where = " AND GUBUN IN ('전체','교구장') ";
        		break;
        	case 3:
        		where = " AND GUBUN IN ('전체','총대리') ";
        		break;
        	case 4:
        		where = " AND GUBUN IN ('교구') ";
        		break;
        	case 5:
        		where = " AND GUBUN IN ('부서') ";
        		break;
        	}
        	//
            query = " SELECT A.TITLE, A.GUBUN, A.CONTENT, CONVERT(CHAR(10),A.START_DATE,120) AS START_DATE, CONVERT(CHAR(10),A.END_DATE,120) AS END_DATE, displayYN"
            		+ ", ORG_IDX, (SELECT NAME FROM ORG_HIERARCHY WHERE ORG_IDX=ISNULL(A.ORG_IDX,0)) ORG_NAME"
            		+ ", (SELECT MIN(S_IDX) FROM SCHEDULE X WHERE displayYN='Y' and S_IDX>"+s_idx+where+ ") AS PRE_S_IDX"
            		+ ", (SELECT MAX(S_IDX) FROM SCHEDULE X WHERE displayYN='Y' and S_IDX<"+s_idx+where+ ") AS NEXT_S_IDX"
            		+ " FROM SCHEDULE A "
            		+ " WHERE S_IDX="+s_idx;
            result = super.executeQueryMap(query);
        } catch(Exception e) {
    		_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        	free();
    	}
        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+result );
        return result;
    }

    private final Logger _logger = Logger.getLogger(getClass());
}
