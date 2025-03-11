package com.ian.mission01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String DB_URL = "jdbc:sqlite:wifi.db";
    private static Connection conn;

    // SQLite 연결
    public static Connection connect() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection(DB_URL);

        if (conn != null) {
            System.out.println("Connected to database");
        }
        return conn;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Connection conn = DBConnection.connect();

        if (conn != null) {
            // DB 정상 연결
            System.out.println("Connection established: " + conn);
        } else {
            // 연결 실패
            System.out.println("Failed to establish connection.");
        }
    }
}