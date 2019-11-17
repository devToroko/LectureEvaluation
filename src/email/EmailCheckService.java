package email;

import java.sql.Connection;

import email.errors.AlreadyCheckedException;
import email.errors.EmailHashCodeNotEqualException;
import email.errors.NoChangeException;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import member.dao.UserDAO;
import member.dao.UserDTO;
import member.errors.NoSuchUserException;

public class EmailCheckService {
	
	private UserDAO userDAO = new UserDAO();
	
	public void checkEmail(EmailCheckRequest emailReq)  {
		Connection conn = null;
		
		try{
			conn = ConnectionProvider.getConnection();
			ConnectionProvider.setAutoCommit(conn, false);
			
			UserDTO user = userDAO.selectById(conn, emailReq.getID()); 
			
			System.out.println(user);
			
			if(user == null) {				//존재하지 않는 회원
				throw new NoSuchUserException();
			} 
			
			if(user.getUserEmailChecked()) {//이미 이메일 인증이 된 사람이라면
				throw new AlreadyCheckedException();
			}
			
			if(!user.getUserEmailHash().equals(emailReq.getCode())) {//해쉬값이 틀릴 경우
				throw new EmailHashCodeNotEqualException();
			}
			
			int result = userDAO.updateEmailChecked(conn, user.getUserID(), true);
			
			if(result == 0) {	//변화가 없다면?
				throw new NoChangeException();
			}
			
			JdbcUtil.commit(conn);
			
		}  finally {
			JdbcUtil.close(conn);
		}
		
	}
}
