package com.javaex.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.GuestBookDao;
import com.javaex.vo.GuestBookVo;

@Service
public class GuestBookService {

	@Autowired
	private GuestBookDao gbDao;
	
	
	public List<GuestBookVo> getList() {
		List<GuestBookVo> gbList = gbDao.selectList();
		
		return gbList; 
	}
	
	
	public void addVisit(GuestBookVo visit) {
		int count = gbDao.insertVisit(visit);
		
		if (count > 0) System.out.println("[" + count + "건 등록되었습니다.]");
		else System.out.println("[방명록이 등록되지 않았습니다.]");
	}
	
	
	public GuestBookVo add(GuestBookVo visit) {
		int count = -1;
		count = gbDao.insert(visit);

		if (count > 0) System.out.println("[" + count + "건 등록되었습니다.]");
		else System.out.println("[방명록이 등록되지 않았습니다.]");
		
		int no = visit.getNo();
		visit = gbDao.getVisit(no);
		
		return visit;
	}
		
	
	public String deleteVisit(GuestBookVo visit) {
		String result = gbDao.checkPw(visit);
		
		if (result.equals("fail")) System.out.println("[비밀번호를 확인하세요.]");
		else {
			System.out.println("[비밀번호 일치]");
			
			int count = gbDao.deleteVisit(visit);
			
			if (count > 0) System.out.println("[" + count + "건 삭제되었습니다.]");
			else System.out.println("[삭제가 완료되지 않았습니다.]");
		}
		
		return result;
	}
	
}
