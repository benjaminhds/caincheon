// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AdmChurchDaoImpl.java

package kr.caincheon.church.dao;

import java.io.File;
import java.sql.CallableStatement;
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
import kr.caincheon.church.common.base.CommonException;
import kr.caincheon.church.common.utils.UtilInt;

// Referenced classes of package kr.caincheon.church.dao:
//            AdmChurchDao

@Repository("admChurchDao")
public class AdmChurchDaoImpl extends CommonDao
    implements AdmChurchDao
{

	private final Logger _logger = Logger.getLogger(getClass());

	@Override
    public List admChurchList(Map _params)
    {
        String query="";
        String strWhere="";
        List result=null;
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        int pageNo = UtilInt.pint(_params.get("pageNo"), 1);
        int pageSize = UtilInt.pint(_params.get("pageSize"), 10);
        int startRnum = (pageNo - 1) * pageSize + 1;
        int endRnum = pageNo * pageSize;
        String schTextGubun = pnull(_params.get("schTextGubun"));
        String schText = pnull(_params.get("searchText"));
        
        
        if(schText.length() > 0) {
            if("group".equals(schTextGubun)) {
                strWhere  = " AND O.ORG_IDX IN ( SELECT ORG_IDX FROM ORG_HIERARCHY X WHERE LV2 IN ("
                		+ "SELECT LV2 FROM ORG_HIERARCHY X WHERE LV1='02' and lv3!='000' and NAME LIKE '%"+schText+"%'"
                		+ ") )";
                
                strWhere  = " AND C.GIGU_CODE IN (SELECT CODE_INST FROM NEWCAINCHEON.CODE_INSTANCE WHERE CODE='000004' AND NAME LIKE '%"+schText+"%') ";
            } else {
                strWhere  = " AND C.NAME LIKE '%"+schText+"%' ";
            }
        }
        
        try {
            query = " SELECT A.* FROM ( "
            		+ "  SELECT ROW_NUMBER() OVER(ORDER BY C.NAME) AS RNUM"
            		+ "       , C.ORG_IDX, O.LV1+O.LV2+O.LV3 AS DEPART_CODE "
            		+ "       , CASE WHEN C.CHURCH_IDX IS NULL THEN 'I' ELSE 'M' END MODE"
            		+ "       , ISNULL(C.CHURCH_IDX,'') AS CHURCH_IDX "
            		+ "       , B.NAME AS RIDX_GIGUNAME "
            		+ "       , C.NAME, C.TEL, C.FAX, C.EMAIL, O.DEL_YN "
            		+ "  FROM CHURCH C "
            		+ "  LEFT OUTER JOIN CODE_INSTANCE B ON B.CODE_INST = C.GIGU_CODE AND B.CODE='000004' /* 지구코드 */ "
            		+ "  LEFT OUTER JOIN ORG_HIERARCHY O ON O.ORG_IDX = C.ORG_IDX AND O.LV1='02' AND O.LV3!='000' "
            		+ "  WHERE 1=1 "+strWhere
            		+ " ) A"
            		+ " WHERE A.RNUM BETWEEN "+startRnum+" AND "+endRnum;
            result = super.executeQueryList(query);

        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        }
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Result:"+result );
        return result;
    }

	@Override
    public int admChurchListCount(Map _params)
    {
        String strWhere="";
        int result = 0;
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        String query = "";
        String schTextGubun = pnull(_params.get("schTextGubun"));
        String schText = pnull(_params.get("searchText"));
        
        
        if(schText.length() > 0) {
            if("group".equals(schTextGubun)) {
                strWhere  = " AND O.ORG_IDX IN ( SELECT ORG_IDX FROM ORG_HIERARCHY X WHERE LV2 IN ("
                		+ "SELECT LV2 FROM ORG_HIERARCHY X WHERE LV1='02' and lv3!='000' and NAME LIKE '%"+schText+"%'"
                		+ ") )";
            } else {
                strWhere  = " AND C.NAME LIKE '%"+schText+"%' ";
            }
        }
        
        try {
            query = " SELECT COUNT(1) FROM ( "
					+ " SELECT ROW_NUMBER() OVER(ORDER BY C.NAME) AS RNUM"
					+ "       , C.ORG_IDX, O.LV1+O.LV2+O.LV3 AS DEPART_CODE "
					+ "       , C.NAME, C.TEL, C.FAX, C.EMAIL "
					+ "  FROM CHURCH C "
            		+ "  LEFT OUTER JOIN ORG_HIERARCHY O ON O.ORG_IDX = C.ORG_IDX AND O.LV1='02' AND O.LV3!='000' "
            		+ "  WHERE 1=1 "+strWhere
            		+ ") A ";
            result = super.executeCount(query, false);

        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        	free();
        }
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Result:"+result );
        return result;
    }
	
	@Override
    public void admMissaList(Map _params, CommonDaoDTO dto)
    {
					
        String query="";
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        String church_idx  = pnull(_params.get("church_idx"));
        String church_code = pnull(_params.get("church_code"));
        
        try
        {
        	//
            query = "SELECT A.WT, A.WEEK AS WEEK_KEY, TT.* FROM ("
            		+ " SELECT 0 WEEK, '일' WT UNION  SELECT 1 WEEK, '월' WT UNION  SELECT 2 WEEK, '화' WT UNION  SELECT 3 WEEK, '수' WT UNION  SELECT 4 WEEK, '목' WT "
            		+ " UNION  "
            		+ " SELECT 5 WEEK, '금' WT UNION  SELECT 6 WEEK, '토' WT UNION  SELECT 7 WEEK, '비고' WT "
            		+ " ) A "
            		+ " LEFT OUTER JOIN ( "
            			+ " SELECT * FROM ( "
	            			+ " SELECT DISTINCT  A.CHURCH_IDX, week"
	            			+ " , STUFF( ( SELECT DISTINCT ', ' "
	            			+ " + '<span data-id=\"'+CAST(MI_IDX AS VARCHAR)+'\" id=\"mnamespan'+CAST(MI_IDX AS VARCHAR)+'\"><U>' /*For delete event*/ "
	            			+ " + CASE WHEN MISA_HOUR < 12 THEN '오전 '+CONVERT(VARCHAR, MISA_HOUR)+'시 ' WHEN MISA_HOUR = 12 THEN '오후 12시' ELSE '오후 '+CONVERT(VARCHAR,(MISA_HOUR-12)) +'시 ' END "
	            			+ " + CASE WHEN MISA_MIN > 0 THEN CONVERT(VARCHAR, MISA_MIN)+'분' ELSE '' END + CASE WHEN LEN(LTRIM(txt))>0 THEN '('+txt+')' ELSE '' END "
	            			+ " + '</U></span>' "
	            			+ " FROM ( "
	            				+ " SELECT MI_IDX, WEEK, MISA_HOUR, MISA_MIN, TXT, REPLICATE('0', 2 - LEN(CONVERT(VARCHAR, MISA_HOUR)))+CONVERT(VARCHAR, MISA_HOUR)+REPLICATE('0', 2 - LEN(CONVERT(VARCHAR, MISA_MIN)))+CONVERT(VARCHAR, MISA_MIN) AS MISA_TIME "
	            				+ " FROM MISSA_INFO "
	            				+ " WHERE CHURCH_IDX="+church_idx
	            				+ ") B "
	            				+ " WHERE  B.WEEK = A.WEEK FOR XML PATH('') ), 1, 1,'') AS mName "
		            		+ " FROM  MISSA_INFO A "
		            		+ " WHERE A.CHURCH_IDX="+church_idx
	            		+ ") XX "
	            		+ " UNION ALL  "
	            		+ " SELECT  CHURCH_IDX, '7' AS WEEK, ETC  FROM MISSA_ETC "
	            		+ " WHERE CHURCH_IDX="+church_idx
            		+ ") TT ON A.WEEK = TT.WEEK "
            		+ " ORDER BY CASE WHEN A.WEEK=0 THEN 7 WHEN A.WEEK=7 THEN 9 ELSE A.WEEK END ";
            dto.otherList = super.executeQueryList(query);
            
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        }
        
		// 본당 미사 최종 업데이트
        query = " SELECT CONVERT(CHAR(10),  MAX(LAST_UPD_DATE), 120) AS LAST_UPD_DATE FROM ( "
        		+ " SELECT MAX(UPD_DATE) LAST_UPD_DATE FROM MISSA_ETC WHERE CHURCH_IDX=" + church_idx
        		+ " UNION"
        		+ " SELECT MAX(UPD_DATE) LAST_UPD_DATE FROM MISSA_INFO WHERE CHURCH_IDX="+ church_idx +") x "
        		;
        Map mmm = new HashMap();
		try {
			mmm = executeQueryMap(query);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dto.otherData = pnull(mmm, "LAST_UPD_DATE");
			free();
		}
		
                
        D(_logger, Thread.currentThread().getStackTrace(), "DAO dto:"+dto );
    }

	@Override
    public Map admChurchContents(Map _params)
    {
		D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
		
        Map result = null;
        String org_idx = pnull(_params.get("org_idx"));
        String church_idx  = pnull(_params.get("church_idx"));
        String church_code = pnull(_params.get("church_code"));
        int attached_files = ipnull(_params, "attached_files", 5); //본당둘러보기는 최대 파일 10개(예외), 첨부 기본 5개
        String query = "";
        try
        {
//            if("insert".equals(pnull(_params, "query_type")))
//                query = "SELECT A.DEPART_CODE AS CHURCH_CODE, A.NAME AS NAME"
//                		+ ", fnGetPriestNameList2('"+church_idx+"','01014') AS PRIEST_MAIN"
//                		+ ", fnGetPriestNameList2('"+church_idx+"','01015') AS PRIEST_SUB"
//                		+ ", fnGetPriestNameList2('"+church_idx+"','01018') AS PRIEST_BO "
//                		+ " FROM DEPARTMENT a "
//                		+ " WHERE A.CHURCH_IDX = '"+church_idx+"'";
//            else
        	// file get
        	String imageSubQuery = "";
        	for(int i=1; i<=attached_files ; i++) {
        		imageSubQuery += ",(SELECT IMAGE_NAME FROM (SELECT ROW_NUMBER() OVER(ORDER BY REG_DATE DESC, IDX DESC) RNUM, * FROM CHURCH_PHOTO WHERE CHURCH_IDX=A.CHURCH_IDX ) X WHERE RNUM="+i+") AS STRFILENAME"+i;
        		imageSubQuery += ",(SELECT IMAGE_NAME FROM (SELECT ROW_NUMBER() OVER(ORDER BY REG_DATE DESC, IDX DESC) RNUM, * FROM CHURCH_PHOTO WHERE CHURCH_IDX=A.CHURCH_IDX ) X WHERE RNUM="+i+") AS FILENAME"+i;
        		imageSubQuery += ",(SELECT IMAGE_PATH FROM (SELECT ROW_NUMBER() OVER(ORDER BY REG_DATE DESC, IDX DESC) RNUM, * FROM CHURCH_PHOTO WHERE CHURCH_IDX=A.CHURCH_IDX ) X WHERE RNUM="+i+") AS FILEPATH"+i;
        	}
        	// priest list
        	String priests = ", (SELECT STUFF( (SELECT ',' + X.PRIESTNAME FROM ("
        			+ "SELECT ROW_NUMBER() OVER(ORDER BY P.ONUM ASC) RNUM, C.NAME + ' ' + P.NAME+'('+P.CHRISTIAN_NAME+')' AS PRIESTNAME "
        			+ "FROM PRIEST P "
        			+ "LEFT OUTER JOIN ORG_DEPARTMENT_PRIEST_REL PR ON PR.P_IDX   = P.P_IDX "
        			+ "LEFT OUTER JOIN CHURCH                    CC ON CC.ORG_IDX = PR.ORG_IDX "
        			+ "LEFT OUTER JOIN CODE_INSTANCE C ON C.CODE_INST = PR.P_POSITION AND  C.CODE='000003' "
        			+ " WHERE CC.CHURCH_IDX=A.CHURCH_IDX"
        			+ ") X FOR XML PATH('') ),1,1,'')) "
        			+ " AS PRIESTNAMES ";
        	//
            query = "SELECT A.*, B.NAME AS GIGOO_NAME, C.UPD_DATE"
//            		+ ", fnGetPriestNameList2('"+church_idx+"','01014') AS PRIEST_MAIN"
//            		+ ", fnGetPriestNameList2('"+church_idx+"','01015') AS PRIEST_SUB"
//            		+ ", fnGetPriestNameList2('"+church_idx+"','01018') AS PRIEST_BO "
            		+ ", '' AS PRIEST_MAIN"
            		+ ", '' AS PRIEST_SUB"
            		+ ", '' AS PRIEST_BO"
            		+ imageSubQuery
            		+ priests
            		+ " FROM CHURCH A "
            		+ " LEFT OUTER JOIN CODE_INSTANCE B ON B.CODE_INST  = A.GIGU_CODE AND B.CODE='000004' /* 지구코드 */ "
            		+ " LEFT OUTER JOIN MISSA_ETC     C ON A.CHURCH_IDX = C.CHURCH_IDX "
            		+ " WHERE A.CHURCH_IDX='"+church_idx+"'";
            
            result = super.executeQueryMap(query);

        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        }
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Result:"+result );
        return result;
    }

	@Override
    public Map getMember(Map _params)
    {
		D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
		String query = "";
        Map result = null;
        try {
            query = "SELECT ADM_NAME AS NAME FROM ADMMEMBER WHERE ADM_ID='"+_params.get("user_id")+"' ";
            result = super.executeQueryMap(query);

        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        }
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Result:"+result );
        return result;
    }

	@Override
    public boolean admChurchInsert(Map _params, List uploadedFileList)
    {
        int rn=0;
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        String query="";
        Map row=null;
        boolean bReturn = true;
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        try {
        	query = "  SELECT ISNULL(MAX(CHURCH_IDX), 0) + 1 AS CHURCH_IDX FROM CHURCH WHERE CHURCH_IDX < '14900' ";
            row = super.executeQueryMap(query);
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        }
        
        try
        {
        	if(connection==null || connection.isClosed()) connection = dataSource.getConnection();
            query = "INSERT INTO CHURCH  (church_idx, name, tel, fax, postcode, addr1, addr2,  email, jubo_saint, establish_date"
            		+ ", christian_cnt, homepage, intro, images,  history, map, traffic_mass, traffic_self, mem_cnt, supporter_cnt, r_order"
            		+ ", gigu_code"
            		+ ", title_image, logo_image, copywrite, church_code"
            		+ ", insertDT,  adm_id, map_point, map_controlarea ) "
            		+ " VALUES (?, ?, ?, ?, ?, ?, ?,  ?, ?, ?, ?, ?, ?, ?,  ?, ?, ?, ?, ?, ?, ?,  ?, '', '', '', ?, getdate(),  ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, pnull(row.get("CHURCH_IDX")));
            preparedStatement.setString(2, pnull(_params.get("name")));
            preparedStatement.setString(3, pnull(_params.get("tel")));
            preparedStatement.setString(4, pnull(_params.get("fax")));
            preparedStatement.setString(5, pnull(_params.get("postcode")));
            preparedStatement.setString(6, pnull(_params.get("addr1")));
            preparedStatement.setString(7, pnull(_params.get("addr2")));
            preparedStatement.setString(8, pnull(_params.get("email")));
            preparedStatement.setString(9, pnull(_params.get("jubo_saint")));
            preparedStatement.setString(10, pnull(_params.get("establish_date")));
            preparedStatement.setString(11, pnull(_params.get("christian_cnt")));
            preparedStatement.setString(12, pnull(_params.get("homepage")));
            preparedStatement.setString(13, pnull(_params.get("intro")));
            preparedStatement.setString(14, pnull(_params.get("images")));
            preparedStatement.setString(15, pnull(_params.get("history")));
            preparedStatement.setString(16, pnull(_params.get("map")));
            preparedStatement.setString(17, pnull(_params.get("traffic_mass")));
            preparedStatement.setString(18, pnull(_params.get("traffic_self")));
            preparedStatement.setString(19, pnull(_params.get("mem_cnt"), "0"));
            preparedStatement.setString(20, pnull(_params.get("supporter_cnt"), "0"));
            preparedStatement.setString(21, pnull(_params.get("r_order")));
            preparedStatement.setString(22, pnull(_params.get("gigu_code"), "01"));
            preparedStatement.setString(23, pnull(_params.get("church_code")));
            
            preparedStatement.setString(24, pnull(_params.get("adm_id")));
            preparedStatement.setString(25, pnull(_params.get("map_point")));
            preparedStatement.setString(26, pnull(_params.get("map_controlarea")));
            rn = preparedStatement.executeUpdate();

        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        	if(preparedStatement != null) try { preparedStatement.close(); } catch(SQLException e) { e.printStackTrace(); }
            if(connection != null) try {  connection.close(); } catch(SQLException e) { e.printStackTrace(); }
        }
        
        try
        {
        	if(connection==null || connection.isClosed()) connection = dataSource.getConnection();
            query = "INSERT INTO MISSA_ETC  (church_idx, etc, church_code, upda_date)  VALUES (?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, pnull(row.get("CHURCH_IDX")));
            preparedStatement.setString(2, pnull(_params.get("txt7")));
            preparedStatement.setString(3, pnull(_params.get("church_code")));
            preparedStatement.setString(4, pnull(_params.get("upd_date")));
            rn += preparedStatement.executeUpdate();

        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        	if(preparedStatement != null) try { preparedStatement.close(); } catch(SQLException e) { e.printStackTrace(); }
            if(connection != null) try {  connection.close(); } catch(SQLException e) { e.printStackTrace(); }
        }
        
        return rn >= 1;
    }

	@Override
	public boolean admChurchModify(Map _params, List uploadedFileList)
	{
	    int rn = 0;
	    PreparedStatement preparedStatement=null;
	    boolean bReturn = true;
	    
	    D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
	    
	    String query = "";
	    String master_id = pnull(_params.get("user_id"));
	    Map memberInfo = getMember(_params);
	    if(memberInfo == null) return false;
	    
	    // 성당둘러보기이미지 업데이트
	    try
	    {
	    	admChurchPhotoDelete(_params);
	    	admChurchPhotoRegister(_params, uploadedFileList);
	    } catch(Exception e) {
	    	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL1:"+query, e);
	    } finally {
	    	if(preparedStatement != null) try { preparedStatement.close(); } catch(SQLException e) { e.printStackTrace(); }
	    }
	    
	    // 본당
	    try
	    {
	        query = "UPDATE CHURCH SET "
	        		+ " NAME=?,  TEL=?,  FAX=?,  POSTCODE=?,  ADDR1=?,  ADDR2=?,  EMAIL=?,  JUBO_SAINT=?"
	        		+ ", ESTABLISH_DATE=?,  CHRISTIAN_CNT=?,  HOMEPAGE=?,  INTRO=?,  IMAGES=?,  HISTORY=?,  MAP=?,  TRAFFIC_MASS=?"
	        		+ ", TRAFFIC_SELF=?,  MEM_CNT=?,  SUPPORTER_CNT=?,  R_ORDER=?"
	        		+ ", GIGU_CODE=?"
	        		+ ", ADM_ID=?, MAP_POINT=?, MAP_CONTROLAREA=?, UPDATEDT=getdate()"
	        		+ " WHERE CHURCH_IDX=? ";
	        preparedStatement = getConnection().prepareStatement(query);
	        preparedStatement.setString(1, pnull(_params.get("name")));
	        preparedStatement.setString(2, pnull(_params.get("tel")).trim());
	        preparedStatement.setString(3, pnull(_params.get("fax")).trim());
	        preparedStatement.setString(4, pnull(_params.get("postcode")));
	        preparedStatement.setString(5, pnull(_params.get("addr1")).trim());
	        preparedStatement.setString(6, pnull(_params.get("addr2")).trim());
	        preparedStatement.setString(7, pnull(_params.get("email")).trim());
	        preparedStatement.setString(8, pnull(_params.get("jubo_saint")));
	        preparedStatement.setString(9, pnull(_params.get("establish_date")));
	        preparedStatement.setString(10, pnull(_params.get("christian_cnt")));
	        preparedStatement.setString(11, pnull(_params.get("homepage")).trim());
	        preparedStatement.setString(12, pnull(_params.get("intro")).trim());
	        preparedStatement.setString(13, pnull(_params.get("images")).trim());
	        preparedStatement.setString(14, pnull(_params.get("history")).trim());
	        preparedStatement.setString(15, pnull(_params.get("map")).trim());
	        preparedStatement.setString(16, pnull(_params.get("traffic_mass")).trim());
	        preparedStatement.setString(17, pnull(_params.get("traffic_self")).trim());
	        preparedStatement.setString(18, pnull(_params.get("mem_cnt"), "0"));
	        preparedStatement.setString(19, pnull(_params.get("supporter_cnt"), "0"));
	        preparedStatement.setString(20, pnull(_params.get("r_order")));
	        preparedStatement.setString(21, pnull(_params.get("gigu_code"))); /* 지구코드 2자리 CODE=000004 */
	        preparedStatement.setString(22, pnull(_params.get("adm_id")));
	        preparedStatement.setString(23, pnull(_params.get("map_point")).trim()); /* 본당위치 */
	        preparedStatement.setString(24, pnull(_params.get("map_controlarea")).trim()); /* 본당관할구역 */
	        preparedStatement.setString(25, pnull(_params.get("church_idx")));
	        rn = preparedStatement.executeUpdate();
	
	    } catch(Exception e) {
	    	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL2:"+query, e);
	    } finally {
	    	if(preparedStatement != null) try { preparedStatement.close(); } catch(SQLException e) { e.printStackTrace(); }
	    }
	    
	    // misa time : hour & minutes 월~토((WEEK : 0 ~ 6) -> MISSA_INFO
	    // 화면에서 개별 등록하는 기능으로 구현됨
	    
	    // misa time : 비고 (WEEK : 7) -> MISSA_ETC
	    String misa_etc = "";
		int x = 0;
		try {
			misa_etc = pnull(_params.get("txt7"));
			query = "select count(1) from MISSA_ETC where CHURCH_IDX="+pnull(_params.get("church_idx"));
			x = executeCount(query, false);
		} catch (SQLException e) {
			_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL3:"+query, e);
		}
		
	    try
	    {
	    	if( x > 0 )
	    		query = "UPDATE MISSA_ETC SET UPD_DATE=GETDATE(), ETC=?, CHURCH_CODE=? WHERE CHURCH_IDX=?";
	    	else
	    		query = "INSERT INTO MISSA_ETC (UPD_DATE, ETC, CHURCH_CODE, CHURCH_IDX) VALUES (GETDATE(), ?, ?, ?) ";
	    	preparedStatement = getConnection().prepareStatement(query);
	        preparedStatement.setString(1, misa_etc);
	        preparedStatement.setString(2, pnull(_params.get("church_code")));
	        preparedStatement.setString(3, pnull(_params.get("church_idx")));
	        rn += preparedStatement.executeUpdate();
	    } catch(Exception e) {
	    	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL4:"+query, e);
	    } finally {
	    	if(preparedStatement != null) try { preparedStatement.close(); } catch(SQLException e) { e.printStackTrace(); }
	    }
	    
	    return rn >= 1;
	}

	@Override
	public boolean admChurchDelete(Map _params) {
		boolean bReturn = false;
		String query2="";  
		String church_idx = pnull(_params, "church_idx");  
		String org_idx    = pnull(_params, "org_idx");  
		try {
        	int i = 0;
    		query2 = "update ORG_HIERARCHY set DEL_YN='Y'"
            		+ ", DEL_DATE=CONVERT(CHAR(8), getdate(), 112) "
            		+ ", UPD_DATE=CONVERT(CHAR(8), getdate(), 112) "
    				+ " where ";
        	if(org_idx.length()>0) {
        		query2 += "org_idx = "+org_idx;
        	} else {/* ORG_HIERARCHY에는 데이터가 있는데, DEPARTMENT에 없는 경우가 발생 */
        		query2 += "org_idx = (SELECT org_idx FROM CHURCH WHERE church_idx="+church_idx+")";
        	}
        	//query = " BEGIN TRAN " + query;//transactionStart();
        	//i = executeUpdate(query);
            //if(i < 2) {
                i = executeUpdate(query2);
            	bReturn = i==1 ? true : false;
            //} else {
            //}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return bReturn;
	}
		
	/*
	 * 내부 성당둘러보기 이미지 공통 처리(등록/수정/삭제)
	 */
	private int admChurchPhotoDelete(Map _params) throws Exception {
		// 삭제 -> 등록
		//String uploadedFileURI      = pnull(_params, "CONTEXT_URI_PATH");
		String uploadedFileRootPath = pnull(_params, "CONTEXT_ROOT_PATH");
		int rn5 = -1;
		
		// files delete
		PreparedStatement pstmt = null;
		try {
			String church_idx = pnull(_params, "church_idx");
			pstmt = getPreparedStatement("DELETE FROM CHURCH_PHOTO WHERE CHURCH_IDX=? AND IMAGE_NAME=? ");
			for(int i=1; i<6; i++) {
				String strfilename = pnull(_params, "delFile"+i);
				if (strfilename.length() > 0) {
					I(_logger, Thread.currentThread().getStackTrace(), " delFile"+i+"=" + strfilename);
					
					// file remove & thumbnail
					File f = new File(uploadedFileRootPath + strfilename);
					if( f.exists() ) {
						f.delete();
					}
					f = new File(uploadedFileRootPath + "thumbnail/" + strfilename);
					if( f.exists() ) {
						f.delete();
					}
					
					// db row delete
					rn5 += executeUpdatePreparedStatement(pstmt, getLinkedHashMap(church_idx, strfilename), false);
				}
			}
			
		} catch (Exception e) {
			throw e;
		} finally {
			free(pstmt);
		}
		
		return rn5;
	}
	

	/*
	 * 내부 성당둘러보기 이미지 공통 처리(등록/수정/삭제)
	 */
	private int admChurchPhotoRegister(Map _params, List<Map> uploadedFileList) throws Exception {
		String uploadedFileURI      = pnull(_params, "CONTEXT_URI_PATH");
		String uploadedFileRootPath = pnull(_params, "CONTEXT_ROOT_PATH");
		int rn5 = -1;
		
		I(_logger, Thread.currentThread().getStackTrace(), " new Uploaded Files = " + uploadedFileList);
		
		String church_idx = pnull(_params, "church_idx");
		String query = "INSERT INTO CHURCH_PHOTO (CHURCH_IDX, image_type, image_name, image_uri, IMAGE_DESC, IMAGE_PATH, REG_DATE) "
				+ " VALUES ( "
				+ " "+church_idx+", ?, ?, ?, '', '"+uploadedFileURI+"', CONVERT(CHAR(10),GETDATE(),120) )";
		PreparedStatement pstmt = getPreparedStatement(query);
		for(int i=0, i2=uploadedFileList.size(); i<i2; i++) {
			
			Map filemap = uploadedFileList.get(i);
			
			String imagetype = pnull(filemap, "FILE_TYPE");
			String imagename = pnull(filemap, "STORED_FILE_NAME");
			String imageuri  = uploadedFileURI + imagename;
			
			// 180325, 동일이름의 이미지가 있으면 insert 하지 않음.
			if( executeCount("SELECT /* image dup check */ COUNT(*) FROM CHURCH_PHOTO WHERE IMAGE_NAME='"+imagename+"' AND CHURCH_IDX="+church_idx, false) < 1 ) {
				// Existed logic
				if (imagename.length() > 0) {
					rn5 += executeUpdatePreparedStatement(pstmt, getLinkedHashMap(imagetype, imagename, imageuri), false);
				}
			}
		}
		
		return rn5;
	}
	
	
	@Override
    public boolean admChurchMissaInfoDelete(Map _params)
        throws CommonException
    {
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        String church_idx = pnull(_params.get("church_idx"));
        String mi_idx      = pnull(_params.get("mi_idx"));
        
        int rn = 0;
        try {
            rn = executeUpdate("DELETE FROM MISSA_INFO WHERE MI_IDX="+mi_idx+" AND CHURCH_IDX="+church_idx);
        } catch(SQLException e) {
            throw new CommonException(e.getMessage(), "ERR-D013", e);
        }
        
        return rn > 0;
    }

	@Override
    public int admChurchMissaInfoInsert(Map _params) throws CommonException
    {
		D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
		
        int result = -1;
        String sql = "", query="";
        
        String church_idx  = pnull(_params, "church_idx");
        String church_code = pnull(_params, "church_code");
        String week        = pnull(_params, "week");
        String txt         = pnull(_params, "txt");
        String missa_hour  = pnull(_params, "missa_hour");
        String missa_min   = pnull(_params, "missa_min");
        
        CallableStatement cstmt = null;
        
        try {

        	String churchidxSQL = ( church_idx.length() > 0 ? ""+church_idx : "(SELECT CHURCH_IDX FROM CHURCH WHERE CHURCH_CODE='"+church_code+"')" );
        	
        	
        	query = "INSERT INTO MISSA_INFO (MI_IDX, CHURCH_IDX, WEEK, MISA_HOUR, MISA_MIN, TXT, CHURCH_CODE, UPD_DATE)"
        			+ " VALUES ( "
        			+ " (SELECT ISNULL(MAX(MI_IDX),0) + 1 FROM MISSA_INFO)"
        			+ ","+churchidxSQL
        			+ ","+week+", "+missa_hour+", "+missa_min+", '"+txt+"', null, GETDATE())";
        	int i = executeUpdate(query);
        	if(i > 0) {
	        	query = "SELECT MI_IDX FROM MISSA_INFO  WHERE WEEK="+week+" AND MISA_HOUR="+missa_hour+" AND MISA_MIN="+missa_min+" AND CHURCH_IDX="+churchidxSQL;
	        	int mi_idx = executeCount(query, false);
	        	result = mi_idx;
        	}
        }
        catch(SQLException e)
        {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
            throw new CommonException(e.getMessage(), "ERR-D013", e);
        }
        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+result );
        return result;
    }

    
}
