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

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.favorites.domain.FavoritesDTO;
import com.pcwk.ehr.mapper.FavoritesMapper;
import com.pcwk.ehr.mapper.TourMapper;
import com.pcwk.ehr.mapper.UserMapper;
import com.pcwk.ehr.tour.domain.TourDTO;
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
	
	List<UserDTO> users;
	
	TourDTO tour;
	
	@Autowired
	DataSource dataSource;

	@Autowired
	PlatformTransactionManager transactionManager;
	
	@Qualifier("dummyMailService") //bean id로 특정 빈 주입
	@Autowired
	MailSender mailSender;
	
	UserDTO dto01 = new UserDTO(0,"pcwk01", "pcwk01234!", "이상무", "이상무01", "pcwk01@gmail.com", 
			"010-1111-1111","서울시 마포구 서교동 21-1","user", "사용안함", "사용안함");
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {

		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ setUp()                                                 │");
		log.debug("└─────────────────────────────────────────────────────────┘");
		
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
		user.setUserId("test123465");
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
	
	@Test
    public void getFavoriteTours() throws Exception {			
		UserDTO userTest = new UserDTO(); 
		userTest.setUserNo(176);		
		UserDTO user01 = mapper.doSelectOne(userTest);
		assertNotNull(user01);		
		log.debug("user01:{}", user01);

		TourDTO tourDTO = new TourDTO();
		tourDTO.setTourNo(138);
		TourDTO tour01 = tourMapper.doSelectOne(tourDTO);
		log.debug("tour01:{}", tour01);
		
		FavoritesDTO fav = new FavoritesDTO();
		fav.setFavNo(0);
		fav.setUserId("pcwk01");
		fav.setTargetNo(tour01.getTourNo());
		fav.setTableName("tour");
		log.debug("fav:{}", fav);
		FavoritesDTO favorite01 = favoritesMapper.doSelectOne(fav);
		log.debug("favorite01:{}", favorite01);
		
//        List<TourDTO> favoriteTours = userService.getFavoriteTours(user.getUserId());
//
//        assertNotNull(favoriteTours, "즐겨찾기 관광지 리스트 Null");
//        log.debug("favoriteTours:{}", favoriteTours);
//        
//        for (TourDTO t : favoriteTours) {
//            assertNotNull(t.getName(), "관광지 이름 null");
//        }
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
