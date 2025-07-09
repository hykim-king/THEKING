package com.pcwk.ehr.comment.domain;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.user.domain.UserDTO;

public class CommentDTO extends DTO {
	
	private int comNo     ;
	private String userId    ;
	private String contents  ;
	private int targetNo  ;
	private String tableName ;
	private String regDt     ;
	private String modDt     ;
	
	private UserDTO userDTO;
	private String userName;
	private String userNickname;

	public CommentDTO() {
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
	public CommentDTO(int comNo, String userId, String contents, int targetNo, String tableName, String regDt,
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
	public int getComNo() {
		return comNo;
	}


	/**
	 * @param comNo the comNo to set
	 */
	public void setComNo(int comNo) {
		this.comNo = comNo;
	}


	/**
	 * @return the targetNo
	 */
	public int getTargetNo() {
		return targetNo;
	}


	/**
	 * @param targetNo the targetNo to set
	 */
	public void setTargetNo(int targetNo) {
		this.targetNo = targetNo;
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
	
	
	public UserDTO getUserDTO() {
		return userDTO;
	}


	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getUserNickname() {
		return userNickname;
	}


	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}


	@Override
	public String toString() {
		return "CommentDTO [comNo=" + comNo + ", userId=" + userId + ", contents=" + contents + ", targetNo=" + targetNo
				+ ", tableName=" + tableName + ", regDt=" + regDt + ", modDt=" + modDt + ", userDTO=" + userDTO
				+ ", userName=" + userName + ", userNickname=" + userNickname + ", toString()=" + super.toString()
				+ "]";
	}

}
