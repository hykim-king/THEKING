package com.pcwk.ehr.comment.controller;

import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pcwk.ehr.comment.domain.CommentDTO;
import com.pcwk.ehr.comment.service.CommentService;

@Controller
@RequestMapping("/comment")
public class CommentController {
	Logger log = LogManager.getLogger(getClass());
	
	@Autowired
	CommentService commentService;

	public CommentController() {
	}
	
	@GetMapping("/allComment.do")
    public String getAllComments(Model model) throws SQLException {
        List<CommentDTO> list = commentService.getAllComments();
        model.addAttribute("comments", list);
        return "comment/list"; // → comment/list.jsp 또는 .html
    }
	
}
