package com.javaex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.javaex.service.GuestBookService;
import com.javaex.vo.GuestBookVo;

@Controller
@RequestMapping("/guestbook")
public class GuestBookController {
	
	@Autowired
	private GuestBookService gbService;
	
	
	// 방명록 조회
	@RequestMapping(value="/list", method={RequestMethod.GET, RequestMethod.POST})
	public String getList(Model model) {
		System.out.println("guestbook > list");
		
		List<GuestBookVo> gbList = gbService.getList();
		model.addAttribute("gbList", gbList);
		
		return "guestbook/addList";
	}
	
	
	// 방명록 추가
	@RequestMapping(value="/add", method={RequestMethod.GET, RequestMethod.POST})
	public String addVisit(@ModelAttribute GuestBookVo visit) {
		System.out.println("guestbook > add");
		
		gbService.addVisit(visit);
		
		return "redirect:/guestbook/list";
	}
	
	
	// 삭제
	@RequestMapping(value="/deleteForm/{no}", method={RequestMethod.GET, RequestMethod.POST})
	public String deleteForm(@PathVariable("no") int no, Model model) {
		System.out.println("guestbook > deleteForm");
		
		model.addAttribute("no", no);
				
		return "/guestbook/deleteForm";
	}
	
	
	@RequestMapping(value="/delete", method={RequestMethod.GET, RequestMethod.POST})
	public String delete(@ModelAttribute GuestBookVo visit, Model model) {
		System.out.println("guestbook > delete");
		
		String result = gbService.deleteVisit(visit);
		
		if (result.equals("success")) return "redirect:/guestbook/list";
		
		else {
			model.addAttribute("no", visit.getNo());
			
			return "redirect:/guestbook/deleteForm/{no}?result=fail";
		}
	}
	
}
