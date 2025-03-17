package com.ian.mission01.servlet;

import com.ian.mission01.dto.BookmarkGroup;
import com.ian.mission01.repository.BookmarkGroupRepository;

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


// url = /bookmarkGroup/* 처리
@WebServlet("/bookmarkGroup")
public class BookmarkGroupServlet extends HttpServlet {
    private BookmarkGroupRepository bookmarkGroupRepository;

    // 서블릿 실행 시, 리포지토리 연결
    @Override
    public void init() throws ServletException {
        bookmarkGroupRepository = BookmarkGroupRepository.getInstance();
    }


    // http method = get
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // action의 value가 null인 경우, value를 list로 반환하여 NullPointerException 핸들링
        String action = Optional.ofNullable(req.getParameter("action")).orElse("list");

        try {
            switch (action) {
                // 전체 북마크 그룹 조회
                case "list":
                    BookmarkGroupList(req, resp);
                    break;
                // 북마크 그룹 상세 정보
                case "info":
                    bookmarkGroupInfo(req, resp);
                    break;
                default:
                    resp.sendRedirect("/bookmarkGroup?action=list");
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException("DB 처리 중 오류가 발생했습니다.", e);
        }
    }

    // 전체 북마크 그룹 조회
    private void BookmarkGroupList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        ArrayList<BookmarkGroup> bookmarkGroups = bookmarkGroupRepository.selectBookmarkGroup();

        HttpSession session = req.getSession();
        // session 값이 null이 아니면 msg 전송 후, 세션 삭제
        Optional.ofNullable(session.getAttribute("msg"))
                .ifPresent(msg -> {
                    req.setAttribute("msg", msg);
                    session.removeAttribute("msg");
                });

        req.setAttribute("bookmarkGroups", bookmarkGroups);
        req.getRequestDispatcher("/bookmarkGroup/bookmarkgroup-list.jsp").forward(req, resp);
    }

    // 북마크 그룹 상세 정보
    private void bookmarkGroupInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        // 빈 문자열을 null로 변환하여 둘 다 처리
        String getBmgId = Optional.ofNullable(req.getParameter("bmgId"))
                .filter(bmgId -> !bmgId.isEmpty()) // 빈 문자열 필터링
                .orElse(null); // 빈 문자열을 null로 변환

        if (getBmgId == null) {
            resp.sendRedirect("/bookmarkGroup?action=list");
        }

        try {
            int bmgId = Integer.parseInt(getBmgId);

            BookmarkGroup bookmarkGroup = bookmarkGroupRepository.selectBookmarkGroupByBmgId(bmgId);

            if (bookmarkGroup != null) {
                req.setAttribute("bmgId", bmgId);
                req.setAttribute("bookmarkGroup", bookmarkGroup);
                req.getRequestDispatcher("/bookmarkGroup/bookmarkgroup-info.jsp").forward(req, resp);
            } else {
                resp.sendRedirect("/bookmarkGroup?action=list");
            }
        } catch (NumberFormatException e) {
            resp.sendRedirect("/bookmarkGroup?action=list");
        }
    }


    // http method = post
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // action이 null인 경우, 빈 문자열을 반환하여 switch문의 default로 핸들링
        String action = Optional.ofNullable(req.getParameter("action")).orElse("");

        try {
            switch (action) {
                // 북마크 그룹 추가
                case "add":
                    addBookmarkGroup(req, resp);
                    break;
                // 북마크 그룹 수정
                case "update":
                    updateBookmarkGroup(req, resp);
                    break;
                // 북마크 그룹 삭제
                case "delete":
                    deleteBookmarkGroup(req, resp);
                    break;
                default:
                    resp.sendRedirect("/bookmarkGroup?action=list");
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException("DB 처리 중 오류가 발생했습니다.", e);
        }
    }

    // 북마크 그룹 추가
    private void addBookmarkGroup(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        String addBmgName = req.getParameter("bmgName");
        int addBmgOrder = Integer.parseInt(req.getParameter("bmgOrder"));

        bookmarkGroupRepository.insertBookmarkGroup(addBmgName, addBmgOrder);

        setalertMsg(req, "북마크 그룹을 추가했습니다.");
        resp.sendRedirect("/bookmarkGroup?action=list");
    }

    // 북마크 그룹 수정
    private void updateBookmarkGroup(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        int bmgId = Integer.parseInt(req.getParameter("bmgId"));
        String bmgName = req.getParameter("bmgName");
        int bmgOrder = Integer.parseInt(req.getParameter("bmgOrder"));

        bookmarkGroupRepository.updateBookmarkGroup(bmgId, bmgName, bmgOrder);

        setalertMsg(req, "북마크 그룹을 수정했습니다.");
        resp.sendRedirect("/bookmarkGroup?action=list");
    }

    // 북마크 그룹 삭제
    private void deleteBookmarkGroup(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        int bmgId = Integer.parseInt(req.getParameter("bmgId"));

        bookmarkGroupRepository.deleteBookmarkGroup(bmgId);

        setalertMsg(req, "북마크 그룹을 삭제했습니다.");
        resp.sendRedirect("/bookmarkGroup?action=list");
    }

    // alert 메시지 설정
    private void setalertMsg(HttpServletRequest req, String msg) {
        HttpSession session = req.getSession();
        session.setAttribute("msg", msg);
    }
}
