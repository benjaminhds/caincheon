// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AdmParService.java

package kr.caincheon.church.service;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import kr.caincheon.church.common.base.CommonException;

public interface AdmParService
{

    public abstract List parList(Map map);

    public abstract int parListCount(Map map);

    public abstract Map parContents(Map map)
        throws CommonException;

    public abstract boolean parInsert(Map map, HttpServletRequest httpservletrequest)
        throws CommonException;

    public abstract boolean parModify(Map map, HttpServletRequest httpservletrequest)
        throws CommonException;

    public abstract boolean parDelete(Map map)
        throws CommonException;
}
