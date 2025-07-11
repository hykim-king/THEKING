package com.pcwk.ehr.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.pcwk.ehr.board.domain.BoardDTO;
import com.pcwk.ehr.cmn.PLog;
import com.pcwk.ehr.festival.domain.FestivalDTO;
import com.pcwk.ehr.mapper.BoardMapper;
import com.pcwk.ehr.mapper.FavoritesMapper;
import com.pcwk.ehr.mapper.TourMapper;
import com.pcwk.ehr.tour.domain.TourDTO;

@Controller
public class MainController implements PLog {
	
	@Autowired
	private BoardMapper boardMapper;
	
	@Autowired
	private FavoritesMapper favoritesMapper;
	
	@GetMapping("/main.do")
	public String mainPage(Model model) {
		log.debug("┌─────────────────────────────────┐");
		log.debug("│ UserController()                │");
		log.debug("└─────────────────────────────────┘");
		
		List<BoardDTO> posts = boardMapper.getNoticeList(); // div == '20' 게시글만
	    List<TourDTO> popularTours = favoritesMapper.getPopularTours();
	    List<FestivalDTO> popularFestivals = favoritesMapper.getPopularFestival();
	    
	    model.addAttribute("popularFestivals", popularFestivals);
	    model.addAttribute("popularTours", popularTours);
	    model.addAttribute("posts", posts);
	    
		return "main";
	}
}
