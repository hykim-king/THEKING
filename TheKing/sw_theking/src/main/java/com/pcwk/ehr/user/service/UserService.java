package com.pcwk.ehr.user.service;

import java.sql.SQLException;
import java.util.List;

import com.pcwk.ehr.cmn.SearchDTO;
import com.pcwk.ehr.comment.domain.CommentDTO;
import com.pcwk.ehr.festival.domain.FestivalDTO;
import com.pcwk.ehr.tour.domain.TourDTO;
import com.pcwk.ehr.user.domain.UserDTO;

public interface UserService {

	List<UserDTO> doRetrieve(SearchDTO param);

	int doDelete(UserDTO param);

	int doUpdate(UserDTO param);

	UserDTO doSelectOne(UserDTO param) throws SQLException;

	int doSave(UserDTO param) throws SQLException;
	
	UserDTO doLogin(UserDTO loginUserId)throws SQLException;
	
}