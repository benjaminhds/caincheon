// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PriestDao.java

package kr.caincheon.church.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import kr.caincheon.church.common.base.CommonDaoDTO;

public interface PriestDao
{

    public abstract void priestList(CommonDaoDTO dto, Map map) throws Exception;

//    public abstract int priestListCount(Map map);

    public abstract void priestContents(CommonDaoDTO dto, Map map) throws Exception;

    public abstract void holyList(CommonDaoDTO dto, Map map) throws Exception;

//    public abstract int holyListCount(Map map);

    public abstract Map holyContents(Map map) throws Exception;

    public abstract void befPriestList(CommonDaoDTO dto, Map map) throws Exception;

//    public abstract int befPriestListCount(Map map);

    public abstract Map befPriestContents(Map map) throws Exception;

    public abstract void admPriestList(CommonDaoDTO dto, Map map) throws Exception;

//    public abstract int admPriestListCount(Map map);
    
    public abstract Map admPriestContents(Map map) throws Exception;

    public abstract List admPriestDCodeList(Map map) throws Exception;

    public abstract boolean admPriestInsert(Map map) throws Exception;

    public abstract boolean admPriestModify(Map map) throws Exception;

    public abstract boolean admPriestOrgDelete(Map map) throws Exception;

    public abstract boolean admPriestDelete(Map map) throws Exception;

    public abstract List brialPlaceList(Map map) throws Exception;

    public abstract boolean befPriestTombInsert(Map map) throws Exception;

    public abstract boolean befPriestTombDelete(Map map) throws Exception;

    public abstract boolean befPriestInsert(Map map) throws Exception;

    public abstract boolean befPriestModify(Map map) throws Exception;

    public abstract boolean befPriestDelete(Map map) throws Exception;
    
    /*
     * 발령정보 upsert 처리
     */
    public void upsertPreistAssign(CommonDaoDTO dto, Map _params) throws Exception;

    /*
     * 발령정보 upsert 처리
     */
    public void deletePreistAssign(CommonDaoDTO dto, Map _params) throws Exception;
    
    /*
     * 사제 발령 정보를 조회한다.
     */
    public List selectOrgDepartmentPriestRel(String p_idx) throws Exception;
    
    
    // 통합검색 시작시 conneciton 생성하여 1개로만 검색을 하기 위함.
    public Connection getConn();
    // 통합검색이 종료시 객체를 닫는다.
    public void closeConn();
    
    /*
     * 통합검색 서비스 :: 사제 ( Called by UnifySearchServiceImpl)
     */
    public List unifySearchPriest(Map _params) throws Exception ;
    
    /*
     * 통합검색 서비스 :: 선종사제 ( Called by UnifySearchServiceImpl)
     */
    public List unifySearchPriestOld(Map _params) throws Exception ;
}
