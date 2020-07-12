// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DocDaoImpl.java

package kr.caincheon.church.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import kr.caincheon.church.common.base.CommonDao;
import kr.caincheon.church.common.utils.FileUtils;

// Referenced classes of package kr.caincheon.church.dao:
//            DocDao

@Repository("docDao")
public class DocDaoImpl extends CommonDao
    implements DocDao
{


    public List docList(Map _params)
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
        List result=null;
        
        String id = pnull(_params.get("id"));
        String ADM_YN = pnull(_params.get("ADM_YN"));
        String searchGubun1 = pnull(_params.get("searchGubun1"));
        String searchGubun2 = pnull(_params.get("searchGubun2"));
        String schText = pnull(_params.get("searchText"));
        String strWhere = "";
        
        if(searchGubun1.length() > 0)
            strWhere = " AND D.member_type='"+searchGubun1+"' ";
        if(schText.length() > 0)
            strWhere = " AND D."+searchGubun2+" LIKE '%"+schText+"%' ";
        if(pnull(_params.get("approve_yn")).equals("N"))
            strWhere = " AND D.APPROVE_YN = 'N' ";
        
        int pageNo   = ipnull(_params, "pageNo", 1);
        int pageSize = ipnull(_params, "pageSize", 10);
        int startRnum = (pageNo - 1) * pageSize + 1;
        int endRnum = pageNo * pageSize;
        String query = "";
        try
        {
        	String orderby = " ROW_NUMBER() OVER(ORDER BY D.APPLY_DAY DESC) RNUM ", idAnd = " AND D.ID='"+id+"' ";
            if(ADM_YN.equals("Y")) {
            	orderby = " ROW_NUMBER() OVER(ORDER BY D.APPROVE_YN ASC, D.APPLY_DAY DESC) RNUM ";
            	idAnd = "";
            }
            	
        	query = "SELECT * FROM "
            		+ " ( "
            		+ " SELECT "+orderby+", D.DOCTRINE_NO, D.ID,  D.MEMBER_TYPE"
            		+ " , CASE WHEN D.MEMBER_TYPE='1' THEN '예비신자' WHEN D.MEMBER_TYPE='2' THEN '재교육자' ELSE '' END AS MEMBER_TYPE_TEXT"
            		+ " , D.NAME, D.BAPTISMAL_NAME, D.CHURCH_IDX, C.NAME AS CHURCH_NAME,  CONVERT(char(10),  D.APPLY_DAY, 120) APPLY_DAY,  D.APPROVE_YN"
            		+ " , CASE WHEN D.APPROVE_YN = 'Y' THEN '승인'  ELSE '미승인' END AS APPROVE_YN_TEXT "
            		+ " FROM DOCTRINE_APPLY D "
            		+ " LEFT OUTER JOIN CHURCH C ON C.CHURCH_IDX = D.CHURCH_IDX "
            		+ " WHERE 1=1 "+strWhere+" " + idAnd
            		+ ") ROWS "
            		+ " WHERE RNUM BETWEEN "+startRnum+" AND "+endRnum;
//                query = "SELECT * FROM "
//                		+ " ( "
//                		+ " SELECT ROW_NUMBER() OVER(ORDER BY D.APPROVE_YN ASC, D.APPLY_DAY DESC) RNUM, D.DOCTRINE_NO, D.ID,  D.MEMBER_TYPE"
//                		+ " , CASE WHEN D.MEMBER_TYPE='1' THEN '예비신자' WHEN D.MEMBER_TYPE='2' THEN '재교육자' ELSE '' END AS MEMBER_TYPE_TEXT"
//                		+ " , D.NAME, D.BAPTISMAL_NAME, D.CHURCH_IDX, C.NAME AS CHURCH_NAME,  CONVERT(char(10),  D.APPLY_DAY, 120) APPLY_DAY,  D.APPROVE_YN"
//                		+ " , CASE WHEN D.APPROVE_YN = 'Y' THEN '승인'  ELSE '미승인' END AS APPROVE_YN_TEXT "
//                		+ " FROM DOCTRINE_APPLY D "
//                		+ " LEFT OUTER JOIN CHURCH C ON C.CHURCH_IDX = D.CHURCH_IDX "
//                		+ " WHERE 1=1 "+strWhere+" "
//                		+ ") ROWS "
//                		+ " WHERE RNUM BETWEEN "+startRnum+" AND "+endRnum;
//            else
//                query = "SELECT * FROM  "
//                		+ " ( "
//                		+ " SELECT ROW_NUMBER() OVER(ORDER BY D.APPLY_DAY DESC) RNUM, D.DOCTRINE_NO, D.ID,  D.MEMBER_TYPE"
//                		+ ", CASE WHEN D.MEMBER_TYPE='1' THEN '예비신자' WHEN D.MEMBER_TYPE='2' THEN '재교육자' ELSE '' END AS MEMBER_TYPE_TEXT"
//                		+ ", D.NAME, D.BAPTISMAL_NAME, D.CHURCH_IDX, C.NAME AS CHURCH_NAME, CONVERT(char(10),  D.APPLY_DAY, 120) APPLY_DAY,  D.APPROVE_YN"
//                		+ ", CASE WHEN D.APPROVE_YN = 'Y' THEN '승인'  ELSE '미승인' END AS APPROVE_YN_TEXT"
//                		+ " FROM DEPARTMENT C, DOCTRINE_APPLY D WHERE C.DEPART_CODE=D.CHURCH_IDX "
//                		+ strWhere+") ROWS WHERE RNUM BETWEEN "+startRnum+" AND "+endRnum;
            result = executeQueryList(query);
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        	free();
    	}

        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+result );
        return result;
    }

    public int docListCount(Map _params)
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
        String strWhere = "";
        int result = 0;

        String id     = pnull(_params.get("id"));
        String ADM_YN = pnull(_params.get("ADM_YN"));
        String searchGubun1 = pnull(_params.get("searchGubun1"));
        String searchGubun2 = pnull(_params.get("searchGubun2"));
        String schText      = pnull(_params.get("schText"));
        
        if(searchGubun1.length() > 0)
            strWhere = " AND D.member_type='"+searchGubun1+"' ";
        if(schText.length() > 0)
            strWhere = " AND D."+searchGubun2+" LIKE '%"+schText+"%' ";
        if(pnull(_params.get("approve_yn")).equals("N"))
            strWhere = " AND D.APPROVE_YN = 'N' ";
        
        String query = "";
        try
        {
            if(ADM_YN.equals("Y"))
                query = "SELECT COUNT(1) FROM  (SELECT D.DOCTRINE_NO "
                		+ " FROM DOCTRINE_APPLY D "
                		+ " LEFT OUTER JOIN CHURCH C ON C.CHURCH_IDX = D.CHURCH_IDX "
                		+ " WHERE 1=1 "+strWhere+") ROWS  WHERE 1= 1";
            else
                query = "SELECT count(1) FROM  (SELECT D.DOCTRINE_NO "
                		+ " FROM DOCTRINE_APPLY D "
                		+ " LEFT OUTER JOIN CHURCH C ON C.CHURCH_IDX = D.CHURCH_IDX "
            			+ " WHERE 1=1 AND D.ID='"+id+"' "+strWhere+") ROWS WHERE 1 = 1";
            
            result = super.executeCount(query, false);
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        	free();
    	}

        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+result );
        return result;
    }

    public Map docContents(Map _params)
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
        Map result = null;
        String id = pnull(_params.get("id"));
        String doctrine_no = pnull(_params.get("doctrine_no"));
        String where = "", query = "";
        try
        {
            where = " DOCTRINE_NO='"+doctrine_no+"' ";
            if(doctrine_no.equals(""))
                query = "SELECT ID, NAME, TEL AS M_TEL, ID AS EMAIL  FROM MEMBER WHERE ID='"+id+"'";
            else
                query = "SELECT DOCTRINE_NO, ID, MEMBER_TYPE, NAME, BAPTISMAL_NAME, CHURCH_IDX "
                		+ ", APPROVE_YN, SEX, JOB, BIRTH_TYPE,  BIRTHDAY, M_TEL, H_TEL, O_TEL, EMAIL, POSTCODE, ADDR1, ADDR2, USER_COMMENT, ADMIN_COMMENT, FILENAME, STRFILENAME "
                		+ ", CONVERT(CHAR(10), APPLY_DAY, 120) APPLY_DAY "
                		+ " FROM DOCTRINE_APPLY "
                		+ " WHERE "+where;
            
            result = super.executeQueryMap(query);
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        	free();
    	}

        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+result );
        return result;
    }

    public Map getMember(Map _params)
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
        Map result = new HashMap();
        String query = "";
        try
        {
            query = "SELECT NAME, BAPTISMALNAME, MEMBERTYPE, CHURCH_IDX FROM MEMBER WHERE ID='"+_params.get("member_id")+"' ";
            result = super.executeQueryMap(query);
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        	free();
    	}

        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+result );
        return result;
    }

    public boolean docInsert(Map _params)
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "params ? "+_params);
    	
    	int rn = 0;
        String id = pnull(_params.get("id"));
        String uri = pnull(_params, "CONTEXT_URI_PATH");
        
        String query = "";
        try
        {
            query = "INSERT INTO DOCTRINE_APPLY ("
            		+ "  ID, MEMBER_TYPE, NAME, BAPTISMAL_NAME, CHURCH_IDX"
            		+ ", APPLY_DAY,APPROVE_YN,SEX,JOB,BIRTH_TYPE,BIRTHDAY"
            		+ ", M_TEL,H_TEL,O_TEL,PHOTO,EMAIL,POSTCODE,ADDR1,ADDR2, USER_COMMENT,ADMIN_COMMENT,UPDATEDT"
            		+ ", FILENAME, FILESIZE, STRFILENAME"
            		+ ") VALUES (?, ?, ?, ?, ?,   GETDATE(),'N', ?, ?, ?, ?,  ?, ?, ?, '', ?, ?, ?, ?, ?, '', GETDATE(), ?, ?, ?)";
            
            LinkedHashMap mParams = getLinkedHashMap(id 
            		, pnull(_params, "member_type")
            		, pnull(_params, "name")
            		, pnull(_params, "baptismal_name")
            		, pnull(_params, "church_idx")
            		, pnull(_params, "sex")
            		, pnull(_params, "job")
            		, pnull(_params, "birth_type")
            		, pnull(_params, "birthday")
            		, pnull(_params, "m_tel")
            		, pnull(_params, "h_tel")
            		, pnull(_params, "o_tel")
            		, pnull(_params, "email")
            		, pnull(_params, "postcode")
            		, pnull(_params, "addr1")
            		, pnull(_params, "addr2")
            		, pnull(_params, "user_comment")
            		, pnull(_params, "filename")
            		, pnull(_params, "filesize")
            		, uri + pnull(_params, "strfilename")
            		); 
            rn = executeUpdatePreparedStatement(query, mParams);
            
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        	free();
    	}
        

        D(_logger, Thread.currentThread().getStackTrace(), "Query Result: rn="+ rn);
        return rn > 0;
    }

    public boolean docModify(Map _params)
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
        int rn = 0;
        
        String id = pnull(_params.get("id"));
        
        String doctrine_no  = pnull(_params.get("doctrine_no"));
        String member_type  = pnull(_params.get("member_type"));
        String name         = pnull(_params.get("name"));
        String baptismal_name = pnull(_params.get("baptismal_name"));
        String church_idx   = pnull(_params.get("church_idx"));
        String approve_yn   = pnull(_params.get("approve_yn"));
        String sex          = pnull(_params.get("sex"));
        String job          = pnull(_params.get("job"));
        String birth_type   = pnull(_params.get("birth_type"));
        String birthday     = pnull(_params.get("birthday"));
        String m_tel        = pnull(_params.get("m_tel"));
        String h_tel        = pnull(_params.get("h_tel"));
        String o_tel        = pnull(_params.get("o_tel"));
        String email        = pnull(_params.get("email"));
        String postcode     = pnull(_params.get("postcode"));
        String addr1        = pnull(_params.get("addr1"));
        String addr2        = pnull(_params.get("addr2"));
        String user_comment = pnull(_params.get("user_comment"));
        String filename     = pnull(_params.get("filename"));
        String filesize     = pnull(_params.get("filesize"));
        String strfilename  = pnull(_params.get("strfilename"));
        
        // 기존 파일 삭제라면,,,,
        String delFile  = pnull(_params.get("delFile"));
        if("Y".equalsIgnoreCase(delFile)) {
        	Map row = null;
			try {
				row = executeQueryMap(" SELECT strfilename FROM DOCTRINE_APPLY WHERE DOCTRINE_NO = '"+doctrine_no+"'");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if( row.size() > 0 ) {
				String delfile = pnull(row, "STRFILENAME");
				delfile = delfile.substring(delfile.lastIndexOf("/") + 1);
				
				String fileFullpath          = pnull(_params, "CONTEXT_ROOT_PATH") + delfile;
	        	String thumbnailFileFullpath = pnull(_params, "CONTEXT_ROOT_PATH") + "thumbnail/" + delfile;
	        	
	        	FileUtils.getInstance().deleteFile(fileFullpath, thumbnailFileFullpath);
			}
        }
        
        String query ="";
        try
        {
        	query = "UPDATE DOCTRINE_APPLY  "
        			+ " SET MEMBER_TYPE=?, NAME=?, BAPTISMAL_NAME=?, CHURCH_IDX=?,  APPROVE_YN=?, SEX=?, JOB=?, BIRTH_TYPE=?"
        			+ ", BIRTHDAY=?, M_TEL=?, H_TEL=?, O_TEL=?, EMAIL=?, POSTCODE=?, ADDR1=?, ADDR2=?, USER_COMMENT=?, updateDT=getdate()"
        			+ (strfilename.length() == 0 ? "" : ", filename=?, filesize=?, strfilename=? " )
        			+ " WHERE DOCTRINE_NO = '"+doctrine_no+"'";

            LinkedHashMap mParams = getLinkedHashMap(
            		  member_type
            		, name
            		, baptismal_name
            		, church_idx
            		, approve_yn
            		, sex
            		, job
            		, birth_type
            		, birthday
            		, m_tel
            		, h_tel
            		, o_tel
            		, email
            		, postcode
            		, addr1
            		, addr2
            		, user_comment
            		, filename
            		, filesize
            		, pnull(_params, "CONTEXT_URI_PATH")+strfilename
            		);
            if(strfilename.length()==0) {
            	mParams.remove("18");
            	mParams.remove("19");
            	mParams.remove("20");
            }
            rn = executeUpdatePreparedStatement(query, mParams);
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        } finally {
        	free();
    	}
        

        D(_logger, Thread.currentThread().getStackTrace(), "Query Result: rn="+rn );
        return rn > 0;
    }

    public boolean docDelete(Map _params)
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
        boolean bReturn = true;
        String query="";
        String doctrine_no = pnull(_params.get("doctrine_no"));

        try {
            query = "DELETE FROM DOCTRINE_APPLY WHERE DOCTRINE_NO = "+doctrine_no;
            int i = executeUpdate(query);
            bReturn = i == 1 ? true : false;
            
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
            bReturn = false;
        } finally {
        	free();
    	}

        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+bReturn);
        return bReturn;
    }

    public List docExcelList(Map _params)
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
        List result = null;
        
        String searchGubun1 = pnull(_params.get("searchGubun1"));
        String searchGubun2 = pnull(_params.get("searchGubun2"));
        String schText = pnull(_params.get("searchText"));
        String strWhere = "";
        
        if(searchGubun1.length() > 0)
            strWhere = " AND D.member_type='"+searchGubun1+"' ";
        if(schText.length() > 0)
            strWhere = " AND D."+searchGubun2+" LIKE '%"+schText+"%' ";
        
        
        String query = "";
        try
        {
            query = "SELECT * FROM "
            		+ " ("
            		+ " SELECT ROW_NUMBER() OVER(ORDER BY D.APPLY_DAY DESC) RNUM, D.DOCTRINE_NO, D.ID"
            		+ ",  CASE WHEN D.MEMBER_TYPE='1' THEN '예비신자' WHEN D.MEMBER_TYPE='2' THEN '재교육자' ELSE '' END AS MEMBER_TYPE_TEXT"
            		+ ",  D.NAME, D.BAPTISMAL_NAME, D.CHURCH_IDX, C.NAME AS CHURCH_NAME"
            		+ ",  CONVERT(char(10),  D.APPLY_DAY, 120) APPLY_DAY"
            		+ ",  CASE WHEN D.APPROVE_YN = 'Y' THEN '승인'  ELSE '미승인' END AS APPROVE_YN_TEXT "
            		+ " FROM DOCTRINE_APPLY D "
            		+ " LEFT OUTER JOIN  CHURCH C ON C.CHURCH_IDX = D.CHURCH_IDX "
            		+ " WHERE 1=1 "+strWhere
            		+") ROWS ";
            result = super.executeQueryList(query);
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
