<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.seoultech.stock24.User.UserDAO" %>
<%@ page import="java.io.PrintWriter" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Stock 24</title>
</head>
<body>
<%
    String userEmail = request.getParameter("userEmail");
    String userPassword = request.getParameter("userPassword");
    String userName = "";

    UserDAO userDAO = new UserDAO();
    int result = userDAO.login(userEmail, userPassword);

    if (result == 1) {
        UserDAO acceptUserDAO = userDAO.getUserInfo(userEmail);
        userName = acceptUserDAO.getUserName();

        session.setAttribute("userName", userName);   // index.jsp 로 이동했을 때 session 에 저장

        PrintWriter script = response.getWriter();
        script.println("<script>");
        script.println("alert('로그인 성공')");
        script.println("location.href = '../index.jsp'");
        script.println("</script>");
    } else if(result == 0) {
        PrintWriter script = response.getWriter();
        script.println("<script>");
        script.println("alert('비밀번호가 틀립니다.')");
        script.println("history.back()");
        script.println("</script>");
    } else if(result == -2) {
        PrintWriter script = response.getWriter();
        script.println("<script>");
        script.println("alert('이메일을 다시 확인해주세요.')");
        script.println("history.back()");
        script.println("</script>");
    } else if(result == -1) {
        PrintWriter script = response.getWriter();
        script.println("<script>");
        script.println("alert('서버 오류 입니다.')");
        script.println("history.back()");
        script.println("</script>");
    }
%>

</body>
</html>
