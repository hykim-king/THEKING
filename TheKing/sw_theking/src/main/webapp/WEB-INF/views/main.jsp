<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
	  <a href="/ehr/tour/doSelectOne.do?tourNo=602"><img src="/ehr/resources/images/tour/계양산_1_공공3유형.jpg" alt="이미지1"></a>
	  <a href="/ehr/tour/doSelectOne.do?tourNo=204"><img src="/ehr/resources/images/tour/강화_전등사_1_공공1유형.jpg" alt="이미지2"></a>
	  <a href="/ehr/tour/doSelectOne.do?tourNo=509"><img src="/ehr/resources/images/tour/고성_장산숲_1_공공3유형.JPG" alt="이미지3"></a>
	  <a href="/ehr/tour/doSelectOne.do?tourNo=650"><img src="/ehr/resources/images/tour/구름포해수욕장_1_공공3유형.jpg" alt="이미지4"></a>
	  <a href="/ehr/tour/doSelectOne.do?tourNo=633"><img src="/ehr/resources/images/tour/국립수목원(광릉숲)_1_공공1유형.jpg" alt="이미지5"></a>
	</div>
	<button class="carousel-btn left">&#10094;</button>
  	<button class="carousel-btn right">&#10095;</button>
</div>
<div class="tour-wrapper">
    <img class="t-main-img" alt="경복궁 이미지" src="/ehr/resources/images/광화문광장.png">
    <div class="tour-column">
    <h3>인기 관광지</h3>
    <div class="t-sub-img-row">
      <c:forEach var="tour" items="${popularTours}">
	  <div class="t-sub-img">
	    <a href="${pageContext.request.contextPath}/tour/doSelectOne.do?tourNo=${tour.tourNo}">
	      <img src="${pageContext.request.contextPath}${tour.image.imageUrl}" alt="${tour.name}" />
	    </a>
	  </div>
	</c:forEach>
   </div>
  </div>
</div>
<div class="festival-wrapper">
  <div class="festival-column">
    <h3>인기 축제</h3>
        <div class="f-sub-img-row">
		    <c:forEach var="festival" items="${popularFestivals}">
		        <a href="${pageContext.request.contextPath}/festival/doSelectOne.do?festaNo=${festival.festaNo}">
		          <img class="f-sub-img" src="${pageContext.request.contextPath}/resources/images/festival/${festival.image.saveName}" alt="${festival.name}" />
		        </a>
		    </c:forEach>
        </div>
    </div>
    <img class="f-main-img" alt="불꽃놀이 이미지" src="/ehr/resources/images/축제_불꽃놀이.jpg">
</div>
<div class="notice-wrapper">
  <h3>공지사항</h3>
  <div class="notice">
    <c:forEach var="post" items="${posts}">
          <div class="post">
          <c:choose>
              <c:when test="${post['div'] eq '20'}">
                <a href="${pageContext.request.contextPath}/board/doSelectOne.do?div=20&seq=${post.seq}&regId=">
                    ${post.title}
                </a>
              </c:when>
            </c:choose>
          </div>
        </c:forEach>
  </div>
</div>
<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
<script src="/ehr/resources/js/carousel.js"></script>
</body>
</html>