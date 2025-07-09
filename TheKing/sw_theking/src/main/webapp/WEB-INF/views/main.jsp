<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/ehr/resources/css/user/main.css">
<title>떠나볼지도</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body class="main-page">
<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
<div class="carousel-wrapper">
	<div class="carousel-track">
	  <a href="#"><img src="/ehr/resources/images/tour/계양산_1_공공3유형.jpg" alt="이미지1"></a>
	  <a href="#"><img src="/ehr/resources/images/tour/강화 전등사_1_공공1유형.jpg" alt="이미지2"></a>
	  <a href="#"><img src="/ehr/resources/images/tour/고성 장산숲_1_공공3유형.JPG" alt="이미지3"></a>
	  <a href="#"><img src="/ehr/resources/images/tour/구름포해수욕장_1_공공3유형.jpg" alt="이미지4"></a>
	  <a href="#"><img src="/ehr/resources/images/tour/국립수목원(광릉숲)_1_공공1유형.jpg" alt="이미지5"></a>
	</div>
	<button class="carousel-btn left">&#10094;</button>
  	<button class="carousel-btn right">&#10095;</button>
</div>
<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
<script src="/ehr/resources/js/carousel.js"></script>
</body>
</html>