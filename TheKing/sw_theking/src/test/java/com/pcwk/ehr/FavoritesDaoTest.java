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
import com.pcwk.ehr.mapper.FavoritesMapper;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
class FavoritesDaoTest {
	Logger log = LogManager.getLogger(getClass());
	
	@Autowired
	FavoritesMapper mapper;
	@Autowired
	ApplicationContext context;
	
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
		assertNotNull(mapper);
		
		log.debug("context :{}", context);
		log.debug("mapper :{}", mapper);
	}
	//@Disabled
	@Test
	void getTourCount() {
		int count = mapper.getTourCount(dto.getUserId());
		log.debug("count:{}",count);
	}
	//@Disabled
	@Test
	void getFestaCount() {
		int count = mapper.getFestaCount(dto.getUserId());
		log.debug("count:{}",count);
	}
	
	//@Disabled
	@Test
	void doSelectOne() {
		mapper.doDelete(dto);
		
		mapper.doSave(dto);
		assertNotNull(dto);
		
		FavoritesDTO outVO = mapper.doSelectOne(dto);
		assertNotNull(outVO);
		log.debug("outVO :{}", outVO);
		
	}
	
	//@Disabled
	@Test
	void doSave() {
		mapper.doSave(dto);
		assertNotNull(dto);
		
		log.debug("dto :{}",dto);
	}
	
	//@Disabled
	@Test
	void doDeleteAnddoSave() {
		mapper.doDelete(dto);
		
		mapper.doSave(dto);
		assertNotNull(dto);
		
		log.debug("dto :{}", dto);
	}

}
