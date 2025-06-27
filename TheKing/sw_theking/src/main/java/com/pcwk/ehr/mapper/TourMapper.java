package com.pcwk.ehr.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.pcwk.ehr.cmn.WorkDiv;
import com.pcwk.ehr.region.domain.RegionDTO;
import com.pcwk.ehr.tour.domain.TourDTO;

@Mapper
public interface TourMapper extends WorkDiv<TourDTO> {
	
	public int saveAll();
	
	public List<TourDTO> doRetrieve(Map<String, Object> paramMap);
	
	public int viewsUpdate(TourDTO param);
	
	public int getCount() throws SQLException;
	
	public void deleteAll()throws SQLException;
	
	Integer getRegionNo(@Param("regionSido") String sido,
            @Param("regionGugun") String gugun);


}
