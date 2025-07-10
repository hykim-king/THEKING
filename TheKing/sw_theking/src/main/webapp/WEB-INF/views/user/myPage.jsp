<%@page import="com.pcwk.ehr.cmn.PcwkString"%>
<%@page import="com.pcwk.ehr.cmn.SearchDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
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
    <img src="${pageContext.request.contextPath}/resources/images/user/${user.profileImage.saveName}" alt="프로필 이미지">
    <h3 id="user-name">${user.name}</h3><br>
    <p id="user-email">${user.email}</p><br>
    <br><br>
    <ul>
        <li><a href="/ehr/user/updatePage.do">정보 수정</a></li>
        <li><a href="/ehr/user/passwordPage.do">비밀번호변경</a></li>
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
      <div class="post">
<%--         <c:forEach var="post" items="${posts}"> --%>
<!--           <div class="post"> -->
<%--           <c:choose> --%>
<%--               <c:when test="${post.div == 10}"> --%>
<%--                 <a href="${pageContext.request.contextPath}/tour/doSelectOne.do?div=10&seq=${post.seq}"> --%>
<%--                     ${post.title} --%>
<!--                 </a> -->
<%--               </c:when> --%>
<%--               <c:when test="${post.div == 20}"> --%>
<%--                 <a href="${pageContext.request.contextPath}/tour/doSelectOne.do?div=20&seq=${post.seq}"> --%>
<%--                     ${post.title} --%>
<!--                 </a> -->
<%--               </c:when> --%>
<%--             </c:choose> --%>
<!--           </div> -->
<%--         </c:forEach> --%>
    </div>

    <div class="section" id="comments">
      <h3>작성한 댓글</h3>
      <div class="comment-list">
	    <c:forEach var="comment" items="${comments}">
	      <div class="comment">
	      <c:choose>
			  <c:when test="${comment.tableName == 'TOUR'}">
			    <a href="${pageContext.request.contextPath}/tour/doSelectOne.do?tourNo=${comment.targetNo}">
			        ${comment.contents}
			    </a>
			  </c:when>
			  <c:when test="${comment.tableName == 'festival'}">
			    <a href="${pageContext.request.contextPath}/${comment.tableName}/doSelectOne.do?festaNo=${comment.targetNo}">
			        ${comment.contents}
			    </a>
			  </c:when>
			  <c:otherwise>
			    <a href="${pageContext.request.contextPath}/${comment.tableName}/doSelectOne.do?targetNo=${comment.targetNo}">
			        ${comment.contents}
			    </a>
			  </c:otherwise>
			</c:choose>
	      </div>
	  	</c:forEach>
  	  </div>
    </div>

    <div class="section" id="tour">
      <h3>관심있는 관광지</h3>
      <div class="cards">
    	<c:forEach var="tour" items="${favoriteTours}">
      	<div class="card">
        	<img src="${pageContext.request.contextPath}/resources/images/tour/${tour.image.saveName}" alt="${tour.name} 관광지 이미지" width="100" height="100" />
        	<div class="card-name">${tour.name}</div>
      	</div>
    	</c:forEach>
    	<c:if test="${empty favoriteTours}">
      		<p>즐겨찾기한 관광지가 없습니다.</p>
    	</c:if>
  	  </div>
    </div>

    <div class="section" id="festival">
      <h3>관심있는 축제</h3>
      <div class="cards">
    	<c:forEach var="festival" items="${favoriteFestivals}">
      	<div class="card">
        	<a href="${pageContext.request.contextPath}/festival/doSelectOne.do?festaNo=${festival.festaNo}">
        	   <img src="${pageContext.request.contextPath}/resources/images/festival/${festival.image.saveName}" alt="${festival.name} 축제 이미지" width="100" height="100" />
        	</a>
        	<div class="card-name">${festival.name}</div>
      	</div>
    	</c:forEach>
    	<c:if test="${empty favoriteFestivals}">
      		<p>즐겨찾기한 축제가 없습니다.</p>
    	</c:if>
  	  </div>
    </div>
  </div>
</div>

<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
</body>
</html>