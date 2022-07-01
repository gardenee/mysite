package com.javaex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.javaex.service.FileService;

@RequestMapping("/fileupload")
@Controller
public class FileController {
	
	@Autowired
	private FileService fService;

	@RequestMapping(value="/form", method={RequestMethod.GET, RequestMethod.POST})
	public String form() {
		System.out.println("fileupload > form");
				
		return "fileupload/form";
	}
	
	
	@RequestMapping(value="/upload", method={RequestMethod.GET, RequestMethod.POST})
	public String upload(@RequestParam("file") MultipartFile file, Model model) {
		System.out.println("fileupload > upload");
		
		String saveName = fService.save(file);
		model.addAttribute("saveName", saveName);
		
		return "fileupload/result";
	}
	
}
