<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>일반게시판</title>
<link href="/mysite2/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="/mysite2/assets/css/board.css" rel="stylesheet" type="text/css">
</head>

<body>
	<div id="wrap">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<c:import url="/WEB-INF/views/includes/nav.jsp"/>

		<div id="container" class="clearfix">
			<c:import url="/WEB-INF/views/includes/aside/asideBoard.jsp"/>

			<div id="content">
				<div id="content-head">
					<h3>게시판</h3>
					<div id="location">
						<ul>
							<li>홈</li>
							<li>게시판</li>
							<li class="last">일반게시판</li>
						</ul>
					</div>
					<div class="clear"></div>
				</div>
				<!-- //content-head -->
	
				<div id="board">
					<div id="list">
						<form action="/mysite2/board" method="get">
							<input type="hidden" name="action" value="search">
							<div class="form-group text-right">
								<input type="text" name="search" value="">
								<button type="submit" id="btn_search">검색</button>
							</div>
						</form>
						
						<table>
							<thead>
								<tr>
									<th>번호</th>
									<th>제목</th>
									<th>글쓴이</th>
									<th>조회수</th>
									<th>작성일</th>
									<th>관리</th>
								</tr>
							</thead>
							
							<tbody>
								<c:forEach items="${bList}" var="post">
									<tr>
										<td>${post.no}</td>
										<td class="text-left"><a href="/mysite2/board?action=read&no=${post.no}">${post.title}</a></td>
										<td>${post.name}</td>
										<td>${post.hit}</td>
										<td>${post.regDate}</td>
										<c:if test="${user.no == post.userNo}">
											<td><a href="/mysite2/board?action=delete&no=${post.no}">[삭제]</a></td>
										</c:if>
									</tr>
								</c:forEach>
							</tbody>
						</table>
			
						<div id="paging">
							<ul>
								<li><a href="">◀</a></li>
								<li class="active"><a href="">1</a></li>
								<li><a href="">2</a></li>
								<li><a href="">3</a></li>
								<li><a href="">4</a></li>
								<li><a href="">5</a></li>
								<li><a href="">6</a></li>
								<li><a href="">7</a></li>
								<li><a href="">8</a></li>
								<li><a href="">9</a></li>
								<li><a href="">10</a></li>
								<li><a href="">▶</a></li>
							</ul>
														
							<div class="clear"></div>
						</div>
						
						<c:if test="${!(empty user)}">
							<a id="btn_write" href="/mysite2/board?action=writeForm">글쓰기</a>
						</c:if>
						
					</div>
					<!-- //list -->
					
				</div>
				<!-- //board -->
				
			</div>
			<!-- //content  -->
			
		</div>
		<!-- //container  -->
		
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>

	</div>
	<!-- //wrap -->
	
</body>

</html>
