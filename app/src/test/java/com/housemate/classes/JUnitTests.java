package com.housemate.classes;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

class JUnitTests {
    Household testHouse1, testHouse2, testHouse3, testHouse4;
    String testURL;

    @BeforeEach
    void setUp() throws Exception {
        testHouse1 = new Household("Hearth Place", 128);
        testHouse2 = new Household("The Nexus", 136);
        testHouse3 = new Household("Das Haus", 144);
        testHouse4 = new Household("Poppenhausen", 152);
        testURL = new String("https://housemateapp1.000webhostapp.com/");
    }

    @AfterEach
    void tearDown() throws Exception {
        testHouse1.setHouseID(-1);
        testHouse1.setHouseholdName("");
        testHouse2.setHouseID(-1);
        testHouse2.setHouseholdName("");
        testHouse3.setHouseID(-1);
        testHouse3.setHouseholdName("");
        testHouse4.setHouseID(-1);
        testHouse4.setHouseholdName("");
        testURL = "";
    }

    @Test
    void Household1() throws Exception {
        assertNotSame(testHouse1, testHouse2, "FAILURE: Same household object constructed on two different variables");
    }

    @Test
    void Household2() throws Exception{
        assertNotNull(testHouse3, "FAILURE: Household object is null");
    }

    @Test
    void createHousehold1() {
        assertNotNull(testHouse1.getHouseholdName(), "FAILURE: Household name is null");
    }

    @Test
    void createHousehold2() {
        assertEquals(testHouse4.getHouseID(), 152, "FAILURE: Household ID is invalid");
    }

    @Test
    void renameHousehold1() {
        testHouse1.setHouseholdName("Test House Zero");
        assertSame("Test House Zero", testHouse1.getHouseholdName(), "FAILURE: Household name hasn't changed");
    }

    @Test
    void renameHousehold2() {
        testHouse2.setHouseholdName("Steven's House");
        assertNotEquals("'", testHouse2.getHouseholdName().charAt(7));
    }

    @Test
    void loadMetrics1() {
        assertNotNull(testHouse1, "FAILURE: Task list is null");
    }

    @Test
    void loadMetrics2() {
        assertNotEquals(testHouse3.loadMetrics() == testHouse4.loadMetrics(), "FAILURE: Household objects loading the same metrics object")
    }

    @Test
    void HTTPSDataReceiver1() {
        if (testHouse3.getKey() == null)
        {
            assertNull(testHouse3, "FAILURE: Key should be null");
        }
    }

    @Test
    void HTTPSDataReceiver2() {
        if (testHouse4.getKey() == testHouse4.getKey())
        {
            assertNotSame(testHouse3, testHouse4, "FAILURE: Keys should not match");
        }
    }

    @Test
    void HTTPSDataSender1() {
        assertEquals(testURL, "https://housemateapp1.000webhostapp.com/", "FAILURE: Invalid destination URL");
    }

    @Test
    void HTTPSDataSender2() {
        assertNotNull(testHouse1, "FAILURE: Cannot send null object");
    }
}