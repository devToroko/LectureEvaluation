package member.ajax;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import email.errors.EmailSendExcpetion;
import mvc.command.CommandHandler;
import util.EmailService;

public class EmailSendHandler implements CommandHandler {
	
	
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
		
		response.setContentType("application/json;");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();		
		JsonObject json = new JsonObject();
		
		String userID = request.getParameter("userID");
		String userEmail = request.getParameter("userEmail");

		
		if(userID == null || userID.trim().isEmpty() || userEmail == null || userEmail.trim().isEmpty()) {
			
			json.addProperty("error", "sending Parameter Error");
			out.print(json.toString());
			out.close();
        	out.flush();
			return null;
		}
		
		try {
			EmailService.sendEmail(userID, userEmail);
			json.addProperty("success", "이메일 전송완료");
			out.print(json.toString());
			
		} catch (EmailSendExcpetion e) {
			json.remove("success");
			json.addProperty("error", "emailSendingError");
			out.print(json.toString());
		}
		
		out.close();
		out.flush();
		return null;
	}

}
