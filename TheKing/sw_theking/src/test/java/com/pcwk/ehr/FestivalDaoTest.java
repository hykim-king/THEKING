package com.pcwk.ehr;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.SQLException;
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
import com.pcwk.ehr.mapper.FestivalMapper;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
class FestivalDaoTest {

	Logger log = LogManager.getLogger(getClass());
	
	FestivalDTO dto01;
	FestivalDTO dto02;
	FestivalDTO dto03;
	
	SearchDTO searchDTO = new SearchDTO();
	
	@Autowired
	FestivalMapper mapper;
	@Autowired
	ApplicationContext context;
	@BeforeEach
	void setUp() throws Exception {
		dto01 =new FestivalDTO(1, "축제1","축제 시작1" , "축제가 시작됩니다.",0, "경기도 고양시", "010-1234-1234",
				10000, 41280, "2025-06-12", "2025-07-12");
		dto02 =new FestivalDTO(2, "축제2","축제 시작2" , "축제가 시작됩니다.",0, "경기도 고양시", "010-1234-1234",
				10000, 41280, "2025-06-12", "2025-07-12");
		dto03 =new FestivalDTO(3, "축제3","축제 시작3" , "축제가 시작됩니다.",0, "경기도 고양시", "010-1234-1234",
				10000, 41280, "2025-06-12", "2025-07-12");
		
		
	}
 
	@AfterEach
	void tearDown() throws Exception {
		log.debug("***************************");
		log.debug("@After");
		log.debug("***************************");
	}
	
	

	@Test
	void beans() {
		assertNotNull(context);
		assertNotNull(mapper);
		
		log.debug("context : " + context);
		log.debug("mapper : " + mapper);
	}
	
	@Test
	void checkRetrieve() {
		//null or 값  
		String sido = null;
		String date ="2025-06-26";
		searchDTO.setPageNo(1);
		searchDTO.setPageSize(10);
		List<FestivalDTO> list = mapper.checkRetrieve(sido, date, searchDTO);
		assertNotNull(list);
		log.debug(list);
		for(FestivalDTO outVO : list) {
			log.debug(outVO);
		}
	}
	
	
	@Disabled
	@Test
	void upView(){
		//삭제
		mapper.doDelete(dto01);

		//등록
		mapper.doSave(dto01);
		assertNotNull(dto01);
		
		//조회수 증가
		mapper.upViews(dto01);
		
		FestivalDTO vo = mapper.doSelectOne(dto01);
		log.debug(vo.getViews());
		
		assertEquals(1, vo.getViews());
	}
	
	
	@Disabled
	@Test
	void getCount() throws SQLException {
		int count = mapper.getCount();
		
		log.debug("count :{}",count);
		
		assertEquals(52, count);
		
	}
	
	
	@Disabled
	@Test
	void doRetrieve() {

		searchDTO.setPageNo(2);
		searchDTO.setPageSize(20);
		List<FestivalDTO> list = mapper.doRetrieve(searchDTO);
		assertNotNull(list);
		for(FestivalDTO outVO : list) {
			log.debug(outVO);
		}
	}
	
	@Disabled
	@Test
	void doUpdate() {
		//삭제
		mapper.doDelete(dto01);

		//등록
		mapper.doSave(dto01);
		assertNotNull(dto01);
		
		log.debug("dto01 :{}",dto01);
		//수정
		dto01.setName("축제9999");
		dto01.setContents("축제09999");
		dto01.setSubtitle("재밌는 축제요~~~");
		dto01.setAddress("서울시 마포구");
		mapper.doUpdate(dto01);
		
		FestivalDTO outVO = mapper.doSelectOne(dto01);
		log.debug("outVO :{}",outVO);
		assertEquals(outVO.getName(), dto01.getName());
		assertEquals(outVO.getContents(), dto01.getContents());
		assertEquals(outVO.getSubtitle(), dto01.getSubtitle());
		assertEquals(outVO.getAddress(), dto01.getAddress());
		
	}
	
	@Disabled
	@Test
	void doSelectOne() {
		FestivalDTO outVO = mapper.doSelectOne(dto01);
		assertNotNull(outVO);
		log.debug("outVO :{}",outVO);
		
		outVO = mapper.doSelectOne(dto02);
		assertNotNull(outVO);
		log.debug("outVO :{}",outVO);
		
		outVO = mapper.doSelectOne(dto03);
		assertNotNull(outVO);
		log.debug("outVO :{}",outVO);
	}
	
	@Disabled
	@Test
	void deleteAnddoSave() throws SQLException {
		mapper.doDelete(dto01);
		mapper.doDelete(dto02);
		mapper.doDelete(dto03);		

		mapper.doSave(dto01);
		assertNotNull(dto01);
		mapper.doSave(dto02);
		assertNotNull(dto02);
		mapper.doSave(dto03);
		assertNotNull(dto03);
		
	}
	@Disabled
	@Test
	void doSave() throws SQLException{
		mapper.doSave(dto01);
		assertNotNull(dto01);
		mapper.doSave(dto02);
		assertNotNull(dto02);
		mapper.doSave(dto03);
		assertNotNull(dto03);
	}

}
