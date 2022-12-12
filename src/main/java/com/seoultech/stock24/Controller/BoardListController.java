package com.seoultech.stock24.Controller;

import com.seoultech.stock24.Entity.Board;
import com.seoultech.stock24.Service.BoardService;

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

        // 전달 형식: list?f=title&q=a
        String field_ = request.getParameter("f");
        String query_ = request.getParameter("q");
        String page_ = request.getParameter("p");    // null 을 처리하기 위해 int 대신 String 사용.

        String field = "title";
        if (field_ != null && !field_.equals("")) {
            field = field_;
        }
        String query = "";
        if (query_ != null && !query_.equals("")) {
            query = query_;
        }
        int page = 1;
        if (page_ != null && !page_.equals("")) {
            page = Integer.parseInt(page_);
        }

        BoardService service = new BoardService();
        List<Board> list = service.getBoardList(field, query, page);
        int count = service.getBoardCount(field, query);

        request.setAttribute("list", list);
        request.setAttribute("count", count);

        // forward
        request.getRequestDispatcher("list.jsp").forward(request, response);
    }
}
