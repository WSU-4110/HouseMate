package com.housemate.classes;

import com.housemate.activities.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testng.reporters.jq.Main;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.testng.Assert.assertEquals;

public class UserTesting {
    public static User user;
    @Before
    public void setBefore(){
        user = new User();
        user.setUser_name("jsmith");
        user.setUser_pass("password");
        user.login();
    }

    @Test
    public void updateUserFirstName() {
        String firstName = "billy";

        user.setFirstName(firstName);
        assertEquals(firstName, user.getFirstName(), "update first name only");
    }
    @Test
    public void updateUserLastName() {
        String lastName = "smith";

        user.setLastName(lastName);

        assertEquals(lastName, user.getLastName(), "update last name only");
    }

    @Test
    public void updateUserEmail() {
        String email = "email@email.com";

        user.setEmail(email);

        assertEquals(email, user.getEmail(), "update email only");
    }


    @Test
    public void updateFirstNameInDB() {
        String firstName = "johnnn";
        user.updateUsernameInDB(firstName);
        assertEquals(firstName, user.getFirstName());
    }

    @Test
    public void updateLastNameInDB() {
        String lastName = "smithh";
        user.updateUsernameInDB(lastName);
        assertEquals(lastName, user.getLastName());
    }

    @Test
    public void updateUsernameInDB() {
        String username = "jjsmith";
        user.updateUsernameInDB(username);
        assertEquals(username, user.getUser_name());
    }

    @Test
    public void updateEmailInDB() {
        String email = "test@email.com";
        user.updateEmailInDB("test@email.com");
        assertEquals(email, user.getEmail());
    }
}