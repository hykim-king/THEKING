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
    public int doSave(BoardDTO param) {
        log.debug("doSave 호출: {}", param);
        return boardMapper.doSave(param);
    }

    @Override
    public int doUpdate(BoardDTO param) {
        log.debug("doUpdate 호출: {}", param);
        return boardMapper.doUpdate(param);
    }

    @Override
    public int doDelete(BoardDTO param) {
        log.debug("doDelete 호출: {}", param);
        return boardMapper.doDelete(param);
    }

    @Override
    public BoardDTO doSelectOne(BoardDTO param) {
        log.debug("doSelectOne 호출: {}", param);
        return boardMapper.doSelectOne(param);
    }

    @Override
    public List<BoardDTO> doRetrieve(SearchDTO param) {
        log.debug("doRetrieve 호출: {}", param);
        return boardMapper.doRetrieve(param);
    }

    @Override
    public int increaseViews(BoardDTO param) {
        log.debug("increaseViews 호출: {}", param);
        return boardMapper.upViews(param.getBoardNo());
    }

    @Override
    public int getCount() {
        log.debug("getCount 호출");
        return boardMapper.getCount();
    }
}
