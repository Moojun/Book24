package com.seoultech.stock24.Controller;

import com.seoultech.stock24.Entity.Interest;
import com.seoultech.stock24.Entity.MyPost;
import com.seoultech.stock24.Service.MyPageService;

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
import java.util.*;

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

        List<Interest> interestList = new ArrayList<>();    // 관심 목록들을 담을 변수
        List<MyPost> myPostList;        // 내 글 작성 목록들을 담을 변수

        String resource = "db.properties";
        Properties properties = new Properties();

        // view/stock.jsp 에서, "관심등록" 버튼을 통해 접근
        if (uri.equals("/view/myPage")) {

            // 실제로 stock 정보를 담을 List
            List<String> stockName = new LinkedList<>();

            String stockName__ = request.getParameter("stockName");

            // stock.jsp 에서 넘어오는 빈 형식이 '[]' 라, check 필요
            if (stockName__ == null || stockName__.equals("") || stockName__.equals("[]")) {
                printAlertMessage(response, "입력된 정보가 없습니다.");
            }  else {
                // stock.jsp 에서 "관심등록" 버튼을 누른 경우, stockName__으로 오는 형식은 아래와 같다.
                // [삼성증권, 삼성전자, 삼성물산] : 따라서, 오른쪽 왼쪽 대괄호를 제거해야 함
                stockName__ = stockName__.replace('[', ' ');
                stockName__ = stockName__.replace(']', ' ');
                stockName__ = stockName__.replaceAll(" ", "");

                if (stockName__.contains(",")) {
                    String[] arr = stockName__.split(",");
                    stockName.addAll(Arrays.asList(arr));
                } else {
                    stockName.add(stockName__); // 하나인 경우는 바로 더함
                }
            }

            // Session 의 stockClass 배열도 가져와야 함
            String [] seStockClass = (String []) session.getAttribute("stockClass");

            // db 접근
            InputStream reader = getClass().getClassLoader().getResourceAsStream(resource);
            properties.load(reader);

            String dbURL = properties.getProperty("url");
            String dbID = properties.getProperty("username");
            String dbPassword = properties.getProperty("password");

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");

                java.sql.Connection con = DriverManager.getConnection(dbURL, dbID, dbPassword);

                for (int i = 0; i < stockName.size(); i++) {

                    String sql = "SELECT * FROM mypage_interest WHERE stock_name = ? AND user_id = ?";
                    PreparedStatement pst = con.prepareStatement(sql);
                    pst.setString(1, stockName.get(i));
                    pst.setString(2, (String) session.getAttribute("userID"));

                    ResultSet rs = pst.executeQuery();

                    if (!rs.next()) {    // 만약 해당 주식이 관심목록에 존재하지 않는다면
                        String sql2 = "INSERT INTO mypage_interest (user_id, stock_name, class, regDate) "
                                + "VALUES (?, ?, ?, ?)";
                        PreparedStatement pst2 = con.prepareStatement(sql2);
                        pst2.setString(1, (String) session.getAttribute("userID"));
                        pst2.setString(2, stockName.get(i));
                        pst2.setString(3, seStockClass[i]);
                        pst2.setString(4, currentDStr);

                        pst2.executeUpdate();
                    }
                }

                con.close();

            } catch(ClassNotFoundException | SQLException e){
                throw new RuntimeException(e);
            }

            MyPageService myPageService = new MyPageService();
            interestList = myPageService.getMyInterestList((String) session.getAttribute("userID"));
            myPostList = myPageService.getMyPostList((String) session.getAttribute("userID"));

            // MyPage.jsp 로 이동 전에 session 에 저장된 해당 값들 제거
            session.removeAttribute("stockName");
            session.removeAttribute("stockClass");
            session.removeAttribute("manyStockInfoList");

            request.setAttribute("interestList", interestList);
            request.setAttribute("myPostList", myPostList);
            request.getRequestDispatcher("myPage.jsp").forward(request, response);
        }

        // index.jsp 에서 "마이페이지" 메뉴를 통해 접근
        else if (uri.equals("/ind/myPage")) {

            MyPageService myPageService = new MyPageService();
            interestList = myPageService.getMyInterestList((String) session.getAttribute("userID"));
            myPostList = myPageService.getMyPostList((String) session.getAttribute("userID"));

            // MyPage.jsp 로 이동 전에 session 에 저장된 해당 값들 제거
            session.removeAttribute("stockName");
            session.removeAttribute("stockClass");
            session.removeAttribute("manyStockInfoList");

            request.setAttribute("interestList", interestList);
            request.setAttribute("myPostList", myPostList);
            request.getRequestDispatcher("../view/myPage.jsp").forward(request, response);
        }

        // myPage.jsp 에서 "보기" 버튼을 누른 경우
        else if (uri.equals("/move/myPage")) {

            String stockName = request.getParameter("stockName");
            session.setAttribute("stockNameFromMyPage", stockName); // 중복을 피하기 위해 "stockName" 이 아닌 다른 이름으로 세션에 저장

            // StockViewController 로 redirect: 경로 수정을 위해서.
            // 아래 forwarding 의 경우는 경로 수정이 반영되지 않음
            response.sendRedirect("../view/stock");

            // StockViewController 로 forwarding
            //request.getRequestDispatcher("../view/stock").forward(request, response);
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

            // MyPageController 내에 /ind/myPage 로 redirect: 경로 수정을 위해서
            response.sendRedirect("/ind/myPage");
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
