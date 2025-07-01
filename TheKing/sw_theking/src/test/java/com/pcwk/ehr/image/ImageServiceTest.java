package com.pcwk.ehr.image;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

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

import com.pcwk.ehr.image.domain.ImageDTO;
import com.pcwk.ehr.image.service.ImageService;
import com.pcwk.ehr.image.service.ImageServiceImpl;
import com.pcwk.ehr.mapper.ImageMapper;
import com.pcwk.ehr.user.domain.UserDTO;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml" 
		, "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
class ImageServiceTest {
	
	Logger log = LogManager.getLogger(getClass());
	
	@Autowired
	ApplicationContext context;
	
	@Autowired
	ImageMapper mapper;
	
	@Autowired
	ImageService imageService;
	
	ImageDTO dto01; 
	ImageDTO dto02; 
	ImageDTO dto03;
	
	@BeforeEach
	void setUp() throws Exception {
		log.debug("┌──────────────────────────┐");
		log.debug("│ setUp()                  │");
		log.debug("└──────────────────────────┘");
		
		dto01 = new ImageDTO(0,1,"TOUR","이미지명1.jpg","이미지경로1","저장이름1","SYSDATE");
		dto02 = new ImageDTO(0,2,"TOUR","이미지명2","이미지경로2","저장이름2","SYSDATE");
		dto03 = new ImageDTO(0,3,"TOUR","이미지명3","이미지경로3","저장이름3","SYSDATE");
	}

	@AfterEach
	void tearDown() throws Exception {
		log.debug("┌──────────────────────────┐");
		log.debug("│ After()                  │");
		log.debug("└──────────────────────────┘");
	}
	
	//@Disabled
	@Test
	void charLimit() throws SQLException {
		//1.전체 삭제
		mapper.deleteAll();
		
		dto01.setImageName("30자이상이면실패30자이상이면실패30자이상이면실패.jpg");
		
//실패 확인 완료
//		int result = imageService.doSave(dto01);
//		log.debug("result:{}", result);
//		assertEquals(1, result);	
		
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			imageService.doSave(dto01);
		});
		assertEquals("이미지명은 30자를 넘을 수 없습니다.", exception.getMessage());
  }
	
	
	//@Disabled
	@Test
	void ValidationFailure() throws SQLException {
		//1.전체 삭제
		mapper.deleteAll();
		
		dto01.setImageName("이미지명99.pp");
// 실패 확인 완료
//		int result = imageService.doSave(dto01);
//		log.debug("result:{}", result);
//		assertEquals(1, result);		
		
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			imageService.doSave(dto01);
		});
		assertEquals("허용되지 않은 이미지 형식입니다.", exception.getMessage());
	}
	
	//@Disabled
	@Test
	void doSave() throws SQLException {
		//1.전체 삭제
		mapper.deleteAll();
		//2.단건 등록
		int flag = imageService.doSave(dto01);
		assertEquals(1, flag);	
	}
	
	//@Disabled
	@Test
	void beans() {
		assertNotNull(context);
		assertNotNull(mapper);
		assertNotNull(imageService);
		
		log.debug("context: {}",context);
		log.debug("mapper: {}",mapper);
		log.debug("imageService: {}",imageService);
	}

}
