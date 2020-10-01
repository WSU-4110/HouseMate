package com.housemate.classes;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class Task {
    int id;
    private String description;
    private String assignedUser;
    private boolean isCompleted;

    public Task(
            String description,
            String assignedUser,
            boolean isCompleted
    ) {
        this.description = description;
        this.assignedUser = assignedUser;
        this.isCompleted = isCompleted;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void createTask() throws RuntimeException {
        setId((description + System.currentTimeMillis()).hashCode());

        try {
            URL url = new URL("https://housemateapp1.000webhostapp.com/createTask.php");

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            String data = objectMapper.writeValueAsString(this);

            HTTPSDataSender sender = new HTTPSDataSender(url, data);
            FutureTask<Void> senderTask = new FutureTask<>(sender);
            ExecutorService executor = Executors.newFixedThreadPool(1);
            executor.execute(senderTask);

            while(!senderTask.isDone());
        }
        catch (Exception e) {
            throw new RuntimeException("Error communicating with server, please try again");
        }
    }
}

