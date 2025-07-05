<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>떠나볼지도</title>
<div><strong>떠나볼지도</strong> Get going</div>
  <nav>
    <a href="#">홈</a>
    <a href="#">관광지</a>
    <a href="#">축제</a>
    <a href="#">게시판</a>
    <a href="#">공지사항</a>
    <a href="#">로그인</a>
  </nav>

  <script>
    function confirmAndSubmit() {
        if (confirm("정말 탈퇴하시겠습니까?")) {
          document.getElementById("deleteForm").submit();
        }
    }
  </script>
</head>
<body>
	<div class="container">
  <h2>MY</h2>
  <div class="profile-area">
    <img src="/path/to/profile.png" alt="프로필 이미지">
    <h3>${user.name}</h3>
    <p>${user.email}</p>
    <p>관심지역: 인천</p>
    <br><br>
    <form id="userForm" action="#" method="post">
    <ul>
        <li><a href="/ehr/user/updatePage.do">정보 수정</a></li>
        <li><a href="#" onclick="return confirmAndDelete();">계정 탈퇴</a></li>
    </ul>
    </form>
    
  </div>

  <div class="info-area">
    <div class="stats">
      <div>게시글<br>0</div>
      <div>댓글<br>0</div>
      <div>관광지<br>0</div>
      <div>축제<br>0</div>
    </div>

    <div class="section">
      <h3>작성한 게시글</h3>
      <div class="post"></div>
      <div class="post"></div>
      <div class="pagination">
        &lt; 1 2 &gt;
      </div>
    </div>

    <div class="section">
      <h3>작성한 댓글</h3>
      <div class="comment"></div>
      <div class="comment"></div>
      <div class="comment"></div>
      <div class="pagination">
        &lt; 1 2 &gt;
      </div>
    </div>

    <div class="section">
      <h3>관심있는 관광지</h3>
      <div class="scroll-btn">▲</div>
      <div class="cards">
        <div class="card">
          <img src="/path/to/image.jpg" alt="관광지">
          <div>제목</div>
        </div>
      </div>
      <div class="scroll-btn">▼</div>
    </div>

    <div class="section">
      <h3>관심있는 축제</h3>
      <div class="scroll-btn">▲</div>
      <div class="cards">
        <div class="card">
          <img src="/path/to/image.jpg" alt="축제">
          <div>제목</div>
        </div>
      </div>
      <div class="scroll-btn">▼</div>
    </div>
  </div>
</div>

<footer>
  Footer
</footer>
</body>
</html>