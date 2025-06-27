package com.pcwk.ehr.user.service;

import com.pcwk.ehr.mapper.UserMapper;
import com.pcwk.ehr.user.domain.UserDTO;

public class UserValidation {

	/**
	 * 아이디 유효성 검사
	 * @param userId
	 * @return
	 */
	public static boolean isValidUserId(String userId) {
	    return userId != null && userId.matches("^[A-Za-z0-9_-]{6,12}$");
	}

	/**
	 * 이메일 유효성 검사
	 * @param email
	 * @return
	 */
	public static boolean isValidEmail(String email) {
	    return email != null && email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
	}

	/**
	 * 핸드폰 유효성 검사
	 * @param mobile
	 * @return
	 */
	public static boolean isValidMobile(String mobile) {
	    return mobile != null && mobile.matches("^01[016789]-\\d{3,4}-\\d{4}$");
	}

	/**
	 * 비밀번호 유효성 검사
	 * @param password
	 * @return
	 */
	public static boolean isValidPassword(String password) {
	    return password != null && password.matches("^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[^A-Za-z0-9]).{8,20}$");
	}
	

	/**
	 * 아이디 중복 검사
	 * @param mapper
	 * @param userId
	 * @return
	 */
    public static boolean isDuplicateUserId(UserMapper mapper, String userId) {
    	if (userId == null) {
        	return false;
        } else {
        	return mapper.isDuplicateUserId(userId) > 0;
        }
    }
    
    public static boolean isDuplicateNickname(UserMapper mapper, String nickname) {
        if (nickname == null) {
        	return false;
        } else {
        	return mapper.isDuplicateNickname(nickname) > 0;
        }	
    }
    
    public static boolean isDuplicateEmail(UserMapper mapper, String email) {
    	if (email == null) {
        	return false;
        } else {
        	return mapper.isDuplicateEmail(email) > 0;
        }
    }
    
}

