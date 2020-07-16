<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<!-- ---------------------- -->
	<!-- common_left_menu start -->
<!--/.sidebar-->
	<!-- common_left_menu end -->
	<!-- -------------------- -->

<c:if test="${admSessionMemId ne '' and ADM_MEM_GROUP eq '1'}"><!-- Const.SESSION_KEY_ADM_MEM_GROUP -->
	<!-- 수퍼 어드민 메뉴들 -->
	<div class="navbar-default sidebar" role="navigation">
        <div class="sidebar-nav navbar-collapse">
            <ul class="nav" id="side-menu">
                <li>
                    <a href="#"><i class="fa fa-sitemap fa-fw"></i> 코드관리</a>
                    <ul class="nav nav-second-level">
                        <!-- <li data-pg="org_01"><a href="/admin/org/org_list.do">임지코드관리</a></li><!-- old menu -->
                        <!-- <li data-pg="org_02"><a href="/admin/org/pos_list.do">직급코드관리</a></li><!-- old menu -->
                        <li data-pg="org_03"><a href="/admin/org/org_code_mgmt.do">임지코드관리</a></li>
                        <li data-pg="org_04"><a href="/admin/org/pos_code_mgmt.do">직급코드관리</a></li>
                        <li data-pg="org_05"><a href="/admin/org/welfareagent_code_mgmt.do">복지기관 세목코드관리</a></li>
                        <li data-pg="org_06"><a href="/admin/code/common_code_master_list.do">공통코드관리</a></li>
                        <li data-pg="org_08"><a href="/admin/code/board_category_list.do">게시판 카테고리코드관리</a></li>
                        <li data-pg="org_07"><a href="/admin/code/board_list.do">게시판 코드관리</a></li>
                    </ul>
                    <!-- /.nav-second-level -->
                </li>
                <li>
                    <a href="#"><i class="fa fa-sitemap fa-fw"></i> 게시판관리</a>
                    <ul class="nav nav-second-level">
                        <li data-pg="board_01"><a href="/n/admin/board/board_list.do">게시판관리</a></li>
                        <li data-pg="board_01"><a href="/admin/board/par_list.do">교구장동정</a></li>
                        <li data-pg="board_02"><a href="/admin/board/news_list.do">교회/교구/공동체</a></li>
                        <li data-pg="board_03"><a href="/admin/board/church_list.do">본당알림</a></li>
                        <li data-pg="board_04"><a href="/admin/board/cure_list.do">사목자료</a></li>
                    </ul>
                    <!-- /.nav-second-level -->
                </li>
				<li><a href="#"><i class="fa fa-sitemap fa-fw"></i>앨범게시판관리</a>
                    <ul class="nav nav-second-level">
                    	<li data-pg="board_05"><a href="/admin/board/oldAlb_list.do">역대교구장앨범</a></li>
						<li data-pg="board_06"><a href="/admin/board/album_list.do">교구앨범</a></li>
						<li data-pg="board_07"><a href="/admin/church/church_album.do">본당앨범</a></li>
						<li data-pg="board_08"><a href="/admin/gyogu/gyoguMov_list.do">교구영상</a></li>
                    </ul>
                    <!-- /.nav-second-level -->
                </li>
				<li><a href="#"><i class="fa fa-sitemap fa-fw"></i>기타게시판 관리</a>
                    <ul class="nav nav-second-level">
					<li data-pg="etc_01"><a href="/admin/board/msg_list.do">교구장메시지</a></li>
					<li data-pg="etc_02"><a href="/admin/board/sch_cal.do">교구장일정</a></li>
					<li data-pg="etc_03"><a href="/admin/board/sch_list.do">교구일정</a></li>
					<li data-pg="etc_04"><a href="/admin/board/mgz_list.do">간행물관리</a></li>
					<li data-pg="etc_05"><a href="/admin/board/faq_list.do">FAQ(교구법원)</a></li>
                    </ul>
                    <!-- /.nav-second-level -->
                </li>
				<li><a href="#"><i class="fa fa-sitemap fa-fw"></i>정보관리</a>
                    <ul class="nav nav-second-level">
					<li data-pg="info_01"><a href="/admin/board/history_list.do">역사관관리</a></li>
					<li data-pg="info_02"><a href="/admin/board/departdc_list.do">교구청</a></li>
					<!--<li data-pg="info_03"><a href="/admin/board/dgroup_list.do">지구코드</a></li>-->
					<li data-pg="info_13"><a href="/admin/org/area_code_mgmt.do">지구코드</a></li>
					<li data-pg="info_04"><a href="/admin/church/church_list.do">본당</a></li>
					<li data-pg="info_05"><a href="/admin/board/org_list.do">수도회/기관단체</a></li>
					<li data-pg="info_06"><a href="/admin/board/priest_list.do">사제</a></li>
					<li data-pg="info_07"><a href="/admin/board/bef_priest_list.do">선종사제</a></li>
                    </ul>
                    <!-- /.nav-second-level -->
                </li>
				<li><a href="#"><i class="fa fa-sitemap fa-fw"></i>접수관리</a>
                    <ul class="nav nav-second-level">
					<li data-pg="order_01"><a href="/admin/board/doctrine_list.do">통신교리 신청관리</a></li>
					<li data-pg="order_02"><a href="/admin/board/marry_list.do">카나혼인강좌,약혼자주말 신청관리</a></li>
                    </ul>
                    <!-- /.nav-second-level -->
                </li>
				<li><a href="#"><i class="fa fa-sitemap fa-fw"></i>메인관리</a>
                    <ul class="nav nav-second-level">
					<li data-pg="main_01"><a href="/admin/board/popup_list.do">팝업관리</a></li>
					<li data-pg="main_02"><a href="/admin/board/banner_list.do">배너관리</a></li>
					<li data-pg="main_03"><a href="/admin/board/holiday_list.do">전례력/말씀 관리</a></li>
                    </ul>
                    <!-- /.nav-second-level -->
                </li>
				<!-- <li><a href="#"><i class="fa fa-sitemap fa-fw"></i>관리자정보관리</a>
                    <ul class="nav nav-second-level">
						<li data-pg="adm_01"><a href="/admin/member/admmember_list.do">관리자정보</a></li>
						<li data-pg="adm_02"><a href="/admin/member/admauth_list.do">관리자권한그룹설정</a></li>
                    </ul>
                </li> --><!-- /.nav-second-level -->
				<li><a href="#"><i class="fa fa-sitemap fa-fw"></i>회원서비스관리</a>
                    <ul class="nav nav-second-level">
					<li data-pg="mem_01"><a href="/admin/member/member_list.do">회원관리</a></li>
					<li data-pg="mem_02"><a href="/admin/board/inquire_list.do">나누고싶은이야기</a></li>
					<!-- <li data-pg="mem_03"><a href="/admin/board/email_list.do">메일발송</a></li> -->
                    </ul>
                    <!-- /.nav-second-level -->
                </li>
            </ul>
        </div>
        <!-- /.sidebar-collapse -->
    </div>
</c:if>
	
<c:if test="${ADM_MEM_ID ne 'admin' and SS_WRITEYN eq 'Y' and SS_GROUPGUBUN eq '3'}">
	<!-- 글쓰기 권한 사용자를 위한  메뉴들 -->
	<div class="navbar-default sidebar" role="navigation">
		<div class="sidebar-nav navbar-collapse">
			<ul class="nav" id="side-menu">
				<li>
					<a href="/home.do" target="newcaincheon"><i class="fa fa-sitemap fa-fw"></i> 홈으로 이동하기</a>
					<!-- <ul class="nav nav-second-level">
						<li data-pg="board_03"><a href="/admin/board/church_list.do">본당알림</a></li>
					</ul> -->
				</li>
				<li><a href="#"><i class="fa fa-sitemap fa-fw"></i>정보관리</a>
					<ul class="nav nav-second-level">
						<li data-pg="info_04"><a href="/church_member/church_view.do?church_idx=${SS_CHURCHIDX}&query_type=modify">본당</a></li>
					</ul>
				</li>
			</ul>
		</div>
	</div>
</c:if>

<c:if test="${admSessionMemId ne '' and ADM_MEM_GROUP eq 'et4'}"><!-- Const.SESSION_KEY_ADM_MEM_GROUP -->
	<!-- 글쓰기 권한 사용자를 위한  메뉴 :: 간행물관리자 -->
	<div class="navbar-default sidebar" role="navigation">
		<div class="sidebar-nav navbar-collapse">
			<ul class="nav" id="side-menu">
				<li><a href="#"><i class="fa fa-sitemap fa-fw"></i>기타게시판 관리</a>
                    <ul class="nav nav-second-level">
					<li data-pg="etc_04"><a href="/admin/board/mgz_list.do">간행물관리</a></li>
                    </ul>
                    <!-- /.nav-second-level -->
                </li>
			</ul>
		</div>
	</div>
</c:if>


<c:if test="${admSessionMemId ne '' and ADM_MEM_GROUP eq 'od1'}"><!-- Const.SESSION_KEY_ADM_MEM_GROUP -->
	<!-- 글쓰기 권한 사용자를 위한  메뉴 :: 통신교리신청관리자  -->
	<div class="navbar-default sidebar" role="navigation">
		<div class="sidebar-nav navbar-collapse">
			<ul class="nav" id="side-menu">
				<li><a href="#"><i class="fa fa-sitemap fa-fw"></i>접수관리</a>
                    <ul class="nav nav-second-level">
					<li data-pg="order_01"><a href="/admin/board/doctrine_list.do">통신교리 신청관리</a></li>
                    </ul>
                    <!-- /.nav-second-level -->
                </li>
			</ul>
		</div>
	</div>
</c:if>


<c:if test="${admSessionMemId ne '' and ADM_MEM_GROUP eq 'od2'}"><!-- Const.SESSION_KEY_ADM_MEM_GROUP -->
	<!-- 글쓰기 권한 사용자를 위한  메뉴 :: 카나혼인신청관리자 -->
	<div class="navbar-default sidebar" role="navigation">
		<div class="sidebar-nav navbar-collapse">
			<ul class="nav" id="side-menu">
				<li><a href="#"><i class="fa fa-sitemap fa-fw"></i>접수관리</a>
                    <ul class="nav nav-second-level">
					<li data-pg="order_02"><a href="/admin/board/marry_list.do">카나혼인강좌,약혼자주말 신청관리</a></li>
                    </ul>
                    <!-- /.nav-second-level -->
                </li>
			</ul>
		</div>
	</div>
</c:if>