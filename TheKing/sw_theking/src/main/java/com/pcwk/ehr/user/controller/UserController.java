package com.pcwk.ehr.user.controller;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.pcwk.ehr.board.domain.BoardDTO;
import com.pcwk.ehr.cmn.MessageDTO;
import com.pcwk.ehr.cmn.PcwkString;
import com.pcwk.ehr.cmn.SearchDTO;
import com.pcwk.ehr.comment.domain.CommentDTO;
import com.pcwk.ehr.festival.domain.FestivalDTO;
import com.pcwk.ehr.tour.domain.TourDTO;
import com.pcwk.ehr.user.domain.UserDTO;
import com.pcwk.ehr.user.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	Logger log = LogManager.getLogger(getClass());

	@Autowired
	private UserService userService;

	public UserController() {
		log.debug("┌─────────────────────────────────┐");
		log.debug("│ UserController()                │");
		log.debug("└─────────────────────────────────┘");
	}

	// 로그인 후 화면
	@GetMapping("/main.do")
	public String mainPage() {
		return "user/main";
	}

	// 회원가입 화면
	@GetMapping("/signUpPage.do")
	public String signUPPage() {
		return "user/user_Signup";
	}
	
	// 로그인 화면
	@GetMapping("/loginPage.do")
	public String loginPage() {
		return "user/user_Login";
	}
		
	// 마이페이지 화면
	@GetMapping("/myPage.do")
	public String myPage(HttpSession session, Model model) {
		UserDTO loggedInUser = (UserDTO) session.getAttribute("loginUser");
		if (loggedInUser == null) {
		       return "user/loginDenied";
		   }
		 
		model.addAttribute("user", loggedInUser);
		
		return "user/myPage";
	}
	
	//회원 정보 수정 화면
	@GetMapping("/updatePage.do")
	public String updatePage(HttpSession session, Model model) {
		UserDTO loggedInUser = (UserDTO) session.getAttribute("loginUser");
		if (loggedInUser == null) {
		       return "user/loginDenied";
		   }
		
		// 전화번호 나누기
	    String mobile = loggedInUser.getMobile();
	    String[] phoneParts = mobile != null ? mobile.split("-") : new String[]{"", "", ""};

	    model.addAttribute("user", loggedInUser);
	    model.addAttribute("tel1", phoneParts.length > 0 ? phoneParts[0] : "");
	    model.addAttribute("tel2", phoneParts.length > 1 ? phoneParts[1] : "");
	    model.addAttribute("tel3", phoneParts.length > 2 ? phoneParts[2] : "");
		
		return "user/user_Update";
	}
	
	@GetMapping(value="/doRetrieve.do")
	public String doRetrieve(Model model, SearchDTO inVO, HttpSession session) throws SQLException {
		String viewName = "user/userList";

		 // 현재 로그인 사용자 꺼내기
	    UserDTO loggedInUser = (UserDTO) session.getAttribute("loginUser");	

	    boolean isAdmin = false;
	    
	    // 로그인 여부 확인
	    if (loggedInUser == null) {
	        return "user/accessDenied";
	    }
	    
	    // 관리자 여부 확인
	    if (loggedInUser != null) {
	        isAdmin = PcwkString.isAdmin(loggedInUser);
	        if(isAdmin == false) {
	        	return "user/accessDenied";
	        }
	    }
		
	    
	    
	    log.debug("isAdmin:{}",isAdmin);
	    
		//페이지 번호
		int pageNo=PcwkString.nvlZero(inVO.getPageNo(), 1);
		//페이지 사이즈
		int pageSize=PcwkString.nvlZero(inVO.getPageSize(), 10);
		//검색구분
		String searchDiv = PcwkString.nullToEmpty(inVO.getSearchDiv());
		//검색어
		String searchWord = PcwkString.nullToEmpty(inVO.getSearchWord());
		
		inVO.setPageNo(pageNo);
		inVO.setPageSize(pageSize);
		inVO.setSearchDiv(searchDiv);
		inVO.setSearchWord(searchWord);
		
		log.debug("inVO:{}",inVO);
		
		List<UserDTO> list = userService.doRetrieve(inVO);
		model.addAttribute("list", list);
				
		model.addAttribute("isAdmin", isAdmin);
		
		return viewName;
	}	
	
	// 회원가입
	@PostMapping(value="/signUp.do",produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String doSave(UserDTO user) throws SQLException {
		String jsonString = "";
		log.debug("user:{}", user);
		
		try {
			int flag = userService.doSave(user);
			String message = "";
			
			if(1 == flag) {
				message = "회원가입 되었습니다.";
			} else {
				message = "회원가입에 실패 했습니다.";
			}
			
			MessageDTO messageDTO = new MessageDTO(flag, message);
			jsonString = new Gson().toJson(messageDTO);
			log.debug("2.jsonString: {}", jsonString);
			return jsonString;
		
		} catch (IllegalArgumentException | SQLException e) {
			e.printStackTrace();
			// 3. 유효성 검사 실패 또는 DB 오류 시
			return new Gson().toJson(new MessageDTO(0, e.getMessage()));
		}
		
	}

	@GetMapping("/logout.do")
	public String logout(HttpSession httpSession) {
		String viewName = "redirect:/main.do";
		
		if(httpSession.getAttribute("user") != null) {
			//session삭제
			httpSession.invalidate();
		}
		
		return viewName;
	}
	
	@PostMapping(value = "/login.do", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String doLogin(@RequestParam String userId,
	                      @RequestParam String password,
	                      HttpSession session,
	                      Model model) throws SQLException {
	    // 1. 서비스에서 로그인 검증 (아이디, 비밀번호 체크)
		UserDTO loginUserId = new UserDTO();
		loginUserId.setUserId(userId);
		log.debug("userId 전달값 확인: {}", loginUserId);
		UserDTO loginUser = userService.doLogin(loginUserId);
		log.debug("입력된 password: '{}'", password);
		log.debug("DB에서 가져온 password: '{}'", loginUser.getPassword());
		String message = "";
		// 2.로그인 실패 시
		if (loginUser == null || password == null || !password.equals(loginUser.getPassword())) {
			MessageDTO failMessage = new MessageDTO(0, "아이디 또는 비밀번호가 올바르지 않습니다.");
		    return new Gson().toJson(failMessage);
	    } else {  // 3.로그인 성공 시
	    	int flag = 1;
	    	message = "로그인 되었습니다.";
	    	MessageDTO messageDTO = new MessageDTO(flag, message);
			session.setAttribute("loginUser", loginUser);
		    return new Gson().toJson(messageDTO);
	    }		
		
	}

	// doUpdate
	@PostMapping(value = "/doUpdate.do", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String doUpdate(UserDTO param, Model model) throws SQLException {
		// 비동기 통신
		// 회원등록
		// db입력
		// 성공실패: JSON

		log.debug("┌───────────────────────────┐");
		log.debug("│ *doUpdate()*              │");
		log.debug("└───────────────────────────┘");
		String jsonString = "";
		UserDTO inVO = param;

		log.debug("inVO:{}", inVO);

		int flag = userService.doUpdate(inVO);

		String message = "";
		if (1 == flag) {
			message = inVO.getUserId() + "님이 수정 되었습니다.";
		} else {
			message = inVO.getUserId() + "님이 수정 실패 했습니다.";
		}

		MessageDTO messageDTO = new MessageDTO(flag, message);

		jsonString = new Gson().toJson(messageDTO);
		log.debug("jsonString:{}", jsonString);
		
		return jsonString;

	}

	@PostMapping(value = "/doDelete.do", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String doDelete(@RequestParam(name = "userId", required = true, defaultValue = "guest") String userId) {
		String jsonString = "";
		// 비동기 통신
		// 회원등록
		// db입력
		// 성공실패: JSON

		log.debug("┌───────────────────────────┐");
		log.debug("│ *doDelete()*              │");
		log.debug("└───────────────────────────┘");

		log.debug("userId: {}", userId);
		UserDTO inVO = new UserDTO();
		inVO.setUserId(userId);

		log.debug("inVO: {}", inVO);

		// 서비스 호출
		int flag = userService.doDelete(inVO);

		String message = "";
		if (1 == flag) {
			message = userId + "님이 삭제 되었습니다.";
		} else {
			message = userId + "님이 삭제 실패 했습니다.";
		}

		MessageDTO messageDTO = new MessageDTO(flag, message);

		jsonString = new Gson().toJson(messageDTO);
		log.debug("jsonString:{}", jsonString);

		return jsonString;
	}

	@PostMapping(value = "/doSave.do")
	public String doSave(@RequestParam String userId, @RequestParam String name, Model model) throws SQLException {
		String viewName = "user/user_mng";
		log.debug("┌─────────────────────────┐");
		log.debug("│ doSave()                │");
		log.debug("└─────────────────────────┘");
		// http://localhost:8080/ehr/user/doSave.do?userId=pcwk01
		// String userId = req.getParameter("userId");
		log.debug("userId:" + userId);
		log.debug("name:" + name);

		// 화면(vies)로 데이터 전달
		model.addAttribute("userId", userId);
		model.addAttribute("name", name);

		// /WEB-INF/views/+viewName+.jsp -> /WEB-INF/views/user/user_mng.jsp
		return viewName;

	}

	@PostMapping(value = "/deleteUser.do", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String deleteUser(@RequestParam String targetUserId, HttpSession session)
			throws SQLException {

		// 1. 로그인한 사용자 꺼내기
		UserDTO loggedInUser = (UserDTO) session.getAttribute("loginUser");

		// 로그인 여부 확인
	    if (loggedInUser == null) {
	        return "user/loginDenied";
	    }
	    
	    boolean isAdmin = false;
	    // 관리자 여부 확인
	    if (loggedInUser != null) {
	        isAdmin = PcwkString.isAdmin(loggedInUser);
	        if(isAdmin == false) {
	        	return "user/accessDenied";
	        }
	    }

		// 4. 삭제 수행
		UserDTO targetUser = new UserDTO();
		targetUser.setUserId(targetUserId);

		int result = userService.doDelete(targetUser);
	    String message = (result > 0) ? "사용자 삭제에 성공했습니다." : "사용자 삭제에 실패했습니다.";

	    return new Gson().toJson(new MessageDTO(result, message));
	}

}
