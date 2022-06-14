package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.javaex.vo.UserVo;

public class UserDao {
	
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	private String id = "webdb";
	private String pw = "webdb";
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";

	
	public void join(UserVo uVo) {
		int count = -1;
		getConnection();
		
		try {
			String query = "insert into users values(seq_users_no.nextval, ?, ?, ?, ?) ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, uVo.getId());
			pstmt.setString(2, uVo.getPw());
			pstmt.setString(3, uVo.getName());
			pstmt.setString(4, uVo.getGender());
			
			count = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
		close();
		if (count == 1) System.out.println("[가입 완료 되었습니다]");
	}
	
	
	public UserVo getUser(UserVo uVo) {
		UserVo user = null;
		getConnection();
		
		try {
			String query = "select no, name from users where id = ? and password = ? ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, uVo.getId());
			pstmt.setString(2, uVo.getPw());
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int no = rs.getInt(1);
				String name = rs.getString(2);
				
				user = new UserVo(no, name);
			}
			
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
		close();
		if (user == null) System.out.println("[비밀번호가 일치하지 않습니다]");
		
		return user;
	}
	
	
	public UserVo getUser(int no) {
		UserVo user = null;
		getConnection();
		
		try {
			String query = "select no, id, password, name, gender from users where no = ? ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				no = rs.getInt(1);
				id = rs.getString(2);
				String pw = rs.getString(3);
				String name = rs.getString(4);
				String gender = rs.getString(5);
				
				user = new UserVo(no, id, pw, name, gender);
			}
			
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
		close();
		
		return user;
	}
	
	
	public void modify(UserVo uVo) {
		int count = -1;
		getConnection();
		
		try {
			String query = "update users set password= ?, name= ?, gender= ? where no= ? ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, uVo.getPw());
			pstmt.setString(2, uVo.getName());
			pstmt.setString(3, uVo.getGender());
			pstmt.setInt(4, uVo.getNo());
			
			count = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
		
		close();
		if (count != -1) System.out.println("[" + count + "건 수정되었습니다]");
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
