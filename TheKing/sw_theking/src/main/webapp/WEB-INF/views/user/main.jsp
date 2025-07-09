<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>떠나볼지도</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
<h1>로그인 상태</h1>
<c:if test="${not empty message}">
    <div>${message}</div>
</c:if>
<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
</body>
</html>