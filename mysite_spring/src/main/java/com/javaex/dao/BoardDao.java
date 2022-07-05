package com.javaex.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.BoardVo;

@Repository
public class BoardDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	
	// 리스트, 검색
	public List<BoardVo> list(HashMap<String, Object> map) {
		List<BoardVo> bList = sqlSession.selectList("board.list", map);

		return bList;
	}
	
	// 글 개수 불러오기
	public int cnt(String search) {
		int cnt = sqlSession.selectOne("board.cnt", search);
		
		return cnt;
	}
	
	// 글 읽기, 수정하기 > 불러오기
	public BoardVo selectPost(int postNo) {
		BoardVo post = sqlSession.selectOne("board.selectPost", postNo);
				
		return post;
	}
	
	
	// 글 작성자 불러오기
	public int selectUser(int postNo) {
		int userNo = sqlSession.selectOne("board.selectUser", postNo);
		
		return userNo;
	}
	
	
	// 조회수
	public int hit(int postNo) {
		int count = -1;
		count = sqlSession.update("board.hit", postNo);
		
		return count;
	}
	
	
	// 글 작성
	public int insertPost(BoardVo post) {
		int count = -1;
		count = sqlSession.insert("board.insertPost", post);
		
		return count;
	}
	
	
	// 글 수정
	public int updatePost(BoardVo post) {
		int count = -1;
		count = sqlSession.update("board.updatePost", post);
		
		return count;
	}
	
	
	// 글 삭제
	public int deletePost(int no) {
		int count = -1;
		count = sqlSession.delete("board.deletePost", no);
		
		return count;
	}

}
