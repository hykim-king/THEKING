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
document.addEventListener('DOMContentLoaded', function() {
    console.log('DOMContentLoaded');

    function isEmpty(value) {
        return value == null || value.trim() === '';
    }

    // tourNo
    const tourNoInput = document.querySelector("#tourNo");
    console.log(tourNoInput);

    // 삭제 버튼
    const doDeleteButton = document.querySelector("#doDelete");
    console.log(doDeleteButton);

    // 수정으로 이동 버튼
    const doUpdateMoveBtn = document.querySelector("#doUpdateMove");
    console.log(doUpdateMoveBtn);

    // 수정 이동 버튼 클릭 시
    if (doUpdateMoveBtn) {
        doUpdateMoveBtn.addEventListener('click', function() {
            const tourNo = tourNoInput.value;
            console.log('tourNo:', tourNo);
            window.location.href = '/ehr/tour/doUpdateView.do?tourNo=' + tourNo;
        });
    }

    // 삭제 버튼 클릭 시
    if (doDeleteButton) {
        doDeleteButton.addEventListener('click', function(event) {
            console.log('doDeleteButton click');

            if (isEmpty(tourNoInput.value)) {
                alert("tourNo를 확인 하세요.");
                return;
            }

            if (!confirm('관광지를 삭제 하시겠습니까?')) {
                return;
            }

            $.ajax({
                type: "POST",
                url: "/ehr/tour/doDelete.do",
                async: true,
                dataType: "html",
                data: {
                    "tourNo": tourNoInput.value
                },
                success: function(response) {
                    console.log("success:" + response);
                    const message = JSON.parse(response);

                    if (message.messageId === 1) {
                        alert(message.message);
                        window.location.href = '/ehr/tour/doRetrieve.do';
                    } else {
                        alert(message.message);
                    }
                },
                error: function(response) {
                    console.log("error:" + response);
                }
            });
        });
    }
});
</script>

<body>
	<h2>${TourDTO.name }</h2>
	<h3>${TourDTO.subtitle }</h3>
	<div class="button-area">
	    <input type="hidden" id="tourNo" value="${TourDTO.tourNo}">
		<input type="button" id="doUpdateMove" value="수정"> 
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