package com.pcwk.ehr.tour;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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

import com.pcwk.ehr.image.domain.ImageDTO;
import com.pcwk.ehr.image.service.ImageServiceImpl;
import com.pcwk.ehr.mapper.ImageMapper;
import com.pcwk.ehr.mapper.TourMapper;
import com.pcwk.ehr.tour.domain.TourDTO;
import com.pcwk.ehr.tour.service.TourServiceImpl;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml" 
		, "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
class TourServiceTest {
	Logger log = LogManager.getLogger(getClass());

	@Autowired
	ApplicationContext context;
	
	@Autowired
	TourServiceImpl tourService;
	
	@Autowired
	ImageServiceImpl imageService;
	
	@Autowired
	TourMapper mapper;
	
	@Autowired
	ImageMapper imageMapper;
		
	TourDTO dto01;


	@BeforeEach
	void setUp() throws Exception {
		log.debug("┌─────────────────────┐");
		log.debug("│ setUp()             │");
		log.debug("└─────────────────────┘");
		
		log.debug("context: {}", context);

        dto01 = new TourDTO(0, "관광지1", "소제목1", "상세내용1", 0,
                "서울특별시 서대문구 123", "토요일", "09:00-16:00", "010-1111-2222", 100000, 0, null);
  
	}

	@AfterEach
	void tearDown() throws Exception {
		log.debug("┌─────────────────────┐");
		log.debug("│ tearDown()          │");
		log.debug("└─────────────────────┘");
	}
	//dosave 0
	//doupdate 0
	//입력값이 없는 경우 0
	//정규식 위배하는 경우0
	//글자수 초과하는 경우0
	//비정상적인 주소 입력0
	//doSave - 이미지가 있는 경우 추가0
	//doUpdate 이미지 추가0
	//이미지 삭제0
	//doDelete - 이미지가 있는 경우 삭제0
	@Test
	void imageDelete() throws SQLException {
		//1. 전체 삭제
		mapper.deleteAll();
		
		//2.새로운 이미지 추가
		List<String>images = Arrays.asList("img_001.jpg", "img_002.jpg");
		dto01.setTourImage(images);
		tourService.doSave(dto01);
		//3.이미지 조회
		List<ImageDTO>saveImages = imageMapper.getImages(dto01.getTourNo(), "TOUR");
		
		List<String> actualImageNames = new ArrayList<>();
		for(ImageDTO dto : saveImages) {
			 actualImageNames.add(dto.getImageName());
		}
		assertEquals(2,actualImageNames.size());
		//4.이미지 삭제
		int flag = tourService.doUpdate(dto01);
		log.debug("flag: {}",flag);
		assertEquals(1,flag);
		tourService.doSelectOne(dto01);
	}
	
	//@Disabled
	@Test
	void imageUpdate() throws SQLException {
		//1. 전체 삭제
		mapper.deleteAll();
		//2.새로운 이미지 추가
		List<String>images = Arrays.asList("img_001.jpg", "img_002.jpg");
		dto01.setTourImage(images);
		tourService.doSave(dto01);
		//3.이미지 변경
		List<String> updateImages = Arrays.asList("img_002.jpg", "img_003.jpg");
		dto01.setTourImage(updateImages);
		//4.업데이트
		tourService.doUpdate(dto01);
		//5.조회
		List<ImageDTO>saveImages = imageMapper.getImages(dto01.getTourNo(), "TOUR");
		
		List<String> actualImageNames = new ArrayList<>();
		for(ImageDTO dto : saveImages) {
			 actualImageNames.add(dto.getImageName());
		}
		assertFalse(actualImageNames.contains("img_001.jpg"));
		assertTrue(actualImageNames.contains("img_002.jpg"));
		assertTrue(actualImageNames.contains("img_003.jpg"));
		
		assertEquals(2,actualImageNames.size());
		
	}
	
	//doSave - 이미지가 있는 경우 추가
	//@Disabled
	@Test
	void imageSave() throws SQLException {
		//1. 전체 삭제
		//2. 이미지 추가 후 조회
		//4. 조회
		
		//1.
		mapper.deleteAll();
		
		//2.
		List<String> images = new ArrayList<>();
		images.add("img_001.jpg");
		
		dto01.setTourImage(images);
		
		int flag = tourService.doSave(dto01);
		log.debug("dto01: {}",dto01);
		assertEquals(1,flag);
		
		mapper.doSelectOne(dto01);
		
	}
	//@Disabled
	@Test
	void AddressNullFailure() throws SQLException {
		mapper.deleteAll();
		tourService.doSave(dto01);
		//update
		dto01.setAddress("");
		log.debug("dto01:{}", dto01);
		
//실패 확인 완료	
//		int result = tourService.doSave(dto01);
//		log.debug("result:{}", result);
		
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			tourService.doUpdate(dto01);
		});
		assertEquals("주소가 유효하지 않습니다.", exception.getMessage());
	}
	//@Disabled
	@Test 
	void addressValidFailure() throws SQLException {
		mapper.deleteAll();
		tourService.doSave(dto01);
		//2.등록
		dto01.setAddress("경기도도 용인시");
		log.debug("dto01:{}", dto01);
		
//실패 확인 완료	
//		int result = tourService.doSave(dto01);
//		log.debug("result:{}", result);
		Exception exception = assertThrows(IllegalStateException.class, () -> {
			tourService.doUpdate(dto01);
		});
		assertEquals("해당 주소에 대한 지역 번호가 존재하지 않습니다.", exception.getMessage());
		
	}
	//구군이 없는 세종특별자치시 등록 성공
	//@Disabled
	@Test 
	public void addressValidSussecc() throws SQLException {
		mapper.deleteAll();
		tourService.doSave(dto01);
		//update
		dto01.setAddress("세종특별자치시");
		log.debug("dto01:{}", dto01);
		
		int result = tourService.doUpdate(dto01);
		log.debug("result:{}", result);
		assertEquals(1, result);
		
		
	}
	
	//길이 제한 테스트 
	//관광지면 30자 이하
	//소제목 50자 이하
	//홀리데이 15자
	//@Disabled
	@Test
	public void charLimit() throws SQLException {
		mapper.deleteAll();
		tourService.doSave(dto01);
		//update
		dto01.setSubtitle("환상의 동화나라 애버랜드로 오세요!환상의 동화나라 애버랜드로 오세요!환상의 동화나라 애버랜드로 오세요!환상의 동화나라 애버랜드로 오세요!");
		
		log.debug("tour:{}", dto01);

//실패 확인 완료
//		int result = tourService.doSave(dto01);
//		log.debug("result:{}", result);
		
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			tourService.doUpdate(dto01);
		});
		assertEquals("소제목은 50자 이내로 입력해 주세요.", exception.getMessage());
	}
	//Tel 정규식 검사
	//@Disabled
	@Test
	public void regexFailure() throws SQLException {
		mapper.deleteAll();
		tourService.doSave(dto01);
		//update
		dto01.setTel("031-1111-44445");
		log.debug("tour:{}", dto01);
		
//실패 확인 완료
//		int result = tourService.doSave(dto01);
//		log.debug("result:{}", result);
		
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			tourService.doUpdate(dto01);
		});
		assertEquals("전화번호 형식이 올바르지 않습니다. (예: 010-1234-5678)", exception.getMessage());
	}
	
	//필수값의 입력값이 없는 경우(NotNull : Name, Contents, Address)
	//@Disabled
	@Test
	public void inputFailure() throws SQLException {
		log.debug("┌─────────────────────────┐");
		log.debug("│ *inputFailure()*        │");
		log.debug("└─────────────────────────┘");
		
		mapper.deleteAll();
		tourService.doSave(dto01);
		//update
		dto01.setName("");
		log.debug("tour:{}", dto01);
//실패 확인 완료
//		int result = tourService.doUpdate(dto01);
//		log.debug("result:{}", result);

		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			tourService.doUpdate(dto01);
		});
		assertEquals("관광지명을 입력해 주세요.", exception.getMessage());
	}
	
	
	//@Disabled
	@Test
	public void doSave() throws SQLException {
		log.debug("┌─────────────────────────┐");
		log.debug("│ *doSave()*              │");
		log.debug("└─────────────────────────┘");
		//1.전체 삭제
		mapper.deleteAll();
		//2.등록
		int flag = tourService.doSave(dto01);
		log.debug("tour:{}", dto01);
		
		assertEquals(1, flag);
	}
	
	//@Disabled
	@Test
	void beans() {
		assertNotNull(tourService);
		assertNotNull(context);
		assertNotNull(mapper);
		
		log.debug("tourService:" + tourService);
		log.debug("context:" + context);
		log.debug("mapper:" + mapper);
		
	}

}
