<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>접근 오류</title>
<script>
    alert('관리자만 접근 가능합니다.');
    setTimeout(function(){
    location.href = '/ehr/user/main.do';  // 메인 페이지 URL로 이동
    }, 100);
</script>
</head>
<body>

</body>
</html>