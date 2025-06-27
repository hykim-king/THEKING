package com.pcwk.ehr.favorites.domain;

public class favoritesDTO {
	private int favNo	    ;// 즐겨찾기번호	
	private String userId	;// 유저아이디	  
	private int targetNo    ;//	타겟번호	
	private String tableName;//테이블명
	
	public favoritesDTO() {}


	public int getFavNo() {
		return favNo;
	}

	public void setFavNo(int favNo) {
		this.favNo = favNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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


	public favoritesDTO(int favNo, String userId, int targetNo, String tableName) {
		super();
		this.favNo = favNo;
		this.userId = userId;
		this.targetNo = targetNo;
		this.tableName = tableName;
	}


	@Override
	public String toString() {
		return "favoritesDTO [favNo=" + favNo + ", userId=" + userId + ", targetNo=" + targetNo + ", tableName="
				+ tableName + "]";
	}



	
}
