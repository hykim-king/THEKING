package com.pcwk.ehr.festival.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import com.pcwk.ehr.cmn.SearchDTO;
import com.pcwk.ehr.festival.domain.FestivalDTO;

public interface FestivalService {
	//등록
	int doSave(FestivalDTO param);
	//수정
	int doUpdate(FestivalDTO param);
	//삭제
	int doDelete(FestivalDTO param);
	//단건 조회
	FestivalDTO doSelectOne(FestivalDTO param);
	//조건별 리스트 조회
	List<FestivalDTO> checkRetrieve(@Param("sido") String sido,
									@Param("date") String date,
									SearchDTO param);
	//리스트 조회
	List<FestivalDTO> doRetrieve(SearchDTO param);
	//총 축제 건수 
	int getCount() throws SQLException;
	//조회수 증가
	int upViews(FestivalDTO param);
}
