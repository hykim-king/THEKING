package com.pcwk.ehr.festival.domain;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.image.domain.ImageDTO;
import com.pcwk.ehr.region.domain.RegionDTO;

public class FestivalDTO extends DTO{
	private int festaNo  ;	//축제번호
	private String name	     ; //축제명
	private String subtitle  ;	//소제목
	private String contents  ;	//상세 내용
	private int views     ;	    //조회수
	private String address   ;	    //축제 주소
	private String tel	     ;     //연락처  
	private int fee	     ;     //입장료  
	private int regionNo ;	//지역번호
	private String startDate;	//축제 시작일
	private String endDate  ;	//축제 종료일
	private ImageDTO image  ;
	private RegionDTO region ;
	
	
	public ImageDTO getImage() {
		return image;
	}


	public void setImage(ImageDTO image) {
		this.image = image;
	}


	public RegionDTO getRegion() {
		return region;
	}


	public void setRegion(RegionDTO region) {
		this.region = region;
	}


	public FestivalDTO() {}


	public int getFestaNo() {
		return festaNo;
	}


	public void setFestaNo(int festaNo) {
		this.festaNo = festaNo;
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


	public int getRegionNo() {
		return regionNo;
	}


	public void setRegionNo(int regionNo) {
		this.regionNo = regionNo;
	}


	public String getStartDate() {
		return startDate;
	}


	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}


	public String getEndDate() {
		return endDate;
	}


	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}





	public FestivalDTO(int festaNo, String name, String subtitle, String contents, int views, String address,
			String tel, int fee, int regionNo, String startDate, String endDate) {
		super();
		this.festaNo = festaNo;
		this.name = name;
		this.subtitle = subtitle;
		this.contents = contents;
		this.views = views;
		this.address = address;
		this.tel = tel;
		this.fee = fee;
		this.regionNo = regionNo;
		this.startDate = startDate;
		this.endDate = endDate;
	}


	@Override
	public String toString() {
		return "FestivalDTO [festaNo=" + festaNo + ", name=" + name + ", subtitle=" + subtitle + ", contents="
				+ contents + ", views=" + views + ", address=" + address + ", tel=" + tel + ", fee=" + fee
				+ ", regionNo=" + regionNo + ", startDate=" + startDate + ", endDate=" + endDate + ", image=" + image
				+ ", region=" + region + "]";
	}





	
	
}
