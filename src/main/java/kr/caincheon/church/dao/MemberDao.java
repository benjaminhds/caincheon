// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MemberDao.java

package kr.caincheon.church.dao;

import java.util.Map;

import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.base.CommonException;

public interface MemberDao
{

    public  Map login(Map map) throws CommonException;

    /** 회원가입 */
    public  boolean join(Map map) throws CommonException;

    /** 회원가입 실패 : 실패시 아이디를 delete한다. */
    public  int joinCancel(String delId) throws CommonException;

    
    public  boolean joinFinish(Map map)
        throws CommonException;

    public  Map memberDormantClearRequest(Map map)
        throws CommonException;

    public  boolean memberDormantClearConfirm(Map map)
        throws CommonException;

    public  Map memberFindId(Map map)
        throws CommonException;

    public  Map memberFindPwd(Map map)
        throws CommonException;

    public  Map memberModifyInformation(Map map)
        throws CommonException;

    
    public  CommonDaoDTO memberLeave(Map map) throws CommonException;

    public  Map selectMemberInfo(Map map)
        throws CommonException;
}
