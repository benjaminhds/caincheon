<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="/_common/inc/head_const.jsp"%>
<style>
/* .mainLayer{position:absolute;  display:block; width:100%; height:100%; left:0; top:0;} */
.mainLayer{position:absolute; z-index:999999; width:100%;  left:0; top:0; display:none;}
.mainLayer .layer{position:fixed;z-index:9995;width:1200px; top:180px; left:50%; margin-left:-600px; opacity:1; display:block; text-align:center; font-size:0; } 
.mainLayer .layer .layerCont{display:inline-block; vertical-align:top; width:290px;padding:10px 10px 0;background-color:#2368ad; position:relative; transition:all .1s; margin:0 5px; display:none;}
.mainLayer .layer .layerCont.one{width:auto; max-width:590px; }
.mainLayer .layer .layerCont.two{width:auto; max-width:490px; }
.mainLayer .layer .layerCont.three{width:auto; max-width:390px; }
.mainLayer .layer .layerCont.four{width:auto; max-width:290px; }

/* img 100percent  */
.mainLayer .layer .layerCont > a{display:block; width:100%; height:100%;}
.mainLayer .layer .layerCont img{display:block; width:100%;  border-radius:3px;}
/* layerBtm */
.layerBtm{width:100%; height: 30px; position: absolute; bottom: -30px; text-align:right; background-color:#2368ad; left:0;}
.layerBtm button{color:#fff; font-weight:bold; font-size:12px; letter-spacing:-0.4px; background:none; display:inline-block; vertical-align:middle; margin:5px;}
.layerBtm a{color:#fff; font-weight:bold; font-size:12px; letter-spacing:-0.4px; display:inline-block; vertical-align:middle; margin:8px 12px 5px 5px;}

@media screen and (max-width:1220px){
.mainLayer .layer{width:600px; margin-left:-300px; position:absolute;}
.mainLayer .layer .layerCont:nth-child(2) ~ .layerCont{margin-top:40px;}
}

@media screen and (max-width:640px){
.mainLayer .layer{top:90px}
.mainLayer .layer{width:300px; margin-left:-150px;}
.mainLayer .layer .layerCont ~ .layerCont{margin-top:35px;}
}
  </style>
<c:if test="${fn:length(popupList) > 0}">
    <c:forEach items="${popupList}" var="list" varStatus="status">
    <c:if test="${list.POPUP_TYPE == 2}">
      <script>
      var param${status.count} = '${fn:replace(list.CONTENTS, cn, "")}';
      param${status.count} = param${status.count}.replace("\"", "");
      param${status.count} = encodeURI(param${status.count});
      window.open("pop/popupWin.jsp?CONTENTS=" + param${status.count},"mainPop${status.count}","width=${list.WINDOW_SIZE_WIDTH}, height=${list.WINDOW_SIZE_HEIGHT}, resizable=no, scrollbars=no, status=no"); 
      </script>
      </c:if>
    </c:forEach>
</c:if>
  <!-- [popup] mainLayer :: pc popup layer, POPUP_TYPE : 1(레이어팝업), 2(윈도우팝업) -->
  <div class="mainLayer">
    <div class="layer">
    
    <c:if test="${fn:length(popupList) > 0}">
    <c:forEach items="${popupList}" var="list" varStatus="status">
      <c:if test="${list.POPUP_TYPE == 1 or list.POPUP_TYPE == 2}">
      <div class="layerCont layerCont${status.count}">
        ${list.CONTENTS }
        <div class="layerBtm">
          <button type="button">오늘 하루 보지 않기</button>
          <a href="#none" class="btClose">닫기 X</a>
        </div> 
      </div>
      </c:if>
    </c:forEach>
    </c:if>
      <!-- 
      <div class="layerCont layerCont2">
        <a href="http://www.naver.com" target="_blank" title="새창"><img src="../img/popup.jpg" alt="샘플이미지"></a>
        <div class="layerBtm">
          <button type="button">오늘 하루 보지 않기</button>
          <a href="#none" class="btClose">닫기 X</a>
        </div>
      </div>  
      <div class="layerCont layerCont3">
        <a href="http://www.naver.com" target="_blank" title="새창"><img src="../img/popup.jpg" alt="샘플이미지"></a>
        <div class="layerBtm">
          <button type="button">오늘 하루 보지 않기</button>
          <a href="#none" class="btClose">닫기 X</a>
        </div>    
      </div> 
      <div class="layerCont layerCont4">
        <a href="http://www.naver.com" target="_blank" title="새창"><img src="../img/popup.jpg" alt="샘플이미지"></a>
        <div class="layerBtm">
          <button type="button">오늘 하루 보지 않기</button>
          <a href="#none" class="btClose">닫기 X</a>
        </div>    
      </div> 
       -->
    </div>
  </div>
<!-- //mainLayer -->

<script type="text/javascript">
$(document).ready(function() {
   
    $('.mainLayer').stop().show(0); 
    // mainLayer Cookie
    cookiedata = document.cookie;
    if (cookiedata.indexOf("wcookie=done") < 0) {
        $('.layerCont1').stop().fadeIn(0).css('display','inline-block');
    } else {
        $('.layerCont1').stop().fadeOut(0).css('display','none')
        
    }
    // mainLayer Cookie
    cookiedata = document.cookie;
    if (cookiedata.indexOf("kcookie=done") < 0) {
        $('.layerCont2').stop().fadeIn(0).css('display','inline-block');
    } else {
        $('.layerCont2').stop().fadeOut(0).css('display','none')
        
    }
    // mainLayer Cookie
    cookiedata = document.cookie;
    if (cookiedata.indexOf("dcookie=done") < 0) {
        $('.layerCont3').stop().fadeIn(0).css('display','inline-block');
    } else {
        $('.layerCont3').stop().fadeOut(0).css('display','none')
        
    }
    // mainLayer Cookie
    cookiedata = document.cookie;
    if (cookiedata.indexOf("scookie=done") < 0) {
        $('.layerCont4').stop().fadeIn(0).css('display','inline-block');
    } else {
        $('.layerCont4').stop().fadeOut(0).css('display','none')
        
    }
    
    popEa();
    
    $('.btClose').on('click', function(e) {
        e.preventDefault()
        $(this).parent().parent('.layerCont').css('display','none')
          popEa();

    });
    $('.layerCont1 .layerBtm button').on('click', function(e) {
        e.preventDefault()
        setCookie("wcookie", "done", 24);
        $(this).parent().parent('.layerCont1').css('display','none')

    });
    $('.layerCont2 .layerBtm button').on('click', function(e) {
        e.preventDefault()
        setCookie("kcookie", "done", 24);
        $(this).parent().parent('.layerCont2').css('display','none')


    });
    $('.layerCont3 .layerBtm button').on('click', function(e) {
        e.preventDefault()
        setCookie("dcookie", "done", 24);
        $(this).parent().parent('.layerCont3').css('display','none')


    });
    $('.layerCont4 .layerBtm button').on('click', function(e) {
        e.preventDefault()
        setCookie("scookie", "done", 24);
        $(this).parent().parent('.layerCont4').css('display','none')

    });
});
//document-ready end 
//개수 마다 사이즈 조절
 function popEa() {
    if ($('.layerCont:visible').length < 2 ) {
        $('.layerCont').removeClass('one two three four').addClass('one');
    } else if ($('.layerCont:visible').length < 3) {
        $('.layerCont').removeClass('one two three four').addClass('two');
    } else if ($('.layerCont:visible').length < 4) {
        $('.layerCont').removeClass('one two three four').addClass('three');
    } else if ($('.layerCont:visible').length < 5) {
        $('.layerCont').removeClass('one two three four').addClass('four');
    }
  };


// Popup Cookie
function setCookie(name, value, expirehours) {
    var todayDate = new Date();
    todayDate.setHours(todayDate.getHours() + expirehours);
    document.cookie = name + "=" + escape(value) + "; path=/; expires=" + todayDate.toGMTString() + ";"
    console.log(document.cookie);
}
</script>
