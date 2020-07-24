package kr.caincheon.church.common.base;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import kr.caincheon.church.admin.mboard.MBoardDAO;
import kr.caincheon.church.common.utils.FileUtils;

/**
 * 
 */
public abstract class CommonService extends CommonParameter {

	// 멀티게시판 조회
	@Resource(name="mBoardDAO")
	private MBoardDAO mBoardDAO;

	
	/*
	 * DB 조회 결과 컬럼에서 불필요한 컬럼을 제거한다.
	 * - 이는 공통적으로 사용하는 dao에서 안쓰는 컬럼을 제거하고 response하기 위함.
	 */
	protected void removeColumns(List<Map> rows, String[] removeColumns) {
		if( rows != null ) {
			// 필요치 않은 컬럼 제거
			for(int i=0; i < rows.size() ; i++) {
				Map row = rows.get(i);
				for(int j=0, j2=removeColumns.length ; j<j2; j++) {
					row.remove(removeColumns[j].toUpperCase());
				}
			}
		}
	}

	/*
	 * DB 조회 결과 컬럼에서 불필요한 컬럼을 제거한다.
	 * - 이는 공통적으로 사용하는 dao에서 안쓰는 컬럼을 제거하고 response하기 위함.
	 */
	protected void removeColumns(Map row, String[] removeColumns) {
		if( row != null ) {
			// 필요치 않은 컬럼 제거
			for(int j=0, j2=removeColumns.length ; j<j2; j++) {
				row.remove(removeColumns[j].toUpperCase());
			}
		}
	}
	/*
	 * paging
	 */
	protected void setPaging(CommonDaoDTO dto, String pageNo, String pageSize, int totalCount) {
		
		Paging paging = new Paging();
        paging.setPageNo(Integer.parseInt(pnull(pageNo, "1")));
        paging.setPageSize(Integer.parseInt(pnull(pageSize, "20")));
        paging.setTotalCount(totalCount);
        
        dto.paging = paging;
	}
	
	/*
	 * paging
	 */
	protected void setPaging(Map params, String pageNo, String pageSize, int totalCount) {
		Paging paging = new Paging();
        paging.setPageNo(Integer.parseInt(pnull(pageNo, "1")));
        paging.setPageSize(Integer.parseInt(pnull(pageSize, "20")));
        paging.setTotalCount(totalCount);
        
        params.put(Const.ADM_MAPKEY_PAGING, paging);
	}
	
	/*
	 * paging
	 */
	protected void setPaging(Map params, String pageNo, String pageSize, Integer totalCount) {
		this.setPaging(params, pageNo, pageSize, totalCount.intValue());
	}

	/*
	 * 
	 */
	protected String getBoardTitle(String b_idx, String c_idx) {
		String rtVal = b_idx;
		if(b_idx==null) return "";
		int i_b_idx=0;
		try {
			i_b_idx = Integer.parseInt(b_idx);
			switch(i_b_idx) {
				case  8: rtVal = "교구장 동정";
					if( "3".equals(c_idx) ) rtVal = "동정";
					else if( "4".equals(c_idx) ) rtVal = "강론";
					break;
				case  9: rtVal = "교회소식"; break;
				case 10: rtVal = "공동체소식"; break;
				case 11: rtVal = "본당알림"; break;
				case 12: rtVal = "교구소식"; break;
				case 14: rtVal = "사목자료"; 
					if( "5".equals(c_idx) ) rtVal = "전례";
					else if( "6".equals(c_idx) ) rtVal = "양식";
					else if( "7".equals(c_idx) ) rtVal = "교리";
					else if( "8".equals(c_idx) ) rtVal = "사목";
					else if( "9".equals(c_idx) ) rtVal = "선교";
					else if( "10".equals(c_idx) ) rtVal = "기타";
					break;
				case 21: rtVal = "역대교구장 앨범"; 
					if( c_idx != null && c_idx.length() > 0 ) 
						rtVal = c_idx + "대 교구장";
					break;
				case 22: rtVal = "본당 앨범"; break;
				case 23: rtVal = "교구 영상";
					if( "11".equals(c_idx) ) rtVal = "소식";
					else if( "12".equals(c_idx) ) rtVal = "강좌";
					break;
			}
		} catch (NumberFormatException e) {
			rtVal = "전체보기";
		}
    	return rtVal;
	}
	
	/*
	 * 
	 */
	protected void pnullPutBidxAll(Map _params, String left_menu_data_pg) {
		String _name = "b_idx";
    	String FldVal = pnull(_params, _name);
    	
    	if(FldVal.equals("ALL") || FldVal.length()==0) {
    		if("board_01".equalsIgnoreCase(left_menu_data_pg)) {
	    		_params.put(_name, "8"); // nboard.b_idx 교구장 동정
    		}
    		if("board_02".equalsIgnoreCase(left_menu_data_pg)) {
    			_params.put(_name, "9, 12, 10"); // nboard.b_idx 교회소식 9, 공동체소식 10, 교구소식 12
    		}
    		if("board_03".equalsIgnoreCase(left_menu_data_pg)) {
    			_params.put(_name, "11"); // nboard.b_idx 본당알림
    		}
    		if("board_04".equalsIgnoreCase(left_menu_data_pg)) {
    			_params.put(_name, "14"); // nboard.b_idx 사목자료
    		}
    		if("board_05".equalsIgnoreCase(left_menu_data_pg)) {
    			_params.put(_name, "21"); // nboard.b_idx 역대교구장앨범
    		}
    		if("board_07".equalsIgnoreCase(left_menu_data_pg)) {
    			_params.put(_name, "22"); // nboard.b_idx 본당앨범
    		}
    		if("board_08".equalsIgnoreCase(left_menu_data_pg)) {
    			_params.put(_name, "23"); // nboard.b_idx 교구영상
    		}
    	}
    	
    	// fixed code..
    	
	}

	/*
	 * 
	 */
	protected void pnullPutCidxAll(Map _params, String left_menu_data_pg) {
		String _name = "c_idx";
    	String FldVal = pnull(_params, _name);

    	if(FldVal.equals("ALL") || FldVal.length()==0) {
    		if("board_01".equalsIgnoreCase(left_menu_data_pg)) {
	    		_params.put(_name, "3,4"); // nboard.c_idx 교구장 동정 : 동적 3, 강론 4
    		}
    		if("board_04".equalsIgnoreCase(left_menu_data_pg)) {
    			_params.put(_name, "5,6,7,8,9,10"); // nboard.c_idx 사목자료 : 전례 5, 양식 6, 교리 7, 사목 8, 선교 9, 기타 10
    		}
    		if("board_05".equalsIgnoreCase(left_menu_data_pg)) {
    			_params.put(_name, "1,2"); // nboard.c_idx 역대교구장앨범 : 1대교구장 1, 2대교구장 2
    		}
    		if("board_06".equalsIgnoreCase(left_menu_data_pg)) {
    			_params.put(_name, "1,2"); // parish_album.c_idx : 미사|행사 c_idx=1, 교육|사업 c_idx=2
    		}
    		if("board_08".equalsIgnoreCase(left_menu_data_pg)) {
    			_params.put(_name, "11, 12"); // nboard.c_idx 교구영상 : 소식 11, 강좌 12
    		}
    		if("etc_01".equalsIgnoreCase(left_menu_data_pg)) {
    			_params.put(_name, "1, 2, 3"); //  message.c_idx 교구장메시지 : 교서 1, 메시지 2, 담화문 3
    		}
    	}
	}

    /**
     * file Upload process
     */
	protected List fileUploadProcess(Map _params, HttpServletRequest request, String uploadURI) throws CommonException {
		//
		// List uploadedFiles = fileUploadProcess(_params, request, "upload/news/");
		//
		if( uploadURI.indexOf("/") == 0) {
			uploadURI = uploadURI.substring(1);
		}
		
    	pnullPut(_params, "CONTEXT_URI_PATH", "/"+uploadURI);// "/upload/news/");
    	pnullPut(_params, "CONTEXT_ROOT_PATH", request.getSession().getServletContext().getRealPath("/") + uploadURI);
    	
    	List rtList = null;
    	
    	try {
			rtList = FileUtils.getInstance().parseInsertFileInfo(_params, request);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CommonException("File Upload Error 입니다.["+e.getMessage()+"]", e);
		}
    	
    	return rtList; 
    }
	protected List fileUploadProcessNew(Map _params, HttpServletRequest request, String uploadURI) throws CommonException {
		//

		if( uploadURI.indexOf("/") == 0) {
			uploadURI = uploadURI.substring(1);
		}
		
		pnullPut(_params, "CONTEXT_URI_PATH", "/"+uploadURI);
		pnullPut(_params, "CONTEXT_ROOT_PATH", request.getSession().getServletContext().getRealPath("/") + uploadURI);
		
		List rtList = null;
		
		try {
			rtList = FileUtils.getInstance().parseInsertFileInfoNew(_params, request);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new CommonException("File Upload Error 입니다.["+e.getMessage()+"]", e);
		}
		
		return rtList; 
	}
	
	
	protected String getUploadBaseURI(String left_menu_data_pg) {

		if("board_01".equalsIgnoreCase(left_menu_data_pg)) {
			return "upload/bishop/";
		}
		if("board_02".equalsIgnoreCase(left_menu_data_pg)) {
			return "upload/news/";
		}
		if("board_03".equalsIgnoreCase(left_menu_data_pg)) {
			return "upload/church_notice/";
		}
		if("board_04".equalsIgnoreCase(left_menu_data_pg)) {
			return "upload/cure/";
		}
		if("board_05".equalsIgnoreCase(left_menu_data_pg)) {
			return "upload/bishop_oldalbum/";
		}
		if("board_06".equalsIgnoreCase(left_menu_data_pg)) {
			return "upload/gyogu_album/";
		}
		if("board_07".equalsIgnoreCase(left_menu_data_pg)) {
			return "upload/church_album/";
		}
		if("board_08".equalsIgnoreCase(left_menu_data_pg)) {
			return "upload/gyogu_mov/";
		}

		return "upload/"+left_menu_data_pg+"/";
	}
}
