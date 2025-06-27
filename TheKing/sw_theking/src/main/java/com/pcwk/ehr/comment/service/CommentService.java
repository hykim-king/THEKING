package com.pcwk.ehr.comment.service;

import java.sql.SQLException;
import java.util.List;

import com.pcwk.ehr.cmn.SearchDTO;
import com.pcwk.ehr.comment.domain.CommentDTO;

public interface CommentService {

	List<CommentDTO> doRetrieve(SearchDTO param);

	int doDelete(CommentDTO param);

	int doUpdate(CommentDTO param);

	CommentDTO doSelectOne(CommentDTO param) throws SQLException;

	int doSave(CommentDTO param) throws SQLException;
	
}
