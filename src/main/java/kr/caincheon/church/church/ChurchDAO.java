package kr.caincheon.church.church;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import kr.caincheon.church.common.base.AbstractDAO;


/**
 * 
 * @author benjamin
 */
@Repository("churchDAO")
public class ChurchDAO extends AbstractDAO {

	private final Logger L = Logger.getLogger(getClass());
	
	/**
	 * 멀티보드를 조회하는 메서드
	 * @param dto
	 * @param params
	 * @param left_menu_data_pg
	 * @param attachedFileMaxCount
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List selectChurchListInGigu(Map params) throws Exception {
		
		L.debug("DAO Called. params: "+params);

		// basic env
		params.put("DB_PREFIX", "newcaincheon");
		params.put("pageNo",    ipnull(params, "pageNo",   1));
		params.put("pageSize",  ipnull(params, "pageSize", 12));
		
		//
		List rtList = null;
		
		// where conditions
		String gigu_code  = pnull(params, "gigu_code"); // 지구별 코드(FK)
		
		// Query Combination
		try {
			rtList = selectList("church.Church.selectChurchListInGigu", params);
		} catch (Exception e) {
			L.error("SQL ERROR:"+e.getMessage()+"]");
			e.printStackTrace();
			throw e;
		} finally {
		}
		
		L.debug("DAO Result.[List:"+rtList+"]" );
		
		return rtList;
	}
	
	

}
