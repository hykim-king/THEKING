package com.pcwk.ehr.cmn;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.pcwk.ehr.board.domain.BoardDTO;
import com.pcwk.ehr.image.domain.ImageDTO;
import com.pcwk.ehr.mapper.MainMapper;

@Controller
public class HomeController {
	Logger log = LogManager.getLogger(getClass());
	
	@Autowired
	MainMapper mapper;
	
	public HomeController() {
		log.debug("┌─────────────────────────────────┐");
		log.debug("│ HomeController()                │");
		log.debug("└─────────────────────────────────┘");
	}
	
	
	@GetMapping("/home")
	public String home(Model model) {
		List<ImageDTO> tourList = mapper.getPopularTour();
		List<ImageDTO> festaList = mapper.getRecentFestival();
		List<BoardDTO> boardList = mapper.getRecentNotice();
		
		model.addAttribute("tourList",tourList);
		model.addAttribute("festaList",festaList);
		model.addAttribute("boardList",boardList);
		
		return"/home";
	}
	
}
