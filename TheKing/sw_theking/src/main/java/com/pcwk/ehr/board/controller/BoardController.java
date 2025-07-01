package com.pcwk.ehr.board.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pcwk.ehr.board.domain.BoardDTO;
import com.pcwk.ehr.board.service.BoardService;
import com.pcwk.ehr.cmn.SearchDTO;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	final Logger log = LogManager.getLogger(getClass());
	
	@Autowired
	BoardService boardService;
	
	//목록 조회
	@GetMapping("/list")
	public String list(SearchDTO param, Model model) {
		log.debug("/board/list 호출: {}", param);
		
		List<BoardDTO> list = boardService.doRetrieve(param);
		model.addAttribute("list", list);
		
		return "board/list";
	}
	
	//글 상세 보기
	@GetMapping("/view")
	public String view(@RequestParam("boardNo") int boardNo, Model model) {
	    log.debug("/board/view?boardNo={} 호출", boardNo);

	    BoardDTO param = new BoardDTO();
	    param.setBoardNo(boardNo);

	    boardService.increaseViews(param);
	    BoardDTO dto = boardService.doSelectOne(param);

	    model.addAttribute("board", dto);
	    return "board/view";
	}
}
