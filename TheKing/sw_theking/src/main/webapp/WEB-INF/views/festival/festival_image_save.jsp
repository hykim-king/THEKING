<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<h2>이미지 저장</h2>
	<hr>
	<div>
		<h3>축제 명</h3>
		<p>${dto.name }</p>
	</div>
	<div>
		<h3>소제목</h3>
		<p>${dto.subtitle }</p>
	</div>
	<div>
		<h3>내용</h3>
		<p>${dto.contents }</p>
	</div>

	<form action="test.do" method="post" enctype="multipart/form-data">
		<label for="festaNo">축제 번호</label>
		<input type="text" name="festaNo"value="${dto.festaNo }">
		<br>
		<label>이미지 업로드:</label><br>  
		<input type="file"	name="image"><br>
		<br>

		<button type="submit">등록하기</button>
	</form>
</body>
</html>