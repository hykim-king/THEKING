package com.pcwk.ehr.image.domain;

public class ImageDTO {
	private Integer    imageId   ; //이미지 번호
	private Integer    targetNo  ; //타겟 번호
	private String tableName ; //테이블명
	private String imageName ; //이미지 이름
	private String imageUrl  ; //이미지 경로
	private String saveName  ; //저장 이름
	private String regDate   ; //이미지 등록일
	
	
	
	public ImageDTO() {
	}



	public ImageDTO(Integer imageId, Integer targetNo, String tableName, String imageName, String imageUrl, String saveName,
			String regDate) {
		super();
		this.imageId = imageId;
		this.targetNo = targetNo;
		this.tableName = tableName;
		this.imageName = imageName;
		this.imageUrl = imageUrl;
		this.saveName = saveName;
		this.regDate = regDate;
	}



	public int getImageId() {
		return imageId;
	}



	public void setImageId(Integer imageId) {
		this.imageId = imageId;
	}



	public int getTargetNo() {
		return targetNo;
	}



	public void setTargetNo(Integer targetNo) {
		this.targetNo = targetNo;
	}



	public String getTableName() {
		return tableName;
	}



	public void setTableName(String tableName) {
		this.tableName = tableName;
	}



	public String getImageName() {
		return imageName;
	}



	public void setImageName(String imageName) {
		this.imageName = imageName;
	}



	public String getImageUrl() {
		return imageUrl;
	}



	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}



	public String getSaveName() {
		return saveName;
	}



	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}



	public String getRegDate() {
		return regDate;
	}



	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}



	public ImageDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ImageDTO(int imageId, int targetNo, String tableName, String imageName, String imageUrl, String saveName,
			String regDate) {
		super();
		this.imageId = imageId;
		this.targetNo = targetNo;
		this.tableName = tableName;
		this.imageName = imageName;
		this.imageUrl = imageUrl;
		this.saveName = saveName;
		this.regDate = regDate;
	}

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	public int getTargetNo() {
		return targetNo;
	}

	public void setTargetNo(int targetNo) {
		this.targetNo = targetNo;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getSaveName() {
		return saveName;
	}

	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	@Override
	public String toString() {
		return "ImageDTO [imageId=" + imageId + ", targetNo=" + targetNo + ", tableName=" + tableName + ", imageName="
				+ imageName + ", imageUrl=" + imageUrl + ", saveName=" + saveName + ", regDate=" + regDate + "]";
	}
	
	
}
