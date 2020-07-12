// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PopupService.java

package kr.caincheon.church.service;

import java.util.List;
import java.util.Map;

import kr.caincheon.church.common.base.CommonDaoDTO;

public interface PopupService
{
	// front popup select serivce
	public abstract List mainPopupList(Map map);
	
	// admin management
    public abstract CommonDaoDTO admPopupList(Map map);

    // admin management
    public abstract Map admPopupContents(Map map);

    // admin management
    public abstract boolean admPopupInsert(Map map);

    // admin management
    public abstract boolean admPopupModify(Map map);

    // admin management
    public abstract boolean admPopupDelete(Map map);
}
