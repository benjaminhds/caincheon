// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TempleDao.java

package kr.caincheon.church.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import kr.caincheon.church.common.base.BaseDao;
import kr.caincheon.church.common.base.CommonDaoMultiDTO;

public interface TempleDao
    extends BaseDao
{

    public void inquireChurchListWithMisa(Map map, CommonDaoMultiDTO daodto)
        throws Exception;

    public void vacancyList(Map map, CommonDaoMultiDTO daodto)
        throws Exception;

    public void selectMisaInfo(String as[], CommonDaoMultiDTO daodto, Map map)
        throws SQLException;

    public Map groupbyMailhallInRegion(Map map)
        throws Exception;

    public Map listMailhallInRegion(Map map)
        throws Exception;

    public Map selectTotallyMainhallInfo(String s)
        throws Exception;

    public Map selectPriestInChurch(String s)
        throws Exception;

    public List selectChurchPictures(String s)
        throws Exception;

    public Map selectNewsInChurch(Map map)
        throws Exception;

//    public void newsList(Map map, CommonDaoMultiDTO daodto)
//        throws Exception;

    public void newsVO(Map map, CommonDaoMultiDTO daodto)
        throws Exception;

    public void parishAlbumList(Map map, CommonDaoMultiDTO daodto)
        throws Exception;

    public void albumVO(Map map, CommonDaoMultiDTO daodto)
        throws Exception;

    // 본당 앨범 
    public Map templeAlbumContents(Map map)
        throws Exception;
    
    /** 통합검색 :: 본당/공소 검색 */
    public List unifySearch(Map _params, Connection conn) throws Exception;
    
    /* 지구코드 목록 조회 :: true이면 이름으로 asc, false이면 code로 asc */
    public List selectGiguCodeList(String orderbyCuase) throws Exception ;
    
    /* 지구별 본당 목록조회 */
    public List selectChurchListInGigu(String gigu_code) throws Exception ;
}
