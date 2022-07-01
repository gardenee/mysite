package com.javaex.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.BoardDao;
import com.javaex.vo.BoardVo;

@Service
public class BoardService {
	
	@Autowired
	private BoardDao bDao;
	

	public List<BoardVo> list(String search){
		if (search.equals("") || search == null) System.out.println("[글 목록을 불러옵니다.]");
		else System.out.println("['" + search + "'를 검색합니다.]");
		
		List<BoardVo> bList = bDao.list(search);

		System.out.println("[" + bList.size() + "건 검색되었습니다.]");
		
		return bList;
	}
	
	
	public BoardVo readPost(BoardVo post) {
		Integer userNo = post.getUserNo();
		int postNo = post.getNo();
		int postUser = bDao.selectUser(postNo);
		
		if (userNo == null || userNo != postUser) {
			int count = bDao.hit(postNo);
			if (count > 0) System.out.println("[" + postNo + "번 글 조회수++]");
		}
		post = bDao.selectPost(postNo);
		
		return post;
	}
		
	
	public BoardVo prepareModify(int postNo) {
		BoardVo post = bDao.selectPost(postNo);
		
		return post;
	}

	
	public void write(BoardVo post) {
		post.setContent(post.getContent().replace("\n", "<br>"));
		int count = bDao.insertPost(post);
		
		if (count > 0) System.out.println("[" + count + "건 등록되었습니다.]");
		else System.out.println("[게시글이 등록되지 않았습니다.]");
	}
	
	
	public void modify(BoardVo post) {
		post.setContent(post.getContent().replace("\n", "<br>"));
		int count = bDao.updatePost(post);
		
		if (count > 0) System.out.println("[" + count + "건 수정되었습니다.]");
		else System.out.println("[게시글이 수정되지 않았습니다.]");
	}
	
	
	public void delete(int no) {
		int count = bDao.deletePost(no);
		
		if (count > 0) System.out.println("[" + count + "건 삭제되었습니다.]");
		else System.out.println("[게시글이 삭제되지 않았습니다.]");
	}
	
}
