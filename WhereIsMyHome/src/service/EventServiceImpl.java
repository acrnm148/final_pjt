package service;


import java.util.List;

import dao.EventDao;
import dao.EventDaoImpl;
import dto.EventDto;

public class EventServiceImpl implements EventService {

	
	
	private static EventServiceImpl instance = new EventServiceImpl();
	
	private EventServiceImpl() {}
	
	public static EventServiceImpl getinstance() {
		return instance;
	}
	
	
	EventDao eventDao = EventDaoImpl.getInstance();
	
	
	@Override
	public int eventRegister(EventDto eventDto) {
		return eventDao.eventRegister(eventDto);
	}

	@Override
	public List<EventDto> getEvent() {
		return eventDao.getEvent();
	}

	@Override
	public int eventDelete(int eventSeq) {
		return eventDao.eventDelete(eventSeq);
	}

}
