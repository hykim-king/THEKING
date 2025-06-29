package com.pcwk.ehr.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import com.pcwk.ehr.cmn.WorkDiv;
import com.pcwk.ehr.favorites.domain.FavoritesDTO;
import com.pcwk.ehr.user.domain.UserDTO;

@Mapper
public interface FavoritesMapper extends WorkDiv<FavoritesDTO>{
	int getFestaCount(@RequestParam("userId")String userId);
	int getTourCount(@RequestParam("userId")String userId);
}
