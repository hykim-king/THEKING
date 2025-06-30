package com.pcwk.ehr.festival.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pcwk.ehr.cmn.SearchDTO;
import com.pcwk.ehr.festival.domain.FestivalDTO;
import com.pcwk.ehr.mapper.FestivalMapper;
@Service
public class FestivalServiceImpl implements FestivalService {

	Logger log = LogManager.getLogger(getClass());
	
	@Autowired
	FestivalMapper mapper;
	
	@Override
	public int doSave(FestivalDTO param) {
		return mapper.doSave(param);
	}

	@Override
	public int doUpdate(FestivalDTO param) {
		return mapper.doUpdate(param);
	}

	@Override
	public int doDelete(FestivalDTO param) {
		return mapper.doDelete(param);
	}

	@Override
	public FestivalDTO doSelectOne(FestivalDTO param) {
		return mapper.doSelectOne(param);
	}

	@Override
	public List<FestivalDTO> checkRetrieve(String sido, String date, SearchDTO param) {
		if(sido.equals("")) {
			sido = null;
		}
		if(date.equals("")) {
			date = null;
		}
		if(param == null) {
			param.setPageNo(1);
			param.setPageSize(12);
		}
		return mapper.checkRetrieve(sido, date, param);
	}

	@Override
	public List<FestivalDTO> doRetrieve(SearchDTO param) {
		return mapper.doRetrieve(param);
	}

	@Override
	public int getCount() throws SQLException {
		return mapper.getCount();
	}

	@Override
	public int upViews(FestivalDTO param) {
		return mapper.upViews(param);
	}

}
