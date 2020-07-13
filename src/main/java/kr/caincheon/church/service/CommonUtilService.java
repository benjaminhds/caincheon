// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CommonUtilService.java

package kr.caincheon.church.service;

import java.util.List;

/*
 * 
 */
public interface CommonUtilService
{

    public abstract List getDepartList(String s)
        throws Exception;

    public abstract List getDGroupCodeList(String s)
        throws Exception;

    public abstract List getPosList(String s)
        throws Exception;

    public abstract void freeDataSource();
}
