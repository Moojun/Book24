package com.seoultech.book24.User;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Properties;
import java.time.LocalDate;
import java.time.LocalTime;

public class UserDAO {

    //private String userID;
    private String userEmail;
    private String userPassword;
    private LocalDate regDate;
    private LocalTime regTime;

    private Connection conn;
    private ResultSet rs;

    String resource = "db.properties";
    Properties properties = new Properties();

    public UserDAO() {
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

    /*
    login

    -2: id doesn't exist in database
    -1: server connection error
    0: password is incorrect
    1: login success
     */

    public int login(String userEmail, String userPassword) {
        try {
            PreparedStatement pst = conn.prepareStatement("SELECT user_password FROM member WHERE user_email = ?");
            pst.setString(1, userEmail);
            rs = pst.executeQuery();

            if (rs.next()) {
                return rs.getString("user_password").equals(userPassword) ? 1 : 0;
            }
            else {
                return -2;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /*
    Check if the e-mail already exists
     */
    public boolean Email_Check(String userEmail) {
        try {
            PreparedStatement pst = conn.prepareStatement("SELECT user_email FROM member WHERE user_email = ?");
            pst.setString(1, userEmail);
            rs = pst.executeQuery();

            if (rs.next()) {
                return false;
            }
            else {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /*
    signup
    -1: server connection error
    0: id already exists in database
    1: success
     */

    public int join(UserDAO userDAO) {
        if (!Email_Check(userDAO.getUserEmail())) {
            return 0;
        }

        try {
            PreparedStatement pst = conn.prepareStatement("INSERT INTO member " +
                    "(user_email, user_password, regdate, regtime) VALUES (?, ?, ?, ?)");

            pst.setString(1, userDAO.getUserEmail());
            pst.setString(2, userDAO.getUserPassword());
            regDate = LocalDate.now();
            regTime = LocalTime.now();
            pst.setObject(3, regDate);
            pst.setObject(4, regTime);


            return pst.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /*
    get user data
     */
    public UserDAO getUser(String userEmail) {
        try {
            PreparedStatement pst = conn.prepareStatement("SELECT * FROM member WHERE user_email = ?");
            pst.setString(1, userEmail);
            rs = pst.executeQuery();

            if (rs.next()) {
                UserDAO userDAO = new UserDAO();
                userDAO.setUserEmail(rs.getString(2));
                userDAO.setUserPassword(rs.getString(3));
                return userDAO;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
