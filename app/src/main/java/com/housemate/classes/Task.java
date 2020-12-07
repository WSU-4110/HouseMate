package com.housemate.classes;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ObjectInputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public abstract class Task {
    public ObjectInputStream.GetField getDescription;
    private String name;
    private String description;
    private int id;

    // Dynamic assignment code to be implemented later:
    // private static final String[] frequencyTypes = { "ONE-TIME", "DAILY", "WEEKLY", "MONTHLY" };
    // private int assignedUserIndex;
    // private ArrayList<String> assignableUsers;
    // private String frequency;

    public Task(
            String name,
            String description
            //ArrayList<String> assignableUsers,
            //String frequency
    ) {
        this.name = name;
        this.description = description;
        id = -1;

/*
        if (assignableUsers.size() == 0)
            throw new RuntimeException("Error creating task: need at least 1 assigned user");
        this.assignableUsers = assignableUsers;

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
            throw new RuntimeException("Error creating task: invalid frequency");
 */
    }

    public abstract String getDateAndTimeText();

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public int getId() { return id; }

    void setId(int id) { this.id = id; }

    // public String getAssignedUser() { return assignableUsers.get(assignedUserIndex); }

    // public ArrayList<String> getAssignableUsers() { return assignableUsers; }

    // public String getFrequency() { return frequency; }

    /*
    public Task completeTask(int householdId) { //Temporary solution; change so householdId is returned from the PHP script instead later on rather than being a parameter
        if (isCompleted)
            return null;

        try {
            String script = "completeTask.php";
            String data = String.valueOf(id);
            String[] responseLines = HTTPSDataSender.initiateTransaction(script, data);

            if (responseLines.length < 1 || responseLines[0].equals("CONNECT_ERROR"))
                throw new RuntimeException();
        }
        catch (Exception e) {
            throw new RuntimeException("Error communicating with server");
        }

        isCompleted = true;
        LocalDate newDueDate = null;

        switch (frequency) {
            case "ONE-TIME":
                return null;
            case "DAILY":
                newDueDate = dueDate.plusDays(1);
                break;
            case "WEEKLY":
                newDueDate = dueDate.plusWeeks(1);
                break;
            case "MONTHLY":
                newDueDate = dueDate.plusMonths(1);
        }

        //If task is recurring, new task with updated due date is created and returned
        Task task = new Task(name, description, assignableUsers, newDueDate, dueTime, frequency);
        task.assignedUserIndex = (assignedUserIndex + 1) % assignableUsers.size();
        task.createTask(householdId);
        return task;
    }
     */

    /* Necessary anymore?
    public static Task getTaskById(int taskId) {
        try {
            String script = "getTaskById.php";
            String data = String.valueOf(taskId);
            String[] responseLines = HTTPSDataSender.initiateTransaction(script, data);

            if (responseLines.length < 1 || responseLines[0].equals("CONNECT_ERROR"))
                throw new RuntimeException();
            else {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(responseLines[0], Task.class);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error communicating with server");
        }
    }
     */
}

