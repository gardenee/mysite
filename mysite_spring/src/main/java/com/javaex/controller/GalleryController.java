package com.javaex.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.javaex.service.GalleryService;
import com.javaex.vo.GalleryVo;
import com.javaex.vo.UserVo;

@RequestMapping("/gallery")
@Controller
public class GalleryController {

	@Autowired
	private GalleryService gService;
	
	
	@RequestMapping(value="/list", method={RequestMethod.GET, RequestMethod.POST})
	public String list(Model model) {
		System.out.println("gallery > list");
		
		List<GalleryVo> gList = gService.show();
		model.addAttribute("gList", gList);
		
		return "gallery/list";
	}
	
	
	@RequestMapping(value="/add", method={RequestMethod.GET, RequestMethod.POST})
	public String add(@RequestParam("file") MultipartFile file, @RequestParam("content") String content, HttpSession session) {
		System.out.println("gallery > add");
		
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		if (authUser != null) {
			gService.add(file, content, authUser.getNo());
		}
		
		return "redirect:/gallery/list";
	}
	
	
	@ResponseBody
	@RequestMapping(value="/delete", method={RequestMethod.GET, RequestMethod.POST})
	public String delete(@RequestBody GalleryVo info, HttpSession session) {
		System.out.println("gallery > delete");
		String result = "fail";
		
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if (authUser != null) {
			result = gService.delete(info.getNo());
		}
		
		return result;
	}
}
