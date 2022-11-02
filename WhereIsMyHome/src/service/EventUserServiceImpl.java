package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.EventUserDao;
import dao.EventUserDaoImpl;
import dto.EventUserDto;

public class EventUserServiceImpl implements EventUserService {

	public static EventUserServiceImpl instance = new EventUserServiceImpl();
	private EventUserServiceImpl() {}
	public static EventUserServiceImpl getInstance() {
		return instance;
	}
	EventUserDao eventUserDao = EventUserDaoImpl.getInstance();
	@Override
	public int insert(int eventSeq, int userSeq) throws SQLException {
		return eventUserDao.insert(eventSeq, userSeq);
	}
	@Override
	public List<EventUserDto> select() throws SQLException {
		List<EventUserDto> list = new ArrayList<> ();
		list = eventUserDao.select();
		return list;
	}
	@Override
	public EventUserDto detail(int eventUserSeq) throws SQLException {
		return eventUserDao.detail(eventUserSeq);
	}
	@Override
	public int delete(int eventUserSeq) throws SQLException {
		return eventUserDao.delete(eventUserSeq);
	}
	@Override
	public int modify(int eventUserSeq) throws SQLException {
		return 0;
	}

}
