package com.housemate.activities;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.testng.Assert.assertEquals;

public class EditProfileTesting {

    @Test
    public void isEmailValid() {
        String email = "go2213@wayne.edu";
        boolean output = EditProfile.isEmailValid(email);
        assertEquals(true, output);
    }
}