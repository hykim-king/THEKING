package com.pcwk.ehr.favorites.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.pcwk.ehr.cmn.MessageDTO;
import com.pcwk.ehr.comment.service.CommentService;
import com.pcwk.ehr.favorites.service.FavoritesService;
import com.pcwk.ehr.festival.domain.FestivalDTO;
import com.pcwk.ehr.tour.domain.TourDTO;
import com.pcwk.ehr.user.domain.UserDTO;

@Controller
@RequestMapping("/favorites")
public class FavoritesController {
	Logger log = LogManager.getLogger(getClass());
	
	@Autowired
	FavoritesService favoritesService;
	
	//관광지 즐겨찾기 정보 가져오기
	@GetMapping(value = "/favoriteTour.do", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getFavoriteTour(HttpSession session) throws SQLException {
		String jsonString = "";
	
	    // 1. 로그인 여부 확인
	    UserDTO loginUser = (UserDTO) session.getAttribute("user");
	    
	    if (loginUser == null) {
	        MessageDTO failMessage = new MessageDTO(0, "로그인이 필요합니다.");
	        jsonString = new Gson().toJson(failMessage);
	        return jsonString;
	    }
	
	    // 2. 즐겨찾기 축제 리스트 조회
	    String userId = loginUser.getUserId();
	    List<TourDTO> favoriteTourList = favoritesService.getFavoriteTours(userId);
	
	    // 3. 결과 포장해서 응답
	    Map<String, Object> resultMap = new HashMap<>();
	    resultMap.put("status", 1);
	    resultMap.put("data", favoriteTourList);
	
	    jsonString = new Gson().toJson(resultMap);
	    return jsonString;
	}
		
	//축제 즐겨찾기 정보 가져오기
	@GetMapping(value = "/favoriteFestival.do", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getFavoriteFestivals(HttpSession session) throws SQLException {
		String jsonString = "";
	
	    // 1. 로그인 여부 확인
	    UserDTO loginUser = (UserDTO) session.getAttribute("user");
	    
	    if (loginUser == null) {
	        MessageDTO failMessage = new MessageDTO(0, "로그인이 필요합니다.");
	        jsonString = new Gson().toJson(failMessage);
	        return jsonString;
	    }
	
	    // 2. 즐겨찾기 축제 리스트 조회
	    String userId = loginUser.getUserId();
	    List<FestivalDTO> favoriteFestivalList = favoritesService.getFavoriteFestivals(userId);
	
	    // 3. 결과 포장해서 응답
	    Map<String, Object> resultMap = new HashMap<>();
	    resultMap.put("status", 1);
	    resultMap.put("data", favoriteFestivalList);
	
	    jsonString = new Gson().toJson(resultMap);
	    return jsonString;
	}
}
