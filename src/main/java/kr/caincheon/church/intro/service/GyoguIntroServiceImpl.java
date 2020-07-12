// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GyoguIntroServiceImpl.java

package kr.caincheon.church.intro.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import kr.caincheon.church.admin.dao.CodeDao;
import kr.caincheon.church.admin.dao.OrgHierarchyDao;
import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.base.CommonService;
import kr.caincheon.church.dao.GyoguIntroDao;

// Referenced classes of package kr.caincheon.church.service:
//            GyoguIntroService

@Service("gyoguIntroService")
public class GyoguIntroServiceImpl extends CommonService
    implements GyoguIntroService
{

    private final Logger _logger = Logger.getLogger(getClass());
    
    // 기관/단체/수도회 
    @Resource(name="gyoguIntroDao")
    private GyoguIntroDao gyoguIntroDao;

    // 조직정보 조회
    @Resource(name="orgHierarchyDao")
    private OrgHierarchyDao orgHierarchyDao;

    // 공통코드 조회 :: 복지기관세목코드(000005) 조회 or 지구코드(000004) 
    @Resource(name="codeDao")
    private CodeDao codeDao;
    
	/*
	 * front :: 기간/단체/수도회 목록조회
	 */
    @Override
    public CommonDaoDTO organizationList(Map _params)
    {
    	CommonDaoDTO dto = new CommonDaoDTO();

    	// 기관/단체/수도회 조회
    	gyoguIntroDao.organizationList(_params, dto);
    	
    	// 조직 조회 : combo-box
    	Map m = cloneParams(_params);
    	try {
    		int level  = ipnull(_params, "lv", 1);
    		String lv1 = "12";
    		String lv2 = "";
			dto.orgList = orgHierarchyDao.selectOrgHierarchyGroupby(level, lv1);
			
			// LV2 == "00" 은 제거
			boolean isRemove = false;
			if(lv2.length()>0) {
				isRemove = lv2.charAt(0)=='!' ? true : false;
				if( isRemove ) lv2 = lv2.substring(1);
			}
			
			if( dto.orgList != null ) {
				// dto.orgList
				//if( level!=2 ) 
				{
					for(int i=0; i < dto.orgList.size() ; i++) {
						Map row = (Map)dto.orgList.get(i);
						String dbLV2 = pnull(row, "LV2");
						if( "00".equals(dbLV2) ) {
							dto.orgList.remove(i);
							--i;
							System.out.println(" >>> 제거 됨 1 : [param lv1:"+lv1+" lv2:"+lv2+", db lv2:"+dbLV2+"]");
						}
						else if( isRemove && lv2.equals(dbLV2) ) {
							dto.orgList.remove(i);
							--i;
							System.out.println(" >>> 제거 됨 2 : [param lv1:"+lv1+" lv2:"+lv2+", db lv2:"+dbLV2+"]");
						}
						else if( !isRemove && lv2.length()>0 && !lv2.equals(dbLV2)) {
							dto.orgList.remove(i);
							--i;
							System.out.println(" >>> 제거 됨 3 : [param lv1:"+lv1+" lv2:"+lv2+", db lv2:"+dbLV2+"]");
						}
						// 필요치 않은 컬럼 제거
						removeColumns(row, new String[]{"DEL_YN","CHILD_COUNT","LV2","LV1"});
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	// 세목코드 조회
    	dto.otherList = selectWelfareCodeList();
		
        return dto;
    }

    /*
     * 세목코드 조회
     */
    private List selectWelfareCodeList() {
    	List list = null;
    	// 세목코드 조회
    	Map m = new HashMap();
    	pnullPut(m, "code", "000005");//세목코드 조회
		pnullPut(m, "use_yn", "Y");//사용중
		pnullPut(m, "del_yn", "N");//미삭제코드
		pnullPut(m, "orderby", "CODE_INST ASC, ORDER_NO ASC");//정렬
		try {
			CommonDaoDTO dto = codeDao.selectCodeInstances(m);
			
			list = (List<Map>)dto.daoList;
			
			// 필요치 않은 컬럼 제거
			removeColumns(list, new String[]{"CODE","UPD_ID","DEL_DATE","UPD_DATE","INS_DATE","MEMO","DEL_YN","USE_YN","ORDER_NO"});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
    }
    
    /**
     * 
     * @deprecated - It's not a called method
     */
    @Override
    public CommonDaoDTO beforePriestList(Map _params)
    {
    	CommonDaoDTO dto = new CommonDaoDTO();
    	
    	gyoguIntroDao.beforePriestList(_params, dto);
    	
        return dto;
    }

    /*
     * (non-Javadoc)
     * @see kr.caincheon.church.intro.service.GyoguIntroService#admOrganizationList(java.util.Map)
     */
    @Override
    public CommonDaoDTO admOrganizationList(Map _params)
    {
    	int pageNo   = ipnull(_params, "pageNo", 1);
    	int pageSize = ipnull(_params, "pageSize", 20);
    	
    	CommonDaoDTO dto = gyoguIntroDao.admOrganizationList(_params);
    	dto.setPaging(pageNo, pageSize);
    	
    	return dto;
    }

    /*
     * (non-Javadoc)
     * @see kr.caincheon.church.intro.service.GyoguIntroService#adm_org_view(java.util.Map)
     */
    @Override
    public Map admOrganizationView(Map _params)
    {
        return gyoguIntroDao.admOrganizationView(_params);
    }

    /*
     * (non-Javadoc)
     * @see kr.caincheon.church.intro.service.GyoguIntroService#adm_priest_list(java.util.Map)
     */
    @Override
    public CommonDaoDTO adm_priest_list(Map _params)
    {
    	// 사제목록
    	CommonDaoDTO dto = gyoguIntroDao.adm_priest_list(_params);
    	// 세목코드목록
    	dto.otherList = selectWelfareCodeList();
		
    	
        return dto;
    }

    /*
	 * 관리자 :: 기관/단체/수도회 정보 신규등록하기
	 */
    @Override
    public boolean organizationInsert(Map _params)
    {
        return gyoguIntroDao.organizationInsert(_params);
    }

    /*
	 * 관리자 :: 기관/단체/수도회 정보 수정하기
	 */
    @Override
    public boolean organizationModify(Map _params)
    {
        return gyoguIntroDao.organizationModify(_params);
    }

    /*
	 * 관리자 :: 기관/단체/수도회 정보 삭제하기
	 */
    @Override
    public boolean organizationDelete(Map _params)
    {
        return gyoguIntroDao.organizationDelete(_params);
    }

    /**
     * front :: 통합검색 
     * @see kr.caincheon.church.intro.service.GyoguIntroService#unifySearchOrganization(java.util.Map, java.sql.Connection)
     */
	@Override
	public List unifySearchOrganization(Map _params, Connection conn) throws Exception {
		D(_logger, Thread.currentThread().getStackTrace(), "Service Called. params:"+_params );
		
		List list = gyoguIntroDao.unifySearch(_params, conn);
		
		D(_logger, Thread.currentThread().getStackTrace(), "Service Returned. results:"+list );
		
		return list;
	}

	/**
	 * 관할구역 좌표 조회
	 * @see kr.caincheon.church.intro.service.GyoguIntroService#jurisdictionCoordinate(java.util.Map)
	 */
	@Override
	public CommonDaoDTO jurisdictionCoordinate(Map _params) throws Exception {
		
		//
		CommonDaoDTO dto = new CommonDaoDTO();
		
    	// 교구청 지구코드 :: memo 에 관할지구 좌표, memo2 지구중심좌표가 저장됨.
    	Map m = new HashMap();
    	pnullPut(m, "code", "000004");//교구청 지구코드
		pnullPut(m, "use_yn", "Y");//사용중
		pnullPut(m, "del_yn", "N");//미삭제코드
		pnullPut(m, "orderby", "CODE_INST ASC, ORDER_NO ASC");//정렬
		try {
			// total list
			CommonDaoDTO dto2 = codeDao.selectCodeInstances(m);
			dto.daoList = dto2.daoList;
			
			List<Map> list = (List<Map>)dto.daoList;
			
			// extract by parameter
			List<Map> list2 = new ArrayList<Map>(); 
			String code_inst = pnull(_params, "code_inst");
			if(code_inst.length() > 0) {
				for(Map row : list) {
					if(code_inst.equals(pnull(row, "CODE_INST"))) {
						Map r = new HashMap();
						r.putAll(row);
						list2.add(r);
						break;
					}
				}
			} else {
				for(Map row : list) {
					Map r = new HashMap();
					r.putAll(row);
					list2.add(r);
				}
			}
			dto.otherList = list2;
			
			// 지도의 중심좌표 계산하기
			String gps1 = "";
			for(Map row : list2) {
				String gps2 = pnull(row, "MEMO2").trim().replaceAll(" ", "");
				gps1 = calcGpsCenter(gps1, gps2);
			}
			dto.otherData = gps1;
			
			
			// 필요치 않은 컬럼 제거
			removeColumns(dto.daoList,   new String[]{"CODE","UPD_ID","DEL_DATE","UPD_DATE","INS_DATE","DEL_YN","USE_YN","ORDER_NO","MEMO"});
			removeColumns(dto.otherList, new String[]{"CODE","UPD_ID","DEL_DATE","UPD_DATE","INS_DATE","DEL_YN","USE_YN","ORDER_NO"});
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return dto;
	}

	/**
	 * 지도의 중심 좌표를 계산하는 함수
	 * @param gps1 - 좌표형식
	 * @param gps2 - 좌표형식
	 * @return String - 좌표형식
	 */
	private String calcGpsCenter(String gps1, String gps2) {
		String rtVal = gps1;
		
		try {
			if(gps1.length() == 0) {
				return gps2.length() == 0 ? "" : gps2;
			} else if(gps2.length() == 0) {
				return gps1;
			}
			
			int idx1 = gps1.indexOf(",");
			String gpsL1 = gps1.substring(0, idx1).trim(); 
			String gpsR1 = gps1.substring(idx1+1).trim();
			
			int idx2 = gps2.indexOf(",");
			String gpsL2 = gps2.substring(0, idx2).trim(); 
			String gpsR2 = gps2.substring(idx2+1).trim();
			
			double L=0, R=0 ;
			L = (Double.parseDouble(gpsL1) + Double.parseDouble(gpsL2))/2;
			R = (Double.parseDouble(gpsR1) + Double.parseDouble(gpsR2))/2;
			
			rtVal = L + ", " + R;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return rtVal;
	}
	
	
//	
//	public static void main(String[] args) {
//		String gps1 = "37.536181, 126.744844";
//		String gps2 = "37.5378158,126.7344885";
//		GyoguIntroServiceImpl i = new GyoguIntroServiceImpl();
//		
//		System.out.println(i.calcGpsCenter(gps1, gps2));
//	}
}
