package com.ian.mission01.servlet;

import com.ian.mission01.dto.Bookmark;
import com.ian.mission01.repository.BookmarkRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;


// url = /bookmark/* 처리
@WebServlet("/bookmark")
public class BookmarkServlet extends HttpServlet {
    private BookmarkRepository bookmarkRepository;

    // 서블릿 실행 시, 리포지토리 연결
    @Override
    public void init() throws ServletException {
        try {
            bookmarkRepository = BookmarkRepository.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException("DB 연결에 실패했습니다.", e);
        }
    }


    // http method = get
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // action의 value가 null인 경우, value를 list로 반환하여 NullPointerException 핸들링
        String action = Optional.ofNullable(req.getParameter("action")).orElse("list");

        try {
            switch (action) {
                // 전체 북마크 조회
                case "list":
                    bookmarkList(req, resp);
                    break;
                // 북마크 상세 정보
                case "info":
                    bookmarkInfo(req, resp);
                    break;
                default:
                    resp.sendRedirect("/bookmark?action=list");
            }
        } catch (SQLException e) {
            throw new ServletException("DB 처리 중 오류가 발생했습니다.", e);
        }
    }

    // 전체 북마크 조회
    private void bookmarkList(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        ArrayList<Bookmark> bookmarks = bookmarkRepository.selectBookmark();

        HttpSession session = req.getSession();
        // session 값이 null이 아니면 msg 전송 후, 세션 삭제
        Optional.ofNullable(session.getAttribute("msg"))
                .ifPresent(msg -> {
                    req.setAttribute("msg", msg);
                    session.removeAttribute("msg");
                });
        req.setAttribute("bookmarks", bookmarks);
        req.getRequestDispatcher("/bookmark/bookmark-list.jsp").forward(req, resp);
    }

    // 북마크 상세 정보
    private void bookmarkInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        // 빈 문자열을 null로 변환하여 둘 다 처리
        String getBmId = Optional.ofNullable(req.getParameter("bmId"))
                .filter(bmId -> !bmId.isEmpty()) // 빈 문자열 필터링
                .orElse(null); // 빈 문자열을 null로 변환

        if (getBmId == null) {
            resp.sendRedirect("/bookmark?action=list");
        }

        try {
            int bmId = Integer.parseInt(getBmId);

            Bookmark bookmark = bookmarkRepository.selectBookmarkByBmgId(bmId);

            if (bookmark != null) {
                req.setAttribute("bookmark", bookmark);
                req.getRequestDispatcher("/bookmark/bookmark-info.jsp").forward(req, resp);
            } else {
                resp.sendRedirect("/bookmark?action=list");
            }
        } catch (NumberFormatException e) {
            resp.sendRedirect("/bookmark?action=list");
        }
    }


    // http method = post
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // action의 value가 null인 경우, value를 list로 반환
        String action = Optional.ofNullable(req.getParameter("action")).orElse("list");

        try {
            switch (action) {
                // 북마크 추가
                case "add":
                    addBookmark(req, resp);
                    break;
                // 북마크 삭제
                case "delete":
                    deleteBookmark(req, resp);
                    break;
                default:
                    resp.sendRedirect("/bookmark?action=list");
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException("DB 처리 중 오류가 발생했습니다.", e);
        }
    }

    // 북마크 추가
    private void addBookmark(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        String wfName = req.getParameter("wfName");
        String bmgName = req.getParameter("bmgName");

        bookmarkRepository.insertBookmark(bmgName, wfName);

        setalertMsg(req, "북마크를 추가했습니다.");
        resp.sendRedirect("/bookmark?action=list");
    }

    // 북마크 삭제
    private void deleteBookmark(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        int bmId = Integer.parseInt(req.getParameter("bmId"));

        bookmarkRepository.deleteBookmark(bmId);

        setalertMsg(req, "북마크를 삭제했습니다.");
        resp.sendRedirect("/bookmark?action=list");
    }

    // alert 메시지 설정
    private void setalertMsg(HttpServletRequest req, String msg) {
        HttpSession session = req.getSession();
        session.setAttribute("msg", msg);
    }
}
