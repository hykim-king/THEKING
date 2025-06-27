package com.pcwk.ehr.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.pcwk.ehr.board.domain.BoardDTO;
import com.pcwk.ehr.image.domain.ImageDTO;

@Mapper
public interface MainMapper{
	List<BoardDTO> getRecentNotice();

	List<ImageDTO> getRecentFestival();

	List<ImageDTO> getPopularTour();

}
