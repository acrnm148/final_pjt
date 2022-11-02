package dao;

import java.sql.*;
import java.util.*;

import common.DBManager;
import dto.HouseDealDetailDto;
import dto.HouseDealSimpleDto;

public class HouseDaoImpl implements HouseDao{

	public static HouseDaoImpl instance = new HouseDaoImpl();
	private HouseDaoImpl() {}
	public static HouseDaoImpl getInstance() {
		return instance;
	}
	
	@Override
	public List<HouseDealSimpleDto> findHouseDealsByDongCode(HouseDealSimpleDto houseDealDto, int limit, int offset) {
		String dong = houseDealDto.getDong();
		
		List<HouseDealSimpleDto> list = new ArrayList<>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DBManager.getConnection();
			StringBuilder sb = new StringBuilder();
			
			sb.append(" select no, dong, AptName, code, dealAmount, buildYear, dealYear, dealMonth, dealDay, area, floor, jibun, house_no ") 
					.append(" from housedeal ") 
					.append(" where dong = ? ")			
					.append(" limit ? offset ? ");
			pstmt = con.prepareStatement(sb.toString());
			pstmt.setString(1, dong);
			pstmt.setInt(2, limit);
			pstmt.setInt(3, offset);
			System.out.println(pstmt.toString());
			
			rs = pstmt.executeQuery();
			while(rs.next()) {			
				HouseDealSimpleDto addDto = new HouseDealSimpleDto();
				addDto.setNo(rs.getInt("no"));
				addDto.setDong(rs.getString("dong"));
				addDto.setAptName(rs.getString("AptName"));
				addDto.setCode(rs.getString("code"));
				addDto.setDealAmount(rs.getString("dealAmount"));
				addDto.setBuildYear(rs.getString("buildYear"));
				addDto.setDealYear(rs.getString("dealYear"));
				addDto.setDealMonth(rs.getString("dealMonth"));
				addDto.setDealDay(rs.getString("dealDay"));
				addDto.setArea(rs.getString("area"));
				addDto.setFloor(rs.getString("floor"));
				addDto.setJibun(rs.getString("jibun"));
				addDto.setHouseNo(rs.getInt("house_no"));
				list.add(addDto);				
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			DBManager.releaseConnection(rs, pstmt, con);
		}
		return list;
	}
	@Override
	public int houseDealsByDongCodeTotalCnt(HouseDealSimpleDto houseDealSimpleDto) {		
		String dong = houseDealSimpleDto.getDong();

		int totalCnt = -1;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DBManager.getConnection();
			StringBuilder sb = new StringBuilder();
			sb.append(" select count(*) from housedeal ")
				.append(" where dong = ? ");
			
			pstmt = con.prepareStatement(sb.toString());
			pstmt.setString(1, dong);
			
			rs = pstmt.executeQuery(); // 영향받은 row의 개수를 return
			if(rs.next()) {
				totalCnt = rs.getInt(1);
			}			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(rs, pstmt, con);
		}
		
		return totalCnt;	
	}
	@Override
	public HouseDealDetailDto findHouseDealDetail(HouseDealSimpleDto houseDealSimpleDto) {		
		int dealId = houseDealSimpleDto.getNo();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		HouseDealDetailDto detailDto = null;
		try {
			con = DBManager.getConnection();
			
			String query = " select d.no, d.dong, dong.city_name, dong.gugun_name, d.AptName, d.code, d.dealAmount, " + 
							"		d.buildYear, d.dealYear, d.dealMonth, d.dealDay, " + 
							"		d.area, d.floor, d.jibun, d.house_no, i.lat, i.lng, i.img " + 
							" from housedeal d left join houseinfo i on d.house_no = i.no " + 
							" left join dong_code dong on d.dong = dong.name " + 
							" where d.no = ?";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, dealId);
			System.out.println(pstmt.toString());
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {			
				detailDto = new HouseDealDetailDto();
				detailDto.setNo(rs.getInt("no"));
				detailDto.setDong(rs.getString("dong"));
				detailDto.setCityName(rs.getString("city_name"));
				detailDto.setGugunName(rs.getString("gugun_name"));
				detailDto.setAptName(rs.getString("AptName"));
				detailDto.setCode(rs.getString("code"));
				detailDto.setDealAmount(rs.getString("dealAmount"));
				detailDto.setBuildYear(rs.getString("buildYear"));
				detailDto.setDealYear(rs.getString("dealYear"));
				detailDto.setDealMonth(rs.getString("dealMonth"));
				detailDto.setDealDay(rs.getString("dealDay"));
				detailDto.setArea(rs.getString("area"));
				detailDto.setFloor(rs.getString("floor"));
				detailDto.setJibun(rs.getString("jibun"));
				detailDto.setHouseNo(rs.getInt("house_no"));
				detailDto.setLat(rs.getString("lat"));
				detailDto.setLng(rs.getString("lng"));
				detailDto.setImg(rs.getString("img"));
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			DBManager.releaseConnection(rs, pstmt, con);
		}
		return detailDto;	
	}

	@Override
	public List<HouseDealSimpleDto> findHouseDealsByAptName(String searchWord, int limit, int offset) {
		List<HouseDealSimpleDto> list = new ArrayList<>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DBManager.getConnection();
			
			String query = " select no, dong, AptName, code, dealAmount, buildYear, dealYear, dealMonth, dealDay, area, floor, jibun, house_no " + 
					" from housedeal " + 
					" where AptName like ? " + 
					" limit ? offset ? ";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, "%" + searchWord + "%");
			pstmt.setInt(2, limit);
			pstmt.setInt(3, offset);
			System.out.println(pstmt.toString());
			
			rs = pstmt.executeQuery();
			while(rs.next()) {			
				HouseDealSimpleDto addDto = new HouseDealSimpleDto();
				addDto.setNo(rs.getInt("no"));
				addDto.setDong(rs.getString("dong"));
				addDto.setAptName(rs.getString("AptName"));
				addDto.setCode(rs.getString("code"));
				addDto.setDealAmount(rs.getString("dealAmount"));
				addDto.setBuildYear(rs.getString("buildYear"));
				addDto.setDealYear(rs.getString("dealYear"));
				addDto.setDealMonth(rs.getString("dealMonth"));
				addDto.setDealDay(rs.getString("dealDay"));
				addDto.setArea(rs.getString("area"));
				addDto.setFloor(rs.getString("floor"));
				addDto.setJibun(rs.getString("jibun"));
				addDto.setHouseNo(rs.getInt("house_no"));
				list.add(addDto);				
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			DBManager.releaseConnection(rs, pstmt, con);
		}
		return list;
	}
	@Override
	public int houseDealsByAptNameTotalCnt(String searchWord) {		
		int totalCnt = -1;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DBManager.getConnection();
			StringBuilder sb = new StringBuilder();
			sb.append(" select count(*) from housedeal ")
				.append(" where AptName like ? ");
			
			pstmt = con.prepareStatement(sb.toString());
			pstmt.setString(1, "%" + searchWord + "%");
			
			rs = pstmt.executeQuery(); // 영향받은 row의 개수를 return
			if(rs.next()) {
				totalCnt = rs.getInt(1);
			}			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(rs, pstmt, con);
		}
		
		return totalCnt;	
	}

}
