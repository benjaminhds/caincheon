// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Paging.java

package kr.caincheon.church.common.base;

import java.util.HashMap;
import java.util.Map;

public class Paging
{

	/**
	 * default construct
	 */
	public Paging() {
	}
	
	public Paging(int blockSize, int pageSize, int pageNo) {
		this.blockSize = blockSize; // pages per block
		this.pageSize = pageSize; // rows per page
		this.pageNo = pageNo; // current in page
		
		init(pageNo);
	}
	
	
    public void copy(Paging paging)
    {
        paging.setPageSize(pageSize);
        paging.setFirstPageNo(firstPageNo);
        paging.setPrevPageNo(prevPageNo);
        paging.setStartPageNo(startPageNo);
        paging.setPageNo(pageNo);
        paging.setEndPageNo(endPageNo);
        paging.setNextPageNo(nextPageNo);
        paging.setFinalPageNo(finalPageNo);
        paging.setTotalCount(totalCount);
        paging.setPagingStart(pagingStart);
        paging.setPagingEnd(pagingEnd);
    }

    public int getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }

    public int getFirstPageNo()
    {
        return firstPageNo;
    }

    public void setFirstPageNo(int firstPageNo)
    {
        this.firstPageNo = firstPageNo;
    }

    public int getPrevPageNo()
    {
        return prevPageNo;
    }

    public void setPrevPageNo(int prevPageNo)
    {
        this.prevPageNo = prevPageNo;
    }

    public int getStartPageNo()
    {
        return startPageNo;
    }

    public void setStartPageNo(int startPageNo)
    {
        this.startPageNo = startPageNo;
    }

    public int getPageNo()
    {
        return pageNo;
    }

    public void setPageNo(int pageNo)
    {
        this.pageNo = pageNo;
    }

    public int getEndPageNo()
    {
        return endPageNo;
    }

    public void setEndPageNo(int endPageNo)
    {
        this.endPageNo = endPageNo;
    }

    public int getNextPageNo()
    {
        return nextPageNo;
    }

    public void setNextPageNo(int nextPageNo)
    {
        this.nextPageNo = nextPageNo;
    }

    public int getFinalPageNo()
    {
        return finalPageNo;
    }

    public void setFinalPageNo(int finalPageNo)
    {
        this.finalPageNo = finalPageNo;
    }

    public int getTotalCount()
    {
        return totalCount;
    }

    public void setTotalCount(int totalCount)
    {
        this.totalCount = totalCount;
        makePaging();
    }

    public int getPagingStart()
    {
        return pagingStart;
    }

    public int getPagingEnd()
    {
        return pagingEnd;
    }

    public void setPagingStart(int pagingStart)
    {
        this.pagingStart = pagingStart;
    }

    public void setPagingEnd(int pagingEnd)
    {
        this.pagingEnd = pagingEnd;
    }

	private void init(int currentPage)
	{
		this.pageNo = currentPage;
		this.startRow = ((currentPage - 1) * this.pageSize);
		this.endRow = (currentPage * this.pageSize);
		makePaging();
		// block calc
	}
	private int startRow  = 1; // 시작 레코드번호, just for mysql
	private int endRow    = 1; // 페이징 마지막 레코드 번호, just for mysql
	private int blockSize = 10; // 10개 레코드 1페이지, 10페이지 1블록. 
	public int getStartRow() { return this.startRow; }
	public int getEndRow() { return this.endRow; }
	
	
    private void makePaging()
    {
        if(totalCount == 0)
            return;
        if(pageNo == 0)
            setPageNo(1);
        if(pageSize == 0)
            setPageSize(10);
        int finalPage = (totalCount + (pageSize - 1)) / pageSize;
        if(pageNo > finalPage)
            setPageNo(finalPage);
        if(pageNo < 0 || pageNo > finalPage)
            pageNo = 1;
        
        boolean isNowFirst = pageNo == 1;
        boolean isNowFinal = pageNo == finalPage;
        int startPage = ((pageNo - 1) / 10) * 10 + 1;
        int endPage = (startPage + 10) - 1;
        if(endPage > finalPage)
            endPage = finalPage;
        setFirstPageNo(1);
        if(isNowFirst)
            setPrevPageNo(1);
        else
            setPrevPageNo(pageNo - 1 >= 1 ? pageNo - 1 : 1);
        
        setStartPageNo(startPage);
        setEndPageNo(endPage);
        
        if(isNowFinal)
            setNextPageNo(finalPage);
        else
            setNextPageNo(pageNo + 1 <= finalPage ? pageNo + 1 : finalPage);
        setFinalPageNo(finalPage);
        
        pagingStart = (getPageNo() - 1) * getPageSize() + 1;
        pagingEnd = getPageNo() * getPageSize();
    }

    public String toString()
    {
        return (new StringBuilder("{pageSize=")).append(pageSize).append(",firstPageNo=").append(firstPageNo).append(",prevPageNo=").append(prevPageNo).append(",startPageNo=").append(startPageNo).append(",pageNo=").append(pageNo).append(",endPageNo=").append(endPageNo).append(",nextPageNo=").append(nextPageNo).append(",finalPageNo=").append(finalPageNo).append(",daoTotalCount=").append(totalCount).append("}").toString();
    }
    
    public Map toMap() {
    	Map m = new HashMap();
    	
    	m.put("pageSize",    pageSize   );    // 게시 글 수
    	m.put("firstPageNo", firstPageNo);    // 첫 번째 페이지 번호
    	m.put("prevPageNo",  prevPageNo );    // 이전 페이지 번호
    	m.put("startPageNo", startPageNo);    // 시작 페이지 (페이징 네비 기준)
    	m.put("pageNo",      pageNo     );    // 페이지 번호(current page no)
    	m.put("endPageNo",   endPageNo  );    // 끝 페이지 (페이징 네비 기준)
    	m.put("nextPageNo",  nextPageNo );    // 다음 페이지 번호
    	m.put("finalPageNo", finalPageNo);    // 마지막 페이지 번호
    	m.put("daoTotalCount",  totalCount );    // 게시 글 전체 수
    	m.put("pagingStart", pagingStart);    // paging query start no
    	m.put("pagingEnd",   pagingEnd  );    // paging query end no
    	
    	return m;
    }

    private int pageSize;    // 게시 글 수  **페이징 기본 필수 필드**
    private int firstPageNo; // 첫 번째 페이지 번호
    private int prevPageNo;  // 이전 페이지 번호(for mysql)
    private int startPageNo; // 시작 페이지 (페이징 네비 기준)(for mysql)
    private int pageNo;      // 현재 페이지 번호  **페이징 기본 필수 필드**
    private int endPageNo;   // 끝 페이지 (페이징 네비 기준)
    private int nextPageNo;  // 다음 페이지 번호
    private int finalPageNo; // 마지막 페이지 번호
    private int totalCount;  // 게시 글 전체 수  **페이징 기본 필수 필드**

    private int pagingStart; // paging query start no
    private int pagingEnd;   // paging query end no

}
