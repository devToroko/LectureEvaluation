package evaluation_board.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jdbc.JdbcUtil;


public class EvaluationDao {
	
	public EvaluationDto getEvaluation(Connection conn, String evaluationID) {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select * from evaluation where evaluationID = ?");
			pstmt.setString(1, evaluationID);
			rs = pstmt.executeQuery();
			EvaluationDto evaluation = null;
			if(rs.next()) {
				evaluation = convert(rs);
			}
			return evaluation;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}
	
	public int insert(Connection conn, EvaluationDto evaluationDTO) {
		try(PreparedStatement pstmt = 
			conn.prepareStatement("insert into evaluation values (null,?,?,?,?,?,?,?,?,?,?,?,?,0)")) {
			
			pstmt.setString(1, evaluationDTO.getUserID());
			pstmt.setString(2, evaluationDTO.getLectureName());
			pstmt.setString(3,  evaluationDTO.getProfessorName());
			pstmt.setInt(4, evaluationDTO.getLectureYear());
			pstmt.setString(5,  evaluationDTO.getSemesterDivide());
			pstmt.setString(6,  evaluationDTO.getLectureDivide());
			pstmt.setString(7,  evaluationDTO.getEvaluationTitle());
			pstmt.setString(8,  evaluationDTO.getEvaluationContent());
			pstmt.setString(9,  evaluationDTO.getTotalScore());
			pstmt.setString(10, evaluationDTO.getCreditScore());
			pstmt.setString(11, evaluationDTO.getComfortableScore());
			pstmt.setString(12, evaluationDTO.getLectureScore());
			
			return pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public int selectCount(Connection conn) {
		
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select count(*) from evaluation");
			
			int result = -1;
			if(rs.next()) {
				result =  rs.getInt(1);
			}
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
		}
		
	}
	
	public int selectCount(Connection conn, SearchInfo info) {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			
			String sql = "";
			
			if(info.getSearchType().equals("최신순")) {
				sql = "select count(*) from evaluation where lectureDivide LIKE ? AND CONCAT(lectureName,professorName,evaluationTitle,evaluationContent) like ?";
			} else if(info.getSearchType().equals("추천순")) {
				sql= "select count(*) from evaluation where lectureDivide LIKE ? AND CONCAT(lectureName,professorName,evaluationTitle,evaluationContent) like ?" ;
			}  
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+info.getLectureDivide()+"%");
			pstmt.setString(2, "%"+info.getSearch()+"%");
			rs = pstmt.executeQuery();
			int result = 0;
			if(rs.next()) {
				result = rs.getInt(1);
			}
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		
	}
	
	public static void main(String[] args)  {
		EvaluationDao dao = new EvaluationDao();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		SearchInfo info = new SearchInfo("전공","최신순","");
		
		try {
			String dbURL ="jdbc:mysql://localhost:3306/LectureEvaluation?characterEncoding=UTF-8&serverTimezone=UTC";
			String dbID = "jspexam";
			String dbPassword = "jsppw";
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			conn =  DriverManager.getConnection(dbURL,dbID,dbPassword);
			
			System.out.println(new EvaluationDao().selectCount(conn, info));;

//			String sql = "";
//			
//			if(info.getSearchType().equals("최신순")) {
//				sql = "select count(*) from evaluation where lectureDivide LIKE ? AND CONCAT(lectureName,professorName,evaluationTitle,evaluationContent) like ?";
//			} else if(info.getSearchType().equals("추천순")) {
//				sql= "select count(*) from evaluation where lectureDivide LIKE ? AND CONCAT(lectureName,professorName,evaluationTitle,evaluationContent) like ?" ;
//			} 
//			pstmt = conn.prepareStatement(sql);
//			pstmt.setString(1, "%"+info.getLectureDivide()+"%");
//			pstmt.setString(2, "%"+info.getSearch()+"%");
//			rs = pstmt.executeQuery();
//			if(rs.next()) {
//				System.out.println(rs.getInt(1));
//			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(conn);
		}
	}
	
	
	public List<EvaluationDto> select(Connection conn, int startRow, int size) {
		
		List<EvaluationDto> list = new ArrayList<EvaluationDto>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			
			pstmt = conn.prepareStatement("select * from evaluation order by evaluationID desc limit ?,?");
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, size);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				list.add(convert(rs));
			}
			return list;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		
	}
	
	public List<EvaluationDto> select(Connection conn, int startRow, int size,SearchInfo info) {
		
		List<EvaluationDto> list = new ArrayList<EvaluationDto>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = null;
		try {
			if(info.getSearchType().equals("최신순")) {
				query = "select * from evaluation where lectureDivide LIKE ? AND CONCAT(lectureName,professorName,evaluationTitle,evaluationContent)"
						+" LIKE ? order by evaluationID desc limit ?,?";
			} else if(info.getSearchType().equals("추천순")) {
				query = "select * from evaluation where lectureDivide LIKE ? AND CONCAT(lectureName,professorName,evaluationTitle,evaluationContent)"
						+" LIKE ? order by likeCount desc limit ?,?";
			}
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "%"+info.getLectureDivide()+"%");
			pstmt.setString(2, "%"+info.getSearch()+"%");
			pstmt.setInt(3, startRow);
			pstmt.setInt(4, size);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				list.add(convert(rs));
			}
			return list;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		
	}
	
	public int delete(Connection conn, String evaluationID) {
		try(PreparedStatement pstmt = conn.prepareStatement("delete from evaluation where evaluationID = ?")) {
			pstmt.setString(1, evaluationID);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public int update(Connection conn, EvaluationDto evaluationDto) {
		try(PreparedStatement pstmt = conn.prepareStatement("update evaluation set evaluationTitle = ?, evaluationContent = ? where evaluationID = ?")) {
			pstmt.setString(1, evaluationDto.getEvaluationTitle());
			pstmt.setString(2, evaluationDto.getEvaluationContent());
			pstmt.setInt(3, evaluationDto.getEvaluationID());
			
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public EvaluationDto select(Connection conn, int evaluationID) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select * from evaluation where evaluationID = ?");
			pstmt.setInt(1, evaluationID);
			rs = pstmt.executeQuery();
			EvaluationDto evaluation = null;
			if(rs.next()) {
				evaluation = convert(rs);
			}
			return evaluation;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}
	
	
	
	//검색을 위한 것이다.
	public int selectCount(Connection conn, String lectureDivide, String searchType, String search) {
		
		String query = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			if(searchType.equals("최신순")) {
				query = "select count(*) from evaluation where lectureDivide LIKE ? AND CONCAT(lectureName,professorName,evaluationTitle,evaluationContent)"
						+" LIKE ? order by evaluationID";
			} else if(searchType.equals("추천순")) {
				query = "select count(*) from evaluation where lectureDivide LIKE ? AND CONCAT(lectureName,professorName,evaluationTitle,evaluationContent)"
						+" LIKE ? order by likeCount";
			}
			
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "%"+lectureDivide+"%");
			pstmt.setString(2, "%"+search+"%");
			rs = pstmt.executeQuery();
			
			int result = -1;
			if(rs.next()) {
				result =  rs.getInt(1);
			}
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		
	}
	
	public List<EvaluationDto> select(Connection conn, String searchType, String lectureDivide, String search,
			int startRow, int size) {
		
		List<EvaluationDto> list = new ArrayList<EvaluationDto>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			
			pstmt = conn.prepareStatement("select * from evaluation order by evaluationID desc limit ?,?");
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, size);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				list.add(convert(rs));
			}
			return list;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
		
	}

	public int updateLike(Connection conn,int evalautionID) {
		try(PreparedStatement pstmt = conn.prepareStatement("update evaluation set likeCount=likeCount+1 where evaluationID = ?")) {
			pstmt.setInt(1, evalautionID);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	private EvaluationDto convert(ResultSet rs) throws SQLException {
		return new EvaluationDto(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),
				rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11),
				rs.getString(12),rs.getString(13),rs.getInt(14));
	}
}
