<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="CP" value="${pageContext.request.contextPath }" />
<c:set var="now" value="<%=new java.util.Date()%>" />
<c:set var="sysDate"><fmt:formatDate value="${now}" pattern="yyyy-MM-dd_HH:mm:ss" /></c:set> 

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script>
document.addEventListener('DOMContentLoaded', function() {
    console.log('DOMContentLoaded');

    function isEmpty(value) {
        return value == null || value.trim() === '';
    }
    
    const tourNoInput document.querySelector("#tourNo");
    console.log(tourNoInput);
    
    // 수정으로 이동 버튼
    const moveToUpdateBtn = document.querySelector("#moveToUpdate");
    console.log(moveToUpdateBtn);
    // 수정 이동 버튼 클릭 시
    moveToUpdateBtn.addEventListener('click', function() {
        
        window.location.href = '/ehr/tour/doUpdateView.do?tourNo='+${TourDTO.tourNo };
        
        // 사용자 확인
        if (!confirm('수정 페이지로 이동합니다.')) {
            return;
        }
    });
    
    // 목록으로 이동 버튼
    const moveToListBtn = document.querySelector("#moveToList");
    console.log(moveToListBtn);

    // 목록 이동 버튼 클릭 시
        moveToListBtn.addEventListener('click', function() {
            
            window.location.href = '/ehr/tour/doRetrieve.do';
            
            // 사용자 확인
            if (!confirm('목록으로 이동합니다.')) {
                return;
            }
        });
});
</script>

<style>
    img{
        width:400px;
        height:200px;
    }
</style>
<body>
	<h2>Tour MOD</h2>
	<hr>
	<a href="/ehr/festival/main.do"><img alt="로고이미지" src="${pageContext.request.contextPath}/resources/images/logo.png"></a>
	<h4>관광지 수정</h4>
	<hr>
	<!-- 버튼 구역 -->
	<div class="button-area">
	    <input type="hidden" id="tourNo" value="${TourDTO.tourNo}">
		<input type="button" id="moveToList"   value="목록">
		<input type="button" id="moveToUpdate" value="수정"> 
	</div>
    <!-- //버튼 구역 -->
    <!-- 지역 -->
	<div>
		<input type="text" maxlength="9" name="regionSido" id="regionSido"
			value="${TourDTO.region.regionSido }"> <input type="text"
			maxlength="9" name="regionGugun" id="regionGugun"
			value="${TourDTO.region.regionGugun }">
	</div>
	<!-- //지역 -->
	<hr>
	<div>
		<label>사진</label> <label>상세 설명</label> <label>댓글</label>
	</div>
	<hr>
	
	<!-- form area -->
	<div class="container">
	       <input type="hidden" name="tourNo" id="tourNo" value="<c:out value='${TourDTO.tourNo }'/>">
	  
            <div>
                <label for="name">관광지명</label>
                <input type="text" id="name" name="name" value="${TourDTO.name }">
            </div>
            <div>
                <label for="subtitle">소제목</label>
                <input type="text" id="subtitle" name="subtitle" value="${TourDTO.subtitle }">
            </div>
            <div>
                <c:url var="imgUrl" value="${TourDTO.image.imageUrl}" />   
                <img src="${imgUrl}" alt="${TourDTO.name}" width="800" />
            </div>
            <div>
                <label for="contents">내용</label>
                <textarea class="contents" id="contents" name="contents">${TourDTO.contents }</textarea>
            </div>
            <div>
                <label for="address">주소</label>
                <textarea class="address" id="address" name="address">${TourDTO.address }</textarea>
        
            </div>
            <div>
                <label for="time">운영시간</label>
                <textarea class="time" id="time" name="time">${TourDTO.time }</textarea>
            </div>
            <div>
                <label for="holiday">휴일</label>
                <textarea class="holiday" id="holiday" name="holiday">${TourDTO.holiday }</textarea>
        
            </div>
            <div>
                <label for="fee">입장료</label>
                <textarea class="fee" id="fee" name="fee">${TourDTO.fee }</textarea>
            </div>	   
</div>

	<%-- <p>TourDTO: ${TourDTO}</p> --%>
	<%-- <p>Image URL: ${imgUrl}</p> --%>
	<%-- <p>regionSido: ${TourDTO.region.regionSido }</p> --%>
	<%-- <p>regionGugun: ${TourDTO.region.regionGugun }</p> --%>
	<!-- 댓글 목록 -->

	<!-- //댓글 목록 -->
</body>
</html>