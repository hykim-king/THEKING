package com.pcwk.ehr.comment.controller;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@GetMapping("doDelete.do")
	public String doDelete(int comNo, String tableName,int targetNo) {
		CommentDTO commentDTO = new CommentDTO();
		commentDTO.setComNo(comNo);

		int flag = commentService.doDelete(commentDTO);
		log.debug("tableName:{}",tableName);
		if(flag == 1) {
			if(tableName.equals("festival")) {
				return "redirect:/festival/doSelectOne.do?festaNo="+targetNo;
			}else {
				return "redirect:/tour/doSelectOne.do?tourNo="+targetNo;
			}
		}else {
			return null;
		}
		
	}
	
	
	@GetMapping("/allComment.do")
    public String getAllComments(HttpSession session, Model model) throws SQLException {
		UserDTO user = (UserDTO) session.getAttribute("loginUser");
		List<CommentDTO> list = commentService.getAllComments(user.getUserId());
        model.addAttribute("comments", list);
        return "comment/list"; 
    }
	//상세 페이지 안에 넣을 view - JSP 조각
	@GetMapping("/festaCommentsList.do")
	public String reloadFestaList(@RequestParam("festaNo") int festaNo, Model model) throws SQLException {
	    List<CommentDTO> list = commentService.getCommentsByTarget(festaNo, "festaNo");
	    
	    // 날짜 포맷을 위한 설정
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	    // 날짜를 포맷해서 DTO에 다시 셋팅
	    for (CommentDTO comment : list) {
	        if (comment.getRegDt() == null && comment.getRegDt() != null) {
	            comment.setRegDt(sdf.format(comment.getRegDt()));
	        }
	    }
	    
	    model.addAttribute("list", list);
	    return "comment/comment_list"; //상세 페이지 안에 넣을 view - JSP 조각
	}
	
	//댓글 목록 조회(festival)
	@GetMapping(value = "/listByfestaNo.do", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String listByfestaNo(@RequestParam("festaNo") int festaNo) throws SQLException {
	    List<CommentDTO> commentList = commentService.getCommentsByTarget(festaNo, "festaNo");
	    
	    Map<String, Object> resultMap = new HashMap<>();
	    resultMap.put("status", 1);
	    resultMap.put("list", commentList);

	    return new Gson().toJson(resultMap);
	} //ajax 요청용
	
	//댓글 등록 festival
	@PostMapping(value = "/festaNoCommentsadd.do", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public MessageDTO festaAddComment(@RequestBody CommentDTO commentDTO, HttpSession session) throws SQLException {

		UserDTO user = (UserDTO) session.getAttribute("loginUser");
		
		
		//테스트용
//	    if (user != null) {
//	        commentDTO.setUserId(user.getUserId());
//	    } else {
//	        commentDTO.setUserId("guest");  
//	    }

		if (user == null) {
			return new MessageDTO(0, "로그인이 필요합니다.");
		}
		
		commentDTO.setUserId(user.getUserId());
	    commentDTO.setTableName("festival");
	    
	    int result = commentService.doSave(commentDTO);
	    String message = "";
	    		
	    if(result == 1) {
	    	message = "댓글 등록 성공!";
	    }else {
	    	message = "댓글 등록 실패!";
	    }
		
	    return new MessageDTO(result, message);
	}
	
	//상세 페이지 안에 넣을 view - JSP 조각
		@GetMapping("/commentsList.do")
		public String reloadList(@RequestParam("tourNo") int tourNo, Model model) throws SQLException {
		    List<CommentDTO> list = commentService.getCommentsByTarget(tourNo, "tour");
		    
		    // 날짜 포맷을 위한 설정
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		    // 날짜를 포맷해서 DTO에 다시 셋팅
		    for (CommentDTO comment : list) {
		        if (comment.getRegDt() == null && comment.getRegDt() != null) {
		            comment.setRegDt(sdf.format(comment.getRegDt()));
		        }
		    }
		    
		    model.addAttribute("list", list);
		    return "comment/comment_list"; //상세 페이지 안에 넣을 view - JSP 조각
		}
		
		//댓글 목록 조회(Tour)
		@GetMapping(value = "/listByTourNo.do", produces = "application/json;charset=UTF-8")
		@ResponseBody
		public String listByTourNo(@RequestParam("tourNo") int tourNo) throws SQLException {
		    List<CommentDTO> commentList = commentService.getCommentsByTarget(tourNo, "tour");
		    
		    Map<String, Object> resultMap = new HashMap<>();
		    resultMap.put("status", 1);
		    resultMap.put("list", commentList);

		    return new Gson().toJson(resultMap);
		} //ajax 요청용
		
		//댓글 등록 tour
		@PostMapping(value = "/tourCommentsadd.do", produces = "application/json;charset=UTF-8")
		@ResponseBody
		public MessageDTO addComment(@RequestBody CommentDTO commentDTO, HttpSession session) throws SQLException {

			UserDTO user = (UserDTO) session.getAttribute("loginUser");
			
			
			//테스트용
//		    if (user != null) {
//		        commentDTO.setUserId(user.getUserId());
//		    } else {
//		        commentDTO.setUserId("guest");  
//		    }

			if (user == null) {
				return new MessageDTO(0, "로그인이 필요합니다.");
			}
			
			commentDTO.setUserId(user.getUserId());
		    commentDTO.setTableName("TOUR");
		    
		    int result = commentService.doSave(commentDTO);
		    String message = "";
		    		
		    if(result == 1) {
		    	message = "댓글 등록 성공!";
		    }else {
		    	message = "댓글 등록 실패!";
		    }
			
		    return new MessageDTO(result, message);
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
