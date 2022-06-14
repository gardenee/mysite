package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.BoardDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;


@WebServlet("/board")
public class BoardController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private BoardDao bDao;
	private BoardVo post;
	private String title;
	private String content;
	private int no;
	
	private List<BoardVo> bList;
	private HttpSession session;
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		System.out.println("boardController action: " + action);
		
		switch((action!=null) ? action : "default") {
		
			case "writeForm":
				WebUtil.forward(request, response, "/WEB-INF/views/board/writeForm.jsp");
				break;
				
			case "write":
				session = request.getSession();
				UserVo user = (UserVo)session.getAttribute("user");
				
				title = request.getParameter("title");
				content = request.getParameter("content").replace("\n", "<br>");
				post = new BoardVo(title, content);
				post.setUserNo(user.getNo());
				
				bDao = new BoardDao();
				bDao.write(post);
				
				WebUtil.redirect(request, response, "/mysite2/board?action=list");
				break;
				
			case "read":
				no = Integer.parseInt(request.getParameter("no"));
				
				bDao = new BoardDao();
				bDao.hit(no);
				
				post = bDao.readPost(no);
				request.setAttribute("post", post);
				
				WebUtil.forward(request, response, "/WEB-INF/views/board/read.jsp");
				break;
				
			case "delete":
				no = Integer.parseInt(request.getParameter("no"));
				
				bDao = new BoardDao();
				bDao.delete(no);
				
				WebUtil.redirect(request, response, "/mysite2/board?action=list");
				break;
				
			case "modifyForm":
				no = Integer.parseInt(request.getParameter("no"));
				
				bDao = new BoardDao();
				post = bDao.readPost(no);
				post.setContent(post.getContent().replace("<br>", "\n"));
				request.setAttribute("post", post);
				
				WebUtil.forward(request, response, "/WEB-INF/views/board/modifyForm.jsp");
				break;
				
			case "modify":
				no = Integer.parseInt(request.getParameter("postNo"));
				title = request.getParameter("title");
				content = request.getParameter("content").replace("\n", "<br>");
				
				bDao = new BoardDao();
				bDao.modify(new BoardVo(no, title, content));
				
				WebUtil.redirect(request, response, "/mysite2/board?action=list");
				break;
				
			case "search":
				String search = request.getParameter("search");

				bDao = new BoardDao();
				bList = bDao.showList(search);
				
				request.setAttribute("bList", bList);
				
				WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
				break;
				
			default:
				bDao =  new BoardDao();
				bList = bDao.showList("");
				request.setAttribute("bList", bList);
				
				WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
				break;
		}			
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");		
		doGet(request, response);
	}
	
}
