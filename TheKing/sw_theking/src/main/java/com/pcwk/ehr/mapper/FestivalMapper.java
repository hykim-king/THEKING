package com.pcwk.ehr.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.cmn.SearchDTO;
import com.pcwk.ehr.cmn.WorkDiv;
import com.pcwk.ehr.festival.domain.FestivalDTO;
import com.pcwk.ehr.user.domain.UserDTO;

@Mapper
public interface FestivalMapper extends WorkDiv<FestivalDTO>{
	//총 축제 건수 
	int getCount() throws SQLException;
	//조회수 증가
	int upViews(FestivalDTO dto);
	
	List<FestivalDTO> checkRetrieve(@Param("sido") String sido,
            						@Param("date") String date,
            						@Param("dto")SearchDTO dto);
}
