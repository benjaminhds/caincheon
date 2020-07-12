package kr.caincheon.church.search;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.caincheon.church.common.base.CommonController;
import kr.caincheon.church.common.base.CommonDaoMultiDTO;
import kr.caincheon.church.common.base.CommonException;
import kr.caincheon.church.search.service.UnifySearchServiceImpl;

/**
 * 통합검색 서비스 
 * @author benjamin
 */
@Controller
public class UnifySearchController extends CommonController {
	
	private final Logger _logger = Logger.getLogger(getClass());
	
	@Resource(name="unifySearchService")
    private UnifySearchServiceImpl unifySearchService;
	
	/**
	 * 통합 검색 서비스
	 * - 검색 출력 순서 :: 사제 > 교구청 >  본당/공소 > 기관/단체/수도회 > 선종사제
	 * - 검색 기준 : 
	 *   사제 : 성명/세례명/임지/성구
	 *   교구청 : 부서명, 부서소개
	 *   본당/공소 : 본당명, 주보성인, 주소
	 *   기관/단체/수도회 : 기관명
	 *   선종사제 : 이름, 세례명, 묘소
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/search/unify_search.do")
	public ModelAndView schList(HttpServletRequest request) throws ServletException, Exception
	{
		// request
		build(request);
		D(_logger, Thread.currentThread().getStackTrace(), "Controller Called. [params:"+_params+"]" );

		// service call
		String err_msg = null;
		CommonDaoMultiDTO dto = null;
		try {
			dto = unifySearchService.multiSearch(_params);
		} catch (CommonException e) {
			err_msg = e.getErrMessage();
			e.printStackTrace();
		}

		// response
		ModelAndView mv = new ModelAndView("/search/unify_search");
		if(dto == null) {
			// skip
		} else if(err_msg == null) {
			mv.addObject("DATA_PRIEST", dto.daoResult1); // 사제
			mv.addObject("DATA_GYOGU", dto.daoResult2); // 교구청
			mv.addObject("DATA_CHURCH_L", ((CommonDaoMultiDTO)dto.daoResult3).daoResult1); // 본당 목록 -> CommonDaoMultiDTO church{1,2}
			mv.addObject("DATA_CHURCH_M", ((CommonDaoMultiDTO)dto.daoResult3).daoResult2); // 본당 미사 -> CommonDaoMultiDTO church{1,2}
			mv.addObject("DATA_GONGSO_L", ((CommonDaoMultiDTO)dto.daoResult3).daoResult3); // 공소 목록 -> CommonDaoMultiDTO gongso{3,4}
			mv.addObject("DATA_GONGSO_M", ((CommonDaoMultiDTO)dto.daoResult3).daoResult4); // 공소 미사 -> CommonDaoMultiDTO gongso{3,4}
			mv.addObject("DATA_ORGA", dto.daoResult4); // 기관/단체/수도회
			mv.addObject("DATA_OLDPRIEST", dto.daoResult5); // 선종사제
		} else {
			mv.addObject("ERR_MSG", err_msg);
		}
		mv.addObject("_params", _params);
		
		D(_logger, Thread.currentThread().getStackTrace(), "Response ModelMap >> \n\t\t"+mv  );
        
		return mv;
	}
	
}
