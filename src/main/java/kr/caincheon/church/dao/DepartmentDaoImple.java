// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DepartmentDaoImple.java

package kr.caincheon.church.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import kr.caincheon.church.common.base.CommonDao;

// Referenced classes of package kr.caincheon.church.dao:
//            DepartmentDao

@Repository("departmentDao")
public class DepartmentDaoImple extends CommonDao
    implements DepartmentDao
{

	private final Logger _logger = Logger.getLogger(getClass());
	
    public List selectDepartment(String lv1_of_org_idx, String lv2_of_org_idx, String lv3_of_org_idx)
        throws Exception
    {
    	String lv1 = pnull(lv1_of_org_idx);
    	String lv2 = pnull(lv2_of_org_idx);
    	String lv3 = pnull(lv3_of_org_idx);
    	
        if(lv1.length() < 1 || lv2.length() < 1 )
            throw new Exception("It's not enough the parameter values.[lv1="+lv1+", lv2="+lv2+", lv3="+lv3+"]");

        List rtList = null;
        String query = "";
        String where = " A.ORG_IDX IN (SELECT ORG_IDX FROM ORG_HIERARCHY "
        		+ " WHERE LV1='"+lv1+"'"
        		+ " AND LV2 like '"+lv2+"'"
        		+ (lv3.length()==0? "": " AND LV3 LIKE '"+lv3+"'")
        		+ ") ";
        
        try {
        	String priests = " (SELECT STUFF( (SELECT ',' + X.PRIESTNAME FROM ("
        			+ "SELECT ROW_NUMBER() OVER(ORDER BY P.ONUM ASC) RNUM, P.NAME+'('+P.CHRISTIAN_NAME+')' AS PRIESTNAME "
        			+ "FROM PRIEST P "
        			+ "LEFT OUTER JOIN ORG_DEPARTMENT_PRIEST_REL PR ON PR.P_IDX = P.P_IDX "
        			+ "LEFT OUTER JOIN DEPARTMENT                DD ON PR.ORG_IDX = DD.ORG_IDX "
        			+ " WHERE DD.DEPART_IDX=A.DEPART_IDX " // 대체할 것 : "WHERE DD.DEPART_IDX = 14052"
        			+ ") X FOR XML PATH('') ),1,1,''))  AS PRIESTNAMES  "//AS PRIESTNAMES "
        			;
            query = "SELECT DEPART_IDX, A.ORG_IDX, O.NAME AS ORG_NAME, O.LV1, O.LV2, O.LV3"
            		+ ", A.NAME, TEL, FAX, MAIL, HOMEPAGE1, HOMEPAGE2, ADDR1 "
            		+ priests
            		+ ", INTRO "
            		+ " FROM DEPARTMENT A "
            		+ " LEFT OUTER JOIN ORG_HIERARCHY O ON O.ORG_IDX=A.ORG_IDX "
            		+ " WHERE "+where
            		+ " ORDER BY O.LV1 , O.LV2, O.LV3 " 
            		;
            rtList = super.executeQueryList(query);
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
            rtList = new ArrayList();
        } finally {
        	free();
    	}
        
        return rtList;
    }

    /**
     * 통합검색 서비스 :: 교구청 ( Called by UnifySearchServiceImpl)
     * @see kr.caincheon.church.dao.DepartmentDao#unifySearch(java.util.Map, java.sql.Connection)
     */
	@Override
	public List<Map> unifySearch(Map _params, Connection conn) throws Exception {
		D(_logger, Thread.currentThread().getStackTrace(), "Dao Called. params:"+_params );
		
		// 부서명, 부서소개
		List rtList = null;
		try {
			String srchText = pnull(_params, "srchText");
			lastSQL = "SELECT ROW_NUMBER() OVER(ORDER BY NAME ASC ) RNUM "
					+ ", DEPART_IDX, NAME, HOMEPAGE1, HOMEPAGE2, TEL, FAX, MAIL, ADDR1, ADDR2, P_IDX1 "
					+ " FROM DEPARTMENT WHERE NAME LIKE '%"+srchText+"%' OR INTRO LIKE '%"+srchText+"%' ";
			rtList = super.executeQueryList(lastSQL, conn);
		} catch (Exception e) {
			_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+lastSQL, e);
		}
		
		D(_logger, Thread.currentThread().getStackTrace(), "Dao Returned. rtList:"+rtList );
		return rtList;
	}

    
}
