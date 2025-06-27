/**
 * Package Name : com.pcwk.ehr.user <br/>
 * 파일명: UserTest.java <br/>
 */
package com.pcwk.ehr;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
import com.pcwk.ehr.mapper.UserMapper;
import com.pcwk.ehr.user.domain.UserDTO;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
public class UserDaoTest {
	Logger log = LogManager.getLogger(getClass());

	@Autowired
	UserMapper mapper;

	UserDTO dto01;
	UserDTO dto02;
	UserDTO dto03;

	SearchDTO search;
	
	@Autowired
	ApplicationContext context;

	@BeforeEach
	public void setUp() throws Exception {

		log.debug("context:" + context);

		dto01 = new UserDTO("pcwk01", "4321abc!@#" ,"이상무01", "이상무닉1", "pcwk01@naver.com", "010-1111-1111", "서울시 마포구 서교동11","admin", "profile", "사용안함", "사용안함");
		dto02 = new UserDTO("pcwk02", "1234abc!@#$","이상무02", "이상무닉2", "pcwk02@naver.com", "010-1111-1111", "서울시 마포구 서교동11","user" , "profile", "사용안함", "사용안함");
		dto03 = new UserDTO("pcwk03", "1234abcd!!" ,"이상무03", "이상무닉3", "pcwk03@naver.com", "010-1111-1111", "서울시 마포구 서교동11","user" , "profile", "사용안함", "사용안함");

		search = new SearchDTO();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	public void tearDown() throws Exception {
		log.debug("***************************");
		log.debug("@After");
		log.debug("***************************");
	}

	//@Disabled
	@Test
	void beans() {
		assertNotNull(context);
		assertNotNull(mapper);

		log.debug(context);
		log.debug(mapper);
	}

	//@Disabled
	@Test
	void doRetrieve() throws SQLException {
		// 매번 동일한 결과가 도출 되도록 작성
		// 1. 전체삭제
		// 2. 다건등록
		// 3. paging조회

		// 1.
		mapper.deleteAll();

		// 2.
		int count = mapper.saveAll();
		log.debug("count:" + count);
		assertEquals(502, count);

		//paging
		search.setPageSize(10);
		search.setPageNo(1);
		
		search.setSearchDiv("20");
		search.setSearchWord("이상무");
		
		// 3.
		List<UserDTO> list = mapper.doRetrieve(search);
		
		for (UserDTO vo : list) {
			log.debug(vo);
		}

		assertEquals(list.size(), 10);
	}

	//@Disabled
	@Test
	void doDelete() throws SQLException {
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
	void doUpate() throws SQLException {
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
		UserDTO outVO = mapper.doSelectOne(dto01);
		assertNotNull(outVO);
		isSameUser(outVO, dto01);

		// 4.
		String upString = "_U";

		outVO.setPassword(outVO.getPassword() + upString);
		outVO.setName(outVO.getName() + upString);
		outVO.setNickname(outVO.getNickname() + upString);
		outVO.setEmail(outVO.getEmail() + upString);
		outVO.setMobile(outVO.getMobile() + upString);
		outVO.setProfile(outVO.getProfile() + upString);

		log.debug("outVO:" + outVO);

		// 5.
		flag = mapper.doUpdate(outVO);
		assertEquals(1, flag);

		// 6.
		UserDTO upVO = mapper.doSelectOne(outVO);

		// 7.
		isSameUser(outVO, upVO);
		System.out.println("***");

	}

	//@Disabled
	@Test
	public void getAll() throws SQLException {
		// 매번 동일한 결과가 도출 되도록 작성
		// 1. 전체삭제
		// 2. 단건등록
		// 3. 단건등록
		// 4. 단건등록
		// 5. 전체조회: 3건

		// 1.
		mapper.deleteAll();

		// 2.
		int flag = mapper.doSave(dto01);
		assertEquals(1, flag);

		int count = mapper.getCount();
		assertEquals(count, 1);

		// 3
		mapper.doSave(dto02);
		count = mapper.getCount();
		assertEquals(count, 2);
		// 4
		mapper.doSave(dto03);
		count = mapper.getCount();
		assertEquals(count, 3);

		// 5
		List<UserDTO> userList = mapper.getAll();

		assertEquals(userList.size(), 3);

		for (UserDTO dto : userList) {
			log.debug(dto);
		}

	}

	@Disabled
	@Test
	public void getFailure() throws SQLException {
		// 매번 동일한 결과가 도출 되도록 작성
		// 1. 전체삭제
		// 2. 단건등록
		// 3. 단건조회

		// 1.
		mapper.deleteAll();

		// 2.
		mapper.doSave(dto01);
		assertEquals(1, mapper.getCount());

		UserDTO unknownUser = new UserDTO();
		unknownUser.setName(dto01.getName() + "_99");

		assertThrows(EmptyResultDataAccessException.class, () -> {
			mapper.doSelectOne(unknownUser);

		});

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
		count = mapper.getCount();
		assertEquals(count, 3);

		// 3.
		UserDTO outVO = mapper.doSelectOne(dto01);
		assertNotNull(outVO);
		isSameUser(outVO, dto01);

		UserDTO outVO2 = mapper.doSelectOne(dto02);
		assertNotNull(outVO2);
		isSameUser(outVO2, dto02);

		UserDTO outVO3 = mapper.doSelectOne(dto03);
		assertNotNull(outVO3);
		isSameUser(outVO3, dto03);

	}

	// 데이터 비교
	public void isSameUser(UserDTO outVO, UserDTO dto01) {
	    assertEquals(outVO.getUserId(), dto01.getUserId());
	    assertEquals(outVO.getPassword(), dto01.getPassword());
	    assertEquals(outVO.getName(), dto01.getName());
	    assertEquals(outVO.getNickname(), dto01.getNickname());
	    assertEquals(outVO.getEmail(), dto01.getEmail());
	    assertEquals(outVO.getMobile(), dto01.getMobile());
	    assertEquals(outVO.getAddress(), dto01.getAddress());
	    assertEquals(outVO.getRole(), dto01.getRole());
	    assertEquals(outVO.getProfile(), dto01.getProfile());
	}
}