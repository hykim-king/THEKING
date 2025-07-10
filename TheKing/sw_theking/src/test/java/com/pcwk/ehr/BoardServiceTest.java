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
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
class BoardServiceTest {
	Logger log = LogManager.getLogger(getClass());
	
	@Autowired
	ApplicationContext context;
	
	@Autowired
	BoardService service;
	
	@Autowired
	BoardMapper mapper;
	
	BoardDTO dto01;
	
	@BeforeEach
	void setUp() throws Exception {
		dto01 = new BoardDTO(99, "제목1", "10", "내용1", 0, "사용안함", "admin", "사용안함", "admin");
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Test
	void doSelectOne() {
		log.debug("┌───────────────────────────┐");
		log.debug("│ *doSelectOne()*           │");
		log.debug("└───────────────────────────┘");
		
		mapper.deleteAll();
		assertEquals(0,mapper.getCount());
		
		log.debug("before:{}", dto01);
		int flag = mapper.doSave(dto01);
		log.debug("after:{}", dto01);
		assertEquals(1, flag);
		dto01.setRegId("james");
		BoardDTO outVO = service.doSelectOne(dto01);
		assertNotNull(outVO);
		assertEquals(1, outVO.getReadCnt());
	}

	@Test
	void beans() {
		log.debug("┌───────────────────────────┐");
		log.debug("│ *beans()*                 │");
		log.debug("└───────────────────────────┘");
		assertNotNull(mapper);
		assertNotNull(context);
		assertNotNull(service);


		log.debug("context:" + context);
		log.debug("service:" + service);
		log.debug("mapper:" + mapper);

	}
	

}
