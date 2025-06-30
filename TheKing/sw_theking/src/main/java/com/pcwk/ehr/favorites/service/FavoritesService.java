package com.pcwk.ehr.favorites.service;

import com.pcwk.ehr.favorites.domain.FavoritesDTO;

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
}
