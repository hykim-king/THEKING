package com.pcwk.ehr.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import com.pcwk.ehr.cmn.WorkDiv;
import com.pcwk.ehr.favorites.domain.FavoritesDTO;
import com.pcwk.ehr.festival.domain.FestivalDTO;
import com.pcwk.ehr.tour.domain.TourDTO;
import com.pcwk.ehr.user.domain.UserDTO;

@Mapper
public interface FavoritesMapper extends WorkDiv<FavoritesDTO>{
	int getFestaCount(String userId);
	int getTourCount(String userId);
	List<TourDTO> getFavoriteTours(String userId);
	List<FestivalDTO> getFavoriteFestivals(String userId);
} 
