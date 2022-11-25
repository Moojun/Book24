package com.seoultech.stock24.Controller;

import com.seoultech.stock24.Entity.Notice;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.Date;

@WebServlet("/board/detail")
public class BoardDetailController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        request.setAttribute("id", id);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://localhost:3306/Stock24?useUnicode=true&characterEncoding=utf8";
            String username = "root";
            String password = "mac";
            String sql = "select * from board where id = ?";

            Connection con = DriverManager.getConnection(url, username, password);
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            rs.next();


            String title = rs.getString("title");
            String writerId = rs.getString("writer_id");
            Date regDate = rs.getDate("regdate");
            int hit = rs.getInt("hit");
            String content = rs.getString("content");

            Notice notice = new Notice(id, title, writerId, regDate,
                    hit, content);

            request.setAttribute("n", notice);

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
