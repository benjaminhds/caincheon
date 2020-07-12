package kr.caincheon.church.common.base;

import java.util.List;
import java.util.Map;

/**
 * " DAO <-> 서비스 <-> Controller " 컴포넌트 간에서 데이터를 주고 받기 위한 DTO
 * 불필요한 함수 생성 및 로직을 자제하고 코드의 단순화 및 명확화를 위해 추가함.
 * @author benjamin
 * @date 2017.10.10 
 */
public class CommonDaoDTO
{
	/**
     * 목록조회
     */
    public List daoList = null;

    /**
     * 목록조회시 해당 조건의 전체 레코드 수
     */
    public int daoTotalCount = 0;

    /**
     * 상세보기 : 하나의 레코드 데이터 보기
     */
    public Map daoDetailContent = null;
    
    /**
     * 목록에서 페이징 처리 정보
     */
    public Paging paging = null;

    /**
     * 조직목록
     */
    public List orgList = null;

    /**
     * 기타목록
     */
    public List otherList = null;
    
    /**
     * 기타 데이터 : Object로 어떤 데이터도 세팅이 가능
     */
    public Object otherData = null;
    /**
     * 기타 데이터 : Object로 어떤 데이터도 세팅이 가능
     */
    public Object otherData1 = null;
    
    /*
     * 
     */
    public boolean isBool = false;

    /**
     * 디폴트생성자
     */
    public CommonDaoDTO() {
    	
    }
    
    /**
     * 페이징 설정
     */
    public void setPaging(int pageNo, int pageSize) {
    	if(paging==null) 
    		paging = new Paging();
    	
        paging.setPageNo(pageNo);
        paging.setPageSize(pageSize);
        paging.setTotalCount(daoTotalCount);
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
    	return "[LIST_TOTAL="+daoTotalCount
    			+", isBool="+isBool
    			+(daoList==null?"":", LIST="+daoList)
    			+(daoDetailContent==null?"":", daoDetailContent="+daoDetailContent)
    			+(orgList==null?"":", orgList="+orgList)
    			+(otherList==null?"":", otherList="+otherList)
    			+(otherData==null?"":", otherData="+otherData)
    			+(otherData1==null?"":", otherData1="+otherData1)
    			+(paging==null?"":", paging="+paging)
    			+"]";
    }
    
}
