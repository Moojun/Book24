package com.seoultech.stock24.Controller;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
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

        // 실제로 stock 정보를 담을 List
        List<String> stockName = new LinkedList<>();

        String stockName__ = request.getParameter("stockName");
        String seStockName = (String) checkedUserLoginSession.getAttribute("stockNameFromMyPage");

        // redirect 할 때, session 에 값을 저장했으므로 session 에서 값을 가져온다.
        if (seStockName != null){
            stockName__ = seStockName;
        }
        checkedUserLoginSession.removeAttribute("stockNameFromMyPage"); // 사용 후 바로 제거

        if (stockName__ == null || stockName__.equals("")) {
            printAlertMessage(response, "입력된 정보가 없습니다.");
        } else {
            // stock.jsp 에서 "갱신하기" 버튼을 누른 경우, stockName__으로 오는 형식은 아래와 같다.
            // [삼성증권, 삼성전자, 삼성물산] : 따라서, 오른쪽 왼쪽 대괄호를 제거해야 함
            stockName__ = stockName__.replace('[', ' ');
            stockName__ = stockName__.replace(']', ' ');
            stockName__ = stockName__.replaceAll(" ", "");

            if (stockName__.contains(",")) {
                String[] arr = stockName__.split(",");
                stockName.addAll(Arrays.asList(arr));
            } else {
                stockName.add(stockName__);
            }
        }

        // 동시에 받는 주식은 최대 3개까지로 설정
        if (stockName.size() > 3) {
            printAlertMessage(response, "너무 많은 주식 명칭을 입력하셨어요!");
        }

        String[] stockCode = new String[3];
        String[] stockClass = new String[3]; // KOSPI or KOSDAQ

        List<Map<String, Map<String, Float>>> manyStockInfoList = new ArrayList<>();

        String resource = "db.properties";
        Properties properties = new Properties();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            InputStream reader = getClass().getClassLoader().getResourceAsStream(resource);
            properties.load(reader);

            String dbURL = properties.getProperty("url");
            String dbID = properties.getProperty("username");
            String dbPassword = properties.getProperty("password");

            java.sql.Connection con = DriverManager.getConnection(dbURL, dbID, dbPassword);

            for (int i = 0; i < stockName.size(); i++) {

                String sql = "SELECT * FROM stock_kr WHERE name = ?";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, stockName.get(i));
                ResultSet rs = pst.executeQuery();

                if (!rs.next()) {
                    printAlertMessage(response, "올바른 주식 명칭이 아닙니다.");
                } else {
                    stockCode[i] = rs.getString("code");
                    stockClass[i] = rs.getString("class");
                }
            }
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < stockName.size(); i++) {

            // 웹 크롤링
            String URL = "https://finance.naver.com/item/main.naver?code=";
            URL = URL + stockCode[i];
            Connection connection = Jsoup.connect(URL);
            try {
                Document document = connection.get();
                Elements elements = document.select(".date");
                String[] str = elements.text().split(" ");
                Elements currentList = document.select(".new_totalinfo dl>dd");

                String currentTime = str[1];
                String currentPrice = currentList.get(3).text().split(" ")[1];  // 현재가

                System.out.println("종목명 : " + stockName.get(i));
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

                java.sql.Connection con = DriverManager.getConnection(dbURL, dbID, dbPassword);

                if (!currentTime.equals("기준(장마감)")) {
                    String sql = "INSERT INTO stock_price (user_id, stock_name, date, time, price)" +
                            " VALUES(?, ?, ?, ?, ?)";
                    PreparedStatement pst = con.prepareStatement(sql);
                    pst.setString(1, userID);
                    pst.setString(2, stockName.get(i));

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
                pst.setString(2, stockName.get(i));
                pst.setString(3, serverDate);   // currentDate -> serverDate 로 수정
                ResultSet rs = pst.executeQuery();

                while (rs.next()) {
                    String time = rs.getString("time");
                    Float price = rs.getFloat("price");

                    Map<String, Map<String, Float>> map2 = new HashMap<>();
                    Map<String, Float> map = new HashMap<>();
                    map.put(time, price);
                    map2.put(stockName.get(i), map);

                    manyStockInfoList.add(map2);
                }

            } catch(IOException | ClassNotFoundException | SQLException e){
                e.printStackTrace();
            }
        }
        // System.out.println("stockName size : " + stockName.size());
        // System.out.println("manyStockInfoList size: " + manyStockInfoList.size());

        HttpSession session = request.getSession();

        // 먼저 기존의 세션 제거
        //session.removeAttribute("stockName");

        session.setAttribute("stockName", stockName); // String [] 이 아닌 ArrayList 로 변환해서 넣는 이유: jstl에서 ArrayList 를 배열처럼 인식한다
        session.setAttribute("stockClass", stockClass);
        session.setMaxInactiveInterval(60 * 60);   // session 만료 시간 3600초(60분) 설정

        HttpSession session2 = request.getSession();
        session2.setAttribute("manyStockInfoList", manyStockInfoList);
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