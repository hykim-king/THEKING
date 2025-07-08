<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Festival Main</title>
<link rel="stylesheet" href="/ehr/resources/css/festival_main.css">
</head>
<body>

	<div class="header">
		<div class="nav">
			<span>떠나볼지도</span> 
			<a href="#">관광지</a>
			<a href="#">축제</a> 
			<a href="#">게시판</a> 
			<a href="#">공지사항</a>
		</div>
		<div class="login">
			<a href="#">로그인</a> 
			<a href="#">회원가입</a>
		</div>
	</div>
	<div class="container">
    <h1 style="text-align: center;">축제 목록</h1>

		<!-- 검색 영역 -->
		<form action="/ehr/festival/main.do" method="get" class="search">
			<div style="display: flex; gap: 10px; align-items: center;">
				<input type="date" name="date"> <select name="sido">
					<option value="">전체</option>
					<option>서울특별시</option>
					<option>경기도</option>
					<option>인천광역시</option>
					<option>부산광역시</option>
					<option>대구광역시</option>
					<option>광주광역시</option>
					<option>대전광역시</option>
					<option>울산광역시</option>
					<option>강원특별자치도</option>
					<option>충청북도</option>
					<option>충청남도</option>
					<option>전북특별자치도</option>
					<option>전라남도</option>
					<option>경상북도</option>
					<option>경상남도</option>
					<option>제주특별자치도</option>
				</select>
				<button type="submit" id="searchButton">검색</button>
			</div>
		</form>
        
        <select style="width:100px" name="pageSize" id="pageSize">
            <option value="10">10개씩</option>
            <option value="20">20개씩</option>
            <option value="30">30개씩</option>
        </select>

		<!-- 등록 버튼 -->
		<button class="register-btn"
			onclick="window.location.href='/ehr/festival/doSave.do';">+
			축제 등록</button>

		<!-- 테이블 리스트 -->
		<div class="table-wrapper">
			<table>
				<thead>
					<tr>
						<th>이미지</th>
						<th>제목</th>
						<th>지역</th>
						<th>조회수</th>
						<th>시작일</th>
						<th>종료일</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${list.size() > 0}">
							<c:forEach var="vo" items="${list}">
								<tr>
									<td><img alt="이미지"
										src="${pageContext.request.contextPath}/resources/images/festival/${vo.getImage().getSaveName()}">
									</td>
									<td><a
										href="/ehr/festival/doSelectOne.do?festaNo=${vo.festaNo}">${vo.name}</a>
									</td>
									<td>${vo.getRegion().getRegionSido()}</td>
									<td>${vo.views}</td>
									<td>${vo.startDate}</td>
									<td>${vo.endDate}</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="6">등록된 축제가 없습니다.</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
		</div>
	</div>

</body>
</html>