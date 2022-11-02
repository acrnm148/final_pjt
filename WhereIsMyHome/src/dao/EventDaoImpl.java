package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import common.DBManager;
import dto.EventDto;


public class EventDaoImpl implements EventDao {
	
	
	private static EventDaoImpl instance = new EventDaoImpl();
	
	private EventDaoImpl() {}
	
	public static EventDaoImpl getInstance() {
		return instance;
	}
	
	

	@Override
	public int eventRegister(EventDto eventDto) {
		Connection con = null;
		PreparedStatement pstmt= null;
		ResultSet rs = null;
		
		int ret=-1;
		try {
			con=DBManager.getConnection();
			StringBuilder sb = new StringBuilder();
			sb.append("insert into event ")
			.append("(name, author_seq, createdAt, use_yn, url, img)")
			.append(" values(?, ?, now(), ?, ?, ? ) ");
			
			pstmt=con.prepareStatement(sb.toString());
			pstmt.setString(1, eventDto.getName());
			pstmt.setInt(2, eventDto.getAuthor_seq());
			pstmt.setString(3, eventDto.getUse_yn());
			pstmt.setString(4, eventDto.getUrl());
			pstmt.setString(5, eventDto.getImg());
			
			ret=pstmt.executeUpdate(); //영향받은  row수 리턴
			System.out.println(eventDto.getName()+" 이벤트 db 등록 완료");
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.releaseConnection(rs, pstmt, con);
		}
		
		return ret;
	}

	@Override
	public List<EventDto> getEvent() {
		List<EventDto> list = new ArrayList<>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DBManager.getConnection();
			StringBuilder sb = new StringBuilder();
			
			sb.append(" select * ") 
				.append(" from event ");

			pstmt = con.prepareStatement(sb.toString());

			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				if(rs.getString("use_yn").equals("N")) continue;
				EventDto addDto = new EventDto();
				addDto.setEvent_seq(rs.getInt("event_seq"));
				addDto.setName(rs.getString("name"));
				addDto.setFromTime(rs.getDate("fromTime"));
				addDto.setEndTime(rs.getDate("endTime"));
				addDto.setAuthor_seq(rs.getInt("author_seq"));
				addDto.setCreatedAt(rs.getDate("createdAt"));
				addDto.setUse_yn(rs.getString("use_yn"));
				addDto.setUrl(rs.getString("url"));
				addDto.setImg(rs.getString("img"));
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
	public int eventDelete(int eventSeq) {
		Connection con = null;
		PreparedStatement pstmt= null;
		ResultSet rs = null;
		int ret=-1;
		try {
			con=DBManager.getConnection();
			StringBuilder sb = new StringBuilder();
			sb.append("delete from event where ")
			.append(" event_seq = ?  ");
			
			pstmt=con.prepareStatement(sb.toString());
			pstmt.setInt(1, eventSeq);
			
			
			ret = pstmt.executeUpdate(); //영향받은  row수 리턴
			
			System.out.println("이벤트 삭제 결과 나온 count "+ret);
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.releaseConnection(rs, pstmt, con);
		}
		
		return ret;
	}

}
