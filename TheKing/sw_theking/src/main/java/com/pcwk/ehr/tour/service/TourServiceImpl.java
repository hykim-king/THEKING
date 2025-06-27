package com.pcwk.ehr.tour.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.pcwk.ehr.cmn.SearchDTO;
import com.pcwk.ehr.mapper.TourMapper;
import com.pcwk.ehr.region.domain.RegionDTO;
import com.pcwk.ehr.tour.domain.TourDTO;
import com.pcwk.ehr.user.domain.UserDTO;

public class TourServiceImpl implements TourService {
	Logger log = LogManager.getLogger(getClass());
	
	@Autowired
	TourMapper tourMapper;
	
	@Override
	public List<TourDTO> doRetrieve(SearchDTO param) {
		return tourMapper.doRetrieve(param);
	}

	@Override
	public int doDelete(TourDTO param) {
		return tourMapper.doDelete(param);
	}

	@Override
	public int doUpdate(TourDTO param) {

		RegionDTO region = parseAddress(param.getAddress());
	    param.setRegion(region);
		
		return tourMapper.doUpdate(param);
	}

	@Override
	public TourDTO doSelectOne(TourDTO param) throws SQLException {
		return tourMapper.doSelectOne(param);
	}

	@Override
	public int doSave(TourDTO param) throws SQLException {
		
		RegionDTO region = parseAddress(param.getAddress());
	    param.setRegion(region);
	    
		return tourMapper.doSave(param);
	}
	private RegionDTO parseAddress(String address) {
	    if (address == null || address.trim().isEmpty()) {
	        throw new IllegalArgumentException("주소가 유효하지 않습니다.");
	    }

	    String[] parts = address.split(" ");

	    String regionSido = null;
	    String regionGugun = null;

	    if (parts.length > 0 && "세종특별자치시".equals(parts[0])) {
	        regionSido = parts[0];
	        regionGugun = null;
	    } else if (parts.length > 1) {
	        regionSido = parts[0];
	        regionGugun = parts[1];
	    }

	    Integer regionNo = tourMapper.getRegionNo(regionSido, regionGugun);
	    log.debug("주소 파싱: sido={}, gugun={}", regionSido, regionGugun);
	    log.debug("조회된 regionNo={}", regionNo);
	    
	    if (regionNo == null) {
	        throw new IllegalStateException("해당 주소에 대한 지역 번호가 존재하지 않습니다.");
	    }

	    RegionDTO region = new RegionDTO();
	    region.setRegionNo(regionNo);
	    region.setRegionSido(regionSido);
	    region.setRegionGugun(regionGugun);

	    return region;
	}

}
