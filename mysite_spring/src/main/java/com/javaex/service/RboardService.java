package com.javaex.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.RboardDao;
import com.javaex.vo.RboardVo;

@Service
public class RboardService {

	@Autowired
	private RboardDao rDao;
	
	
	public List<RboardVo> showList(String search) {
		if (search.equals("") || search == null) System.out.println("[글 목록을 불러옵니다.]");
		else System.out.println("['" + search + "'를 검색합니다.]");
		
		List<RboardVo> bList = rDao.selectList(search);
		System.out.println("[" + bList.size() + "건 검색되었습니다.]");
		
		for (RboardVo post: bList) {
			if (post.getDepth() > 0) {
				String title = post.getTitle();
				title = "ㄴ" + title;
				for (int i = 1; i < post.getDepth(); i++) title = "&nbsp;&nbsp;&nbsp;&nbsp;" + title;
				post.setTitle(title);
			}
		}
		
		return bList;
	}
	
	
	public RboardVo readPost(RboardVo post) {
		Integer userNo = post.getUserNo();
		int postNo = post.getNo();
		int postUser = rDao.selectUser(postNo);
		
		if (userNo == null || userNo != postUser) {
			int count = rDao.hit(postNo);
			if (count > 0) System.out.println("[" + postNo + "번 글 조회수++]");
		}
		post = rDao.selectPost(postNo);
		
		return post;
	}
	
	
	public void write(RboardVo post) {
		post.setContent(post.getContent().replace("\n", "<br>"));
		int count = rDao.insertPost(post);
		
		if (count > 0) System.out.println("[" + count + "건 등록되었습니다.]");
		else System.out.println("[게시글이 등록되지 않았습니다.");
	}
	
	
	public void delete(int postNo) {
		int count = rDao.deletePost(postNo);
		
		if (count > 0) System.out.println("[" + count + "건 삭제되었습니다.]");
		else System.out.println("[게시글이 삭제되지 않았습니다.]");
	}
	
	
	public RboardVo prepareModify(int postNo) {
		RboardVo post = rDao.selectPost(postNo);
		
		return post;
	}
	
	
	public void modify(RboardVo post) {
		post.setContent(post.getContent().replace("\n", "<br>"));
		int count = rDao.updatePost(post);
		
		if (count > 0) System.out.println("[" + count + "건 수정되었습니다.]");
		else System.out.println("[게시글이 수정되지 않았습니다.]");
	}
	
	
	public RboardVo prepareReply(int postNo) {
		RboardVo post = rDao.selectNo(postNo);
		
		return post;		
	}
	
	
	public void writeReply(RboardVo post) {
		post.setContent(post.getContent().replace("\n", "<br>"));
		
		rDao.updateNo(post);
		int count = rDao.insertReply(post);
		
		if (count > 0) System.out.println("[답글이 등록되었습니다.]");
		else System.out.println("[답글이 등록되지 않았습니다.]");
	}
}
