package login.dto;

//로그인 세션에서 필요한 것은 회원 아이디(userID), 회원 이메일(userEmail) 그리고 이메일 인증 여부(userEmailChecked)이다.
//이메일과 인증여부는 이메일 인증 재전송 때 사용하게 된다.
//인증 여부가 false인 사용자는 index에 후에 못들어가게 될것이다.
public class LoginUser {
	

	private String id;
	private String email;
	private boolean checked;
	
	public LoginUser(String id, String email, boolean checked) {
		this.id = id;
		this.email = email;
		this.checked = checked;
	}
	public LoginUser() {}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public boolean isChecked() {
		return checked;
	}
	
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	@Override
	public String toString() {
		return "LoginUser [id=" + id + ", email=" + email + ", checked=" + checked + "]";
	}
	
	
	
}
