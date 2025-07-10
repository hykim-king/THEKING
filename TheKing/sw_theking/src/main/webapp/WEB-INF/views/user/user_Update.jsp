<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 정보 수정</title>
<!-- 카카오 우편번호 서비스 스크립트 -->
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

document.addEventListener("DOMContentLoaded", function(){
    console.log("DOMContentLoaded");
	
	
	 const profileFindBtn = document.getElementById('profileFindBtn');
	 const fileInput = document.getElementById('fileInput');
	 
	 profileFindBtn.addEventListener('click', function(event) {
		    fileInput.click();  // 숨겨진 input[type=file] 클릭 이벤트 발생
		  });
	 
	 fileInput.addEventListener('change', function(event) {
		 const file = event.target.files[0];
		    if (file) {
	            const reader = new FileReader();
	            reader.onload = function (e) {
	                document.getElementById("previewImage").src = e.target.result;
	            };
	            reader.readAsDataURL(file);
	        }
		  });	
	 
	 //주소 합치기
	 const postcode = document.getElementById("postcode").value;
	 const roadAddress = document.getElementById("roadAddress").value;
	 const detailAddress = document.getElementById("detailAddress").value;
	 
	 const updateBtn = document.getElementById("updateBtn");
     
	 updateBtn.addEventListener('click', function(event){
		//모바일 합치기
	     const tel1 = document.querySelector("#tel1").value;
	     const tel2 = document.querySelector("#tel2").value;
	     const tel3 = document.querySelector("#tel3").value;
	     
		 const fullAddress = $("#roadAddress").val() + " " + $("#detailAddress").val();
		 const mobile = tel1 + "-" + tel2 + "-" + tel3;
	     console.log(mobile);
	     
	     const file = document.getElementById("fileInput").files[0];
	     
	     const formData = new FormData();
	     formData.append("userNo", $("#userNo").val());
         formData.append("userId", $("#userId").val());
         formData.append("name", $("#name").val());
         formData.append("nickname", $("#nickname").val());
         formData.append("email", $("#email").val());
         formData.append("mobile", mobile);
         formData.append("address", fullAddress);
	     
         if (file) {
             formData.append("imageFile", file);
         }
         
		 $.ajax({
		      type: "POST",
		      url: "/ehr/user/doUpdate.do",
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
		        alert("중복 확인 중 오류 발생");
		      }
		    });
	 });
	document.getElementById("checkNicknameBtn").addEventListener("click", function () {
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
		        alert(result.message);
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
<style>
    body {
      font-family: '맑은 고딕', sans-serif;
      margin: 0;
      padding: 0;
    }
    .container {
      display: flex;
      padding: 40px;
    }
    .profile-area {
      width: 200px;
      text-align: center;
      border-right: 1px solid #ccc;
      padding-right: 30px;
    }
    .profile-area img {
      width: 100px;
      height: 100px;
      object-fit: cover;
      border-radius: 50%;
    }
    .profile-area h3 {
      margin: 10px 0 5px;
    }
    .profile-area p {
      margin: 0;
      font-size: 14px;
      color: #888;
    }
    .profile-area .interest {
      margin-top: 10px;
      background-color: #eee;
      padding: 5px 10px;
      display: inline-block;
      border-radius: 10px;
    }
    .profile-area ul {
      list-style: none;
      padding: 0;
      margin-top: 30px;
    }
    .profile-area li {
      margin-bottom: 10px;
    }
    .profile-area a {
      display: block;
      padding: 10px;
      border: 1px solid #000;
      text-decoration: none;
      color: #000;
    }

	.profile-picture-wrapper {
	    text-align: left;
	    padding-bottom: 10px;
	    border: none !important;
    }   

	.profile-picture-wrapper img {
	  width: 100px;
	  height: 100px;
	  object-fit: cover;   /* 핵심! 이미지를 비율 유지하며 채움 */
	  border-radius: 50%;  /* 동그란 이미지 원형 처리 */
	}

    .form-area {
      flex-grow: 1;
      padding-left: 40px;
    }
    .form-area h2 {
      margin-top: 0;
    }
    .form-group {
      margin-bottom: 15px;
    }
    label {
      display: block;
      font-weight: bold;
      margin-bottom: 5px;
    }
    input[type="text"],
    input[type="email"],
    input[type="tel"] {
      width: 300px;
      padding: 7px;
      border: 1px solid #ccc;
      border-radius: 5px;
      margin-top: 5px;
      margin-bottom: 5px;
    }
    .form-inline {
      display: flex;
      gap: 10px;
      align-items: center;
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
      border-radius: 15px;
      cursor: pointer;
      font-weight: bold;
    }
    
    </style>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>

	<div class="container">
		<div class="profile-area">
            <img src="${pageContext.request.contextPath}/resources/images/user/${user.profileImage.saveName}" alt="프로필 이미지">
			<h3 id="user-name">${user.name}</h3><br>
            <p id="user-email">${user.email}</p><br>
		</div>

		<div class="form-area">
			<h2>정보 수정</h2>
            <input type="hidden" id="userNo" name="userNo" value="${user.userNo}">
			<div class="form-group">
				<label>프로필 사진</label>
				<div class="profile-picture-wrapper">
				<img id="previewImage" src="${pageContext.request.contextPath}/resources/images/user/${user.profileImage.saveName}" alt="프로필 이미지">
				</div>
				<button id="profileFindBtn">찾기</button>
				<input type="file" id="fileInput" accept="image/*" style="display:none" />
			</div>
            <input type="hidden" id="userNo" value="${user.userNo}" />
			<div class="form-group form-inline">
				<label>아이디</label> 
				<input type="text" id="userId" value="${user.userId}" readonly>
			</div>

			<div class="form-group form-inline">
				<label>닉네임*</label>
				<input type="text" id="nickname" value="${user.nickname}" placeholder="닉네임">
				<button type="button" id="checkNicknameBtn">중복확인</button>
			    <span id="nicknameResult" style="margin-left:10px;"></span>
			</div>

			<div class="form-group">
				<label>이름</label> 
				<input type="text" id="name" value="${user.name}" placeholder="이름">
			</div>

			<div class="form-group">
				<label>이메일</label> 
				<input type="email" id="email" value="${user.email}" placeholder="이메일" readonly>
			</div>

			<div class="form-group form-inline">
				<label>전화번호*</label> 
				<input type="text" id="tel1" name="tel1" value="${tel1}" maxlength="3"> -
    			<input type="text" id="tel2" name="tel2" value="${tel2}" maxlength="4"> -
    			<input type="text" id="tel3" name="tel3" value="${tel3}" maxlength="4">
			</div>

			<div class="form-group form-inline">
				<label>주소 (선택사항)*</label> 
				<input type="text" id="postcode" placeholder="우편번호" readonly>
				<button type="button" onclick="execDaumPostcode()">주소 검색</button>
			</div>

			<div class="detail-address">
				<input type="text" id="roadAddress"  value="${roadAddress}" placeholder="도로명 주소" readonly><br>
				<input type="text" id="detailAddress" value="${detailAddress}" placeholder="상세 주소 입력">
			</div>
			<div class="form-group">
				<button id="updateBtn" class="updateBtn">수정</button>
			</div>
		</div>
	</div>

<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
</body>
</html>