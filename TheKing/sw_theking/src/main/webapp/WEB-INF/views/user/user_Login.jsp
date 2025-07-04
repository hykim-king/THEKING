<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>어디갈지도 로그인</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script>
	$.ajax({
	    type: "POST",
	    url: "/ehr/user/login.do",
	    async: true,
	    dataType: "json", // 서버에서 JSON 응답
	    data: {
	        "userId": $("#userId").val(),
	        "password": $("#password").val()
	    },
	    success: function(response) {
	        console.log("success:", response);
	        if (response.msgId === 1) {
	            window.location.href = response.msgContents;
	        } else {
	            // 로그인 실패 메시지 출력
	            alert(response.msgContents);
	        }
	    },
	    error: function(error) {
	        console.log("error:", error);
	        alert("서버 오류가 발생했습니다.");
	    }   
	});
</script>
</head>
<body>
    <h1>로그인 페이지</h1>
    <form id="loginForm" method="post">
    <div>
        <label for="userId">아이디:</label>
        <input type="text" name="userId" id="userId">
    </div>
    <div>
        <label for="password">비밀번호:</label>
        <input type="password" name="password" id="password">
    </div>
        <input type="button" id="sendBtn" value="전송">
    </form>
</body>
</html>