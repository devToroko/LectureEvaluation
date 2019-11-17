package member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jdbc.JdbcUtil;

public class UserDAO {
	
	public UserDTO selectById(Connection conn,String userID) {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement("select * from user where userID = ?");
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			UserDTO userDTO = null;
			if(rs.next()) {
				userDTO = convertToUser(rs);
			}
			return userDTO;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}
	
	public void insert(Connection conn ,UserDTO user) {
		try (PreparedStatement pstmt = conn.prepareStatement("insert into user values(?,?,?,?,false)")){
			pstmt.setString(1, user.getUserID());
			pstmt.setString(2, user.getUserPassword());
			pstmt.setString(3, user.getUserEmail());
			pstmt.setString(4, user.getUserEmailHash());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL 오류 ---> UserDAO",e);
		}  catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} 
	}
	
	public int updateEmailChecked(Connection conn, String primaryKey, boolean changeTo)  {
		try(PreparedStatement pstmt = conn.prepareStatement("update user set userEmailChecked = ? where userID = ?")) {
			pstmt.setBoolean(1, changeTo);
			pstmt.setString(2, primaryKey);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("이메일 인증업데이트 오류",e);
		}
	}

	public int updatePassword(Connection conn, String primaryKey, String changePassword) {
		try(PreparedStatement pstmt = conn.prepareStatement("update user set userPassword = ? where userID = ?")){
			
			pstmt.setString(1, changePassword);
			pstmt.setString(2, primaryKey);
			return pstmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} 
	}
	
	
	
	private UserDTO convertToUser(ResultSet rs) throws SQLException {
		return new UserDTO(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getBoolean(5));
	}
	
}
