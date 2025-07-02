package com.pcwk.ehr.tour.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.pcwk.ehr.cmn.SearchDTO;
import com.pcwk.ehr.tour.domain.TourDTO;
import com.pcwk.ehr.user.domain.UserDTO;

public interface TourService {
	List<TourDTO> doRetrieve(SearchDTO param);

	int doDelete(TourDTO param);

	int doUpdate(TourDTO param) throws SQLException;

	TourDTO doSelectOne(TourDTO param) throws SQLException;

	int doSave(TourDTO param) throws SQLException;
	
	public List<TourDTO> doRetrieve(Map<String, Object> paramMap);
	

}
