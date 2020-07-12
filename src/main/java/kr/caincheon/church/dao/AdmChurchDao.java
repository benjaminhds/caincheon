// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AdmChurchDao.java

package kr.caincheon.church.dao;

import java.util.List;
import java.util.Map;

import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.base.CommonException;

public interface AdmChurchDao
{

    public  List admChurchList(Map map);

    public  int admChurchListCount(Map map);

    /*
     * 미사 정보 조회
     */
    public  void admMissaList(Map _params, CommonDaoDTO dto);

    public  Map admChurchContents(Map map);

    public  boolean admChurchInsert(Map map, List list);

    public  boolean admChurchModify(Map map, List list);
    
    public  boolean admChurchDelete(Map map);

    
    public  boolean admChurchMissaInfoDelete(Map map) throws CommonException;

    public  int  admChurchMissaInfoInsert(Map map) throws CommonException;

    public  Map getMember(Map map);
}
