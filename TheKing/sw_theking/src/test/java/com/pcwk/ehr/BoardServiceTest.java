package com.pcwk.ehr;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import com.pcwk.ehr.board.domain.BoardDTO;
import com.pcwk.ehr.board.service.BoardService;
import com.pcwk.ehr.mapper.BoardMapper;
import com.pcwk.ehr.cmn.SearchDTO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(locations = {
    "file:src/main/webapp/WEB-INF/spring/root-context.xml",
    "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"
})
public class BoardServiceTest {

    final Logger log = LogManager.getLogger(getClass());

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    ApplicationContext context;

    @Autowired
    BoardService service;

    @Autowired
    BoardMapper boardMapper;

    BoardDTO board;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() throws Exception {
        board = new BoardDTO();
        board.setTitle("JUnit 게시글 제목");
        board.setContents("JUnit 게시글 내용");
        board.setRegId("pcwk_junit");
        board.setBoardPart(10);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterEach
    void tearDown() throws Exception {
        boardMapper.doDelete(board);
    }

    @Disabled
    @Test
    public void doSaveAndSelect() {
        service.doSave(board);
        BoardDTO out = service.doSelectOne(board);
        assertNotNull(out);
        assertEquals(board.getTitle(), out.getTitle());
        log.debug("저장 후 조회된 게시글: {}", out);
    }

    @Disabled
    @Test
    public void doRetrieveTest() {
        SearchDTO search = new SearchDTO();
        search.setPageNo(1);
        search.setPageSize(10);
        List<BoardDTO> list = service.doRetrieve(search);
        assertNotNull(list);
        log.debug("조회된 게시글 수: {}", list.size());
    }

    @Disabled
    @Test
    public void doDeleteTest() {
        service.doSave(board);
        int deleted = service.doDelete(board);
        assertEquals(1, deleted);
    }

    @Disabled
    @Test
    void beans() {
        assertNotNull(context);
        assertNotNull(service);
        log.debug("context: {}", context);
        log.debug("service: {}", service);
    }
}
