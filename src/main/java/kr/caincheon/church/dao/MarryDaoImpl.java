// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MarryDaoImpl.java

package kr.caincheon.church.dao;

import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import kr.caincheon.church.common.base.CommonDao;
import kr.caincheon.church.common.base.CommonException;

// Referenced classes of package kr.caincheon.church.dao:
//            MarryDao

@Repository("marryDao")
public class MarryDaoImpl extends CommonDao
    implements MarryDao
{

	private final Logger _logger = Logger.getLogger(getClass());
	
	/*
     * 프론트 > 나의정보 > 카나혼인~~ 내가 신청한 것 조회
     * (non-Javadoc)
     * @see kr.caincheon.church.dao.MarryDao#marryListCount(java.util.Map)
     */
	@Override
    public List marryList(Map _params)
    {
        List result=null;
        String whereStr="";
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        String id = pnull(_params.get("id"));
        String page_type = pnull(_params.get("page_type"));
        int pageNo = Integer.parseInt(pnull(_params.get("pageNo"), "1"));
        int startRnum = (pageNo - 1) * 20 + 1;
        int endRnum = pageNo * 20;
        
        String query = "";
        whereStr = "";
        try
        {
            if(page_type.equals("admin"))
            {
                String search_gubun1 = pnull(_params.get("searchGubun1"));
                String search_gubun2 = pnull(_params.get("searchGubun2"));
                String search_gubun3 = pnull(_params.get("searchGubun3"));
                String search_date = pnull(_params.get("searchDate"));
                String search_text = pnull(_params.get("searchText"));
                
                if(search_date != null && search_date.length() != 0)
                    if(search_gubun1.equals("1"))
                        whereStr = " AND lecture_apply_day between '"+search_date+"' AND DATEADD(dd, 1, '"+search_date+"') ";
                    else
                        whereStr = " AND marry_day between '"+search_date+"' AND DATEADD(dd, 1, '"+search_date+"') ";
                if(search_gubun2.length() > 0)
                    whereStr = String.valueOf(whereStr)+" AND apply_type = '"+search_gubun2+"' ";
                if(search_text != null && search_text.length() != 0)
                    if(search_gubun3.equals("1"))
                        whereStr = String.valueOf(whereStr)+" AND (man_name like '%"+search_text+"%' OR woman_name like '%"+search_text+"%') ";
                    else
                        whereStr = String.valueOf(whereStr)+" AND (man_baptismal_name like '%"+search_text+"%' OR woman_baptismal_name like '%"+search_text+"%') ";
                
                query = "SELECT * FROM ("
                		+ "SELECT ROW_NUMBER() OVER(ORDER BY XO.XX ASC, XO.LECTURE_APPLY_DAY DESC, XO.APPLY_DAY DESC, XO.MAN_NAME ASC) RNUM, XO.* "
                		+ " FROM ("
                		+ " SELECT 1 XX, MARRY_NO, ID,  APPLY_TYPE"
                		+ ", CASE WHEN APPLY_TYPE='1' THEN '카나혼인강좌' ELSE '약혼자주말' END AS APPLY_TYPE_TEXT "
                		+ ", CASE WHEN APPLY_TYPE='1' THEN CONVERT(varchar(10),  LECTURE_APPLY_DAY, 120)  ELSE LECTURE_APPLY_DAY2 END AS LECTURE_APPLY_DAY"
                		+ ", CONVERT(varchar(10),  MARRY_DAY, 120) MARRY_DAY,  MAN_NAME, MAN_BAPTISMAL_NAME, WOMAN_NAME, WOMAN_BAPTISMAL_NAME,  CONVERT(varchar(10),  APPLY_DAY, 120) APPLY_DAY"
                		+ ", PROCESS_STATUS"
                		+ ", (SELECT ISNULL(NAME,'미정의'+A.PROCESS_STATUS) FROM CODE_INSTANCE WHERE CODE='000010' AND CODE_INST=A.PROCESS_STATUS) AS PROCESS_STATUS_TEXT"
                		+ " FROM MARRY_APPLY A "
                		+ " WHERE 1=1 AND PROCESS_STATUS='1' "+whereStr
                		+ " UNION ALL "
                		+ " SELECT 2 XX, MARRY_NO, ID,  APPLY_TYPE"
                		+ ", CASE WHEN APPLY_TYPE='1' THEN '카나혼인강좌' ELSE '약혼자주말' END AS APPLY_TYPE_TEXT "
                		+ ", CASE WHEN APPLY_TYPE='1' THEN CONVERT(varchar(10),  LECTURE_APPLY_DAY, 120)  ELSE LECTURE_APPLY_DAY2 END AS LECTURE_APPLY_DAY"
                		+ ", CONVERT(varchar(10),  MARRY_DAY, 120) MARRY_DAY,  MAN_NAME, MAN_BAPTISMAL_NAME, WOMAN_NAME, WOMAN_BAPTISMAL_NAME,  CONVERT(varchar(10),  APPLY_DAY, 120) APPLY_DAY"
                		+ ", PROCESS_STATUS"
                		+ ", (SELECT ISNULL(NAME,'미정의'+A.PROCESS_STATUS) FROM CODE_INSTANCE WHERE CODE='000010' AND CODE_INST=A.PROCESS_STATUS) AS PROCESS_STATUS_TEXT"
                		+ " FROM MARRY_APPLY A "
                		+ " WHERE 1=1 AND PROCESS_STATUS!='1' "+whereStr
                		+ " ) XO "
                		+ " ) ROWS "
                		+ " WHERE RNUM BETWEEN "+startRnum+" AND "+endRnum;
                result = executeQueryList(query);
            } else {
                query = "SELECT * FROM ("
                		+ " SELECT ROW_NUMBER() OVER(ORDER BY LECTURE_APPLY_DAY DESC, APPLY_DAY DESC, MAN_NAME ASC) RNUM, MARRY_NO, ID,  APPLY_TYPE"
                		+ ", CASE WHEN apply_type = '1' THEN '카나혼인강좌' ELSE '약혼자주말' END AS APPLY_TYPE_TEXT"
                		+ ", CASE WHEN APPLY_TYPE = '1' THEN CONVERT(varchar(10),  LECTURE_APPLY_DAY, 120)  ELSE LECTURE_APPLY_DAY2 END AS LECTURE_APPLY_DAY"
                		+ ", CONVERT(varchar(10),  MARRY_DAY, 120) MARRY_DAY,  MAN_NAME, MAN_BAPTISMAL_NAME, WOMAN_NAME, WOMAN_BAPTISMAL_NAME"
                		+ ", CONVERT(char(10),  APPLY_DAY, 120) APPLY_DAY"
                		+ ", PROCESS_STATUS"
                		+ ", (SELECT ISNULL(NAME,'미정의'+A.PROCESS_STATUS) FROM CODE_INSTANCE WHERE CODE='000010' AND CODE_INST=A.PROCESS_STATUS) AS PROCESS_STATUS_TEXT"
//                		+ ", APPROVE_YN"
//                		+ ",  CASE WHEN APPROVE_YN = 'Y' THEN '승인'  ELSE\t'미승인' END AS APPROVE_YN_TEXT "
                		+ " FROM MARRY_APPLY A"
                		+ " WHERE ID='"+id+"' or email='"+id+"' "
                		+ ") ROWS "
                		+ " WHERE RNUM BETWEEN "+startRnum+" AND "+endRnum;
                result = executeQueryList(query);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        } finally {
        	free();
    	}
        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+result );
        return result;
    }

    /*
     * 프론트 > 나의정보 > 카나혼인~~ 내가 신청한 것 조회
     * (non-Javadoc)
     * @see kr.caincheon.church.dao.MarryDao#marryListCount(java.util.Map)
     */
	@Override
    public int marryListCount(Map _params)
    {
        int result;
        String whereStr="";
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        String id = pnull(_params.get("id"));
        String page_type = pnull(_params.get("page_type"));
        result = 0;
        String query = "";
        whereStr = "";
        try
        {
            if(page_type.equals("admin"))
            {
                String search_gubun1 = pnull(_params.get("searchGubun1"));
                String search_gubun2 = pnull(_params.get("searchGubun2"));
                String search_gubun3 = pnull(_params.get("searchGubun3"));
                String search_date = pnull(_params.get("searchDate"));
                String search_text = pnull(_params.get("searchText"));

                if(search_date != null && search_date.length() != 0)
                    if(search_gubun1.equals("1"))
                        whereStr = " AND lecture_apply_day between '"+search_date+"' AND DATEADD(dd, 1, '"+search_date+"') ";
                    else
                        whereStr = " AND marry_day between '"+search_date+"' AND DATEADD(dd, 1, '"+search_date+"') ";
                if(search_gubun2.length() > 0)
                    whereStr = String.valueOf(whereStr)+" AND apply_type = '"+search_gubun2+"' ";
                if(search_text != null && search_text.length() != 0)
                    if(search_gubun3.equals("1"))
                        whereStr = String.valueOf(whereStr)+" AND (man_name like '%"+search_text+"%' OR woman_name like '%"+search_text+"%') ";
                    else
                        whereStr = String.valueOf(whereStr)+" AND (man_baptismal_name like '%"+search_text+"%' OR woman_baptismal_name like '%"+search_text+"%') ";
                
                query = "SELECT COUNT(1) FROM  (SELECT ROW_NUMBER() OVER(ORDER BY LECTURE_APPLY_DAY DESC, APPLY_DAY DESC) RNUM, MARRY_NO, ID,  APPLY_TYPE "
                		+ " FROM MARRY_APPLY WHERE 1=1 "+whereStr+") ROWS  WHERE 1 = 1";
            } else {
                query = "SELECT COUNT(1) FROM  (SELECT ROW_NUMBER() OVER(ORDER BY LECTURE_APPLY_DAY DESC, APPLY_DAY DESC) RNUM, MARRY_NO, ID,  APPLY_TYPE "
                		+ " FROM MARRY_APPLY WHERE ID='"+id+"' or email='"+id+"') ROWS WHERE 1 = 1 ";
            }
            result = super.executeCount(query, false);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        } finally {
        	free();
    	}
        
        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+result );
        return result;
    }

	@Override
    public Map marryContents(Map _params)
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
        Map result = new HashMap();
        String id = pnull(_params.get("id"));
        String marry_no = pnull(_params.get("marry_no"));
        
        try {
            String where = " MARRY_NO = '"+marry_no+"'";
            String query = "SELECT "
            		+ " MARRY_NO, ID, APPLY_TYPE, CONVERT(CHAR(10), LECTURE_APPLY_DAY, 120) LECTURE_APPLY_DAY,  MAN_NAME, MAN_OTHER_REGION, MAN_TAREGION"
            		+ ", MAN_CHURCH_IDX, MAN_MEMBER_TYPE, MAN_BAPTISMAL_NAME, CONVERT(CHAR(10), MAN_BIRTHDAY, 120) MAN_BIRTHDAY, MAN_TEL"
            		+ ", WOMAN_NAME, WOMAN_OTHER_REGION, WOMAN_TAREGION"
            		+ ", WOMAN_CHURCH_IDX, WOMAN_MEMBER_TYPE, WOMAN_BAPTISMAL_NAME"
            		+ ", CONVERT(CHAR(10), WOMAN_BIRTHDAY, 120) WOMAN_BIRTHDAY, WOMAN_TEL"
            		+ ", EMAIL, POSTCODE, ADDR1, ADDR2, CONVERT(CHAR(10), MARRY_DAY, 120) MARRY_DAY"
            		+ ", MARRY_PLACE, APPLY_REASON, ADMIN_COMMENT, PROCESS_STATUS, LECTURE_APPLY_DAY2 "
            		+ " FROM MARRY_APPLY "
            		+ " WHERE "+where;
            result = super.executeQueryMap(query);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        } finally {
        	free();
    	}
        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+result );
        return result;
    }

    /*
     * front > 카나혼인강좌 신청 by 회원
     * (non-Javadoc)
     * @see kr.caincheon.church.dao.MarryDao#marryInsert(java.util.Map)
     */
    @Override
    public boolean marryInsert(Map _params)
    {
        boolean bReturn = true;
        PreparedStatement preparedStatement = null;
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        int i = 1;
        String query = "";
        try
        {
        	String isManChurch  = pcompare(_params, "man_member_type",   "3", "-", null);
        	String isGirlChurch = pcompare(_params, "woman_member_type", "3", "-", null);
        	
        	String id	                = pnull(_params, "id");
        	String apply_type           = pnull(_params, "apply_type");
        	String man_name	            = pnull(_params, "man_name");
        	String man_other_region     = pnull(_params, "man_other_region");
        	String man_church_idx       = pnull(_params, "man_church_idx");
        	String man_member_type      = pnull(_params, "man_member_type");
        	String man_baptismal_name   = pnull(_params, "man_baptismal_name");
        	String man_birthday         = pnull(_params, "man_birthday");
        	String man_tel	            = pnull(_params, "man_tel");
        	String woman_name           = pnull(_params, "woman_name");
        	String woman_other_region   = pnull(_params, "woman_other_region");
        	String woman_church_idx     = pnull(_params, "woman_church_idx");
        	String woman_member_type    = pnull(_params, "woman_member_type");
        	String woman_baptismal_name = pnull(_params, "woman_baptismal_name");
        	String woman_birthday       = pnull(_params, "woman_birthday");
        	String woman_tel	        = pnull(_params, "woman_tel");
        	String email	            = pnull(_params, "email");
        	String postcode	            = pnull(_params, "postcode");
        	String addr1	            = pnull(_params, "addr1");
        	String addr2	            = pnull(_params, "addr2");
        	String marry_day	        = pnull(_params, "marry_day");
        	String marry_place          = pnull(_params, "marry_place");
        	String apply_reason         = pnull(_params, "apply_reason");
        	String process_status       = pnull(_params, "process_status", "1");
        	String   man_taregion       = pnull(_params, "man_taregion");
        	String woman_taregion       = pnull(_params, "woman_taregion");
        	
            query = "INSERT INTO MARRY_APPLY "
            		+ " (id, apply_type, lecture_apply_day"
            		+ ", man_name, man_other_region, man_church_idx, man_member_type, man_baptismal_name, man_birthday, man_tel"
            		+ ", woman_name, woman_other_region, woman_church_idx, woman_member_type, woman_baptismal_name, woman_birthday, woman_tel"
            		+ ", email, postcode, addr1, addr2"
            		+ ", marry_day, marry_place, apply_reason, admin_comment"
            		+ ", apply_day, updateDT"
            		+ ", lecture_apply_day2, process_status"
            		+ ", man_taregion, woman_taregion"
            		+ ") VALUES ('"+id+"', '"+apply_type+"', ?"
            				+ ", ?, ?, ?, ?, ?, ?"
            				+ ", ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ''"
            				+ ", getdate(), getdate()"
            				+ ", ?, ?"
            				+ ", ?, ?"
            				+ ")";
            preparedStatement = getConnection().prepareStatement(query);
//            preparedStatement.setString(1, pnull(_params, "id"));
//            preparedStatement.setString(2, pnull(_params, "apply_type"));
            preparedStatement.setString(i++, pnull(_params, "lecture_apply_day", null));
            preparedStatement.setString(i++, man_name);
            preparedStatement.setString(i++, man_other_region);
            preparedStatement.setString(i++, man_church_idx);
            preparedStatement.setString(i++, man_member_type);
            preparedStatement.setString(i++, man_baptismal_name);
            preparedStatement.setString(i++, man_birthday);
            preparedStatement.setString(i++, man_tel);
            preparedStatement.setString(i++, woman_name);
            preparedStatement.setString(i++, woman_other_region);
            preparedStatement.setString(i++, woman_church_idx);
            preparedStatement.setString(i++, woman_member_type);
            preparedStatement.setString(i++, woman_baptismal_name);
            preparedStatement.setString(i++, woman_birthday);
            preparedStatement.setString(i++, woman_tel);
            preparedStatement.setString(i++, email);
            preparedStatement.setString(i++, postcode);
            preparedStatement.setString(i++, addr1);
            preparedStatement.setString(i++, addr2);
            preparedStatement.setString(i++, marry_day);
            preparedStatement.setString(i++, marry_place);
            preparedStatement.setString(i++, apply_reason);
            preparedStatement.setString(i++, pnull(_params, "lecture_apply_day2", null));
            preparedStatement.setString(i++, process_status);
            preparedStatement.setString(i++, man_taregion);
            preparedStatement.setString(i++, woman_taregion);
            i = -1;
            i = preparedStatement.executeUpdate();
            if(i == 0)
                bReturn = false;
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
            bReturn = false;
        } finally {
        	if(preparedStatement != null) try { preparedStatement.close(); } catch ( Exception e ) { e.printStackTrace(); }
        	free();
    	}
        D(_logger, Thread.currentThread().getStackTrace(), "Query Result : "+ (bReturn && i>0) + " -> " + bReturn +", i=" + i );
        return bReturn && i > 0;
    }

    /*
     * 관리자 > 카나혼인강좌 신청 by 관리자
     * (non-Javadoc)
     * @see kr.caincheon.church.dao.MarryDao#marryModify(java.util.Map)
     */
    @Override
    public boolean marryModify(Map _params)
    {
        boolean bReturn = true;
        
        PreparedStatement preparedStatement = null;
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );

        String marry_no             = pnull(_params, "marry_no");
    	String id	                = pnull(_params, "id");
    	String apply_type           = pnull(_params, "apply_type");
    	String man_name	            = pnull(_params, "man_name");
    	String man_other_region     = pnull(_params, "man_other_region");
    	String man_church_idx       = pnull(_params, "man_church_idx");
    	String man_member_type      = pnull(_params, "man_member_type");
    	String man_baptismal_name   = pnull(_params, "man_baptismal_name");
    	String man_birthday         = pnull(_params, "man_birthday");
    	String man_tel	            = pnull(_params, "man_tel");
    	String woman_name           = pnull(_params, "woman_name");
    	String woman_other_region   = pnull(_params, "woman_other_region");
    	String woman_church_idx     = pnull(_params, "woman_church_idx");
    	String woman_member_type    = pnull(_params, "woman_member_type");
    	String woman_baptismal_name = pnull(_params, "woman_baptismal_name");
    	String woman_birthday       = pnull(_params, "woman_birthday");
    	String woman_tel	        = pnull(_params, "woman_tel");
    	String email	            = pnull(_params, "email");
    	String postcode	            = pnull(_params, "postcode");
    	String addr1	            = pnull(_params, "addr1");
    	String addr2	            = pnull(_params, "addr2");
    	String marry_day	        = pnull(_params, "marry_day");
    	String marry_place          = pnull(_params, "marry_place");
    	String apply_reason         = pnull(_params, "apply_reason");
    	String process_status       = pnull(_params, "process_status", "1");
    	String   man_taregion       = pnull(_params, "man_taregion");
    	String woman_taregion       = pnull(_params, "woman_taregion");
    	
        String query = "";
        int i = 1;
        try
        {
            query = "UPDATE MARRY_APPLY "
            		+ " SET PROCESS_STATUS=?, APPLY_TYPE=?, lecture_apply_day=?"
            		+ ", man_name=?, man_other_region=?, man_church_idx=?,man_member_type=?,man_baptismal_name=?,man_birthday=?,man_tel=?"
            		+ ", woman_name=?, woman_other_region=?, woman_church_idx=?, woman_member_type=?, woman_baptismal_name=?, woman_birthday=?, woman_tel=?"
            		+ ", email=?, postcode=?, addr1=?, addr2=?, marry_day=?, marry_place=?, apply_reason=?,  lecture_apply_day2=?"
            		+ ", man_taregion=?, woman_taregion=?"
            		+ ", updateDT=getdate() "
            		+ " WHERE marry_no = ? and id= ?";
            preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(i++, process_status);
            preparedStatement.setString(i++, apply_type);
            preparedStatement.setString(i++, pnull(_params, "lecture_apply_day", null));
            preparedStatement.setString(i++, man_name);
            preparedStatement.setString(i++, man_other_region);
            preparedStatement.setString(i++, man_church_idx);
            preparedStatement.setString(i++, man_member_type);
            preparedStatement.setString(i++, man_baptismal_name);
            preparedStatement.setString(i++, man_birthday);
            preparedStatement.setString(i++, man_tel);
            preparedStatement.setString(i++, woman_name);
            preparedStatement.setString(i++, woman_other_region);
            preparedStatement.setString(i++, woman_church_idx);
            preparedStatement.setString(i++, woman_member_type);
            preparedStatement.setString(i++, woman_baptismal_name);
            preparedStatement.setString(i++, woman_birthday);
            preparedStatement.setString(i++, woman_tel);
            preparedStatement.setString(i++, email);
            preparedStatement.setString(i++, postcode);
            preparedStatement.setString(i++, addr1);
            preparedStatement.setString(i++, addr2);
            preparedStatement.setString(i++, marry_day);
            preparedStatement.setString(i++, marry_place);
            preparedStatement.setString(i++, apply_reason);
            preparedStatement.setString(i++, pnull(_params, "lecture_apply_day2", null));
            preparedStatement.setString(i++, man_taregion);
            preparedStatement.setString(i++, woman_taregion);
            preparedStatement.setString(i++, marry_no);
            preparedStatement.setString(i++, id);
            i = -1;
            i = preparedStatement.executeUpdate();
            if(i == 0)
                bReturn = false;
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
            bReturn = false;
        } finally {
        	if(preparedStatement != null) 
        		try { preparedStatement.close(); } catch ( Exception e ) { e.printStackTrace(); }
        	free();
    	}
        
        return bReturn && i > 0;
    }

    @Override
    public boolean marryDelete(Map _params)
    {
        boolean bReturn = true;
        String marry_no = pnull(_params.get("marry_no"));
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        int i = 0;
        
        String query = "";
        try {
        	query = "delete from MARRY_APPLY where marry_no=" + marry_no;
            i = executeUpdate(query);
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        	free();
    	}
        
        return bReturn && i > 0;
    }

    @Override
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

    /**
     * 카나혼인강좌 스케쥴 조회
     * (non-Javadoc)
     * @see kr.caincheon.church.dao.MarryDao#marryLectureList(java.util.Map)
     */
    @Override
    public List marryLectureList(Map _params)
    {
        List result = null;
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        String query = "";
        try {
            query = "SELECT CONVERT(VARCHAR(10), CONVERT(DATETIME, EDU_DATE_START), 120) AS ekey "
            		+ " FROM MARRY_GUIDE "
            		+ " WHERE TYPE='1'/*카나혼인강좌*/ AND CLOSE_YN='N' AND DEL_YN='N' AND EDU_DATE_START > CONVERT(CHAR(10), GETDATE(), 112) "
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

    /**
     * 약혼자 주말강좌 스케쥴 조회
     * (non-Javadoc)
     * @see kr.caincheon.church.dao.MarryDao#marryLectureList2(java.util.Map)
     */
    @Override
    public List marryLectureList2(Map _params)
    {
        List result=null;
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        String query = "";
        
        try {
//            query = "SELECT * FROM ( "
//            		+ "       SELECT CONVERT(varchar(10),FDATE1,120)+'~'+ CONVERT(varchar(10),TDATE1,120) AS EKEY "
//            		+ " FROM ENGAGE_GUIDE "
//            		+ " UNION SELECT CONVERT(varchar(10),FDATE2,120)+'~'+ CONVERT(varchar(10),TDATE2,120) AS EKEY "
//            		+ " FROM ENGAGE_GUIDE "
//            		+ " UNION SELECT CONVERT(varchar(10),FDATE3,120)+'~'+ CONVERT(varchar(10),TDATE3,120) AS EKEY "
//            		+ " FROM ENGAGE_GUIDE "
//            		+ " UNION SELECT CONVERT(varchar(10),FDATE4,120)+'~'+ CONVERT(varchar(10),TDATE4,120) AS EKEY "
//            		+ " FROM ENGAGE_GUIDE "
//            		+ " UNION SELECT CONVERT(varchar(10),FDATE5,120)+'~'+ CONVERT(varchar(10),TDATE5,120) AS EKEY "
//            		+ " FROM ENGAGE_GUIDE "
//            		+ " UNION SELECT CONVERT(varchar(10),FDATE6,120)+'~'+ CONVERT(varchar(10),TDATE6,120) AS EKEY "
//            		+ " FROM ENGAGE_GUIDE "
//            		+ " ) Z WHERE Z.EKEY IS NOT NULL"
//            		+ " ORDER BY 1 ";

            query = "SELECT A.MARRY_GUIDE_NO, A.EXPLAIN, A.EDU_GUIDE "
            		+ ", CONVERT(VARCHAR(10), CONVERT(DATETIME, A.EDU_DATE_START), 120) +'~'+ CONVERT(VARCHAR(10), CONVERT(DATETIME, A.EDU_DATE_START)+3, 120) AS EKEY "
            		+ " FROM MARRY_GUIDE A "
            		+ " WHERE TYPE='2'/*주말약혼자강좌*/ AND CLOSE_YN='N' AND DEL_YN='N' AND EDU_DATE_START > CONVERT(CHAR(10), GETDATE(), 112) "
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
    public Map marryGuideContents(Map _params)
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
        Map result = null;
        String id = pnull(_params.get("id"));
        String query = "";
        try {
            query = "SELECT TOP 1 M.* "
            		+ " FROM MARRY_GUIDE M  WHERE M.ID='"+id+"' ";
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
    public List marryDList(Map _params)
    {
    	String id   = pnull(_params.get("id"));
        String type = pnull(_params.get("type"), "1"); // 카나혼인 1, 약혼자주말 2
        List result=null;
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        String query = "";
        
        try {
            query  = "SELECT ROW_NUMBER() OVER(ORDER BY D.LECTURE_DATE) RNUM"
            		+ ", CONVERT(VARCHAR(10), D.LECTURE_DATE, 120) LECTURE_DATE "
            		+ " FROM MARRY_GUIDE M "
            		//+ " LEFT OUTER JOIN MARRY_GUIDE_SCHEDULE_DATE D ON M.MARRY_GUIDE_NO = D.MARRY_GUIDE_NO "
            		//+ " WHERE M.ID='"+id+"' "
            		;
            query  = "SELECT /* 카나혼인 1, 약혼자주말 2 */ ROW_NUMBER() OVER(ORDER BY EDU_DATE_START) RNUM "
            		+ ", CONVERT(VARCHAR(10), CONVERT(DATETIME, EDU_DATE_START), 120) LECTURE_DATE "
            		+ " FROM MARRY_GUIDE "
            		+ " WHERE DEL_YN='N' AND CLOSE_YN='N' AND EDU_DATE_START >= CONVERT(VARCHAR(10), GETDATE(), 112) AND TYPE='"+type+"' "
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

    /**
     * [카나혼인강좌/주말혼인강좌] 시간 일정 조회
     * (non-Javadoc)
     * @see kr.caincheon.church.dao.MarryDao#marryTList(java.util.Map)
     */
    @Override
    public List marryTList(Map _params)
    {
        String id;
        List result=null;
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        id = pnull(_params.get("id"));
        result = null;
        String query = "";
        try
        {
        	// 일자별로 스케쥴을 관리하지 않는다. 만약, 일자별로 관리를 한다면, 아래를 복구시켜야 함.
            query = "SELECT ROW_NUMBER() OVER(ORDER BY T.marry_guide_sno) RNUM"
            		+ ", T.MARRY_GUIDE_SNO AS TIME_SNO, T.LECTURE_TIME, T.CONTENTS  "
            		+ " FROM MARRY_GUIDE M "
            		+ " LEFT OUTER JOIN MARRY_GUIDE_SCHEDULE_TIME T ON M.MARRY_GUIDE_NO = T.MARRY_GUIDE_NO "
            		//+ " where M.ID='"+id+"' "
            		;
            query = "SELECT ROW_NUMBER() OVER(ORDER BY T.MARRY_GUIDE_NO, T.LECTURE_TIME) RNUM"
            		+ ", T.MARRY_GUIDE_SNO AS TIME_SNO, T.LECTURE_TIME, T.CONTENTS  "
            		+ " FROM MARRY_GUIDE_SCHEDULE_TIME T "
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

    /**
     * 카나혼인강좌(TYPE=1, Cana) 및 약혼자주말강좌(TYPE=2, Fiance) 데이터 수정
     */
    @Override
    public boolean marryGModify(Map _params)
        throws CommonException
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
        int rn = 0, rn1=0, rn2=0, rn3=0;
        boolean bReturn = true;
        String query="";
        
        String id  = pnull(_params.get("id"), "admin");
        String eduDateStart = pnull(_params.get("eduDateStart"), null);
        String eduDateEnd   = pnull(_params.get("eduDateEnd"), null);
        String recruitmentPersonnel = pnull(_params.get("recruitmentPersonnel"),"0");
        String delYn   = pnull(_params.get("delYn"), "N");
        String closeYn = pnull(_params.get("closeYn"), "N");
        
        String gno = pnull(_params.get("gno"));
        String type = pnull(_params.get("type"));
        
        try {
            query = "UPDATE MARRY_GUIDE SET "
            		+ " explain=?, edu_guide=?"
            		+ ", updateDT = getdate() "
            		+ ( eduDateStart.length()==0 ? "":", EDU_DATE_START=''")
            		+ ( eduDateEnd.length()==0 ? "":", EDU_DATE_END=''")
            		+ ( recruitmentPersonnel.length()==0 ? "":", RECRUITMENT_PERSONNEL="+recruitmentPersonnel)
            		+ ( delYn.length()  ==0 ? "":", DEL_YN="+delYn)
            		+ ( closeYn.length()==0 ? "":", CLOSE_YN="+closeYn)
            		+ " WHERE marry_guide_no="+gno+" and TYPE='"+type+"'"
            		;
            LinkedHashMap lmap = getLinkedHashMap(
            		  pnull(_params.get("explain"), " ")
            		, pnull(_params.get("edu_guide"), " ")
            		);
            rn1 = executeUpdatePreparedStatement(query, lmap);
            if(rn1 == 0) {
            	query = "INSERT INTO MARRY_GUIDE (marry_guide_no, id"
            			+ ", TYPE,EDU_DATE_START,EDU_DATE_END,RECRUITMENT_PERSONNEL,CLOSE_YN,DEL_YN"
            			+ ", explain, edu_guide, updateDT, regdateDT) VALUES "
            			+ "( (SELECT ISNULL(MAX(MARRY_GUIDE_NO),0)+1 FROM MARRY_GUIDE), '"+id+"'"
            			+ ", '"+type+"', '"+eduDateStart+"', '"+eduDateEnd+"', '"+recruitmentPersonnel+"', '"+closeYn+"', '"+delYn+"'"
            			+ ", ?, ?, null, getdate() )";
            	rn1 = executeUpdatePreparedStatement(query, lmap);
            }
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
            bReturn = false;
    	}
        
        /*
        CallableStatement cstmt = null;
        int dateCnt = Integer.parseInt(pnull(_params.get("dateCnt"), "0"));
        if(dateCnt > 0) {
            try
            {
                query = "{call pcMarryGuideDate(?,?,?,?,?)}";
                cstmt = getConnection().prepareCall(query);
                for(int i = 0; i < dateCnt; i++)
                {
                    cstmt.setString(1, gno);
                    cstmt.setString(2, pnull(_params.get("id")));
                    cstmt.setInt(3, i + 1);
                    cstmt.setString(4, pnull(_params.get((new StringBuilder("lecture_date")).append(i + 1).toString())));
                    cstmt.registerOutParameter(5, 4);
                    cstmt.execute();
                    rn2 += cstmt.getInt(5);
                }

            } catch(Exception e) {
            	E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
            	bReturn = false;
                throw new CommonException(e.getMessage(), "ERR-D013", e);
            }
        }
        
        int timeCnt = ipnull(_params, "timeCnt", 0);
        if(timeCnt > 0) {
            try
            {
                query = "{call pcMarryGuideTime(?,?,?,?,?,?)}";
                cstmt = getConnection().prepareCall(query);
                for(int i = 0; i < timeCnt; i++)
                {
                    cstmt.setString(1, gno);
                    cstmt.setString(2, pnull(_params.get("id")));
                    cstmt.setInt(3, i + 1);
                    cstmt.setString(4, pnull(_params.get((new StringBuilder("lecture_time")).append(i + 1).toString())));
                    cstmt.setString(5, pnull(_params.get((new StringBuilder("lecture_contents")).append(i + 1).toString())));
                    cstmt.registerOutParameter(6, 4);
                    cstmt.execute();
                    rn3 += cstmt.getInt(6);
                }

            } catch(Exception e) {
            	E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
            	bReturn = false;
                throw new CommonException(e.getMessage(), "ERR-D013", e);
            }
        }*/
        rn = rn1 + rn2 + rn3;
        return rn >= 1 && bReturn;
    }

//    @Override
//    public boolean marryGDateDelete(Map _params) throws CommonException
//    {
//    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
//        int rn=0;
//        String gno = pnull(_params.get("gno"));
//        String id = pnull(_params.get("id"));
//        String query = "";
//        
//        try {
//            query = "DELETE FROM MARRY_GUIDE_SCHEDULE_DATE  WHERE marry_guide_no=" + gno;
//            rn = executeUpdate(query);
//        } catch(Exception e) {
//        	E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
//        } finally {
//        	free();
//    	}
//        return rn >= 0;
//    }

    /**
     * 카나혼 강좌 일일 시간표 수정
     * (non-Javadoc)
     * @see kr.caincheon.church.dao.MarryDao#marryGTimeUpsert(java.util.Map)
     */
    @Override
    public boolean marryGTimeUpsert(Map _params) throws CommonException
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
        int rn=0;
        String id      = pnull(_params.get("id"));
        int    timeCnt = ipnull(_params, "timeCnt", 0);
        String lecTime = "lecture_time", lecContnt="lecture_contents", lecSno="time_sno";
        String query="", queryI = "", queryU="", queryC="";
        
        try {
        	queryC = "SELECT COUNT(*) FROM MARRY_GUIDE_SCHEDULE_TIME WHERE MARRY_GUIDE_SNO=";
        	queryU = "UPDATE MARRY_GUIDE_SCHEDULE_TIME SET LECTURE_TIME=?, CONTENTS=?, UPDATEDT=GETDATE() WHERE MARRY_GUIDE_SNO=?";
        	queryI = "insert into MARRY_GUIDE_SCHEDULE_TIME ("
        			+ "MARRY_GUIDE_NO, id, LECTURE_TIME, CONTENTS, UPDATEDT, MARRY_GUIDE_SNO"
        			+ ") VALUES ("
        			//+ "(SELECT ISNULL(MAX(), 0)+1 FROM "+TBL+")"
        			+ "1, '"+id+"', ?, ?, GETDATE(), ?"
        			+ ")";
            
        	PreparedStatement _pStmtI = getPreparedStatement(queryI);
        	PreparedStatement _pStmtU = getPreparedStatement(queryU);
        	LinkedHashMap lmap = null;
        	for(int i=1 ; i <= timeCnt; i++) {
        		query = queryI;
        		lmap = getLinkedHashMap( pnull(_params, lecTime+i), pnull(_params, lecContnt+i), pnull(_params, lecSno+i) );
            	if(executeCount(queryC+pnull(_params, lecSno+i), false) == 1) {
            		query = queryU;
            		rn += executeUpdatePreparedStatement(_pStmtU, lmap, false);
            	} else {
            		query = queryI;
            		rn += executeUpdatePreparedStatement(_pStmtI, lmap, false);
            	}
            	lmap.clear();
            }
        	
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        	free();
    	}
        return rn > 0;
    }
    
    @Override
    public boolean marryGTimeDelete(Map _params)
        throws CommonException
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
        int rn=0;
        String id = pnull(_params.get("id"));
        String gno = pnull(_params.get("gno"));
        String query = "";
        
        try {
            query = "DELETE FROM MARRY_GUIDE_SCHEDULE_TIME  WHERE marry_guide_no=" + gno;
            rn = executeUpdate(query);
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        	free();
    	}
        return rn > 0;
    }

    @Override
    public List marryExcelList(Map _params)
    {
        List result=null;
        String whereStr="";
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        String id = pnull(_params.get("id"));
        String page_type = pnull(_params.get("page_type"));
        String query = "";
        try
        {
            String search_gubun1 = pnull(_params.get("searchGubun1"));
            String search_gubun2 = pnull(_params.get("searchGubun2"));
            String search_gubun3 = pnull(_params.get("searchGubun3"));
            String search_date = pnull(_params.get("searchDate"));
            String search_text = pnull(_params.get("searchText"));

            if(search_date.length() != 0) {
                if(search_gubun1.equals("1"))
                    whereStr = " AND lecture_apply_day between '"+search_date+"' AND DATEADD(dd, 1, '"+search_date+"') ";
                else
                    whereStr = " AND marry_day between '"+search_date+"' AND DATEADD(dd, 1, '"+search_date+"') ";
            }
            
            if(search_gubun2.length() > 0)
                whereStr += " AND apply_type = '"+search_gubun2+"' ";
            
            if(search_text.length() != 0) {
                if(search_gubun3.equals("1"))
                    whereStr = String.valueOf(whereStr)+" AND (man_name like '%"+search_text+"%' OR woman_name like '%"+search_text+"%') ";
                else
                    whereStr = String.valueOf(whereStr)+" AND (man_baptismal_name like '%"+search_text+"%' OR woman_baptismal_name like '%"+search_text+"%') ";
            }
            query = "SELECT * FROM ("
            		+ " SELECT ROW_NUMBER() OVER(ORDER BY APPLY_DAY DESC) RNUM"
            		+ ", MARRY_NO, ID,  APPLY_TYPE"
            		+ ", CASE WHEN APPLY_TYPE='1' THEN '카나혼인강좌' ELSE '약혼자주말' END AS APPLY_TYPE_TEXT "
            		+ ", CONVERT(varchar(10),  LECTURE_APPLY_DAY, 120) LECTURE_APPLY_DAY"
            		+ ", CONVERT(varchar(10),  MARRY_DAY, 120) MARRY_DAY"
            		+ ", MAN_NAME, MAN_BAPTISMAL_NAME, MAN_CHURCH_IDX, MAN_BIRTHDAY"
            		+ ", WOMAN_NAME, WOMAN_BAPTISMAL_NAME, WOMAN_CHURCH_IDX, WOMAN_BIRTHDAY"
            		+ ", CONVERT(varchar(10),  APPLY_DAY, 120) APPLY_DAY"
            		+ ", PROCESS_STATUS"
            		+ ", (SELECT ISNULL(NAME,'미정의'+A.PROCESS_STATUS) FROM CODE_INSTANCE WHERE CODE='000010' AND CODE_INST=A.PROCESS_STATUS) AS PROCESS_STATUS_TEXT"
            		+ ", (SELECT NAME FROM CHURCH C WHERE C.CHURCH_IDX=A.MAN_CHURCH_IDX) AS MAN_CHURCH_NM"
            		+ ", (SELECT NAME FROM CHURCH C WHERE C.CHURCH_IDX=A.WOMAN_CHURCH_IDX) AS WOMAN_CHURCH_NM"
            		+ " FROM MARRY_APPLY A "
            		+ " WHERE 1=1 "+whereStr
            		+ " ) ROWS";
            
            result = super.executeQueryList(query);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        } finally {
        	free();
    	}
        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+result );
        return result;
    }

    
}
