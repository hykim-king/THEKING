<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    boolean isFavorite = (boolean)request.getAttribute("isFavorite");
    int favoriteCount = (Integer)request.getAttribute("favoritesCount");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>축제 상세보기</title>
<script src="/ehr/resources\asset\js\common.js"></script>
<script
    src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" href="/ehr/resources/css/festival_mng.css">
<link rel="stylesheet" href="/ehr/resources/css/comment.css">
<script type="text/javascript">
document.addEventListener("DOMContentLoaded", function () {
	if('${sessionScope.loginUser.role  }'==='admin'){
	const doDeleteButton = document.getElementById('doDelete');
	
	
	//삭제 버튼
	doDeleteButton.addEventListener('click',function(){
		           
		if (confirm('삭제 하시겠습니까?') === false)
	        return;
	
	    //ajax 비동기 통신
	    $.ajax({
	        type : "POST", //GET/POST
	        url : "/ehr/festival/doDelete.do", //서버측 URL
	        asyn : "true", //비동기
	        dataType : "html",//서버에서 받을 데이터 타입
	        data : { //파라메터
	            "festaNo" : '${dto.festaNo}'
	        },
	        success : function(response) {//요청 성공
	            console.log("success:" + response)
	            //문자열 : javascript 객체
	            const message = JSON.parse(response);
	            //{"messageId":1,"message":"제목등록되었습니다.","no":0,"totalCnt":0,"pageSize":0,"pageNo":0}
	            if (message.messageId === 1) { //등록 성공
	                alert(message.message);
	
	                //목록 화면으로 이동
	                window.location.href = '/ehr/festival/main.do';
	            } else {
	                alert(message.message);
	            }
	        },
	        error : function(response) {//요청 실패
	            console.log("error:" + response)
	        }
	
	    });
	});
	
	}
	
	//좋아요 버튼 
	$('#likeBtn').on('click', function () {
	    const $btn = $(this);
	    const liked = $btn.data('liked');
	    const targetNo = $btn.data('festival-id');

	    $.ajax({
	      url: '/ehr/favorites/toggle.do',
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
	//댓글용
    const festaNo = ${dto.festaNo};
    const commentContentsInput = document.querySelector("#commentContents");
    const commentSubmitBtn = document.querySelector("#commentSubmit");

    
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
            url: '/ehr/comment/festaNoCommentsadd.do',
            contentType: 'application/json',
            dataType: 'json',
            data: JSON.stringify({ 
                contents : contents, 
                targetNo: Number(festaNo) }),
            success: function(res) {
                if (res.messageId === 1) {
                    alert(res.message);
                    window.location.href='/ehr/festival/doSelectOne.do?festaNo='+festaNo;
                } else {
                    alert(res.message);
                }
            },
            error: function(xhr, status, error) {
                console.error("댓글 등록 실패:", status, error);
            }
        });
    });
	
});


</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
	<!-- 이미지 등록 -->

	<!--<button onclick="window.location.href='/ehr/festival/test.do?festaNo=${dto.festaNo}';">이미지 등록</button> -->
<div class="container">
	<div style="float:right;">
	    <img 
			  id="likeBtn"
			  src="${pageContext.request.contextPath}/resources/images/<%= isFavorite ? "heart-on.png" : "heart-off.png" %>"
			  data-liked="<%= isFavorite %>"
			  data-festival-id="${dto.festaNo }"
			  style="width: 24px; cursor: pointer;"
			/>
	<span id="likeCount"><%= favoriteCount %></span>
	    <img style="width: 24px;" alt="조회수" src="${pageContext.request.contextPath}/resources/images/views.png">
	<span>${dto.views}</span>
	</div>
    <h2>축제 상세보기</h2>

    <!-- 이미지 등록 -->
<!-- <div class="btn-group">
        <button onclick="location.href='/ehr/festival/test.do?festaNo=${dto.festaNo}'">이미지 등록</button>
    </div>  -->
    
    <!-- 이미지 리스트 -->
    <div class="image-wrapper">
        <c:choose>
            <c:when test="${imageList.size() > 0}">
                <c:forEach var="imageDTO" items="${imageList}">
                    <img alt="이미지" src="${pageContext.request.contextPath}/resources/images/festival/${imageDTO.saveName}">
                </c:forEach>
            </c:when>
            <c:otherwise>
                <p>이미지 없음</p>
            </c:otherwise>
        </c:choose>
    </div>

    <!-- 정보 영역 -->
    <div class="info-group">
        <h3>축제 명</h3>
        <p>${dto.name}</p>
    </div>

    <div class="info-group">
        <h3>소제목</h3>
        <p>${dto.subtitle}</p>
    </div>

    <div class="info-group">
        <h3>내용</h3>
        <p>${dto.contents}</p>
    </div>

    <div class="info-group">
        <h3>주소</h3>
        <p>${dto.address}</p>
    </div>

    <div class="info-group">
        <h3>연락처</h3>
        <p>${dto.tel}</p>
    </div>

    <div class="info-group">
        <h3>요금</h3>
        <p>${dto.fee} 원</p>
    </div>

    <div class="info-group">
        <h3>시작일</h3>
        <p>${dto.startDate}</p>
    </div>

    <div class="info-group">
        <h3>종료일</h3>
        <p>${dto.endDate}</p>
    </div>
    <c:if test="${sessionScope.loginUser.role =='admin'  }">
    <div class="btn-group">
        <button onclick="location.href='/ehr/festival/doUpdate.do?festaNo=${dto.festaNo}'">수정</button>
        <button id="doDelete">삭제</button>
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
        <p>※ 등록하려면 로그인이 필요합니다.</p>
    </c:if>
	
	<!-- 댓글 목록 영역 -->
	<div id="commentContainer">
	    <jsp:include page="/WEB-INF/views/comment/comment_list.jsp" />
	</div>
	
</div>
<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>

</body>
</html>