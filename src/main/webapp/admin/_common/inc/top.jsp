<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
	<div id="wrapper">
		<!-- Navigation : begin -->
		<nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="/admin/main.jsp">천주교 인천교구 관리자</a>
            </div>
            <!-- /.navbar-header -->

            <ul class="nav navbar-top-links navbar-right">
            	<li class="dropdown">
					<a class="dropdown-toggle" data-toggle="dropdown" href="#">
						<span><Strong> 
						<c:if test = "${fn:length(admSessionMemId) > 0 }">
					        <li>${admSessionMemNm }</li>
					    </c:if></Strong></span>
						<i class="fa fa-user fa-fw"></i>  <i class="fa fa-caret-down"></i>
					</a>
					<ul class="dropdown-menu dropdown-user">
						<li class="divider"></li>
						<li><a href="/admin/logout.jsp"><i class="fa fa-sign-out fa-fw"></i> Logout</a>
					</ul>
					<!-- /.dropdown-user -->
				</li>
                <!-- /.dropdown -->
            </ul>
            <!-- /.navbar-top-links -->

            <%@ include file="/admin/_common/inc/left_menu.jsp" %>
            <!-- /.navbar-static-side -->
        </nav>
