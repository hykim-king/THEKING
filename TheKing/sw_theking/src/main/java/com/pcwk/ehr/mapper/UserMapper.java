/**
 * Package Name : com.pcwk.ehr.mapper <br/>
 * Class Name: UserMapper.java <br/>
 * Description:  <br/>
 * Modification imformation : <br/> 
 * ------------------------------------------<br/>
 * 최초 생성일 : 2025-06-25<br/>
 *
 * ------------------------------------------<br/>
 * @author :user
 * @since  :2025-06-25
 * @version: 0.5
 */
package com.pcwk.ehr.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.pcwk.ehr.cmn.WorkDiv;
import com.pcwk.ehr.user.domain.UserDTO;

/**
 * @author user
 *
 */
@Mapper
public interface UserMapper extends WorkDiv<UserDTO> {

	int saveAll();
	
	List<UserDTO> getAll();
	
	void deleteAll() throws SQLException;
	
	int getCount() throws SQLException;
	
	int isDuplicateUserId(String userId);
	
	int isDuplicateNickname(String nickname);
	
	int isDuplicateEmail(String email);
}
