// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AdmBonNoticeDaoImpl.java

package kr.caincheon.church.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import kr.caincheon.church.common.base.CommonDao;
import kr.caincheon.church.common.utils.UtilInt;

// Referenced classes of package kr.caincheon.church.dao:
//            AdmBonNoticeDao
@Repository("admBonNoticeDao")
public class AdmBonNoticeDaoImpl extends CommonDao
    implements AdmBonNoticeDao
{
	private final Logger _logger = Logger.getLogger(getClass());

	@Override
    public List admBonNoticeList(Map _params)
    {
        String b_idx = pnull(_params.get("searchGubun1"), "ALL").trim();
        String query="";
        String strWhere="";
        List result = null;
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        int pageNo    = UtilInt.pint(_params.get("pageNo"), 1);
        int pageSize  = UtilInt.pint(_params.get("pageSize"), 10);
        int startRnum = (pageNo - 1) * pageSize + 1;
        int endRnum   = pageNo * pageSize;
        
        String schTextGubun = pnull(_params.get("searchGubun2"));
        String schText      = pnull(_params.get("searchText"));
        String church_idx   = pnull(_params.get("church_idx"));
        
        String org_idx  = pnull(_params.get("org_idx"));
        
        if(schText.length() > 0)
            if(schTextGubun.equals("all"))
                strWhere = " AND (TITLE LIKE '%"+schText+"%' OR CONTENT LIKE '%"+schText+"%')";
            else
                strWhere = " AND "+schTextGubun+" LIKE '%"+schText+"%' ";
        
        if(!"".equals(org_idx))
            strWhere = strWhere+" AND org_idx=" + org_idx + " ";
        
        if("1".equals(pnull(_params.get("notice_type"))))
            strWhere = strWhere+" AND IS_NOTICE='Y' ";
        
        try {
        	String top = " TOP 20 ";
        	String tmp = " B_IDX="+b_idx;
        	if("ALL".equals(b_idx) || b_idx.length()==0)
        		tmp = " B_IDX IN (11,17)"; // 11공지, 17알림

            query = "SELECT  CASE WHEN A.B_IDX='11' THEN '공지' ELSE '소식' END AS CATEGORY_NAME, C.NAME AS CHURCH_NAME, D.NAME AS DEPART_NAME "
            		+ ", A.* FROM ("
            		+ "    SELECT ROW_NUMBER() OVER(ORDER BY NOTICE_TYPE, REF DESC, STEP) RNUM, B_IDX, NOTICE_TYPE"
            		+ "         , CASE WHEN NOTICE_TYPE='1' THEN '공지' ELSE '소식' END AS NOTICE_NAME"
            		+ "         , CHURCH_IDX, DEPART_IDX "
            		+ "         , BL_IDX, TITLE, USER_ID, WRITER, convert(char(10),  REGDATE, 120) REGDATE, HIT, REF, STEP, C_IDX, IS_SECRET"
            		+ "         , (SELECT FILENAME FROM NBOARD_UPLOAD A3 WHERE A3.BU_IDX IN (SELECT MAX(BU_IDX) FROM NBOARD_UPLOAD A4 WHERE A1.BL_IDX=A4.BL_IDX AND A4.FILETYPE IS NULL) ) FILENAME"
            		+ "         , (SELECT FILEPATH FROM NBOARD_UPLOAD A3 WHERE A3.BU_IDX IN (SELECT MAX(BU_IDX) FROM NBOARD_UPLOAD A4 WHERE A1.BL_IDX=A4.BL_IDX AND A4.FILETYPE IS NULL) ) FILEPATH"
            		+ "         , (SELECT COUNT(*) FROM NBOARD_UPLOAD A3 WHERE A1.BL_IDX=A3.BL_IDX AND IS_USE='Y') FILE_CNT"
            		//+ "         , (SELECT COUNT(*) FROM NBOARD_MEMO A2 WHERE A1.BL_IDX=A2.BL_IDX) MEMO_CNT     "
            		+ "    FROM ( "
            		+ "        SELECT "+top+" '1' AS NOTICE_TYPE, A.* FROM (SELECT "+top+" * FROM NBOARD WHERE "+tmp+" "+strWhere+" AND  IS_NOTICE='Y' ORDER BY BL_IDX DESC) A "
            		+ "        UNION ALL "
            		+ "        SELECT '2' AS NOTICE_TYPE, * FROM NBOARD WHERE "+tmp+" "+strWhere+" AND BL_IDX NOT IN (SELECT "+top+" BL_IDX FROM NBOARD WHERE "+tmp+" "+strWhere+" AND  IS_NOTICE='Y' ORDER BY BL_IDX DESC) "
            		+ "    ) A1  "
            		+ " ) A "
            		+ " LEFT JOIN NBOARD_CATEGORY B ON A.C_IDX = B.C_IDX "
            		+ " LEFT JOIN CHURCH          C ON A.CHURCH_IDX = C.CHURCH_IDX "
            		+ " LEFT JOIN DEPARTMENT      D ON A.DEPART_IDX = D.DEPART_IDX "
            		+ " WHERE RNUM BETWEEN "+startRnum+" AND "+endRnum+" " + (church_idx.length() == 0 ? "" : " AND A.CHURCH_IDX="+church_idx)
            		+ " ORDER BY A.NOTICE_TYPE, REF DESC, STEP";
        	
        	
//            if(b_idx.equals("ALL"))
//                query = "SELECT  A.*, CASE WHEN A.B_IDX='11' THEN '공지' ELSE '소식' END AS CATEGORY_NAME, C.NAME AS CHURCH_NAME, D.NAME AS DEPART_NAME "
//                		+ "FROM ("
//                		+ "    SELECT ROW_NUMBER() OVER(ORDER BY NOTICE_TYPE, REF DESC, STEP) RNUM, B_IDX, NOTICE_TYPE, CASE WHEN NOTICE_TYPE='1' THEN '공지' ELSE '소식' END AS NOTICE_NAME"
//                		+ "         , BL_IDX, TITLE, USER_ID, WRITER, convert(char(10),  REGDATE, 120) REGDATE, HIT, REF, STEP, C_IDX, CHURCH_IDX, DEPART_CODE, IS_SECRET"
//                		+ "         , (SELECT FILENAME FROM NBOARD_UPLOAD A3 WHERE A3.BU_IDX IN (SELECT MAX(BU_IDX) FROM NBOARD_UPLOAD A4 WHERE A1.BL_IDX=A4.BL_IDX AND A4.FILETYPE is null) ) FILENAME"
//                		+ "         , (SELECT FILEPATH FROM NBOARD_UPLOAD A3 WHERE A3.BU_IDX IN (SELECT MAX(BU_IDX) FROM NBOARD_UPLOAD A4 WHERE A1.BL_IDX=A4.BL_IDX AND A4.FILETYPE is null) ) FILEPATH"
//                		+ "         , (SELECT COUNT(*) FROM NBOARD_UPLOAD A3 WHERE A1.BL_IDX=A3.BL_IDX AND IS_USE='Y') FILE_CNT"
//                		+ "         , (SELECT COUNT(*) FROM NBOARD_MEMO A2 WHERE A1.BL_IDX=A2.BL_IDX) MEMO_CNT     "
//                		+ "    FROM ( "
//                		+ "        SELECT TOP 2 '1' AS NOTICE_TYPE, A.* FROM (SELECT top 2 * FROM NBOARD WHERE B_IDX IN (11,17) "+strWhere+" AND  IS_NOTICE='Y' ORDER BY BL_IDX DESC) A "
//                		+ "        UNION ALL "
//                		+ "        SELECT '2' AS NOTICE_TYPE, * FROM NBOARD WHERE B_IDX IN (11,17) "+strWhere+" AND BL_IDX NOT IN (SELECT TOP 2 BL_IDX FROM NBOARD WHERE B_IDX IN (11,17) "+strWhere+" AND  IS_NOTICE='Y' ORDER BY BL_IDX DESC) "
//                		+ "    ) A1  "
//                		+ " ) A "
//                		+ " LEFT JOIN NBOARD_CATEGORY B ON A.C_IDX=B.C_IDX "
//                		+ " LEFT JOIN CHURCH C ON A.CHURCH_IDX=C.CHURCH_IDX "
//                		+ " LEFT JOIN DEPARTMENT_NEW D ON A.DEPART_CODE=D.DEPART_CODE "
//                		+ " WHERE RNUM BETWEEN "+startRnum+" AND "+endRnum+" "
//                		+ " ORDER BY A.NOTICE_TYPE, REF DESC, STEP";
//            else
//                query = "SELECT  A.*, CASE WHEN A.B_IDX='11' THEN '공지' ELSE '소식' END AS CATEGORY_NAME, C.NAME AS CHURCH_NAME, D.NAME AS DEPART_NAME "
//                		+ " FROM ("
//                		+ "    SELECT ROW_NUMBER() OVER(ORDER BY NOTICE_TYPE, REF DESC, STEP) RNUM, B_IDX, NOTICE_TYPE, CASE WHEN NOTICE_TYPE='1' THEN '공지' ELSE '소식' END AS NOTICE_NAME"
//                		+ "         , BL_IDX, TITLE, USER_ID, WRITER, convert(char(10),  REGDATE, 120) REGDATE, HIT, REF, STEP, C_IDX, CHURCH_IDX, DEPART_CODE, IS_SECRET"
//                		+ "   , (SELECT FILENAME FROM NBOARD_UPLOAD A3 WHERE A3.BU_IDX IN (SELECT MAX(BU_IDX) FROM NBOARD_UPLOAD A4 WHERE A1.BL_IDX=A4.BL_IDX AND A4.FILETYPE is null) ) FILENAME"
//                		+ "   , (SELECT FILEPATH FROM NBOARD_UPLOAD A3 WHERE A3.BU_IDX IN (SELECT MAX(BU_IDX) FROM NBOARD_UPLOAD A4 WHERE A1.BL_IDX=A4.BL_IDX AND A4.FILETYPE is null) ) FILEPATH"
//                		+ "   , (SELECT COUNT(*) FROM NBOARD_UPLOAD A3 WHERE A1.BL_IDX=A3.BL_IDX AND IS_USE='Y') FILE_CNT"
//                		+ "   , (SELECT COUNT(*) FROM NBOARD_MEMO A2 WHERE A1.BL_IDX=A2.BL_IDX) MEMO_CNT "
//                		+ " FROM ("
//                		+ "     SELECT TOP 2 '1' AS NOTICE_TYPE, A.* "
//                		+ "       FROM  ( SELECT top 2 *  FROM NBOARD WHERE B_IDX="+b_idx+" "+strWhere+" AND  IS_NOTICE='Y' ORDER BY BL_IDX DESC) A "
//                		+ "     UNION ALL "
//                		+ "     SELECT '2' AS NOTICE_TYPE, * FROM NBOARD WHERE B_IDX="+b_idx+" "+strWhere+" AND BL_IDX NOT IN (SELECT TOP 2 BL_IDX FROM NBOARD WHERE B_IDX="+b_idx+" "+strWhere+" AND  IS_NOTICE='Y' ORDER BY BL_IDX DESC) ) A1 "
//                		+ " ) A "
//                		+ " LEFT JOIN NBOARD_CATEGORY B ON A.C_IDX=B.C_IDX "
//                		+ " LEFT JOIN CHURCH C ON A.CHURCH_IDX=C.CHURCH_IDX "
//                		+ " LEFT JOIN DEPARTMENT_NEW D ON A.DEPART_CODE=D.DEPART_CODE "
//                		+ " WHERE RNUM BETWEEN "+startRnum).append(" AND ").append(endRnum).append(" "
//                		+ " ORDER BY A.NOTICE_TYPE, REF DESC, STEP").toString();
            
            result = super.executeQueryList(query);
            
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query+"]", e );
        } finally {
        	free();
        }
        
        return result;
    }

    @Override
    public int admBonNoticeListCount(Map _params)
    {
        int result=0;
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        String b_idx = pnull(_params.get("searchGubun1"), "ALL").trim();
        String query = "";
        String schTextGubun = pnull(_params.get("searchGubun2"));
        String schText      = pnull(_params.get("searchText"));
        String church_idx   = pnull(_params.get("church_idx"));
        String depart_idx   = pnull(_params.get("depart_idx"));
        String notice_type  = pnull(_params.get("notice_type"));
        String strWhere = "";
        
        if(schText.length() > 0) {
            if(schTextGubun.equals("all"))
                strWhere = " AND (TITLE LIKE '%"+schText+"%' OR CONTENT LIKE '%"+schText+"%')";
            else
                strWhere = " AND "+schTextGubun+" LIKE '%"+schText+"%' ";
        }
        if( !"".equals(depart_idx) ) strWhere += " AND DEPART_IDX = '"+depart_idx+"' ";
        if( "1".equals(notice_type)) strWhere += " AND IS_NOTICE = 'Y'";
        
        if(b_idx.length()==0) b_idx="ALL";
        
        try {
        	String top = " TOP 100 ";
        	String tmp = " B_IDX="+b_idx ;
        	String tmp2 = church_idx.length() == 0 ? "" : " where church_idx="+church_idx+" ";
        	if("ALL".equals(b_idx) || b_idx.length()==0)
        		tmp = " B_IDX IN (11,17)"; // 11공지, 17알림
        	
        	
            if(b_idx.equals("ALL"))
                query = "SELECT COUNT(1) FROM   ("
                		+ " SELECT ROW_NUMBER() OVER(ORDER BY NOTICE_TYPE, REF DESC, STEP) RNUM, B_IDX "
                		+ "FROM (SELECT "+top+" '1' AS NOTICE_TYPE, A.* FROM  "
                		+ " (SELECT "+top+" *  FROM NBOARD  WHERE B_IDX IN (11,17) "+strWhere+" AND  IS_NOTICE='Y' ORDER BY BL_IDX DESC) A "
                		+ " UNION ALL SELECT '2' AS NOTICE_TYPE, * FROM NBOARD WHERE B_IDX IN (11,17) "+strWhere+" AND BL_IDX NOT IN (SELECT "+top+" BL_IDX FROM NBOARD WHERE B_IDX IN (11,17) "+strWhere+" AND  IS_NOTICE='Y' "
                		+ " ORDER BY BL_IDX DESC)  ) A1 "+tmp2+" ) A ";
            else
                query = "SELECT COUNT(1) FROM   ("
                		+ " SELECT ROW_NUMBER() OVER(ORDER BY NOTICE_TYPE, REF DESC, STEP) RNUM, B_IDX     "
                		+ "FROM (SELECT "+top+" '1' AS NOTICE_TYPE, A.* FROM "
                		+ " (SELECT "+top+" *  FROM NBOARD  WHERE B_IDX="+b_idx+" "+strWhere+" AND  IS_NOTICE='Y' ORDER BY BL_IDX DESC) A "
                		+ " UNION ALL  SELECT '2' AS NOTICE_TYPE, * FROM NBOARD WHERE B_IDX="+b_idx+" "+strWhere+" AND BL_IDX NOT IN (SELECT "+top+" BL_IDX FROM NBOARD WHERE B_IDX="+b_idx+" "+strWhere+" AND  IS_NOTICE='Y' "
                		+ " ORDER BY BL_IDX DESC)  ) A1 "+tmp2+" ) A ";
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
    public Map admBonNoticeContents(Map _params)
    {
        Map result = null;
        String b_idx = pnull(_params.get("b_idx"));
        String bl_idx = pnull(_params.get("bl_idx"));
        String query = "";
        
        try {
            query = "SELECT BL_IDX, TITLE, CONTENT, USER_ID, WRITER, EMAIL, REGDATE, IS_VIEW, IS_SECRET, HIT, IS_NOTICE, B_IDX, EVENT_DATE"
            		+ ", DEPART_IDX"
            		+ ", CHURCH_IDX"
            		+ ", DISPLAY_YN"
            		+ ", (SELECT FILENAME FROM NBOARD_UPLOAD WHERE BL_IDX='"+bl_idx+"') AS FILENAME "
            		+ " FROM NBOARD A "
            		+ " WHERE B_IDX='"+b_idx+"' AND BL_IDX='"+bl_idx+"'";
            
            result = super.executeQueryMap(query);

        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        	free();
        }
        
        return result;
    }

    @Override
    public Map getMember(Map _params)
    {
        Map result = new HashMap();
        String query = "";
        try
        {
            query = "SELECT ADM_NAME AS NAME FROM ADMMEMBER WHERE ADM_ID='"+_params.get("user_id")+"' ";
            result = executeQueryMap(query);

        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        	free();
        }
        
        return result;
    }

    @Override
    public boolean admBonNoticeInsert(Map _params, List list)
    {
        int rn=0;
        String master_id = pnull(_params.get("user_id"));
        
        PreparedStatement preparedStatement=null;
        String query="";
        Map row=null;
        boolean bReturn = true;
        
        
        Map memberInfo = getMember(_params);
        if(memberInfo == null)
            return false;
        
        try {
        	query = "  SELECT ISNULL(MAX(BL_IDX), 0) + 1 AS BL_IDX FROM NBOARD ";
        	row = super.executeQueryMap(query);

        } catch(Exception e) {
            _E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        }
        
        try
        {
            query = "INSERT INTO NBOARD  ("
            		+ " bl_idx, title, content, user_id, writer"
            		+ ", pwd, regdate, upddate, email, is_view"
            		+ ", is_secret, depart_idx, church_idx "
            		+ ", hit, b_idx, is_notice"
            		+ ", c_idx"
            		+ ", display_yn, event_date "
            		+ ")  VALUES ("
            		+ "?, ?, ?, ?, ?"
            		+ ", '', GETDATE(), NULL, '', 'Y'"
            		+ ",  'N', '"+pnull(_params, "depart_idx")+"', '"+pnull(_params, "church_idx")+"'"
            		+ ", 0, ?, ? "
            		+ ", (SELECT C_IDX FROM NBOARD_CATEGORY WHERE B_IDX = ? AND C_IDX > 22)"
            		+ ", ?,?)";
            preparedStatement = getPreparedStatement(query);
            preparedStatement.setString(1, pnull(row.get("BL_IDX")));
            preparedStatement.setString(2, pnull(_params.get("title")));
            preparedStatement.setString(3, pnull(_params.get("contents")));
            preparedStatement.setString(4, master_id);
            preparedStatement.setString(5, pnull(memberInfo.get("NAME")));
            
            preparedStatement.setString(6, pnull(_params.get("b_idx")));
            preparedStatement.setString(7, pnull(_params.get("is_notice"), "N"));
            
            preparedStatement.setString(8, pnull(_params.get("b_idx")));
            
            preparedStatement.setString(9, pnull(_params.get("display_yn")));
            preparedStatement.setString(10, pnull(_params.get("event_date")));
            rn = preparedStatement.executeUpdate();

        } catch(Exception e) {
            _E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        	if(preparedStatement != null) try { preparedStatement.close(); } catch(SQLException e) { e.printStackTrace(); }
        }
        
        try {
        	String fileUploadedURI  = pnull(_params, "CONTEXT_URI_PATH");//"/upload/church_notice/"
        	String fileUploadedPath = pnull(_params, "CONTEXT_ROOT_PATH");//"d:\\newcaincheon\\webapps\\upload\\"
            query = "INSERT INTO NBOARD_UPLOAD  (bu_idx,filepath,filesize,is_use,userid,filename,bl_idx,filetype,strfilename)  values((select ISNULL(MAX(BU_IDX), 0) + 1 from nboard_upload),  ?,?,?,?,?,?,NULL,?) ";
            preparedStatement = getPreparedStatement(query);
            for(int i = 0; i < list.size(); i++)
            {
                Map tmp = (Map)list.get(i);
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
        } finally {
        	if(preparedStatement != null) try { preparedStatement.close(); } catch(SQLException e) { e.printStackTrace(); }
            free();
        }
        
        return rn >= 1;
    }

    @Override
    public boolean admBonNoticeModify(Map _params, List list)
    {
        int rn=0;
        String query="";
        
        PreparedStatement preparedStatement=null;
        boolean bReturn = true;
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        String master_id = pnull(_params.get("user_id"));
        String bl_idx = pnull(_params.get("bl_idx"));
        Map memberInfo = getMember(_params);
        if(memberInfo == null)
            return false;
        
        try {
            if(pnull(_params.get("delFile")).equals("Y")) {
                query = "delete from NBOARD_UPLOAD where bl_idx="+bl_idx;
                rn = executeUpdate(query);
            }
        } catch(Exception e) {
            _E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        }
        
        //
        try
        {
            query = "UPDATE NBOARD SET  title= ?, content = ?,  user_id = ?,  writer = ?, is_notice = ?,  display_yn = ?"
            		+ ",  b_idx = ? , upddate = getdate()  WHERE bl_idx = ? ";
            _logger.info((new StringBuilder("query \uC870\uD68C : ")).append(query).toString());
            
            preparedStatement = getPreparedStatement(query);
            preparedStatement.setString(1, pnull(_params.get("title")));
            preparedStatement.setString(2, pnull(_params.get("contents")));
            preparedStatement.setString(3, master_id);
            preparedStatement.setString(4, pnull(memberInfo.get("NAME")));
            preparedStatement.setString(5, pnull(_params.get("is_notice"), "N"));
            preparedStatement.setString(6, pnull(_params.get("display_yn")));
            preparedStatement.setString(7, pnull(_params.get("b_idx")));
            preparedStatement.setString(8, pnull(_params.get("bl_idx")));
            rn = preparedStatement.executeUpdate();

        } catch(Exception e) {
            _E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        	if(preparedStatement != null) try { preparedStatement.close(); } catch(SQLException e) { e.printStackTrace(); }
        }
        
        try
        {
        	String fileUploadedURI  = pnull(_params, "CONTEXT_URI_PATH");//"d:\\newcaincheon\\webapps\\upload\\"
        	String fileUploadedPath = pnull(_params, "CONTEXT_ROOT_PATH");//"d:\\newcaincheon\\webapps\\upload\\"
        	
            query = "INSERT INTO NBOARD_UPLOAD "
            		+ " (bu_idx,filepath,filesize,is_use,userid,filename,bl_idx,filetype,strfilename) "
            		+ " values ((select ISNULL(MAX(BU_IDX), 0) + 1 from nboard_upload),  ?,?,?,?,?,?,NULL,?) ";
            preparedStatement = getPreparedStatement(query);
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
        } finally {
        	if(preparedStatement != null) try { preparedStatement.close(); } catch(SQLException e) { e.printStackTrace(); }
        }
        
        return rn >= 1;
    }

    @Override
    public boolean admBonNoticeDelete(Map _params)
    {
        int rn=0;
        String query="";
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        String bl_idx = pnull(_params.get("bl_idx"), "0");
        
        try {
            query = "DELETE FROM NBOARD WHERE BL_IDX="+bl_idx;
            rn = executeUpdate(query);
        } catch(Exception e) {
            _E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        }
        
        try {
            query = "DELETE FROM NBOARD_UPLOAD WHERE BL_IDX="+bl_idx;
            rn += executeUpdate(query);
        } catch(Exception e) {
            _E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        }
        
        return rn >= 1;
    }

    @Override
    public List _1x00xList(Map _params)
    {
        List result=null;
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        String level = pnull(_params.get("LEVEL"));
        String lv1 = pnull(_params.get("LV1"));
        String query = "";

        try {
//            query = "SELECT /* Wrong by Old-SQL */ depart_idx, name, depart_code, left(depart_code,2) depart_code_1x "
//            		+ " FROM DEPARTMENT "
//            		+ " WHERE left(depart_code,1)='"+level+"'  AND right(depart_code,3)<>'000'  "
//            		+ " ORDER BY name ";
//            
//            query = "SELECT /* Corrected */ church_idx, name, church_code, LEFT(church_code,2) AS LV1, SUBSTRING(church_code,3,2) AS LV2, RIGHT(church_code,3) AS LV3 "
//            		+ " FROM CHURCH "
//            		+ " WHERE RIGHT(church_code, 3)<>'000' "
//            		+ " ORDER BY name ";
            
            query = "SELECT /* church list */ DISTINCT C.CHURCH_IDX, C.NAME, O.NAME AS ORG_NAME, O.ORG_IDX, O.LV1+O.LV2+O.LV3 AS CHURCH_CODE, O.LV1, O.LV2, O.LV3 "
            		+ " FROM CHURCH  C "
            		+ " LEFT JOIN NEWCAINCHEON.ORG_HIERARCHY O ON O.ORG_IDX = C.ORG_IDX " + (lv1.length()>0 ? " AND LV1='"+lv1+"' ":"")
            		+ " WHERE O.LV3 <> '000'"
            		+ " ORDER BY NAME ";
            
            result = super.executeQueryList(query);

        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        	free();
        }
        
        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+result );
        
        return result;
    }

    
}
