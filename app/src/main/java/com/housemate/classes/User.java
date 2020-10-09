package com.housemate.classes;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

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
        this.lastName = lastName;

    }

    @JsonCreator
    User(
            @JsonProperty("id") int id,
            @JsonProperty("houseID") int houseID,
            @JsonProperty("user_name") String user_name,
            @JsonProperty("user_pass") String user_pass,
            @JsonProperty("email") String email,
            @JsonProperty("firstName") String firstName,
            @JsonProperty("lastName") String lastName
    ) {
        this.id = id;
        this.houseId = houseID;
        this.user_name = user_name;
        this.user_pass = user_pass;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }


    public void register() throws RuntimeException {
        try {
            URL url = new URL("https://housemateapp1.000webhostapp.com/register.php");

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            String data = objectMapper.writeValueAsString(this);

            HTTPSDataSender sender = new HTTPSDataSender(url, data);
            FutureTask<String[]> senderTask = new FutureTask<>(sender);
            ExecutorService executor = Executors.newFixedThreadPool(1);
            executor.execute(senderTask);
            String[] responseLines = senderTask.get();

            if (responseLines.length < 1 || responseLines[0].equals("CONNECT_ERROR"))
                throw new RuntimeException();
            else {
                id = Integer.parseInt(responseLines[0]);
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Error communicating with server");
        }
    }

    public void login() throws RuntimeException {
        try {
            URL url = new URL("https://housemateapp1.000webhostapp.com/login.php");

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            String data = objectMapper.writeValueAsString(this);

            HTTPSDataSender sender = new HTTPSDataSender(url, data);
            FutureTask<String[]> senderTask = new FutureTask<>(sender);
            ExecutorService executor = Executors.newFixedThreadPool(1);
            executor.execute(senderTask);
            String[] responseLines = senderTask.get();

            if (responseLines.length < 1 || responseLines[0].equals("CONNECT_ERROR"))
                throw new RuntimeException();
            else {
                id = Integer.parseInt(responseLines[0]);
                houseId = Integer.parseInt(responseLines[1]);
                firstName = responseLines[2];
                lastName = responseLines[3];
                email = responseLines[4];
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Error communicating with server");
        }
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
