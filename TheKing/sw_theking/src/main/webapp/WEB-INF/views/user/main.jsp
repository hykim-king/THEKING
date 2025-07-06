<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
<h1>로그인 상태</h1>
<c:if test="${not empty message}">
    <div>${message}</div>
</c:if>
<a href="/ehr/user/myPage.do">마이페이지</a>
  
</body>
</html>