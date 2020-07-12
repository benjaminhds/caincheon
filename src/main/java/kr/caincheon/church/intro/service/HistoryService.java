// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HistoryService.java

package kr.caincheon.church.intro.service;

import java.util.List;
import java.util.Map;

import kr.caincheon.church.common.base.CommonDaoDTO;

public interface HistoryService
{

    public List historymList(Map _params)  throws Exception ;

    public int historymListCount(Map _params)  throws Exception ;

    public Map historymContents(Map _params)  throws Exception ;

    public boolean historymInsert(Map _params)  throws Exception ;

    public boolean historymModify(Map _params)  throws Exception ;

    public boolean historymDelete(Map _params)  throws Exception ;

    public Object historyEventsList(Map _params, boolean isAdminCall)  throws Exception ;

    public int historyEventsListCount(Map _params)  throws Exception ;

    public Map historysContents(Map _params)  throws Exception ;

    public boolean historysInsert(Map _params)  throws Exception ;

    public boolean historysModify(Map _params)  throws Exception ;

    public boolean historysDelete(Map _params)  throws Exception ;

    public List categoryList(Map _params)  throws Exception ;

    public CommonDaoDTO categoryFullList(Map _params)  throws Exception ;

    public List historyYearList(Map _params, String category_code)  throws Exception ;
}
