<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 신고하기 모달창 -->
<div class="modal fade" id="modifyModal" tabindex="-1" role="dialog" aria-labelledby="modal" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="modal">신고하기</h5>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			
			<div class="modal-body">
				<form action="modifyEval.do" method="post">
					<input type="hidden" name="evaluationID" value=""/>
					<input type="hidden" name="writer" value=""/><!-- userID(작성자) -->
					<input type="hidden" name="pNo" value="${param.pNo}"/>
					<div class="form-group">
						<label>제목</label>
						<input type="text" name="title" class="form-control" maxlength="40">
					</div>
					<div class="form-group">
						<label>내용</label>
						<textarea name="content" class="form-control" maxlength="2048" style="height: 180px;"></textarea>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
						<button type="submit" class="btn btn-primary">수정하기</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>