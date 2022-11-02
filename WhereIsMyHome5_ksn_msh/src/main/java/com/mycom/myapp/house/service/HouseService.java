package com.mycom.myapp.house.service;

import java.util.List;

import com.mycom.myapp.house.dto.HouseDealDetailDto;
import com.mycom.myapp.house.dto.HouseDealSimpleDto;

public interface HouseService {
	List<HouseDealSimpleDto> findHouseDealsByDongCode(HouseDealSimpleDto houseDealDto, int limit, int offset);
	int houseDealsByDongCodeTotalCnt(HouseDealSimpleDto houseDealDto);
	List<HouseDealSimpleDto> findHouseDealsByAptName(String searchWord, int limit, int offset);
	int houseDealsByAptNameTotalCnt(String searchWord);

	HouseDealDetailDto findHouseDealDetail(HouseDealSimpleDto houseDealSimpleDto);
}
