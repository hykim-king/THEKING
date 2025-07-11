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
<title>관광지 수정</title>
<link rel="stylesheet" href="/ehr/resources/css/tour/tour_mod.css"/>
</head>
<!-- 카카오 우편번호 서비스 스크립트 -->
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="/ehr/resources/js/common.js"></script>
<script>
document.addEventListener("DOMContentLoaded", function() {
    const fullAddress = "${TourDTO.address}";
    const addressParts = fullAddress.trim().split(" ");
    const road = addressParts.slice(0, 3).join(" ");
    const detail = addressParts.slice(3).join(" ");

    document.getElementById("roadAddress").value = road;
    document.getElementById("detailAddress").value = detail;
});
</script>
<script>
function execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            document.getElementById('postcode').value = data.zonecode;
            document.getElementById("roadAddress").value = data.roadAddress;
        }
    }).open();
}

document.addEventListener("DOMContentLoaded", function() {
    // 나머지 초기화 코드들
});
</script>
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
    const roadAddressInput   = document.querySelector("#roadAddress");
    const detailAddressInput   = document.querySelector("#detailAddress");
    const telInput       = document.querySelector("#tel");
    const timeInput      = document.querySelector("#time");
    const holidayInput   = document.querySelector("#holiday");
    const feeInput       = document.querySelector("#fee");
    const doSaveButton   = document.querySelector("#doSave");

    console.log(tourNoInput, nameInput, subtitleInput, contentsInput, roadAddressInput,detailAddressInput,
                 telInput, timeInput, holidayInput, feeInput, doSaveButton);
    
    //목록 이동 버튼
    const moveToListButton = document.querySelector('#moveToList');
    console.log(moveToListButton);
    
   //수정 버튼
    const doUpdateButton = document.querySelector('#doUpdate');
    console.log(doUpdateButton);
    
    //목록 이동 이벤트 감지
    moveToListButton.addEventListener('click',function(event){
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

        if (isEmpty(roadAddressInput.value)) {
            alert('주소를 입력 하세요');
            addressInput.focus();
            return;
        }

        // 사용자 확인
        if(confirm('게시글을 수정 하시겠습니까?')===false) return;
        
        const fullAddress = $("#roadAddress").val() + " " + $("#detailAddress").val();
        
        $.ajax({
            type:"POST",    //GET/POST
            url:"/ehr/tour/doUpdate.do", //서버측 URL
            asyn:"true",    //비동기
            dataType:"html",//서버에서 받을 데이터 타입
            data:{          //파라메터
            	"tourNo":tourNoInput.value,
            	"name" : nameInput.value,
                "subtitle" : subtitleInput.value,
                "contents" : contentsInput.value,
                "address" : fullAddress,
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
<body>
    <jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
	<div class="logo-container">
            
            <a href="/ehr/main.do">
                <img alt="로고이미지" src="${pageContext.request.contextPath}/resources/images/logo2.png">
            </a>
    </div>
<div id="container" class="container">
	<h4 id="h4">관광지 상세 정보 수정</h4>
	<hr>
	<form action="doUpdate.do" method="post">		
		<!-- form area -->
		        <input type="hidden" name="tourNo" id="tourNo" value="<c:out value='${TourDTO.tourNo }'/>">
	            <div>
	                <label for="name">제목</label>
	                <input type="text" id="name" name="name" value="${TourDTO.name }">
	            </div>
	            <div>
	                <label for="subtitle">소제목</label>
	                <input type="text" id="subtitle" name="subtitle" value="${TourDTO.subtitle }">
	            </div>
<!-- 	            <div> -->
<%-- 	                <c:url var="imgUrl" value="${TourDTO.image.imageUrl}" />    --%>
<%-- 	                <img src="${imgUrl}" alt="${TourDTO.name}" width="800" /> --%>
<!-- 	            </div> -->
	            <div>
	                <label for="contents">상세 정보</label>
	                <textarea class="contents" id="contents" name="contents">${TourDTO.contents }</textarea>
	            </div>
	            <div class="form-group form-inline">
				    <label>주소*</label>
				    <input type="text" id="postcode" value="" placeholder="우편번호" readonly>
				    <button type="button" onclick="execDaumPostcode()">주소 검색</button>
				</div>
				
				<div class="detail-address">
				    <input type="text" id="roadAddress" placeholder="도로명 주소" readonly><br/>
				    <input type="text" id="detailAddress" placeholder="상세 주소 입력">
				</div>
				<div>
                    <label for="tel">연락처</label>
                    <textarea class="tel" id="tel" name="tel">${TourDTO.tel }</textarea>
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
	       </form>
	    <div id="modBtn" class="button-group">
            <input type="hidden" id="tourNo" value="${TourDTO.tourNo}">
            <input type="button" id="doUpdate" value="수정"> 
            <button type="button" onclick="history.back()">취소</button>
            <input type="button" id="moveToList" value="취소">
        </div>   
</div>


	<%-- <p>TourDTO: ${TourDTO}</p> --%>
	<%-- <p>Image URL: ${imgUrl}</p> --%>
	<%-- <p>regionSido: ${TourDTO.region.regionSido }</p> --%>
	<%-- <p>regionGugun: ${TourDTO.region.regionGugun }</p> --%>

<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
</body>
</html>