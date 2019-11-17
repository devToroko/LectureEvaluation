package login.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import login.dto.LoginUser;
import login.errors.PasswordNotEqualException;
import login.service.LoginService;
import member.errors.NoSuchUserException;
import mvc.command.CommandHandler;

public class LoginHandler implements CommandHandler {

	private static final String FORM_VIEW = "/WEB-INF/view/userLogin.jsp";
	private LoginService service = new LoginService();
	
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
	
	private String processSubmit(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		//메시지 체크
		String userID = request.getParameter("userID");
		String userPassword = request.getParameter("userPassword");
		
		Map<String, Boolean> errors = new HashMap<String, Boolean>();
		request.setAttribute("errors", errors);
		
		if(userID==null||userID.trim().isEmpty()) errors.put("userID", Boolean.TRUE);
		if(userPassword==null||userPassword.trim().isEmpty()) errors.put("userPassword", Boolean.TRUE);
		
		if(!errors.isEmpty()) {	//입력란이 잘못 입력되면...
			return FORM_VIEW;
		}
		
		//service 사용해서 request 혹은 session 에 저장할 값들 가져오기 (로그인 정보 유지를 위해서 session에 저장!)
		//HttpSession session = request.getSession().setAttribute("authUser", value);;
		try {
			LoginUser loginUser = service.login(userID, userPassword);
			request.getSession().setAttribute("loginUser", loginUser);
			request.setAttribute("flash.message", userID + "님께서 로그인이 성공하셨습니다.");
			
			response.sendRedirect(request.getContextPath());// 여기서 발생하는 IOException은 특별한 처리를 하지 않겠다.
															// index.jsp로 간다.
			return null;
			
		} catch (NoSuchUserException e) {	//사용자가 없거나...
			errors.put("userNotFound", Boolean.TRUE);
			return FORM_VIEW;	
		} catch (PasswordNotEqualException e) {//비번이 일치하지 않을 때.
			errors.put("pwdNotEqual", Boolean.TRUE);
			return FORM_VIEW;
		} 
		
	}

}
