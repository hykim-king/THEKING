package com.pcwk.ehr.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.pcwk.ehr.cmn.PLog;

//임시로 만듬 삭제 예정
@Controller
public class MainController implements PLog {
	
	@GetMapping("/main.do")
	public String mainPage() {
		log.debug("┌─────────────────────────────────┐");
		log.debug("│ UserController()                │");
		log.debug("└─────────────────────────────────┘");
		return "main";
	}
}
