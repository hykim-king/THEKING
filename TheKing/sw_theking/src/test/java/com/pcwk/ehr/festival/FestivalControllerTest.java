package com.pcwk.ehr.festival;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;
import com.pcwk.ehr.cmn.MessageDTO;
import com.pcwk.ehr.cmn.SearchDTO;
import com.pcwk.ehr.festival.domain.FestivalDTO;
import com.pcwk.ehr.mapper.FestivalMapper;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
class FestivalControllerTest {

	Logger log = LogManager.getLogger(getClass());

	@Autowired
	WebApplicationContext webApplicationContext;

	@Autowired
	FestivalMapper mapper;

	MockMvc mockMvc;

	FestivalDTO dto;

	SearchDTO search;

	@BeforeEach
	void setUp() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ setUp()                                                 │");
		log.debug("└─────────────────────────────────────────────────────────┘");
		dto = new FestivalDTO(1, "축제1", "축제 시작1", "축제가 시작됩니다.", 0, "경기도 고양시", "010-1234-1234", 10000, 41280,
				"2025-06-12", "2025-07-12");
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@AfterEach
	void tearDown() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ tearDown()                                              │");
		log.debug("└─────────────────────────────────────────────────────────┘");
	}

	@Test
	void doUpdate() throws Exception {
		log.debug("┌───────────────────────┐");
		log.debug("│ doUpdate()            │");
		log.debug("└───────────────────────┘");

		int flag = mapper.doSave(dto);
		assertEquals(1, flag);

		int festaNo = mapper.checkSeq();
		FestivalDTO outVO = mapper.doSelectOne(dto);
		log.debug("before:{}", outVO);

		// 2.1 url호출, method:post,param
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/festival/doUpdate.do")
				.param("name", dto.getName() + "999").param("contents", dto.getContents() + "999")
				.param("address", dto.getAddress() + "999").param("tel", dto.getTel())
				.param("fee", String.valueOf(dto.getFee())).param("regionNo", String.valueOf(dto.getRegionNo()))
				.param("startDate", dto.getStartDate()).param("endDate", dto.getEndDate())
				.param("subtitle", dto.getSubtitle()).param("festaNo", String.valueOf(dto.getFestaNo()));

		// 2.2 호출
		ResultActions resultActions = mockMvc.perform(requestBuilder).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"));

		// 2.3호출 결과 받기
		String returnBody = resultActions.andDo(print()).andReturn().getResponse().getContentAsString();
		log.debug("returnBody:{}", returnBody);

		// 2.4 json to MessageDTO 변환
		MessageDTO resultMessage = new Gson().fromJson(returnBody, MessageDTO.class);

		log.debug("resultMessage:{}", resultMessage.toString());

		assertEquals(1, resultMessage.getMessageId());
		assertEquals("축제1999이 수정되었습니다.", resultMessage.getMessage());

		outVO = mapper.doSelectOne(outVO);
		log.debug("after:{}", outVO);

		dto.setFestaNo(festaNo);

		flag = mapper.doDelete(dto);
		assertEquals(1, flag);
	}

	@Disabled
	@Test
	void doSave() throws Exception {
		log.debug("┌───────────────────────┐");
		log.debug("│ doSave()              │");
		log.debug("└───────────────────────┘");

		mapper.doDelete(dto);

		// 2.1 url호출, method:post,param
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/festival/doSave.do")
				.param("name", dto.getName()).param("contents", dto.getContents()).param("address", dto.getAddress())
				.param("tel", dto.getTel()).param("fee", String.valueOf(dto.getFee()))
				.param("regionNo", String.valueOf(dto.getRegionNo())).param("startDate", dto.getStartDate())
				.param("endDate", dto.getEndDate()).param("subtitle", dto.getSubtitle());

		// 2.2 호출
		ResultActions resultActions = mockMvc.perform(requestBuilder).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"));

		// 2.3호출 결과 받기
		String returnBody = resultActions.andDo(print()).andReturn().getResponse().getContentAsString();
		log.debug("returnBody:{}", returnBody);

		// 2.4 json to MessageDTO 변환
		MessageDTO resultMessage = new Gson().fromJson(returnBody, MessageDTO.class);

		log.debug("resultMessage:{}", resultMessage.toString());

		assertEquals(1, resultMessage.getMessageId());
		assertEquals("축제1이 등록되었습니다.", resultMessage.getMessage());

		int festaNo = mapper.checkSeq();
		dto.setFestaNo(festaNo);

		int flag = mapper.doDelete(dto);
		assertEquals(1, flag);

	}

	@Disabled
	@Test
	void beans() {
		log.debug("┌───────────────────────┐");
		log.debug("│ beans()               │");
		log.debug("└───────────────────────┘");

		assertNotNull(webApplicationContext);
		assertNotNull(mockMvc);
		assertNotNull(mapper);

		log.debug("webApplicationContext:{}", webApplicationContext);
		log.debug("mockMvc:{}", mockMvc);
		log.debug("mapper:{}", mapper);
	}

}
