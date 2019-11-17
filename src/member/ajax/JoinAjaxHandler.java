package member.ajax;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import member.errors.DuplicateUserException;
import member.service.JoinRequest;
import mvc.command.CommandHandler;

public class JoinAjaxHandler implements CommandHandler {

	private JoinAjaxService joinService = new JoinAjaxService();
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {

		//response.setContentType("application/json;charset=UTF-8");
		response.setContentType("application/json;");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();		
		
		JsonObject json = new JsonObject();
		
		JoinRequest	joinReq = new JoinRequest();
		joinReq.setUserID(request.getParameter("userID"));
		joinReq.setUserPassword(request.getParameter("userPassword"));
		joinReq.setUserEmail(request.getParameter("userEmail"));
        System.out.println(joinReq);
		
		Map<String, Boolean> errors = new HashMap<String, Boolean>();
		joinReq.Validate(errors);
		
		if(!errors.isEmpty()) {
			json.addProperty("fail", "공백이 있어서는 안됩니다.");
			out.print(json.toString());
			out.close();
        	out.flush();
			return null;
		}
		
		try {
			joinService.join(joinReq);
			json.addProperty("success", "회원가입에 성공하셨습니다. 이메일로 이메일 인증 메시지가 날라갔습니다. 확인해주시기 바랍니다.");
			json.addProperty("userEmail", joinReq.getUserEmail());
			json.addProperty("userID", joinReq.getUserID());
			out.print(json.toString());
		} catch (DuplicateUserException e) {
			json.addProperty("fail", "중복된 회원입니다. 다른 아이디를 사용해주시기 바랍니다.");
			out.print(json.toString());
		}
		out.close();
		out.flush();
		return null;
		
	}
	
	/*
	 
	 let query = $('#joinModal form').serialize();
	  $.ajax({
	    url:'/DevToroko_MVC_Pattern_WEB/joinAjax.do',
	    type:'POST',
	    data:query,
	    datatype:'json',
	    success:function(data){
	        if(data.hasOwnProperty('fail')) {
	                alert(data.fail); 
	        }
	        else if(data.hasOwnProperty('success')) {
				alert(data.success);
				///emailAjaxSend.do
				let email = data.userEmail;
				let userID = data.userID;
				$.ajax({
					url:'/DevToroko_MVC_Pattern_WEB/emailAjaxSend.do',
					data: {"userID" : userID, "userEmail" : email},
					type:'POST',
					datatype:'json',
					success:function(data) {
	                    if(data.hasOwnProperty('errors')) {
	                    	console.log(data); 
	                    }else if(data.hasOwnProperty('success')) {
							console.log(data.success);
						}
	                }
				});
	        }
	    },
	    error:function(e,j,k){console.log(e);}
	})
	  
	 */

}
