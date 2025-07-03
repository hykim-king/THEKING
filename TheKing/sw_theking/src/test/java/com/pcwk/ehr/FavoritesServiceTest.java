package com.pcwk.ehr;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.pcwk.ehr.favorites.domain.FavoritesDTO;
import com.pcwk.ehr.favorites.service.FavoritesService;
import com.pcwk.ehr.festival.domain.FestivalDTO;
import com.pcwk.ehr.mapper.FavoritesMapper;
import com.pcwk.ehr.mapper.FestivalMapper;
import com.pcwk.ehr.mapper.TourMapper;
import com.pcwk.ehr.mapper.UserMapper;
import com.pcwk.ehr.tour.domain.TourDTO;
import com.pcwk.ehr.tour.service.TourService;
import com.pcwk.ehr.user.domain.UserDTO;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
class FavoritesServiceTest {
	Logger log = LogManager.getLogger(getClass());

	@Autowired
	WebApplicationContext webApplicationContext;

	@Autowired
	ApplicationContext context;
	
	@Autowired
	FavoritesService service;

	FavoritesDTO dto;

	MockMvc mockMvc;
	
	UserDTO userDTO01;
	UserDTO userDTO02;
	
	FestivalDTO festival;
	TourDTO tour;
	FavoritesDTO favorite01;
	FavoritesDTO favorite02;
	
	@Autowired
	UserMapper userMapper;
	
	@Autowired
	TourMapper tourMapper;
	
	@Autowired
	FavoritesMapper favoritesMapper;
	
	@Autowired
	FestivalMapper festivalMapper;
	
	@Autowired
	TourService tourService;
	
	
	@BeforeEach
	void setUp() throws Exception {
		dto = new FavoritesDTO(20,"pcwk01",144,"festival");
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		userDTO01 = new UserDTO(0,"pcwk01", "4321abc!@#" ,"이상무01", "이상무닉1", "pcwk01@naver.com", "010-1111-1111", "서울시 마포구 서교동11","admin", "사용안함", "사용안함");
		userDTO02 = new UserDTO(0,"pcwk02", "1234abc!@#$","이상무02", "이상무닉2", "pcwk02@naver.com", "010-1111-1111", "서울시 마포구 서교동11","user" , "사용안함", "사용안함");
		
		festival = new FestivalDTO(0, "축제1", "축제 시작1", "축제가 시작됩니다.", 0, "경기도 고양시", "010-1234-1234", 10000, 41280,
				"2025-06-12", "2025-07-12");
		
		tour = new TourDTO(0, "관광지1", "소제목1", "상세내용1", 0,
                "서울특별시 서대문구 123", "토요일", "09:00-16:00", "010-1111-2222", 100000, 0, null);
         
		favorite01 = new FavoritesDTO(0, "pcwk01", 10, "tour");
		favorite02 = new FavoritesDTO(0, "pcwk01", 10, "festival");

	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Disabled
	@Test
	public void getFavoriteFestival() throws Exception {
		//1. 전체삭제
		userMapper.deleteAll();
		//2. 등록		
		int flag = userMapper.doSave(userDTO01);
		assertEquals(1, flag);	
		log.debug("dto01:{}", userDTO01);
		
		//3. 축제 조회
		festivalMapper.deleteAll();
		flag = festivalMapper.doSave(festival);
		assertEquals(1, flag);	
		log.debug("festival:{}", festival);
		
		//4. 즐겨찾기 조회
		favoritesMapper.deleteAll();
		favorite02.setTargetNo(festival.getFestaNo());
		flag = favoritesMapper.doSave(favorite02);
		assertEquals(1, flag);	
		log.debug("favorite:{}", favorite02);
		
		//5. 아이디값 비교
		    List<FestivalDTO> favoriteFestival = service.getFavoriteFestivals(userDTO01.getUserId());
	
		    assertNotNull(favoriteFestival, "즐겨찾기 축제 리스트 Null");
		    log.debug("favoriteFestival:{}", favoriteFestival);
		    
		    for (FestivalDTO f : favoriteFestival) {
		        assertNotNull(f.getName(), "축제 이름 null");
		    }
		}
	
	@Disabled
	@Test
	public void getFavoriteTours() throws Exception {			
		//1. 전체삭제
		userMapper.deleteAll();
		
		//2. 등록		
		int flag = userMapper.doSave(userDTO01);
		assertEquals(1, flag);	
		log.debug("dto01:{}", userDTO01);
			
		//3. 투어 조회
		tourMapper.deleteAll();
		flag = tourService.doSave(tour);
		assertEquals(1, flag);	
		log.debug("tour:{}", tour);
		
		//4. 즐겨찾기 조회
		favoritesMapper.deleteAll();
		favorite01.setTargetNo(tour.getTourNo());
		flag = favoritesMapper.doSave(favorite01);
		assertEquals(1, flag);	
		log.debug("favorite:{}", favorite01);
		
		//5. 아이디값 비교
		List<TourDTO> favoriteTours = service.getFavoriteTours(userDTO01.getUserId());
			
		assertNotNull(favoriteTours, "즐겨찾기 관광지 리스트 Null");
		log.debug("favoriteTours:{}", favoriteTours);
		    
		for (TourDTO t : favoriteTours) {
		    assertNotNull(t.getName(), "관광지 이름 null");
		}
		}
	
	@Disabled
	@Test
	void beans() {
		assertNotNull(context);
		assertNotNull(service);

		log.debug("context:{}", context);
		log.debug("service:{}", service);
	}

	@Disabled
	@Test
	void getTourCount() {
		int count = service.getTourCount(dto.getUserId());
		log.debug("count:{}", count);
	}

	@Disabled
	@Test
	void getFestaCount() {
		int count = service.getFestaCount(dto.getUserId());
		log.debug("count:{}", count);
	}

	@Disabled
	@Test
	void doSelectOne() {
		service.doDelete(dto);

		service.doSave(dto);
		assertNotNull(dto);

		FavoritesDTO outVO = service.doSelectOne(dto);
		assertNotNull(outVO);
		log.debug("outVO :{}", outVO);

	}

	@Disabled
	@Test
	void doSave() {
		service.doSave(dto);
		assertNotNull(dto);

		log.debug("dto :{}", dto);
	}

	@Disabled
	@Test
	void doDeleteAnddoSave() {
		service.doDelete(dto);

		service.doSave(dto);
		assertNotNull(dto);

		log.debug("dto :{}", dto);
	}

}
