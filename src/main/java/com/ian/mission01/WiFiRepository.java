package com.ian.mission01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;

public class WiFiRepository {
    private static final String DB_URL = "jdbc:sqlite:C:/Users/lound/IdeaProjects/Mission01/wifi.db";
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


    // 테이블에 데이터 저장
    public static void insert(Connection conn, ArrayList<Row> rows) throws SQLException {
        String sql = "INSERT INTO WIFI (X_SWIFI_MGR_NO, X_SWIFI_WRDOFC, X_SWIFI_MAIN_NM, X_SWIFI_ADRES1, X_SWIFI_ADRES2, X_SWIFI_INSTL_FLOOR, X_SWIFI_INSTL_TY, "
                + "X_SWIFI_INSTL_MBY, X_SWIFI_SVC_SE, X_SWIFI_CMCWR, X_SWIFI_CNSTC_YEAR, X_SWIFI_INOUT_DOOR, X_SWIFI_REMARS3, LAT, LNT, WORK_DTTM)"
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (Row row : rows) {
                try {
                    ps.setString(1, row.getX_SWIFI_MGR_NO());   // 관리번호
                    ps.setString(2, row.getX_SWIFI_WRDOFC());   // 자치구
                    ps.setString(3, row.getX_SWIFI_MAIN_NM());  // 와이파이명
                    ps.setString(4, row.getX_SWIFI_ADRES1());   // 도로명주소
                    ps.setString(5, row.getX_SWIFI_ADRES2());   // 상세주소
                    ps.setString(6, row.getX_SWIFI_INSTL_FLOOR());  // 설치위치(층)
                    ps.setString(7, row.getX_SWIFI_INSTL_TY()); // 설치유형
                    ps.setString(8, row.getX_SWIFI_INSTL_MBY());    // 설치기관
                    ps.setString(9, row.getX_SWIFI_SVC_SE());   // 서비스구분
                    ps.setString(10, row.getX_SWIFI_CMCWR());   // 망종류
                    ps.setString(11, row.getX_SWIFI_CNSTC_YEAR());   // 설치년도
                    ps.setString(12, row.getX_SWIFI_INOUT_DOOR());  // 실내외구분
                    ps.setString(13, row.getX_SWIFI_REMARS3()); // wifi접속환경
                    ps.setDouble(14, Double.parseDouble(row.getLAT())); // Y좌표
                    ps.setDouble(15, Double.parseDouble(row.getLNT())); // X좌표
                    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
                    sdf2.setTimeZone(TimeZone.getTimeZone("UTC"));
                    java.util.Date parsedDate2 = sdf2.parse(row.getWORK_DTTM());
                    java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(parsedDate2.getTime());
                    ps.setTimestamp(16, sqlTimestamp); // 작업일자

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

}
