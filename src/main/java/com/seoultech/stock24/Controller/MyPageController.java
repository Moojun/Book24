package com.seoultech.stock24.Controller;

import com.seoultech.stock24.Entity.Interest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@WebServlet(urlPatterns = {"/view/myPage", "/ind/myPage", "/move/myPage", "/del/myPage"})
public class MyPageController extends HttpServlet {

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

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String uri = request.getRequestURI();
        System.out.println("uri : " + uri);

        // session 확인
        HttpSession session = request.getSession();

        LocalDate currentDate = LocalDate.now();
        String currentDStr = currentDate.toString();    // setLocalDate 는 없어서 String으로 형변환

        List<Interest> interestList = new ArrayList<>();

        String resource = "db.properties";
        Properties properties = new Properties();

        // view/stock.jsp 에서, "관심등록" 버튼을 통해 접근
        if (uri.equals("/view/myPage")) {

            String stockName = request.getParameter("stockName");

            if (stockName == null || stockName.equals("")) {
                printAlertMessage(response, "검색된 주식 정보가 없습니다!");
            } else {   // db 접근

                InputStream reader = getClass().getClassLoader().getResourceAsStream(resource);
                properties.load(reader);

                String dbURL = properties.getProperty("url");
                String dbID = properties.getProperty("username");
                String dbPassword = properties.getProperty("password");

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");

                    java.sql.Connection con = DriverManager.getConnection(dbURL, dbID, dbPassword);

                    String sql = "SELECT * FROM mypage_interest WHERE stock_name = ? AND user_id = ?";
                    PreparedStatement pst = con.prepareStatement(sql);
                    pst.setString(1, stockName);
                    pst.setString(2, (String) session.getAttribute("userID"));

                    ResultSet rs = pst.executeQuery();

                    if (!rs.next()) {    // 만약 해당 주식이 관심목록에 존재하지 않는다면
                        String sql2 = "INSERT INTO mypage_interest (user_id, stock_name, class, regDate) "
                                + "VALUES (?, ?, ?, ?)";
                        PreparedStatement pst2 = con.prepareStatement(sql2);
                        pst2.setString(1, (String) session.getAttribute("userID"));
                        pst2.setString(2, stockName);
                        pst2.setString(3, (String) session.getAttribute("stockClass"));
                        pst2.setString(4, currentDStr);

                        pst2.executeUpdate();
                        System.out.println("여기서 문제?");
                    }

                    rs.close();
                    pst.close();
                    con.close();

                } catch (ClassNotFoundException | SQLException e) {
                    throw new RuntimeException(e);
                }

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");

                    java.sql.Connection con = DriverManager.getConnection(dbURL, dbID, dbPassword);

                    String sql3 = "SELECT * FROM mypage_interest WHERE user_id = ?";
                    PreparedStatement pst3 = con.prepareStatement(sql3);
                    pst3.setString(1, (String) session.getAttribute("userID"));
                    ResultSet rs3 = pst3.executeQuery();

                    while (rs3.next()) {
                        String stock_name = rs3.getString("stock_name");
                        String stock_class = rs3.getString("class");
                        String regDate = rs3.getString("regDate");

                        System.out.println("stname : " + stock_name + ", userID : " + rs3.getString("user_id"));

                        Interest interest = new Interest(stock_name, stock_class, regDate);
                        interestList.add(interest);
                    }

                    rs3.close();
                    pst3.close();
                    con.close();
                } catch (ClassNotFoundException | SQLException e) {
                    throw new RuntimeException(e);
                }
            }

            // MyPage.jsp 로 이동 전에 session 에 저장된 아래 두 값 제거
            session.removeAttribute("stockName");
            session.removeAttribute("stockInfoMap");

            request.setAttribute("interestList", interestList);
            request.getRequestDispatcher("myPage.jsp").forward(request, response);
        }

        // index.jsp 에서 "마이페이지" 메뉴를 통해 접근
        else if (uri.equals("/ind/myPage")) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");

                InputStream reader = getClass().getClassLoader().getResourceAsStream(resource);
                properties.load(reader);

                String dbURL = properties.getProperty("url");
                String dbID = properties.getProperty("username");
                String dbPassword = properties.getProperty("password");

                java.sql.Connection con = DriverManager.getConnection(dbURL, dbID, dbPassword);

                String sql3 = "SELECT * FROM mypage_interest WHERE user_id = ?";
                PreparedStatement pst3 = con.prepareStatement(sql3);
                pst3.setString(1, (String) session.getAttribute("userID"));
                ResultSet rs3 = pst3.executeQuery();

                while (rs3.next()) {
                    String stock_name = rs3.getString("stock_name");
                    String stock_class = rs3.getString("class");
                    String regDate = rs3.getString("regDate");

                    Interest interest = new Interest(stock_name, stock_class, regDate);
                    interestList.add(interest);
                }

                pst3.close();
                con.close();

            } catch (ClassNotFoundException | SQLException e) {
                throw new RuntimeException(e);
            }

            // MyPage.jsp 로 이동 전에 session 에 저장된 아래 두 값 제거
            session.removeAttribute("stockName");
            session.removeAttribute("stockInfoMap");

            request.setAttribute("interestList", interestList);
            request.getRequestDispatcher("../view/myPage.jsp").forward(request, response);
        }

        // myPage.jsp 에서 "보기" 버튼을 누른 경우
        else if (uri.equals("/move/myPage")) {

            String stockName = request.getParameter("stockName");
            request.setAttribute("stockName", stockName);

            // StockViewController 로 forwarding
            request.getRequestDispatcher("../view/stock").forward(request, response);
        }

        // myPage.jsp 에서 "삭제하기" 버튼을 누른 경우
        else if (uri.equals("/del/myPage")) {
            String stockName = request.getParameter("stockName");

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");

                InputStream reader = getClass().getClassLoader().getResourceAsStream(resource);
                properties.load(reader);

                String dbURL = properties.getProperty("url");
                String dbID = properties.getProperty("username");
                String dbPassword = properties.getProperty("password");

                java.sql.Connection con = DriverManager.getConnection(dbURL, dbID, dbPassword);

                String sql = "DELETE FROM mypage_interest WHERE user_id = ? AND stock_name = ?";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, (String)session.getAttribute("userID"));
                pst.setString(2, stockName);

                int res = pst.executeUpdate();
                if (res > 0) {
                    System.out.println("remove is complete");
                }

                pst.close();
                con.close();

            } catch (ClassNotFoundException | SQLException e) {
                throw new RuntimeException(e);
            }

            // MyPageController 내에 /ind/myPage 로 재 forwarding
            request.getRequestDispatcher("/ind/myPage").forward(request, response);
        }

    }

    public static void printAlertMessage(HttpServletResponse response, String message) {
        try {
            PrintWriter printWriter = response.getWriter();
            printWriter.println("<script>");
            printWriter.println("alert('" + message + "')");
            printWriter.println("location.href = 'stock.jsp'");
            printWriter.println("</script>");
            printWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
