package com.ian.mission01.servlet;

import com.ian.mission01.dto.BookmarkGroup;
import com.ian.mission01.repository.BookmarkGroupRepository;
import com.ian.mission01.repository.LocationRepository;
import com.ian.mission01.repository.WiFiRepository;
import com.ian.mission01.dto.Location;
import com.ian.mission01.dto.Row;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;


// url = /wifi/* 처리
@WebServlet("/wifi")
public class WiFiServlet extends HttpServlet {
    private WiFiRepository wiFiRepository;
    private LocationRepository locationRepository;
    private BookmarkGroupRepository bookmarkGroupRepository;

    // 서블릿 실행 시, 리포지토리 연결
    @Override
    public void init() throws ServletException {
        wiFiRepository = WiFiRepository.getInstance();
        locationRepository = LocationRepository.getInstance();
        bookmarkGroupRepository = BookmarkGroupRepository.getInstance();
    }


    // http method = get
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // action의 value가 null인 경우, value를 list로 반환하여 NullPointerException 핸들링
        String action = Optional.ofNullable(req.getParameter("action")).orElse("list");

        try {
            switch (action) {
                case "list":
                    wiFiList(req, resp);
                    break;
                case "info":
                    wiFiInfo(req, resp);
                    break;
                default:
                    req.getRequestDispatcher("/index.jsp").forward(req, resp);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException("DB 처리 중 오류가 발생했습니다.", e);
        }
    }

    // 전체 와이파이 정보 조회
    private void wiFiList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        ArrayList<Row> rows = wiFiRepository.selectWiFiList();
        Location location = locationRepository.selectLatLnt();

        req.setAttribute("location", location);
        req.setAttribute("rows", rows);
        req.getRequestDispatcher("/wifi/wifi-list.jsp").forward(req, resp);
    }

    // 와이파이 상세 정보
    private void wiFiInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        // 빈 문자열을 null로 변환하여 둘 다 처리
        String wfId = Optional.ofNullable(req.getParameter("wfId"))
                .filter(wId -> !wId.isEmpty()) // 빈 문자열 필터링
                .orElse(null); // 빈 문자열을 null로 변환

        if (wfId == null) {
            resp.sendRedirect("/wifi?action=list");
        }

        Row row = wiFiRepository.selectWiFi(wfId);
        ArrayList<BookmarkGroup> bookmarkGroups = bookmarkGroupRepository.selectBookmarkGroup();

        if (row != null && bookmarkGroups != null) {
            req.setAttribute("row", row);
            req.setAttribute("bookmarkGroups", bookmarkGroups);
            req.getRequestDispatcher("/wifi/wifi-info.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("/wifi?action=list");
        }
    }


    // http method = get
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // null과 빈 문자열을 한 번에 처리
        Optional<String> getLat = Optional.ofNullable(req.getParameter("lat")).filter(lat -> !lat.isEmpty());
        Optional<String> getLnt = Optional.ofNullable(req.getParameter("lnt")).filter(lnt -> !lnt.isEmpty());

        if (getLat.isPresent() && getLnt.isPresent()) {
            try {
                double lat = Double.parseDouble(getLat.get());
                double lnt = Double.parseDouble(getLnt.get());

                locationRepository.insertLocation(lat, lnt);

                resp.sendRedirect("/wifi?action=list");
            } catch (NumberFormatException e) {
                throw new RuntimeException("타입을 변환할 수 없습니다.", e);
            } catch (SQLException e) {
                throw new RuntimeException("DB 처리 중 오류가 발생했습니다.", e);
            }
        } else {
            resp.sendRedirect("/wifi");
        }
    }
}
