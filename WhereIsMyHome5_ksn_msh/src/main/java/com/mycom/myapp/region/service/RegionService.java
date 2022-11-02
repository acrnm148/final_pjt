package com.mycom.myapp.region.service;

import java.util.List;

import com.mycom.myapp.region.dto.DongCodeDto;
import com.mycom.myapp.region.dto.GugunCodeDto;

public interface RegionService {
	List<GugunCodeDto> findGugunCodeList(String sidoName);
	List<DongCodeDto> findDongCodeList(String gugunName);
}
