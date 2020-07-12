// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HistoryDaoImpl.java

package kr.caincheon.church.dao;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import kr.caincheon.church.common.base.CommonDao;

// Referenced classes of package kr.caincheon.church.dao:
//            HistoryDao

@Repository("historyDao")
public class HistoryDaoImpl extends CommonDao
    implements HistoryDao
{


	private final Logger _logger = Logger.getLogger(getClass());
	
	@Override
    public List historymList(Map _params) throws Exception
    {
        String whereStr = "", query = "";
        List result = null;
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        int pageNo    = ipnull(_params, "pageNo", 1);
        int pageSize  = ipnull(_params, "pageSize", 20);
        int startRnum = (pageNo - 1) * pageSize + 1;
        int endRnum   = pageNo * pageSize;
        String searchText = pnull(_params.get("searchMText"));
        
        if(searchText.length() > 0)
            whereStr = " AND category_name LIKE '%"+searchText+"%'";
        
        try {
            query = "SELECT * FROM ("
            		+ "   SELECT ROW_NUMBER() OVER(ORDER BY DISPLAYNO, REGISTDT DESC) RNUM, CATEGORY_CODE, CATEGORY_NAME, DISPLAYYN"
            		+ "   , CASE WHEN DISPLAYYN='Y' THEN '노출' ELSE '비노출' END AS DISPLAYYN_TEXT"
            		+ "   , DISPLAYNO, CONVERT(CHAR(10),  REGISTDT, 120) REGISTDT "
            		+ "   , IMAGE, FILE_PATH "
            		+ "   , (SELECT COUNT(1) FROM HISTORY_SLAVE S WHERE S.CATEGORY_CODE=M.CATEGORY_CODE ) AS SLAVE_CNT "
            		+ "   FROM HISTORY_MASTER M "
            		+ "   WHERE 1=1 "+whereStr
            		+ ") ROWS "
            		+ " WHERE RNUM BETWEEN "+startRnum+" AND "+endRnum;
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
    public int historymListCount(Map _params) throws Exception
    {
        int result=0;
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        String whereStr = "", searchText = pnull(_params.get("searchMText"));
        
        if(searchText.length() > 0)
            whereStr = " AND category_name LIKE '%"+searchText+"%' ";

        String query = "";
        try {
            query = "SELECT COUNT(1)  FROM HISTORY_MASTER WHERE 1=1 "+whereStr;
            result = super.executeCount(query, false);
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        	free();
    	}
        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+result );
        return result;
    }

	@Override
    public Map historymContents(Map _params) throws Exception
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
        Map result = new HashMap();
        String query_type    = pnull(_params.get("query_type"));
        String category_code = pnull(_params.get("category_code"));
        String query = "";
        String whereStr = "";
        
        try {
            if(query_type.equals("modify")) {
                whereStr = " AND category_code = '"+category_code+"' ";
                query = " SELECT category_code, category_name, image, FILE_PATH, displayYN, displayNo "
                		+ " FROM HISTORY_MASTER "
                		+ " WHERE 1=1 "+whereStr;
                result = super.executeQueryMap(query);
            }
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        	free();
    	}
        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+result );
        return result;
    }

    @Override
    public boolean historymInsert(Map _params) throws Exception
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
        boolean bReturn=true;
        String query="";
        String category_name = pnull(_params.get("category_name"));
        String image     = pnull(_params.get("image"));
        String imgfile   = pnull(_params.get("imgfile"));
        String displayYN = pnull(_params.get("displayYN"));
        String displayNo = pnull(_params.get("displayNo"));
        String filePath  = imgfile.substring(0, imgfile.lastIndexOf("/")+1); 
               imgfile   = imgfile.substring( imgfile.lastIndexOf("/")+1 );
        
        int i = 1;
        try
        {
        	query = "INSERT INTO HISTORY_MASTER "
        			+ " (category_name, image, FILE_PATH, displayYN, displayNo, registDT, updateDT) "
        			+ " VALUES "
        			+ " (?, ?, ?, ?, ?, getdate(), getdate()) ";
        	LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
        	params.put(""+i++, category_name);
        	params.put(""+i++, imgfile);
        	params.put(""+i++, filePath);
        	params.put(""+i++, displayYN);
        	params.put(""+i++, Integer.parseInt(displayNo));
        	i = 0;
        	i = executeUpdatePreparedStatement(query, params);
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
            bReturn = false;
        } finally {
        	free();
    	}
        return bReturn;
    }

    @Override
    public boolean historymModify(Map _params) throws Exception
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
        boolean bReturn = true;

        String category_code = pnull(_params.get("category_code"));
        String category_name = pnull(_params.get("category_name"));
        String image     = pnull(_params.get("image"));
        String imgfile   = pnull(_params.get("imgfile"));
        String filePath  = imgfile.substring(0, imgfile.lastIndexOf("/")+1); 
               imgfile   = imgfile.substring( imgfile.lastIndexOf("/")+1 );
        String displayYN = pnull(_params.get("displayYN"));
        String displayNo = pnull(_params.get("displayNo"));
        
        // file delete
        if(pnull(_params.get("delImg")).equalsIgnoreCase("Y")) {
            Map delRow = executeQueryMap("SELECT * FROM HISTORY_MASTER WHERE category_code = " + category_code );
            
            String wwwroot = pnull(_params, "WWWROOT");
            String delFile = pnull(delRow, "IMAGE");
            String delPath = pnull(delRow, "FILE_PATH");
            if(kr.caincheon.church.common.utils.ImageUtils.deleteFileWithThumbnail(wwwroot + delPath, delFile)) {
            	I(_logger, Thread.currentThread().getStackTrace(), "File Delete : Success, Target:" + wwwroot + delPath + delFile);
            } else {
            	E(_logger, Thread.currentThread().getStackTrace(), "File Delete : Fail, Target:" + wwwroot + delPath + delFile);
            }
        }
        
        String query ="";
        int i = 1;
        try {
        	query = "UPDATE HISTORY_MASTER  SET category_name=?"
        			+ ", image=?"
        			+ ( filePath.length()>0 ? ", FILE_PATH=?" : "" )
        			+ ", displayYN=?, displayNo=?, updateDT=getdate()  WHERE category_code = ? ";
        	LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
        	params.put(""+i++, category_name);
        	params.put(""+i++, imgfile);
        	if(filePath.length() > 0) { params.put(""+i++, filePath); }
        	params.put(""+i++, displayYN);
        	params.put(""+i++, Integer.parseInt(displayNo));
        	params.put(""+i++, Integer.parseInt(category_code));
        	i = 0;
        	i = executeUpdatePreparedStatement(query, params);
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
            bReturn = false;
        } finally {
        	free();
    	}
        return bReturn;
    }

	@Override
    public boolean historymDelete(Map _params) throws Exception
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
        boolean bReturn = false;
        
        String category_code = pnull(_params.get("category_code")), query = "";

        //file delete
        if(pnull(_params.get("delImg")).equalsIgnoreCase("Y")) {
            Map delRow = executeQueryMap("SELECT * FROM HISTORY_MASTER WHERE category_code = " + category_code );
            
            String wwwroot = pnull(_params, "WWWROOT");
            String delFile = pnull(delRow, "IMAGE");
            String delPath = pnull(delRow, "FILE_PATH");
            if(kr.caincheon.church.common.utils.ImageUtils.deleteFileWithThumbnail(wwwroot + delPath, delFile)) {
            	I(_logger, Thread.currentThread().getStackTrace(), "File Delete : Success, Target:" + wwwroot + delPath + delFile);
            } else {
            	E(_logger, Thread.currentThread().getStackTrace(), "File Delete : Fail, Target:" + wwwroot + delPath + delFile);
            }
        }
        
        try {
        	query = "delete from HISTORY_MASTER where category_code = " + category_code;
            int i = executeUpdate(query);
            bReturn = i == 1 ? true : false;
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        	free();
    	}
        
        return bReturn;
    }


	@Override
	public List historyEventsList(Map _params, boolean isAdminCall) throws Exception
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
        String whereStr="";
        List result=null;
        
        int pageNo    = ipnull(_params, "pageNo", 1);
        int pageSize  = ipnull(_params, "pageSize", 20);
        int startRnum = (pageNo - 1) * pageSize + 1;
        int endRnum   = pageNo * pageSize;
        
        String category_code = pnull(_params.get("category_code"));
        if(category_code.length() > 0 && !category_code.equals("ALL"))
            whereStr = " AND s.category_code = '"+category_code+"' ";
                
        String Q_YEAR      = pnull(_params.get("Q_YEAR")); // front에서 연혁을 조회하기 위한 것으로 화면이 아닌, `Service` 에서 생성되는 파라메터이다.
        String Q_CATE      = pnull(_params.get("CATEGORY_CODE")); // front에서 연혁을 조회하기 위한 것으로 화면이 아닌, `Service` 에서 생성되는 파라메터이다.
        String searchText  = pnull(_params.get("searchSText"));
        String searchGubun = pnull(_params.get("searchGubun"));
        if(searchText.length() > 0)
            if(searchGubun.equals("TITLE"))
                whereStr += " AND s.title like '%"+searchText+"%' ";
            else
            if(searchGubun.equals("CONTENTS"))
                whereStr += " AND s.contents like '%"+searchText+"%' ";
        
        String query = "";
        try
        {
            query = "SELECT * FROM ("
            		+ " SELECT ROW_NUMBER() OVER(ORDER BY S.CATEGORY_CODE DESC, S.EVENTDT DESC) RNUM"
            		+ " , S.CATEGORY_CODE, M.CATEGORY_NAME"
            		+ " , S.HISTORY_NO, S.TITLE, S.CONTENTS, S.IMAGE, S.FILE_PATH, S.DISPLAYYN"
            		+ " , CASE WHEN S.DISPLAYYN = 'Y' THEN '노출'  ELSE	'비노출'  END AS DISPLAYYN_TEXT"
            		+ " , EVENTDT, SUBSTRING(EVENTDT,1,4) EVENTYEAR, CONVERT(VARCHAR(10),  S.REGISTDT, 120) REGISTDT "
            		+ " , CASE WHEN LEN(EVENTDT)=10 THEN SUBSTRING(EVENTDT,6,10) WHEN LEN(EVENTDT)=7 THEN SUBSTRING(EVENTDT,6,10)+'월' ELSE ' ' END AS EVENTMMDD "
            		+ " FROM HISTORY_SLAVE S "
            		+ " LEFT OUTER JOIN HISTORY_MASTER M ON S.CATEGORY_CODE=M.CATEGORY_CODE "
            		+ " WHERE 1=1 "+whereStr
            		+ (isAdminCall ? "" : " AND S.DISPLAYYN = 'Y' " )
            		+ (Q_YEAR.length() < 1 ? "" : " AND SUBSTRING(EVENTDT,1,4)='"+Q_YEAR+"'")
            		+ (Q_CATE.length() < 1 ? "" : " AND S.CATEGORY_CODE='"+Q_CATE+"'")
            		+ ") ROWS "
            		+ " WHERE RNUM BETWEEN "+startRnum+" AND "+endRnum
            		;
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
	public int historyEventsListCount(Map _params) throws Exception
    {
        String whereStr = "", query = "";
        int result=0;
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        String category_code = pnull(_params.get("category_code"));
        String searchText    = pnull(_params.get("searchSText"));
        String searchGubun   = pnull(_params.get("searchGubun"));
        
        if(category_code.length() > 0 && !category_code.equals("ALL"))
            whereStr = " AND category_code = '"+category_code+"' ";
        
        if(searchText.length() > 0) {
            if(searchGubun.equals("제목"))
                whereStr += " AND title like '%"+searchText+"%' ";
            else if(searchGubun.equals("내용"))
                whereStr += " AND contents like '%"+searchText+"%' ";
        }

        try
        {
            query = " SELECT COUNT(1) FROM HISTORY_SLAVE WHERE 1=1 "+whereStr;
            result = super.executeCount(query, false);
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        	free();
    	}
        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+result );
        return result;
    }

	@Override
	public Map historysContents(Map _params) throws Exception
    {
        _logger.info((new StringBuilder("_params=")).append(_params.toString()).toString());
        
        Map result = new HashMap();
        String query_type = pnull(_params.get("query_type"));
        String history_no = pnull(_params.get("history_no"));
        String query = "", whereStr = "";
        try
        {
            if(query_type.equals("modify"))
            {
                whereStr = " AND history_no = '"+history_no+"'";
                query   = "SELECT category_code, title, eventDT, image, FILE_PATH, contents, displayYN, history_no "
                		+ " FROM HISTORY_SLAVE WHERE 1=1 "+whereStr;
                result = super.executeQueryMap(query);
            }
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        	free();
    	}
        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+result );
        return result;
    }

	@Override
	public boolean historysInsert(Map _params) throws Exception
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
        boolean bReturn = true;
        
        String query = "";
        String category_code = pnull(_params.get("category_code"));
        String title     = pnull(_params.get("title"));
        String eventDT   = pnull(_params.get("eventDT"));
        String image      = pnull(_params.get("image"));
        String imgfile    = pnull(_params.get("imgfile"));
        String filePath  = imgfile.substring(0, imgfile.lastIndexOf("/")+1);
               imgfile   = imgfile.substring( imgfile.lastIndexOf("/")+1 );
        String contents  = pnull(_params.get("contents"));
        String displayYN = pnull(_params.get("displayYN"));
        
        int i = 1;
        try
        {
        	query = "INSERT INTO HISTORY_SLAVE (category_code, title, eventDT, image, FILE_PATH, contents, displayYN, registDT, updateDT) "
        			+ " VALUES (?, ?, ?, ?, ?, ?, ?, getdate(), getdate()) ";
        	LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
        	params.put(""+i++, Integer.parseInt(category_code));
        	params.put(""+i++, title);
        	params.put(""+i++, eventDT);
        	params.put(""+i++, imgfile);
        	params.put(""+i++, filePath);
        	params.put(""+i++, contents);
        	params.put(""+i++, displayYN);
        	i = 0;
        	i = executeUpdatePreparedStatement(query, params);
        	
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
            bReturn = false;
        } finally {
        	free();
    	}
        return bReturn && i > 0 ;
    }

	@Override
	public boolean historysModify(Map _params) throws Exception
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
        boolean bReturn = true;
        
        String category_code = pnull(_params.get("category_code"));
        String title      = pnull(_params.get("title"));
        String eventDT    = pnull(_params.get("eventDT"));
        String contents   = pnull(_params.get("contents"));
        String displayYN  = pnull(_params.get("displayYN"));
        String history_no = pnull(_params.get("history_no"));
        String imgfile    = pnull(_params.get("imgfile"));//uploaded path + name
        String filePath   = "", imgfileNm ="";
        if(imgfile.length()>0) {
        	filePath   = imgfile.substring(0, imgfile.lastIndexOf("/")+1);// path
            imgfileNm  = imgfile.substring( imgfile.lastIndexOf("/")+1 );// file name
        }
        
        // file delete
        if(pnull(_params.get("delImg")).equalsIgnoreCase("Y")) {
            Map delRow = executeQueryMap("SELECT * FROM HISTORY_SLAVE WHERE HISTORY_NO="+history_no );
            String wwwroot = pnull(_params, "WWWROOT");
            String delFile = pnull(delRow, "IMAGE");
            String delPath = pnull(delRow, "FILE_PATH");
            if(kr.caincheon.church.common.utils.ImageUtils.deleteFileWithThumbnail(wwwroot + delPath, delFile)) {
            	I(_logger, Thread.currentThread().getStackTrace(), "File Delete : Success, Target:" + wwwroot + delPath + delFile);
            } else {
            	E(_logger, Thread.currentThread().getStackTrace(), "File Delete : Fail, Target:" + wwwroot + delPath + delFile);
            }
        }
        
        String query = "";
        int i = 1;
        try
        {
            query = "UPDATE HISTORY_SLAVE SET "
            		+ " CATEGORY_CODE=?" /* 180325 카테고리가 변경될 수 있도록 변경함. */
            		+ ", TITLE=?"
            		+ ", EVENTDT=?"
            		+ ( imgfileNm.length()>0 ? ", IMAGE=?" : "" )
            		+ ( filePath.length()>0  ? ", FILE_PATH=?" : "" )
            		+ ", CONTENTS=?"
            		+ ", DISPLAYYN=?"
            		+ ", UPDATEDT=GETDATE() "
            		+ " WHERE HISTORY_NO=? "
            		;
                    
        	LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
        	params.put(""+i++, Integer.parseInt(category_code));
        	params.put(""+i++, title);
        	params.put(""+i++, eventDT);
        	if(imgfileNm.length()>0) { params.put(""+i++, imgfileNm); }
        	if(filePath.length()>0)  { params.put(""+i++, filePath); }
        	params.put(""+i++, contents);
        	params.put(""+i++, displayYN);
        	params.put(""+i++, history_no);
        	i = 0;
        	i = executeUpdatePreparedStatement(query, params);
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
            bReturn = false;
        } finally {
        	free();
    	}
        return bReturn && i > 0;
    }

	@Override
	public boolean historysDelete(Map _params) throws Exception
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
        boolean bReturn = true;
        String category_code = pnull(_params.get("category_code"));
        String history_no    = pnull(_params.get("history_no")), query = "";

        // file delete
        if(pnull(_params.get("delImg")).equalsIgnoreCase("Y")) {
            Map delRow = executeQueryMap("SELECT * FROM HISTORY_SLAVE WHERE CATEGORY_CODE=" + category_code + " AND HISTORY_NO="+history_no );
            
            String wwwroot = pnull(_params, "WWWROOT");
            String delFile = pnull(delRow, "IMAGE");
            String delPath = pnull(delRow, "FILE_PATH");
            if(kr.caincheon.church.common.utils.ImageUtils.deleteFileWithThumbnail(wwwroot + delPath, delFile)) {
            	I(_logger, Thread.currentThread().getStackTrace(), "File Delete : Success, Target:" + wwwroot + delPath + delFile);
            } else {
            	E(_logger, Thread.currentThread().getStackTrace(), "File Delete : Fail, Target:" + wwwroot + delPath + delFile);
            }
        }
        
        int i = 0 ;
        try {
        	query = "DELETE FROM HISTORY_SLAVE "
        			+ " WHERE CATEGORY_CODE=" + category_code + " AND HISTORY_NO="+history_no;
            i = executeUpdate(query);
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
            bReturn = false;
        } finally {
        	free();
    	}
        return bReturn && i > 0;
    }

	@Override
	public List categoryList(Map _params) throws Exception
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
        List result = null;
        String query = "";
        
        try {
            query = " SELECT CONVERT(VARCHAR(10), CATEGORY_CODE) AS CATEGORY_CODE, CATEGORY_NAME "
            		+ " FROM HISTORY_MASTER "
            		+ " WHERE displayYN='Y' AND LEN(CATEGORY_NAME)>0 ";
            result = super.executeQueryList(query);
            
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
	 * (non-Javadoc)
	 * @see kr.caincheon.church.dao.HistoryDao#categoryFullList(java.util.Map)
	 */
	@Override
	public List<Map<String, Object>> categoryFullList(Map _params) throws Exception
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        List<Map<String, Object>> result = null;
        String query = "";
        try
        {
            query = " SELECT CONVERT(VARCHAR(10),CATEGORY_CODE) AS CATEGORY_CODE, CATEGORY_NAME, IMAGE, FILE_PATH "
            		+ " FROM HISTORY_MASTER "
            		+ " WHERE displayYN='Y' "
            		+ " AND CATEGORY_CODE IN (SELECT DISTINCT CATEGORY_CODE FROM HISTORY_SLAVE WHERE displayYN='Y' AND LEN(TITLE)>0 AND LEN(EVENTDT)>0 ) "
            		+ " ORDER BY DISPLAYNO ASC ";
            result = super.executeQueryList(query);
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        	free();
    	}
        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+result );
        return result;
    }

	/**
	 * 특정 카테고리의 연도 및 행사내용을 조회
	 * (non-Javadoc)
	 * @see kr.caincheon.church.dao.HistoryDao#historyEventyearsOnCategory(java.lang.String)
	 */
	@Override
	public List<Map<String, Object>> historyEventyearsOnCategory(String category_code) throws Exception
    {
		//
		List rtList = null;
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[category_code:"+category_code+"]" );
        
        //String category_code = pnull(_params.get("category_code"));
        String query = "";
        try
        {
            query = "SELECT * FROM ( "
            		+ " SELECT DISTINCT "
            		+ " S.CATEGORY_CODE, LEFT(S.EVENTDT,4) AS EVENTYEAR "
            		//+ " , S.TITLE, S.CONTENTS, S.FILE_PATH, S.IMAGE "
            		+ " FROM HISTORY_SLAVE S "
            		+ " LEFT OUTER JOIN HISTORY_MASTER M ON S.CATEGORY_CODE=M.CATEGORY_CODE "
            		+ " WHERE S.displayYN='Y' AND LEN(S.EVENTDT)>0 "+(category_code.length() == 0 ? "" : " AND S.CATEGORY_CODE="+category_code)
            		+ " ) XX ORDER BY CATEGORY_CODE DESC, EVENTYEAR DESC ";
            rtList = super.executeQueryList(query);
            
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        	free();
    	}
        
        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+rtList );
        
        return rtList;
    }

    
}
