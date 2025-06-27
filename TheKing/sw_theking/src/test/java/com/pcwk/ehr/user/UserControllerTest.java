package com.pcwk.ehr.user;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;
import com.pcwk.ehr.cmn.MessageDTO;
import com.pcwk.ehr.mapper.UserMapper;
import com.pcwk.ehr.user.domain.UserDTO;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
class UserControllerTest {
	Logger log = LogManager.getLogger(getClass());
	
	@Autowired
	WebApplicationContext webApplicationContext;
	
	MockMvc mockMvc;
	
	UserDTO userDTO01;
	UserDTO userDTO02;
	
	MockHttpSession session;
	
	@Autowired
	UserMapper userMapper;
	
	@BeforeEach
	void setUp() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ setUp()                                                 │");
		log.debug("└─────────────────────────────────────────────────────────┘");

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		userDTO01 = new UserDTO("pcwk01", "4321abc!@#" ,"이상무01", "이상무닉1", "pcwk01@naver.com", "010-1111-1111", "서울시 마포구 서교동11","admin", "profile", "사용안함", "사용안함");
		userDTO02 = new UserDTO("pcwk02", "1234abc!@#$","이상무02", "이상무닉2", "pcwk02@naver.com", "010-1111-1111", "서울시 마포구 서교동11","user" , "profile", "사용안함", "사용안함");
		
		//session으로 로그인 세팅
		session = new MockHttpSession();
		session.setAttribute("user", userDTO01);
	}

	@AfterEach
	void tearDown() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ tearDown()                                              │");
		log.debug("└─────────────────────────────────────────────────────────┘");
		
	}

	@Disabled
	@Test
	void doLogin() throws Exception {
	    log.debug("┌───────────────────────────────┐");
	    log.debug("│ doLoginTest()                 │");
	    log.debug("└───────────────────────────────┘");

	    // 1. 테스트용 사용자 저장
	    userMapper.deleteAll();
	    assertEquals(0, userMapper.getCount());
	    
	    // 2. 사용자 등록
	    userMapper.doSave(userDTO01); // userDTO01은 아이디/비번이 들어있는 테스트 객체
	    assertEquals(1, userMapper.getCount());
	    
	    // 3. 로그인 요청 호출
	    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/login.do")
	            .param("userId", userDTO01.getUserId())
	            .param("password", userDTO01.getPassword());
	    		log.debug("requestBuilder:{}",requestBuilder);

	    // 4. 요청 실행 및 검증
	    ResultActions resultActions = mockMvc.perform(requestBuilder)
	    		.andExpect(status().isOk());
//				.andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"));
	    
	    //3.3 호출 결과 받기
	    String returnBody = resultActions.andDo(print())
	  		              	.andReturn().getResponse().getContentAsString();
	  		
	    log.debug("returnBody:{}",returnBody);
	  		
	    //3.4 json to MessageDTO 변환
		
	  	MessageDTO resultMessage = new Gson().fromJson(returnBody, MessageDTO.class);
	  	log.debug("resultMessage: {}",resultMessage);
	  		
	  	assertEquals(1, resultMessage.getMessageId());
	  	assertEquals("pcwk01님 환영합니다.",resultMessage.getMessage());
	}
	
	@Disabled
	@Test
	void deleteUser() throws SQLException {

		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ deleteUser()                                            │");
		log.debug("└─────────────────────────────────────────────────────────┘");
		
		//1. 전체삭제
		userMapper.deleteAll();
		
		//2.1 등록후 DB에서 adminId에 등록된 아이디값 저장 후 adminId로 doSelectOne호출해 adminUser추가
		userMapper.doSave(userDTO01);
		UserDTO adminId = new UserDTO();
		adminId.setUserId(userDTO01.getUserId());
		UserDTO adminUser = userMapper.doSelectOne(adminId);
		assertEquals(1, userMapper.getCount());
		
		//2.2 로그인 세션에 adminUser 설정
		session.setAttribute("user", adminUser);
		log.debug("session:{}",session);
		
		//3. 대상 삭제
		MockHttpServletRequestBuilder requestBuilder
		= MockMvcRequestBuilders.post("/user/deleteUser.do")
		.param("userId", userDTO02.getUserId())
		.session(session);
	    
//	    if (loginUser == null) {
//	        return new MessageDTO(0, "로그인이 필요합니다.");
//	    }
//
//	    if (!PcwkString.isAdmin(loginUser)) {
//	        return new MessageDTO(0, "관리자만 삭제할 수 있습니다.");
//	    }
//
//	    UserDTO targetUser = new UserDTO();
//	    targetUser.setUserId(targetUserId);
//
//	    int result = userMapper.doDelete(targetUser);
//
//	    if (result > 0) {
//	        return new MessageDTO(1, targetUserId + "님이 삭제 되었습니다.");
//	    } else {
//	        return new MessageDTO(0, "사용자 삭제 실패.");
//	    }
	}
	
	@Test
	void doRetrieve() throws Exception {
		log.debug("┌───────────────────────────┐");
		log.debug("│ doRetrieve()              │");
		log.debug("└───────────────────────────┘");
		
		//1. 전체삭제
		//2. 502건 입력
		//3. 조회
		
		//1.
		userMapper.deleteAll();
		
		//2.
		userMapper.saveAll();
		assertEquals(502, userMapper.getCount());
		
		//3.url호출, method:get, param
		MockHttpServletRequestBuilder  requestBuilder
		= MockMvcRequestBuilders.get("/user/doRetrieve.do")
		.param("pageNo", "0")
		.param("pageSize", "0")
		.param("searchDiv", "")
		.param("searchWord", "");
		
		
		//3.1
		ResultActions resultActions= mockMvc.perform(requestBuilder)
		.andExpect(status().isOk());		
		
		
		//3.2 Model 데이터 조회
		MvcResult  mvcResult=resultActions.andDo(print()).andReturn();
				
		//3.3
		Map<String, Object> model=mvcResult.getModelAndView().getModel();
		List<UserDTO> list=(List<UserDTO>) model.get("list");
		
		for(UserDTO dto   :list) {
			log.debug(dto);
		}
		
		//3.4
		String viewName = mvcResult.getModelAndView().getViewName();
		log.debug("viewName:{}",viewName);
		assertEquals("user/user_list", viewName);
	}
	
	@Disabled
	@Test
	void doDelete() throws Exception {
		log.debug("┌───────────────────────────┐");
		log.debug("│ doSave()                  │");
		log.debug("└───────────────────────────┘");
		//1.전체삭제
		//2.등록
		//3.삭제
		//4.상태값 비교
		
		//1.전체 삭제
		userMapper.deleteAll();
		
		//2.등록
		userMapper.doSave(userDTO01);
		
		//2.1 비교
		assertEquals(1, userMapper.getCount());
		
		//3.1 url호출, method:post, param
		MockHttpServletRequestBuilder  requestBuilder
		= MockMvcRequestBuilders.post("/user/doDelete.do")
		.param("userId", userDTO01.getUserId());
		
		
		//3.2 호출
		ResultActions resultActions=mockMvc.perform(requestBuilder)
				.andExpect(status().isOk())
				.andExpect(
				MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"));		
	
		
		//3.3 호출 결과 받기
		String returnBody = resultActions.andDo(print())
		                     .andReturn().getResponse().getContentAsString();
		
		log.debug("returnBody:{}",returnBody);
		//{"messageId":1,"message":"pcwk01님이 삭제 되었습니다.","no":0,"totalCnt":0,"pageSize":0,"pageNo":0}
		
		
		//3.4 json to MessageDTO 변환
		
		MessageDTO resultMessage = new Gson().fromJson(returnBody, MessageDTO.class);
		log.debug("resultMessage: {}",resultMessage);
		
		assertEquals(1, resultMessage.getMessageId());
		assertEquals("pcwk01님이 삭제 되었습니다.",resultMessage.getMessage());
	}
	
	@Disabled
	@Test
	void beans() {
		assertNotNull(webApplicationContext);
		assertNotNull(mockMvc);
		assertNotNull(userDTO01);
		assertNotNull(userMapper);

		log.debug("webApplicationContext:{}", webApplicationContext);
		log.debug("mockMvc:{}", mockMvc);
		log.debug("userDTO01:{}", userDTO01);
		log.debug("userMapper:{}", userMapper);
	}

	
	
	
}
