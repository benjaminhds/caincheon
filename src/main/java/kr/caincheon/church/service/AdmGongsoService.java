// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AdmGongsoService.java

package kr.caincheon.church.service;

import java.util.Map;

import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.base.CommonException;

public interface AdmGongsoService
{

    public abstract CommonDaoDTO admGongsoList(Map map);

    public abstract CommonDaoDTO admGongsoContents(Map map)
        throws CommonException;

    public abstract boolean admGongsoInsert(Map map)
        throws CommonException;

    public abstract boolean admGongsoModify(Map map)
        throws CommonException;

    public abstract boolean admGongsoDelete(Map map)
        throws CommonException;

    public abstract String admGongsoMissaManage(Map map)
        throws CommonException;
}
