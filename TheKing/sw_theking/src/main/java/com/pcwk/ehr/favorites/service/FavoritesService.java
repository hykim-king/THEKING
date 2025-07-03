package com.pcwk.ehr.favorites.service;

import java.sql.SQLException;
import java.util.List;

import com.pcwk.ehr.favorites.domain.FavoritesDTO;
import com.pcwk.ehr.festival.domain.FestivalDTO;
import com.pcwk.ehr.tour.domain.TourDTO;

public interface FavoritesService {
	//등록
	int doSave(FavoritesDTO param);
	
	//삭제
	int doDelete(FavoritesDTO param);
	
	//단건 조회
	FavoritesDTO doSelectOne(FavoritesDTO param);
	
	//축제 개수 조회
	int getFestaCount(String userId);
	
	//관광지 개수 조회
	int getTourCount(String userId);
	
	//관광지 즐겨찾기 조회
	List<TourDTO> getFavoriteTours(String userId) throws SQLException;
	
	//축제 즐겨찾기 조회
	List<FestivalDTO> getFavoriteFestivals(String userId) throws SQLException;
}
