// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AlbDao.java

package kr.caincheon.church.dao;

import java.util.List;
import java.util.Map;

import kr.caincheon.church.common.base.CommonDaoDTO;

public interface AlbDao
{

    public abstract String getBoardCategory(Map map) throws Exception;

    public abstract List albList(Map map) throws Exception;

    public abstract int albListCount(Map map) throws Exception;

    public abstract Map albContents(Map map) throws Exception;

    public abstract List albDList(Map map) throws Exception;

    public abstract List albList_Main(Map map) throws Exception;

    public abstract Map scheduleByDiary(Map map) throws Exception ;

    public abstract Map scheduleContents(Map map) throws Exception;

    public abstract List oldAlbList(Map map) throws Exception;

    public abstract int oldAlbListCount(Map map) throws Exception;

    public abstract Map oldAlbContents(Map map) throws Exception;

    public abstract boolean oldAlbInsert(Map map, List list) throws Exception;

    public abstract boolean oldAlbModify(Map map, List list) throws Exception;

    public abstract boolean oldAlbDelete(Map map) throws Exception;

    public abstract void albumList(Map map, CommonDaoDTO dto) throws Exception;

    public abstract Map albumContents(Map map) throws Exception;

    public abstract boolean albumInsert(Map map, List list) throws Exception;

    public abstract boolean albumModify(Map map, List list) throws Exception;

    public abstract boolean albumDelete(Map map) throws Exception;

    public abstract Map getAdmin(Map map) throws Exception;
}
