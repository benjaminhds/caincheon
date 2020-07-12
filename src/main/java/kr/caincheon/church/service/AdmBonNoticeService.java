// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AdmBonNoticeService.java

package kr.caincheon.church.service;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import kr.caincheon.church.common.base.CommonException;

public interface AdmBonNoticeService
{

    public abstract List admBonNoticeList(Map map);

    public abstract int admBonNoticeListCount(Map map);

    public abstract Map admBonNoticeContents(Map map)
        throws CommonException;

    public abstract boolean admBonNoticeInsert(Map map, HttpServletRequest httpservletrequest)
        throws CommonException;

    public abstract boolean admBonNoticeModify(Map map, HttpServletRequest httpservletrequest)
        throws CommonException;

    public abstract boolean admBonNoticeDelete(Map map)
        throws CommonException;

    public abstract List _1x00xList(Map map);
}
