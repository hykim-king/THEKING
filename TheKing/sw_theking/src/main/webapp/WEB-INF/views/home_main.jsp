<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Document</title>
  <style>
    .navbar {
    display: flex; /* 플렉스박스를 사용하여 레이아웃 배치 */
    justify-content: space-between; /* 항목들을 균등하게 분배 (로고-메인메뉴-유틸리티) */
    align-items: center; /* 세로 방향으로 항목들을 중앙 정렬 */
    width: 100%; /* 부모 컨테이너의 전체 너비 사용 */
}
.navbar-nav {
    list-style: none; /* 목록의 점 제거 */
    margin: 0;
    padding: 0;
    display: flex; /* 메뉴 항목들을 한 줄로 표시 */
    flex-grow: 1; /* 남은 공간을 차지하도록 */
    justify-content: center; /* 메뉴 항목들을 중앙에 가깝게 정렬 */
}
.navbar-nav li {
    margin: 0 15px; /* 메뉴 항목들 사이의 간격 */
}
  </style>
</head>
<body>
  <header>
    <nav class="navbar">
      <div>
        <a href="/ehr/home.do">GG MAP</a>
      </div>
      <ul class="navbar-nav">
        <li><a href="/ehr/home.do">홈</a></li> 
        <li><a href="/ehr/tour/doRetrieve.do">관광지</a></li>
        <li><a href="/ehr/festival/main.do">축제</a></li>
        <li><a href="/ehr/board/list">게시판</a></li>
        <li><a href="/ehr/board/list">공지사항</a></li>
      </ul>
    </nav>

  </header>
</body>
</html>