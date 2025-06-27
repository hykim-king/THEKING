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

    final Logger log = LogManager.getLogger(getClass());

    @Autowired
    BoardMapper boardMapper;

    @Override
    public int insertBoard(BoardDTO dto) {
        log.debug("insertBoard 호출: {}", dto);
        return boardMapper.doSave(dto);
    }

    @Override
    public int updateBoard(BoardDTO dto) {
        log.debug("updateBoard 호출: {}", dto);
        return boardMapper.doUpdate(dto);
    }

    @Override
    public int deleteBoard(int boardNo) {
        log.debug("deleteBoard 호출: boardNo={}", boardNo);
        return boardMapper.doDelete(boardNo);
    }

    @Override
    public BoardDTO getBoard(int boardNo) {
        log.debug("getBoard 호출: boardNo={}", boardNo);
        return boardMapper.doSelectOne(boardNo);
    }

    @Override
    public List<BoardDTO> getBoardList(SearchDTO search) {
        log.debug("getBoardList 호출: {}", search);
        return boardMapper.doRetrieve(search);
    }

    @Override
    public int increaseViews(int boardNo) {
        log.debug("increaseViews 호출: boardNo={}", boardNo);
        return boardMapper.upViews(boardNo);
    }

    @Override
    public int getCount() {
        log.debug("getCount 호출");
        return boardMapper.getCount();
    }
}