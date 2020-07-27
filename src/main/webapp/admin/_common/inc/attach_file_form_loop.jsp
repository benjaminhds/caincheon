<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!-- //파일 업로드 폼 시작 -->

	<table border="0" bordercolor="red" width="100%" id="attached_file_table_loop_xxx">
		
		<c:forEach var="i" begin="1" end="${HEAD_CONST__attach_file_form__MAX_FILE_COUNT}" varStatus="status">
		
		<c:if test="${ i % 2 == 1 }">
		<tr style="vertical-align: top; font-size:14px">
		</c:if>
			
			<% /* 동적변수 할당 */ %>
			<c:set var="XFILENAME"    value="FILENAME${i}"    scope="page" />
			<c:set var="XFILEPATH"    value="FILEPATH${i}"    scope="page" />
			<c:set var="XSTRFILENAME" value="STRFILENAME${i}" scope="page" />
			
			<% /* 동적변수로 값을 추출 :: requestScope[동적변수] 로 추출 , MAP에서는 맵[변수] 로 추출한다. */ %>
			<td>
			<span style="width:100%; ">
				<input class="form-control" type="file" name="thumbFile${i}" id="thumbFile${i}">
			</span>
			<div id="imagePreview${i}_Wrap" ><p id="imagePreview${i}_tt">신규 이미지</p><p><img id="imagePreview${i}" /></p><p id="imagePreview${i}_info"></p></div>
			<c:if test="${fn:length(CONTENTS[XSTRFILENAME]) > 0 }">
				<span><p>(<label class="checkbox-inline" for=""><input type="checkbox" name="delFile${i}" id="delFile${i}" value="${CONTENTS[XSTRFILENAME]}">이전파일 삭제</label>)</p></span>
				<span>
				<c:choose>
				<c:when test="${fn:indexOf(CONTENTS[XSTRFILENAME], 'jpg')>-1 || fn:indexOf(CONTENTS[XSTRFILENAME], 'png')>-1 || fn:indexOf(CONTENTS[XSTRFILENAME], 'jpeg')>-1 || fn:indexOf(CONTENTS[XSTRFILENAME], 'gif')>-1
							|| fn:indexOf(CONTENTS[XSTRFILENAME], 'JPG')>-1 || fn:indexOf(CONTENTS[XSTRFILENAME], 'PNG')>-1 || fn:indexOf(CONTENTS[XSTRFILENAME], 'JPEG')>-1 || fn:indexOf(CONTENTS[XSTRFILENAME], 'GIF')>-1}">
					<img src="${CONTENTS[XFILEPATH]}thumbnail/${CONTENTS[XSTRFILENAME]}" width="150" />
				</c:when><c:otherwise>${CONTENTS[XFILENAME]}</c:otherwise>
				</c:choose>
				</span>
			</c:if></td>
			
			<c:if test="${ i == HEAD_CONST__attach_file_form__MAX_FILE_COUNT }">
				<td>&nbsp;</td>
			</c:if>
		
		<c:if test="${ i > 1 and i % 2 == 0 }">	
		</tr>
		</c:if>
		<c:if test="${ i == HEAD_CONST__attach_file_form__MAX_FILE_COUNT }">	
		</tr>
		</c:if>
		
		</c:forEach>
		
	</table>

	<script>
	
	//$( ":not(#attached_file_table_loop_xxx)" ).css( "font-size", "14px" );
	
	//window.onload = function() 
	{
		// thumbnail :: handleMultiFileSelect() 함수 정의는 /admin/_common/js/admCommon.js 에 정의됨
		<c:forEach var="i" begin="1" end="${HEAD_CONST__attach_file_form__MAX_FILE_COUNT}" varStatus="status">
		$("#thumbFile${i}").on("change", {maxWidth:200, maxHeight:150, imgPreviewId:"imagePreview${i}", imgOnly:"N"},  handleMultiFileSelect );
		</c:forEach>
	}
	</script>
<!-- //파일 업로드 폼 끝 -->