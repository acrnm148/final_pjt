package service;

import java.util.List;

import dto.DongCodeDto;
import dto.GugunCodeDto;

public interface RegionService {
	List<GugunCodeDto> findGugunCodeList(String sidoName);
	List<DongCodeDto> findDongCodeList(String gugunName);
}
