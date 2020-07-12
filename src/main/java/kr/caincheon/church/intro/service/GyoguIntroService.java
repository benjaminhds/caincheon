// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GyoguIntroService.java

package kr.caincheon.church.intro.service;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import kr.caincheon.church.common.base.CommonDaoDTO;

public interface GyoguIntroService
{

    public CommonDaoDTO organizationList(Map map);

    public CommonDaoDTO beforePriestList(Map map);

    public CommonDaoDTO admOrganizationList(Map map);

    public Map admOrganizationView(Map map);

    public CommonDaoDTO adm_priest_list(Map map);

    public boolean organizationInsert(Map map);

    public boolean organizationModify(Map map);

    public boolean organizationDelete(Map map);
    

    /*
     * 통합검색 서비스 :: 기관/단체/수도회 ( Called by UnifySearchServiceImpl)
     */
    public List unifySearchOrganization(Map _params, Connection conn) throws Exception ;
    
    /*
     * 관할구역 좌표 조회
     */
    public CommonDaoDTO jurisdictionCoordinate(Map _params) throws Exception ;

}
