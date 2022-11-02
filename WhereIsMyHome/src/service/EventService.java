package service;

import java.util.List;

import dto.EventDto;

public interface EventService {
	int eventRegister(EventDto eventDto);
	List<EventDto> getEvent();
	public int eventDelete(int eventSeq);
}
