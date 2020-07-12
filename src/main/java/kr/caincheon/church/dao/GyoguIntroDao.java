// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GyoguIntroDao.java

package kr.caincheon.church.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import kr.caincheon.church.common.base.CommonDaoDTO;

public interface GyoguIntroDao
{
	// 기관/단체 목록
    public  void organizationList(Map map, CommonDaoDTO dto);

    // 선종사제 목록
    public  void beforePriestList(Map map, CommonDaoDTO dto);

    public  CommonDaoDTO admOrganizationList(Map map);

    public  Map admOrganizationView(Map map);

    public  CommonDaoDTO adm_priest_list(Map map);

    public  boolean organizationInsert(Map map);

    public  boolean organizationModify(Map map);

    public  boolean organizationDelete(Map map);
    
    public  List unifySearch(Map map, Connection conn);
}
