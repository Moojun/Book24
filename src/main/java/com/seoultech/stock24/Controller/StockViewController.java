package com.seoultech.stock24.Controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.Connection;
import org.jsoup.select.Elements;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
// import java.sql.Connection; // In Java, cannot import two different classes with the same name.
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@WebServlet("/view/stock")
public class StockViewController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession checkedUserLoginSession = request.getSession();
        String userID = (String) checkedUserLoginSession.getAttribute("userID");

        if (userID == null || userID.equals("")) {
            printAlertMessage(response, "로그인 후 이용해 주세요!");
        }

        String stockName = request.getParameter("stockName");
        if (stockName == null) {
            // redirect 할 때, session에 값을 저장했으므로 session에서 값을 가져온다.
            stockName = (String) checkedUserLoginSession.getAttribute("stockName");
        }
        String stockCode = "";
        String stockClass = ""; // KOSPI or KOSDAQ

        Map<String, Float> stockInfoMap = new LinkedHashMap<>();

        String resource = "db.properties";
        Properties properties = new Properties();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            InputStream reader = getClass().getClassLoader().getResourceAsStream(resource);
            properties.load(reader);

            String dbURL = properties.getProperty("url");
            String dbID = properties.getProperty("username");
            String dbPassword = properties.getProperty("password");
            String sql = "SELECT * FROM stock_kr WHERE name = ?";

            java.sql.Connection con = DriverManager.getConnection(dbURL, dbID, dbPassword);
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, stockName);
            ResultSet rs = pst.executeQuery();

            if (!rs.next()) {
                printAlertMessage(response, "올바른 주식 명칭이 아닙니다.");
            }
            else {
                stockCode = rs.getString("code");
                stockClass = rs.getString("class");
                System.out.println("stockClass is " + stockClass);
            }

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

        // 웹 크롤링
        String URL = "https://finance.naver.com/item/main.naver?code=";
        URL = URL + stockCode;
        Connection connection = Jsoup.connect(URL);
        try {
            Document document = connection.get();
            Elements elements = document.select(".date");
            String[] str = elements.text().split(" ");
            Elements currentList = document.select(".new_totalinfo dl>dd");

            //String currentDate = str[0];
            String currentTime = str[1];
            String currentPrice = currentList.get(3).text().split(" ")[1];  // 현재가

            //System.out.println("str[0] is 웹 크롤링 date : " + str[0]);
            //System.out.println("str[1] is 웹 크롤링 time : " + str[1]);
            System.out.println("종목명 : " + stockName);
            System.out.println("주가:" + currentPrice);
            //System.out.println("가져오는 시간:" + currentDate.indexOf('.') +" / " + currentTime);

            // Float 형 변환
            currentPrice = currentPrice.replaceAll(",", "");
            float currentFloatPrice = Float.parseFloat(currentPrice);

            // 현재 날짜 받아오기(년, 월, 일)
            LocalDate localDate = LocalDate.now();
            DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy.MM.dd");
            String serverDate = localDate.format(formatter1);

            // 현재 시간에서 분/초 받아오기
            LocalTime now = LocalTime.now();
            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm:ss");
            String serverClock = now.format(formatter2);
            System.out.println("현재 시:분:초 : " + serverClock);

            Class.forName("com.mysql.cj.jdbc.Driver");

            InputStream reader = getClass().getClassLoader().getResourceAsStream(resource);
            properties.load(reader);

            String dbURL = properties.getProperty("url");
            String dbID = properties.getProperty("username");
            String dbPassword = properties.getProperty("password");

            java.sql.Connection con = DriverManager.getConnection(dbURL,dbID, dbPassword);

            if (!currentTime.equals("기준(장마감)")) {
                String sql = "INSERT INTO stock_price (user_id, stock_name, date, time, price)" +
                        " VALUES(?, ?, ?, ?, ?)";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, userID);
                pst.setString(2, stockName);

                // 크롤링 결과 사용 하면 안됨. 주말의 경우 날짜 갱신이 안된다.
                // currentDate -> serverDate 로 수정
                pst.setString(3, serverDate);

                pst.setString(4, serverClock);      // 현재 시간 사용(크롤링 결과와 무관)
                pst.setFloat(5, currentFloatPrice); // 크롤링 결과 사용
                int result = pst.executeUpdate();
            }

            String sql = "SELECT * FROM stock_price WHERE user_id = ? AND stock_name = ? AND date = ? " +
                    "ORDER BY id DESC LIMIT 10";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, userID);
            pst.setString(2, stockName);
            pst.setString(3, serverDate);   // currentDate -> serverDate 로 수정
            ResultSet rs = pst.executeQuery();

            while(rs.next()) {
                String time = rs.getString("time");
                Float price = rs.getFloat("price");
                stockInfoMap.put(time, price);
            }

        }
        catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        HttpSession session = request.getSession();
        session.setAttribute("stockName", stockName);
        session.setAttribute("stockClass", stockClass);
        session.setMaxInactiveInterval(60 * 60);   // session 만료 시간 3600초(60분) 설정

        HttpSession session2 = request.getSession();
        session2.setAttribute("stockInfoMap", stockInfoMap);
        session2.setMaxInactiveInterval(60 * 60 * 24);

        // forward
        request.getRequestDispatcher("stock.jsp").forward(request, response);
    }

    public static void printAlertMessage(HttpServletResponse response, String message) {
        try {
            PrintWriter printWriter = response.getWriter();
            printWriter.println("<script>");
            printWriter.println("alert('" + message + "')");
            printWriter.println("location.href = '../index.jsp'");
            printWriter.println("</script>");
            printWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}