// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EuchaService.java

package kr.caincheon.church.member.service;

import java.util.List;
import java.util.Map;

import kr.caincheon.church.common.base.CommonException;

public interface EuchaService
{

    public abstract List euchaList(Map map);

    public abstract int euchaListCount(Map map);

    public abstract Map euchaContents(Map map);

    public abstract boolean euchaInsert(Map map)
        throws CommonException;

    public abstract boolean euchaModify(Map map);

    public abstract boolean euchaDelete(Map map);
}
