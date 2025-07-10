package com.pcwk.ehr.festival.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
import com.pcwk.ehr.cmn.SearchDTO;
import com.pcwk.ehr.comment.domain.CommentDTO;
import com.pcwk.ehr.comment.service.CommentService;
import com.pcwk.ehr.favorites.domain.FavoritesDTO;
import com.pcwk.ehr.festival.domain.FestivalDTO;
import com.pcwk.ehr.festival.service.FestivalService;
import com.pcwk.ehr.image.domain.ImageDTO;
import com.pcwk.ehr.mapper.FavoritesMapper;
import com.pcwk.ehr.mapper.ImageMapper;
import com.pcwk.ehr.user.domain.UserDTO;

@Controller
@RequestMapping("/festival")
public class FestivalController {
	Logger log = LogManager.getLogger(getClass());

	@Autowired
	FestivalService service;

	@Autowired
	ImageMapper imageMapper;

	@Autowired
	FavoritesMapper favoritesMapper;
	
	@Autowired
	CommentService commentService;

	public FestivalController() {
		log.debug("┌─────────────────────────────────┐");
		log.debug("│ FestivalController()            │");
		log.debug("└─────────────────────────────────┘");
	}

	@GetMapping("/main.do")
	public String main(HttpServletRequest request, Model model) {
		SearchDTO dto = new SearchDTO();
		List<FestivalDTO> list;
		String sido = null;
		String date = null;

		if (request.getParameter("pageNo") == null || request.getParameter("pageNo").isEmpty()) {
			dto.setPageNo(1);
		} else {
			dto.setPageNo(Integer.parseInt(request.getParameter("pageNo")));
		}
		if (request.getParameter("pageSize") == null || request.getParameter("pageSize").isEmpty()) {
			dto.setPageSize(10);
		} else {
			dto.setPageSize(Integer.parseInt(request.getParameter("pageSize")));
		}

		if (request.getParameter("sido") != null && !request.getParameter("sido").isEmpty()) {
			sido = request.getParameter("sido");
		}

		if (request.getParameter("date") != null && !request.getParameter("date").isEmpty()) {
			date = request.getParameter("date");
		}

		list = service.checkRetrieve(sido, date, dto);

		// 총글수
		int totalCnt = 0;

		if (null != list && list.size() > 0) {
			FestivalDTO totalVO = list.get(0);
			totalCnt = totalVO.getTotalCnt();
		}
		log.debug("totalCnt:{}", totalCnt);

		model.addAttribute("search", dto);
		model.addAttribute("list", list);
		model.addAttribute("totalCnt", totalCnt);

		return "/festival/main";
	}

	@GetMapping("/doSave.do")
	public String doSave() {
		return "/festival/festival_reg";
	}

	@GetMapping("/doUpdate.do")
	public String doUpdate(int festaNo, Model model) {
		FestivalDTO dto = service.doSelectOne(festaNo);
		model.addAttribute("dto", dto);
		return "/festival/festival_mod";
	}

	@GetMapping("/doSelectOne.do")
	public String doSelectOne(int festaNo, Model model, HttpSession session) throws SQLException {
		service.upViews(festaNo);
		FestivalDTO dto = service.doSelectOne(festaNo);
		List<ImageDTO> imageList = imageMapper.getImages(festaNo, "festival");
		log.debug("imageList:{}", imageList);

		boolean flag = false;

		UserDTO user = (UserDTO) session.getAttribute("loginUser");
		if (user != null) {

			FavoritesDTO vo = new FavoritesDTO();
			vo.setTableName("festival");
			vo.setTargetNo(festaNo);
			vo.setUserId(user.getUserId());

			FavoritesDTO isFav = favoritesMapper.doSelectOne(vo);

			if (isFav != null) {
				flag = true;
			} else {
				flag = false;
			}
		}else {
			flag = false;
		}
		
		// 댓글 목록 조회
	    List<CommentDTO> commentList = commentService.getCommentsByTarget(festaNo, "festival");
	    log.debug("commentList:{}",commentList);
	    
	    model.addAttribute("list", commentList);

		model.addAttribute("imageList", imageList);
		model.addAttribute("dto", dto);
		model.addAttribute("favoritesCount", favoritesMapper.getFestaFavoriteCount(festaNo));
		model.addAttribute("isFavorite", flag);

		return "/festival/festival_mng";
	}

	@PostMapping(value = "/doDelete.do", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String doDelete(int festaNo) {
		log.debug("┌───────────────────────────┐");
		log.debug("│ *doDelete()*              │");
		log.debug("└───────────────────────────┘");

		String jsonString = "";
		log.debug("festaNo :{}", festaNo);

		int flag = service.doDelete(festaNo);
		String message = "";

		if (flag == 1) {
			message = "삭제되었습니다.";
		} else {
			message = "삭제에 실패하였습니다.";
		}
		MessageDTO messageDTO = new MessageDTO(flag, message);
		jsonString = new Gson().toJson(messageDTO);
		log.debug("jsonString:{}", jsonString);
		return jsonString;

	}

	// 이미지 등록 해야함
	@PostMapping(value = "/doSave.do", produces = "text/plain;charset=UTF-8")
	public String doSave(@RequestParam(value = "imageFile", required = false) MultipartFile file,
			@ModelAttribute FestivalDTO param) throws IOException {
		log.debug("┌───────────────────────────┐");
		log.debug("│ *doSave()*                │");
		log.debug("└───────────────────────────┘");

		log.debug("param:{}", param);
		log.debug("file:{}", file);
		int flag = service.doSave(param);

		if (flag == 1) {
			// 이미지 저장
			if (file != null && !file.isEmpty()) {

				log.debug("이미지 전송");
				String uploadDir = "C:/Users/user/THEKING/TheKing/sw_theking/src/main/webapp/resources/images/festival";
				String savedFilename = FileUtil.saveFileWithUUID(file, uploadDir);

				ImageDTO imageDTO = new ImageDTO();
				imageDTO.setImageName(file.getOriginalFilename());
				imageDTO.setImageUrl("src/main/webapp/resources/images/festival/");
				imageDTO.setSaveName(savedFilename);
				imageDTO.setTableName("festival");
				imageDTO.setTargetNo(param.getFestaNo());

				imageMapper.doSave(imageDTO);
			}

		} else {
			return null;
		}

		return "redirect:main.do";
	}

	@PostMapping(value = "/doUpdate.do", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String doUpdate(FestivalDTO param) {
		log.debug("┌───────────────────────────┐");
		log.debug("│ *doUpdate()*              │");
		log.debug("└───────────────────────────┘");

		String jsonString = "";
		log.debug("param :{}", param);

		int flag = service.doUpdate(param);
		String message = "";

		if (flag == 1) {
			message = param.getName() + "이 수정되었습니다.";
		} else {
			message = param.getName() + " 수정을 실패하였습니다.";
		}
		MessageDTO messageDTO = new MessageDTO(flag, message);

		jsonString = new Gson().toJson(messageDTO);
		log.debug("jsonString:{}", jsonString);

		return jsonString;
	}

	@PostMapping(value = "/test.do", produces = "text/plain;charset=UTF-8")
	public String imageTest(@RequestParam(value = "image", required = false) MultipartFile file,
			HttpServletRequest request, int festaNo) {
		log.debug("확인");
		log.debug("file:{}", file);
		log.debug("festaNo :{}", festaNo);
		if (file != null && !file.isEmpty()) {

			log.debug("이미지 전송");
			String uploadDir = "C:/Users/user/THEKING/TheKing/sw_theking/src/main/webapp/resources/images/festival";
			String savedFilename = FileUtil.saveFileWithUUID(file, uploadDir);
			log.debug("uploadDir:{}", uploadDir);
			ImageDTO imageDTO = new ImageDTO();
			imageDTO.setImageName(file.getOriginalFilename());
			imageDTO.setImageUrl("src/main/webapp/resources/images/festival/");
			imageDTO.setSaveName(savedFilename);
			imageDTO.setTableName("festival");
			imageDTO.setTargetNo(festaNo);
			log.debug("imageDTO:{}", imageDTO);
			imageMapper.doSave(imageDTO);
		}

		return "redirect:main.do";
	}

	@GetMapping("test.do")
	public String imageTest(int festaNo, Model model) {
		FestivalDTO dto = service.doSelectOne(festaNo);
		model.addAttribute("dto", dto);

		return "/festival/festival_image_save";
	}

}
