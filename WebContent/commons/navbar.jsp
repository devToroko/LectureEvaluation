<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
	${ctxPath=pageContext.request.contextPath;'' }
	<!-- navbar-brand  nav에 제목 쓸때 쓴다. -->
	<a class="navbar-brand" href="${ctxPath}">메인 페이지</a>
	<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbar"><!-- id가 navbar를 숨겼다 표시하는게 가능 -->
		<span class="navbar-toggler-icon"></span>
	</button>
	<div id="navbar" class="collapse navbar-collapse">
		<ul class="navbar-nav mr-auto">
			<li class="nav-item active">
				<a class="nav-link" href="${ctxPath}/evaluation.do">강의평가</a>
			</li>
			<li class="nav-item dropdown">
				<a class="nav-link dropdown-toggle" id="dropdown" data-toggle="dropdown">
					회원 관리
				</a>
				<div class="dropdown-menu" aria-labelledby="dropdown">
					<c:if test="${empty loginUser}">
						<a class="dropdown-item" href="${ctxPath}/login.do">로그인</a>
						<a class="dropdown-item" href="${ctxPath}/join.do">회원가입</a>
					</c:if>
					<c:if test="${! empty loginUser }">
						<a class="dropdown-item" href="${ctxPath}/logout.do">로그아웃</a>
						<a class="dropdown-item" href="${ctxPath}/changePwd.do">암호 변경</a>
					</c:if>
				</div>
			</li>
		</ul>
		<!-- <form class="form-inline my-2 my-lg-0" method="get" action="./index.jsp">
			<input  type="text" name="search"  class="form-control mr-sm-2" placeholder="내용을 입력하세요" aria-label="Search">
			<button class="btn btn-outline-success my-2 my-sm-0" type="submit">검색</button>
		</form> -->
	</div>
</nav>