package com.pcwk.ehr.tour;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pcwk.ehr.cmn.SearchDTO;
import com.pcwk.ehr.mapper.TourMapper;
import com.pcwk.ehr.mapper.UserMapper;
import com.pcwk.ehr.region.domain.RegionDTO;
import com.pcwk.ehr.tour.domain.TourDTO;
import com.pcwk.ehr.tour.service.TourServiceImpl;
import com.pcwk.ehr.user.domain.UserDTO;

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
	TourMapper mapper;
	
	List<TourDTO> tours;
	
	
	TourDTO dto01;
	TourDTO dto02;
	TourDTO dto03;

	@BeforeEach
	void setUp() throws Exception {
		log.debug("┌─────────────────────┐");
		log.debug("│ setUp()             │");
		log.debug("└─────────────────────┘");
		
		tours = Arrays.asList(
				new TourDTO(0, "관광지1", "소제목1", "상세내용1", 0,                               
						"서울특별시 서대문구 123", "토요일", "09:00-16:00", "010-1111-2222", 100000, 0, null),
				new TourDTO(0, "관광지2", "소제목2", "상세내용2", 0,                               
						"세종특별자치시 123", "토요일", "09:00-16:00", "010-1111-2223", 200000, 0, null),  
				new TourDTO(0, "관광지3", "소제목3", "상세내용3", 0,                               
						"세종특별자치시 풍무로", "토요일", "09:00-16:00", "010-1111-2223", 200000, 0, null) 
);
		log.debug("context: {}", context);

        dto01 = new TourDTO(0, "관광지1", "소제목1", "상세내용1", 0,
                "서울특별시 서대문구 123", "토요일", "09:00-16:00", "010-1111-2222", 100000, 0, null);
        dto02 = new TourDTO(0, "관광지2", "소제목2", "상세내용2", 0,
                "세종특별자치시 123", "토요일", "09:00-16:00", "010-1111-2223", 200000, 0, null);
        dto03 = new TourDTO(0, "관광지3", "소제목3", "상세내용3", 0,
                "세종특별자치시 풍무로", "토요일", "09:00-16:00", "010-1111-2223", 200000, 0, null);
	
	
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
	//글자수 초과하는 경우
	//비정상적인 주소 입력
	//@Disabled
	@Test
	public void regexFailure() throws SQLException {
		mapper.deleteAll();
		//2.등록
		TourDTO tour2 = new TourDTO();
		tour2.setTourNo(0);
		tour2.setName("애버랜드");
		tour2.setSubtitle("환상의 동화나라");
		tour2.setContents("환상의 동화나라 애버랜드로 오세요!");
		tour2.setAddress("경기도 용인시 애버랜드로1");
		tour2.setHoliday("연중무휴");
		tour2.setTime("9:00~20:00");
		tour2.setTel("031-1111-44445");
		tour2.setFee(45000);
		log.debug("tour:{}", tour2);
		
		//실패 확인
//		int result = tourService.doSave(tour2);
//		log.debug("result:{}", result);
//		assertEquals(0, result);
		
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			tourService.doSave(tour2);
		});
		assertEquals("전화번호 형식이 올바르지 않습니다. (예: 010-1234-5678)", exception.getMessage());
	}
	
	//입력값이 없는 경우
	@Disabled
	@Test
	public void inputFailure() throws SQLException {
		log.debug("┌─────────────────────────┐");
		log.debug("│ *inputFailure()*        │");
		log.debug("└─────────────────────────┘");
		
		mapper.deleteAll();
		//2.등록
		TourDTO tour = new TourDTO();
		tour.setTourNo(0);
		tour.setName("");
		tour.setSubtitle("환상의 동화나라");
		tour.setContents("환상의 동화나라 애버랜드로 오세요!");
		tour.setAddress("경기도 용인시 애버랜드로1");
		tour.setHoliday("연중무휴");
		tour.setTime("9:00~20:00");
		tour.setTel("031-1111-4444");
		tour.setFee(45000);
		log.debug("tour:{}", tour);
		
//실패 확인
//		int result = tourService.doSave(tour);
//		log.debug("result:{}", result);
//		assertEquals(0, result);

		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			tourService.doSave(tour);
		});
		assertEquals("관광지명을 입력해 주세요.", exception.getMessage());
	}
	
	@Disabled
	@Test
	public void doUpdate() throws SQLException {
		log.debug("┌─────────────────────────┐");
		log.debug("│ *doUpdate()*            │");
		log.debug("└─────────────────────────┘");
		
		mapper.deleteAll();
		tourService.doSave(dto01);
		assertEquals(1, mapper.getCount());
		
		tourService.doUpdate(dto02);
		assertEquals(1, mapper.getCount());
		
	}
	@Disabled
	@Test
	public void doSave() throws SQLException {
		log.debug("┌─────────────────────────┐");
		log.debug("│ *doSave()*              │");
		log.debug("└─────────────────────────┘");
		//1.전체 삭제
		mapper.deleteAll();
		//2.등록
		dto01.setTourNo(0);
		dto01.setName("애버랜드");
		dto01.setSubtitle("환상의 동화나라");
		dto01.setContents("환상의 동화나라 애버랜드로 오세요!");
		dto01.setAddress("경기도 용인시 애버랜드로1");
		dto01.setHoliday("연중무휴");
		dto01.setTime("9:00~20:00");
		dto01.setTel("031-1111-4444");
		dto01.setFee(45000);
		log.debug("tour:{}", dto01);
		
		int result = tourService.doSave(dto01);
		log.debug("result:{}", result);
		assertEquals(1, result);
	}
	
	@Disabled
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
