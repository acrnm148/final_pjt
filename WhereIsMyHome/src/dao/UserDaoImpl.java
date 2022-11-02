package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import common.DBManager;
import dto.UserDto;

public class UserDaoImpl implements UserDao{

	private static UserDaoImpl instance = new UserDaoImpl();
	
	private UserDaoImpl() {}
	
	public static UserDaoImpl getInstance() {
		return instance;
	}
	
	
	
	@Override
	public int userRegister(UserDto userDto) {
		Connection con = null;
		PreparedStatement pstmt= null;
		ResultSet rs = null;
		
		int ret=-1;
		try {
			con=DBManager.getConnection();
			StringBuilder sb = new StringBuilder();
			sb.append("insert into users ")
			.append("(user_name, user_password, user_email, user_profile_image_url, user_register_date, user_clsf)")
			.append(" values(?, ?, ?, '', now(), ? ) ");
			
			pstmt=con.prepareStatement(sb.toString());
			pstmt.setString(1, userDto.getUser_name());
			pstmt.setString(2, userDto.getUser_password());
			pstmt.setString(3, userDto.getUser_email());
			pstmt.setString(4, userDto.getUser_clsf());
			
			ret=pstmt.executeUpdate(); //영향받은  row수 리턴
			System.out.println(userDto.getUser_name()+" 유저 db 등록 완료");
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.releaseConnection(rs, pstmt, con);
		}
		
		return ret;
	}

	@Override
	public int userDupCheck(UserDto userDto) {
		
		Connection con = null;
		PreparedStatement pstmt= null;
		ResultSet rs = null;
		
		
		
		int ret=-1;
		try {
			con=DBManager.getConnection();
			StringBuilder sb = new StringBuilder();
			sb.append("select count(*)  count from users ")
			.append(" where user_email = ? ");
			
			pstmt=con.prepareStatement(sb.toString());
			pstmt.setString(1, userDto.getUser_email());
			
			
			rs = pstmt.executeQuery(); //영향받은  row수 리턴
			if (rs.next()) {
				ret = rs.getInt("count");
				System.out.println("조회 결과 나온 count "+ret);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.releaseConnection(rs, pstmt, con);
		}
		
		return ret;
	}

	@Override
	public UserDto userLogin(String email, String password) {
		Connection con = null;
		PreparedStatement pstmt= null;
		ResultSet rs = null;
		
		
		
		UserDto ret=null;
		try {
			con=DBManager.getConnection();
			StringBuilder sb = new StringBuilder();
			sb.append("select * from users ")
			.append(" where user_email = ? and user_password = ? ");
			
			pstmt=con.prepareStatement(sb.toString());
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			
			
			rs = pstmt.executeQuery(); //영향받은  row수 리턴
			if (rs.next()) {
				ret= new UserDto();
				 ret.setUser_seq(rs.getInt("user_seq"));
				 ret.setUser_name(rs.getString("user_name"));
				 ret.setUser_password(rs.getString("user_password"));
				 ret.setUser_email(rs.getString("user_email"));
				 ret.setUser_profile_image_url(rs.getString("USER_PROFILE_IMAGE_URL"));
				 ret.setUser_register_date(rs.getDate("user_register_date"));
				 ret.setUser_clsf(rs.getString("user_clsf"));
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.releaseConnection(rs, pstmt, con);
		}
		
		return ret;
	}

	@Override
	public int userChange(String email, String password, String name) {
		Connection con = null;
		PreparedStatement pstmt= null;
		ResultSet rs = null;
		int ret=-1;
		try {
			con=DBManager.getConnection();
			StringBuilder sb = new StringBuilder();
			sb.append("update users set user_password = ? , user_name = ?  ")
			.append(" where user_email = ? ");
			
			pstmt=con.prepareStatement(sb.toString());
			pstmt.setString(1, password);
			pstmt.setString(2, name);
			pstmt.setString(3, email);
			
			
			ret = pstmt.executeUpdate(); //영향받은  row수 리턴
			
			System.out.println("수정 결과 나온 count "+ret);
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.releaseConnection(rs, pstmt, con);
		}
		
		return ret;
	}

	@Override
	public int userUnregist(String email, String password) {
		Connection con = null;
		PreparedStatement pstmt= null;
		ResultSet rs = null;
		int ret=-1;
		try {
			con=DBManager.getConnection();
			StringBuilder sb = new StringBuilder();
			sb.append("delete from users where ")
			.append(" user_password = ? and user_email = ?  ");
			
			pstmt=con.prepareStatement(sb.toString());
			pstmt.setString(1, password);
			pstmt.setString(2, email);
			
			
			ret = pstmt.executeUpdate(); //영향받은  row수 리턴
			
			System.out.println("삭제 결과 나온 count "+ret);
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.releaseConnection(rs, pstmt, con);
		}
		
		return ret;
	}

}
