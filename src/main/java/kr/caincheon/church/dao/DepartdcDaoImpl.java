// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DepartdcDaoImpl.java

package kr.caincheon.church.dao;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import kr.caincheon.church.common.base.CommonDao;
import kr.caincheon.church.common.base.CommonException;

// Referenced classes of package kr.caincheon.church.dao:
//            DepartdcDao

@Repository("departdcDao")
public class DepartdcDaoImpl extends CommonDao
    implements DepartdcDao
{

    private final Logger _logger = Logger.getLogger(getClass());
    
    @Override
    public List departdcList(Map _params)
    {
        String whereStr = "";
        List result = null;
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        int pageNo   = ipnull(_params, "pageNo", 1);
        int pageSize = ipnull(_params, "pageSize", 1);
        int startRnum = (pageNo - 1) * pageSize + 1;
        int endRnum = pageNo * pageSize;
        String searchText = pnull(_params.get("searchText"));
        
        if(searchText.length() > 0)
            whereStr += " AND name LIKE '%" + searchText + "%'";
        
        String query = "";
        try
        {
            query = "SELECT A.* FROM ( "
            		+ " SELECT DISTINCT ROW_NUMBER() OVER(ORDER BY O.LV2, O.LV3) AS RNUM"
            		+ ", ISNULL(C.DEPART_IDX,'') AS DEPART_IDX"
            		+ ", O.ORG_IDX "
            		+ ", C.NAME AS NAME"
            		+ ", CASE WHEN C.DEPART_IDX IS NULL THEN 'I' ELSE 'M' END MODE "
            		+ " FROM DEPARTMENT C "
            		+ " LEFT OUTER JOIN ORG_HIERARCHY O ON O.ORG_IDX=C.ORG_IDX "
            		+ " WHERE 1 = 1 "+whereStr+" AND O.LV1 = '01' AND O.LV2!='00' AND O.LV3!='000' /* AND O.DEL_YN='N' */ "
            		+ ") A "
            		+ " WHERE A.RNUM BETWEEN "+startRnum+" AND "+endRnum;
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
    public int departdcListCount(Map _params)
    {
        String whereStr = "";
        int result=0;
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        String searchText = pnull(_params.get("searchText"));
        if(searchText.length() > 0)
            whereStr += " AND name LIKE '%" + searchText + "%'";
        
        String query = "";
        try {
            query = "SELECT COUNT(1)  FROM DEPARTMENT C  "
            		+ " LEFT OUTER JOIN ORG_HIERARCHY O ON O.ORG_IDX=C.ORG_IDX "
            		+ "  WHERE 1 = 1 " + whereStr + " O.LV1 = '01' AND O.LV2!='00' AND O.LV3!='000' /* AND O.DEL_YN='N' */ ";
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
    public Map departdcContents(Map _params) throws CommonException
    {
    	D(_logger, Thread.currentThread().getStackTrace(), " called..." + _params);
    	
        Map result = new HashMap();
        
        String query_type = pnull(_params.get("query_type"));
        String depart_idx = pnull(_params.get("depart_idx"));
        String org_idx    = pnull(_params.get("org_idx"));
        
        String query = "";
        try
        {
        	String priestSql = " (SELECT STUFF( (SELECT ',' + X.PRIESTNAME FROM ("
        			+ "SELECT ROW_NUMBER() OVER(ORDER BY P.ONUM ASC) RNUM, P.NAME+'('+P.CHRISTIAN_NAME+')' AS PRIESTNAME "
        			+ "FROM PRIEST P "
        			+ "LEFT OUTER JOIN ORG_DEPARTMENT_PRIEST_REL PR ON PR.P_IDX = P.P_IDX "
        			+ "LEFT OUTER JOIN DEPARTMENT                DD ON PR.ORG_IDX = DD.ORG_IDX "
        			+ " {WHERE} " // 대체할 것 : "WHERE DD.DEPART_IDX = 14052"
        			+ ") X FOR XML PATH('') ),1,1,'')) "//AS PRIESTNAMES "
        			;
        	if(org_idx.length() > 0 && depart_idx.length() > 0)
        		priestSql = priestSql.replace("{WHERE}", "WHERE  H.ORG_IDX="+org_idx+" AND DD.DEPART_IDX="+depart_idx);
        	else if(org_idx.length() > 0)
        		priestSql = priestSql.replace("{WHERE}", "WHERE H.ORG_IDX="+org_idx);
        	else if(depart_idx.length() > 0)
        		priestSql = priestSql.replace("{WHERE}", "WHERE DD.DEPART_IDX="+depart_idx);
        	
        	
            if(query_type.equals("modify"))
            {
                query = "SELECT A.DEPART_IDX, A.ORG_IDX"
                		+ ",  A.HOMEPAGE1, A.HOMEPAGE2, A.MAIL, A.TEL, A.FAX, A.POSTCODE, A.ADDR1, A.ADDR2, A.IMAGE, A.IS_VIEW "
                		//+ ", fnGetPriestNameList(A.DEPART_IDX) AS PRIESTNAMELIST "
                		+ ", "+priestSql+" AS PRIESTNAMELIST "
                		+ ", H.NAME AS DEPART_NAME, A.INTRO "
                		+ ", ISNULL(O.LV1+O.LV2+O.LV3, '') AS DEPART_CODE "
                		+ " FROM DEPARTMENT A  "
                		+ " LEFT OUTER JOIN DEPARTMENT H ON A.DEPART_IDX = H.DEPART_IDX "
                		+ " LEFT OUTER JOIN ORG_HIERARCHY O ON O.ORG_IDX = A.ORG_IDX "
                		+ " WHERE 1=1 "
                		+ (depart_idx.length() > 0 ? " AND A.DEPART_IDX='"+depart_idx+"' " : "") // new code by bjm
                		+ (org_idx.length()    > 0 ? " AND A.ORG_IDX='"+org_idx+"' " : "") // new code by bjm
                		;
            } else {
                //query = "SELECT fnGetPriestNameList('"+depart_idx+"') AS PRIESTNAMELIST, NAME AS DEPART_NAME "
            	query = "SELECT "+priestSql+" AS PRIESTNAMELIST, NAME AS DEPART_NAME "
                		+ " FROM DEPARTMENT "
                		+ " WHERE DEPART_IDX = '"+depart_idx+"'" // new code by bjm
                		;
            }
            D(_logger, Thread.currentThread().getStackTrace(), " query=" + query);
            result.putAll(executeQueryMap(query));
            
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
        	free();
    	}
        
        D(_logger, Thread.currentThread().getStackTrace(), " rtVO=" + result);
        
        return result;
    }

    @Override
    public List priestList(Map _params)
    {
        List result = null;
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        String query = "";
        String strWhere = "";
        
        try
        {
            query = "SELECT * FROM ("
            		+ " SELECT ROW_NUMBER() OVER(ORDER BY j_gubun) RNUM"
            		+ ", p_idx, name, christian_name, gubun, s_gubun, j_gubun, j_name, p_code, p_name "
            		+ " FROM PRIEST"
            		+ " ) ROWS ";
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
    public List departdcViewGetPriestList(Map _params)
    {
        List result = null;
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        String query = "";
        try {
            query = "SELECT B.NAME, B.P_NAME  FROM DEPARTMENT A "
            		+ " LEFT OUTER JOIN PRIEST B ON A.depart_idx = B.j_gubun "
            		+ " WHERE A.depart_idx='" + pnull(_params, "departIdx") + "'"
            		+ " ORDER BY B.P_CODE ";
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
    public List _1x000List(Map _params)
    {
        List result = null;
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO ORGList Called.[params:"+_params+"]" );
        
        String query = "";
        
        try {
            query = "SELECT D.DEPART_IDX, D.NAME, D.ORG_IDX, O.LV2 AS ORG_LV2, O.LV3 AS ORG_LV3 "
            		+ " FROM DEPARTMENT D "
            		+ " JOIN ORG_HIERARCHY O ON O.ORG_IDX=D.ORG_IDX "
            		+ " WHERE O.LV1='01' AND O.LV2!='00' AND O.LV3 != '000' ";
            result = super.executeQueryList(query);
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
        	free();
    	}
        
        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+result );
        return result;
    }

    @Override
    public boolean departdcInsert(Map _params)
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Insert Called.[params:"+_params+"]" );
    	
        boolean bReturn = false;
        
        String depart_name = pnull(_params, "depart_name");
        String org_idx     = pnull(_params, "org_idx");
        String depart_idx  = pnull(_params, "depart_idx");
        String intro       = pnull(_params, "intro");
        String homepage1   = pnull(_params, "homepage1", pnull(_params,"homepage"));
        String homepage2   = pnull(_params, "homepage2");
        String mail        = pnull(_params, "mail");
        String tel         = pnull(_params, "tel");
        String fax         = pnull(_params, "fax");
        String postcode    = pnull(_params, "postcode");
        String addr1       = pnull(_params, "addr1");
        String addr2       = pnull(_params, "addr2");
        String is_view     = pnull(_params, "is_view").toUpperCase();
        String image       = pnull(_params, "image");
        
        if(!("Y".equals(is_view) || "N".equals(is_view)))
        	is_view = "1".equals(is_view) ? "Y":"N" ;
        
        String query = "";
        try
        {
        	int i=1;
            query = "INSERT INTO DEPARTMENT ( "
            		+ " depart_idx "
            		+ ", name "
            		+ ", intro"
            		+ ", homepage1, homepage2, mail, tel, fax, postcode, addr1, addr2, image, is_view"
            		+ ", org_idx "
            		+ " ) VALUES ( "
            		+ (depart_idx.length()!=0 ? depart_idx : " (SELECT ISNULL(MAX(depart_idx),0)+1 FROM DEPARTMENT)")
            		+ (org_idx.length()==0 ? ", ''" : ", (SELECT ISNULL(NAME,'') FROM ORG_HIERARCHY WHERE ORG_IDX="+org_idx+")")
            		//+ ", ? "
            		//+ ", ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?"
            		+ ", ?"
            		+ ", ?, ?, ?, ?, ?, ?, ?, ?, ?, ?"
            		+ ", ? ) ";
            

            LinkedHashMap<String, Object> lmap = 
            		super.getLinkedHashMap( 
            				  intro
            				, homepage1, homepage2, mail, tel, fax, postcode, addr1, addr2, image, is_view
            				, org_idx);
            
            i = super.executeUpdatePreparedStatement(query, lmap);
            bReturn = i == 1 ? true : false;
            
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        	free();
    	}
        return bReturn;
    }


    @Override
    public boolean departdcModify(Map _params) throws CommonException
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Modify Called.[params:"+_params+"]" );
    	
        boolean bReturn = true;
        
        String org_idx     = pnull(_params, "org_idx");
        String depart_idx  = pnull(_params, "depart_idx");
        String intro       = pnull(_params, "intro");
        String homepage1   = pnull(_params, "homepage1", pnull(_params,"homepage"));
        String homepage2   = pnull(_params, "homepage2");
        String mail        = pnull(_params, "mail");
        String tel         = pnull(_params, "tel");
        String fax         = pnull(_params, "fax");
        String postcode    = pnull(_params, "postcode");
        String addr1       = pnull(_params, "addr1");
        String addr2       = pnull(_params, "addr2");
        String is_view     = pnull(_params, "is_view").toUpperCase();
        String image       = pnull(_params, "image");
        if(pnull(_params.get("delImg")).equals("Y"))
            image = "";

        if(!("Y".equals(is_view) || "N".equals(is_view)))
        	is_view = "1".equals(is_view) ? "Y":"N" ;
        
        String query = "";
        try
        {
        	int i = 1;
            query = "UPDATE DEPARTMENT "
            		+ " SET "
            		+ " intro=?, homepage1=?, homepage2=?, mail=?, tel=?, fax=?, postcode=?,  addr1=?, addr2=?, image=?, is_view=? "
            		+ " WHERE depart_idx=?";
            
            LinkedHashMap<String, Object> lmap = 
            		super.getLinkedHashMap(intro, homepage1, homepage2, mail, tel, fax, postcode, addr1, addr2, image, is_view, depart_idx);
            
            i = super.executeUpdatePreparedStatement(query, lmap);
            if(i == 0) {
            	i = departdcInsert(_params) ? 1 : 0;
//            	lmap.put(""+(lmap.size()+1), org_idx);
//            	// insert
//            	query = "INSERT INTO DEPARTMENT ("
//            			+ "intro, homepage1, homepage2, mail, tel, fax, postcode,  addr1, addr2, image, is_view, org_idx"
//            			+ ") VALUES("
//            			+ "?, ?, ?, ?, ?, ?, ?,  ?, ?, ?, ?, ?"
//            			+ ")"
//            			;
            }
            //commit();
            
            bReturn = i > 0 ? true : false;
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
            bReturn = false;
        } finally {
        	free();
    	}
        return bReturn;
    }


    @Override
    public boolean departdcDelete(Map _params)
    {
        boolean bReturn = false;
        String depart_idx = pnull(_params.get("depart_idx"));
        String org_idx = pnull(_params.get("org_idx"));
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Delete Called.[params:"+_params+"]" );
        String query = "", query2 = "";
        
        try {
        	int i = 0;
        	query  = "update DEPARTMENT set IS_VIEW='N' where ";
    		query2 = "update ORG_HIERARCHY set DEL_YN='Y' where ";
        	if(org_idx.length()>0) {
        		query  += "org_idx = "+org_idx;
        		query2 += "org_idx = "+org_idx;
        	} else {/* ORG_HIERARCHY에는 데이터가 있는데, DEPARTMENT에 없는 경우가 발생 */
        		query  += "depart_idx = "+depart_idx;
        		query2 += "org_idx = (SELECT org_idx FROM DEPARTMENT WHERE depart_idx="+depart_idx+")";
        	}
        	//query = " BEGIN TRAN " + query;//transactionStart();
        	i = executeUpdate(query);
            if(i < 2) {
                i = executeUpdate(query2);
            	//transactionEnd();
            	bReturn = true;
            } else {
            	//transactionFail();
            }
            
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        	free();
    	}
        return bReturn;
    }

}
