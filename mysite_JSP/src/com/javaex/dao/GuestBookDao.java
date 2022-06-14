package com.javaex.dao;

import java.sql.Connection;
import com.javaex.vo.GuestBookVo;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GuestBookDao {
	
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	private String id = "webdb";
	private String pw = "webdb";
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";

	
	public void add(GuestBookVo gvo) {
		int count = -1;
		getConnection();
		
		try {
			String query = "insert into guestbook values(seq_guestbook_no.nextval, ?, ?, ?, sysdate) ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, gvo.getName());
			pstmt.setString(2, gvo.getPassword());
			pstmt.setString(3, gvo.getContent());

			count = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
		
		close();		
		if (count > 0) System.out.println("[" + count + "건 삭제되었습니다.]");
	}
	
	
	public void delete(int no, String pw) {
		int count = -1;
		getConnection();
		
		try {
			String query = "delete from guestbook where no = ? and password = ? ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);
			pstmt.setString(2, pw);
			
			count = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("error: " + e);
			
		}
		close();
		if (count > 0) System.out.println("[" + count + "건 삭제되었습니다.]");
		else System.out.println("[삭제되지 않았습니다.]");
	}
	
	
	public List<GuestBookVo> select() {
		List<GuestBookVo> gList = new ArrayList<>();
		getConnection();
		
		try {
			String query = "select no, name, reg_date, content from guestbook order by no desc ";
			
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				int no = rs.getInt(1);
				String name = rs.getString(2);
				String reg_date = rs.getString(3);
				String content = rs.getString(4);
				
				gList.add(new GuestBookVo(no, name, content, reg_date));
			}
			
		} catch(SQLException e) {
			System.out.println("error: " + e);
			
		}
		close();
		
		return gList;
	}
	
	
	public String findPW(int no) {
		String pw = "";
		getConnection();
		
		try {
			String query = "select password from guestbook where no = ? ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				pw = rs.getString(1);
				break;
			}
			
		} catch(SQLException e) {
			System.out.println("error: " + e);
			
		}
		close();
		
		return pw;
	}
	
	
	private void getConnection() {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
			
		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}
	
	
	private void close() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}
}
