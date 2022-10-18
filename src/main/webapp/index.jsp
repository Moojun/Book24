<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Book 24</title>
    <link href="css/login.css" rel="stylesheet" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css"/>
    <script src="js/login.js"></script>
</head>
<body>
<div class="login-logo">
    <i class="fa fa-book"></i> Book 24
    <h1>프로그래밍 도서 온라인 쇼핑몰입니다.</h1>
</div>
<div class="login-text">
    <ul>
        <!-- <li>현재 로그인 기능이 원활하지 않습니다. "게스트 입장" 버튼을 클릭하여 사이트에 접속하세요!</li> -->
        <li>Chrome 브라우저에서 접속을 권장합니다.</li><br>
        <li>아이디와 비밀번호를 입력 후 "로그인"를 눌러주세요!</li>
        <li>로그인 원활하게 진행되지 않으면, "게스트 입장"버튼을 클릭하여 사이트에 접속하세요!</li>
    </ul>
</div>

<div class="login-form">
    <form name="form1">
        <input type="text" name="id" class="text-field" id="id" placeholder="아이디">
        <input type="password" name="password" id="pw" class="text-field" placeholder="비밀번호">
        <button type="button" class="submit-btn" onClick="login()">로그인</button>
        <button type="button" class="submit-btn" onclick="location.href='signup.html'">회원가입</button>
        <button type="button" class="guest-btn" onclick="location.href='mainPage.html'">게스트 입장</button>
    </form>
    <div class="links">
        <a href="#">비밀번호를 잊어버리셨나요?</a>
    </div>
</div>
<div class="result">
    <h2>가입을 환영합니다!</h2>
    <h3>가입 정보</h3>
    <hr>
    <p>아이디: <span id="result-id"></span><br></p>
    <p>e-mail: <span id="result-email"></span><br></p>
    <button type="button" class="result-btn" onclick="location.href='mainPage.html'">입장하기</button>
</div>
<script>
    document.querySelector(".result").style.display = "none";
</script>
</body>
</html>