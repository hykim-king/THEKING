<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    //DOM문서(HTML)문서가 로드가 완료되면 수행!
    document.addEventListener("DOMContentLoaded", function(){
        console.log("DOMContentLoaded");
        
        //전송 버튼 이벤트 처리
        $("#sendBtn").on("click",function(){
            console.log("sendBtn click");
            //alert("sendBtn click");
            
            signUpSend();
        });
        
        //함수
        function signUpSend(){
            
            $.ajax({
                type:"POST",     //GET/ POST
                url:"/ehr/user/signUp.do", //서버측URL
                async:"true",     //비동기
                dataType:"html", //서버에서 받을 데이터 타입
                data:{           //파라메터
                    "userId": $("#userId").val(),
                    "password": $("#password").val(),
                    "name": $("#name").val(),
                    "nickname": $("#nickname").val(),
                    "email": $("#email").val(),
                    "mobile": $("#mobile").val(),
                    "address": $("#address").val()
                },
                success:function(response){ //요청성공
                	console.log("success:");
                    //console.log("success:"+response);
                    //window.location.href = "/ehr/user/main.do"
                },
                error:function(response){ //요청 실패
                	console.log("error:");
                    //console.log("error:"+response)
                }
                
            });
        }
        
    });
</script>

    <h1>signUpPage</h1>
    <hr>
    
    <form id="signUpForm" method="post">
        <label for="userId">아이디:</label>
        <input type="text" name="userId" id="userId"><br>
        <label for="password">비밀번호:</label>
        <input type="password" name="password" id="password"><br>
        <label for="name">이름:</label>
        <input type="text" name="name" id="name"><br>
        <label for="nickname">닉네임:</label>
        <input type="text" name="nickname" id="nickname"><br>
        <label for="email">이메일:</label>
        <input type="email" name="email" id="email"><br>
        <label for="mobile">전화번호:</label>
        <input type="tel" name="mobile" id="mobile"><br>
        <label for="address">주소:</label>
        <input type="text" name="address" id="address"><br>
        <input type="button" id="sendBtn" value="전송">
    </form>
    <div id="result"></div>
</body>
</html>