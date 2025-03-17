package com.ian.mission01.servlet;

import com.ian.mission01.dto.Location;
import com.ian.mission01.repository.LocationRepository;

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


// url = location/* 처리
@WebServlet("/location")
public class LocationServlet extends HttpServlet {
    private LocationRepository locationRepository;

    // 서블릿 실행 시, 리포지토리 연결
    @Override
    public void init() throws ServletException {
        locationRepository = LocationRepository.getInstance();
    }


    // http method = get
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            locationList(req, resp);
        } catch (SQLException e) {
            throw new ServletException("DB 처리 중 오류가 발생했습니다.", e);
        }
    }

    // 위치 정보 조회
    private void locationList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        ArrayList<Location> locations;

        HttpSession session = req.getSession();
        // session 값이 null이 아니면 msg 전송 후, 세션 삭제
        Optional.ofNullable(session.getAttribute("msg"))
                .ifPresent(msg -> {
                    req.setAttribute("msg", msg);
                    session.removeAttribute("msg");
                });

        locations = locationRepository.selectLocation();

        req.setAttribute("locations", locations);
        req.getRequestDispatcher("/wifi/location-list.jsp").forward(req, resp);
    }


    // http method = post
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            deleteLocation(req, resp);
        } catch (SQLException e) {
            throw new ServletException("DB 처리 중 오류가 발생했습니다.", e);
        }
    }

    // 위치 정보 삭제
    private void deleteLocation(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        int lcId = Integer.parseInt(req.getParameter("lcId"));

        locationRepository.deleteLocation(lcId);

        setalertMsg(req, "위치 정보를 삭제했습니다.");
        resp.sendRedirect("/location");
    }

    // alert 메시지 설정
    private void setalertMsg(HttpServletRequest req, String msg) {
        HttpSession session = req.getSession();
        session.setAttribute("msg", msg);
    }
}
