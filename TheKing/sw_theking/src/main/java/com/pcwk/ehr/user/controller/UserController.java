package com.pcwk.ehr.user.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Mapper;
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
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.pcwk.ehr.board.domain.BoardDTO;
import com.pcwk.ehr.board.service.BoardService;
import com.pcwk.ehr.cmn.FileUtil;
import com.pcwk.ehr.cmn.MessageDTO;
import com.pcwk.ehr.cmn.PcwkString;
import com.pcwk.ehr.cmn.SearchDTO;
import com.pcwk.ehr.comment.domain.CommentDTO;
import com.pcwk.ehr.comment.service.CommentService;
import com.pcwk.ehr.favorites.service.FavoritesService;
import com.pcwk.ehr.festival.domain.FestivalDTO;
import com.pcwk.ehr.image.domain.ImageDTO;
import com.pcwk.ehr.mapper.BoardMapper;
import com.pcwk.ehr.mapper.ImageMapper;
import com.pcwk.ehr.mapper.UserMapper;
import com.pcwk.ehr.tour.domain.TourDTO;
import com.pcwk.ehr.tour.service.TourService;
import com.pcwk.ehr.user.domain.UserDTO;
import com.pcwk.ehr.user.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	Logger log = LogManager.getLogger(getClass());

	@Autowired
	private UserService userService;

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private FavoritesService favoritesService;
	
	@Autowired
	private ImageMapper imageMapper;
	
	@Autowired
	private BoardMapper boardMapper;
	
	public UserController() {
		log.debug("┌─────────────────────────────────┐");
		log.debug("│ UserController()                │");
		log.debug("└─────────────────────────────────┘");
	}

	// 비밀번호 변경 화면
	@GetMapping("/passwordPage.do")
	public String passwordPage(HttpSession session, Model model) {
		UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");
		if (loginUser == null) {
		       return "user/loginDenied";
		   }
		
		model.addAttribute("user", loginUser);
		
		return "user/user_password";
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
	public String myPage(HttpSession session, Model model) throws SQLException {
		UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");
		if (loginUser == null) {
		       return "user/loginDenied";
		   }
		 
		List<BoardDTO> noticePosts = boardMapper.getNoticeListAll();
	    List<BoardDTO> boardPosts = boardMapper.getBoardListAll(); 
		List<FestivalDTO> favoriteFestivals = favoritesService.getFavoriteFestivals(loginUser.getUserId());
	 	List<CommentDTO> userComments = commentService.getAllComments(loginUser.getUserId());
	 	List<TourDTO> favoriteTours = favoritesService.getFavoriteTours(loginUser.getUserId());
	 	
	 	model.addAttribute("noticePosts", noticePosts);
	    model.addAttribute("boardPosts", boardPosts);
	    model.addAttribute("comments", userComments);
	    model.addAttribute("favoriteFestivals", favoriteFestivals);
	    model.addAttribute("favoriteTours", favoriteTours);
	    // 사용자 정보 등도 모델에 넣기
	    model.addAttribute("user", loginUser);
	    
	    return "user/myPage"; // JSP 경로
	}
	
	@PostMapping(value = "/updatePassword.do", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String updatePassword(@RequestParam("userNo") int userNo,
            					 @RequestParam("password") String password) throws SQLException {
		UserDTO user = new UserDTO();
	    user.setUserNo(userNo);
	    user.setPassword(password);

	    int flag = userMapper.updatePassword(user);
	    String message = (flag == 1) ? "비밀번호가 성공적으로 변경되었습니다." : "비밀번호 변경에 실패했습니다.";

	    MessageDTO messageDTO = new MessageDTO(flag, message);
	    return new Gson().toJson(messageDTO);
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
	
	@PostMapping(value = "/isDuplicateId.do",produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String isDuplicateId(@RequestParam("userId") String userId) throws SQLException {
		try {
			userId = PcwkString.nullToEmpty(userId);
	        int result = userMapper.isDuplicateUserId(userId);
	        String message = (result == 1) ? "이미 사용 중인 아이디입니다." : "사용 가능한 아이디입니다.";
	        return new Gson().toJson(new MessageDTO(result, message));
	    } catch (IllegalArgumentException e) {
	        return new Gson().toJson(new MessageDTO(0, e.getMessage()));
	    }
	}
	
	@PostMapping(value = "/isDuplicateNickname.do",produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String isDuplicateNickname(@RequestParam("nickname") String nickname) throws SQLException {
		try {
			nickname = PcwkString.nullToEmpty(nickname);
	        int result = userMapper.isDuplicateNickname(nickname);
	        String message = (result == 1) ? "이미 사용 중인 닉네임입니다." : "사용 가능한 닉네임입니다.";
	        return new Gson().toJson(new MessageDTO(result, message));
	    } catch (IllegalArgumentException e) {
	        return new Gson().toJson(new MessageDTO(0, e.getMessage()));
	    }
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
	public String doSave(@RequestParam(value = "imageFile", required = false) MultipartFile file, UserDTO user) throws SQLException {
		String jsonString = "";
		log.debug("user:{}", user);
		
		try {
			int flag = userService.doSave(user);
			String message = "";
			
			if(1 == flag) {
				if (file != null && !file.isEmpty()) {

					log.debug("이미지 전송");
					String uploadDir = "C:/Users/user/THEKING/TheKing/sw_theking/src/main/webapp/resources/images/user";
					String savedFilename = FileUtil.saveFileWithUUID(file, uploadDir);

					ImageDTO imageDTO = new ImageDTO();
					imageDTO.setImageName(file.getOriginalFilename());
					imageDTO.setImageUrl("src/main/webapp/resources/images/user/");
					imageDTO.setSaveName(savedFilename);
					imageDTO.setTableName("user_tk");
					imageDTO.setTargetNo(user.getUserNo());

					imageMapper.doSave(imageDTO);
				} else {
					log.debug("이미지 전송 실패");
				}
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
		
		if(httpSession.getAttribute("loginUser") != null) {
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
	    	// 유저 프로필 이미지 조회
	    	ImageDTO profileImage = imageMapper.doSelectOneByTarget("user_tk", loginUser.getUserNo());
	    	if (profileImage != null) {
	    		loginUser.setProfileImage(profileImage); // UserDTO에 이미지 DTO 추가
	    	}

	    	message = "로그인 되었습니다.";
	    	MessageDTO messageDTO = new MessageDTO(flag, message);
			session.setAttribute("loginUser", loginUser);
		    return new Gson().toJson(messageDTO);
	    }		
		
	}

	// doUpdate
	@PostMapping(value = "/doUpdate.do", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String doUpdate(@RequestParam(value = "imageFile", required = false) MultipartFile file, HttpSession session, UserDTO user, Model model) throws SQLException {
		// 비동기 통신
		// 회원등록
		// db입력
		// 성공실패: JSON
		log.debug("┌───────────────────────────┐");
		log.debug("│ *doUpdate()*              │");
		log.debug("└───────────────────────────┘");
		UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");
		if (loginUser == null) {
		       return "user/loginDenied";
		   }	
		String jsonString = "";
		UserDTO inVO = user;

		log.debug("inVO:{}", inVO);

		int flag = userService.doUpdate(inVO);

		String message = "";
		if (1 == flag) {
			if (file != null && !file.isEmpty()) {
                log.debug("새 이미지 업로드 요청");

                // 2. 기존 이미지 조회
                ImageDTO existingImage = imageMapper.doSelectOneByTarget("user_tk", user.getUserNo());

                if (existingImage != null) {
                    // 3. 기존 이미지 파일 삭제
                    String existingFilePath = "C:/Users/user/THEKING/TheKing/sw_theking/src/main/webapp/resources/images/user/" + existingImage.getSaveName();
                    FileUtil.deleteFile(existingFilePath);

                    // 4. DB에서 기존 이미지 정보 삭제
                    imageMapper.deleteImages("user_tk", user.getUserNo());
                    log.debug("기존 이미지 삭제 완료");
                }

                // 5. 새 이미지 저장
                String uploadDir = "C:/Users/user/THEKING/TheKing/sw_theking/src/main/webapp/resources/images/user";
                String savedFilename = FileUtil.saveFileWithUUID(file, uploadDir);

                ImageDTO newImage = new ImageDTO();
                newImage.setImageName(file.getOriginalFilename());
                newImage.setImageUrl("src/main/webapp/resources/images/user/");
                newImage.setSaveName(savedFilename);
                newImage.setTableName("user_tk");
                newImage.setTargetNo(user.getUserNo());
                
                imageMapper.doSave(newImage);
                log.debug("새 이미지 등록 완료");
            } else {
                log.debug("이미지 변경 없음 - 기존 이미지 유지");
            }
			
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
