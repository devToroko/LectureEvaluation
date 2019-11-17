package filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

// spring의 flashAttribute를 흉내낸 것이다. 인터넷에 flash scope 라고 검색하면 나온다.

// http://smartkey.co.uk/development/implementing-flash-scope-in-java-web-applications/
// 해당 글의 주석을 번역한 것과 제 생각이 조금씩 들어간 주석이 섞여있습니다. 그리고 doFilter 내에 있는 1~3의 순서를 유의해서 보시면 이해가 갈겁니다.

/**
 * request의 속성으로 첨가한 것중 'flash.' 라는 문장으로 시작하는 것은 다음과 같은 필터를 꼭 거치도록 한다.
 */
public class FlashScopeFilter implements Filter {

	private static final String FLASH_SESSION_KEY = "FLASH_SESSION_KEY";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		// 2. session의 속성으로 FLASH_SESSION_KEY 라는 이름의 속성값을 읽어와서 request의 속성으로 넣는다.
		if(request instanceof HttpServletRequest) {
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			HttpSession session = httpRequest.getSession(true);
//			if(session != null) {
				@SuppressWarnings("unchecked")
				Map<String, Object> flashParams = (Map<String, Object>) session.getAttribute(FLASH_SESSION_KEY);
				if(flashParams != null) {
					for(Entry<String, Object> flashEntry : flashParams.entrySet()) {
						request.setAttribute(flashEntry.getKey(), flashEntry.getValue());
					}
					session.removeAttribute(FLASH_SESSION_KEY);
				}
//			}
		}
		
		// 3. 다음 체인 혹은 서블릿에 request와 response를 PASS!
		chain.doFilter(request, response);
		
		// 1. 다음 요청(request)를 위해서 세션에 flash scope으로쓸 파라미터 값을 저장합니다.
		if(request instanceof HttpServletRequest) {
			HttpServletRequest httpRequest = (HttpServletRequest)request;
			Map<String, Object> flashParams = new HashMap<String, Object>();
			Enumeration<String> e = httpRequest.getAttributeNames();//'flash.' 이라는 파라미터의 이름과 값을 찾기 위해서 조회를 해야한다.
			while(e.hasMoreElements()) {
				String paramName = e.nextElement();
				if (paramName.startsWith("flash.")) {
					Object value = request.getAttribute(paramName);
					paramName = paramName.substring(6,paramName.length());
					flashParams.put(paramName, value);
				}
			}
			
			if(flashParams.size() > 0) {
				HttpSession session = httpRequest.getSession(false);
				if(session !=null) {
					session.setAttribute(FLASH_SESSION_KEY, flashParams);
				}
			}
		}
		
		
	}

	
	/*
	  작성이 다 끝났으면 테스트를 해보자. web.xml에 
	  
	  <filter>
	  	<filter-name>FlashScopeFilter</filter-name>
	  	<filter-class>filter.FlashScopeFilter</filter-class>
	  </filter>
	  
	  <filter-mapping>
	  	<filter-name>FlashScopeFilter</filter-name>
	  	<url-pattern>/*</url-pattern> <!-- 나중에 범위 제한을 더 좁게 할 수 있으면 하면된다. -->
	  </filter-mapping>
	
	  
	  그리고 나서 WebContent에  1.jsp 를 만들고
	  
	<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

	<%
		request.setAttribute("flash.message", "안녕하세요");
		response.sendRedirect(request.getContextPath()+"/2.jsp");
		return;
	%>
	
		
	그리고 2.jsp를 작성
	
	
	<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<!DOCTYPE html>
	<html>
	<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	</head>
	<body>
		<h1>${message }</h1>
	</body>
	</html>
	
	이후에 서버 시작후 1.jsp를 url에 쳐보자. 그러면 주소창에 2.jsp가 뜨고 화면에  "안녕하세요!" 라는 메시지가 보일 것이다.
	중요한 것은 메시지 확인 후 바로 새로 고침을 해보는 것이다. 이때 "안녕하세요!" 메시지가 사라졌다는 것을 확인하면 성공적으로 테스트가 끝났다.
	 */
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// do noting
	}
	
	@Override
	public void destroy() {
		// do noting
	}

}
