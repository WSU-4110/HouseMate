package com.housemate.classes;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class CompletedTaskTest {
    CompletedTask task;

    @BeforeEach
    void setUp() {
        task = new CompletedTask("test", "description", LocalDate.of(2020,12,15), LocalTime.of(12,0), 1);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getDateCompleted() {
        LocalDate test = LocalDate.of(2020,12,15);
        assertEquals(test, task.getDateCompleted(), "testing test date vs. Completed task date");
        assertEquals(test.getYear(),task.getDateCompleted().getYear(), "testing test year vs. Completed task year");
        assertEquals(test.getMonth(),task.getDateCompleted().getMonth(), "testing test month vs. Completed task month");
        test = LocalDate.of(2020,12,16);
        assertNotEquals(test, task.getDateCompleted(), "testing 12/16 vs. 12/15");
        test = LocalDate.of(2020,11,15);
        assertNotEquals(test, task.getDateCompleted(), "testing 12/16 vs. 11/15");
        test = LocalDate.of(2021,12,15);
        assertNotEquals(test, task.getDateCompleted(), "testing 12/15/2021 vs. 12/15/2020");
    }

    @Test
    void getTimeCompleted() {
        LocalTime test = LocalTime.of(12,0);
        assertEquals(test, task.getTimeCompleted(), "testing test time vs. Completed task time");
        assertEquals(test.getHour(),task.getTimeCompleted().getHour(), "testing test hour vs. Completed task hour");
        assertEquals(test.getMinute(), task.getTimeCompleted().getMinute(), "testing test minute vs. Completed task minute");
        test = LocalTime.of(12,1);
        assertNotEquals(test, task.getTimeCompleted(), "testing 12:01 vs. 12:00");
        test = LocalTime.of(11,0);
        assertNotEquals(test, task.getTimeCompleted(), "testing 11:00 vs. 12:00");
    }
}