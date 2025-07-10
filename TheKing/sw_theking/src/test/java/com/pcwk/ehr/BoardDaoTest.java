package com.pcwk.ehr;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
class BoardDaoTest {

	Logger log = LogManager.getLogger(getClass());

	@Autowired
	ApplicationContext context;

	@Autowired
	BoardMapper mapper;

	BoardDTO dto01;
	
	SearchDTO search;

	@BeforeEach
	void setUp() throws Exception {
		log.debug("┌───────────────────────────┐");
		log.debug("│ *setUp()*                 │");
		log.debug("└───────────────────────────┘");

		int seq = 99;


		dto01 = new BoardDTO(seq, "제목1", "10", "내용1", 0, "사용안함", "admin", "사용안함", "admin");
	}

	@AfterEach
	void tearDown() throws Exception {
		log.debug("┌───────────────────────────┐");
		log.debug("│ *tearDown()*              │");
		log.debug("└───────────────────────────┘");
	}
	@Disabled
	@Test
	void updateReadCnt() {
		mapper.deleteAll();
		
		int flag = mapper.doSave(dto01);
		assertEquals(1, flag);
		
		int count = mapper.getCount();
		log.debug("count:{}",count);
		assertEquals(1, count);
		
		dto01.setRegId("james");
		
		flag = mapper.updateReadCnt(dto01);
		assertEquals(1, flag);
		
		BoardDTO outVO = mapper.doSelectOne(dto01);
		
		assertEquals(1,outVO.getReadCnt());
		log.debug("outVO.getReadCnt():{}",outVO.getReadCnt());
	}
	
	//@Disabled
	@Test
	void doRetrieve() {
		log.debug("┌───────────────────────────┐");
		log.debug("│ *doRetrieve()*            │");
		log.debug("└───────────────────────────┘");
		
		search = new SearchDTO();
		
		mapper.deleteAll();
		
		int count = mapper.doSaveAll();
		log.debug("count:{}",count);
		assertEquals(502, count);
		
		search.setPageNo(1);
		search.setPageSize(10);
		search.setDiv("10");
		
		search.setSearchDiv("40");
		search.setSearchWord("제목1");
		
		List<BoardDTO> list = mapper.doRetrieve(search);
		for(BoardDTO dto : list) {
			log.debug("dto :{}",dto);
		}
		
		
	}
	
	@Disabled
	@Test
	void doUpdate() {
		log.debug("┌───────────────────────────┐");
		log.debug("│ *doUpdate()*              │");
		log.debug("└───────────────────────────┘");
		
		mapper.deleteAll();

		log.debug("before:{}", dto01);

		int flag = mapper.doSave(dto01);
		log.debug("after:{}", dto01);
		log.debug("flag:{}", flag);
		assertEquals(1, flag);
		
		int count = mapper.getCount();
		log.debug("count:{}", count);
		assertEquals(1, count);
		
		BoardDTO outVO = mapper.doSelectOne(dto01);
		assertNotNull(outVO);
		log.debug("outVO:{}", outVO);

		isSaveBoard(outVO, dto01);
		
		String upString = "_U";
		int upInt = 999;
		
		outVO.setTitle(outVO.getTitle()+upString);
		outVO.setContents(outVO.getContents()+upString);
		outVO.setDiv(outVO.getDiv()+upInt);
		outVO.setModId(outVO.getModId()+upString);
		
		flag = mapper.doUpdate(outVO);
		log.debug("flag:{}", flag);
		assertEquals(1, flag);
		
		BoardDTO upVO = mapper.doSelectOne(outVO);
		isSaveBoard(outVO, upVO);
		
		
		
	}
	
	
	@Disabled
	@Test
	void doDelete() {
		log.debug("┌───────────────────────────┐");
		log.debug("│ *doDelete()*              │");
		log.debug("└───────────────────────────┘");

		mapper.deleteAll();

		log.debug("before:{}", dto01);

		int flag = mapper.doSave(dto01);
		log.debug("after:{}", dto01);
		log.debug("flag:{}", flag);
		assertEquals(1, flag);
		
		int count = mapper.getCount();
		log.debug("count:{}", count);
		assertEquals(1, count);
		
		flag = mapper.doDelete(dto01);
		log.debug("flag:{}", flag);
		assertEquals(1, flag);
		
		
		count = mapper.getCount();
		log.debug("count:{}", count);
		assertEquals(0, count);
	}

	@Disabled
	@Test
	void addAndGet() {
		log.debug("┌───────────────────────────┐");
		log.debug("│ *addAndGet()*             │");
		log.debug("└───────────────────────────┘");

		mapper.deleteAll();

		log.debug("before:{}", dto01);

		int flag = mapper.doSave(dto01);
		log.debug("after:{}", dto01);
		log.debug("flag:{}", flag);
		assertEquals(1, flag);

		int count = mapper.getCount();
		log.debug("count:{}", count);
		assertEquals(1, count);

		BoardDTO outVO = mapper.doSelectOne(dto01);
		assertNotNull(outVO);
		log.debug("outVO:{}", outVO);

		isSaveBoard(outVO, dto01);

	}

	//@Disabled
	@Test
	void beans() {
		log.debug("┌───────────────────────────┐");
		log.debug("│ *beans()*                 │");
		log.debug("└───────────────────────────┘");

		assertNotNull(context);
		assertNotNull(mapper);
		assertNotNull(dto01);

		assertNotEquals(0, dto01.getSeq());

		log.debug("context:{}", context);
		log.debug("mapper:{}", mapper);
		log.debug("dto01:{}", dto01);
	}

	private void isSaveBoard(BoardDTO outVO, BoardDTO dto01) {
		assertEquals(outVO.getSeq(), dto01.getSeq());
		assertEquals(outVO.getTitle(), dto01.getTitle());
		assertEquals(outVO.getDiv(), dto01.getDiv());
		assertEquals(outVO.getContents(), dto01.getContents());
		assertEquals(outVO.getReadCnt(), dto01.getReadCnt());
		assertEquals(outVO.getRegId(), dto01.getRegId());
		assertEquals(outVO.getModId(), dto01.getModId());
	}

}
