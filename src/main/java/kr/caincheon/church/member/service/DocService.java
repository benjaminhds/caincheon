// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DocService.java

package kr.caincheon.church.member.service;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import kr.caincheon.church.common.base.CommonException;

public interface DocService
{

    public abstract List docList(Map map);

    public abstract int docListCount(Map map);

    public abstract Map docContents(Map map);

    public abstract boolean docInsert(Map map, HttpServletRequest httpservletrequest)
        throws CommonException;

    public abstract boolean docModify(Map map, HttpServletRequest httpservletrequest)
        throws CommonException;

    public abstract boolean docDelete(Map map);
}
