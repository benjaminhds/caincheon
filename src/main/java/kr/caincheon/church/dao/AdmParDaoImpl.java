// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AdmParDaoImpl.java

package kr.caincheon.church.dao;

import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import kr.caincheon.church.common.base.CommonDao;
import kr.caincheon.church.common.utils.ImageUtils;
import kr.caincheon.church.common.utils.UtilInt;

// Referenced classes of package kr.caincheon.church.dao:
//            AdmParDao

@Repository("admParDao")
public class AdmParDaoImpl extends CommonDao
    implements AdmParDao
{
	private final Logger _logger = Logger.getLogger(getClass());
	
    public List parList(Map _params)
    {
        String b_idx = pnull(_params.get("b_idx"), "ALL");
        String query = "", strWhere="";
        List result = null;
        
        D(_logger, Thread.currentThread().getStackTrace(), "_params ? "+_params );
        
        int pageNo = UtilInt.pint(_params.get("pageNo"), 1);
        int pageSize = UtilInt.pint(_params.get("pageSize"), 10);
        int startRnum = (pageNo - 1) * pageSize + 1;
        int endRnum = pageNo * pageSize;
        
        String schTextGubun = pnull(_params.get("searchGubun2"));
        String schText = pnull(_params.get("searchText"));
        
        strWhere = "";
        
        try {
        	query = getQuery(_params);
            result = super.executeQueryList(query);
            
        } catch(Exception e) {
            _logger.error((new StringBuilder("ERROR SQL:")).append(query).toString(), e);
        } finally {
        	free();
    	}
        
        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+result );
        
        return result;
    }
    
    private String getQuery(Map _params) {
    	
    	D(_logger, Thread.currentThread().getStackTrace(), "_params ? "+_params );
    	
    	String query = "", strWhere = "" ;
    	String b_idx = pnull(_params.get("b_idx"), "ALL");
    	String schTextGubun = pnull(_params.get("searchGubun2"));
        String schText = pnull(_params.get("searchText"));
        
        int pageNo = UtilInt.pint(_params.get("pageNo"), 1);
        int pageSize = UtilInt.pint(_params.get("pageSize"), 10);
        int startRnum = (pageNo - 1) * pageSize + 1;
        int endRnum = pageNo * pageSize;
        
        if(schText.length() > 0) {
            if(schTextGubun.equals("all"))
                strWhere = " AND (TITLE LIKE '%"+schText+"%' OR CONTENT LIKE '%"+schText+"%')";
            else
                strWhere = " AND "+schTextGubun+" LIKE '%"+schText+"%' ";
        }
        
        if(b_idx.equals("ALL"))
            query = "SELECT /*ALL*/ A.*, B.NAME AS CATEGORY_NAME "
            		+ "FROM ( "
            		+ "  SELECT ROW_NUMBER() OVER(ORDER BY NOTICE_TYPE, REF DESC, STEP) RNUM, B_IDX, NOTICE_TYPE, BL_IDX, TITLE, USER_ID, WRITER, convert(char(10),  REGDATE, 120) REGDATE, HIT"
            		+ "         , REF, STEP, C_IDX, CHURCH_IDX, DEPART_IDX, IS_SECRET"
            			+ "     , (SELECT COUNT(*) FROM NBOARD_UPLOAD A3 WHERE A1.BL_IDX=A3.BL_IDX AND IS_USE='Y') FILE_CNT"
            			//+ "     , (SELECT COUNT(*) FROM NBOARD_MEMO A2 WHERE A1.BL_IDX=A2.BL_IDX) MEMO_CNT     "
            		+ "  FROM ("
            		+ "      SELECT TOP 200 '1' AS NOTICE_TYPE, A.* FROM  (SELECT top 200 * FROM NBOARD WHERE B_IDX IN (61,62) "+strWhere+" AND  IS_NOTICE='Y' ORDER BY BL_IDX DESC ) A "
            		+ "      UNION ALL  "
            		+ "      SELECT '2' AS NOTICE_TYPE, * FROM NBOARD WHERE B_IDX IN (61,62) "+strWhere+" AND BL_IDX NOT IN (SELECT TOP 200 BL_IDX FROM NBOARD WHERE B_IDX IN (61,62) "+strWhere+"  AND  IS_NOTICE='Y' ORDER BY BL_IDX DESC) ) A1 "
      				+ ") A "
      				+ "LEFT JOIN NBOARD_CATEGORY B ON A.C_IDX=B.C_IDX WHERE RNUM BETWEEN "+startRnum+" AND "+endRnum
      				;
        else
            query = "SELECT /*b_idx:"+b_idx+"*/ A.*, B.NAME AS CATEGORY_NAME FROM  "
            		+ "( "
            		+ " SELECT ROW_NUMBER() OVER(ORDER BY NOTICE_TYPE, REF DESC, STEP) RNUM, B_IDX"
            		+ " , NOTICE_TYPE, BL_IDX, TITLE, USER_ID, WRITER, convert(char(10),  REGDATE, 120) REGDATE, HIT, REF, STEP, C_IDX, CHURCH_IDX, DEPART_IDX, IS_SECRET"
            		+ " , (SELECT COUNT(*) FROM NBOARD_UPLOAD A3 WHERE A1.BL_IDX=A3.BL_IDX AND IS_USE='Y') FILE_CNT"
            		//+ " , (SELECT COUNT(*) FROM NBOARD_MEMO A2   WHERE A1.BL_IDX=A2.BL_IDX) MEMO_CNT  "
            		+ "  FROM ( "
            		+ "     SELECT TOP 200 '1' AS NOTICE_TYPE, A.*  FROM  (SELECT top 200 *  FROM NBOARD  WHERE B_IDX="+b_idx+" "+strWhere+" AND IS_NOTICE='Y' ORDER BY BL_IDX DESC) A "
            		+ "     UNION ALL  "
            		+ "     SELECT '2' AS NOTICE_TYPE, * FROM NBOARD WHERE B_IDX="+b_idx+" "+strWhere+" AND BL_IDX NOT IN (SELECT TOP 200 BL_IDX FROM NBOARD WHERE B_IDX="+b_idx+" "+strWhere+" AND  IS_NOTICE='Y' ORDER BY BL_IDX DESC) "
            		+ "     ) A1  "
            		+ ") A "
            		+ "LEFT JOIN NBOARD_CATEGORY B ON A.C_IDX=B.C_IDX  "
            		+ "WHERE RNUM BETWEEN "+startRnum+" AND "+endRnum
            		;
            
        return query;
    }
    
    public int parListCount(Map _params)
    {
        int result;
        
        D(_logger, Thread.currentThread().getStackTrace(), "_params ? "+_params );
        
        String query = "";
        
        result = 0;
        try {
        	query = "SELECT COUNT(1) FROM   ( " + getQuery(_params) + " ) XXX ";
            result = super.executeCount(query, false);
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
        	free();
    	}
        
        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+result );
        
        return result;
    }

    public Map parContents(Map _params)
    {
        Map result = null;
        
        String b_idx = pnull(_params.get("b_idx"));
        String bl_idx = pnull(_params.get("bl_idx"));
        String query = "";
        
        try
        {
            query = "SELECT BL_IDX, TITLE, CONTENT, USER_ID, WRITER, EMAIL, REGDATE, IS_VIEW, IS_SECRET, HIT"
            		+ ", IS_NOTICE, B_IDX, EVENT_DATE, DISPLAY_YN "
            		+ ",  (SELECT A.STRFILENAME "
            		+ "    FROM   ( SELECT ROW_NUMBER() OVER(ORDER BY bu_idx DESC) AS RNUM, FILEPATH+'thumbnail/'+STRFILENAME AS STRFILENAME "
            		+ "             FROM NBOARD_UPLOAD "
            		+ "             WHERE BL_IDX='"+bl_idx+"' ) A "
            		+ "    WHERE A.RNUM=1) AS FILENAME "
            		+ " FROM NBOARD A "
            		+ " WHERE B_IDX='"+b_idx+"' AND BL_IDX='"+bl_idx+"'"
            		;
            
            result = super.executeQueryMap(query);
            
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        	free();
    	}
        
        D(_logger, Thread.currentThread().getStackTrace(), "result ? "+result );
        
        return result;
    }

    public Map getMember(Map _params)
    {
        Map result = new HashMap();
        String query = "";
        try
        {
            query = "SELECT ADM_NAME AS NAME FROM ADMMEMBER WHERE ADM_ID='"+_params.get("user_id")+"' ";
            result = super.executeQueryMap(query);
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        	free();
    	}
        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+result );
        
        return result;
    }

    public boolean admParInsert(Map _params, List upfileList)
    {
        int rn=0;
        String master_id="";
        Map memberInfo=null;
        PreparedStatement preparedStatement=null;
        String query="";
        Map row=null;
        boolean bReturn = true;
        
        master_id = pnull(_params.get("user_id"));
        memberInfo = getMember(_params);
        if(memberInfo == null)
            return false;
        
        query = "  SELECT ISNULL(MAX(BL_IDX), 0) + 1 AS BL_IDX FROM NBOARD ";
        try {
            row = super.executeQueryMap(query);
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        }
        
        try
        {
            query = "INSERT INTO NBOARD (bl_idx,title,content,user_id,writer, pwd,regdate,upddate,email,is_view, is_secret"
            		+ ",depart_idx,church_idx"
            		+ ",REF,STEP,  LEVEL,hit,b_idx,is_notice,c_idx"
            		+ ",display_yn,event_date "
            		+ ")  VALUES ("
            		+ "?, ?, ?, ?, ?,  '', GETDATE(), NULL, '', 'Y',  'N', '', '', ?, 0,  0, 0, ?, ?, ?"
            		+ ", ?,?)";
            preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1, pnull(row.get("BL_IDX")));
            preparedStatement.setString(2, pnull(_params.get("title")));
            preparedStatement.setString(3, pnull(_params.get("contents")));
            preparedStatement.setString(4, master_id);
            preparedStatement.setString(5, pnull(memberInfo.get("NAME")));
            preparedStatement.setString(6, pnull(row.get("BL_IDX")));
            preparedStatement.setString(7, pnull(_params.get("b_idx")));
            preparedStatement.setString(8, pnull(_params.get("is_notice"), "N"));
            preparedStatement.setString(9, pnull(_params.get("C_IDX"), "0"));
            preparedStatement.setString(10, pnull(_params.get("display_yn")));
            preparedStatement.setString(11, pnull(_params.get("event_date")));
            rn = preparedStatement.executeUpdate();
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
            bReturn = false;
        } finally {
        	if(preparedStatement != null) try { preparedStatement.close(); } catch ( Exception e ) { e.printStackTrace(); }
    	}
        
        try
        {
        	String fileUploadedURI  = pnull(_params, "CONTEXT_URI_PATH");//"d:\\newcaincheon\\webapps\\upload\\"
        	String fileUploadedPath = pnull(_params, "CONTEXT_ROOT_PATH");//"d:\\newcaincheon\\webapps\\upload\\"
        	
            query = "INSERT INTO NBOARD_UPLOAD (bu_idx,filepath,filesize,is_use,userid,filename,bl_idx,filetype,strfilename)"
            		+ " values ("
            		+ "(select ISNULL(MAX(BU_IDX), 0) + 1 from nboard_upload),  ?,?,?,?,?,?,NULL,?) ";
            preparedStatement = getConnection().prepareStatement(query);
            for(int i = 0; i < upfileList.size(); i++)
            {
                Map tmp = (Map)upfileList.get(i);
                preparedStatement.setString(1, fileUploadedURI);
                preparedStatement.setString(2, pnull(tmp.get("FILE_SIZE")));
                preparedStatement.setString(3, pnull(tmp.get("IS_USE"), "Y"));
                preparedStatement.setString(4, master_id);
                preparedStatement.setString(5, pnull(tmp.get("ORIGINAL_FILE_NAME")));
                preparedStatement.setString(6, pnull(row.get("BL_IDX")));
                preparedStatement.setString(7, pnull(tmp.get("STORED_FILE_NAME")));
                rn += preparedStatement.executeUpdate();
            }
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
            bReturn = false;
        } finally {
        	if(preparedStatement != null) try { preparedStatement.close(); } catch ( Exception e ) { e.printStackTrace(); }
        	free();
    	}
        return rn >= 1;
    }

    public boolean admParModify(Map _params, List list)
    {
        int rn=0;
        boolean bReturn = true;
        String master_id = pnull(_params, "user_id");
        String query = "";
        
        Map memberInfo = getMember(_params);
        PreparedStatement preparedStatement=null;

        D(_logger, Thread.currentThread().getStackTrace(), " DAO Called. _params: " + _params);
        
        if(memberInfo == null)
            return false;
        try
        {
            if(pnull(_params.get("delFile")).equals("Y"))
            {
                D(_logger, Thread.currentThread().getStackTrace(), "EXE Query(1) old file delete option is on.");
                // file del
                Map delMap = executeQueryMap("select filepath, strfilename from NBOARD_UPLOAD where bl_idx = " + pnull(_params, "bl_idx") );
                String dFilepath = pnull(delMap, "STRFILENAME");
                if(dFilepath.length()>0) {
                	//String t = pnull(_params, "CONTEXT_URI_PATH");
                	boolean rtBool = ImageUtils.deleteFileWithThumbnail(pnull(_params, "CONTEXT_ROOT_PATH"), dFilepath);
                	if(rtBool) {
                		D(_logger, Thread.currentThread().getStackTrace(), "EXE Query(1) File is deleted.[" + dFilepath +"]");
                	} else {
                		D(_logger, Thread.currentThread().getStackTrace(), "EXE Query(1) File is not exits.[" + dFilepath +"]");
                	}
                }
                // db row del
                query = "delete from NBOARD_UPLOAD where bl_idx = ?";
                D(_logger, Thread.currentThread().getStackTrace(), "EXE Query(1) : " + query);
                preparedStatement = getPreparedStatement(query);
                preparedStatement.setString(1, pnull(_params.get("bl_idx")));
                rn = preparedStatement.executeUpdate();
            }
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
            bReturn = false;
        } finally {
        	if(preparedStatement != null) try { preparedStatement.close(); } catch ( Exception e ) { e.printStackTrace(); }
    	}
        
        
        try
        {
            query = "UPDATE NBOARD SET  title= ?, content = ?,  user_id = ?,  writer = ?,  upddate = getdate(),  is_notice = ?,  display_yn = ?,  event_date = ?,  b_idx = ?  WHERE bl_idx = ? ";
            
            D(_logger, Thread.currentThread().getStackTrace(), "EXE Query(2) : " + query);
            
            preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1, pnull(_params.get("title")));
            preparedStatement.setString(2, pnull(_params.get("contents")));
            preparedStatement.setString(3, master_id);
            preparedStatement.setString(4, pnull(memberInfo.get("NAME")));
            preparedStatement.setString(5, pnull(_params.get("is_notice"), "N"));
            preparedStatement.setString(6, pnull(_params.get("display_yn")));
            preparedStatement.setString(7, pnull(_params.get("event_date")));
            preparedStatement.setString(8, pnull(_params.get("b_idx")));
            preparedStatement.setString(9, pnull(_params.get("bl_idx")));
            rn = preparedStatement.executeUpdate();
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
            bReturn = false;
        } finally {
        	if(preparedStatement != null) try { preparedStatement.close(); } catch ( Exception e ) { e.printStackTrace(); }
    	}
        
        try
        {
        	String fileUploadedURI  = pnull(_params, "CONTEXT_URI_PATH");//"/upload/church_notice/"
        	String fileUploadedPath = pnull(_params, "CONTEXT_ROOT_PATH");//"d:/newcaincheon/webapps/upload/church_notice/"
        	
            query = "INSERT INTO NBOARD_UPLOAD  "
            		+ "(bu_idx,filepath,filesize,is_use,userid,filename,bl_idx,filetype,strfilename)  "
            		+ " values "
            		+ "((select ISNULL(MAX(BU_IDX), 0) + 1 from nboard_upload),  ?,?,?,?,?,?,NULL,?) ";
            
            D(_logger, Thread.currentThread().getStackTrace(), "EXE Query(3) : " + query);
            preparedStatement = getConnection().prepareStatement(query);
            for(int i = 0; i < list.size(); i++)
            {
                Map tmp = (Map)list.get(i);
                preparedStatement.setString(1, fileUploadedURI);
                preparedStatement.setString(2, pnull(tmp.get("FILE_SIZE")));
                preparedStatement.setString(3, pnull(tmp.get("IS_USE"), "Y"));
                preparedStatement.setString(4, master_id);
                preparedStatement.setString(5, pnull(tmp.get("ORIGINAL_FILE_NAME")));
                preparedStatement.setString(6, pnull(_params.get("bl_idx")));
                preparedStatement.setString(7, pnull(tmp.get("STORED_FILE_NAME")));
                rn += preparedStatement.executeUpdate();
            }
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
            bReturn = false;
        } finally {
        	
        	if(preparedStatement != null) try { preparedStatement.close(); } catch ( Exception e ) { e.printStackTrace(); }
        	free();
    	}
        
        return rn >= 1 && bReturn;
    }

    public boolean admParDelete(Map _params)
    {
        int rn=0;
        boolean bReturn=true;
        String bl_idx = pnull(_params.get("bl_idx"), "0");
        String query = "";
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        try {
            query = "delete from NBOARD where bl_idx="+bl_idx;
            rn = executeUpdate(query);
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        	bReturn=false;
        } finally {
    	}
        
        try {
            query = "delete from NBOARD_UPLOAD where bl_idx="+bl_idx;
            rn += executeUpdate(query);
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        	bReturn=false;
        } finally {
        	free();
    	}
        
        return rn >= 1 && bReturn ;
    }

    
}
