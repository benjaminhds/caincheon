// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FaqDaoImpl.java

package kr.caincheon.church.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import kr.caincheon.church.common.base.CommonDao;

// Referenced classes of package kr.caincheon.church.dao:
//            FaqDao

@Repository("faqDao")
public class FaqDaoImpl extends CommonDao
    implements FaqDao
{

	private final Logger _logger = Logger.getLogger(getClass());
	
	@Override
    public List faqList(Map _params)
    {
        String whereStr = "";
        List result = null;
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        int pageNo    = ipnull(_params, "pageNo", 1);
        int pageSize  = ipnull(_params, "pageSize", 20);
        int startRnum = (pageNo - 1) * pageSize + 1;
        int endRnum   = pageNo * pageSize;
        String searchGubun = pnull(_params.get("searchGubun"));
        String searchText  = pnull(_params.get("searchText"));
        String displayYN   = pnull(_params.get("displayYN"));
        
        if(searchText.length() > 0 && searchGubun.length() > 0 )
        {
            if(searchGubun.equals("all")) {
                whereStr = " AND ( QUESTION LIKE '%"+searchText+"%' OR ANSWER LIKE '%"+searchText+"%') ";
            } else if(searchGubun.equals("question")) {
                whereStr = " AND QUESTION LIKE '%"+searchText+"%' ";
            } else if(searchGubun.equals("answer")) {
                whereStr = " AND ANSWER LIKE '%"+searchText+"%' ";
            }
        }
        
        String query = "";
        try
        {
            query = "SELECT * FROM  ( "
            		+ " SELECT ROW_NUMBER() OVER(ORDER BY registDT DESC) RNUM"
            		+ ", FAQ_NO"
            		+ ", QUESTION"
            		+ ", ANSWER"
            		+ ", DISPLAYYN"
            		+ ", CASE WHEN DISPLAYYN = 'Y' THEN '노출'  ELSE '비노출' END AS DISPLAYYN_TEXT"
            		+ ", CONVERT(char(10),  REGISTDT, 120) REGISTDT"
            		+ ", CONVERT(char(10),  UPDATEDT, 120) UPDATEDT "
            		+ " FROM FAQ "
            		+ " WHERE 1=1 " + whereStr + ( displayYN.length()==1 ? " AND displayYN='"+displayYN+"' " : "" )
            		+ " ) ROWS WHERE RNUM BETWEEN "+startRnum+" AND "+endRnum
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
    public int faqListCount(Map _params)
    {
        int result = 0;
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );        
        
        String searchGubun = pnull(_params.get("searchGubun"));
        String whereStr    = "";
        String searchText  = pnull(_params.get("searchText"));
        String displayYN   = pnull(_params.get("displayYN"));
        
        if(searchText.length() > 0 && searchGubun.length() > 0 )
        {   
            if(searchGubun.equals("all")) {
                whereStr = " AND ( QUESTION LIKE '%"+searchText+"%' OR ANSWER LIKE '%"+searchText+"%') ";
            } else if(searchGubun.equals("question")) {
                whereStr = " AND QUESTION LIKE '%"+searchText+"%' ";
            } else if(searchGubun.equals("answer")) {
                whereStr = " AND ANSWER LIKE '%"+searchText+"%' ";
            }
        }
        
        String query = "";
        try {
            query = "SELECT COUNT(1) FROM FAQ "
            		+ " WHERE 1=1 " + whereStr + ( displayYN.length()==1 ? " AND displayYN='"+displayYN+"' " : "" )
            		;
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
    public Map faqContents(Map _params)
    {
        Map result = null;
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        String query_type = pnull(_params.get("query_type"));
        String faq_no = pnull(_params.get("faq_no"));
        String query = "";
        String whereStr = "";
        try
        {
        	//if(query_type.equals("modify"))
            if(faq_no.length() > 0)
            {
                whereStr = " AND faq_no = '"+faq_no+"'";
                query = " SELECT faq_no, question, answer, displayYN  FROM FAQ WHERE 1=1 "+whereStr;
                result = super.executeQueryMap(query);
            }
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        	free();
    	}
        
        D(_logger, Thread.currentThread().getStackTrace(), "Query Result:"+result );
        return result;
    }

	@Override
    public boolean faqInsert(Map _params)
    {
        boolean bReturn = true;
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        String question  = pnull(_params.get("question"));
        String answer    = pnull(_params.get("answer"));
        String displayYN = pnull(_params.get("displayYN"), "N");
        
        String query = "";
        int i = 0;
        try
        {
        	query = "INSERT INTO FAQ  (question, answer, displayYN, registDT, updateDT)  VALUES (?, ?, ?, getdate(), getdate()) ";
        	LinkedHashMap qm = super.getLinkedHashMap(question, answer, displayYN);
        	i = super.executeUpdatePreparedStatement(query, qm);
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
            bReturn = false;
        }  finally {
        	free();
    	}
        return bReturn && i > 0 ;
    }

	@Override
    public boolean faqModify(Map _params)
    {
        boolean bReturn = true;
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        String faq_no    = pnull(_params.get("faq_no"));
        String question  = pnull(_params.get("question"));
        String answer    = pnull(_params.get("answer"));
        String displayYN = pnull(_params.get("displayYN"), "N");

        String query = "";
        int i = 0;
        try {
            query = "UPDATE FAQ  SET question=?, answer=?, displayYN=?, updateDT=getdate()  WHERE faq_no = ? ";
        	LinkedHashMap qm = super.getLinkedHashMap(question, answer, displayYN, Integer.parseInt(faq_no));
        	i = super.executeUpdatePreparedStatement(query, qm);
        	
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
            bReturn = false;
        } finally {
        	free();
    	}
        return bReturn && i > 0;
    }

	@Override
    public boolean faqDelete(Map _params)
    {
        boolean bReturn = true;
        String faq_no = pnull(_params.get("faq_no"));
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );

        int i = 0;
        String query = "";
        try
        {
            query = "delete from FAQ where faq_no = " + faq_no;
        	i = super.executeUpdate(query);
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
            bReturn = false;
        } finally {
        	free();
    	}
        return bReturn && i > 0;
    }

    
}
