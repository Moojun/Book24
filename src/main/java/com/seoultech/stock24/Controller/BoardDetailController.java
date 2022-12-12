package com.seoultech.stock24.Controller;

import com.seoultech.stock24.Entity.Board;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Date;
import java.util.Properties;

@WebServlet("/board/detail")
public class BoardDetailController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        //request.setAttribute("id", id);

        String resource = "db.properties";
        Properties properties = new Properties();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            InputStream reader = getClass().getClassLoader().getResourceAsStream(resource);
            properties.load(reader);

            String dbURL = properties.getProperty("url");
            String dbID = properties.getProperty("username");
            String dbPassword = properties.getProperty("password");
            String sql = "select * from board where id = ?";

            Connection con = DriverManager.getConnection(dbURL, dbID, dbPassword);
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            rs.next();

            String title = rs.getString("title");
            String writerId = rs.getString("writer_id");
            Date regDate = rs.getDate("regdate");
            int hit = rs.getInt("hit");
            String files = rs.getString("files");
            String content = rs.getString("content");

            Board board = new Board(id, title, writerId, regDate,
                    hit, files, content);

            request.setAttribute("n", board);

            rs.close();
            pst.close();
            con.close();

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

        // forward
        request.getRequestDispatcher("detail.jsp").forward(request, response);
    }
}
