package com.pcwk.ehr.user.service;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Service;

import com.pcwk.ehr.cmn.SearchDTO;
import com.pcwk.ehr.image.domain.ImageDTO;
import com.pcwk.ehr.mapper.CommentMapper;
import com.pcwk.ehr.mapper.FavoritesMapper;
import com.pcwk.ehr.mapper.ImageMapper;
import com.pcwk.ehr.mapper.UserMapper;
import com.pcwk.ehr.user.domain.UserDTO;

@Service
public class UserServiceImpl implements UserService {
	Logger log = LogManager.getLogger(getClass());

	String uuid8 = UUID.randomUUID().toString().substring(0, 8);
	
	@Qualifier("dummyMailService") //bean id로 특정 빈 주입
	@Autowired
	private MailSender mailSender;

	@Autowired
	private UserMapper mapper;
	
	@Autowired
	private ImageMapper imageMapper;

	@Autowired
	private FavoritesMapper favoritesMapper;
	
	@Autowired
	private CommentMapper commentMapper;
	
	public UserServiceImpl() {
	}
	
	@Autowired
    public UserServiceImpl(UserMapper userMapper, FavoritesMapper favoritesMapper) {
        this.mapper = userMapper;
        this.favoritesMapper = favoritesMapper;
    }
	
	@Override
	public List<UserDTO> doRetrieve(SearchDTO param) {
		return mapper.doRetrieve(param);	
	}
	
	@Override
	public int doDelete(UserDTO param) {
		return mapper.doDelete(param);
	}
	
	@Override
	public int doUpdate(UserDTO param) {
		return mapper.doUpdate(param);
	}
	
	@Override
	public UserDTO doSelectOne(UserDTO param) throws SQLException {
		return mapper.doSelectOne(param);
	}
	
	@Override
	public UserDTO doLogin(UserDTO param) throws SQLException {
		return mapper.doLogin(param);
	}
	
	
	@Override
	public int doSave(UserDTO param) throws SQLException {
		// 1. 정규표현식 검사
		if (!UserValidation.isValidUserId(param.getUserId())) {
	        throw new IllegalArgumentException("아이디는 영문자, 숫자, _,- 포함 6~12자리여야 합니다.");
	    }
	    if (!UserValidation.isValidEmail(param.getEmail())) {
	        throw new IllegalArgumentException("이메일 형식이 올바르지 않습니다.");
	    }
	    if (!UserValidation.isValidMobile(param.getMobile())) {
	        throw new IllegalArgumentException("전화번호 형식이 올바르지 않습니다. (예: 010-1234-5678)");
	    }
	    if (!UserValidation.isValidPassword(param.getPassword())) {
	        throw new IllegalArgumentException("비밀번호는 영문, 숫자, 특수문자를 포함한 8~20자여야 합니다.");
	    }

	 // 2. 아이디 중복 체크
	    if (UserValidation.isDuplicateUserId(mapper, param.getUserId())) {
	        throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
	    }
	    if (UserValidation.isDuplicateNickname(mapper, param.getNickname())) {
	        throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
	    }
	    if (UserValidation.isDuplicateEmail(mapper, param.getEmail())) {
	        throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
	    }

	    // 3. 저장
	    int result = mapper.doSave(param);
	    
	    // 4. profileImage가 있을 경우 저장
	    if (param.getProfileImage() != null && !param.getProfileImage().isEmpty()) {
	    	String safeRegDt = param.getRegDt().replaceAll("[/:\\s]", "_");	
	        ImageDTO image = new ImageDTO();
	        image.setTargetNo(param.getUserNo());     // 방금 생성된 user_no
	        image.setTableName("user_tk");               // 고정값
	        image.setImageName(param.getProfileImage().replaceAll(" ", "_"));
	        image.setSaveName(safeRegDt + "_" + uuid8 +"_" + param.getProfileImage());
	        image.setImageUrl("/resources/images/" + image.getImageName());        // 등록자 ID
	        image.setRegDate(param.getRegDt());
	        
	        imageMapper.doSave(image);
	    }
	    return result;
	}
	

}
// class 끝
