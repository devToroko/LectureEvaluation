package login.service;

import java.sql.Connection;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import login.dto.LoginUser;
import login.errors.PasswordNotEqualException;
import member.dao.UserDAO;
import member.dao.UserDTO;
import member.errors.NoSuchUserException;

public class LoginService {
	
	private UserDAO dao = new UserDAO();
	
	public LoginUser login(String userID, String userPassword) {
		
		Connection conn = null;
		try {
			
			conn = ConnectionProvider.getConnection();
			
			UserDTO user = dao.selectById(conn, userID);
			
			if(user == null) {
				throw new NoSuchUserException();
			}
			
			if(!user.getUserPassword().equals(userPassword)) {
				throw new PasswordNotEqualException();
			}
			
			return new LoginUser(userID, user.getUserEmail(), user.getUserEmailChecked());
			
		} finally {
			JdbcUtil.close(conn);
		}
		
	}
	
}
