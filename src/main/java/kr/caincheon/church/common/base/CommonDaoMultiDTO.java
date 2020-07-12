// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CommonDaoMultiDTO.java

package kr.caincheon.church.common.base;

// Referenced classes of package kr.caincheon.church.common:
//            Paging

public class CommonDaoMultiDTO
{

    public Paging paging;
    public Paging paging2;
    public Object daoResult;
    public Object daoResult1;
    public Object daoResult2;
    public Object daoResult3;
    public Object daoResult4;
    public Object daoResult5;
    public Object daoResult6;
    public Object daoResult7;
    
    /**
     * default constructor
     */
    public CommonDaoMultiDTO(){} 
    
    /**
     * constructor
     */
    public CommonDaoMultiDTO(Object daoResult, Paging paging) {
        this.daoResult = daoResult;
        this.paging = paging;
    }

    public String toString() {
    	return (daoResult==null?"":"\n\tdaoResult->"+daoResult.toString())
    			+ (daoResult1==null?"":"\n\tdaoResult1->"+daoResult1.toString())
    			+ (daoResult2==null?"":"\n\tdaoResult2->"+daoResult2.toString())
    			+ (daoResult3==null?"":"\n\tdaoResult3->"+daoResult3.toString())
    			+ (daoResult4==null?"":"\n\tdaoResult4->"+daoResult4.toString())
    			+ (daoResult5==null?"":"\n\tdaoResult5->"+daoResult5.toString())
    			+ (daoResult6==null?"":"\n\tdaoResult6->"+daoResult6.toString())
    			+ (daoResult7==null?"":"\n\tdaoResult7->"+daoResult7.toString())
    			+ (paging==null?"":"\n\tpaging->"+paging.toString())
    			+ (paging2==null?"":"\n\tpaging2->"+paging2.toString())
    			;
    }
}
