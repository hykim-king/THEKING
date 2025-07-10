<%@page import="com.pcwk.ehr.cmn.PcwkString"%>
<%@page import="com.pcwk.ehr.cmn.SearchDTO"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="CP" value="${pageContext.request.contextPath }"></c:set>
<c:set var="now" value="<%=new java.util.Date()%>" />
<c:set var="sysDate"><fmt:formatDate value="${now}" pattern="yyyy-MM-dd_HH:mm:ss" /></c:set> 
<%
  int bottomCount = 5;
  int pageSize    = 0; //페이지 사이즈
  int pageNo      = 0; // 페이지 번호
  int maxNum      = 0; //총 글 수
  
  String url = "";//호출URL
  String scriptName = "";//자바스크립트 이동
  
  //request : 요청 처리를 할 수 있는 jsp 내장 객체
  String totalCntString = request.getAttribute("totalCnt").toString();
  //out.print("totalCntString" + totalCntString);
  
  maxNum = Integer.parseInt(totalCntString);
  
  SearchDTO paramVO = (SearchDTO)request.getAttribute("search");
  pageSize = paramVO.getPageSize();
  pageNo = paramVO.getPageNo();
  
  //out.print("pageSize:"+pageSize);
  //out.print("pageNo:"+pageNo);
  
  String cp = request.getContextPath();
  //out.print("cp:"+cp);
  
  url = cp+"/board/doRetrieve.do";
//  out.print("url:"+url);
  
  scriptName = "pagerDoRetrieve";
  
  String pageHtml = PcwkString.renderingPager(maxNum, pageNo, pageSize, bottomCount, url, scriptName);
 // System.out.print(pageHtml);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${CP}/resources/css/board_list.css">
<title>Insert title here</title>
<script src="${CP}/resources/js/common.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script>
document.addEventListener('DOMContentLoaded',function(){
    console.log('DOMContentLoaded');
    const doRetrieveButton = document.getElementById("doRetrieve");
    console.log(doRetrieveButton);
    
    const divInput = document.getElementById("div");
    console.log(divInput);
    
    if('${sessionScope.loginUser.userId}' !== ''){
    	const moveToRegButton = document.getElementById("moveToReg");
        console.log(moveToRegButton);

	    moveToRegButton.addEventListener("click",function(event){
	        console.log('moveToRegButton click');
	        
	        if(confirm('등록 화면으로 이동 하시겠습니까?')===false)return;
	        
	        window.location.href="/ehr/board/doSaveView.do?div="+ divInput.value;
	    });
    }
    doRetrieveButton.addEventListener("click",function(){
        console.log('doRetrieveButton click');
        doRetrieve(1);
    });
    
    
    function doRetrieve(pageNo){
        console.log('doRetrieve pageNo:' + pageNo);
        
        //form
        const userForm = document.userForm;
        userForm.pageNo.value = pageNo;
        
        userForm.action="/ehr/board/doRetrieve.do";
        
        userForm.submit();
    }
    

    
    
});
//paging
function pagerDoRetrieve(url,pageNo){
    console.log('pageDoRetrieve pageNo:' + pageNo);
    console.log('pageDoRetrieve url:' + url);
    
    //form
    const userForm = document.userForm;
    userForm.pageNo.value = pageNo;
    
    userForm.action=url;
    
    userForm.submit();
}
</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
<div class="container">
  <c:choose>
    <c:when test="${divValue==10}">
      <h2>자유게시판 - 목록</h2>
    </c:when>
    <c:otherwise>
      <h2>공지사항 - 목록</h2>
    </c:otherwise>
  </c:choose>

  <hr class="title-underline">

  <form action="#" name="userForm" id="userForm" method="get" class="search-form">
    <input type="hidden" name="div" id="div" value="<c:out value='${divValue }'/>">
    <input type="hidden" name="pageNo" id="pageNo">
    
    <div class="search-group">
      <label for="searchDiv">구분</label>
      <select name="searchDiv" id="searchDiv">
        <option value="">전체</option>
        <option value="10" <c:if test="${search.searchDiv == 10}">selected</c:if>>제목</option>
        <option value="20" <c:if test="${search.searchDiv == 20}">selected</c:if>>내용</option>
        <option value="30" <c:if test="${search.searchDiv == 30}">selected</c:if>>SEQ</option>
        <option value="40" <c:if test="${search.searchDiv == 40}">selected</c:if>>제목+내용</option>
      </select>

      <input type="search" name="searchWord" id="searchWord" size="15" value="${search.searchWord}">
      
      <select name="pageSize" id="pageSize">
        <option value="10" <c:if test="${search.pageSize ==10 }">selected</c:if>>10</option>
        <option value="20" <c:if test="${search.pageSize ==20 }">selected</c:if>>20</option>
        <option value="30" <c:if test="${search.pageSize ==30 }">selected</c:if>>30</option>
      </select>

      <input type="button" value="조회" id="doRetrieve">
      <c:if test="${(not empty sessionScope.loginUser.userId && divValue==10)||sessionScope.loginUser.role=='admin' }">
           <input type="button" value="등록" id="moveToReg">
      </c:if>
    </div>
  </form>

  <div class="table-wrapper">
    <table id="listTable">
      <colgroup>
        <col width="10%">
        <col width="60%">
        <col width="10%">
        <col width="10%">
        <col width="10%">
        <col width="0%">
      </colgroup>
      <thead>
        <tr>
          <th>번호</th>
          <th>제목</th>
          <th>글쓴이</th>
          <th>작성일</th>
          <th>조회수</th>
          <th style="display: none;">seq</th>
        </tr>
      </thead>
      <tbody>
        <c:choose>
          <c:when test="${list.size() > 0}">
            <c:forEach var="vo" items="${list}">
              <tr>
                <td class="table-cell text-center"><c:out value="${vo.no}" /></td>
                <td class="table-cell text-left highlight">
                  <a href="${CP }/board/doSelectOne.do?div=${divValue}&seq=${vo.seq}&regId=${vo.regId}">
                    <c:out value="${vo.title}" />
                  </a>
                </td>
                <td class="table-cell text-center"><c:out value="${vo.modId}" /></td>
                <td class="table-cell text-left"><c:out value="${vo.modDt}" /></td>
                <td class="table-cell text-right"><c:out value="${vo.readCnt}" /></td>
                <td style="display: none;"><c:out value="${vo.seq}" /></td>
              </tr>
            </c:forEach>
          </c:when>
          <c:otherwise>
            <tr>
              <td colspan="99" class="table-cell text-center">데이터가 없습니다.</td>
            </tr>
          </c:otherwise>
        </c:choose>
      </tbody>
    </table>
  </div>

  <!-- paging -->
  <%
    out.print(pageHtml);
  %>
</div>
<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
</body>
</html>