package kr.caincheon.church.dao;

import java.util.Map;

import kr.caincheon.church.common.base.CommonDaoDTO;

public interface PopupDao
{

    public abstract void popupList(Map map, CommonDaoDTO dto);

    public abstract Map popupContents(Map map);

    public abstract boolean popupInsert(Map map);

    public abstract boolean popupModify(Map map);

    public abstract boolean popupDelete(Map map);

    public abstract Map getMember(Map map);
}
