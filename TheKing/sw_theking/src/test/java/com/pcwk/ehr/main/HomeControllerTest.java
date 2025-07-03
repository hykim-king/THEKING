package com.pcwk.ehr.main;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.pcwk.ehr.board.domain.BoardDTO;
import com.pcwk.ehr.festival.domain.FestivalDTO;
import com.pcwk.ehr.image.domain.ImageDTO;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
class HomeControllerTest {

	Logger log = LogManager.getLogger(getClass());

	@Autowired
	WebApplicationContext webApplicationContext;

	MockMvc mockMvc;

	@BeforeEach
	void setUp() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ setUp()                                                 │");
		log.debug("└─────────────────────────────────────────────────────────┘");

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@AfterEach
	void tearDown() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ tearDown()                                              │");
		log.debug("└─────────────────────────────────────────────────────────┘");
	}

	@Test
	void home() throws Exception {
		// 3. url호출, method:get , param
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/home");
		
		ResultActions resultActions = mockMvc.perform(requestBuilder).andExpect(status().isOk());

		// 4.1 Model 데이터 조회
		MvcResult mvcResult = resultActions.andDo(print()).andReturn();

		Map<String, Object> model = mvcResult.getModelAndView().getModel();
		List<ImageDTO> festaList = (List<ImageDTO>) model.get("festaList");
		List<ImageDTO> tourList = (List<ImageDTO>) model.get("tourList");
		List<BoardDTO> boardList = (List<BoardDTO>) model.get("boardList");
		
//		assertNotNull(festaList);
		assertNotNull(tourList);
		assertNotNull(boardList);
		
//		for (ImageDTO vo : festaList) {
//			log.debug(vo);
//		}
		log.debug("관광지");
		for (ImageDTO vo : tourList) {
			log.debug(vo);
		}
		log.debug("공지사항");
		for (BoardDTO vo : boardList) {
			log.debug(vo);
		}
		
		
	}
	
	@Disabled
	@Test
	void beans() {
		log.debug("┌───────────────────────┐");
		log.debug("│ beans()               │");
		log.debug("└───────────────────────┘");

		assertNotNull(webApplicationContext);
		assertNotNull(mockMvc);

		log.debug("webApplicationContext:{}", webApplicationContext);
		log.debug("mockMvc:{}", mockMvc);
	}

}
