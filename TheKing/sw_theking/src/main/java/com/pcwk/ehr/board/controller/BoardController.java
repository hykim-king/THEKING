package com.pcwk.ehr.board.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.google.gson.Gson;
import com.pcwk.ehr.board.domain.BoardDTO;
import com.pcwk.ehr.board.service.BoardService;
import com.pcwk.ehr.cmn.MessageDTO;
import com.pcwk.ehr.cmn.PcwkString;
import com.pcwk.ehr.cmn.SearchDTO;

@Controller
@RequestMapping("/board")
public class BoardController {
	Logger log = LogManager.getLogger(getClass());

	@Autowired
	BoardService service;

	public BoardController() {
		log.debug("┌───────────────────────────┐");
		log.debug("│ *BoardController()*       │");
		log.debug("└───────────────────────────┘");
	}
	
	@GetMapping("/doSaveView.do")
	public String doSaveView(HttpServletRequest request,Model model) {
		String viewString = "board/board_reg";
		log.debug("┌───────────────────────────┐");
		log.debug("│ *doSaveView()*            │");
		log.debug("└───────────────────────────┘");
		log.debug("viewString:{}",viewString);
		
		model.addAttribute("divValue",request.getParameter("div") );
		
		return viewString;
	}

	@GetMapping("/doRetrieve.do")
	public String doRetrieve(SearchDTO param, Model model) {
		String viewName = "board/board_list";
		log.debug("┌───────────────────────────┐");
		log.debug("│ *doRetrieve()*            │");
		log.debug("└───────────────────────────┘");

		int pageNo = PcwkString.nvlZero(param.getPageNo(), 1);
		int pageSize = PcwkString.nvlZero(param.getPageSize(), 10);

		// 게시구분 : 공지사항(10)
		String div = PcwkString.nvlString(param.getDiv(), "10");

		// 검색 구분
		String searchDiv = PcwkString.nullToEmpty(param.getSearchDiv());

		// 검색어
		String searchWord = PcwkString.nullToEmpty(param.getSearchWord());
		
		log.debug("pageNo:{}",pageNo);
		log.debug("pageSize:{}",pageSize);
		log.debug("div:{}",div);
		log.debug("searchDiv:{}",searchDiv);
		log.debug("searchWord:{}",searchWord);
		
		param.setPageNo(pageNo);
		param.setPageSize(pageSize);
		param.setDiv(div);
		param.setSearchDiv(searchDiv);
		param.setSearchWord(searchWord);
		
		log.debug("param:{}",param);
		List<BoardDTO> list = service.doRetrieve(param);
		
		model.addAttribute("list",list);
		
		//총 글수
		int totalCnt = 0;
		
		if(list != null && list.size()>0) {
			BoardDTO totalVO = list.get(0);
			totalCnt = totalVO.getTotalCnt();
		}
		log.debug("totalCnt:{}",totalCnt);
		model.addAttribute("totalCnt",totalCnt);
		model.addAttribute("divValue",div);
		//Form Submit : 파라메터 값 유지
		model.addAttribute("search", param);

		return viewName;
	}

	@GetMapping(value = "/doSelectOne.do")
	public String doSelectOne(BoardDTO param, Model model, HttpServletRequest req) {
		log.debug("┌───────────────────────────┐");
		log.debug("│ *doSelectOne()*           │");
		log.debug("└───────────────────────────┘");
		log.debug("1. param:{}", param);
		String viewName = "board/board_mod";
		
		//게시구분 : 공지사항 (10)
		String div = PcwkString.nvlString(param.getDiv(), "10");

		BoardDTO outVO = service.doSelectOne(param);
		log.debug("2. outVO:{}", outVO);
		
		model.addAttribute("vo", outVO);
		model.addAttribute("divValue",div);

		return viewName;
	}

	@PostMapping(value = "/doUpdate.do", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String doUpdate(BoardDTO param, HttpServletRequest req) {
		log.debug("┌───────────────────────────┐");
		log.debug("│ *doUpdate()*              │");
		log.debug("└───────────────────────────┘");
		log.debug("1. param:{}", param);
		String jsonString = "";

		int flag = service.doUpdate(param);

		String message = "";
		if (1 == flag) {
			message = "수정 되었습니다.";
		} else {
			message = "수정 실패!";
		}

		return new Gson().toJson(new MessageDTO(flag, message));
	}

	@PostMapping(value = "/doDelete.do", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String doDelete(BoardDTO param, HttpServletRequest req) {
		log.debug("┌───────────────────────────┐");
		log.debug("│ *doDelete()*              │");
		log.debug("└───────────────────────────┘");
		log.debug("1. param:{}", param);
		String jsonString = "";
		int flag = service.doDelete(param);

		String message = "";
		if (1 == flag) {
			message = "삭제 되었습니다.";
		} else {
			message = "삭제 실패!";
		}

		jsonString = new Gson().toJson(new MessageDTO(flag, message));
		log.debug("2.jsonString:{}", jsonString);
		return jsonString;
	}

	// 화면 목록 : /board/board_list
	// 등록 : /board/board_reg
	@PostMapping(value = "doSave.do", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String doSave(BoardDTO param) {
		log.debug("┌───────────────────────────┐");
		log.debug("│ *doSave()*                │");
		log.debug("└───────────────────────────┘");
		String jsonString = "";
		log.debug("param:{}", param);

		int flag = service.doSave(param);
		String message = "";

		if (1 == flag) {
			message = param.getTitle() + "등록되었습니다.";
		} else {
			message = param.getTitle() + "등록 실패";
		}

		MessageDTO messageDTO = new MessageDTO(flag, message);
		jsonString = new Gson().toJson(messageDTO);
		log.debug("jsonString:{}", jsonString);
		return jsonString;
	}
}
