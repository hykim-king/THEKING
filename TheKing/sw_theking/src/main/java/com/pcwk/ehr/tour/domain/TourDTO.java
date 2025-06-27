package com.pcwk.ehr.tour.domain;

import com.pcwk.ehr.image.domain.ImageDTO;
import com.pcwk.ehr.region.domain.RegionDTO;

public class TourDTO  {
	private  Integer tourNo	; //관광지 번호
	private  String name	    ; //관광지명      
	private  String subtitle	; //소제목
	private  String contents	; //상세 내용
	private  int views	    ; //조회수
	private  String address ; //관광지 주소
	private  String holiday	; //휴일
	private  String time	; //운영시간
	private  String tel	    ; //연락처
	private  int fee	    ; //입장료
	private  Integer regionNo	; //지역코드
	
	private RegionDTO region;
	private ImageDTO image;
	
	
	public TourDTO() {}
	


	public TourDTO(Integer tourNo, String name, String subtitle, String contents, int views, String address,
			String holiday, String time, String tel, int fee, Integer regionNo, RegionDTO region) {
		super();
		this.tourNo = tourNo;
		this.name = name;
		this.subtitle = subtitle;
		this.contents = contents;
		this.views = views;
		this.address = address;
		this.holiday = holiday;
		this.time = time;
		this.tel = tel;
		this.fee = fee;
		this.regionNo = regionNo;
		this.region = region;
	}



	public RegionDTO getRegion() {
		return region;
	}


	public void setRegion(RegionDTO region) {
		this.region = region;
	}


	public Integer getRegionNo() {
		return regionNo;
	}


	public void setRegionNo(Integer regionNo) {
		this.regionNo = regionNo;
	}

	

	public ImageDTO getImage() {
		return image;
	}



	public void setImage(ImageDTO image) {
		this.image = image;
	}



	public Integer getTourNo() {
		return tourNo;
	}



	public void setTourNo(Integer tourNo) {
		this.tourNo = tourNo;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getSubtitle() {
		return subtitle;
	}



	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}



	public String getContents() {
		return contents;
	}



	public void setContents(String contents) {
		this.contents = contents;
	}



	public int getViews() {
		return views;
	}



	public void setViews(int views) {
		this.views = views;
	}



	public String getAddress() {
		return address;
	}



	public void setAddress(String address) {
		this.address = address;
	}



	public String getHoliday() {
		return holiday;
	}



	public void setHoliday(String holiday) {
		this.holiday = holiday;
	}



	public String getTime() {
		return time;
	}



	public void setTime(String time) {
		this.time = time;
	}



	public String getTel() {
		return tel;
	}



	public void setTel(String tel) {
		this.tel = tel;
	}



	public int getFee() {
		return fee;
	}



	public void setFee(int fee) {
		this.fee = fee;
	}



	@Override
	public String toString() {
		return "TourDTO [tourNo=" + tourNo + ", name=" + name + ", subtitle=" + subtitle + ", contents=" + contents
				+ ", views=" + views + ", address=" + address + ", holiday=" + holiday + ", time=" + time + ", tel="
				+ tel + ", fee=" + fee + ", regionNo=" + regionNo + ", region=" + region + ", image=" + image + "]";
	}

}
