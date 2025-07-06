<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>����������</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="/ehr/resources/js/common.js"></script>
<div><strong>����������</strong> Get going</div>
  <script>
  document.addEventListener("DOMContentLoaded", function() {
  	const userDelete = document.getElementById("userDelete");
  	userDelete.addEventListener("click", function(event) {
	    console.log("���� Ż�� Ŭ����");
	    
	    if (confirm("���� Ż���Ͻðڽ��ϱ�?")) {
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
	    	            // �α��� ���� �޽��� ���
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
	  text-align: left; /* �ʿ� �� center�� ���� ���� */
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
    <a href="#">Ȩ</a>
    <a href="#">������</a>
    <a href="#">����</a>
    <a href="#">�Խ���</a>
    <a href="#">��������</a>
    <a href="#">�α���</a>
  </nav>
	<div class="container">
  <h2>MY</h2>
  <div class="profile-area">
    <img src="/path/to/profile.png" alt="������ �̹���">
    <h3>${user.name}</h3>
    <p>${user.email}</p>
    <p>��������: ��õ</p>
    <br><br>
    <ul>
        <li><a href="/ehr/user/updatePage.do">���� ����</a></li>
        <li><a href="#" id="userDelete">���� Ż��</a></li>
    </ul>
    <input type="hidden" name="userId" value="${loginUser.userId}" id="userId">
  </div>

  <div class="info-area">
    <div class="section-tabs">
    	<button onclick="document.getElementById('posts').scrollIntoView({ behavior: 'smooth' })">�Խñ�</button>
    	<button onclick="document.getElementById('comments').scrollIntoView({ behavior: 'smooth' })">���</button>
    	<button onclick="document.getElementById('tour').scrollIntoView({ behavior: 'smooth' })">������</button>
    	<button onclick="document.getElementById('festival').scrollIntoView({ behavior: 'smooth' })">����</button>
 	</div>

    <div class="section" id="posts">
      <h3>�ۼ��� �Խñ�</h3>
      <div class="post"></div>
      <div class="post"></div>
      <div class="pagination">
        &lt; 1 2 &gt;
      </div>
    </div>

    <div class="section" id="comments">
      <h3>�ۼ��� ���</h3>
      <div class="comment"></div>
      <div class="comment"></div>
      <div class="comment"></div>
      <div class="pagination">
        &lt; 1 2 &gt;
      </div>
    </div>

    <div class="section" id="tour">
      <h3>�����ִ� ������</h3>
      <div class="scroll-btn">��</div>
      <div class="cards">
        <div class="card">
          <img src="/path/to/image.jpg" alt="������">
          <div>����</div>
        </div>
      </div>
      <div class="scroll-btn">��</div>
    </div>

    <div class="section" id="festival">
      <h3>�����ִ� ����</h3>
      <div class="scroll-btn">��</div>
      <div class="cards">
        <div class="card">
          <img src="/path/to/image.jpg" alt="����">
          <div>����</div>
        </div>
      </div>
      <div class="scroll-btn">��</div>
    </div>
  </div>
</div>

<footer>
  Footer
</footer>
</body>
</html>