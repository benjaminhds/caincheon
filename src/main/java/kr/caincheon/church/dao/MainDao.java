<<<<<<< HEAD
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MainDaoImpl.java

package kr.caincheon.church.dao;

import java.util.List;
import java.util.Map;


//
public interface MainDao 
{
	
	//
    public Map todayContents();

    //
    public List noticeList(String b_idx);

    //
    public List parishList();

    /*
     * 이달의 사제
     */
    public List priestListOfThisMonth();

    //
    public List parList_Main();

    /*
     * 주별 교구 일정을 조회
     */
    public Map<String , Object> schList_Main(Map _params);

    
}
=======
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MainDaoImpl.java

package kr.caincheon.church.dao;

import java.util.List;
import java.util.Map;


//
public interface MainDao 
{
	
	//
    public Map todayContents();

    //
    public List noticeList(String b_idx);

    //
    public List parishList();

    /*
     * 이달의 사제
     */
    public List priestListOfThisMonth();

    //
    public List parList_Main();

    /*
     * 주별 교구 일정을 조회
     */
    public Map<String , Object> schList_Main(Map _params);

    
}
>>>>>>> branch 'master' of https://github.com/benjaminhds/caincheon
