package com.mycom.myapp.user.service;

import com.mycom.myapp.user.dto.UserDto;
import com.mycom.myapp.user.dto.UserResultDto;

public interface UserService {
	UserResultDto userRegister(UserDto userDto); //post
	UserResultDto userInfo(UserDto userDto); //get
	UserResultDto userUpdate(UserDto userDto); //put
	UserResultDto userDelete(UserDto userDto); //delete
}
