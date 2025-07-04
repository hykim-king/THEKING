<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>tour_reg</title>
</head>
<body>
    <h2>관광지 상세 등록</h2>
    <hr>
    <!-- form area -->
    <form action="/ehr/tour/doSave.do" method="post" >
    <div>
	    <label for="title" >제목</label>
	    <input type="text" name="title" id="title" maxlength="200" required placeholder="제목" >
    </div>
    <div>
        <label for="subtitle" >소제목</label>
        <input type="text" name="subtitle" id="subtitle" maxlength="20" required  placeholder="소제목">
    </div>
    <div>
        <label for="contents" >내용</label>
        <textarea id="contents" name="contents"  placeholder="내용" class="contents"></textarea>
    </div>
    <div>
        <label for="address" >주소</label>
        <input type="text" name="address" id="address" maxlength="20" required  placeholder="주소">
    </div>
    <!-- 이미지 변수명 뭐 넣어야 하지 -->
    <div>
        <label for="image" >이미지</label>
        <input type="file" name="image" id="image" accept="/resources/image/tour/*"> 
    </div>
    <div>
        <label for="tel" >연락처</label>
        <input type="text" name="tel" id="tel" maxlength="20" required  placeholder="연락처">
    </div>
    <div>
        <label for="time" >운영시간</label>
        <input type="text" name="time" id="time" maxlength="20" required  placeholder="운영시간">
    </div>
    <div>
        <label for="holiday" >휴일</label>
        <input type="text" name="holiday" id="holiday" maxlength="20" required  placeholder="휴일">
    </div>
    <div>
        <label for="fee" >입장료</label>
        <input type="text" name="fee" id="fee" maxlength="20" required  placeholder="입장료">
    </div>
     <!-- Button area -->
    <div  class="button-area">
         <input type="button" id="doSave" value="등록">
         <input type="button" id="moveToList" value="취소">
    </div>
    </form>
    <!--// Button area -->

</body>
</html>