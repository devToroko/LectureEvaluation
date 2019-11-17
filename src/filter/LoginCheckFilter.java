package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCheckFilter implements Filter {


	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		
		
//		System.out.println("=================== LoginCheckFilter FILTER START ===================");
//		System.out.print("Before chain.doFilter --> ");
//		HttpServletRequest request2 = (HttpServletRequest)req;
//		System.out.println(request2.getRequestURI());
//		System.out.println(request2.getSession(false));
		
		HttpServletRequest request = (HttpServletRequest)req;
		HttpSession session = request.getSession(false);

		if (session == null || session.getAttribute("loginUser") == null) {
			HttpServletResponse response = (HttpServletResponse)res;
			request.setAttribute("flash.message", "로그인을 먼저하셔야 사용 가능한 페이지입니다.");
			response.sendRedirect(request.getContextPath()+"/login.do");
			//원위치 할 예정
//			History.goTo(res, "로그인을 먼저해야 사용이 가능한 페이지입니다.", request.getContextPath()+"/login.do");
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
