// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HolidayFileDao.java

package kr.caincheon.church.dao;

import java.util.List;
import java.util.Map;

import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.base.CommonException;

public interface HolidayFileDao
{

	public abstract boolean insertTempFile(List list);
	
    public abstract void holidayList(Map map, CommonDaoDTO dto);

    public abstract Map holidayContents(Map map)
        throws CommonException;

    public abstract boolean holidayModify(Map map)
        throws CommonException;

    public abstract boolean holidayDelete(Map map)
        throws CommonException;
}
