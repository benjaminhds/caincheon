<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/_common/inc/headSub.jsp" %>
<script type="text/javascript">
function goPage(pageNo) {
	document.getElementById('pageNo').value=pageNo;
	document.form01.submit();
	return false;
}

function goSearch() {
	if(document.getElementById('searchText').value == '') {
		alert('검색할 내용을 입력하세요.');
		document.getElementById('searchText').focus();
		return false;
	}
	
	document.form01.submit();
	return false;
}

function viewMgz(idx) {
	document.form01.action = '/news/mgz_view.do';
	document.getElementById('idx').value=idx;
	document.form01.submit();
	return false;
}
function goPdf(file) {
	var fff = "/img/magazine/upload/pdf/" + file;
	window.open(file, "_new");
}

</script>

<body>
<form name="form01" action="/n/board/mgz_board_list.do" method="POST">
<input type="hidden" name="type" id="type" value="${type}"/>
<input type="hidden" name="m_idx" id="m_idx" value="${m_idx}"/>
<input type="hidden" name="pageNo" id="pageNo" value="${paging.pageNo}"/>

	<p class="skipNavi" tabindex="1">본문바로가기</p>
	<!-- wrap -->
	<div id="wrap">
		<!-- header -->
		<header>
			<%@include file="/_common/inc/gnb.jsp" %>
		</header>
		<!-- //header -->
		<!-- subVisual -->
		<section class="subVisual news">
			<h2 class="blind">중심내용</h2>
			<div class="visBg">bg</div>
			<div class="artWrap">
				<article> 
					<h3>인천주보</h3>
					<ul>
						<li>홈</li>
						<li>소식</li> 
						<li>인천주보</li>
					</ul>
				</article>
			</div>
		</section>
		<!-- //subVisual -->
		<!-- secWrap -->
		<div class="secWrap">
			<!-- sec01 -->
			<section class="sec01 fin">
				<!-- secCont -->
				<div class="secCont">
					<h3 class="ttl">소리주보</h3>
					<!-- playForm -->
					<div class="playForm">
						<audio preload ></audio>
						<!-- playButtons -->
						<ul class="playButtons">
							<li class="btPlay"><img src="/img/sub/_ico/music_play.gif" alt=""></li>
							<li class="btPause"><img src="/img/sub/_ico/music_pause.gif" alt=""></li>
							<li class="btStop"><img src="/img/sub/_ico/music_stop.gif" alt=""></li>
							<li class="btPrev"><img src="/img/sub/_ico/music_prev.gif" alt=""></li>
							<li class="btNext"><img src="/img/sub/_ico/music_next.gif" alt=""></li>
						</ul>
						<!-- //playButtons -->
						<!-- Jubo -->
						<dl class="jubo clear">
							<dt>광고접수 및 옛 주보보기</dt>
							<dd>
								<a href="http://home.caincheon.or.kr/hongbo" target="_blank" title="새창">광고접수</a>
								<a href="http://old.caincheon.or.kr/web/magazine/magazine_list.aspx?pub_idx=3" target="_blank" title="새창">옛 주보보기</a>
							</dd>
						</dl>						
						<!-- //Jubo -->
					</div>
					<!-- //playForm -->
					<h3 class="ttl">인천주보</h3>
					<div class="boardList oflow playList">
						<table>
							<caption>주보 리스트</caption>
							<colgroup>
								<col style="width:15%">
								<col>
								<col style="width:12%">
								<col style="width:12%">
								<col style="width:15%">
							</colgroup>
							<thead>
								<tr>
									<th scope="col">발행호</th>
									<th scope="col">제목</th>
									<th scope="col">PDF</th>
									<th scope="col">주보듣기</th>
									<th scope="col">발행일</th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
								<c:when test="${fn:length(mgzList) > 0}">
									<c:forEach items="${mgzList}" var="list">
										<tr>
											<td>제${list.NO}호</td> 
											<td class="tit">${list.DESCRIPTION }</td>
											<td>
												<button type="button" onclick="javascript: goPdf('${list.PDF}')" class="pdf" ><img src="/img/sub/_ico/pdf.png" alt="pdf보기"></button>
											</td>
											<td>
												<c:set value="" var="tmp_music_uri" />
												<c:choose>
													<c:when test="${fn:length(list.MP3) == 0}"><c:set value="/upload/magazine/sound/${list.MP3FILE}.mp3" var="tmp_music_uri" /></c:when>
													<c:otherwise><c:set value="${list.MP3}" var="tmp_music_uri" /></c:otherwise>
												</c:choose>
												<span class="list">
													<a href="#none" data-src="${tmp_music_uri}" class="snd"><img src="/img/sub/_ico/play.png" alt=""></a>
												</span>
											</td>
											<td>${list.PUBDATE}</td>
										</tr>
									</c:forEach>
								</c:when>
							</c:choose>  
							</tbody>
						</table>
					</div>
					<!-- arrow -->
					<%@ include file="/_common/inc/paging2.jsp" %>
					<!-- //arrow -->
				</div>
				<!-- //secCont -->
			</section>
			<!-- //sec01 -->
		</div>
		<!-- //secWrap -->
		<!-- footer -->
		<footer>
			<%@ include file="/_common/inc/footer.jsp" %>
		</footer>
		<!-- //footer -->
	</div>
	<!--// wrap -->
	<script src="/_common/js/audio.min.js"></script>
	<script>
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
				try {
					audio.play();
				} catch ( excpt ) {
					alert("소리 주보 파일이 존재 하지 않습니다.");
					return;
				}
				var txts = $('span.playing').parent('td').siblings('.tit').text();
				$('.play-txt').empty();
				$('.play-txt').text(txts)
				$('.error-message').empty()
			});
			

			// click Custom
			$('.btPlay').on('click', function(e){e.preventDefault();  audio.play(); }  );
			$('.btPause').on('click', function(e){e.preventDefault(); audio.pause();}  );
			$('.btStop').on('click', function(e){e.preventDefault();  audio.stop(); }  );
			$('.btNext').on('click', function(e){e.preventDefault();
				 var next = $('span.playing').parents('tr').next().find('span.list');
				 if (!next.length) next = $('.playList span.list').first();
				 next.click();
			});
			$('.btPrev').on('click', function(e){e.preventDefault();
				var prev = $('span.playing').parents('tr').prev().find('span.list');if (!prev.length) prev = $('.playList span.list').last();
				prev.click();
				// spacebar
			});


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
			});
		});
	</script>	
</body>
</html>