package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import common.DBManager;
import dto.CodeDto;

public class CodeDaoImpl implements CodeDao{
	
	public static CodeDaoImpl instance = new CodeDaoImpl();
	private CodeDaoImpl() {}
	public static CodeDaoImpl getInstance() {
		return instance;
	}
	
	@Override
	public List<CodeDto> getCodeList() {
		List<CodeDto> list = new ArrayList<>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DBManager.getConnection();
			StringBuilder sb = new StringBuilder();
			
			sb.append(" select code, code_name, use_yn ") 
					.append(" from code ")
					.append(" where group_code = ? ");
			pstmt = con.prepareStatement(sb.toString());
			pstmt.setString(1, "001");
			System.out.println(pstmt.toString());
			
			rs = pstmt.executeQuery();
			while(rs.next()) {			
				CodeDto addDto = new CodeDto();
				addDto.setCode(rs.getString("code"));
				addDto.setCode_name(rs.getString("code_name"));
				addDto.setUse_yn(rs.getString("use_yn"));
				System.out.println(rs.getString("code_name"));
				
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

}
