<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 읽기</title>
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
					<h3>답글게시판</h3>
					<div id="location">
						<ul>
							<li>홈</li>
							<li>게시판</li>
							<li class="last">답글게시판</li>
						</ul>
					</div>
					<div class="clear"></div>
				</div>
				<!-- //content-head -->
	
				<div id="board">
					<form action="${pageContext.request.contextPath}/rboard/modifyForm" method="post">
						<input type="hidden" name="no" value="${post.no}">
						<div id="read">
							<!-- 작성자 -->
							<div class="form-group">
								<span class="form-text">작성자</span>
								<span class="form-value">${post.name}</span>
							</div>
							
							<!-- 조회수 -->
							<div class="form-group">
								<span class="form-text">조회수</span>
								<span class="form-value">${post.hit}</span>
							</div>
							
							<!-- 작성일 -->
							<div class="form-group">
								<span class="form-text">작성일</span>
								<span class="form-value">${post.regDate}</span>
							</div>
							
							<!-- 제목 -->
							<div class="form-group">
								<span class="form-text">제 목</span>
								<span class="form-value">${post.title}</span>
							</div>
						
							<!-- 내용 -->
							<div id="txt-content">
								<span class="form-value" >
									${post.content}
								</span>
							</div>
							
							<c:if test="${post.userNo == authUser.no}">
								<button id="btn_modify" type="submit">수정</button>
							</c:if>
							<c:if test="${!(empty authUser)}">
								<a id="btn_reply" href="${pageContext.request.contextPath}/rboard/replyForm/${post.no}">답글달기</a>
							</c:if>
							<a id="btn_list" href="${pageContext.request.contextPath}/rboard/list">목록</a>
						</div>
						<!-- //read -->
					</form>		
					
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
