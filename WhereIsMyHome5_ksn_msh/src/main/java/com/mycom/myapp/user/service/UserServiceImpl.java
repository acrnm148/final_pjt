package com.mycom.myapp.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycom.myapp.user.dao.UserDao;
import com.mycom.myapp.user.dto.UserDto;
import com.mycom.myapp.user.dto.UserResultDto;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired // field DT -> 생성자 DT 추천 기억
	UserDao userDao;
	
	private final int SUCCESS = 1;
	private final int FAIL = -1;
	
	@Override
	public UserResultDto userRegister(UserDto userDto) {
		UserResultDto userResultDto = new UserResultDto();
		if (userDao.userRegister(userDto) == 1) {
			userResultDto.setResult(SUCCESS);
		} else {
			userResultDto.setResult(FAIL);
		}
		return userResultDto;
	}

	@Override
	public UserResultDto userInfo(UserDto userDto) {
		UserResultDto userResultDto = new UserResultDto();
		
		try {
			
		} catch(Exception e) {
			e.printStackTrace();
			userResultDto.setResult(FAIL);
		}
		
		return userResultDto;
	}

	@Override
	public UserResultDto userUpdate(UserDto userDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserResultDto userDelete(UserDto userDto) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
