// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AlbDaoImpl.java

package kr.caincheon.church.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import kr.caincheon.church.common.base.CommonDao;
import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.utils.FileUtils;
import kr.caincheon.church.common.utils.UtilDate;
import kr.caincheon.church.common.utils.UtilInt;

// Referenced classes of package kr.caincheon.church.dao:
//            AlbDao

@Repository("albDao")
public class AlbDaoImpl extends CommonDao
    implements AlbDao
{

    private final Logger _logger = Logger.getLogger(getClass());
    
	@Override
    public String getBoardCategory(Map _params) throws Exception
    {
        return null;
    }

	@Override
    public List albList(Map _params) throws Exception
    {
        String c_idx = pnull(_params.get("c_idx"));
        String whereStr = " AND IS_VIEW='Y' ";
        List   result = null;
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        String searchGubun = pnull(_params.get("searchGubun"));
        String searchText = pnull(_params.get("searchText"));
        String searchYear1 = pnull(_params.get("searchYear1"));
        String searchYear2 = pnull(_params.get("searchYear2"));
        
        int pageNo    = ipnull(_params, "pageNo", 1);
        int startRnum = (pageNo - 1) * 12 + 1;
        int endRnum   = pageNo * 12;
        
        if(searchGubun != null) {
            if(searchGubun.equals("date")) {
                whereStr   = whereStr+" AND REGDATE > '"+searchYear1+"' AND REGDATE < '"+searchYear2+"'";
            } else if(searchGubun.equals("contents")) {
                whereStr   = whereStr+" AND TITLE LIKE '%"+searchText+"%'";
            }
        }
        
        String query = "", tmp = "  ";
        try
        {
        	if(c_idx.equals("ALL") || "".equals(c_idx))
        		tmp = " C_IDX IN ('1','2') " + whereStr;
    		else
    			tmp = " C_IDX IN ("+c_idx+") " + whereStr;
        	
        	if(c_idx.equals("ALL") || "".equals(c_idx))
                query = "SELECT * FROM ( "
                		+ "   SELECT ROW_NUMBER() OVER(ORDER BY REGDATE DESC, IDX DESC) RNUM, TITLE, FILEPATH, FILENAME, STRFILENAME, convert(char(10),  ALBUM_DATE, 120) ALBUM_DATE"
                		+ "          , convert(char(10),  REGDATE, 120) REGDATE, HIT, IDX "
                		+ "   FROM   PARISH_ALBUM A1 WHERE "+tmp
                		+ " ) A "
                		+ " WHERE RNUM BETWEEN "+startRnum+" AND "+endRnum;
            else
                query = "SELECT * FROM  ("
                		+ "   SELECT ROW_NUMBER() OVER(ORDER BY REGDATE DESC, IDX DESC) RNUM, TITLE, FILEPATH, FILENAME, STRFILENAME, convert(char(10),  ALBUM_DATE, 120) ALBUM_DATE"
                		+ "          , convert(char(10),  REGDATE, 120) REGDATE, HIT, IDX  "
                		+ "   FROM   PARISH_ALBUM A1 WHERE "+tmp
                		+ " ) A "
                		+ " WHERE RNUM BETWEEN "+startRnum+" AND "+endRnum;
            result = super.executeQueryList(query);
            
            if(result == null) result = new ArrayList();
            
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        	free();
    	}
        
        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+result );
        
        return result;
    }

	@Override
    public int albListCount(Map _params) throws Exception
    {

        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );

        String c_idx = pnull(_params.get("c_idx"));
        String whereStr = " IS_VIEW='Y' ";
        int result=0;
        
        String searchGubun = pnull(_params.get("searchGubun"));
        String searchText  = pnull(_params.get("searchText"));
        String searchYear1 = pnull(_params.get("searchYear1"));
        String searchYear2 = pnull(_params.get("searchYear2"));
        
        if(searchGubun.length() > 0 ) {
            if(searchGubun.equals("date")) {
                whereStr = whereStr+" AND REGDATE > '"+searchYear1+"' AND REGDATE < '"+searchYear2+"'";
            } else if(searchGubun.equals("contents")) {
                whereStr = whereStr+" AND TITLE LIKE '%"+searchText+"%'";
            }
        }
        
        String query = "";
        try {
            if(c_idx.equals("ALL") || "".equals(c_idx))
                query = "SELECT count(1) FROM PARISH_ALBUM WHERE "+whereStr;
            else
                query = "SELECT count(1) FROM PARISH_ALBUM WHERE "+whereStr+" AND c_idx='"+c_idx+"' ";
            result = super.executeCount(query, false);
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
        	free();
    	}
        
        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+result );
        
        return result;
    }

	@Override
    public Map albContents(Map _params) throws Exception
    {
        Map result = null;
        String query = "";
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        try {
            String whereStr = " AND IS_VIEW='Y'";
            query = "SELECT IDX, TITLE, CONTENT, USER_ID, WRITER, CONVERT(CHAR(10), ALBUM_DATE, 120) ALBUM_DATE, REGDATE, IS_VIEW, ORG_IDX, HIT, IS_NOTICE"
            		+ ", FILEPATH, FILENAME, STRFILENAME, FILESIZE, C_IDX "
            		+ ", (SELECT TOP 1 IDX FROM PARISH_ALBUM WHERE IDX=(SELECT MAX(IDX) FROM PARISH_ALBUM X WHERE X.IDX < A.IDX)) AS before_p_idx"
            		+ ", (SELECT TOP 1 IDX FROM PARISH_ALBUM WHERE IDX=(SELECT MIN(IDX) FROM PARISH_ALBUM X WHERE X.IDX > A.IDX)) AS next_p_idx "
            		+ " FROM PARISH_ALBUM A "
            		+ " WHERE IDX='"+_params.get("idx")+"' "+whereStr;
            result = super.executeQueryMap(query);
            
            // view count up
            executeUpdate("UPDATE PARISH_ALBUM SET HIT=HIT+1 WHERE IDX="+_params.get("idx"));
            
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        	free();
        	if(result==null) result = new HashMap();
    	}
        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+result );
        return result;
    }

	@Override
    public List albDList(Map _params) throws Exception
    {
        List result = null;
        String query = "";
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        try {
            query = "SELECT FILENAME, FILEPATH, FILESIZE, P_IDX FROM PARISH_UPLOAD WHERE P_IDX='"+_params.get("p_idx")+"' AND IS_USE='Y'";
            result = super.executeQueryList(query);
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        	free();
        	if(result==null) result = new ArrayList();
    	}
        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+result );
        return result;
    }

	/**
     * 교구앨범 메인홈페에 서비스되는 OP
     */
	@Override
    public List albList_Main(Map _params) throws Exception
    {
        String whereStr;
        List result = null;
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        String query = "";
        whereStr = " IS_VIEW='Y'";
        
        try
        {
            query = "SELECT TOP(10) * FROM  (SELECT ROW_NUMBER() OVER(ORDER BY ALBUM_DATE DESC, IDX DESC) RNUM, TITLE, FILEPATH, STRFILENAME"
            		+ ", convert(char(10),  ALBUM_DATE, 120) ALBUM_DATE, convert(char(10),  REGDATE, 120) REGDATE, HIT, IDX  "
            		+ " FROM PARISH_ALBUM A1 "
            		+ " WHERE "+whereStr+" ) A ";
            result = super.executeQueryList(query);
        }
        catch(Exception e)
        {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        	free();
    	} 
        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+result );
        return result;
    }

	@Override
    public Map scheduleByDiary(Map _params) throws Exception
    {
        String srch_ym = pnull(_params.get("srch_ym"));
        String query="";
        Map rtMap = new HashMap();
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        if(srch_ym.length() == 0)
            srch_ym = UtilDate.getDateFormat("yyyy-MM");
        else if(srch_ym.length() != 7)
            throw new Exception("\uC870\uD68C\uD558\uB294 \uB144\uC6D4\uC758 \uB370\uC774\uD130 \uD615\uC2DD\uC774 \uB9DE\uC9C0 \uC54A\uC74C [TYPE:YYYY-MM, "+srch_ym+"]");
        
        
        List result = null;
        try
        {
            String yyyymm = srch_ym.replace("-", "").replace(".", "");
            query = " WITH t AS (   \n SELECT DATEADD(d, 0, '"+yyyymm+"'+'01') dt "
            		+ " UNION ALL "
            		+ " SELECT DATEADD(d, 1, dt) dt FROM t WHERE dt + 1 < DATEADD(m, 1, '"+yyyymm+"'+'01') ) "
            		+ "        SELECT [1] \uC77C , [2] \uC6D4  , [3] \uD654, [4] \uC218 , [5] \uBAA9 , [6] \uAE08 , [7] \uD1A0 "
            		+ " FROM (SELECT DATEPART(d, dt) d , DATEPART(w, dt) w , DATEPART(ww, dt) ww FROM t ) a "
            		+ " PIVOT( MIN(d) FOR w IN ([1], [2], [3], [4], [5], [6], [7]) ) a ";
            List list = executeQueryList(query);
            rtMap.put("DIARY", list);
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL1:"+query, e);
        } finally {
    	}
        
        
        try {
            query = "SELECT CONVERT( CHAR(10), START_DATE, 120) S_DATE, CAST(SUBSTRING(CONVERT( CHAR(10), START_DATE, 120), 9, 10)  AS INT) S_DAY, S_IDX, TITLE "
            		+ " FROM SCHEDULE  "
            		+ " WHERE GUBUN = '\uAD50\uAD6C\uC7A5'  AND CONVERT( CHAR(7), START_DATE, 120)='"+srch_ym+"' "
            		+ " ORDER BY START_DATE ASC ";
            List list = executeQueryList(query);
            Map schedule = new HashMap();
            int i = 0;
            for(int i2 = list.size(); i < i2; i++)
            {
                Map row = (Map)list.get(i);
                Integer iDay = new Integer(UtilInt.pint(row.get("S_DAY")));
                HashMap ent = new HashMap();
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
        
        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+result );
        return rtMap;
    }

	@Override
    public Map scheduleContents(Map _params) throws Exception
    {
        Map rtMap = null;
        String query = "";
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        try
        {
            query = "SELECT * FROM SCHEDULE  WHERE S_IDX = '"+pnull(_params.get("s_idx"))+"' ORDER BY START_DATE ASC ";
            List l = executeQueryList(query);
            if(l.size() > 0)
                rtMap = (Map)l.get(0);
        } catch(Exception e) {
            rtMap = new HashMap();
            _logger.error((new StringBuilder("ERROR SQL2:")).append(query).toString(), e);
        } finally {
        	free();
    	}
        
        return rtMap;
    }

	// 교구장 앨범
	@Override
    public List oldAlbList(Map _params) throws Exception
    {
        List result = null;

        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        int pageNo      = ipnull(_params, "pageNo", 1);
        int pageSize    = ipnull(_params, "pageSize", 10);
        int startRnum   = (pageNo - 1) * pageSize + 1;
        int endRnum     = pageNo * pageSize;
        String c_idx       = pnull(_params, "c_idx"); // 미사(31) 행사(32) 교육(33) .. newcaincheon.NBOARD_CATEGORY 참조 
        String s_gubun     = pnull(_params, "s_gubun");     /* 역대교구장 앨범 1대  1, 2대  2 */
        String album_gubun = pnull(_params, "album_gubun"); /* 역대교구장 앨범 1대  1, 2대  2 */
        String s_search    = "";
        String searchText  = pnull(_params.get("searchText"));
        String whereStr    = " ALBUM_GUBUN IN ('1','2') ";//" ALBUM_GUBUN IN ('41','42') "; /* 역대교구장 앨범 1대 41, 2대 42 */
        
        if( "41".equals(album_gubun) || "42".equals(album_gubun) )
            whereStr = " ALBUM_GUBUN = '"+album_gubun+"' ";
        else {
        	if("41".equals(s_gubun) || "42".equals(s_gubun)) { /* EverController에서 호출되는 경우 s_gubun이 넘어온다. */
        		whereStr = " ALBUM_GUBUN = '"+s_gubun+"' ";
        	}
        }
        
        if(searchText != null && searchText.length() > 0)
        {
            s_search = pnull(_params.get("s_search"));
            if(s_search.equals("all"))
                whereStr = whereStr+" AND ( TITLE LIKE '%"+searchText+"%' OR CONTENT LIKE '%"+searchText+"%') ";
            else if(s_search.equals("title"))
                whereStr = whereStr+" AND TITLE LIKE '%"+searchText+"%' ";
            else if(s_search.equals("content"))
                whereStr = whereStr+" AND CONTENT LIKE '%"+searchText+"%' ";
        }
        
        String query = "";
        try {
            query = "SELECT * FROM  ( "
            		+ "    SELECT ROW_NUMBER() OVER(ORDER BY REGDATE DESC) RNUM, idx, ALBUM_GUBUN, title, convert(char(10),regdate,120) as regdate, hit, filepath, strfilename  "
            		+ "    FROM PARISH_ALBUM  "
            		+ "    WHERE "+whereStr
            		+ " ) A "
            		+ " WHERE RNUM BETWEEN "+startRnum+" AND "+endRnum;
            
            result = super.executeQueryList(query);
            
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        	free();
    	}
        
        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+result );
        
        return result == null ? new ArrayList() : result;
    }

	// 교구장 앨범
	@Override
    public int oldAlbListCount(Map _params) throws Exception
    {
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        int result = 0;
        int pageNo    = ipnull(_params, "pageNo", 1);
        int startRnum = (pageNo - 1) * 10 + 1;
        int endRnum   = pageNo * 10;
        String c_idx       = pnull(_params, "c_idx");
        String album_gubun = pnull(_params, "album_gubun");
        String s_search    = "";
        String searchText  = pnull(_params.get("searchText"));
        String whereStr    = " ALBUM_GUBUN IN ('41','42') "; // 
        
        if( "41".equals(album_gubun) || "42".equals(album_gubun) )
            whereStr = " ALBUM_GUBUN = '"+album_gubun+"' ";
        
        if(searchText != null && searchText.length() > 0)
        {
            s_search = pnull(_params.get("s_search"));
            if(s_search.equals("all"))
                whereStr = whereStr+" AND ( TITLE LIKE '%"+searchText+"%' OR CONTENT LIKE '%"+searchText+"%') ";
            else if(s_search.equals("title"))
                whereStr = whereStr+" AND TITLE LIKE '%"+searchText+"%' ";
            else if(s_search.equals("content"))
                whereStr = whereStr+" AND CONTENT LIKE '%"+searchText+"%' ";
        }
        
        String query = "";
        try
        {
            query = "SELECT COUNT(1) FROM PARISH_ALBUM  WHERE "+whereStr;
            result = super.executeCount(query, false);
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        	free();
    	}
        
        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+result );
        
        return result;
    }

	/*
	 * front에서 앨범 보기
	 */
	@Override
    public Map oldAlbContents(Map _params) throws Exception
    {
        Map result = new HashMap();
        String idx = pnull(_params.get("idx")), query = "";
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        try {
            query = " SELECT "
            		+ "  (SELECT TOP 1 IDX FROM PARISH_ALBUM WHERE IDX > A.IDX ORDER BY IDX ASC ) AS PRE_IDX "
            		+ ", (SELECT TOP 1 IDX FROM PARISH_ALBUM WHERE IDX < A.IDX ORDER BY IDX DESC) AS NXT_IDX "
            		+ ", A.* "
            		+ " FROM PARISH_ALBUM A "
            		+ " WHERE IDX='"+idx+"'";
            result = super.executeQueryMap(query);
            
            // count up +1
            if(result.size() > 0) {
            	executeUpdate("UPDATE PARISH_ALBUM SET HIT=HIT+1 WHERE IDX='"+idx+"'");
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
    public boolean oldAlbInsert(Map _params, List list) throws Exception
    {
        boolean bReturn = true;
        Connection connection;
        PreparedStatement preparedStatement;
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        String title       = pnull(_params.get("title"));
        String filepath    = pnull(_params, "CONTEXT_URI_PATH");//pnull(_params, "CONTEXT_ROOT_PATH");//FileUtils.getAlbumfilepath();
        String filename    = pnull(_params.get("filename"));
        String contents    = pnull(_params.get("contents"));
        String is_view     = pnull(_params.get("is_view"));
        String org_idx     = pnull(_params.get("org_idx"));
        String c_idx       = pnull(_params.get("c_idx"));
        String album_gubun = pnull(_params.get("album_gubun"));
        String strfilename = "";
        String orgfilename = "";
        String filesize    = "";
        for(int i = 0; i < list.size(); i++) {
            Map tmp = (Map)list.get(i);
            strfilename = pnull(tmp.get("STORED_FILE_NAME"));
            filesize = pnull(tmp.get("FILE_SIZE"));
            orgfilename = pnull(tmp.get("ORIGINAL_FILE_NAME"));
        }

        connection = null;
        preparedStatement = null;
        int j = 0;
        try
        {
        	//String fileUploadedPath = pnull(_params, "CONTEXT_ROOT_PATH");//"d:\\newcaincheon\\webapps\\upload\\"
        	
        	if(connection==null || connection.isClosed()) connection = dataSource.getConnection();
            String query = "INSERT INTO PARISH_ALBUM  (idx, C_IDX, title, content, filepath, filename, is_view, regdate, strfilename, filesize, org_idx, album_gubun) "
            		+ " VALUES ((select ISNULL(MAX(idx), 0) + 1 from PARISH_ALBUM)"
            				+ ",?, ?, ?, ?, ?, ?, getdate(), ?, ?, ?) ";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, c_idx);
            preparedStatement.setString(2, title);
            preparedStatement.setString(3, contents);
            preparedStatement.setString(4, filepath);
            preparedStatement.setString(5, orgfilename);
            preparedStatement.setString(6, is_view);
            preparedStatement.setString(7, strfilename);
            preparedStatement.setString(8, filesize);
            preparedStatement.setString(9, org_idx);
            preparedStatement.setString(10, album_gubun);
            j = preparedStatement.executeUpdate();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            bReturn = false;
        } finally {
        	if(preparedStatement != null) try { preparedStatement.close(); } catch ( Exception e ) { e.printStackTrace(); }
            if(connection != null) try { connection.close();} catch ( Exception e ) { e.printStackTrace(); }
        	free();
    	}
        return bReturn && j>0;
    }

	@Override
    public boolean oldAlbModify(Map _params, List list) throws Exception
    {
        boolean bReturn = false;
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        Map memberInfo = getAdmin(_params);
        if(memberInfo == null) { memberInfo = new HashMap(); memberInfo.put("NAME", "관리자"); }
        
        String master_id = pnull(_params.get("user_id"));
        String idx       = pnull(_params.get("idx"));
        String c_idx     = pnull(_params.get("c_idx"));
        String title     = pnull(_params.get("title"));
        String contents  = pnull(_params.get("contents"));
        String is_view   = pnull(_params.get("is_view"));
        String org_idx   = pnull(_params.get("org_idx"));
        String album_date  = pnull(_params.get("album_date"));
        String strfilename = "", orgfilename = "", filesize    = "";
        String ctxUri      = pnull(_params, "CONTEXT_URI_PATH");
        String album_gubun = pnull(_params, "album_gubun");
        
        String query       = "";
        
        // uploaded file infor
        for(int i = 0; i < list.size(); i++)
        {
            Map tmp     = (Map)list.get(i);
            strfilename = pnull(tmp.get("STORED_FILE_NAME"));
            filesize    = pnull(tmp.get("FILE_SIZE"));
            orgfilename = pnull(tmp.get("ORIGINAL_FILE_NAME"));
        }

        // 기존 파일 삭제
        if(pnull(_params.get("delFile")).equals("Y")) {
        	// select a file infor
        	query = "SELECT FILEPATH, STRFILENAME FROM PARISH_ALBUM WHERE IDX=" + idx;
        	try {
				Map m = executeQueryMap(query);
				String savedFileName = pnull(m, "STRFILENAME");
	        	// file delete
	        	String realPath = pnull(_params, "CONTEXT_ROOT_PATH");
	        	if(FileUtils.getInstance().deleteFile(realPath + savedFileName, realPath + "thumbnail/" + savedFileName)) {
	        		I(_logger, Thread.currentThread().getStackTrace(), "File Delete Result: true");
	        	} else {
	        		I(_logger, Thread.currentThread().getStackTrace(), "File Delete Result: false" + realPath + savedFileName );
	        	}
	        	
			} catch (SQLException e) {
				_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
			}
        }
        
//
//        int j = 0;
//        try
//        {
//        	if(connection==null || connection.isClosed()) connection = dataSource.getConnection();
//            String query = "UPDATE PARISH_ALBUM  SET ALBUM_GUBUN=?, title=?, content=?, filename=?, is_view=?,  strfilename=?, filesize=?  WHERE idx = ? ";
//            preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setString(1, gubun);
//            preparedStatement.setString(2, title);
//            preparedStatement.setString(3, content);
//            preparedStatement.setString(4, orgfilename);
//            preparedStatement.setString(5, is_view);
//            preparedStatement.setString(6, strfilename);
//            preparedStatement.setString(7, filesize);
//            preparedStatement.setInt(8, Integer.parseInt(idx));
//            j = preparedStatement.executeUpdate();
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//            bReturn = false;
//        } finally {
//    	}

        try
        {
        	LinkedHashMap<String, Object> pstmtParams = getLinkedHashMap(album_date
			        			, master_id
			        			, pnull(memberInfo, "NAME")
			        			, title
			        			, contents
			        			, is_view
			        			, ctxUri
			        			, orgfilename
			        			, filesize
			        			, c_idx
			        			, album_gubun
			        			, strfilename
			        			, org_idx
			        			, Integer.parseInt(idx));
			query = "UPDATE PARISH_ALBUM  SET "
			+ " album_date=?, user_id=?, writer=?,  title=?, content=?, is_view=?, filepath=?, filename=?, filesize=?,  C_IDX=?, album_gubun=?, strfilename=?, org_idx=? "
			+ " WHERE idx = ? ";
			
			bReturn = executeUpdatePreparedStatement(query, pstmtParams)==1 ? true : false;

//        	if(connection==null || connection.isClosed()) connection = dataSource.getConnection();
//            String query = "UPDATE PARISH_ALBUM  SET ALBUM_GUBUN=?, title=?, content=?, is_view=?  WHERE idx = ? ";
//            preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setString(1, gubun);
//            preparedStatement.setString(2, title);
//            preparedStatement.setString(3, content);
//            preparedStatement.setString(4, is_view);
//            preparedStatement.setInt(5, Integer.parseInt(idx));
//            int k = preparedStatement.executeUpdate();
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        	free();
    	}
        
//        for(int i = 0; i < list.size(); i++)
//        {
//            Map tmp = (Map)list.get(i);
//            strfilename = pnull(tmp.get("STORED_FILE_NAME"));
//            filesize = pnull(tmp.get("FILE_SIZE"));
//            orgfilename = pnull(tmp.get("ORIGINAL_FILE_NAME"));
//        }
//
//        try
//        {
//        	if(connection==null || connection.isClosed()) connection = dataSource.getConnection();
//            String query = "UPDATE PARISH_ALBUM  SET ALBUM_GUBUN=?, title=?, content=?, filename=?, is_view=?,  strfilename=?, filesize=?  WHERE idx = ? ";
//            preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setString(1, gubun);
//            preparedStatement.setString(2, title);
//            preparedStatement.setString(3, content);
//            preparedStatement.setString(4, orgfilename);
//            preparedStatement.setString(5, is_view);
//            preparedStatement.setString(6, strfilename);
//            preparedStatement.setString(7, filesize);
//            preparedStatement.setInt(8, Integer.parseInt(idx));
//            int l = preparedStatement.executeUpdate();
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//            bReturn = false;
//        } finally {
//        	
//        	if(preparedStatement != null) try { preparedStatement.close(); } catch ( Exception e ) { e.printStackTrace(); }
//            if(connection != null) try { connection.close();} catch ( Exception e ) { e.printStackTrace(); }
//        	free();
//    	}
        return bReturn;
    }

	@Override
    public boolean oldAlbDelete(Map _params) throws Exception
    {
        boolean bReturn;
        String idx;
        Connection connection;
        PreparedStatement preparedStatement;
        bReturn = true;
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        idx = pnull(_params.get("idx"));
        connection = null;
        preparedStatement = null;
        try
        {
        	if(connection==null || connection.isClosed()) connection = dataSource.getConnection();
            String query = "delete from PARISH_ALBUM where idx = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, Integer.parseInt(idx));
            int i = preparedStatement.executeUpdate();
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
            bReturn = false;
        } finally {
        	
        	if(preparedStatement != null) try { preparedStatement.close(); } catch ( Exception e ) { e.printStackTrace(); }
            if(connection != null) try { connection.close();} catch ( Exception e ) { e.printStackTrace(); }
        	free();
        }
        return bReturn;
    }

//	@Override
//    public List _1x00xList(Map _params)
//    {
//        List result;
//        ResultSet rs;
//
//        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
//        
//        String query = "";
//        result = new ArrayList();
//        rs = null;
//        try
//        {
//            query = "SELECT depart_idx, name, depart_code, left(depart_code,2) depart_code_1x FROM  DEPARTMENT WHERE left(depart_code,1)='1' AND right(depart_code,4)<>'0000' ";
//            _logger.info((new StringBuilder("_1x000List 조회쿼리 : ")).append(query).toString());
//            rs = super.executeQuery(query);
//            result = ResultSetHandler.conversion(rs);
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        } finally {
//        	freeResultSet(rs);
//        	free();
//    	}
//        _logger.info((new StringBuilder("_1x00xList 조회 결과 : ")).append(result.toString()).toString());
//        return result;
//    }

	/*
	 * 교구 앨범 조회
	 * (non-Javadoc)
	 * @see kr.caincheon.church.dao.AlbDao#albumList(java.util.Map, kr.caincheon.church.common.CommonDaoDTO)
	 */
	@Override
    public void albumList(Map _params, CommonDaoDTO dto) throws Exception
    {
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        int pageNo   = ipnull(_params, "pageNo", 1);
        int pageSize = ipnull(_params, "pageSize", 10);
        int startRnum = (pageNo - 1) * pageSize + 1;
        int endRnum   = pageNo * pageSize;
        
        String whereStr   = " AND A.C_IDX IN ('1','2') ";// 미사|행사 c_idx=1, 교육|사업 c_idx=2
        String org_idx    = pnull(_params.get("org_idx"));
        
        String c_idx      = pnull(_params.get("c_idx"));
        String s_search   = pnull(_params.get("schTextGubun"));
        String searchText = pnull(_params.get("schText"));
        
        // 분류
        if( c_idx.length() > 0 ) whereStr = " AND A.C_IDX IN ( '"+c_idx+"' ) ";
        // 분류 콤보박스
        if(org_idx.length() > 0) whereStr = whereStr + " AND A.ORG_IDX=" + org_idx;
        // 검색(검색 콤보, 검색어)
        if(searchText.length() > 0) {
            if(s_search.equals("all"))
                whereStr = whereStr + " AND ( A.TITLE LIKE '%"+searchText+"%' OR A.CONTENT LIKE '%"+searchText+"%') ";
            else if(s_search.equals("title"))
                whereStr = whereStr + " AND A.TITLE LIKE '%"+searchText+"%' ";
            else if(s_search.equals("contents"))
                whereStr = whereStr + " AND A.CONTENT LIKE '%"+searchText+"%' ";
            else if(s_search.equals("writer"))
                whereStr = whereStr + " AND A.WRITER LIKE '%"+searchText+"%' ";
        }

        String query = "", innerSQL = "";
        try
        {
        	//innerSQL = (new StringBuilder("SELECT * FROM  (SELECT ROW_NUMBER() OVER(ORDER BY REGDATE DESC) RNUM, idx, fnGetOrgName(depart_code) as depart_name,  album_gubun, case when album_gubun='1' then '\uBBF8\uC0AC'  when album_gubun='2' then '\uAD50\uC721'  when album_gubun='3' then '\uD589\uC0AC' END AS album_gubun_text,  title, user_id, writer, convert(char(10),regdate,120) as regdate, hit  FROM PARISH_ALBUM  WHERE ")).append(whereStr).append(" )A ").append(" WHERE RNUM BETWEEN ").append(startRnum).append(" AND ").append(endRnum).toString();
        	innerSQL = "SELECT ROW_NUMBER() OVER(ORDER BY REGDATE DESC, IDX DESC) RNUM"
            		+ ", A.IDX"
            		+ ", A.ORG_IDX, O.NAME AS ORG_NAME "
            		+ ", A.ALBUM_GUBUN"
            		+ ", A.C_IDX, A.IS_VIEW"
            		+ ", CASE WHEN A.C_IDX='1' THEN '미사|행사'  WHEN A.C_IDX='2' THEN '교육|사업' END AS ALBUM_GUBUN_TEXT"
            		+ ", A.TITLE, A.USER_ID, A.WRITER, CONVERT(CHAR(10), A.REGDATE, 120) AS REGDATE, A.HIT  "
            		+ ", A.FILEPATH, A.STRFILENAME, A.FILENAME "
            		+ " FROM PARISH_ALBUM  A"
            		+ " LEFT OUTER JOIN ORG_HIERARCHY O ON O.ORG_IDX=A.ORG_IDX "
            		+ " WHERE 1=1 "+whereStr
            		;
        	
        	
        	query = "SELECT * FROM  ( " + innerSQL + " ) A WHERE RNUM BETWEEN "+startRnum+" AND "+endRnum ;
            dto.daoList = super.executeQueryList(query);
            
            dto.daoTotalCount = super.executeCount(innerSQL, true);
            
            
        } catch(Exception e) { 
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        	free();
    	}
        
        D(_logger, Thread.currentThread().getStackTrace(), "Query Result DTO :"+dto );
        
    }

	/*
	 * 교구 앨범 카운트 조회
	 * (non-Javadoc)
	 * @see kr.caincheon.church.dao.AlbDao#albumListCount(java.util.Map)
	 */
//	@Override
//    public int albumListCount(Map _params)
//    {
//        
//        int result;
//
//        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
//        
//        String whereStr = " C_IDX IN ('31','32', '33') ";//" ALBUM_GUBUN IN ('1','2', '3') ";
//        String s_part = pnull(_params.get("s_part"));
//        String s_gubun = pnull(_params.get("s_gubun"));
//        String s_search = "";
//        String searchText = searchText = pnull(_params.get("searchText"));;
//        
//        
//        if(s_gubun != null && s_gubun.length() > 0 && (s_gubun.equals("1") || s_gubun.equals("2") || s_gubun.equals("3")))
//            whereStr = (new StringBuilder(" ALBUM_GUBUN = '")).append(s_gubun).append("' ").toString();
//        
//        if(s_part != null && s_part.length() > 0)
//            whereStr = (new StringBuilder(String.valueOf(whereStr))).append("  ").toString();
//        
//        if(searchText != null && searchText.length() > 0)
//        {
//            s_search = pnull(_params.get("s_search"));
//            if(s_search.equals("all"))
//                whereStr = (new StringBuilder(String.valueOf(whereStr))).append(" AND ( TITLE LIKE '%").append(searchText).append("%' OR CONTENT LIKE '%").append(searchText).append("%') ").toString();
//            else
//            if(s_search.equals("title"))
//                whereStr = (new StringBuilder(String.valueOf(whereStr))).append(" AND TITLE LIKE '%").append(searchText).append("%' ").toString();
//            else
//            if(s_search.equals("contents"))
//                whereStr = (new StringBuilder(String.valueOf(whereStr))).append(" AND CONTENT LIKE '%").append(searchText).append("%' ").toString();
//            else
//            if(s_search.equals("writer"))
//                whereStr = (new StringBuilder(String.valueOf(whereStr))).append(" AND WRITER LIKE '%").append(searchText).append("%' ").toString();
//        }
//        _logger.info((new StringBuilder("[ALBUM LIST QUERY] whereStr=")).append(whereStr).toString());
//        result = 0;
//        
//        String query = "";
//        try
//        {
//            query = (new StringBuilder("SELECT COUNT(1) FROM PARISH_ALBUM  WHERE ")).append(whereStr).toString();
//            _logger.info((new StringBuilder("[ALBUM LIST COUNT QUERY] =")).append(query).toString());
//            result = super.executeCount(query, false);
//            
//        } catch(Exception e) {
//            e.printStackTrace();
//        } finally {
//        	free();
//    	}
//        _logger.info((new StringBuilder("[ALBUM LIST COUNT QUERY RESULT] =")).append(result).toString());
//        return result;
//    }

	/*
	 * 앨범상세보기
	 * (non-Javadoc)
	 * @see kr.caincheon.church.dao.AlbDao#albumContents(java.util.Map)
	 */
	@Override
    public Map albumContents(Map _params) throws Exception
    {
        Map result = null;
        String idx = pnull(_params.get("idx"));
        String query = "";
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        try
        {
            query = " SELECT IDX, CONVERT(char(10),  ALBUM_DATE, 120) ALBUM_DATE, USER_ID, WRITER, TITLE, CONTENT, IS_VIEW"
            		+ ", FILEPATH, FILENAME, FILESIZE, STRFILENAME, RTRIM(ALBUM_GUBUN) AS ALBUM_GUBUN, C_IDX "
            		+ ", ORG_IDX "
            		+ " FROM PARISH_ALBUM "
            		+ " WHERE IDX="+idx;
            result = super.executeQueryMap(query);
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        	free();
    	}
        
        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+result );
        
        return result;
    }

	@Override
    public boolean albumInsert(Map _params, List list) throws Exception
    {
        boolean bReturn    = false;
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        String master_id   = pnull(_params.get("user_id"));
        String album_date  = pnull(_params.get("album_date"));
        String title       = pnull(_params.get("title"));
        String contents    = pnull(_params.get("contents"));
        String is_view     = pnull(_params.get("is_view"));
        String org_idx     = pnull(_params.get("s_org_idx"));
        String album_gubun = pnull(_params.get("album_gubun"));
        String c_idx       = pnull(_params.get("c_idx"));
        String filepath    = pnull(_params, "CONTEXT_URI_PATH");//pnull(_params, "CONTEXT_ROOT_PATH");//FileUtils.getAlbumfilepath();
        String filename    = pnull(_params.get("filename"));
        String strfilename = "";
        String orgfilename = "";
        String filesize    = "";
        
        
        Map memberInfo = getAdmin(_params);
        if(memberInfo == null) return false;
        
        // uploaded file 
        for(int i = 0; i < list.size(); i++) {
            Map tmp = (Map)list.get(i);
            strfilename = pnull(tmp.get("STORED_FILE_NAME"));
            filesize = pnull(tmp.get("FILE_SIZE"));
            orgfilename = pnull(tmp.get("ORIGINAL_FILE_NAME"));
        }

        String query = "";
        try
        {
//        	if(connection==null || connection.isClosed()) connection = dataSource.getConnection();
//            query = "INSERT INTO PARISH_ALBUM  (idx"
//            		+ ", album_date, user_id, writer, title, content"
//            		+ ", regdate, is_view, hit"
//            		+ ",  filepath, filename, filesize, strfilename"
//            		+ ", album_gubun, org_idx) "
//            		+ " VALUES ("
//            		+ "(select ISNULL(MAX(idx), 0) + 1 from PARISH_ALBUM)"
//            		+ ", ?, ?, ?, ?, ?"
//            		+ ", getdate(), ?, 0"
//            		+ ", ?, ?, ?, ?"
//            		+ ", ?, ?) ";
//            preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setString(1, album_date);
//            preparedStatement.setString(2, master_id);
//            preparedStatement.setString(3, pnull(memberInfo.get("NAME")));
//            preparedStatement.setString(4, title);
//            preparedStatement.setString(5, contents);
//            preparedStatement.setString(6, is_view);
//            preparedStatement.setString(7, filepath);
//            preparedStatement.setString(8, orgfilename);
//            preparedStatement.setString(9, filesize);
//            preparedStatement.setString(10, album_gubun);
//            preparedStatement.setString(11, strfilename);
//            preparedStatement.setString(12, org_idx);
//            j = preparedStatement.executeUpdate();
            

        	query = "INSERT INTO PARISH_ALBUM  (idx"
            		+ ", album_date, user_id, writer, title, content, is_view"
            		+ ", regdate, hit"
            		+ ",  filepath, filename, filesize, strfilename"
            		+ ", album_gubun, org_idx, c_idx) "
            		+ " VALUES ("
            		+ "(select ISNULL(MAX(idx), 0) + 1 from PARISH_ALBUM)"
            		+ ", ?, ?, ?, ?, ?, ?"
            		+ ", getdate(), 0"
            		+ ", ?, ?, ?, ?"
            		+ ", ?, ?, ?) ";
        	LinkedHashMap<String, Object> params = getLinkedHashMap( album_date
											        			, pnull(master_id, getSession(_params, "ADM_MEM_ID"))
											        			, pnull(memberInfo, "NAME", getSession(_params, "ADM_MEM_NM"))
											        			, title
											        			, contents
											        			, is_view
											        			, filepath
											        			, orgfilename
											        			, filesize
											        			, strfilename
											        			, album_gubun
											        			, org_idx
											        			, c_idx
											        			);
            bReturn = executeUpdatePreparedStatement(query, params) == 1 ? true : false;
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        	free();
    	}

        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+bReturn );
        
        return bReturn;
    }

	@Override
    public boolean albumModify(Map _params, List list) throws Exception
    {
        boolean bReturn = false;
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        String master_id = pnull(_params.get("user_id"));
        Map memberInfo = getAdmin(_params);
        if(memberInfo == null) return false;
        
        String idx         = pnull(_params.get("idx"));
        String album_gubun = pnull(_params.get("album_gubun"));
        String c_idx       = pnull(_params.get("c_idx"));
        String title       = pnull(_params.get("title"));
        String album_date  = pnull(_params.get("album_date"));
        String ctxUri      = pnull(_params, "CONTEXT_URI_PATH");
        String contents    = pnull(_params.get("contents"));
        String is_view     = pnull(_params.get("is_view"));
        String org_idx     = pnull(_params.get("s_org_idx"));
        String strfilename = "";
        String orgfilename = "";
        String filesize    = "";
        String query = "";
        for(int i = 0; i < list.size(); i++)
        {
            Map tmp     = (Map)list.get(i);
            strfilename = pnull(tmp.get("STORED_FILE_NAME"));
            filesize    = pnull(tmp.get("FILE_SIZE"));
            orgfilename = pnull(tmp.get("ORIGINAL_FILE_NAME"));
        }
        
        // 기존 파일 삭제
        if(pnull(_params.get("delFile")).equals("Y")) {
        	// select a file infor
        	query = "SELECT FILEPATH, STRFILENAME FROM PARISH_ALBUM WHERE IDX=" + idx;
        	try {
				Map m = executeQueryMap(query);
				String savedFileName = pnull(m, "STRFILENAME");
	        	// file delete
	        	String realPath = pnull(_params, "CONTEXT_ROOT_PATH");
	        	if(FileUtils.getInstance().deleteFile(realPath + savedFileName,  realPath + "thumbnail/" + savedFileName)) {
	        		I(_logger, Thread.currentThread().getStackTrace(), "File Delete Result: true");
	        	} else {
	        		I(_logger, Thread.currentThread().getStackTrace(), "File Delete Result: false" + realPath + savedFileName );
	        	}
	        	
			} catch (SQLException e) {
				_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
			}
        }
        
        try
        {
        	LinkedHashMap<String, Object> params = getLinkedHashMap( album_date
											        			, master_id
											        			, pnull(memberInfo, "NAME", null)
											        			, title
											        			, contents
											        			, is_view
											        			, ctxUri.length()==0 ? null:ctxUri
											        			, orgfilename.length()==0 ? null:orgfilename
											        			, filesize.length()==0 ? "0":filesize
											        			, album_gubun
											        			, strfilename.length()==0 ? null:strfilename
											        			, org_idx
											        			, c_idx
											        			, Integer.parseInt(idx));
            query = "UPDATE PARISH_ALBUM  SET "
            		+ " album_date=?, user_id=?, writer=ISNULL(?, WRITER), title=?, content=?, is_view=?"
            		+ ", filepath=ISNULL(?, filepath), filename=ISNULL(?, filename), filesize=ISNULL(?, filesize)"
            		+ ", album_gubun=?, strfilename=ISNULL(?, strfilename)"
            		+ ", org_idx=? , c_idx=? "
            		+ " WHERE idx = ? ";
            
            bReturn = executeUpdatePreparedStatement(query, params) == 1 ? true : false;
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        	free();
    	}
        
        return bReturn;
    }

	@Override
    public boolean albumDelete(Map _params) throws Exception
    {
        boolean bReturn = false;
        String idx = pnull(_params.get("idx"));
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );

        try
        {
        	// file delete
        	Map row = executeQueryMap("select * from PARISH_ALBUM where idx = " + idx);
        	if(row.size() > 0) {
        		String filepath = pnull(_params.get("CONTEXT_ROOT_PATH"));
        		String filename = pnull(row, "FILENAME");
        		FileUtils.getInstance().deleteFile(filepath+filename, filepath+"thumbnail/"+filename);
        	}
        	
        	// 
            String query = "delete from PARISH_ALBUM where idx = " + idx;
            bReturn = executeUpdate(query) == 1 ? true : false;
            if(bReturn) {
            	
            }
        }
        catch(Exception e)
        {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR :", e);
        } finally {
        	free();
    	}
        return bReturn;
    }

	@Override
    public Map getAdmin(Map _params) throws Exception
    {
        Map result;
        ResultSet rs;
        result = new HashMap();
        rs = null;
        try
        {
            String query = "SELECT ADM_NAME AS NAME FROM ADMMEMBER WHERE ADM_ID='"+_params.get("user_id")+"' ";
            rs = super.executeQuery(query);
            if(rs.next())
                result.put("NAME", rs.getString(1));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        } finally {
        	freeResultSet(null);
        	free();
    	}
        _logger.debug((new StringBuilder("result=")).append(result.toString()).toString());
        return result;
    }

}
