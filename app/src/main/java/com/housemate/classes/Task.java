package com.housemate.classes;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class Task {
    private static final String[] frequencyTypes = { "ONE-TIME", "DAILY", "WEEKLY", "MONTHLY" };
    private String name;
    private String description;
    private int assignedUserIndex;
    private ArrayList<String> assignableUsers;
    private String dueDate;
    private String dueTime;
    private String frequency;
    private int id;
    private boolean isCompleted;

    public Task(
            String name,
            String description,
            ArrayList<String> assignableUsers,
            String dueDate,
            String dueTime,
            String frequency
    ) {
        this.name = name;
        this.description = description;
        assignedUserIndex = 0;
        this.assignableUsers = assignableUsers;
        this.dueDate = dueDate;
        this.dueTime = dueTime;

        boolean frequencyFound = false;
        for (String frequencyType : frequencyTypes) {
            if (frequency.equals(frequencyType)) {
                frequencyFound = true;
                break;
            }
        }
        if (frequencyFound)
            this.frequency = frequency;
        else
            throw new RuntimeException("Error initializing task: invalid frequency");
    }

    @JsonCreator
    Task(
            @JsonProperty("name") String name,
            @JsonProperty("description") String description,
            @JsonProperty("assignedUserIndex") int assignedUserIndex,
            @JsonProperty("assignableUsers") ArrayList<String> assignableUsers,
            @JsonProperty("dueDate") String dueDate,
            @JsonProperty("dueTime") String dueTime,
            @JsonProperty("frequency") String frequency,
            @JsonProperty("id") int id,
            @JsonProperty("isCompleted") boolean isCompleted
    ) {
        this(name, description, assignableUsers, dueDate, dueTime, frequency);
        this.assignedUserIndex = assignedUserIndex;
        this.id = id;
        this.isCompleted = isCompleted;
    }

    public String getName() { return name; }

    public String getDescription() { return description; }

    public String getAssignedUser() { return assignableUsers.get(assignedUserIndex); }

    public ArrayList<String> getAssignableUsers() { return assignableUsers; }

    public boolean isCompleted() { return isCompleted; }

    public String getDueDate() { return dueDate; }

    public String getDueTime() { return dueTime; }

    public String getFrequency() { return frequency; }

    public int getId() { return id; }

    public void createTask(int householdId) throws RuntimeException {
        try {
            URL url = new URL("https://housemateapp1.000webhostapp.com/createTask.php");

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            String data = objectMapper.writeValueAsString(this) + "\n" + householdId; //Approach may change later

            HTTPSDataSender sender = new HTTPSDataSender(url, data);
            FutureTask<String[]> senderTask = new FutureTask<>(sender);
            ExecutorService executor = Executors.newFixedThreadPool(1);
            executor.execute(senderTask);
            String[] responseLines = senderTask.get();

            if (responseLines.length < 1 || responseLines[0].equals("CONNECT_ERROR"))
                throw new RuntimeException();
            else {
                id = Integer.parseInt(responseLines[0]);
                isCompleted = Boolean.parseBoolean(responseLines[1]);
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Error communicating with server");
        }
    }

    public Task completeTask(int householdId) {
        if (isCompleted)
            return null;

        try {
            URL url = new URL("https://housemateapp1.000webhostapp.com/completeTask.php");

            HTTPSDataSender sender = new HTTPSDataSender(url, String.valueOf(id));
            FutureTask<String[]> senderTask = new FutureTask<>(sender);
            ExecutorService executor = Executors.newFixedThreadPool(1);
            executor.execute(senderTask);
            String[] responseLines = senderTask.get();

            if (responseLines.length < 1 || responseLines[0].equals("CONNECT_ERROR"))
                throw new RuntimeException();
        }
        catch (Exception e) {
            throw new RuntimeException("Error communicating with server");
        }

        isCompleted = true;
        String newDueDate = "";

        switch (frequency) {
            case "ONE-TIME":
                return null;
            case "DAILY":
                newDueDate = LocalDate.parse(dueDate).plusDays(1).toString();
                break;
            case "WEEKLY":
                newDueDate = LocalDate.parse(dueDate).plusWeeks(1).toString();
                break;
            case "MONTHLY":
                newDueDate = LocalDate.parse(dueDate).plusMonths(1).toString();
        }

        //If task is recurring, new task with updated due date is created and returned
        Task task = new Task(name, description, assignableUsers, newDueDate, dueTime, frequency);
        task.assignedUserIndex = (assignedUserIndex + 1) % assignableUsers.size();
        task.createTask(householdId);
        return task;
    }

    public static Task getTaskById(int taskId) {
        try {
            URL url = new URL("https://housemateapp1.000webhostapp.com/getTaskById.php");

            HTTPSDataSender sender = new HTTPSDataSender(url, String.valueOf(taskId));
            FutureTask<String[]> senderTask = new FutureTask<>(sender);
            ExecutorService executor = Executors.newFixedThreadPool(1);
            executor.execute(senderTask);
            String[] responseLines = senderTask.get();

            if (responseLines.length < 1 || responseLines[0].equals("CONNECT_ERROR"))
                throw new RuntimeException();
            else {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(responseLines[0], Task.class);
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
            ArrayList<Task> tasks = new ArrayList<>(responseLines.length);

            if (responseLines.length > 0) {
                if (responseLines[0].equals("CONNECT_ERROR"))
                    throw new RuntimeException();
                else {
                    ObjectMapper objectMapper = new ObjectMapper();

                    for (int index = 0; index < responseLines.length; index++)
                        tasks.add(index, objectMapper.readValue(responseLines[index], Task.class));
                }
            }
            return tasks;
        }
        catch (Exception e) {
            throw new RuntimeException("Error communicating with server");
        }

    }
}

