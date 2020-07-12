package kr.caincheon.church.admin.serivce;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import kr.caincheon.church.admin.dao.NBoardDao;
import kr.caincheon.church.admin.dao.OrgHierarchyDao;
import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.base.CommonException;
import kr.caincheon.church.common.base.CommonService;
import kr.caincheon.church.common.base.Paging;


@Service("nBoardService")
public class NBoardServiceImpl extends CommonService
//    implements NBoardService
{

    private final Logger _logger = Logger.getLogger(getClass());
    
    @Resource(name="orgHierarchyDao")
    private OrgHierarchyDao orgHierarchyDao;

    @Resource(name="nBoardDao")
    private NBoardDao nBoardDao;

    /*
     * NBOARD 목록 조회
     * (non-Javadoc)
     * @see kr.caincheon.church.admin.serivce.NBoardService#nboardList(java.util.Map, java.lang.String, boolean, int)
     */
    public CommonDaoDTO nboardList(Map _params, String left_menu_data_pg, boolean hasOrgList, int attachedFileCount) 
    	throws Exception
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "NBoard ListService Called.[left_menu_data_pg="+left_menu_data_pg+", hasOrgList="+hasOrgList+", params:"+_params+"]" );
    	
    	// NBoard list 조회
    	CommonDaoDTO rtDTO = new CommonDaoDTO();
    	try {
    		pnullPutBidxAll(_params, left_menu_data_pg);
			nBoardDao.nboardList(rtDTO, _params, left_menu_data_pg, attachedFileCount);
		} catch (Exception e) {
			throw e; //throw new CommonException("NBoard를 조회하지 못했습니다.", e, "EXPT-100", e.getMessage());
		}
    	// 페이징
    	Paging paging = new Paging();
        paging.setPageNo(ipnull(_params, "pageNo", 1));
        paging.setPageSize(ipnull(_params, "pageSize", 20));//basic count=20
        paging.setTotalCount(rtDTO.daoTotalCount);
        rtDTO.paging = paging;
    	
    	// 교구조직 LV2부터 전부 가져오기
    	try {
    		if(hasOrgList) {
				Map pm = new HashMap<String, String>();
				pm.put("lv1", pnull(_params, "LV1", "01' AND LV2!='00' AND LV3!='000")); // 교구청 조직
				rtDTO.orgList = orgHierarchyDao.selectOrgHierarchy(pm);
    		}
		} catch (Exception e) {
			throw new CommonException("조직의 상하관계를 조회하지 못했습니다.", "EXPT-101", e);
		}
    	
    	D(_logger, Thread.currentThread().getStackTrace(), "NBoard ListService return.[DTO="+rtDTO+"]" );
    	
    	return rtDTO;
    }

    /*
     * NBOARD 게시물 상세 조회
     * (non-Javadoc)
     * @see kr.caincheon.church.admin.serivce.NBoardService#nboardContents(java.util.Map, java.lang.String, boolean, int)
     */
    public CommonDaoDTO nboardContents(Map _params, String left_menu_data_pg, boolean hasOrgList, int attachedFileCount)
        throws Exception
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "NBoard ContentsService Called.[left_menu_data_pg="+left_menu_data_pg+", hasOrgList="+hasOrgList+", params:"+_params+"]" );
    	
    	// contents
    	CommonDaoDTO rtDTO = new CommonDaoDTO();
    	
    	String query_type = pnull(_params, "query_type");
    	if("insert".equalsIgnoreCase(query_type))
    		rtDTO.daoDetailContent = new HashMap();
		else
			try {
				pnullPutBidxAll( _params, left_menu_data_pg);
				nBoardDao.nboardContents(rtDTO, _params, attachedFileCount);
			} catch (Exception e) {
				throw new CommonException("NBoard를 조회하지 못했습니다.", "EXPT-200", e.getMessage());
			}
    	
    	// 교구조직 LV2부터 전부 가져오기
    	try {
    		if(hasOrgList) {
				Map pm = new HashMap<String, String>();
				pm.put("lv1", pnull(_params, "LV1", "01' AND LV2!='00' AND LV3!='000"));
				pnullPut(pm, "ORG_HIERARCHY_ORDERBY", " O.NAME ASC, O.LV1 ASC, O.LV2 ASC, O.LV3 ASC ");
				rtDTO.orgList = orgHierarchyDao.selectOrgHierarchy(pm);
    		}
		} catch (Exception e) {
			throw new CommonException("조직의 상하관계를 조회하지 못했습니다.", "EXPT-201", e);
		}
    	
    	D(_logger, Thread.currentThread().getStackTrace(), "NBoard ContentsService return.[DTO="+rtDTO+"]" );
    	
        return rtDTO;
    }

    /*
     * NBOARD 신규 등록
     * (non-Javadoc)
     * @see kr.caincheon.church.admin.serivce.NBoardService#nboardInsert(java.util.Map, java.lang.String, javax.servlet.http.HttpServletRequest)
     */
    public boolean nboardInsert(Map _params, String left_menu_data_pg, HttpServletRequest request)
        throws Exception
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "NBoard InsertService Called.[left_menu_data_pg="+left_menu_data_pg+", params:"+_params+"]" );

    	String b_idx = pnull(_params, "b_idx");
    	if(b_idx.length() == 0) {
    		pnullPutBidxAll(_params, left_menu_data_pg);
    	}
    	
    	List list = fileUploadProcess(_params, request, getUploadBaseURI(left_menu_data_pg));
        if(list !=null ) {
        	D(_logger, Thread.currentThread().getStackTrace(), "NBoard InsertService File Uploaded.[count="+list.size()+", list:"+list+"]" );
        }
        
    	boolean rtBool;
		try {
			rtBool = nBoardDao.nboardInsert(_params, list);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CommonException("NBoard에 등록하지 못했습니다.["+e.getMessage()+"]", "EXPT-300", e.getMessage());
		}
    	
    	D(_logger, Thread.currentThread().getStackTrace(), "NBoard InsertService return.[rtBool="+rtBool+"]" );
    	
        return rtBool;
    }

    /*
     * NBOARD 수정
     * (non-Javadoc)
     * @see kr.caincheon.church.admin.serivce.NBoardService#nboardModify(java.util.Map, java.lang.String, javax.servlet.http.HttpServletRequest)
     */
    public boolean nboardModify(Map _params, String left_menu_data_pg, HttpServletRequest request) throws Exception
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "Service ModifyCalled.[left_menu_data_pg="+left_menu_data_pg+", params:"+_params+"]" );
    	
    	String b_idx = pnull(_params, "b_idx");
    	if(b_idx.length() == 0) {
    		pnullPutBidxAll(_params, left_menu_data_pg);
    	}
    	
    	// file upload handle
        List list = fileUploadProcess(_params, request, getUploadBaseURI(left_menu_data_pg));
        if(list !=null ) {
        	D(_logger, Thread.currentThread().getStackTrace(), "NBoard ModifyService File Uploaded.[count="+list.size()+", list:"+list+"]" );
        }
        
        // nboard modify
        boolean rtBool;
		try {
			rtBool = nBoardDao.nboardModify(_params, list);
		} catch (Exception e) {
			throw new CommonException("NBoard 상세를 수정하지 못했습니다.", "EXPT-400", e.getMessage());
		}
        
        D(_logger, Thread.currentThread().getStackTrace(), "NBoard ModifyService return.[rtBool="+rtBool+"]" );
    	
        return rtBool;
    }

    /*
     * NBOARD 게시물 삭제
     * (non-Javadoc)
     * @see kr.caincheon.church.admin.serivce.NBoardService#nboardDelete(java.util.Map, java.lang.String)
     */
    public boolean nboardDelete(Map _params, String left_menu_data_pg)
        throws Exception
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "NBoard DeleteService Called.[left_menu_data_pg="+left_menu_data_pg+", params:"+_params+"]" );
    	
    	boolean rtBool;
		try {
			rtBool = nBoardDao.nboardDelete(_params);
		} catch (Exception e) {
			throw new CommonException("NBoard에서 삭제하지 못했습니다.", "EXPT-500", e.getMessage());
		}
        
    	D(_logger, Thread.currentThread().getStackTrace(), "NBoard DeleteService return.[rtBool="+rtBool+"]" );
    	
        return rtBool;
    }

    
}
