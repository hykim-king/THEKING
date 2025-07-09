<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 작성</title>
</head>
<body>
<h2>게시글 작성</h2>
<hr>
<form action="<c:url value='/board/write.do'/>" method="post">
    <label for="title">제목</label>
    <input type="text" name="title" id="title" required><br>

    <label for="contents">내용</label><br>
    <textarea name="contents" id="contents" rows="10" cols="50" required></textarea><br>

    <label for="regId">작성자</label>
    <input type="text" name="regId" id="regId" required><br>

    <input type="hidden" name="boardPart" value="10">
    <button type="submit">등록</button>
    <button type="button" onclick="location.href='<c:url value="/board/list.do"/>'">목록</button>
</form>
</body>
</html>