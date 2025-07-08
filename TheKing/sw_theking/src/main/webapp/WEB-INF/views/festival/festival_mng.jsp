<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>축제 상세보기</title>
<style>
	img{
		width:300px;
		height:300px;
	}
</style>
</head>
<body>
	<h2>Festival mng</h2>
	<hr>
	<!-- 이미지 등록 -->
	<button onclick="window.location.href='/ehr/festival/test.do?festaNo=${dto.festaNo}';">이미지 등록</button> 
	<div>
		<c:choose>
			<c:when test="${imageList.size() > 0 }">
				<c:forEach var="imageDTO" items="${imageList }">
					<img alt="이미지" src="${pageContext.request.contextPath}/resources/images/festival/${imageDTO.saveName}">
				</c:forEach>
			</c:when>
			<c:otherwise>
				<p>이미지 없음</p>
			</c:otherwise>
		</c:choose>
	</div>
	<div>
		<h3>축제 명</h3>
		<p>${dto.name }</p>
	</div>
	<div>
		<h3>소제목</h3>
		<p>${dto.subtitle }</p>
	</div>
	<div>
		<h3>내용</h3>
		<p>${dto.contents }</p>
	</div>
	<div>
		<h3>조회수</h3>
		<p>${dto.views }</p>
	</div>
	<div>
		<h3>주소</h3>
		<p>${dto.address }</p>
	</div>
	<div>
		<h3>연락처</h3>
		<p>${dto.tel }</p>
	</div>
	<div>
		<h3>요금</h3>
		<p>${dto.fee }원</p>
	</div>
	<div>
		<h3>시작일</h3>
		<p>${dto.startDate }</p>
	</div>
	<div>
		<h3>종료일</h3>
		<p>${dto.endDate }</p>
	</div>
	<button onclick="window.location.href='/ehr/festival/doUpdate.do?festaNo=${dto.festaNo}'">수정</button>
</body>
</html>