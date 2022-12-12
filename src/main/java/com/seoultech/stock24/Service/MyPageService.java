package com.seoultech.stock24.Service;

import com.seoultech.stock24.Entity.Interest;
import com.seoultech.stock24.Entity.MyPost;

import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MyPageService {

    public List<Interest> getMyInterestList(String sessionUserID) {
        // MyPage left side: 관심목록 조회

        List<Interest> interestList = new ArrayList<>();

        String resource = "db.properties";
        Properties properties = new Properties();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            InputStream reader = getClass().getClassLoader().getResourceAsStream(resource);
            properties.load(reader);

            String dbURL = properties.getProperty("url");
            String dbID = properties.getProperty("username");
            String dbPassword = properties.getProperty("password");

            java.sql.Connection con = DriverManager.getConnection(dbURL, dbID, dbPassword);

            String sql = "SELECT * FROM mypage_interest WHERE user_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, sessionUserID);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String stock_name = rs.getString("stock_name");
                String stock_class = rs.getString("class");
                String regDate = rs.getString("regDate");

                Interest interest = new Interest(stock_name, stock_class, regDate);
                interestList.add(interest);
            }

            rs.close();
            pst.close();
            con.close();

        } catch (ClassNotFoundException | SQLException | IOException e) {
            throw new RuntimeException(e);
        }

        return interestList;
    }

    public List<MyPost> getMyPostList(String sessionUserID) {
        // MyPage right side: 내 post 목록 조회

        String resource = "db.properties";
        Properties properties = new Properties();

        List<MyPost> myPostList = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            InputStream reader = getClass().getClassLoader().getResourceAsStream(resource);
            properties.load(reader);

            String dbURL = properties.getProperty("url");
            String dbID = properties.getProperty("username");
            String dbPassword = properties.getProperty("password");

            java.sql.Connection con = DriverManager.getConnection(dbURL, dbID, dbPassword);

            String sql = "SELECT * FROM board WHERE writer_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, sessionUserID);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                int hit = rs.getInt("hit");

                MyPost myPost = new MyPost(id, title, hit);
                myPostList.add(myPost);
            }

            rs.close();
            pst.close();
            con.close();

        } catch (ClassNotFoundException | SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        return myPostList;
    }
}
