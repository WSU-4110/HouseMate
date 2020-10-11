package com.housemate.classes;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class Households
{
    // Member variables
    private String household;
    private int houseID;

    // Constructor
    public Households(String household, int houseID)
    {
        this.household = household;
        this.houseID =houseID;
    }

    // Setters
    public void setHouseID(int houseID)
    {
        this.houseID = houseID;
    }

    public void setHousehold(String household)
    {
        this.household = household;
    }

    // Getters
    public int getHouseID()
    {
        return houseID;
    }

    public String getHousehold()
    {
        return household;
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
}
