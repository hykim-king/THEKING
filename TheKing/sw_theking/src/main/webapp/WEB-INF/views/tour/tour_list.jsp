<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Tour List</title>
</head>
<body>
    <h2>떠나볼지도</h2>
    <p>인기 관광지 알려드릴게요<p>
    <table border="1">
        <thead>
            <th>사진</th>
            <th>제목</th>
            <th>지역시도</th>
            <th>지역구군</th>
        </thead>
        <tbody>
            <c:forEach var="vo" items="${list }">
                <tr>
                   <td>${vo.Image() }</td>
                   <td>${vo.title }</td>
                   <td>${vo.regionSido }</td>
                   <td>${vo.regionGugun }</td>
            </c:forEach>
        </tbody>
    </table>

</body>
</html>