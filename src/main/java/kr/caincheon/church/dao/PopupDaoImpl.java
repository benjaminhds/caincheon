package kr.caincheon.church.dao;

import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import kr.caincheon.church.common.base.CommonDao;
import kr.caincheon.church.common.base.CommonDaoDTO;

/**
 * 팝업 관리하는 DAO Implementation 클래스
 * @author benjamin
 */
@Repository("popupDao")
public class PopupDaoImpl extends CommonDao implements PopupDao
{

	/*
	 * (non-Javadoc)
	 * @see kr.caincheon.church.dao.PopupDao#popupList(java.util.Map)
	 */
	@Override
    public void popupList(Map _params, CommonDaoDTO dto)
    {
        String whereStr = "";
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        String searchGubun = "";
        String searchText = pnull(_params.get("searchText"));
        
        int pageNo   = ipnull(_params, "pageNo", 1);
        int pageSize = ipnull(_params, "pageSize", 20);
        int startRnum = (pageNo - 1) * pageSize + 1;
        int endRnum = pageNo * pageSize;
        
        // from admin
        if(searchText != null && searchText.length() > 0)
        {
            searchGubun = pnull(_params.get("searchGubun"));
            if(searchGubun.equals("title"))
                whereStr = " AND TITLE LIKE '%"+searchText+"%'";
            else if(searchGubun.equals("name"))
                whereStr = " AND NAME LIKE '%"+searchText+"%'";
            else if(searchGubun.equals("contents"))
                whereStr = " AND CONTENTS LIKE '%"+searchText+"%'";
        }
        
        // etc :: from front side
        String use_yn  = pnull(_params, "use_yn");
        if(use_yn.length() > 0 ) {
        	use_yn = " AND USE_YN='"+use_yn+"' ";
        } else {
        	use_yn = "";
        }
        String open_date= pnull(_params, "open_date");//형식:'2017-11-28 15:00:01' or 'now'
        String open_yn  = pnull(_params, "open_yn");
        if(use_yn.length() > 0 ) {
        	open_yn = " AND OPEN_YN='"+open_yn+"' ";
        } else {
        	open_yn = "";
        }
        if("now".equalsIgnoreCase(open_date)) {
        	open_date = " AND POST_DATE_FROM <= GETDATE() AND POST_DATE_TO >= GETDATE()";
        } else if (open_date.length() > 3) {
        	open_date = " AND POST_DATE_FROM <= CONVERT(DATETIME, '"+open_date+"') AND POST_DATE_TO >= CONVERT(DATETIME, '"+open_date+"') ";
        } else {
        	open_date = "";
        }
        
        String query = "", query0 = "";
        try {
        	query0 = " SELECT /* 팝업관리(popup) 목록 */ ROW_NUMBER() OVER(ORDER BY registDT DESC) RNUM"
        			+ ", POPUP_NO, ID, NAME, POPUP_TYPE, TITLE, CONTENTS, USE_YN, OPEN_YN"
        			+ ", WINDOW_POS_LEFT, WINDOW_POS_TOP, WINDOW_SIZE_WIDTH, WINDOW_SIZE_HEIGHT"
            		+ ", CONVERT(CHAR(10),  POST_DATE_FROM, 120) POST_DATE_FROM"
            		+ ", CONVERT(CHAR(10),  POST_DATE_TO  , 120) POST_DATE_TO"
            		+ ", CASE WHEN OPEN_YN = 'Y' THEN '노출'  ELSE '비노출'  END AS OPEN_YN_TEXT"
            		+ ", CASE WHEN USE_YN  = 'Y' THEN '사용'  ELSE '미사용'  END AS USE_YN_TEXT"
            		+ ", CONVERT(CHAR(10),  REGISTDT, 120) REGISTDT, CONVERT(CHAR(10),  UPDATEDT, 120) UPDATEDT"
            		+ " FROM POPUP_BOX "
            		+ " WHERE 1=1 "+whereStr
            		+ open_date
            		+ open_yn
            		+ use_yn
            		;
        	
            query = "SELECT * FROM  ("
            		+ query0
            		+ " ) ROWS "
            		+ " WHERE RNUM BETWEEN "+startRnum+" AND "+endRnum;
            // list
            dto.daoList = executeQueryList(query);
            // total count
            dto.daoTotalCount = executeCount(query0, true);
            // paging
            dto.setPaging(pageNo, pageSize);
            
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        	free();
    	}
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO dto:"+dto );
        
    }

	@Override
    public Map popupContents(Map _params)
    {
        Map result;
        String query_type;
        String popup_no;
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        result = new HashMap();
        query_type = pnull(_params.get("query_type"));
        popup_no = pnull(_params.get("popup_no"));
        String whereStr = "", query="";
        
        try {
            if(query_type.equals("modify")) {
                whereStr = " WHERE POPUP_NO = '"+popup_no+"'";
                query    = "SELECT /* 팝업관리(popup) 조회 */ POPUP_NO, ID, NAME, POPUP_TYPE, OPEN_YN"
                		+ ", CONVERT(CHAR(16), POST_DATE_FROM, 120) POST_DATE_FROM"
                		+ ", CONVERT(CHAR(16), POST_DATE_TO  , 120) POST_DATE_TO"
                		+ ", WINDOW_POS_LEFT, WINDOW_POS_TOP, WINDOW_SIZE_WIDTH, WINDOW_SIZE_HEIGHT, TITLE, CONTENTS, USE_YN"
                		+ ", CONVERT(CHAR(10), REGISTDT, 120) REGISTDT"
                		+ ", CONVERT(CHAR(10), UPDATEDT, 120) UPDATEDT "
                		+ " FROM POPUP_BOX "+whereStr;
                result   = super.executeQueryMap(query);
            }
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        	free();
    	}
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Result:"+result );
        return result;
    }

	@Override
    public boolean popupInsert(Map _params)
    {
        boolean bReturn;
        
        PreparedStatement preparedStatement = null;
        bReturn = true;
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        String id = pnull(_params.get("id"));
        String popup_no = pnull(_params.get("popup_no"));
        String popup_type = pnull(_params.get("popup_type"));
        String open_yn = pnull(_params.get("open_yn"));
        String post_date_from = pnull(_params.get("post_date_from"));
        String post_date_to = pnull(_params.get("post_date_to"));
        String window_pos_left = pnull(_params.get("window_pos_left"));
        String window_pos_top = pnull(_params.get("window_pos_top"));
        String window_size_width = pnull(_params.get("window_size_width"));
        String window_size_height = pnull(_params.get("window_size_height"));
        String title = pnull(_params.get("title"));
        String contents = pnull(_params.get("contents"));
        String use_yn = "Y";
        Map memberInfo = getMember(_params);
        
        int i = 0;
        String query = "";
        try
        {
            query = "INSERT INTO /* 팝업관리(popup) 신규등록 */ POPUP_BOX  (id, name"
            		+ ", popup_type, open_yn"
            		+ ", post_date_from, post_date_to"
            		+ ", window_pos_left, window_pos_top, window_size_width, window_size_height"
            		+ ",  title, contents, use_yn"
            		+ ", registDT, updateDT) "
            		+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, getdate(),getdate())";
            preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, pnull(memberInfo.get("NAME")));
            preparedStatement.setString(3, popup_type);
            preparedStatement.setString(4, open_yn);
            preparedStatement.setString(5, post_date_from);
            preparedStatement.setString(6, post_date_to);
            preparedStatement.setString(7, window_pos_left);
            preparedStatement.setString(8, window_pos_top);
            preparedStatement.setString(9, window_size_width);
            preparedStatement.setString(10, window_size_height);
            preparedStatement.setString(11, title);
            preparedStatement.setString(12, contents);
            preparedStatement.setString(13, use_yn);
            i = preparedStatement.executeUpdate();
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
            bReturn = false;
        } finally {
        	
        	if(preparedStatement != null) try { preparedStatement.close(); } catch ( Exception e ) { e.printStackTrace(); }
        	free();
    	}
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Result:"+ (bReturn && i > 0) );
        return bReturn && i > 0;
    }

	@Override
    public boolean popupModify(Map _params)
    {
        boolean bReturn;
        
        PreparedStatement preparedStatement = null;
        bReturn = true;
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        String id = pnull(_params.get("id"));
        String popup_no = pnull(_params.get("popup_no"));
        String popup_type = pnull(_params.get("popup_type"));
        String open_yn = pnull(_params.get("open_yn"));
        String post_date_from = pnull(_params.get("post_date_from"));
        String post_date_to = pnull(_params.get("post_date_to"));
        String window_pos_left = pnull(_params.get("window_pos_left"));
        String window_pos_top = pnull(_params.get("window_pos_top"));
        String window_size_width = pnull(_params.get("window_size_width"));
        String window_size_height = pnull(_params.get("window_size_height"));
        String title = pnull(_params.get("title"));
        String contents = pnull(_params.get("contents"));
        
        Map memberInfo = getMember(_params);
        
        int i = 0;
        String query = "";
        try
        {
            query = "UPDATE /* 팝업관리(popup) 수정 */ POPUP_BOX  SET name=?"
            		+ ", popup_type=?, open_yn=?"
            		+ ", post_date_from=?, post_date_to=?"
            		+ ",  window_pos_left=?, window_pos_top=?, window_size_width=?, window_size_height=?"
            		+ ",  title=?, contents=?, updateDT=getdate() "
            		+ " WHERE popup_no = ? and id= ?";
            preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1, pnull(memberInfo.get("NAME")));
            preparedStatement.setString(2, popup_type);
            preparedStatement.setString(3, open_yn);
            preparedStatement.setString(4, post_date_from);
            preparedStatement.setString(5, post_date_to);
            preparedStatement.setString(6, window_pos_left);
            preparedStatement.setString(7, window_pos_top);
            preparedStatement.setString(8, window_size_width);
            preparedStatement.setString(9, window_size_height);
            preparedStatement.setString(10, title);
            preparedStatement.setString(11, contents);
            preparedStatement.setInt(12, Integer.parseInt(popup_no));
            preparedStatement.setString(13, id);
            
            i = preparedStatement.executeUpdate();
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
            bReturn = false;
        } finally {
        	if(preparedStatement != null) try { preparedStatement.close(); } catch ( Exception e ) { e.printStackTrace(); }
        	free();
    	}
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Result:"+ (bReturn && i > 0) );
        return bReturn && i > 0;
    }

	/**
	 * 팝업 삭제 Flag 설정
	 * (non-Javadoc)
	 * @see kr.caincheon.church.dao.PopupDao#popupDelete(java.util.Map)
	 */
	@Override
    public boolean popupDelete(Map _params)
    {
        boolean bReturn = true;
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
        
        String popup_no = pnull(_params.get("popup_no"));
        String use_yn   = pnull(_params, "use_yn", "Y");
        int i = 0;
        String query = "";
        try {
            //query = "DELETE /* 팝업관리(popup) 삭제 */ FROM POPUP_BOX WHERE POPUP_NO="+popup_no;
            query = "UPDATE /* 팝업관리(popup) 삭제 */ POPUP_BOX  SET USE_YN='"+use_yn+"', updateDT=getdate() "
            		+ " WHERE POPUP_NO="+popup_no;
            
            i = executeUpdate(query);
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
            bReturn = false;
        } finally {
        	free();
    	}
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Result:"+ (bReturn && i > 0) );
        return bReturn && i > 0;
    }

	@Override
    public Map getMember(Map _params)
    {
		D(_logger, Thread.currentThread().getStackTrace(), "DAO Called.[params:"+_params+"]" );
		
        Map result;
        result = new HashMap();
        String query="";
        try
        {
            query = (new StringBuilder("SELECT ADM_NAME AS NAME FROM ADMMEMBER WHERE ADM_ID='")).append(_params.get("id")).append("' ").toString();
            result = super.executeQueryMap(query);
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e);
        } finally {
        	free();
    	}
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Result:"+result );
        return result;
    }

    private final Logger _logger = Logger.getLogger(getClass());
}
