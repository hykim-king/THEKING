package com.pcwk.ehr.festival.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pcwk.ehr.cmn.SearchDTO;
import com.pcwk.ehr.festival.domain.FestivalDTO;
import com.pcwk.ehr.festival.service.FestivalService;

@Controller
@RequestMapping("/festival")
public class FestivalController {
	Logger log = LogManager.getLogger(getClass());
	
	@Autowired
	FestivalService service;
	
	public FestivalController() {
		log.debug("┌─────────────────────────────────┐");
		log.debug("│ FestivalController()            │");
		log.debug("└─────────────────────────────────┘");
	}
	
	@GetMapping("/")
	public String main(Model model) {
		SearchDTO dto = new SearchDTO();
		List<FestivalDTO> list = service.doRetrieve(dto);
		
		model.addAttribute("list",list);
		return "/festival/main";
	}
	
	@GetMapping("doSave.do")
	public String doSave() {
		return "/festival/doSave";
	}
	
	@GetMapping("doUpdate.do")
	public String doUpdate(FestivalDTO param, Model model) {
		model.addAttribute(param);
		return "/festival/doUpdate";
	}
	
	@GetMapping("doDelete.do")
	public String doDelete(FestivalDTO param, HttpServletResponse response) throws IOException {
		int flag = service.doDelete(param);
		if(flag == 1) {
			return "redirect:/";
		}else {
			 // 직접 alert와 자바스크립트 출력
	        response.setContentType("text/html; charset=UTF-8");
	        PrintWriter out = response.getWriter();
	        out.println("<script>alert('삭제에 실패했습니다.');</script>");
	        out.flush();
	        return null; // 이미 응답을 직접 작성했기 때문에 리턴 null
		}
		
	}
	
}
