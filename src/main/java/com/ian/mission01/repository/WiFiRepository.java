package com.ian.mission01.repository;

import com.ian.mission01.dto.Row;

import java.sql.*;
import java.util.ArrayList;

public class WiFiRepository {
    private static final String DB_URL = "jdbc:sqlite:C:/Users/lound/IdeaProjects/Mission01/wifi.db";
    private static Connection conn;

    static {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Database connection failed", e);
        }
    }


    // 테이블에 데이터 저장
    public static void insertWifi(ArrayList<Row> rows) throws SQLException {
        String sql = "INSERT INTO WIFI (W_ID, W_AREA, W_NAME, W_ADDRESS1, W_ADDRESS2, W_FLOOR, W_TYPE, "
                + "W_PROVIDER, W_SERVICE, W_NETWORK, W_YEAR, W_INOUT, W_CONDITION, W_LAT, W_LNT, W_DATE)"
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (Row row : rows) {
                try {
                    ps.setString(1, row.getWfId());   // 관리번호
                    ps.setString(2, row.getWfArea());   // 자치구
                    ps.setString(3, row.getWfName());  // 와이파이명
                    ps.setString(4, row.getWfAddress1());   // 도로명주소
                    ps.setString(5, row.getWfAddress2());   // 상세주소
                    ps.setString(6, row.getWfFloor());  // 설치위치(층)
                    ps.setString(7, row.getWfType()); // 설치유형
                    ps.setString(8, row.getWfProvider());    // 설치기관
                    ps.setString(9, row.getWfService());   // 서비스구분
                    ps.setString(10, row.getWfNetwork());   // 망종류
                    ps.setString(11, row.getWfYear());   // 설치년도
                    ps.setString(12, row.getWfInOut());  // 실내외구분
                    ps.setString(13, row.getWfCondition()); // wifi접속환경
                    ps.setDouble(14, Double.parseDouble(row.getWfLat())); // Y좌표
                    ps.setDouble(15, Double.parseDouble(row.getWfLnt())); // X좌표
                    ps.setString(16, row.getWfDate());

                    ps.addBatch();
                } catch (Exception e) {
                    System.err.println("Error setting batch for row: " + row);
                    e.printStackTrace();
                }
            }
            ps.executeBatch();
        } catch (SQLException e) {
            System.err.println("Error executing batch");
            e.printStackTrace();
        }
    }


    public static ArrayList<Row> selectWiFi() throws SQLException {
        String sql = "WITH LatestLocation AS (" +
                "SELECT L_LAT, L_LNT " +
                "FROM LOCATION " +
                "ORDER BY L_DATE " +
                "LIMIT 1)" +
                "SELECT SQRT(POW(W.W_LAT - L.L_LAT, 2) + POW(W.W_LNT - L.L_LNT, 2)) AS DISTANCE, W.* " +
                "From WIFI W, LatestLocation L " +
                "ORDER BY DISTANCE ASC " +
                "LIMIT 20";

        ArrayList<Row> rows = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                // WiFi 정보 가져오기
                Row row = new Row();
                row.setDistance(rs.getDouble("DISTANCE"));
                row.setWfId(rs.getString("W_ID"));
                row.setWfArea(rs.getString("W_AREA"));
                row.setWfName(rs.getString("W_NAME"));
                row.setWfAddress1(rs.getString("W_ADDRESS1"));
                row.setWfAddress2(rs.getString("W_ADDRESS2"));
                row.setWfFloor(rs.getString("W_FLOOR"));
                row.setWfType(rs.getString("W_TYPE"));
                row.setWfProvider(rs.getString("W_PROVIDER"));
                row.setWfService(rs.getString("W_SERVICE"));
                row.setWfNetwork(rs.getString("W_NETWORK"));
                row.setWfYear(rs.getString("W_YEAR"));
                row.setWfInOut(rs.getString("W_INOUT"));
                row.setWfCondition(rs.getString("W_CONDITION"));
                row.setWfLat(rs.getString("W_LAT"));
                row.setWfLnt(rs.getString("W_LNT"));
                row.setWfDate(rs.getString("W_DATE"));

                rows.add(row);
            }
        }
        return rows;
    }


}
