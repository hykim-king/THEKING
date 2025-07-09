<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:choose>
    <c:when test="${not empty list}">
        <c:forEach var="comment" items="${list}">
            <div class="comment-box">
                <div class="comment-header">
                    <span class="userNickname">${comment.userNickname}</span>
                    <span class="userName">${comment.userName}</span>
                    <span class="date">${comment.regDt}</span> 
                </div>
                <div class="comment-content">
                    ${comment.contents}
                </div>  
            </div>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <p>작성된 댓글이 없습니다.</p>
    </c:otherwise>
</c:choose>

<%-- <p>list 값: ${list}</p> --%>
<%-- <p>list 크기: ${fn:length(list)}</p> --%>