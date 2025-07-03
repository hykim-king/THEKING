package com.pcwk.ehr.image.service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pcwk.ehr.image.domain.ImageDTO;
import com.pcwk.ehr.mapper.ImageMapper;

@Service
public class ImageServiceImpl implements ImageService {
	Logger log = LogManager.getLogger(getClass());
	
	@Autowired
	private ImageMapper mapper;

	public ImageServiceImpl() {}
	//이미지는 저장, 업데이트, 삭제 가능
	//조회 기능은  없음.
		
	public ImageServiceImpl(ImageMapper mapper) {
		super();
		this.mapper = mapper;
	}

	@Override
	public int doDelete(ImageDTO param) {
		return mapper.doDelete(param);
	}

	@Override
	public int doUpdate(ImageDTO param) {
		
	    return mapper.doUpdate(param);
		}
	
	@Override
	public int doSave(ImageDTO param) throws SQLException {
		
		//유효성 검사
		if(param.getImageName()!= null && param.getImageName().length()>30) {
			throw new IllegalArgumentException("이미지명은 30자를 넘을 수 없습니다.");
		}
		//2.유효성 검사 확장자 체크
		String extension = param.getImageName().substring(param.getImageName().lastIndexOf(".")+1);
		
		List<String>allowsext = Arrays.asList("jpg", "jpeg", "png", "gif", "webp");
		if(!allowsext.contains(extension)) {
			throw new IllegalArgumentException("허용되지 않은 이미지 형식입니다.");
		}
		
		String regDt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd"));
        String uuid = UUID.randomUUID().toString().substring(0, 8);
        String url = "/resources/images/";

        // 이미지 정보를 ImageDTO에 담고
        param.setSaveName(regDt + "_" + uuid + "_" + param.getImageName());
        param.setImageUrl(url + param.getImageName());
        param.setRegDate(regDt);

        // 저장
		return mapper.doSave(param);
	}

}
