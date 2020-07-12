// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DepartdcService.java

package kr.caincheon.church.service;

import java.util.List;
import java.util.Map;

import kr.caincheon.church.common.base.CommonException;

public interface DepartdcService
{

    public abstract List<Map> departdcList(Map map) throws CommonException;

    public abstract int departdcListCount(Map map);

    public abstract Map departdcContents(Map map) throws CommonException;

    public abstract List<Map> priestList(Map map);

    public abstract List<Map> departdcViewGetPriestList(Map map);

    public abstract List<Map> _1x000List(Map map);

    public abstract boolean departdcInsert(Map map);

    public abstract boolean departdcModify(Map map) throws CommonException;

    public abstract boolean departdcDelete(Map map);
}
