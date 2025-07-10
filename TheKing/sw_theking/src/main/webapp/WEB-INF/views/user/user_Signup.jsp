<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>떠나볼지도 회원가입</title>
<link rel="stylesheet" href="/ehr/resources/css/user/signUp.css">
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="/ehr/resources/js/common.js"></script>
<script>
function execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            document.getElementById('postcode').value = data.zonecode; // 우편번호
            document.getElementById("roadAddress").value = data.roadAddress; // 도로명 주소
            }
        }).open();
}
    //DOM문서(HTML)문서가 로드가 완료되면 수행
    document.addEventListener("DOMContentLoaded", function(){
        console.log("DOMContentLoaded");
        
        const profileFindBtn = document.getElementById('profileFindBtn');
         const fileInput = document.getElementById('fileInput');
         
         profileFindBtn.addEventListener('click', function(event) {
                fileInput.click();  // 숨겨진 input[type=file] 클릭 이벤트 발생
              });
         
         fileInput.addEventListener('change', function(event) {
                const files = event.target.files;
                if (files.length > 0) {
                  alert("선택한 파일: " + files[0].name);
                  // 여기에 선택한 파일을 서버로 업로드하거나 처리하는 코드 작성 가능
                }
              });   
         
        const userIdInput = document.querySelector("#userId");
        console.log(userIdInput);
        
        const passwordInput = document.querySelector("#password");
        console.log(passwordInput);
        
        const nameInput = document.querySelector("#name");
        console.log(nameInput);
        
        const nicknameInput = document.querySelector("#nickname");
        console.log(nicknameInput);
        
        const emailInput = document.querySelector("#email");
        console.log(emailInput);
        
        const doSaveButton = document.querySelector("#signUpButton"); //id값
        console.log(doSaveButton);
        
      //등록 버튼 이벤트 감지
        doSaveButton.addEventListener("click",function(event){
            console.log('doSaveButton: click');
            //모바일 합치기
            const tel1 = document.querySelector("#tel1").value;
            const tel2 = document.querySelector("#tel2").value;
            const tel3 = document.querySelector("#tel3").value;
            
            const fullAddress = $("#roadAddress").val() + " " + $("#detailAddress").val();
            const mobile = tel1 + "-" + tel2 + "-" + tel3;
            
            //아이디 필수 입력 처리
            if(isEmpty($("#userId").val()) === true){
              alert('아이디를 입력 하세요')
               $("#userId").focus();
              return;
            }
            
            //비밀번호 필수 입력 처리
            if(isEmpty($("#password").val()) === true){
              alert('비밀번호를 입력 하세요')
               $("#password").focus();
              return;
            }
            
            //이름 필수 입력 처리
            if(isEmpty($("#name").val()) === true){
              alert('이름을 입력 하세요')
               $("#name").focus();
              return;
            }
            
            //닉네임 필수 입력 처리
            if(isEmpty($("#nickname").val()) === true){
              alert('닉네임을 입력 하세요')
               $("#nickname").focus();
              return;
            }
            
            //이메일 필수 입력 처리
            if(isEmpty($("#email").val()) === true){
              alert('이메일을 입력 하세요')
               $("#email").focus();
              return;
            }
            
            //전화번호 필수 입력 처리
            if(isEmpty(mobile) === true){
              alert('전화번호를 입력 하세요')
              return;
            }
 
            const formData = new FormData();
            formData.append("userId", $("#userId").val());
            formData.append("password", $("#password").val());
            formData.append("name", $("#name").val());
            formData.append("nickname", $("#nickname").val());
            formData.append("email", $("#email").val());
            formData.append("mobile", mobile);
            formData.append("address", fullAddress);

            const fileInput = document.getElementById("fileInput");
            if (fileInput.files.length > 0) {
                formData.append("imageFile", fileInput.files[0]);
            }

            $.ajax({
                type: "POST",
                url: "/ehr/user/signUp.do",
                async: true,
                data: formData,
                processData: false,
                contentType: false,
                dataType: "html",
                success: function(response) {
                    const data = JSON.parse(response);
                    if (data.messageId === 1) {
                        alert(data.message);
                        location.href = "/ehr/main.do";
                    } else {
                        alert(data.message);
                    }
                },
                error: function(response) {
                    console.log("error:");
                    console.log("error:" + response);
                }
            });
        });
      
        document.getElementById("idCheck").addEventListener("click", function () {
            const userId = document.getElementById("userId").value.trim();
            const resultSpan = document.getElementById("idResult");
            const idRegex = /^(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z0-9_-]{6,12}$/;
            
            if (userId === "") {
              resultSpan.textContent = "아이디를 입력하세요.";
              resultSpan.style.color = "red";
              return;
            }
            
            if (!idRegex.test(userId)) {
                resultSpan.textContent = "아이디는 영문자  + 숫자, _, -포함 6~12자리여야 합니다.";
                resultSpan.style.color = "red";
                return; // 함수 종료
            }
             
            $.ajax({
                       type: "POST",
                       url: "/ehr/user/isDuplicateId.do",
                       data: {
                           "userId": $("#userId").val()
                       },
                       success: function (response) {
                         try {
                           const result = JSON.parse(response);
                           if (result.messageId === 1) {
                               // 중복된 닉네임
                               resultSpan.textContent = result.message; // 예: "이미 사용 중인 닉네임입니다."
                               resultSpan.style.color = "red";
                           } else {
                               // 사용 가능한 닉네임
                               resultSpan.textContent = result.message; // 예: "사용 가능한 닉네임입니다."
                               resultSpan.style.color = "green";
                           }
                         } catch (e) {
                           console.error("JSON 파싱 에러:", e);
                         }
                       },
                       error: function (xhr, status, error) {
                         console.error("AJAX 오류:", error);
                         alert("수정 중 오류 발생");
                       }
                  });
          });
        
        document.getElementById("nicknameCheck").addEventListener("click", function () {
            const nickname = document.getElementById("nickname").value.trim();
            const resultSpan = document.getElementById("nicknameResult");
          
            if (nickname === "") {
              resultSpan.textContent = "닉네임을 입력하세요.";
              resultSpan.style.color = "red";
              return;
            }
              
            $.ajax({
                       type: "POST",
                       url: "/ehr/user/isDuplicateNickname.do",
                       data: {
                         "nickname": $("#nickname").val(),
                       },
                       success: function (response) {
                         try {
                           const result = JSON.parse(response);
                           if (result.messageId === 1) {
                               // 중복된 닉네임
                               resultSpan.textContent = result.message; // 예: "이미 사용 중인 닉네임입니다."
                               resultSpan.style.color = "red";
                           } else {
                               // 사용 가능한 닉네임
                               resultSpan.textContent = result.message; // 예: "사용 가능한 닉네임입니다."
                               resultSpan.style.color = "green";
                           }
                         } catch (e) {
                           console.error("JSON 파싱 에러:", e);
                         }
                       },
                       error: function (xhr, status, error) {
                         console.error("AJAX 오류:", error);
                         alert("수정 중 오류 발생");
                       }
                     });
          });
    });
</script>
</head>
<body>
  <jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
  <h1 id="page">회원 가입</h1>
    <div class="container">
    <form id="signUpForm" method="post" enctype="multipart/form-data">
        <div class="form-group">
            <div class="picture-wrapper">
                <p>프로필 사진</p>
                <c:choose>
                  <c:when test="${not empty user.profileImage.saveName}">
                    <img src="${pageContext.request.contextPath}/resources/images/user/${user.profileImage.saveName}" alt="프로필 이미지">
                  </c:when>
                  <c:otherwise>
                  </c:otherwise>
                </c:choose>
                <button type="button" id="profileFindBtn">찾기</button><br>
            </div>
            <input type="file" id="fileInput" accept="image/*" name="imageFile" style="display:none" />
        </div>
        <div class="form-group">
        <div class="form-row">
            <label for="userId">아이디:</label>
            <input type="text" name="userId" id="userId">
            <button type="button" id="idCheck">중복 확인</button>
            <span id="idResult" style="margin-left:10px;"></span>
        </div>
        <div class="form-row">
            <label for="password">비밀번호:</label>
            <input type="password" name="password" id="password">
        </div>
        <div class="form-row">
            <label for="name">이름:</label>
            <input type="text" name="name" id="name">
        </div>
        <div class="form-row">
            <label for="nickname">닉네임:</label>
            <input type="text" name="nickname" id="nickname">
            <button type="button" id="nicknameCheck">중복 확인</button>
            <span id="nicknameResult" style="margin-left:10px;"></span>
        </div>
        <div class="form-row">
            <label for="email">이메일:</label>
            <input type="email" name="email" id="email">
        </div>
        <div class="form-row form-inline">
            <label>전화번호*</label>
            <input type="text" class="tel" id="tel1" name="tel1" value="${tel1}" maxlength="3"> -
            <input type="text" class="tel" id="tel2" name="tel2" value="${tel2}" maxlength="4"> -
            <input type="text" class="tel" id="tel3" name="tel3" value="${tel3}" maxlength="4">
        </div>

        <div class="form-row form-inline last-row">
            <label>주소 (선택사항)*</label>
            <input type="text" id="postcode" placeholder="우편번호" readonly>
            <button type="button" onclick="execDaumPostcode()">주소 검색</button>
        </div>

        <div class="detail-address">
            <input type="text" id="roadAddress"  value="${roadAddress}" placeholder="도로명 주소" readonly><br>
            <input type="text" id="detailAddress" value="${detailAddress}" placeholder="상세 주소 입력">
        </div>
        <input type="button" id="signUpButton" value="가입하기">
        </div>
    </form>
    </div>
<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
</body>
</html>