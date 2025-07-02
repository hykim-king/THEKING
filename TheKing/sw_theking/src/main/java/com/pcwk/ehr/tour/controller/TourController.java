package com.pcwk.ehr.tour.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pcwk.ehr.tour.service.TourService;

@Controller
@RequestMapping("/tour")
public class TourController {
	Logger log = LogManager.getLogger(getClass());
	
	@Autowired
	TourService tourService;

//	public TourController() {}
//	
//	@PostMapping(value = "/doSave.do",produces="text/plain;charset=UTF-8")
//	@ResponseBody
//	public String doSave()
	
}
