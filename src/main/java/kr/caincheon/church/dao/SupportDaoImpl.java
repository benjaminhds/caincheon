// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SupportDaoImpl.java

package kr.caincheon.church.dao;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import kr.caincheon.church.common.base.CommonDao;

// Referenced classes of package kr.caincheon.church.dao:
//            SupportDao

@Repository("supportDao")
public class SupportDaoImpl extends CommonDao implements SupportDao
{

    private final Logger _logger = Logger.getLogger(getClass());
    
    
    private String getSelectSQL(Map _params, boolean isViewPage) {
    	String query = "", strWhere = "";
    	
    	String b_idx = pnull(_params.get("b_idx"));
    	String schText = pnull(_params.get("schText"));
    	String schTextGubun = pnull(_params.get("schTextGubun"));
    	
    	// for list page
        if(schText.length() > 0) {
            if(schTextGubun.equalsIgnoreCase("all"))
                strWhere = " AND (TITLE LIKE '%"+schText+"%' OR CONTENT LIKE '%"+schText+"%') ";
            else
                strWhere = " AND "+schTextGubun+" LIKE '%"+schText+"%' ";
        }
        
        // for list page
    	String b_idx_cuase = " B_IDX IN (51,52,53,54,55,13,21) "; // TODO 사목자료 조회 : front-side 현업 확인 필요 -> 13, 21 은 포함되어야 하나 ?
    	if(b_idx.equalsIgnoreCase("ALL"))
    		b_idx_cuase = " B_IDX IN (51,52,53,54,55) ";
		else if(b_idx.equals("55") || b_idx.equals("13") || b_idx.equals("21"))
			b_idx_cuase = " B_IDX IN (55) ";
		else
			b_idx_cuase = " B_IDX="+b_idx+" ";

    	// for view page
    	String selectFields = ", BL_IDX, C_IDX, B_IDX, TITLE, USER_ID, WRITER, PWD, EMAIL, IS_VIEW, IS_SECRET, DEPART_IDX, CHURCH_IDX"
			    			+ ", HIT, IS_NOTICE, DISPLAY_YN"
			    			+ ", EVENT_DATE, DOWN_LEVEL, CONVERT(CHAR(10),  REGDATE, 120) REGDATE"
			    			//+ ", CONVERT(CHAR(10),  UPDDATE, 120) UPDDATE " 
			    			;
    	if(isViewPage) 
    		selectFields = ", CONTENT ";
    	
    	String bu_idx = pnull(_params, "bl_idx");
    	if( bu_idx.length() > 0 ) 
    		bu_idx = " AND BL_IDX="+bu_idx+" ";
    	else 
    		bu_idx = "";
    	
    	// merge..
    	strWhere = b_idx_cuase+strWhere+bu_idx;

    	// 첨부 쿼리 : 5개까지.
    	String attachSQL = "SELECT BU_IDX FROM (SELECT ROW_NUMBER() OVER(ORDER BY BU_IDX DESC) RNUM, * FROM NBOARD_UPLOAD WHERE BL_IDX=A1.BL_IDX) X WHERE RNUM"; 
    	
    	//
    	query = "SELECT ROW_NUMBER() OVER(ORDER BY NOTICE_TYPE, REGDATE DESC) RNUM "
    			+ selectFields
    			+ ", (SELECT FILENAME FROM NBOARD_UPLOAD A3 WHERE A3.BU_IDX IN ("+attachSQL+"=1) ) FILENAME"
    			+ ", (SELECT FILEPATH FROM NBOARD_UPLOAD A3 WHERE A3.BU_IDX IN (SELECT MAX(BU_IDX) FROM NBOARD_UPLOAD A4 WHERE A1.BL_IDX=A4.BL_IDX AND A4.FILETYPE is null) ) FILEPATH1"
    			+ ", REPLACE((SELECT FILEPATH FROM NBOARD_UPLOAD A3 WHERE A3.BU_IDX IN (SELECT MAX(BU_IDX) FROM NBOARD_UPLOAD A4 WHERE A1.BL_IDX=A4.BL_IDX AND A4.FILETYPE is null) ),'\\','/') FILEPATH2"
    			+ ", (SELECT COUNT(*) FROM NBOARD_UPLOAD A3 WHERE A1.BL_IDX=A3.BL_IDX AND IS_USE='Y') FILE_CNT"
    			//+ ",  CASE WHEN B_IDX = 51 THEN '전례' WHEN B_IDX = 52 THEN '양식' WHEN B_IDX = 53 THEN '교리' WHEN B_IDX = 54 THEN '성서' WHEN B_IDX = 55 THEN '기타' ELSE '기타' END AS GUBUN_NAME "
    			+ " FROM ("
    				+ " SELECT TOP 2 '1' AS NOTICE_TYPE, A.* FROM (SELECT top 2 *  FROM NBOARD  WHERE "+strWhere+" AND IS_NOTICE='Y' ORDER BY BL_IDX DESC) A  "
    				+ " UNION ALL  "
    				+ " SELECT '2' AS NOTICE_TYPE, * FROM NBOARD  WHERE "+strWhere+" AND BL_IDX NOT IN (SELECT TOP 2 BL_IDX FROM NBOARD WHERE "+strWhere+" AND IS_NOTICE='Y' ORDER BY BL_IDX DESC) "
    				+ " ) A1 ";
    	
    	return query ;
    }
    
	@Override
	public List cureList(Map _params)
    {
        String query = "";
        List result=null;
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        int pageNo    = ipnull(_params, "pageNo", 1);
        int pageSize  = ipnull(_params, "pageSize", 20);
        int startRnum = (pageNo - 1) * pageSize + 1;
        int endRnum   = pageNo * pageSize;
        
        try {
            query = "SELECT  A.*, B.NAME AS CATEGORY_NAME FROM ( " 
            		+ getSelectSQL(_params, false) 
            		+ ") A "
            		+ " LEFT OUTER JOIN NBOARD_CATEGORY B ON A.C_IDX=B.C_IDX AND B.IS_USE='Y' AND B.C_IDX>22 "
            		+ " WHERE RNUM BETWEEN "+startRnum+" AND "+endRnum
            		;
            result = super.executeQueryList(query);
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally { 
        	//free();//count에서 호출
        }

        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+result );
        
        return result;
    }

	@Override
    public int cureListCount(Map _params)
    {
        String query="";
        int result=0;
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        try {
            query = "SELECT  COUNT(1) FROM ( " 
            		+ getSelectSQL(_params, false) 
            		+ ") A "
            		;
            result = super.executeCount(query, false);
            
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally { 
        	free();
        }
        
        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+result );
        
        return result;
    }


	/**
	 * 
	 * @deprecated
	 */
	@Override
    public Map cureContents(Map _params)
    {
		D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
		
        Map result=null;
        
        String query = "";
        try {
            // 
        	_params.put("is_view", "Y");
        	query =  getSelectSQL(_params, true) ; 
        	//query =  getSQLSelectNBoard(_params, true, 5); 
        	
            result = super.executeQueryMap(query);
            // hit up
            executeUpdate("UPDATE NBOARD SET HIT=HIT+1 WHERE BL_IDX=" + pnull(_params, "bl_idx") );
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        }  finally { 
        	free();
        }
        
        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+result );
        
        return result;
    }

    
}
