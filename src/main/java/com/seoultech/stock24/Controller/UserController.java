package com.seoultech.stock24.Controller;

import com.seoultech.stock24.User.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("*.do")
public class UserController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doAction(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doAction(request, response);
    }

    public void doAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html; charset=UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        String uri = request.getRequestURI();

        // session 확인
        HttpSession session = request.getSession();

        if (uri.equals("/loginAction.do")) {
            String userEmail = request.getParameter("userEmail");
            String userPassword = request.getParameter("userPassword");
            String userID = "";

            UserDAO userDAO = new UserDAO();
            int result = userDAO.login(userEmail, userPassword);

            PrintWriter script = response.getWriter();

            if (result == 1) {
                UserDAO acceptUserDAO = userDAO.getUserInfo(userEmail);
                userID = acceptUserDAO.getUserId();

                session.setAttribute("userID", userID);   // index.jsp 로 이동했을 때 session 에 저장
                session.setMaxInactiveInterval(60 * 60);

                script.println("<script>");
                script.println("alert('로그인 성공')");
                script.println("location.href = '../index.jsp'");
                script.println("</script>");
            }  else if (result == 0) {

                script.println("<script>");
                script.println("alert('비밀번호가 틀립니다.')");
                script.println("history.back()");
                script.println("</script>");
            }  else if(result == -2) {

                script.println("<script>");
                script.println("alert('이메일을 다시 확인해주세요.')");
                script.println("history.back()");
                script.println("</script>");
            }  else if(result == -1) {

                script.println("<script>");
                script.println("alert('서버 오류 입니다.')");
                script.println("history.back()");
                script.println("</script>");
            }
        }

        /*
        주의!
        request.getRequestURI() 의 경우 프로젝트 + 파일경로까지 가져온다.
        e.g. http://localhost:8080/member/joinAction.do
        [return] /member/joinAction.do

        sol)
        String [] uri = request.getRequestURI().split("/");
        String Name = uri[uri.length - 1];  // joinAction.do
         */
        else if (uri.equals("/member/joinAction.do")) {
            String userEmail = request.getParameter("userEmail");
            String userID = request.getParameter("userID");
            String userPassword1 = request.getParameter("userPassword1");

            UserDAO joinUserDAO = new UserDAO();
            joinUserDAO.setUserEmail(userEmail);
            joinUserDAO.setUserId(userID);
            joinUserDAO.setUserPassword(userPassword1);

            int result = joinUserDAO.join(joinUserDAO);

            PrintWriter script = response.getWriter();

            if (result == -1) {
                script.println("<script>");
                script.println("alert('Server is an error')");
                script.println("history.back()");
                script.println("</script>");
            } else if (result == 0) {
                script.println("<script>");
                script.println("alert('이미 존재하는 이메일입니다.')");
                script.println("history.back()");
                script.println("</script>");
            } else {
                script.println("<script>");
                script.println("alert('회원가입에 성공하셨습니다!')");
                script.println("location.href = '../login.jsp'");
                script.println("</script>");
            }
        }

        else if (uri.equals("/logout.do")) {

            // 세션 무효화시키고 로그인 페이지로 이동
            session.invalidate();
            response.sendRedirect("login.jsp");
        }

    }

}
