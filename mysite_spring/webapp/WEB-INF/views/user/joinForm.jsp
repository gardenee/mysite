<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<link href="${pageContext.request.contextPath}/assets/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/assets/css/button.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/assets/css/user.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery/jquery-1.12.4.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/bootstrap/js/bootstrap.js"></script>
</head>

<body>
	<div id="wrap">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>

		<div id="container" class="clearfix">
			<c:import url="/WEB-INF/views/includes/aside/asideUser.jsp"/>

			<div id="content">
				<div id="content-head">
					<h3>회원가입</h3>
					<div id="location">
						<ul>
							<li>홈</li>
							<li>회원</li>
							<li class="last">회원가입</li>
						</ul>
					</div>
					<div class="clear"></div>
				</div>
				<!-- //content-head -->
	
				<div id="user">
					<div id="joinForm">
					<form id="join-form" action="${pageContext.request.contextPath}/user/join" method="post">
							<!-- 아이디 -->
							<div class="form-group">
								<label class="form-text" for="input-uid">아이디</label> 
								<input type="text" id="input-uid" name="id" value="" placeholder="아이디를 입력하세요">
								<button type="button" id="btn-idcheck">중복체크</button>
								<input type="hidden" id="id-checked" value="">
							</div>
	
							<!-- 비밀번호 -->
							<div class="form-group">
								<label class="form-text" for="input-pass">패스워드</label> 
								<input type="password" id="input-pass" name="pw" value="" placeholder="비밀번호를 입력하세요"	>
							</div>
	
							<!-- 이름 -->
							<div class="form-group">
								<label class="form-text" for="input-name">이름</label> 
								<input type="text" id="input-name" name="name" value="" placeholder="이름을 입력하세요">
							</div>
	
							<!-- //나이 -->
							<div class="form-group">
								<span class="form-text">성별</span> 
								
								<label for="rdo-male">남</label> 
								<input type="radio" id="rdo-male" name="gender" value="male" > 
								
								<label for="rdo-female">여</label> 
								<input type="radio" id="rdo-female" name="gender" value="female" > 
	
							</div>
	
							<!-- 약관동의 -->
							<div class="form-group">
								<span class="form-text">약관동의</span> 
								
								<input type="checkbox" id="chk-agree" name="">
								<label for="chk-agree">서비스 약관에 동의합니다.</label> 
							</div>
							
							<!-- 버튼영역 -->
							<div class="button-area">
								<button type="submit" id="btn-submit">회원가입</button>
							</div>
					</form>
					</div>
					<!-- //joinForm -->
					
				</div>
				<!-- //user -->
				
			</div>
			<!-- //content  -->
			
		</div>
		<!-- //container  -->
		
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>

	</div>
	<!-- //wrap -->
	
</body>

<script type="text/javascript">

$("#btn-idcheck").on("click", function(){
	$("#id-checked").val("")
	var id = $("#input-uid").val();
	
	if (id.length <= 0) {
		alert("아이디를 입력해주세요.")
	} else {
		idcheck();
	}
});

function idcheck() {
	var id = $("#input-uid").val();
		
	var test = {id: id};
	
	$.ajax({	
		url: "${pageContext.request.contextPath}/user/idcheck",
		type : "post",
		contentType : "application/json",
		data : JSON.stringify(test),
		
		dataType: "json",
		success : function(result){
			if (!result) {
				alert("이미 사용중인 ID입니다.")
				$("#input-uid").val("")
				
			} else {
				alert("사용 가능한 ID입니다.")
				$("#id-checked").val("checked")
			}			
		},
		error : function(XHR, status, error) {
			console.error(status + " : " + error);
		}
	});	
}

$("#btn-submit").on("click", function(){
	var id = $("#id-checked").val();
	var pw = $("#input-pass").val();
	var name = $("#input-name").val();

	if (id != "checked") {
		alert("아이디를 중복 검사를 해주세요.")
		return false;
		
	} else if (pw == "" || pw == null) {
		alert("비밀번호를 입력해주세요.")
		return false;
		
	} else if (name == "" || name == null) {
		alert("이름을 입력해주세요.")
		return false;
		
	} else if (! $("#chk-agree").is(":checked")) {
		alert("약관에 동의해주세요.")
		return false;
		
	} else return true;
});
</script>

</html>
