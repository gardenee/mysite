package com.javaex.service;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.javaex.dao.GalleryDao;
import com.javaex.vo.GalleryVo;

@Service
public class GalleryService {

	@Autowired
	private GalleryDao gDao;
	
	public List<GalleryVo> show() {
		List<GalleryVo> gList = gDao.selectList();
		
		return gList;
	}
	
	
	public void add(MultipartFile file, String content, int userNo) {
		String orgName = file.getOriginalFilename();
		String exName = orgName.substring(orgName.lastIndexOf("."));
		String saveName = System.currentTimeMillis() + UUID.randomUUID().toString() + exName;
		
		String saveDir = "C:\\javastudy\\upload";
		String filePath = saveDir + "\\" + saveName;
		
		long fileSize = file.getSize();
		
		GalleryVo photo = new GalleryVo(userNo, content, filePath, orgName, saveName, fileSize);
		int count = gDao.insertPhoto(photo);
		
		if (count > 0) {
			System.out.println("[" + count + "건 등록되었습니다.]");
			
			try {
				byte[] fileData = file.getBytes();
				BufferedOutputStream bs = new BufferedOutputStream(new FileOutputStream(filePath));
				
				bs.write(fileData);
				System.out.println("[저장완료]");
				bs.close();
				
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		else System.out.println("[등록되지 않았습니다.]");
	}
	
	
	public String delete(int no) {
		String result = "fail";
		
		int count = gDao.delete(no);
		if (count > 0) {
			System.out.println("[성공적으로 삭제되었습니다.]");
			result = "success";
		}
		else System.out.println("[삭제되지 않았습니다.]");
		
		return result;
	}
}
