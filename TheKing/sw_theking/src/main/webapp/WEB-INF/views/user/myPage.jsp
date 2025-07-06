<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>떠나볼지도</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="/ehr/resources/js/common.js"></script>
<div><strong>떠나볼지도</strong> Get going</div>
  <script>
  document.addEventListener("DOMContentLoaded", function() {
  	const userDelete = document.getElementById("userDelete");
  	userDelete.addEventListener("click", function(event) {
	    console.log("계정 탈퇴 클릭됨");
	    
	    if (confirm("정말 탈퇴하시겠습니까?")) {
	    	$.ajax({
	    	    type: "POST",
	    	    url: "/ehr/user/doDelete.do",
	    	    async: "true",
	    	    dataType: "html",
	    	    data: {
	    	        "userId": $("#userId").val()
	    	    },
	    	    success: function(response) {
	    	        console.log("success:", response);
	    	        const data = JSON.parse(response);
	    	        if (data.messageId === 1) {
	    	        	alert(data.message);
	    	            location.href = "/ehr/main.do";
	    	        } else {
	    	            // 로그인 실패 메시지 출력
	    	            alert(data.message);
	    	        }
	    	    },
	    	    error: function(response) {
	    	        console.log(response);
	    	    }   
	    	    });
	    }
  	});
  	
  });
  

  </script>
  <style>
  body {
      font-family: 'Arial', sans-serif;
      margin: 0;
      padding: 0;
    }
    nav {
      background-color: #ffdc9f;
      padding: 15px;
      display: flex;
      justify-content: space-between;
      align-items: center;
    }
    nav a {
      margin: 0 10px;
      text-decoration: none;
      color: black;
      font-weight: bold;
    }

    .container {
      display: flex;
      padding: 20px;
    }

    .profile-area {
      width: 200px;
      border: 1px solid #333;
      padding: 20px;
      margin-right: 20px;
    }

    .profile-area img {
      width: 100px;
      height: 100px;
      border-radius: 50%;
      display: block;
      margin: 0 auto 10px auto;
    }

    .info-area {
      flex: 1;
    }

    .section-tabs {
 	  margin-bottom: 20px;
	  text-align: left; /* 필요 시 center로 변경 가능 */
	}
	
	.section-tabs button {
	  margin-right: 10px;
	  padding: 6px 16px;
	  background-color: #f0f0f0;
	  border: 1px solid #bbb;
	  border-radius: 6px;
	  font-weight: bold;
	  cursor: pointer;
	  transition: background-color 0.2s ease;
	}
	
	.section-tabs button:hover {
	  background-color: #e0e0e0;
	}
	
	.section-tabs button:focus {
	  outline: none;
	  border-color: #999;
	  background-color: #ddd;
	}

    .stats {
      display: flex;
      justify-content: space-around;
      padding: 20px;
      border: 1px solid #333;
      margin-bottom: 20px;
    }

    .section {
      border: 1px solid #333;
      padding: 15px;
      margin-bottom: 30px;
    }

    .post, .comment {
      background-color: #ccc;
      height: 20px;
      margin-bottom: 10px;
    }

    .pagination {
      text-align: center;
    }

    .cards {
      display: flex;
      flex-wrap: wrap;
      gap: 10px;
      margin-top: 10px;
    }

    .card {
      width: 150px;
      border: 1px solid #999;
      text-align: center;
    }

    .card img {
      width: 100%;
      height: 100px;
      object-fit: cover;
    }

    .scroll-btn {
      text-align: center;
      font-size: 20px;
      cursor: pointer;
    }

    footer {
      background-color: #eee;
      padding: 10px;
      text-align: center;
    }
  </style>
  
</head>
<body>
<nav>
    <a href="#">홈</a>
    <a href="#">관광지</a>
    <a href="#">축제</a>
    <a href="#">게시판</a>
    <a href="#">공지사항</a>
    <a href="#">로그인</a>
  </nav>
	<div class="container">
  <h2>MY</h2>
  <div class="profile-area">
    <img src="/path/to/profile.png" alt="프로필 이미지">
    <h3>${user.name}</h3>
    <p>${user.email}</p>
    <p>관심지역: 인천</p>
    <br><br>
    <ul>
        <li><a href="/ehr/user/updatePage.do">정보 수정</a></li>
        <li><a href="#" id="userDelete">계정 탈퇴</a></li>
    </ul>
    <input type="hidden" name="userId" value="${loginUser.userId}" id="userId">
  </div>

  <div class="info-area">
    <div class="section-tabs">
    	<button onclick="document.getElementById('posts').scrollIntoView({ behavior: 'smooth' })">게시글</button>
    	<button onclick="document.getElementById('comments').scrollIntoView({ behavior: 'smooth' })">댓글</button>
    	<button onclick="document.getElementById('tour').scrollIntoView({ behavior: 'smooth' })">관광지</button>
    	<button onclick="document.getElementById('festival').scrollIntoView({ behavior: 'smooth' })">축제</button>
 	</div>

    <div class="section" id="posts">
      <h3>작성한 게시글</h3>
      <div class="post"></div>
      <div class="post"></div>
      <div class="pagination">
        &lt; 1 2 &gt;
      </div>
    </div>

    <div class="section" id="comments">
      <h3>작성한 댓글</h3>
      <div class="comment"></div>
      <div class="comment"></div>
      <div class="comment"></div>
      <div class="pagination">
        &lt; 1 2 &gt;
      </div>
    </div>

    <div class="section" id="tour">
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

    <div class="section" id="festival">
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