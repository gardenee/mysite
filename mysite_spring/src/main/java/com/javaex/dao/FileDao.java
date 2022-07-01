package com.javaex.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.FileVo;

@Repository
public class FileDao {

	@Autowired
	private SqlSession sqlSession;
	
	public int save(FileVo file) {
		int count = -1;
		count = sqlSession.insert("fileupload.save", file);
		
		return count;
	}
}
