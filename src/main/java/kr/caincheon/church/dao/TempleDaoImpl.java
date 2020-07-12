package kr.caincheon.church.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.caincheon.church.common.CAGiguInfoUtil;
import kr.caincheon.church.common.base.CommonDao;
import kr.caincheon.church.common.base.CommonDaoMultiDTO;
import kr.caincheon.church.common.base.Const;
import kr.caincheon.church.common.utils.UtilInt;

/**
 * front 본당 관련 DAO 클래스
 * @author benjamin
 */
@Repository("templeDao")
public class TempleDaoImpl extends CommonDao
    implements TempleDao
{

	private final Logger _logger = Logger.getLogger(getClass());
	
	/*
	 * 
	 */
    private void removeMisaEtcText(List list, String columnName, String removeTxt) {
        if(list != null && list.size() > 0) {
            for(int i = 0; i < list.size(); i++) {
                LinkedHashMap m = (LinkedHashMap)list.get(i);
                String columnVal = pnull(m.get(columnName));
                if(columnVal.indexOf(removeTxt) != -1)
                    m.put(columnName, columnVal.replace(removeTxt, ""));
            }

        }
    }

    /*
     * DB조회에서 누락된 요일 추가하기
     */
    private void insertOmittedYoil(List list) {
        if(list != null && list.size() > 0) {
            String churchIdx = "";
            Map wm = new HashMap();
            int j = 0;
            // 월~일,비고 메모리생성
            for(int j2 = Const.WeekCodes.length; j < j2; j++)
                wm.put(Const.WeekCodes[j][0], Const.WeekCodes[j][1]);
            // 메모리생성 - DB조회
            for(int i = 0; i < list.size(); i++) {
                LinkedHashMap m = (LinkedHashMap)list.get(i);
                wm.remove(m.get("WEEK").toString());
                if(m.get("CHURCH_IDX") != null)
                    churchIdx = pnull(m, "CHURCH_IDX");
            }
            // DB조회에서 누락된 요일 추가하기
            int ki=0;
            LinkedHashMap mmm=null;
            for(Iterator itr = wm.keySet().iterator(); itr.hasNext(); list.add(ki-1, mmm)) {
                String k = (String)itr.next();
                ki = Integer.parseInt(k);
                mmm = new LinkedHashMap();
                mmm.put("CHURCH_IDX", churchIdx);
                mmm.put("WEEK", k);
                mmm.put("MNAME", " ");
                mmm.put("UPD_DATE", " ");
            }
        }
    }

    /*
     * 
     */
    private void addWeekname(List list, String lastUpdDateX) {
        if(list != null && list.size() > 0) {
        	long lastUpded = 0L;
            for(int i = 0; i < list.size(); i++) {
                LinkedHashMap<String, Object> m = (LinkedHashMap<String, Object>)list.get(i);
                //
                if(lastUpdDateX.trim().length()<1) 
                	lastUpdDateX="2017-12-15";
                //
                Object okey = m.get("WEEK");
                int w = Integer.parseInt(okey.toString().trim());
                switch(w) {
                case 1: // '\001'
                    m.put("WEEK_NAME", "월"); break;
                case 2: // '\002'
                    m.put("WEEK_NAME", "화"); break;
                case 3: // '\003'
                    m.put("WEEK_NAME", "수"); break;
                case 4: // '\004'
                    m.put("WEEK_NAME", "목"); break;
                case 5: // '\005'
                    m.put("WEEK_NAME", "금"); break;
                case 6: // '\006'
                    m.put("WEEK_NAME", "토"); break;
                case 7: // '\0'
                    m.put("WEEK_NAME", "일"); break;
                case 8: // '\007'
                    m.put("WEEK_NAME", "비고"); m.put("UPD_DATE", "최종 업데이트 일자 : "+lastUpdDateX); break;
                }
            }
        }
    }

	/*
	 * 공소목록 조회
	 * (non-Javadoc)
	 * @see kr.caincheon.church.dao.TempleDao#vacancyList(java.util.Map, kr.caincheon.church.common.CommonDaoMultiDTO)
	 */
	@Override
    public void vacancyList(Map _params, CommonDaoMultiDTO dto)
        throws Exception
    {
		D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+", dto="+dto+"]" );
		
        String qo = pnull(_params.get("qo"), "B.NAME");
        String qk = pnull(_params.get("qk"));
        if(qo.length() == 0) qo = " B.NAME, A.NAME ";
        String query = "";
        int rtVal = 0;
        
        // basic sql
        query = "SELECT /*공소목록 조회*/ ROW_NUMBER() OVER(ORDER BY "+qo+") RNUM,"
        		+ "	      A.G_IDX, A.NAME AS GONGSO_NAME, (A.ADDR1+' ' + A.ADDR2) AS ADDR, "
        		+ "	      A.CHIEF_NAME, ISNULL(A.MAP, '') AS MAP, B.CHURCH_IDX,"
        		+ "	      B.NAME AS CHURCH_NAME, A.ETC, B.TEL, B.TRAFFIC_MASS, A.TRAFFIC "
        		+ "	FROM  CHURCH B "
        		+ "     , GONGSO A "
        		+ " WHERE A.CHURCH_IDX=B.CHURCH_IDX "+("".equals(qk) ? "" : "AND B.NAME LIKE '%"+qk+"%' ")
        		;
        
        // total count
        try {
            //query = "SELECT COUNT(1) FROM \n\t(SELECT ROW_NUMBER() OVER(ORDER BY B.NAME, A.NAME) RNUM,\n\t      A.G_IDX, \n\t      A.NAME AS GONGSO_NAME, \n\t      (A.ADDR1+' ' + A.ADDR2) AS ADDR, \n\t      A.CHIEF_NAME, \n\t      ISNULL(A.MAP, '') AS MAP, \n\t      B.CHURCH_IDX,\n\t      B.NAME AS CHURCH_NAME, \n\t      A.ETC, \n\t      B.TEL \n \tFROM  CHURCH B \n       , GONGSO A \n   WHERE A.CHURCH_IDX=B.CHURCH_IDX \n\t      " + ("".equals(qk) ? "" : "AND B.NAME LIKE '%"+qk+"%' ") + "\n) a";
            rtVal = super.executeCount("SELECT COUNT(1) FROM ("+query+") A ", false);
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally { 
        }
        dto.paging.setTotalCount(rtVal);
        
        // list
        List rows = null;
        try
        {
            //query = "SELECT * FROM ( \n\tSELECT ROW_NUMBER() OVER(ORDER BY "+qo+" ASC) RNUM,\n \tA.G_IDX, A.NAME AS GONGSO_NAME, (A.ADDR1+' ' + A.ADDR2) AS ADDR, \n \tA.CHIEF_NAME, ISNULL(A.MAP, '') AS MAP, B.CHURCH_IDX,\n \tB.NAME AS CHURCH_NAME, A.ETC, B.TEL, B.TRAFFIC_MASS \n \tFROM  CHURCH B \n       , GONGSO A \n   WHERE A.CHURCH_IDX=B.CHURCH_IDX \n \t" + ("".equals(qk) ? "" : " AND B.NAME LIKE '%"+qk+"%' ") + "\n ) A \n WHERE A.RNUM BETWEEN "+dto.paging.getPagingStart()+" AND "+dto.paging.getPagingEnd();
        	query = "SELECT * FROM ( "
        			+ query
        			+ " ) A WHERE A.RNUM BETWEEN "+dto.paging.getPagingStart()+" AND "+dto.paging.getPagingEnd();
            rows = super.executeQueryList(query);
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally { 
        	free();
        }
        
        dto.daoResult = rows;
        

        D(_logger, Thread.currentThread().getStackTrace(), "DAO Result:[paging="+dto.paging + ", list=" + dto.daoResult +"]" );
        
        
        return;
    }

    /*
     * 미사가 있는 본당 목록 조회
     * (non-Javadoc)
     * @see kr.caincheon.church.dao.TempleDao#inquireChurchListWithMisa(java.util.Map, kr.caincheon.church.common.CommonDaoMultiDTO)
     */
	@Override
    public void inquireChurchListWithMisa(Map _params, CommonDaoMultiDTO dto)
        throws Exception
    {
		D(_logger, Thread.currentThread().getStackTrace(), "DAO Called. params -> "+_params + "\n\t\tDTO->"+dto );
		
        String query="", priestSQL = "";
        String countQuery="";
        String pagingQuery="";
        int rtVal=0;
        
        String qo = pnull(_params.get("qo"), "B.NAME");
        String qk = pnull(_params.get("qk"));
        String church_idx = pnull(_params.get("church_idx"));
        String whereAnd = "";
        
        if(qk.length() > 0)
            whereAnd += " AND C.NAME LIKE '%"+qk+"%' ";
        if(church_idx.length() > 0)
            whereAnd += " AND C.CHURCH_IDX IN ("+church_idx+") ";
        int startRnum = (dto.paging.getPageNo() - 1) * dto.paging.getPageSize() + 1;
        int endRnum   = dto.paging.getPageNo() * dto.paging.getPageSize();
        
//        query       = "SELECT ROW_NUMBER() OVER(ORDER BY X.NAME ) ROWNUM , X.CHURCH_IDX, X.NAME AS CHURCH_NAME, X.ADDR2, X.TEL"
//        			+ ", CASE WHEN X.CHURCH_IDX='14102' THEN '김흥주,김혁중,조성진' WHEN X.CHURCH_IDX='14997' THEN '안규태,김승호' "
//        			+ "  ELSE (SELECT MAX(D.NAME1) "
//        			+ "             + CASE WHEN LEN(MAX(D.NAME2))>0 THEN ','+MAX(D.NAME2) ELSE '' END "
//        			+ "             + CASE WHEN LEN(MAX(D.NAME3))>0 THEN ','+MAX(D.NAME3) ELSE '' END "
//        			+ "             + CASE WHEN LEN(MAX(D.NAME4))>0 THEN ','+MAX(D.NAME4) ELSE '' END "
//        			+ "             + CASE WHEN LEN(MAX(D.NAME5))>0 THEN ','+MAX(D.NAME5) ELSE '' END "
//        			+ "             + CASE WHEN LEN(MAX(D.NAME6))>0 THEN ','+MAX(D.NAME6) ELSE '' END "
//        			+ "        FROM ( "
//        			+ "          SELECT C.church_idx, CASE RNUM WHEN 1 THEN C.NAME ELSE '' END NAME1"
//        			+ "               , CASE RNUM WHEN 2 THEN C.NAME ELSE '' END NAME2"
//        			+ "               , CASE RNUM WHEN 3 THEN C.NAME ELSE '' END NAME3"
//        			+ "               , CASE RNUM WHEN 4 THEN C.NAME ELSE '' END NAME4"
//        			+ "               , CASE RNUM WHEN 5 THEN C.NAME ELSE '' END NAME5"
//        			+ "               , CASE RNUM WHEN 6 THEN C.NAME ELSE '' END NAME6"
//        			+ "          FROM  ("
//        			+ "               SELECT ROW_NUMBER() OVER(ORDER BY B.P_IDX) RNUM, A.CHURCH_IDX, B.NAME "
//        			+ "               FROM CHURCH a "
//        			+ "               LEFT OUTER JOIN PRIEST b ON a.church_idx = '14'+b.j_gubun AND b.s_gubun='11' "
//        			+ "               WHERE A.church_idx = X.church_idx"
//        			+ "          ) C "
//        			+ "        ) D GROUP BY D.church_idx "
//        			+ "   ) END AS FATHER_NAME "
//        			+ " FROM CHURCH X "
//        			+ " WHERE 1=1 "+whereAnd;
        
        priestSQL = "ISNULL(STUFF((SELECT DISTINCT ','+P_NAME, ONUM "
        		+ "		FROM ("
        		+ "		SELECT CHURCH_IDX, P_NAME, ONUM FROM ("
        		+ "			SELECT ROW_NUMBER() OVER(ORDER BY PP.ONUM ASC) RNUM, RR.ORG_IDX, CC.CHURCH_IDX, PP.NAME P_NAME, PP.ONUM "
        		+ "			FROM ORG_DEPARTMENT_PRIEST_REL RR"
        		+ "			JOIN PRIEST PP ON PP.P_IDX = RR.P_IDX"
        		+ "			JOIN CHURCH CC ON CC.ORG_IDX = RR.ORG_IDX"
        		+ "			WHERE CONVERT(VARCHAR, GETDATE(), 112) BETWEEN RR.APPOINTMENT_START_DATE AND RR.APPOINTMENT_END_DATE "
        		+ "			) XX"
        		+ "	    ) B "
        		+ "	   WHERE B.CHURCH_IDX=C.CHURCH_IDX ORDER BY ONUM ASC FOR XML PATH('')), 1,1,''),'-') AS P_NAMES";
        
        query   = " SELECT DISTINCT ROW_NUMBER() OVER(ORDER BY C.NAME ) ROWNUM "
                + " 	, C.CHURCH_IDX"
                + " 	, C.NAME CHURCH_NAME"
                //+ " 	, ISNULL(R.P_IDX,'') AS P_IDX"
                //+ " 	, ISNULL(R.P_POSITION,'') AS P_POSITION"
                //+ " 	, ISNULL(P.NAME,'-') AS PRIEST_NAME"
                + ", " + priestSQL 
                + " 	, C.addr2 +' '+ C.addr1 AS ADDR"
                + " 	, C.TEL"
                + " FROM CHURCH C "
                //+ " left outer join ORG_DEPARTMENT_PRIEST_REL R ON R.ORG_IDX = C.ORG_IDX AND R.MAIN_ASSIGN_YN='Y'"
                //+ " left outer join PRIEST P ON P.p_idx = R.P_IDX"
                + " WHERE 1=1 "+whereAnd
        		;
        
        
        // total count
        rtVal = 0;
        try {
        	countQuery  = "SELECT COUNT(1) FROM ( "+query+" ) X1 ";
            rtVal = super.executeCount(countQuery, false);
	    } catch(Exception e) {
	    	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+countQuery, e);
	    } finally {
	    }
        dto.paging.setTotalCount(rtVal);
	    
        // list
        List rows = null;
        try {
        	pagingQuery = "SELECT * FROM ( "+query+" ) X1 WHERE ROWNUM BETWEEN "+startRnum+" AND "+endRnum;
            rows = super.executeQueryList(pagingQuery);
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+pagingQuery, e);
        } finally { 
        	free();
        }
        dto.daoResult = rows;
        
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Result:[paging="+dto.paging + ", list=" + dto.daoResult +"]" );
        
        return;
    }

	/*
	 * 본당별 미사 목록 조회
	 * (non-Javadoc)
	 * @see kr.caincheon.church.dao.TempleDao#selectMisaInfo(java.lang.String[], kr.caincheon.church.common.CommonDaoMultiDTO, java.util.Map)
	 */
	@Override
    public void selectMisaInfo(String churchIds[], CommonDaoMultiDTO dto, Map _params)
        throws SQLException
    {
		D(_logger, Thread.currentThread().getStackTrace(), "DAO Called. params->"+_params+", \n\t\t DTO -> " + dto );
		
        String churchIdxList = "";
        for(int i = 0; i < churchIds.length; i++) {
            churchIdxList += (i <= 0 ? "" : ", ");
            churchIdxList += churchIds[i];
        }

        String srchDiv = pnull(_params.get("srchDiv"));
        String yoil = pnull(_params.get("yoil"));
        String sHour = pnull(_params.get("sHour"));
        String sMin = pnull(_params.get("sMin"));
        String eHour = pnull(_params.get("eHour"));
        String eMin = pnull(_params.get("eMin"));
        String where2 = "";
        String where3 = "";
        
        if(srchDiv.equalsIgnoreCase("yoil"))
        {
            where2 = " and week = "+yoil;
            where3 = " where XX.mName is not null ";
        } else if(srchDiv.equalsIgnoreCase("time"))
        {
            where2 = " and misa_time >= '"+sHour+sMin+"' and misa_time <= '"+eHour+eMin+"'  ";
            where3 = " where XX.mName is not null ";
        }
        HashMap rtMisaMap = new HashMap();
        
        for(int i = 0; i < churchIds.length; i++)
        {
        	/* 2017-11-12 인 이유는 시스템 오픈일이기 때문임 */
            String query = "SELECT TT.* FROM ( "
            		+ "SELECT XX.* FROM ( "
            		+ " SELECT DISTINCT "
            		+ "  A.CHURCH_IDX"
            		+ ", CASE WHEN WEEK=0 THEN 7 ELSE WEEK END AS WEEK /*월~토,일 정렬을 위해서 일요일의 0을 7로 변경*/ "
            		+ ", STUFF(( "
            		+ "   SELECT ', ' "
            		+ "			+ CASE WHEN MISA_HOUR < 12 THEN '오전 '+CONVERT(VARCHAR, MISA_HOUR)+'시 ' WHEN MISA_HOUR=12 THEN '오후 12시' ELSE '오후 '+CONVERT(VARCHAR,(MISA_HOUR-12)) +'시 ' END "
            		+ "			+ CASE WHEN MISA_MIN > 0 THEN CONVERT(VARCHAR, MISA_MIN)+'분' ELSE '' END  "
            		+ "			+ CASE WHEN LEN(LTRIM(TXT))>0 THEN '('+TXT+')' ELSE '' END  "
            		+ "   FROM ("
            		+ "    SELECT ROW_NUMBER() OVER(ORDER BY WEEK,MISA_TIME ) ROWNUM1, Y.* FROM ( "
            		+ "     SELECT ROW_NUMBER() OVER(ORDER BY WEEK,MISA_HOUR,MISA_MIN ) ROWNUM0, WEEK, MISA_HOUR, MISA_MIN, TXT"
            		+ "          , REPLICATE('0', 2 - LEN(CONVERT(VARCHAR, MISA_HOUR)))+CONVERT(VARCHAR, MISA_HOUR)+REPLICATE('0',2-LEN(CONVERT(VARCHAR, MISA_MIN)))+CONVERT(VARCHAR, MISA_MIN) AS MISA_TIME "
            		+ "     FROM MISSA_INFO "
            		+ "     WHERE CHURCH_IDX IN ( "+churchIds[i]+" ) "
            		+ "    ) Y "
            		+ "   ) B "
            		+ "   WHERE B.WEEK = A.WEEK "+where2+" ORDER BY MISA_TIME ASC  FOR XML PATH('')  ),1,1,''"
            		+ "  ) AS MNAME"
            		+ " /*, ISNULL(UPD_DATE,'2017-11-12') AS UPD_DATE*/ "
            		+ " FROM  MISSA_INFO A /* misa time mon~sat */  "
            		+ " WHERE A.CHURCH_IDX IN ( "+churchIds[i]+" )   "
            		+ ") XX " + where3
            		+ " UNION ALL "
            		+ " SELECT  CHURCH_IDX, '8' AS WEEK, ETC /*, ISNULL(UPD_DATE,'2017-11-12') AS UPD_DATE*/ "
            		+ " FROM MISSA_ETC /* misa bigo */ WHERE CHURCH_IDX IN ( "+churchIds[i]+" ) "
            		+ ") TT "
            		+ "ORDER BY TT.CHURCH_IDX, TT.WEEK ";
            List list = super.executeQueryList(query);
            
            //
            insertOmittedYoil(list);
            
            // 마지막 수정 일자
            query = "SELECT CASE WHEN MAX(I.UPD_DATE) > MAX(E.UPD_DATE) THEN MAX(I.UPD_DATE) ELSE MAX(E.UPD_DATE) END AS MAX_UPD_DATE "
            		+ " FROM MISSA_INFO I, MISSA_ETC E"
            		+ " WHERE I.CHURCH_IDX = E.CHURCH_IDX AND I.CHURCH_IDX IN ("+churchIds[i]+")"
            		;
            Map lastUpdDate = executeQueryMap(query);
            addWeekname(list, pnull(lastUpdDate, "MAX_UPD_DATE")); // DB에 데이터가 없다면, DB이전일자 세팅
            
            rtMisaMap.put(churchIds[i], list);
        }

        dto.daoResult2 = rtMisaMap;

        D(_logger, Thread.currentThread().getStackTrace(), "DAO Result:[paging="+dto.paging + ", dto.daoResult2 list=" + dto.daoResult2 +"]" );
        
    }

	@Override
    public Map groupbyMailhallInRegion(Map _params)
        throws Exception
    {
		D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
		
		List<Map<String,Object>> gList1 = null, gList2 = null;
		
		try {
			gList1 = executeQueryList("SELECT CODE_INST AS GIGU_CODE, NAME AS GIGU_NAME FROM CODE_INSTANCE WHERE CODE='000004'");
			
			gList2 = executeQueryList("SELECT G.CODE_INST, G.NAME GIGU_NAME, C.CHURCH_IDX , C.NAME "
					+ " FROM CODE_INSTANCE G "
					+ " LEFT OUTER JOIN CHURCH C ON C.GIGU_CODE = G.CODE_INST "
					+ " WHERE G.CODE='000004' ORDER BY G.NAME, C.NAME ");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//LinkedHashMap rows = TempleUtil.groupbyMailhallInRegion();
		LinkedHashMap rtList = new LinkedHashMap();
		if(gList1 != null) {
			int total=0;
			for(Map row : gList1) {
				String giguName = pnull(row.get("GIGU_NAME"));
				
				String church_idsx="";
				LinkedHashMap churchs = new LinkedHashMap();
				int cnt = 0;
				for(Map row2 : gList2) {
					String giguName2 = pnull(row2.get("GIGU_NAME"));
					if(giguName.equals(giguName2)) {
						cnt++;
						total++;
						String cName = pnull(row2.get("NAME"));
						String cIdx  = pnull(row2.get("CHURCH_IDX"));
						churchs.put(cName, cIdx);
						churchs.put(cIdx, cName);
						church_idsx += ( church_idsx.length() > 0 ? ",":"") + cIdx;
					}
				}
				rtList.put(giguName+"_CNT", cnt);
				rtList.put(giguName+"_MAP", churchs);
				rtList.put(giguName       , church_idsx);
			}
			rtList.put("전체_CNT", total);
		}
		
        
        
        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+rtList );
        
        return rtList;
    }

	@Override
    public Map listMailhallInRegion(Map _params)
        throws Exception
    {
		D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
		
		/* 지구별 본당 목록인데, 본당명(키), 본당idx는(값)인 map을 리턴  */
        Map rows = CAGiguInfoUtil.getInstance().listMailhallInRegion(true);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+rows );
        
        return rows;
    }

	/*
	 * 본당둘러보기 이미지 조회
	 * (non-Javadoc)
	 * @see kr.caincheon.church.dao.TempleDao#selectChurchPictures(java.lang.String)
	 */
	@Override
    public List selectChurchPictures(String church_idx)
        throws Exception
    {
		D(_logger, Thread.currentThread().getStackTrace(), "DAO Called. params -> church_idx:"+church_idx );
        
        String query = "SELECT /* 본당둘러보기 이미지는 최대 10개만 보이게 */ TOP 10 * "
        		+ " FROM CHURCH_PHOTO "
        		+ " WHERE CHURCH_IDX in ("+church_idx+") "
        		+ " ORDER BY IMAGE_NAME ASC ";
        
        List rows = super.executeQueryList(query);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+rows );
        
        return rows;
    }

	/* 본당 기본정보 조회 */
	@Override
    public Map selectTotallyMainhallInfo(String church_idx)
        throws Exception
    {
		D(_logger, Thread.currentThread().getStackTrace(), "DAO Called. params -> church_idx:"+church_idx );
		
		// priest list
		String priests = " (SELECT STUFF( (SELECT ',' + X.PRIESTNAME FROM ("
				+ "SELECT ROW_NUMBER() OVER(ORDER BY P.ONUM ASC) RNUM, C.NAME + ' ' + P.NAME+'('+P.CHRISTIAN_NAME+')' AS PRIESTNAME "
				+ "FROM PRIEST P "
				+ "LEFT OUTER JOIN ORG_DEPARTMENT_PRIEST_REL PR ON PR.P_IDX   = P.P_IDX "
				+ "LEFT OUTER JOIN CHURCH                    CC ON CC.ORG_IDX = PR.ORG_IDX "
				+ "LEFT OUTER JOIN CODE_INSTANCE C ON C.CODE_INST = PR.P_POSITION AND  C.CODE='000003' "
				+ " WHERE CC.CHURCH_IDX=A.CHURCH_IDX " // 대체할 것 : "WHERE CC.CHURCH_IDX = 14052"
				+ ") X FOR XML PATH('') ),1,1,'')) "//AS PRIESTNAMES "
				+ " AS PRIESTNAMES "
				;

		// main sql
        Map rtMap = null;
        String query = "SELECT top 1 * "
        		+ priests
        		+ " FROM CHURCH A WHERE CHURCH_IDX="+church_idx;
        
        try {
        	rtMap = super.executeQueryMap(query);
            
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        }
        
        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+rtMap );
        
        return rtMap;
    }

	/**
	 * 성당정보내, 주임/보좌 신부 목록 조회
	 */
	@Override
    public Map selectPriestInChurch(String church_idx)
        throws Exception
    {
		D(_logger, Thread.currentThread().getStackTrace(), "DAO Called. params -> church_idx:"+church_idx );
		
        String ci = church_idx.substring(2);
        String query = "";
        
        if(ci == "997")
            query = "SELECT p_idx"
            		+ ", '본당신부' AS P_NAME "
            		+ ", name+' '+christian_name AS christian_name"
            		+ " FROM PRIEST A "
            		+ " WHERE (p_idx='1987002') OR (p_idx='2014003') "
            		+ " ORDER BY P_IDX";
        else if(ci == "102")
            query = "SELECT p_idx"
            		+ ", '본당신부' AS P_NAME "
            		+ ", name+' '+christian_name AS christian_name "
            		+ " FROM PRIEST A "
            		+ " WHERE (p_idx='1988006') OR (p_idx='2011010') OR (p_idx='2016012') "
            		+ " ORDER BY P_IDX" ;
        else {
//        	query = "SELECT p_idx "
//        			+ ", ISNULL(C2.NAME,'') AS P_NAME "
//        			+ ", A.name+' '+christian_name AS christian_name" 
//        			+ " FROM PRIEST A "
//        			+ " LEFT OUTER JOIN CODE_INSTANCE C2 ON C2.CODE_INST=A.APPELLATION AND C2.CODE='000002' "
//        			+ " WHERE GUBUN IN ('1','2','3') ORDER BY P_IDX";
        	query = "SELECT C.CHURCH_IDX, C.NAME"
        			+ ", ISNULL(R.MAIN_ASSIGN_YN,'N') MAIN_ASSIGN_YN, R.P_IDX, R.P_POSITION, R.APPOINTMENT_START_DATE, R.APPOINTMENT_END_DATE"
        			+ ", P.NAME P_NAME, P.CHRISTIAN_NAME"
        			+ ", P.APPELLATION, (SELECT NAME FROM NEWCAINCHEON.CODE_INSTANCE WHERE CODE='000002' AND CODE_INST=P.APPELLATION) APPELLATION_NM"
        			+ " FROM NEWCAINCHEON.CHURCH C  "
        			+ " LEFT OUTER JOIN NEWCAINCHEON.ORG_DEPARTMENT_PRIEST_REL R ON R.ORG_IDX = C.ORG_IDX AND CONVERT(VARCHAR, GETDATE(), 112) BETWEEN APPOINTMENT_START_DATE AND APPOINTMENT_END_DATE"
        			+ " LEFT OUTER JOIN NEWCAINCHEON.PRIEST P ON P.P_IDX = R.P_IDX "
        			+ " WHERE CHURCH_IDX="+church_idx
        			+ " ORDER BY CHURCH_IDX, P.ONUM, R.P_IDX";
        }
        
        Map<String,String> rtMap = new HashMap<String,String>();
        List<Map<String,Object>> rtList = super.executeQueryList(query);
        boolean isMainSinbu = false;
        String pKey = "주임신부", sKey = "보좌신부";
        for(int i = 0; i < rtList.size(); i++)
        {
            Map<String,Object> row = (Map<String,Object>)rtList.get(i);
            String pname = pnull(row,"P_NAME");
            String cname = pnull(row,"CHRISTIAN_NAME");
            String isMain = pnull(row,"MAIN_ASSIGN_YN");
            String appellationNm = pnull(row.get("APPELLATION_NM"));
            
            pname = pname + "("+cname+")" + appellationNm;
            
            
            if(!isMainSinbu && isMain.equals("Y")) {
            	/* 주임신부(APPELLATION='Y')가 여러개인 경우 첫번째를 주임신부로 설정하고 나머진 보좌신부로 강제로 처리
            	rtMap.put(pKey, pname);
            	isMainSinbu = true;
            	*/
            	/* DB 데이터에 따라, 주임이 여러명 나올 수 있음.*/
            	rtMap.put(pKey, (rtMap.containsKey(pKey) ? rtMap.get(pKey)+"<BR>"+pname : pname) );
            } else {
            	rtMap.put(sKey, (rtMap.containsKey(sKey) ? rtMap.get(sKey)+"<BR>"+pname : pname) );
            }
        }
        /*
        // 주임신부가 없다면, 강제로 onum이 첫번째(가장 오래된 신부)를 주임으로 설정 -> 이 로직은 현업 요구가 아님 :: 주임이 없다면, DB 데이터 오류
        if(!isMainSinbu) {
        	String t1 = rtMap.get(sKey);
        	rtMap.put(pKey, t1.substring(0, t1.indexOf("<BR>")));
        	rtMap.put(sKey, t1.substring(   t1.indexOf("<BR>") + 4));
        }
        */

        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+rtMap );
        
        return rtMap;
    }

	@Override
    public Map selectNewsInChurch(Map _params)
        throws Exception
    {
		D(_logger, Thread.currentThread().getStackTrace(), "DAO Called. params -> "+_params );
		
        int pageNo = UtilInt.pint(_params.get("pageNo"), 1);
        int pageSize = UtilInt.pint(_params.get("pageSize"), 20);
        int totalCount = 0;
        int startRnum = 0;
        int endRnum = 0;
        String b_idx = pnull(_params.get("b_idx"));
        String qk = pnull(_params.get("qk"));
        String church_idx = pnull(_params.get("church_idx"));
        String query = "";
        String churchWhere = "";
        
        if(church_idx.length() > 0)
            churchWhere += " AND CHURCH_IDX IN ("+church_idx+") ";
        if(qk.length() > 0)
            churchWhere += " AND CHURCH_IDX IN ( SELECT CHURCH_IDX FROM CHURCH WHERE NAME LIKE '%"+qk+"%') ";

        // 
        String top_cnt = " TOP 5 ";
        //
        query = "SELECT  A.*, CASE WHEN A.B_IDX='11' THEN '공지' ELSE '소식' END AS CATEGORY_NAME, ISNULL(C.NAME,'') AS CHURCH_NAME, ISNULL(D.NAME,'') AS DEPART_NAME "
        		+ " FROM ("
        		+ "   SELECT ROW_NUMBER() OVER(ORDER BY NOTICE_TYPE, REGDATE DESC) RNUM, B_IDX, NOTICE_TYPE, BL_IDX, TITLE, USER_ID, WRITER"
        		+ ", CONVERT(CHAR(10),  REGDATE, 120) REGDATE"
        		+ ",  HIT, C_IDX, CHURCH_IDX, DEPART_IDX, IS_SECRET"
        		+ ",  (SELECT STRFILENAME FROM NBOARD_UPLOAD A3 WHERE A3.BU_IDX IN (SELECT MAX(BU_IDX) FROM NBOARD_UPLOAD A4 WHERE A1.BL_IDX=A4.BL_IDX AND A4.FILETYPE is null) ) STRFILENAME"
        		+ ",  (SELECT FILENAME FROM NBOARD_UPLOAD A3 WHERE A3.BU_IDX IN (SELECT MAX(BU_IDX) FROM NBOARD_UPLOAD A4 WHERE A1.BL_IDX=A4.BL_IDX AND A4.FILETYPE is null) ) FILENAME"
        		+ ",  (SELECT FILEPATH FROM NBOARD_UPLOAD A3 WHERE A3.BU_IDX IN (SELECT MAX(BU_IDX) FROM NBOARD_UPLOAD A4 WHERE A1.BL_IDX=A4.BL_IDX AND A4.FILETYPE is null) ) FILEPATH"
        		+ ",  (SELECT COUNT(*) FROM NBOARD_UPLOAD A3 WHERE A1.BL_IDX=A3.BL_IDX AND IS_USE='Y') FILE_CNT"
//        		+ ",  (SELECT COUNT(*) FROM NBOARD_MEMO A2 WHERE A1.BL_IDX=A2.BL_IDX) MEMO_CNT "
        		+ "   FROM ( "
        				+ " SELECT "+top_cnt+" '1' AS NOTICE_TYPE, A.* FROM (SELECT "+top_cnt+" * FROM NBOARD WHERE B_IDX IN (11) "+churchWhere+" AND  IS_NOTICE='Y' ORDER BY REGDATE DESC) A "
        				+ " UNION ALL "
        				+ " SELECT '2' AS NOTICE_TYPE, * FROM NBOARD WHERE B_IDX IN (11) "+churchWhere+" AND BL_IDX NOT IN (SELECT "+top_cnt+" BL_IDX FROM NBOARD WHERE B_IDX IN (11) "+churchWhere+" AND  IS_NOTICE='Y' ORDER BY REGDATE DESC) "
        				+ ") A1 "
        		+ ") A "
        		+ " LEFT OUTER JOIN CHURCH C ON A.CHURCH_IDX=C.CHURCH_IDX "
        		+ " LEFT OUTER JOIN DEPARTMENT D ON A.DEPART_IDX=D.DEPART_IDX  "
        		+ "WHERE RNUM BETWEEN "+ ( (pageNo-1)*pageSize+1) + " AND " + (pageNo * pageSize)
        		+" ORDER BY A.NOTICE_TYPE, REGDATE DESC";
        List newsList = super.executeQueryList(query);
        
        //
        query = "SELECT COUNT(1) FROM  (SELECT ROW_NUMBER() OVER(ORDER BY NOTICE_TYPE, REGDATE DESC) RNUM"
        		+ ", NOTICE_TYPE, BL_IDX, TITLE, USER_ID, WRITER"
        		+ ", CONVERT(CHAR(10),  REGDATE, 120) REGDATE, HIT, C_IDX, CHURCH_IDX, DEPART_IDX, IS_SECRET"
        		+ ", (SELECT COUNT(*) FROM NBOARD_UPLOAD A3 WHERE A1.BL_IDX=A3.BL_IDX AND IS_USE='Y') FILE_CNT"
//        		+ ", (SELECT COUNT(*) FROM NBOARD_MEMO A2 WHERE A1.BL_IDX=A2.BL_IDX) MEMO_CNT "
        		+ "  FROM "
        		+ " ( "
        		+ "  SELECT "+top_cnt+" '1' AS NOTICE_TYPE, A.* FROM (SELECT "+top_cnt+" * FROM NBOARD WHERE B_IDX IN (11) "+churchWhere+" AND  IS_NOTICE='Y' ORDER BY BL_IDX DESC) A "
        		+ "  UNION ALL "
        		+ "  SELECT '2' AS NOTICE_TYPE, * FROM NBOARD WHERE B_IDX IN (11) "+churchWhere+" AND BL_IDX NOT IN (SELECT "+top_cnt+" BL_IDX FROM NBOARD WHERE B_IDX IN (11) "+churchWhere+" AND  IS_NOTICE='Y' ORDER BY BL_IDX DESC) "
   				+ " ) A1 "
   				+ ") A WHERE 1=1 ";
        totalCount = super.executeCount(query, false);

        //
        Map rtMap = new HashMap();
        rtMap.put("LIST", newsList);
        rtMap.put("TOTAL", ""+totalCount);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+rtMap );
        
        return rtMap;
    }

//	@Override
//    public void newsList(Map _params, CommonDaoMultiDTO dto)
//        throws Exception
//    {
//		D(_logger, Thread.currentThread().getStackTrace(), "DAO Called. params -> "+_params + "\n\t\tDTO->"+dto );
//		
//        String qk = pnull(_params.get("qk"));
//        String church_idx = pnull(_params.get("church_idx"));
//        String query = "";
//        if(church_idx.length() == 0 && (!"".equals(qk) || qk.length() > 0))
//        {
//            query = "SELECT CHURCH_IDX, NAME FROM CHURCH WHERE NAME LIKE '%"+qk+"%' ";
//            ResultSet rs = super.executeQuery(query);
//            if(rs != null) {
//                int i = 0;
//                while(rs.next()) {
//                    if(i > 0)
//                        church_idx = church_idx + ", ";
//                    church_idx = church_idx + rs.getString(1);
//                    i++;
//                }
//            }
//            _params.put("church_idx", church_idx);
//        }
//        Map rtMap = selectNewsInChurch(_params);
//        List list = (List)rtMap.get("LIST");
//        int count = UtilInt.pint(rtMap.get("TOTAL"));
//        if(church_idx.length() == 0 && (!"".equals(qk) || qk.length() > 0))
//            _params.remove("church_idx");
//        dto.daoResult = list;
//        dto.paging.setTotalCount(count);
//    }

	@Override
    public void newsVO(Map _params, CommonDaoMultiDTO dto)
        throws Exception
    {
		D(_logger, Thread.currentThread().getStackTrace(), "DAO Called. params -> "+_params + "\n\t\tDTO->"+dto );
		
        Map  result = null;
        List result2 = null;
        String b_idx  = pnull(_params.get("b_idx"));
        String bl_idx = pnull(_params.get("bl_idx"));
        String query = "SELECT * FROM NBOARD WHERE BL_IDX="+bl_idx+" ";
        
        try
        {
            String schTextGubun = pnull(_params.get("schTextGubun"));
            String schText = pnull(_params.get("schText"));
            String strWhere = "";
            if(schText.length() > 0)
                if(schTextGubun.equals("all"))
                    strWhere = " AND (TITLE LIKE '%"+schText+"%' OR CONTENT LIKE '%"+schText+"%') ";
                else
                    strWhere = " AND "+schTextGubun+" LIKE '%"+schText+"%' ";
            
            // attached files query
        	String imageSubQuery = "";
        	for(int i=1; i<6 ; i++) {
        		imageSubQuery += ",(SELECT STRFILENAME FROM (SELECT ROW_NUMBER() OVER(ORDER BY REGDATE DESC, BU_IDX DESC) RNUM, * FROM NBOARD_UPLOAD WHERE bl_idx=A.bl_idx ) X WHERE RNUM="+i+") AS STRFILENAME"+i;
        		imageSubQuery += ",(SELECT FILENAME FROM (SELECT ROW_NUMBER() OVER(ORDER BY REGDATE DESC, BU_IDX DESC) RNUM, * FROM NBOARD_UPLOAD WHERE bl_idx=A.bl_idx ) X WHERE RNUM="+i+") AS FILENAME"+i;
        		imageSubQuery += ",(SELECT FILEPATH FROM (SELECT ROW_NUMBER() OVER(ORDER BY REGDATE DESC, BU_IDX DESC) RNUM, * FROM NBOARD_UPLOAD WHERE bl_idx=A.bl_idx ) X WHERE RNUM="+i+") AS FILEPATH"+i;
        	}
            
            //
            query  = "SELECT BL_IDX, TITLE, CONTENT, USER_ID, WRITER, EMAIL, CONVERT(CHAR(10),  REGDATE, 120) REGDATE, CHURCH_IDX, IS_VIEW, DEPART_IDX, C_IDX, IS_SECRET,  HIT, IS_NOTICE, B_IDX"
            		+ imageSubQuery
            		+ ", ISNULL((SELECT NAME FROM DEPARTMENT B WHERE A.DEPART_IDX=B.DEPART_IDX), '') as  DEPART_NAME"
            		+ ", ISNULL((SELECT NAME FROM CHURCH B WHERE A.CHURCH_IDX=B.CHURCH_IDX), '') as  CHURCH_NAME"
            		+ ", ISNULL((SELECT NAME FROM NBOARD_CATEGORY B WHERE A.C_IDX=B.C_IDX), '') as  CATEGORY_NAME"
            		+ ", (SELECT MAX(BL_IDX)  FROM NBOARD X    WHERE B_IDX=A.B_IDX "+strWhere+" AND X.BL_IDX < A.BL_IDX) AS before_bl_idx"
            		+ ", (SELECT MIN(BL_IDX)  FROM NBOARD X    WHERE B_IDX=A.B_IDX "+strWhere+" AND X.BL_IDX > A.BL_IDX) AS next_bl_idx "
            		+ " FROM NBOARD A "
            		+ " WHERE B_IDX IN ("+b_idx+") AND BL_IDX='"+bl_idx+"'";
            result = super.executeQueryMap(query);

            // hit count
            try {
            	executeUpdate("update NBOARD set hit=hit+1 where bl_idx="+bl_idx);
            } catch (Exception e){e.printStackTrace();}
            
            //
            if(result.containsKey("FILEPATH")) {
                String p = pnull(result.get("FILEPATH"));
                p = p.replace("\\", "/");
                int i = p.indexOf("/upload/");
                if(i > -1)
                {
                    p = p.substring(p.indexOf("/upload/"));
                    result.put("FILEPATH", p);
                }
            }
            
            //
            query = "SELECT * FROM ( SELECT  TOP 1 'PREV' AS DIV, BL_IDX , CHURCH_IDX, B_IDX FROM NBOARD A WHERE B_IDX IN ("+b_idx+") AND BL_IDX < '"+bl_idx+"' ORDER BY BL_IDX DESC ) A "
            	 + " UNION ALL "
            	 + " SELECT  TOP 1 'POST' AS DIV, BL_IDX , CHURCH_IDX, B_IDX FROM NBOARD A WHERE B_IDX IN ("+b_idx+") AND BL_IDX > '"+bl_idx+"'  ORDER BY BL_IDX ASC";
            result2 = super.executeQueryList(query);
            
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        	free();
        }

        dto.daoResult4 = result;
//        dto.daoResult5 = result2!=null && result2.size() >= 1 ? result2.get(0) : new HashMap();
//        dto.daoResult6 = result2!=null && result2.size() > 1 ? result2.get(1)  : new HashMap();
        
    }

	@Override
    public void parishAlbumList(Map _params, CommonDaoMultiDTO dto)
        throws Exception
    {
		D(_logger, Thread.currentThread().getStackTrace(), "DAO Called. params -> "+_params + "\n\t\tDTO->"+dto );
		
        List rtList = null;
        int totalCount=0;
        
        String b_idx = pnull(_params.get("b_idx"));
        String bl_idx = pnull(_params.get("bl_idx"));
        String church_idx = pnull(_params.get("church_idx"));
        String qc = pnull(_params.get("qc"));
        String qk = pnull(_params.get("qk"));
        //String qo = pnull(_params.get("qo"));
        int pageNo = UtilInt.pint(_params.get("pageNo"), 1);
        int pageSize = UtilInt.pint(_params.get("pageSize"), 8);
        int startRnum = (pageNo - 1) * pageSize + 1;
        int endRnum = pageNo * pageSize;
        
        //
        String query    = "";
        String whereStr = " AND A1.IS_VIEW='Y'" ;
        String whereB   = "", whereS = "";
        
        // 'search keyword' or 'tab' event
        if(qk.trim().length() > 0 && "NAME".equalsIgnoreCase(qc))
            whereS = " AND A1.CHURCH_IDX IN (SELECT CHURCH_IDX FROM CHURCH WHERE NAME LIKE '%"+qk+"%') ";
        else if(church_idx.length() > 0)
            whereS = " AND A1.CHURCH_IDX IN ("+church_idx+") ";
        //
        //whereB = ( "ALL".equals(b_idx) ? "" : ( "".equals(b_idx) ? " AND B_IDX in (19,39) " : " AND B_IDX="+b_idx ) );
        //whereB = ( "ALL".equals(b_idx) ? "" : ( "".equals(b_idx) ? " AND C_IDX in (31,32) " : " AND B_IDX="+b_idx ) );
        whereB = ( "ALL".equals(b_idx) ? "" : ( "".equals(b_idx) ? " AND C_IDX in (1,2) " : " AND B_IDX="+b_idx ) );
        
        try
        {
        	// 아래 쿼리는 C_IDX=32 만 쿼리됨.
        	String tmp = whereStr + whereS + whereB;
        	query = "SELECT /* parishAlbumList.select */ A.*, B.NAME AS CATEGORY_NAME, C.NAME AS CHURCH_NAME, D.NAME AS DEPART_NAME "
            		+ " FROM ( "
            		+ "   SELECT  ROW_NUMBER() OVER(ORDER BY REGDATE DESC, BL_IDX DESC) RNUM"
            		+ "        ,  BL_IDX, B_IDX, TITLE, USER_ID, WRITER, convert(char(10),  REGDATE, 120) REGDATE, HIT, C_IDX, CHURCH_IDX, DEPART_IDX, A1.CONTENT"
                    + "        , (SELECT A.FILEPATH FROM (SELECT ROW_NUMBER() OVER(ORDER BY BU_IDX DESC) AS RNUM, FILEPATH FROM NBOARD_UPLOAD WHERE BL_IDX=A1.BL_IDX) A WHERE A.RNUM=1) AS FILEPATH "
                    + "        , (SELECT A.FILENAME FROM (SELECT ROW_NUMBER() OVER(ORDER BY BU_IDX DESC) AS RNUM, FILENAME FROM NBOARD_UPLOAD WHERE BL_IDX=A1.BL_IDX) A WHERE A.RNUM=1) AS FILENAME "
                    + "        , (SELECT A.STRFILENAME FROM (SELECT ROW_NUMBER() OVER(ORDER BY BU_IDX DESC) AS RNUM, STRFILENAME FROM NBOARD_UPLOAD WHERE BL_IDX=A1.BL_IDX) A WHERE A.RNUM=1) AS STRFILENAME "
//            		+ "        , (SELECT COUNT(*) FROM NBOARD_MEMO A2 WHERE A2.BL_IDX=A1.BL_IDX) AS MEMO_CNT "
            		+ "   FROM NBOARD A1 "
            		+ "   WHERE 1=1 " + tmp
            		+ " ) A "
            		+ " LEFT JOIN NBOARD_CATEGORY B ON A.C_IDX      = B.C_IDX "
            		+ " LEFT JOIN CHURCH          C ON A.CHURCH_IDX = C.CHURCH_IDX "
            		+ " LEFT JOIN DEPARTMENT      D ON A.DEPART_IDX = D.DEPART_IDX "
            		+ " WHERE RNUM BETWEEN "+startRnum+" AND "+endRnum
            		+ " ORDER BY REGDATE DESC, BL_IDX DESC "
            		;
        	
//            if("".equals(b_idx) || b_idx.equals("ALL"))
//                query = "SELECT /* parishAlbumList.select */ A.*, B.NAME AS CATEGORY_NAME, C.NAME AS CHURCH_NAME, D.NAME AS DEPART_NAME "
//                		+ " FROM ("
//                		+ "   SELECT  ROW_NUMBER() OVER(ORDER BY REF DESC, STEP DESC) RNUM"
//                		+ "        ,  BL_IDX, B_IDX, TITLE, USER_ID, WRITER, convert(char(10),  REGDATE, 120) REGDATE, HIT, REF, STEP, C_IDX, CHURCH_IDX, DEPART_IDX, A1.CONTENT"
////                		+ "        , (SELECT FILENAME FROM NBOARD_UPLOAD A3 WHERE A3.BU_IDX IN (SELECT MIN(BU_IDX) FROM NBOARD_UPLOAD A4 WHERE A1.BL_IDX=A4.BL_IDX) ) FILENAME "
////                		+ "        , (SELECT FILEPATH FROM NBOARD_UPLOAD A3 WHERE A3.BU_IDX IN (SELECT MIN(BU_IDX) FROM NBOARD_UPLOAD A4 WHERE A1.BL_IDX=A4.BL_IDX) ) FILEPATH "
//                        + "        , (SELECT A.FILENAME FROM (SELECT ROW_NUMBER() over(order by bu_idx desc) as rnum, FILENAME FROM NBOARD_UPLOAD WHERE BL_IDX=A1.BL_IDX) A WHERE A.RNUM=1) AS FILENAME "
//                        + "        , (SELECT A.FILEPATH FROM (SELECT ROW_NUMBER() over(order by bu_idx desc) as rnum, FILEPATH FROM NBOARD_UPLOAD WHERE BL_IDX=A1.BL_IDX) A WHERE A.RNUM=1) AS FILEPATH "
//                		+ "        , (SELECT COUNT(*) FROM NBOARD_MEMO   A2 WHERE A1.BL_IDX=A2.BL_IDX) MEMO_CNT   FROM NBOARD A1  " 
//    					+ " WHERE B_IDX = 19 "+whereStr+whereS
//                		+ "  ) A "
//                		+ " LEFT JOIN NBOARD_CATEGORY B ON A.C_IDX      = B.C_IDX "
//                		+ " LEFT JOIN CHURCH          C ON A.CHURCH_IDX = C.CHURCH_IDX "
//                		+ " LEFT JOIN DEPARTMENT      D ON A.DEPART_IDX = D.DEPART_IDX "
//                		+ " WHERE RNUM BETWEEN "+startRnum+" AND "+endRnum
//                		+ " ORDER BY REF DESC, STEP ";
//            else
//                query = "SELECT /* parishAlbumList.select */ A.*, B.NAME AS CATEGORY_NAME, C.NAME AS CHURCH_NAME, D.NAME AS DEPART_NAME FROM (   SELECT   ROW_NUMBER() OVER(ORDER BY REF DESC, STEP DESC) RNUM, "
//                		+ "BL_IDX, B_IDX, TITLE, USER_ID, WRITER, convert(char(10),  REGDATE, 120) REGDATE, HIT, REF, STEP, C_IDX, CHURCH_IDX, DEPART_IDX          ,"
//                		+ "  A1.CONTENT          "
////                		+ ", (SELECT FILENAME FROM NBOARD_UPLOAD A3 WHERE A3.BU_IDX IN (SELECT MIN(BU_IDX) FROM NBOARD_UPLOAD A4 WHERE A1.BL_IDX=A4.BL_IDX) ) FILENAME "
////                		+ ", (SELECT FILEPATH FROM NBOARD_UPLOAD A3 WHERE A3.BU_IDX IN (SELECT MIN(BU_IDX) FROM NBOARD_UPLOAD A4 WHERE A1.BL_IDX=A4.BL_IDX) ) FILEPATH "
//                		+ ", (SELECT COUNT(*) FROM NBOARD_MEMO   A2 WHERE A1.BL_IDX=A2.BL_IDX) MEMO_CNT    FROM NBOARD A1  "
//                		+ " WHERE 1=1 "+whereStr+whereS+whereB
//                		+ ") A  "
//                		+ " LEFT JOIN NBOARD_CATEGORY  B ON A.C_IDX      = B.C_IDX "
//                		+ " LEFT JOIN CHURCH          C ON A.CHURCH_IDX = C.CHURCH_IDX "
//                		+ " LEFT JOIN DEPARTMENT      D ON A.DEPART_IDX = D.DEPART_IDX "
//                		+ " WHERE RNUM BETWEEN "+startRnum+" AND "+endRnum
//                		+ " ORDER BY REF DESC, STEP ";
            rtList = super.executeQueryList(query);
            dto.daoResult4 = rtList;
            
            //
            query = "SELECT /* parishAlbumList.count */ COUNT(*) FROM NBOARD A1 WHERE B_IDX=19 "+whereStr+whereS;
            if( !("".equals(b_idx) || b_idx.equals("ALL")) )
                query += whereB;
            totalCount = super.executeCount(query, false);
            
            //
            if(bl_idx.length() > 0)
            {
                query = "SELECT /* parishAlbumList.top 1 */ TOP 1 A.* "
                		+ "FROM ( "
                		+ "  SELECT  ROW_NUMBER() OVER(ORDER BY REGDATE DESC) RNUM, BL_IDX, B_IDX, CHURCH_IDX, '\uC774\uC804' DIV "
                		+ "  FROM NBOARD A1 WHERE 1=1 "+whereStr+whereS+" AND BL_IDX < "+bl_idx
                		+ " ) A ORDER BY RNUM ASC";
                dto.daoResult5 = super.executeQueryMap(query);
                
                //
                query = "SELECT /* parishAlbumList.top 1 */ TOP 1 A.* "
                		+ "FROM ( "
                		+ "  SELECT ROW_NUMBER() OVER(ORDER BY REGDATE DESC) RNUM, BL_IDX, B_IDX, CHURCH_IDX, '\uC774\uD6C4' DIV "
                		+ "  FROM NBOARD A1 WHERE 1=1 "+whereStr+whereS+" AND BL_IDX > "+bl_idx
                		+ " ) A  ORDER BY RNUM DESC";
                dto.daoResult6 = super.executeQueryMap(query);
            }
        }
        catch(Exception e)
        {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally { 
        	free();
        }

        dto.paging.setTotalCount(totalCount);
        
        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+dto );
        
        return;
    }

	@Override
    public void albumVO(Map _params, CommonDaoMultiDTO dto)
        throws Exception
    {
		D(_logger, Thread.currentThread().getStackTrace(), "DAO Called. params -> "+_params + "\n\t\tDTO->"+dto );
		
        String bl_idx = pnull(_params.get("bl_idx"));
        String church_idx = pnull(_params.get("church_idx"));
    }
	
	/* 본당 앨범 조회 */
	@Override
    public Map templeAlbumContents(Map _params)
        throws Exception
    {
		D(_logger, Thread.currentThread().getStackTrace(), "DAO Called. params -> "+_params  );
		
        Map result = new HashMap();
        String b_idx = pnull(_params.get("b_idx"));
        String bl_idx = pnull(_params.get("bl_idx"));
        String church_idx = pnull(_params.get("church_idx"));
        String qc = pnull(_params.get("qc"));
        String qk = pnull(_params.get("qk"));
        String qo = pnull(_params.get("qo"));
        String query = "", whereS = "", whereStr = "";
        String tabs = "";
        
        //
        if(!pnull(_params.get("tabs")).equals(""))
            tabs = CAGiguInfoUtil.getInstance().getChurchListByTabCode(Integer.parseInt(pnull(_params.get("tabs"))));
        if(qk.trim().length() > 0 && "NAME".equalsIgnoreCase(qc))
            whereS = " AND X.CHURCH_IDX IN (SELECT CHURCH_IDX FROM CHURCH WHERE NAME LIKE '%"+qk+"%') ";
        else if(tabs.length() > 0)
            whereS = " AND X.CHURCH_IDX IN ("+tabs+") ";
        
        // Check a session
        Map session_map = (Map)_params.get(SESSION_MAP);
        String GROUPGUBUN = pnull(session_map, "GROUPGUBUN");
        if ( pnull(session_map, "WRITEYN").equals("Y")
        	&& ( "2".equals(GROUPGUBUN) || "3".equals(GROUPGUBUN) || "4".equals(GROUPGUBUN) )
        	&& pnull(session_map, "CHURCHIDX").length() > 0
        		) {
        	whereStr = " AND X.IS_VIEW IN ('Y', 'N') ";
        } else {
        	whereStr = " AND X.IS_VIEW = 'Y' ";
        }
        
        
        try {
        	//
            query  = "SELECT BL_IDX, TITLE, CONTENT, USER_ID, WRITER, EMAIL, CONVERT(CHAR(10),  REGDATE, 120) REGDATE, CHURCH_IDX, IS_VIEW, DEPART_IDX, C_IDX, IS_SECRET, HIT, IS_NOTICE, B_IDX"
            		+ ", ISNULL((SELECT NAME FROM DEPARTMENT      B WHERE A.DEPART_IDX=B.DEPART_IDX), '') as  DEPART_NAME"
            		+ ", ISNULL((SELECT NAME FROM CHURCH          B WHERE A.CHURCH_IDX=B.CHURCH_IDX), '') as  CHURCH_NAME"
            		+ ", ISNULL((SELECT NAME FROM NBOARD_CATEGORY B WHERE A.C_IDX=B.C_IDX), '') as  CATEGORY_NAME"
            		+ ", (SELECT MAX(BL_IDX) FROM NBOARD          X WHERE B_IDX=A.B_IDX "+whereStr+whereS+" AND X.BL_IDX < A.BL_IDX) AS before_bl_idx"
            		+ ", (SELECT MIN(BL_IDX) FROM NBOARD          X WHERE B_IDX=A.B_IDX "+whereStr+whereS+" AND X.BL_IDX > A.BL_IDX) AS next_bl_idx"
            		+ ", (SELECT FILENAME    FROM NBOARD_UPLOAD  A3 WHERE A3.BU_IDX IN (SELECT MAX(BU_IDX) FROM NBOARD_UPLOAD A4 WHERE A.BL_IDX=A4.BL_IDX AND A4.FILETYPE is null) ) FILENAME1 "
            		+ ", (SELECT FILEPATH    FROM NBOARD_UPLOAD  A3 WHERE A3.BU_IDX IN (SELECT MAX(BU_IDX) FROM NBOARD_UPLOAD A4 WHERE A.BL_IDX=A4.BL_IDX AND A4.FILETYPE is null) ) FILEPATH1 "
            		+ ", (SELECT STRFILENAME FROM NBOARD_UPLOAD  A3 WHERE A3.BU_IDX IN (SELECT MAX(BU_IDX) FROM NBOARD_UPLOAD A4 WHERE A.BL_IDX=A4.BL_IDX AND A4.FILETYPE is null) ) STRFILENAME1 "
            		+ " FROM NBOARD A "
            		+ " WHERE B_IDX='"+b_idx+"' AND BL_IDX='"+bl_idx+"'";
            result = super.executeQueryMap(query);
            
            // view count up
            executeUpdate("UPDATE NBOARD SET HIT=HIT+1 WHERE BL_IDX="+_params.get("bl_idx"));
            
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally { 
        	free();
        }
        
        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+result );
        
        return result;
    }

	/**
	 * 통합검색 :: 본당/공소 검색
	 * @see kr.caincheon.church.dao.TempleDao#unifySearch(java.util.Map, java.sql.Connection)
	 */
	@Override
	public List unifySearch(Map _params, Connection conn) throws Exception {
		D(_logger, Thread.currentThread().getStackTrace(), "Dao Called. params:"+_params );

		// 부서명, 부서소개
		List rtList = null;
		try {
			String srchText = pnull(_params, "srchText");
			

	        // basic sql
			lastSQL = "SELECT /*본당/공소 통합검색*/ ROW_NUMBER() OVER(ORDER BY TYPE, CHURCH_NAME ASC ) RNUM, X.* "
					+ "FROM ( "
					+ "  SELECT 'C' TYPE, CHURCH_IDX, '' G_IDX, NAME CHURCH_NAME"
					+ "  , '' GONGSO_NAME, TEL,JUBO_SAINT, '' CHIEF_NAME, ADDR2+' '+ADDR1 AS ADDR, ISNULL(TRAFFIC_MASS, TRAFFIC_SELF) AS TRAFFIC, MAP "
					+ "  FROM CHURCH WHERE NAME LIKE '%"+srchText+"%' OR ADDR1 LIKE '%"+srchText+"%' OR ADDR2 LIKE '%"+srchText+"%' OR JUBO_SAINT LIKE '%"+srchText+"%'"
					+ "  UNION ALL"
					+ "  SELECT 'G' TYPE, CHURCH_IDX, G_IDX, (SELECT NAME FROM NEWCAINCHEON.CHURCH WHERE CHURCH_IDX=A.CHURCH_IDX) AS CHURCH_NAME"
					+ "  , NAME GONGSO_NAME, '' TEL, '' JUBO_SAINT , CHIEF_NAME, ADDR1+' '+ADDR2 AS ADDR, TRAFFIC, MAP "
					+ "  FROM GONGSO A WHERE NAME LIKE '%"+srchText+"%' OR CHURCH_NAME LIKE '%"+srchText+"%' OR ADDR1 LIKE '%"+srchText+"%' OR ADDR2 LIKE '%"+srchText+"%'"
					+ ") X ";
			rtList = executeQueryList(lastSQL);
		} catch (Exception e) {
			_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+lastSQL, e);
		}
		
		D(_logger, Thread.currentThread().getStackTrace(), "Dao Returned. params:"+_params );
		return rtList;
	}

	@Autowired
    private CommonUtilDao commonUtilDao;
	
	/* 지구코드 조회 */
	@Override
	public List<Map> selectGiguCodeList(String orderbyCuase) throws Exception {
		return commonUtilDao.getCodeInstanceList("000004", orderbyCuase);
	}
	
	/* 지구별 본당 목록조회 */
	public List selectChurchListInGigu(String gigu_code) throws Exception {
		D(_logger, Thread.currentThread().getStackTrace(), "Dao Called. [params:"+gigu_code+"]" );

		// 부서명, 부서소개
		List rtList = null;
		try {
			String tmpSQL = gigu_code==null || gigu_code.length()==0 ? "" : " AND CODE_INST='"+gigu_code+"' ";
			lastSQL = " SELECT /* 지구별 소속본당 목록 조회 */ C.CHURCH_IDX, C.NAME CHURCH_NAME, I.CODE_INST GIGU_CODE, I.NAME AS GIGU_NAME "
					+ " FROM CHURCH C "
					+ " LEFT OUTER JOIN CODE_INSTANCE I ON I.CODE_INST = C.GIGU_CODE AND I.USE_YN='Y' AND I.CODE='000004' " + tmpSQL
					+ " ORDER BY C.GIGU_CODE ASC, C.NAME ASC ";
			rtList = executeQueryList(lastSQL);
		} catch (Exception e) {
			_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+lastSQL, e);
		}
		
		D(_logger, Thread.currentThread().getStackTrace(), "Dao Returned. : " + rtList );
		return rtList;
	}
	
}
