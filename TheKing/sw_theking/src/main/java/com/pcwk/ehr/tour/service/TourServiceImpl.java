package com.pcwk.ehr.tour.service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pcwk.ehr.cmn.SearchDTO;
import com.pcwk.ehr.image.domain.ImageDTO;
import com.pcwk.ehr.image.service.ImageService;
import com.pcwk.ehr.mapper.ImageMapper;
import com.pcwk.ehr.mapper.TourMapper;
import com.pcwk.ehr.region.domain.RegionDTO;
import com.pcwk.ehr.tour.domain.TourDTO;
import com.pcwk.ehr.user.domain.UserDTO;
import com.pcwk.ehr.user.service.UserValidation;

@Service
public class TourServiceImpl implements TourService {
	Logger log = LogManager.getLogger(getClass());

	@Autowired
	TourMapper tourMapper;

	@Autowired
	ImageMapper imageMapper;
	
	@Autowired
	ImageService imageService;

	@Override
	public List<TourDTO> doRetrieve(SearchDTO param) {
		return tourMapper.doRetrieve(param);
	}

	@Override
	public int doDelete(TourDTO param) {
		return tourMapper.doDelete(param);
	}

	@Override
	public int doUpdate(TourDTO param) throws SQLException {
		// 1.입력값 검사
		validateTourInput(param);

		// 2.관광지 유효성 검사
		RegionDTO region = parseAddress(param.getAddress());
		param.setRegion(region);
		
		//3.1 이미지가 추가 되는경우(DBx list0)
		//3.2 이미지가 삭제 되는 경우(DB0 listx)
		//3.3 이미지가 삭제된 후 추가 되는 경우
		
		//3.기존 리스트 조회
	    List<String> newImages = param.getTourImage(); // 새로 등록할 이미지 목록
	    if (newImages == null) newImages = new ArrayList<>(); //
	    
	    int result = tourMapper.doSave(param);
	    if (result > 0 && param.getTourNo() != null) {
	    List<ImageDTO> existingImages = imageMapper.getImages(param.getTourNo(), "TOUR");
	    
	    Set<String> existingImageNames = new HashSet<>(); //이미 존재하는 이름 
	    Set<String> newImageSet = new HashSet<>(newImages); //새로 넘어온 이름
	    
	    //기존에 있던 이름 set에 저장
	    for (ImageDTO image : existingImages) {
	        existingImageNames.add(image.getImageName());
	    }
	    //기존에 있는데 새롭게 추가된 것에 없을 경우
	    //이름으로 조회하는 게 의미가 있나?
	    for (ImageDTO image : existingImages) {
	    	if(!newImageSet.contains(image.getImageName())){
	    		imageService.doDelete(image);
	    	}
	    }
	    }
		

		return tourMapper.doUpdate(param);
	}

	@Override
	public TourDTO doSelectOne(TourDTO param) throws SQLException {
		return tourMapper.doSelectOne(param);
	}

	@Override
	public int doSave(TourDTO param) throws SQLException {

		// 1.입력값 검사
		validateTourInput(param);

		// 2.관광지 유효성 검사
		RegionDTO region = parseAddress(param.getAddress());
		param.setRegion(region);
		

		// 3.이미지가 있을 경우 저장
		int result = tourMapper.doSave(param);
		
		if (result > 0 && param.getTourNo() != null) {
			List<String> newImages = param.getTourImage(); // 새로 등록할 이미지 목록
			if (newImages != null && !newImages.isEmpty()) {
				for (String imageName : newImages) {
					ImageDTO image = new ImageDTO();
					image.setTargetNo(param.getTourNo());
					image.setTableName("TOUR");
					image.setImageName(imageName);

					imageService.doSave(image);
				}
			}
		}

		return result;
	}
	
//	private void saveOrUpdateTourImages(TourDTO param) throws SQLException {
//	    List<String> newImages = param.getTourImage(); // 새로 등록할 이미지 목록
//	    if (newImages == null) newImages = new ArrayList<>();
//
//	    // 1. DB에서 기존 이미지 목록 조회
//	    List<ImageDTO> existingImages = imageService.getImagesByTarget("TOUR", param.getTourNo());
//	    Set<String> existingImageNames = existingImages.stream()
//	        .map(ImageDTO::getImageName)
//	        .collect(Collectors.toSet());
//
//	    Set<String> newImageSet = new HashSet<>(newImages);
//
//	    // 2. 삭제 대상 찾기 (기존에는 있었는데, 새로 넘어온 리스트엔 없는 것)
//	    for (ImageDTO existing : existingImages) {
//	        if (!newImageSet.contains(existing.getImageName())) {
//	            imageService.doDelete(existing);
//	        }
//	    }

//	    // 3. 추가 대상 찾기 (새로 넘어왔는데 기존에 없던 것)
//	    for (String imageName : newImages) {
//	        if (!existingImageNames.contains(imageName)) {
//	            ImageDTO image = new ImageDTO();
//	            image.setTargetNo(param.getTourNo());
//	            image.setTableName("TOUR");
//	            image.setImageName(imageName);
//
//	            imageService.doSave(image); // 새 이미지 추가
//	        }
//	    }
//	}

	/**
	 * 입력값 검사
	 * 
	 * @param param
	 * @return
	 */
	private TourDTO validateTourInput(TourDTO param) {
		// 1.필수값 검사
		if (TourValidation.isEmpty(param.getName())) {
			throw new IllegalArgumentException("관광지명을 입력해 주세요.");
		}
		if (TourValidation.isEmpty(param.getContents())) {
			throw new IllegalArgumentException("내용을 입력해 주세요.");
		}

		// 2.정규 표현식 검사
		if (!TourValidation.isValidTel(param.getTel())) {
			throw new IllegalArgumentException("전화번호 형식이 올바르지 않습니다. (예: 010-1234-5678)");
		}
		// 3.글자 수 확인
		if (param.getName() != null && param.getName().length() > 30) {
			throw new IllegalArgumentException("관광지명은 1자 이상 30자 이내로 입력해 주세요.");
		}
		if (param.getSubtitle() != null && param.getSubtitle().length() > 50) {
			throw new IllegalArgumentException("소제목은 50자 이내로 입력해 주세요.");
		}
		if (param.getHoliday() != null && param.getHoliday().length() > 15) {
			throw new IllegalArgumentException("휴일 정보는 15자 이내로 입력해 주세요.");
		}
		return param;
	}

	/**
	 * 주소 유효성 확인
	 * 
	 * @param address
	 * @return
	 */
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
