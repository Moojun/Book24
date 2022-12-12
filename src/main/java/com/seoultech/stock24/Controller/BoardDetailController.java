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
import java.util.Date;
import java.util.Properties;

@WebServlet("/board/detail")
public class BoardDetailController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        BoardService boardService = new BoardService();
        Board board = boardService.getBoard(id);

        request.setAttribute("n", board);


        // forward
        request.getRequestDispatcher("detail.jsp").forward(request, response);
    }
}
