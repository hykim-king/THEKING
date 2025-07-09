<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 목록</title>
<link rel="stylesheet" href="<c:url value='/resources/css/board_list.css' />">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script>
document.addEventListener('DOMContentLoaded', function(){
    document.getElementById("moveToReg").addEventListener("click", function(){
        window.location.href = "<c:url value='/board/write.do'/>";
    });

    document.getElementById("doRetrieveButton").addEventListener("click", function(){
        document.forms['searchForm'].submit();
    });
});
</script>
</head>
<body>
    <h2>게시판 목록</h2>
    <hr/>

    <!-- 검색 영역 -->
    <form name="searchForm" method="get" action="<c:url value='/board/list.do'/>">
      <div>
        <label for="searchDiv">검색조건</label>
        <select name="searchDiv" id="searchDiv">
           <option value="">전체</option> 
           <option value="10">제목</option> 
           <option value="20">내용</option> 
           <option value="30">작성자</option> 
        </select>
        <input type="text" name="searchWord" id="searchWord" size="20" placeholder="검색어 입력">
        <input type="submit" value="조회" id="doRetrieveButton">
        <input type="button" value="등록" id="moveToReg">
      </div>
    </form>
    <!-- //검색 영역 end -->

    <table border="1" id="boardTable">
        <colgroup>
            <col width="10%">
            <col width="50%">
            <col width="15%">
            <col width="15%">
            <col width="10%">
        </colgroup>
        <thead>
            <tr>
                <th>번호</th>
                <th>제목</th>
                <th>작성자</th>
                <th>등록일</th>
                <th>조회수</th>
            </tr>
        </thead>
        <tbody>
            <c:choose>
                <c:when test="${not empty list}">
                    <c:forEach var="board" items="${list}">
                        <tr>
                            <td>${board.boardNo}</td>
                            <td>
                                <a href="<c:url value='/board/view.do?boardNo=${board.boardNo}'/>">
                                    ${board.title}
                                </a>
                            </td>
                            <td>${board.regId}</td>
                            <td>${board.regDate}</td>
                            <td>${board.views}</td>
                        </tr>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <tr>
                        <td colspan="5">조회된 게시물이 없습니다.</td>
                    </tr>
                </c:otherwise>
            </c:choose>
        </tbody>
    </table>
</body>
</html>
