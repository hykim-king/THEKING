package com.pcwk.ehr.region.dao;

import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RegionDao {
	final String NAMESPACE = "com.pcwk.ehr.tour";
	final String DOT = ".";
	
	@Autowired
	SqlSessionTemplate sqlSessionTemplate; //DB 연결, sql 수행, 자원 반납
	
	
}
