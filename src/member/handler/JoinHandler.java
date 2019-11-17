package member.handler;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.errors.DuplicateUserException;
import member.service.JoinRequest;
import member.service.JoinService;
import mvc.command.CommandHandler;

public class JoinHandler implements CommandHandler {
	
	private static final String FORM_VIEW = "/WEB-INF/view/userJoin.jsp";
	private JoinService joinService = new JoinService();
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String method = request.getMethod();
		if(method.equalsIgnoreCase("GET")) {
			return processForm(request, response);
		} else if(method.equalsIgnoreCase("POST")) {
			return processSubmit(request, response);
		} else {
			response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}
	
	private String processForm(HttpServletRequest request, HttpServletResponse response) {
		return FORM_VIEW;
	}

	private String processSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		JoinRequest	joinReq = new JoinRequest();
		joinReq.setUserID(request.getParameter("userID"));
		joinReq.setUserPassword(request.getParameter("userPassword"));
		joinReq.setUserEmail(request.getParameter("userEmail"));
		
		
		Map<String, Boolean> errors = new HashMap<String, Boolean>();
		request.setAttribute("errors", errors);
		joinReq.Validate(errors);
		
		if(!errors.isEmpty()) {
			return FORM_VIEW;
		}
		
		try {
			joinService.join(joinReq);
			request.setAttribute("flash.message", "사용자 인증을 위해서 이메일로 인증 메시지를 보냈습니다. 인증이 완료된 상태에서만 게시판을 사용할 수 있습니다.");
			response.sendRedirect(request.getContextPath()+"/userLogin.jsp");
			return null;
		} catch (DuplicateUserException e) {
			errors.put("duplicatedId", Boolean.TRUE);
			return FORM_VIEW;
		} 
		
	}

}
