package service;

import java.util.List;

import dao.RegionDao;
import dao.RegionDaoImpl;
import dto.DongCodeDto;
import dto.GugunCodeDto;

public class RegionServiceImpl implements RegionService{
	
	public static RegionServiceImpl instance = new RegionServiceImpl();
	private RegionServiceImpl() {}
	public static RegionServiceImpl getInstance() {
		return instance;
	}
	
	RegionDao dao = RegionDaoImpl.getInstance();
	
	@Override
	public List<GugunCodeDto> findGugunCodeList(String sidoName) {
		return dao.findGugunCodeList(sidoName);
	}

	@Override
	public List<DongCodeDto> findDongCodeList(String gugunName) {
		return dao.findDongCodeList(gugunName);
	}

}
