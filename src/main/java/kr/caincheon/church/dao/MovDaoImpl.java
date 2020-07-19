// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MovDaoImpl.java

package kr.caincheon.church.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import kr.caincheon.church.common.base.CommonDao;
import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.utils.UtilString;

// Referenced classes of package kr.caincheon.church.dao:
//            MovDao

@Repository("movDao")
public class MovDaoImpl extends CommonDao implements MovDao
{

    public String getBoardCategory(Map _params)
    {
        return null;
    }

    @Override
    public void movList(Map _params, CommonDaoDTO dto)
    {
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called. params -> "+_params  );
        
        String b_idx = pnull(_params.get("b_idx"));
        String query = "" ;
        
        // paging
        pageNo    = ipnull(_params, "pageNo", 1);
        pageSize  = ipnull(_params, "pageSize", 12);
        setPaging();
        
        // query
        try {
        	query = "SELECT ROW_NUMBER() OVER(ORDER BY REGDATE DESC) RNUM"
            		+ "   , B_IDX, BL_IDX, TITLE, USER_ID, WRITER, convert(char(10),  REGDATE, 120) REGDATE, HIT, REF, STEP, C_IDX, CHURCH_IDX, DEPART_IDX"
            		+ "   , (SELECT FILENAME    FROM NBOARD_UPLOAD A3 WHERE A3.BU_IDX IN (SELECT MAX(BU_IDX) FROM NBOARD_UPLOAD A4 WHERE A1.BL_IDX=A4.BL_IDX AND A4.FILETYPE is null) ) FILENAME"
            		+ "   , (SELECT FILEPATH    FROM NBOARD_UPLOAD A3 WHERE A3.BU_IDX IN (SELECT MAX(BU_IDX) FROM NBOARD_UPLOAD A4 WHERE A1.BL_IDX=A4.BL_IDX AND A4.FILETYPE is null) ) FILEPATH"
            		+ "   , (SELECT STRFILENAME FROM NBOARD_UPLOAD A3 WHERE A3.BU_IDX IN (SELECT MAX(BU_IDX) FROM NBOARD_UPLOAD A4 WHERE A1.BL_IDX=A4.BL_IDX AND A4.FILETYPE is null) ) STRFILENAME"
            		//+ "   , (SELECT COUNT(*) FROM NBOARD_MEMO   A2 WHERE A1.BL_IDX=A2.BL_IDX) MEMO_CNT    "
            		+ "  FROM NBOARD A1  WHERE B_IDX IN ("+b_idx+") AND IS_VIEW='Y' "
            		;
        	dto.daoTotalCount = executeCount(query);
        	
        	
            query = "SELECT A.*, B.NAME AS CATEGORY_NAME, C.NAME AS CHURCH_NAME, D.NAME AS DEPART_NAME FROM   "
            		+ " ( " + query + " ) A "
            		+ " LEFT JOIN NBOARD_CATEGORY B ON A.C_IDX=B.C_IDX "
            		+ " LEFT JOIN CHURCH C ON A.CHURCH_IDX=C.CHURCH_IDX "
            		+ " LEFT JOIN DEPARTMENT D ON A.DEPART_IDX=D.DEPART_IDX "
            		+ " WHERE RNUM BETWEEN "+startRnum+" AND "+endRnum;
            dto.daoList = super.executeQueryList(query);
            
            dto.setPaging(pageNo, pageSize);
            
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
        	free();
    	}
        D(_logger, Thread.currentThread().getStackTrace(), "Query Result dto:"+dto );
        
    }

    @Override
    public Map movContents(Map _params)
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called. params -> "+_params  );
    	
        Map result = new HashMap();
        String query="", whereStr = " AND IS_VIEW='Y'";
        String b_idx  = pnull(_params.get("b_idx"));
        String bl_idx = pnull(_params.get("bl_idx"));
        
        try {
        	// select
            query  = "SELECT TITLE, CONTENT, USER_ID, WRITER, EMAIL, REGDATE, IS_VIEW, DEPART_IDX, CHURCH_IDX, C_IDX, IS_SECRET, HIT, IS_NOTICE, B_IDX"
            		+ ", ISNULL((SELECT NAME FROM DEPARTMENT B WHERE A.DEPART_IDX=B.DEPART_IDX), '') as  DEPART_NAME"
            		+ ", ISNULL((SELECT NAME FROM CHURCH B WHERE A.CHURCH_IDX=B.CHURCH_IDX), '') as  CHURCH_NAME"
            		+ ", ISNULL((SELECT NAME FROM NBOARD_CATEGORY B WHERE A.C_IDX=B.C_IDX), '') as  CATEGORY_NAME"
            		+ ", (SELECT TOP 1 BL_IDX FROM NBOARD WHERE BL_IDX=(SELECT MAX(BL_IDX) FROM NBOARD X WHERE X.B_IDX IN ("+b_idx+") AND X.BL_IDX < "+bl_idx+" )) AS before_p_idx "
            		+ ", (SELECT TOP 1 BL_IDX FROM NBOARD WHERE BL_IDX=(SELECT MIN(BL_IDX) FROM NBOARD X WHERE X.B_IDX IN ("+b_idx+") AND X.BL_IDX > "+bl_idx+" )) AS next_p_idx"
            		
            		+ ", (SELECT Z.FILEPATH FROM (SELECT ROW_NUMBER() OVER(ORDER BY BU_IDX DESC) AS RNUM, FILEPATH FROM NBOARD_UPLOAD WHERE BL_IDX='"+bl_idx+"') Z WHERE Z.RNUM=1) AS FILEPATH "
					+ ", (SELECT Z.FILENAME FROM (SELECT ROW_NUMBER() OVER(ORDER BY BU_IDX DESC) AS RNUM, FILENAME FROM NBOARD_UPLOAD WHERE BL_IDX='"+bl_idx+"') Z WHERE Z.RNUM=1) AS FILENAME "
					+ ", (SELECT Z.STRFILENAME FROM (SELECT ROW_NUMBER() OVER(ORDER BY BU_IDX DESC) AS RNUM, STRFILENAME AS STRFILENAME FROM NBOARD_UPLOAD WHERE BL_IDX='"+bl_idx+"') Z WHERE Z.RNUM=1) AS STRFILENAME "
            		
            		+ " FROM NBOARD A "
            		+ " WHERE B_IDX="+b_idx+" AND BL_IDX=" + bl_idx + whereStr;
            result = super.executeQueryMap(query);
            
            // hit update
            if( result!=null & result.size() > 0 )
            	executeUpdate("UPDATE NBOARD SET HIT=HIT+1 WHERE B_IDX="+b_idx+" AND BL_IDX=" + bl_idx);
            
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
        	free();
    	}
        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+result );
        return result;
    }

    /*
     * (non-Javadoc)
     * @see kr.caincheon.church.dao.MovDao#movDownloads(java.util.Map, kr.caincheon.church.common.CommonDaoDTO)
     */
    @Override
    public void movDownloads(Map _params, CommonDaoDTO dto)
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called. params -> "+_params  );
    	
        String query = "";
        
        try {
        	setPaging(_params);
            query = "SELECT FILENAME, FILEPATH, FILESIZE, BL_IDX "
            		+ " FROM NBOARD_UPLOAD "
            		+ " WHERE BL_IDX="+_params.get("bl_idx")+" AND IS_USE='Y' AND FILETYPE='V'";
            
            dto.daoTotalCount = executeCount(query);
            dto.daoList = super.executeQueryList(query);
            dto.setPaging(pageNo, pageSize);
            
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
        	free();
    	}
        
        D(_logger, Thread.currentThread().getStackTrace(), "Query Result dto:"+dto );
        
    }

    /*
     * 교구앨범 메인홈페에 서비스되는 OP :: 교구영상
     * (non-Javadoc)
     * @see kr.caincheon.church.dao.MovDao#youtubeList(java.util.Map)
     */
    @Override
    public void youtubeList(Map _params, CommonDaoDTO dto)
    {
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called. params -> "+_params  );
        
        String query = "", whereStr = " AND IS_VIEW='Y'";
        // search
        String c_idx     = pnull(_params, "c_idx");
        String text      = pnull(_params, "srchText");
        String srchYearS = pnull(_params, "srchYearS");
        String srchYearE = pnull(_params, "srchYearE");
        
        if(c_idx.length() > 0) {
        	text = text.replaceAll(" ", "").replaceAll(",", "','");
        	whereStr += " AND c_idx in ('"+text+"')";
        }
        if(text.length() > 0) {
        	whereStr += " AND ( TITLE like '%"+text+"%' OR CONTENT like '%"+text+"%' )";
        }
        if(srchYearS.length() > 0 && srchYearE.length() > 0) {
        	srchYearS = UtilString.setDayString(srchYearS, "-");
        	srchYearE = UtilString.setDayString(srchYearE, "-");
        	whereStr += " AND REGDATE BETWEEN CONVERT(DATETIME, '"+srchYearS+"',110) and CONVERT(DATETIME, '"+srchYearE+" 23:59:59',111)";
        } else if(srchYearS.length() > 0) {
        	srchYearS = UtilString.setDayString(srchYearS, "-");
        	whereStr += " AND REGDATE BETWEEN CONVERT(DATETIME, '"+srchYearS+"',110) and CONVERT(DATETIME, '"+srchYearS+" 23:59:59',111)";
        } else if(srchYearE.length() > 0) {
        	srchYearE = UtilString.setDayString(srchYearE, "-");
        	whereStr += " AND REGDATE BETWEEN CONVERT(DATETIME, '"+srchYearE+" 00:00:00',111) and CONVERT(DATETIME, '"+srchYearE+" 23:59:59',111)";
        }
        
        
        try
        {
        	// new code style 
	        query = "SELECT A.*, B.NAME AS CATEGORY_NAME, C.NAME AS CHURCH_NAME, D.NAME AS DEPART_NAME FROM   "
		    		+ "("
		    		+ "  SELECT ROW_NUMBER() OVER(ORDER BY REGDATE DESC) RNUM"
		    		+ ", B_IDX, A2.BL_IDX, TITLE, USER_ID, WRITER, HIT, C_IDX, CHURCH_IDX, DEPART_IDX, CONTENT"
		    		+ ", CONVERT(CHAR(10),  REGDATE, 120) REGDATE"
//		    		+ ", (SELECT FILENAME FROM NBOARD_UPLOAD A3 WHERE A3.BU_IDX IN (SELECT MAX(BU_IDX) FROM MBOARD_UPLOAD A4 WHERE A1.BL_IDX=A4.BL_IDX AND A4.FILETYPE is null) ) FILENAME"
//		    		+ ", (SELECT FILEPATH FROM NBOARD_UPLOAD A3 WHERE A3.BU_IDX IN (SELECT MAX(BU_IDX) FROM MBOARD_UPLOAD A4 WHERE A1.BL_IDX=A4.BL_IDX AND A4.FILETYPE is null) ) FILEPATH"
		    		+ " FROM newcaincheon.NBOARD A1 "
		    		+ " left outer join (SELECT FILENAME, FILEPATH, BL_IDX FROM newcaincheon.NBOARD_UPLOAD "  
		    		+ "                  WHERE BL_IDX IN (SELECT MAX(BL_IDX) FROM newcaincheon.NBOARD WHERE B_IDX='23' AND FILETYPE is null "+whereStr+" ) "  
		    		+ "                 ) A2 ON A2.BL_IDX = A1.BL_IDX"
		    		+ " WHERE B_IDX = '23' "+whereStr+" "
		    		+ ") A "
		    		+ "LEFT JOIN MBOARD_CATEGORY B ON A.C_IDX=B.C_IDX  "
		    		+ "LEFT JOIN CHURCH          C ON A.CHURCH_IDX=C.CHURCH_IDX  "
		    		+ "LEFT JOIN DEPARTMENT      D ON A.DEPART_IDX=D.DEPART_IDX  ";
	        dto.daoTotalCount = executeCount(query);
	        
	        setPaging(_params);
	        dto.daoList = executeQueryPageList(query);
	        
	        dto.setPaging(pageNo, pageSize);
        
        }catch(Exception e){
            e.printStackTrace();
        } finally {
        	free();
    	}
        
        D(_logger, Thread.currentThread().getStackTrace(), "Query Result dto:"+dto );
        
    }

    private final Logger _logger = Logger.getLogger(getClass());
}
