<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 변경</title>
<!-- 카카오 우편번호 서비스 스크립트 -->
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="/ehr/resources/js/common.js"></script>
<script>
document.addEventListener("DOMContentLoaded", function(){
    console.log("DOMContentLoaded");

	updateBtn = document.getElementById("updateBtn");
     
	 updateBtn.addEventListener('click', function(event){
		 const password = document.getElementById("password").value.trim();
	     const passwordCheck = document.getElementById("passwordCheck").value.trim();
	     const resultSpan = document.getElementById("passwordCheckResult");
	     const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%^&*()_+{}\[\]:;<>,.?~\\/-])[A-Za-z\d!@#$%^&*()_+{}\[\]:;<>,.?~\\/-]{8,20}$/;

	     const pwRegexSpan = document.getElementById("passwordRegexResult");
	     const pwCheckSpan = document.getElementById("passwordCheckResult");
	     
	     // 비밀번호 일치 확인
         if (password !== passwordCheck) {
             resultSpan.textContent = "비밀번호가 일치하지 않습니다.";
             return; // AJAX 전송 중지
         } else {
             resultSpan.textContent = ""; // 에러메시지 제거
         }
	     
	     // 정규식 검사
	     if (!passwordRegex.test(password)) {
	         pwRegexSpan.textContent = "비밀번호는 영문+숫자+특수문자 포함 8~20자여야 합니다.";
	         pwCheckSpan.textContent = "";
	         return;
	     } else {
	         pwRegexSpan.textContent = "";
	     }
	     
	     const formData = new FormData();
	     formData.append("userNo", $("#userNo").val());
	     formData.append("password", $("#password").val());
	     
		 $.ajax({
	         type: "POST",
	         url: "/ehr/user/updatePassword.do",
	         data: formData,
	         processData: false,
	         contentType: false,
	         dataType: "html",
	         success: function (response) {
	           try {
	             const result = JSON.parse(response);
	             alert(result.message);
	             location.href = "/ehr/user/myPage.do";
	           } catch (e) {
	             console.error("JSON 파싱 에러:", e);
	           }
	         },
	         error: function (xhr, status, error) {
	           console.error("AJAX 오류:", error);
	           alert("비밀번호 변경 중 오류 발생");
	         }
	       });
	 });
	 
});
</script>
<style>
    .container {
      display: flex;
      padding: 20px;
      width: 800px;
      margin-top: 2%;
      margin-left: 25%;
      margin-right: 25%;
      margin-bottom: 5%;
      height: 90%;
      box-shadow: 2px 2px 5px #636363;
      border-radius: 10px;
      border: 1px solid #6363632d;
    }

    #page {
	  padding-bottom: 25px;
	}
	
    label {
      display: block;
      font-weight: bold;
      margin-bottom: 5px;
    }
    input[type="text"],
    input[type="password"] {
      width: 300px;
      padding: 7px;
      border: 1px solid #ccc;
      border-radius: 5px;
      margin-top: 5px;
      margin-bottom: 5px;
    }
    .form-group {
	  display: flex;
	  align-items: center;
	  gap: 15px;
	  margin-top: 15px;
	  margin-bottom: 50px;
	}
	
	.form-group label {
	  width: 110px;
	  font-weight: bold;
	}
	
	.form-group input {
	  width: 250px;
	  padding: 5px;
	  border: 1px solid #ccc;
	  border-radius: 5px;
    }   
    
    .form-inline {
      display: flex;
      gap: 10px;
      align-items: center;
      margin-bottom: 20px;
    }
    .form-inline input[type="text"] {
      width: auto;
    }
    button {
      padding: 7px 12px;
      border: 1px solid #888;
      border-radius: 5px;
      background-color: #fff;
      cursor: pointer;
    }
    .updateBtn {
      background-color: #d9c2f5;
      color: #000;
      border: none;
      padding: 10px 20px;
      margin-top: 35px;
      border-radius: 15px;
      cursor: pointer;
      font-weight: bold;
    }
    
    </style>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>

	<div class="container">
		<div class="form-area">
			<h2 id="page">비밀번호 변경</h2>
            <input type="hidden" id="userNo" name="userNo" value="${user.userNo}">

			<div class="form-group form-inline">
				<label for="userId">아이디</label> 
				<input type="text" id="userId" value="${user.userId}" readonly>
			</div>

			<div class="form-group form-inline">
				<label for="password">비밀번호*</label>
				<input type="password" id="password" placeholder="영문,숫자,특수문자 조합 8자이상20자이하">
				<span id="passwordRegexResult" style="margin-left:10px; color: red;"></span>
			</div>

            <div class="form-group form-inline">
                <label for="passwordCheck">비밀번호 확인*</label>
                <input type="password" id="passwordCheck" placeholder="비밀번호 확인">
                <span id="passwordCheckResult" style="margin-left:10px; color: red;"></span>
            </div>

			<button id="updateBtn" class="updateBtn">변경</button>

		</div>
	</div>

<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
</body>
</html>