<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>일반게시판</title>
<link href="${pageContext.request.contextPath}/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/assets/css/board.css" rel="stylesheet" type="text/css">

</head>

<body>
	<div id="wrap">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>

		<div id="container" class="clearfix">
			<c:import url="/WEB-INF/views/includes/aside/asideBoard.jsp"/>

			<div id="content">
				<div id="content-head">
					<h3>일반게시판</h3>
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
						<form action="${pageContext.request.contextPath}/board/list/1" method="get">
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
								<c:forEach items="${pVo.bList}" var="post">
									<tr>
										<td>${post.no}</td>
										<td class="text-left"><a href="${pageContext.request.contextPath}/board/read/${post.no}">${post.title}</a></td>
										<td>${post.name}</td>
										<td>${post.hit}</td>
										<td>${post.regDate}</td>
										<c:if test="${authUser.no == post.userNo}">
											<td><a href="${pageContext.request.contextPath}/board/delete/${post.no}">[삭제]</a></td>
										</c:if>
									</tr>
								</c:forEach>
							</tbody>
						</table>
			
						<div id="paging">
							<ul>
								<c:if test="${pVo.prev}">
									<li><a href="${pageContext.request.contextPath}/board/list/${pVo.startBtn-1}?search=${search}">◀</a></li>
								</c:if>
								
								<c:forEach begin="${pVo.startBtn}" end="${pVo.endBtn}" step="1" var="page">
									<c:if test="${page == pVo.currPage}">
										<li class="active"><a href="${pageContext.request.contextPath}/board/list/${page}?search=${search}">${page}</a></li>
									</c:if>
									<c:if test="${page != pVo.currPage}">
										<li><a href="${pageContext.request.contextPath}/board/list/${page}?search=${search}">${page}</a></li>
									</c:if>
								</c:forEach>
								
								<c:if test="${pVo.next}">
									<li><a href="${pageContext.request.contextPath}/board/list/${pVo.endBtn+1}?search=${search}">▶</a></li>
								</c:if>
							</ul>
														
							<div class="clear"></div>
						</div>
						
						<c:if test="${!(empty authUser)}">
							<a id="btn_write" href="${pageContext.request.contextPath}/board/writeForm">글쓰기</a>
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
