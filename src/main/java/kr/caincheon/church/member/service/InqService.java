package kr.caincheon.church.member.service;

import java.util.Map;

import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.base.CommonException;

public interface InqService
{
	
    public CommonDaoDTO inqList(Map map, boolean isAdmin);

    public Map inqContents(Map map, boolean isAdmin);

    public boolean inqInsert(Map map, boolean isAdmin) throws CommonException;

    public boolean inqModify(Map map, boolean isAdmin);

    public boolean inqDelete(Map map, boolean isAdmin);
    
    
}
