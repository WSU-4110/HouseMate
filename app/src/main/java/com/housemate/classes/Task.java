package com.housemate.classes;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.housemate.activities.MainActivity;

import java.net.MalformedURLException;
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

public class Task {
    private static final String[] frequencyTypes = { "ONE-TIME", "DAILY", "WEEKLY", "MONTHLY" };
    private String name;
    private String description;
    private int assignedUserIndex;
    private ArrayList<String> assignableUsers;
    private LocalDate dueDate;
    private LocalTime dueTime;
    private String frequency;
    private int id;
    private boolean isCompleted;

    public Task(
            String name,
            String description,
            ArrayList<String> assignableUsers,
            LocalDate dueDate,
            LocalTime dueTime,
            String frequency
    ) {
        this.name = name;
        this.description = description;
        assignedUserIndex = 0;
        this.dueDate = dueDate;
        this.dueTime = dueTime;

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
    }

    @JsonCreator
    Task(
            @JsonProperty("name") String name,
            @JsonProperty("description") String description,
            @JsonProperty("assignedUserIndex") int assignedUserIndex,
            @JsonProperty("assignableUsers") ArrayList<String> assignableUsers,
            @JsonProperty("dueDate") LocalDate dueDate,
            @JsonProperty("dueTime") LocalTime dueTime,
            @JsonProperty("frequency") String frequency,
            @JsonProperty("id") int id,
            @JsonProperty("isCompleted") boolean isCompleted
    ) {
        this(name, description, assignableUsers, dueDate, dueTime, frequency);
        this.assignedUserIndex = assignedUserIndex;
        this.id = id;
        this.isCompleted = isCompleted;
    }

    @Override @NonNull
    public String toString () {
        return String.format("%s\n%s\nAssigned to %s\nDue %s at %s",
                name, description, getAssignedUser(), dueDate, dueTime);
    }

    public String getName() { return name; }

    public String getDescription() { return description; }

    public String getAssignedUser() { return assignableUsers.get(assignedUserIndex); }

    public ArrayList<String> getAssignableUsers() { return assignableUsers; }

    public boolean isCompleted() { return isCompleted; }

    public LocalDate getDueDate() { return dueDate; }

    public LocalTime getDueTime() { return dueTime; }

    public String getFrequency() { return frequency; }

    public int getId() { return id; }

    public void setName(String name) {this.name = name;}

    public void createTask(int householdId) throws RuntimeException {
        try {
            String script = "createTask.php";
            String data = HTTPSDataSender.mapToJson(this) + "\n" + householdId;
            String[] responseLines = HTTPSDataSender.initiateTransaction(script, data);

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

    public void completeNew (int userId, int houseId) {
        try {
            if (this == null || userId == -1 || houseId == -1) {throw new RuntimeException();}
            String script = "completeTask1.php";
            String data = "{\"taskId\":" + id + ",\"userId\":" + userId + ",\"houseId\":" + houseId + "}";
            HTTPSDataSender.initiateTransaction(script, data);
        } catch (Exception e) {
            throw new RuntimeException("Error communicating with server");
        }
    }

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

    public int delete() {
        try {
            String script = "deleteTask.php";
            String data = String.valueOf(id);
            String[] responseLines = HTTPSDataSender.initiateTransaction(script, data);

            if (responseLines[0].equals("1")) {return 1;}

            else {return 0;}
        }
        catch (Exception e) {
            throw new RuntimeException("Error communicating with server");
        }
    }

    public void update() throws RuntimeException{
        try {
            String script = "updateTask.php";
            String data = HTTPSDataSender.mapToJson(this);
            String[] responseLines = HTTPSDataSender.initiateTransaction(script,data);

            if (responseLines.length < 1 || responseLines[0].equals("CONNECT_ERROR"))
                throw new RuntimeException();
        }
        catch (Exception e) {
            throw new RuntimeException("Error communicating with server");
        }
    }

    public static ArrayList<Task> loadTasks (int householdId) {
        try {
            String script = "loadTasks.php";
            String data = String.valueOf(householdId);
            String[] responseLines = HTTPSDataSender.initiateTransaction(script, data);
            ArrayList<Task> tasks = new ArrayList<>(responseLines.length);

            if (responseLines.length > 0) {
                if (responseLines[0].equals("CONNECT_ERROR"))
                    throw new RuntimeException();
                else {
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.findAndRegisterModules();
                    for (int index = 0; index < responseLines.length; index++)
                        tasks.add(index, objectMapper.readValue(responseLines[index], Task.class));
                }
                sortByEarliestDeadline(tasks);
            }
            return tasks;

        } catch (Exception e) {
            throw new RuntimeException("Error communicating with server");
        }
    }

    private static void sortByEarliestDeadline(ArrayList<Task> tasks) {
        Comparator<Task> soonestDeadlineComparator = (task1, task2) -> {
            String task1DateTime = task1.getDueDate().toString() + task1.getDueTime().toString();
            String task2DateTime = task2.getDueDate().toString() + task2.getDueTime().toString();
            return (task1DateTime.compareTo(task2DateTime));
        };
        tasks.sort(soonestDeadlineComparator);
    }
}

