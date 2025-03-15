package com.ian.mission01.servlet;

import com.ian.mission01.repository.LocationRepository;
import com.ian.mission01.dto.Location;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;


@WebServlet("/location-history")
public class LocationListServlet extends HttpServlet {
    private LocationRepository locationRepository;

    @Override
    public void init() throws ServletException {
        try {
            locationRepository = new LocationRepository();
        } catch (SQLException e) {
            throw new ServletException("DB is not connected", e);
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Call doGet");

        ArrayList<Location> locations;
        try {
            locations = locationRepository.selectLocation();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println(locations);
        for (Location location : locations) {
            System.out.println(location);
        }

        request.setAttribute("locations", locations);
        request.getRequestDispatcher("location-history.jsp").forward(request, response);
    }
}
