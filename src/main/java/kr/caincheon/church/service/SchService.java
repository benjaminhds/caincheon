// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SchService.java

package kr.caincheon.church.service;

import java.util.List;
import java.util.Map;

import kr.caincheon.church.common.base.CommonDaoDTO;

public interface SchService
{

    public CommonDaoDTO schList(Map map);

    //public int schListCount(Map map);

    public Map schContents(Map map);

    public Map schCView(Map map);

    public CommonDaoDTO schView(Map map) throws Exception;

    public boolean schInsert(Map map);

    public boolean schModify(Map map);

    public boolean schDelete(Map map);

    public Map scheduleByDiary(Map map) throws Exception;

    public List _1x00xList(Map map);
}
