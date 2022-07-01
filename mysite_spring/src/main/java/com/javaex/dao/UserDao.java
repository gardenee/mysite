package com.javaex.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.UserVo;

@Repository
public class UserDao {
	
	@Autowired  
	private SqlSession sqlSession;
	
	
	// 회원가입
	public int insertUser(UserVo user) {
		int count = -1;
		count = sqlSession.insert("user.insertUser", user);
		
		return count;
	}
	
	
	// id & pw 확인 > 세션용 authUser 불러오기
	public UserVo getUser(UserVo user) {
		UserVo authUser = null;
		authUser = sqlSession.selectOne("user.getUser", user);
		
		return authUser;
	}
		
	
	// 정보 다 있는 authUser 불러오기(수정용)
	public UserVo userInfo(UserVo authUser) {
		authUser = sqlSession.selectOne("user.userInfo", authUser);
		
		return authUser;
	}
	
	
	// 회원 정보 업데이트
	public int userUpdate(UserVo user) {
		int count = -1;
		count = sqlSession.update("user.userUpdate", user);
			
		return count;
	}
	
	
	// 아이디 중복 체크
	public int idcheck(String id) {
		int count = 0;
		count = Integer.parseInt(sqlSession.selectOne("user.idcheck", id));
		
		return count;
	}

}
