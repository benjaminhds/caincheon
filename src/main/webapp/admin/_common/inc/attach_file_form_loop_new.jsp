<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!-- //파일 업로드 폼 시작 -->

	<table border="0" bordercolor="red" width="100%" id="attached_file_table_loop_xxx">
		<c:forEach items="${attachList}" var="list" varStatus="status">
			<c:if test="${ status.count % 2 == 1 }">
				<tr style="vertical-align: top; font-size:14px">
			</c:if> 
		
			<span style="width:100%; ">
				<input class="form-control" type="file" name="thumbFile${status.count}" id="thumbFile${status.count}">
			</span>
			<div id="imagePreview${status.count}_Wrap" ><p id="imagePreview${status.count}_tt">신규 이미지</p><p><img id="imagePreview${status.count}" /></p><p id="imagePreview${status.count}_info"></p></div>
			<span><p>(<label class="checkbox-inline" for=""><input type="checkbox" name="delFile${status.count}" id="delFile${status.count}" value="${list.STRFILENAME}">이전파일 삭제</label>)</p></span>
					<span>
					<c:choose>
						<c:when test="${
										fn:indexOf(list.STRFILENAME, 	'jpg')>-1
										|| fn:indexOf(list.STRFILENAME, 'png')>-1 
										|| fn:indexOf(list.STRFILENAME, 'jpeg')>-1
										|| fn:indexOf(list.STRFILENAME, 'gif')>-1
										|| fn:indexOf(list.STRFILENAME, 'JPG')>-1
										|| fn:indexOf(list.STRFILENAME, 'PNG')>-1
										|| fn:indexOf(list.STRFILENAME, 'JPEG')>-1
										|| fn:indexOf(list.STRFILENAME, 'GIF')>-1
										}
										">
							<img src="${list.FILEPATH}thumbnail/${list.STRFILENAME}" width="150" />
						</c:when>
						<c:otherwise>${list.FILENAME}</c:otherwise>
					</c:choose>
					</span>
				<c:if test="${status.count == HEAD_CONST__attach_file_form__MAX_FILE_COUNT }">
				<td>&nbsp;</td>
				</c:if>
		
				<c:if test="${ status.count > 1 and status.count % 2 == 0 }">	
					</tr>
				</c:if>
				<c:if test="${ status.count == HEAD_CONST__attach_file_form__MAX_FILE_COUNT }">	
					</tr>
				</c:if>
		</c:forEach>
		
		<c:forEach var="i" begin="${fn:length(attachList) + 1}" end="${HEAD_CONST__attach_file_form__MAX_FILE_COUNT}" varStatus="status">
		
			<c:if test="${ i % 2 == 1 }">
				<tr style="vertical-align: top; font-size:14px">
			</c:if> 
			
			<% /* 동적변수로 값을 추출 :: requestScope[동적변수] 로 추출 , MAP에서는 맵[변수] 로 추출한다. */ %>
			<td>
				<span style="width:100%; ">
					<input class="form-control" type="file" name="thumbFile${i}" id="thumbFile${i}">
				</span>
				<div id="imagePreview${i}_Wrap" ><p id="imagePreview${i}_tt">신규 이미지</p><p><img id="imagePreview${i}" /></p><p id="imagePreview${i}_info"></p></div>
			</td>
			
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