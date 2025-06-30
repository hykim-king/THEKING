package com.pcwk.ehr.tour.service;

public class TourValidation {
	/**
	 * NotNull 검사
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
	/**
	 * 전화번호 유효성 검사
	 * @param Tel
	 * @return
	 */
	public static boolean isValidTel(String Tel) {
	    return Tel != null && Tel.matches("^\\d{2,3}-\\d{3,4}-\\d{4}$");
	}

}
