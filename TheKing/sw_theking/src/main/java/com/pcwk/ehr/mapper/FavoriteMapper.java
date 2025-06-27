package com.pcwk.ehr.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.pcwk.ehr.user.domain.UserDTO;

@Mapper
public interface FavoriteMapper {
	int doSave(UserDTO user,@Param("targetNo")String targetNo,@Param("tableName")String tableName);
	int doDelete(UserDTO user,@Param("targetNo")String targetNo,@Param("tableName")String tableName);
}
