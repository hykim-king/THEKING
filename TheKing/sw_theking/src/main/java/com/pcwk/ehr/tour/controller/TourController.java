package com.pcwk.ehr.tour.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.pcwk.ehr.cmn.MessageDTO;
import com.pcwk.ehr.cmn.PcwkString;
import com.pcwk.ehr.cmn.SearchDTO;
import com.pcwk.ehr.region.domain.RegionDTO;
import com.pcwk.ehr.tour.domain.TourDTO;
import com.pcwk.ehr.tour.service.TourService;
import com.pcwk.ehr.user.domain.UserDTO;

@Controller
@RequestMapping("/tour")
public class TourController {
	Logger log = LogManager.getLogger(getClass());

	@Autowired
	TourService tourService;

	public TourController() {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ TourContrlloer()                                        │");
		log.debug("└─────────────────────────────────────────────────────────┘");
	}
	//동기, GET
	@GetMapping(value="doRetrieve.do", produces = "text/plain;charset=UTF-8")
	public String doRetrieve(SearchDTO search,RegionDTO region,Model model) {
		String viewName = "tour/tour_list";
		log.debug("┌──────────────────────────────┐");
		log.debug("│ *doRetrieve()*               │");
		log.debug("└──────────────────────────────┘");
		
		
		int pageNo = PcwkString.nvlZero(search.getPageNo(),1);
		int PageSize = PcwkString.nvlZero(search.getPageSize(),10);
		//검색 구분
		String searchDiv = PcwkString.nullToEmpty(search.getSearchDiv());
		//검색어
		String searchWord = PcwkString.nullToEmpty(search.getSearchWord());
		
		search.setPageNo(pageNo);
		search.setPageSize(PageSize);
		search.setSearchDiv(searchDiv);
		search.setSearchWord(searchWord);
		
    	region.setRegionSido("서울특별시");
    	region.setRegionGugun("서대문구");
    	
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("search", search);
        paramMap.put("region", region);
		
		log.debug("search: {}",search);
		
		List<TourDTO> list = tourService.doRetrieve(paramMap);
		
		model.addAttribute("list", list);
		
		return viewName;
	}
	
	@PostMapping(value="/doDelete.do", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String doDelete(@RequestParam(name = "tourNo")int tourNo) {
		String jsonString ="";
		log.debug("┌──────────────────────────────┐");
		log.debug("│ *doDelete()*                 │");
		log.debug("└──────────────────────────────┘");
		log.debug("tourNo:{}",tourNo);
		
		TourDTO inVO = new TourDTO();
		inVO.setTourNo(tourNo);
		log.debug("inVO:{}",inVO);
		
		int flag = tourService.doDelete(inVO);
		String massage = "";
		if(1==flag) {
			massage = "관광지가 삭제 되었습니다.";
		}else {
			massage = "관광지가 삭제 실패했습니다.";
		}
		MessageDTO messageDTO = new MessageDTO(flag, massage);
		
		jsonString = new Gson().toJson(messageDTO);
		log.debug("jsonString:{}",jsonString);
		
		return jsonString;
	}

	@GetMapping(value = "/doSelectOne.do", produces = "text/plain;charset=UTF-8")
	public String doSelectOne(Model model, @RequestParam("tourNo") int tourNo) throws SQLException {
		// 동기 통신, GET
		// 단 건 조회
		String viewName = "tour/tour_mng";
		log.debug("┌──────────────────────────────┐");
		log.debug("│ *doSelectOne()*              │");
		log.debug("└──────────────────────────────┘");
		log.debug("tourNo:{}", tourNo);

		
		TourDTO inVO = new TourDTO();
		inVO.setTourNo(tourNo);
		
		TourDTO outVO = tourService.doSelectOne(inVO);
		
		model.addAttribute("TourDTO",outVO);
		

		return viewName;
	}

	@PostMapping(value = "/doUpdate.do", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String doUpdate(TourDTO param) throws SQLException {
		log.debug("┌──────────────────────────────┐");
		log.debug("│ *doUpdate()*                 │");
		log.debug("└──────────────────────────────┘");
		String jsonString = "";

		// 서비스 호출
		int flag = tourService.doUpdate(param);
		String massage = "";
		if (1 == flag) {
			massage = param.getName() + " 관광지가 업데이트 되었습니다.";
		} else {
			massage = param.getName() + " 관광지가 업데이트 실패 되었습니다.";
		}
		MessageDTO messageDTO = new MessageDTO(flag, massage);
		jsonString = new Gson().toJson(messageDTO);
		log.debug("2 jsonString:{}", jsonString);
		return jsonString;
	}

	@PostMapping(value = "/doSave.do", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String doSave(TourDTO param) throws SQLException {
		log.debug("┌──────────────────────────────┐");
		log.debug("│ *doSave()*                   │");
		log.debug("└──────────────────────────────┘");
		String jsonString = "";

		log.debug("1 param: {}", param);

		// 서비스 호출
		int flag = tourService.doSave(param);
		String massage = "";
		if (1 == flag) {
			massage = param.getName() + " 관광지가 등록 되었습니다.";
		} else {
			massage = param.getName() + " 관광지가 등록 실패 되었습니다.";
		}
		MessageDTO messageDTO = new MessageDTO(flag, massage);
		jsonString = new Gson().toJson(messageDTO);
		log.debug("2 jsonString:{}", jsonString);

		return jsonString;
	}
}
