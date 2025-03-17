package com.ian.mission01.repository;

import com.ian.mission01.dto.Bookmark;
import com.ian.mission01.dto.BookmarkGroup;
import com.ian.mission01.dto.Row;

import java.sql.*;
import java.util.ArrayList;

public class BookmarkRepository {

    private static final String DB_URL = "jdbc:sqlite:C:/Users/lound/IdeaProjects/Mission01/wifi.db";
    private static BookmarkRepository instance;

    // 외부에서 인스턴스 생성을 제한하는 private 생성자
    private BookmarkRepository() {}


    // 싱글턴 인스턴스 반환
    public static synchronized BookmarkRepository getInstance() throws SQLException {
        if (instance == null) {
            instance = new BookmarkRepository();
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


    // 북마크 추가 (트랜잭션 처리 O)
    public void insertBookmark(String bmgName, String wfName) throws SQLException {
        String sql = "INSERT INTO BOOKMARK (BG_NAME, W_NAME) VALUES (?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, bmgName);
            ps.setString(2, wfName);
            ps.executeUpdate();
            conn.commit(); // 성공 시, 커밋

        } catch (SQLException e) {
            throw e; // 싷패 시, 예외 발생(자동 롤백)
        }
    }

    // 북마크 전체 조회 (트랜잭션 처리 X)
    public ArrayList<Bookmark> selectBookmark() throws SQLException {
        String sql = "SELECT * FROM BOOKMARK";
        ArrayList<Bookmark> bookmarks = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                BookmarkGroup bookmarkGroup = new BookmarkGroup(rs.getString("BG_NAME"));
                Row row = new Row(rs.getString("W_NAME"));
                Bookmark bookmark = new Bookmark(
                        rs.getInt("B_ID"),
                        bookmarkGroup,
                        row,
                        rs.getTimestamp("B_DATE").toLocalDateTime()
                );
                bookmarks.add(bookmark);
            }
        }
        return bookmarks;
    }


    // 특정 북마크 조회 (트랜잭션 처리 X)
    public Bookmark selectBookmarkByBmgId(int bmId) throws SQLException {
        String sql = "SELECT * FROM BOOKMARK WHERE B_ID = ?";
        Bookmark bookmark = null;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, bmId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    bookmark = new Bookmark();
                    bookmark.setBmId(rs.getInt("B_ID"));
                    bookmark.setBmDate(rs.getTimestamp("B_DATE").toLocalDateTime());

                    BookmarkGroup bookmarkGroup = new BookmarkGroup();
                    bookmarkGroup.setBmgName(rs.getString("BG_NAME"));
                    bookmark.setBookmarkGroup(bookmarkGroup);

                    Row row = new Row();
                    row.setWfName(rs.getString("W_NAME"));
                    bookmark.setRow(row);
                }
            }
        } catch (SQLException e) {
            throw e;
        }
        return bookmark;
    }


    // 북마크 삭제 (트랜잭션 처리 O)
    public void deleteBookmark(int bmId) throws SQLException {
        String sql = "DELETE FROM BOOKMARK WHERE B_ID = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, bmId);
            ps.executeUpdate(); // 성공 시, 커밋

            conn.commit();
        } catch (SQLException e) {
            throw e; // 실패 시, 예외 발생(자동 롤백)
        }
    }

}
