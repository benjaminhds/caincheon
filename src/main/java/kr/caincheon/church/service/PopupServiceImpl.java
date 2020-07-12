package kr.caincheon.church.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.base.CommonService;
import kr.caincheon.church.common.utils.UtilDate;
import kr.caincheon.church.dao.PopupDao;

/**
 * 팝업 관리하는 Service Implementation 클래스
 * @author benjamin
 */
@Service("popupService")
public class PopupServiceImpl extends CommonService implements PopupService
{

    private final Logger _logger = Logger.getLogger(getClass());
    
    //@Resource(name="popupDao")
    @Autowired
    private PopupDao popupDao;
    
    /*
     * 프론트 메인 : 팝업 조회
     * (non-Javadoc)
     * @see kr.caincheon.church.service.PopupService#mainPopupList(java.util.Map)
     */
    @Override
    public List mainPopupList(Map _params) {
    	
    	//String now_date = UtilDate.getDateFormat("yyyy-MM-dd HH:mm:ss");// 형식) 2017-11-28 15:00:01
    	_params.put("use_yn", "Y"); // 사용여부(Y:사용, N:중지)
    	_params.put("open_yn", "Y"); // 노출여부
    	_params.put("open_date", "now");
    	
    	CommonDaoDTO dto = new CommonDaoDTO();
    	popupDao.popupList(_params, dto);
    	
    	_params.remove("use_yn");
    	_params.remove("open_yn");
    	_params.remove("open_date");
    	
    	return dto.daoList;
    }
    
    /*
     * 관리자: 팝업목록조회
     * (non-Javadoc)
     * @see kr.caincheon.church.service.PopupService#admPopupList(java.util.Map)
     */
    @Override
    public CommonDaoDTO admPopupList(Map _params)
    {
    	CommonDaoDTO dto = new CommonDaoDTO();
    	popupDao.popupList(_params, dto);
    	
        return dto;
    }

    /*
     * 관리자: 팝업보기
     * (non-Javadoc)
     * @see kr.caincheon.church.service.PopupService#admPopupContents(java.util.Map)
     */
    @Override
    public Map admPopupContents(Map _params)
    {
        return popupDao.popupContents(_params);
    }

    /*
     * 관리자: 팝업등록
     * (non-Javadoc)
     * @see kr.caincheon.church.service.PopupService#admPopupInsert(java.util.Map)
     */
    @Override
    public boolean admPopupInsert(Map _params)
    {
        return popupDao.popupInsert(_params);
    }

    /*
     * 관리자: 팝업수정
     * (non-Javadoc)
     * @see kr.caincheon.church.service.PopupService#admPopupModify(java.util.Map)
     */
    @Override
    public boolean admPopupModify(Map _params)
    {
        return popupDao.popupModify(_params);
    }

    /*
     * 관리자: 팝업삭제
     * (non-Javadoc)
     * @see kr.caincheon.church.service.PopupService#admPopupDelete(java.util.Map)
     */
    @Override
    public boolean admPopupDelete(Map _params)
    {
        return popupDao.popupDelete(_params);
    }

}
