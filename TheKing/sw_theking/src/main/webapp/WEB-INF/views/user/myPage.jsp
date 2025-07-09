<%@page import="com.pcwk.ehr.cmn.PcwkString"%>
<%@page import="com.pcwk.ehr.cmn.SearchDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Jua&display=swap" rel="stylesheet">
<meta charset="UTF-8">
<link rel="stylesheet" href="/ehr/resources/css/footer.css">
<link rel="stylesheet" href="/ehr/resources/css/nav.css">
<link rel="stylesheet" href="/ehr/resources/css/user/myPage.css">
<title>떠나볼지도</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="/ehr/resources/js/common.js"></script>
  <script>
  document.addEventListener("DOMContentLoaded", function() {
  	const userDelete = document.getElementById("userDelete");
  	userDelete.addEventListener("click", function(event) {
	    console.log("계정 탈퇴 클릭됨");
	    
	    if (confirm("정말 탈퇴하시겠습니까?")) {
	    	$.ajax({
	    	    type: "POST",
	    	    url: "/ehr/user/doDelete.do",
	    	    async: "true",
	    	    dataType: "html",
	    	    data: {
	    	        "userId": $("#userId").val()
	    	    },
	    	    success: function(response) {
	    	        console.log("success:", response);
	    	        const data = JSON.parse(response);
	    	        if (data.messageId === 1) {
	    	        	alert(data.message);
	    	            location.href = "/ehr/main.do";
	    	        } else {
	    	            // 로그인 실패 메시지 출력
	    	            alert(data.message);
	    	        }
	    	    },
	    	    error: function(response) {
	    	        console.log(response);
	    	    }   
	    	    });
	    }
  	});
  	
  });
  

  </script>
</head>
<body>
  <jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
  <h1 id ="page">MY</h1>
  <div class="container">
  <div class="profile-area">
    <img src="/ehr/resources/images/user/짱구1.jpg" alt="프로필 이미지"> <br>
    <h3 id="user-name">${user.name}</h3><br>
    <p id="user-email">${user.email}</p><br>
    <br><br>
    <ul>
        <li><a href="/ehr/user/updatePage.do">정보 수정</a></li>
        <li><a href="#" id="userDelete">계정 탈퇴</a></li>
        <c:choose>
            <c:when test="${sessionScope.loginUser.role eq 'admin'}">
                <li><a href="/ehr/user/doRetrieve.do" id="userRetrieve">회원 조회</a></li>
            </c:when>
            <c:otherwise>
            
            </c:otherwise>
        </c:choose>
    </ul>
    <input type="hidden" name="userId" value="${loginUser.userId}" id="userId">
  </div>

  <div class="info-area">
    <div class="section-tabs">
    	<button onclick="document.getElementById('posts').scrollIntoView({ behavior: 'smooth' })">게시글</button>
    	<button onclick="document.getElementById('comments').scrollIntoView({ behavior: 'smooth' })">댓글</button>
    	<button onclick="document.getElementById('tour').scrollIntoView({ behavior: 'smooth' })">관광지</button>
    	<button onclick="document.getElementById('festival').scrollIntoView({ behavior: 'smooth' })">축제</button>
 	</div>

    <div class="section" id="posts">
      <h3>작성한 게시글</h3>
      <div class="post"></div>
      <div class="post"></div>
      <div class="pagination">
        &lt; 1 2 &gt;
      </div>
    </div>

    <div class="section" id="comments">
      <h3>작성한 댓글</h3>
      <div class="comment"></div>
      <div class="comment"></div>
      <div class="comment"></div>
      <div class="pagination">
        &lt; 1 2 &gt;
      </div>
    </div>

    <div class="section" id="tour">
      <h3>관심있는 관광지</h3>
      <div class="scroll-btn">▲</div>
      <div class="cards">
        <div class="card">
          <img src="/path/to/image.jpg" alt="관광지">
          <div class="card-name">제목</div>
        </div>
      </div>
      <div class="scroll-btn">▼</div>
    </div>

    <div class="section" id="festival">
      <h3>관심있는 축제</h3>
      <div class="scroll-btn">▲</div>
      <div class="cards">
        <div class="card">
          <img src="/path/to/image.jpg" alt="축제">
          <div class="card-name">제목</div>
        </div>
      </div>
      <div class="scroll-btn">▼</div>
    </div>
  </div>
</div>

<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
</body>
</html>