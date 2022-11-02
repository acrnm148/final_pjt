package com.mycom.myapp.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycom.myapp.user.dao.LoginDao;
import com.mycom.myapp.user.dto.UserDto;

@Service
public class LoginServiceImpl implements LoginService {
	
	@Autowired
	LoginDao loginDao;
	
	@Override
	public UserDto login(UserDto dto) {
		UserDto userDto = loginDao.login(dto.getUserEmail());
		
		// userDtosms 테이블에서 조회한 데이터가 포함
		// dto는 client가 전송한 데이터가 포함
		if (userDto != null && userDto.getUserPassword().equals(dto.getUserPassword())) {
			return userDto;
		}
		return null;
	}

}
