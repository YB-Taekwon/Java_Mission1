package com.ian.mission01.repository;

import com.ian.mission01.dto.BookmarkGroup;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class BookmarkGroupRepository {

    private static final String DB_URL = "jdbc:sqlite:C:/Users/lound/IdeaProjects/Mission01/wifi.db";
    private static BookmarkGroupRepository instance;


    // 외부에서 인스턴스 생성을 제한하는 private 생성자
    private BookmarkGroupRepository() {}


    // 싱글턴 인스턴스 반환
    public static synchronized BookmarkGroupRepository getInstance() {
        if (instance == null) {
            instance = new BookmarkGroupRepository();
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


    // 북마크 그룹 추가 (트랜잭션 처리 O)
    public void insertBookmarkGroup(String bmgName, int bmgOrder) throws SQLException {
        String sql = "INSERT INTO BOOKMARK_GROUP (BG_NAME, BG_ORDER) VALUES (?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, bmgName);
            ps.setInt(2, bmgOrder);
            ps.executeUpdate();
            conn.commit(); // 성공 시, 커밋

        } catch (SQLException e) {
            throw e; // 싷패 시, 예외 발생(자동 롤백)
        }
    }


    // 북마크 그룹 전체 조회 (트랜잭션 처리 X)
    public ArrayList<BookmarkGroup> selectBookmarkGroup() throws SQLException {
        String sql = "SELECT * FROM BOOKMARK_GROUP ORDER BY BG_ORDER";
        ArrayList<BookmarkGroup> bookmarkGroups = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                BookmarkGroup bookmarkGroup = new BookmarkGroup();
                bookmarkGroup.setBmgId(rs.getInt("BG_ID"));
                bookmarkGroup.setBmgName(rs.getString("BG_NAME"));
                bookmarkGroup.setBmgOrder(rs.getInt("BG_ORDER"));
                bookmarkGroup.setBmgRegistDate(rs.getTimestamp("BG_REGIST_DATE").toLocalDateTime());
                bookmarkGroup.setBmgUpdateDateString(Optional.ofNullable(rs.getString("BG_UPDATE_DATE")).orElse(""));
                bookmarkGroups.add(bookmarkGroup);
            }
        }
        return bookmarkGroups;
    }


    // 특정 북마크 그룹 조회 (트랜잭션 처리 X)
    public BookmarkGroup selectBookmarkGroupByBmgId(int bmgId) throws SQLException {
        String sql = "SELECT BG_NAME, BG_ORDER FROM BOOKMARK_GROUP WHERE BG_ID = ?";
        BookmarkGroup bookmarkGroup = new BookmarkGroup();

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, bmgId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    bookmarkGroup.setBmgName(rs.getString("BG_NAME"));
                    bookmarkGroup.setBmgOrder(rs.getInt("BG_ORDER"));
                }
            }
        }
        return bookmarkGroup;
    }


    // 북마크 그룹 수정 (트랜잭션 처리 O)
    public void updateBookmarkGroup(int bmgId, String bmgName, int bmgOrder) throws SQLException {
        String sql = "UPDATE BOOKMARK_GROUP SET BG_NAME = ?, BG_ORDER = ?, BG_UPDATE_DATE = datetime('now', 'localtime') WHERE BG_ID = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, bmgName);
            ps.setInt(2, bmgOrder);
            ps.setInt(3, bmgId);
            ps.executeUpdate();
            conn.commit();  // 성공 시, 커밋

        } catch (SQLException e) {
            throw e;  // 실패 시, 예외 발생(자동 롤백)
        }
    }


    // 특정 북마크 그룹 삭제 (트랜잭션 처리 O)
    public void deleteBookmarkGroup(int bmgId) throws SQLException {
        String sql = "DELETE FROM BOOKMARK_GROUP WHERE BG_ID = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, bmgId);
            ps.executeUpdate();
            conn.commit();  // 성공 시, 커밋

        } catch (SQLException e) {
            throw e;  // 실패 시, 예외 발생(자동 롤백)
        }
    }
}
