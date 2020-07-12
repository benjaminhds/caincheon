<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/admin/_common/inc/head.jsp" %>

<%
if(admSessionMemId==null||admSessionMemId.equals("")) {                // id가 Null 이거나 없을 경우
	response.sendRedirect("/admin/login.jsp");    // 로그인 페이지로 리다이렉트 한다.
}
%>

<script src="/_common/js/audio.min.js"></script>
<script type="text/javascript">
function goPdf(file) {
	//var fff = "/img/magazine/upload/pdf/" + file;
	var fff = file;
	window.open(fff, "_new");
}

function goPage(pageNo) {
	document.getElementById('pageNo').value=pageNo;
	document.form01.submit();
    return false;
}

function goSearch() {
	if(document.getElementById('searchText').value == '') {
		//alert('검색할 문자를 입력하세요.');
		//document.getElementById('searchText').focus();
		//return false;
	}
	document.form01.submit();
    return false;
}

function insertContents() {
	document.form01.action = '/admin/board/mgz_view.do';
	document.getElementById('query_type').value = "insert";
	document.form01.submit();
    return false;
}

function modifyContents(m_idx) {
	//alert("modifyContents");
	document.form01.action = '/admin/board/mgz_view.do';
	document.getElementById('query_type').value = "modify";
	document.getElementById('m_idx').value = m_idx;
	document.form01.submit();
    return false;
}

function deleteContents(m_idx) {
	//alert("deleteContents");
	if (confirm("정말 삭제하시겠습니까??") == true){    //확인
		document.form01.action = '/admin/board/mgz_delete.do';
		document.getElementById('m_idx').value=m_idx;
		document.form01.submit();
	}
    return false;
}

$(document).ready(function(){
    
    // Setup the player to autoplay the next track
    var a = audiojs.createAll({
        trackEnded: function() {
            var next = $('.playList span.playing').parents('tr').next();
            if (!next.length) next = $('.playList span.list').first();
                next.addClass('playing').siblings().removeClass('playing');
                audio.load($('a', next).attr('data-src'));
                audio.play();
        }
    });

    // Load in the first track
    var audio = a[0];
    first = $('.playList a').attr('data-src');
    $('.playList span').first().addClass('playing');
    audio.load(first);
   // audio.play();
    var txts = $('span.playing').parent('td').siblings('.tit').text();
    $('.play-txt').text(txts);

    // Load in a track on click
    $('.playList span.list').click(function(e) {
        e.preventDefault();
        $(this).addClass('playing').parents('tr').siblings().find('.list').removeClass('playing');
        audio.load($('a', this).attr('data-src'));
        audio.play();
        var txts = $('span.playing').parent('td').siblings('.tit').text();
        $('.play-txt').empty();
        $('.play-txt').text(txts)
        $('.error-message').empty()
    });
    

    // click Custom
    $('.btPlay').on('click', function(e){e.preventDefault();
        audio.play();})
    $('.btPause').on('click', function(e){e.preventDefault();
        audio.pause();})
    $('.btStop').on('click', function(e){e.preventDefault();
        audio.stop();})
    $('.btNext').on('click', function(e){e.preventDefault();var next = $('span.playing').parents('tr').next().find('span.list');
         if (!next.length) next = $('.playList span.list').first();
        next.click();})
    $('.btPrev').on('click', function(e){e.preventDefault();
        var prev = $('span.playing').parents('tr').prev().find('span.list');if (!prev.length) prev = $('.playList span.list').last();
        prev.click();
        // spacebar
        })


    // Keyboard shortcuts
    $(document).keydown(function(e) {
        var unicode = e.charCode ? e.charCode : e.keyCode;
        // right arrow
        if (unicode == 39) {
        var next = $('span.playing').parents('tr').next().find('span.list');
        if (!next.length) next = $('.playList span.list').first();
        next.click();
        // back arrow
        } else if (unicode == 37) {
        var prev = $('span.playing').parents('tr').prev().find('span.list');
        if (!prev.length) prev = $('.playList span.list').last();
        prev.click();
        // spacebar
        } else if (unicode == 32) {
        audio.playPause();
         return false;
        }
    })
});
</script>

<body>
<form class="form-inline" name="form01" action="/admin/board/mgz_list.do" method="POST">
<input type="hidden" name="query_type" id="query_type" value="" />
<input type="hidden" name="m_idx" id="m_idx" value=""/>
<input type="hidden" name="pageNo" id="pageNo" value=""/>

    <div class="container" id="wrapper">
        <!-- Navigation -->
        <%@ include file="/admin/_common/inc/top.jsp" %>
        <!-- Page Content -->
        
        <div id="page-wrapper">
        	<div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header">간행물 관리 목록</h3>
                </div>
                <!-- /.col-lg-12 -->
            </div>
	        
	        <div class="row">	
            <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">		  
						  <div class="form-group">			
						  	<table style="width:100%" border="0">
						  		<tr>
								    <th width="50"><label>검색</label></th>
								    <th><select name="searchGubun" id="searchGubun">
										  <option value="DESCRIPTION">제목</option>
										  <option value="NO">발행호</option>
										</select>
									</th>
									<th>
										<input type="text" name="searchText" id="searchText" value="${_params.searchText}">
									</th>
									<th>
										<button type="button" onclick="javascript:goSearch();return false;">검색</button>
									</th>
								</tr>
							    <tr>
							    	<td></td>
							    	<td></td>
							    	<td></td>
							    	<td><button type="button" onclick="javascript:insertContents();return false;">등록</button>
							    	</td>
							    </tr>
						  	</table>
						  			  	
						    
						  </div>
						</div>
            		<!-- /.row -->
			            <div class="panel-body">
			              <table width="100%" class="table" id="dataTables-example">
						    <thead>
						      <tr>
						        <th>NO</th>
						        <th>발행호</th>
						        <th>제목</th>
						        <th>발행일</th>
						        <th>PDF</th>
						        <th>소리주보</th>						        		        
						        <th>수정</th>
						        <th>삭제</th>
						      </tr>
						    </thead>
						    <tbody>
						      	<c:choose>
									<c:when test="${fn:length(mgzList) > 0}">
									<c:forEach items="${mgzList}" var="list">
			                            <tr>
			                            	<td>${list.RNUM} </td>
			                                <td>통권 ${list.NO} </td>
			                                <td>${list.DESCRIPTION} </td>
			                                <td>${list.PUBDATE }</td>
			                                <td>
		                                        <button type="button" onclick="javascript: goPdf('${list.PDF}')" class="pdf" ><img src="/img/sub/_ico/pdf.png" alt="pdf보기"></button>
		                                    </td>
		                                    <td>
		                                        <span class="list">
		                                        
		                                        <c:set value="" var="tmp_music_uri" />
		                                        <c:choose>
		                                        	<c:when test="${fn:length(list.MP3) == 0}"><c:set value="/_common/sound/${list.MP3FILE}.mp3" var="tmp_music_uri" /></c:when>
		                                        	<c:otherwise><c:set value="${list.MP3}" var="tmp_music_uri" /></c:otherwise>
		                                        </c:choose>
 		                                        	
		                                        	<a href="#none" data-src="${tmp_music_uri}" class="snd"><img src="/img/sub/_ico/play.png" alt=""></a>
		                                        </span>
		                                    </td>
		                                    <td><a href="javascript:modifyContents('${list.M_IDX}')">수정</a></td>
											<td><a href="javascript:deleteContents('${list.M_IDX}')">삭제</a></td>
			                                
										</tr>
									</c:forEach>
									</c:when>
								</c:choose>		      
						    </tbody>
						  </table>			
						  <div>
			                  <!-- arrow -->
			                  <%@ include file="/_common/inc/paging2.jsp" %>
			      			<!-- //arrow -->                                
			              </div>  
						</div>	
						<!-- /.panel-body -->
				</div>
				<!-- panel panel-default -->
			</div>
			<!-- col-lg-12 -->
		</div>	
		</div>
	</div>
			
</form>
</body>

</html>
