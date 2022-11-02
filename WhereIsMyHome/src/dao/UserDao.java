package dao;

import dto.UserDto;

public interface UserDao {
	int userRegister(UserDto userDto);
	int userDupCheck(UserDto userDto);
	UserDto userLogin(String email, String password);
	int userChange(String email, String password, String name);
	int userUnregist(String email, String password);
	
}
