package evaluation_board.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import evaluation_board.errors.NoSuchEvaluationException;
import evaluation_board.service.ModfiyRequest;
import evaluation_board.service.ModifyService;
import login.dto.LoginUser;
import mvc.command.CommandHandler;

public class ModifyHandler implements CommandHandler {

	private ModifyService service = new ModifyService();
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(request.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(request, response);
		} else {
			response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}
	
	private String processSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		LoginUser loginUser;
		HttpSession session = request.getSession(false);
		if(	
			(session == null) || //세션이 null이거나
			((loginUser = (LoginUser)session.getAttribute("loginUser"))==null) //세션이 존재하더라도 loginUser가 없다면 로그인 상태를 확인 해줄 것을 알린다.
		) {
			request.setAttribute("flash.message","로그인이 되어 있지 않습니다. 로그인을 먼저해주시기 바랍니다.");
			response.sendRedirect(request.getContextPath()+"/login.do");
			return null;
		}
		
		String loginId = loginUser.getId();
		
		String evaluationIDVal= request.getParameter("evaluationID");
		String writer= request.getParameter("writer");
		String pNo= request.getParameter("pNo");
		String title= request.getParameter("title");
		String content= request.getParameter("content");
		
		int evaluationID = 0;
		try {
			evaluationID = Integer.parseInt(evaluationIDVal);
			Integer.parseInt(pNo);
		} catch (NumberFormatException e) {
			pNo = "1";
			return setMessage(request, response, "잘못된 수정 요청입니다.", pNo);
		}
		
		
		
		Map<String, Boolean> errors = new HashMap<String, Boolean>();
		
		ModfiyRequest ModRequest = new ModfiyRequest(evaluationID,writer,pNo,title,content);
		ModRequest.validate(errors);
		if(!errors.isEmpty()) {
			return setMessage(request, response, "수정을 하기 위해서는 제목과 내용이 공백이면 안됩니다", pNo);
		}
		
		if(!loginId.equals(writer)) return setMessage(request, response, "작성자만이 글을 수정할 권한이 있습니다.", pNo);
		
		try {
			service.changeEvaluation(ModRequest);
			return setMessage(request, response, "성공적으로 수정을 완료하였습니다.", pNo);
		} catch (NoSuchEvaluationException e) {
			return setMessage(request, response, "존재하지 않는 글을 수정할 수 없습니다.", pNo);
		}
		
		
	}

	private String setMessage(HttpServletRequest request, HttpServletResponse response,String message,String pNo) throws IOException {
		request.setAttribute("flash.message",message);
		response.sendRedirect(request.getContextPath()+"/evaluation.do?pageNo="+pNo);
		return null;
	}
}
