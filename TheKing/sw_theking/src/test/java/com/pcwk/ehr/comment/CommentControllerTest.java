package com.pcwk.ehr.comment;

import static org.junit.Assert.assertNotNull;
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
import org.springframework.mock.web.MockHttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import com.pcwk.ehr.comment.domain.CommentDTO;
import com.pcwk.ehr.comment.service.CommentService;
import com.pcwk.ehr.mapper.CommentMapper;
import com.pcwk.ehr.user.domain.UserDTO;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
class CommentControllerTest {
	Logger log = LogManager.getLogger(getClass());
	
	@Autowired
	WebApplicationContext webApplicationContext;
	
	MockMvc mockMvc;
	
	CommentDTO dto01;
	
	MockHttpSession session;
	
	@Autowired
	CommentMapper mapper;
	
	@BeforeEach
	void setUp() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ setUp()                                                 │");
		log.debug("└─────────────────────────────────────────────────────────┘");
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		dto01 = new CommentDTO(0, "pcwk01", "내용1", 0, "tour", "사용안함", "사용안함");

	}

	@AfterEach
	void tearDown() throws Exception {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ tearDown()                                              │");
		log.debug("└─────────────────────────────────────────────────────────┘");
		
	}

	@Test
	public void getAllCommentsTest() throws SQLException {
		mapper.deleteAll();
		mapper.doSave(dto01);
	    List<CommentDTO> list = mapper.getAllComments();
	    assertNotNull(list);
	    for (CommentDTO dto : list) {
	        log.debug("comment: {}", dto);
	    }
	}
	
	@Disabled
	@Test
	void beans() {
		assertNotNull(webApplicationContext);
		assertNotNull(mockMvc);
		assertNotNull(dto01);
		log.debug("webApplicationContext:{}", webApplicationContext);
		log.debug("mockMvc:{}", mockMvc);
		log.debug("dto01:{}", dto01);
	}
}
