package com.pcwk.ehr.festival.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Param;
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
	public int doDelete(int festaNo) {
		FestivalDTO dto = new FestivalDTO();
		dto.setFestaNo(festaNo);
		return mapper.doDelete(dto);
	}

	@Override
	public FestivalDTO doSelectOne(int festaNo) {
		FestivalDTO dto = new FestivalDTO();
		dto.setFestaNo(festaNo);
		return mapper.doSelectOne(dto);
	}

	@Override
	public List<FestivalDTO> checkRetrieve(@Param("sido")String sido,
										@Param("date")String date, SearchDTO param) {


		if(sido.equals("")) {
			sido = null;
		}
		if(date.equals("")) {
			date = null;
		}
		
		return mapper.checkRetrieve(sido, date, param);
	}

	@Override
	public List<FestivalDTO> doRetrieve(SearchDTO param) {
		if(param.getPageNo()==0) {
			param.setPageNo(1);
		}
		if(param.getPageSize()==0) {
			param.setPageSize(12);
		}
		return mapper.doRetrieve(param);
	}

	@Override
	public int getCount() throws SQLException {
		return mapper.getCount();
	}

	@Override
	public int upViews(int festaNo) {
		FestivalDTO dto = new FestivalDTO();
		dto.setFestaNo(festaNo);
		return mapper.upViews(dto);
	}

}
