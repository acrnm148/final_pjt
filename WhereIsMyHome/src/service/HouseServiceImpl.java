package service;

import java.util.List;

import dao.HouseDao;
import dao.HouseDaoImpl;
import dto.HouseDealDetailDto;
import dto.HouseDealSimpleDto;

public class HouseServiceImpl implements HouseService{

	public static HouseServiceImpl instance = new HouseServiceImpl();
	private HouseServiceImpl() {}
	public static HouseServiceImpl getInstance() {
		return instance;
	}
	
	HouseDao houseDao = HouseDaoImpl.getInstance();
	
	@Override
	public List<HouseDealSimpleDto> findHouseDealsByDongCode(HouseDealSimpleDto houseDealDto, int limit, int offset) {
		return houseDao.findHouseDealsByDongCode(houseDealDto, limit, offset);
	}

	@Override
	public int houseDealsByDongCodeTotalCnt(HouseDealSimpleDto houseDealDto) {
		return houseDao.houseDealsByDongCodeTotalCnt(houseDealDto);
	}
	
	@Override
	public HouseDealDetailDto findHouseDealDetail(HouseDealSimpleDto houseDealSimpleDto) {
		return houseDao.findHouseDealDetail(houseDealSimpleDto);
	}
	@Override
	public List<HouseDealSimpleDto> findHouseDealsByAptName(String searchWord, int limit, int offset) {
		return houseDao.findHouseDealsByAptName(searchWord, limit, offset);
	}
	@Override
	public int houseDealsByAptNameTotalCnt(String searchWord) {
		return houseDao.houseDealsByAptNameTotalCnt(searchWord);
	}

}
