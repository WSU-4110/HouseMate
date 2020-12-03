package com.housemate.classes;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;

public class IncompleteTask extends Task {
    private ArrayList<String> assignedUsers;
    private LocalDate dueDate;
    private LocalTime dueTime;
    private final String repeatType;
    private static final String[] repeatTypes = { "NEVER", "DAILY", "WEEKLY", "MONTHLY" };
    private ArrayList<Subtask> subtaskArrayList;

    public IncompleteTask(
            String name,
            String description,
            ArrayList<String> assignedUsers,
            LocalDate dueDate,
            LocalTime dueTime,
            String repeatType
    ) {
        super(name, description);
        this.assignedUsers = new ArrayList<>(assignedUsers);
        this.dueDate = dueDate;
        this.dueTime = dueTime;

        boolean repeatTypeFound = false;
        for (String type : repeatTypes) {
            if (repeatType.equals(type)) {
                repeatTypeFound = true;
                break;
            }
        }
        if (repeatTypeFound)
            this.repeatType = repeatType;
        else
            throw new RuntimeException("Error creating task: invalid repeat type");
    }

    @JsonCreator
    IncompleteTask(
            @JsonProperty("name") String name,
            @JsonProperty("description") String description,
            @JsonProperty("assignedUsers") ArrayList<String> assignedUsers,
            @JsonProperty("dueDate") LocalDate dueDate,
            @JsonProperty("dueTime") LocalTime dueTime,
            @JsonProperty("repeatType") String repeatType,
            @JsonProperty("id") int id
    ) {
        this(name, description, assignedUsers, dueDate, dueTime, repeatType);
        setId(id);
    }

    @Override
    public String getDateAndTimeText() {
        return String.format("<p>Due %s at %s</p>\n",
                getDueDate().format(DateTimeFormatter.ofPattern("M-d-yyyy")),
                getDueTime().format(DateTimeFormatter.ofPattern("h:mm a")));
    }

    public ArrayList<String> getAssignedUsers() { return new ArrayList<>(assignedUsers); }

    public void setAssignedUsers(ArrayList<String> assignedUsers) { this.assignedUsers = new ArrayList<>(assignedUsers); }

    public LocalDate getDueDate() { return dueDate; }

    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public LocalTime getDueTime() { return dueTime; }

    public void setDueTime(LocalTime dueTime) { this.dueTime = dueTime; }

    public String getRepeatType() { return repeatType; }

    @Override @NonNull
    public String toString() {
        return String.format("<h3>%s</h3>" +
                        "<b><i>%s</i></b>" +
                        "<p>Due %s at %s</p>\n",
                getName(), getDescription(),
                getDueDate().format(DateTimeFormatter.ofPattern("M-d-yyyy")),
                getDueTime().format(DateTimeFormatter.ofPattern("h:mm a")));
    }

    public void create(int houseId) throws RuntimeException {
        if (getId() == -1) { //Prevents duplicate storage. Id is updated after first storage.
            try {
                String script = "createTask1.php";
                String data = HTTPSDataSender.mapToJson(this) + "\n" + houseId;
                String[] responseLines = HTTPSDataSender.initiateTransaction(script, data);

                if (responseLines.length < 1 || responseLines[0].equals("CONNECT_ERROR"))
                    throw new RuntimeException();
                else
                    super.setId(Integer.parseInt(responseLines[0]));

            } catch (Exception e) {
                throw new RuntimeException("Error communicating with server");
            }
        }
    }

    public static void complete(int taskId, int userId, int houseId) throws RuntimeException {
        try {
            if (userId == -1 || houseId == -1) {  throw new RuntimeException(); }

            String script = "getTaskById.php";
            String data = String.valueOf(taskId);
            String[] responseLines = HTTPSDataSender.initiateTransaction(script, data);

            if (responseLines.length < 1 || responseLines[0].equals("CONNECT_ERROR"))
                throw new RuntimeException();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.findAndRegisterModules();
            IncompleteTask task = objectMapper.readValue(responseLines[0], IncompleteTask.class);

            String newDueDate;
            switch (task.getRepeatType()) {
                case ("DAILY"):
                    newDueDate = task.getDueDate().plusDays(1).toString();
                    break;
                case ("WEEKLY"):
                    newDueDate = task.getDueDate().plusWeeks(1).toString();
                    break;
                case ("MONTHLY"):
                    newDueDate = task.getDueDate().plusMonths(1).toString();
                    break;
                default:
                    newDueDate = "NONE";
            }

            script = "completeTask2.php";
            data = "{\"taskId\":" + taskId + ",\"userId\":" + userId + ",\"houseId\":" + houseId + ",\"newDueDate\":\"" + newDueDate + "\"}";
            HTTPSDataSender.initiateTransaction(script, data);

        } catch (Exception e) {
            throw new RuntimeException("Error communicating with server");
        }
    }

    public static int delete(int taskId) throws RuntimeException {
        try {
            String script = "deleteTask1.php";
            String data = String.valueOf(taskId);
            String[] responseLines = HTTPSDataSender.initiateTransaction(script, data);

            if (responseLines[0].equals("1")) { return 1; }
            else { return 0; }
        }
        catch (Exception e) {
            throw new RuntimeException("Error communicating with server");
        }
    }

    public void update() throws RuntimeException{
        try {
            String script = "updateTask1.php";
            String data = HTTPSDataSender.mapToJson(this);
            String[] responseLines = HTTPSDataSender.initiateTransaction(script,data);

            if (responseLines.length < 1 || responseLines[0].equals("CONNECT_ERROR"))
                throw new RuntimeException();
        }
        catch (Exception e) {
            throw new RuntimeException("Error communicating with server");
        }
    }

    public static ArrayList<Task> loadHouseholdTasks (int houseId) {
        try {
            String script = "loadIncompleteTasks.php";
            String data = String.valueOf(houseId);
            String[] responseLines = HTTPSDataSender.initiateTransaction(script, data);
            ArrayList<IncompleteTask> tasks = new ArrayList<>(responseLines.length);

            if (responseLines.length > 0) {
                if (responseLines[0].equals("CONNECT_ERROR"))
                    throw new RuntimeException();
                else {
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.findAndRegisterModules();
                    for (String response : responseLines)
                        tasks.add(objectMapper.readValue(response, IncompleteTask.class));
                }
                sortByEarliestDeadline(tasks);
            }
            return new ArrayList<>(tasks);

        } catch (Exception e) {
            throw new RuntimeException("Error communicating with server");
        }
    }

    private static void sortByEarliestDeadline(ArrayList<IncompleteTask> tasks) {
        Comparator<IncompleteTask> soonestDeadlineComparator = (task1, task2) -> {
            String task1DateTime = task1.getDueDate().toString() + task1.getDueTime().toString();
            String task2DateTime = task2.getDueDate().toString() + task2.getDueTime().toString();
            return (task1DateTime.compareTo(task2DateTime));
        };
        tasks.sort(soonestDeadlineComparator);
    }
}
