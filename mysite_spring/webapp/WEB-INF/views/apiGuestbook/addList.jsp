<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<%@ page import="com.javaex.vo.GuestBookVo" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>방명록</title>
<link href="${pageContext.request.contextPath}/assets/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/assets/css/button.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/assets/css/guestbook.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery/jquery-1.12.4.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/bootstrap/js/bootstrap.js"></script>

</head>

<body>
	<div id="wrap">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
	
		<div id="container" class="clearfix">
			<c:import url="/WEB-INF/views/includes/aside/asideGuestBook.jsp"/>

			<div id="content">
				<div id="content-head" class="clearfix">
					<h3>ajax방명록</h3>
					<div id="location">
						<ul>
							<li>홈</li>
							<li>방명록</li>
							<li class="last">ajax방명록</li>
						</ul>
					</div>
				</div>
				<!-- //content-head -->

				<div id="guestbook">
					<table id="guestAdd">
						<colgroup>
							<col style="width: 70px;">
							<col>
							<col style="width: 70px;">
							<col>
						</colgroup>
						
						<tr>
							<th><label class="form-text" for="input-uname">이름</label></th>
							<td><input id="name" type="text" name="name" value=""></td>
							<th><label class="form-text" for="input-pass">패스워드</label></th>
							<td><input id="password"type="password" name="password" value=""></td>
						</tr>
						<tr>
							<td colspan="4"><textarea id="content" name="content" cols="72" rows="5"></textarea></td>
						</tr>
						<tr class="button-area">
							<td colspan="4" class="text-center"><button id="btn-submit">등록</button></td>
						</tr>
					</table>
					<!-- //guestWrite -->
										
					<div id="listarea">
						
					</div>
					<!-- //guestRead -->
					
				</div>
				<!-- //guestbook -->
			
			</div>
			<!-- //content  -->
		</div>
		<!-- //container  -->
		
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>

	</div>
	<!-- //wrap -->


	<!-- 삭제 모달창  -->
	<div id="del-modal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">비밀번호를 입력하세요</h4>
				</div>
				<div class="modal-body">
					<input type="hidden" name="no" value="">
					<input type="text" name="modal-pw" value="">
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
					<button id="modal-btn-del" type="button" class="btn btn-danger">삭제</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->

</body>

<script type="text/javascript">

$(document).ready(function(){
	$.ajax({
		url: "${pageContext.request.contextPath}/api/guestbook/show",
		type : "post",
		
		success : function(gbList){
			console.log(gbList);
			
			for (var i = 0; i < gbList.length; i++) {
				render(gbList[i]);
			}
		},
		error : function(XHR, status, error) {
			console.error(status + " : " + error);
		}
	});
});


function render(gbVo) {
	$("#listarea").append(
			
		"<table id='table" + gbVo.no + "'class='guestRead'>"+
     		"<colgroup>"+
				"<col style='width: 10%;'>"+
				"<col style='width: 40%;'>"+
 				"<col style='width: 40%;'>"+
   				"<col style='width: 10%;'>"+
			"</colgroup>"+
		
			"<tr>"+
				"<td>" + gbVo.no + "</td>"+
				"<td>" + gbVo.name + "</td>"+
				"<td>" + gbVo.regDate + "</td>"+
				"<td><button class='btn-del' type='button' data-no='" + gbVo.no + "'>삭제</button></td>"+
			"</tr>"+
			"<tr>"+
				"<td colspan=4 class='text-left'>" + gbVo.content + "</td>"+
			"</tr>"+
		"</table>"
	);
}


function render2(gbVo) {
	$("#listarea").prepend(
			
		"<table id='table" + gbVo.no + "'class='guestRead'>"+
     		"<colgroup>"+
				"<col style='width: 10%;'>"+
				"<col style='width: 40%;'>"+
 				"<col style='width: 40%;'>"+
   				"<col style='width: 10%;'>"+
			"</colgroup>"+
		
			"<tr>"+
				"<td>" + gbVo.no + "</td>"+
				"<td>" + gbVo.name + "</td>"+
				"<td>" + gbVo.regDate + "</td>"+
				"<td><button class='btn-del' type='button' data-no=''>삭제</button></td>"+
			"</tr>"+
			"<tr>"+
				"<td colspan=4 class='text-left'>" + gbVo.content + "</td>"+
			"</tr>"+
		"</table>"
	);
}


$("#btn-submit").on("click", function(){
	var name = $("[name=name]").val();
	var password = $("[name=password]").val();
	var content = $("[name=content]").val();
	
	var gbVo = {
		name: name,
		password: password,
		content: content
	};
			
	$.ajax({	
		url: "${pageContext.request.contextPath}/api/guestbook/add",
		type : "post",
		contentType : "application/json",
		data : JSON.stringify(gbVo),
		
		dataType: "json",
		success : function(visit){
			console.log(visit);
			
			render2(visit);
			
			$("[name=name]").val("");
			$("[name=password]").val("");
			$("[name=content]").val("");
		},
		error : function(XHR, status, error) {
			console.error(status + " : " + error);
		}
	});	
	
	
	/*
	$.ajax({
		url: "${pageContext.request.contextPath}/api/guestbook/add",
		type : "post",
		data : visit
		
		success : function(visit){
			console.log(visit);
			
			render2(visit);
			
			$("[name=name]").val("");
			$("[name=password]").val("");
			$("[name=content]").val("");
		},
		error : function(XHR, status, error) {
			console.error(status + " : " + error);
		}
	});	
	*/
});


$("#listarea").on("click", ".btn-del", function(){
	var no = $(this).data("no");
	$("[name=modal-pw]").val("");
	$("[name=no]").val(no);
	
	$("#del-modal").modal("show");
});


$("#modal-btn-del").on("click", function(){
	var pw = $("[name=modal-pw]").val();
	var no = $("[name=no]").val();
	
	$.ajax({
		url: "${pageContext.request.contextPath}/api/guestbook/delete",
		type : "post",
		data : {password: pw, no: no},
		
		success : function(result){
			if (result == "success") {
				$("#table" + no).remove();
				$("#del-modal").modal("hide");
				
			} else {			
				alert("비밀번호가 일치하지 않습니다.");
			}
		},
		error : function(XHR, status, error) {
			console.error(status + " : " + error);
		}
	});
});

</script>

</html>
