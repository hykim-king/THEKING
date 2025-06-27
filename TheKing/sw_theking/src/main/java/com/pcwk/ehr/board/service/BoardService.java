package com.pcwk.ehr.board.service;

import java.util.List;
import com.pcwk.ehr.board.domain.BoardDTO;
import com.pcwk.ehr.cmn.SearchDTO;

public interface BoardService {
    int insertBoard(BoardDTO dto);
    int updateBoard(BoardDTO dto);
    int deleteBoard(int boardNo);
    BoardDTO getBoard(int boardNo);
    List<BoardDTO> getBoardList(SearchDTO search);
    int increaseViews(int boardNo);
    int getCount();
}