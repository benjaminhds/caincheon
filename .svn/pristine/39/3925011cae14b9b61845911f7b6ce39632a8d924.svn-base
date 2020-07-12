<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<script type="text/javascript">
function attachedFile_Download(filePath, fileName, strfileName) {
	if(fileName=="" || strfileName=="") {
		alert("다운로드 할 파일이 없습니다.");
	} else {
		var vfm = document.attachedFileDown;
		vfm.filePath.value    = filePath;
		vfm.fileName.value    = fileName;
		vfm.strfileName.value = strfileName;
	    vfm.submit();
	    return false;
	}
}
</script>
<!-- unify search form --><!-- 
<form id="unifySearchForm" name="unifySearchForm" action="/search/unify_search.do">
<input type="hidden" name="srchText" value="">
</form> -->
<!-- attached file download -->
<form  name="attachedFileDown" action="/filedownload.jsp" method="POST" target="ifmAttachedFileDown">
<input type="hidden" name="fileName" id="fileName" value=""/>
<input type="hidden" name="filePath" id="filePath" value=""/>
<input type="hidden" name="strfileName" id="strfileName" value=""/>
</form>
<iframe name="ifmAttachedFileDown" id="ifmAttachedFileDown" width=0 height=0 src="about:blank"></iframe>
