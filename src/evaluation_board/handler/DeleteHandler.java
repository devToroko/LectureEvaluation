package evaluation_board.handler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import evaluation_board.service.DeleteService;
import login.dto.LoginUser;
import member.errors.NoChangeException;
import mvc.command.CommandHandler;

public class DeleteHandler implements CommandHandler {

	private DeleteService service = new DeleteService();
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		if(request.getMethod().equalsIgnoreCase("GET")) {
			return processSubmit(request, response);
		} else {
			response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}
	
	private String processSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//세션 유무 확인
		HttpSession session = request.getSession(false);
		if(session == null) {
			return setMessage(request, response, "로그인 상태가 맞는지 확인해주십쇼", "1");
		} 
		
		//세션에 로그인 사용자 정보가 있는지 확인
		LoginUser loginUser = (LoginUser)session.getAttribute("loginUser");
		String userID = request.getParameter("userID");
		String evaluationID = request.getParameter("evaluationID");
		
		
		if(loginUser == null) {
			return setMessage(request, response, "로그인 상태가 맞는지 확인해주십쇼", "1");
		}
		
		String loginUserID = loginUser.getId();
		loginUserID = loginUserID == null? "": loginUserID;
		
		//현재 접속자와 
		if(!loginUserID.equals(userID)) {
			return setMessage(request, response, "글작성자만이 글을 삭제할 수 있습니다.", "1");
		}
		
		
		//파라미터 체크 끝
		try {
			service.eraseEvaluation(evaluationID);
			return setMessage(request, response, "글이 성공적으로 지워졌습니다", "1");
		} catch (NoChangeException e) {
			return setMessage(request, response, "글이 지워지지 않았습니다. 관리자에게 문의해주세요", "1");
		}
		
	}
	
	private String setMessage(HttpServletRequest request, HttpServletResponse response,String message,String pNo) throws IOException {
		request.setAttribute("flash.message",message);
		response.sendRedirect(request.getContextPath()+"/evaluation.do?pageNo="+pNo);
		return null;
	}

}
