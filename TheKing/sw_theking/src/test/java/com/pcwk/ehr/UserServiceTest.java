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

import com.pcwk.ehr.mapper.UserMapper;
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

	List<UserDTO> users;

	@Autowired
	DataSource dataSource;

	@Autowired
	PlatformTransactionManager transactionManager;
	
	@Qualifier("dummyMailService") //bean id로 특정 빈 주입
	@Autowired
	MailSender mailSender;
	
//	@Autowired
//	TestUserService testUserService;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {

		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ setUp()                                                 │");
		log.debug("└─────────────────────────────────────────────────────────┘");

		users = Arrays.asList(
				
				new UserDTO("pcwk01", "pcwk01234!", "이상무", "이상무01", "pcwk01@gmail.com", 
						"010-1111-1111","서울시 마포구 서교동 21-1","user","profile", "사용안함", "사용안함"),
				new UserDTO("pcwk02", "pcwk01234!", "이상무", "이상무01", "pcwk02@gmail.com", 
						"010-1111-1111","서울시 마포구 서교동 21-1","user","profile", "사용안함", "사용안함"),
				new UserDTO("pcwk03", "pcwk01234!", "이상무", "이상무01", "pcwk03@gmail.com", 
						"010-1111-1111","서울시 마포구 서교동 21-1","user","profile", "사용안함", "사용안함"),
				new UserDTO("pcwk04", "pcwk01234!", "이상무", "이상무01", "pcwk04@gmail.com", 
						"010-1111-1111","서울시 마포구 서교동 21-1","user","profile", "사용안함", "사용안함"),
				new UserDTO("admin", "admin123!", "관리자", "관리자", "admin01@gmail.com", 
						"010-1111-1111","서울시 마포구 서교동 21-1","user","profile", "사용안함", "사용안함"));
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
	void upgradeAllOrNothing() throws SQLException {
		log.debug("┌─────────────────────────────────┐");
		log.debug("│ upgradeAllOrNothing()           │");
		log.debug("└─────────────────────────────────┘");
		// 매번 동일한 결과가 도출 되도록 작성
		// 1.전체삭제
		// 2.5명 사용자 모두 입력
		// 3.등업
		// 3.1 예외
		
		try {
			// 1.
			mapper.deleteAll();
			assertEquals(0, mapper.getCount());

			// 2.
			for (UserDTO dto : users) {
				mapper.doSave(dto);
			}
			assertEquals(5, mapper.getCount());

			// 3.
//			testUserService.upgradeLevels();

		} catch (Exception e) {
			log.debug("┌─────────────────────────────────┐");
			log.debug("│ Exception()                     │" + e.getMessage());
			log.debug("└─────────────────────────────────┘");
		}
		
//		checkLevel(users.get(1), false);
	}

	//@Disabled
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
		user.setProfile("default.png");
		log.debug("user:{}", user);
		
		int result = userService.doSave(user);
		log.debug("result:{}", result);
		assertEquals(1, result);

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
