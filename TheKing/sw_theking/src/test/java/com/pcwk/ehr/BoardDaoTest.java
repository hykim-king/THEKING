package com.pcwk.ehr;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
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

import com.pcwk.ehr.board.dao.BoardDao;
import com.pcwk.ehr.board.domain.BoardDTO;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {
    "file:src/main/webapp/WEB-INF/spring/root-context.xml",
    "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"
})
class BoardDaoTest {

    final Logger log = LogManager.getLogger(getClass());

    @Autowired
    BoardDao dao;

    @Autowired
    ApplicationContext context;

    BoardDTO dto01;
    BoardDTO dto02;
    BoardDTO dto03;

    @BeforeEach
    void setUp() throws Exception {
        log.debug("┌───────────────────────────────┐");
        log.debug("│ @BeforeEach - 테스트 데이터 생성          │");
        log.debug("└───────────────────────────────┘");

        dto01 = new BoardDTO(0, "제목1", "내용1", 0, 10, "tester", null, null);
        dto02 = new BoardDTO(0, "제목2", "내용2", 0, 10, "tester", null, null);
        dto03 = new BoardDTO(0, "제목3", "내용3", 0, 20, "tester", null, null);
    }

    @AfterEach
    void tearDown() throws Exception {
        log.debug("***************************");
        log.debug("@AfterEach - 테스트 종료");
        log.debug("***************************");
    }

    @Test
    void beans() {
        assertNotNull(context);
        assertNotNull(dao);

        log.debug("context : {}", context);
        log.debug("dao     : {}", dao);
    }

    @Test
    void doSaveAndDelete() throws SQLException {
        log.debug("게시글 등록 및 삭제 테스트 시작");

        // 데이터 삭제 (기존에 존재할 수 있는 데이터 정리용)
        List<BoardDTO> all = dao.getBoardList();
        for (BoardDTO b : all) {
            if (b.getTitle().startsWith("제목")) {
                dao.deleteBoard(b.getBoardNo());
                log.debug("삭제된 게시글: {}", b.getBoardNo());
            }
        }

        // 저장
        assertEquals(1, dao.insertBoard(dto01));
        assertEquals(1, dao.insertBoard(dto02));
        assertEquals(1, dao.insertBoard(dto03));

        log.debug("저장 완료");

        // 목록 확인
        List<BoardDTO> boardList = dao.getBoardList();
        assertTrue(boardList.stream().anyMatch(b -> b.getTitle().equals("제목1")));
        assertTrue(boardList.stream().anyMatch(b -> b.getTitle().equals("제목2")));
        assertTrue(boardList.stream().anyMatch(b -> b.getTitle().equals("제목3")));

        // 삭제 테스트
        for (BoardDTO b : boardList) {
            if (b.getTitle().startsWith("제목")) {
                dao.deleteBoard(b.getBoardNo());
                log.debug("삭제됨: {}", b.getBoardNo());
            }
        }
    }

    @Test
    void updateTest() throws SQLException {
        // 저장
        dao.insertBoard(dto01);
        List<BoardDTO> list = dao.getBoardList();
        BoardDTO latest = list.get(0); // 최신 글 (DESC 정렬)

        // 수정
        latest.setTitle("수정 제목");
        latest.setContents("수정된 내용입니다");
        int result = dao.updateBoard(latest);
        assertEquals(1, result);

        log.debug("수정 완료: {}", latest);

        // 정리
        dao.deleteBoard(latest.getBoardNo());
    }

    @Test
    void increaseViewsTest() throws SQLException {
        dao.insertBoard(dto01);
        List<BoardDTO> list = dao.getBoardList();
        BoardDTO target = list.get(0);

        int result = dao.increaseViews(target.getBoardNo());
        assertEquals(1, result);
        log.debug("조회수 증가 완료 for boardNo={}", target.getBoardNo());

        dao.deleteBoard(target.getBoardNo());
    }
}