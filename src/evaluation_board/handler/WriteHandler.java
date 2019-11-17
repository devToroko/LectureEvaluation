package evaluation_board.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import evaluation_board.dao.EvaluationDto;
import evaluation_board.errors.NoChageException;
import evaluation_board.service.WriteService;
import login.dto.LoginUser;
import mvc.command.CommandHandler;

public class WriteHandler implements CommandHandler {
	
	private WriteService service =  new WriteService();
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(request.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(request, response);
		} else {
			response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}
	
	private String processSubmit(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		
		String pNoVal = request.getParameter("pNo");
		int pNo=1;
		if(pNoVal == null) {
			try {pNo = Integer.parseInt(pNoVal);} catch (NumberFormatException e) {/*ignore*/}
		} 
		LoginUser user = (LoginUser)request.getSession().getAttribute("loginUser");
		String userID = user.getId();
		String lectureName = request.getParameter("lectureName");
		String professorName = request.getParameter("professorName");
		int lectureYear = Integer.parseInt(request.getParameter("lectureYear"));
		String semesterDivide = request.getParameter("semesterDivide");
		String lectureDivide = request.getParameter("lectureDivide");
		String evaluationTitle = request.getParameter("evaluationTitle");
		String evaluationContent = request.getParameter("evaluationContent");
		String totalScore = request.getParameter("totalScore");
		String creditScore = request.getParameter("creditScore");
		String comfortableScore = request.getParameter("comfortableScore");
		String lectureScore = request.getParameter("lectureScore");
		
		
		
		EvaluationDto evaluation 
				= new EvaluationDto(0,userID,lectureName,professorName,lectureYear,semesterDivide,
						lectureDivide,evaluationTitle,evaluationContent,totalScore,
						creditScore,comfortableScore,lectureScore,0);
		
		// 입력란 체크.
		Map<String, Boolean> errors = new HashMap<String, Boolean>();
		evaluation.validation(errors);
//		request.setAttribute("errors", errors);
		
		if(!errors.isEmpty()) {
			request.setAttribute("flash.message", "게시글 작성란에 공백만 남기고 제시할 수는 없습니다. 다시 작성해주세요.");
			
		} else {
			try {
				service.write(evaluation);
				request.setAttribute("flash.message", "글이 게시되었습니다.");
			} catch (NoChageException e) {
				request.setAttribute("flash.message", "데이터베이스에 입력이 되지않았습니다. 운영자에게 문의해주시기 바랍니다.");
			}
		}
		
		response.sendRedirect(request.getContextPath()+"/evaluation.do?pageNo="+pNo);
		return null;
		//메시지 체크
	}
}
