package com.pcwk.ehr;

import static org.junit.Assert.assertNotNull;

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
import com.pcwk.ehr.image.domain.ImageDTO;
import com.pcwk.ehr.mapper.MainMapper;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
class MainDaoTest {
	Logger log = LogManager.getLogger(getClass());
	
	@Autowired
	ApplicationContext context;
	
	@Autowired
	MainMapper mapper;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void beans() {
		assertNotNull(context);
		assertNotNull(mapper);
		
		log.debug("context :{}",context);
		log.debug("mapper :{}",mapper);
	}
	
	@Disabled
	@Test
	void getPopularTour() {
		List<ImageDTO> list = mapper.getPopularTour();
		
		assertNotNull(list);
		log.debug("list:{}",list);
		
		for(ImageDTO dto : list) {
			log.debug(dto);
		}
	}
	
	@Disabled
	@Test
	void getRecentFestival() {
		List<ImageDTO> list = mapper.getRecentFestival();
		
		assertNotNull(list);
		log.debug("list:{}",list);
		
		for(ImageDTO dto : list) {
			log.debug(dto);
		}
	}
	
	@Disabled
	@Test
	void getRecentNotice() {
		List<BoardDTO> list = mapper.getRecentNotice();
		
		log.debug(list);
		assertNotNull(list);
		
		for(BoardDTO dto :list) {
			log.debug(dto);
		}
	}

}
