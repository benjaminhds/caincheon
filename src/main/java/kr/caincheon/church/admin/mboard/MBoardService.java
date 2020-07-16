package kr.caincheon.church.admin.mboard;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import kr.caincheon.church.admin.dao.CodeDao;
import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.base.CommonException;
import kr.caincheon.church.common.base.CommonService;
import kr.caincheon.church.common.base.Const;


@Service("mBoardService")
public class MBoardService extends CommonService
{
	private final Logger L = Logger.getLogger(getClass());

	// 멀티게시판 조회
	@Resource(name="mBoardDAO")
	private MBoardDAO mBoardDAO;

	// 공통코드 조회
	@Resource(name="codeDao")
	private CodeDao codeDao;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void listBoard(Map params) throws CommonException, Exception {
		L.debug("..called param: " + params);
		
		// param
		String pageNo = pnull(params, "pageNo");
		String pageSize = pnull(params, "pageSize");
		
		// board list
		CommonDaoDTO dto = mBoardDAO.getMBoardList(params);

		// return
		params.put(Const.ADM_MAPKEY_LIST, dto.daoList);
		params.put(Const.ADM_MAPKEY_COUNT, dto.daoTotalCount);
		
		setPaging(params, pageNo, pageSize, dto.daoTotalCount);
		
		L.debug(".. end. rtn: " + dto );
		
	}
	
	/** 보드 관리 Content */
	public CommonDaoDTO boardViewContent(Map<String, Object> _params) throws CommonException, Exception {
		CommonDaoDTO rtDto = new CommonDaoDTO();
		
		try {
			/* 1. Board 마스터 정보
			 * 2. Board 카테고리 정보
			 * */
			rtDto	=	mBoardDAO.mboardContents(_params);
			
			_params.put(Const.ADM_MAPKEY_CONTENT, rtDto.daoDetailContent);
			_params.put(Const.ADM_MAPKEY_LIST_OTHERS, rtDto.otherList);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rtDto;
	}

	/*code instanct 조회*/
	public void getCodeInstance(Map<String, Object> _params) {
		
		CommonDaoDTO rtDto = new CommonDaoDTO();
		
		try {
			/*code 정보 조회*/
			rtDto	=	mBoardDAO.getCodeInstance(_params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		_params.put("code_list", rtDto.otherList);
	}
	//============= 게시판 management ==================
	/** 메스도에 다르게 */
	public void iudBoardMaster(Map params) throws CommonException, Exception {
		
		String mode = pnull(params, "mode");
		
		D(L, Thread.currentThread().getStackTrace(), mode+") ..called");
		
		if("i".equalsIgnoreCase(mode)) {
			mBoardDAO.insertBoard(params);
			
			this.insertCateGory(params);
		} else if("u".equalsIgnoreCase(mode)) {
			/*게시판 마스터 저장*/
			mBoardDAO.updateBoard(params);
			/*게시판 카테고리 이전 데이터 삭제*/
			mBoardDAO.deleteBoardCategory(params);
			/*게시판 카테고리 저장*/
			/*for(int i = 0; i < params.get("i_arrCgList"); i++) {
				
			}*/
			this.insertCateGory(params);
		} else if("d".equalsIgnoreCase(mode)) {
			mBoardDAO.deleteBoard(params);
			mBoardDAO.deleteBoardCategory(params);
		}
		
		D(L, Thread.currentThread().getStackTrace(), mode);
	}
	
	/*카테고리 넣기*/
	private void insertCateGory(Map params) {
		
		/*카테고리&이름*/
		String[] cgList	=	(String[]) params.get("i_arrCgList");
		String[] cgNameList	=	(String[]) params.get("i_arrCgNameList");
		
		Map tempDao = new HashMap();
		
		tempDao.put("i_sBidx",params.get("i_sBidx"));
		
		if(cgList != null) {
			for(int i=0; i < cgList.length; i++) {
				
				tempDao.put("i_sCIdx",cgList[i]);
				tempDao.put("i_sName",cgNameList[i]);
				
				mBoardDAO.insertBoardCategory(tempDao);
			}
		}
	}
	/*메인 메뉴 게시판 리스트*/
	public void getMenuBoardList(Map<String, Object> _params) {
		//메인 메뉴 게시판 리스트
		
		// board list
		CommonDaoDTO dto = mBoardDAO.getMenuBoardList(_params);

		// return
		_params.put(Const.ADM_MAPKEY_LIST, dto.daoList);
	}
	/*Normal Board List*/
	public CommonDaoDTO NormalboardList(Map<String, Object> _params) {
		// param
		String pageNo = pnull(_params, "pageNo");
		String pageSize = pnull(_params, "pageSize");
		
		// board list
		CommonDaoDTO dto = mBoardDAO.NormalboardList(_params);

		// return
		_params.put(Const.ADM_MAPKEY_LIST, dto.daoList);
		_params.put(Const.ADM_MAPKEY_COUNT, dto.daoTotalCount);
		
		setPaging(_params, pageNo, pageSize, dto.daoTotalCount);
		
		L.debug(".. end. rtn: " + dto );
		return dto;
	}
}
