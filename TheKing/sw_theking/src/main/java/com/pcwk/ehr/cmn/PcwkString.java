package com.pcwk.ehr.cmn;

import com.google.common.base.Strings;
import com.pcwk.ehr.user.domain.UserDTO;

public class PcwkString {
	
	/**
	 * value가 0이면 defaultValue 리턴
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static int nvlZero(int value, int defaultValue) {
		return value==0 ? defaultValue : value;
	}

	/**
	 * null 또는 빈 문자열인지 체크
	 * @param str
	 * @return
	 */
	public static boolean isNullOrEmpty(String str) {
		return Strings.isNullOrEmpty(str);
	}
	
	/**
	 * null 0-> 빈 문자열
	 * @param str
	 * @return
	 */
	public static String nullToEmpty(String str) {
		//return (str==null) ? "" : str;
		return Strings.nullToEmpty(str);
	}
	
	//ADMIN인지 확인
	public static boolean isAdmin(UserDTO user) {
	    return "ADMIN".equalsIgnoreCase(user.getRole());
	}
	/**
	 * 문자가 null,""입력 시 -> defaultValue로 변경
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static String nvlString(String value,String defaultValue) {
		if(Strings.isNullOrEmpty( value)==true) {
			return defaultValue;
		}else
			return value;
	}
}
