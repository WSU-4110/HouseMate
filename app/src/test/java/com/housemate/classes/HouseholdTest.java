package com.housemate.classes;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HouseholdTest {
    Household testHouse1, testHouse2;

    @BeforeEach
    void setUp() throws Exception {
        testHouse1 = new Household("Test House One", 1024);
        testHouse2 = new Household("Test House Two", 2048);
    }

    @AfterEach
    void tearDown() throws Exception {

    }

    @Test
    void createHousehold() {
        assertNotNull(testHouse1.getHouseholdName(), "Household name is non null");
    }

    @Test
    void renameHousehold() {
        testHouse1.setHouseholdName("Test House Zero");
        assertNotSame("Test House One", testHouse1.getHouseholdName());
    }
}