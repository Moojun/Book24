<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Stock 24</title>
    <link href="css/login.css" rel="stylesheet" />
<%--    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css"/>--%>
</head>
<body>
<div class="login-logo">
<%--    <i class="fa fa-book"></i> --%>
    Stock 24
<%--    <h1>프로그래밍 도서 온라인 쇼핑몰입니다.</h1>--%>
</div>
<div class="login-text">
<%--    <ul>--%>
<%--        <li>Chrome 브라우저에서 접속을 권장합니다.</li>--%>
<%--    </ul>--%>
</div>

<div class="login-form">
    <form name="form1" action="member/loginAction.jsp" method="post">
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