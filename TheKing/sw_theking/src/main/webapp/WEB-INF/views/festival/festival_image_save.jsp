<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<h2>�̹��� ����</h2>
	<hr>
	<div>
		<h3>���� ��</h3>
		<p>${dto.name }</p>
	</div>
	<div>
		<h3>������</h3>
		<p>${dto.subtitle }</p>
	</div>
	<div>
		<h3>����</h3>
		<p>${dto.contents }</p>
	</div>

	<form action="test.do" method="post" enctype="multipart/form-data">
		<label for="festaNo">���� ��ȣ</label>
		<input type="text" name="festaNo"value="${dto.festaNo }">
		<br>
		<label>�̹��� ���ε�:</label><br>  
		<input type="file"	name="image"><br>
		<br>

		<button type="submit">����ϱ�</button>
	</form>
</body>
</html>