<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">

<head>
    <title>회원가입 페이지</title>
    <meta charset="utf-8">

    <link href="../css/join.css?after" rel="stylesheet" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css"/>
    <script src="../js/join.js"></script>

</head>

<body>
    <div class="wrapper">
        <div class="login-logo">
            <i class="fa fa-book"></i> Book 24
            <h1>회원 가입</h1>
        </div>

        <form action="joinAction.jsp" method="post" onsubmit="return signUpCheck()">
            <div class="email">
                <input id="email" type="text" name="userEmail" placeholder="이메일을 입력해 주세요.">
                <div id="emailError" class="error"></div>
            </div>
            <div class="jName">
                <input id="jName" type="text" name="userName" placeholder="이름을 입력해 주세요.">
                <div id="name_Error" class="error"></div>
            </div>
            <div class="password">
                <input id="password" type="password" name="userPassword1" placeholder="비밀번호를 입력해 주세요.">
                <div id="passwordError" class="error"></div>
            </div>
            <div class="passwordCheck">
                <input id="passwordCheck" type="password" name="userPassword2" placeholder="비밀번호를 다시 입력해 주세요.">
                <div id="passwordCheckError" class="error"></div>
            </div>
            <div class="line">
                <hr>
            </div>
            <div class="signUp">
<%--                <button id="signUpButton" onclick="signUpCheck()">가입하기</button>--%>
                <input type="submit" id="signUpButton" value="가입 완료" />
            </div>
        </form>

    </div>

</body>

</html>