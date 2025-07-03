package com.pcwk.ehr.festival.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.pcwk.ehr.cmn.FileUtil;
import com.pcwk.ehr.cmn.MessageDTO;
import com.pcwk.ehr.cmn.SearchDTO;
import com.pcwk.ehr.festival.domain.FestivalDTO;
import com.pcwk.ehr.festival.service.FestivalService;
import com.pcwk.ehr.image.domain.ImageDTO;
import com.pcwk.ehr.mapper.ImageMapper;

@Controller
@RequestMapping("/festival")
public class FestivalController {
	Logger log = LogManager.getLogger(getClass());

	@Autowired
	FestivalService service;
	
	@Autowired
	ImageMapper imageMapper;

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
		
		if (request.getParameter("pageNo") == null && request.getParameter("pageSize") == null) {
			dto.setPageNo(1);
			dto.setPageSize(12);
		}else {
			dto.setPageNo(Integer.parseInt(request.getParameter("pageNo")));
			dto.setPageSize(Integer.parseInt(request.getParameter("pageSize")));
		}
		
		if (request.getParameter("sido") != null) {
			sido = request.getParameter("sido");
		}
		
		if (request.getParameter("date") != null) {
			date = request.getParameter("date");
		}
		
		list = service.checkRetrieve(sido, date, dto);

		model.addAttribute("list", list);
		
		return "/festival/main";
	}

	@GetMapping("doSave.do")
	public String doSave() {
		return "/festival/doSave";
	}

	@GetMapping("doUpdate.do")
	public String doUpdate(int festaNo, Model model) {
		FestivalDTO dto = service.doSelectOne(festaNo);
		model.addAttribute("dto", dto);
		return "/festival/doUpdate";
	}


	@GetMapping("doSelectOne.do")
	public String doSelectOne(int festaNo, Model model) {
		service.upViews(festaNo);
		FestivalDTO dto = service.doSelectOne(festaNo);
		model.addAttribute("dto", dto);
		return "/festival/doSelectOne";
	}

	@PostMapping(value = "doDelete.do", produces = "text/plain;charset=UTF-8")
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

	//이미지 등록 해야함
	@PostMapping(value = "doSave.do", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String doSave(@RequestParam(value = "image", required = false)MultipartFile file, FestivalDTO param) throws IOException {
		log.debug("┌───────────────────────────┐");
		log.debug("│ *doSave()*                │");
		log.debug("└───────────────────────────┘");
		
		String jsonString = "";
		log.debug("param:{}",param);
		
		int flag = service.doSave(param);
		String message = "";
		
		if (flag == 1) {
			message = param.getName()+"이 등록되었습니다.";
			//이미지 저장
//			if(file!=null&&file.isEmpty()) {
//				String uploadDir = request.getServletContext().getRealPath("/images");
//			    String savedFilename = FileUtil.saveFileWithUUID(file, uploadDir);
//			    
//			    ImageDTO imageDTO = new ImageDTO();
//			    imageDTO.setImageName(file.getOriginalFilename());
//			    imageDTO.setImageUrl("/images/");
//			    imageDTO.setSaveName(savedFilename);
//			    imageDTO.setTableName("festival");
//			    imageDTO.setTargetNo(param.getFestaNo());
//			    
//			    imageMapper.doSave(imageDTO);
//			}		    
			
		} else {
			message = param.getName()+" 등록에 실패하였습니다.";
		}
		MessageDTO messageDTO = new MessageDTO(flag,message);
		
		jsonString = new Gson().toJson(messageDTO);
		log.debug("jsonString:{}",jsonString);
		
		return jsonString;
	}
	
	@PostMapping(value = "doUpdate.do", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String doUpdate(FestivalDTO param){
		log.debug("┌───────────────────────────┐");
		log.debug("│ *doUpdate()*              │");
		log.debug("└───────────────────────────┘");
		
		String jsonString = "";
		log.debug("param :{}",param);
		
		int flag = service.doUpdate(param);
		String message = "";
		
		if (flag == 1) {
			message = param.getName()+"이 수정되었습니다.";
		} else {
			message = param.getName()+" 수정을 실패하였습니다.";
		}
		MessageDTO messageDTO = new MessageDTO(flag,message);
		
		jsonString = new Gson().toJson(messageDTO);
		log.debug("jsonString:{}",jsonString);
		
		return jsonString;
	}
	
	@PostMapping("test.do")
	public String imageTest(@RequestParam(value = "image", required = false)MultipartFile file, HttpServletRequest request) {
		log.debug("확인");
		log.debug("file:{}",file);
		if(file!=null&&!file.isEmpty()) {
			log.debug("이미지 전송");
			String uploadDir = "C:/Users/user/THEKING/TheKing/sw_theking/src/main/resources/images";
		    String savedFilename = FileUtil.saveFileWithUUID(file, uploadDir);
		    log.debug("uploadDir:{}",uploadDir);
		    ImageDTO imageDTO = new ImageDTO();
		    imageDTO.setImageName(file.getOriginalFilename());
		    imageDTO.setImageUrl("/images/");
		    imageDTO.setSaveName(savedFilename);
		    imageDTO.setTableName("festival");
		    imageDTO.setTargetNo(142);
		    log.debug("imageDTO:{}",imageDTO);
		    imageMapper.doSave(imageDTO);
		}	
		
		return null;
	}
	@GetMapping("test.do")
	public String imageTest() {
		return "/festival/test";
	}

}
