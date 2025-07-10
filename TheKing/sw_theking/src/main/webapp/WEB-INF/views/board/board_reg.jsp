<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
<link rel="stylesheet" href="/ehr/resources/css/board_list.css">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Document</title>
<script src="/ehr/resources/js/common.js"></script>
<script
    src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script>
    document.addEventListener('DOMContentLoaded', function() {
        console.log('DOMContentLoaded');

        //control
        const titleInput = document.querySelector("#title");
        console.log(titleInput);

        const regIdInput = document.querySelector("#regId")
        console.log(regIdInput);

        const contentsTextarea = document.getElementById("contents");
        console.log(contentsTextarea);

        const divInput = document.getElementById("div");
        console.log(div);

        //id 선택자
        document.getElementById("contents");
        //class 선택자
        document.getElementsByClassName("contents");
        //태그 선택자
        document.getElementsByTagName("textarea");

        //등록 버튼을 자바스크립트로 가져오기
        const doSaveButton = document.querySelector("#doSave");
        console.log('doSaveButton: ' + doSaveButton);
        
        const moveToListButton = document.querySelector("#moveToList");
        console.log(moveToListButton);
        moveToListButton.addEventListener("click",function(event){
            console.log("moveToListButton click");
            if(confirm('목록으로 이동 하시겠습니까?')===false)return;
            window.location.href='/ehr/board/doRetrieve.do?div='+divInput.value;
        });
            
        

        //등록 버튼 이벤트 감지
        doSaveButton.addEventListener("click", function(event) {
            console.log('doSaveButton: click');

            //제목
            console.log('titleInput.value : ' + titleInput.value);

            if (isEmpty(titleInput.value)) {
                alert('제목을 입력하세요');

                titleInput.focus();
                return;
            }

            if (isEmpty(regIdInput.value)) {
                alert('등록자을 입력하세요');

                regIdInput.focus();
                return;
            }

            if (isEmpty(contentsTextarea.value)) {
                alert('내용을 입력하세요');

                regIdInput.focus();
                return;
            }

            if (confirm('등록 하시겠습니까?') === false)
                return;

            //ajax 비동기 통신
            $.ajax({
                type : "POST", //GET/POST
                url : "/ehr/board/doSave.do", //서버측 URL
                asyn : "true", //비동기
                dataType : "html",//서버에서 받을 데이터 타입
                data : { //파라메터
                    "title" : titleInput.value,
                    "regId" : regIdInput.value,
                    "contents" : contentsTextarea.value,
                    "div" : divInput.value
                },
                success : function(response) {//요청 성공
                    console.log("success:" + response)
                    //문자열 : javascript 객체
                    const message = JSON.parse(response);
                    //{"messageId":1,"message":"제목등록되었습니다.","no":0,"totalCnt":0,"pageSize":0,"pageNo":0}
                    if (message.messageId === 1) { //등록 성공
                        alert(message.message);

                        //목록 화면으로 이동
                        window.location.href = '/ehr/board/doRetrieve.do?div='
                                + divInput.value;
                    } else {
                        alert(message.message);
                    }
                },
                error : function(response) {//요청 실패
                    console.log("error:" + response)
                }

            });

        });

    });
</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
    <div id="container">
            <div class="form-container">
                  <c:choose>
                    <c:when test="${divValue==10 }"><h2>자유게시판 - 등록</h2></c:when>
                    <c:otherwise><h2>공지사항 - 등록</h2></c:otherwise>
                  </c:choose>
                <hr class="title-underline">
                <!--버튼 영역-->
                <div class="button-area">
                <c:if test="${not empty sessionScope.loginUser.userId }">
                    <input type="button" id="doSave" value="등록">
                </c:if>
                 <input type="button" id="moveToList" value="목록">
                </div>
                <!--버튼 영역 end-->
         
                <!--form-->
                <form action="/ehr/board/doSave.do" method="post">
                    <input type="hidden" name="div" id="div" value="${divValue}"> 
                    <div class="form-group">
                        <label for="title">제목</label> <input type="text" id="title"
                            name="title" maxlength="200" required placeholder="제목">
                    </div>
                    <div class="form-group">
                        <label for="regId">등록자</label> <input type="text" id="regId"
                            name="regId" maxlength="20" required placeholder="등록자" disabled value="${sessionScope.loginUser.userId  }">
                    </div>
                    <div class="form-group">
                        <label for="contents">내용</label>
                        <textarea name="contents" id="contents" placeholder="내용"></textarea>
                    </div>
                </form>
            </div>
    </div>
    <jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
</body>
</html>