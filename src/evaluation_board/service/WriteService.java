package evaluation_board.service;

import java.sql.Connection;

import evaluation_board.dao.EvaluationDao;
import evaluation_board.dao.EvaluationDto;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import member.errors.NoChangeException;

public class WriteService {
	
	private EvaluationDao evaluationDao = new EvaluationDao();
	
	public void write(EvaluationDto evaluation) throws RuntimeException {
		
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			int result = evaluationDao.insert(conn, evaluation);
			if(result == 0) throw new NoChangeException();
		} finally {
			JdbcUtil.close(conn);
		}
	}
}
