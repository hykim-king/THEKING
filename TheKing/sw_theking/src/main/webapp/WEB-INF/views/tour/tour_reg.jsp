<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>tour_reg</title>
</head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
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
    const addressInput   = document.querySelector("#address");
    const imageInput     = document.querySelector("#image");
    const telInput       = document.querySelector("#tel");
    const timeInput      = document.querySelector("#time");
    const holidayInput   = document.querySelector("#holiday");
    const feeInput       = document.querySelector("#fee");
    const doSaveButton   = document.querySelector("#doSave");

    console.log(tourNoInput, nameInput, subtitleInput, contentsInput, addressInput,
                imageInput, telInput, timeInput, holidayInput, feeInput, doSaveButton);


    // 등록 버튼이 존재할 경우
    if (doSaveButton) {
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

            if (isEmpty(addressInput.value)) {
                alert('주소를 입력 하세요');
                addressInput.focus();
                return;
            }

            // 사용자 확인
            if (!confirm('관광지를 등록합니다.')) {
                return;
            }

            // AJAX 요청
            $.ajax({
                type: "POST",
                url: "/ehr/tour/doSave.do",
                async: true,
                dataType: "json",
                data: {
                	"name": nameInput.value,
                    "subtitle": subtitleInput.value,
                    "contents": contentsInput.value,
                    "address": addressInput.value,
                    "image": imageInput.value,
                    "tel": telInput.value,
                    "time": timeInput.value,
                    "holiday": holidayInput.value,
                    "fee": feeInput.value
                },
                success: function(response) {
                    console.log("success: " + response);
                    const message = JSON.parse(response);

                    alert(message.message);

                    if (message.messageId === 1) {
                        window.location.href = '/ehr/tour/doRetrieve.do';
                    }
                },
                error: function(response) {
                    console.log("error: " + response);
                    alert("등록 중 오류가 발생했습니다.");
                }
            });
        });
    } else {
        console.error('등록 버튼(#doSave)을 찾을 수 없습니다.');
    }

});
</script>

<body>
    <h2>관광지 상세 등록</h2>
    <hr>
    <!-- form area -->
    <form action="/ehr/tour/doSave.do" method="post" >
    <div>
	    <label for="title" >제목</label>
	    <input type="text" name="title" id="title" maxlength="200" required placeholder="제목" >
    </div>
    <div>
        <label for="subtitle" >소제목</label>
        <input type="text" name="subtitle" id="subtitle" maxlength="20" required  placeholder="소제목">
    </div>
    <div>
        <label for="contents" >내용</label>
        <textarea id="contents" name="contents"  placeholder="내용" class="contents"></textarea>
    </div>
    <div>
        <label for="address" >주소</label>
        <input type="text" name="address" id="address" maxlength="20" required  placeholder="주소">
    </div>
    <!-- 이미지 변수명 뭐 넣어야 하지 -->
    <div>
        <label for="image" >이미지</label>
        <input type="file" name="image" id="image" accept="/resources/image/tour/*"> 
    </div>
    <div>
        <label for="tel" >연락처</label>
        <input type="text" name="tel" id="tel" maxlength="20" required  placeholder="연락처">
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

</body>
</html>