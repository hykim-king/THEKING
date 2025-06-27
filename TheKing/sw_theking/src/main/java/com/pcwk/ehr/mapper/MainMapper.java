package com.pcwk.ehr.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.pcwk.ehr.board.domain.BoardDTO;
import com.pcwk.ehr.festival.domain.FestivalDTO;
import com.pcwk.ehr.tour.domain.TourDTO;
@Mapper
public interface MainMapper {
	List<BoardDTO> getRecentNotice();

	List<FestivalDTO> getRecentFestival();

	List<TourDTO> getPopularTour();
}
