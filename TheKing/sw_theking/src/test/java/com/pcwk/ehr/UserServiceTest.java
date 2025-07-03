/**
 * Package Name : com.pcwk.ehr.user <br/>
 * 파일명: UserServiceTest.java <br/> 
 */
package com.pcwk.ehr;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.MailSender;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;

import com.pcwk.ehr.favorites.domain.FavoritesDTO;
import com.pcwk.ehr.festival.domain.FestivalDTO;
import com.pcwk.ehr.mapper.FavoritesMapper;
import com.pcwk.ehr.mapper.FestivalMapper;
import com.pcwk.ehr.mapper.TourMapper;
import com.pcwk.ehr.mapper.UserMapper;
import com.pcwk.ehr.tour.domain.TourDTO;
import com.pcwk.ehr.tour.service.TourService;
import com.pcwk.ehr.user.domain.UserDTO;
import com.pcwk.ehr.user.service.UserService;

/**
 * @author user
 *
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml" 
		, "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
class UserServiceTest {
	Logger log = LogManager.getLogger(getClass());

	@Autowired
	ApplicationContext context;

	@Autowired
	UserService userService;

	@Autowired
	UserMapper mapper;

	@Autowired
	FavoritesMapper favoritesMapper;
	
	@Autowired
	TourMapper tourMapper;
	
	@Autowired
	TourService tourService;
	
	@Autowired
	FestivalMapper festivalMapper;
	
	UserDTO dto01;
	List<UserDTO> users;
	FavoritesDTO favorite01;
	FavoritesDTO favorite02;
	TourDTO tour;
	FestivalDTO festival;
	
	@Autowired
	DataSource dataSource;

	@Autowired
	PlatformTransactionManager transactionManager;
	
	@Qualifier("dummyMailService") //bean id로 특정 빈 주입
	@Autowired
	MailSender mailSender;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {

		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ setUp()                                                 │");
		log.debug("└─────────────────────────────────────────────────────────┘");
		
		dto01 = new UserDTO(0,"pcwk01", "pcwk01234!", "이상무", "이상무01", "pcwk01@gmail.com", 
				"010-1111-1111","서울시 마포구 서교동 21-1","user", "사용안함", "사용안함");
		
		tour = new TourDTO(0, "관광지1", "소제목1", "상세내용1", 0,
                "서울특별시 서대문구 123", "토요일", "09:00-16:00", "010-1111-2222", 100000, 0, null);
                
		favorite01 = new FavoritesDTO(0, "pcwk01", 10, "tour");
		favorite02 = new FavoritesDTO(0, "pcwk01", 10, "festival");
		
		festival = new FestivalDTO(0, "축제1", "축제 시작1", "축제가 시작됩니다.", 0, "경기도 고양시", "010-1234-1234", 10000, 41280,
				"2025-06-12", "2025-07-12");
		
		users = Arrays.asList(
				
				new UserDTO(0,"pcwk01", "pcwk01234!", "이상무", "이상무01", "pcwk01@gmail.com", 
						"010-1111-1111","서울시 마포구 서교동 21-1","user", "사용안함", "사용안함"),
				new UserDTO(0,"pcwk02", "pcwk01234!", "이상무", "이상무01", "pcwk02@gmail.com", 
						"010-1111-1111","서울시 마포구 서교동 21-1","user", "사용안함", "사용안함"),
				new UserDTO(0,"pcwk03", "pcwk01234!", "이상무", "이상무01", "pcwk03@gmail.com", 
						"010-1111-1111","서울시 마포구 서교동 21-1","user", "사용안함", "사용안함"),
				new UserDTO(0,"pcwk04", "pcwk01234!", "이상무", "이상무01", "pcwk04@gmail.com", 
						"010-1111-1111","서울시 마포구 서교동 21-1","user", "사용안함", "사용안함"),
				new UserDTO(0,"admin", "admin123!", "관리자", "관리자", "admin01@gmail.com", 
						"010-1111-1111","서울시 마포구 서교동 21-1","user", "사용안함", "사용안함"));
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ tearDown()                                              │");
		log.debug("└─────────────────────────────────────────────────────────┘");
	}

	@Disabled
	@Test
	public void doSave() throws SQLException {
		log.debug("┌─────────────────────────┐");
		log.debug("│ *doSave()*              │");
		log.debug("└─────────────────────────┘");

		// 1. 전체 삭제
		mapper.deleteAll();
		assertEquals(0, mapper.getCount());
		
		//2.등록
		UserDTO user = new UserDTO();
		user.setUserId("pcwk01");
		user.setPassword("Test@1234");
		user.setName("홍길동");
		user.setNickname("별명");
		user.setEmail("test" + "@email.com");
		user.setMobile("010-1234-5678");
		user.setAddress("서울시 마포구 서교동 22-1");
		user.setRole("user");
		log.debug("user:{}", user);
		
		int result = userService.doSave(user);
		log.debug("result:{}", result);
		assertEquals(1, result);

	}
	
	//@Disabled
	@Test
    public void getFavoriteFestival() throws Exception {
		//1. 전체삭제
		mapper.deleteAll();
		//2. 등록		
		int flag = mapper.doSave(dto01);
		assertEquals(1, flag);	
		log.debug("dto01:{}", dto01);
		
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
        List<FestivalDTO> favoriteFestival = userService.getFavoriteFestivals(dto01.getUserId());

        assertNotNull(favoriteFestival, "즐겨찾기 축제 리스트 Null");
        log.debug("favoriteFestival:{}", favoriteFestival);
        
        for (FestivalDTO f : favoriteFestival) {
            assertNotNull(f.getName(), "축제 이름 null");
        }
    }
	
	//@Disabled
	@Test
    public void getFavoriteTours() throws Exception {			
		//1. 전체삭제
		mapper.deleteAll();
		
		//2. 등록		
		int flag = mapper.doSave(dto01);
		assertEquals(1, flag);	
		log.debug("dto01:{}", dto01);

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
        List<TourDTO> favoriteTours = userService.getFavoriteTours(dto01.getUserId());

        assertNotNull(favoriteTours, "즐겨찾기 관광지 리스트 Null");
        log.debug("favoriteTours:{}", favoriteTours);
        
        for (TourDTO t : favoriteTours) {
            assertNotNull(t.getName(), "관광지 이름 null");
        }
    }
	
	@Disabled
	@Test
	void beans() {
		assertNotNull(mapper);
		assertNotNull(context);
		assertNotNull(userService);
		assertNotNull(dataSource);
		assertNotNull(transactionManager);
		assertNotNull(mailSender);
		
		log.debug("context:" + context);
		log.debug("userService:" + userService);
		log.debug("userDao:" + mapper);
		log.debug("dataSource:" + dataSource);
		log.debug("transactionManager:" + transactionManager);
		log.debug("mailSender" + mailSender);
	}

}
