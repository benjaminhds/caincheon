// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MarryDao.java

package kr.caincheon.church.dao;

import java.util.List;
import java.util.Map;

import kr.caincheon.church.common.base.CommonException;

public interface MarryDao
{

    public abstract List marryList(Map map);

    public abstract int marryListCount(Map map);

    public abstract Map marryContents(Map map);

    public abstract List marryLectureList2(Map map)
        throws CommonException;

    public abstract Map marryGuideContents(Map map);

    // 카나혼인강좌 일자 조회
    public abstract List marryDList(Map map);

    // 카나혼인강좌 일일시간표 조회
    public abstract List marryTList(Map map);

    public abstract boolean marryGModify(Map map) throws CommonException;
    
    // 카나혼 강좌 일일 시간표 수정
    public boolean marryGTimeUpsert(Map _params) throws CommonException;

    public abstract List marryLectureList(Map map);
    
//    public abstract boolean marryGDateDelete(Map map)
//        throws CommonException;

    public abstract boolean marryGTimeDelete(Map map)
        throws CommonException;

    public abstract boolean marryInsert(Map map);

    public abstract boolean marryModify(Map map);

    public abstract boolean marryDelete(Map map);

    public abstract Map getMember(Map map);

    public abstract List marryExcelList(Map map);
}
