<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>갤러리</title>

<link href="${pageContext.request.contextPath}/assets/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/assets/css/button.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/assets/css/gallery.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery/jquery-1.12.4.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/bootstrap/js/bootstrap.js"></script>

</head>

<body>
	<div id="wrap">
		<c:import url="/WEB-INF/views/includes/header.jsp"></c:import>
		<c:import url="/WEB-INF/views/includes/aside/asideGallery.jsp"></c:import>

		<div id="content">

			<div id="content-head">
				<h3>갤러리</h3>
				<div id="location">
					<ul>
						<li>홈</li>
						<li>갤러리</li>
						<li class="last">갤러리</li>
					</ul>
				</div>
				<div class="clear"></div>
			</div>
			<!-- //content-head -->

			<div id="gallery">
				<div id="list">		
					<c:if test="${!(empty authUser)}">		
						<button id="btnImgUpload">이미지올리기</button>
						<div class="clear"></div>
					</c:if>
					
					<ul id="viewArea">
					<!-- 이미지반복영역 -->
					<c:forEach items="#{gList}" var="photo">
						<li id="img-li">
							<div class="view">
								<img class="imgItem" src="${pageContext.request.contextPath}/upload/${photo.saveName}">
								<div class="imgWriter">작성자: <strong>${photo.name}</strong></div>
								<input type="hidden" name="thisImgNo" value="${photo.no}">
								<input type="hidden" name="thisImgName" value="${photo.saveName}">
								<input type="hidden" name="thisImgUser" value="${photo.userNo}">
							</div>
						</li>
					</c:forEach>
					<!-- 이미지반복영역 -->
					</ul>
				</div>
				<!-- //list -->
			</div>
			<!-- //board -->
		</div>
		<!-- //content  -->
		<div class="clear"></div>

		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>
		<!-- //footer -->

	</div>
	<!-- //wrap -->

	<!-- 이미지등록 팝업(모달)창 -->
	<div class="modal fade" id="addModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title">이미지등록</h4>
				</div>
				
				<form method="post" action="${pageContext.request.contextPath}/gallery/add" enctype="multipart/form-data">
					<div class="modal-body">
						<div class="form-group">
							<label class="form-text">글작성</label>
							<input id="addModalContent" type="text" name="content" value="" >
						</div>
						<div class="form-group">
							<label class="form-text">이미지선택</label>
							<input id="file" type="file" name="file" value="" >
						</div>
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn" id="btnUpload">등록</button>
					</div>
				</form>
				
				
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	
	<!-- 이미지보기 팝업(모달)창 -->
	<div class="modal fade" id="viewModal">
		<div class="modal-dialog" >
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title">이미지보기</h4>
				</div>
				<div class="modal-body">
					
					<div id="imgHere" class="formgroup" >
					</div>
					
					<div class="formgroup">
						<p id="viewModelContent"></p>
					</div>
				</div>
				
				<div id="show-modal-footer" class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
				</div>			
				
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->	

</body>

<script type="text/javascript">

$("#btnImgUpload").on("click", function(){
	$("#addModal").modal("show");
});

$(".view").on("click", function(){
	var no = $(this).children("[name=thisImgNo]"); 
	var name = $(this).children("[name=thisImgName]").val();
	var userNo = $(this).children("[name=thisImgUser]").val();
	console.log(userNo)
	$("#imgHere").append('<img class="imgItem" src="${pageContext.request.contextPath}/upload/' + name + '">')
	
	var authUserNo = ${authUser.no}; 

	console.log(authUserNo)
	if ((authUserNo != "" || authUserNo != null) && userNo == authUserNo) {
		$("#show-modal-footer").append('<button type="button" id="btn-delete" class="btn btn-danger" id="btnDel">삭제</button>');
	}
	
	$("#viewModal").modal("show");
	
});

$("#show-modal-footer #btn-delete").on("click", function(){
	var no = $(this).children("[name=thisImgNo]"); 
	var info = {no: no};
	
	$.ajax({	
		url: "${pageContext.request.contextPath}/gallery/delete",
		type : "post",
		contentType : "application/json",
		data : JSON.stringify(info),
		
		dataType: "json",
		success : function(visit){
			
		},
		error : function(XHR, status, error) {
			console.error(status + " : " + error);
		}
	});	
});

$("#viewModal").on("hidden.bs.modal", function(){
	$("#imgHere").empty();
})

</script>

</html>

