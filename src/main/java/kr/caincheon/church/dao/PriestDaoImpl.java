// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PriestDaoImpl.java

package kr.caincheon.church.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import kr.caincheon.church.common.base.CommonDao;
import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.utils.ImageUtils;
import kr.caincheon.church.common.utils.UtilInt;

// Referenced classes of package kr.caincheon.church.dao:
//            PriestDao

@Repository("priestDao")
public class PriestDaoImpl extends CommonDao
    implements PriestDao
{

    private final Logger _logger = Logger.getLogger(getClass());
    
    /*
     * 프론트에서 사제 목록 조회 
     * (non-Javadoc)
     * @see kr.caincheon.church.dao.PriestDao#priestList(kr.caincheon.church.common.CommonDaoDTO, java.util.Map)
     */
	@Override
    public void priestList(CommonDaoDTO dto, Map _params) throws Exception
    {
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        String query = "", query0 = "";
        int pageNo    = ipnull(_params, "pageNo", 1);
        int pageSize  = ipnull(_params, "pageSize", 20);
        int startRnum = (pageNo - 1) * pageSize + 1;
        int endRnum   = pageNo * pageSize;
        
        String sortGubun    = pnull(_params, "sortGubun");
        String schTextGubun = pnull(_params, "schTextGubun");
        String schText      = pnull(_params.get("schText"));
        String strWhere     = "";
        
        if(schText.length() > 0) {
//        	if("p.org_idx".equalsIgnoreCase(schTextGubun))
//        		strWhere = " AND "+schTextGubun+" IN (SELECT ORG_IDX FROM ORG_HIERARCHY WHERE NAME LIKE '%"+schText+"%') ";
//        	else 
        		strWhere = " AND "+schTextGubun+" LIKE '%"+schText+"%' ";
        }

        // 정렬조건
        if(sortGubun.length()>0) {
        	if(sortGubun.equalsIgnoreCase("org_idx"))
        		sortGubun = "X." + sortGubun + " ASC, P.ONUM ASC";
        	else if(sortGubun.equalsIgnoreCase("onum"))
        		sortGubun = "P.ONUM ASC";
        	else
        		sortGubun = "P." + sortGubun + " ASC, P.ONUM ASC";
        } else {
        	sortGubun = "P.ONUM ASC";
        }
        
        // checkbox 검색조건
        String sosok_divA   = pnull(_params, "sosok_divA");
        String sosok_div1   = pnull(_params, "sosok_div1");
        String sosok_div2   = pnull(_params, "sosok_div2");
        String sosok_div3   = pnull(_params, "sosok_div3");
        String appellationA = pnull(_params, "appellationA");
        String appellation1 = pnull(_params, "appellation1");
        String appellation3 = pnull(_params, "appellation3");
        String appellation4 = pnull(_params, "appellation4");
        String org_lv1A     = pnull(_params, "org_lv1A");
        String org_lv11     = pnull(_params, "org_lv11");
        String org_lv12     = pnull(_params, "org_lv12");

        String sosok_divSQL = "";
        if(sosok_divA.length()>0 || sosok_div1.length()>0 || sosok_div2.length()>0 || sosok_div3.length()>0) {
        	if(sosok_divA.length() > 0) {
        		sosok_divSQL = sosok_divA.replaceAll(",", "','");
        	} else {
        		if(sosok_div1.length() > 0) sosok_divSQL = sosok_div1;
        		if(sosok_div2.length() > 0) sosok_divSQL = (sosok_divSQL.length()==0?"":sosok_divSQL+"','")+sosok_div2;
        		if(sosok_div3.length() > 0) sosok_divSQL = (sosok_divSQL.length()==0?"":sosok_divSQL+"','")+sosok_div3;
        	}
        	sosok_divSQL = " AND P.GUBUN IN ('" +sosok_divSQL+ "')";
        }
        
        String appellationSQL = "";
        if(appellationA.length()>0 || appellation1.length()>0 || appellation3.length()>0 || appellation4.length()>0) {
        	if(appellationA.length() > 0) {
        		appellationSQL = appellationA.replaceAll(",", "','");
        	} else {
        		if(appellation1.length() > 0) appellationSQL = appellation1;
        		if(appellation3.length() > 0) appellationSQL = (appellationSQL.length()==0?"":appellationSQL+"','")+appellation3;
        		if(appellation4.length() > 0) appellationSQL = (appellationSQL.length()==0?"":appellationSQL+"','")+appellation4;
        	}
        	appellationSQL = " AND P.APPELLATION IN ('" +appellationSQL+ "')";
        }
        
        String org_lvSQL = "";
        if(org_lv1A.length()>0 || org_lv11.length()>0 || org_lv12.length()>0) {
        	if(org_lv1A.length() > 0) {
        		org_lvSQL = org_lv1A.replaceAll(",", "','");
        	} else {
        		if(org_lv11.length() > 0) org_lvSQL = org_lv11;
        		if(org_lv12.length() > 0) org_lvSQL = (org_lvSQL.length()==0?"":org_lvSQL+"','")+org_lv12;
        	}
        	org_lvSQL = " AND O.LV1 IN ('" +org_lvSQL+ "')";
        }
        
        // query
        try {
            // list
            query0 = "SELECT A.*, ISNULL(O.NAME,'') ORG_NAME FROM ( "
            		+ "SELECT ROW_NUMBER() OVER(ORDER BY "+sortGubun+", P.P_IDX ASC ) RNUM "
            		+ ", P.ONUM, P.P_IDX"
            		+ ", P.GUBUN, P.NAME, P.CHRISTIAN_NAME"
            		+ ", CONVERT(CHAR(10), P.P_BIRTHDAY,120) AS P_BIRTHDAY "
            		+ ", P.NEW_BIRTHDAY"
            		+ ", X.ORG_IDX"
            		+ ", P.APPELLATION "
            		+ " FROM PRIEST P "
            		+ " JOIN (SELECT DISTINCT P_IDX, ORG_IDX FROM ORG_DEPARTMENT_PRIEST_REL "
    		                 + " WHERE REPLACE(CONVERT(CHAR(10), GETDATE(), 120),'-','') BETWEEN APPOINTMENT_START_DATE AND APPOINTMENT_END_DATE "
    		                 + " AND MAIN_ASSIGN_YN='Y' "
    		                 + ") X ON X.P_IDX=P.P_IDX "
    		        + " LEFT OUTER JOIN ORG_HIERARCHY O ON O.ORG_IDX=X.ORG_IDX "
            		+ " WHERE 1=1 " + strWhere  + sosok_divSQL + appellationSQL + org_lvSQL 
            		+ " ) A" 
            		+ " LEFT OUTER JOIN ORG_HIERARCHY O ON O.ORG_IDX = A.ORG_IDX "
            		;

            // total count
            query = "SELECT COUNT(1) FROM ("+query0+") X ";
            dto.daoTotalCount = super.executeCount(query, false);
            
            // list
            query = query0 + " WHERE A.RNUM BETWEEN "+startRnum+" AND "+endRnum
            		+ " ORDER BY RNUM ASC";
            dto.daoList = super.executeQueryList(query);

        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        }  finally {
            free();
        }
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Result dto:"+dto);
    }
//
//    private int priestListCount(Map _params) throws Exception
//    {
//        String strWhere;
//        int result;
//        
//        
//        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
//        String query = "";
//        String schTextGubun = pnull(_params.get("schTextGubun"));
//        String schText = pnull(_params.get("schText"));
//        strWhere = "";
//        if(schText.length() > 0)
//            strWhere = (new StringBuilder(" AND ")).append(schTextGubun).append(" LIKE '%").append(schText).append("%' ").toString();
//        result = 0;
//        
//        try
//        {
//            query = (new StringBuilder("SELECT COUNT(1)   FROM priest  WHERE gubun IN ('1','2','3') ")).append(strWhere).toString();
//            result = super.executeCount(query, false);
//        } catch(Exception e) {
//        	E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
//        } finally {
//        	
//            free();
//        }
//        return result;
//    }

	/*
	 * 프론트에서 사제 상세보기 OP
	 */
	@Override
    public void priestContents(CommonDaoDTO dto, Map _params) throws Exception
    {        
        String query = "", p_idx = pnull(_params, "p_idx");
        try
        {
        	// 사제정보
            query = "SELECT A.P_IDX "
            		+ ", ISNULL(A.NAME,'') AS NAME "
            		+ ", ISNULL(A.CHRISTIAN_NAME,'') AS CHRISTIAN_NAME"
            		//+ ", CASE WHEN A.GUBUN='1' THEN '\uAD50\uAD6C' WHEN A.GUBUN='2' THEN '\uC218\uB3C4\uD68C' WHEN A.GUBUN='3' THEN '\uD0C0\uAD50\uAD6C' ELSE ISNULL(A.GUBUN,'\uAD50\uAD6C') END AS GUBUN"
            		+ ", ISNULL(C1.NAME, '') AS GUBUN"
            		+ ", ISNULL(CONVERT(CHAR(10), A.NEW_BIRTHDAY, 120),'') AS NEW_BIRTHDAY"
            		+ ", ISNULL(CONVERT(CHAR(10), A.P_BIRTHDAY, 120),'') AS P_BIRTHDAY"
            		+ ", ISNULL(A.TEL,'') AS TEL"
            		+ ", ISNULL(A.EMAIL,'') AS EMAIL"
//            		+ ", ISNULL(A.S_GUBUN,'') AS S_GUBUN"
//            		+ ", ISNULL(A.J_GUBUN,'') AS J_GUBUN"
//            		+ ", ISNULL(A.J_NAME,'') AS J_NAME"
//            		+ ", ISNULL(A.P_CODE,'') AS P_CODE"
//            		+ ", ISNULL(A.P_NAME,'') AS P_NAME"
            		+ ", ISNULL(C2.NAME,'') AS APPELLATION_NAME "
            		+ ", ISNULL(A.ETC,'') AS ETC"
            		+ ", ISNULL(A.IMAGE,'') AS IMAGE /*포토*/ "
            		+ ", ISNULL(A.IMAGE2,'') AS IMAGE2 /*상본*/ "
            		+ ", ISNULL(A.IMAGE3,'') AS IMAGE3 /*at main page*/ "
            		+ ", ISNULL(A.HOMEPAGE,'') AS HOMEPAGE"
            		+ ", ISNULL(A.PHRASE,'') AS PHRASE"
            		+ ", ONUM "
            		+ ", APPELLATION "
            		+ ", (SELECT TOP 1 P_IDX FROM PRIEST WHERE ONUM=(SELECT MAX(ONUM) FROM PRIEST X WHERE X.ONUM < A.ONUM)) AS BEFORE_P_IDX"
            		+ ", (SELECT TOP 1 P_IDX FROM PRIEST WHERE ONUM=(SELECT MIN(ONUM) FROM PRIEST X WHERE X.ONUM > A.ONUM)) AS NEXT_P_IDX "
            		+ " FROM PRIEST A "
                    + " LEFT OUTER JOIN CODE_INSTANCE C1 on C1.CODE_INST=A.GUBUN AND C1.CODE='000001' "
                    + " LEFT OUTER JOIN CODE_INSTANCE C2 on C2.CODE_INST=A.APPELLATION AND C2.CODE='000002' "
            		+ " WHERE A.P_IDX = '"+p_idx+"'";
            dto.daoDetailContent = super.executeQueryMap(query);
            
            //사제임지정보
            dto.daoList = executeQueryList("SELECT R.*"
            		+ ", (SELECT NAME FROM ORG_HIERARCHY WHERE ORG_IDX=R.ORG_IDX) ORG_NAME "
            		+ ", (SELECT NAME FROM CODE_INSTANCE WHERE CODE='000003' AND CODE_INST = R.P_POSITION) POSITION_NAME "
            		+ " FROM ORG_DEPARTMENT_PRIEST_REL R "
            		+ " WHERE P_IDX="+p_idx 
            		+ " AND REPLACE(CONVERT(CHAR(10), getdate(), 120),'-','') BETWEEN APPOINTMENT_START_DATE AND APPOINTMENT_END_DATE " );
            
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        	
            free();
        }
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Result dto:"+dto);
    }

	/*
	 * front side 선종사제 목록 조회
	 * (non-Javadoc)
	 * @see kr.caincheon.church.dao.PriestDao#holyList(kr.caincheon.church.common.CommonDaoDTO, java.util.Map)
	 */
	@Override
    public void holyList(CommonDaoDTO rtDTO, Map _params) throws Exception
    {
        String strWhere;
        String query = "";
        int pageNo = ipnull(_params, "pageNo", 1);
        int pageSize  = ipnull(_params, "pageSize", 8);
        int startRnum = (pageNo - 1) * pageSize + 1;
        int endRnum = pageNo * pageSize;
        strWhere = "";
        String searchGubun = pnull(_params.get("searchGubun"));
        String month = pnull(_params.get("month"));
        
        if(searchGubun.equals("month") && month.length() > 0)
            strWhere = (new StringBuilder(" AND DATEPART(MM,DEAD) = '")).append(month).append("' ").toString();
        
        // uqery
        try {
            // total count
            query = (new StringBuilder("SELECT COUNT(1)   FROM BEFORE_PRIEST WHERE 1=1  ")).append(strWhere).toString();
            rtDTO.daoTotalCount = super.executeCount(query, false);
            
            // list
            query = "SELECT A.* FROM "
            		+ " ( SELECT ROW_NUMBER() OVER(ORDER BY DEAD DESC) RNUM,  bp_idx, name,christian_name"
            		+ ", convert(char(10),ordination,120) as  ordination"
            		+ ", convert(char(10),dead,120) as dead, brial_place"
            		+ ", convert(char(10),birthday,120) as birthday,image "
            		+ " FROM BEFORE_PRIEST WHERE 1=1  "+strWhere+") A "
            		+ " WHERE A.RNUM BETWEEN "+startRnum+" AND "+endRnum;
            rtDTO.daoList = super.executeQueryList(query);
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
            free();
        }
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Result dto:"+rtDTO );
    }

//	private int holyListCount(Map _params) throws Exception
//    {
//        String strWhere;
//        int result;
//        
//        String query = "";
//        strWhere = "";
//        String searchGubun = pnull(_params.get("searchGubun"));
//        String month = pnull(_params.get("month"));
//        if(searchGubun.equals("month") && month.length() > 0)
//            strWhere = (new StringBuilder(" AND DATEPART(MM,DEAD) = '")).append(month).append("' ").toString();
//        result = 0;
//        
//        try
//        {
//            query = (new StringBuilder("SELECT COUNT(1)   FROM BEFORE_PRIEST WHERE 1=1  ")).append(strWhere).toString();
//            result = super.executeCount(query, false);
//        } catch(Exception e) {
//        	E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
//        } finally {
//        	
//            free();
//        }
//        return result;
//    }

	/*
	 * front side 선종사제 상세 보기
	 * (non-Javadoc)
	 * @see kr.caincheon.church.dao.PriestDao#holyContents(java.util.Map)
	 */
	@Override
    public Map holyContents(Map _params) throws Exception
    {
        Map result = new HashMap();
        
        String query = "";
        try
        {
            query = "SELECT A.bp_idx, isnull(A.name,'') as name , isnull(A.christian_name,'') as christian_name"
            		+ ", isnull(CONVERT(char(10), A.ordination, 120),'') as ordination"
            		+ ", isnull(CONVERT(char(10), A.dead, 120),'') as dead"
            		+ ", isnull(A.brial_place,'') as brial_place"
            		+ ", isnull(CONVERT(char(10), A.birthday, 120),'') as birthday"
            		+ ", isnull(A.image,'') as image, isnull(A.phrase,'') as phrase"
            		+ ", isnull(A.profile,'') as profile"
            		+ ", (SELECT MAX(bp_idx) FROM before_priest X WHERE X.bp_idx < A.bp_idx) AS before_p_idx"
            		+ ", (SELECT MIN(bp_idx) FROM before_priest X WHERE X.bp_idx > A.bp_idx) AS next_p_idx "
            		+ " FROM before_priest A "
            		+ " WHERE A.bp_idx="+_params.get("bp_idx");
            result = super.executeQueryMap(query);
            
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
            free();
        }
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Result:"+result );
        return result;
    }

	@Override
    public void befPriestList(CommonDaoDTO rtDTO, Map _params) throws Exception
    {
        String strWhere = "";
        
        String query = "";
        int pageNo = ipnull(_params, "pageNo", 1);
        int pageSize  = ipnull(_params, "pageSize", 10);
        int startRnum = (pageNo - 1) * pageSize + 1;
        int endRnum = pageNo * pageSize;
        
        String searchGubun = pnull(_params.get("searchGubun"));
        String searchText = pnull(_params.get("searchText"));
        
        if(searchText.length() != 0) {
            if(searchGubun.equals("1"))
                strWhere = " AND NAME LIKE '%"+searchText+"%' ";
            else
                strWhere = " AND CHRISTIAN_NAME LIKE '%"+searchText+"%' ";
        }
        
        // query
        try {

            // total count
            query = "SELECT COUNT(1)   FROM BEFORE_PRIEST WHERE 1=1 "+strWhere;
            rtDTO.daoTotalCount = super.executeCount(query,false);
            
        	// list
            query = "SELECT A.*   FROM  (SELECT ROW_NUMBER() OVER(ORDER BY DEAD DESC) RNUM,  bp_idx, name,christian_name"
            		+ ",convert(char(10),ordination,120) as ordination"
            		+ ", convert(char(10),dead,120) as dead,brial_place "
            		+ " FROM BEFORE_PRIEST "
            		+ " WHERE 1=1 "+strWhere+") A WHERE A.RNUM BETWEEN "+startRnum+" AND "+endRnum;
            rtDTO.daoList = super.executeQueryList(query);
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        	throw e;
        } finally {
            free();
        }
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Result dto:"+rtDTO );
    }

//	private int befPriestListCount(Map _params) throws Exception
//    {
//        String strWhere;
//        int result;
//        
//        String query = "";
//        strWhere = "";
//        String searchGubun = pnull(_params.get("searchGubun"));
//        String searchText = pnull(_params.get("searchText"));
//        if(searchText != null && searchText.length() != 0)
//            if(searchGubun.equals("1"))
//                strWhere = (new StringBuilder(" AND NAME LIKE '%")).append(searchText).append("%' ").toString();
//            else
//                strWhere = (new StringBuilder(" AND CHRISTIAN_NAME LIKE '%")).append(searchText).append("%' ").toString();
//        result = 0;
//        
//        try
//        {
//            query = (new StringBuilder("SELECT COUNT(1)   FROM BEFORE_PRIEST WHERE 1=1  ")).append(strWhere).toString();
//            result = super.executeCount(query,false);
//        } catch(Exception e) {
//        	E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
//        } finally {
//        	
//            free();
//        }
//        _logger.info((new StringBuilder("result=")).append(result).toString());
//        return result;
//    }

	@Override
    public Map befPriestContents(Map _params) throws Exception
    {
        Map result;
        
        result = new HashMap();
        String query = "";
        try
        {
            query = "SELECT A.BP_IDX, ISNULL(A.NAME,'') AS NAME , ISNULL(A.CHRISTIAN_NAME,'') AS CHRISTIAN_NAME"
            		+ ", ISNULL(CONVERT(CHAR(10), A.ORDINATION, 120),'') AS ORDINATION, ISNULL(CONVERT(CHAR(10), A.DEAD, 120),'') AS DEAD"
            		+ ", ISNULL(A.BRIAL_PLACE,'') AS BRIAL_PLACE, ISNULL(CONVERT(CHAR(10), A.BIRTHDAY, 120),'') AS BIRTHDAY"
            		+ ", ISNULL(A.IMAGE,'') AS IMAGE /* 양력 */ "
            		//+ ", ISNULL(A.IMAGE2,'') AS IMAGE2 /* 상본 */"
            		+ ", ISNULL(A.PHRASE,'') AS PHRASE, ISNULL(A.PROFILE,'') AS PROFILE"
            		+ ", (SELECT MAX(BP_IDX) FROM BEFORE_PRIEST X WHERE X.BP_IDX < A.BP_IDX) AS BEFORE_P_IDX"
            		+ ", (SELECT MIN(BP_IDX) FROM BEFORE_PRIEST X WHERE X.BP_IDX > A.BP_IDX) AS NEXT_P_IDX "
            		+ " FROM BEFORE_PRIEST A "
            		+ " WHERE A.BP_IDX='"+_params.get("bp_idx")+"'";
            result = super.executeQueryMap(query);
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
            free();
        }

        D(_logger, Thread.currentThread().getStackTrace(), "DAO Result:"+result );
        
        return result;
    }

	/*
	 * Admin side 선종사제 묘소 목록
	 * (non-Javadoc)
	 * @see kr.caincheon.church.dao.PriestDao#brialPlaceList(java.util.Map)
	 */
	@Override
    public List brialPlaceList(Map _params) throws Exception
    {
        List result=null;
        
        try
        {
            String query = "SELECT ROW_NUMBER() OVER(ORDER BY updateDT DESC) RNUM, BRIAL_PLACE_NO, ID, BRIAL_PLACE_NAME "
            		+ " FROM BRIAL_PLACE ";
            result = super.executeQueryList(query);
            
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+lastSQL, e);
        } finally {
        	
            free();
        }
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Result:"+result );
        return result;
    }

	// 선종사제 묘소 추가 등록
	@Override
    public boolean befPriestTombInsert(Map _params) throws Exception
    {
        boolean bReturn;
        String id;
        String brial_place_name;
        
        PreparedStatement preparedStatement;
        bReturn = true;
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        id = pnull(_params, "id",  pnull((Map)_params.get(SESSION_MAP), "ADM_MEM_ID", "admin") );
        brial_place_name = pnull(_params.get("new_brial_name"));
        
        preparedStatement = null;
        
        String query = "";
        try
        {
        	
            query = "INSERT INTO BRIAL_PLACE  (id, brial_place_name, updateDT)  VALUES (?, ?, getdate())";
            preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, brial_place_name);
            int i = preparedStatement.executeUpdate();
            bReturn = i > 0 ? true : false;
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
            bReturn = false;
        } finally {
        	if(preparedStatement != null) try { preparedStatement.close(); } catch(SQLException e) { e.printStackTrace(); }
            
            free();
        }
        return bReturn;
    }

	// 선종사제 묘소 삭제
	@Override
    public boolean befPriestTombDelete(Map _params) throws Exception
    {
        boolean bReturn;
        String brial_place_no;
        
        PreparedStatement preparedStatement;
        bReturn = true;
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        brial_place_no = pnull(_params.get("brial_place_no"));
        
        preparedStatement = null;
        String query = "";
        try
        {
        	
            query = "delete from BRIAL_PLACE where brial_place_no = ?";
            preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setInt(1, Integer.parseInt(brial_place_no));
            int i = preparedStatement.executeUpdate();
            bReturn = i > 0 ? true : false;
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
            bReturn = false;
        } finally {
        	if(preparedStatement != null) try { preparedStatement.close(); } catch(SQLException e) { e.printStackTrace(); }
            
            free();
        }
        return bReturn;
    }

	/*
	 * 선종사체 등록
	 * (non-Javadoc)
	 * @see kr.caincheon.church.dao.PriestDao#befPriestInsert(java.util.Map)
	 */
	@Override
    public boolean befPriestInsert(Map _params) throws Exception
    {
        boolean bReturn = true;
        
        PreparedStatement preparedStatement = null;
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO befPriestInsert Called.[params:"+_params+"]" );
        
        String name = pnull(_params.get("name"));
        String christian_name = pnull(_params.get("christian_name"));
        String ordination = pnull(_params.get("ordination"));
        String dead = pnull(_params.get("dead"));
        String brial_place = pnull(_params.get("brial_place"));
        String birthday = pnull(_params.get("birthday"));
        String image = pnull(_params.get("image"));
        String phrase = pnull(_params.get("phrase"));
        String profile = pnull(_params.get("profile"));
        
        String query = "";
        try
        {
        	
            query = "INSERT INTO BEFORE_PRIEST  (name, christian_name, ordination, dead, brial_place, birthday, image, phrase, profile) "
            		+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, christian_name);
            preparedStatement.setString(3, ordination);
            preparedStatement.setString(4, dead);
            preparedStatement.setString(5, brial_place);
            preparedStatement.setString(6, birthday);
            preparedStatement.setString(7, image);
            preparedStatement.setString(8, phrase);
            preparedStatement.setString(9, profile);
            int i = preparedStatement.executeUpdate();
            bReturn = i > 0 ? true : false;
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
            bReturn = false;
        } finally {
        	if(preparedStatement != null) try { preparedStatement.close(); } catch(SQLException e) { e.printStackTrace(); }
            
            free();
        }
        return bReturn;
    }

	@Override
    public boolean befPriestModify(Map _params) throws Exception
    {
        boolean bReturn = true;
        
        PreparedStatement preparedStatement = null;
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO befPriestModify Called.[params:"+_params+"]" );
        
        String name = pnull(_params.get("name"));
        String christian_name = pnull(_params.get("christian_name"));
        String ordination = pnull(_params.get("ordination"));
        String dead = pnull(_params.get("dead"));
        String brial_place = pnull(_params.get("brial_place"));
        String birthday = pnull(_params.get("birthday"));
        String image = pnull(_params.get("image"));
        String phrase = pnull(_params.get("phrase"));
        String profile = pnull(_params.get("profile"));
        String bp_idx = pnull(_params.get("bp_idx"));
        
        // if 이전이미지 삭제 flag='Y'
        if(pnull(_params.get("delImg")).equals("Y")) {
        	Object col = executeColumnOne("SELECT IMAGE FROM BEFORE_PRIEST WHERE bp_idx="+bp_idx, 1);
        	if(col != null) {
        		String fullPath = pnull((Map)_params.get(SESSION_MAP), "CONTEXT_ROOT") + "/upload/de_father/" ;
        		boolean isDel = ImageUtils.deleteFileWithThumbnail(fullPath, col.toString());
        		if(!isDel) {
        			_params.put(ERROR_MSG, "File Delete Failed, You need to check a file storage path["+fullPath+col.toString()+"]");
        		}
        	}
        }

        // 
        String query = "";
        try
        {
        	int i = 1;
            query = "UPDATE BEFORE_PRIEST "
            		+ " SET name=?, christian_name=?, ordination=?, dead=?, brial_place=?, birthday=?"
            		+ ( image.length() > 0 ? ", image=?" : "")
            		+ ", phrase = ?, profile = ?"
            		+ " WHERE bp_idx=?";
            preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(i++, name);
            preparedStatement.setString(i++, christian_name);
            preparedStatement.setString(i++, ordination);
            preparedStatement.setString(i++, dead);
            preparedStatement.setString(i++, brial_place);
            preparedStatement.setString(i++, birthday);
            if( image.length() > 0 ) preparedStatement.setString(i++, image);
            preparedStatement.setString(i++, phrase);
            preparedStatement.setString(i++, profile);
            preparedStatement.setInt(i++, Integer.parseInt(bp_idx));
            int x = preparedStatement.executeUpdate();
            bReturn = x > 0 ? true : false;
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
            bReturn = false;
        } finally {
        	if(preparedStatement != null) try { preparedStatement.close(); } catch(SQLException e) { e.printStackTrace(); }
            
            free();
        }
        return bReturn;
    }

	/*
	 * 선종사제 삭제
	 * (non-Javadoc)
	 * @see kr.caincheon.church.dao.PriestDao#befPriestDelete(java.util.Map)
	 */
	@Override
    public boolean befPriestDelete(Map _params) throws Exception
    {
        boolean bReturn = true;
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO befPriestModify Called.[params:"+_params+"]" );
        
        String bp_idx = pnull(_params.get("bp_idx"));
        
        // if 이전이미지 삭제 flag='Y'
    	Object col = executeColumnOne("SELECT IMAGE FROM BEFORE_PRIEST WHERE bp_idx="+bp_idx, 1);
    	if(col != null) {
    		String fullPath = pnull((Map)_params.get(SESSION_MAP), "CONTEXT_ROOT") + "/upload/de_father/" ;
    		boolean isDel = ImageUtils.deleteFileWithThumbnail(fullPath, col.toString());
    		if(!isDel) {
    			_params.put(ERROR_MSG, "File Delete Failed, You need to check a file storage path["+fullPath+col.toString()+"]");
    		}
    	}
        
        // 
        try
        {
            int i = executeUpdate("DELETE FROM BEFORE_PRIEST WHERE bp_idx="+bp_idx);
            bReturn = i > 0 ? true : false;
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+lastSQL, e);
            bReturn = false;
        } finally {
            free();
        }
        return bReturn;
    }
	
//	private int admPriestListCount(Map _params) throws Exception
//    {
//        String strWhere;
//        int result;
//        
//        
//        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
//        String query = "";
//        String schTextGubun = pnull(_params.get("schTextGubun"));
//        String schText = pnull(_params.get("schText"));
//        strWhere = "";
//        if(schText.length() > 0)
//            strWhere = (new StringBuilder(" AND ")).append(schTextGubun).append(" LIKE '%").append(schText).append("%' ").toString();
//        result = 0;
//        
//        try
//        {
//            query = (new StringBuilder("SELECT COUNT(1)   FROM priest  WHERE gubun IN ('1','2','3') ")).append(strWhere).toString();
//            result = super.executeCount(query, false);
//        } catch(Exception e) {
//        	E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
//        } finally {
//        	
//            free();
//        }
//        return result;
//    }
	
	/*
	 * 사제 메뉴의 목록 조회 
	 * (non-Javadoc)
	 * @see kr.caincheon.church.dao.PriestDao#admPriestList(kr.caincheon.church.common.CommonDaoDTO, java.util.Map)
	 */
	@Override
    public void admPriestList(CommonDaoDTO dto, Map _params) throws Exception
    {
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        String query = "";
        int pageNo    = ipnull(_params, "pageNo", 1);
        int pageSize  = ipnull(_params, "pageSize", 10);
        int startRnum = (pageNo - 1) * pageSize + 1;
        int endRnum   = pageNo * pageSize;
        
        int sortGubun = UtilInt.pint(_params.get("sortGubun"), 1);
        String[] sortGubunText = {"", "A1.ONUM", "A1.P_BIRTHDAY", "A1.NEW_BIRTHDAY", "C2.NAME", "A1.NAME", "A1.CHRISTIAN_NAME"}; //sortGubun 이 selectbox임
        String strWhere = " WHERE 1=1 ";
        
        // searchGubun & searchText
        String searchText = pnull(_params.get("searchText"));
        if(searchText.length() > 0) {
        	int searchGubun = UtilInt.pint(_params.get("searchGubun"), 1);
        	switch(searchGubun){
        	case 1: /*이름*/
        		strWhere += " AND A1.NAME LIKE '%"+searchText+"%' ";
        		break;
        	case 2: /*세례명*/ 
        		strWhere += " AND A1.CHRISTIAN_NAME LIKE '%"+searchText+"%' ";
        		break;
        	case 3:/*임지*/
        		String tSQL = "SELECT P_IDX FROM ORG_DEPARTMENT_PRIEST_REL WHERE ORG_IDX IN (SELECT H.ORG_IDX FROM ORG_HIERARCHY H "
        				+ "LEFT OUTER JOIN  DEPARTMENT D ON D.ORG_IDX = H.ORG_IDX "
        				+ "LEFT OUTER JOIN  CHURCH C ON C.ORG_IDX = H.ORG_IDX "
        				+ "LEFT OUTER JOIN  ORGANIZATION O ON O.ORG_IDX = H.ORG_IDX "
        				+ "WHERE D.NAME LIKE '%"+searchText+"%' OR C.NAME LIKE '%"+searchText+"%' OR O.NAME LIKE '%"+searchText+"%' )";
        		strWhere += " AND A1.P_IDX IN ("+tSQL+") ";
        		break;
        	case 4: /*호칭*/
        		strWhere += " AND A1.APPELLATION IN (SELECT CODE_INST FROM CODE_INSTANCE WHERE CODE='000002' AND NAME LIKE '%"+searchText+"%') ";
        		break;
        	}
        }
        
        // query
        try {
            // total
            query = "SELECT COUNT(1)   FROM PRIEST A1 " 
            		+ (strWhere.length() > 0 ? strWhere : "")
            		+ " AND GUBUN IN (SELECT CODE_INST from CODE_INSTANCE WHERE CODE='000001') "
            		;
            
            dto.daoTotalCount = super.executeCount(query, false);
            
            // list
            query = "SELECT * FROM  (SELECT ROW_NUMBER() OVER(ORDER BY "+sortGubunText[sortGubun]+") RNUM"
            		+ ", P_IDX, GUBUN "
            		//+ ",  CASE WHEN gubun='1' THEN '\uAD50\uAD6C'  WHEN gubun='2' THEN '\uC218\uB3C4\uD68C'  WHEN gubun='3' THEN '\uD0C0\uAD50\uAD6C'  ELSE isnull(gubun,'\uAD50\uAD6C') END as gubun_text"
            		+ ", ISNULL(C1.NAME, '') AS GUBUN_TEXT"
            		+ ", ISNULL(A1.NAME,'') AS NAME "
            		+ ", ISNULL(C2.NAME,'') AS APPELLATION_NAME "
            		+ ", ISNULL(CHRISTIAN_NAME,'') AS CHRISTIAN_NAME"
            		+ ", ISNULL(CONVERT(CHAR(10), P_BIRTHDAY, 120),'') AS P_BIRTHDAY"
            		+ ", ISNULL(CONVERT(CHAR(10), NEW_BIRTHDAY, 120),'') AS NEW_BIRTHDAY"
            		+ ", ISNULL(WON_YN,'') AS WON_YN "
            		+ " FROM PRIEST A1 "
            		+ " left outer join CODE_INSTANCE C1 on C1.CODE_INST=A1.GUBUN AND C1.CODE='000001' "
            		+ " left outer join CODE_INSTANCE C2 on C2.CODE_INST=A1.APPELLATION AND C2.CODE='000002' "
            		+ " "+strWhere+") A "
            		+ " WHERE A.RNUM BETWEEN "+startRnum+" AND "+endRnum;
            dto.daoList = super.executeQueryList(query);
            
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
            throw e;
        } finally {
            free();
        }
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Result dto:"+dto );
    }

	@Override
    public Map admPriestContents(Map _params) throws Exception
    {
        Map result = new HashMap();
        
        String query = "";
        try
        {
            query = "SELECT A.P_IDX, ISNULL(A.NAME,'') AS NAME , ISNULL(A.CHRISTIAN_NAME,'') AS CHRISTIAN_NAME, A.GUBUN"
            		//+ ", CASE WHEN A.gubun='1' THEN '\uAD50\uAD6C' WHEN A.gubun='2' THEN '\uC218\uB3C4\uD68C' WHEN A.gubun='3' THEN '\uD0C0\uAD50\uAD6C' ELSE isnull(A.gubun,'\uAD50\uAD6C') END as gubun_name"
            		+ ", ISNULL(C1.NAME, '') AS GUBUN_NAME"
            		+ ", ISNULL(CONVERT(CHAR(10), A.NEW_BIRTHDAY, 120),'') AS NEW_BIRTHDAY"
            		+ ", ISNULL(CONVERT(CHAR(10), A.P_BIRTHDAY, 120),'') AS P_BIRTHDAY"
            		+ ", ISNULL(A.TEL,'') AS TEL"
            		+ ", ISNULL(A.EMAIL,'') AS EMAIL"
//            		+ ", isnull(A.s_gubun,'') as s_gubun"
//            		+ ", isnull(A.j_gubun,'') as j_gubun"
//            		+ ", isnull(A.j_name,'') as j_name"
//            		+ ", isnull(A.p_code,'') as p_code"
//            		+ ", isnull(A.p_name,'') as p_name"
					+ ", ISNULL(A.APPELLATION,'') AS APPELLATION "
					+ ", ISNULL(C2.NAME,'') AS P_NAME "
            		+ ", ISNULL(A.ETC,'') AS ETC"
            		+ ", ISNULL(A.IMAGE,'') AS IMAGE"
            		+ ", ISNULL(A.IMAGE2,'') AS IMAGE2"
            		+ ", ISNULL(A.IMAGE3,'') AS IMAGE3"
            		+ ", ISNULL(A.WON_YN,'') AS WON_YN"
            		+ ", ISNULL(A.HOMEPAGE,'') AS HOMEPAGE"
            		+ ", ISNULL(A.PHRASE,'') AS PHRASE"
            		+ ", ISNULL(A.WON_YN,'') AS WON_YN"
            		+ ", ONUM "
            		+ " FROM PRIEST A "
            		+ " LEFT OUTER JOIN CODE_INSTANCE C1 on C1.CODE_INST=A.GUBUN AND C1.CODE='000001' "
            		+ " LEFT OUTER JOIN CODE_INSTANCE C2 on C2.CODE_INST=A.APPELLATION AND C2.CODE='000002' "
            		+ " WHERE A.p_idx = '"+_params.get("p_idx")+"'";
            result = super.executeQueryMap(query);
            
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
            free();
        }
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Result:"+result );
        
        return result;
    }

	@Override
    public List admPriestDCodeList(Map _params) throws Exception
    {
        String query;
        List result=null;
        
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        query = "";
        
        try
        {
            query = "SELECT ROW_NUMBER() OVER(ORDER BY A.P_IDX) AS RNUM, B.ORG_IDX, C.DEPART_IDX, C.NAME, B.P_POSITION "
            		+ " FROM PRIEST a "
            		//+ " left outer join PRIEST_DEPART b on a.p_idx = b.p_idx "
            		+ " LEFT OUTER JOIN ORG_DEPARTMENT_PRIEST_REL B ON A.P_IDX = B.P_IDX  "
            		+ " LEFT OUTER JOIN DEPARTMENT C ON C.ORG_IDX = B.ORG_IDX  "
            		+ " WHERE A.P_IDX= '"+pnull(_params, "p_idx")+"' "
            		;
            result = super.executeQueryList(query);
        }
        catch(Exception e)
        {
            _logger.error((new StringBuilder("ERROR SQL:")).append(query).toString(), e);
        } finally {
        	
            free();
        }
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Result:"+result );
        
        return result;
    }

	@Override
    public boolean admPriestInsert(Map _params) throws Exception
    {
        int rn=0, rn1=0, rn2=0;
        
        PreparedStatement preparedStatement;
        boolean bReturn = true;
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        String p_idx = pnull(_params.get("p_idx"));//사제명
        String name = pnull(_params.get("name"));//사제명
        String christian_name = pnull(_params.get("christian_name"));//세례명
        String gubun = pnull(_params.get("gubun"));//소속
        String new_birthday = pnull(_params.get("new_birthday"));//축일
        String p_birthday = pnull(_params.get("p_birthday"));//서품일
        String email = pnull(_params.get("email"));//이메일
        String onum = pnull(_params.get("onum"));//기본정렬번호
        String phrase = pnull(_params.get("phrase"));//성구
        String homepage = pnull(_params.get("homepage"));//홈피/sns
        String image1 = pnull(_params.get("image1"));//사진
        String image2 = pnull(_params.get("image2"));//상본
        String image3 = pnull(_params.get("image3"));//메인노출이미지
        String won_yn = pnull(_params.get("won_yn"));//원로여부
        String appellation = pnull(_params.get("appellation"));//호칭
        
        preparedStatement = null;
        String query = "";
        try
        {
        	// PK , P_IDX : 해당연도에서 3자리수로 추출 예 `2017001` 처럼 p_idx를 자동 추출
            query = "INSERT INTO PRIEST  (p_idx, name, christian_name, gubun, new_birthday,  p_birthday, email, appellation, onum,  phrase, homepage, image, image2, image3, won_yn) "
            		//+ " VALUES ( (SELECT CONVERT(INT, CONVERT(CHAR(4),GETDATE(),120)+'000'))+1 FROM PRIEST WHERE P_IDX LIKE CONVERT(CHAR(4),  GETDATE(), 120)+'%' ) "
            		+ " VALUES ( ? "
            		+ ", ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1 , p_idx);
            preparedStatement.setString(2 , name);
            preparedStatement.setString(3 , christian_name);
            preparedStatement.setString(4 , gubun);
            preparedStatement.setString(5 , new_birthday);
            preparedStatement.setString(6 , p_birthday);
            preparedStatement.setString(7 , email);
            preparedStatement.setString(8 , appellation);
            preparedStatement.setString(9 , onum);
            preparedStatement.setString(10, phrase);
            preparedStatement.setString(11, homepage);
            preparedStatement.setString(12, image1);
            preparedStatement.setString(13, image2);
            preparedStatement.setString(14, image3);
            preparedStatement.setString(15, won_yn);
            rn1 = preparedStatement.executeUpdate();
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
            bReturn = false;
        } finally {
        	if(preparedStatement != null) try { preparedStatement.close(); } catch(SQLException e) { e.printStackTrace(); }
            
            free();
        }
        
//        int departCodeCnt;
//        String depart_code = "";
//        departCodeCnt = Integer.parseInt(pnull(_params.get("departCodeCnt"), "0"));
//        int departCodeCommitCnt = 0;
//        
//        try
//        {
//        	if(departCodeCnt <= 0) throw new Exception("부서정보가 없습니다.");
//        	
//            query = "INSERT INTO PRIEST_DEPART  (p_idx, depart_code, pos_code, insertDT, userid)  values(?, ?, ?, getdate(), ?) ";
//            preparedStatement = getConnection().prepareStatement(query);
//            for(int i = 0; i < departCodeCnt; i++)
//            {
//                //String depart_code;
//                if(pnull(_params.get((new StringBuilder("departCode3")).append(i + 1).toString())).equals(""))
//                {
//                    if(pnull(_params.get((new StringBuilder("departCode2")).append(i + 1).toString())).equals(""))
//                    {
//                        if(pnull(_params.get((new StringBuilder("departCode1")).append(i + 1).toString())).equals(""))
//                            depart_code = "";
//                        else
//                            depart_code = pnull(_params.get((new StringBuilder("departCode1")).append(i + 1).toString()));
//                    } else
//                    {
//                        depart_code = pnull(_params.get((new StringBuilder("departCode2")).append(i + 1).toString()));
//                    }
//                } else
//                {
//                    depart_code = pnull(_params.get((new StringBuilder("departCode3")).append(i + 1).toString()));
//                }
//                preparedStatement.setString(1, p_idx);
//                preparedStatement.setString(2, depart_code);
//                preparedStatement.setString(3, pnull(_params.get((new StringBuilder("posCode")).append(i + 1).toString())));
//                preparedStatement.setString(4, pnull(_params.get("adm_id")));
//                rn2 += preparedStatement.executeUpdate();
//            }
//
//	    }
//	    catch(Exception e)
//	    {
//	        e.printStackTrace();
//	        bReturn = false;
//	    } finally {
//	    	if(preparedStatement != null) try { preparedStatement.close(); } catch(SQLException e) { e.printStackTrace(); }
//	        
//	        free();
//	    }
        
        rn = rn1 + rn2;
        
        return rn > 0 && bReturn;
    }

	/*
	 * 관리자에서 사제 정보 수정하기
	 */
	@Override
    public boolean admPriestModify(Map _params) throws Exception
    {
        int rn=0, rn1=0, rn2=0, rn3=0;
        String p_idx;
        String name;
        String christian_name;
        String gubun;
        String new_birthday;
        String p_birthday;
        String email;
        String appellation;
        String image;
        String onum;
        String phrase;
        String homepage;
        String image2;
        String image3;
        String won_yn;
        
        PreparedStatement preparedStatement = null;
        
        boolean bReturn = true;
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        p_idx = pnull(_params, "p_idx");
        name = pnull(_params.get("name"));
        christian_name = pnull(_params.get("christian_name"));
        gubun = pnull(_params.get("gubun"));
        new_birthday = pnull(_params.get("new_birthday"));
        p_birthday = pnull(_params.get("p_birthday"));
        email = pnull(_params.get("email"));
        appellation = pnull(_params.get("appellation"));
        image = pnull(_params.get("image1"));
        onum = pnull(_params.get("onum"));
        phrase = pnull(_params.get("phrase"));
        homepage = pnull(_params.get("homepage"));
        image2 = pnull(_params.get("image2"));
        image3 = pnull(_params.get("image3"));
        won_yn = pnull(_params.get("won_yn"));
        
        
        // 파일 삭제처리
        if(pnull(_params.get("delImage1")).equals("Y")) {
        	image = "";
        }
        if(pnull(_params.get("delImage2")).equals("Y")) {
        	image2 = "";
        }
        if(pnull(_params.get("delImage3")).equals("Y")) {
        	image3 = "";
        }
        
        // 사제정보 수정
        String query = "";
        try
        {
        	int i=1;
            query = "UPDATE PRIEST SET "
            		+ " name=?, christian_name=?, gubun=?,  new_birthday=?, p_birthday=?, email=?, appellation=?"
            		+ ", onum=?, phrase=?, homepage=?"
            		+ ", won_yn=? "
            		+ (image.length() >0 ? ", image=?" :"")
            		+ (image2.length() >0 ? ", image2=?" :"")
            		+ (image3.length() >0 ? ", image3=?" :"")
            		+ " WHERE p_idx=?";
            preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(i++, name);
            preparedStatement.setString(i++, christian_name);
            preparedStatement.setString(i++, gubun);
            preparedStatement.setString(i++, new_birthday);
            preparedStatement.setString(i++, p_birthday);
            preparedStatement.setString(i++, email);
            preparedStatement.setString(i++, appellation);
            preparedStatement.setString(i++, onum);
            preparedStatement.setString(i++, phrase);
            preparedStatement.setString(i++, homepage);
            preparedStatement.setString(i++, won_yn);
            if(image.length() >0) preparedStatement.setString(i++, image);
            if(image2.length()>0) preparedStatement.setString(i++, image2);
            if(image3.length()>0) preparedStatement.setString(i++, image3);
            preparedStatement.setString(i++, p_idx);
            rn2 = preparedStatement.executeUpdate();
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
            bReturn = false;
        } finally {
        	if(preparedStatement != null) try { preparedStatement.close(); } catch(SQLException e) { e.printStackTrace(); }
        }
        
//        int departCodeCnt;
//        String depart_code = "";
//        departCodeCnt = Integer.parseInt(pnull(_params.get("departCodeCnt"), "0"));
//        int departCodeCommitCnt = 0;
        
        // 발령정보 수정
//        preparedStatement = null;
//        query = (new StringBuilder("DELETE FROM PRIEST_DEPART WHERE P_IDX='")).append(p_idx).append("' ").toString();
//        //query = (new StringBuilder("DELETE FROM ORG_DEPARTMENT_PRIEST_REL WHERE P_IDX='")).append(p_idx).append("' ").toString();
//        try
//        {
//            rn1 = executeUpdate(query);
//        } catch(Exception e) {
//        	E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
//            bReturn = false;
//        } finally {
//        	if(preparedStatement != null) try { preparedStatement.close(); } catch(SQLException e) { e.printStackTrace(); }
//        }
        
//        try
//        {
//        	if(departCodeCnt <= 0) throw new Exception("부서정보가 없습니다.");
//
//        	query = "INSERT INTO PRIEST_DEPART  (p_idx, depart_code, pos_code, insertDT, userid)  values(?, ?, ?, getdate(), ?) ";
//            preparedStatement = getConnection().prepareStatement(query);
//            for(int i = 0; i < departCodeCnt; i++)
//            {
//                //String depart_code;
//                if(pnull(_params.get((new StringBuilder("departCode3")).append(i + 1).toString())).equals(""))
//                {
//                    if(pnull(_params.get((new StringBuilder("departCode2")).append(i + 1).toString())).equals(""))
//                    {
//                        if(pnull(_params.get((new StringBuilder("departCode1")).append(i + 1).toString())).equals(""))
//                            depart_code = "";
//                        else
//                            depart_code = pnull(_params.get((new StringBuilder("departCode1")).append(i + 1).toString()));
//                    } else
//                    {
//                        depart_code = pnull(_params.get((new StringBuilder("departCode2")).append(i + 1).toString()));
//                    }
//                } else
//                {
//                    depart_code = pnull(_params.get((new StringBuilder("departCode3")).append(i + 1).toString()));
//                }
//                preparedStatement.setString(1, p_idx);
//                preparedStatement.setString(2, depart_code);
//                preparedStatement.setString(3, pnull(_params.get((new StringBuilder("posCode")).append(i + 1).toString())));
//                preparedStatement.setString(4, pnull(_params.get("adm_id")));
//                rn3 += preparedStatement.executeUpdate();
//            }
//            
//        } catch(Exception e) {
//        	E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
//            bReturn = false;
//        } finally {
//        	if(preparedStatement != null) try { preparedStatement.close(); } catch(SQLException e) { e.printStackTrace(); }
//            
//            free();
//        }
        rn = rn1 + rn2 + rn3;
        return rn2 > 0 && bReturn;
    }

	/*
	 * 사제 삭제
	 * (non-Javadoc)
	 * @see kr.caincheon.church.dao.PriestDao#admPriestOrgDelete(java.util.Map)
	 */
	@Override
    public boolean admPriestOrgDelete(Map _params) throws Exception
    {
        int rn=0;
        String   idx = pnull(_params, "idx");
        String p_idx = pnull(_params, "p_idx");
        
        boolean bReturn = true;
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        
        String query = "";
        
        try {
            query = "delete from PRIEST where p_idx = "+p_idx;
            rn = executeUpdate(query);
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
            bReturn = false;
        } finally {
        }
        
        try {
        	query = "delete from ORG_DEPARTMENT_PRIEST_REL where p_idx = "+p_idx + " AND IDX="+idx;
            query = "delete from ORG_DEPARTMENT_PRIEST_REL where p_idx = "+p_idx;
            rn += executeUpdate(query);
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
            bReturn = false;
        } finally {
            free();
        }
        
        return rn > 0 && bReturn;
    }

	@Override
    public boolean admPriestDelete(Map _params) throws Exception
    {
        int rn = 0;
        String idx = pnull(_params, "idx");
        
        boolean bReturn = true;
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        String query = "";
        
        try {
            query = "delete from ORG_DEPARTMENT_PRIEST_REL where idx = "+idx;
            rn += executeUpdate(query);
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
            bReturn = false;
        } finally {
            free();
        }
        return rn > 0 && bReturn;
    }


	/*
     * 발령정보 delete 처리
     */
    @Override
    public void deletePreistAssign(CommonDaoDTO dto, Map _params) throws Exception {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	String idx                    = pnull(_params, "idx");

		int i=0;
		try {
			i = executeUpdate("DELETE FROM ORG_DEPARTMENT_PRIEST_REL WHERE IDX = " + idx );
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			free();
		}
		dto.isBool = i == 1 ? true : false;
		dto.otherData = Integer.valueOf(idx);
    }
    
	/*
     * 발령정보 upsert 처리
     */
    @Override
    public void upsertPreistAssign(CommonDaoDTO dto, Map _params) throws Exception {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
    	String idx                    = pnull(_params, "idx");
    	String org_idx1               = pnull(_params, "org_lv1");
    	String org_idx2               = pnull(_params, "org_lv2");
    	String org_idx3               = pnull(_params, "org_lv3");
    	String p_idx                  = pnull(_params, "p_idx");
    	String p_position             = pnull(_params, "p_position");
    	String main_assign_yn         = pnull(_params, "main_assign_yn", "N").toUpperCase();
    	String appointment_start_date = pnull(_params, "appointment_start_date").replaceAll("-", "");
    	String appointment_end_date   = pnull(_params, "appointment_end_date").replaceAll("-", "");
    	
    	//
    	String org_idx = pnull(_params, "org_idx");
    	if(org_idx.length()==0) {
	    	org_idx = (org_idx3.length()>0 ? org_idx3 : (org_idx2.length()>0 ? org_idx2 : org_idx1));
	    	if(org_idx.indexOf("|")>-1) {
	    		org_idx = org_idx.substring(org_idx.indexOf("|") + 1);
	    	}
    	}
    	
    	/*
    	 * 데이터 조회
    	 */
    	Map row = null;
    	if(idx.length() > 0) {
    		row = executeQueryMap("SELECT * FROM ORG_DEPARTMENT_PRIEST_REL WHERE IDX='"+idx+"'");
    	}
    	
    	// upsert
    	int i = 0;
    	try {
			if(idx.length() > 0 && row.size() > 0) {
				// update
				i = executeUpdate("UPDATE ORG_DEPARTMENT_PRIEST_REL SET"
						+ "  ORG_IDX="+org_idx+", P_IDX="+p_idx+", P_POSITION='"+p_position+"'"
						+ ", MAIN_ASSIGN_YN='"+main_assign_yn+"'"
						+ ", APPOINTMENT_START_DATE='"+appointment_start_date+"'"
						+ ", APPOINTMENT_END_DATE='"+appointment_end_date+"'"
						+ ", UPD_DATE=REPLACE(CONVERT(CHAR(10),  GETDATE(), 120),'-','') "
						+ " WHERE IDX="+idx
						);
				
				// return a PK
				dto.otherData = Integer.valueOf(idx);
				
			} else {
				// insert
				int max_idx = executeCount("SELECT ISNULL(MAX(IDX),0)+1 FROM ORG_DEPARTMENT_PRIEST_REL ", false);
				
				i = executeUpdate("INSERT INTO ORG_DEPARTMENT_PRIEST_REL "
						+ " (IDX, ORG_IDX, P_IDX, P_POSITION, MAIN_ASSIGN_YN, APPOINTMENT_START_DATE, APPOINTMENT_END_DATE, INS_DATE) VALUES ( "
						//+ " (SELECT ISNULL(MAX(IDX), 0)+1 FROM ORG_DEPARTMENT_PRIEST_REL) "
						+ "  " + max_idx
						+ ", "+org_idx+", "+p_idx+", '"+p_position+"'"
						+ ", '"+main_assign_yn+"'"
						+ ", '"+appointment_start_date+"'"
						+ ", '"+appointment_end_date+"'"
						+ ", REPLACE(CONVERT(CHAR(10),  GETDATE(), 120),'-','') "
						+ " ) "
						);
				
				// return a PK
				dto.otherData = Integer.valueOf(max_idx);
			}
			dto.isBool = i > 0 ? true : false;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			free();
		}
    	
    }
    
    /*
     * 사제 발령 정보를 조회한다.
     */
    @Override
    public List selectOrgDepartmentPriestRel(String p_idx) throws Exception {
    	List result = null;
    	
    	try {
    		result = executeQueryList("SELECT R.*"
					//+ ", O.LV1+'|'+CONVERT(VARCHAR, O.ORG_IDX) LV1, O.LV2+'|'+CONVERT(VARCHAR, O.ORG_IDX) LV2, O.LV3+'|'+CONVERT(VARCHAR, O.ORG_IDX) LV3"
					+ ", O.LV1, O.LV2, O.LV3  "
					+ ", O.NAME ORG_NAME "
					+ ", C.NAME POSITION_NAME "
					+ "FROM ORG_DEPARTMENT_PRIEST_REL R"
					+ " LEFT OUTER JOIN ORG_HIERARCHY O ON O.ORG_IDX = R.ORG_IDX "
					+ " LEFT OUTER JOIN CODE_INSTANCE C ON C.CODE_INST = R.P_POSITION AND C.CODE='000003' "
					+ " WHERE R.P_IDX="+p_idx);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			free();
		}
    	
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Result:"+result );
    	
    	return result;
    }

    /**
     * 통합검색시작시 호출 :: 객체 리턴
     * @see kr.caincheon.church.dao.PriestDao#getConn()
     */
	@Override
	public Connection getConn() {
		Connection cn = null;
		try {
			cn = super.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cn;
	}

	/**
	 * 통합검색 종료시 호출
	 */
	@Override
	public void closeConn() {
		super.free();
	}

	/**
	 * 통합검색 서비스 :: 사제 ( Called by UnifySearchServiceImpl)
	 * @see kr.caincheon.church.dao.PriestDao#unifySearchPriest(java.util.Map)
	 */
	@Override
	public List unifySearchPriest(Map _params) throws Exception {
		D(_logger, Thread.currentThread().getStackTrace(), "Dao Called. params:"+_params );

		// 부서명, 부서소개
		List rtList = null;
		try {
			String srchText = pnull(_params, "srchText");
			rtList = executeQueryList("SELECT ROW_NUMBER() OVER(ORDER BY P.ONUM ASC ) RNUM "
					+ ", H.ORG_IDX, H.NAME ORG_NAME "
					+ ", R.MAIN_ASSIGN_YN, R.APPOINTMENT_START_DATE, R.APPOINTMENT_END_DATE"
					+ ", P.P_IDX, P.NAME, P.CHRISTIAN_NAME, CONVERT(CHAR(10), P.P_BIRTHDAY, 120) P_BIRTHDAY, P.NEW_BIRTHDAY "
					+ "FROM PRIEST P "
					+ "JOIN ORG_DEPARTMENT_PRIEST_REL R ON R.P_IDX = P.P_IDX "
					+ "JOIN ORG_HIERARCHY             H ON H.ORG_IDX = R.ORG_IDX  "
					+ "WHERE (P.NAME LIKE '%"+srchText+"%' OR P.CHRISTIAN_NAME LIKE '%"+srchText+"%' OR P.PHRASE LIKE '%"+srchText+"%' OR H.NAME LIKE '%"+srchText+"%') ");
		} catch (Exception e) {
			_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+lastSQL, e);
		}
		
		D(_logger, Thread.currentThread().getStackTrace(), "Dao Returned. params:"+rtList );
		return rtList;
	}

	/**
	 * 통합검색 서비스 :: 선종사제 ( Called by UnifySearchServiceImpl)
	 * @see kr.caincheon.church.dao.PriestDao#unifySearchPriestOld(java.util.Map)
	 */
	@Override
	public List unifySearchPriestOld(Map _params) throws Exception {
		D(_logger, Thread.currentThread().getStackTrace(), "Dao Called. params:"+_params );

		// 부서명, 부서소개
		List rtList = null;
		try {
			String srchText = pnull(_params, "srchText");
			rtList = executeQueryList("SELECT ROW_NUMBER() OVER(ORDER BY ORDINATION ASC ) RNUM"
					+ ", NAME, CHRISTIAN_NAME, IMAGE, BRIAL_PLACE, BP_IDX "
					+ ", CONVERT(VARCHAR(10), BIRTHDAY, 120) BIRTHDAY, CONVERT(VARCHAR(10), DEAD, 120) DEAD, CONVERT(VARCHAR(10), ORDINATION, 120) ORDINATION "
					+ " FROM BEFORE_PRIEST "
					+ " WHERE NAME LIKE '%"+srchText+"%' OR CHRISTIAN_NAME LIKE '%"+srchText+"%' OR BRIAL_PLACE LIKE '%"+srchText+"%'");
		} catch (Exception e) {
			_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+lastSQL, e);
		}
		
		D(_logger, Thread.currentThread().getStackTrace(), "Dao Returned. params:"+_params );
		return rtList;
	}
}
