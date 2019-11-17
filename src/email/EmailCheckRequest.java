package email;

import java.util.Map;

public class EmailCheckRequest {
	
	private String id;
	private String code;
	
	public EmailCheckRequest(String id, String code) {
		this.id = id;
		this.code = code;
	}
	
	public EmailCheckRequest() {}

	public String getID() {
		return id;
	}
	
	public void setID(String id) {
		this.id = id;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	@Override
	public String toString() {
		return "EmailCheckRequest [id=" + id + ", code=" + code + "]";
	}
	
	public void validate(Map<String, Boolean> errors) {
		check(errors, id, "id");
		check(errors,code,"code");
	}
	
	private void check(Map<String, Boolean> errors, String value, String fieldName) {
		if(value == null || value.trim().isEmpty()) errors.put(fieldName, Boolean.TRUE);
	}
	
}
