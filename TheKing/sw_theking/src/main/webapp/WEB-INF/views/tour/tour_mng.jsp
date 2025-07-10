<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="CP" value="${pageContext.request.contextPath }" />
<%
    java.util.Date now = new java.util.Date();
    pageContext.setAttribute("now", now);
%>
<c:set var="sysDate"><fmt:formatDate value="${now}" pattern="yyyy-MM-dd_HH:mm:ss" /></c:set> 
<%
    boolean isFavorite = (boolean)request.getAttribute("isFavorite");
    int favoriteCount = (Integer)request.getAttribute("favoritesCount");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관광지 상세</title>
<link rel="stylesheet" href="/ehr/resources/css/tour/tour_mng.css"/>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<link rel="stylesheet" href="/ehr/resources/css/comment.css">

<script>
document.addEventListener('DOMContentLoaded', function() {
	
    console.log('DOMContentLoaded');
    
    function isEmpty(value) {
        return value == null || value.trim() === '';
    }
    //댓글용
    const tourNo = document.querySelector("#tourNo").value;
    const commentContentsInput = document.querySelector("#commentContents");
    const commentSubmitBtn = document.querySelector("#commentSubmit");
    //목록 이동용
    const moveToUpdateBtn = document.querySelector("#moveToUpdate");
    
    function loadComments(tourNo) { //고유번호 
    	  console.log("loadComments 호출, tourNo:", tourNo);
    	  $("#commentContainer").load('/ehr/commentsList.do?tourNo='+tourNo, function(response, status, xhr){
    	    console.log("댓글 목록 리로드 상태:", status);
    	  });
    	}
    
    commentSubmitBtn.addEventListener('click', function() {
        const contents = commentContentsInput.value.trim();
        if (!contents) {
            alert('내용을 입력하세요.');
            commentContentsInput.focus();
            return;
        }

        if (!confirm('댓글을 등록하시겠습니까?')) return;

        $.ajax({
            type: 'POST',
            url: '/ehr/comment/tourCommentsadd.do',
            contentType: 'application/json',
            dataType: 'json',
            data: JSON.stringify({ 
            	contents : contents, 
            	targetNo: Number(tourNo) }),
            success: function(res) {
                if (res.messageId === 1) {
                    alert(res.message);
                    commentContentsInput.value = '';
                    loadComments(tourNo);
                } else {
                    alert(res.message);
                }
            },
            error: function(xhr, status, error) {
                console.error("댓글 등록 실패:", status, error);
            }
        });
    });
    
    moveToUpdateBtn.addEventListener('click', function() {
        if (confirm('수정 페이지로 이동합니다.')) {
            window.location.href = '/ehr/tour/doUpdateView.do?tourNo=' + tourNo;
        }
    });

    const moveToListBtn = document.querySelector("#moveToList");
    moveToListBtn.addEventListener('click', function() {
        if (confirm('목록으로 이동합니다.')) {
            window.location.href = '/ehr/tour/doRetrieve.do';
        }
    });
  //좋아요 버튼 
    $('#likeBtn').on('click', function () {
        const $btn = $(this);
        const liked = $btn.data('liked');
        const targetNo = $btn.data('tour-id');

        $.ajax({
          url: '/ehr/favorites/toggleTour.do',
          method: 'POST',
          data: { targetNo: targetNo },
          success: function (res) {
            if (res.success) {
              const imgSrc = res.liked ? '${pageContext.request.contextPath}/resources/images/heart-on.png' : '${pageContext.request.contextPath}/resources/images/heart-off.png';
              $btn.attr('src', imgSrc);
              $btn.data('liked', res.liked);
              $('#likeCount').text(res.count);
            } else {
              alert(res.message || "에러가 발생했습니다.");
            }
          },
          error: function () {
            alert('서버 통신 오류');
          }
        });
      });
});
</script>
</head>

<body>
<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
<div class="container"> 
    <!-- 좋아요, 즐겨찾기 -->
    <div class="top-meta">
        <img 
              id="likeBtn"
              src="${pageContext.request.contextPath}/resources/images/<%= isFavorite ? "heart-on.png" : "heart-off.png" %>"
              data-liked="<%= isFavorite %>"
              data-tour-id="${TourDTO.tourNo }"
              style="width: 24px; cursor: pointer;"
            />
    <span id="likeCount"><%= favoriteCount %></span>
        <img style="width: 24px;" alt="조회수" src="${pageContext.request.contextPath}/resources/images/views.png">
    <span>${TourDTO.views}</span>
    </div>
     <!-- //좋아요, 즐겨찾기 -->
     <div class="title-area">
	    <h2>${TourDTO.name}</h2>
	 </div>

    <!-- 지역 -->
    <div class="region-info">
        <span>${TourDTO.region.regionSido }</span>
        <span>${TourDTO.region.regionGugun }</span>   
    </div>
    <!-- 이미지 리스트 -->
          <div class="img-wrapper">
            <c:url var="imgUrl" value="${TourDTO.image.imageUrl}" />   
            <img src="${imgUrl}" onerror="this.onerror=null; this.src='${pageContext.request.contextPath}/resources/images/defaultImage.jpg';">
         </div>

        <!-- 관광지 정보 -->
        <!-- 관광지 번호 -->
        <input type="hidden" id="tourNo" name="tourNo" value="<c:out value='${TourDTO.tourNo }'/>">
        
        <div class="info-group">
	        <h3>소개</h3>
	        <p>${TourDTO.subtitle}</p>
	    </div>
        <div class="info-group">
	        <h3>상세 정보</h3>
	        <p>${TourDTO.contents}</p>
	    </div>
        <div class="info-group">
	        <h3>주소</h3>
	        <p>${TourDTO.address}</p>
	    </div>
	        <div class="info-group">
	        <h3>연락처</h3>
	        <p>${TourDTO.tel}</p>
	    </div>
        <div class="info-group">
	        <h3>운영시간</h3>
	        <p>${TourDTO.time }</p>
	    </div>
	    <div class="info-group">
            <h3>휴무일</h3>
            <p>${TourDTO.holiday }</p>
        </div>
        <div class="info-group">
            <h3>요금</h3>
            <p>${TourDTO.fee }원</p>
        </div>
      <!-- 버튼 영역 -->
    <c:if test="${sessionScope.loginUser.role =='admin'  }">
	    <div class="button-area">
	        <input type="hidden" id="tourNo" value="${TourDTO.tourNo}">
	        <input type="button" id="moveToList" value="목록">
	        <input type="button" id="moveToUpdate" value="수정"> 
	    </div>
    </c:if>
    <!-- 댓글 입력 영역 -->
    <div class="comment-input-box">
        <div class="comment-input-wrapper">
            <textarea id="commentContents" name="contents" placeholder="댓글을 입력하세요."></textarea>
            <div class="comment-submit-wrapper">
                <input type="button" id="commentSubmit" value="등록" />
            </div>
        </div>
    </div>
    <c:if test="${empty sessionScope.loginUser.userId}">
        <p align="left">※ 등록하려면 로그인이 필요합니다.</p>
    </c:if>
    
    <!-- 댓글 목록 영역 -->
    <div id="commentContainer">
        <jsp:include page="/WEB-INF/views/comment/comment_list.jsp" />
    </div>
    
</div>
<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
</body>
</html>