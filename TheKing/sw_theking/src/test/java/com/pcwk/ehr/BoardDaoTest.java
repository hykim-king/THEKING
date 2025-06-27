package com.pcwk.ehr;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pcwk.ehr.board.domain.BoardDTO;
import com.pcwk.ehr.mapper.BoardMapper;
import com.pcwk.ehr.cmn.SearchDTO;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { 
    "file:src/main/webapp/WEB-INF/spring/root-context.xml",
    "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" 
})
class BoardDaoTest {

    Logger log = LogManager.getLogger(getClass());

    @Autowired
    BoardMapper mapper;

    @Autowired
    ApplicationContext context;

    BoardDTO dto01, dto02, dto03;
    SearchDTO searchDTO;

    @BeforeEach
    void setUp() throws Exception {
        dto01 = new BoardDTO(0, "제목1", "내용1", 0, 10, "tester", null, null);
        dto02 = new BoardDTO(0, "제목2", "내용2", 0, 10, "tester", null, null);
        dto03 = new BoardDTO(0, "제목3", "내용3", 0, 20, "tester", null, null);
        searchDTO = new SearchDTO();
        searchDTO.setPageNo(1);
        searchDTO.setPageSize(10);
    }

    @AfterEach
    void tearDown() throws Exception {
        log.debug("@AfterEach - 테스트 종료");
    }

    //@Disabled
    @Test
    void Beans() {
        assertNotNull(context);
        assertNotNull(mapper);
        log.debug("context : {}", context);
        log.debug("mapper  : {}", mapper);
    }

    //@Disabled
    @Test
    void doSaveAndDelete() {
        mapper.doSave(dto01);
        mapper.doSave(dto02);
        mapper.doSave(dto03);

        List<BoardDTO> list = mapper.doRetrieve(searchDTO);
        assertNotNull(list);

        for (BoardDTO dto : list) {
            log.debug("삭제 대상: {}", dto.getBoardNo());
            mapper.doDelete(dto.getBoardNo());
        }
    }

    //@Disabled
    @Test
    void IncreaseViews() {
        mapper.doSave(dto01);
        List<BoardDTO> list = mapper.doRetrieve(searchDTO);
        int boardNo = list.get(0).getBoardNo();
        mapper.upViews(boardNo);
        BoardDTO selected = mapper.doSelectOne(boardNo);
        assertEquals(1, selected.getViews());
    }

    //@Disabled
    @Test
    void doUpdate() {
        mapper.doSave(dto01);
        List<BoardDTO> list = mapper.doRetrieve(searchDTO);
        BoardDTO board = list.get(0);
        board.setTitle("수정 제목");
        board.setContents("수정된 내용입니다");
        mapper.doUpdate(board);

        BoardDTO updated = mapper.doSelectOne(board.getBoardNo());
        assertEquals("수정 제목", updated.getTitle());
        assertEquals("수정된 내용입니다", updated.getContents());
    }

    //@Disabled
    @Test
    void GetCount() {
        int count = mapper.getCount();
        assertTrue(count >= 0);
        log.debug("총 게시글 수: {}", count);
    }
}