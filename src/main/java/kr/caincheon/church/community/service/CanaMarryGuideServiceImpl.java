package kr.caincheon.church.community.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.base.CommonException;
import kr.caincheon.church.community.dao.CanaMarryGuideDao;

@Service("canaMarryGuideService")
public class CanaMarryGuideServiceImpl implements CanaMarryGuideService {
	
    @Resource(name="canaMarryGuideDao")
    private CanaMarryGuideDao canaMaryyGuideDao;
	
    /**
	 * 목록조회 :: 카나혼(type=1)과 주말혼인 강좌(type=2)
	 */
    public CommonDaoDTO listMarryGuide(Map<String,Object> _params) throws Exception {
    	CommonDaoDTO dto = new CommonDaoDTO();
    	
    	canaMaryyGuideDao.list(_params, dto);
    	
    	return dto;
    }
	
	/**
	 * 등록 :: 카나혼과 주말혼인 강좌
	 */
    public int insertMarryGuide(Map<String,Object> _params) throws Exception {
    	
    	
    	return canaMaryyGuideDao.insert(_params);
    }
	
	/**
	 * 수정 :: 카나혼과 주말혼인 강좌
	 */
    public int updateMarryGuide(Map<String,Object> _params) throws Exception {
    	
    	return canaMaryyGuideDao.update(_params);
    }
	
	/**
	 * 삭제 :: 카나혼과 주말혼인 강좌 - 삭제 flag on
	 */
    public int deleteMarryGuide(Map<String,Object> _params) throws CommonException, Exception {
    	
    	return canaMaryyGuideDao.delete(_params);
    }
    

}
