package kr.caincheon.church.schedule;

public class GyoguScheduleUtil {

	
	/*
	 * 교구일정에서 구분코드를 TEXT로 리턴
	 * - 교구청 조직내 처실+국부과로 끝나는 부서들
	 * - 해당 컬럼은 GUBUN 
	 */
	public static String convert2GubunText(String code) {
		int i = Integer.parseInt(code);
		switch(i) {
		case 1:
			return "전체";
		case 2:
			return "교구장";
		case 3:
			return "총대리";
		case 4:
			return "교구";/* 교구청 조직내 처실+국부과로 끝나는 부서들, 늦은 요건 제공(12-19) */
		}
		return "부서";/* 교구청 조직내 교구장,총대리 이외에.... 주관부서 미지정(null)이면 교구입니다. 해당 컬럼은 GUBUN 임. */
	}
	
	/*
	 * 교구일정에서 TEXT를 구분코드로 리턴
	 * - 교구청 조직내 처실+국부과로 끝나는 부서들
	 * - 해당 컬럼은 GUBUN 
	 */
	public static String convert2GubunCode(String text) {
		if("전체".equals(text)) {
			return "1";
		} else if("교구장".equals(text)) {
			return "2";
		} else if("총대리".equals(text)) {
			return "3";
		} else if("부서".equals(text)) {
			return "4";
		} else {
			return "5";/* 교구청 조직내 교구장,총대리 이외에....  주관부서 미지정(null)이면 교구입니다. 해당 컬럼은 GUBUN 임. */
		}
	}
	
}
