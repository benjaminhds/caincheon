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