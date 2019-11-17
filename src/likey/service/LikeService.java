package likey.service;

import java.sql.Connection;

import evaluation_board.dao.EvaluationDao;
import evaluation_board.dao.EvaluationDto;
import evaluation_board.errors.NoSuchEvaluationException;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import likey.dao.LikeyDAO;
import likey.dao.LikeyDTO;
import likey.errors.AlreadyRecommendException;

public class LikeService {
	
	private LikeyDAO LikeDao = new LikeyDAO();
	private EvaluationDao evalDao = new EvaluationDao();
	
	public void recommend(LikeyRequest request) {
		Connection conn = null;
		try {
			
			conn = ConnectionProvider.getConnection();
			ConnectionProvider.setAutoCommit(conn, false);
			
			EvaluationDto eval = evalDao.getEvaluation(conn, String.valueOf(request.getEvaluationID()));
			if(eval==null) throw new NoSuchEvaluationException();
			evalDao.updateLike(conn, request.getEvaluationID());
			
			LikeyDTO like = LikeDao.select(conn, request.getUserID(), request.getEvaluationID());
			if(like != null) throw new AlreadyRecommendException();
			LikeDao.insert(conn, new LikeyDTO(request.getUserID(), request.getEvaluationID(), request.getUserIP()));
			
			JdbcUtil.commit(conn);
		} catch (RuntimeException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
			throw e;
		} finally {
			JdbcUtil.close(conn);
		}
	}
}
