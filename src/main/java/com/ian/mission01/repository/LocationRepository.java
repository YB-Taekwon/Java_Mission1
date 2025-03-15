package com.ian.mission01.repository;

import com.ian.mission01.dto.Location;

import java.sql.*;
import java.util.ArrayList;

public class LocationRepository {
    private static final String DB_URL = "jdbc:sqlite:C:/Users/lound/IdeaProjects/Mission01/wifi.db";
    private static Connection conn;

    public LocationRepository() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("Database connection failed", e);
        }
    }


    public static void insertLocation(double lat, double lnt) throws SQLException {
        String sql = "INSERT INTO LOCATION (L_LAT, L_LNT) VALUES(?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, lat);
            ps.setDouble(2, lnt);

            ps.executeUpdate();
        }
    }

    public static Location selectLatLnt() throws SQLException {
        String sql = "SELECT L_LAT, L_LNT " +
                "FROM LOCATION " +
                "ORDER BY L_DATE " +
                "LIMIT 1";

        Location location = new Location();

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            location.setLcLat(rs.getDouble("L_LAT"));
            location.setLcLnt(rs.getDouble("L_LNT"));
        }
        return location;
    }


    public ArrayList<Location> selectLocation() throws SQLException {
        String sql = "SELECT * " +
                "FROM LOCATION " +
                "ORDER BY L_ID DESC";

        ArrayList<Location> locations = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Location location = new Location();
                location.setLcId(rs.getInt("L_ID"));
                location.setLcLat(rs.getDouble("L_LAT"));
                location.setLcLnt(rs.getDouble("L_LNT"));
                location.setLcDate(rs.getDate("L_DATE").toLocalDate());

                locations.add(location);
            }
        }
        return locations;
    }


}
