package com.pcwk.ehr.user.controller;

import java.sql.SQLException;
import java.util.List;

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
import com.pcwk.ehr.cmn.MessageDTO;
import com.pcwk.ehr.cmn.PcwkString;
import com.pcwk.ehr.cmn.SearchDTO;
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
		return "user/signUp";
	}
	
	// 사용자 전체 조회 화면
	@GetMapping("/userList.do")
	public String loginPage() {
		return "user/userList";
	}
	
	@GetMapping(value="/doRetrieve.do")
	public String doRetrieve(Model model, SearchDTO inVO ) throws SQLException {
		String viewName = "user/user_list";
		
		log.debug("┌───────────────────────────┐");
		log.debug("│ *doRetrieve()*            │");
		log.debug("└───────────────────────────┘");		
		
		//pageNo==0 ->1
		//pageSize==0 ->10
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

		return viewName;
	}	
	
	// 회원가입
	@PostMapping("/signUp.do")
	public String doSave(UserDTO user, Model model) throws SQLException {
		try {
			// 1. 서비스 호출
			userService.doSave(user);

			// 2. 성공 시 성공 페이지로 이동
			model.addAttribute("message", "회원가입 성공!");
			return "user/main";

		} catch (IllegalArgumentException | SQLException e) {
			e.printStackTrace();
			// 3. 유효성 검사 실패 또는 DB 오류 시
			model.addAttribute("error", e.getMessage()); // 서비스에서 던진 에러 메시지를 모델에 추가

			// 4. 다시 회원가입 폼으로 돌아감
			return "user/signUp"; //
		}
	}

	@PostMapping(value = "/login.do", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String doLogin(@RequestParam String userId,
	                      @RequestParam String password,
	                      HttpSession session,
	                      Model model) throws SQLException {
		
		String jsonString = "";
	    // 1. 서비스에서 로그인 검증 (아이디, 비밀번호 체크)
		UserDTO loginUserId = new UserDTO();
		loginUserId.setUserId(userId);
		UserDTO loginUser = userService.doSelectOne(loginUserId);
		log.debug("loginUser: {}", loginUser);

		String message = "";
		// 2.로그인 실패 시
		if (loginUser == null || password == null || !password.equals(loginUser.getPassword())) {
			message = "아이디 또는 비밀번호가 올바르지 않습니다.";
			return message; // 로그인 페이지로 이동
	    }
		
		// 3.로그인 성공 시
		session.setAttribute("loginUser", loginUser);
		log.debug("session:{}", session);
		model.addAttribute("message", loginUser.getUserId() + "님 환영합니다.");
		message = loginUser.getUserId() + "님 환영합니다.";
		MessageDTO messageDTO = new MessageDTO(1, message);
		
		jsonString = new Gson().toJson(messageDTO);
		log.debug("jsonString:{}", jsonString);

		return jsonString;
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
		log.debug("│ *doUpdate()*                │");
		log.debug("└───────────────────────────┘");
		// http://localhost:8080/ehr/user/doSave.do?userId=pcwk01
		// String userId = req.getParameter("userId");
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

//	@PostMapping(value = "/deleteUser.do", produces = "text/plain;charset=UTF-8")
//	@ResponseBody
//	public String deleteUser(@RequestParam String targetUserId, HttpSession session, RedirectAttributes redirect)
//			throws SQLException {
//
//		// 1. 로그인한 사용자 꺼내기
//		UserDTO loginUser = (UserDTO) session.getAttribute("user");
//
//		// 2. 로그인 안 됐으면 리턴
//		if (loginUser == null) {
//			redirect.addFlashAttribute("error", "로그인이 필요합니다.");
//			return "redirect:/login.do";
//		}
//
//		// 3. 어드민인지 확인 (여기서 만든 static 메서드 활용!)
//		if (!PcwkString.isAdmin(loginUser)) {
//			redirect.addFlashAttribute("error", "관리자만 삭제할 수 있습니다.");
//			return "redirect:/user/list.do";
//		}
//
//		// 4. 삭제 수행
//		UserDTO targetUser = new UserDTO();
//		targetUser.setUserId(targetUserId);
//
//		int result = userService.doDelete(targetUser);
//		if (result > 0) {
//			redirect.addFlashAttribute("message", "사용자 삭제 성공!");
//		} else {
//			redirect.addFlashAttribute("error", "사용자 삭제 실패.");
//		}
//
//		return "redirect:/user/list.do";
//	}

}
