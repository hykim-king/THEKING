package com.pcwk.ehr.tour.service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
	public int doUpdate(TourDTO param) {
		// 1.입력값 검사
		validateTourInput(param);

		// 2.관광지 유효성 검사
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

		// 1.입력값 검사
		validateTourInput(param);

		// 2.관광지 유효성 검사

		RegionDTO region = parseAddress(param.getAddress());
		param.setRegion(region);

		// 3.이미지가 있을 경우 저장
		if (param.getTourImage() != null || !param.getTourImage().isEmpty()) {

			for (String imageName : param.getTourImage()) {
                ImageDTO image = new ImageDTO();
                image.setTargetNo(param.getTourNo());  // 관광지 번호
                image.setTableName("TOUR");  // 관광지 관련 테이블

                image.setImageName(imageName);  // 이미지 이름
                
                // ImageService의 updateImages 호출
                imageService.doSave(image);  // 이미지를 업데이트
            }
        }

		return tourMapper.doSave(param);
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
