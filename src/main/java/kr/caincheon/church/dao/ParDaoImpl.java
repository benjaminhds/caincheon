package kr.caincheon.church.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import kr.caincheon.church.common.base.CommonDao;


@Repository("parDao")
public class ParDaoImpl extends CommonDao implements ParDao
{
	private final Logger _logger = Logger.getLogger(getClass());

	/**
	 * 공통적인 쿼리를 리턴한다.
	 * @param _params
	 * @return
	 */
	private String getQuery(Map _params, String callFrom) {
		
		String b_idx = pnull(_params, "b_idx", "ALL");
        String query = "";
        String strWhere = "";

        String schTextGubun = pnull(_params.get("schTextGubun"));
        String schText      = pnull(_params.get("schText"));
        
        int pageNo    = ipnull(_params, "pageNo", 1);
        int pageSize  = ipnull(_params, "pageSize", 20);
        int startRnum = (pageNo - 1) * pageSize + 1;
        int endRnum   = pageNo * pageSize;
        
        String topCount = pnull(_params, "topCount", "5");
        String topQuery = " TOP " + topCount + " ";
        
        if(schText.length() > 0)
            if(schTextGubun.equals("all"))
                strWhere = " AND ( TITLE LIKE '%"+schText+"%' OR CONTENT LIKE '%"+schText+"%') ";
            else
                strWhere = " AND "+schTextGubun+" LIKE '%"+schText+"%' ";
        
        if(b_idx.equals("ALL"))
            query = "SELECT /* ParDaoImpl."+callFrom+"()->getQuery(all) */ A.*, B.NAME AS CATEGORY_NAME "
            		+ " FROM ( "
            		+ "   SELECT ROW_NUMBER() OVER(ORDER BY NOTICE_TYPE, REF DESC, STEP) RNUM, B_IDX, \t\t NOTICE_TYPE, BL_IDX, TITLE, USER_ID, WRITER, convert(char(10),  REGDATE, 120) REGDATE, HIT, REF, STEP, C_IDX, CHURCH_IDX, DEPART_IDX, IS_SECRET"
            				+ ", (SELECT COUNT(*) FROM NBOARD_UPLOAD A3 WHERE A1.BL_IDX=A3.BL_IDX AND IS_USE='Y') FILE_CNT"
//            				+ ", (SELECT COUNT(*) FROM NBOARD_MEMO A2 WHERE A1.BL_IDX=A2.BL_IDX) MEMO_CNT     "
            		+ "   FROM ( SELECT "+topQuery+" '1' AS NOTICE_TYPE, A.* FROM (SELECT "+topQuery+" * FROM NBOARD WHERE B_IDX IN (61,62) "+strWhere+" AND IS_NOTICE='Y' ORDER BY BL_IDX DESC) A "
            		+ "          UNION ALL "
            		+ "          SELECT '2' AS NOTICE_TYPE, * FROM NBOARD WHERE B_IDX IN (61,62) "+strWhere+" AND BL_IDX NOT IN (SELECT "+topQuery+" BL_IDX FROM NBOARD WHERE B_IDX IN (61,62) "+strWhere+" AND  IS_NOTICE='Y' ORDER BY BL_IDX DESC) "
            		+ "        ) A1  "
            		+ ") A "
            		+ "LEFT JOIN NBOARD_CATEGORY B ON A.C_IDX=B.C_IDX WHERE RNUM BETWEEN "+startRnum+" AND "+endRnum;
        else
            query = "SELECT /*ParDaoImpl.getQuery("+b_idx+")*/ A.*, B.NAME AS CATEGORY_NAME "
            		+ " FROM ("
            		+ "   SELECT ROW_NUMBER() OVER(ORDER BY NOTICE_TYPE, REF DESC, STEP) RNUM, B_IDX, NOTICE_TYPE, BL_IDX, TITLE, USER_ID, WRITER, convert(char(10),  REGDATE, 120) REGDATE, HIT, REF, STEP, C_IDX, CHURCH_IDX, DEPART_IDX, IS_SECRET"
            		+ "       , (SELECT COUNT(*) FROM NBOARD_UPLOAD A3 WHERE A1.BL_IDX=A3.BL_IDX AND IS_USE='Y') FILE_CNT"
//            		+ "       , (SELECT COUNT(*) FROM NBOARD_MEMO A2 WHERE A1.BL_IDX=A2.BL_IDX) MEMO_CNT     "
            		+ "   FROM ("
            		+ "         SELECT "+topQuery+" '1' AS NOTICE_TYPE, A.*  FROM (SELECT "+topQuery+" * FROM NBOARD WHERE B_IDX ="+b_idx+" "+strWhere+" AND IS_NOTICE='Y' ORDER BY BL_IDX DESC) A "
            		+ "         UNION ALL  "
            		+ "         SELECT '2' AS NOTICE_TYPE, * FROM NBOARD WHERE B_IDX ="+b_idx+" "+strWhere+" AND BL_IDX NOT IN (SELECT "+topQuery+" BL_IDX FROM NBOARD WHERE B_IDX="+b_idx+" "+strWhere+" AND IS_NOTICE='Y' ORDER BY BL_IDX DESC) "
            		+ "   ) A1  "
            		+ " ) A "
            		+ " LEFT JOIN NBOARD_CATEGORY B ON A.C_IDX=B.C_IDX WHERE RNUM BETWEEN "+startRnum+" AND "+endRnum;
        
        return query;
	}
	
	
	@Override
    public List parList(Map _params)
    {
		D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
		
        String query = "";
        List<Map<String,Object>> result = null;
        try {
        	query = this.getQuery(_params,"parList");
            result = super.executeQueryList(query);

        } catch(Exception e) {
            e.printStackTrace();
        } finally {
        	free();
    	}
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO result : "+result );
        
        if(result==null) result = new ArrayList();
        
        return result;
    }
	
	@Override
    public int parListCount(Map _params)
    {
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        int result = 0;
        String query = "";
        try {
            query = this.getQuery(_params,"parListCount");
            result = super.executeCount(query, false);
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
        	free();
    	}
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO result : "+result );
        
        return result;
    }

	@Override
    public Map parContents(Map _params)
    {
        Map result = null;
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        String b_idx  = pnull(_params.get("b_idx"));
        String bl_idx = pnull(_params.get("bl_idx"));
        String schText   = pnull(_params.get("schText"));
        String schTextGb = pnull(_params.get("schTextGubun"));
        
        String query = "", strWhere = "";
        
        if(schText.length() > 0) {
            if(schTextGb.equals("all"))
                strWhere = " AND (TITLE LIKE '%"+schText+"%' OR CONTENT LIKE '%"+schText+"%') ";
            else
                strWhere = " AND "+schTextGb+" LIKE '%"+schText+"%' ";
        }
        
        try {
            if(b_idx.equals("ALL"))
                query = "SELECT /* b_idx ALL */ TITLE "
                		+ ", CONTENT, USER_ID, WRITER, EMAIL, REGDATE, IS_VIEW, IS_SECRET, HIT, IS_NOTICE "
                		+ ", B_IDX "
                		+ ", (SELECT MAX(BL_IDX) FROM NBOARD X WHERE B_IDX IN (61,62) "+strWhere+" AND X.BL_IDX < A.BL_IDX) AS before_bl_idx "
                		+ ", (SELECT MIN(BL_IDX) FROM NBOARD X WHERE B_IDX IN (61,62) "+strWhere+" AND X.BL_IDX > A.BL_IDX) AS next_bl_idx "
                		//+ ", (SELECT A.STRFILENAME FROM (SELECT ROW_NUMBER() OVER(ORDER BY bu_idx DESC) AS RNUM, FILEPATH+'thumbnail/'+STRFILENAME  as STRFILENAME FROM NBOARD_UPLOAD WHERE BL_IDX='"+bl_idx+"' ) A WHERE A.RNUM=1) AS STRFILENAME "
                		+ ", (SELECT A.STRFILENAME FROM (SELECT ROW_NUMBER() OVER(ORDER BY bu_idx DESC) AS RNUM, FILEPATH+STRFILENAME  as STRFILENAME FROM NBOARD_UPLOAD WHERE BL_IDX='"+bl_idx+"' ) A WHERE A.RNUM=1) AS STRFILENAME "
                		+ " FROM NBOARD A "
                		+ " WHERE B_IDX IN (61,62) AND BL_IDX='"+bl_idx+"'"
                		;
            else
                query = "SELECT TITLE, CONTENT, USER_ID, WRITER, EMAIL, REGDATE, IS_VIEW, IS_SECRET, HIT, IS_NOTICE, B_IDX "
                		+ ", (SELECT MAX(BL_IDX) FROM NBOARD X WHERE B_IDX='"+b_idx+"' "+strWhere+" AND X.BL_IDX < A.BL_IDX) AS before_bl_idx"
                		+ ", (SELECT MIN(BL_IDX) FROM NBOARD X WHERE B_IDX='"+b_idx+"' "+strWhere+" AND X.BL_IDX > A.BL_IDX) AS next_bl_idx  "
                		//+ ", (SELECT A.STRFILENAME FROM (SELECT ROW_NUMBER() OVER(ORDER BY bu_idx DESC) AS RNUM, FILEPATH+'thumbnail/'+STRFILENAME  as STRFILENAME FROM NBOARD_UPLOAD WHERE BL_IDX='"+bl_idx+"' ) A WHERE A.RNUM=1) AS STRFILENAME "
                		+ ", (SELECT A.STRFILENAME FROM (SELECT ROW_NUMBER() OVER(ORDER BY bu_idx DESC) AS RNUM, FILEPATH+STRFILENAME  as STRFILENAME FROM NBOARD_UPLOAD WHERE BL_IDX='"+bl_idx+"' ) A WHERE A.RNUM=1) AS STRFILENAME "
                		+ " FROM NBOARD A "
                		+ " WHERE B_IDX='"+b_idx+"' AND BL_IDX='"+bl_idx+"'"
                		;
            result = super.executeQueryMap(query);
            
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
        	free();
    	}

        D(_logger, Thread.currentThread().getStackTrace(), "DAO result : "+result );
        
        if(result==null) result = new HashMap();
        
        return result;
    }

    
}
