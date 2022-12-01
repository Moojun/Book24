package com.seoultech.stock24.Controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Properties;

@WebServlet("/stock/view")
public class StockViewController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=UTF-8");

        String stockName = request.getParameter("stockName");

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

            Connection con = DriverManager.getConnection(dbURL, dbID, dbPassword);
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
                System.out.println("wow! succeed");
            }

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
