package com.housemate.classes;

import android.util.Log;
import android.widget.ArrayAdapter;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class Household
{
    // Singleton object/instance declaration
        // 'volatile' ensures that our Household objects are correctly represented between different users
    private volatile static Household household;

    // Member variables
    private String householdName;
    private int houseID;

    // Constructors(Private Singleton) - Default/String init/ID init
    private Household() {
        this("",-1);
    }
    private Household(String householdName) {
        this(householdName, -1);
    }
    private Household(String householdName, int houseID)
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

    // Singleton getHouseholdInstance() method definition with synchronized lazy instantiation for app DB and display consistency between testers
    public static Household getHouseholdInstance()
    {
        // Double Checked Locking implementation of Lazy instantiation of singleton object
        if (household == null)
        {
            synchronized (Household.class) {
                if (household == null)
                    household = new Household("", -1);
            }
        }
        return household; // Returns lazily instantiated singleton class object
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

    // Set household group from database
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

    // Retrieve Household information from the database
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

    // Retrieve Users in Household group
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

    // Retrieve User IDs for Household Group list
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

    // Remove a User from the Household Group
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

    // Load User task metrics for the Household Group
    public ArrayList<ArrayList<String>> loadMetrics() throws RuntimeException {
        try {
            String script = "loadMetrics.php";
            String data = "{\"houseID\":" + houseID + "}";
            String[] responseLines = HTTPSDataSender.initiateTransaction(script,data);

            if (responseLines.length < 1 || responseLines[0].equals("CONNECT_ERROR"))
                throw new RuntimeException();
            else {
                ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
                for (int i = 0; i < responseLines.length; i+=2) {
                    result.add(new ArrayList<String>(List.of(responseLines[i],responseLines[i+1])));
                }
                return result;
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Error communicating with server");
        }
    }


}
