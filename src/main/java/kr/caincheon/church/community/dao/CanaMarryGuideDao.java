package kr.caincheon.church.community.dao;

import java.util.List;
import java.util.Map;

import kr.caincheon.church.common.base.CommonDaoDTO;

public interface CanaMarryGuideDao {

	public void list(Map _params, CommonDaoDTO dto) throws Exception;
	
	public int insert(Map _params) throws Exception;
	
	public int update(Map _params) throws Exception;
	
	public int delete(Map _params) throws Exception;
	
}
