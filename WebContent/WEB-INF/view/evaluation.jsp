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
	<section class="container">
		<form action="evaluation.do" method="get" class="form-inline mt-3">
			<input type="hidden" name="pageNo" value="${pageInfo.currentPage}"/>
			<select name="lectureDivide" class="form-control mx-1 mt-2">
				<option value="전체" ${pageInfo.searchInfo.lectureDivide eq '전체'? 'selected':'' }>전체</option>
				<option value="전공" ${pageInfo.searchInfo.lectureDivide eq '전공'? 'selected':'' }>전공</option>
				<option value="교양" ${pageInfo.searchInfo.lectureDivide eq '교양'? 'selected':'' }>교양</option>
				<option value="기타" ${pageInfo.searchInfo.lectureDivide eq '기타'? 'selected':'' }>기타</option>
			</select>
			<select name="searchType" class="form-control mx-1 mt-2">
				<option value="최신순" ${pageInfo.searchInfo.searchType eq '최신순'? 'selected':'' }>최신순</option>
				<option value="추천순" ${pageInfo.searchInfo.searchType eq '추천순'? 'selected':'' }>추천순</option>
			</select>
			<input type="text" name="search" value="${pageInfo.searchInfo.search }" class="form-control mx-1 mt-2" placeholder="내용을 입력하세요"/>
			<button type="submit" class="btn btn-primary mx-1 mt-2">검색</button>
			<a class="btn btn-primary mx-1 mt-2" data-toggle="modal" href="#registerModal">등록하기</a>
		</form>
		
		
		

		<!-- 게시글(= 카드) -->
		<c:if test="${pageInfo.hasNoPage()}">
			<div class="alert alert-danger text-center mt-3">
				<strong>게시글이 존재하지 않습니다</strong>
			</div>
		</c:if>
		
		<c:forEach var="list" items="${pageInfo.list }">
		<div class="card bg-light mt-3">
			<div class="card-header b-light">
				<div class="row">
					<div class="col-8 text-left">${list.lectureName }&nbsp;<small>${list.professorName } / (${list.lectureYear }년 ${list.semesterDivide })</small></div>
					<div class="col-4 text-right">
						종합<span style="color:red;">${list.totalScore }</span>
					</div>
				</div>
			</div>
			<div class="card-body">
				<h5 class="card-title">
					${list.evaluationTitle }&nbsp;<small></small>
				</h5>
				<p class="card-text">${list.evaluationContent }</p>
				<div class="row">
					<div class="col-9 text-left">
						성적 <span style="color:red;">${list.creditScore }</span>
						널널 <span style="color:red;">${list.comfortableScore }</span>
						강의 <span style="color:red;">${list.lectureScore }</span>
						<span style="color: green;">(추천: ${list.likeCount })</span>
					</div>
					<div class="col-3 text-right">
						<form action="likeEval.do">
							
						</form>
						<form action="deleteEval.do">
							<input type="hidden"  name="evaluationID" value="${list.evaluationID }"/>
							<input type="hidden"  name="userID" value="${list.userID }"/>
						</form>
						
						<c:url value="/likeEval.do" var="likeUrl">
							<c:param name="evaluationID" value="${list.evaluationID }"/>
							<c:param name="pageNo" value="${pageInfo.currentPage}"/>
							<c:param name="lectureDivide" value="${pageInfo.searchInfo.lectureDivide}"/>
							<c:param name="searchType" value="${pageInfo.searchInfo.searchType}"/>
							<c:param name="search" value="${pageInfo.searchInfo.search}"/>
						</c:url>
						
						<c:url value="/deleteEval.do" var="deleteUrl">
							<c:param name="pageNo" value="${pageInfo.currentPage}"/>
							<c:param name="lectureDivide" value="${pageInfo.searchInfo.lectureDivide}"/>
							<c:param name="searchType" value="${pageInfo.searchInfo.searchType}"/>
							<c:param name="search" value="${pageInfo.searchInfo.search}"/>
							<c:param name="evaluationID" value="${list.evaluationID }"/>
							<c:param name="userID" value="${list.userID}"/>
						</c:url>
						
						<a onclick="return confirm('추천하시겠습니까?');" href="${likeUrl}">추천</a>
						<c:if test="${list.userID == loginUser.id }">
							<a class='modifyLink' data-evalNum="${list.evaluationID }" data-writer="${list.userID}" data-toggle="modal" href="#modifyModal">수정</a>
							<a onclick="return confirm('삭제하시겠습니까?');" href="deleteEval.do?evaluationID=${list.evaluationID }&userID=${list.userID}">삭제</a>
						</c:if>
					</div>
				</div>
			</div>
		</div>
		</c:forEach>
		
	</section>
	</div>
	
	
	
	${url }
	
	<!-- 페이징 관련 -->
	<c:if test="${pageInfo.hasPages() }">
		<ul class="pagination justify-content-center mt-3">
		   	<c:if test="${pageInfo.prev}">
			<li class="page-item"><a class="page-link" href="evaluation.do?pageNo="${pageInfo.startPage-5}>이전</a></li>
		   	</c:if>
			
			<c:forEach var="pNo" begin="${pageInfo.startPage }" end="${pageInfo.endPage }">		   
			
			<c:url value="/evaluation.do" var="url">
				<c:param name="pageNo" value="${pNo}"/>
				<c:param name="lectureDivide" value="${pageInfo.searchInfo.lectureDivide}"/>
				<c:param name="searchType" value="${pageInfo.searchInfo.searchType}"/>
				<c:param name="search" value="${pageInfo.searchInfo.search}"/>
			</c:url>
		    <li class="page-item ${pageInfo.currentPage eq pNo?'active':''}"><a class="page-link" href="${url}">[${pNo}]</a></li>
			</c:forEach>
			
			<c:if test="${pageInfo.next}">
			<li class="page-item"><a class="page-link" href="evaluation.do?pageNo="${pageInfo.startPage+5}>다음</a></li>
		   	</c:if>
		</ul>
	</c:if>
	

	<jsp:include page="/commons/registerModal.jsp">
		<jsp:param name="pNo" value="${pageInfo.currentPage }"/>
	</jsp:include>
	<jsp:include page="/commons/modifyModal.jsp">
		<jsp:param  name="pNo" value="${pageInfo.currentPage }"/>
	</jsp:include>
	<jsp:include page="/commons/bootstrapJS.jsp"/>
	<!-- 개인 자바스크립트는 여기에 -->
	<script type="text/javascript">
	$(document).ready(function(){
		let message ='${! empty message ? message: ""}';
		if(message) {
			alert(message);
		}
		
		// 게시글 수정용 modal box에 필요한 몇가지 파라미터 값과 내용을 동적으로 변환한다. 
		for(let i = 0 ; i < 5 ; i++) {

			let $div = $($('.modifyLink')[i]).parents('div[class="card-body"]');
			let title = $($div.find('h5')).text().trim();
			let content = $($div.find('p')).text().trim();
			
			$($('.modifyLink')[i]).on("click",function(){
				$('#modifyModal input[name="evaluationID"]').val($(this).data('evalnum'));
				$('#modifyModal input[name="writer"]').val($(this).data('writer'));
				$('#modifyModal input[name="title"]').val(title);
				$('#modifyModal textarea[name="content"]').val(content);
			});
		}
	});
	</script>
	<jsp:include page="/commons/footer.jsp" />	

</body>
</html>