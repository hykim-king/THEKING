<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
 <script src="/ehr/resources/js/common.js"></script>
<head>
<meta charset="UTF-8">
<title>축제 등록</title>
<style>
	img{
		width:400px;
		height:200px;
	}
</style>
<script>

 document.addEventListener('DOMContentLoaded', function(){
   console.log('DOMContentLoaded');

   //contorl
   const nameInput = document.querySelector("#name");
   const subtitleInput = document.querySelector("#subtitle");
   const contentsTextarea = document.querySelector("#contents");
   const addressInput = document.querySelector("#address");
   const telInput = document.querySelector("#tel");
   const feeInput = document.querySelector("#fee");
   const startDateInput = document.querySelector("#startDate");
   const endDateInput = document.querySelector("#endDate");
   const regionNoInput = document.querySelector("#regionNo");
   const imageInput = document.querySelector("#image");



   //등록 버튼을 자바스크립트로 가져 오기
   const doSaveButton = document.querySelector("#doSave");//id(#아이디명), class(.클래스명)
   console.log(doSaveButton);

   //등록 버튼 이벤트 감지
   doSaveButton.addEventListener("click",function(event){
      console.log('qweqwe');
      //제목
      if(isEmpty(nameInput.value) === true){
         alert('제목을 입력 하세요');
         nameInput.focus();
         return;
      }

      //등록자 필수 입력
      if(isEmpty(subtitleInput.value) === true){
         alert('소제목을 입력 하세요');
         subtitleInput.focus();
         return;
      }

      //내용 필수 입력
      if(isEmpty(contentsTextarea.value) === true){
         alert('내용을 입력 하세요');
         contentsTextarea.focus();
         return;
      }
      
   	  //주소 필수 입력
      if(isEmpty(addressInput.value) === true){
         alert('주소를 입력 하세요');
         addressInput.focus();
         return;
      }
    
      //연락처 필수 입력
      if(isEmpty(telInput.value) === true){
         alert('연락처를 입력 하세요');
         telInput.focus();
         return;
      }
    
      //요금 필수 입력
      if(isEmpty(feeInput.value) === true){
         alert('요금을 입력 하세요');
         feeInput.focus();
         return;
      }
    
      //시작 날짜 필수 입력
      if(isEmpty(startDateInput.value) === true){
         alert('시작 날짜를 입력 하세요');
         startDateInput.focus();
         return;
      }
      
    //시작 날짜 필수 입력
      if(isEmpty(endDateInput.value) === true){
         alert('종료 날짜를 입력 하세요');
         endDateInput.focus();
         return;
      }
    
      //지역 코드 필수 입력
      if(isEmpty(regionNoInput.value) === true){
         alert('지역 코드를 입력 하세요');
         regionNoInput.focus();
         return;
      }
      //확인(true)/취소(false)
      if(confirm('등록 하시겠습니까?') === false)return;

      //ajax 비동기 통신
      $.ajax({
         type:"POST",    //GET/POST
         url:"/ehr/festival/doSave.do", //서버측 URL
         asyn:"true",    //비동기
         dataType:"html",//서버에서 받을 데이터 타입
         data:{          //파라메터
            "name": nameInput.value,
            "subtitle": subtitleInput.value,
            "contents": contentsTextarea.value,
            "tel": telInput.value,
            "address": addressInput.value,
            "fee": feeInput.value,
            "startDate": startDateInput.value,
            "endDate": endDateInput.value,
            "regionNo": regionNoInput.value
            
         },
         success:function(response){//요청 성공
             console.log("success:"+response)
             //문자열 : Javascript객체
             const message = JSON.parse(response);
             //{"messageId":1,"message":"제목99 등록 되었습니다.","no":0,"totalCnt":0,"pageSize":0,"pageNo":0}

             if( 1 === message.messageId){//등록 성공
                alert(message.message);

                //목록화면으로 이동
                window.location.href = '/ehr/festival/main.do';
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

</head>
<body>
	<h2>Festival Reg</h2>
	<hr>
	<a href="/ehr/festival/main.do"><img alt="로고이미지" src="${pageContext.request.contextPath}/resources/images/logo.png"></a>
	<h4>축제 등록</h4>
	<hr>
	<div class="container">
		<form action="/ehr/festival/doSave.do" method="post" enctype="multipart/form-data">
			<div>
				<label for="name">제목</label>
				<input type="text" name="name" id="name">
			</div>
			<div>
				<label for="subtitle">소제목</label>
				<input type="text" name="subtitle" id="subtitle">
			</div>			
			<div>
				<label>상세 정보</label>
				<textarea rows="20px" cols="40px" name="contents" id="contents"></textarea>
			</div>
			<div>
				<label for="image">이미지</label>
				<input type="file" name="image" id=image>
			</div>
			<div>
				<label for="address">주소</label>
				<input type="text" name="address" id="address">
			</div>
			<div>
				<label for="tel">연락처</label>
				<input type="text" name="tel" id="tel">
			</div>
			<div>
				<label for="fee">입장료</label>
				<input type="text" name="fee" id="fee">
			</div>
			<div>
				<label for="startDate">시작 날짜</label>
				<input type="date" name="startDate" id="startDate">
				<label for="endDate">종료 날짜</label>
				<input type="date" name="endDate" id="endDate">
			</div>
			<div>
				<label for="regionNo">지역코드</label>
				<input type="text" name="regionNo" id="regionNo">
			</div>
			
			<button type="button" id="doSave">등록</button>
			<button type="button" onclick="history.back()">취소</button>
		</form>
	</div>
</body>
</html>