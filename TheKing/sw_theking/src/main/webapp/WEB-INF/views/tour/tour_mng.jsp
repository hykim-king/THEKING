<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="CP" value="${pageContext.request.contextPath }" />
<c:set var="now" value="<%=new java.util.Date()%>" />
<c:set var="sysDate"><fmt:formatDate value="${now}" pattern="yyyy-MM-dd_HH:mm:ss" /></c:set> 

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관광지 상세</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script>
$(document).ready(function () {
    const tourNo = "${TourDTO.tourNo}";
    const isLoggedIn = "${not empty sessionScope.user}" === "true";
    const sessionUserId = "${sessionScope.user.userId != null ? sessionScope.user.userId : ''}";

    // 댓글 목록 불러오기
    function loadComments() {
        $.get("/comment/listByTourNo.do", { tourNo: tourNo }, function (res) {
            if (res.status === 1) {
                const comments = res.data;
                const $list = $("#commentList").empty();

                comments.forEach(c => {
                    $list.append(`
                        <li>
                            <strong>${c.userDTO.nickname || '익명'}</strong> (${c.regDt})<br/>
                            ${c.contents}
                        </li>
                    `);
                });
            }
        });
    }

    // 댓글 등록 버튼 클릭
    $("#commentSubmitBtn").on("click", function () {
        const contents = $("#commentContent").val().trim();

        if (!contents) {
            alert("댓글 내용을 입력하세요.");
            return;
        }

        if (!isLoggedIn) {
            alert("로그인이 필요합니다.");
            return;
        }

        $.ajax({
            type: "POST",
            url: "/comment/add.do",
            contentType: "application/json",
            data: JSON.stringify({
                contents: contents,
                targetNo: tourNo
            }),
            success: function (res) {
                const result = JSON.parse(res);
                alert(result.message);
                if (result.flag === 1) {
                    $("#commentContent").val("");
                    loadComments();
                }
            }
        });
    });

    loadComments();
});
</script>

<script>
document.addEventListener('DOMContentLoaded', function() {
    const tourNoInput = document.querySelector("#tourNo");

    const moveToUpdateBtn = document.querySelector("#moveToUpdate");
    moveToUpdateBtn.addEventListener('click', function() {
        if (confirm('수정 페이지로 이동합니다.')) {
            window.location.href = '/ehr/tour/doUpdateView.do?tourNo=' + tourNoInput.value;
        }
    });

    const moveToListBtn = document.querySelector("#moveToList");
    moveToListBtn.addEventListener('click', function() {
        if (confirm('목록으로 이동합니다.')) {
            window.location.href = '/ehr/tour/doRetrieve.do';
        }
    });
});
</script>

<style>
    img {
        width: 400px;
        height: 200px;
    }
</style>
</head>

<body>
    <h2>Tour MOD</h2>
    <hr>
    <a href="/ehr/festival/main.do">
        <img alt="로고이미지" src="${CP}/resources/images/logo.png">
    </a>
    <h4>관광지 수정</h4>
    <hr>

    <!-- 버튼 영역 -->
    <div class="button-area">
        <input type="hidden" id="tourNo" value="${TourDTO.tourNo}">
        <input type="button" id="moveToList" value="목록">
        <input type="button" id="moveToUpdate" value="수정"> 
    </div>

    <!-- 지역 -->
    <div>
        <input type="text" maxlength="9" name="regionSido" id="regionSido" value="${TourDTO.region.regionSido }">
        <input type="text" maxlength="9" name="regionGugun" id="regionGugun" value="${TourDTO.region.regionGugun }">
    </div>
    <hr>

    <!-- 관광지 정보 -->
    <div class="container">
        <div>
            <label for="name">관광지명</label>
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

    <!-- 댓글 입력 영역 -->
    <textarea id="commentContent" placeholder="댓글을 입력하세요."></textarea>
    <button id="commentSubmitBtn">등록</button>

    <c:if test="${empty sessionScope.user}">
        <p style="color: gray;">※ 등록하려면 로그인이 필요합니다.</p>
    </c:if>

    <!-- 댓글 목록 -->
    <ul id="commentList"></ul>
<footer> Footer </footer>
</body>
</html>