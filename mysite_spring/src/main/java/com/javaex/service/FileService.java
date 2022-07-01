package com.javaex.service;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.javaex.dao.FileDao;
import com.javaex.vo.FileVo;

@Service
public class FileService {
	
	@Autowired
	private FileDao fDao;
	
	public String save(MultipartFile file) {
		String orgName = file.getOriginalFilename();
		String exName = orgName.substring(orgName.lastIndexOf("."));
		String saveName = System.currentTimeMillis() + UUID.randomUUID().toString() + exName;
		
		String saveDir = "C:\\javastudy\\upload";
		String filePath = saveDir + "\\" + saveName;
		
		long fileSize = file.getSize();
				
		
		int count = fDao.save(new FileVo(orgName, saveName, filePath, fileSize));
		
		if (count > 0) System.out.println("[" + count + "건 저장되었습니다.]");
		else System.out.println("[저장되지 않았습니다.]");
		
		
		try {
			byte[] fileData = file.getBytes();
			BufferedOutputStream bs = new BufferedOutputStream(new FileOutputStream(filePath));
			
			bs.write(fileData);
			System.out.println("[저장완료]");
			bs.close();
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return saveName;
	}

}
