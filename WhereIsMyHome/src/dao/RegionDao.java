package dao;

import java.util.List;

import dto.DongCodeDto;
import dto.GugunCodeDto;

public interface RegionDao {
	List<GugunCodeDto> findGugunCodeList(String cityName);
	List<DongCodeDto> findDongCodeList(String gugunName);
}
