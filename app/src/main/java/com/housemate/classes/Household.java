package com.housemate.classes;

import android.util.Log;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class Household
{
    // Member variables
    private String householdName;
    private int houseID;

    // Constructor
    public Household() {
        this("",-1);
    }
    public Household(String householdName) {
        this(householdName, -1);
    }
    public Household(String householdName, int houseID)
    {
        this.householdName = householdName;
        this.houseID = houseID;
    }


    // Setters
    public void setHouseID(int houseID)
    {
        this.houseID = houseID;
    }

    public void setHouseholdName(String household)
    {
        this.householdName = household;
    }

    // Getters
    public int getHouseID()
    {
        return houseID;
    }

    public String getHouseholdName()
    {
        return householdName;
    }

    // Create a new household group
    public void createHousehold() throws RuntimeException {
        try {
            URL url = new URL("https://housemateapp1.000webhostapp.com/createNewHousehold.php");

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
                houseID = Integer.parseInt(responseLines[0]);
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Error communicating with server");
        }
    }

    // Create a new household group
    public void setHousehold(int id) throws RuntimeException {
        try {
            URL url = new URL("https://housemateapp1.000webhostapp.com/setHousehold.php");

            String data = "{\"houseId\":" + id + "}";

            HTTPSDataSender sender = new HTTPSDataSender(url, data);
            FutureTask<String[]> senderTask = new FutureTask<>(sender);
            ExecutorService executor = Executors.newFixedThreadPool(1);
            executor.execute(senderTask);
            String[] responseLines = senderTask.get();

            if (responseLines.length < 1 || responseLines[0].equals("CONNECT_ERROR"))
                throw new RuntimeException();
            else {
                houseID = Integer.parseInt(responseLines[0]);
                householdName = responseLines[1];
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Error communicating with server");
        }
    }

    public static String getHouseholdInfo(int id) throws RuntimeException {
        try {
            URL url = new URL("https://housemateapp1.000webhostapp.com/getHouseholdInfo.php");

            String data = "{\"houseId\":" + id + "}";

            HTTPSDataSender sender = new HTTPSDataSender(url, data);
            FutureTask<String[]> senderTask = new FutureTask<>(sender);
            ExecutorService executor = Executors.newFixedThreadPool(1);
            executor.execute(senderTask);
            String[] responseLines = senderTask.get();

            if (responseLines.length < 1 || responseLines[0].equals("CONNECT_ERROR"))
                throw new RuntimeException();
            else {
                String name = responseLines[0];
                return name;
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Error communicating with server");
        }
    }

    public ArrayList<String> getUsers() throws RuntimeException {
        try {
            URL url = new URL("https://housemateapp1.000webhostapp.com/getUsers.php");

            String data = "{\"houseID\":" + houseID + "}";

            HTTPSDataSender sender = new HTTPSDataSender(url, data);
            FutureTask<String[]> senderTask = new FutureTask<>(sender);
            ExecutorService executor = Executors.newFixedThreadPool(1);
            executor.execute(senderTask);
            String[] responseLines = senderTask.get();

            if (responseLines.length < 1 || responseLines[0].equals("CONNECT_ERROR"))
                throw new RuntimeException();
            else {
                ArrayList<String> users = new ArrayList<String>();
                for (int i = 0; i < responseLines.length; i++) {
                    users.add(responseLines[i]);
                }
                return users;
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Error communicating with server");
        }
    }
    public ArrayList<String> getUserIdList() throws RuntimeException {
        try {
            URL url = new URL("https://housemateapp1.000webhostapp.com/getUserIdList.php");

            String data = "{\"houseID\":" + houseID + "}";

            HTTPSDataSender sender = new HTTPSDataSender(url, data);
            FutureTask<String[]> senderTask = new FutureTask<>(sender);
            ExecutorService executor = Executors.newFixedThreadPool(1);
            executor.execute(senderTask);
            String[] responseLines = senderTask.get();

            if (responseLines.length < 1 || responseLines[0].equals("CONNECT_ERROR"))
                throw new RuntimeException();
            else {
                ArrayList<String> userIdList = new ArrayList<String>();
                for (int i = 0; i < responseLines.length; i++) {
                    userIdList.add(responseLines[i]);
                }
                return userIdList;
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Error communicating with server");
        }
    }
    public void removeHousemateFromHousehold(int userID) throws RuntimeException {
        try {
            URL url = new URL("https://housemateapp1.000webhostapp.com/removeHousemateFromHousehold.php");

            String data = "{\"houseID\":" + houseID + ",\"userID\":" + userID + "}";

            HTTPSDataSender sender = new HTTPSDataSender(url, data);
            FutureTask<String[]> senderTask = new FutureTask<>(sender);
            ExecutorService executor = Executors.newFixedThreadPool(1);
            executor.execute(senderTask);
            String[] responseLines = senderTask.get();

            if (responseLines.length < 1 || responseLines[0].equals("CONNECT_ERROR"))
                throw new RuntimeException();
            else {
                Log.i("DB response",responseLines[0] ) ;
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Error communicating with server");
        }
    }


}
