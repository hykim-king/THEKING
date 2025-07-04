<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
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
	    
	    const nameInput = document.querySelector("#name");
	    console.log(nameInput);
	    
	    const nicknameInput = document.querySelector("#nickname");
	    console.log(nicknameInput);
	    
	    const emailInput = document.querySelector("#email");
	    console.log(emailInput);
	    
	    const mobileInput = document.querySelector("#mobile");
	    console.log(mobileInput);
	    
	    const addressInput = document.querySelector("#address");
	    console.log(addressInput);
	    
	    const doSaveButton = document.querySelector("#signUpButton"); //id값
	    console.log(doSaveButton);
	    
	    console.log('userIdInput.value: ' + userIdInput.value);
	    console.log('passwordInput.value: ' + passwordInput.value);
	    console.log('nameInput.value: ' + nameInput.value);
	    console.log('nicknameInput.value: ' + nicknameInput.value);
	    console.log('emailInput.value: ' + emailInput.value);
	    console.log('mobileInput.value: ' + mobileInput.value);
	    console.log('addressInput.value: ' + addressInput.value);
            
	  //등록 버튼 이벤트 감지
	    doSaveButton.addEventListener("click",function(event){
	        console.log('doSaveButton: click');
	        
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
            if(isEmpty($("#mobile").val()) === true){
              alert('전화번호를 입력 하세요')
               $("#mobile").focus();
              return;
            }
            
            //주소 필수 입력 처리
            if(isEmpty($("#address").val()) === true){
              alert('주소를 입력 하세요')
               $("#address").focus();
              return;
            }
            
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
                	const data = JSON.parse(response);
                    if (data.msgId === 1) {
                        alert(data.msgContents);
                        location.href = "/ehr/user/main.do";
                    } else {
                        alert("에러: " + data.msgContents);
                    }
                },
                error:function(response){ //요청 실패
                	console.log("error:");
                    console.log("error:"+response)
                }
                
            });
	    });
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
        <input type="button" id="signUpButton" value="가입하기">
    </form>
    <div id="result"></div>
</body>
</html>