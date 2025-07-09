<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/ehr/resources/css/footer.css">
<title>footer</title>
</head>
<body>
	<footer>
  		<c:choose>
            <c:when test="${empty sessionScope.loginUser}">
                <a href="/ehr/main.do"><img alt="footer_logo" src="/ehr/resources/images/logo2.png"></a>
            </c:when>
            <c:otherwise>
            	<a href="/ehr/user/main.do"><img alt="footer_logo" src="/ehr/resources/images/logo2.png"></a>
            </c:otherwise>
        </c:choose>
        <div class="team">
        	<p>TheKing</p>
        	<p>GGMAP</p>
        </div>
	</footer>
</body>
</html>