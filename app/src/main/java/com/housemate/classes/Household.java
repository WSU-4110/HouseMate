package com.housemate.classes;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.PropertyAccessor;

import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class Household
{
    // Class Member Variables
    private int houseID;
    private String houseName;
    // private User user;

    // Default Constructor
    public Household()
    {
        houseID = 0;
        houseName = "";
        // user = null;
    }

    // Parameterized Constructor
    public Household(int houseID, String name)
    {
        this.houseID = houseID;
        this.houseName = houseName;
        // this.user = user;
    }

    // Getter Methods
    public int getID()
    {
        return houseID;
    }

    public String getName()
    {
        return houseName;
    }
    
    //public User getUser() { return user; }

    // Setter Methods
    public void setID(int houseID)
    {
        this.houseID = houseID;
    }

    public void setName(String name)
    {
        this.houseName = houseName;
    }

    // public void setUser(User user) { this.user = user; }

    // Concept for associating a user with a household
    /*private boolean assocUser()
    {
        // If User class object references THIS, return true;
        // else return false;
    }*/

    // Concept for associating a user's task with the household
    /*public void assocTask()
    {
        // If User class object creates a new task, associate it with the household
    }*/
}