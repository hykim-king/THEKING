package com.pcwk.ehr.mapper;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;

import com.pcwk.ehr.cmn.WorkDiv;
import com.pcwk.ehr.image.domain.ImageDTO;

@Mapper
public interface ImageMapper extends WorkDiv<ImageDTO> {
	int saveAll();
	
	void deleteAll() throws SQLException;
	
	int getCount() throws SQLException;

}
