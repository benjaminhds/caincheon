package kr.caincheon.church.member.service;

import java.util.Map;

import kr.caincheon.church.common.base.CommonException;

public interface MemberService
{

    public Map login(Map map) throws CommonException, Exception;

    public boolean join(Map map) throws CommonException, Exception;

    public boolean joinFinish(Map map) throws CommonException, Exception;

    public Map memberDormantClearRequest(Map map) throws CommonException, Exception;

    public boolean memberDormantClearConfirm(Map map) throws CommonException, Exception;

    public Map memberFindId(Map map) throws CommonException, Exception;

    public Map memberFindPwd(Map map) throws CommonException, Exception;

    public Map memberModifyInformation(Map map) throws CommonException, Exception;

    /** 회원이 나의정보에서 탈퇴버튼을 누를때 처리 - 회원 탈퇴 : 탈퇴처리(Flag is on) 및 나누고싶은이야기 탈퇴처리(Flag is on) */
    public boolean memberLeave(Map map) throws CommonException, Exception;

    public Map selectMemberInfo(Map map) throws CommonException, Exception;
}
