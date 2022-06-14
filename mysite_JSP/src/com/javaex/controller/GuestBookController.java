package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestBookDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestBookVo;


@WebServlet("/guestbook")
public class GuestBookController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private GuestBookDao gbDao;
	private GuestBookVo visit;
	
	private String name;
	private String pw;
	private String content;
	private int no;
	
	private List<GuestBookVo> gList;
	
	      
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		System.out.println("guestbookController action: " + action);
		
		switch((action!=null) ? action : "default") {			
			case "add":
				name = request.getParameter("name");
				pw = request.getParameter("pw");
				content = request.getParameter("content");
				
				visit = new GuestBookVo(name, pw, content);
				visit.setContent(visit.getContent().replace("\n", "<br>"));
				
				gbDao = new GuestBookDao();
				gbDao.add(visit);
				
				WebUtil.redirect(request, response, "/mysite2/guestbook?action=addList");
				break;
				
			case "deleteForm":
				no = Integer.parseInt(request.getParameter("no"));
				request.setAttribute("no", no);

				WebUtil.forward(request, response, "/WEB-INF/views/guestBook/deleteForm.jsp");
				break;
				
			case "delete":
				no = Integer.parseInt(request.getParameter("no"));
				pw = request.getParameter("pw");
				System.out.println("no " + no + " pw " + pw);
				
				gbDao = new GuestBookDao();
				gbDao.delete(no, pw);
				
				WebUtil.redirect(request, response, "/mysite2/guestbook?action=addList");
				break;
				
			default:
				gbDao = new GuestBookDao();
				gList = gbDao.select();
				
				request.setAttribute("gList", gList);
				
				WebUtil.forward(request, response, "/WEB-INF/views/guestBook/addList.jsp");
				break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		doGet(request, response);
	}

}
