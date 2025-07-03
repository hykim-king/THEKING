package com.pcwk.ehr.tour;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;
import com.pcwk.ehr.cmn.MessageDTO;
import com.pcwk.ehr.cmn.SearchDTO;
import com.pcwk.ehr.mapper.TourMapper;
import com.pcwk.ehr.tour.domain.TourDTO;
import com.pcwk.ehr.tour.service.TourService;
import com.pcwk.ehr.user.domain.UserDTO;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"		
		,"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})	
class TourControllerTest {
	Logger log = LogManager.getLogger(getClass());
	
	@Autowired
	WebApplicationContext webAppConfiguration;
	
	//웹 브라우저 대역 객체
	MockMvc mockMvc;
	
	TourDTO tourDTO01;
	SearchDTO search;
	
	@Autowired
	TourMapper mapper;
	
	@Autowired
	TourService tourService;

	@BeforeEach
	void setUp() throws Exception {
		log.debug("┌───────────────────────────┐");
		log.debug("│ *setUp()*                 │");
		log.debug("└───────────────────────────┘");
		mockMvc = MockMvcBuilders.webAppContextSetup(webAppConfiguration).build();//.build()최종적으로 MockMvc 인스턴스 생성
		tourDTO01 = new TourDTO(0, "관광지1", "소제목1", "상세내용1", 0,
                "서울특별시 서대문구 123", "토요일", "09:00-16:00", "010-1111-2222", 100000, 0, null);
	}

	@AfterEach
	void tearDown() throws Exception {
		log.debug("┌───────────────────────────┐");
		log.debug("│ *tearDown()*              │");
		log.debug("└───────────────────────────┘");
	}
	@Test
	void doRetrieve() throws Exception {
		log.debug("┌───────────────────────────┐");
		log.debug("│ *doRetrieve()*            │");
		log.debug("└───────────────────────────┘");
		
		//1. 전체 삭제
		mapper.deleteAll();
		//2. 등록
		mapper.saveAll();
		assertEquals(509,mapper.getCount());
		//3.
		MockHttpServletRequestBuilder requestBuilder
		= MockMvcRequestBuilders.get("/tour/doRetrieve.do")
		.param("pageNo","0")
		.param("PageSize","0")
		.param("searchDiv","")
		.param("searchWord", "")
		.param("regionSido", "서울특별시")
		.param("regionGugun", "서대문구");
		
		//4.
		ResultActions resultActions = mockMvc.perform(requestBuilder)
		.andExpect(status().isOk());
		//4.1 Model 데이터 조회
		MvcResult mvcResult = resultActions.andDo(print()).andReturn();
		//4.2
		Map<String,Object> model = mvcResult.getModelAndView().getModel();
		List<TourDTO>list= (List<TourDTO>) model.get("list");
		log.debug("list: {}",list);
		
		for(TourDTO dto: list) {
			log.debug(dto);
		}
		//4.3 화면
		String viewName = mvcResult.getModelAndView().getViewName();
		log.debug("viewName: {}",viewName);
		
		assertEquals("tour/tour_list", viewName);	
	}
	
	//@Disabled
	@Test
	void doDelete() throws Exception {
		log.debug("┌───────────────────────────┐");
		log.debug("│ *doDeledte()*             │");
		log.debug("└───────────────────────────┘");
		
		//1.전체 삭제
		//2.등록
		//3.삭제
		//4.상태값 비교
		
		//1.
		mapper.deleteAll();
		//2.
		tourService.doSave(tourDTO01);
		assertEquals(1,mapper.getCount());
		
		//3.1 url호출, method : post,param
		MockHttpServletRequestBuilder requestBuilder
			= MockMvcRequestBuilders.post("/tour/doDelete.do")
			.param("tourNo", String.valueOf(tourDTO01.getTourNo()));
		//3.2 호출
		ResultActions resultActions = mockMvc.perform(requestBuilder)
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"));
		//3.3
		String returnBody = resultActions.andDo(print())
				.andReturn().getResponse().getContentAsString();
		
		log.debug("returnBody: {}"+returnBody);
		//3.4  json to MessageDTO 변환
		MessageDTO resultMessage = new Gson().fromJson(returnBody, MessageDTO.class);
		log.debug("resultMessage: {}"+resultMessage.toString());
		
		//관광지가 삭제 되었습니다.
		assertEquals(1,resultMessage.getMessageId());
		assertEquals("관광지가 삭제 되었습니다.",resultMessage.getMessage());
	}
	
	//@Disabled
	@Test
	void doSelectOne() throws Exception {
		log.debug("┌───────────────────────────┐");
		log.debug("│ *doSelectOne()*           │");
		log.debug("└───────────────────────────┘");
		
		//1.전체 삭제
		//2.등록
		//3.단건 조회
		mapper.deleteAll();
		//2. 
		tourService.doSave(tourDTO01);
		assertEquals(1,mapper.getCount());
		//3.
		MockHttpServletRequestBuilder requestBuilder
		= MockMvcRequestBuilders.get("/tour/doSelectOne.do")
		.param("tourNo", String.valueOf(tourDTO01.getTourNo()));
		//4.
		ResultActions resultActions = mockMvc.perform(requestBuilder)
		.andExpect(status().isOk());
		//4.1 Model 데이터 조회
		MvcResult mvcResult = resultActions.andDo(print()).andReturn();
		//4.2
		Map<String,Object> model = mvcResult.getModelAndView().getModel();
		TourDTO outVO = (TourDTO) model.get("tourDTO");
		log.debug("outVO: {}",outVO);
		
		//isSameUser(outVO, tourDTO01);
		
		//4.3 화면
		String viewName = mvcResult.getModelAndView().getViewName();
		log.debug("viewName: {}",viewName);
		
		assertEquals("tour/tour_mng", viewName);
				
	} 
	
	//@Disabled
	@Test 
	void doUpdate() throws Exception{
		//1. 전체 삭제
		mapper.deleteAll();
		//2.저징
		tourService.doSave(tourDTO01);
		assertEquals(1,mapper.getCount());
		//3.조회
		TourDTO outVO = mapper.doSelectOne(tourDTO01);
		
		String upString ="_U";
		int upInt = 999;
		MockHttpServletRequestBuilder requestBuilder
		= MockMvcRequestBuilders.post("/tour/doUpdate.do")
		.param("tourNo",String.valueOf(outVO.getTourNo()))
		.param("name", outVO.getName()+upString)
		.param("subtitle", outVO.getSubtitle()+upString)
		.param("contents", outVO.getContents()+upString)
		.param("address", outVO.getAddress())
		.param("holiday", outVO.getHoliday()+upString)
		.param("time", outVO.getTime()+upString)//정규식 제한
		.param("tel", outVO.getTel())
		.param("fee", String.valueOf(tourDTO01.getFee()));
		//3.2 호출
		ResultActions resultActions = mockMvc.perform(requestBuilder)
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"));
		//3.3
		String returnBody = resultActions.andDo(print())
				.andReturn().getResponse().getContentAsString();	
		
		log.debug("returnBody: {}"+returnBody);
		
		MessageDTO resultMessage = new Gson().fromJson(returnBody,MessageDTO.class);
		log.debug("resultMessage: {}"+resultMessage.toString());
	}
	
	//@Disabled
	@Test
	void doSave() throws Exception {
		//1. 전체 삭제
		mapper.deleteAll();
		
		//2. 등록 url 호출, 호출 및 호출 결과 받기
		MockHttpServletRequestBuilder requestBuilder
		= MockMvcRequestBuilders.post("/tour/doSave.do")
		.param("name", tourDTO01.getName())
		.param("subtitle", tourDTO01.getSubtitle())
		.param("contents", tourDTO01.getContents())
		.param("address", tourDTO01.getAddress())
		.param("holiday", tourDTO01.getHoliday())
		.param("time", tourDTO01.getTime())
		.param("tel", tourDTO01.getTel())
		.param("fee", String.valueOf(tourDTO01.getFee())); //숫자->문자
		
		ResultActions resultActions=mockMvc.perform(requestBuilder)
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"));
		//2.3 호출 결과 받기
		String returnBody = resultActions.andDo(print())
			.andReturn().getResponse().getContentAsString();
		log.debug("returnBody: {}"+returnBody);
		
		MessageDTO resultMessage = new Gson().fromJson(returnBody,MessageDTO.class);
		log.debug("resultMessage: {}"+resultMessage.toString());
		
		assertEquals(1,resultMessage.getMessageId());
		assertEquals("관광지1 관광지가 등록 되었습니다.",resultMessage.getMessage());
	}
	
	//@Disabled
	@Test
	void beans() {
		assertNotNull(webAppConfiguration);
		assertNotNull(mockMvc);
		assertNotNull(mapper);
		assertNotNull(tourDTO01);
		assertNotNull(tourService);
		
		log.debug("webAppConfiguration: "+webAppConfiguration);
		log.debug("mockMvc: "+mockMvc);
		log.debug("mapper: "+mapper);
		log.debug("tourDTO01: "+tourDTO01);
		log.debug("service: "+tourService);
	}

}
