// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AlbService.java

package kr.caincheon.church.news.service;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import kr.caincheon.church.common.base.CommonDaoDTO;

public interface AlbService
{

    public abstract List albList(Map map) throws Exception;

    public abstract int albListCount(Map map) throws Exception;

    public abstract Map albContents(Map map) throws Exception;

    public abstract List albDList(Map map) throws Exception;

    public abstract List albList_Main(Map map) throws Exception;

    public abstract Map scheduleByDiary(Map map) throws Exception;

    public abstract Map scheduleContents(Map map) throws Exception;

    public abstract List oldAlbList(Map map) throws Exception;

    public abstract int oldAlbListCount(Map map) throws Exception;

    public abstract Map oldAlbContents(Map map) throws Exception;

    public abstract boolean oldAlbInsert(Map map, HttpServletRequest request) throws Exception;

    public abstract boolean oldAlbModify(Map map, HttpServletRequest request) throws Exception;

    public abstract boolean oldAlbDelete(Map map) throws Exception;

    public abstract List _1x00xList(Map map) throws Exception;

    public abstract CommonDaoDTO albumList(Map map) throws Exception;

    public abstract Map albumContents(Map map) throws Exception;

    public abstract boolean albumInsert(Map map, HttpServletRequest request) throws Exception;

    public abstract boolean albumModify(Map map, HttpServletRequest request) throws Exception;

    public abstract boolean albumDelete(Map map, HttpServletRequest request) throws Exception;
}
