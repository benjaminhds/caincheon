// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GyoguIntroDaoImpl.java

package kr.caincheon.church.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import kr.caincheon.church.common.base.CommonDao;
import kr.caincheon.church.common.base.CommonDaoDTO;

// Referenced classes of package kr.caincheon.church.dao:
//            GyoguIntroDao

/**
 * 수도회/기관단체 관리
 * @author benjamin
 *
 */
@Repository("gyoguIntroDao")
public class GyoguIntroDaoImpl extends CommonDao
    implements GyoguIntroDao
{

	private final Logger _logger = Logger.getLogger(getClass());

	/*
	 * 홈페이지 : 기관단체 목록 조회
	 * (non-Javadoc)
	 * @see kr.caincheon.church.dao.GyoguIntroDao#ordoList(java.util.Map)
	 */
    @Override
	public void organizationList(Map _params, CommonDaoDTO dto)
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
        String strWhere="";
        String query   = "";
        String lv1     = pnull(_params.get("lv1"));
        String lv2     = pnull(_params.get("lv2"));
        String org_lv2 = pnull(_params.get("org_lv2"));
        String wf_code = pnull(_params.get("wf_code")); // 세목분류코드
        String searchText = pnull(_params.get("searchText"));
        
        if(searchText.length() > 0)
            strWhere = " AND A.NAME LIKE '%"+searchText+"%' ";
        
        // 조직 LV1 or LV2 분류로 조회하기
        String lv1SQL  = " AND H.LV1='"+lv1+"' ";
        if(lv2.length() == 2) {
        	lv1SQL  += " AND H.LV2='"+lv2+"' ";
        } else if(lv2.length() == 3) {
        	lv1SQL  += " AND H.LV2!='"+lv2.substring(1)+"' ";
        } else {
        	lv1SQL  += " AND H.LV2!='00' ";
        }
        lv1SQL  += " AND H.LV3!='000' ";
        
        try {
        	// 기관/단체 페이징 조회
        	setPaging(_params);
            
        	query = "SELECT /* FRONT:ORGANIZATION */ ROW_NUMBER() OVER(ORDER BY H.NAME ASC ) RNUM, H.ORG_IDX, H.NAME, ISNULL(A.TEL, '') AS TEL, ISNULL(A.FAX, '') AS FAX "
        			+ ", ISNULL(ADDR1, '') AS ADDR1, ISNULL(ADDR2, '') AS ADDR2, ISNULL(EMAIL, '') AS EMAIL "
        			+ ", (SELECT  TOP 1 NAME FROM PRIEST C WHERE C.P_IDX=A.P_IDX) AS P_NAME "
        			+ ", A.ETC_NAME , A.HOMEPAGE, A.WF_CODE "
        			+ "FROM ORG_HIERARCHY H "
        			+ (org_lv2.length() == 0 ? "" : " JOIN (SELECT * FROM ORG_HIERARCHY WHERE ORG_IDX="+org_lv2+") H2 ON H.LV1=H2.LV1 and H.LV2=H2.LV2 ")
        			+ "LEFT OUTER JOIN ORGANIZATION A ON "+(wf_code.length()==0?"":" A.WF_CODE='"+wf_code+"' AND ")+" A.ORG_IDX=H.ORG_IDX /*정상적용*/ "
        			+ "WHERE H.DEL_YN='N' AND A.DISPLAY_YN='Y' "+strWhere + lv1SQL
        			;
        	dto.daoTotalCount = executeCount(query, true);
        	
            query = "SELECT A.*   FROM (" + query + ") A WHERE A.RNUM BETWEEN "+startRnum+" AND "+endRnum;
            
            dto.daoList = super.executeQueryList(query);
            
            dto.setPaging(pageNo, pageSize);
            
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
        	free();
    	}
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Result dto:"+dto );
        
    }

    /*
     * 선종사제 목록
     * (non-Javadoc)
     * @see kr.caincheon.church.dao.GyoguIntroDao#beforePriestList(java.util.Map, kr.caincheon.church.common.CommonDaoDTO)
     */
    @Override
	public void beforePriestList(Map _params, CommonDaoDTO dto)
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
        String strWhere="";
        String query = "";
        String searchText = pnull(_params.get("searchText"));
        
        if(searchText.length() > 0)
            strWhere = " AND name LIKE '%"+searchText+"%' ";
        
        try {
        	setPaging(_params);
        	
            query = "SELECT ROW_NUMBER() OVER(ORDER BY DEAD DESC) RNUM, BP_IDX, NAME, CHRISTIAN_NAME, CONVERT(CHAR(10),ORDINATION,120) AS ORDINATION"
            		+ " , CONVERT(CHAR(10),DEAD,120) AS DEAD, BRIAL_PLACE, CONVERT(CHAR(10),BIRTHDAY,120) AS BIRTHDAY, IMAGE "
            		+ "  FROM BEFORE_PRIEST "
            		+ "  WHERE 1=1  "+strWhere+" "
            		;
            dto.daoTotalCount = executeCount(query);
            
            query = "SELECT A.* FROM  ("
            		+ query
            		+ " ) A WHERE A.RNUM BETWEEN "+startRnum+" AND "+endRnum;
            
            dto.daoList = super.executeQueryList(query);
            
            dto.setPaging(pageNo, pageSize);
            
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
        	free();
    	}
        
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Result dto:"+dto );
        
    }

    /*
     * 관리자 : 기관단체 목록 조회
     * (non-Javadoc)
     * @see kr.caincheon.church.dao.GyoguIntroDao#admOrganizationList(java.util.Map)
     */
    @Override
	public CommonDaoDTO admOrganizationList(Map _params)
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
    	CommonDaoDTO dto = new CommonDaoDTO();
    	
        String strWhere="";
        String query = "";
        String searchGubun = pnull(_params.get("searchGubun"));
        String searchText  = pnull(_params.get("searchText"));
        
        if(searchText != null && searchText.length() > 0) {
            if(searchGubun.equals("1"))
                strWhere = " AND A.NAME LIKE '%"+searchText+"%' ";
            else if(searchGubun.equals("2"))
                strWhere = " AND B.LV1 IN (SELECT LV1 FROM ORG_HIERARCHY WHERE LV1 NOT IN ('01','02') AND LV2='00' AND LV3='000' AND NAME LIKE '%"+searchText+"%') "; 
        }
        
        try {
        	setPaging(_params);
        	
        	String query0 = " SELECT /* ORGANIZATION LIST */ ROW_NUMBER() OVER(ORDER BY B.LV1 ASC, A.NAME ASC ) RNUM"
            		+ ", O_IDX, CASE WHEN A.O_IDX IS NULL THEN 'I' ELSE 'M' END AS MODE"
            		+ "  , (SELECT NAME FROM ORG_HIERARCHY WHERE LV1=B.LV1 AND LV2='00' AND LV3='000') AS ORG_LV1_NAME"
            		+ "  , ISNULL(A.NAME, '') AS NAME, ISNULL(A.HOMEPAGE, '') AS HOMEPAGE, A.POS"
            		//+ ", ISNULL(A.TEL, '') AS TEL, ISNULL(A.FAX, '') AS FAX"
            		//+ "  , ISNULL(A.POSTCODE, '') AS POSTCODE, ISNULL(A.ADDR1, '') AS ADDR1, ISNULL(A.ADDR2, '') AS ADDR2"
            		//+ "  , ISNULL(A.LINK, '') AS LINK, ISNULL(A.MAP, '') AS MAP"
            		+ "  , ISNULL(A.EMAIL, '') AS EMAIL, A.ETC_NAME"
            		+ "  , A.P_IDX, DISPLAY_YN, WF_CODE "
            		+ "  , (SELECT TOP 1 NAME FROM PRIEST C WHERE C.P_IDX=A.P_IDX) AS P_NAME  "
            		+ "  , B.DEL_YN "
            		+ " FROM ORGANIZATION A "
            		+ " LEFT OUTER JOIN ORG_HIERARCHY B ON B.ORG_IDX = A.ORG_IDX AND B.LV1 NOT IN ('01','02')  "
            		+ " WHERE 1=1 "+ strWhere;
        	
        	dto.daoTotalCount = super.executeCount(query0, true);
        	
            query = " SELECT A.* FROM (" + query0 + ") A WHERE A.RNUM BETWEEN "+startRnum+" AND "+endRnum;
            
            dto.daoList = super.executeQueryList(query);
            
            dto.setPaging(pageNo, pageSize);
            
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
        	free();
    	}
        
        D(_logger, Thread.currentThread().getStackTrace(), "DTO Result:"+dto );
        
        return dto;
    }

    /*
     * 관리자 : 기관단체 상세보기
     * (non-Javadoc)
     * @see kr.caincheon.church.dao.GyoguIntroDao#adm_org_view(java.util.Map)
     */
    @Override
	public Map admOrganizationView(Map _params)
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
        Map result = null;
        String query = "";
        String query_type = pnull(_params.get("query_type"));
        String o_idx = pnull(_params.get("o_idx"));
        String whereStr = "";
        try {
            if(query_type.equals("insert")) {
                query = "SELECT A.o_idx, A.NAME AS NAME  FROM ORGANIZATION A "
                		+ " WHERE A.O_IDX = '"+o_idx+"'";
                result = new java.util.HashMap();
                
            } else if(query_type.equals("modify")) {
                whereStr = " WHERE O_IDX = '"+o_idx+"'";
                query = "SELECT O_IDX, ORG_IDX, NAME, TEL, FAX, EMAIL, POSTCODE, ADDR1, ADDR2, MAP, HOMEPAGE"
                		+ ", CHURCH_IDX,  LINK, POS, P_IDX, ETC_NAME, ORG_IDX, DISPLAY_YN, WF_CODE "
                		+ " FROM ORGANIZATION "+whereStr;
                result = super.executeQueryMap(query);
            }
            
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
        	free();
    	}
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Result:"+result );
        return result;
    }

    /*
     * 사제 목록 - 기관/단체/수도회에서 사제 정보를 매핑 할 때 조회되는 combox list 임.
     * (non-Javadoc)
     * @see kr.caincheon.church.dao.GyoguIntroDao#adm_priest_list(java.util.Map)
     */
    @Override
	public CommonDaoDTO adm_priest_list(Map _params)
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
    	CommonDaoDTO dto = new CommonDaoDTO();
        
        String query = "";
        
        try {
        	pageNo = 1;
        	pageSize = 900000;
        	
            query = "SELECT P_IDX, NAME+' '+CHRISTIAN_NAME AS NAME FROM PRIEST ";
            dto.daoTotalCount = executeCount(query);
            
            query += " ORDER BY NAME ASC, ONUM ASC ";
            dto.daoList = executeQueryList(query);
            
            dto.setPaging(pageNo, pageSize);
            
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
        	free();
    	}
    	
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Result dto:"+dto);
    	
        return dto;
    }

    /**
     * (non-Javadoc)
     * @see kr.caincheon.church.dao.GyoguIntroDao#organizationInsert(java.util.Map)
     */
    @Override
	public boolean organizationInsert(Map _params)
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
    	PreparedStatement preparedStatement=null;
        boolean bReturn = true;

        String o_idx = pnull(_params.get("o_idx"));
        String name = pnull(_params.get("name"));
        String tel = pnull(_params.get("tel"));
        String fax = pnull(_params.get("fax"));
        String email = pnull(_params.get("email"));
        String postcode = pnull(_params.get("postcode"));
        String addr1 = pnull(_params.get("addr1"));
        String addr2 = pnull(_params.get("addr2"));
        String homepage = pnull(_params.get("homepage"));
        String p_idx = pnull(_params.get("p_idx"));
        String etc_name = pnull(_params.get("etc_name"));
        String org_idx = pnull(_params.get("org_idx"));
        String display_yn = pnull(_params.get("display_yn"));
        String wf_code = pnull(_params.get("wf_code")); // 세목분류코드
                
        int i = 0;
        try {
        	
            String query = "INSERT INTO ORGANIZATION "
            		+ " ( name, tel, fax, email, postcode, addr1, addr2, homepage, p_idx, etc_name, org_idx, display_yn, wf_code ) "
            		+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, wf_code) ";
            preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, tel);
            preparedStatement.setString(3, fax);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, postcode);
            preparedStatement.setString(6, addr1);
            preparedStatement.setString(7, addr2);
            preparedStatement.setString(8, homepage);
            preparedStatement.setString(9, p_idx);
            preparedStatement.setString(10, etc_name);
            preparedStatement.setString(11, org_idx);
            preparedStatement.setString(12, display_yn);
            preparedStatement.setString(13, wf_code);
            i = preparedStatement.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
            bReturn = false;
        } finally {
        	if(preparedStatement != null) try { preparedStatement.close(); } catch ( Exception e ) { e.printStackTrace(); }
        	free();
    	}
        return bReturn && i > 0;
    }

    /*
     * 관리자 :: 기관/단체/수도회 정보 수정하기
     * (non-Javadoc)
     * @see kr.caincheon.church.dao.GyoguIntroDao#orgModify(java.util.Map)
     */
    @Override
	public boolean organizationModify(Map _params)
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        PreparedStatement preparedStatement=null;
        boolean bReturn = false;
        
        
        String o_idx = pnull(_params.get("o_idx"));
        String name = pnull(_params.get("name"));
        String tel = pnull(_params.get("tel"));
        String fax = pnull(_params.get("fax"));
        String email = pnull(_params.get("email"));
        String postcode = pnull(_params.get("postcode"));
        String addr1 = pnull(_params.get("addr1"));
        String addr2 = pnull(_params.get("addr2"));
        String homepage = pnull(_params.get("homepage"));
        String p_idx = pnull(_params.get("p_idx"));
        String etc_name = pnull(_params.get("etc_name"));//호칭
        String org_idx = pnull(_params.get("org_idx"));//
        String display_yn = pnull(_params.get("display_yn"));
        String wf_code = pnull(_params.get("wf_code")); // 세목분류코드
        int i = 1;
        try {
            String query = "UPDATE ORGANIZATION  SET"
            		+ " name=?, tel=?, fax=?, email=?"
            		+ ", postcode=?, addr1=?, addr2=?, homepage=?, p_idx=?, etc_name=?, display_yn=? "
            		+ ", wf_code=?"
            		+ " WHERE org_idx=? and o_idx = ?";
            preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(i++, name);
            preparedStatement.setString(i++, tel);
            preparedStatement.setString(i++, fax);
            preparedStatement.setString(i++, email);
            preparedStatement.setString(i++, postcode);
            preparedStatement.setString(i++, addr1);
            preparedStatement.setString(i++, addr2);
            preparedStatement.setString(i++, homepage);
            preparedStatement.setString(i++, p_idx);
            preparedStatement.setString(i++, etc_name);
            preparedStatement.setString(i++, display_yn);
            preparedStatement.setString(i++, wf_code);
            
            preparedStatement.setString(i++, org_idx);
            preparedStatement.setString(i++, o_idx);
            
            i = 0;
            i = preparedStatement.executeUpdate();
            bReturn = true;
            // 변경 성공이면, 조직도 이름도 동시에 수정
            if(i>0) {
            	executeUpdate("UPDATE ORG_HIERARCHY /* 조직도원장 */ SET NAME='"+name+"' where org_idx="+org_idx);
            }
            
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERR-SQL: "+lastSQL, e );
        } finally {
        	if(preparedStatement != null) try { preparedStatement.close(); } catch ( Exception e ) { e.printStackTrace(); }
        	free();
    	}
        return bReturn && i > 0;
    }

    /*
     * 기관단체수도회 조직 삭제 flag 처리
     * (non-Javadoc)
     * @see kr.caincheon.church.dao.GyoguIntroDao#organizationDelete(java.util.Map)
     */
    @Override
	public boolean organizationDelete(Map _params)
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        boolean bReturn = true;
        String o_idx = pnull(_params.get("o_idx"));
        int i = 0 ;
        try {	
            String query = ""; 
            //query = "DELETE FROM ORGANIZATION where O_IDX = "+o_idx;
            
            query = "UPDATE ORGANIZATION SET DISPLAY_YN='N'  WHERE O_IDX = "+o_idx;
            i = executeUpdate(query);
            
            query = "UPDATE ORG_HIERARCHY SET "
            		+ "  DEL_YN='Y' "
            		+ ", DEL_DATE=CONVERT(CHAR(8), getdate(), 112) "
            		+ ", UPD_DATE=CONVERT(CHAR(8), getdate(), 112) "
            		+ " WHERE ORG_IDX = (SELECT ORG_IDX FROM ORGANIZATION WHERE o_idx="+o_idx+")";
            i = executeUpdate(query);
            
        } catch(Exception e) {
            e.printStackTrace();
            bReturn = false;
        } finally {
        	free();
    	}
        return bReturn && i > 0;
    }

    /**
     * front :: 통합검색
     * @see kr.caincheon.church.dao.GyoguIntroDao#unifySearch(java.util.Map, java.sql.Connection)
     */
	@Override
	public List unifySearch(Map _params, Connection conn) {
		D(_logger, Thread.currentThread().getStackTrace(), "Dao Called. params:"+_params );

		// 부서명, 부서소개
		List rtList = null;
		try {
			String srchText = pnull(_params, "srchText");
			lastSQL = "SELECT ROW_NUMBER() OVER(ORDER BY NAME ASC ) RNUM, O.* "
					+ " FROM ORGANIZATION O WHERE NAME LIKE '%"+srchText+"%'";
			rtList = executeQueryList(lastSQL, conn);
		} catch (Exception e) {
			_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+lastSQL, e);
		}
		
		D(_logger, Thread.currentThread().getStackTrace(), "Dao Returned. params:"+_params );
		return rtList;
	}

    
}
