package likey.service;

import javax.servlet.http.HttpServletRequest;

import evaluation_board.dao.SearchInfo;

public class LikeyRequest {
	
	private int currentPage;
	private SearchInfo searchInfo;
	private int evaluationID;
	private String userID;	//작성자가 아니라 추천하려는 사람의 아이디다.
	private String userIP;
	
	
	public LikeyRequest() {}

	public LikeyRequest(int currentPage, SearchInfo searchInfo, int evaluationID, String userID,String userIP) {
		this.currentPage = currentPage;
		this.searchInfo = searchInfo;
		this.evaluationID = evaluationID; 
		this.userID = userID;
		this.userIP = userIP;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public SearchInfo getSearchInfo() {
		return searchInfo;
	}

	public void setSearchInfo(SearchInfo searchInfo) {
		this.searchInfo = searchInfo;
	}

	public int getEvaluationID() {
		return evaluationID;
	}

	public void setEvaluationID(int evaluationID) {
		this.evaluationID = evaluationID;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	public String getUserIP() {
		return userIP;
	}

	public void setUserIP(String userIP) {
		this.userIP = userIP;
	}

	@Override
	public String toString() {
		return "LikeyRequest [currentPage=" + currentPage + ", searchInfo=" + searchInfo + ", evaluationID="
				+ evaluationID + ", userID=" + userID + "]";
	}
	
	public void getClientIP(HttpServletRequest request) {
		String ip = request.getHeader("X-FORWARDED-FOR");
		if(ip == null || ip.length() == 0) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0) {
			ip = request.getRemoteAddr();
		}
		this.userIP = ip;
	}
	
}
