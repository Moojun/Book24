package com.seoultech.stock24.Controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/board/write")
public class WriteController extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        HttpSession session = request.getSession();

        String seUserID = (String)session.getAttribute("userID");

        if (seUserID == null || seUserID.equals("")) {
            printAlertMessage(response, "로그인을 한 뒤 사용할 수 있습니다.");
        }

        response.sendRedirect("/board/write.jsp");

    }

    public static void printAlertMessage(HttpServletResponse response, String message) {
        try {
            PrintWriter printWriter = response.getWriter();
            printWriter.println("<script>");
            printWriter.println("alert('" + message + "')");
            printWriter.println("location.href = '../board/list'");
            printWriter.println("</script>");
            printWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
