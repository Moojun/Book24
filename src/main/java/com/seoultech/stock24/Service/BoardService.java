package com.seoultech.stock24.Service;

import com.seoultech.stock24.Entity.Board;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class BoardService {

    public List<Board> getBoardList() {

        return getBoardList("title", "", 1);
    }

    public List<Board> getBoardList(int page) {

        return getBoardList("title", "", page);
    }

    public List<Board> getBoardList(String field, String query, int page) {
        /*
         field와 query는 "WHERE TITLE LIKE ?" 부분에 들어간다.
         field: title or writer_id
         query: 문자열 A
         page(앞): 1, 11, 21, 31 -> an = 1 + (page - 1) * 10 [등차수열]
         page(뒤): 10, 20, 30, 40 -> page * 10
         */

        List<Board> list = new ArrayList<>();

        String sql = "SELECT * FROM ( " +
                " SELECT @ROWNUM := @ROWNUM + 1 AS ROWNUM, N.* " +
                " FROM (SELECT * FROM board WHERE " + field + " LIKE ? " +
                " ORDER BY REGDATE desc) AS N " +
                ") AS A, (SELECT @ROWNUM := 0) AS B " +
                "WHERE (ROWNUM BETWEEN ? AND ?); ";

        String resource = "db.properties";
        Properties properties = new Properties();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            InputStream reader = getClass().getClassLoader().getResourceAsStream(resource);
            properties.load(reader);

            String dbURL = properties.getProperty("url");
            String dbID = properties.getProperty("username");
            String dbPassword = properties.getProperty("password");

            Connection con = DriverManager.getConnection(dbURL, dbID, dbPassword);
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, "%" + query + "%");    // 검색을 위한 패턴 (query)
            pst.setInt(2, 1 + (page - 1) * 10);
            pst.setInt(3, page * 10);
            ResultSet rs = pst.executeQuery();

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
            pst.close();
            con.close();
        } catch (ClassNotFoundException | SQLException | IOException e) {
            throw new RuntimeException(e);
        }

        return list;

    }
    public int getBoardCount() {

        return getBoardCount("title", "");
    }

    public int getBoardCount(String field, String query) {

        int count = 0;

        String sql = "SELECT COUNT(*) AS COUNT FROM( " +
                " SELECT ROW_NUMBER() OVER(ORDER BY ID DESC) AS ROWNUM, " +
                "    board.* FROM board where " + field + " LIKE ?  ) TMP";

        String resource = "db.properties";
        Properties properties = new Properties();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            InputStream reader = getClass().getClassLoader().getResourceAsStream(resource);
            properties.load(reader);

            String dbURL = properties.getProperty("url");
            String dbID = properties.getProperty("username");
            String dbPassword = properties.getProperty("password");

            Connection con = DriverManager.getConnection(dbURL, dbID, dbPassword);
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, "%" + query + "%");    // 검색을 위한 패턴 (query)
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                count = rs.getInt("COUNT");
            }

            rs.close();
            pst.close();
            con.close();
        } catch (ClassNotFoundException | SQLException | IOException e) {
            throw new RuntimeException(e);
        }

        return count;
    }

    public Board getBoard(int id) {

        Board board = null;

        String sql = "SELECT * FROM board WHERE id = ?";

        String resource = "db.properties";
        Properties properties = new Properties();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            InputStream reader = getClass().getClassLoader().getResourceAsStream(resource);
            properties.load(reader);

            String dbURL = properties.getProperty("url");
            String dbID = properties.getProperty("username");
            String dbPassword = properties.getProperty("password");

            Connection con = DriverManager.getConnection(dbURL, dbID, dbPassword);
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                int nid = rs.getInt("id");
                String title = rs.getString("title");
                String writerId = rs.getString("writer_id");
                Date regDate = rs.getDate("regdate");
                int hit = rs.getInt("hit");
                String files = rs.getString("files");
                String content = rs.getString("content");

                board = new Board(nid, title, writerId, regDate, hit, files, content);

            }

            rs.close();
            pst.close();
            con.close();
        } catch (ClassNotFoundException | SQLException | IOException e) {
            throw new RuntimeException(e);
        }

        return board;
    }

    public Board getNextBoard(int id) {
        Board board = null;

        String sql = "SELECT @ROWNUM := @ROWNUM + 1 as ROWNUM, board.* " +
                " FROM board, (SELECT @ROWNUM := 0) AS R" +
                " WHERE id = any ( " +
                " SELECT id FROM board " +
                "    WHERE regdate > (SELECT regdate FROM board WHERE id = ? )" +
                ") " +
                " ORDER BY @ROWNUM ASC" +
                " LIMIT 1";

        String resource = "db.properties";
        Properties properties = new Properties();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            InputStream reader = getClass().getClassLoader().getResourceAsStream(resource);
            properties.load(reader);

            String dbURL = properties.getProperty("url");
            String dbID = properties.getProperty("username");
            String dbPassword = properties.getProperty("password");

            Connection con = DriverManager.getConnection(dbURL, dbID, dbPassword);
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                int nid = rs.getInt("id");
                String title = rs.getString("title");
                String writerId = rs.getString("writer_id");
                Date regDate = rs.getDate("regdate");
                int hit = rs.getInt("hit");
                String files = rs.getString("files");
                String content = rs.getString("content");

                board = new Board(nid, title, writerId, regDate, hit, files, content);

            }

            rs.close();
            pst.close();
            con.close();
        } catch (ClassNotFoundException | SQLException | IOException e) {
            throw new RuntimeException(e);
        }

        return board;
    }

    public Board getPrevBoard(int id) {
        Board board = null;

        String sql = "SELECT @ROWNUM := @ROWNUM + 1 AS ROWNUM, A.* " +
                "FROM (SELECT * FROM board ORDER BY regdate DESC) AS A, " +
                "(SELECT @ROWNUM := 0) AS R " +
                "WHERE regdate <  ( " +
                "SELECT regdate FROM board WHERE id = ? " +
                ") " +
                "LIMIT 1 ";

        String resource = "db.properties";
        Properties properties = new Properties();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            InputStream reader = getClass().getClassLoader().getResourceAsStream(resource);
            properties.load(reader);

            String dbURL = properties.getProperty("url");
            String dbID = properties.getProperty("username");
            String dbPassword = properties.getProperty("password");

            Connection con = DriverManager.getConnection(dbURL, dbID, dbPassword);
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                int nid = rs.getInt("id");
                String title = rs.getString("title");
                String writerId = rs.getString("writer_id");
                Date regDate = rs.getDate("regdate");
                int hit = rs.getInt("hit");
                String files = rs.getString("files");
                String content = rs.getString("content");

                board = new Board(nid, title, writerId, regDate, hit, files, content);

            }

            rs.close();
            pst.close();
            con.close();
        } catch (ClassNotFoundException | SQLException | IOException e) {
            throw new RuntimeException(e);
        }

        return board;
    }
}
