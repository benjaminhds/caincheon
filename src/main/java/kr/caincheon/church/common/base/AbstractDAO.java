package kr.caincheon.church.common.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

/**
 * DAO의 추상화 클래스
 * @author benjamin
 */
public abstract class AbstractDAO extends CommonParameter {
	
	Logger log = Logger.getLogger(this.getClass());

	// 페이징 :: 블록
//	@Value("#{common['DEFAULT_blockSize']}")
	private int DEFAULT_blockSize = 1;
	
	// 페이징 :: 페이지 사이즈
//	@Value("#{common['DEFAULT_PAGESIZE']}")
	private int DEFAULT_PAGESIZE = 20;
	
	// 
	@Autowired
    private SqlSessionTemplate sqlSession;
    
	//
    protected void printQueryId(String queryId) {
        if(log.isDebugEnabled()){
            log.debug("\t QueryId  \t:  " + queryId);
        }
    }
     
    public Object insert(String queryId, Object params) {
        printQueryId(queryId);
        return sqlSession.insert(queryId, params);
    }
     
    public Object update(String queryId, Object params) {
        printQueryId(queryId);
        return sqlSession.update(queryId, params);
    }
     
    public Object delete(String queryId, Object params) {
        printQueryId(queryId);
        return sqlSession.delete(queryId, params);
    }

    public Object selectOne(String queryId) {
        printQueryId(queryId);
        return sqlSession.selectOne(queryId);
    }

    public Map selectOneRow(String queryId) {
        printQueryId(queryId);
        Object row = sqlSession.selectOne(queryId);
        if(row == null) {
        	row = new HashMap();
        }
        return (Map)row;
    }
    
    public Object selectOne(String queryId, Object params) {
    	printQueryId(queryId);
    	return sqlSession.selectOne(queryId, params);
    }
    
    public int selectCount(String queryId, Object params) {
        printQueryId(queryId);
        Object obj = sqlSession.selectOne(queryId, params);
        return Integer.parseInt(String.valueOf(obj).replaceAll("[^0-9]", ""));
    }
    
    /**
     * 목록조회 :: 페이징객체 없음, 파라메터 없이 조회
     * @param queryId
     * @return
     */
    @SuppressWarnings("rawtypes")
    public List selectList(String queryId) {
        printQueryId(queryId);
        return sqlSession.selectList(queryId);
    }
     
    /**
     * 목록조회 :: 페이징객체 없음, 파라메터를 가지고  조회
     * @param queryId
     * @param params
     * @return
     */
    @SuppressWarnings("rawtypes")
    public List selectList(String queryId, Object params) {
        printQueryId(queryId);
        return sqlSession.selectList(queryId, params);
    }
    
    /**
     * 목록조회 :: 페이징객체를 함께 세팅
     * @param queryId
     * @param params
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
	public Map selectPagingList(String queryId, Object params) {
    	printQueryId(queryId);

    	Map<String,Object>  returnMap = new HashMap<String,Object>();
    	Map<String, Object> map       = (Map<String, Object>)params;
    	
    	// 현재페이지 번호
    	if(!map.containsKey("pageNo") || StringUtils.isEmpty(map.get("pageNo")))
    		map.put("pageNo", "1");
    	
    	//
    	int pageNo = Integer.parseInt(map.get("pageNo").toString());
    	int blockSize   = DEFAULT_blockSize ;
    	int pageSize    = DEFAULT_PAGESIZE;
    	
    	// page size :: 페이지 사이즈
    	if(map.containsKey("pageSize") && !StringUtils.isEmpty(map.get("pageSize")))
    		pageSize = Integer.parseInt(map.get("pageSize").toString());
    	
    	// page block size :: 블록 사이즈(페이지들의 사이즈) 
    	if(map.containsKey("blockSize") && !StringUtils.isEmpty(map.get("blockSize")))
    		blockSize = Integer.parseInt(map.get("blockSize").toString());
    	
    	// orderby
    	if(!map.containsKey("orderby"))
    		map.put("orderby", "1");
    	
    	
    	// new 
    	Paging pInfo = new Paging(blockSize, pageSize, pageNo);
    	
//    	int start = pInfo.getStartRow();
    	//int end = start + pInfo.getEndRow();
    	//map.put("START", start + 1);
    	//map.put("END", end);
    	map.put("Paging", pInfo);
    	
    	params = map;
    	
    	List<Map<String, Object>> list = sqlSession.selectList(queryId, map);

    	// 
    	if(list == null) {
    		list = new java.util.ArrayList();
    		Map row = new HashMap();
    		row.put("ROWNUM", "0");
    		row.put("TOTAL_CNT", "0");
    		list.add(row);
    	} 
    	if(list.size() == 0) {
    		if(pInfo != null) {
    			pInfo.setTotalCount(0);
    			returnMap.put("Paging", pInfo);
    		}
    	} else {
    		if(pInfo != null) {
    			pInfo.setTotalCount(Integer.parseInt(list.get(0).get("TOTAL_CNT").toString()));
    			returnMap.put("Paging", pInfo);
    		}
    	}
    	returnMap.put("List", list);
    	
    	//
    	return returnMap;
    }
}
