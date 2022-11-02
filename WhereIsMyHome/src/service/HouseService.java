package service;

import java.util.List;

import dto.HouseDealDetailDto;
import dto.HouseDealSimpleDto;

public interface HouseService {
	List<HouseDealSimpleDto> findHouseDealsByDongCode(HouseDealSimpleDto houseDealDto, int limit, int offset);
	int houseDealsByDongCodeTotalCnt(HouseDealSimpleDto houseDealDto);
	List<HouseDealSimpleDto> findHouseDealsByAptName(String searchWord, int limit, int offset);
	int houseDealsByAptNameTotalCnt(String searchWord);

	HouseDealDetailDto findHouseDealDetail(HouseDealSimpleDto houseDealSimpleDto);
}
