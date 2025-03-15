package com.ian.mission01;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class WiFiRepositoryTest {

    // 테스트 전에 SQLite 데이터베이스 파일 생성
    @BeforeAll
    public static void setup() throws SQLException {
        // 데이터베이스 파일이 없다면 새로 생성
        Connection conn = DriverManager.getConnection("jdbc:sqlite:wifi.db");
        conn.close();
    }

    // DB 연결 테스트
//    @Test
//    public void testConnect() {
//        try {
//            WiFiRepository repository = new WiFiRepository();
//            Connection conn = repository.connect();
//            assertNotNull(conn, "Database connection should not be null");
//        } catch (SQLException | ClassNotFoundException e) {
//            fail("Exception occurred: " + e.getMessage());
//        }
//    }

//    @Test
//    public void testInsert() throws SQLException, ClassNotFoundException {
//        WiFiRepository repository = new WiFiRepository();
//        Connection conn = repository.connect();
//        repository.insertLocation(conn, 0.123, 0.123);
//    }

//    @Test
//    void test() throws SQLException {
//        // given
//        ArrayList<Row> rows = WiFiRepository.selectWiFi();
//        assertEquals(20, rows.size());
//        for (Row row : rows) {
//            System.out.println(row.getCnstcYear());
//        }
//    }

}
