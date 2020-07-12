// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AdmMemberDao.java

package kr.caincheon.church.dao;

import java.util.List;
import java.util.Map;

import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.base.CommonException;

public interface AdmMemberDao
{

    public abstract Map admMemberLogin(Map map)
        throws CommonException;

    public abstract List inquireMemberList(Map map);

    public abstract int inquireMemberListCount(Map map);

    public abstract Map admMemberView(Map map)
        throws CommonException;

    public abstract String admMemberSelectId(Map map)
        throws CommonException;

    public abstract boolean admMemberInsert(Map map)
        throws CommonException;

    public abstract boolean admMemberModify(Map map)
        throws CommonException;

    public abstract boolean admMemberDelete(Map map)
        throws CommonException;

    public abstract List admMemberNonList(Map map);

    public abstract List admMemberAuthList(Map map);

    public abstract String admMemberAuthManage(Map map)
        throws CommonException;

    public abstract List admMemberDepartCodeList(Map map);

    public abstract List selectMemberListForExceldownload(Map map);

    public abstract CommonDaoDTO selectMemberList(Map map);

    //public abstract int selectMemberListCount(Map map);

    public abstract Map selectMemberView(Map map)
        throws CommonException;

    public abstract String selectMemberId(Map map)
        throws CommonException;

    public abstract boolean insertMember(Map map)
        throws CommonException;

    public abstract boolean updateMember(Map map)
        throws CommonException;

    public abstract boolean deleteMember(Map map)
            throws CommonException;

    public abstract boolean restoreMember(Map map)
            throws CommonException;

    
    /** 휴면계정 목록 조회 */
    public List<Map> listDormantMembers() throws CommonException;
    
    /** 휴먼계정 휴면Flag 설정(on)하기 */
    public void updateDormantMember(String id) throws CommonException;
    
    /** DB Connection 관련 자원을 모두 닫는 함수 */
    public void daoClose();
    
}
