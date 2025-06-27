package com.pcwk.ehr.board.dao;

import java.util.List;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.pcwk.ehr.board.domain.BoardDTO;

@Repository
public class BoardDaoImpl implements BoardDao {

	private static final String NAMESPACE = "com.pcwk.ehr.board";

	@Autowired
	SqlSessionTemplate sqlSessionTemplate;

	@Override
	public List<BoardDTO> getBoardList() {
		return sqlSessionTemplate.selectList(NAMESPACE + ".getBoardList");
	}

	@Override
	public int insertBoard(BoardDTO dto) {
		return sqlSessionTemplate.insert(NAMESPACE + ".insertBoard", dto);
	}

	@Override
	public int updateBoard(BoardDTO dto) {
		return sqlSessionTemplate.update(NAMESPACE + ".updateBoard", dto);
	}

	@Override
	public int deleteBoard(int boardNo) {
		return sqlSessionTemplate.delete(NAMESPACE + ".deleteBoard", boardNo);
	}

	@Override
	public int increaseViews(int boardNo) {
		return sqlSessionTemplate.update(NAMESPACE + ".increaseViews", boardNo);
	}
}