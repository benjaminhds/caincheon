// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TempleService.java

package kr.caincheon.church.church.service;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import kr.caincheon.church.common.base.CommonDaoMultiDTO;

public interface TempleService
{

    public abstract void vacancyList(Map _params, CommonDaoMultiDTO daodto)
        throws Exception;

    public abstract void inquireMisaInChurchList(Map _params, CommonDaoMultiDTO daodto)
        throws Exception;

    public abstract void groupbyMailhallInRegion(Map _params, CommonDaoMultiDTO daodto)
        throws Exception;

    public abstract void selectMailhallTotally(Map _params, CommonDaoMultiDTO daodto)
        throws Exception;

    public abstract void selectChurchPictures(Map _params, CommonDaoMultiDTO daodto)
        throws Exception;

    public abstract void newsList(Map _params, CommonDaoMultiDTO daodto)
        throws Exception;

    public abstract void newsVO(Map _params, CommonDaoMultiDTO daodto)
        throws Exception;

    public abstract void parishAlbumList(Map _params, CommonDaoMultiDTO daodto)
        throws Exception;

    public abstract void albumVO(Map _params, CommonDaoMultiDTO daodto)
        throws Exception;

    public abstract Map templeAlbumContents(Map _params)
        throws Exception;

    public abstract void freeDataSource();
    
    
    
    // 글쓰기 권한 사용자를 위한 OP
    public boolean church_view_for_user(Map _params, String left_menu_data_pg, HttpServletRequest httpservletrequest) throws Exception ;
    public boolean church_view_for_user_write_update(Map _params, String left_menu_data_pg, HttpServletRequest httpservletrequest) throws Exception ;
    

    /*
     * 통합검색 서비스 :: 본당/공소 ( Called by UnifySearchServiceImpl)
     */
    public CommonDaoMultiDTO unifySearch(Map _params, Connection conn) throws Exception ;
    
}
