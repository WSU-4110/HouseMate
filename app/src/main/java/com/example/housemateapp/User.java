package com.example.housemateapp;

public class User {

    // Class constructor
    public User (String username, String password) {
        this(username, password, null, null, null, 0, 0);
    }

    public  User (String username, String password, String email, String firstName, String lastName, int id, int houseId) {
        this.id = id;
        this.houseId = houseId;
        this.user_name = username;
        this.user_pass = password;
        this.email = email;
        this.firstName = firstName;
        this. lastName = lastName;

    }

    // Getter and Setter functions
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getHouseId() {
        return houseId;
    }

    public void setHouseId(int houseId) {
        this.houseId = houseId;
    }


    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }


    public String getUser_pass() {
        return user_pass;
    }

    public void setUser_pass(String user_pass) {
        this.user_pass = user_pass;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // Member variables
    private int id;
    private int houseId;
    private String user_name;
    private String user_pass;
    private String email;
    private String firstName;
    private String lastName;

}
