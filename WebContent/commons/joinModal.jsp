<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 등록하기 모달창 -->
	<div class="modal fade" id="joinModal" tabindex="-1" role="dialog" aria-labelledby="modal" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="modal">평가등록</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				
				<div class="modal-body">
					<form>
						<div class="form-group">
							<label>아이디</label>
							<input type="text" name="userID" class="form-control" maxlength="20">
						</div>
						<div class="form-group">
							<label>비밀번호</label>
							<input type="password" name="userPassword" class="form-control" maxlength="20">
						</div>
						<div class="form-group">
							<label>이메일</label>
							<input type="email" name="userEmail" class="form-control" maxlength="30">
						</div>
						<div id="loading" style="display:none;" class="spinner-border text-primary"></div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
					<button id="joinBtn" type="button" class="btn btn-primary">가입</button>
				</div>
			</div>
		</div>
	</div>