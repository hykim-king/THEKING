package com.pcwk.ehr.comment.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.pcwk.ehr.cmn.MessageDTO;
import com.pcwk.ehr.comment.domain.CommentDTO;
import com.pcwk.ehr.comment.service.CommentService;
import com.pcwk.ehr.user.domain.UserDTO;

@Controller
@RequestMapping("/comment")
public class CommentController {
	Logger log = LogManager.getLogger(getClass());
	
	@Autowired
	CommentService commentService;
	
	public CommentController() {
	}
	
	@GetMapping("/allComment.do")
    public String getAllComments(Model model) throws SQLException {
        List<CommentDTO> list = commentService.getAllComments();
        model.addAttribute("comments", list);
        return "comment/list"; // → comment/list.jsp 또는 .html
    }
	
	//관광지 댓글 정보 가져오기
	@GetMapping(value = "/CommentTour.do", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getCommentTour(HttpSession session) throws SQLException {
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
	    List<CommentDTO> tourCommentList = commentService.getTourComments(userId);
	
	    // 3. 결과 포장해서 응답
	    Map<String, Object> resultMap = new HashMap<>();
	    resultMap.put("status", 1);
	    resultMap.put("data", tourCommentList);
	
	    jsonString = new Gson().toJson(resultMap);
	    return jsonString;
	}
		
	//축제 댓글 정보 가져오기
	@GetMapping(value = "/CommentFestival.do", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getFestivalComments(HttpSession session) throws SQLException {
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
	    List<CommentDTO> festivalCommentList = commentService.getFestivalComments(userId);
	
	    // 3. 결과 포장해서 응답
	    Map<String, Object> resultMap = new HashMap<>();
	    resultMap.put("status", 1);
	    resultMap.put("data", festivalCommentList);
	
	    jsonString = new Gson().toJson(resultMap);
	    return jsonString;
	}
			
		
}
