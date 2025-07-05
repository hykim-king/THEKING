<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="CP" value="${pageContext.request.contextPath}"></c:set>
<c:url var="imgUrl" value="${TourDTO.image.imageUrl}" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script>

	//DOMContentLoaded: HTML문서 loading이 완료되면 발생 되는 event
	document.addEventListener('DOMContentLoaded', function() {
		console.log('DOMContentLoaded');
		
		function isEmpty(value) {
		    return value == null || value.trim() === '';
		}
		//tourNo
		const tourNoInput = document.querySelector("#tourNo");
		console.log(tourNoInput);

		//삭제 버튼
		const doDeleteButton = document.querySelector("#doDelete");
		console.log(doDeleteButton);

		//삭제 버튼 event감지
		doDeleteButton.addEventListener('click', function(event) {
			console.log('doDeleteButton click');

			//seq
			if (isEmpty(tourNoInput.value) === true) {
				alert("tourNo를 확인 하세요.");
				return;
			}

			if (confirm('관광지를 삭제 하시겠습니까?') === false)
				return;

			$.ajax({
				type : "POST", //GET/POST
				url : "/ehr/tour/doDelete.do", //서버측 URL
				async : "true", //비동기
				dataType : "html",//서버에서 받을 데이터 타입
				data : { //파라메터
					"tourNo" : tourNoInput.value
				},
				success : function(response) {//요청 성공
					console.log("success:" + response)
					//문자열 : Javascript객체로 변환
					const message = JSON.parse(response);

					if (1 === message.messageId) {//삭제 성공
						alert(message.message);

						//목록화면으로 이동
						window.location.href = '/ehr/tour/doRetrieve.do';
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

<body>
	<h2>${TourDTO.name }</h2>
	<h3>${TourDTO.subtitle }</h3>
	<div class="button-area">
	    <input type="hidden" id="tourNo" value="${TourDTO.tourNo}">
		<input type="button" id="doUpdate" value="수정"> 
		<input type="button" id="doDelete" value="삭제">
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
	<img src="${imgUrl}" alt="${TourDTO.name}" width="800" />
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
	<%-- <p>TourDTO: ${TourDTO}</p> --%>
	<%-- <p>Image URL: ${imgUrl}</p> --%>
	<%-- <p>regionSido: ${TourDTO.region.regionSido }</p> --%>
	<%-- <p>regionGugun: ${TourDTO.region.regionGugun }</p> --%>
	<!-- 댓글 목록 -->

	<!-- //댓글 목록 -->
</body>
</html>