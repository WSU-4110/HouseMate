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

public class Subtask {

    // Use Foreign keys to differentiate between tasks
    // Subtask member variables
    private int parentTaskId;
    private String subtaskName;
    private int subtaskId;
    private IncompleteTask parentTask;
    String script = "getIncompleteTaskById.php";
    HTTPSDataReceiver dataReceiver;

    // Subtask constructor
    Subtask(int parentTaskId, String subtaskName, int subtaskId, IncompleteTask parentTask)
    {
        this.parentTaskId = parentTaskId;
        this.subtaskName = subtaskName;
        this.subtaskId = subtaskId;
        this.parentTask = parentTask;
    }

    // Json Creator
    @JsonCreator
    Subtask(
            @JsonProperty("subtaskName") String subtaskName,
            @JsonProperty("subtaskId") int subtaskId,
            @JsonProperty("parentTaskId") int parentTaskId,
            @JsonProperty("parentTask") IncompleteTask parentTask
    ) {
        this.setSubtaskName(subtaskName);
        this.setSubtaskID(subtaskId);
        this.setParentTaskId(parentTaskId);
    }

    // Create a new household group
    public void createSubtask() throws RuntimeException {
        try {
            URL url = new URL("https://housemateapp1.000webhostapp.com/createSubtask.php");

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
                subtaskName = responseLines[0];
                subtaskId = Integer.parseInt(responseLines[1]);
                parentTaskId = Integer.parseInt(responseLines[2]);
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Error communicating with server");
        }
    }

    // Task Loader
    public int loadParentTask() { return parentTask.getId(); }

    // Setters
    public void setParentTaskId(int parentTaskId)
    {
        this.parentTaskId = parentTaskId;
    }

    public void setSubtaskName(String subtaskName)
    {
        this.subtaskName = subtaskName;
    }

    public void setSubtaskID(int subtaskId) { this.subtaskId = subtaskId; }

    // Getters
    public int getParentTaskId()
    {
        return parentTaskId;
    }

    public String getSubtaskName()
    {
        return subtaskName;
    }

    public int getSubtaskID() { return subtaskId; }
}
