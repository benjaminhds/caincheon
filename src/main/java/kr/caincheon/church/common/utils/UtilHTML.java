package kr.caincheon.church.common.utils;

/**
 * 문자열의 HTML 이나 HTML TAG를 다루는 유틸리티
 * @author benjamin
 */
public class UtilHTML
{
	
	/**
	 * 지정한 문자열에서 HTML TAG를 제거하고 리턴
	 * @param html
	 * @return String
	 */
    public static String removeHTML(String html)
    {
        return html.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
    }
    
}