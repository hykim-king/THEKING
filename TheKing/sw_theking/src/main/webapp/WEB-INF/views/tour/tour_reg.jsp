<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>tour_reg</title>
<link rel="stylesheet" href="/ehr/resources/css/festival_reg.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<!-- 카카오 우편번호 서비스 스크립트 -->
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="/ehr/resources/js/common.js"></script>
<script>
function execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            document.getElementById('postcode').value = data.zonecode;
            document.getElementById("roadAddress").value = data.roadAddress;
        }
    }).open();
}

document.addEventListener("DOMContentLoaded", function() {
    // 나머지 초기화 코드들
});
</script>
<script>
document.addEventListener('DOMContentLoaded', function() {
    console.log('DOMContentLoaded');
    

    function isEmpty(value) {
        return value == null || value.trim() === '';
    }

    // 요소 가져오기
    const tourNoInput    = document.querySelector("#tourNo");
    const nameInput      = document.querySelector("#name");
    const subtitleInput  = document.querySelector("#subtitle");
    const contentsInput  = document.querySelector("#contents");
    const roadAddressInput   = document.querySelector("#roadAddress");
    const detailAddressInput   = document.querySelector("#detailAddress");
    const imageInput     = document.querySelector("#imageFile");
    const telInput       = document.querySelector("#tel");
    const timeInput      = document.querySelector("#time");
    const holidayInput   = document.querySelector("#holiday");
    const feeInput       = document.querySelector("#fee");
    const doSaveButton   = document.querySelector("#doSave");

    console.log(tourNoInput, nameInput, subtitleInput, contentsInput,roadAddressInput,detailAddressInput,
                imageInput, telInput, timeInput, holidayInput, feeInput, doSaveButton);

    // 목록으로 이동 버튼
    const moveToListBtn = document.querySelector("#moveToList");
    console.log(moveToListBtn);

    // 목록 이동 버튼 클릭 시
        moveToListBtn.addEventListener('click', function() {
            
            window.location.href = '/ehr/tour/doRetrieve.do';
            
            // 사용자 확인
            if (!confirm('목록으로 이동합니다.')) {
                return;
            }
        });
 
	// 등록 버튼이 존재할 경우
        doSaveButton.addEventListener('click', function(event) {
            console.log('doSaveButton click');

            // 필수값 체크
            if (isEmpty(nameInput.value)) {
                alert('제목을 입력 하세요');
                nameInput.focus();
                return;
            }

            if (isEmpty(contentsInput.value)) {
                alert('내용을 입력 하세요');
                contentsInput.focus();
                return;
            }

            if (isEmpty(roadAddressInput.value)) {
                alert('주소를 입력 하세요');
                roadAddressInput.focus();
                return;
            }

            // 사용자 확인
            if (!confirm('관광지를 등록합니다.')) {
                return;
            }

            // 주소 합치기
            const fullAddress = $("#roadAddress").val() + " " + $("#detailAddress").val();

            // FormData 객체 생성
            var formData = new FormData();

            // 폼 데이터 삽입
            formData.append("name", nameInput.value);
            formData.append("subtitle", subtitleInput.value);
            formData.append("contents", contentsInput.value);
            formData.append("address", fullAddress);

            // 이미지 파일 첨부
            if (imageInput.files.length > 0) {
                formData.append("imageFile", imageInput.files[0]);
            }

            formData.append("tel", telInput.value);
            formData.append("time", timeInput.value);
            formData.append("holiday", holidayInput.value);
            formData.append("fee", feeInput.value);

            $.ajax({
                type: "POST",
                url: "/ehr/tour/doSave.do",
                data: formData,
                processData: false,  // 필수! 데이터를 query string으로 변환하지 않음
                contentType: false,  // 필수! multipart/form-data 헤더를 자동 설정
                dataType: "json",    // 서버가 JSON 응답일 경우
                success: function(response) {
                    console.log("success: ", response);

                    alert(response.message);

                    if (response.messageId == 1) {
                        window.location.href = '/ehr/tour/doRetrieve.do';
                    }
                },
                error: function(xhr, status, error) {
                    console.log("error: ", error);
                    alert("등록 중 오류가 발생했습니다.");
                }
            });
        });
	});
</script>

<body>
<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>

    <div class="container">
         <div class="logo-container">
            <a href="/ehr/festival/main.do">
                <img alt="로고이미지" src="${pageContext.request.contextPath}/resources/images/logo2.png">
            </a>
        </div>
    <h4>관광지 상세 등록</h4>
    <hr>
    <!-- form area -->
    <form action="doSave.do" method="post" enctype="multipart/form-data">
    <div>
	    <label for="name" >제목*</label>
	    <input type="text" name="name" id="name" autocomplete="name" maxlength="200" required placeholder="제목" >
    </div>
    <div>
        <label for="subtitle" >소제목</label>
        <input type="text" name="subtitle" id="subtitle" maxlength="20" required  placeholder="소제목">
    </div>
    <div>
        <label for="contents" >내용*</label>
        <textarea id="contents" name="contents"  placeholder="내용" class="contents"></textarea>
    </div>
	<div class="form-group form-inline">
	    <label for="postcode">주소*</label> 
	    <input type="hidden" id="address" name="address">
	    <input type="text" id="postcode" placeholder="우편번호" readonly>
	    <button type="button" onclick="execDaumPostcode()">주소 검색</button>
	</div>
	
	<div class="detail-address">
	    <input type="text" id="roadAddress" name="roadAddress" placeholder="도로명 주소" readonly><br>
	    <input type="text" id="detailAddress" name="detailAddress" placeholder="상세 주소 입력">
	</div>
    <div>
        <label for="imageFile" >이미지</label>
        <input type="file" name="imageFile" id="imageFile" > 
    </div>
    <div>
        <label for="tel" >연락처</label>
        <input type="text" name="tel" id="tel" autocomplete="tel" maxlength="20" required  placeholder="연락처">
    </div>
    <div>
        <label for="time" >운영시간</label>
        <input type="text" name="time" id="time" maxlength="20" required  placeholder="운영시간">
    </div>
    <div>
        <label for="holiday" >휴일</label>
        <input type="text" name="holiday" id="holiday" maxlength="20" required  placeholder="휴일">
    </div>
    <div>
        <label for="fee" >입장료</label>
        <input type="text" name="fee" id="fee" maxlength="20" required  placeholder="입장료">
    </div>
     <!-- Button area -->
    <div  class="button-area">
         <input type="button" id="doSave" value="등록">
         <input type="button" id="moveToList" value="취소">
    </div>
    </form>
    <!--// Button area -->
</div>

<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
</body>
</html>