// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EngageDaoImpl.java

package kr.caincheon.church.dao;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import kr.caincheon.church.common.base.CommonDao;

// Referenced classes of package kr.caincheon.church.dao:
//            EngageDao

@Repository("engageDao")
public class EngageDaoImpl extends CommonDao
    implements EngageDao
{
	private final Logger _logger = Logger.getLogger(getClass());
	
	/**
	 * front service :: 주말약혼자 강좌 일자 
	 * (non-Javadoc)
	 * @see kr.caincheon.church.dao.EngageDao#engageGuide(java.util.Map)
	 */
	@Override
    public List engageGuide(Map _params)
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
    	
        List result = null;
        String query = "";

        try {
            query = "SELECT A.MARRY_GUIDE_NO, A.EXPLAIN, A.EDU_GUIDE"
            		+ ", CONVERT(VARCHAR(10), CONVERT(DATETIME, A.EDU_DATE_START), 120) AS FKEY "
            		+ ", CONVERT(VARCHAR(10), CONVERT(DATETIME, A.EDU_DATE_START)+2, 120) AS TKEY "/* 약혼자주말은 2박3일 일정 */
            		+ " FROM MARRY_GUIDE A "
            		+ " WHERE TYPE='2'/*주말약혼자강좌*/ AND CLOSE_YN='N' AND DEL_YN='N' AND EDU_DATE_START > CONVERT(CHAR(10), GETDATE(), 112) "
            		+ " ORDER BY A.EDU_DATE_START ASC "
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

    
}
