<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
		    const files = event.target.files;
		    if (files.length > 0) {
		      alert("선택한 파일: " + files[0].name);
		      // 여기에 선택한 파일을 서버로 업로드하거나 처리하는 코드 작성 가능
		    }
		  });	
	 
	 //주소 합치기
	 const postcode = document.getElementById("postcode").value;
	 const roadAddress = document.getElementById("roadAddress").value;
	 const detailAddress = document.getElementById("detailAddress").value;
	 const fullAddress = $("#roadAddress").val() + " " + $("#detailAddress").val();

	 const updateBtn = document.getElementById("updateBtn");
	 
	 updateBtn.addEventListener('click', function(event){
		 $.ajax({
		      type: "POST",
		      url: "/ehr/user/doUpdate.do",
		      data: {
		    	"userId": $("#userId").val(),
		      	"name": $("#name").val(),
		      	"nickname": $("#nickname").val(),
		      	"email": $("#email").val(),
		      	"mobile": $("#mobile").val(),
		      	"address": fullAddress
		      },
		      success: function (response) {
		        try {
		          const result = JSON.parse(response);
		          alert(result.message);
		          if (result.messageId == 1) {
		        	  location.href = "/ehr/user/mypage.do";
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
<style>
    body {
      font-family: '맑은 고딕', sans-serif;
      margin: 0;
      padding: 0;
    }
    header {
      background-color: #f8c471;
      padding: 10px 20px;
      display: flex;
      justify-content: space-between;
      align-items: center;
    }
    nav a {
      margin: 0 10px;
      text-decoration: none;
      color: black;
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
    
    footer {
      text-align: center;
      padding: 20px;
      background-color: #eee;
      margin-top: 50px;
    }
    </style>
</head>
<body>
	<header>
		<div>
			<strong>떠나볼지도</strong> Get going
		</div>
		<nav>
			<a href="#">홈</a>
			 <a href="#">관광지</a>
			  <a href="#">축제</a> 
			  <a href="#">게시판</a>
			<a href="#">공지사항</a>
			 <a href="#">로그인</a>
		</nav>
	</header>

	<div class="container">
		<div class="profile-area">
			<img src="/ehr/resources/images/user/짱구1.jpg" alt="프로필 이미지">
			<h3>홍길동</h3>
			<p>이메일@gmail.com</p>
			<div class="interest">관심 지역: 인천</div>
		</div>

		<div class="form-area">
			<h2>정보 수정</h2>

			<div class="form-group">
				<label>프로필 사진</label>
				<button id="profileFindBtn">찾기</button>
				<input type="file" id="fileInput" accept="image/*" style="display:none" />
			</div>

			<div class="form-group form-inline">
				<label>아이디</label> 
				<input type="text" value="${user.userId}" readonly>
			</div>

			<div class="form-group form-inline">
				<label>닉네임*</label>
				 <input type="text" value="${user.nickname}" placeholder="닉네임">
				<button>중복확인</button>
			</div>

			<div class="form-group">
				<label>이름</label> 
				<input type="text" value="${user.name}" placeholder="이름">
			</div>

			<div class="form-group">
				<label>이메일*</label> 
				<input type="email" value="${user.email}" placeholder="이메일">
			</div>

			<div class="form-group form-inline">
				<label>전화번호*</label> 
				<input type="text" name="tel1" value="${tel1}" maxlength="3"> -
    			<input type="text" name="tel2" value="${tel2}" maxlength="4"> -
    			<input type="text" name="tel3" value="${tel3}" maxlength="4">
			</div>

			<div class="form-group form-inline">
				<label>주소 (선택사항)*</label> 
				<input type="text" id="postcode" placeholder="우편번호" readonly>
				<button type="button" onclick="execDaumPostcode()">주소 검색</button>
			</div>

			<div class="detail-address">
				<input type="text" id="roadAddress" placeholder="도로명 주소" readonly><br>
				<input type="text" id="detailAddress" placeholder="상세 주소 입력">
			</div>
			<div class="form-group">
				<button id="updateBtn" class="updateBtn">수정</button>
			</div>
		</div>
	</div>

	<footer> Footer </footer>
</body>
</html>