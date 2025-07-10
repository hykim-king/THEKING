<%@page import="com.pcwk.ehr.cmn.PcwkString"%>
<%@page import="com.pcwk.ehr.cmn.SearchDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String sido = request.getParameter("sido");
    request.setAttribute("sido", sido);

	int bottomCount = 5;
	int pageSize    = 0;//페이지 사이즈
	int pageNo      = 0;//페이지 번호
	int maxNum      = 0;//총글수
	
	String url      = "";//호출URL
	String scriptName="";//자바스크립트 이름
	
	
	//request: 요청 처리를 할수 있는 jsp 내장 객체
	String totalCntString = request.getAttribute("totalCnt").toString();
	//out.print("totalCntString:"+totalCntString);
	maxNum = Integer.parseInt(totalCntString);	
	
	SearchDTO  paramVO = (SearchDTO)request.getAttribute("search");   
	pageSize = paramVO.getPageSize();
	pageNo   = paramVO.getPageNo();
	
	String cp = request.getContextPath();
	   //out.print("cp:"+cp);
	   
	   url = cp+"/festival/main.do";
	   //out.print("url:"+url);
	   
	   scriptName = "pagerDoRetrieve";
	   
	   String pageHtml=PcwkString.renderingPager(maxNum, pageNo, pageSize, bottomCount, url, scriptName);
	   
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Festival Main</title>
<link rel="stylesheet" href="/ehr/resources/css/festival_main.css">
<script type="text/javascript">
//페이징 
function pagerDoRetrieve(url, pageNo){   
    console.log('pagerDoRetrieve pageNo:'+pageNo);
    console.log('pagerDoRetrieve url:'+url);
    
    //form
    const searchForm = document.searchForm;
    searchForm.pageNo.value =pageNo;
    
    searchForm.action=url;
    
    searchForm.submit();     
    
}
</script>
</head>
<body>

<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
	<div class="container">
    <h1 style="text-align: center;">축제 목록</h1>

		<!-- 검색 영역 -->
		<form action="/ehr/festival/main.do" method="get" class="search" name="searchForm">
			<div style="display: flex; gap: 10px; align-items: center;">
				<input type="date" name="date"> 
				<select name="sido">
					<option value="">전체</option>
					<option ${sido == '서울특별시' ? 'selected' : ''}>서울특별시</option>
					<option ${sido == '경기도' ? 'selected' : ''}>경기도</option>
					<option ${sido == '인천광역시' ? 'selected' : ''}>인천광역시</option>
					<option ${sido == '부산광역시' ? 'selected' : ''}>부산광역시</option>
					<option ${sido == '대구광역시' ? 'selected' : ''}>대구광역시</option>
					<option ${sido == '광주광역시' ? 'selected' : ''}>광주광역시</option>
					<option ${sido == '대전광역시' ? 'selected' : ''}>대전광역시</option>
					<option ${sido == '울산광역시' ? 'selected' : ''}>울산광역시</option>
					<option ${sido == '강원특별자치도' ? 'selected' : ''}>강원특별자치도</option>
					<option ${sido == '충청북도' ? 'selected' : ''}>충청북도</option>
					<option ${sido == '충청남도' ? 'selected' : ''}>충청남도</option>
					<option ${sido == '전북특별자치도' ? 'selected' : ''}>전북특별자치도</option>
					<option ${sido == '전라남도' ? 'selected' : ''}>전라남도</option>
					<option ${sido == '경상북도' ? 'selected' : ''}>경상북도</option>
					<option ${sido == '경상남도' ? 'selected' : ''}>경상남도</option>
					<option ${sido == '제주특별자치도' ? 'selected' : ''}>제주특별자치도</option>
				</select>
				<select style="width:100px" name="pageSize" id="pageSize">
		            <option value="10" ${search.pageSize == 10 ? 'selected' : ''}>10개씩</option>
		            <option value="20" ${search.pageSize == 20 ? 'selected' : ''}>20개씩</option>
		            <option value="30" ${search.pageSize == 30 ? 'selected' : ''}>30개씩</option>
		        </select>
		        <input type="hidden" name="pageNo" id="pageNo">
				<button type="submit" id="searchButton">검색</button>
			</div>
		</form>
 
        <c:if test="${sessionScope.loginUser.role =='admin'  }">
        <!-- 등록 버튼 -->
        <button class="register-btn" onclick="window.location.href='/ehr/festival/doSave.do';">+ 축제 등록</button>
        </c:if>
		

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
									<td><img class="listImage" alt="이미지"
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
		<!-- paging -->
		    <%
		        out.print(pageHtml);
		    %>
		    <!--// paging end -->
	</div>
<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
</body>
</html>