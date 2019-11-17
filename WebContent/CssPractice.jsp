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
	.container {
		background-color: #eee;
		border
	}
	
	
</style>

</head>
<body>
	
	<jsp:include page="/commons/navbar.jsp"/>
	

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