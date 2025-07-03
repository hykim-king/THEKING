package com.pcwk.ehr.favorites.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pcwk.ehr.favorites.domain.FavoritesDTO;
import com.pcwk.ehr.festival.domain.FestivalDTO;
import com.pcwk.ehr.mapper.FavoritesMapper;
import com.pcwk.ehr.tour.domain.TourDTO;

@Service
public class FavoritesServiceImpl implements FavoritesService{
	@Autowired
	FavoritesMapper mapper;

	@Override
	public int doSave(FavoritesDTO param) {
		return mapper.doSave(param);
	}

	@Override
	public int doDelete(FavoritesDTO param) {
		return mapper.doDelete(param);
	}

	@Override
	public FavoritesDTO doSelectOne(FavoritesDTO param) {
		return mapper.doSelectOne(param);
	}

	@Override
	public int getFestaCount(String userId) {
		return mapper.getFestaCount(userId);
	}

	@Override
	public int getTourCount(String userId) {
		return mapper.getTourCount(userId);
	}

	@Override
    public List<TourDTO> getFavoriteTours(String userId) throws SQLException {
        return mapper.getFavoriteTour(userId);
    }

    @Override
    public List<FestivalDTO> getFavoriteFestivals(String userId) throws SQLException {
        return mapper.getFavoriteFestival(userId);
    }
}
