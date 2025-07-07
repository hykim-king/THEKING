<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Festival Main</title>
<style>
    table, th, td{
        border: 1px solid;
        border-collapse: collapse;
        margin-left: auto;
		margin-right: auto;
	
    }
    
    img{
    	width:100px;
    	height:100px;
    }
    form{
    	margin-left: auto;
		margin-right: auto;
    }
    input, select{
    	border:2px solid;
    	border-color:grey;
    	border-radius:10px;
    	width:300px;
    	height:40px;
    }
    #searchButton{
    	border:2px solid;
    	border-radius:5px;
    	border-color:grey;
    	height:40px;
    }
    .search{
    	margin:auto;
    	padding:50px;
    	display:flex;
    }
</style>
</head>
<body>
    <h2>Festival list</h2>
    <hr>
    <div class="search">
    	<form action="/ehr/festival/main.do" method="get">
    		<input type="date" name="date">
    		<select name="sido">
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
    	</form>
    </div>
    
    <div class="contents">
    	<button onclick="window.location.href='/ehr/festival/doSave.do';">등록</button>
	    <table>
	        <tr>
	        	<th>이미지</th>
	            <th>제목</th>
	            <th>지역</th>
	            <th>조회수</th>
	            <th>시작일</th>
	            <th>종료일</th>
	        </tr>
	        <c:choose>
	        	<c:when test="${list.size() > 0 }">
	        		<c:forEach var="vo" items="${list }">
		        		<tr>
		        			<td><img alt="이미지" src="${pageContext.request.contextPath}/resources/images/festival/${vo.getImage().getSaveName()}"></td>
		        			<td><a href="/ehr/festival/doSelectOne.do?festaNo=${vo.festaNo }">${vo.name }</a></td>
		        			<td>${vo.getRegion().getRegionSido() }</td>
		        			<td>${vo.views }</td>
		        			<td>${vo.startDate }</td>
		        			<td>${vo.endDate }</td>
		        		</tr>
	        		</c:forEach>
	        	</c:when>
	        </c:choose>
	    </table>
	</div>	
</body>
</html>