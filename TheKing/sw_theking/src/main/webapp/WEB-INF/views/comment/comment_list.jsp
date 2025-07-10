<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script>
function commentDelete(comNo,tableName,targetNo){
	if(confirm("댓글을 삭제하시겠습니까??")===true){
		  let url = '/ehr/comment/doDelete.do?';
		  url += 'comNo=' + comNo;
		  url += '&tableName=' + tableName;
	      url += '&targetNo=' + targetNo;
		    
	      window.location.href = url;
	}else{
		return;
	}

}
</script>

<c:choose>
    <c:when test="${not empty list}">
        <c:forEach var="comment" items="${list}">
            <div class="comment-box">
                <div class="comment-header">
                    <span class="user-nickname">${comment.userNickname}</span>
                    <span class="user-name">${comment.userName}</span>
                    <span class="comment-date">${comment.regDt}</span>
                </div>
                <div class="comment-content">
                    ${comment.contents}
                </div>
                <c:if test="${sessionScope.loginUser.userId eq comment.userId}">
                    <div class="comment-submit-wrapper">
                     <input type="button" onclick="javascript:commentDelete(${comment.comNo},'${comment.tableName }',${comment.targetNo })" value="삭제" />
                    </div>
                </c:if>
            </div>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <p class="no-comment">작성된 댓글이 없습니다.</p>
    </c:otherwise>
</c:choose>

<%-- <p>list 값: ${list}</p> --%>
<%-- <p>list 크기: ${fn:length(list)}</p> --%>