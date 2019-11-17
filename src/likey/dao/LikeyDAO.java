package likey.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jdbc.JdbcUtil;

public class LikeyDAO {
	
	public int insert(Connection conn, LikeyDTO like) {
		try(PreparedStatement pstmt = conn.prepareStatement("insert into likey(userID,evaluationID,userIP) values(?,?,?)")) {
			pstmt.setString(1, like.getUserID());
			pstmt.setInt(2, like.getEvaluationID());
			pstmt.setString(3, like.getUserIP());
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public LikeyDTO select(Connection conn,String userID, int evaluationID) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select * from likey where userID = ? and evaluationID = ?");
			pstmt.setString(1, userID);
			pstmt.setInt(2, evaluationID);
			
			rs = pstmt.executeQuery();
			LikeyDTO like = null;
			if(rs.next()) {
				like = convert(rs);
			}
			return like;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}
	
	private LikeyDTO convert(ResultSet rs) throws SQLException {
		return new LikeyDTO(rs.getString(1), rs.getInt(2), rs.getString(3));
	}
}
