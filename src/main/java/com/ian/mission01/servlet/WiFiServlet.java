package com.ian.mission01.servlet;

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


@WebServlet("/wifi-list")
public class WiFiServlet extends HttpServlet {
    private LocationRepository locationRepository;
    private WiFiRepository wiFiRepository;

    @Override
    public void init() throws ServletException {
        try {
            locationRepository = new LocationRepository();
            wiFiRepository = new WiFiRepository();
        } catch (SQLException e) {
            throw new ServletException("DB is not connected", e);
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("doGet");

        String lat = request.getParameter("lat");
        String lnt = request.getParameter("lnt");

        System.out.println("lat = " + lat + ", lnt = " + lnt);

        if (lat != null && lnt != null) {
            ArrayList<Row> rows;
            Location location;

            try {
                rows = wiFiRepository.selectWiFi();
                location = locationRepository.selectLatLnt();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            System.out.println("rows = " + rows);
            System.out.println("location = " + location);

            request.setAttribute("location", location);
            request.setAttribute("rows", rows);

            request.getRequestDispatcher("/wifi-list.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("doPost");

        String latString = request.getParameter("lat");
        String lntString = request.getParameter("lnt");

        if (latString != null && lntString != null) {
            double lat = Double.parseDouble(request.getParameter("lat"));
            double lnt = Double.parseDouble(request.getParameter("lnt"));

            System.out.println("lat = " + lat);
            System.out.println("lnt = " + lnt);

            try {
                locationRepository.insertLocation(lat, lnt);  // 데이터베이스 처리
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            String url = "/wifi-list?lat=" + latString + "&lnt=" + lntString;
            System.out.println("url = " + url);
            response.sendRedirect(url);
        } else {
            response.sendRedirect("/wifi-list");
        }


    }


}
