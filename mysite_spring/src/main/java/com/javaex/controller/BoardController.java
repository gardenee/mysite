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

import com.javaex.service.BoardService;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService bService;
	
	
	// 목록
	@RequestMapping(value="/list", method={RequestMethod.GET, RequestMethod.POST})
	public String list(@RequestParam(value="search", defaultValue="") String search, Model model) {
		System.out.println("board > list");

		List<BoardVo> bList = bService.list(search);
		model.addAttribute("bList", bList);
		
		return "/board/list";
	}
	

	// 게시글 읽기
	@RequestMapping(value="/read/{postNo}", method={RequestMethod.GET, RequestMethod.POST})
	public String read(@PathVariable("postNo") int postNo, HttpSession session, Model model) {
		System.out.println("board > read");
		
		BoardVo post = new BoardVo();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		post.setNo(postNo);
		if (authUser != null) {
			int userNo = authUser.getNo();
			post.setUserNo(userNo);
		}
		
		post = bService.readPost(post);
		model.addAttribute("post", post);
		
		return "/board/read";
	}
	
	
	// 게시글 작성
	@RequestMapping(value="/writeForm", method={RequestMethod.GET, RequestMethod.POST})
	public String writeForm(HttpSession session) {
		System.out.println("board > writeForm");
		
		if (session.getAttribute("authUser") == null) return "user/loginForm";
		
		return "board/writeForm";
	}
	
	
	@RequestMapping(value="/write", method={RequestMethod.GET, RequestMethod.POST})
	public String write(@ModelAttribute("post") BoardVo post) {
		System.out.println("board > write");
		
		bService.write(post);
		
		return "redirect:/board/list";
	}
	
	
	// 게시글 수정
	@RequestMapping(value="/modifyForm", method={RequestMethod.GET, RequestMethod.POST})
	public String modifyForm(@RequestParam("no") int postNo, Model model, HttpSession session) {
		System.out.println("board > modifyForm");
		
		if (session.getAttribute("authUser") == null) return "board/list";
		
		BoardVo post = bService.prepareModify(postNo);
		post.setContent(post.getContent().replace("<br>", "\n"));

		model.addAttribute("post", post);
		
		return "board/modifyForm";
	}
	
	
	@RequestMapping(value="/modify", method={RequestMethod.GET, RequestMethod.POST})
	public String modify(@ModelAttribute("post") BoardVo post) {
		System.out.println("board > modify");
		
		bService.modify(post);
		
		return "redirect:/board/read/" + post.getNo();
	}
	
	
	//게시글 삭제
	@RequestMapping(value="/delete/{no}", method={RequestMethod.GET, RequestMethod.POST})
	public String delete(@PathVariable("no") int postNo) {
		System.out.println("board > delete");
		
		bService.delete(postNo);
		
		return "redirect:/board/list";
	}
	
}
