<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="CP" value="${pageContext.request.contextPath }" />
<c:set var="now" value="<%=new java.util.Date()%>" />
<c:set var="sysDate"><fmt:formatDate value="${now}" pattern="yyyy-MM-dd_HH:mm:ss" /></c:set> 

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

<script>
document.addEventListener('DOMContentLoaded', function(){ 
    console.log('DOMContentLoaded');
    
    function isEmpty(value) {
        return value == null || value.trim() === '';
    }

    // 요소 가져오기
    const tourNoInput    = document.querySelector("#tourNo");
    const nameInput      = document.querySelector("#name");
    const subtitleInput  = document.querySelector("#subtitle");
    const contentsInput  = document.querySelector("#contents");
    const addressInput   = document.querySelector("#address");
    const imageInput     = document.querySelector("#image");
    const telInput       = document.querySelector("#tel");
    const timeInput      = document.querySelector("#time");
    const holidayInput   = document.querySelector("#holiday");
    const feeInput       = document.querySelector("#fee");
    const doSaveButton   = document.querySelector("#doSave");

    console.log(tourNoInput, nameInput, subtitleInput, contentsInput, addressInput,
                imageInput, telInput, timeInput, holidayInput, feeInput, doSaveButton);
    
    //목록 이동 버튼
    const moveToListButton = document.querySelector('#moveToList');
    console.log(moveToListButton);
    
   //수정 버튼
    const doUpdateButton = document.querySelector('#doUpdate');
    console.log(doUpdateButton);
    
    //목록 이동 이벤트 감지
    moveToListButton.addEventListener('click',function(){
        console.log('moveToListButton click');
        
        if(confirm('목록으로 이동하시겠습니까?')===false) return;
        window.location.href ='/ehr/tour/doRetrieve.do';
    });
    
   //수정 버튼 이벤트 감지
    doUpdateButton.addEventListener('click',function(){
        console.log('doUpdateButton click');
        
     // 필수값 체크
        if (isEmpty(nameInput.value)) {
            alert('제목을 입력 하세요');
            nameInput.focus();
            return;
        }

        if (isEmpty(contentsInput.value)) {
            alert('내용을 입력 하세요');
            contentsInput.focus();
            return;
        }

        if (isEmpty(addressInput.value)) {
            alert('주소를 입력 하세요');
            addressInput.focus();
            return;
        }

        // 사용자 확인
        if(confirm('게시글을 수정 하시겠습니까?')===false) return;
        
        $.ajax({
            type:"POST",    //GET/POST
            url:"/ehr/tour/doUpdate.do", //서버측 URL
            asyn:"true",    //비동기
            dataType:"html",//서버에서 받을 데이터 타입
            data:{          //파라메터
            	"name" : nameInput.value,
                "subtitle" : subtitleInput.value,
                "contents" : contentsInput.value,
                "address" : addressInput.value,
                "image" : imageInput.value,
                "tel" : telInput.value,
                "time" : timeInput.value,
                "holiday" : holidayInput.value,
                "fee" : feeInput.value
                    
            },
            success:function(response){//요청 성공
                console.log("success:"+response)
                //문자열 : Javascript 객체
                const message = JSON.parse(response);
                
                //success:{"messageId":1,"message":"sss 글이 등록 되었습니다."}
                if(1 == message.messageId){
                    alert(message.message);
                    //목록 화면으로 이동
                    window.location.href ='/ehr/tour/doRetrieve.do';
                    
                }else{
                    alert(message.message);
                }
            },
            error:function(response){//요청 실패
                console.log("error:"+response)
            }
        });
        
    });
});
</script>
<style>
    img{
        width:400px;
        height:200px;
    }
</style>
<body>
	<h2>Tour MOD</h2>
	<hr>
	<a href="/ehr/festival/main.do"><img alt="로고이미지" src="${pageContext.request.contextPath}/resources/images/logo.png"></a>
	<h4>관광지 수정</h4>
	<form action="/ehr/tour/doSave.do" method="post">
		<div class="button-area">
		    <input type="hidden" id="tourNo" value="${TourDTO.tourNo}">
			<input type="button" id="doUpdate" value="수정"> 
			<input type="button" id="moveToList"   value="취소">
		</div>

		<div>
			<input type="text" maxlength="9" name="regionSido" id="regionSido"
				value="${TourDTO.region.regionSido }"> <input type="text"
				maxlength="9" name="regionGugun" id="regionGugun"
				value="${TourDTO.region.regionGugun }">
		</div>
		<hr>
	
		<div>
			<label>사진</label> <label>상세 설명</label> <label>댓글</label>
		</div>
		<!-- form area -->
		<div class="container">
		<input type="hidden" name="tourNo" id="tourNo" value="<c:out value='${TourDTO.tourNo }'/>">
	            <div>
	                <label for="name">제목</label>
	                <input type="text" id="name" name="name" value="${TourDTO.name }">
	            </div>
	            <div>
	                <label for="subtitle">소제목</label>
	                <input type="text" id="subtitle" name="subtitle" value="${TourDTO.subtitle }">
	            </div>
	            <div>
	                <c:url var="imgUrl" value="${TourDTO.image.imageUrl}" />   
	                <img src="${imgUrl}" alt="${TourDTO.name}" width="800" />
	            </div>
	            <div>
	                <label for="contents">내용</label>
	                <textarea class="contents" id="contents" name="contents">${TourDTO.contents }</textarea>
	            </div>
	            <div>
	                <label for="address">주소</label>
	                <textarea class="address" id="address" name="address">${TourDTO.address }</textarea>
	        
	            </div>
	            <div>
	                <label for="time">운영시간</label>
	                <textarea class="time" id="time" name="time">${TourDTO.time }</textarea>
	            </div>
	            <div>
	                <label for="holiday">휴일</label>
	                <textarea class="holiday" id="holiday" name="holiday">${TourDTO.holiday }</textarea>
	        
	            </div>
	            <div>
	                <label for="fee">입장료</label>
	                <textarea class="fee" id="fee" name="fee">${TourDTO.fee }</textarea>
	            </div>	   
		</div>
    </form>


	<%-- <p>TourDTO: ${TourDTO}</p> --%>
	<%-- <p>Image URL: ${imgUrl}</p> --%>
	<%-- <p>regionSido: ${TourDTO.region.regionSido }</p> --%>
	<%-- <p>regionGugun: ${TourDTO.region.regionGugun }</p> --%>
	<!-- 댓글 목록 -->

	<!-- //댓글 목록 -->
</body>
</html>