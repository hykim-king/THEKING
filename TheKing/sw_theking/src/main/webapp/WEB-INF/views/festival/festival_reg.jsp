<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
 <script src="/ehr/resources/js/common.js"></script>
<head>
<meta charset="UTF-8">
<title>축제 등록</title>
<link rel="stylesheet" href="/ehr/resources/css/festival_reg.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>
<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>

    <div class="container">
        <div class="logo-container">
		    <h1>떠나볼지도</h1>
		    <p>Get going</p>
		    <a href="/ehr/festival/main.do">
                <img alt="로고이미지" src="${pageContext.request.contextPath}/resources/images/logo.png">
            </a>
		</div>
        <hr>
        <h4>상세 페이지 등록</h4>

        <form action="doSave.do" method="post" enctype="multipart/form-data">
            <div>
                <label for="name">제목</label>
                <input type="text" name="name" id="name" maxlength="20">
            </div>
            <div>
                <label for="subtitle">소제목</label>
                <input type="text" name="subtitle" id="subtitle" maxlength="50">
            </div>          
            <div>
                <label>상세정보</label>
                <textarea name="contents" id="contents"></textarea>
            </div>

            <div>
			    <label for="imageFile">이미지</label>
			    <input type="file" name="imageFile" id="imageFile">
			</div>

            <div>
                <label for="address">주소</label>
                <input type="text" name="address" id="address" maxlength="50">
            </div>
            <div>
                <label for="tel">연락처</label>
                <input type="text" name="tel" id="tel" maxlength="15">
            </div>
            <div>
                <label for="fee">입장료</label>
                <input type="text" name="fee" id="fee" maxlength="6">
            </div>
            <div>
                <label for="regionNo">지역코드</label>
                <input type="text" name="regionNo" id="regionNo">
            </div>

            <div class="date-picker">
                <div>
                    <label for="startDate">시작 날짜</label>
                    <input type="date" name="startDate" id="startDate">
                </div>
                <div>
                    <label for="endDate">종료 날짜</label>
                    <input type="date" name="endDate" id="endDate">
                </div>
            </div>

            <div class="button-group">
                <button type="submit">등록</button>
                <button type="button" onclick="history.back()">취소</button>
            </div>
        </form>
    </div>

    <jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
</body>
</html>