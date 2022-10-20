<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">

<head>
    <title>회원가입 페이지</title>
    <meta charset="utf-8">

    <link href="../css/signup.css" rel="stylesheet" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css"/>
    <script src="../js/signup.js"></script>

</head>

<body>
    <div class="wrapper">
        <div class="login-logo">
            <i class="fa fa-book"></i> Book 24
            <h1>회원 가입</h1>
        </div>

        <div class="email">
            <input id="email" type="text" name="userEmail" placeholder="이메일을 입력해 주세요.">
            <div id="emailError" class="error"></div>
        </div>
        <div class="id_">
            <input id="id_"  type="text" name="userID" placeholder="아이디를 입력해 주세요.">
            <div id="id_Error" class="error"></div>
        </div>
        <div class="password">
            <input id="password" type="password" name="userPassword" placeholder="비밀번호를 입력해 주세요.">
            <div id="passwordError" class="error"></div>
        </div>
        <div class="passwordCheck">
            <input id="passwordCheck" type="password" placeholder="비밀번호를 다시 입력해 주세요.">
            <div id="passwordCheckError" class="error"></div>
        </div>

        <div class="line">
            <hr>
        </div>
        <div class="signUp">
            <button id="signUpButton" onclick="signUpCheck()">가입하기</button>
        </div>
    </div>


</body>

</html>