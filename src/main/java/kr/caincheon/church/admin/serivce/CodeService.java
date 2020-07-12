package kr.caincheon.church.admin.serivce;

import java.util.List;
import java.util.Map;

import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.base.CommonException;

public interface CodeService
{
    /** 코드를 등록 */
    public int iudCodeMaster(Map params) throws CommonException, Exception;
    
    /** 코드를 리턴 */
    public Map viewCode(Map params) throws CommonException, Exception;

    /** 코드 목록을 리턴 */
    public Map listCodes(Map params) throws CommonException, Exception;
    
    

    /** 코드인스턴스를 리턴 */
    public int iudCodeInstance(Map params) throws CommonException, Exception;
    
    /** 코드인스턴스를 리턴 */
    public Map viewCodeInstance(Map params) throws CommonException, Exception;

    /** 코드인스턴스 목록을 리턴 */
    public CommonDaoDTO listCodeInstances(Map params) throws CommonException, Exception;

    
    /** 코드인스턴스의 메모값을 조회 */
    public List<Map> selectedAreaCodeMapPolygonPath(Map params) throws CommonException, Exception;
    
}
