package com.mycom.myapp.event.service;

import java.util.List;

import com.mycom.myapp.event.dto.EventDto;

public interface EventService {
	int eventRegister(EventDto eventDto);
	List<EventDto> getEvent();
	public int eventDelete(int eventSeq);
}
