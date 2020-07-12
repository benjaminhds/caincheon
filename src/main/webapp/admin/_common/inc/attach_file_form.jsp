<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!-- //파일 업로드 폼 시작 -->
	<script>
	window.onload = function() {
		// thumbnail :: handleMultiFileSelect() 함수 정의는 /admin/_common/js/admCommon.js 에 정의됨
		$("#thumbFile1").on("change", {maxWidth:200, maxHeight:150, imgPreviewId:"imagePreview1"},  handleMultiFileSelect);
		$("#thumbFile2").on("change", {maxWidth:200, maxHeight:150, imgPreviewId:"imagePreview2"},  handleMultiFileSelect);
		$("#thumbFile3").on("change", {maxWidth:200, maxHeight:150, imgPreviewId:"imagePreview3"},  handleMultiFileSelect);
		$("#thumbFile4").on("change", {maxWidth:200, maxHeight:150, imgPreviewId:"imagePreview4"},  handleMultiFileSelect);
		$("#thumbFile5").on("change", {maxWidth:200, maxHeight:150, imgPreviewId:"imagePreview5"},  handleMultiFileSelect);
	}
	</script>
	<table  border=0 width="100%">
		<tr style="vertical-align: top;">
			<td><div class="form-group input-group" style="width:100%; "><input class="form-control" type="file" name="thumbFile1" id="thumbFile1"></div>
			<div id="imagePreview1_Wrap" class="img_wrap hide"><p id="imagePreview1_tt">이미지 미리보기</p><p><img id="imagePreview1" /></p><p id="imagePreview1_info"></p></div>
			<c:if test="${fn:length(CONTENTS.STRFILENAME1) > 0 }">
				<c:choose><c:when test="${fn:indexOf(CONTENTS.STRFILENAME1, 'jpg')>-1 || fn:indexOf(CONTENTS.STRFILENAME1, 'png')>-1 || fn:indexOf(CONTENTS.STRFILENAME1, 'jpeg')>-1 || fn:indexOf(CONTENTS.STRFILENAME1, 'gif')>-1
							|| fn:indexOf(CONTENTS.STRFILENAME1, 'JPG')>-1 || fn:indexOf(CONTENTS.STRFILENAME1, 'PNG')>-1 || fn:indexOf(CONTENTS.STRFILENAME1, 'JPEG')>-1 || fn:indexOf(CONTENTS.STRFILENAME1, 'GIF')>-1}">
					<img src="${CONTENTS.FILEPATH1}thumbnail/${CONTENTS.STRFILENAME1}" width="150" />
				</c:when><c:otherwise>${CONTENTS.FILENAME1}</c:otherwise></c:choose><p>(<label class="checkbox-inline" for=""><input 
					type="checkbox" name="delFile1" id="delFile1" value="${CONTENTS.STRFILENAME1}">이전파일 삭제</label>)</p></c:if></td>
		
			<td><div class="form-group input-group" style="width:100%; "><input class="form-control" type="file" name="thumbFile2" id="thumbFile2"></div>
			<div id="imagePreview2_Wrap" class="img_wrap hide"><p id="imagePreview2_tt">이미지 미리보기</p><p><img id="imagePreview2" /></p><p id="imagePreview2_info"></p></div>
			<c:if test="${fn:length(CONTENTS.STRFILENAME2) > 0 }">
				<c:choose><c:when test="${fn:indexOf(CONTENTS.STRFILENAME2, 'jpg')>-1 || fn:indexOf(CONTENTS.STRFILENAME2, 'png')>-1 || fn:indexOf(CONTENTS.STRFILENAME2, 'jpeg')>-1 || fn:indexOf(CONTENTS.STRFILENAME2, 'gif')>-1 
							|| fn:indexOf(CONTENTS.STRFILENAME2, 'JPG')>-1 || fn:indexOf(CONTENTS.STRFILENAME2, 'PNG')>-1 || fn:indexOf(CONTENTS.STRFILENAME2, 'JPEG')>-1 || fn:indexOf(CONTENTS.STRFILENAME2, 'GIF')>-1}">
					<img src="${CONTENTS.FILEPATH2}thumbnail/${CONTENTS.STRFILENAME2}" width="150" />
				</c:when><c:otherwise>${CONTENTS.FILENAME2}</c:otherwise></c:choose><p>(<label class="checkbox-inline" for=""><input 
					type="checkbox" name="delFile2" id="delFile2" value="${CONTENTS.STRSTRFILENAME2}">이전파일 삭제</label>)</p></c:if></td>
		</tr>
		
		<tr style="vertical-align: top;">
			<td><div class="form-group input-group" style="width:100%; "><input class="form-control" type="file" name="thumbFile3" id="thumbFile3"></div>
			<div id="imagePreview3_Wrap" class="img_wrap hide"><p id="imagePreview3_tt">이미지 미리보기</p><p><img id="imagePreview3" /></p><p id="imagePreview3_info"></p></div>
			<c:if test="${fn:length(CONTENTS.STRFILENAME3) > 0 }">
				<c:choose><c:when test="${fn:indexOf(CONTENTS.STRFILENAME3, 'jpg')>-1 || fn:indexOf(CONTENTS.STRFILENAME3, 'png')>-1 || fn:indexOf(CONTENTS.STRFILENAME3, 'jpeg')>-1 || fn:indexOf(CONTENTS.STRFILENAME3, 'gif')>-1 
							|| fn:indexOf(CONTENTS.STRFILENAME3, 'JPG')>-1 || fn:indexOf(CONTENTS.STRFILENAME3, 'PNG')>-1 || fn:indexOf(CONTENTS.STRFILENAME3, 'JPEG')>-1 || fn:indexOf(CONTENTS.STRFILENAME3, 'GIF')>-1}">
					<img src="${CONTENTS.FILEPATH3}thumbnail/${CONTENTS.STRFILENAME3}" width="150" />
				</c:when><c:otherwise>${CONTENTS.FILENAME3}</c:otherwise></c:choose><p>(<label class="checkbox-inline" for=""><input 
					type="checkbox" name="delFile3" id="delFile3" value="${CONTENTS.STRFILENAME3}">이전파일 삭제</label>)</p></c:if></td>
		
			<td><div class="form-group input-group" style="width:100%; "><input class="form-control" type="file" name="thumbFile4" id="thumbFile4"></div>
			<div id="imagePreview4_Wrap" class="img_wrap hide"><p id="imagePreview4_tt">이미지 미리보기</p><p><img id="imagePreview4" /></p><p id="imagePreview4_info"></p></div>
			<c:if test="${fn:length(CONTENTS.STRFILENAME4) > 0 }">
				<c:choose><c:when test="${fn:indexOf(CONTENTS.STRFILENAME4, 'jpg')>-1 || fn:indexOf(CONTENTS.STRFILENAME4, 'png')>-1 || fn:indexOf(CONTENTS.STRFILENAME4, 'jpeg')>-1 || fn:indexOf(CONTENTS.STRFILENAME4, 'gif')>-1 
							|| fn:indexOf(CONTENTS.STRFILENAME4, 'JPG')>-1 || fn:indexOf(CONTENTS.STRFILENAME4, 'PNG')>-1 || fn:indexOf(CONTENTS.STRFILENAME4, 'JPEG')>-1 || fn:indexOf(CONTENTS.STRFILENAME4, 'GIF')>-1}">
					<img src="${CONTENTS.FILEPATH4}thumbnail/${CONTENTS.STRFILENAME4}" width="150" />
				</c:when><c:otherwise>${CONTENTS.FILENAME4}</c:otherwise></c:choose><p>(<label class="checkbox-inline" for=""><input 
					type="checkbox" name="delFile4" id="delFile4" value="${CONTENTS.STRFILENAME4}">이전파일 삭제</label>)</p></c:if></td>
		</tr>
		
		<tr style="vertical-align: top;">
			<td><div class="form-group input-group" style="width:100%; "><input class="form-control" type="file" name="thumbFile5" id="thumbFile5"></div>
			<div id="imagePreview5_Wrap" class="img_wrap hide"><p id="imagePreview5_tt">이미지 미리보기</p><p><img id="imagePreview5" /></p><p id="imagePreview5_info"></p></div>
			<c:if test="${fn:length(CONTENTS.STRFILENAME5) > 0 }">
				<c:choose><c:when test="${fn:indexOf(CONTENTS.STRFILENAME5, 'jpg')>-1 || fn:indexOf(CONTENTS.STRFILENAME5, 'png')>-1 || fn:indexOf(CONTENTS.STRFILENAME5, 'jpeg')>-1 || fn:indexOf(CONTENTS.STRFILENAME5, 'gif')>-1 
							|| fn:indexOf(CONTENTS.STRFILENAME5, 'JPG')>-1 || fn:indexOf(CONTENTS.STRFILENAME5, 'PNG')>-1 || fn:indexOf(CONTENTS.STRFILENAME5, 'JPEG')>-1 || fn:indexOf(CONTENTS.STRFILENAME5, 'GIF')>-1}">
					<img src="${CONTENTS.FILEPATH5}thumbnail/${CONTENTS.STRFILENAME5}" width="150" />
				</c:when><c:otherwise>${CONTENTS.FILENAME5}</c:otherwise></c:choose><p>(<label class="checkbox-inline" for=""><input 
					type="checkbox" name="delFile5" id="delFile5" value="${CONTENTS.STRFILENAME5}">이전파일 삭제</label>)</p></c:if></td>
			
			<td><div class="form-group input-group" style="width:100%; "></div></td>
			
		</tr>
	</table>
<!-- //파일 업로드 폼 끝 -->