package com.pcwk.ehr.board;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.pcwk.ehr.board.domain.BoardDTO;
import com.pcwk.ehr.board.service.BoardService;
import com.pcwk.ehr.cmn.MessageDTO;
import com.pcwk.ehr.cmn.SearchDTO;
import com.pcwk.ehr.mapper.BoardMapper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import org.springframework.web.context.WebApplicationContext;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
class BoardControllerTest {
	Logger log = LogManager.getLogger(getClass());

	@Autowired
	WebApplicationContext webApplicationContext;

	@Autowired
	BoardMapper mapper;

	// 웹 브라우저 대역 객체
	MockMvc mockMvc;

	BoardDTO dto;

	SearchDTO search;

	@BeforeEach
	void setUp() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ setUp()                                                 │");
		log.debug("└─────────────────────────────────────────────────────────┘");
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		dto = new BoardDTO(99, "제목1", "10", "내용1", 0, "사용안함", "admin", "사용안함", "admin");
	}

	@AfterEach
	void tearDown() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ tearDown()                                              │");
		log.debug("└─────────────────────────────────────────────────────────┘");
	}

	@Test
	void doRetrieve() throws Exception {
		log.debug("┌────────────────────────────┐");
		log.debug("│ doRetrieve()               │");
		log.debug("└────────────────────────────┘");

		// 1.전체 삭제
		// 2.다건 등록(saveAll)
		// 3.목록 조회
		// 4.비교

		// 1.
		mapper.deleteAll();
		assertEquals(0, mapper.getCount());

		// 2.
		int count = mapper.doSaveAll();
		assertEquals(502, mapper.getCount());

		// 3. url호출, method:get , param
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/board/doRetrieve.do")
				.param("pageNo", "1").param("pageSize", "10").param("searchDiv", "").param("searchDiv", "0")
				.param("div", "10");
		
		ResultActions resultActions = mockMvc.perform(requestBuilder).andExpect(status().isOk());

		// 4.1 Model 데이터 조회
		MvcResult mvcResult = resultActions.andDo(print()).andReturn();

		// 4.2
		Map<String, Object> model = mvcResult.getModelAndView().getModel();
		int totalCnt = (int) model.get("totalCnt");
		log.debug("totalCnt:{}",totalCnt);
		
		List<BoardDTO> list = (List<BoardDTO>) model.get("list");
		for(BoardDTO vo : list) {
			log.debug(vo);
		}
		
		assertEquals(10, list.size());

	}

	@Disabled
	@Test
	void doUpdate() throws Exception {
		log.debug("┌────────────────────────────┐");
		log.debug("│ doUpdate()                 │");
		log.debug("└────────────────────────────┘");
		// 1.전체삭제
		// 2.등록
		// 3.한건조회
		// 4.수정

		// 1.
		mapper.deleteAll();
		assertEquals(0, mapper.getCount());

		// 2.
		log.debug("before:{}", dto);
		int flag = mapper.doSave(dto);// 한건 등록
		assertEquals(1, flag);
		log.debug("after:{}", dto);

		// 3.단건조회
		BoardDTO outVO = mapper.doSelectOne(dto);
		assertNotNull(outVO);
		log.debug("outVO:{}", outVO);

		String upString = "U";
		int upInt = 99;

		// 2.1 url호출, method:post, param
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/board/doUpdate.do")
				.param("title", outVO.getTitle() + upString).param("div", outVO.getDiv())
				.param("contents", outVO.getContents() + upString).param("modId", outVO.getModId() + upString)
				.param("seq", String.valueOf(outVO.getSeq()));

		ResultActions resultActions = mockMvc.perform(requestBuilder).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"));

		String returnBody = resultActions.andDo(print()).andReturn().getResponse().getContentAsString();

		MessageDTO resultMessage = new Gson().fromJson(returnBody, MessageDTO.class);
		log.debug("resultMessage:{}", resultMessage);

		assertEquals(1, resultMessage.getMessageId());
		assertEquals("수정 되었습니다.", resultMessage.getMessage());
	}

	@Disabled
	@Test
	void doSelectOne() throws Exception {
		log.debug("┌────────────────────────────┐");
		log.debug("│ doSelectOne()              │");
		log.debug("└────────────────────────────┘");
		// 1.전체삭제
		// 2.등록
		// 3.단건조회
		// 1.
		mapper.deleteAll();

		// 2.
		log.debug("before:{}", dto);
		int flag = mapper.doSave(dto);// 한건 등록
		assertEquals(1, flag);
		log.debug("after:{}", dto);

		// 3
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/board/doSelectOne.do")
				.param("seq", String.valueOf(dto.getSeq())).param("regId", String.valueOf(dto.getRegId()) + "UP");

		ResultActions resultActions = mockMvc.perform(requestBuilder).andExpect(status().isOk());
		MvcResult mvcResult = resultActions.andDo(print()).andReturn();
		Map<String, Object> model = mvcResult.getModelAndView().getModel();
		BoardDTO outVO = (BoardDTO) model.get("vo");
		log.debug("outVO:{}", outVO);

		// 조회count 증가
		dto.setReadCnt(dto.getReadCnt() + 1);
		isSameBoard(outVO, dto);

		// 3.3
		String viewName = mvcResult.getModelAndView().getViewName();
		log.debug("viewName:{}", viewName);
		assertEquals("board/board_mod", viewName);

	}

	private void isSameBoard(BoardDTO outVO, BoardDTO dto) {
		log.debug("┌──────────────────────────┐");
		log.debug("│ isSaveBoard()            │");
		log.debug("└──────────────────────────┘");
		assertEquals(outVO.getSeq(), dto.getSeq());
		assertEquals(outVO.getTitle(), dto.getTitle());
		assertEquals(outVO.getDiv(), dto.getDiv());
		assertEquals(outVO.getContents(), dto.getContents());
		assertEquals(outVO.getReadCnt(), dto.getReadCnt());

		assertEquals(outVO.getRegId(), dto.getRegId());
		assertEquals(outVO.getModId(), dto.getModId());

	}

	@Disabled
	@Test
	void doDelete() throws Exception {
		log.debug("┌────────────────────────────┐");
		log.debug("│ doDelete()                 │");
		log.debug("└────────────────────────────┘");
		// 1.전체삭제
		// 2.등록
		// 3.삭제

		// 1.
		mapper.deleteAll();

		// 2.
		log.debug("before:{}", dto);
		int flag = mapper.doSave(dto);// 한건 등록
		assertEquals(1, flag);
		log.debug("after:{}", dto);

		// 3.
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/board/doDelete.do").param("seq",
				String.valueOf(dto.getSeq()));

		ResultActions resultActions = mockMvc.perform(requestBuilder).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"));

		// 2.3
		String returnBody = resultActions.andDo(print()).andReturn().getResponse().getContentAsString();

		log.debug("returnBody:{}", returnBody);
		MessageDTO resultMessage = new Gson().fromJson(returnBody, MessageDTO.class);
		log.debug("resultMessage: {}", resultMessage);

		assertEquals(1, resultMessage.getMessageId());
		assertEquals("삭제 되었습니다.", resultMessage.getMessage());
	}

	@Disabled
	@Test
	void doSave() throws Exception {
		log.debug("┌───────────────────────┐");
		log.debug("│ doSave()              │");
		log.debug("└───────────────────────┘");

		mapper.deleteAll();

		// url 호출
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/board/doSave.do")
				.param("title", dto.getTitle()).param("div", dto.getDiv()).param("contents", dto.getContents())
				.param("regId", dto.getRegId());

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
		assertEquals("제목1등록되었습니다.", resultMessage.getMessage());
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
