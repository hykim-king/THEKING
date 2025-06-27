package com.pcwk.ehr.region.domain;

public class RegionDTO {
	private Integer regionNo	    ; //지역번호
	private String regionSido	; //지역시도
	private String regionGugun	; //지역구군
	
	public RegionDTO() {
		super();
	}

	public RegionDTO(int regionNo, String regionSido, String regionGugun) {
		super();
		this.regionNo = regionNo;
		this.regionSido = regionSido;
		this.regionGugun = regionGugun;
	}

	public Integer getRegionNo() {
		return regionNo;
	}

	public void setRegionNo(Integer regionNo) {
		this.regionNo = regionNo;
	}

	public String getRegionSido() {
		return regionSido;
	}

	public void setRegionSido(String regionSido) {
		this.regionSido = regionSido;
	}

	public String getRegionGugun() {
		return regionGugun;
	}

	public void setRegionGugun(String regionGugun) {
		this.regionGugun = regionGugun;
	}

	@Override
	public String toString() {
		return "RegionDTO [regionNo=" + regionNo + ", regionSido=" + regionSido + ", regionGugun=" + regionGugun + "]";
	}
	
	
}
