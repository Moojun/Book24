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
        <li>Chrome 브라우저에서 접속을 권장합니다.</li><br>
        <li>아이디와 비밀번호를 입력 후 "로그인"를 눌러주세요!</li>
        <li>로그인 원활하게 진행되지 않으면, "게스트 입장"버튼을 클릭하여 사이트에 접속하세요!</li>
    </ul>
</div>

<div class="login-form">
    <form name="form1" action="member/login_action" method="post">
        <input type="text" name="userID" class="text-field" id="id" placeholder="이메일">
        <input type="password" name="userPassword" id="pw" class="text-field" placeholder="비밀번호">
        <button type="submit" class="submit-btn" onClick="login()">로그인</button>
        <button type="button" class="submit-btn" onclick="location.href='member/join.jsp'">회원가입</button>
        <button type="button" class="guest-btn" onclick="location.href='mainPage.html'">게스트 입장</button>
    </form>
    <div class="links">
        <a href="#">비밀번호를 잊어버리셨나요?</a>
    </div>
</div>

</body>
</html>