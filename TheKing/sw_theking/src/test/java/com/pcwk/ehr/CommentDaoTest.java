package com.pcwk.ehr;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pcwk.ehr.cmn.SearchDTO;
import com.pcwk.ehr.comment.domain.CommentDTO;
import com.pcwk.ehr.comment.service.CommentService;
import com.pcwk.ehr.mapper.CommentMapper;
import com.pcwk.ehr.user.domain.UserDTO;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
class CommentDaoTest {
	Logger log = LogManager.getLogger(getClass());
	
	@Autowired
	CommentMapper mapper;

	CommentDTO dto01;
	CommentDTO dto02;
	CommentDTO dto03;

	SearchDTO search;
	
	@Autowired
	CommentService commentService;
	
	@Autowired
	ApplicationContext context;
	
	@BeforeEach
	void setUp() throws Exception {
		
		log.debug("context:" + context);

		dto01 = new CommentDTO(1, "pcwk01" ,"테스트 댓글 내용1", 10, "TOUR", "사용안함", "사용안함");
		dto02 = new CommentDTO(2, "pcwk02" ,"테스트 댓글 내용2", 20, "FESTIVAL", "사용안함", "사용안함");
		dto03 = new CommentDTO(3, "pcwk03" ,"테스트 댓글 내용3", 30, "BOARD", "사용안함", "사용안함");

		search = new SearchDTO();
		
	}

	@AfterEach
	void tearDown() throws Exception {
		log.debug("***************************");
		log.debug("@After");
		log.debug("***************************");
	}

	//@Disabled
	@Test
	void beans() {
		assertNotNull(context);
		assertNotNull(mapper);
		assertNotNull(search);
		assertNotNull(commentService);

		log.debug(commentService);
		log.debug(context);
		log.debug(search);
		log.debug(mapper);
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
		CommentDTO comment = new CommentDTO();
		comment.setComNo(1);
		comment.setUserId("pcwk01");
		comment.setContents("오늘은 즐거운 금요일");
		comment.setTargetNo(10);
		comment.setTableName("TOUR");
		log.debug("comment:{}", comment);

		int result = commentService.doSave(comment);
		log.debug("result:{}", result);
		assertEquals(1, result);

	}
	
	//@Disabled
	@Test
	void doDelete() throws SQLException {
		// 1.전체삭제
		mapper.deleteAll();

		// 2.단건등록
		int flag = mapper.doSave(dto01);
		assertEquals(1, flag);

		// 2.1 등록건수 비교
		int count = mapper.getCount();
		assertEquals(count, 1);

		// 3. 삭제
		flag = mapper.doDelete(dto01);
		assertEquals(1, flag);

		// 4. 등록건수 비교==0
		count = mapper.getCount();
		assertEquals(0, count);
	}
	
	//@Disabled
	@Test
	void doUpate() throws SQLException {
		// 1. 전체삭제
		mapper.deleteAll();

		// 2. 단건등록
		int flag = mapper.doSave(dto01);
		assertEquals(1, flag);

		// 2.1 등록건수 비교
		int count = mapper.getCount();
		assertEquals(count, 1);

		// 3. 단건조회
		CommentDTO outVO = mapper.doSelectOne(dto01);
		assertNotNull(outVO);
		isSameUser(outVO, dto01);

		// 4. 단건조회 데이터 수정
		String upString = "_U";
		
		outVO.setComNo(outVO.getComNo());
		outVO.setUserId(outVO.getUserId());
		outVO.setContents(outVO.getContents() + upString);
		outVO.setTargetNo(outVO.getTargetNo());
		outVO.setTableName(outVO.getTableName());

		log.debug("outVO:" + outVO);

		// 5. 수정
		flag = mapper.doUpdate(outVO);
		assertEquals(1, flag);
		
		// 6. 수정 조회
		CommentDTO upVO = mapper.doSelectOne(outVO);
		log.debug("upVO:" + upVO);
		
		// 7. 4번 6번 비교
		isSameUser(outVO, upVO);
		System.out.println("***");

	}
	
	//@Disabled
	@Test
	void doRetrieve() throws SQLException {
		// 1. 전체삭제
		mapper.deleteAll();
	
		// 2. 다건등록
		int count = mapper.saveAll();
		log.debug("count:" + count);
		assertEquals(502, count);
	
		//paging
		search.setPageSize(10);
		search.setPageNo(1);
		
		search.setSearchDiv("10");
		search.setSearchWord("pcwk");
		
		// 3. paging조회
		List<CommentDTO> list = mapper.doRetrieve(search);
		
		for (CommentDTO vo : list) {
			log.debug(vo);
		}
	
		assertEquals(list.size(), 10);
	}
	
	//@Disabled
	@Test
	public void getAll() throws SQLException {
		// 1. 전체삭제
		mapper.deleteAll();
	
		// 2. 단건등록
		int flag = mapper.doSave(dto01);
		assertEquals(1, flag);
	
		int count = mapper.getCount();
		assertEquals(count, 1);
	
		mapper.doSave(dto02);
		count = mapper.getCount();
		assertEquals(count, 2);
	
		mapper.doSave(dto03);
		count = mapper.getCount();
		assertEquals(count, 3);
	
		// 5 전체 조회
		List<CommentDTO> commentList = mapper.getAll();
	
		assertEquals(commentList.size(), 3);
	
		for (CommentDTO dto : commentList) {
			log.debug(dto);
		}

	}
	
	
	// 데이터 비교
	public void isSameUser(CommentDTO outVO, CommentDTO dto01) {
		assertEquals(outVO.getComNo(), dto01.getComNo());
		assertEquals(outVO.getUserId(), dto01.getUserId());
		assertEquals(outVO.getContents(), dto01.getContents());
		assertEquals(outVO.getTargetNo(), dto01.getTargetNo());
		assertEquals(outVO.getTableName(), dto01.getTableName());
	}
}
