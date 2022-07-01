package com.javaex.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javaex.service.UserService;
import com.javaex.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService uService;
	
	
	// 로그인
	@RequestMapping(value="/loginForm", method={RequestMethod.GET, RequestMethod.POST})
	public String loginForm() {
		System.out.println("user > loginForm");
				
		return "user/loginForm";
	}
	
	
	@RequestMapping(value="/login", method={RequestMethod.GET, RequestMethod.POST})
	public String login(@ModelAttribute UserVo user, HttpSession session) {
		System.out.println("user > login");
		
		UserVo authUser = uService.login(user);
		
		if (authUser != null) {
			session.setAttribute("authUser", authUser);
			return "redirect:/main";
			
		} else return "redirect:/user/loginForm?result=fail";
	}
	
	
	//로그아웃
	@RequestMapping(value="/logout", method={RequestMethod.GET, RequestMethod.POST})
	public String logout(HttpSession session) {
		System.out.println("user > logout");
		
		session.removeAttribute("authUser");
		session.invalidate();
		
		return "redirect:/main";
	}
		

	//회원가입
	@RequestMapping(value="/joinForm", method={RequestMethod.GET, RequestMethod.POST})
	public String joinForm() {
		System.out.println("user > joinForm");
		
		return "user/joinForm";
	}
	
	
	@RequestMapping(value="/join", method={RequestMethod.GET, RequestMethod.POST})
	public String join(@ModelAttribute UserVo user) {
		System.out.println("user > join");
		
		int count = uService.join(user);
		
		if (count > 0 ) return "user/joinOk";
		else return "user/joinForm";
	}
	
	
	// 회원정보 수정
	@RequestMapping(value="/modifyForm", method={RequestMethod.GET, RequestMethod.POST})
	public String modifyForm(HttpSession session, Model model) {
		System.out.println("user > modifyForm");
		
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		if (authUser == null) return "user/loginForm";

		authUser = uService.modifyInfo(authUser);
		model.addAttribute("authUser", authUser);
		
		return "user/modifyForm";
	}
	
	
	@RequestMapping(value="/modify", method={RequestMethod.GET, RequestMethod.POST})
	public String modify(@ModelAttribute UserVo user, HttpSession session) {
		System.out.println("user > modify");

		UserVo authUser = uService.modifyUser(user);
		if (authUser != null) session.setAttribute("authUser", authUser);
		
		return "redirect:/main";
	}
	
	
	@ResponseBody
	@RequestMapping(value="/idcheck", method={RequestMethod.GET, RequestMethod.POST})
	public boolean idcheck(@RequestBody UserVo test) {
		System.out.println("api user > idcheck");
		
		boolean result = uService.idcheck(test.getId());
		
		return result;
	}
	
}
