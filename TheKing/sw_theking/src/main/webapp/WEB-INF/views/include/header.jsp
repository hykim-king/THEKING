<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/ehr/resources/css/nav.css">
<title>header</title>
</head>
<body>
	<nav>
	<c:choose>
    	<c:when test="${empty sessionScope.loginUser}"><a href="/ehr/main.do"><img src="/ehr/resources/images/logo2.png"></a></c:when>
    	<c:otherwise><a href="/ehr/user/main.do"><img src="/ehr/resources/images/logo2.png"></a></c:otherwise>
    </c:choose>
    
    <a href="/ehr/tour/doRetrieve.do">관광지</a>
    <a href="/ehr/festival/main.do">축제</a>
    <a href="#">게시판</a>
    <a href="#">공지사항</a>
    <c:choose>
    	<c:when test="${empty sessionScope.loginUser}">
    		<a href="/ehr/user/signUpPage.do">회원가입</a>
    		<a href="/ehr/user/loginPage.do">로그인</a>
    	</c:when>
    	<c:otherwise><a href="/ehr/user/logout.do">로그아웃</a></c:otherwise>
    </c:choose>
  </nav>
</body>
</html>