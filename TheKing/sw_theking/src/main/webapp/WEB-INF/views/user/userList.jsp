<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/ehr/resources/css/user/userList.css">
<title>떠나볼지도 회원 조회</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="/ehr/resources/js/common.js"></script>
<script>
document.addEventListener('DOMContentLoaded', function(){
    console.log('DOMContentLoaded')
    
    const deleteButtons = document.querySelectorAll(".deleteUserBtn");
    
    deleteButtons.forEach(function (button) {
        button.addEventListener("click", function (event) {
            const targetUserId = button.getAttribute("data-userid");
            console.log("강퇴 버튼 클릭됨:", targetUserId);

            if (confirm(targetUserId + "님을 정말 삭제하시겠습니까?")) {
                $.ajax({
                    type: "POST",
                    url: "/ehr/user/deleteUser.do",
                    dataType: "json",
                    data: {
                        targetUserId: targetUserId
                    },
                    success: function (response) {
                        console.log("삭제 성공:", response);
                        alert(response.message);
                        location.reload(); // 페이지 새로고침
                    },
                    error: function (xhr, status, error) {
                        console.error("삭제 실패:", error);
                        alert("오류가 발생했습니다.");
                    }
                });
            }
        });
    });
});
</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
  <!-- 검색 구분 -->
  <form action="/ehr/user/doRetrieve.do" name="userForm" method="get" enctype="application/x-www-form-urlencoded">
    <div class="searchClass"> 
      <select name="searchDiv" id="searchDiv">
        <option value="">전체</option>
        <option value="10">아이디</option>
        <option value="20">이름</option>
        <option value="30">닉네임</option>
        <option value="40">이름+닉네임</option>
      </select>
      <input type="search" name="searchWord" id="searchWord" size="20">
      <select name="pageSize" id="pageSize">
        <option value="10">10</option>
        <option value="20">20</option>
        <option value="30">30</option>
        <option value="50">50</option>
        <option value="100">100</option>
      </select>
      <input type="submit" value="조회" id="doRetrieveButton">
    </div>
  </form>
  <!-- 검색 영역 end -->
  <table border="1" id="ListTable">
    <thead>
      <tr>
        <th>유저번호</th>
        <th>아이디</th>
        <th>패스워드</th>
        <th>이름</th>
        <th>닉네임</th>
        <th>이메일</th>
        <th>전화번호</th>
        <th>주소</th>
        <th>가입날짜</th>
        <th></th>
      </tr>
    </thead>
    <tbody>
        <c:choose>
            <c:when test="${list.size() > 0 }">
                <c:forEach var="vo" items="${list }">
                    <tr>
                        <td>${vo.userNo }</td>
                        <td>${vo.userId }</td>
                        <td>${vo.password }</td>
                        <td>${vo.name }</td>
                        <td>${vo.nickname }</td>
                        <td>${vo.email }</td>
                        <td>${vo.mobile }</td>
                        <td>${vo.address }</td>
                        <td>${vo.regDt }</td>
                        <td><input type="button" value="강퇴" class="deleteUserBtn" data-userid="${vo.userId}"></td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr>
                    <td colspan="99">조회된 데이터가 없습니다.</td>
                </tr>
            </c:otherwise>
        </c:choose>
    </tbody>
  </table>
 <jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
</body>
</html>