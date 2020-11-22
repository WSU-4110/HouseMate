package com.housemate.classes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Subtask {

    // Use Foreign keys to differentiate between tasks
    // Subtask member variables
    private Task parentTask;
    private String name;
    private int subtaskID;

    // Subtask constructor
    Subtask(Task parentTask, String name, boolean isCompleted, int subtaskID)
    {
        this.parentTask = parentTask;
        this.name = name;
        this.subtaskID = subtaskID;
    }

    // Json Creator
    @JsonCreator
    Subtask(
            @JsonProperty("parentTask") Task parentTask,
            @JsonProperty("name") String name,
            @JsonProperty("subtaskID") int subtaskID
    ) {
        this.setParentTask(parentTask);
        this.setSubtaskName(name);
        this.setSubtaskID(subtaskID);
    }

    // Setters
    public void setParentTask(Task parentTask)
    {
        this.parentTask = parentTask;
    }

    public void setSubtaskName(String name)
    {
        this.name = name;
    }

    public void setSubtaskID(int subtaskID) { this.subtaskID = subtaskID; }

    // Getters
    public Task getParentTask()
    {
        return parentTask;
    }

    public String getSubtaskName()
    {
        return name;
    }

    public int getSubtaskID() { return subtaskID; }

    //
}
