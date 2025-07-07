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

    // tourNo
    const tourNoInput = document.querySelector("#tourNo");
    console.log(tourNoInput);
    
    // 수정 버튼
    const doUpdateButton = document.querySelector("#doUpdate");
    console.log(doUpdateButton);
    
    if (!doUpdateButton) {
        console.error('수정 버튼을 찾을 수 없습니다.');
        return;
    }
    
    const nameInput = document.querySelector("#name");
    console.log(nameInput);
    
    const subtitleInput = document.querySelector("#subtitle");
    console.log(subtitleInput);
    
    const contentsInput = document.querySelector("#contents");
    console.log(contentsInput);
    
    const addressInput = document.querySelector("#address");
    console.log(addressInput);
    
    const imageInput = document.querySelector("#image");
    console.log(imageInput);
    
    const telInput = document.querySelector("#tel");
    console.log(telInput);
    
    const timeInput = document.querySelector("#time");
    console.log(timeInput);
    
    const holidayInput = document.querySelector("#holiday");
    console.log(holidayInput);
    
    const feeInput = document.querySelector("#fee");
    console.log(feeInput);
    //제목
    console.log('nameInput.value: '+nameInput.value);
    console.log('subtitleInput.value: '+subtitleInput.value);
    console.log('contentsInput.value: '+contentsInput.value);
    console.log('addressInput.value: '+addressInput.value);
    console.log('imageInput.value: '+imageInput.value);
    console.log('telInput.value: '+telInput.value);
    console.log('timeInput.value: '+timeInput.value);
    console.log('holidayInput.value: '+holidayInput.value);
    console.log('feeInput.value: '+feeInput.value);

    
    
    // 수정 버튼 클릭 시
    	doUpdateButton.addEventListener('click', function(event) {
            console.log('doUpdateButton click');
            
            if (isEmpty(tourNoInput.value)) {
                alert("tourNo를 확인 하세요.");
                return;
            }
            
          //제목
            if(isEmpty(nameInput.value) === true){
               alert('제목을 입력 하세요');
               nameInput.focus();
               return;
            }
            //내용
            if(isEmpty(contentsInput.value) === true){
               alert('내용을 입력 하세요');
               contentsInput.focus();
               return;
            }
            //주소
            if(isEmpty(addressInput.value) === true){
               alert('주소를 입력 하세요');
               addressInput.focus();
               return;
            }

            if (!confirm('관광지를 수정 하시겠습니까?')) {
                return;
            }
            
            $.ajax({
                type: "POST",
                url: "/ehr/tour/doUpdate.do",
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
                    console.log("success:" + response);
                    const message = JSON.parse(response);

                    if (message.messageId === 1) {
                        alert(message.message);
                        window.location.href = '/ehr/tour/doRetrieve.do';
                    } else {
                        alert(message.message);
                    }
                },
                error: function(response) {
                    console.log("error:" + response);
                }
            });
    	});
    
});
    
</script>
<body>
    <h2>관광지 상세 수정 </h2>
    <hr>
    <form action="/ehr/tour/doUpdate.do" method="post" >
    <div>
        <label for="name" >제목</label>
        <input type="text" name="name" id="name" maxlength="200" required placeholder="제목" value="${vo.name }">
    </div>
    <div>
        <label for="subtitle" >소제목</label>
        <input type="text" name="subtitle" id="subtitle" maxlength="20" required  placeholder="소제목" value="${vo.subtitle }">
    </div>
    <div>
        <label for="contents" >내용</label>
        <textarea id="contents" name="contents"  placeholder="내용을 입력하세요." class="contents">${vo.contents }</textarea>
    </div>
    <div>
        <label for="address" >주소</label>
        <input type="text" name="address" id="address" maxlength="20" required  placeholder="주소" value="${vo.address }">
    </div>
    <!-- 이미지 변수명 뭐 넣어야 하지 -->
    <div>
        <label for="image" >이미지</label>
        <input type="file" name="image" id="image" accept="/resources/image/tour/*"> 
    </div>
    <div>
        <label for="tel" >연락처</label>
        <input type="text" name="tel" id="tel" maxlength="20" required  placeholder="연락처" value="${vo.tel }">
    </div>
    <div>
        <label for="time" >운영시간</label>
        <input type="text" name="time" id="time" maxlength="20" required  placeholder="운영시간" value="${vo.time }">
    </div>
    <div>
        <label for="holiday" >휴일</label>
        <input type="text" name="holiday" id="holiday" maxlength="20" required  placeholder="휴일" value="${vo.holiday }">
    </div>
    <div>
        <label for="fee" >입장료</label>
        <input type="text" name="fee" id="fee" maxlength="20" required  placeholder="입장료" value="${vo.fee }">
    </div>
    </form>
     <!-- Button area -->
    <div  class="button-area">
         <input type="button" id="doUpdate" value="수정">
         <input type="button" id="moveToList" value="취소">
    </div>
    <!--// Button area -->

</body>
</html>