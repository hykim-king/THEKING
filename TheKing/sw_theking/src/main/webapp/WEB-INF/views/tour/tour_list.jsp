<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Tour List</title>
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
    <h2>떠나볼지도</h2>
    <p>인기 관광지 알려드릴게요</p>
    <!-- 검색 영역 -->
    <form action="#" name=tourForm method="get" enctype="application/x-www-form-urlencoded">
        <div>
            <input type="search" name="searchWord" id="searchWord" size="15">
            <input type="button" value="검색" id="doRetrieveButton">
        </div>
    
    </form>
    <!-- //검색 영역 -->
    <table border="1">
        <thead>
            <tr>
                <th>사진</th>
                <th>제목</th>
                <th>지역시도</th>
                <th>지역구군</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="vo" items="${list}">
                <tr>
                   <td>
                        <img src="${vo.image.imageUrl}" alt="${vo.name}" width="150" height="100" />
                   </td>
                   <td>${vo.name}</td>
                   <td>${vo.region.regionSido}</td>
                   <td>${vo.region.regionGugun}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>