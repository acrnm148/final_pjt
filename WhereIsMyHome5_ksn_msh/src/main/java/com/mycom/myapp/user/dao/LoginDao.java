package com.mycom.myapp.user.dao;

import org.apache.ibatis.annotations.Mapper;

import com.mycom.myapp.user.dto.UserDto;

@Mapper
public interface LoginDao {
	UserDto login(String userEmail);
}
