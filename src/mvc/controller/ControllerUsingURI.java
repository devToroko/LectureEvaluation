package mvc.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import mvc.command.NullHandler;

public class ControllerUsingURI extends HttpServlet {

	private static final long serialVersionUID = -6303336152262351019L;
	// <커맨드, 핸들러인스턴스> 매핑 정보 저장
	private Map<String, CommandHandler> commandHandlerMap = new HashMap<String, CommandHandler>();

	@Override
	public void init() throws ServletException {
		String configFile = getInitParameter("configFile");
		Properties prop = new Properties();
		String configFilePath = getServletContext().getRealPath(configFile);
		try(FileInputStream fis = new FileInputStream(configFilePath)) {
			prop.load(fis);
		} catch (IOException e) {
			throw new ServletException(e);
		}
		prop.list(System.out);
		
		@SuppressWarnings("rawtypes")
		Iterator keyIter = prop.keySet().iterator();
		
		while(keyIter.hasNext()) {
			String command = (String)keyIter.next();
			String handlerClassName = prop.getProperty(command);
			
			try {
				Class<?> handlerClass = Class.forName(handlerClassName);
				CommandHandler handlerInstance = (CommandHandler)handlerClass.newInstance();
				commandHandlerMap.put(command, handlerInstance);
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				throw new ServletException(e);
			}
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}
	
	private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String command = request.getRequestURI();
		if(command.indexOf(request.getContextPath())==0) {
			command = command.substring(request.getContextPath().length());
		}
		
		// web.xml에서 servlet-mapping에서 url-pattern 을 *.do로 되어있다.
		// properties 파일에서 /hello.do=mvc.hello.HelloHandler 를 해주면 끝
		
		CommandHandler handler = commandHandlerMap.get(command);
		
		if(handler == null) {
			handler = new NullHandler();
		} 
		
		String viewPage = null;
		
		try {
			viewPage = handler.process(request, response);
		} catch (Exception e) {
			//예외가 다시 던져져도 웹서버는 멀쩡히 계속 돌아간다. 에러가난 해당 서블릿 잘 돌아간다.(물론 console에는 에러 메시지는 뜬다)
			//그러니 마지막에 잡히는 예외는 이렇게 ServletException으로 감싸서 보내주자.
			throw new ServletException(e);
		}
		
		
		// handler.process(request, response); 메서드 안에서
		// 1. 리다이렉트를 하고 싶으면 response.sendRedirect(request.getContextPath()+"/where_you_want_to_go"); 를 설정후 null을 반환하면 된다.
		//	1-1. redirect로도 request에 요청을 넣어서 보낼 수 있게 FlashScopeFilter를 만들었다. 쓰는 방법은 
		//		 request.setAttribute("flash.message","안녕하세요")  --> response.sendRedirect(~~.do)  --> ~~.do 에서  ${message} 로 불러낼 수 있다.  
		// 2. 포워드는 반대로 viewPage가 null만 아니며 되는 것이다.
		if(viewPage != null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
			dispatcher.forward(request, response);
		}
		
		/*
		
			2.4 요청 URI를 명령어로 사용하기
			위의 예제는 cmd 파라미터를 명령어로 사용했다. 하지만 문제점이 있다.
			그건 바로 컨트롤러의 URL이 사용자에게 노출된다는 점이다.
			
			?cmd=hello 에다가 다른 다양한 값을 주입할 가능성이 있다. 이런 불필요한 공격 아닌 공격을 방지하려면
			요청 URI 자체를 명령어로 사용하는 것이 좋다. 
			즉, 다음과 같이    "URL의 일부를 명령어"  로 사용하는 것이다.
			
			http://localhost:8080/chap18/hello.do
			http://localhost:8080/chap18/guestbook/list.do
			
			그래서 컨트롤러 서블릿의 process() 메서드에서 request,getParameter("cmd") 대신 다음과 같은 ㅗ드를 사용하면 된다.
			
			
			ex) http://localhost:8080/chap18/hello.do 를 요청
			
			String command = request.getRequestURI();         //   							  "/chap18/hello.do"
			if(command.indexOf(request.getContextPath())==0) {	// request.getContextPath() : "/chap18"
				command = command.substring(request.getContextPath().length()); //			  "/hello.do" 
			}
			
			==> 이렇게만 하면 요청 URI를 명령어로 사용할 수 있다.
			
			이부분만 위 코드에서 바꿔 주자.
		 */
		
	}
	
	
}



