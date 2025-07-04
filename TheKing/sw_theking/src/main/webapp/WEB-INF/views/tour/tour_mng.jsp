<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>tour_reg</title>
</head>
<body>
    <h2>관광지 상세 수정 </h2>
    <hr>
    <form action="/ehr/tour/doUpdate.do" method="post" >
    <div>
        <label for="name" >제목</label>
        <input type="text" name="name" id="name" maxlength="200" required placeholder="제목" value="${vo.name }">
    </div>
    <div>
        <label for="subtitle" >소제목</label>
        <input type="text" name="subtitle" id="subtitle" maxlength="20" required  placeholder="소제목" value="${vo.subtitle }">
    </div>
    <div>
        <label for="contents" >내용</label>
        <textarea id="contents" name="contents"  placeholder="내용을 입력하세요." class="contents">${vo.contents }</textarea>
    </div>
    <div>
        <label for="address" >주소</label>
        <input type="text" name="address" id="address" maxlength="20" required  placeholder="주소" value="${vo.address }">
    </div>
    <!-- 이미지 변수명 뭐 넣어야 하지 -->
    <div>
        <label for="image" >이미지</label>
        <input type="file" name="image" id="image" accept="/resources/image/tour/*"> 
    </div>
    <div>
        <label for="tel" >연락처</label>
        <input type="text" name="tel" id="tel" maxlength="20" required  placeholder="연락처" value="${vo.tel }">
    </div>
    <div>
        <label for="time" >운영시간</label>
        <input type="text" name="time" id="time" maxlength="20" required  placeholder="운영시간" value="${vo.time }">
    </div>
    <div>
        <label for="holiday" >휴일</label>
        <input type="text" name="holiday" id="holiday" maxlength="20" required  placeholder="휴일" value="${vo.holiday }">
    </div>
    <div>
        <label for="fee" >입장료</label>
        <input type="text" name="fee" id="fee" maxlength="20" required  placeholder="입장료" value="${vo.fee }">
    </div>
    </form>
     <!-- Button area -->
    <div  class="button-area">
         <input type="button" id="doUpdate" value="수정">
         <input type="button" id="moveToList" value="취소">
    </div>
    <!--// Button area -->

</body>
</html>