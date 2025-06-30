package com.pcwk.ehr;

import static org.junit.jupiter.api.Assertions.*;

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

import com.pcwk.ehr.favorites.domain.FavoritesDTO;
import com.pcwk.ehr.favorites.service.FavoritesService;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
class FavoritesServiceTest {
	Logger log = LogManager.getLogger(getClass());

	@Autowired
	ApplicationContext context;
	@Autowired
	FavoritesService service;

	FavoritesDTO dto;

	@BeforeEach
	void setUp() throws Exception {
		dto = new FavoritesDTO(20,"pcwk01",144,"festival");
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void beans() {
		assertNotNull(context);
		assertNotNull(service);

		log.debug("context:{}", context);
		log.debug("service:{}", service);
	}

	//@Disabled
	@Test
	void getTourCount() {
		int count = service.getTourCount(dto.getUserId());
		log.debug("count:{}", count);
	}

	// @Disabled
	@Test
	void getFestaCount() {
		int count = service.getFestaCount(dto.getUserId());
		log.debug("count:{}", count);
	}

	//@Disabled
	@Test
	void doSelectOne() {
		service.doDelete(dto);

		service.doSave(dto);
		assertNotNull(dto);

		FavoritesDTO outVO = service.doSelectOne(dto);
		assertNotNull(outVO);
		log.debug("outVO :{}", outVO);

	}

	//@Disabled
	@Test
	void doSave() {
		service.doSave(dto);
		assertNotNull(dto);

		log.debug("dto :{}", dto);
	}

	//@Disabled
	@Test
	void doDeleteAnddoSave() {
		service.doDelete(dto);

		service.doSave(dto);
		assertNotNull(dto);

		log.debug("dto :{}", dto);
	}

}
