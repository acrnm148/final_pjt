package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import common.DBManager;
import dto.DongCodeDto;
import dto.GugunCodeDto;

public class RegionDaoImpl implements RegionDao{
	
	public static RegionDaoImpl instance = new RegionDaoImpl();
	private RegionDaoImpl() {}
	public static RegionDaoImpl getInstance() {
		return instance;
	}

	@Override
	public List<GugunCodeDto> findGugunCodeList(String cityName) {
		List<GugunCodeDto> list = new ArrayList<>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = DBManager.getConnection();			
			
			String query = " select g.name " + 
					" from gugun_code g, sido_code s " + 
					" where g.sido_code = s.code " + 
					" and s.name = ? ";			
			pstmt = con.prepareStatement(query);		
			pstmt.setString(1, cityName);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				GugunCodeDto gugunDto = new GugunCodeDto();
				gugunDto.setName(rs.getString("name"));
				list.add(gugunDto);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally{
			DBManager.releaseConnection(rs, pstmt, con);
		}
		return list;
	}

	@Override
	public List<DongCodeDto> findDongCodeList(String gugunName) {
		List<DongCodeDto> list = new ArrayList<>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = DBManager.getConnection();			
			
			String query = " select name " + 
							" from dong_code " + 
							" where gugun_name = ? ";			
			pstmt = con.prepareStatement(query);		
			pstmt.setString(1, gugunName);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				DongCodeDto dongDto = new DongCodeDto();
				dongDto.setName(rs.getString("name"));
				list.add(dongDto);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally{
			DBManager.releaseConnection(rs, pstmt, con);
		}
		return list;
	}

}
