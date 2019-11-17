package member.service;

import java.sql.Connection;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import login.errors.PasswordNotEqualException;
import member.dao.UserDAO;
import member.dao.UserDTO;
import member.errors.NoChangeException;

public class ChangePasswordService {
	
	private UserDAO dao = new UserDAO();
	
	public void changePassword(String userID, String currentPwd, String newPwd) {
		
		Connection conn = null;
		
		try {
			conn = ConnectionProvider.getConnection();
			ConnectionProvider.setAutoCommit(conn, false);
			
			UserDTO user = dao.selectById(conn, userID);
			
			if(!user.getUserPassword().equals(currentPwd)) {
				throw new PasswordNotEqualException();
			}
			
			int result = dao.updatePassword(conn, userID, newPwd);
			if(result == 0) {
				throw new NoChangeException();//변경한 비밀번호와 일치할 경우 이런 현상이 일어난다.
			}
			
			JdbcUtil.commit(conn);
			
		} catch (RuntimeException e) {
			JdbcUtil.rollback(conn);
			throw e;
		} finally {
			JdbcUtil.close(conn);
		}
				
		
	}
}
