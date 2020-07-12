// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MgzDaoImpl.java

package kr.caincheon.church.dao;

import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import kr.caincheon.church.common.base.CommonDao;
import kr.caincheon.church.common.utils.FileUtils;

// Referenced classes of package kr.caincheon.church.dao:
//            MgzDao

@Repository("mgzDao")
public class MgzDaoImpl extends CommonDao
    implements MgzDao
{


	@Override
    public List mgzList(Map _params)
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
        String pub_idx = pnull(_params.get("pub_idx"));
        String query="";
        int startRnum;
        int endRnum;
        String strWhereA="";
        String strWhereB="";
        List result=null;
        
        // 하드코딩 불필요한 코드
        int pageNo   = ipnull(_params, "pageNo", 1);
        int pageSize = ipnull(_params, "pageSize", 12);
        startRnum = (pageNo - 1) * pageSize + 1;
        endRnum   = pageNo * pageSize;
        
        String searchGubun = pnull(_params.get("searchGubun"));
        String searchText = pnull(_params.get("searchText"));
        
        if(searchText.length() > 0)
            if(searchGubun.equals("DESCRIPTION"))
                strWhereB = " AND DESCRIPTION LIKE '%"+searchText+"%' ";
            else
                strWhereA = " AND NO LIKE '%"+searchText+"%' ";
        
        
        try
        {
            //if(pub_idx.equals("3"))
                query = " SELECT A.* FROM  (  "
                		+ " SELECT ROW_NUMBER() OVER(ORDER BY PUBDATE DESC) RNUM"
                		+ ", M_IDX"
                		+ ", NO"
                		+ ", CONVERT(char(10), PUBDATE, 102) AS PUBDATE "
                		+ ", CONVERT(char(8), PUBDATE, 112) + '_' + NO AS MP3FILE "
                		+ ", ISNULL(MP3, '') AS MP3 "
                		+ ", PDF "
                		+ ", ISNULL(COVER_IMAGE, '') AS COVER_IMAGE"
                		+ ", ISNULL(WEEK_COMMENT, '') AS WEEK_COMMENT"
                		+ ", ISNULL(COVER_COMMENT, '') AS COVER_COMMENT"
                		+ ", ISNULL(B.DESCRIPTION, '') AS DESCRIPTION  "
                		+ " FROM MAGAZINE A  "
                		+ " LEFT OUTER JOIN HOLIDAY B ON A.PUBDATE=B.H_DATE   "
                		+ " WHERE PUB_IDX ='3' AND IS_PUBLISH ='1' "+strWhereA +" " + strWhereB+" "
                		+ ") A "
                		+ " WHERE RNUM BETWEEN "+startRnum+" AND "+endRnum;

            result = super.executeQueryList(query);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        } finally {
        	free();
    	}
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Result:"+result );
        return result;
    }

    @Override
    public int mgzListCount(Map _params)
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
        String pub_idx;
        String query;
        String strWhereA;
        String strWhereB;
        int result=0;
        
        pub_idx = pnull(_params.get("pub_idx"));
        query = "";
        String searchGubun = pnull(_params.get("searchGubun"));
        String searchText = pnull(_params.get("searchText"));
        strWhereA = "";
        strWhereB = "";
        if(searchText.length() > 0) {
            if(searchGubun.equals("DESCRIPTION"))
                strWhereB = " AND DESCRIPTION LIKE '%"+searchText+"%' ";
            else
                strWhereA = " AND NO LIKE '%"+searchText+"%' ";
        }
        
        try {
            //if(pub_idx.equals("3"))
                query = "SELECT count(1) FROM  (  "
                		+ " SELECT ROW_NUMBER() OVER(ORDER BY PUBDATE DESC) RNUM, M_IDX, NO, CONVERT(char(10), PUBDATE, 102) PUBDATE, CONVERT(char(8), PUBDATE, 112) + '_' + NO MP3FILE, COVER_IMAGE, WEEK_COMMENT, COVER_COMMENT, PDF , B.DESCRIPTION  "
                		+ " FROM MAGAZINE A   "
                		+ " LEFT OUTER JOIN HOLIDAY B ON A.PUBDATE=B.H_DATE    "
                		+ " WHERE PUB_IDX ='3' AND IS_PUBLISH ='1' "+strWhereA+strWhereB
                		+ " ) A ";
            result = super.executeCount(query, false);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        } finally {
        	free();
    	}
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Result:"+result );
        return result;
    }

    @Override
    public Map mgzContents(Map _params)
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
        Map result = new HashMap();
        String query_type = pnull(_params.get("query_type"));
        String m_idx = pnull(_params.get("m_idx"));
        
        String query = "";
        String whereStr = "";
        
        try
        {
            if(query_type.equals("modify"))
            {
                whereStr = " AND m_idx='"+m_idx+"' ";
                query    = " SELECT M_IDX, NO, CONVERT(char(10), PUBDATE, 102) PUBDATE,   CONVERT(char(8), PUBDATE, 112) + '_' + NO MP3FILE"
                		+ ", PDF, MP3"
                		+ ", B.DESCRIPTION "
                		+ " FROM MAGAZINE  A "
                		+ " LEFT JOIN HOLIDAY B ON A.PUBDATE=B.H_DATE "
                		+ " WHERE A.PUB_IDX = '3' AND A.IS_PUBLISH = '1' AND A.M_IDX ='"+m_idx+"' ";
                
                result = super.executeQueryMap(query);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        } finally {
        	free();
    	}
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Result:"+result );
        return result;
    }

    @Override
    public String mgzTitle(Map _params)
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
        String pubdate;
        String result;
        
        pubdate = pnull(_params.get("pubdate"));
        String query = "";
        result = "";
        
        try
        {
            query = " SELECT DESCRIPTION FROM HOLIDAY  WHERE H_DATE = '"+pubdate+"' ";
            Map m = super.executeQueryMap(query);
            result = pnull( m, "DESCRIPTION" );
        }
        catch(Exception e)
        {
            e.printStackTrace();
        } finally {
        	free();
    	}
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Result:"+result );
        return result;
    }

    @Override
    public boolean mgzInsert(Map _params)
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
        boolean result=true;
        String mgz_no;
        String pubdate;
        String pdffile;
        String mp3file;
        
        PreparedStatement preparedStatement;
        
        mgz_no = pnull(_params.get("mgz_no"));
        pubdate = pnull(_params.get("pubdate"));
        pdffile = pnull(_params.get("pdffile"));
        mp3file = pnull(_params.get("mp3file"));
        preparedStatement = null;
        int i = 0;
        try
        {
            String query = "INSERT INTO MAGAZINE  (no, pub_idx, regdate, is_mailing, pubdate, is_publish, pdf, mp3) "
            		+ " VALUES (?, '3', getdate(), '1', ?, '1', ?, ?) ";
            preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1, mgz_no);
            preparedStatement.setString(2, pubdate);
            preparedStatement.setString(3, pdffile);
            preparedStatement.setString(4, mp3file);
            i = preparedStatement.executeUpdate();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            result = false;
        } finally {
        	
        	if(preparedStatement != null) try { preparedStatement.close(); } catch ( Exception e ) { e.printStackTrace(); }
        	free();
    	}
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Result:"+result+", i="+i );
        return result && i > 0;
    }

    @Override
    public boolean mgzModify(Map _params)
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
        boolean result = true;
        String m_idx;
        String pubdate;
        String pdffile;
        String mp3file;
        String no = pnull(_params.get("mgz_no"));
        PreparedStatement preparedStatement;
        
        
        m_idx = pnull(_params.get("m_idx"));
        pubdate = pnull(_params.get("pubdate"));
        pdffile = pnull(_params.get("pdffile"));
        mp3file = pnull(_params.get("mp3file"));
        
        // 이전 파일 삭제 flag :: pdf
        if(pnull(_params.get("delPdf")).equals("Y")) {
        	String ctxroot = getSession(_params, "CONTEXT_ROOT");
        	String fn = "", thumbnail=null;
        	try {
//        		Object fno = executeColumnOne("SELECT PDF FROM MAGAZINE WHERE M_IDX="+m_idx, 1);
				Object fno = executeColumnOne("SELECT PDF FROM MAGAZINE WHERE NO="+no, 1);
				if(fno!=null) {
					FileUtils.getInstance().deleteFile(ctxroot+fno.toString(), thumbnail);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
        
        // update
        preparedStatement = null;
        int i = 0;
        try
        {
            String query = "UPDATE MAGAZINE SET pubdate=?, pdf=?, mp3=? WHERE m_idx=?";
            preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1, pubdate);
            preparedStatement.setString(2, pdffile);
            preparedStatement.setString(3, mp3file);
            preparedStatement.setInt(4, Integer.parseInt(m_idx));
            i = preparedStatement.executeUpdate();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            result = false;
        } finally {
        	
        	if(preparedStatement != null) try { preparedStatement.close(); } catch ( Exception e ) { e.printStackTrace(); }
        	free();
    	}
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Result:"+result+", i="+i );
        return result && i > 0;
    }

    @Override
    public boolean mgzDelete(Map _params)
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
        boolean result=true;
        String m_idx = pnull(_params.get("m_idx"));
        
        int i = 0;
        try
        {
            String query = "delete from MAGAZINE where m_idx = "+m_idx;
            i = executeUpdate(query);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            result = false;
        } finally {
        	free();
    	}
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Result:"+result+", i="+i );
        return result && i > 0;
    }

    @Override
    public String mgzMaxNo(Map _params)
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
        String result="";
        String query = "";
        try
        {
            query = " SELECT max(no)+1 max_no FROM MAGAZINE ";
            int i = super.executeCount(query, false);
            result = ""+i;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        } finally {
        	free();
    	}
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Result:"+result );
        return result;
    }

    private final Logger _logger = Logger.getLogger(getClass());
}
