package com.pcwk.ehr.tour.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.pcwk.ehr.cmn.FileUtil;
import com.pcwk.ehr.cmn.MessageDTO;
import com.pcwk.ehr.cmn.PcwkString;
import com.pcwk.ehr.cmn.SearchDTO;
import com.pcwk.ehr.comment.domain.CommentDTO;
import com.pcwk.ehr.comment.service.CommentService;
import com.pcwk.ehr.image.domain.ImageDTO;
import com.pcwk.ehr.mapper.ImageMapper;
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
	
	@Autowired
	ImageMapper imageMapper;
	
	@Autowired
	CommentService commentService;

	public TourController() {
		log.debug("┌─────────────────────────────────────────────────────────┐");
		log.debug("│ TourContrlloer()                                        │");
		log.debug("└─────────────────────────────────────────────────────────┘");
	}
	//화면 띄우기.
	@GetMapping("/doUpdateView.do")
	public String doUpdateView(@RequestParam("tourNo") int tourNo, Model model,HttpSession session) throws SQLException {
		log.debug("┌──────────────────────────────┐");
		log.debug("│ *doUpdateView()*               │");
		log.debug("└──────────────────────────────┘");
		
		UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");
	    model.addAttribute("loginUser", loginUser);
		
		String viewStirng = "tour/tour_mod";
		log.debug("viewStirng: ",viewStirng);
		TourDTO param = new TourDTO();
	    param.setTourNo(tourNo);
		
	    TourDTO tourDTO = tourService.doSelectOne(param);
		model.addAttribute("TourDTO", tourDTO);
		return viewStirng;
	}
	
	//화면 띄우기.
		@GetMapping("/doSaveView.do")
		public String doSaveView(Model model,HttpSession session) {
			log.debug("┌──────────────────────────────┐");
			log.debug("│ *doSaveView()*               │");
			log.debug("└──────────────────────────────┘");
			
			UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");
		    model.addAttribute("loginUser", loginUser);
		    
			String viewStirng = "tour/tour_reg";
			log.debug("viewStirng: ",viewStirng);
			
			return viewStirng;
		}
	

		@GetMapping(value="doRetrieve.do", produces = "text/plain;charset=UTF-8")
		public String doRetrieve(
		    @RequestParam(value="region.regionSido", required=false) String regionSido,
		    @RequestParam(value="region.regionGugun", required=false) String regionGugun,
		    @ModelAttribute SearchDTO search,
		    Model model,HttpSession session) {

		    String viewName = "tour/tour_list";

		    log.debug("┌──────────────────────────────┐");
		    log.debug("│ *doRetrieve()*               │");
		    log.debug("└──────────────────────────────┘");
		    
		    
		    //로그인 사용자 정보를 JSP에서 활용할 수 있게만 전달
		    UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");
		    model.addAttribute("loginUser", loginUser);
		    
		    
		    int pageNo = PcwkString.nvlZero(search.getPageNo(),1);
		    int pageSize = PcwkString.nvlZero(search.getPageSize(),10);

		    String searchDiv = PcwkString.nullToEmpty(search.getSearchDiv());
		    String searchWord = PcwkString.nullToEmpty(search.getSearchWord());

		    regionSido = PcwkString.nullToEmpty(regionSido);
		    regionGugun = PcwkString.nullToEmpty(regionGugun);
		    		    
		    log.debug("pageNo: {}", pageNo);
		    log.debug("pageSize: {}", pageSize);
		    log.debug("searchDiv: {}", searchDiv);
		    log.debug("searchWord: {}", searchWord);
		    log.debug("regionSido: {}", regionSido);
		    log.debug("regionGugun: {}", regionGugun);

		    search.setPageNo(pageNo);
		    search.setPageSize(pageSize);
		    search.setSearchDiv(searchDiv);
		    search.setSearchWord(searchWord);

		    // region 객체 생성 및 값 세팅
		    RegionDTO region = new RegionDTO();
		    region.setRegionSido(regionSido);
		    region.setRegionGugun(regionGugun);

		    log.debug("***search: {}", search);
		    log.debug("***region: {}", region);

		    Map<String, Object> paramMap = new HashMap<>();
		    paramMap.put("search", search);
		    paramMap.put("region", region);

		    // 서비스 호출
		    List<TourDTO> list = tourService.doRetrieve(paramMap);
		    
		    //총 글자 수 세기.

			int totalCnt = 0;
			
			if(null != list && list.size()>0) {
				TourDTO totalVO =  list.get(0);
				totalCnt = totalVO.getTotalCnt();
			}
			log.debug("totalCnt:{}",totalCnt);

		    model.addAttribute("list", list);
		    model.addAttribute("paramMap", paramMap);
		    model.addAttribute("search", search);
		    model.addAttribute("region", region);
		    model.addAttribute("totalCnt", totalCnt);

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
//	@GetMapping("/doSelectOne.do")
//	public String doSelectOne(@RequestParam(value = "tourNo", required = false) Integer tourNo, Model model) throws SQLException {
//	    if (tourNo == null) {
//	        // 에러 페이지로 리다이렉트하거나 사용자에게 안내 메시지를 전달
//	    }
//	    // 정상 처리
//	}

	@GetMapping(value = "/doSelectOne.do", produces = "text/plain;charset=UTF-8")
	public String doSelectOne(Model model, @RequestParam("tourNo") Integer tourNo,HttpSession session) throws SQLException {
		// 동기 통신, GET
		// 단 건 조회
		String viewName = "tour/tour_mng";
		log.debug("┌──────────────────────────────┐");
		log.debug("│ *doSelectOne()*              │");
		log.debug("└──────────────────────────────┘");
		log.debug("tourNo:{}", tourNo);
		
		UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");
	    model.addAttribute("loginUser", loginUser);
		
		TourDTO inVO = new TourDTO();
		inVO.setTourNo(tourNo);
		
		TourDTO outVO = tourService.doSelectOne(inVO);
		
	    // 댓글 목록 조회
	    List<CommentDTO> commentList = commentService.getCommentsByTarget(tourNo, "TOUR");
	    
	    model.addAttribute("list", commentList);
		
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
	public String doSave(@RequestParam(value = "imageFile", required = false) MultipartFile file, @ModelAttribute TourDTO param) throws SQLException {
		log.debug("┌──────────────────────────────┐");
		log.debug("│ *doSave()*                   │");
		log.debug("└──────────────────────────────┘");

		log.debug("param: {}", param);
		log.debug("file:{}", file);

		// 서비스 호출
		int flag = tourService.doSave(param);
		if (flag == 1) {
			// 이미지 저장
			if (file != null && !file.isEmpty()) {

				log.debug("이미지 전송");
				String uploadDir = "C:/Users/user/THEKING/TheKing/sw_theking/src/main/webapp/resources/images/festival";
				String savedFilename = FileUtil.saveFileWithUUID(file, uploadDir);

				ImageDTO imageDTO = new ImageDTO();
				imageDTO.setImageName(file.getOriginalFilename());
				imageDTO.setImageUrl("src/main/webapp/resources/images/tour/");
				imageDTO.setSaveName(savedFilename);
				imageDTO.setTableName("TOUR");
				imageDTO.setTargetNo(param.getTourNo());

				imageMapper.doSave(imageDTO);
			}

		} else {
			return null;
		}

		return "redirect:doSave.do";
	}
}
