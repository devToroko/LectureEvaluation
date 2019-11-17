package util;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import email.errors.EmailSendExcpetion;

public class EmailService {
	
	public static void sendEmail(String userID,String userEmail) {
		
		String host = "http://localhost:8080/DevToroko_MVC_Pattern_WEB/";
		String from = "qkrtkddjs12345@gmail.com";
		String to = userEmail;
		String subject = "강의평가를 위한 이메일 인증 메일입니다.";
		String content = "<h1>다음 링크에 접속하여 이메일 인증을 진행하세요<h1>" +
						"<a href ='"+host+"emailCheck.do?id="+userID+"&code="+ SHA256.getSha256(to)+"'> 이메일 인증하기 </a>";
		
		Properties p = new Properties();
		p.put("mail.smtp.user", from);
		p.put("mail.smtp.host", "smtp.googlemail.com");
		p.put("mail.smtp.port", "456");
		p.put("mail.smtp.starttls.enable", "true");
		p.put("mail.smtp.auth", "true");
		p.put("mail.smtp.debug", "true");
		p.put("mail.smtp.socketFactory.port", "465");
		p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		p.put("mail.smtp.socketFactory.fallback", "false");
		
		
		try{
			Authenticator auth = new Gmail();
			Session ses = Session.getInstance(p,auth);
			ses.setDebug(true);
			MimeMessage msg = new MimeMessage(ses);
			msg.setSubject(subject);
			Address fromAddr = new InternetAddress(from);
			msg.setFrom(fromAddr);
			Address toAddr = new InternetAddress(to);
			msg.addRecipient(Message.RecipientType.TO, toAddr);
			msg.setContent(content,"text/html;charset=UTF8");
			Transport.send(msg);
		} catch (MessagingException e) {
			e.printStackTrace();
			throw new EmailSendExcpetion();
		}
	}
}
