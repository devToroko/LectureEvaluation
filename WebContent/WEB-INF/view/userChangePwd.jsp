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
		<section class="container mt-3" style="max-width: 560px;border:1px solid #cecece;">
			<form method="post" action="changePwd.do">
				<div class="form-group">
					<label>현재 비밀번호</label>
					<input type="password" name="currentPwd" class="form-control" maxlength="64">
				</div>
				<div class="form-group">
					<label>새 비밀번호</label>
					<input type="password" name="newPwd" class="form-control" maxlength="64">
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
	      
	      	<c:if test="${! empty errors.currentPWd  }">
				<div class="alert alert-danger">
				  <strong>현재 사용 중이신 비밀 번호가 틀렸습니다.</strong>
				</div>
			</c:if>
			
			<c:if test="${! empty errors.newPwd  }">
				<div class="alert alert-danger">
				  <strong>새 비밀 번호를 입력하세요</strong>
				</div>
			</c:if>
			
			<c:if test="${! empty errors.PwdNotEqual  }">
				<div class="alert alert-danger">
				  <strong>비밀 번호가 일치하지 않습니다.</strong>
				</div>
			</c:if>
			
			<c:if test="${! empty errors.NoChange  }">
				<div class="alert alert-danger">
				  <strong>이전 암호와 같은 암호를 사용하셨습니다.</strong>
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