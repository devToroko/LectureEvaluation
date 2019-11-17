<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
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
	
		<section class="container mt-3" style="max-width: 560px;">
		<div id="joinModal">
			<form method="post">
				<div class="form-group">
					<label>아이디</label>
					<input type="text" name="userID" class="form-control">
				</div>
				<div class="form-group">
					<label>비밀번호</label>
					<input type="password" name="userPassword" class="form-control">
				</div>
				<div class="form-group">
					<label>이메일</label>
					<input type="email" name="userEmail" class="form-control">
				</div>
				<button id="joinBtn" type="button" class="btn btn-primary">회원가입</button>
			</form>
		</div>
		</section>
		
	</div>
	
	<div id="loader"></div>
	
	<c:if test="${! empty errors}">
	<!-- The Modal -->
	<div class="modal fade" id="errorModal">
	  <div class="modal-dialog">
	    <div class="modal-content">
	    
	      <!-- Modal Header -->
		<div class="modal-header pl-0">
			<h5 class="modal-title w-100 text-center position-absolute" style="color:red">Error</h5>
			<button type="button" class="close" data-dismiss="modal" aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
		</div>
	      
	      <!-- Modal body -->
	      <div class="modal-body text-center">
	      
	      	<c:if test="${! empty errors.userID  }">
				<div class="alert alert-danger">
				  <strong>아이디를 입력하세요</strong>
				</div>
			</c:if>
			
			<c:if test="${! empty errors.userPassword  }">
				<div class="alert alert-danger">
				  <strong>비밀번호를 입력하세요</strong>
				</div>
			</c:if>
			
			<c:if test="${! empty errors.userEmail  }">
				<div class="alert alert-danger">
				  <strong>이메일을 입력하세요</strong>
				</div>
			</c:if>
			
			<c:if test="${! empty errors.duplicatedId  }">
				<div class="alert alert-danger">
				  <strong>사용하시려는 아이디는 이미 다른 분이 사용중이십니다.</strong>
				</div>
			</c:if>
	      
	      <!-- Modal footer -->
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary btn-block" data-dismiss="modal">Close</button>
	      </div>
	      
	    </div>
	  </div>
	</div>
	</div>
	</c:if>
	
	
	<jsp:include page="/commons/footer.jsp" />	
	<jsp:include page="/commons/bootstrapJS.jsp"/>
	<script type="text/javascript" src="/DevToroko_MVC_Pattern_WEB/js/joinAjax.js"></script>
	<!-- 개인 자바스크립트는 여기에 -->
	<script type="text/javascript">
		$('#errorModal').modal('show');
		
		$('#joinBtn').on("click",function(){
			send();
		});
	</script>
	
</body>
</html>