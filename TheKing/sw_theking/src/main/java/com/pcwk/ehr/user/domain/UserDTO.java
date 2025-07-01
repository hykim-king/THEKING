package com.pcwk.ehr.user.domain;

import com.pcwk.ehr.cmn.DTO;
import com.pcwk.ehr.region.domain.RegionDTO;

public class UserDTO extends DTO {
	private int userNo;
	private String userId;
	private String password;
	private String name;
	private String nickname;
	private String email;
	private String mobile;
	private String address;
	private String role;
	private String regDt;
	private String modDt;
	
	
	public UserDTO() {
		
	}
	
	public UserDTO(int userNo, String userId, String password, String name, String nickname, String email,
			String mobile, String address, String role, String regDt, String modDt) {
		super();
		this.userNo = userNo;
		this.userId = userId;
		this.password = password;
		this.name = name;
		this.nickname = nickname;
		this.email = email;
		this.mobile = mobile;
		this.address = address;
		this.role = role;
		this.regDt = regDt;
		this.modDt = modDt;
	}

	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}
	/**
	 * @param nickname the nickname to set
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}
	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
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
		return "UserDTO [userNo=" + userNo + ", userId=" + userId + ", password=" + password + ", name=" + name
				+ ", nickname=" + nickname + ", email=" + email + ", mobile=" + mobile + ", address=" + address
				+ ", role=" + role + ", regDt=" + regDt + ", modDt=" + modDt + "]";
	}



	
	
}
