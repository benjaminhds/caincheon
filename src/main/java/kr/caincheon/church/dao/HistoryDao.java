// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HistoryDao.java

package kr.caincheon.church.dao;

import java.util.List;
import java.util.Map;

import kr.caincheon.church.common.base.CommonDaoDTO;

public interface HistoryDao
{

    public  List historymList(Map map) throws Exception;

    public  int historymListCount(Map map) throws Exception;

    public  Map historymContents(Map map) throws Exception;

    public  boolean historymInsert(Map map) throws Exception;

    public  boolean historymModify(Map map) throws Exception;

    public  boolean historymDelete(Map map) throws Exception;

    public  List historyEventsList(Map map, boolean isAdminCall) throws Exception;

    public  int historyEventsListCount(Map map) throws Exception;

    public  Map historysContents(Map map) throws Exception;

    public  boolean historysInsert(Map map) throws Exception;

    public  boolean historysModify(Map map) throws Exception;

    public  boolean historysDelete(Map map) throws Exception;

    public  List<Map<String, Object>> categoryList(Map map) throws Exception;

    public  List<Map<String, Object>> categoryFullList(Map map) throws Exception;

    public  List<Map<String, Object>> historyEventyearsOnCategory(String category_code) throws Exception;
}
