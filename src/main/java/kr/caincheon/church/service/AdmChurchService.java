// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OrgHierarchyService.java

package kr.caincheon.church.service;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.base.CommonDaoMultiDTO;
import kr.caincheon.church.common.base.CommonException;

public interface AdmChurchService
{

	/* 본당목록조회 */
    public List admChurchList(Map map);

    public int admChurchListCount(Map map);

    /* 본당상세조회 */
    public CommonDaoDTO admChurchContents(Map map) throws CommonException;

    /* 본당등록 */
    public boolean admChurchInsert(Map map, HttpServletRequest httpservletrequest) throws CommonException;

    /* 본당수정 */
    public boolean admChurchModify(Map map, HttpServletRequest httpservletrequest) throws CommonException;

    /* 본당삭제 */
    public boolean admChurchDelete(Map map) throws CommonException;

    
    /* 본당미사목록 */
    public List admMissaList(Map map);
    
    /* 본당미사개별삭제 */
    public boolean admChurchMissaInfoDelete(Map map) throws CommonException;

    /* 본당미사개별등록 */
    public int admChurchMissaManage(Map map) throws CommonException;
    
}
