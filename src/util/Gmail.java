package util;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class Gmail extends Authenticator {

	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		//1번째 파라미터에는 본인의 구글 아이디, 2번째 파라미터로는 해당 아이디의 비밀번호를 입력하세요! 
		return new PasswordAuthentication("", "");//관리자 자신의 지메일
	}
	
}
