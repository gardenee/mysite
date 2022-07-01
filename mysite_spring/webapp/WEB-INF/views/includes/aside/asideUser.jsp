<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div id="aside">
		<h2>회원</h2>
			<c:if test="${empty authUser}">
				<ul>
					<li><a href="${pageContext.request.contextPath}/user/loginForm">로그인</a></li>
					<li><a href="${pageContext.request.contextPath}/user/joinForm">회원가입</a></li>
				</ul>
			</c:if>
			<c:if test="${!(empty authUser)}">
				<ul>
					<li><a href="${pageContext.request.contextPath}/user/modifyForm">회원정보</a></li>
					<li>회원탈퇴</li>
				</ul>		
			</c:if>
	</div>
</body>
</html>