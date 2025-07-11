package com.pcwk.ehr.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.pcwk.ehr.board.domain.BoardDTO;
import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.mapper.BoardMapper;

@Controller
public class MainController implements PLog {
	
	@Autowired
	private BoardMapper boardMapper;
	
	@GetMapping("/main.do")
	public String mainPage(Model model) {
		log.debug("┌─────────────────────────────────┐");
		log.debug("│ UserController()                │");
		log.debug("└─────────────────────────────────┘");
		
		List<BoardDTO> posts = boardMapper.getNoticeList(); // div == '20' 게시글만
	    log.debug("공지사항 개수: {}", posts.size());

	    model.addAttribute("posts", posts);
	    
		return "main";
	}
}
