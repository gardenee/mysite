package com.javaex.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.GalleryVo;

@Repository
public class GalleryDao {

	@Autowired
	private SqlSession sqlSession;
	
	public List<GalleryVo> selectList() {
		List<GalleryVo> gList = sqlSession.selectList("gallery.selectList");
		
		return gList;
	}
	
	
	public int insertPhoto(GalleryVo photo) {
		int count = -1;
		
		count = sqlSession.insert("gallery.insertPhoto", photo);
		
		return count;
	}
	
	
	public int delete(int no) {
		int count = -1;
		
		count = sqlSession.delete("gallery.delete", no);
		
		return count;
	}
}
