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
    //DOM����(HTML)������ �ε尡 �Ϸ�Ǹ� ����!
    document.addEventListener("DOMContentLoaded", function(){
        console.log("DOMContentLoaded");
        
        //���� ��ư �̺�Ʈ ó��
        $("#sendBtn").on("click",function(){
            console.log("sendBtn click");
            //alert("sendBtn click");
            
            signUpSend();
        });
        
        //�Լ�
        function signUpSend(){
            
            $.ajax({
                type:"POST",     //GET/ POST
                url:"/ehr/user/signUp.do", //������URL
                async:"true",     //�񵿱�
                dataType:"html", //�������� ���� ������ Ÿ��
                data:{           //�Ķ����
                    "userId": $("#userId").val(),
                    "password": $("#password").val(),
                    "name": $("#name").val(),
                    "nickname": $("#nickname").val(),
                    "email": $("#email").val(),
                    "mobile": $("#mobile").val(),
                    "address": $("#address").val()
                },
                success:function(response){ //��û����
                	console.log("success:");
                    //console.log("success:"+response);
                    //window.location.href = "/ehr/user/main.do"
                },
                error:function(response){ //��û ����
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
        <label for="userId">���̵�:</label>
        <input type="text" name="userId" id="userId"><br>
        <label for="password">��й�ȣ:</label>
        <input type="password" name="password" id="password"><br>
        <label for="name">�̸�:</label>
        <input type="text" name="name" id="name"><br>
        <label for="nickname">�г���:</label>
        <input type="text" name="nickname" id="nickname"><br>
        <label for="email">�̸���:</label>
        <input type="email" name="email" id="email"><br>
        <label for="mobile">��ȭ��ȣ:</label>
        <input type="tel" name="mobile" id="mobile"><br>
        <label for="address">�ּ�:</label>
        <input type="text" name="address" id="address"><br>
        <input type="button" id="sendBtn" value="����">
    </form>
    <div id="result"></div>
</body>
</html>