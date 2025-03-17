package com.ian.mission01.repository;

import com.ian.mission01.dto.Row;

import java.sql.*;
import java.util.ArrayList;

public class WiFiRepository {

    private static final String DB_URL = "jdbc:sqlite:C:/Users/lound/IdeaProjects/Mission01/wifi.db";
    private static WiFiRepository instance;

    // 외부에서 인스턴스 생성을 제한하는 private 생성자
    private WiFiRepository() {}

    // 싱글턴 인스턴스 반환
    public static synchronized WiFiRepository getInstance() {
        if (instance == null) {
            instance = new WiFiRepository();
        }
        return instance;
    }

    // 데이터베이스 연결은 메서드에서 처리 - 메서드 호출 시에만 연결
    // PRAGMA 설정 및 트랜잭션 관리 (자동 커밋 비활성화)
    private Connection getConnection() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(DB_URL);

            try (Statement stmt = conn.createStatement()) {
                stmt.execute("PRAGMA journal_mode = WAL;"); // WAL 모드 활성화
                stmt.execute("PRAGMA locking_mode = NORMAL;"); // NORMAL 모드 설정
                stmt.execute("PRAGMA synchronous = NORMAL;"); // NORMAL 모드 설정
                stmt.execute("PRAGMA busy_timeout = 10000;"); // 트랜잭션 잠금이 풀릴 때까지 최대 10초 버팀
            }
            conn.setAutoCommit(false); // 자동 커밋 해제
            return conn;
        } catch (ClassNotFoundException e) {
            throw new SQLException("SQLite JDBC 드라이버를 찾을 수 없습니다.", e);
        }
    }


    // 와이파이 정보 저장 (트랜잭션 처리 O)
    public void insertWifi(ArrayList<Row> rows) throws SQLException {
        String sql = "INSERT INTO WIFI (W_ID, W_AREA, W_NAME, W_ADDRESS1, W_ADDRESS2, W_FLOOR, W_TYPE, " +
                "W_PROVIDER, W_SERVICE, W_NETWORK, W_YEAR, W_INOUT, W_CONDITION, W_LAT, W_LNT, W_DATE) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (Row row : rows) { // 각 행을 배치에 추가
                try {
                    ps.setString(1, row.getWfId());
                    ps.setString(2, row.getWfArea());
                    ps.setString(3, row.getWfName());
                    ps.setString(4, row.getWfAddress1());
                    ps.setString(5, row.getWfAddress2());
                    ps.setString(6, row.getWfFloor());
                    ps.setString(7, row.getWfType());
                    ps.setString(8, row.getWfProvider());
                    ps.setString(9, row.getWfService());
                    ps.setString(10, row.getWfNetwork());
                    ps.setString(11, row.getWfYear());
                    ps.setString(12, row.getWfInOut());
                    ps.setString(13, row.getWfCondition());
                    ps.setDouble(14, Double.parseDouble(row.getWfLat()));
                    ps.setDouble(15, Double.parseDouble(row.getWfLnt()));
                    ps.setString(16, row.getWfDate());
                    ps.addBatch();
                } catch (Exception e) {
                    System.err.println("배치 추가 중 오류 발생 (row: " + row + ")");
                    e.printStackTrace();
                }
            }
            ps.executeBatch();
            conn.commit(); // 배치 성공 시, 커밋
        } catch (SQLException e) {
            throw e;
        }
    }


    // 현재 위치에서 가까운 와이파이 정보 20개 조회 (트랜잭션 처리 X)
    public ArrayList<Row> selectWiFiList() throws SQLException {
        String sql = "WITH LatestLocation AS (" +
                "SELECT L_LAT, L_LNT FROM LOCATION ORDER BY L_DATE LIMIT 1) " +
                "SELECT ROUND(SQRT(POW(W.W_LAT - L.L_LAT, 2) + POW(W.W_LNT - L.L_LNT, 2)), 3) AS DISTANCE, W.* " +
                "FROM WIFI W, LatestLocation L " +
                "ORDER BY DISTANCE ASC LIMIT 20";
        ArrayList<Row> rows = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
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


    // 특정 와이파이 정보 조회 (트랜잭션 처리 X)
    public Row selectWiFi(String wfId) throws SQLException {
        String sql = "WITH LatestLocation AS (" +
                "SELECT L_LAT, L_LNT FROM LOCATION ORDER BY L_DATE LIMIT 1) " +
                "SELECT ROUND(SQRT(POW(W.W_LAT - L.L_LAT, 2) + POW(W.W_LNT - L.L_LNT, 2)), 3) AS DISTANCE, W.* " +
                "FROM WIFI W, LatestLocation L " +
                "WHERE W_ID = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, wfId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
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

                    return row;
                }
            }
        }
        return null;
    }
}
