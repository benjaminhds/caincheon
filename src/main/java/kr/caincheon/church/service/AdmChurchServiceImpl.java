// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AdmChurchServiceImpl.java

package kr.caincheon.church.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import kr.caincheon.church.admin.serivce.CodeService;
import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.base.CommonException;
import kr.caincheon.church.common.base.CommonService;
import kr.caincheon.church.common.utils.FileUtils;
import kr.caincheon.church.dao.AdmChurchDao;
import kr.caincheon.church.dao.TempleDao;

// Referenced classes of package kr.caincheon.church.service:
//            OrgHierarchyService

@Service("admChurchService")
public class AdmChurchServiceImpl extends CommonService implements AdmChurchService
{

    private final Logger _logger = Logger.getLogger(getClass());
    
	/*  */
	@Resource(name="admChurchDao")
	private AdmChurchDao admChurchDao;

	@Resource(name="codeService")
    private CodeService codeService;
	
    @Resource(name="templeDao")
    private TempleDao templeDao;
    
    @Override
    public List admChurchList(Map _params) {
        return admChurchDao.admChurchList(_params);
    }

    @Override
    public int admChurchListCount(Map _params) {
        return admChurchDao.admChurchListCount(_params);
    }

    @Override
    public List admMissaList(Map _params) {
    	CommonDaoDTO dto = new CommonDaoDTO();
    	admChurchDao.admMissaList(_params, dto);
    	return dto.otherList;
    }

    @Override
    public CommonDaoDTO admChurchContents(Map _params) throws CommonException { 
    	
    	CommonDaoDTO dto = null;
    	
        try {
        	// 본당 지구코드 목록 조회
        	Map codeMap = new HashMap();
            codeMap.put("code", "000004");
        	dto = codeService.listCodeInstances(codeMap);
			
        	// 본당 상세 조회
			dto.daoDetailContent = admChurchDao.admChurchContents(_params);
			
			// 해당본당의 사제 목록 조회
			//dto.otherData1 = templeDao.selectPriestInChurch(pnull(_params,"church_idx"));
			
			// 본당 미사 조회  => 요일별 미사 시간 정보 :: dto.otherList & 본당 미사 최종 업데이트 :: dto.otherData
			admChurchDao.admMissaList(_params, dto);
			if( dto.otherList.size() > 0 ) { // tag convert
				for(int i=0, i2=dto.otherList.size() ; i<i2; i++) {
					Map row = (Map)dto.otherList.get(i);
					String chidx = pnull(row, "CHURCH_IDX");
					String week  = pnull(row, "WEEK");
					String mname = pnull(row, "MNAME");
					
					mname = mname.replaceAll("&lt;", "<")
							.replaceAll("&gt;", ">")
							.replaceAll("<span data-id=", "<span onclick=\"deleteMissa("+chidx+",this,"+week+")\" data-id=")
							;
					row.put("MNAME", mname);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
        return dto;
    }

    /*
     * 파일 업로드 처리
     */
    private List fileuoload(Map _params, HttpServletRequest request) throws Exception {
    	/* 성당둘러보기 이미지 처리 */
    	String churchFilePath = "upload/church_lookaround/"+pnull(_params, "church_idx")+"/";
    	_params.put("CONTEXT_URI_PATH", "/"+churchFilePath);
    	_params.put("CONTEXT_ROOT_PATH", request.getSession().getServletContext().getRealPath("/") + churchFilePath);
        return FileUtils.getInstance().parseInsertFileInfo(_params, request);
    }
    
    @Override
    public boolean admChurchInsert(Map _params, HttpServletRequest request) throws CommonException {
        List list = null;
        try {
            list = fileuoload(_params, request);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return admChurchDao.admChurchInsert(_params, list);
    }

    /* 본당수정  */
    @Override
    public boolean admChurchModify(Map _params, HttpServletRequest request) throws CommonException {
        List list = null;
        try {
        	_params.put("isKeepOriginalNm", "Y");//원본명유지
            list = fileuoload(_params, request);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return admChurchDao.admChurchModify(_params, list);
    }

    /* 본당삭제 */
	@Override
	public boolean admChurchDelete(Map map) throws CommonException {
		return admChurchDao.admChurchDelete(map);
	}

	
    @Override
    public boolean admChurchMissaInfoDelete(Map _params) throws CommonException {
        return admChurchDao.admChurchMissaInfoDelete(_params);
    }

    @Override
    public int admChurchMissaManage(Map _params) throws CommonException {
        return admChurchDao.admChurchMissaInfoInsert(_params);
    }

    
}
