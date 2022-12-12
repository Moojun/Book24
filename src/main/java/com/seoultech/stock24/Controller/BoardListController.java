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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@WebServlet("/board/list")
public class BoardListController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        List<Board> list = new ArrayList<>();

        String resource = "db.properties";
        Properties properties = new Properties();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            InputStream reader = getClass().getClassLoader().getResourceAsStream(resource);
            properties.load(reader);

            String dbURL = properties.getProperty("url");
            String dbID = properties.getProperty("username");
            String dbPassword = properties.getProperty("password");
            String sql = "select * from board";

            Connection con = DriverManager.getConnection(dbURL, dbID, dbPassword);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while(rs.next()) {

                int id = rs.getInt("id");
                String title = rs.getString("title");
                String writerId = rs.getString("writer_id");
                Date regDate = rs.getDate("regdate");
                int hit = rs.getInt("hit");
                String files = rs.getString("files");
                String content = rs.getString("content");

                Board board = new Board(id, title, writerId, regDate, hit, files, content);
                list.add(board);
            }
            rs.close();
            st.close();
            con.close();

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

        request.setAttribute("list", list);

        // forward
        request.getRequestDispatcher("list.jsp").forward(request, response);
    }
}
