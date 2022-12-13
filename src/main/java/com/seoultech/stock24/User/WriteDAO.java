package com.seoultech.stock24.User;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class WriteDAO {

    private String title;
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    String resource = "db.properties";
    Properties properties = new Properties();

    private Connection conn;
    private ResultSet rs;

    public WriteDAO() {
        try {

            // db.properties 파일에서 가져온다.
            InputStream reader = getClass().getClassLoader().getResourceAsStream(resource);
            properties.load(reader);

            String dbURL = properties.getProperty("url");
            String dbID = properties.getProperty("username");
            String dbPassword = properties.getProperty("password");

            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(dbURL, dbID, dbPassword);

        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 작성일자 메소드
    public String getDate() {
        String sql = "SELECT now()";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";  // db 오류
    }

    // 게시글 번호 부여
    public int getNextNum() {
        String sql = "SELECT id from board order by id desc";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) + 1;
            }
            return 1;   // 첫 번째 게시물인 경우
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;  // db 오류
    }

    // 글쓰기
    public int write(String boardTitle, String boardContent, String userID) {
        String sql = "INSERT INTO board (title, writer_id, content, regdate, hit, files, pub) "
                + " VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, boardTitle);
            pstmt.setString(2, userID);
            pstmt.setString(3, boardContent);
            pstmt.setString(4, getDate());
            pstmt.setInt(5, 0);
            pstmt.setString(6, "");
            pstmt.setInt(7, 0);
            return pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;  // db 오류

    }
}
