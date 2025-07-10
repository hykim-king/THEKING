package com.pcwk.ehr.board.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pcwk.ehr.board.domain.BoardDTO;
import com.pcwk.ehr.cmn.SearchDTO;
import com.pcwk.ehr.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService {

	Logger log = LogManager.getLogger(getClass());
	
	@Autowired
	BoardMapper mapper;
	
	
	public BoardServiceImpl() {}

	@Override
	public List<BoardDTO> doRetrieve(SearchDTO param) {
		return mapper.doRetrieve(param);
	}

	@Override
	public int doDelete(BoardDTO param) {
		return mapper.doDelete(param);
	}

	@Override
	public int doUpdate(BoardDTO param) {
		return mapper.doUpdate(param);
	}

	@Override
	public int doSave(BoardDTO param) {
		return mapper.doSave(param);
	}

	@Override
	public BoardDTO doSelectOne(BoardDTO param) {
		//단건 조회 + 조회 count 증가
		int flag = mapper.updateReadCnt(param);
		log.debug("flag:{}",flag);
		return mapper.doSelectOne(param);
	}
}
