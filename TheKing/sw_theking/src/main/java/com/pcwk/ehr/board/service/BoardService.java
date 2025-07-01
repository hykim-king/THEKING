package com.pcwk.ehr.board.service;

import java.util.List;

import com.pcwk.ehr.board.domain.BoardDTO;
import com.pcwk.ehr.cmn.SearchDTO;
import com.pcwk.ehr.cmn.WorkDiv;

public interface BoardService extends WorkDiv<BoardDTO> {

    /**
     * 조회수 증가
     * @param param boardNo가 포함된 DTO
     * @return 처리 건수
     */
    int increaseViews(BoardDTO param);

    /**
     * 전체 게시글 수
     * @return 총 게시글 수
     */
    int getCount();
}
