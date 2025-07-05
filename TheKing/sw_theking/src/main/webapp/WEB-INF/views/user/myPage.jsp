<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>����������</title>
<div><strong>����������</strong> Get going</div>
  <nav>
    <a href="#">Ȩ</a>
    <a href="#">������</a>
    <a href="#">����</a>
    <a href="#">�Խ���</a>
    <a href="#">��������</a>
    <a href="#">�α���</a>
  </nav>

  <script>
    function confirmAndSubmit() {
        if (confirm("���� Ż���Ͻðڽ��ϱ�?")) {
          document.getElementById("deleteForm").submit();
        }
    }
  </script>
</head>
<body>
	<div class="container">
  <h2>MY</h2>
  <div class="profile-area">
    <img src="/path/to/profile.png" alt="������ �̹���">
    <h3>${user.name}</h3>
    <p>${user.email}</p>
    <p>��������: ��õ</p>
    <br><br>
    <form id="userForm" action="#" method="post">
    <ul>
        <li><a href="/ehr/user/updatePage.do">���� ����</a></li>
        <li><a href="#" onclick="return confirmAndDelete();">���� Ż��</a></li>
    </ul>
    </form>
    
  </div>

  <div class="info-area">
    <div class="stats">
      <div>�Խñ�<br>0</div>
      <div>���<br>0</div>
      <div>������<br>0</div>
      <div>����<br>0</div>
    </div>

    <div class="section">
      <h3>�ۼ��� �Խñ�</h3>
      <div class="post"></div>
      <div class="post"></div>
      <div class="pagination">
        &lt; 1 2 &gt;
      </div>
    </div>

    <div class="section">
      <h3>�ۼ��� ���</h3>
      <div class="comment"></div>
      <div class="comment"></div>
      <div class="comment"></div>
      <div class="pagination">
        &lt; 1 2 &gt;
      </div>
    </div>

    <div class="section">
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

    <div class="section">
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