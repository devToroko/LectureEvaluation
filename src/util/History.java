package util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

//자바스크립트를 사용해서 뒤로가기를 하는 메서드를 어디서나 쉽게 쓰기 위해서 만들었습니다.
public class History {
	
	public static void goBack(ServletResponse res, String message) throws IOException {
		HttpServletResponse response = (HttpServletResponse)res;
		response.setCharacterEncoding("UTF-8"); 
		response.setContentType("text/html; charset=UTF-8"); 
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('"+message+"');");
		script.println("history.back();");
		script.println("</script>");
		script.close();
		return;
	}
	
	public static void goTo(ServletResponse res, String message, String location) throws IOException {
		HttpServletResponse response = (HttpServletResponse)res;
		response.setCharacterEncoding("UTF-8"); 
		response.setContentType("text/html; charset=UTF-8"); 
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('"+message+"');");
		script.println("location.href='"+location+"'");
		script.println("</script>");
		script.close();
		return;
	}
	
}
