<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/ehr/resources/css/footer.css">
<link rel="stylesheet" href="/ehr/resources/css/nav.css">
<link rel="stylesheet" href="/ehr/resources/css/user/login.css">
<title>떠나볼지도 로그인</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="/ehr/resources/js/common.js"></script>
<script>
//DOM문서(HTML)문서가 로드가 완료되면 수행
document.addEventListener("DOMContentLoaded", function(){
    console.log("DOMContentLoaded");
    
    const userIdInput = document.querySelector("#userId");
    console.log(userIdInput);
    
    const passwordInput = document.querySelector("#password");
    console.log(passwordInput);
    
    LoginButton.addEventListener("click",function(event){
        console.log('LoginButton: click');
        
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

        $.ajax({
    	    type: "POST",
    	    url: "/ehr/user/login.do",
    	    async: "true",
    	    dataType: "html",
    	    data: {
    	        "userId": $("#userId").val(),
    	        "password": $("#password").val()
    	    },
    	    success: function(response) {
    	        console.log("success:", response);
    	        const data = JSON.parse(response);
    	        if (data.messageId === 1) {
    	        	alert(data.message);
    	            location.href = "/ehr/user/main.do";
    	        } else {
    	            // 로그인 실패 메시지 출력
    	            alert(data.message);
    	        }
    	    },
    	    error: function(response) {
    	        console.log(response);
    	    }   
    	    });
    	});
    });
	
</script>
</head>
<body>
   <nav>
    <a href="/ehr/user/main.do"><img src="/ehr/resources/images/logo2.png"></a>
    <a href="/ehr/tour/doRetrieve.do">관광지</a>
    <a href="/ehr/festival/main.do">축제</a>
    <a href="#">게시판</a>
    <a href="#">공지사항</a>
    <a href="/ehr/user/logout.do">로그아웃</a>
  </nav>
<div class="login-container">
  <div class="login-box">
    <div class="login-logo">
      <a href="/ehr/user/main.do">
        <img src="/ehr/resources/images/logo2.png" alt="떠나볼지도 로고">
      </a>
    </div>

    <form id="loginForm" method="post">
      <div class="form-group">
        <label for="userId">아이디</label>
        <input type="text" name="userId" id="userId" />
      </div>
      <div class="form-group">
        <label for="password">비밀번호</label>
        <input type="password" name="password" id="password" />
      </div>
      <div class="form-group">
        <button type="button" id="LoginButton">로그인</button>
      </div>
    </form>
  </div>
</div>
<footer>
  <img src="logo2.png">
  <div class="team">
  <p>THE KING</p>
  </div>
</footer>
</body>
</html>