package evaluation_board.service;

import java.sql.Connection;

import evaluation_board.dao.EvaluationDao;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import member.errors.NoChangeException;

public class DeleteService {
	
	private EvaluationDao dao = new EvaluationDao();
	
	public void eraseEvaluation(String evaluationID) {
		Connection conn = ConnectionProvider.getConnection();
		try {
			int result = dao.delete(conn, evaluationID);
			if(result == 0) throw new NoChangeException();
		} finally {
			JdbcUtil.close(conn);
		}
	}
}
