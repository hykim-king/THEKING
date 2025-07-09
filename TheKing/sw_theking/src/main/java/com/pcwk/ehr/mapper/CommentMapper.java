package com.pcwk.ehr.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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

	List<CommentDTO> getAllComments(String userId) throws SQLException;
	
	List<CommentDTO> getTourComments(String userId);
	
	List<CommentDTO> getFestivalComments(String userId);
	
	List<CommentDTO> getBoardComments(String userId);
	
	List<CommentDTO> getCommentsByTarget(Map<String, Object> param);

}
