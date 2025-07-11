package com.pcwk.ehr.mapper;

import java.util.List;

import com.pcwk.ehr.board.domain.BoardDTO;
import com.pcwk.ehr.cmn.SearchDTO;
import com.pcwk.ehr.cmn.WorkDiv;

public interface BoardMapper extends WorkDiv<BoardDTO> {
	int getBoardSeq();

	int deleteAll();

	int getCount();

	int doSaveAll();

	/**
	 * 조회수 증가 (단, 본인글 이외 글만)
	 * 
	 * @param param
	 * @return
	 */
	int updateReadCnt(BoardDTO param);
	
	List<BoardDTO> getNoticeList();
	
	List<BoardDTO> getBoardList();
	
	List<BoardDTO> getNoticeListAll(String userId);
	
	List<BoardDTO> getBoardListAll(String userId);
}
