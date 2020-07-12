/**
 * 좌측 메뉴를 open 하도록 하는 함수
 * @param - <li data-pg="order_01">에서 data-pg
 */
function onLoadLeftMenu(pageName) {
	// 해당 메뉴 선택되게 
	$("#side-menu ul.nav > li[data-pg='"+pageName+"']").addClass("active");
	$("#side-menu ul.nav > li[data-pg='"+pageName+"'] > a").addClass("active");
	// 해당 카테고리 선택되게
	$("#side-menu ul.nav > li[data-pg='"+pageName+"']").parents("li").addClass("active");
	// 해당 카테고리 펼치기
	$("#side-menu ul.nav > li[data-pg='"+pageName+"']").parents("ul").addClass("in");
}

/**
 * 파일의 확장자 정보를 리턴 
 * @param File Prototype의 file의 type 속성
 */
function getFleType( selectedFileType ) {
	selectedFileType = selectedFileType.toLowerCase();
	if( selectedFileType.indexOf("zip") > 0 ) {
		return "ZIP";
	}
	if( selectedFileType.indexOf("compressed") > 0 ) {
		return "압축";
	}
	if( selectedFileType.indexOf("hwp") > 0 && selectedFileType.indexOf("haansoft") > 0 ) {
		return "HWP";
	}
	if( selectedFileType.indexOf("pptx") > 0 ) {
		return "PPTX";
	} else if( selectedFileType.indexOf("ppt") > 0 ) {
		return "PPT";
	}
	if( selectedFileType.indexOf("spreadsheetml.sheet") > 0 ) {
		return "XLSX";
	} else if( selectedFileType.indexOf("ms-excel") > 0 ) {
		return "XLS";
	} else if( selectedFileType.indexOf("excel") > 0 ) {
		return "EXCEL";
	} 
	if( selectedFileType.indexOf("docx") > 0 ) {
		return "DOCX";
	} else if( selectedFileType.indexOf("doc") > 0 ) {
		return "DOC";
	}
	return selectedFileType;
}

/**
 * 이미지업로드시 이미지를 선택하면, 미리 보기를 제공한다.
 * 
 * Usage example1) maxWidth으로 파라메터를 전달해서 이미지 리사이즈를 위한 값을 받는다.
 * $(document).ready(function(){  $("#input_img").on("change", {maxWidth:500, maxHeight:400, imgId:"imagePreview"}, handleImageFileSelect);  });
 * <span class="title">이미지 업로드</span><input type="file" id="input_img" />
 * <div class="img_wrap"><p>이미지 미리보기</p><img id="imagePreview" /></div><p id="imagePreview_info"></p>
 */
function handleImageFileSelect(e) {
	var files = e.target.files;
	var fileArr = Array.prototype.slice.call(files);
	var xW  = e.data.maxWidth;
	var xH  = e.data.maxHeight;
	var xId = e.data.imgPreviewId;
	fileArr.forEach(function(selectedFile){
		if( !selectedFile.type.match("image.*")) {
			alert("첨부는 이미지만 가능합니다.");
			return;
		}
		try { $("#"+xId+"_Wrap").show(); } catch ( e ) {}
		try { $("#"+xId+"_Wrap").removeClass("hide"); } catch ( e ) {}
		var fReader = new FileReader();
		fReader.onload = function(loadedfile) {
			$("#"+xId).attr("src", loadedfile.target.result);
			var img = new Image();
			img.onload = function() {
				var divId = "#"+xId;
				var naturalW = this.naturalWidth;
				var naturalH = this.naturalHeight;
				var xxW = 0, xxH = 0;
				if(this.naturalWidth > xW) {
					xxW = xW;
					xxH = parseInt( (this.naturalHeight * xW) / this.naturalWidth );
				} 
				if(this.naturalHeight > xH) {
					xxW = parseInt( (this.naturalWidth * xH) / this.naturalHeight );
					xxH = xH;
				}
				$(divId).attr("width",  xxW);
				$(divId).attr("height", xxH);

				try {
					$(divId+"_info").css("width", xxW);
					$(divId+"_info").css("text-align", "center");
					$(divId+"_info").css("font-size", "0.7em");
					$(divId+"_info").html("원본 ["+naturalW+"px, " +naturalH +"px], 썸네일["+xxW+"px, "+xxH+"px]" );
				} catch (e) {}
			}
			img.src = $("#"+xId).attr("src");
		}
		fReader.readAsDataURL(selectedFile);
	});
}

/**
 * 멀티 파일 업로드에서 공통함수
 * - handleImageFileSelect() 를 변형하여 이미지 외에도 다른 파일 업로드가 가능
 * - 업로드불가 파일들의 기본 검증 로직을 포함함 
 * - 이미지라면, 기본 리사이즈 미리보기를 제공
 * - 사용법은 handleImageFileSelect() 와 동일하나 마지막 function handle 만 다름
 * 
 * Usage example1) maxWidth으로 파라메터를 전달해서 이미지 리사이즈를 위한 값을 받는다.
 * $(document).ready(function(){  $("#input_img").on("change", {maxWidth:500, maxHeight:400, imgId:"imagePreview", imgOnly:"Y"}, handleMultiFileSelect);  });
 */
function handleMultiFileSelect(e) {
	e.preventDefault();
	// parameters
	var files = e.target.files;
	var xW  = e.data.maxWidth;
	var xH  = e.data.maxHeight;
	var xId  = e.data.imgPreviewId;
	var xImgOnly = false; // 이미지만 업로드 가능하도록
	try {
		xImgOnly = e.data.imgOnly == "Y" ? true : false; 
	} catch ( e ) {}
	//
	var fileArr = Array.prototype.slice.call(files);
	fileArr.forEach(function(selectedFile) {
		
		// 업로드 불가 체크
		var fileExt = selectedFile.name.substring(selectedFile.name.lastIndexOf(".")+1) ;
		var reg = /jsp|java|class|pl|cgi|php|php3|php4|asp|aspx|exe|com|bat|vbs|js/i;
		if(reg.test(fileExt) == true) {
			alert("첨부할 수 없는 파일입니다."); 
			$( "#"+(event.target || event.srcElement).id ).val("");// Empty a file form
			//$("#"+xId+"_Wrap").addClass("hide");
			$("#"+xId+"_info").val("");
			$("#"+xId).attr("src","");
			return ;
		}
		// 이미지 외 기타 파일이라면,,
		if( xImgOnly == true && !selectedFile.type.match("image.*")) {
			alert("이미지 파일만 첨부 할 수 있습니다.");
			$( "#"+(event.target || event.srcElement).id ).val("");// Empty a file form
			$("#"+xId+"_info").val("");
			$("#"+xId).attr("src","");
			return;
		} else if( !selectedFile.type.match("image.*")) {
			$("#"+xId+"_tt").html(getFleType(selectedFile.type)+" 파일입니다.");
			$("#"+xId).hide();
			$("#"+xId+"_info").hide();
			return;
		}
		// 이미지 미리 보기 처리
		$("#"+xId+"_Wrap").removeClass("hide");
		$("#"+xId+"_Wrap").show();
		$("#"+xId+"_info").html("Image Loading ....");
		
		//$("#"+xId+"_info").show();
		$("#"+xId).show();
		var fReader = new FileReader();
		fReader.onload = function(loadedfile) {
			$("#"+xId).attr("src", loadedfile.target.result);
			var img = new Image();
			img.onload = function() {
				var divId = "#"+xId;
				var naturalW = this.naturalWidth;
				var naturalH = this.naturalHeight;
				var xxW = 0, xxH = 0;
				if(this.naturalWidth > xW) {
					xxW = xW;
					xxH = parseInt( (this.naturalHeight * xW) / this.naturalWidth );
				} 
				if(this.naturalHeight > xH) {
					xxW = parseInt( (this.naturalWidth * xH) / this.naturalHeight );
					xxH = xH;
				}
				$(divId).attr("width",  xxW);
				$(divId).attr("height", xxH);

				try {
					$(divId+"_info").css("width", xxW);
					$(divId+"_info").css("text-align", "center");
					$(divId+"_info").css("font-size", "0.7em");
					$(divId+"_info").html("원본 ["+naturalW+"px, " +naturalH +"px], 썸네일["+xxW+"px, "+xxH+"px]" );
				} catch (e) {}
			}
			img.src = $("#"+xId).attr("src");
		}
		fReader.readAsDataURL(selectedFile);
	});
}


// `/admin/_common/js/admCommon.js` & `/_common/js/common.js` 두 곳에서 동일하게 함수를 사용한다.
// Usage) <img onload="javascript:loadedImageResize(this, 400, 300, '#img_info')" />
function loadedImageResize( e , xW, xH, img_info ) {
	var naturalW = e.naturalWidth;
	var naturalH = e.naturalHeight;
	var xxW = 0;
	if(e.naturalWidth > xW) {
		$(e).attr("width", xW);
		$(e).attr("height", (e.naturalHeight * xW) / e.naturalWidth);
		xxW = xW;
	} 
	if(e.naturalHeight > xH) {
		xxW = (e.naturalWidth * xH) / e.naturalHeight;
		$(e).attr("width", xxW);
		$(e).attr("height", xH);
	}
	try {
		$(img_info).css("width", xxW);
		$(img_info).css("text-align", "center");
		$(img_info).css("font-size", "0.7em");
		$(img_info).html("원본 크기(px) : "+naturalW+" x " +naturalH );
	} catch (e) {}
}

// 
function _requestByAjax(formAction, formData, callbackFunctionInstance) {

    $.ajax({
        url: formAction,
            processData: false,
            contentType: false,
            data: formData,
            type: 'POST',
            success: function(result){
            	console.log(result);
            	callbackFunctionInstance("normal", result);
            },
            error: function(result){
            	console.log(result);
            	callbackFunctionInstance("error", result);
            },
            fail: function(result){
            	alert('You need to check you can use a internet.');
            }
        });
}

//
function _requestGet(formId, callbackFunctionInstance) {
	
	console.log("_requestGet:"+$("#frm").serialize());
	
	$.ajax({
		type: 'GET',
		url: $("#"+formId).attr("action"),
		data: $("form[name="+formId+"]").serialize(),
		success: function(response) {
			console.log(response);
			callbackFunctionInstance("normal", response);
		},
		error: function(response, status, error) {
			console.log("status="+status);
			console.log("error="+error);
			callbackFunctionInstance("error", response);
		},
        fail: function(result) {
         	alert('You need to check you can use a internet.');
         }
	});
}

//
function _requestPost(formId, callbackFunctionInstance) {
	
	console.log("_requestPost:"+$("#"+formId).serialize());

	$.ajax({
		type: 'POST',
		url: $("#"+formId).attr("action"),
		data: $("form[name="+formId+"]").serialize(),
		success: function(response) {
			console.log(response);
			callbackFunctionInstance("normal", response);
		},
		error: function(response, status, error) {
			console.log("status="+status);
			console.log("error="+error);
			callbackFunctionInstance("error", response);
		},
        fail: function(result){
         	alert('You need to check you can use a internet.');
         }
	});
}

//
function _requestMultipart(formId, callbackFunctionInstance) {
	
	console.log("_requestMultipart:action ? "+$("#"+formId).attr("action"));
	console.log("_requestMultipart:Fields ? "+$("#"+formId).serialize());
	
	// get form
	var frm = document.getElementById(formId);
    frm.method = 'POST';
    frm.enctype = 'multipart/form-data';
    // set the all forms to FormData class 
    var multipartData = new FormData(frm);
	$.ajax({
		url: $("#"+formId).attr("action"),
		type: 'POST',
		enctype: 'multipart/form-data',
		contentType: false,
		processData: false, // Important!
		cache: false,
		data: multipartData,
		success: function(response) {
			console.log(response);
			callbackFunctionInstance("normal", response);
		},
		error: function(response, status, error) {
			console.log("status="+status);
			console.log("error="+error);
			callbackFunctionInstance("error", response);
		},
        fail: function(result){
         	alert('You need to check you can use a internet.');
         }
	});
}

/**
 * 첨부파일을 개별적으로 즉시 업로드 할 때 사용된다. 응답은 json 으로 응답을 준다. 
 * formData 는 'uploadType=image/pdf/mp3, uploadPath=/upload/ 아래에 저장되므로, upload 제외한 폴더명, image/pdf/mp3=실제업로드할파일'
 * 사용예) _fileUploadByAjax('/admin/board/uploadOk.jsp', formData, 'pdf', 'pdffile', 콜백함수);
 * @param formAction
 * @param formData
 * @param srcElement
 * @param targetElement
 * @returns void
 */
function _fileUploadByAjax(formAction, formData, srcElement, targetElement, callbackFunctionInstance) {
	// formAction '/admin/board/uploadOk.jsp'
	/*
	호출)
	var formData = new FormData($('form01')[0]);
    formData.append("uploadType", "pdf");
    formData.append("uploadPath", "magazine/${TMP_YYYYMM}");
    formData.append("pdf", $("#pdf")[0].files[0]);
    
        정상응답)
      {"status":"normal", "message":"정상메시지", "uploaded":"업로드된 파일의 URI"}
        에러응답)
      {"status":"error", "message":"에러메시지"}
	 */
    $.ajax({
        url: formAction,
            processData: false,
            contentType: false,
            data: formData,
            type: 'POST',
            success: function(result){
            	console.log(result);
            	alert(result.message);
            	if(result.status == "error") {
            		document.getElementById(srcElement).value = "";
            		document.getElementById(targetElement).value = "";
            	} else {
                	/* success case */
            		try {
            			document.getElementById(targetElement).value = result.uploaded;
            		} catch ( e ) {}
            		try {
            			$("#"+targetElement+"_span").text(result.uploaded);
            		} catch ( e ) {}
            		/* if success, call a callbackFunction */
            		if(callbackFunctionInstance!=null) {
            			callbackFunctionInstance(result);
            		}
            	}
            },
            error: function(result){
            	console.log(result);
            	alert(result.responseText);
            },
            fail: function(result){
            	alert('You need to check you can use a internet.');
            }
        });
}

/*
 * 
 */


/*
 * 지도에 다각형을 생성하고 이벤트를 등록하는 함수.
 * - spec) displayAreaPolygon(지도객체, '이름', mPolygon, mapOption);  
 * - 호출 예)  
	var pPolygon = [${churchVO.MAP_CONTROLAREA}];// 관리구역 폴리곤
	var pMapOption   = { center: new daum.maps.LatLng(${churchVO.MAP_POINT}), level: 5 }; // 지도의 중심좌표 , 지도의 확대 레벨
	var pMap = new daum.maps.Map(document.getElementById('map'), pMapOption);
	displayAreaPolygon(pMap, '이름', pPolygon, pMapOption);
 */
function displayAreaPolygon(pMap, pName, pPolygon, pMapOption) {

    // 다각형을 생성합니다
    var _polygon = new daum.maps.Polygon({
        //map: pMap, // 다각형을 표시할 지도 객체
        path: pPolygon, // 그려질 다각형의 좌표 배열입니다
        strokeWeight: 3, // 선의 두께입니다
        strokeColor: '#9c0707', // 선의 색깔입니다
        strokeOpacity: 0.8, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
        strokeStyle: 'solid', // 선의 스타일입니다
        fillColor: '#EFFFED', // 채우기 색깔입니다
        fillOpacity: 0.2 // 채우기 불투명도 입니다
    });
    _polygon.setMap(pMap);

    // 다각형에 click 이벤트를 등록하고 이벤트가 발생하면 다각형의 이름과 면적을 인포윈도우에 표시합니다
    var _iwContent = '<div class="infos">' +
    '   <div class="title">' + pName + '</div>' +
    '   <div class="size">총 면적 : 약 ' + Math.floor(_polygon.getArea()) + ' m<sup>2</sup></area>' +
    '</div>';
    //_iwContent    = '<div style="padding:5px;">${churchVO.NAME} 성당</div>',  // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
    var _infowindow = new daum.maps.InfoWindow({
        map: pMap, // 인포윈도우가 표시될 지도
        position : pMapOption.center, // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
        content  : _iwContent,  // 
        removable : true       // removeable 속성을 ture 로 설정하면 인포윈도우를 닫을 수 있는 x버튼이 표시됩니다
    });
    daum.maps.event.addListener(_polygon, 'click', function(mouseEvent) {
    	_infowindow.setContent(_iwContent);
    	_infowindow.setPosition(mouseEvent.latLng);
    	_infowindow.setMap(pMap);
    });
    
    //
    var _customOverlay = new daum.maps.CustomOverlay({});
    // 다각형에 mouseover 이벤트를 등록하고 이벤트가 발생하면 폴리곤의 채움색을 변경합니다
    // 지역명을 표시하는 커스텀오버레이를 지도위에 표시합니다
    daum.maps.event.addListener(_polygon, 'mouseover', function(mouseEvent) {
   	    _polygon.setOptions({fillColor: '#09f'});
   	    _customOverlay.setContent('<div class="area">' + /*area.name + */'</div>');
   	    _customOverlay.setPosition(mouseEvent.latLng);
   	    _customOverlay.setMap(pMap);
    });
   
    // 다각형에 mousemove 이벤트를 등록하고 이벤트가 발생하면 커스텀 오버레이의 위치를 변경합니다
    daum.maps.event.addListener(_polygon, 'mousemove', function(mouseEvent) {
    	_customOverlay.setPosition(mouseEvent.latLng);
    });
   
    // 다각형에 mouseout 이벤트를 등록하고 이벤트가 발생하면 폴리곤의 채움색을 원래색으로 변경합니다
    // 커스텀 오버레이를 지도에서 제거합니다
    daum.maps.event.addListener(_polygon, 'mouseout', function() {
   	    _polygon.setOptions({fillColor: '#fff'});
   	    _customOverlay.setMap(pMap);
    });
   
}


