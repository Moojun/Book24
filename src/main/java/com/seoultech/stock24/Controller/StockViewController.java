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
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
// import java.sql.Connection; // In Java, cannot import two different classes with the same name.
import java.util.Properties;

@WebServlet("/stock/view")
public class StockViewController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        String stockName = request.getParameter("stockName");
        String stockCode = "";

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

            PrintWriter script = response.getWriter();
            if (!rs.next()) {
                script.println("<script>");
                script.println("alert('존재하지 않는 주식 이름입니다.')");
                script.println("location.href = '../index.jsp'");
                script.println("</script>");
            }
            else {
                stockCode = rs.getString("code");
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
            System.out.println("str[0] : " + str[0]);
            System.out.println("str[1] : " + str[1]);

            Elements currentList = document.select(".new_totalinfo dl>dd");

            /* test
            for (int i = 0; i < currentList.size(); i++) {
                System.out.println(currentList.get(i).text());
            } */

            String currentDate = str[0];
            String currentTime = str[1];
            String currentPrice = currentList.get(3).text().split(" ")[1];  // 현재가

            System.out.println("종목명 : " + stockName);
            System.out.println("주가:" + currentPrice);
            System.out.println("가져오는 시간:" + currentDate +" / " + currentTime);
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}