package evaluation_board.service;

import java.sql.Connection;

import evaluation_board.dao.EvaluationDao;
import evaluation_board.dao.EvaluationDto;
import evaluation_board.errors.NoSuchEvaluationException;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

public class ModifyService {

	private EvaluationDao dao = new EvaluationDao();
	
	public void changeEvaluation(ModfiyRequest request) {
		Connection conn = null;
		
		try {
			
			conn = ConnectionProvider.getConnection();
			ConnectionProvider.setAutoCommit(conn, false);
			EvaluationDto evalaution = dao.select(conn, request.getEvaluationID());
			
			if(evalaution == null) throw new NoSuchEvaluationException();
			
			evalaution.setEvaluationTitle(request.getTitle());
			evalaution.setEvaluationContent(request.getContent());
			evalaution.setEvaluationID(request.getEvaluationID());
			
			dao.update(conn, evalaution);
			
			JdbcUtil.commit(conn);
			
		} catch (RuntimeException e) {
			JdbcUtil.rollback(conn);
			throw e;
		}finally {
			JdbcUtil.close(conn);
		}
		
	}
}
