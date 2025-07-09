package com.pcwk.ehr.comment.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Service;

import com.pcwk.ehr.cmn.SearchDTO;
import com.pcwk.ehr.comment.domain.CommentDTO;
import com.pcwk.ehr.mapper.CommentMapper;
import com.pcwk.ehr.mapper.UserMapper;
import com.pcwk.ehr.user.domain.UserDTO;
import com.pcwk.ehr.user.service.UserService;
import com.pcwk.ehr.user.service.UserValidation;

@Service
public class CommentServiceImpl implements CommentService{
	Logger log = LogManager.getLogger(getClass());

	@Qualifier("dummyMailService") //bean id로 특정 빈 주입
	@Autowired
	private MailSender mailSender;

	@Autowired
	private CommentMapper mapper;
	
	@Autowired
	private UserMapper userMapper;
	
	
	public CommentServiceImpl() {
	}
	
	
	@Override
	public List<CommentDTO> doRetrieve(SearchDTO param) {
		return mapper.doRetrieve(param);	
	}
	
	@Override
	public int doDelete(CommentDTO param) {
		return mapper.doDelete(param);
	}
	
	@Override
	public int doUpdate(CommentDTO param) {
		return mapper.doUpdate(param);
	}
	
	@Override
	public CommentDTO doSelectOne(CommentDTO param) throws SQLException {
		return mapper.doSelectOne(param);
	}
	
	@Override
	public int doSave(CommentDTO param) throws SQLException {
	    return mapper.doSave(param);
	}

	@Override
	public List<CommentDTO> getTourComments(String userId) throws SQLException {
		return mapper.getTourComments(userId);
	}

	@Override
	public List<CommentDTO> getFestivalComments(String userId) throws SQLException {
		return mapper.getFestivalComments(userId);
	}

	@Override
	public List<CommentDTO> getBoardComments(String userId) throws SQLException {
	    return mapper.getBoardComments(userId);
	}
	
	@Override
	public List<CommentDTO> getAllComments() throws SQLException {
	    return mapper.getAllComments();
	}

	@Override
	public List<CommentDTO> getCommentsByTarget(int targetNo, String tableName) throws SQLException{
		Map<String, Object> param = new HashMap<>();
	    param.put("targetNo", targetNo);
	    param.put("tableName", tableName);
	    
	    List<CommentDTO> commentList = mapper.getCommentsByTarget(param);
	    //유저 이름과 유저 닉네임 구하기.
	    for (CommentDTO comment : commentList) {
	        String userId = comment.getUserId();
	        if (userId != null && !userId.isEmpty()) {
	            UserDTO inDTO = new UserDTO();
	            inDTO.setUserId(userId);

	            UserDTO userDTO = userMapper.doLogin(inDTO);
	            if (userDTO != null) {
	                comment.setUserName(userDTO.getName());
	                comment.setUserNickname(userDTO.getNickname());
	            }
	        }
	    }


	    return commentList;
	}


	@Override
	public List<CommentDTO> doRetrieve(CommentDTO param) {
		// TODO Auto-generated method stub
		return null;
	}
}
