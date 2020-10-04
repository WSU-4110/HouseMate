package com.housemate.classes;

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

public class Task {
    private int id;
    private boolean isCompleted;
    private String description;
    private String assignedUser;

    public Task(
            String description,
            String assignedUser
    ) {
        this.description = description;
        this.assignedUser = assignedUser;
    }

    @JsonCreator
    Task(
            @JsonProperty("id") int id,
            @JsonProperty("isCompleted") boolean isCompleted,
            @JsonProperty("description") String description,
            @JsonProperty("assignedUser") String assignedUser
    ) {
        this.id = id;
        this.isCompleted = isCompleted;
        this.description = description;
        this.assignedUser = assignedUser;
    }

    public void createTask() throws RuntimeException {
        try {
            URL url = new URL("https://housemateapp1.000webhostapp.com/createTask.php");

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            String data = objectMapper.writeValueAsString(this);

            HTTPSDataSender sender = new HTTPSDataSender(url, data);
            FutureTask<String[]> senderTask = new FutureTask<>(sender);
            ExecutorService executor = Executors.newFixedThreadPool(1);
            executor.execute(senderTask);
            String[] responseLines = senderTask.get();

            if (responseLines[0].equals("CONNECT_ERROR"))
                throw new RuntimeException();
            else {
                id = Integer.parseInt(responseLines[0]);
                isCompleted = Boolean.parseBoolean(responseLines[1]);
                System.out.println("done");
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Error communicating with server");
        }
    }

    public static ArrayList<Task> loadTasks() {
        try {
            URL url = new URL("https://housemateapp1.000webhostapp.com/loadTasks.php");

            HTTPSDataReceiver receiver = new HTTPSDataReceiver(url);
            FutureTask<String[]> receiverTask = new FutureTask<>(receiver);
            ExecutorService executor = Executors.newFixedThreadPool(1);
            executor.execute(receiverTask);
            String[] responseLines = receiverTask.get();

            if (responseLines[0].equals("CONNECT_ERROR"))
                throw new RuntimeException();
            else {
                ObjectMapper objectMapper = new ObjectMapper();
                ArrayList<Task> tasks = new ArrayList<>(responseLines.length);

                for (int index = 0; index < responseLines.length; index++)
                    tasks.add(index, objectMapper.readValue(responseLines[index], Task.class));

                return tasks;
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Error communicating with server");
        }

    }
}

