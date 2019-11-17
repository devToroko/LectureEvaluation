package evaluation_board.dao;

import java.util.Map;

public class EvaluationDto {

	private int evaluationID;
	private String userID;
	private String lectureName;
	private String professorName;
	private int lectureYear;
	private String semesterDivide;
	private String lectureDivide;
	private String evaluationTitle;
	private String evaluationContent;
	private String totalScore;
	private String creditScore;
	private String comfortableScore;
	private String lectureScore;
	private int likeCount;
	
	public void validation(Map<String, Boolean> errors) {
		check(errors, lectureName, "lectureName");
		check(errors, professorName, "professorName");
		check(errors, semesterDivide, "semesterDivide");
		check(errors, lectureDivide, "lectureDivide");
		check(errors, evaluationTitle, "evaluationTitle");
		check(errors, evaluationContent, "evaluationContent");
		check(errors, totalScore, "totalScore");
		check(errors, creditScore, "creditScore");
		check(errors, comfortableScore, "comfortableScore");
		check(errors, lectureScore, "lectureScore");
		
	}
	
	private void check(Map<String, Boolean> errors, String value, String fieldName) {
		if(value == null || value.trim().isEmpty()) errors.put(fieldName, Boolean.TRUE);
	}
	
	
	public EvaluationDto() {}

	public EvaluationDto(int evaluationID, String userID, String lectureName, String professorName, int lectureYear,
			String semesterDivide, String lectureDivide, String evaluationTitle, String evaluationContent,
			String totalScore, String creditScore, String comfortableScore, String lectureScore, int likeCount) {
		super();
		this.evaluationID = evaluationID;
		this.userID = userID;
		this.lectureName = lectureName;
		this.professorName = professorName;
		this.lectureYear = lectureYear;
		this.semesterDivide = semesterDivide;
		this.lectureDivide = lectureDivide;
		this.evaluationTitle = evaluationTitle;
		this.evaluationContent = evaluationContent;
		this.totalScore = totalScore;
		this.creditScore = creditScore;
		this.comfortableScore = comfortableScore;
		this.lectureScore = lectureScore;
		this.likeCount = likeCount;
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

	public String getLectureName() {
		return lectureName;
	}

	public void setLectureName(String lectureName) {
		this.lectureName = lectureName;
	}

	public String getProfessorName() {
		return professorName;
	}

	public void setProfessorName(String professorName) {
		this.professorName = professorName;
	}

	public int getLectureYear() {
		return lectureYear;
	}

	public void setLectureYear(int lectureYear) {
		this.lectureYear = lectureYear;
	}

	public String getSemesterDivide() {
		return semesterDivide;
	}

	public void setSemesterDivide(String semesterDivide) {
		this.semesterDivide = semesterDivide;
	}

	public String getLectureDivide() {
		return lectureDivide;
	}

	public void setLectureDivide(String lectureDivide) {
		this.lectureDivide = lectureDivide;
	}

	public String getEvaluationTitle() {
		return evaluationTitle;
	}

	public void setEvaluationTitle(String evaluationTitle) {
		this.evaluationTitle = evaluationTitle;
	}

	public String getEvaluationContent() {
		return evaluationContent;
	}

	public void setEvaluationContent(String evaluationContent) {
		this.evaluationContent = evaluationContent;
	}

	public String getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(String totalScore) {
		this.totalScore = totalScore;
	}

	public String getCreditScore() {
		return creditScore;
	}

	public void setCreditScore(String creditScore) {
		this.creditScore = creditScore;
	}

	public String getComfortableScore() {
		return comfortableScore;
	}

	public void setComfortableScore(String comfortableScore) {
		this.comfortableScore = comfortableScore;
	}

	public String getLectureScore() {
		return lectureScore;
	}

	public void setLectureScore(String lectureScore) {
		this.lectureScore = lectureScore;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}
	
	
	
	
	/*
	 evaluationID int(11)  PRI auto_increment
	  userID varchar(20)
	 lectureName varchar(50)
	 professorName varchar(20)
	 lectureYear int(11)
	 semesterDivide varchar(20)
	 lectureDivide varchar(10)
	 evaluationTitle varchar(50)
	 evaluationContent varchar(2048)
	 totalScore varchar(5)
	 creditScore varchar(5)
	 comfortableScore varchar(5)
	 lectureScore varchar(5)
	 likeCount int(11)
	 */
}
