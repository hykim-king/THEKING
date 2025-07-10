<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/ehr/resources/css/board_list.css">
<title>게시글 관리</title>
<script
    src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="/ehr/resources/js/common.js"></script>
<script>
    document.addEventListener('DOMContentLoaded', function() {
        console.log('DOMContentLoaded');

        //seq
        const seqInput = document.querySelector("#seq");
        console.log('seqInput');

        //title
        const titleInput = document.querySelector("#title");
        console.log('titleInput');

        //contents
        const contentsInput = document.querySelector("#contents");
        console.log('contentsInput');

        //목록
        const moveToList = document.querySelector("#moveToList");
        console.log('moveToList');

        //수정 버튼
        const doUpdateButton = document.querySelector("#doUpdate");
        console.log('doUpdateButton');

        //삭제 버튼
        const doDeleteButton = document.querySelector("#doDelete");
        console.log('doDeleteButton');

        //목록 버튼 click event 감자
        moveToList.addEventListener('click', function() {
            if (confirm('목록으로 이동 하시겠습니까?')) {
                //목록 화면으로 이동
                window.location.href = '/ehr/board/doRetrieve.do?div='+${divValue};
            }

        });
        //수정 버튼 event 감지
        doUpdateButton.addEventListener('click', function() {
            console.log('doUpdateButton click');

            //제목
            if (isEmpty(titleInput.value)) {
                alert("제목을 입력하세요");
                titleInput.focus();

                return;
            }
            //내용
            if (isEmpty(contentsInput.value)) {
                alert("내용을 입력하세요");
                contentsInput.focus();

                return;
            }

            if (confirm('게시글을 수정하시겠습니까?') === false) {
                return;
            }

            $.ajax({
                type : "POST", //GET/POST
                url : "/ehr/board/doUpdate.do", //서버측 URL
                asyn : "true", //비동기
                dataType : "html",//서버에서 받을 데이터 타입
                data : { //파라메터
                    "seq" : seqInput.value,
                    "title" : titleInput.value,
                    "contents" : contentsInput.value,
                    "div" : '${divValue}',
                    "modId" : '${vo.modId}'
                },
                success : function(response) {//요청 성공
                    console.log("success:" + response)

                    //문자열: javascript 객체로 변환
                    const message = JSON.parse(response);

                    if (message.messageId === 1) {
                        alert(message.message);

                        //목록 화면으로 이동
                        window.location.href = '/ehr/board/doRetrieve.do?div='+ ${divValue};
                    }
                },
                error : function(response) {//요청 실패
                    console.log("error:" + response)
                }

            });

        });
        //삭제 버튼 event 감지
        doDeleteButton.addEventListener('click', function() {
            console.log('doDeleteButton click');

            //seq
            if (isEmpty(seqInput)) {
                alert("seq를 확인하세요.");
                return;
            }

            if (confirm('게시글을 삭제 하시겠습니까?') === false) {
                return;
            }

            $.ajax({
                type : "POST", //GET/POST
                url : "/ehr/board/doDelete.do", //서버측 URL
                asyn : "true", //비동기
                dataType : "html",//서버에서 받을 데이터 타입
                data : { //파라메터
                    "seq" : seqInput.value
                },
                success : function(response) {//요청 성공
                    console.log("success:" + response)

                    //문자열: javascript 객체로 변환
                    const message = JSON.parse(response);

                    if (message.messageId === 1) {
                        alert(message.message);

                        //목록 화면으로 이동
                        window.location.href = '/ehr/board/doRetrieve.do?div='+${divValue};
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

       <main id="main">
        <div class="form-container">
              <c:choose>
                <c:when test="${divValue==10 }"><h2>자유게시판 - 관리</h2></c:when>
                <c:otherwise><h2>공지사항 - 관리</h2></c:otherwise>
              </c:choose>
            <hr class="title-underline">
            <!-- Button area -->
            <div class="button-area">
                <input type="button" id="moveToList" value="목록">
            <c:if test="${(sessionScope.loginUser.userId==vo.regId && divValue==10)||sessionScope.loginUser.role=='admin' }">
                <input type="button" id="doUpdate" value="수정"> 
                <input type="button" id="doDelete" value="삭제">
            </c:if>
            </div>
            <!--// Button area -->
            <!-- form area -->
            <form action="/ehr/user/doSave.do" method="post">
                <input type="hidden" name="seq" id="seq"
                    value="<c:out value='${vo.seq}'/>">
    
                <div class="form-group">
                    <label for="userId">제목</label> <input type="text" maxlength="50"
                        name="title" id="title" value="${vo.title }">
                </div>
                <div class="form-group">
                    <label for="name">조회수</label> <input type="text" maxlength="9"
                        name="readCnt" id="readCnt" disabled value="${vo.readCnt }">
                </div>
                <div class="form-group">
                    <label for="name">등록일</label> <input type="text" name="regDt"
                        id="regDt" disabled value="${vo.regDt }">
                </div>
                <div class="form-group">
                    <label for="name">등록자</label> <input type="text" 
                        name="regId" id="regId" disabled="disabled" value="${vo.regId}">
                </div>
    
                <div class="form-group">
                    <label for="contents">내용</label>
                    <textarea class="contents" id="contents" name="contents"
                        placeholder="내용을 입력하세요">${vo.contents}</textarea>
                </div>
            </form>
            <!--// form area -->
        </div>
        </main>

    </div>
    <jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
</body>
</html>