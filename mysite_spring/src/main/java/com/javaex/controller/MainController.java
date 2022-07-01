package com.javaex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {
	
	@RequestMapping(value="/main", method={RequestMethod.GET, RequestMethod.POST})
	public String main() {
		System.out.println("Main");
		
		return "main/index";
	}
	

	// test
	@RequestMapping(value="/test", method={RequestMethod.GET, RequestMethod.POST})
	public String test() {
		System.out.println("헬루");
		return "test";
	}
	
}
