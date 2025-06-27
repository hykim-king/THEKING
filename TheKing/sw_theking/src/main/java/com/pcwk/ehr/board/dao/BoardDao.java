package com.pcwk.ehr.board.dao;

import java.util.List;
import com.pcwk.ehr.board.domain.BoardDTO;

public interface BoardDao {

    // 게시글 목록 조회
    List<BoardDTO> getBoardList();

    // 게시글 등록
    int insertBoard(BoardDTO dto);

    // 게시글 수정
    int updateBoard(BoardDTO dto);

    // 게시글 삭제
    int deleteBoard(int boardNo);

    // 조회수 증가
    int increaseViews(int boardNo);
}