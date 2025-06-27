package com.pcwk.ehr.image.domain;

public class ImageDTO {
	private int    imageId   ; //이미지 번호
	private int    targetNo  ; //타겟 번호
	private String tableName ; //테이블명
	private String imageName ; //이미지 이름
	private String imageUrl  ; //이미지 경로
	private String saveName  ; //저장 이름
	private String regDate   ; //이미지 등록일

	@Override
	public String toString() {
		return "ImageDTO [imageId=" + imageId + ", targetNo=" + targetNo + ", tableName=" + tableName + ", imageName="
				+ imageName + ", imageUrl=" + imageUrl + ", saveName=" + saveName + ", regDate=" + regDate + "]";
	}
	
	
}
