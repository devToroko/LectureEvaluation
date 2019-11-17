package likey.handler;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import evaluation_board.dao.SearchInfo;
import evaluation_board.errors.NoSuchEvaluationException;
import likey.errors.AlreadyRecommendException;
import likey.service.LikeService;
import likey.service.LikeyRequest;
import login.dto.LoginUser;
import mvc.command.CommandHandler;

public class LikeHandler implements CommandHandler {
	
	private LikeService service = new LikeService();
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod();
		if(method.equalsIgnoreCase("GET") || method.equalsIgnoreCase("POST")) {
			
			Map<String, Boolean> errors = new HashMap<String, Boolean>();
			HttpSession session = request.getSession(false);
			if(session == null) {
				errors.put("noSession", Boolean.TRUE);
			}
			
			LoginUser loginUser = (LoginUser)session.getAttribute("loginUser");
			if(loginUser == null) {
				errors.put("noLogin", Boolean.TRUE);
			}
			
			String RecommendUserID = loginUser.getId();
			int evaluationID = 0;
			int currentPage = 0;
			String lectureDivide = request.getParameter("lectureDivide");
			String searchType = request.getParameter("searchType");
			String search = request.getParameter("search");
			
			try {
				evaluationID = Integer.parseInt(request.getParameter("evaluationID"));
				currentPage = Integer.parseInt(request.getParameter("pageNo"));
			} catch (NumberFormatException e) {
				errors.put("wrongNumberFormat", Boolean.TRUE);				
			}
			
			
			
			if(!errors.isEmpty()) {
				request.setAttribute("errors", errors);
				return url(currentPage, lectureDivide, searchType, search);
			}
			
			SearchInfo searchInfo = new SearchInfo(lectureDivide, searchType, search);
			
			LikeyRequest likeyRequest = new LikeyRequest(currentPage, searchInfo, evaluationID, RecommendUserID,"");
			likeyRequest.getClientIP(request);
			
			System.out.println(likeyRequest);
			
			try {
				service.recommend(likeyRequest);
				request.setAttribute("flash.message", "추천이 완료되었습니다.");
			} catch (NoSuchEvaluationException e) {
				request.setAttribute("flash.message", "존재하지 않는 글을 추천할 수 없습니다");
			} catch (AlreadyRecommendException e) {
				request.setAttribute("flash.message", "중복 추천을 할 수 없습니다.");
			}
			
			response.sendRedirect(request.getContextPath()+url(currentPage, lectureDivide, searchType, search));
			return null;
			
			
		} else {
			response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println("/evaluation.do?pageNo="+"1"+"&lectureDivide="+URLEncoder.encode("전공", "UTF-8")+
						  "&searchType="+URLEncoder.encode("최신순", "UTF-8")+
						  "&search="+URLEncoder.encode("자자자자자", "UTF-8"));
	}
	
	private String url(int pageNo,String lectureDivide, String searchType, String search) {
		try {
			return "/evaluation.do?pageNo="+pageNo+"&lectureDivide="+URLEncoder.encode(lectureDivide, "UTF-8")+
						  "&searchType="+URLEncoder.encode(searchType, "UTF-8")+
						  "&search="+URLEncoder.encode(search, "UTF-8");
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	
}
