// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AdmMemberService.java

package kr.caincheon.church.service;

import java.util.List;
import java.util.Map;

import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.base.CommonException;

public interface AdmMemberService
{

    public Map login(Map map) throws CommonException;

    public List admMemberList(Map map) throws CommonException;

    public int admMemberListCount(Map map) throws CommonException;

    public Map admMemberView(Map map) throws CommonException;

    public String selectAdminMemberInfo(Map map) throws CommonException;

    public List admMemberDepartCodeList(Map map) throws CommonException;

    public boolean admMemberInsert(Map map) throws CommonException;

    public boolean admMemberModify(Map map) throws CommonException;

    public boolean admMemberDelete(Map map) throws CommonException;

    public List admMemberNonList(Map map) throws CommonException;

    public List admMemberAuthList(Map map) throws CommonException;

    public String admMemberAuthManage(Map map) throws CommonException;


    

    public CommonDaoDTO selectMemberList(Map map) throws CommonException;

    //public int selectMemberListCount(Map map) throws CommonException;

    public Map selectMemberView(Map map) throws CommonException;

    public String selectMemberId(Map map) throws CommonException;

    public boolean insertMember(Map map) throws CommonException;

    public boolean updateMember(Map map) throws CommonException;

    public boolean deleteMember(Map map) throws CommonException;

    public boolean restoreMember(Map map) throws CommonException;
    
    /** 휴면계정에 메일 발송 */
    public Map admMemberSendmaildormant() throws CommonException;

}
