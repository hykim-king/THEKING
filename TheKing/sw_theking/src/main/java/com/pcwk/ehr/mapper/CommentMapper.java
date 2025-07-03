package com.pcwk.ehr.mapper;

import java.sql.SQLException;
import java.util.List;

import javax.xml.stream.events.Comment;

import org.apache.ibatis.annotations.Mapper;

import com.pcwk.ehr.cmn.WorkDiv;
import com.pcwk.ehr.comment.domain.CommentDTO;
import com.pcwk.ehr.user.domain.UserDTO;

@Mapper
public interface CommentMapper extends WorkDiv<CommentDTO>{

	int saveAll();
	
	List<CommentDTO> getAll();
	
	void deleteAll() throws SQLException;
	
	int getCount() throws SQLException;
	
	CommentDTO getCommentsTours(String param) throws SQLException;
	
	CommentDTO getCommentsFestival(String param) throws SQLException;
	
	CommentDTO getCommentsBoard(String param) throws SQLException;
	
	List<CommentDTO> getAllComments() throws SQLException;
}
