package member.ajax;

import java.sql.Connection;
import java.sql.SQLException;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import member.dao.UserDAO;
import member.dao.UserDTO;
import member.errors.DuplicateUserException;
import member.service.JoinRequest;
import util.SHA256;

public class JoinAjaxService {
private UserDAO userDAO = new UserDAO();

	public void join(JoinRequest joinReq) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			//중복회원 검사
			UserDTO user = userDAO.selectById(conn, joinReq.getUserID());
			if(user != null) {
				JdbcUtil.rollback(conn);
				throw new DuplicateUserException();//중복에 의한 것
			}
			//삽입!
			userDAO.insert(conn, new UserDTO(joinReq.getUserID(),joinReq.getUserPassword(),
							joinReq.getUserEmail(),SHA256.getSha256(joinReq.getUserEmail()),false));
			
			conn.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);	//SQL에 의한 것
		} finally {
			JdbcUtil.close(conn);
		}
	}
}
