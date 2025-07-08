<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
 <script src="/ehr/resources/js/common.js"></script>
<head>
<meta charset="UTF-8">
<title>축제 등록</title>
<style>
	img{
		width:400px;
		height:200px;
	}
</style>
</head>
<body>
	<h2>Festival Reg</h2>
	<hr>
	<a href="/ehr/festival/main.do"><img alt="로고이미지" src="${pageContext.request.contextPath}/resources/images/logo.png"></a>
	<h4>축제 등록</h4>
	<hr>
	<div class="container">
		<form action="doSave.do" method="post" enctype="multipart/form-data">
			<div>
				<label for="name">제목</label>
				<input type="text" name="name" id="name">
			</div>
			<div>
				<label for="subtitle">소제목</label>
				<input type="text" name="subtitle" id="subtitle">
			</div>			
			<div>
				<label>상세 정보</label>
				<textarea rows="20px" cols="40px" name="contents" id="contents"></textarea>
			</div>
			<div>
				<label for="imageFile">이미지</label>
				<input type="file" name="imageFile" id=imageFile>
			</div>
			<div>
				<label for="address">주소</label>
				<input type="text" name="address" id="address">
			</div>
			<div>
				<label for="tel">연락처</label>
				<input type="text" name="tel" id="tel">
			</div>
			<div>
				<label for="fee">입장료</label>
				<input type="text" name="fee" id="fee">
			</div>
			<div>
				<label for="startDate">시작 날짜</label>
				<input type="date" name="startDate" id="startDate">
				<label for="endDate">종료 날짜</label>
				<input type="date" name="endDate" id="endDate">
			</div>
			<div>
				<label for="regionNo">지역코드</label>
				<input type="text" name="regionNo" id="regionNo">
			</div>
			
			<button type="submit" id="doSave">등록</button>
			<button type="button" onclick="history.back()">취소</button>
		</form>
	</div>
</body>
</html>