package com.javaex.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.RboardVo;

@Repository
public class RboardDao {

	@Autowired
	private SqlSession sqlSession;
	
	
	public List<RboardVo> selectList(String search) {
		List<RboardVo> bList = sqlSession.selectList("rboard.selectList", search);
		
		return bList;
	}
	
	
	public RboardVo selectPost(int postNo) {
		RboardVo post = sqlSession.selectOne("rboard.selectPost", postNo);
		
		return post;
	}
	
	
	public int hit(int postNo) {
		int count = -1;
		count = sqlSession.update("rboard.hit", postNo);
		
		return count;
	}
	
	
	public int selectUser(int postNo) {
		int userNo = sqlSession.selectOne("rboard.selectUser", postNo);
		
		return userNo;
	}
	
	
	public int insertPost(RboardVo post) {
		int count = -1;
		count = sqlSession.insert("rboard.insertPost", post);
		
		return count;
	}
	
	
	public int deletePost(int postNo) {
		int count = -1;
		count = sqlSession.delete("rboard.deletePost", postNo);
		
		return count;
	}
	
	
	public int updatePost(RboardVo post) {
		int count = -1;
		count = sqlSession.update("rboard.updatePost", post);
		
		return count;
	}
	
	
	public int insertReply(RboardVo post) {
		int count = -1;
		count = sqlSession.insert("rboard.insertReply", post);
		
		return count;
	}
	
	
	public int updateNo(RboardVo post) {
		int count = -1;
		count = sqlSession.update("rboard.updateNo", post);
		
		return count;
	}
	
	public RboardVo selectNo(int postNo) {
		RboardVo post = sqlSession.selectOne("rboard.selectNo", postNo);
		
		return post;
	}
}
