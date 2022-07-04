package com.javaex.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.service.RboardService;
import com.javaex.vo.RboardVo;
import com.javaex.vo.UserVo;

@RequestMapping("/rboard")
@Controller
public class RboardController {
	
	@Autowired
	private RboardService rService;
	
	
	@RequestMapping(value="/list", method={RequestMethod.GET, RequestMethod.POST})
	public String list(@RequestParam(value="search", defaultValue="") String search, Model model) {
		System.out.println("rboard > list");
		
		List<RboardVo> bList = rService.showList(search);
		model.addAttribute("bList", bList);
		
		return "/rboard/list";
	}
	
	
	@RequestMapping(value="/read/{postNo}", method={RequestMethod.GET, RequestMethod.POST})
	public String read(@PathVariable("postNo") int postNo, HttpSession session, Model model) {
		System.out.println("rboard > read");
		
		RboardVo post = new RboardVo();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		post.setNo(postNo);
		if (authUser != null) {
			int userNo = authUser.getNo();
			post.setUserNo(userNo);
		}
		
		post = rService.readPost(post);
		model.addAttribute("post", post);
		
		return "/rboard/read";
	}

	
	@RequestMapping(value="/writeForm", method={RequestMethod.GET, RequestMethod.POST})
	public String writeForm(HttpSession session) {
		System.out.println("rboard > writeForm");
		
		if (session.getAttribute("authUser") == null) return "user/loginForm";
		
		return "rboard/writeForm";
	}
	
	
	@RequestMapping(value="/write", method={RequestMethod.GET, RequestMethod.POST})
	public String write(@ModelAttribute("post") RboardVo post) {
		System.out.println("rboard > write");
		
		rService.write(post);
		
		return "redirect:/rboard/list";
	}
	
	
	@RequestMapping(value="/delete/{no}", method={RequestMethod.GET, RequestMethod.POST})
	public String delete(@PathVariable("no") int postNo) {
		System.out.println("rboard > delete");
		
		rService.delete(postNo);
		
		return "redirect:/rboard/list";
	}
	
	
	@RequestMapping(value="/modifyForm", method={RequestMethod.GET, RequestMethod.POST})
	public String modifyForm(@RequestParam("no") int postNo, Model model, HttpSession session) {
		System.out.println("rboard > modifyForm");
		
		if (session.getAttribute("authUser") == null) return "rboard/list";
		
		RboardVo post = rService.prepareModify(postNo);
		post.setContent(post.getContent().replace("<br>", "\n"));
		model.addAttribute("post", post);
		
		return "rboard/modifyForm";
	}
	
	
	@RequestMapping(value="/modify", method={RequestMethod.GET, RequestMethod.POST})
	public String modify(@ModelAttribute("post") RboardVo post) {
		System.out.println("rboard > modify");
		
		rService.modify(post);
		
		return "redirect:/rboard/read/" + post.getNo();
	}
	
	
	@RequestMapping(value="/replyForm/{postNo}", method={RequestMethod.GET, RequestMethod.POST})
	public String replyForm(@PathVariable("postNo") int postNo, Model model, HttpSession session) {
		System.out.println("rboard > replyForm");
		
		if (session.getAttribute("authUser") == null) return "user/loginForm";
		
		RboardVo post = rService.prepareReply(postNo);
		model.addAttribute("post", post);
		
		return "/rboard/replyForm";
	}
	
	
	@RequestMapping(value="/writeReply", method={RequestMethod.GET, RequestMethod.POST})
	public String writeReply(@ModelAttribute("post") RboardVo post) {
		System.out.println("rboard > writeReply");
		
		System.out.println(post.toString());
		rService.writeReply(post);
		
		return "redirect:/rboard/list";
	}
 }
