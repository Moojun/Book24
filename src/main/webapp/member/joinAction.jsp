<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.seoultech.stock24.User.UserDAO" %>
<%@ page import="java.io.PrintWriter" %>
<%
    request.setCharacterEncoding("UTF-8");
%>

<!DOCTYPE html>
<html lang="ko">
    <head>
        <meta charset="UTF-8">
        <title>Stock 24</title>
    </head>
    <body>
    <%
        String userEmail = request.getParameter("userEmail");
        String userName = request.getParameter("userName");
        String userPassword1 = request.getParameter("userPassword1");
        String userPassword2 = request.getParameter("userPassword2");

        /*
        Server connect error: -1
        Email already exists: 0
        join success: 1
         */

        if (userEmail == null || userPassword1 == null || userPassword2 == null) {
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("alert('빈칸을 확인해 주세요')");
            script.println("history.back()");
            script.println("</script>");
        }
        else if (!userPassword1.equals(userPassword2)) {
            PrintWriter script = response.getWriter();
            script.println("<script>");
            script.println("alert('비밀번호가 일치하지 않습니다')");
            script.println("history.back()");
            script.println("</script>");
        }
        else {
            UserDAO userDAO = new UserDAO();

            userDAO.setUserEmail(userEmail);
            userDAO.setUserName(userName);
            userDAO.setUserPassword(userPassword1);

            int result = userDAO.join(userDAO);

            if (result == -1) {
                PrintWriter script = response.getWriter();
                script.println("<script>");
                script.println("alert('Server is an error')");
                script.println("history.back()");
                script.println("</script>");
            }
            else if (result == 0) {
                PrintWriter script = response.getWriter();
                script.println("<script>");
                script.println("alert('이미 존재하는 이메일입니다.')");
                script.println("history.back()");
                script.println("</script>");
            }
            else {
                PrintWriter script = response.getWriter();
                script.println("<script>");
                script.println("alert('회원가입에 성공하셨습니다!')");
                script.println("location.href = '../login.jsp'");
                script.println("</script>");

            }
        }
    %>
    </body>

</html>