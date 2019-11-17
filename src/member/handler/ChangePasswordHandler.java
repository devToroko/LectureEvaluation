package member.handler;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import login.dto.LoginUser;
import login.errors.PasswordNotEqualException;
import member.errors.NoChangeException;
import member.service.ChangePasswordService;
import mvc.command.CommandHandler;

public class ChangePasswordHandler implements CommandHandler {

	private static final String FORM_VIEW = "/WEB-INF/view/userChangePwd.jsp";
	private ChangePasswordService service = new ChangePasswordService();
	
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
		
		HttpSession session = request.getSession(false);
		LoginUser user = (LoginUser)session.getAttribute("loginUser");
		
		String userID = user.getId();
		String currentPwd = request.getParameter("currentPwd");
		String newPwd = request.getParameter("newPwd");
		
		
		// 파라미터 체크
		Map<String, Boolean> errors = new HashMap<String, Boolean>();
		request.setAttribute("errors", errors);
		
		if(currentPwd == null || currentPwd.trim().isEmpty()) errors.put("currentPWd", Boolean.TRUE);
		if(newPwd == null || newPwd.trim().isEmpty()) errors.put("newPwd", Boolean.TRUE);
		
		if(!errors.isEmpty()) {
			return FORM_VIEW;
		}
		
		
		try {
			// 요청 기능 수행 (암호 변경)
			service.changePassword(userID, currentPwd, newPwd);
			
			request.setAttribute("flash.message", "비밀 번호가 변경되었습니다");
			response.sendRedirect(request.getContextPath());
			
			return null;
		} catch (PasswordNotEqualException e) {
			errors.put("PwdNotEqual", Boolean.TRUE);
			return FORM_VIEW;
		} catch (NoChangeException e) {
			errors.put("NoChange", Boolean.TRUE);
			return FORM_VIEW;
		} 
		
		
	}

}
