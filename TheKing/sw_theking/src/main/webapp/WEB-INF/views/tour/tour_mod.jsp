<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="CP" value="${pageContext.request.contextPath}" ></c:set>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
    <h2>${TourDTO.name }</h2>
    <h3>${TourDTO.subtitle }</h3>
    
    <div>
        <input type="text"  maxlength="9" name="regionSido" id="regionSido"  value="${TourDTO.region.regionSido }">
        <input type="text"  maxlength="9" name="regionGugun" id="regionGugun"  value="${TourDTO.region.regionGugun }">
    </div>
    <hr>
    
    <div>
       <label>사진</label>
       <label>상세 설명</label>
       <label>댓글</label>
    </div>
<%--         <c:url var="imgUrl" value="${TourDTO.image.imageUrl}" /> --%>
<%--         <img src="${TourDTO.image.imageUrl}" alt="${TourDTO.name}" width="800"> --%>
        <img src="../resources/images/tour/(구)인천일본제58은행지점_1_공공3유형.jpg" alt="${TourDTO.name}" width="800">
    <div>
        <label  for="contents">내용</label>
        <textarea class="contents" id="contents" name="contents">${TourDTO.contents }</textarea>
    </div> 
    <div>
        <label for="address" >주소</label>
        <textarea class="address" id="address" name="address">${TourDTO.address }</textarea>
        
    </div>
    <div>
        <label for="time" >운영시간</label>
        <textarea class="time" id="time" name="time">${TourDTO.time }</textarea>
    </div>
    <div>
        <label for="holiday" >휴일</label>
        <textarea class="holiday" id="holiday" name="holiday">${TourDTO.holiday }</textarea>
        
    </div>
    <div>
        <label for="fee" >입장료</label>
        <textarea class="fee" id="fee" name="fee" >${TourDTO.fee }</textarea>
    </div>
<%-- <p>TourDTO: ${TourDTO}</p> --%>
<%-- <p>ImageDTO: ${TourDTO.image}</p> --%>
<%-- <p>Image URL: ${TourDTO.image.imageUrl}</p> --%>
<%-- <p>regionSido: ${TourDTO.region.regionSido }</p> --%>
<%-- <p>regionGugun: ${TourDTO.region.regionGugun }</p> --%>
    <!-- 댓글 목록 -->
    
    <!-- //댓글 목록 -->
</body>
</html>