package evaluation_board.dao;

import java.util.List;

//페이징 처리와 관련된 정보를 담는 클래스입니다.
public class PageInfo {
	
	private int total;
	private int currentPage;
	private List<EvaluationDto> list;
	private int totalPages;
	private int startPage;
	private int endPage;
	private boolean prev;
	private boolean next;
	private SearchInfo searchInfo;
	
	public PageInfo() {	}
	
	
	public PageInfo(int total, int currentPage, int size, List<EvaluationDto> list) {
		this.total = total;
		this.currentPage = currentPage;
		this.list = list;
		if(total == 0) {
			totalPages = 0;
			startPage = 0;
			endPage = 0;
		} else {
			totalPages = total/size;
			if(total%size > 0) totalPages++;
			
			int modVal = currentPage%5;
			startPage = currentPage/5*5+1;
			if(modVal == 0) startPage -= 5;
			
			endPage = startPage + 4;
			if(endPage > totalPages) endPage = totalPages;
			
			if(startPage != 1) prev = true;
			if(endPage < totalPages) next = true;
			
		}
		
	}
	
	public boolean hasNoPage() {
		return total == 0;
	}
	
	
	public boolean hasPages() {
		return total > 0;
	}


	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public List<EvaluationDto> getList() {
		return list;
	}
	public void setList(List<EvaluationDto> list) {
		this.list = list;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}


	public SearchInfo getSearchInfo() {
		return searchInfo;
	}

	public void setSearchInfo(SearchInfo searchInfo) {
		this.searchInfo = searchInfo;
	}
	
	
	
}
