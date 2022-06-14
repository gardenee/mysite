package com.javaex.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;


@WebServlet("/user")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String action = request.getParameter("action");
		System.out.println("UserController" + " action:" + action);

		switch(action) {
			case "joinForm":
				WebUtil.forward(request, response, "/WEB-INF/views/user/joinForm.jsp");
				break;
			
			case "join":
				String id = request.getParameter("id");
				String pw = request.getParameter("pw");
				String name = request.getParameter("name");
				String gender = request.getParameter("gender");
								
				UserDao uDao = new UserDao();
				uDao.join(new UserVo(id, pw, name, gender));
				
				WebUtil.forward(request, response, "/WEB-INF/views/user/joinOk.jsp");
				break;
				
			case "loginForm":
				WebUtil.forward(request, response, "/WEB-INF/views/user/loginForm.jsp");
				break;
				
			case "login":
				id = request.getParameter("id");
				pw = request.getParameter("pw");
				
				UserVo uVo = new UserVo(id, pw);
				
				uDao = new UserDao();
				UserVo user = uDao.getUser(uVo);
												
				if (user == null) {
					System.out.println("[로그인 실패]");
				}
				else {
					System.out.println("[로그인 성공]");
					
					HttpSession session = request.getSession();
					session.setAttribute("user", user);
				}
				
				WebUtil.redirect(request, response, "/mysite2/main");
				break;
				
			case "logout":
				HttpSession session = request.getSession();
				session.removeAttribute("user");
				session.invalidate();
				
				WebUtil.redirect(request, response, "/mysite2/main");
				break;
				
			case "modifyForm":
				session = request.getSession();
				user = (UserVo)session.getAttribute("user");
				
				if (user != null) {
					uDao = new UserDao();
					user = uDao.getUser(user.getNo());
					
					request.setAttribute("user", user);
					WebUtil.forward(request, response, "/WEB-INF/views/user/modifyForm.jsp");					
				}
				break;
				
			case "modify":
				session = request.getSession();
				user = (UserVo)session.getAttribute("user");
				
				int no = user.getNo();
				
				pw = request.getParameter("pw");
				name = request.getParameter("name");
				gender = request.getParameter("gender");
				
				user = new UserVo(no, pw, name, gender);
								
				uDao = new UserDao();
				uDao.modify(user);
				
				session.setAttribute("user", new UserVo(user.getNo(), user.getName()));
				WebUtil.redirect(request, response, "/mysite2/main");
				break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		doGet(request, response);
	}
}
