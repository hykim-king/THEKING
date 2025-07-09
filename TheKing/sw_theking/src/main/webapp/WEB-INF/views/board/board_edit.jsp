<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 수정</title>
</head>
<body>
<h2>게시글 수정</h2>
<hr>
<form action="<c:url value='/board/edit.do'/>" method="post">
    <input type="hidden" name="boardNo" value="${board.boardNo}">

    <label for="title">제목</label>
    <input type="text" name="title" id="title" value="${board.title}" required><br>

    <label for="contents">내용</label><br>
    <textarea name="contents" id="contents" rows="10" cols="50" required>${board.contents}</textarea><br>

    <input type="hidden" name="regId" value="${board.regId}">
    <input type="hidden" name="boardPart" value="${board.boardPart}">

    <button type="submit">저장</button>
    <button type="button" onclick="location.href='<c:url value="/board/view.do?boardNo=${board.boardNo}"/>'">취소</button>
</form>
</body>
</html>
