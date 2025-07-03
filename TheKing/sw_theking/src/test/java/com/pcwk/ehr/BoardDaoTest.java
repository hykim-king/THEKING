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
import com.pcwk.ehr.cmn.SearchDTO;
import com.pcwk.ehr.mapper.BoardMapper;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {
    "file:src/main/webapp/WEB-INF/spring/root-context.xml",
    "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"
})
class BoardDaoTest {

    final Logger log = LogManager.getLogger(getClass());

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

    @Test
    void Beans() {
        assertNotNull(context);
        assertNotNull(mapper);
        log.debug("context : {}", context);
        log.debug("mapper  : {}", mapper);
    }

    @Test
    void doSaveAndDelete() {
        mapper.doSave(dto01);
        mapper.doSave(dto02);
        mapper.doSave(dto03);

        List<BoardDTO> list = mapper.doRetrieve(searchDTO);
        assertNotNull(list);

        for (BoardDTO dto : list) {
            BoardDTO deleteDTO = new BoardDTO();
            deleteDTO.setBoardNo(dto.getBoardNo());
            mapper.doDelete(deleteDTO);
        }
    }

    @Test
    void IncreaseViews() {
        mapper.doSave(dto01);
        List<BoardDTO> list = mapper.doRetrieve(searchDTO);
        assertFalse(list.isEmpty());

        BoardDTO viewTarget = list.get(0);
        mapper.upViews(viewTarget.getBoardNo());

        BoardDTO selectDTO = new BoardDTO();
        selectDTO.setBoardNo(viewTarget.getBoardNo());

        BoardDTO selected = mapper.doSelectOne(selectDTO);
        assertEquals(1, selected.getViews());
    }

    @Test
    void doUpdate() {
        mapper.doSave(dto01);
        List<BoardDTO> list = mapper.doRetrieve(searchDTO);
        assertFalse(list.isEmpty());

        BoardDTO board = list.get(0);
        board.setTitle("수정 제목");
        board.setContents("수정된 내용입니다");

        mapper.doUpdate(board);

        BoardDTO selectDTO = new BoardDTO();
        selectDTO.setBoardNo(board.getBoardNo());

        BoardDTO updated = mapper.doSelectOne(selectDTO);
        assertEquals("수정 제목", updated.getTitle());
        assertEquals("수정된 내용입니다", updated.getContents());
    }

    @Test
    void GetCount() {
        int count = mapper.getCount();
        assertTrue(count >= 0);
        log.debug("총 게시글 수: {}", count);
    }
}
