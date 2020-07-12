// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   InqDao.java

package kr.caincheon.church.dao;

import java.util.Map;

import kr.caincheon.church.common.base.CommonDaoDTO;

public interface InqDao
{
	/* 목록 조회 */
    public CommonDaoDTO inqList(Map map, boolean isAdmin);

    /* 상세 조회 */
    public Map inqContents(Map map, boolean isAdmin);

    /* 신규 */
    public boolean inqInsert(Map map, boolean isAdmin);

    /* 변경 */
    public boolean inqModify(Map map, boolean isAdmin);

    /* 삭제 */
    public boolean inqDelete(Map map, boolean isAdmin);
    
    /* 삭제복원 */
    public boolean inqAdmRecovery(String inquire_no);

    /* 탈퇴시 계정의 모든 글을 삭제플래그(Y) 처리 */
    public int inqMemberLeave(String memberId, String renameId);

    /* 등록/변경자의 이름 조회 */
    public Map getMember(Map map);
    
}
