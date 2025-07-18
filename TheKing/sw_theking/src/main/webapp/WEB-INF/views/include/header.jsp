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
      <a href="/ehr/main.do"><img src="/ehr/resources/images/logo2.png"></a>
      <a href="/ehr/tour/doRetrieve.do">관광지</a>
      <a href="/ehr/festival/main.do">축제</a>
      <a href="/ehr/board/doRetrieve.do?div=10">게시판</a>
      <a href="/ehr/board/doRetrieve.do?div=20">공지사항</a>
    <c:choose>
    	<c:when test="${empty sessionScope.loginUser}">
    	<div class="logIn">
    	   <a href="/ehr/user/loginPage.do" class="logIn">로그인</a>
           <a href="/ehr/user/signUpPage.do" class="logIn">회원가입</a>
    	</div>
    	</c:when>
    	<c:otherwise>
    		<a href="/ehr/user/myPage.do">마이페이지</a>
    		<a href="/ehr/user/logout.do" class="logIn">로그아웃</a>
    	</c:otherwise>
    </c:choose>
  </nav>
</body>
</html>