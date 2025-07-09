<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 상세</title>
</head>
<body>
<h2>게시글 상세</h2>
<hr>
<table border="1">
    <tr><th>제목</th><td>${board.title}</td></tr>
    <tr><th>작성자</th><td>${board.regId}</td></tr>
    <tr><th>등록일</th><td>${board.regDate}</td></tr>
    <tr><th>조회수</th><td>${board.views}</td></tr>
    <tr><th>내용</th><td><pre>${board.contents}</pre></td></tr>
</table>
<br>
<form action="<c:url value='/board/edit.do'/>" method="get">
    <input type="hidden" name="boardNo" value="${board.boardNo}">
    <button type="submit">수정</button>
</form>
<form action="<c:url value='/board/delete.do'/>" method="post" style="display:inline">
    <input type="hidden" name="boardNo" value="${board.boardNo}">
    <button type="submit">삭제</button>
</form>
<button onclick="location.href='<c:url value="/board/list.do"/>'">목록</button>
</body>
</html>