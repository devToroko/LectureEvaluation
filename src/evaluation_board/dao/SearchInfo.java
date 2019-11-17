package evaluation_board.dao;

public class SearchInfo {
	
	private String lectureDivide;
	private String searchType;
	private String search;
	
	public SearchInfo() {}

	public SearchInfo(String lectureDivide, String searchType, String search) {
		
			
		if(lectureDivide == null || lectureDivide.equals("전체")) {
			this.lectureDivide = "";
		} else {
			this.lectureDivide = lectureDivide;
		}
		
		if(searchType == null || searchType.equals("")) {
			this.searchType = "최신순";
		} else {
			this.searchType = searchType;
		}
		
		if(search == null) {
			this.search = "";
		} else {
			this.search = search;
		}
		
	}

	public String getLectureDivide() {
		return lectureDivide;
	}

	public void setLectureDivide(String lectureDivide) {
		this.lectureDivide = lectureDivide;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	@Override
	public String toString() {
		return "SearchInfo [lectureDivide=" + lectureDivide + ", searchType=" + searchType + ", search=" + search + "]";
	}
	
	
	
	
}
