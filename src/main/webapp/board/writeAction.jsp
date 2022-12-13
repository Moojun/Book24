<%@ page import="com.seoultech.stock24.User.WriteDAO" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Stock 24</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../css/write.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css"/>
</head>
<body>
<%
    request.setCharacterEncoding("utf-8");
    String seUserID = (String) session.getAttribute("userID");

    WriteDAO writeDAO = new WriteDAO();
    writeDAO.setTitle(request.getParameter("writeTitle"));
    writeDAO.setContent(request.getParameter("writeContent"));

    if (writeDAO.getTitle() == null || writeDAO.getTitle().equals("")
            || writeDAO.getContent() == null || writeDAO.getContent().equals("")) {
        PrintWriter printWriter = response.getWriter();
        printWriter.println("<script>");
        printWriter.println("alert('입력이 안된 부분이 있습니다')");
        printWriter.println("history.back()");
        printWriter.println("</script>");
    } else {
        int result = writeDAO.write(writeDAO.getTitle(), writeDAO.getContent(), seUserID);

        if (result == -1 ) {
            PrintWriter printWriter = response.getWriter();
            printWriter.println("<script>");
            printWriter.println("alert('글쓰기에 실패했습니다')");
            printWriter.println("history.back()");
            printWriter.println("</script>");
        } else {
            PrintWriter printWriter = response.getWriter();
            printWriter.println("<script>");
            printWriter.println("alert('글 작성 완료')");
            printWriter.println("location.href='list'");
            printWriter.println("</script>");
        }
    }
%>

</body>
</html>
