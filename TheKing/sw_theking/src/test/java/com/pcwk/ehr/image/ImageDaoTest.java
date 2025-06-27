package com.pcwk.ehr.image;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

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
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pcwk.ehr.cmn.SearchDTO;
import com.pcwk.ehr.image.domain.ImageDTO;
import com.pcwk.ehr.mapper.ImageMapper;
import com.pcwk.ehr.tour.domain.TourDTO;
import com.pcwk.ehr.user.domain.UserDTO;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
class ImageDaoTest {
	Logger log = LogManager.getLogger(getClass());
	
	@Autowired
	ImageMapper mapper;
	
	ImageDTO dto01; 
	ImageDTO dto02; 
	ImageDTO dto03; 
	
	SearchDTO search;
	
	@Autowired
	ApplicationContext context;

	@BeforeEach
	void setUp() throws Exception {
		dto01 = new ImageDTO(0,1,"TOUR","이미지명1","이미지경로1","저장이름1","SYSDATE");
		dto02 = new ImageDTO(0,2,"TOUR","이미지명2","이미지경로2","저장이름2","SYSDATE");
		dto03 = new ImageDTO(0,3,"TOUR","이미지명3","이미지경로3","저장이름3","SYSDATE");
			
		search = new SearchDTO();		
	}

	@AfterEach
	void tearDown() throws Exception {
		log.debug("***************************");
		log.debug("@After");
		log.debug("***************************");
	}
	@Test
	void saveAll() throws SQLException {
		//1. 전체 삭제
    	mapper.deleteAll();
    	
    	//2.
    	int count = mapper.saveAll();
    	log.debug("count: "+count);
    	assertEquals(502,count);
    	
    	//paging
    	search.setPageSize(10);
    	search.setPageNo(1);
    	
    	List<ImageDTO> list = mapper.doRetrieve(search);
    	for (ImageDTO vo : list) {
    	    log.debug(vo);
    	}
	}

	//@Disabled
	@Test
	void UpateAndSelectOne() throws SQLException {
		// 매번 동일한 결과가 도출 되도록 작성
		// 1. 전체삭제
		// 2. 단건등록
		// 2.1 등록건수 비교
		// 3. 단건조회
		// 3.1 등록데이터 비교

		// 4. 단건조회 데이터 수정
		// 5. 수정
		// 6. 수정 조회
		// 7. 4 비교 6

		// 1.
		mapper.deleteAll();

		// 2.
		int flag = mapper.doSave(dto01);
		assertEquals(1, flag);

		// 2.1
		int count = mapper.getCount();
		assertEquals(count, 1);

		// 3.
		ImageDTO outVO = mapper.doSelectOne(dto01);
		assertNotNull(outVO);
		isSameUser(outVO, dto01);

		// 4.
		String upString = "_U";

		outVO.setImageName(outVO.getImageName() + upString);
		outVO.setImageUrl(outVO.getImageUrl() + upString);
		outVO.setSaveName(outVO.getSaveName() + upString);


		log.debug("outVO:" + outVO);

		// 5.
		flag = mapper.doUpdate(outVO);
		assertEquals(1, flag);

		// 6.
		ImageDTO upVO = mapper.doSelectOne(outVO);

		// 7.
		isSameUser(outVO, upVO);
		System.out.println("***");

	}
	//@Disabled
	@Test
	void saveAndDelete() throws SQLException {
		// 매번 동일한 결과가 도출 되도록 작성
		// 1. 전체삭제
		// 2. 단건등록
		// 2.1 등록건수 비교
		// 3. 삭제
		// 4. 등록건수 비교==0

		// 1.
		mapper.deleteAll();

		// 2.
		int flag = mapper.doSave(dto01);
		assertEquals(1, flag);

		// 2.1
		int count = mapper.getCount();
		assertEquals(count, 1);

		// 3.
		flag = mapper.doDelete(dto01);
		assertEquals(1, flag);

		// 4.
		count = mapper.getCount();
		assertEquals(0, count);
	}
	//@Disabled
	@Test
	public void addAndGet() throws SQLException {
		// 매번 동일한 결과가 도출 되도록 작성
		// 1. 전체삭제
		// 2. 단건등록
		// 2.1 전체건수 조회
		// 3. 단건조회
		// 4. 비교

		// 1.
		mapper.deleteAll();

		// 2.
		int flag = mapper.doSave(dto01);
		assertEquals(1, flag);

		// 2.1
		int count = mapper.getCount();
		assertEquals(count, 1);

		mapper.doSave(dto02);
		count = mapper.getCount();
		assertEquals(count, 2);

		mapper.doSave(dto03);
		int count2 = mapper.getCount();
		assertEquals(count2, 3);

		// 3.
		ImageDTO outVO = mapper.doSelectOne(dto01);
		assertNotNull(outVO);
		isSameUser(outVO, dto01);

		ImageDTO outVO2 = mapper.doSelectOne(dto02);
		assertNotNull(outVO2);
		isSameUser(outVO2, dto02);

		ImageDTO outVO3 = mapper.doSelectOne(dto03);
		assertNotNull(outVO3);
		isSameUser(outVO3, dto03);

	}
	//@Disabled
	@Test
	void beans() {
		assertNotNull(context);
		assertNotNull(mapper);

		log.debug(context);
		log.debug(mapper);
	}
	// 데이터 비교
		public void isSameUser(ImageDTO outVO, ImageDTO dto01) {
		    assertEquals(outVO.getImageName(), dto01.getImageName());
		    assertEquals(outVO.getImageUrl(), dto01.getImageUrl());
		    assertEquals(outVO.getSaveName(), dto01.getSaveName());
		    
		}

}
