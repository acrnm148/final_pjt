package service;

import dao.UserDao;
import dao.UserDaoImpl;
import dto.UserDto;

public class UserServiceImpl implements UserService{

	private static UserServiceImpl instance = new UserServiceImpl();
	
	private UserServiceImpl() {}
	
	public static UserServiceImpl getinstance() {
		return instance;
	}
	
	
	UserDao userDao = UserDaoImpl.getInstance();
	
	
	@Override
	public int userRegister(UserDto userDto) {
		return userDao.userRegister(userDto);
	}

	@Override
	public int userDupCheck(UserDto userDto) {	
		return userDao.userDupCheck(userDto);
	}

	@Override
	public UserDto userLogin(String email, String password) {
		return userDao.userLogin(email, password);
	}

	@Override
	public int userChange(String email, String password, String name) {
		return userDao.userChange(email, password, name);
	}

	@Override
	public int userUnregist(String email, String password) {
		return userDao.userUnregist(email, password);
	}

	
}
