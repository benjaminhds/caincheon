package kr.caincheon.church.common.base;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

//import kr.caincheon.church.common.CommonSQL;
import kr.caincheon.church.common.utils.ResultSetHandler;

/**
 * DAO를 위한 공통 OP를 제공한다.  
 * @author benjamin
 */
public abstract class CommonDao extends CommonParameter { // implements CommonSQL {
	
	// 
	protected String lastSQL = "";
	
	
	/*
	 * DataSource : A DataSource Instance injected by spring framework
	 * mvc-context.xml : <bean id="dataSource" --> Have to use a @Autowired. 
	 *                   Then 'dataSource' variable will become not null.  
	 */
	@Autowired
	protected DataSource dataSource;
	public void setDataSource(DataSource dataSrc) {
		this.dataSource = dataSrc;
	}

	protected Connection __conn;
	protected Statement __stmt;
	protected PreparedStatement __pstmt;

	protected Connection getConnection() throws SQLException {
		return getConnection(true);
	}

	protected Connection getConnection(boolean isAutoCommit) throws SQLException {
		Connection conn = dataSource.getConnection();
		conn.setAutoCommit(isAutoCommit);
		return conn;
	}
	
	protected void transactionStart() throws SQLException {
		if(__conn==null) {
			getConnection(false);
		} else {
			__conn.setAutoCommit(false);
		}
	}
	protected void transactionEnd() throws SQLException {
		__conn.commit();
	}
	protected void transactionFail() throws SQLException {
		__conn.rollback();
	}
	protected void commit() {
		try {
			__conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
		}
	}
	
	// execution select query
	protected ResultSet executeQuery(String query) throws SQLException {
		if(__conn==null) { __conn=this.getConnection(); }
		if(__stmt==null) { __stmt=__conn.createStatement();   }
		lastSQL = query;
		D(Thread.currentThread(), "executeQuery()::"+lastSQL);
		return __stmt.executeQuery(lastSQL);
	}

	// execution select query
	protected List<Map<String, Object>> executeQueryList(String query) throws SQLException {
		if(__conn==null) { __conn=this.getConnection(); }
		if(__stmt==null) { __stmt=__conn.createStatement();   }
		List<Map<String, Object>> l = executeQueryList(query, __stmt);
		return l;
	}

	// execution select query
	protected List<Map<String, Object>> executeQueryList(String query, Connection pConn) throws SQLException {
		Statement stmt = pConn.createStatement();
		List<Map<String, Object>> l = executeQueryList(query, stmt);
		free(stmt);
		return l;
	}

	// execution select query
	protected List<Map<String, Object>> executeQueryList(String query, Statement pStmt) throws SQLException {
		lastSQL = query;
		D(Thread.currentThread(), "executeQueryList()::"+lastSQL);
		ResultSet rs = pStmt.executeQuery(lastSQL);
		List<Map<String, Object>> l = ResultSetHandler.conversion(rs);
		free(rs);
		return l;
	}
	
	/** execution select paging query :: You have to call setPaging(_parmas) and you should call this method. */
	protected List<Map<String, Object>> executeQueryPageList(String query) throws SQLException {
		if(__conn==null) { __conn=this.getConnection(); }
		if(__stmt==null) { __stmt=__conn.createStatement();   }
		
		// paging query combination
		lastSQL = "SELECT XXX.* FROM (" + query + ") XXX WHERE RNUM BETWEEN "+startRnum+" AND "+endRnum;
		
		// common
		D(Thread.currentThread(), "executeQueryList()::"+lastSQL);
		ResultSet rs = __stmt.executeQuery(lastSQL);
		List<Map<String, Object>> l = ResultSetHandler.conversion(rs);
		free(rs);
		return l;
	}

	/**
	 * execution select paging query
	 * - SQL작성시 RNUM컬럼 "ROW_NUMBER() OVER(ORDER BY 절) RNUM" 이 반드시 있어야 함. 
	 */
	protected List<Map<String, Object>> executeQueryPageList(String query, int _pageNo, int _pageSize) throws SQLException {
		if(__conn==null) { __conn=this.getConnection(); }
		if(__stmt==null) { __stmt=__conn.createStatement();   }
		
		// paging query combination
		int sRNUM = (_pageNo - 1) * _pageSize + 1;
    	int eRNUM = _pageNo * _pageSize;
    	String tsql = query.toUpperCase();
    	if(tsql.indexOf("RNUM") != -1 || tsql.indexOf("ROW_NUMBER()") != -1) {
    		lastSQL = "SELECT XXX.* FROM (" + query + ") XXX WHERE RNUM BETWEEN "+sRNUM+" AND "+eRNUM;
    	} else {
    		// case1) auto-combined sql
    		//tsql = "SELECT ROW_NUMBER() OVER(ORDER BY 1 DESC) RNUM, ZX.* FORM ("+ query +") ZX ";
    		//lastSQL = "SELECT XXX.* FROM (" + query + ") XXX WHERE RNUM BETWEEN "+sRNUM+" AND "+eRNUM;
    		// case2) throw
    		throw new SQLException("SELECT 실행 문에 rowid 조회하는 'ROW_NUMBER() OVER(ORDER BY 컬럼 DESC) RNUM' 이 누락되었습니다. ");
    	}
		
		// common
		D(Thread.currentThread(), "executeQueryList()::"+lastSQL);
		ResultSet rs = __stmt.executeQuery(lastSQL);
		List<Map<String, Object>> l = ResultSetHandler.conversion(rs);
		free(rs);
		return l;
	}

	// execution select query
	protected Map<String, Object> executeQueryMap(String query) throws SQLException {
		getStatement();
		return _executeQueryMap(query, __stmt);
	}
	protected Map<String, Object> executeQueryMap(String query, Connection pConn) throws SQLException {
		if( pConn==null) { pConn=this.getConnection(); } 
		Statement pStmt = pConn.createStatement(); 
		Map<String, Object> rtMap = _executeQueryMap(query, pStmt);
		free(pStmt);
		return rtMap;
	}

	protected Map<String, Object> _executeQueryMap(String query, Statement pStmt) throws SQLException {
		lastSQL = query;
		D(Thread.currentThread(), "executeQueryMap()::"+lastSQL);
		ResultSet rs = pStmt.executeQuery(lastSQL);
		Map<String, Object> row = ResultSetHandler.conversionFirstRow(rs);
		free(rs);
		return row;
	}

	// execution count query
	protected Object executeColumnMax(String table, String column, String whereCuase) throws SQLException {
		// 최소쿼리문 체크
		
		if( whereCuase.trim().toUpperCase().indexOf("WHERE") != 0 ) throw new SQLException("where-cuase is wrong. whereCuase is must start by `WHERE`.");
		
		String query = "SELECT ISNULL(MAX("+column+"), '0') FROM "+table + " " + whereCuase;
		lastSQL = query;
		
		D(Thread.currentThread(), "executeColumnMax()::"+lastSQL);
		
		Object maxObj = null;
		ResultSet rs = getStatement().executeQuery(lastSQL);
		if(rs!=null) {
			rs.next();
			maxObj = rs.getObject(1);
		}
		free(rs);
		
		return maxObj;
	}

	// execution count query
	protected Object executeColumnOne(String query, int columnIndex) throws SQLException {
		// 최소쿼리문 체크
		lastSQL = query;
		if(columnIndex<1) columnIndex = 1;
		D(Thread.currentThread(), "executeColumnOne(select column Index="+columnIndex+")::"+lastSQL);
		
		Object maxObj = null;
		ResultSet rs = getStatement().executeQuery(lastSQL);
		if(rs!=null) {
			rs.next();
			maxObj = rs.getObject(columnIndex);
		}
		free(rs);
		
		return maxObj;
	}
	// execution count query
	protected int executeCount(String query, boolean isCountSqlWrapping) throws SQLException {
		// 최소쿼리문 체크
		if(query==null || query.length() < 10) {
			return -9;
		}
		if(isCountSqlWrapping) {
			query = "SELECT COUNT(*) FROM ( " +  query + " ) XY ";
		}
		lastSQL = query;
		
		D(Thread.currentThread(), "executeCount()::"+lastSQL);
		
		int rtCount = -1;
		ResultSet rs = getStatement().executeQuery(lastSQL);
		if(rs!=null) {
			rs.next();
			rtCount = rs.getInt(1);
		}
		D(Thread.currentThread(), "executeCount() return count : "+rtCount);
		free(rs);
		
		return rtCount;
	}
	// execution count query
	protected int executeCount(String query) throws SQLException {
		return executeCount(query, true) ;
	}
	// execution count query
	public int executeCount() throws SQLException {
		int orderby = lastSQL.toLowerCase().indexOf("order by");
		String sql = lastSQL;
		if(orderby > 0) {//첫번째 order by 이후 쿼리에서 잘라 버린다. 단순 쿼리에서는 사용 가능
			sql = lastSQL.substring(0, orderby);
		}
		return executeCount(sql, true);
	}
	
	// execution insert/update/delete query
	protected int executeUpdate(String query) throws SQLException {
		lastSQL = query;
		D(Thread.currentThread(), "executeUpdate()::"+lastSQL);
		return getStatement().executeUpdate(lastSQL);
	}
	
	
	// return the Statement Object
	protected Statement getStatement() throws SQLException {
		if(__conn==null) { __conn=this.getConnection(); }
		if(__stmt==null) { __stmt=__conn.createStatement();   }
		return __stmt;
	}
	
	// 파라메터 순서를 유지하며, 키는 숫자로 한다. LinkedHashMap으로 변환해서 리턴
	protected LinkedHashMap<String, Object> getLinkedHashMap(Object... objs) {
		LinkedHashMap<String, Object> rtParams = new LinkedHashMap<String, Object>();
		int i = 1;
		for(Object o : objs) {
			rtParams.put( "" + i++ , o );
		}
		return rtParams;
	}

	// void the PreparedStatement Object
	protected int executeUpdatePreparedStatement(String query, LinkedHashMap lmap) throws SQLException {
		lastSQL = query;
		if(__conn==null)  { __conn=this.getConnection(); }
		if(__conn!=null) {
			if(__pstmt==null)
				__pstmt=__conn.prepareStatement(lastSQL);
			if(__pstmt.isClosed()) {
				__pstmt = null;
				__pstmt = __conn.prepareStatement(lastSQL);
			}
		}
		
		return executeUpdatePreparedStatement(__pstmt, lmap, true);
	}
	protected int executeUpdatePreparedStatement(PreparedStatement _pStmt, LinkedHashMap lmap, boolean isClose) throws SQLException {
		
		I(Thread.currentThread().getStackTrace(), "executeUpdatePreparedStatement()\n\t::_pStmt->"+_pStmt+"\n\t::PARAMS->"+lmap+"\n\t::SQL->"+lastSQL);
		
		String tmp = "";
		Iterator keys = lmap.keySet().iterator();
		int i = 1;
		while(keys.hasNext()) {
			Object elemnt = keys.next();
			Object eleVal = lmap.get(elemnt);
			
			tmp += "\n\t\t\t " + i + ", " + eleVal ;
			
			if( eleVal instanceof String ) _pStmt.setString(i, eleVal.toString());
			else if( eleVal instanceof Integer ) _pStmt.setInt(i, (Integer)eleVal);
			else if( eleVal instanceof Long ) _pStmt.setLong(i, (Long)eleVal);
			else if( eleVal instanceof Float ) _pStmt.setFloat(i, (Float)eleVal);
			else if( eleVal instanceof Double ) _pStmt.setDouble(i, (Double)eleVal);
			else if( eleVal instanceof Date ) _pStmt.setDate(i, (Date)eleVal);
			else _pStmt.setObject(i, eleVal);
			i++;
		}
		try {
			i = _pStmt.executeUpdate();
		} catch (Exception e) {
			isClose = true;
			throw new SQLException(e);
		} finally {
			if(isClose) {
				free(_pStmt);
			}
		}
		if(i>0) __conn.commit();
		I(Thread.currentThread().getStackTrace(), "executeUpdatePreparedStatement():: executed result="+i+", ::set params->"+tmp);
		return i;
	}
	
	// return the PreparedStatement Object
	protected PreparedStatement getPreparedStatement(String query) throws SQLException {
		if(__conn==null) { __conn=this.getConnection(); }
		PreparedStatement pstmt = null;
		if(__conn!=null) { pstmt=__conn.prepareStatement(query); }
		lastSQL = query;
		I(Thread.currentThread().getStackTrace(), "getPreparedStatement()::"+lastSQL);
		return pstmt;
	}
	
	// free the ResultSet Object
	protected void freeResultSet(ResultSet rs) {
		this.free(rs);
	}
	// free the ResultSet Object
	protected void free(ResultSet rs) {
		if(rs!=null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		rs = null;
		
		I(Thread.currentThread().getStackTrace(), "Call a freeResultSet() :: Free a ResultSet.");
	}
	//
	protected void free(Statement pStmt) {
		if(pStmt!=null) {
			try {
				pStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				pStmt = null;
			}
		}
	}
	//
	protected void free(PreparedStatement pStmt) {
		if(pStmt!=null) {
			try {
				pStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
//			} finally {
//				pStmt = null;
			}
			pStmt = null;
		}
	}
	//
	protected void free(Connection _cnn) {
		if(_cnn!=null) {
			try {
				_cnn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				_cnn = null;
			}
		}
	}
	// 
	public void rollback() {
		try {
			I(Thread.currentThread().getStackTrace(), "Call a connection.rollbak()...try");
			__conn.rollback();
			I(Thread.currentThread().getStackTrace(), "Call a connection.rollbak()...success");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// DataSource Resource Free
	public void free() {
		if(__stmt!=null) {
			try { 
				__stmt.close(); 
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				__stmt = null;
			}
		}

		if(__pstmt!=null) {
			try { 
				__pstmt.close(); 
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				__pstmt = null;
			}
		}

		if(__conn!=null) {
			try { 
				__conn.commit();
				__conn.close();
				I(Thread.currentThread().getStackTrace(), "Call a connection.commit()...success");
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				__conn = null;
			}
		}
		
		I(Thread.currentThread().getStackTrace(), "Call a free() :: Free a stmt, pstmt, conn.");
	}
	
	//
	public String getLastSQL() {
		return lastSQL;
	}
	
	// paging
	protected int pageNo    = 1;
	protected int pageSize  = 20;
    protected int startRnum = 1;
    protected int endRnum   = 20;
    
    /** 페이징 처리를 쉽게 하기 위한 공통  함수 */
    protected void setPaging(Map _params) {
    	pageNo    = ipnull(_params, "pageNo", 1);
    	pageSize  = ipnull(_params, "pageSize", 20);
    	startRnum = (pageNo - 1) * pageSize + 1;
    	endRnum   = pageNo * pageSize;
    }
    protected void setPaging() {
    	startRnum = (pageNo - 1) * pageSize + 1;
    	endRnum   = pageNo * pageSize;
    }
    /** return a paging object */
    protected Paging getPageing(int totalCount) {
    	Paging paging = new Paging();
    	paging.setPageNo(pageNo);
    	paging.setPageSize(pageSize);
    	paging.setTotalCount(totalCount);
    	return paging;
    }

}
