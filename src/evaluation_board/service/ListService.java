package evaluation_board.service;

import java.sql.Connection;
import java.util.List;

import evaluation_board.dao.EvaluationDao;
import evaluation_board.dao.EvaluationDto;
import evaluation_board.dao.PageInfo;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

public class ListService {
	
	private EvaluationDao dao = new EvaluationDao();
	private int size = 5;
	
	public PageInfo getPaging(int pageNum) {
		
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			int total = dao.selectCount(conn);
			List<EvaluationDto> list = dao.select(conn, (pageNum -1)*size , size);
			return new PageInfo(total, pageNum, size, list);
		} catch (RuntimeException e) {
			throw e;
		} finally {
			JdbcUtil.close(conn);
		}
	}
}
