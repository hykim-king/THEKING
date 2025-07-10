package com.pcwk.ehr.tour.service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
	public List<TourDTO> doRetrieve(Map<String, Object> paramMap) {
		return tourMapper.doRetrieve(paramMap);
	}

	@Override
	public int doDelete(TourDTO param) {
		// 이미지가 있을 경우, 이미지도 같이 삭제
		List<ImageDTO> images = imageMapper.getImages(param.getTourNo(), "TOUR");
		for (ImageDTO image : images) {
			imageMapper.doDelete(image);
		}
		return tourMapper.doDelete(param);
	}

	@Override
	public int doUpdate(TourDTO param) throws SQLException {
		// 1.입력값 검사
		validateTourInput(param);

		// 2.관광지 유효성 검사
		RegionDTO region = parseAddress(param.getAddress());
		param.setRegion(region);

		// 3.1 이미지가 추가 되는경우(DBx list0)
		// 3.2 이미지가 삭제 되는 경우(DB0 listx)

		// 3.기존 리스트 조회
		List<String> newImages = param.getTourImage(); // 새로 등록할 이미지 목록
		if (newImages == null)
			newImages = new ArrayList<>();

		if (param.getTourNo() != null) {
			List<ImageDTO> existingImages = imageMapper.getImages(param.getTourNo(), "TOUR");

			Set<String> existingImageSet = new HashSet<>(); // 이미 존재하는 이름 (DB)
			Set<String> newImageSet = new HashSet<>(newImages); // 새로 넘어온 이름(현재LIST)

			// 기존에 있던 이름 set에 저장
			for (ImageDTO image : existingImages) {
				existingImageSet.add(image.getImageName());
			}
			// 3.2 이미지가 삭제 되는 경우(DB0 listx)
			for (ImageDTO image : existingImages) {// DB존재
				if (!newImageSet.contains(image.getImageName())) {// list없음
					imageService.doDelete(image);// 삭제
				}
			}
			// 3.1 이미지가 추가 되는경우(DBx list0)
			for (String saveImageName : newImageSet) {
				if (!existingImageSet.contains(saveImageName)) {

					ImageDTO image = new ImageDTO();
					image.setTargetNo(param.getTourNo());
					image.setTableName("TOUR");
					image.setImageName(saveImageName);

					imageService.doSave(image);
				}
			}
		}
		return tourMapper.doUpdate(param);
	}

	@Override
	public TourDTO doSelectOne(TourDTO param) throws SQLException {
		//단건 조회 + 조회된 count 조회
		int flag = tourMapper.viewsUpdate(param);
		log.debug("flag: {}",flag);
		
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
	
	private static final Map<String, String> sidoNameMapping = new HashMap<>();

	static {
	    sidoNameMapping.put("서울", "서울특별시");
	    sidoNameMapping.put("부산", "부산광역시");
	    sidoNameMapping.put("대구", "대구광역시");
	    sidoNameMapping.put("인천", "인천광역시");
	    sidoNameMapping.put("광주", "광주광역시");
	    sidoNameMapping.put("대전", "대전광역시");
	    sidoNameMapping.put("울산", "울산광역시");
	    sidoNameMapping.put("세종", "세종특별자치시");
	    sidoNameMapping.put("경기", "경기도");
	    sidoNameMapping.put("강원", "강원도");
	    sidoNameMapping.put("충북", "충청북도");
	    sidoNameMapping.put("충남", "충청남도");
	    sidoNameMapping.put("전북", "전라북도");
	    sidoNameMapping.put("전남", "전라남도");
	    sidoNameMapping.put("경북", "경상북도");
	    sidoNameMapping.put("경남", "경상남도");
	    sidoNameMapping.put("제주", "제주특별자치도");
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

		// 첫 번째 단어가 sido일 경우, 이를 변환
	    if (parts.length > 0) {
	        regionSido = parts[0];  // '서울', '부산' 등
	        if (sidoNameMapping.containsKey(regionSido)) {
	            // sidoNameMapping을 통해 실제 시도명으로 변환
	            regionSido = sidoNameMapping.get(regionSido);
	        }
	    }
	    // 두 번째 단어는 구 이름
	    if (parts.length > 1) {
	        regionGugun = parts[1];  // '마포구', '중구' 등
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
