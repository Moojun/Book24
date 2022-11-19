package com.seoultech.stock24.Controller;

import com.seoultech.stock24.Entity.Notice;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet("/board/list")
public class BoardListController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        List<Notice> list = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://localhost:3306/Stock24?useUnicode=true&characterEncoding=utf8";
            String username = "root";
            String password = "mac";
            String sql = "SELECT * FROM board";

            Connection con = DriverManager.getConnection(url, username, password);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while(rs.next()) {

                int id = rs.getInt("id");
                String title = rs.getString("title");
                String writerId = rs.getString("writer_id");
                Date regDate = rs.getDate("regdate");
                int hit = rs.getInt("hit");
                String content = rs.getString("content");

                Notice notice = new Notice(id, title, writerId, regDate, hit, content);
                list.add(notice);
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
