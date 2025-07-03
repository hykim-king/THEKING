<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
    <form action="test.do" method="post" enctype="multipart/form-data">

        <label>이미지 업로드:</label><br>
        <input type="file" name="image"><br><br>

        <button type="submit">등록하기</button>
    </form>
</body>
</html>