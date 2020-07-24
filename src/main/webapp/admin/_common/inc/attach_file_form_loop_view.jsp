<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!-- //파일 업로드 폼 시작 -->
	<table  border=0 bordercolor="red" width="100%" id="attached_file_table_view_xxx">
		
		<c:forEach var="i" begin="1" end="${HEAD_CONST__attach_file_form__MAX_FILE_COUNT}" varStatus="status">
		
		<c:if test="${ i % 2 == 1 }">
			<tr style="vertical-align: top; font-size:14px">
		</c:if>
			
			<% /* 동적변수로 값을 추출 :: requestScope[동적변수] 로 추출 , MAP에서는 맵[변수] 로 추출한다. */ %>
			<td><div style="width:100%; ">
			
			<c:if test="${fn:length(attachList[i].STRFILENAME) > 0 }">
				<c:choose><c:when test="${fn:indexOf(attachList[i].STRFILENAME, 'jpg')>-1 || fn:indexOf(attachList[i].STRFILENAME, 'png')>-1 || fn:indexOf(attachList[i].STRFILENAME, 'jpeg')>-1 || fn:indexOf(attachList[i].STRFILENAME, 'gif')>-1
							|| fn:indexOf(attachList[i].STRFILENAME, 'JPG')>-1 || fn:indexOf(attachList[i].STRFILENAME, 'PNG')>-1 || fn:indexOf(attachList[i].STRFILENAME, 'JPEG')>-1 || fn:indexOf(attachList[i].STRFILENAME, 'GIF')>-1}">
					<a href="javascript: attachedFile_Download ('${attachList[i].FILEPATH}', '${attachList[i].FILENAME}', '${attachList[i].STRFILENAME}');"><img 
						src="${attachList[i].FILEPATH}thumbnail/${attachList[i].STRFILENAME}" id="contents_thumbnail${i}" /></a>(<a href="#contents_thumbnail_alink${i}" id="contents_thumbnail_alink${i}" onclick="chnageOriginalImg('contents_thumbnail${i}', ${i} )">원본보기</a>)
				</c:when><c:otherwise><a href="#none" 
							onClick="javascript: attachedFile_Download('${attachList[i].FILEPATH}', '${fn:replace(attachList[i].FILENAME,'\'','')}', '${attachList[i].STRFILENAME}')"
							>${attachList[i].FILENAME}</a></c:otherwise>
				</c:choose></c:if></td>
			
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
	//$( ":not(#attached_file_table_view_xxx)" ).css( "font-size", "14px" );
	function chnageOriginalImg(imgId, seq)
	{
		var imgobj = $("#"+imgId);
		var srcUri = imgobj.attr("src");
		if(srcUri.indexOf("thumbnail/")>-1) {
			imgobj.attr("src", srcUri.replace("thumbnail/",""));
			//imgobj.attr("width", "800");
			$("#contents_thumbnail_alink"+seq).text("썸네일보기");
		} else {
			srcUri = srcUri.substring(0, srcUri.lastIndexOf("/")+1) + "thumbnail/" + srcUri.substring(srcUri.lastIndexOf("/")+1); 
			imgobj.attr("src", srcUri);
			//imgobj.removeAttr("width");
			$("#contents_thumbnail_alink"+seq).text("원본보기");
		}
	}
	</script>

<!-- //파일 업로드 폼 끝 -->