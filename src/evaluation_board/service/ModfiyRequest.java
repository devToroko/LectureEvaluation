package evaluation_board.service;

import java.util.Map;

public class ModfiyRequest {
	
	private int evaluationID;
	private String writer;//==userID
	private String pNo;
	private String title;
	private String content;
	
	public ModfiyRequest() {}

	public ModfiyRequest(int evaluationID, String writer, String pNo, String title, String content) {
		super();
		this.evaluationID = evaluationID;
		this.writer = writer;
		this.pNo = pNo;
		this.title = title;
		this.content = content;
	}

	public int getEvaluationID() {
		return evaluationID;
	}

	public void setEvaluationID(int evaluationID) {
		this.evaluationID = evaluationID;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getpNo() {
		return pNo;
	}

	public void setpNo(String pNo) {
		this.pNo = pNo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public void validate(Map<String, Boolean> errors) {
		check(errors, writer,"writer");
		check(errors, title, "title");
		check(errors, content, "content");
	}
	
	private void check(Map<String, Boolean> errors, String value, String fieldName) {
		if(value == null || value.trim().isEmpty()) errors.put(fieldName, Boolean.TRUE);
	}

	@Override
	public String toString() {
		return "ModfiyRequest [evaluationID=" + evaluationID + ", writer=" + writer + ", pNo=" + pNo + ", title="
				+ title + ", content=" + content + "]";
	}
	
	
}
