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
</head>
<body>
	
	<jsp:include page="/commons/navbar.jsp"/>
	
	
	<div class="main">
		<section class="container mt-3" style="max-width: 560px;">
			<form method="post" action="login.do">
				<div class="form-group">
					<label>아이디</label>
					<input type="text" name="userID" class="form-control" value="${id}">
				</div>
				<div class="form-group">
					<label>비밀번호</label>
					<input type="password" name="userPassword" class="form-control">
				</div>
				<button type="submit" class="btn btn-primary">로그인</button>
			</form>
		</section>
	</div>
		
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
			
			<c:if test="${! empty errors.userNotFound  }">
				<div class="alert alert-danger">
				  <strong>존재하지 않는 회원입니다</strong>
				</div>
			</c:if>
			
			<c:if test="${! empty errors.pwdNotEqual  }">
				<div class="alert alert-danger">
				  <strong>비밀번호가 일치하지 않습니다</strong>
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
	
	<!-- 개인 자바스크립트는 여기에 -->
	<script>
	$(document).ready(function(){
		
		//message는 성공적으로 과정이 마쳐졌을 때만 오는 문구다.
		let message ='${! empty message ? message: ""}';
		if(message) {
			alert(message);
		}
		 
		//성공적으로 못했을 경우에는 errors Map으로 날라온다. 그리고 이러한 메시지는 Modal 창으로 보여준다.
		$('#errorModal').modal('show');
		
	});	
	
	</script>

</body>
</html>