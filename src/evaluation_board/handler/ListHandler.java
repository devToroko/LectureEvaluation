package evaluation_board.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import evaluation_board.dao.PageInfo;
import evaluation_board.dao.SearchInfo;
import evaluation_board.service.ListAndSearchService;
import mvc.command.CommandHandler;

public class ListHandler implements CommandHandler {

//	private ListService listService = new ListService();
	private ListAndSearchService service = new ListAndSearchService();
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String pageNoVal = request.getParameter("pageNo");
		int pageNo = 1;
		if(pageNoVal != null ) {
			try {pageNo = Integer.parseInt(pageNoVal);} catch (NumberFormatException e) {/*ignore*/}
		}
		
		String lectureDivide = request.getParameter("lectureDivide");
		String searchType = request.getParameter("searchType");
		String search = request.getParameter("search");
		
		SearchInfo searchInfo = new SearchInfo(lectureDivide, searchType, search);
		
		PageInfo pageInfo = service.getPaging(pageNo, searchInfo);
		
//		PageInfo pageInfo = listService.getPaging(pageNo);
		request.setAttribute("pageInfo", pageInfo);
		return "/WEB-INF/view/evaluation.jsp";
	}

}
