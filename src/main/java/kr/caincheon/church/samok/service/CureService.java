// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CureService.java

package kr.caincheon.church.samok.service;

import java.util.Map;

import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.base.CommonException;

public interface CureService
{

    public CommonDaoDTO cureList(Map _params, String left_menu_data_pg) throws CommonException;

    public CommonDaoDTO cureContents(Map _params, String left_menu_data_pg) throws CommonException;
    
}
