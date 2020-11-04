package com.housemate.classes;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

//Currently immutable from Activities or any other class outside this package; can only be loaded for
//display via loadHouseholdTasks rather than instantiated with a public constructor and modified.

public class CompletedTask extends Task {
    private final LocalDate dateCompleted;
    private final LocalTime timeCompleted;

    @JsonCreator
    CompletedTask(
            @JsonProperty("name") String name,
            @JsonProperty("description") String description,
            @JsonProperty("assignedUsers") ArrayList<String> assignedUsers,
            @JsonProperty("dueDate") LocalDate dateCompleted,
            @JsonProperty("dueTime") LocalTime timeCompleted,
            @JsonProperty("id") int id
    ) {
        super(name, description, assignedUsers);
        setId(id);
        this.dateCompleted = dateCompleted;
        this.timeCompleted = timeCompleted;
    }

    public LocalDate getDateCompleted() { return dateCompleted; }

    public LocalTime getTimeCompleted() { return timeCompleted; }

    @Override @NonNull
    public String toString () {
        return String.format("<h3>%s</h3>" +
                        "<b><i>%s</i></b>" +
                        "<p>Completed %s at %s</p>\n",
                getName(), getDescription(),
                getDateCompleted().format(DateTimeFormatter.ofPattern("MM-dd-yyyy")),
                getTimeCompleted().format(DateTimeFormatter.ofPattern("hh:mm a")));
    }

    public static ArrayList<Task> loadHouseholdTasks (int houseId) {
        try {
            String script = "loadCompletedTasks.php";
            String data = String.valueOf(houseId);
            String[] responseLines = HTTPSDataSender.initiateTransaction(script, data);
            ArrayList<Task> tasks = new ArrayList<>(responseLines.length);

            if (responseLines.length > 0) {
                if (responseLines[0].equals("CONNECT_ERROR"))
                    throw new RuntimeException();
                else {
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.findAndRegisterModules();
                    for (String response : responseLines)
                        tasks.add(0, objectMapper.readValue(response, CompletedTask.class));
                }
            }
            return tasks;

        } catch (Exception e) {
            throw new RuntimeException("Error communicating with server");
        }
    }
}
