// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AdmGongsoDaoImpl.java

package kr.caincheon.church.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import kr.caincheon.church.common.base.CommonDao;
import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.base.CommonException;
import kr.caincheon.church.common.utils.UtilInt;

// Referenced classes of package kr.caincheon.church.dao:
//            AdmGongsoDao

@Repository("admGongsoDao")
public class AdmGongsoDaoImpl extends CommonDao
    implements AdmGongsoDao
{
	/*
	 * 공소목록조회
	 * (non-Javadoc)
	 * @see kr.caincheon.church.dao.AdmGongsoDao#admGongsoList(java.util.Map)
	 */
	@Override
    public CommonDaoDTO admGongsoList(Map _params)
    {
		CommonDaoDTO dto = new CommonDaoDTO();
		
        String query="", strWhere="";
        String b_idx = pnull(_params.get("searchGubun1"), "ALL");
        String idxGroup = "";
        
        int pageNo = UtilInt.pint(_params.get("pageNo"), 1);
        int pageSize = UtilInt.pint(_params.get("pageSize"), 10);
        int startRnum = (pageNo - 1) * pageSize + 1;
        int endRnum = pageNo * pageSize;
        String schTextGubun = pnull(_params.get("schTextGubun"));
        String schText = pnull(_params.get("searchText"));
        
        if(schText.length() > 0) {
            if(schTextGubun.equals("church"))
                strWhere = " AND B.NAME LIKE '%"+schText+"%' ";
            else
                strWhere = " AND A.NAME LIKE '%"+schText+"%' ";
        }
        
        try {
        	query = "SELECT ROW_NUMBER() OVER(ORDER BY A.G_IDX DESC) AS RNUM, A.G_IDX"
        			+ ", A.CHURCH_IDX, C.NAME AS CHURCH_NAME, A.NAME, A.CHIEF_NAME, A.ADDR1+A.ADDR2 AS ADDR, C.TEL, A.TRAFFIC "
        			+ " FROM GONGSO A  "
        			+ " RIGHT OUTER JOIN CHURCH C ON C.CHURCH_IDX = A.CHURCH_IDX "
        			+ " WHERE A.CHURCH_IDX IS NOT NULL AND A.CHURCH_IDX<>'' "+strWhere
        			;
        	dto.daoTotalCount = super.executeCount(query, true);
        	
        	query = "SELECT A.* FROM ("+query+") A WHERE A.RNUM BETWEEN "+startRnum+" AND "+endRnum;
            dto.daoList = super.executeQueryList(query);
            
        } catch(Exception e) {
            _logger.error("["+Thread.currentThread().getStackTrace()[1].getLineNumber()+"]ERROR SQL:"+lastSQL, e);
        } finally {
            free();
        }
        
        D(_logger, Thread.currentThread(), "Return DAO dt:"+dto);
        
        return dto;
    }


	@Override
    public Map admGongsoContents(Map _params)
    {
		D(_logger, Thread.currentThread(), "Call DAO:[_params="+_params+"]");
		
		Map result = null;
        
        String g_idx = pnull(_params.get("g_idx"));
        String query = "";
        try
        {
        	// 공조 정보 조회
            query = "SELECT A.*, B.NAME AS CHURCH_NAME "
            		+ " FROM GONGSO A "
            		+ " LEFT OUTER JOIN CHURCH B ON A.CHURCH_IDX = B.CHURCH_IDX "
            		+ " WHERE A.G_IDX='"+g_idx+"' ";
            result = executeQueryMap(query);
            
        } catch(Exception e) {
            _logger.error("["+Thread.currentThread().getStackTrace()[1].getLineNumber()+"]ERROR SQL:"+lastSQL, e);
        } finally {
            free();
        }
        
        D(_logger, Thread.currentThread(), "Return DAO:"+result);
        
        return result;
    }

	/*
	 * 공소 미사 정보 조회
	 * (non-Javadoc)
	 * @see kr.caincheon.church.dao.AdmGongsoDao#admGMissaList(java.util.Map, kr.caincheon.church.common.CommonDaoDTO, boolean)
	 */
	@Override
    public void admGMissaList(Map _params, CommonDaoDTO dto, boolean hasHtmlTag)
    {
		D(_logger, Thread.currentThread(), "Call DAO:[_params="+_params+"]");
		
        String g_idx = pnull(_params.get("g_idx"));
        String query = "";
        
        try
        {
        	// 공소 미사시간 목록
            query = "SELECT A.WT, A.WEEK AS WEEK_KEY, CASE WHEN A.WEEK='0' then '7' ELSE A.WEEK END AS WEEK_KEY2, TT.* "
            		+ "FROM  ("
            		+ " SELECT 0 WEEK, '일' WT "
            		+ " UNION  SELECT 1 WEEK, '월' WT "
            		+ " UNION  SELECT 2 WEEK, '화' WT "
            		+ " UNION  SELECT 3 WEEK, '수' WT "
            		+ " UNION  SELECT 4 WEEK, '목' WT "
            		+ " UNION  SELECT 5 WEEK, '금' WT "
            		+ " UNION  SELECT 6 WEEK, '토' WT "
            		+ " UNION  SELECT 8 WEEK, '비고' WT"
            		+ ") a "
            		+ "LEFT OUTER JOIN( SELECT * FROM "
            		+ "( "
            		+ " SELECT DISTINCT A.G_IDX, WEEK"
            		+ ", STUFF(( SELECT DISTINCT ', ' ";
            	if(hasHtmlTag) {
            		query+= " + '<span data-id=\"'+CAST(GMI_IDX AS VARCHAR)+'\" id=\"mnamespan'+CAST(GMI_IDX AS VARCHAR)+'\"><U>' /*For delete event*/ "
            				+ " + CASE WHEN MISA_HOUR < 12 THEN '오전 '+CONVERT(VARCHAR, MISA_HOUR)+'시 ' WHEN MISA_HOUR = 12 THEN '오후 12시' ELSE '오후 '+CONVERT(VARCHAR,(MISA_HOUR-12)) +'시 ' END "
            				+ " + CASE WHEN MISA_MIN > 0 THEN CONVERT(VARCHAR, MISA_MIN)+'분' ELSE '' END + CASE WHEN LEN(LTRIM(txt))>0 THEN '('+txt+')' ELSE '' END "
            				+ " + '</U></span>' "
		            		;
            	} else {
            		query+= " + CASE WHEN MISA_HOUR < 12 THEN '오전 '+CONVERT(VARCHAR, MISA_HOUR)+'시 ' WHEN MISA_HOUR = 12 THEN '오후 12시' ELSE '오후 '+CONVERT(VARCHAR,(MISA_HOUR-12)) +'시 ' END + CASE WHEN MISA_MIN > 0 THEN CONVERT(VARCHAR, MISA_MIN)+'분' ELSE '' END "
                    		+ "       + CASE WHEN LEN(LTRIM(TXT))>0 THEN '('+TXT+')' ELSE '' END "
                    		;
            	}
            	query+= "   FROM ( SELECT GMI_IDX, WEEK, MISA_HOUR, MISA_MIN, TXT, REPLICATE('0', 2 - LEN(CONVERT(VARCHAR, MISA_HOUR)))+CONVERT(VARCHAR, MISA_HOUR)+REPLICATE('0', 2 - LEN(CONVERT(VARCHAR, MISA_MIN)))+CONVERT(VARCHAR, MISA_MIN) AS MISA_TIME FROM GONGSO_MISSA_INFO WHERE g_idx='"+g_idx+"') B WHERE  B.WEEK = A.WEEK"
            		+ "   FOR XML PATH('') ) ,1,1,'') AS mName FROM GONGSO_MISSA_INFO A "
            		+ "   WHERE A.g_idx='"+g_idx+"' "
            		+ ") XX "
            		+ " UNION ALL "
            		+ " SELECT  G_IDX, '8' AS WEEK, ETC FROM GONGSO_MISSA_ETC WHERE G_IDX='"+g_idx+"') TT ON A.WEEK = TT.WEEK "
            		+ " ORDER BY CASE WHEN A.WEEK=0 THEN 7 WHEN A.WEEK=7 THEN 9 ELSE A.WEEK END "
            		;
            dto.otherList = super.executeQueryList(query);


    		// 본당 미사 최종 업데이트
            query = " SELECT CONVERT(CHAR(10),  MAX(LAST_UPD_DATE), 120) AS LAST_UPD_DATE FROM ( "
            		+ " SELECT MAX(UPD_DATE) LAST_UPD_DATE FROM GONGSO_MISSA_ETC WHERE G_IDX=" + g_idx
            		+ " UNION"
            		+ " SELECT MAX(UPD_DATE) LAST_UPD_DATE FROM GONGSO_MISSA_INFO WHERE G_IDX="+ g_idx +") x "
            		;
    		try {
    			dto.otherData = executeColumnOne(query, 1);
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
            
        } catch(Exception e) {
            _logger.error("["+Thread.currentThread().getStackTrace()[1].getLineNumber()+"]ERROR SQL:"+lastSQL, e);
        } finally {
            free();
        }
        
        D(_logger, Thread.currentThread(), "Return DTO:"+dto);
        
    }

	@Override
    public boolean admGongsoInsert(Map _params)
    {
		D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
		
		boolean bReturn = true;
        int rn = 0;
        String query="", max_g_idx = "";
        PreparedStatement preparedStatement=null;
        try
        {
        	//(" SELECT ISNULL(MAX(G_IDX), 0) + 1 AS G_IDX FROM GONGSO ");
        	max_g_idx = String.valueOf(executeColumnMax("GONGSO", "G_IDX", ""));
        } catch(Exception e) {
            _logger.error("["+Thread.currentThread().getStackTrace()[1].getLineNumber()+"]ERROR SQL:"+lastSQL, e);
        }
        
        try
        {
            query = "INSERT INTO GONGSO (G_IDX,NAME,POSTCODE,ADDR1,ADDR2, MAP,TRAFFIC,CHIEF_NAME"
            		+ ", CHURCH_IDX"
            		+ ", ETC, INSERTDT,ADM_ID "
            		+ ")  VALUES (?, ?, ?, ?, ?, ?, ?, ?"
            		+ ", ? "
            		+ ", ?, GETDATE(), ?) ";
            D(_logger, Thread.currentThread().getStackTrace(), "lastSQL:"+lastSQL );
            preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1, max_g_idx);
            preparedStatement.setString(2, pnull(_params.get("name")));
            preparedStatement.setString(3, pnull(_params.get("postcode")));
            preparedStatement.setString(4, pnull(_params.get("addr1")));
            preparedStatement.setString(5, pnull(_params.get("addr2")));
            preparedStatement.setString(6, pnull(_params.get("map")));
            preparedStatement.setString(7, pnull(_params.get("traffic")));
            preparedStatement.setString(8, pnull(_params.get("chief_name")));
            
            preparedStatement.setString(9, pnull(_params.get("church_idx")));
            
            preparedStatement.setString(10, pnull(_params.get("etc")));
            preparedStatement.setString(11, pnull(_params.get("adm_id")));
            rn = preparedStatement.executeUpdate();
            
            D(_logger, Thread.currentThread().getStackTrace(), "DAO [inserted1:"+rn+"]" );
            
        } catch(Exception e) {
            _logger.error("["+Thread.currentThread().getStackTrace()[1].getLineNumber()+"]ERROR SQL:"+lastSQL, e);
        } finally {
        	if(preparedStatement != null) try { preparedStatement.close(); } catch(SQLException e) { e.printStackTrace(); }
        }
        
        // 미사시간에서 기타 정보만..
        try {
            query = "INSERT INTO GONGSO_MISSA_ETC  (g_idx, etc, upd_date)  VALUES (?, ?, GETDATE())";
            D(_logger, Thread.currentThread().getStackTrace(), "lastSQL:"+lastSQL );
            preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1, max_g_idx);
            preparedStatement.setString(2, pnull(_params.get("txt8")));
            rn += preparedStatement.executeUpdate();

            D(_logger, Thread.currentThread().getStackTrace(), "DAO [inserted2:"+rn+"]" );
            
        } catch(Exception e) {
            _logger.error("["+Thread.currentThread().getStackTrace()[1].getLineNumber()+"]ERROR SQL:"+lastSQL, e);
        } finally {
        	if(preparedStatement != null) try { preparedStatement.close(); } catch(SQLException e) { e.printStackTrace(); }
            free();
        }
        
        return rn >= 1;
    }

	@Override
    public boolean admGongsoModify(Map _params)
    {
		D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
		
        int rn=0;
        PreparedStatement preparedStatement = null;
        String query="";
        String g_idx = pnull(_params.get("g_idx"));
        
        try {
            query = "UPDATE GONGSO SET  name= ?, postcode = ?,  addr1 = ?,  traffic = ?,  chief_name = ?,  etc = ?,  church_idx = ?,  updateDT = getdate(),  adm_id = ?  WHERE g_idx = ? ";
            D(_logger, Thread.currentThread().getStackTrace(), "lastSQL:"+lastSQL );
            preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1, pnull(_params.get("name")));
            preparedStatement.setString(2, pnull(_params.get("postcode")));
            preparedStatement.setString(3, pnull(_params.get("addr1")));
            preparedStatement.setString(4, pnull(_params.get("traffic")));
            preparedStatement.setString(5, pnull(_params.get("chief_name")));
            preparedStatement.setString(6, pnull(_params.get("etc")));
            preparedStatement.setString(7, pnull(_params.get("church_idx")));
            preparedStatement.setString(8, pnull(_params.get("adm_id")));
            preparedStatement.setString(9, g_idx);
            rn = preparedStatement.executeUpdate();

            D(_logger, Thread.currentThread().getStackTrace(), "DAO [updated1:"+rn+"]" );
            
        } catch(Exception e) {
            _logger.error("["+Thread.currentThread().getStackTrace()[1].getLineNumber()+"]ERROR SQL:"+lastSQL, e);
        } finally {
        	if(preparedStatement != null) try { preparedStatement.close(); } catch(SQLException e) { e.printStackTrace(); }
        }
        
        try
        {
            query = " IF EXISTS(SELECT * FROM GONGSO_MISSA_ETC WHERE G_IDX=?) "
            		+ " UPDATE GONGSO_MISSA_ETC SET ETC=?, UPD_DATE=GETDATE() WHERE G_IDX=? "
            		+ " ELSE  INSERT INTO GONGSO_MISSA_ETC (G_IDX, ETC, UPD_DATE) VALUES(?, ?, GETDATE()); ";
            D(_logger, Thread.currentThread().getStackTrace(), "lastSQL:"+lastSQL );
            preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1, g_idx);
            preparedStatement.setString(2, pnull(_params.get("txt8")));
            preparedStatement.setString(3, g_idx);
            preparedStatement.setString(4, g_idx);
            preparedStatement.setString(5, pnull(_params.get("txt8")));
            rn += preparedStatement.executeUpdate();

            D(_logger, Thread.currentThread().getStackTrace(), "DAO [upsert2:"+rn+"]" );
        } catch(Exception e) {
            _logger.error("["+Thread.currentThread().getStackTrace()[1].getLineNumber()+"]ERROR SQL:"+lastSQL, e);
        } finally {
        	if(preparedStatement != null) try { preparedStatement.close(); } catch(SQLException e) { e.printStackTrace(); }
            free();
        }
        
        return rn >= 1;
    }

	@Override
    public boolean admGongsoDelete(Map _params)
    {
		D(_logger, Thread.currentThread().getStackTrace(), "DAO Called[params:"+_params+"]" );
		
        int rn=0;
        String g_idx = pnull(_params.get("g_idx"), "0");
        
        try
        {
        	rn = executeUpdate("delete from GONGSO where g_idx="+g_idx);
        	D(_logger, Thread.currentThread().getStackTrace(), "DAO [GONGSO deleted:"+rn+"]" );
        	
        	rn = executeUpdate("delete from GONGSO_MISSA_INFO where g_idx="+g_idx);
        	D(_logger, Thread.currentThread().getStackTrace(), "DAO [GONGSO_MISSA_INFO deleted:"+rn+"]" );
        	
        	rn = executeUpdate("delete from GONGSO_MISSA_ETC where g_idx="+g_idx);
        	D(_logger, Thread.currentThread().getStackTrace(), "DAO [GONGSO_MISSA_ETC deleted:"+rn+"]" );
        	
        } catch(Exception e) {
            _logger.error("["+Thread.currentThread().getStackTrace()[1].getLineNumber()+"]ERROR SQL:"+lastSQL, e);
        } finally {
        	free();
        }
        
        return rn >= 1;
    }

	/*
	 * 공소의 미사시간을 등록/삭제 
	 * (non-Javadoc)
	 * @see kr.caincheon.church.dao.AdmGongsoDao#admGongsoMissaManage(java.util.Map)
	 */
	@Override
    public String admGongsoMissaManage(Map _params)
        throws CommonException
    {
        String result = "";
        String query = "";
        
        String g_idx    = pnull(_params, "g_idx"); // 공소 
        String gmi_idx  = pnull(_params, "gmi_idx"); // 공소 미사(삭제시만 값있음)
        String week        = pnull(_params, "week");
        String txt         = pnull(_params, "txt");
        String missa_hour  = pnull(_params, "missa_hour");
        String missa_min   = pnull(_params, "missa_min");
        
        String p_mType   = pnull(_params, "p_mType");
        
        try {
        	if("I".equals(p_mType)) {
	        	query = "INSERT INTO GONGSO_MISSA_INFO (GMI_IDX, G_IDX, WEEK, MISA_HOUR, MISA_MIN, TXT, UPD_DATE)"
	        			+ " VALUES ( "
	        			+ " (SELECT ISNULL(MAX(GMI_IDX),0) + 1 FROM GONGSO_MISSA_INFO)"
	        			+ ","+g_idx
	        			+ ","+week+", "+missa_hour+", "+missa_min+", '"+txt+"', GETDATE())";
	        	int i = executeUpdate(query);
	        	if(i > 0) {
		        	query = "SELECT GMI_IDX FROM GONGSO_MISSA_INFO  WHERE WEEK="+week+" AND MISA_HOUR="+missa_hour+" AND MISA_MIN="+missa_min+" AND G_IDX="+g_idx;
		        	result = String.valueOf(executeColumnOne(query, 1));
	        	}
        	
        	} else if("D".equals(p_mType)) {
        		int rn = executeUpdate("delete from GONGSO_MISSA_INFO WHERE G_IDX="+g_idx+" AND GMI_IDX="+gmi_idx);
            	D(_logger, Thread.currentThread().getStackTrace(), "DAO [GONGSO_MISSA_INFO deleted:"+rn+"]" );
            	result = rn == 1 ? "1":"";
        	}
            
        }
        catch(SQLException e)
        {
            throw new CommonException(e.getMessage(), "ERR-D013", e);
        } finally {
        	free();
        }
        return result;
    }

    private final Logger _logger = Logger.getLogger(getClass());
}
