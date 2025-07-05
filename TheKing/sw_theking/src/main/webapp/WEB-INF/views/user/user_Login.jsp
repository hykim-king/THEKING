<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
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
    <h1>로그인 페이지</h1>
    <form action="#" id="loginForm" method="post">
    <div>
        <label for="userId">아이디:</label>
        <input type="text" name="userId" id="userId">
    </div>
    <div>
        <label for="password">비밀번호:</label>
        <input type="password" name="password" id="password">
    </div>
        <input type="button" id="LoginButton" value="로그인">
    </form>
</body>
</html>