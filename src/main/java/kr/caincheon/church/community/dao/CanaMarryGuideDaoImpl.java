package kr.caincheon.church.community.dao;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import kr.caincheon.church.common.base.CommonDao;
import kr.caincheon.church.common.base.CommonDaoDTO;
import kr.caincheon.church.common.base.CommonException;
import kr.caincheon.church.common.base.Const;



@Repository("canaMarryGuideDao")
public class CanaMarryGuideDaoImpl extends CommonDao implements CanaMarryGuideDao {
	
	private final Logger _logger = Logger.getLogger(getClass());
	
	private final String TBL = "MARRY_GUIDE ";
	
	/*
	 * 목록조회
	 * (non-Javadoc)
	 * @see kr.caincheon.church.community.dao.CanaMarryGuideDao#list(java.util.Map, kr.caincheon.church.common.CommonDaoDTO)
	 */
	@Override
	public void list(Map _params, CommonDaoDTO dto) throws Exception
    {
    	D(_logger, Thread.currentThread().getStackTrace(), "DAO select Called.[params:"+_params+"]" );
        String query = "";
        
        String type  = pnull(_params, "type");
        //String close = pnull(_params, "close");
        
        // 기준일자 조회
        String sdate = pnull(_params, "sdate");
        
        /*int pageNo    = pint(_params, "pageNo", 1);
        int pageSize  = pint(_params, "pageSize", 20);
        int startRnum = (pageNo - 1) * pageSize + 1;
        int endRnum   = pageNo * pageSize;*/
        
        try {
        	/* default : 조회기준일 이후 것만 조회 */
        	/* type 1(카나혼~), 2(약혼자주말~) */
        	query = "SELECT X.* FROM ( "
        			+ "SELECT ROW_NUMBER() OVER(ORDER BY A.TYPE ASC, A.EDU_DATE_START ASC) RNUM"
        			+ ", CASE WHEN TYPE='1' THEN '카나혼인강좌' ELSE '약혼자주말' END AS TYPE_TEXT "
        			+ ", CASE WHEN CLOSE_YN='Y' THEN '마감' ELSE '모집중' END AS CLOSE_YN_TEXT "
        			+ ", (SELECT COUNT(*)*2 FROM MARRY_APPLY WHERE LECTURE_APPLY_DAY=CONVERT (DATETIME, A.EDU_DATE_START) AND APPLY_TYPE='"+type+"') AS RECEIPT_COUNT "
        			+ ", A.* FROM "+TBL+" A "
        			+ " WHERE 1=1 "
        			+ (sdate.length() != 0 ? " AND EDU_DATE_START='"+sdate+"' " : " AND EDU_DATE_START > CONVERT(CHAR(10),  GETDATE(), 112) ")
        			+ (type.length()  == 0 ? "" : " AND TYPE in ('"+type+"')")
        			+ ") X "
        			//+ " WHERE RNUM BETWEEN "+startRnum+" AND "+endRnum+""//페이징없음.
        			;
        	
            dto.daoList = super.executeQueryList(query);
            dto.daoTotalCount = executeCount(query, true);
            
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e );
        } finally {
        	free();
        }
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Return.[dto:"+dto+"]" );
    }
	
	/*
	 * 신규 등록하기
	 * (non-Javadoc)
	 * @see kr.caincheon.church.community.dao.CanaMarryGuideDao#insert(java.util.Map)
	 */
	@Override
	public int insert(Map _params) throws Exception {
		D(_logger, Thread.currentThread().getStackTrace(), "DAO insert Called.[params:"+_params+"]" );
		int rtVal = 0;
        String query = "";

        String type = pnull(_params, "type");
        String sdate = pnull(_params, "edu_date_start");
        String edate = pnull(_params, "edu_date_end");
        String count = pnull(_params, "recruitment_personnel");
        String close_yn = pnull(_params, "close_yn");
        String del_yn = pnull(_params, "del_yn");
        String explain = pnull(_params, "explain");
        String edu_guide = pnull(_params, "edu_guide");
        String id = pnull((Map)_params.get("__SESSION_MAP__"), Const.SESSION_KEY_ADM_MEM_ID);
        
        try {
        	query = "INSERT INTO "+TBL+" ("
        			+ "MARRY_GUIDE_NO, TYPE, ID, EDU_DATE_START, EDU_DATE_END, RECRUITMENT_PERSONNEL, CLOSE_YN, DEL_YN, EXPLAIN, EDU_GUIDE, REGDATEDT, UPDATEDT"
        			+ ") VALUES ("
        			+ " (select isnull(Max(MARRY_GUIDE_NO),0)+1 from "+TBL+" ) "
        			+ ", '"+type+"', '"+id+"', '"+sdate+"', '"+edate+"', '"+count+"', '"+close_yn+"', '"+del_yn+"', '"+explain+"', '"+edu_guide+"', GETDATE(), null"
        			+ ")";
        	
        	rtVal = super.executeUpdate(query);
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e );
        } finally {
        	free();
        }
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Return.[rtVal:"+rtVal+"]" );
        return rtVal;
	}
	
	/*
	 * 수정하기
	 * (non-Javadoc)
	 * @see kr.caincheon.church.community.dao.CanaMarryGuideDao#update(java.util.Map)
	 */
	@Override
	public int update(Map _params) throws Exception {
		D(_logger, Thread.currentThread().getStackTrace(), "DAO update Called.[params:"+_params+"]" );
		int rtVal = 0;
        String query = "";

        String type = pnull(_params, "type");
        String sdate = pnull(_params, "edu_date_start");
        String edate = pnull(_params, "edu_date_end");
        String count = pnull(_params, "recruitment_personnel");
        String close_yn = pnull(_params, "close_yn", "N");
        String del_yn = pnull(_params, "del_yn", "N");
        String explain = pnull(_params, "explain").replaceAll("'", "").trim();
        String edu_guide = pnull(_params, "edu_guide").replaceAll("'", "").trim();
        String id = pnull((Map)_params.get("__SESSION_MAP__"), Const.SESSION_KEY_ADM_MEM_ID);
        String marryguide_no = pnull(_params, "marryguide_no");
        
        try {
        	query = "update "+TBL+" SET UPDATEDT=GETDATE() "
        			+ (sdate.length() != 8 ? "":", edu_date_start='"+sdate+"'" )
        			+ (edate.length() != 8 ? "":", edu_date_end='"+edate+"'" )
        			+ (count.length() == 0 ? "":", recruitment_personnel='"+count+"'" )
        			+ (close_yn.length() != 1 ? "":", close_yn='"+close_yn+"'" )
        			+ (del_yn.length() != 1 ? "":", del_yn='"+del_yn+"'" )
        			+ (id.length() == 0 ? "":", id='"+id+"'" )
        			+ (explain.length() == 0 ? "":", explain='"+explain+"'" )
        			+ (edu_guide.length() == 0 ? "":", edu_guide='"+edu_guide+"'" )
        			+ " where marry_guide_no="+marryguide_no+" and type='"+type+"'"
        			;
        	rtVal = super.executeUpdate(query);
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e );
        } finally {
        	free();
        }
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Return.[rtVal:"+rtVal+"]" );
        return rtVal;
	}
	
	/*
	 * 삭제하기 :: 삭제 플래그 Y
	 * (non-Javadoc)
	 * @see kr.caincheon.church.community.dao.CanaMarryGuideDao#delete(java.util.Map)
	 */
	@Override
	public int delete(Map _params) throws CommonException, Exception {
		D(_logger, Thread.currentThread().getStackTrace(), "DAO delete Called.[params:"+_params+"]" );
		int rtVal = 0;
        String query = "";

        String del_yn = pnull(_params, "del_yn", "Y");
        String id = pnull((Map)_params.get("__SESSION_MAP__"), Const.SESSION_KEY_ADM_MEM_ID);
        String marryguide_no = pnull(_params, "marryguide_no");
        
        try {
        	// 신청건이 없다면, 삭제
        	query = "SELECT COUNT(*) "
        			+ " FROM MARRY_APPLY A "
        			+ " JOIN MARRY_GUIDE B ON A.LECTURE_APPLY_DAY=CONVERT (DATETIME, B.EDU_DATE_START) AND MARRY_GUIDE_NO="+marryguide_no;
        	if(executeCount(query, false) > 0) {
        		throw new CommonException("기존에 신청 건수가 있어서 삭제 할 수 없습니다.");
        	}
        	
        	// 
        	query = "update "+TBL+" SET UPDATEDT=GETDATE() "
        			+ (del_yn.length() != 1 ? "":", del_yn='"+del_yn+"'" )
        			+ (id.length() == 0 ? "":", id='"+id+"'" )
        			+ " where marry_guide_no="+marryguide_no
        			;
        	rtVal = super.executeUpdate(query);
        } catch(Exception e) {
        	_E(_logger, Thread.currentThread().getStackTrace(), "ERROR SQL:"+query, e );
        } finally {
        	free();
        }
        
        D(_logger, Thread.currentThread().getStackTrace(), "DAO Return.[rtVal:"+rtVal+"]" );
        return rtVal;
	}
	

}
