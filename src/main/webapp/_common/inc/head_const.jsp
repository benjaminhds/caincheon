<%
/* 
 * 전체 공통 변수 정의 방법
 *
 *  변수명) {변수선언파일}__{주사용파일명}__대문자영문 = 초기값;
 *  사용법) /admin/_common/inc/attach_file_form.jsp 에서 사용하는 변수명 : attach_file_form__MAX_FILE_COUNT
 *  적용예) /church/temp_02_view.jsp 에서  attach_file_form__MAX_FILE_COUNT = 10; 으로 설정하여 사용
 *
 */
 

//default attachable file count
pageContext.setAttribute("HEAD_CONST__attach_file_form__MAX_FILE_COUNT", 5); // for jstl 구문 사용을 위해서 context에 변수를 세팅한다.


// 
// 치환 변수 선언
// Usage) ${fn:replace(misaInfo.MNAME, cr, "<BR/>")}
pageContext.setAttribute("cr", "\r"); //Carriage Return
pageContext.setAttribute("cn", "\n"); //Enter
pageContext.setAttribute("crcn", "\r\n"); //Carriage Return, Enter
pageContext.setAttribute("br", "<br/>"); //br 태그
pageContext.setAttribute("brbr", "<br/><br/>"); //br 태그

pageContext.setAttribute("quotl", "&quot;"); // " 태그
pageContext.setAttribute("ltl", "&lt;"); // " 태그
pageContext.setAttribute("gtl", "&gt;"); // " 태그
pageContext.setAttribute("quotTAG", "\""); // " 태그
pageContext.setAttribute("ltTAG", "<"); // " 태그
pageContext.setAttribute("gtTAG", ">"); // " 태그



%>