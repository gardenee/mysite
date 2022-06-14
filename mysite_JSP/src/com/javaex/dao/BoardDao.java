package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;

public class BoardDao {

	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	private String id = "webdb";
	private String pw = "webdb";
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	
	
	public List<BoardVo> showList(String search) {
		List<BoardVo> bList = new ArrayList<>();
		search = "%" + search + "%";
		getConnection();
		
		try {
			String query = "select b.no, b.title, u.name, b.user_no, b.hit, to_char(b.reg_date, 'yy-mm-dd hh:mi') ";
			query += "from board b, users u ";
			query += "where b.user_no = u.no ";
			query += "and b.title like ? ";
			query += "order by no desc ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, search);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				int no = rs.getInt(1);
				String title = rs.getString(2);
				String name = rs.getString(3);
				int userNo = rs.getInt(4);
				int hit = rs.getInt(5);
				String regDate = rs.getString(6);
				
				bList.add(new BoardVo(no, title, name, userNo, hit, regDate));
			}
		
		} catch (SQLException e) {
			System.out.println("error: " + e);
			
		}
		close();
		
		return bList;
	}
	
	
	public BoardVo readPost(int no) {
		BoardVo post = null;
		getConnection();
		
		try {
			String query = "select b.no, b.title, b.content, b.user_no, u.name, b.hit, to_char(b.reg_date, 'yy-mm-dd hh:mi') ";
			query += "from board b, users u ";
			query += "where b.user_no = u.no ";
			query += "and b.no = ?  ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				no = rs.getInt(1);
				String title = rs.getString(2);
				String content = rs.getString(3);
				int userNo = rs.getInt(4);
				String name = rs.getString(5);
				int hit = rs.getInt(6);
				String regDate = rs.getString(7);
				
				post = new BoardVo(no, title, content, hit, regDate, userNo, name);
			}
		
		} catch (SQLException e) {
			System.out.println("error: " + e);
			
		}
		close();
		
		return post;
	}

	
	public void write(BoardVo post) {
		int count = -1;
		getConnection();
		
		try {
			String query = "insert into board values(seq_board_no.nextval, ?, ?, 0, sysdate, ?) ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, post.getTitle());
			pstmt.setString(2, post.getContent());
			pstmt.setInt(3, post.getUserNo());
			
			count = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("error: " + e);
			
		}
		close();
		if (count > 0) System.out.println("[게시물 " + count + "건 등록되었습니다.]");
		else System.out.println("[게시물이 등록되지 않았습니다.]");
	}
	
	
	public void hit(int no) {
		int count = -1;
		getConnection();
		
		try {
			String query = "update board set hit = hit+1 where no = ? ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);
			
			count = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("error: " + e);
			
		}
		close();
		if (count > 0) System.out.println("조회수++");
	}
	
	
	public void delete(int no) {
		int count = -1;
		getConnection();
		
		try {
			String query = "delete from board where no = ? ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);
			
			count = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("error: " + e);
			
		}
		close();
		if (count > 0) System.out.println("[" + count + "건 삭제되었습니다.]");
		else System.out.println("[삭제되지 않았습니다.]");
	}
	
	
	public void modify(BoardVo post) {
		int count = -1;
		getConnection();
		
		try {
			String query = "update board set title= ?, content= ? where no= ? ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, post.getTitle());
			pstmt.setString(2, post.getContent());
			pstmt.setInt(3, post.getNo());
			
			count = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("error: " + e);
			
		}
		close();
		if (count > 0) System.out.println("[" + count + "건 수정되었습니다.]");
		else System.out.println("[수정된 게시글이 없습니다.]");
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
