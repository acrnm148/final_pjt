package common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


/**
 * DB Connection Pool 설정
 * */
public class DBManager {
	
	private static DBManager instance = new DBManager();
    public static DBManager getInstance() {
        return instance;
    }
	
	// Connection 객체를 얻어서 리턴
	public static Connection getConnection() {
		Connection con = null;
		try {
			// context.xml 에 접근
			Context context = (Context) new InitialContext().lookup("java:comp/env/"); // 이 설정은 약속
			// context.xml에 정의된 Resource 중, jdbc/board에 접근
			// lookup : context.xml에서 "jdbc/board"라는 이름을 찾는다는 것
			// DataSource == Connection Pool!
			DataSource dataSource = (DataSource) context.lookup("jdbc/my_house");
			con = dataSource.getConnection();			
		} catch(Exception e){
			e.printStackTrace();
		}
		
		return con;
	}
	// 다 사용하면, Connection 객체를 반납하는
	public static void releaseConnection(ResultSet rs, PreparedStatement pstmt, Connection con) {
		try {
			if(rs != null) { rs.close(); }
			if(pstmt != null) { pstmt.close(); }
			if(con != null) { con.close(); }
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void close(AutoCloseable... closeables) {
        for (AutoCloseable c : closeables) {
            if (c != null) {
                try {
                    c.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
