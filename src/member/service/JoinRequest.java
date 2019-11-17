package member.service;

import java.util.Map;

public class JoinRequest {
	
	private String userID;
	private String userPassword;
	private String userEmail;
	
	public JoinRequest() {}

	public JoinRequest(String userID, String userPassword, String userEmail) {
		this.userID = userID;
		this.userPassword = userPassword;
		this.userEmail = userEmail;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	@Override
	public String toString() {
		return "JoinRequest [userID=" + userID + ", userPassword=" + userPassword + ", userEmail=" + userEmail + "]";
	}
	
	public void Validate(Map<String, Boolean> errors) {
		checkEmpty(errors, userID, "userID");
		checkEmpty(errors, userPassword, "userPassword");
		checkEmpty(errors, userEmail, "userEmail");
	}
	
	private void checkEmpty(Map<String, Boolean> errors,String value, String fieldName) {
		if(value == null || value.isEmpty()) errors.put(fieldName, Boolean.TRUE);
	}
	
}
