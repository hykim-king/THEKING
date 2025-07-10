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
<body class="main-page" style="background-image: url(/ehr/resources/images/메인배경.png);">
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
<div class="tour-wrapper">
    <img class="t-main-img" alt="경복궁 이미지" src="/ehr/resources/images/광화문광장.png">
    <div class="tour-column">
    <h3>인기 관광지</h3>
    <div class="t-sub-img-row">
      <img class="t-sub-img" alt="다대포해수욕장" src="/ehr/resources/images/tour/다대포해수욕장_1_공공1유형.jpg">
      <img class="t-sub-img" alt="(구)인천일본제58은행지점" src="/ehr/resources/images/tour/(구)인천일본제58은행지점_1_공공3유형.jpg">
      <img class="t-sub-img" alt="꿀벌나라테마공원" src="/ehr/resources/images/tour/꿀벌나라테마공원_1_공공3유형.jpg">
   </div>
  </div>
</div>
</div>
<div class="festival-wrapper">
  <div class="festival-column">
  <h3>인기 축제</h3>
  <div class="f-sub-img-row">
    <img class="f-sub-img" alt="인기 축제" src="/ehr/resources/images/festival/00717369-919a-4b0e-9378-729116b91ef4.png">
    <img class="f-sub-img" alt="인기 축제" src="/ehr/resources/images/festival/0e40be10-15a6-4613-8888-7687cd907c97.jpg">
    <img class="f-sub-img" alt="인기 축제" src="/ehr/resources/images/festival/18139f14-6b86-43a2-8368-92f07a3cb37f.jpg">
 </div>
</div>
    <img class="f-main-img" alt="불꽃놀이 이미지" src="/ehr/resources/images/축제_불꽃놀이.jpg">
</div>
<div class="notice-wrapper">
  <h3>공지사항</h3>
  <div class="notice">

  </div>
</div>
<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
<script src="/ehr/resources/js/carousel.js"></script>
</body>
</html>