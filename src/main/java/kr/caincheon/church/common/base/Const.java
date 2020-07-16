// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Const.java

package kr.caincheon.church.common.base;

//
public interface Const
{

    public static final String DOMAIN_URL = "http://112.172.48.142";
    public static final String WeekCodes[][] = {  {"1", "월"}
										        , {"2", "화"}
										        , {"3", "수"}
										        , {"4", "목"}
										        , {"5", "금"}
										        , {"6", "토"}
										        , {"7", "일"}
										        , {"8", "비고"} };
    
    public static final String ERR_MSG           = "ERR_MSG";
    public static final String ERR_CODE          = "ERR_CODE";
    public static final String SESSION_KEY_GOTO  = "GOTO_URL";
    
    public static final String SESSION_KEY_MEM_ID    = "SS_MEM_ID";
    public static final String SESSION_KEY_MEM_NM    = "SS_MEM_NM";
    public static final String SESSION_KEY_MEM_BAPNM = "SS_MEM_BAPNM";
    
    public static final String SESSION_KEY_WRITABLEBOARD_IDX = "SS_WRITABLEBOARD_IDX";
    public static final String SESSION_KEY_BOARDWRITEYN = "SS_WRITEYN";
    public static final String SESSION_KEY_GROUPGUBUN   = "SS_GROUPGUBUN";
    public static final String SESSION_KEY_DEPARTIDX    = "SS_DEPARTIDX";
    public static final String SESSION_KEY_DEPARTNM     = "SS_DEPARTNM";
    public static final String SESSION_KEY_CHURCHIDX    = "SS_CHURCHIDX";
    public static final String SESSION_KEY_CHURCHNM     = "SS_CHURCHNM";
    public static final String SESSION_KEY_ORGIDX       = "SS_ORGIDX";
    public static final String SESSION_KEY_ORGNM        = "SS_ORGNM";
    
    
    public static final String SESSION_KEY_ADM_MEM_ID    = "ADM_MEM_ID";
    public static final String SESSION_KEY_ADM_MEM_NM    = "ADM_MEM_NM";
    public static final String SESSION_KEY_ADM_MEM_GROUP = "ADM_MEM_GROUP";
    
    // extends
    public static final String ADM_MAPKEY_GROUPLIST   = "ADM_RETURN_GROUPLIST"; 
    public static final String ADM_MAPKEY_LIST        = "ADM_RETURN_LIST";  
    public static final String ADM_MAPKEY_COUNT       = "ADM_RETURN_COUNT"; 
    public static final String ADM_MAPKEY_PAGING      = "ADM_RETURN_PAGING";
    public static final String ADM_MAPKEY_LIST_OTHERS = "ADM_RETURN_LIST_OTHERS"; 
    public static final String ADM_MAPKEY_CONTENT	  = "ADM_RETURN_CONTENT"; 
    
    
    // 카나혼 발신자 메일 주소
    public static final String EMAIL_SENDER_ADDR_CANAHONIN_LECTURE = "family@caincheon.or.kr";
    // 통신교리 발신자 메일 주소
    public static final String EMAIL_SENDER_ADDR_COMMUNICATION_DOCTRINE = "mission@caincheon.or.kr";
    
}
