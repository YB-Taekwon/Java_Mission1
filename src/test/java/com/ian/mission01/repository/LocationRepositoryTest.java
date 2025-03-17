package com.ian.mission01.repository;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class LocationRepositoryTest {

    @Test
    void getInstance() throws SQLException {
        LocationRepository lr = LocationRepository.getInstance();
        assertNotNull(lr);
    }
}