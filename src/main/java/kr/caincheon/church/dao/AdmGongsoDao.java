// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AdmGongsoDao.java

package kr.caincheon.church.dao;

import java.util.List;
import java.util.Map;

import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.base.CommonException;

public interface AdmGongsoDao
{

    public abstract CommonDaoDTO admGongsoList(Map map);

    public abstract Map admGongsoContents(Map map);

    /*
     * 공소 미사 시간 조회
     */
    public abstract void admGMissaList(Map map, CommonDaoDTO dto, boolean hasHtmlTag);

    public abstract boolean admGongsoInsert(Map map);

    public abstract boolean admGongsoModify(Map map);

    public abstract boolean admGongsoDelete(Map map);

    public abstract String admGongsoMissaManage(Map map)
        throws CommonException;

}
