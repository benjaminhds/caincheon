// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NBoardDaoImpl.java

package kr.caincheon.church.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import kr.caincheon.church.common.base.CommonDao;
import kr.caincheon.church.common.base.CommonExecuteQuery;

/**
 *  
 *
 */
@Repository("admNewsDao")
public class AdmNewsDaoImpl extends CommonDao implements AdmNewsDao {

	private final Logger _logger = Logger.getLogger(getClass());
	
	@Override
	public List<Map<String,Object>> newsList(Map _params, String menu) {
		
		String idxGroup = "";
		String query = "";
		String strWhere = "";
		List<Map<String,Object>> result = null;
		
		D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+", menu="+menu+"]" );
		
		// 게시판 판별
		//idxGroup = getBIdxsInBoardCategory(menu);
		
		// paging
		int pageNo    = ipnull(_params, "pageNo", 1);
		int pageSize  = ipnull(_params, "pageSize", 10);
		int startRnum = (pageNo - 1) * pageSize + 1;
		int endRnum   = pageNo * pageSize;
		
		// parameters
		String b_idx        = pnull(_params, "searchGubun1", "ALL");//구분
		String schTextGubun = pnull(_params, "searchGubun2");//검색조건
		String schText      = pnull(_params, "searchText");//검색키워드
		
		String church_idx = pnull(_params, "church_idx");
		String depart_idx = pnull(_params, "depart_idx");
		
		if(schText.length()>0 && schTextGubun.length()==0) schTextGubun=" TITLE ";
		
		// Query Combination
		if (schText.length() > 0)
			if (schTextGubun.equalsIgnoreCase("ALL"))
				strWhere = " AND (TITLE LIKE '%"+schText+"%' OR CONTENT LIKE '%"+schText+"%')";
			else
				strWhere = " AND "+schTextGubun+" LIKE '%"+schText+"%' ";
		
		if (depart_idx.length() > 0 && menu.equals("church"))
			strWhere += " AND depart_idx = '"+depart_idx+"' ";

		if (church_idx.length() > 0 && menu.equals("church"))
			strWhere += " AND church_idx = '"+church_idx+"' ";
		
		try {
			String b_idx_cause = " C_IDX=(SELECT C_IDX FROM NBOARD_CATEGORY WHERE B_IDX="+b_idx+" AND C_IDX>22 AND IS_USE='Y') ";
			if (b_idx.equalsIgnoreCase("ALL") || b_idx.length() == 0)
				b_idx_cause = " C_IDX=(SELECT C_IDX FROM NBOARD_CATEGORY WHERE B_IDX IN ("+idxGroup+") AND C_IDX>22 AND IS_USE='Y') ";
			
			// 쿼리성능 개선을 위해, 서브쿼리가 아닌 별도 쿼리로 변경
			String b_idxs = (b_idx.equalsIgnoreCase("ALL") || b_idx.length() == 0 ? idxGroup : b_idx);
			query = "SELECT C_IDX FROM NBOARD_CATEGORY WHERE B_IDX IN ("+b_idxs+") AND IS_USE='Y'";
			b_idx_cause  = CommonExecuteQuery.executeCIdxsQuery(this, (b_idx.equalsIgnoreCase("ALL") || b_idx.length() == 0 ? idxGroup : b_idx) );
			
			query = "SELECT /* PriestLists */  B.NAME AS CATEGORY_NAME, B.C_IDX AS CATEGORY_IDX"
					+ ", A.DEPART_IDX, A.CHURCH_IDX, O.ORG_IDX O_ORG_IDX, O.NAME ORG_NAME "
					+ ", O.LV1+O.LV2+O.LV3 AS ORG_CODE /* O.LV1 소속분류코드(000001) : 01 교구, 02 본당(수도회) */ "
					+ ", A.*  "
					+ "FROM ("
//					+ "   SELECT ROW_NUMBER() OVER(ORDER BY NOTICE_TYPE, REF DESC, STEP) RNUM "
					+ "   SELECT ROW_NUMBER() OVER(ORDER BY NOTICE_TYPE, REGDATE DESC, BL_IDX DESC) RNUM "
					+ "      , A1.CHURCH_IDX, A1.DEPART_IDX "
					+ "      , A1.IS_VIEW , B_IDX,  NOTICE_TYPE, BL_IDX, TITLE, USER_ID, WRITER, convert(char(10),  REGDATE, 120) REGDATE, HIT, C_IDX, IS_SECRET "
					+ "      , (SELECT FILENAME FROM NBOARD_UPLOAD A3 WHERE A3.BU_IDX IN (SELECT MAX(BU_IDX) FROM NBOARD_UPLOAD A4 WHERE A1.BL_IDX=A4.BL_IDX AND A4.FILETYPE is null) ) FILENAME "
					+ "      , (SELECT FILEPATH FROM NBOARD_UPLOAD A3 WHERE A3.BU_IDX IN (SELECT MAX(BU_IDX) FROM NBOARD_UPLOAD A4 WHERE A1.BL_IDX=A4.BL_IDX AND A4.FILETYPE is null) ) FILEPATH "
//					+ "      , (SELECT STRFILENAME FROM NBOARD_UPLOAD A3 WHERE A3.BU_IDX IN (SELECT MAX(BU_IDX) FROM NBOARD_UPLOAD A4 WHERE A1.BL_IDX=A4.BL_IDX AND A4.FILETYPE is null) ) STRFILENAME "
					+ "      , (SELECT COUNT(*) FROM NBOARD_UPLOAD A3 WHERE A1.BL_IDX=A3.BL_IDX AND IS_USE='Y') FILE_CNT "
//					+ "      , (SELECT COUNT(*) FROM NBOARD_MEMO A2 WHERE A1.BL_IDX=A2.BL_IDX) MEMO_CNT "
					+ "   FROM ( "
					+ "       SELECT TOP 50 '1' AS NOTICE_TYPE, A.* FROM  (SELECT top 50 *  FROM NBOARD  WHERE " + b_idx_cause + strWhere + " AND  IS_NOTICE='Y' ORDER BY BL_IDX DESC) A "
					+ "       UNION ALL "
					+ "       SELECT '2' AS NOTICE_TYPE, * FROM NBOARD WHERE " + b_idx_cause + strWhere + " AND BL_IDX NOT IN (SELECT TOP 50 BL_IDX FROM NBOARD WHERE " + b_idx_cause + strWhere + " AND  IS_NOTICE='Y' ORDER BY BL_IDX DESC) "
					+ "      ) A1 "
					+ "      LEFT OUTER JOIN DEPARTMENT D ON A1.DEPART_IDX = D.DEPART_IDX   "
					+ " ) A " 
					+ " LEFT OUTER JOIN NBOARD_CATEGORY B ON B.C_IDX      = A.C_IDX  "
					+ " LEFT OUTER JOIN CHURCH          C ON C.CHURCH_IDX = A.CHURCH_IDX " 
					+ " LEFT OUTER JOIN ORG_HIERARCHY   O ON O.ORG_IDX    = A.DEPART_IDX "
					+ " WHERE RNUM BETWEEN " + startRnum + " AND " + endRnum
					+" ORDER BY A.NOTICE_TYPE, A.REGDATE DESC, A.BL_IDX DESC "
					;
			
			result = super.executeQueryList(query);

		} catch (Exception e) {
			_logger.error((new StringBuilder("ERROR SQL:")).append(query).toString(), e);
		} finally {
			free();
		}
		return result;
	}

	
	public int newsListCount(Map _params, String menu) {
		String b_idx = pnull(_params, "searchGubun1", "ALL");
		String idxGroup = "", strWhere="";
		int result=0;
		
		D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );

		// 게시판 판별 :: b_idx 코드 가져오기
		//idxGroup = getBIdxsInBoardCategory(menu);
		
		//
		String query = "";
		String schText = pnull(_params, "searchText");
		String schTextGubun = pnull(_params, "searchGubun2");
		String depart_idx = pnull(_params, "depart_idx");
		String church_idx = pnull(_params, "church_idx");
		

		if (schText.length() > 0)
			if (schTextGubun.equalsIgnoreCase("ALL"))
				strWhere = " AND (TITLE LIKE '%" + schText + "%' OR CONTENT LIKE '%" + schText + "%')";
			else
				strWhere = " AND " + schTextGubun + " LIKE '%" + schText + "%' ";
		
		if (depart_idx.length() > 0 && menu.equals("church")) strWhere = strWhere + " AND depart_idx = '" + depart_idx + "' ";
		if (church_idx.length() > 0 && menu.equals("church")) strWhere = strWhere + " AND church_idx = '" + church_idx + "' ";
		
		try {
			String b_idx_cause = " C_IDX=(SELECT C_IDX FROM NBOARD_CATEGORY WHERE B_IDX="+b_idx+" AND C_IDX>22 AND IS_USE='Y') ";
			if (b_idx.equalsIgnoreCase("ALL") || b_idx.length() == 0)
				b_idx_cause = " C_IDX=(SELECT C_IDX FROM NBOARD_CATEGORY WHERE B_IDX IN ("+idxGroup+") AND C_IDX>22 AND IS_USE='Y') ";

			// 쿼리성능 개선을 위해, 서브쿼리가 아닌 별도 쿼리로 변경
			b_idx_cause  = CommonExecuteQuery.executeCIdxsQuery(this, (b_idx.equalsIgnoreCase("ALL") || b_idx.length() == 0 ? idxGroup : b_idx) );
			
			if (b_idx.equalsIgnoreCase("ALL") || b_idx.length() == 0)
				query = "SELECT COUNT(1) FROM ("
						+ "  SELECT ROW_NUMBER() OVER(ORDER BY NOTICE_TYPE, REF DESC, STEP) RNUM, B_IDX  "
						+ "  FROM ( "
						+ "     SELECT TOP 2 '1' AS NOTICE_TYPE, A.* FROM (SELECT top 50 *  FROM NBOARD  WHERE "+b_idx_cause+strWhere+" AND  IS_NOTICE='Y' ORDER BY BL_IDX DESC) A "
						+ "     UNION ALL  "
						+ "     SELECT '2' AS NOTICE_TYPE, *  FROM NBOARD WHERE "+b_idx_cause+strWhere +" AND BL_IDX NOT IN (SELECT TOP 50 BL_IDX FROM NBOARD WHERE "+b_idx_cause+strWhere+" AND  IS_NOTICE='Y' ORDER BY BL_IDX DESC) "
						+ "  ) A1  "
						+ ") A ";
			else
				query = "SELECT COUNT(1) FROM ("
						+ "  SELECT ROW_NUMBER() OVER(ORDER BY NOTICE_TYPE, REF DESC, STEP) RNUM, B_IDX "
						+ "  FROM ("
						+ "    SELECT TOP 2 '1' AS NOTICE_TYPE, A.* FROM (SELECT top 50 *  FROM NBOARD  WHERE B_IDX=" + b_idx+" "+strWhere+" AND IS_NOTICE='Y' ORDER BY BL_IDX DESC) A "
						+ "    UNION ALL  "
						+ "    SELECT '2' AS NOTICE_TYPE, * FROM NBOARD  WHERE "+b_idx_cause+strWhere+" "+" AND BL_IDX NOT IN (SELECT TOP 50 BL_IDX FROM NBOARD WHERE "+b_idx_cause+strWhere+" AND IS_NOTICE='Y' ORDER BY BL_IDX DESC) "
						+"   ) A1 "
						+ ") A ";
			result = super.executeCount(query, false);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			free();
		}

		return result;
	}

	/**
	 * 
	 * @deprecated 
	 */
	public Map newsContents(Map _params) {
		Map result = null;
		
		String b_idx = pnull(_params, "b_idx");
		String bl_idx = pnull(_params, "bl_idx");
		
		String query = "";
		try {
			query = "SELECT A.BL_IDX, A.TITLE, A.CONTENT, A.USER_ID, A.WRITER, A.EMAIL, A.REGDATE, A.IS_VIEW, A.IS_SECRET, A.HIT, A.IS_NOTICE, A.B_IDX, A.EVENT_DATE, A.DEPART_IDX, A.CHURCH_IDX, A.C_IDX, C.ORG_IDX, C.NAME CHURCH_NAME, A.DISPLAY_YN, A.DOWN_LEVEL "
					+ ", (SELECT A.bu_idx FROM (SELECT ROW_NUMBER() over(order by bu_idx desc) as rnum, bu_idx FROM NBOARD_UPLOAD WHERE BL_IDX='"+bl_idx+"') A WHERE A.RNUM=1) AS FILE_BUIDX "
					+ ", (SELECT A.bu_idx FROM (SELECT ROW_NUMBER() over(order by bu_idx desc) as rnum, bu_idx FROM NBOARD_UPLOAD WHERE BL_IDX='"+bl_idx+"') A WHERE A.RNUM=2) AS FILE_BUIDX2"
					+ ", (SELECT A.FILEPATH FROM (SELECT ROW_NUMBER() over(order by bu_idx desc) as rnum, FILEPATH FROM NBOARD_UPLOAD WHERE BL_IDX='"+bl_idx+"') A WHERE A.RNUM=1) AS FILEPATH "
					+ ", (SELECT A.FILEPATH FROM (SELECT ROW_NUMBER() over(order by bu_idx desc) as rnum, FILEPATH FROM NBOARD_UPLOAD WHERE BL_IDX='"+bl_idx+"') A WHERE A.RNUM=2) AS FILEPATH2 "
					+ ", (SELECT A.FILENAME FROM (SELECT ROW_NUMBER() over(order by bu_idx desc) as rnum, FILENAME FROM NBOARD_UPLOAD WHERE BL_IDX='"+bl_idx+"') A WHERE A.RNUM=1) AS FILENAME "
					+ ", (SELECT A.FILENAME FROM (SELECT ROW_NUMBER() over(order by bu_idx desc) as rnum, FILENAME FROM NBOARD_UPLOAD WHERE BL_IDX='"+bl_idx+"') A WHERE A.RNUM=2) AS FILENAME2 "
					+ ", (SELECT A.STRFILENAME FROM (SELECT ROW_NUMBER() over(order by bu_idx desc) as rnum, STRFILENAME AS STRFILENAME FROM NBOARD_UPLOAD WHERE BL_IDX='"+bl_idx+"') A WHERE A.RNUM=1) AS STRFILENAME "
					+ ", (SELECT A.STRFILENAME FROM (SELECT ROW_NUMBER() over(order by bu_idx desc) as rnum, STRFILENAME AS STRFILENAME FROM NBOARD_UPLOAD WHERE BL_IDX='"+bl_idx+"') A WHERE A.RNUM=2) AS STRFILENAME2 "
					+ " FROM NBOARD A "
					+ " LEFT OUTER JOIN CHURCH C ON C.CHURCH_IDX = A.CHURCH_IDX "
					+ " WHERE B_IDX='"+b_idx+"' AND BL_IDX='"+bl_idx+"'"
					;
			//query = getSQLSelectNBoard(this, _params, true, 5);
			D(_logger, Thread.currentThread().getStackTrace(), "execute query : "+query );
			
			result = super.executeQueryMap(query);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			free();
		}
		return result;
	}

	public Map getMember(Map _params) {
		Map result = null;
		try {
			
			String query = "SELECT ADM_NAME AS NAME FROM ADMMEMBER WHERE ADM_ID='"+_params.get("user_id")+"' ";
			result = super.executeQueryMap(query);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		if(result==null) result = new HashMap();
		return result;
	}

	public boolean admNewsInsert(Map _params, List list) {
		
		
		//super.updateNBoardExecute(_params, uploadedFiles)
		
		int rn = 0, rn1 = 0, rn2 = 0;
		String master_id = "";
		Map memberInfo = new HashMap();
		Map row = new HashMap();

		PreparedStatement preparedStatement = null;
		String maxIdxSQL = "";
		boolean bReturn = true;

		D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );

		master_id = pnull(_params, "user_id");
		memberInfo = getMember(_params);

		if (memberInfo == null)
			return false;
		
		maxIdxSQL = " SELECT ISNULL(MAX(BL_IDX), 0) + 1 AS BL_IDX FROM NBOARD ";
		try {
			row = super.executeQueryMap(maxIdxSQL);
		} catch (SQLException e) {
			e.printStackTrace();
			E(_logger, Thread.currentThread().getStackTrace(), "ERR-MSG : " + e.getMessage() );
		} finally {
		}

		try {
			String org_idx = pnull(_params, "org_idx"); 
			String b_idx = pnull(_params, "b_idx"); 
			String c_idx = pnull(_params, "c_idx"); 
			String is_view = pnull(_params, "is_view", "Y"); 
			
			String query = "INSERT INTO NBOARD /* NBoardDaoImpl.admNewsInsert()1 */ ("
					+ "  bl_idx"
					+ ", title, content, user_id, writer, pwd, regdate, upddate, email, is_view, is_secret"
					+ ", depart_idx, church_idx, REF, STEP, LEVEL, hit, b_idx, is_notice"
					+ ", c_idx"
					+ ", event_date, down_level "
					+ ")  VALUES ("
					+ " ("+ maxIdxSQL +")"
					+ ", ?, ?, ?, ?, '', GETDATE(), NULL, '', '"+is_view+"',  'N'"
					+ ", '"+org_idx+"', '', ?, 0, 0, 0, "+b_idx+", ?"
					+ ", (SELECT C_IDX FROM NBOARD_CATEGORY WHERE B_IDX = ? AND C_IDX > 22)"
					+ ", ?, ?)";
			int i=1;
			preparedStatement = getPreparedStatement(query);
			//preparedStatement.setString(i++,  pnull(row.get("BL_IDX"))); // bl_idx
			preparedStatement.setString(i++,  pnull(_params, "title"));
			preparedStatement.setString(i++,  pnull(_params, "contents"));
			preparedStatement.setString(i++,  master_id); // user_id
			preparedStatement.setString(i++,  pnull(memberInfo.get("NAME"))); // writer
			
			preparedStatement.setString(i++,  pnull(row.get("BL_IDX"))); // ref
			//preparedStatement.setString(i++,  pnull(_params, "c_idx")); // step
			preparedStatement.setString(i++,  pnull(_params, "is_notice")); // is_notice
			
			preparedStatement.setString(i++,  b_idx);
			
			preparedStatement.setString(i++,  pnull(_params, "event_date"));
			preparedStatement.setString(i++,  pnull(_params, "down_level", "A"));
			rn1 = preparedStatement.executeUpdate();

			D(_logger, Thread.currentThread().getStackTrace(), "Query Result[rn1:"+rn1+"]");
			
		} catch (Exception e) {
			e.printStackTrace();
			bReturn = false;
		} finally {
			if (preparedStatement != null)
				try {
					preparedStatement.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
		}

		try {
			if ( list.size() > 0 ) {
				String fileUploadedURI  = pnull(_params, "CONTEXT_URI_PATH");// "d:\\newcaincheon\\webapps\\upload\\"
				String fileUploadedPath = pnull(_params, "CONTEXT_ROOT_PATH");// "d:\\newcaincheon\\webapps\\upload\\"
				
				String query = "INSERT INTO NBOARD_UPLOAD /* NBoardDaoImpl.admNewsInsert()2 */ "
						+ "(bu_idx,filepath,filesize,is_use,userid,filename,bl_idx,filetype,strfilename)  values ( "
						+ "(select ISNULL(MAX(BU_IDX), 0) + 1 from nboard_upload),  ?,?,?,?,?,?,NULL,?) ";
				
				preparedStatement = getPreparedStatement(query);
				for (int i = 0; i < list.size(); i++) {
					Map tmp = (Map) list.get(i);
					preparedStatement.setString(1, fileUploadedURI);
					preparedStatement.setString(2, pnull(tmp.get("FILE_SIZE")));
					preparedStatement.setString(3, pnull(tmp.get("IS_USE"), "Y"));
					preparedStatement.setString(4, master_id);
					preparedStatement.setString(5, pnull(tmp.get("ORIGINAL_FILE_NAME")));
					preparedStatement.setString(6, pnull(row.get("BL_IDX")));
					preparedStatement.setString(7, pnull(tmp.get("STORED_FILE_NAME")));
					rn2 += preparedStatement.executeUpdate();
					
					D(_logger, Thread.currentThread().getStackTrace(), "Query Result[i:"+i+", rn2:"+rn2+"]");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			bReturn = false;
		} finally {
			if (preparedStatement != null)
				try {
					preparedStatement.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			free();
		}
		rn = rn1 + rn2;
		return rn >= 1 && bReturn;
	}

	public boolean admNewsModify(Map _params, List list) {
		int rn = 0, rn1 = 0, rn2 = 0, rn3 = 0, rn4 = 0;
		String master_id;
		Map memberInfo;
		PreparedStatement preparedStatement = null;
		boolean bReturn = true;
		
		D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
		
		master_id = pnull(_params, "user_id");
		memberInfo = getMember(_params);

		if (memberInfo == null) return false;

		String uploadedFileURI  = pnull(_params, "CONTEXT_URI_PATH"); // "/upload/news/"
		String uploadedFileRootPath = pnull(_params, "CONTEXT_ROOT_PATH");// "d:/newcaincheon/webapps/upload/news/"

		// file delete
		try {
			String bl_idx = pnull(_params, "bl_idx");
			int rn5 = -1;
			for(int i=1; i<2; i++) {
				String strfilename = pnull(_params, "delFile"+i);
				if (strfilename.length() > 0) {
					String bu_idx = CommonExecuteQuery.deleteUploadedFile(this, bl_idx, strfilename, uploadedFileRootPath);
					if(bu_idx!=null) {
						rn5 = CommonExecuteQuery.deleteUploadedFileDbRecord(this, bu_idx);
						//rn5 = deleteUploadedFileDbRecord(bl_idx, strfilename);
					} else {
						rn5 = -1;
					}
					D(_logger, Thread.currentThread().getStackTrace(), "delete file ["+ i +"]. is del a file : "+(bu_idx==null ? "not exists":bu_idx) );
					D(_logger, Thread.currentThread().getStackTrace(), "delete db ["+ i +"]. result count rn : "+rn5 );
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			bReturn = false;
		} finally {
		}
		
		// file upload
		try {
			String query = "INSERT INTO NBOARD_UPLOAD (bu_idx, filepath, filesize, is_use,userid,filename,bl_idx,filetype,strfilename"
					+ ")  values("
					+ " (select ISNULL(MAX(BU_IDX), 0) + 1 from nboard_upload) "
					+ ",  ?,?,?,?,?,?,NULL,?) ";
			
			D(_logger, Thread.currentThread().getStackTrace(), "INSERT query :"+query );
			D(_logger, Thread.currentThread().getStackTrace(), "INSERT query fileUploadedURI:"+uploadedFileURI );
			
			
			preparedStatement = getPreparedStatement(query);
			for (int i = 0; i < list.size(); i++) {
				Map tmp = (Map) list.get(i);
				preparedStatement.setString(1, uploadedFileURI);
				preparedStatement.setString(2, pnull(tmp.get("FILE_SIZE")));
				preparedStatement.setString(3, pnull(tmp.get("IS_USE"), "Y"));
				preparedStatement.setString(4, master_id);
				preparedStatement.setString(5, pnull(tmp.get("ORIGINAL_FILE_NAME")));
				preparedStatement.setString(6, pnull(_params, "bl_idx"));
				preparedStatement.setString(7, pnull(tmp.get("STORED_FILE_NAME")));
				int x = preparedStatement.executeUpdate();
				rn4 += x;
				D(_logger, Thread.currentThread().getStackTrace(), "INSERT query strfilename:"+pnull(tmp.get("STORED_FILE_NAME")) + ", x:"+x+ ", rn4:"+rn4 );
			}

		} catch (Exception e) {
			e.printStackTrace();
			bReturn = false;
		} finally {

			if (preparedStatement != null)
				try {
					preparedStatement.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			free();
		}
		
		// board update
		try {
			String org_idx = pnull(_params, "org_idx", "null"); 
			String b_idx = pnull(_params, "b_idx"); 
			String c_idx = pnull(_params, "c_idx", "SELECT C_IDX FROM NBOARD_CATEGORY WHERE B_IDX="+b_idx+" AND IS_USE='Y' AND C_IDX>22"); 
			String is_view = pnull(_params, "is_view", "Y"); 
			int i = 1;
			//LinkedHashMap<String, String> lhm = new LinkedHashMap<String, String>();
			String query = "UPDATE NBOARD SET  title= ?, content = ?,  user_id = ?,  writer = ?,  is_notice = ?, event_date = ?"
					+ ", down_level = ?, b_idx = " + b_idx //(SELECT TOP 1 B_IDX FROM NBOARD_CATEGORY WHERE C_IDX IN ("+pnull(_params, "c_idx")+") ) "
					+ ", upddate = getdate()"
					+ ", is_view = '"+is_view+"' "
					+ ", depart_idx = "+org_idx
					+ ", c_idx =(" + c_idx + ") " //(SELECT C_IDX FROM NBOARD_CATEGORY WHERE B_IDX = ? AND C_IDX > 22)  "
					+ " WHERE bl_idx = ? ";
			
			preparedStatement = getPreparedStatement(query);
			preparedStatement.setString(i++,  pnull(_params, "title"));
			preparedStatement.setString(i++,  pnull(_params, "contents"));
			preparedStatement.setString(i++,  master_id);
			preparedStatement.setString(i++,  pnull(memberInfo.get("NAME")));
			preparedStatement.setString(i++,  pnull(_params, "is_notice", "N"));
			preparedStatement.setString(i++,  pnull(_params, "event_date"));
			
			preparedStatement.setString(i++,  pnull(_params, "down_level", "A"));
			//preparedStatement.setString(i++,  pnull(_params, "depart_code"));
			//preparedStatement.setString(i++, pnull(_params, "b_idx"));
			//preparedStatement.setString(i++, pnull(_params, "b_idx", "0"));
			preparedStatement.setString(i++, pnull(_params, "bl_idx"));
			rn3 = preparedStatement.executeUpdate();

			D(_logger, Thread.currentThread().getStackTrace(), "query executed result count rn3 : "+rn3 );
			
		} catch (Exception e) {
			e.printStackTrace();
			bReturn = false;
		} finally {
			if (preparedStatement != null)
				try {
					preparedStatement.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
		}

		rn = rn1 + rn2 + rn3 + rn4;
		return rn >= 1 && bReturn;
	}

	public boolean admNewsDelete(Map _params) {
		int rn = 0, rn1 = 0, rn2 = 0;
		String bl_idx = pnull(_params, "bl_idx", "0");

		D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );

		try {
			rn = executeUpdate("delete from NBOARD where bl_idx="+bl_idx);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}

		try {
			rn += executeUpdate("delete from NBOARD_UPLOAD where bl_idx="+bl_idx);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			free();
		}

		return rn >= 1;
	}

}
