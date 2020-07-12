package kr.caincheon.church.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import kr.caincheon.church.common.base.CommonDao;
import kr.caincheon.church.common.base.CommonDaoDTO;

/**
 * 전례력 처리하는 DAO
 */
@Repository("holidayFileDao")
public class HolidayFileDaoImpl extends CommonDao
    implements HolidayFileDao
{

	private final Logger _logger = Logger.getLogger(getClass());
	
	@Override
    public boolean insertTempFile(List list)
    {
		D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[Insertable Rows Count:"+list.size()+"]" );
		
        int rn=0;
        boolean bReturn = true;
        PreparedStatement preparedStatement = null;
        
        if(list.size() > 0) {
	        try {
	        	Map tmp = (Map)list.get(0);
	        	String d = pnull(tmp, "B");
	        	String query = "DELETE FROM HOLIDAY WHERE CONVERT(VARCHAR(4), H_DATE, 120) = '"+ d.substring(0,4) +"'";
	            rn = executeUpdate(query);
	        } catch(Exception e) {
	            e.printStackTrace();
	            bReturn = false;
	        } finally {
	    	}
        }
        
        _logger.debug("\n\n ==> "+list.get(0) + "\n");
        
        try {
            String query = "INSERT INTO HOLIDAY (h_idx, h_date, gubun, description, talk) values (?, CONVERT(datetime, ?), ?, ?, ?) ";
            preparedStatement = getConnection().prepareStatement(query);
            for(int i = 0; i < list.size(); i++)
            {
                Map tmp = (Map)list.get(i);
                preparedStatement.setString(1, pnull(tmp, "A"));
                preparedStatement.setString(2, pnull(tmp, "B"));
                preparedStatement.setString(3, pnull(tmp, "C").substring(0,1));
                preparedStatement.setString(4, pnull(tmp, "D"));
                preparedStatement.setString(5, pnull(tmp, "E"));
                rn += preparedStatement.executeUpdate();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            bReturn = false;
        } finally {
        	if(preparedStatement != null) try { preparedStatement.close(); } catch ( Exception e ) { e.printStackTrace(); }
        	free();
    	}
        
        return bReturn && rn > 0;
    }

    @Override
    public void holidayList(Map _params, CommonDaoDTO dto)
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
        String query = "", query0 = "";
        
        int pageNo    = ipnull(_params, "pageNo", 1);
        int pageSize  = ipnull(_params, "pageSize", 10);
        int startRnum = (pageNo - 1) * pageSize + 1;
        int endRnum   = pageNo * pageSize;

        try {
            query0 = "SELECT ROW_NUMBER() OVER(ORDER BY H_DATE DESC) RNUM, CONVERT(VARCHAR(10),  H_DATE, 120) H_DATE, DESCRIPTION, TALK "
            		+ " FROM HOLIDAY "
            		+ " WHERE 1 = 1 AND GUBUN = '1'"
            		;

            query = "SELECT A.* FROM ("
            		+ query0
            		+ " ) A "
            		+ " WHERE RNUM BETWEEN "+startRnum+" AND "+endRnum;
            
            // list
            dto.daoList = super.executeQueryList(query);
            // total count
            dto.daoTotalCount = executeCount(query0, true);
            
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        	free();
    	}
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO return. DTO -> " + dto );
        
    }

    @Override
    public Map holidayContents(Map _params)
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
        Map result = null;
        String query = "";
        try
        {
            query = "SELECT CONVERT(VARCHAR(10),  H_DATE, 120) H_DATE, DESCRIPTION, TALK "
            		+ " FROM HOLIDAY   "
            		+ " WHERE GUBUN = '1' AND CONVERT(VARCHAR(10),  H_DATE, 120) = '"+pnull(_params.get("h_date"))+"'";
            result = super.executeQueryMap(query);
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        	free();
    	}
        return result;
    }

    @Override
    public boolean holidayModify(Map _params)
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
        int rn=0;
        PreparedStatement preparedStatement = null;
        boolean bReturn = true;
        
        String query = "";
        try {
            query = "UPDATE HOLIDAY SET  DESCRIPTION= ?, TALK = ?  WHERE GUBUN='1' AND CONVERT(VARCHAR(10),  H_DATE, 120) = ? ";
            preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1, pnull(_params.get("description")));
            preparedStatement.setString(2, pnull(_params.get("talk")));
            preparedStatement.setString(3, pnull(_params.get("h_date")));
            rn = preparedStatement.executeUpdate();
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
            bReturn = false;
        }  finally {
        	if(preparedStatement != null) try { preparedStatement.close(); } catch ( Exception e ) { e.printStackTrace(); }
        	free();
    	}
        return bReturn && rn > 0;
    }

    @Override
    public boolean holidayDelete(Map _params)
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
        int rn = 0;
        String h_date = pnull(_params.get("h_date"));
        boolean bReturn = true;
        String query = "";
        try {
            query = "delete from HOLIDAY where convert(varchar(10),  H_DATE, 120)= "+h_date;
            rn = executeUpdate(query);
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
            bReturn = false;
        } finally {
        	free();
    	}
        return bReturn && rn > 0;
    }

    
}
