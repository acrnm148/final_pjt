package dao;

import java.util.List;

import dto.EventDto;

public interface EventDao {
	int eventRegister(EventDto eventDto);
	List<EventDto> getEvent();
	int eventDelete(int eventSeq);
}
