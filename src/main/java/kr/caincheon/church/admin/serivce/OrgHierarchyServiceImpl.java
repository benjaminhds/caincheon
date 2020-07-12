package kr.caincheon.church.admin.serivce;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import kr.caincheon.church.admin.dao.OrgHierarchyDao;
import kr.caincheon.church.common.base.CommonException;
import kr.caincheon.church.common.base.CommonService;
import kr.caincheon.church.common.base.Const;


@Service("orgHierarchyService")
public class OrgHierarchyServiceImpl extends CommonService implements OrgHierarchyService 
{
    private final Logger _logger = Logger.getLogger(getClass());
    
    @Resource(name="orgHierarchyDao")
    private OrgHierarchyDao orgHierarchyDao;


	/**
	 * 조직 TREE 조회
	 * @param params
	 * @return
	 * @throws CommonException
	 */
    @Override
    public List selectOrgHierarchy(Map _params) throws CommonException, Exception {
    	String tp  = pnull(_params, "DEPTH_TYPE", "");
    	String lv1 = pnull(_params, "LV1", pnull(_params, "lv1", ""));
		String lv2 = pnull(_params, "LV2", pnull(_params, "lv2", ""));
		
		if(tp.equals("1")) {
			return orgHierarchyDao.selectOrgHierarchyGroupby(1, "");
		} else if(tp.equals("2")) {
			if(lv1.length() == 0) throw new Exception("The 'LV1' Parameter is empty.");
			lv1 += "' AND LV2!='00";
			return orgHierarchyDao.selectOrgHierarchyGroupby(2, lv1);
		}
		
		if(lv1.length() > 0 ) {
			_params.remove("LV1");
			_params.put("lv1", lv1+"' AND O.LV3!='000");
		}
		if(lv1.length() > 0 ) {
			_params.remove("LV2");
			_params.put("lv2", lv2);
		}
		
    	return orgHierarchyDao.selectOrgHierarchy(_params);
    }
    
    
    /**
     * 조직상하관계 데이터 조회
     */
    @Override
    public void selectOrgHierarchyGroupby(Map _params) throws CommonException {
    	
    	D(_logger, Thread.currentThread().getStackTrace(), "Service Called.[params:"+_params+"]" );
    	
    	// dao
    	List listG1 = null;
		List listG2 = null;
		List list   = null;
		int total   = 0;
		try {
			String tp  = pnull(_params, "DEPTH_TYPE", "");
			String lv1 = pnull(_params, "LV1", "");
			
			if(tp.length() == 0) {
				listG1 = orgHierarchyDao.selectOrgHierarchyGroupby(1, "");
				listG2 = orgHierarchyDao.selectOrgHierarchyGroupby(2, lv1);
				list   = orgHierarchyDao.selectOrgHierarchy(_params);
			} else {
				switch(tp.charAt(0)) {
				case '1':
					listG1 = orgHierarchyDao.selectOrgHierarchyGroupby(1, "");
					break;
				case '2':
					listG2 = orgHierarchyDao.selectOrgHierarchyGroupby(2, lv1);
					break;
				}
			}
			total  = orgHierarchyDao.totalCount();
		} catch (Exception e) {
			throw new CommonException("조직의 상하관계를 조회하지 못했습니다.", "EXPT-000", e);
		}
    	
		// return 
		String pageNo   = pnull(_params, "pageNo",   "1");
		String pageSize = pnull(_params, "pageSize", "150");
		_params.put(Const.ADM_MAPKEY_GROUPLIST+"1",listG1==null ? new ArrayList() : listG1);
		_params.put(Const.ADM_MAPKEY_GROUPLIST+"2",listG2==null ? new ArrayList() : listG2);
		_params.put(Const.ADM_MAPKEY_LIST,     list==null ? new ArrayList() : list);
		_params.put(Const.ADM_MAPKEY_COUNT,    total);
		setPaging(_params, pageNo, pageSize, total);
		
		D(_logger, Thread.currentThread().getStackTrace(), "done..." + _params);
    }
    

    /**
     * 조직 정보 업데이트
     * @param _params
     * @throws CommonException
     */
    @Override
    public void updateOrgHierarchy(Map _params) throws CommonException {

    	D(_logger, Thread.currentThread().getStackTrace(), "called..." + _params);

    	// param
    	pchange(_params, "org_mode", "mode");
    	pchange(_params, "org_idx", "idx");
    	pchange(_params, "org_lv1", "lv1");
    	pchange(_params, "org_lv2", "lv2");
    	pchange(_params, "org_lv3", "lv3");
    	pchange(_params, "org_name", "name");
    	pchange(_params, "org_orderno", "orderno");
    	String mode = pnull(_params, "mode", "");
    	D(_logger, Thread.currentThread().getStackTrace(), "params key changed..." + _params);
    	
    	// update by dao
    	int i = -1;
    	
    	try {
			if("I".equalsIgnoreCase(mode)) {//추가
				i = orgHierarchyDao.insertOrgHierarchy(_params);
			} else if("U".equalsIgnoreCase(mode)) {//변경
				i = orgHierarchyDao.updateOrgHierarchy(_params);
			} else if("D".equalsIgnoreCase(mode)) {//삭제
				i = orgHierarchyDao.deleteOrgHierarchy(_params);
			} else if("R".equalsIgnoreCase(mode)) {//삭제복원
				i = orgHierarchyDao.deleteRecoveryOrgHierarchy(_params);
			}
    	} catch (CommonException e) {
    		throw e;
		} catch (Exception e) {
			throw new CommonException("예기치 않은 오류입니다.", "EXPT-000", e);
		}
    	
    	// select
    	_params.clear();
    	selectOrgHierarchyGroupby(_params);
    	
    	// check a update
    	if(i == -11) {
    		throw new CommonException("잘못된 요청입니다.", "ERRU-003",null); 
    	}
    	if(i != 1) {
    		throw new CommonException("정상적인 변경이 되지 않았습니다.", "ERRU-003",null); 
    	}
    }

}
