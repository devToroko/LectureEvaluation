package evaluation_board.service;

import java.sql.Connection;
import java.util.List;

import evaluation_board.dao.EvaluationDao;
import evaluation_board.dao.EvaluationDto;
import evaluation_board.dao.PageInfo;
import evaluation_board.dao.SearchInfo;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

public class ListAndSearchService {
	
	private EvaluationDao dao = new EvaluationDao();
	private int size = 5;
	
	public PageInfo getPaging(int pageNum,SearchInfo searchInfo) {
		
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			int total = dao.selectCount(conn, searchInfo);
			List<EvaluationDto> list = dao.select(conn, (pageNum -1)*size , size,searchInfo);
			
			PageInfo pageInfo = new PageInfo(total, pageNum, size, list);
			pageInfo.setSearchInfo(searchInfo);
			
			return pageInfo;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			JdbcUtil.close(conn);
		}
	}
}
