package com.pcwk.ehr;

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

import com.pcwk.ehr.cmn.SearchDTO;
import com.pcwk.ehr.festival.domain.FestivalDTO;
import com.pcwk.ehr.festival.service.FestivalService;
import com.pcwk.ehr.festival.service.FestivalServiceImpl;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
class FestivalServiceTest {

	Logger log = LogManager.getLogger(getClass());
	
	@Autowired
	ApplicationContext context;
	
	@Autowired
	FestivalService service;
	
	SearchDTO dto;
	
	@BeforeEach
	void setUp() throws Exception {
		dto = new SearchDTO();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void beans() {
		assertNotNull(context);
		assertNotNull(service);
		
		log.debug("context:{}",context);
		log.debug("service:{}",service);
	}
	//@Disabled
	@Test
	void doRetrieve() {
		List<FestivalDTO> list = service.doRetrieve(dto);
		assertNotNull(list);
		for(FestivalDTO outVO : list) {
			log.debug(list);
		}
		
	}
	
	//@Disabled
	@Test
	void checkRetrieve() {

		List<FestivalDTO> list = service.checkRetrieve(null, null, dto);
		assertNotNull(list);
		for(FestivalDTO outVO :list) {
			log.debug("outVO:{}",outVO);
		}
		
	}

}
