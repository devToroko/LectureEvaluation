package email;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import email.errors.AlreadyCheckedException;
import email.errors.EmailHashCodeNotEqualException;
import email.errors.NoChangeException;
import member.errors.NoSuchUserException;
import mvc.command.CommandHandler;

public class EmailCheckHandler implements CommandHandler {

	EmailCheckService emailCkService = new EmailCheckService();
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(request.getMethod().equalsIgnoreCase("GET")) {
			try {
				EmailCheckRequest emailReq= new EmailCheckRequest(request.getParameter("id"),request.getParameter("code"));
				
				emailCkService.checkEmail(emailReq);
				
				request.setAttribute("flash.message", "이메일 인증이 완료되었습니다. 로그인 후 자유롭게 강의 평가를 이용해주세요");
				request.setAttribute("flash.id", emailReq.getID());
				
			} catch (EmailHashCodeNotEqualException e) {
				request.setAttribute("flash.message", "이메일 인증에 실패하셨습니다. 이메일 인증 코드가 맞지 않습니다.");
			} catch (AlreadyCheckedException|NoChangeException e) {
				request.setAttribute("flash.message", "이미 인증이 완료된 회원입니다.");
			} catch (NoSuchUserException e) {
				request.setAttribute("flash.message", "존재하지 않는 회원 아이디가 인증 시도입니다. 회원가입을 먼저 해주세요");
			}
			
			
			response.sendRedirect(request.getContextPath()+"/index.jsp");
			return null;
			
			
		} else {
			response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}

}
