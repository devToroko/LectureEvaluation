<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.io.PrintWriter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>강의 평가 웹 사이트</title>
<%@ include file="/commons/bootstrapCss.jsp" %>

<style type="text/css">
	#loader {
	  display: none;
	  position: fixed;
	  top: 0;
	  left: 0;
	  right: 0;
	  bottom: 0;
	  width: 100%;
	  background: rgba(0,0,0,0.75) url(images/spinner.gif) no-repeat center center;
	  z-index: 10000;
	}
</style>
</head>
<body>
	
	<jsp:include page="/commons/navbar.jsp"/>
	
	<div class="main">
	<div class="container mt-4">
		<div class="jumbotron">
			<h1 class="text-center display-3">강의 평가 사이트</h1>
			<p class="text-center lead">강의 평가 사이트는 다양한 대학의 강의를 평가하는 사이트입니다.</p>
			<p class="text-center"><a class="btn btn-primary btn-lg" href="${pageContext.request.contextPath}/evaluation.do">강의 평가하러 가기</a></p>
			<hr class="my-4">
			<p class="text-center">회원이 아니시면 사용이 불가합니다. 회원가입을 먼저해주세요</p>
			<p class="lead text-center">
				<a class="btn btn-primary mt-2" data-toggle="modal" href="#joinModal">회원 가입</a>
			</p>
		</div>
		
		<!-- <div class="row">
			<div class="col-sm-6 text-center">
				<p>회원가입을 하셔야 사이트 이용이 가능합니다</p>
				<p></p>
			</div>
			<div class="col-sm-6 text-center">
				<p>로그인</p>
				<p><a class="btn btn-primary mx-1 mt-2" data-toggle="modal" href="#registerModal">등록하기</a></p>
			</div>
		</div> -->
	</div>
	
	</div>
	
	<div id="loader"></div>
	
	<!-- http://www.marcorpsa.com/ee/t2228.html  https://gloriajun.github.io/language/2014/03/06/java-servlet-ajax.html-->
	<!-- 페이징 관련 -->
	<jsp:include page="/commons/joinModal.jsp"/>
	<jsp:include page="/commons/bootstrapJS.jsp"/>
	<!-- 개인 자바스크립트는 여기에 -->
	
	<script type="text/javascript" src="/DevToroko_MVC_Pattern_WEB/js/joinAjax.js"></script>
	<script type="text/javascript">
	
	$(document).ready(function(){
		let message ='${! empty message ? message: ""}';
		if(message) {
			alert(message);
		}
		
		$('#joinBtn').on("click",function(){
			send();
		});
	});
	</script>
	<jsp:include page="/commons/footer.jsp" />	

</body>
</html>