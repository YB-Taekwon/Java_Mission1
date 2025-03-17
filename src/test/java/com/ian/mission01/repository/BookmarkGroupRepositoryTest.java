//package com.ian.mission01.repository;
//
//import com.ian.mission01.dto.BookmarkGroup;
//import org.junit.jupiter.api.Test;
//
//import java.sql.SQLException;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class BookmarkGroupRepositoryTest {
//        private static final BookmarkGroupRepository bgr;
//
//    static {
//        try {
//            bgr = new BookmarkGroupRepository();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test
//    void selectBookmarkGroupByBmgId() throws SQLException {
//        BookmarkGroup bg = bgr.selectBookmarkGroupByBmgId(1);
//        assertEquals(1, bg.getBmgOrder());
//    }
//
//    @Test
//    void updateBookmarkGroup() throws SQLException {
//        bgr.updateBookmarkGroup(1, "테스트", 11);
//        assertEquals(11, bgr.selectBookmarkGroupByBmgId(1).getBmgOrder());
//    }
//}