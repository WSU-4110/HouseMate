package com.housemate.classes;

import android.util.Log;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class User {

    // Class constructor
    public User () {
        this(null, null, null, null, null, -1, new ArrayList<Integer>());
    }

    public User (String username, String password) {
        this(username, password, null, null, null, -1, new ArrayList<Integer>());
    }

    public  User (String username, String password, String email, String firstName, String lastName, int id, ArrayList<Integer> houseId) {
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
            @JsonProperty("houseID") ArrayList<Integer> houseID,
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
            String script = "register.php";
            String data = HTTPSDataSender.mapToJson(this);
            String[] responseLines = HTTPSDataSender.initiateTransaction(script, data);

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

    public int login() throws RuntimeException {
        try {
            String script = "login.php";
            String data = HTTPSDataSender.mapToJson(this);
            String[] responseLines = HTTPSDataSender.initiateTransaction(script,data);

            if (responseLines.length < 1 || responseLines[0].equals("CONNECT_ERROR"))
                throw new RuntimeException();
            else {
                id = Integer.parseInt(responseLines[0]);
                firstName = responseLines[1];
                lastName = responseLines[2];
                email = responseLines[3];
                int numHouses = Integer.parseInt(responseLines[4]);
                for (int i = 0; i < numHouses; i++) {
                    houseId.add(Integer.parseInt(responseLines[i + 5]));
                }
                return 1;
            }
        }
        catch (Exception e) {
            return 0;
            //throw new RuntimeException("Error communicating with server");
        }
    }


    public void joinHousehold(int householdId) throws RuntimeException {
        try {
            String script = "joinHousehold.php";
            String data = "{\"id\":" + id + ",\"houseId\":" + householdId + "}";
            String[] responseLines = HTTPSDataSender.initiateTransaction(script, data);

            if (responseLines.length < 1 || responseLines[0].equals("CONNECT_ERROR"))
                throw new RuntimeException();
            else {
                houseId.add(Integer.parseInt(responseLines[0]));
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Error communicating with server");
        }
    }

    public void leaveHousehold(int householdId) throws RuntimeException {
        try {
            String script = "leaveHousehold.php";
            String data = "{\"id\":" + id + ",\"houseId\":" + householdId + "}";
            String[] responseLines = HTTPSDataSender.initiateTransaction(script, data);

            if (responseLines.length < 1 || responseLines[0].equals("CONNECT_ERROR"))
                throw new RuntimeException();
            else {
                houseId.remove(householdId);
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Error communicating with server");
        }
    }
  
    public void deleteUser() throws RuntimeException{
        try {
            URL url = new URL("https://housemateapp1.000webhostapp.com/deleteAccount.php");
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


    public void updateFirstNameInDB(String editedFirstName) throws  RuntimeException{
        try{
            URL url = new URL("https://housemateapp1.000webhostapp.com/updateFirstName.php");

            String data = "{\"id\":" +id + ",\"editedFirstName\":\""+editedFirstName + "\"}";
            HTTPSDataSender sender = new HTTPSDataSender(url, data);
            FutureTask<String[]> senderTask = new FutureTask<>(sender);
            ExecutorService executor = Executors.newFixedThreadPool(1);
            executor.execute(senderTask);
            String[] responseLines = senderTask.get();


            if (responseLines.length < 1 || responseLines[0].equals("CONNECT_ERROR"))
                throw new RuntimeException();
            else {
                    firstName = responseLines[0];
            }

        }
        catch (Exception e){
            throw new RuntimeException("Error communicating with server");
        }

    }
    public void updateLastNameInDB(String editedLastName) throws  RuntimeException{
        try{
            URL url = new URL("https://housemateapp1.000webhostapp.com/updateLastName.php");

            String data = "{\"id\":" +id + ",\"editedLastName\":\""+editedLastName + "\"}";
            HTTPSDataSender sender = new HTTPSDataSender(url, data);
            FutureTask<String[]> senderTask = new FutureTask<>(sender);
            ExecutorService executor = Executors.newFixedThreadPool(1);
            executor.execute(senderTask);
            String[] responseLines = senderTask.get();


            if (responseLines.length < 1 || responseLines[0].equals("CONNECT_ERROR"))
                throw new RuntimeException();
            else {
                    lastName = responseLines[0];
            }
        }
        catch (Exception e){
            throw new RuntimeException("Error communicating with server");
        }
    }

    public void updateUsernameInDB(String editedUsername) throws  RuntimeException{
        try{
            URL url = new URL("https://housemateapp1.000webhostapp.com/updateUsername.php");

            String data = "{\"id\":" +id + ",\"editedUsername\":\""+ editedUsername + "\"}";
            HTTPSDataSender sender = new HTTPSDataSender(url, data);
            FutureTask<String[]> senderTask = new FutureTask<>(sender);
            ExecutorService executor = Executors.newFixedThreadPool(1);
            executor.execute(senderTask);
            String[] responseLines = senderTask.get();

            if (responseLines.length < 1 || responseLines[0].equals("CONNECT_ERROR"))
                throw new RuntimeException();
            else {
                    user_name = responseLines[0];
            }
        }
        catch (Exception e){
            throw new RuntimeException("Error communicating with server");
        }
    }

    public void updateEmailInDB(String editedEmail) throws  RuntimeException {
        try {
            URL url = new URL("https://housemateapp1.000webhostapp.com/updateEmail.php");

            String data = "{\"id\":" + id + ",\"editedEmail\":\"" + editedEmail + "\"}";
            HTTPSDataSender sender = new HTTPSDataSender(url, data);
            FutureTask<String[]> senderTask = new FutureTask<>(sender);
            ExecutorService executor = Executors.newFixedThreadPool(1);
            executor.execute(senderTask);
            String[] responseLines = senderTask.get();
            if (responseLines.length < 1 || responseLines[0].equals("CONNECT_ERROR"))
                throw new RuntimeException();
            else {
                email = responseLines[0];
            }
        } catch (Exception e) {
            throw new RuntimeException("Error communicating with server");
        }
    }

    public int refreshHouseholds() throws RuntimeException {
        try {
            String script = "refreshHouseholds.php";
            String data = HTTPSDataSender.mapToJson(this);
            String[] responseLines = HTTPSDataSender.initiateTransaction(script,data);

            if (responseLines.length < 1 || responseLines[0].equals("CONNECT_ERROR"))
                throw new RuntimeException();
            else {
                int numHouses = Integer.parseInt(responseLines[0]);
                houseId.clear();
                houseId.trimToSize();
                for (int i = 0; i < numHouses; i++) {
                    houseId.add(Integer.parseInt(responseLines[i + 1]));
                }
                return 1;

            }

        } catch (Exception e) {
            return 0;
            //throw new RuntimeException("Error communicating with server");

        }
    }



    // Getter and Setter functions
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public ArrayList<Integer> getHouseId() {
        return houseId;
    }

    public void setHouseId(ArrayList<Integer> houseId) {
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
    private ArrayList<Integer> houseId;
    private String user_name;
    private String user_pass;
    private String email;
    private String firstName;
    private String lastName;

}
