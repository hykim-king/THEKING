package com.pcwk.ehr.cmn;

import java.util.List;

import com.pcwk.ehr.user.domain.UserDTO;

public interface WorkDiv<T> {
	/**
	 * 목록 조회
	 * @param param
	 * @return list<t>
	 */
	List<T> doRetrieve(SearchDTO param);
	/**
	 * 단건 삭제
	 * @param param
	 * @return
	 */
	int doDelete(T param);
	
	/**
	 * 수정
	 * @param param
	 * @return
	 */
	int doUpdate(T param);
	
	/**
	 * 단건 조회
	 * @param dto
	 * @return
	 */
	T doSelectOne(T dto);
	/**
	 * 저장
	 * @param param
	 * @return
	 */
	int doSave(T param);

}
