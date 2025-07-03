package com.pcwk.ehr.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.pcwk.ehr.cmn.WorkDiv;
import com.pcwk.ehr.image.domain.ImageDTO;

@Mapper
public interface ImageMapper extends WorkDiv<ImageDTO> {
	int saveAll();
	
	void deleteAll() throws SQLException;
	
	int getCount() throws SQLException;
	
	List<ImageDTO> getImages(@Param("targetNo")int targetNo,@Param("tableName") String tableName); //이미지 조회

}
