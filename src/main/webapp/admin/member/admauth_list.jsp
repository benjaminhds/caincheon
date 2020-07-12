<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/admin/_common/inc/head.jsp" %>
<%
if(admSessionMemId==null||admSessionMemId.equals("")) {                // id가 Null 이거나 없을 경우
	response.sendRedirect("/admin/login.jsp");    // 로그인 페이지로 리다이렉트 한다.
}
%>

<script type="text/javascript">
function modifyContents(title,menu) {
	document.form01.action = '/admin/member/admauth_view.do';
	document.getElementById('title').value=title;
	document.getElementById('menu').value=menu;
	document.form01.submit();
    return false;
}

window.onload = function () {
	// active gnb
	onLoadLeftMenu('adm_02');
}
</script>
<body>
<form class="form-inline" name="form01" action="/admin/member/admauth_view.do" method="POST">
<input type="hidden" name="title" id="title" value="${title}"/>
<input type="hidden" name="menu" id="menu" value="${menu}"/>
        <!-- top start(left menu include) -->
        <%@ include file="/admin/_common/inc/top.jsp" %>
        <!-- top end -->
        
        <!-- main start -->        
        <!-- Page Content -->
		<div id="page-wrapper">
			<div class="container-fluid">
				<div class="row">
					<div class="col-lg-12">
						<h3 class="page-header">게시판권한부여 목록</h3>
                	</div>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- //.row(page title end -->
            <!-- Contents : Begin //-->
				<div class="page-list">
                    <div class="panel panel-default">
                        <!-- /.panel-heading -->
                        <div class="panel-body">
							<table class="table table-striped table-bordered table-hover">
								<colgroup>
									<col style="width:30%">
									<col style="width:20%">
									<col style="width:30%">
									<col style="width:20%">
								</colgroup>
                                <tbody>
	                                <tr>
										<td>교구장동정</td>
										<td class="center"><button type="button" class="btn40 btn-primary" onClick="javascript:modifyContents('교구장동정','/admin/board/par_list.do')"><i class="fa fa-pencil"></i>등록/수정</button></td>
										<td>간행물(주보)</td>
										<td class="center"><button type="button" class="btn40 btn-primary" onClick="javascript:modifyContents('간행물(주보)','/admin/board/mgz_list.do')"><i class="fa fa-pencil"></i>등록/수정</button></td>
									</tr>
									<tr>
										<td>교회소식</td>
										<td class="center"><button type="button" class="btn40 btn-primary" onClick="javascript:modifyContents('교회소식','/admin/board/news_list.do')"><i class="fa fa-pencil"></i>등록/수정</button></td>
										<td>FAQ(교구법원)</td>
										<td class="center"><button type="button" class="btn40 btn-primary" onClick="javascript:modifyContents('FAQ(교구법원)','/admin/board/faq_list.do')"><i class="fa fa-pencil"></i>등록/수정</button></td>
									</tr>
									<tr>
										<td>교구소식</td>
										<td class="center"><button type="button" class="btn40 btn-primary" onClick="javascript:modifyContents('교구소식','/admin/board/news_list.do')"><i class="fa fa-pencil"></i>등록/수정</button></td>
										<td>역사관</td>
										<td class="center"><button type="button" class="btn40 btn-primary" onClick="javascript:modifyContents('역사관','/admin/board/history_list.do')"><i class="fa fa-pencil"></i>등록/수정</button></td>
									</tr>
									<tr>
										<td>공동체소식</td>
										<td class="center"><button type="button" class="btn40 btn-primary" onClick="javascript:modifyContents('공동체소식','/admin/board/news_list.do')"><i class="fa fa-pencil"></i>등록/수정</button></td>
										<td>교구청</td>
										<td class="center"><button type="button" class="btn40 btn-primary" onClick="javascript:modifyContents('교구청','/admin/board/departdc_list.do/')"><i class="fa fa-pencil"></i>등록/수정</button></td>
									</tr>
									<tr>
										<td>본당알림</td>
										<td class="center"><button type="button" class="btn40 btn-primary" onClick="javascript:modifyContents('본당알림','/admin/board/church_list.do')"><i class="fa fa-pencil"></i>등록/수정</button></td>
										<td>본당</td>
										<td class="center"><button type="button" class="btn40 btn-primary" onClick="javascript:modifyContents('본당','/admin/church/church_list.do')"><i class="fa fa-pencil"></i>등록/수정</button></td>
									</tr>
									<tr>
										<td>사목자료</td>
										<td class="center"><button type="button" class="btn40 btn-primary" onClick="javascript:modifyContents('사목자료','/admin/board/cure_list.do')"><i class="fa fa-pencil"></i>등록/수정</button></td>
										<td>수도회/기관단체</td>
										<td class="center"><button type="button" class="btn40 btn-primary" onClick="javascript:modifyContents('수도회/기관단체','/admin/board/org_list.do')"><i class="fa fa-pencil"></i>등록/수정</button></td>
									</tr>
									<tr>
										<td>역대교구장앨범</td>
										<td class="center"><button type="button" class="btn40 btn-primary" onClick="javascript:modifyContents('역대교구장앨범','/admin/board/oldAlb_list.do')"><i class="fa fa-pencil"></i>등록/수정</button></td>
										<td>사제</td>
										<td class="center"><button type="button" class="btn40 btn-primary" onClick="javascript:modifyContents('사제','/admin/board/priest_list.do')"><i class="fa fa-pencil"></i>등록/수정</button></td>
									</tr>
									<tr>
										<td>교구앨범</td>
										<td class="center"><button type="button" class="btn40 btn-primary" onClick="javascript:modifyContents('교구앨범','/admin/board/album_list.do')"><i class="fa fa-pencil"></i>등록/수정</button></td>
										<td>선종사제</td>
										<td class="center"><button type="button" class="btn40 btn-primary" onClick="javascript:modifyContents('선종사제','/admin/board/bef_priest_list.do')"><i class="fa fa-pencil"></i>등록/수정</button></td>
									</tr>
									<tr>
										<td>본당앨범</td>
										<td class="center"><button type="button" class="btn40 btn-primary" onClick="javascript:modifyContents('본당앨범','/admin/church/church_album.do')"><i class="fa fa-pencil"></i>등록/수정</button></td>
										<td>메일보내기</td>
										<td class="center"><button type="button" class="btn40 btn-primary" onClick="javascript:modifyContents('메일보내기','/admin/board/email_list.do')"><i class="fa fa-pencil"></i>등록/수정</button></td>
									</tr>
									<tr>
										<td>교구영상</td>
										<td class="center"><button type="button" class="btn40 btn-primary" onClick="javascript:modifyContents('교구영상','/admin/gyogu/gyoguMov_list.do')"><i class="fa fa-pencil"></i>등록/수정</button></td>
										<td>나누고싶은이야기</td>
										<td class="center"><button type="button" class="btn40 btn-primary" onClick="javascript:modifyContents('나누고싶은이야기','/admin/board/inquire_list.do')"><i class="fa fa-pencil"></i>등록/수정</button></td>
									</tr>
									<tr>
										<td>교구장 메시지</td>
										<td class="center"><button type="button" class="btn40 btn-primary" onClick="javascript:modifyContents('교구장 메시지','/admin/board/msg_list.do')"><i class="fa fa-pencil"></i>등록/수정</button></td>
										<td>통신교리</td>
										<td class="center"><button type="button" class="btn40 btn-primary" onClick="javascript:modifyContents('통신교리','/admin/board/doctrine_list.do')"><i class="fa fa-pencil"></i>등록/수정</button></td>
									</tr>
									<tr>
										<td>교구장 일정</td>
										<td class="center"><button type="button" class="btn40 btn-primary" onClick="javascript:modifyContents('교구장 일정','/admin/board/sch_cal.do')"><i class="fa fa-pencil"></i>등록/수정</button></td>
										<td>카나혼인강좌&약혼자주말</td>
										<td class="center"><button type="button" class="btn40 btn-primary" onClick="javascript:modifyContents('카나혼인강좌&약혼자주말','/admin/board/marry_list.do')"><i class="fa fa-pencil"></i>등록/수정</button></td>
									</tr>
									<tr>
										<td>교구 일정</td>
										<td class="center"><button type="button" class="btn40 btn-primary" onClick="javascript:modifyContents('교구 일정','/admin/board/sch_list.do')"><i class="fa fa-pencil"></i>등록/수정</button></td>
										<td></td>
										<td class="center"></td>
									</tr>
                                </tbody>
                            </table>
                            <!-- /.table-responsive -->
                            
                        </div>
				<!-- Contents : End //-->

			</div>
			<!-- /.container-fluid -->
		</div>
		<!-- /#page-wrapper -->

	</div>
	<!-- /#wrapper -->
</form>
</body>

</html>
