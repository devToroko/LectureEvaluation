package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import util.History;

public class LoginUserLimitFilter implements Filter {


	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest)req;
		HttpSession session = request.getSession(false);

		if (session != null && session.getAttribute("loginUser") != null) {
//			HttpServletResponse response = (HttpServletResponse)res;
//			response.setCharacterEncoding("UTF-8"); 
//			response.setContentType("text/html; charset=UTF-8"); 
//			PrintWriter script = response.getWriter();
//			script.println("<script>");
//			script.println("alert('로그인 상태에서는 접근이 불가한 페이지입니다.');");
//			script.println("history.back();");
//			script.println("</script>");
//			script.close();
			History.goBack(res, "로그인 상태에서는 접근이 불가한 페이집니다.");
			return;
			
		} else {
			chain.doFilter(req, res);
		}

	}

	
	@Override
	public void init(FilterConfig config) throws ServletException {

	}

	@Override
	public void destroy() {
		
	}
}
