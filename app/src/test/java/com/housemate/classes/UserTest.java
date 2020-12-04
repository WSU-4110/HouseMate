package com.housemate.classes;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    User user = new User("aweinbaum", "pass");
    @BeforeEach
    void setUp() {
        user.login();
        assertNotEquals(-1, user.getId());
        user.joinHouseholdById(8);
        assertTrue(user.getHouseId().contains(8));
        user.joinHouseholdById(22);
        assertTrue(user.getHouseId().contains(22));
    }

    @AfterEach
    void tearDown() {
        while (!user.getHouseId().isEmpty())
            user.leaveHousehold(user.getHouseId().get(0));
    }

    @Test
    void login() {
        User test = new User("","");
        assertEquals(0, test.login(), "test login with empty username and pass");
        test  = new User("aweinbaum","pass");
        assertEquals(1, test.login(), "login with correct username and pass");
        test  = new User("aweinbaum","password");
        assertEquals(0, test.login(), "login with incorrect pass");
        test = new User("AWEINBAUM", "pass");
        assertEquals(1, test.login(), "login username case sensitivity test");
        test = new User("aweinbaum", "PASS");
        assertEquals(0, test.login(), "login password case sensitivity test");
        test = new User("aw", "pass");
        assertEquals(0, test.login(), "login with incorrect username");
    }


    @Test
    void joinHouseholdById() {
        assertFalse(user.getHouseId().contains(1), "user not in house 1");
        user.joinHouseholdById(1);
        assertTrue(user.getHouseId().contains(1), "check if user joined house 1");
        assertFalse(user.getHouseId().contains(2), "user not in house 2");
        user.joinHouseholdById(2);
        assertTrue(user.getHouseId().contains(2), "check if user joined house 2");
        assertThrows(Exception.class, () -> user.joinHouseholdById(-1), "test join house with invalid id");
        assertThrows(Exception.class, () -> user.joinHouseholdById(200), "test join nonexistent house");
    }

    @Test
    void leaveHousehold() {
        assertTrue(user.getHouseId().contains(8), "user in house 8");
        assertTrue(user.getHouseId().contains(22), "user in house 22");
        user.leaveHousehold(22);
        assertFalse(user.getHouseId().contains(22), "check if user left house 22");
        user.leaveHousehold(8);
        assertFalse(user.getHouseId().contains(8), "check if user left house 8");
        assertTrue(user.getHouseId().isEmpty(), "check user is in no houses");
        assertThrows(Exception.class, () -> user.leaveHousehold(-2), "test leave household invalid id");
        assertThrows(Exception.class, () -> user.leaveHousehold(10), "test leave household which user is not in");
    }

    @Test
    void refreshHousehold() {
        ArrayList<Integer> temp = user.getHouseId();
        user.refreshHouseholds();
        assertEquals(temp.size(), user.getHouseId().size(), "test refresh houseIds size equals temp size");
        for (int i = 0; i < temp.size(); i++) {
            assertEquals(temp.get(i),user.getHouseId().get(i), "test refresh houseId values equals temp values");
        }
    }
}