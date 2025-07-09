package com.pcwk.ehr.board;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.Map;

import com.pcwk.ehr.board.domain.BoardDTO;
import com.pcwk.ehr.board.service.BoardService;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import org.springframework.web.context.WebApplicationContext;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {
    "file:src/main/webapp/WEB-INF/spring/root-context.xml",
    "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"
})
public class BoardControllerTest {

    final Logger log = LogManager.getLogger(getClass());

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    BoardMapper boardMapper;

    @Autowired
    BoardService boardService;

    MockMvc mockMvc;

    BoardDTO board;

    @BeforeEach
    void setUp() throws Exception {
    	log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ setUp()                                                 │");
		log.debug("└─────────────────────────────────────────────────────────┘");
    	mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        board = new BoardDTO();
        board.setTitle("Test 제목");
        board.setContents("Test 내용");
        board.setRegId("pcwk_user");
        board.setBoardPart(10);

        boardService.doSave(board);
    }

    @AfterEach
    void tearDown() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ tearDown()                                              │");
		log.debug("└─────────────────────────────────────────────────────────┘");
        boardMapper.doDelete(board);
    }

    @Test
    void doSelectOneTest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = 
            MockMvcRequestBuilders.get("/board/view.do")
                                  .param("boardNo", String.valueOf(board.getBoardNo()));

        ResultActions resultActions = mockMvc.perform(requestBuilder)
                                             .andExpect(status().isOk());

        MvcResult mvcResult = resultActions.andDo(print()).andReturn();
        Map<String, Object> model = mvcResult.getModelAndView().getModel();
        BoardDTO result = (BoardDTO) model.get("board");

        assertNotNull(result);
        log.debug("조회 결과: {}", result);
    }

    //@Disabled
    @Test
    void beans() {
        assertNotNull(webApplicationContext);
        assertNotNull(mockMvc);
        assertNotNull(boardMapper);
        assertNotNull(boardService);
        assertNotNull(board);

        log.debug("webApplicationContext: {}", webApplicationContext);
        log.debug("mockMvc: {}", mockMvc);
        log.debug("boardMapper: {}", boardMapper);
        log.debug("boardService: {}", boardService);
        log.debug("board: {}", board);
    }
}
