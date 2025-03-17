package com.ian.mission01.repository;

import com.ian.mission01.dto.Location;

import java.sql.*;
import java.util.ArrayList;

public class LocationRepository {

    private static final String DB_URL = "jdbc:sqlite:C:/Users/lound/IdeaProjects/Mission01/wifi.db";
    private static LocationRepository instance;

    // 외부에서 인스턴스 생성을 제한하는 private 생성자
    private LocationRepository() {}


    // 싱글턴 인스턴스 반환
    public static synchronized LocationRepository getInstance() {
        if (instance == null) {
            instance = new LocationRepository();
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


    // 위치 정보 추가 (트랜잭션 처리 O)
    public void insertLocation(double lat, double lnt) throws SQLException {
        String sql = "INSERT INTO LOCATION (L_LAT, L_LNT) VALUES(?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, lat);
            ps.setDouble(2, lnt);
            ps.executeUpdate();
            conn.commit(); // 성공 시, 커밋

        } catch (SQLException e) {
            throw e; // 싷패 시, 예외 발생(자동 롤백)
        }
    }


    // 가장 최근의 좌표를 조회 (트랜잭션 불필요)
    public Location selectLatLnt() throws SQLException {
        String sql = "SELECT L_LAT, L_LNT FROM LOCATION ORDER BY L_DATE LIMIT 1";
        Location location = null;
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                location = new Location();
                location.setLcLat(rs.getDouble("L_LAT"));
                location.setLcLnt(rs.getDouble("L_LNT"));
            }
        }
        return location;
    }

    // 위치 정보 전체 조회 (트랜잭션 처리 X)
    public ArrayList<Location> selectLocation() throws SQLException {
        String sql = "SELECT * FROM LOCATION ORDER BY L_ID DESC";
        ArrayList<Location> locations = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Location location = new Location();
                location.setLcId(rs.getInt("L_ID"));
                location.setLcLat(rs.getDouble("L_LAT"));
                location.setLcLnt(rs.getDouble("L_LNT"));
                location.setLcDate(rs.getDate("L_DATE").toLocalDate());
                locations.add(location);
            }
        }
        return locations;
    }


    // 위치 정보 삭제 (트랜잭션 처리 X)
    public void deleteLocation(int lcId) throws SQLException {
        String sql = "DELETE FROM LOCATION WHERE L_ID = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, lcId);
            ps.executeUpdate();
            conn.commit(); // 성공 시, 커밋

        } catch (SQLException e) {
            throw e; // 실패 시, 예외 발생(자동 롤백)
        }
    }
}
