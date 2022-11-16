<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Stock 24</title>
    <link href="css/login.css" rel="stylesheet" />
</head>
<body>
<div class="login-logo">
    Stock 24
<%--    <h1>test</h1>--%>
</div>
<div class="login-text">
    <%--
    <ul>
        <li>test</li>
    </ul>
    --%>
</div>

<div class="login-form">
    <form name="form1" action="loginAction.do" method="post">
        <input type="text" name="userEmail" class="text-field" id="id" placeholder="이메일">
        <input type="password" name="userPassword" id="pw" class="text-field" placeholder="비밀번호">
        <button type="submit" class="submit-btn" >로그인</button>
        <button type="button" class="submit-btn" onclick="location.href='member/join.jsp'">회원가입</button>
        <button type="button" class="guest-btn" onclick="location.href='index.jsp'">비회원으로 계속하기</button>
    </form>
    <div class="links">
        <a href="#">비밀번호를 잊어버리셨나요?</a>
    </div>
</div>

</body>
</html>