// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MarryService.java

package kr.caincheon.church.member.service;

import java.util.List;
import java.util.Map;

import kr.caincheon.church.common.base.CommonException;

public interface MarryService
{

    public abstract List marryList(Map map);

    public abstract int marryListCount(Map map);

    public abstract Map marryContents(Map map);

    public abstract boolean marryInsert(Map map) throws CommonException;

    public abstract boolean marryModify(Map map) throws CommonException;

    public abstract boolean marryDelete(Map map);

    public abstract List marryLectureList(Map map) throws CommonException;

    public abstract List marryLectureList2(Map map) throws CommonException;

    public abstract Map marryGuideContents(Map map);

    public abstract List marryDList(Map map);

    public abstract List marryTList(Map map);

    public abstract boolean marryGTimeUpsert(Map map) throws CommonException;

//    public abstract boolean marryGDateDelete(Map map) throws CommonException;

    public abstract boolean marryGTimeDelete(Map map) throws CommonException;
}
