<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>축제 수정</title>
<style>
	img{
		width:400px;
		height:200px;
	}
</style>
</head>
<body>
	<h2>Festival Mod</h2>
	<hr>
	<a href="/ehr/festival/main.do"><img alt="로고이미지" src="${pageContext.request.contextPath}/resources/images/logo.png"></a>
	<h4>축제 수정</h4>
	<hr>
	<div class="container">
		<img>
		<form action="/ehr/festival/doSave.do" method="post">
			<div>
				<label for="name">제목</label>
				<input type="text" name="name" value="${dto.name }">
			</div>
			<div>
				<label for="subtitle">소제목</label>
				<input type="text" name="subtitle" value="${dto.subtitle }">
			</div>			
			<div>
				<label>상세 정보</label>
				<textarea rows="20px" cols="40px">${dto.contents }</textarea>
			</div>
			<div>
				<label for="address">주소</label>
				<input type="text" name="address" value="${dto.address }">
			</div>
			<div>
				<label for="tel">연락처</label>
				<input type="text" name="tel" value="${dto.tel }">
			</div>
			<div>
				<label for="fee">요금</label>
				<input type="text" name="fee" value="${dto.fee }">
			</div>
			<div>
				<label for="startDate">시작 날짜</label>
				<input type="date" name="startDate" value="${dto.startDate }">
				<label for="endDate">종료 날짜</label>
				<input type="date" name="endDate" value="${dto.endDate }">
			</div>
			<div>
				<label for="regionNo">지역코드</label>
				<input type="text" name="regionNo" value="${dto.regionNo }">
			</div>
			
			<button type="submit">등록</button>
			<button type="button" onclick="history.back()">취소</button>
		</form>
	</div>
</body>
</html>