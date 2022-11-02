package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import common.DBManager;
import dto.EventUserDto;
import dto.UserDto;


public class EventUserDaoImpl implements EventUserDao{

	private final DBManager util = DBManager.getInstance();

    private static EventUserDaoImpl instance = new EventUserDaoImpl();

    private EventUserDaoImpl() {}

    public static EventUserDaoImpl getInstance() {
        return instance;
    }

	@Override
	public int insert(int event_seq, int user_seq) throws SQLException {
        String sql = "insert into event_users(event_seq, user_seq) values(?, ?)";

        Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

        int result = -1;
        try {
            con = DBManager.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, event_seq);
            pstmt.setInt(2, user_seq);
            result = pstmt.executeUpdate();
        }
        finally {
        	DBManager.releaseConnection(rs, pstmt, con);
        }
        return result;
	}

	/*
	 * 사용자와 이벤트 join
	 * */
	@Override
	public List<EventUserDto> select() throws SQLException {
        String sql = " select eu.event_user_seq event_user_seq, u.user_seq user_seq, u.user_name user_name, u.user_password user_password, u.user_email user_email, u.user_profile_image_url user_profile_image_url, u.user_register_date user_register_date, u.user_clsf user_clsf,  " + 
				" e.event_seq event_seq, e.name name " + 
        		" from event_users eu, event e, users u " + 
        		" where eu.event_seq = e.event_seq and eu.user_seq = u.user_seq " + 
        		" order by eu.event_user_seq DESC ";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rset = null;

        List<EventUserDto> result = new ArrayList<>();
        try {
            con = DBManager.getConnection();
            pstmt = con.prepareStatement(sql);
            rset = pstmt.executeQuery();
            while (rset.next()) {
            	EventUserDto eventUserDto = new EventUserDto(rset.getInt("user_seq"),
                        rset.getString("user_name"),
                        rset.getString("user_password"),
                        rset.getString("user_email"),
                        rset.getString("user_profile_image_url"),
                        rset.getDate("user_register_date"),
                        rset.getString("user_clsf"),
                        rset.getInt("event_seq"),
                        rset.getString("name"),
                        rset.getInt("event_user_seq"));
                result.add(eventUserDto);
            }
            System.out.println(result.toString());
        }
        finally {
        	DBManager.releaseConnection(rset, pstmt, con);
        }
        return result;
	}

	@Override
	public EventUserDto detail(int eventUserSeq) throws SQLException {
		EventUserDto eventUserDto = null;
		String sql = " select eu.event_user_seq event_user_seq, u.user_seq user_seq, u.user_name user_name, u.user_password user_password, u.user_email user_email, u.user_profile_image_url user_profile_image_url, u.user_register_date user_register_date, u.user_clsf user_clsf,  " + 
				" e.event_seq event_seq, e.name name " + 
				" from event_users eu, event e, users u " + 
				" where eu.event_seq = e.event_seq and eu.user_seq = u.user_seq " + 
				" and eu.event_user_seq = ? " + 
				" order by eu.event_user_seq DESC ";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rset = null;

        try {
            con = DBManager.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, eventUserSeq);
            rset = pstmt.executeQuery();

            if (rset.next()) {

            	eventUserDto = new EventUserDto(rset.getInt("user_seq"),
                        rset.getString("user_name"),
                        rset.getString("user_password"),
                        rset.getString("user_email"),
                        rset.getString("user_profile_image_url"),
                        rset.getDate("user_register_date"),
                        rset.getString("user_clsf"),
                        rset.getInt("event_seq"),
                        rset.getString("name"),
                        rset.getInt("event_user_seq"));
            }
        }
        finally {
        	DBManager.releaseConnection(rset, pstmt, con);
        }
        return eventUserDto;
	}

	@Override
	public int delete(int eventUserSeq) throws SQLException {
		String sql = "delete from event_users where event_user_seq = ? ";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

        int result = -1;
        try {
            con = DBManager.getConnection();
            pstmt = con.prepareStatement(sql);           
            pstmt.setInt(1, eventUserSeq);
            result = pstmt.executeUpdate();
        }
        finally {
        	DBManager.releaseConnection(rs, pstmt, con);
        }
        return result;
	}

	@Override
	public int modify(int eventUserSeq) throws SQLException {
		return 0;
	}
	

}
