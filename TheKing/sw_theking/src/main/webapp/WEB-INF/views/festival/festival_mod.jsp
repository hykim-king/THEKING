<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
 <script src="/ehr/resources/js/common.js"></script>
<head>
<meta charset="UTF-8">
<title>축제 수정</title>
<link rel="stylesheet" href="/ehr/resources/css/festival_reg.css">
<script>
    document.addEventListener('DOMContentLoaded', function() {
        console.log('DOMContentLoaded');

        //control
        const festaNoInput = document.getElementById("festaNo");
        const nameInput = document.getElementById("name");
        const subtitleInput = document.getElementById("subtitle")
        const contentsTextarea = document.getElementById("contents");
        const addressInput = document.getElementById("address");
        const telInput = document.getElementById("tel");
        const feeInput = document.getElementById("fee");
        const regionNoInput = document.getElementById("regionNo");
        const startDateInput = document.getElementById("startDate");
        const endDateInput = document.getElementById("endDate");


        //수정 버튼을 자바스크립트로 가져오기
        const doUpdateButton = document.getElementById("doUpdate");
        
        //수정 버튼 이벤트 감지
        doUpdateButton.addEventListener("click", function(event) {

            if (isEmpty(nameInput.value)) {
                alert('제목을 입력하세요');

                nameInput.focus();
                return;
            }

            if (isEmpty(subtitleInput.value)) {
                alert('소제목을 입력하세요');

                subtitleInput.focus();
                return;
            }

            if (isEmpty(contentsTextarea.value)) {
                alert('내용을 입력하세요');

                contentsTextarea.focus();
                return;
            }
            
            if (isEmpty(addressInput.value)) {
                alert('주소를 입력하세요');

                addressInput.focus();
                return;
            }
            
            if (isEmpty(telInput.value)) {
                alert('연락처를 입력하세요');

                telInput.focus();
                return;
            }
            
            if (isEmpty(feeInput.value)) {
                alert('요금을 입력하세요');

                feeInput.focus();
                return;
            }
            
            if (isEmpty(regionNoInput.value)) {
                alert('지역코드를 입력하세요');

                regionNoInput.focus();
                return;
            }
            
            if (isEmpty(startDateInput.value)) {
                alert('시작 날짜를 입력하세요');

                startDateInput.focus();
                return;
            }
            
            if (isEmpty(endDateInput.value)) {
                alert('종료 날짜를 입력하세요');

                endDateInput.focus();
                return;
            }
            
           

            if (confirm('수정 하시겠습니까?') === false)
                return;

            //ajax 비동기 통신
            $.ajax({
                type : "POST", //GET/POST
                url : "/ehr/festival/doUpdate.do", //서버측 URL
                asyn : "true", //비동기
                dataType : "html",//서버에서 받을 데이터 타입
                data : { //파라메터
                	"festaNo" : festaNoInput.value,
                    "name" : nameInput.value,
                    "subtitle" : subtitleInput.value,
                    "contents" : contentsTextarea.value,
                    "address" : addressInput.value,
                    "tel" : telInput.value,
                    "fee" : feeInput.value,
                    "regionNo" : regionNoInput.value,
                    "startDate" : startDateInput.value,
                    "endDate" : endDateInput.value
                },
                success : function(response) {//요청 성공
                    console.log("success:" + response)
                    //문자열 : javascript 객체
                    const message = JSON.parse(response);
                    //{"messageId":1,"message":"제목등록되었습니다.","no":0,"totalCnt":0,"pageSize":0,"pageNo":0}
                    if (message.messageId === 1) { //등록 성공
                        alert(message.message);

                        //목록 화면으로 이동
                        window.location.href = '/ehr/festival/main.do';
                    } else {
                        alert(message.message);
                    }
                },
                error : function(response) {//요청 실패
                    console.log("error:" + response)
                }

            });

        });

    });
</script>
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
        <h4>상세 페이지 수정</h4>

        <form action="doUpdate.do" method="post">
        <input type="hidden" name="festaNo" id="festaNo" value="${dto.festaNo }">
            <div>
                <label for="name">제목</label>
                <input type="text" name="name" id="name" value="${dto.name }" maxlength="20">
            </div>
            <div>
                <label for="subtitle">소제목</label>
                <input type="text" name="subtitle" id="subtitle" value="${dto.subtitle }" maxlength="50">
            </div>          
            <div>
                <label>상세정보</label>
                <textarea name="contents" id="contents">${dto.contents }</textarea>
            </div>
            <div>
                <label for="address">주소</label>
                <input type="text" name="address" id="address" value="${dto.address }" maxlength="50">
            </div>
            <div>
                <label for="tel">연락처</label>
                <input type="text" name="tel" id="tel" value="${dto.tel }" maxlength="15">
            </div>
            <div>
                <label for="fee">입장료</label>
                <input type="text" name="fee" id="fee" value="${dto.fee }" maxlength="6">
            </div>
            <div>
                <label for="regionNo">지역코드</label>
                <input type="text" name="regionNo" id="regionNo" value="${dto.regionNo }">
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
        </form>
        <div class="button-group">
            <button id="doUpdate">수정</button>
            <button type="button" onclick="history.back()">취소</button>
        </div>
    </div>

    <jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
</body>
</html>