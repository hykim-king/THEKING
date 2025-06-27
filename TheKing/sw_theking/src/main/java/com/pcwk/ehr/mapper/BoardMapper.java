package com.pcwk.ehr.mapper;

import java.util.List;

import com.pcwk.ehr.board.domain.BoardDTO;
import com.pcwk.ehr.cmn.SearchDTO;
import com.pcwk.ehr.cmn.WorkDiv;

public interface BoardMapper extends WorkDiv<BoardDTO> {
	int doSave(BoardDTO dto);
    int doUpdate(BoardDTO dto);
    int doDelete(int boardNo);
    BoardDTO doSelectOne(int boardNo);
    List<BoardDTO> doRetrieve(SearchDTO search);
    int upViews(int boardNo);
    int getCount();
}
	

