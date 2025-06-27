package com.pcwk.ehr.comment.domain;

public class commentDTO {
	
	private String comNo      ;
	private String userId     ;
	private String contents   ;
	private String targetNo   ;
	private String tableName  ;
	private String regDt      ;
	private String modDt      ;
	
	public commentDTO() {
	}
	
	
	/**
	 * @param comNo
	 * @param userId
	 * @param contents
	 * @param targetNo
	 * @param tableName
	 * @param regDt
	 * @param modDt
	 */
	public commentDTO(String comNo, String userId, String contents, String targetNo, String tableName, String regDt,
			String modDt) {
		super();
		this.comNo = comNo;
		this.userId = userId;
		this.contents = contents;
		this.targetNo = targetNo;
		this.tableName = tableName;
		this.regDt = regDt;
		this.modDt = modDt;
	}



	/**
	 * @return the comNo
	 */
	public String getComNo() {
		return comNo;
	}
	/**
	 * @param comNo the comNo to set
	 */
	public void setComNo(String comNo) {
		this.comNo = comNo;
	}
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the contents
	 */
	public String getContents() {
		return contents;
	}
	/**
	 * @param contents the contents to set
	 */
	public void setContents(String contents) {
		this.contents = contents;
	}
	/**
	 * @return the targetNo
	 */
	public String getTargetNo() {
		return targetNo;
	}
	/**
	 * @param targetNo the targetNo to set
	 */
	public void setTargetNo(String targetNo) {
		this.targetNo = targetNo;
	}
	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}
	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	/**
	 * @return the regDt
	 */
	public String getRegDt() {
		return regDt;
	}
	/**
	 * @param regDt the regDt to set
	 */
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	/**
	 * @return the modDt
	 */
	public String getModDt() {
		return modDt;
	}
	/**
	 * @param modDt the modDt to set
	 */
	public void setModDt(String modDt) {
		this.modDt = modDt;
	}
	
	@Override
	public String toString() {
		return "commentDTO [comNo=" + comNo + ", userId=" + userId + ", contents=" + contents + ", targetNo=" + targetNo
				+ ", tableName=" + tableName + ", regDt=" + regDt + ", modDt=" + modDt + "]";
	}

}
