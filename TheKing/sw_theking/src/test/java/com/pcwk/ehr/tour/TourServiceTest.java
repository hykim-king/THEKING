package com.pcwk.ehr.tour;

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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pcwk.ehr.mapper.TourMapper;
import com.pcwk.ehr.mapper.UserMapper;
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
	TourMapper mapper;
	
	List<TourDTO> tours;

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
);}

	@AfterEach
	void tearDown() throws Exception {
		log.debug("┌─────────────────────┐");
		log.debug("│ tearDown()          │");
		log.debug("└─────────────────────┘");
	}
	@Test
	public void doSave() throws SQLException {
		log.debug("┌─────────────────────────┐");
		log.debug("│ *doSave()*              │");
		log.debug("└─────────────────────────┘");
		//1.전체 삭제
		mapper.deleteAll();
		//2.등록
		TourDTO tour = new TourDTO();
		tour.setTourNo(0);
		tour.setName("애버랜드");
		tour.setSubtitle("환상의 동화나라");
		tour.setContents("환상의 동화나라 애버랜드로 오세요!");
		tour.setAddress("경기도 용인시 애버랜드로1");
		tour.setHoliday("연중무휴");
		tour.setTime("9:00~20:00");
		tour.setTel("031-1111-4444");
		tour.setFee(45000);
		log.debug("tour:{}", tour);
		
		int result = tourService.doSave(tour);
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
