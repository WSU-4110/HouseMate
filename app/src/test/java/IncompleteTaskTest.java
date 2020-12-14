import com.housemate.classes.CompletedTask;
import com.housemate.classes.IncompleteTask;
import com.housemate.classes.Task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

public class IncompleteTaskTest {

    @Test
    public void testGetDateAndTimeText_oneTimeTask() {
        IncompleteTask testTask = getOneTimeTestTask();

        String expectedText = String.format("<p>Due %s at %s%s</p>\n", "1-2-2020", "9:10 AM", "");
        String actualText = testTask.getDateAndTimeText();

        assertEquals(expectedText, actualText);
    }

    @Test
    public void testGetDateAndTimeText_recurringTask() {
        IncompleteTask testTask = getRecurringTestTask();

        String expectedText = String.format("<p>Due %s at %s%s</p>\n", "1-2-2020", "9:10 AM", " (WEEKLY)");
        String actualText = testTask.getDateAndTimeText();

        assertEquals(expectedText, actualText);
    }

    @Test
    public void testCreateTask_once() {
        IncompleteTask testTask = getOneTimeTestTask();
        assertEquals(-1, testTask.getId());

        testTask.create(8);

        assertNotEquals(-1, testTask.getId());

        IncompleteTask.delete(testTask.getId());
    }

    @Test
    public void testCreateTask_twice() {
        IncompleteTask testTask = getRecurringTestTask();
        testTask.create(8);
        int idAfterCreate1 = testTask.getId();

        assertNotEquals(-1, idAfterCreate1);

        testTask.create(8);
        int idAfterCreate2 = testTask.getId();

        assertEquals(idAfterCreate1, idAfterCreate2);

        IncompleteTask.delete(idAfterCreate2);
    }

    @Test
    public void testCompleteTask_oneTimeTask() {
        ArrayList<Task> completedTasksOld = CompletedTask.loadHouseholdTasks(8);
        IncompleteTask testTask = getOneTimeTestTask();
        testTask.create(8);
        IncompleteTask.complete(testTask.getId(), 8, 8);
        ArrayList<Task> completedTasksNew = CompletedTask.loadHouseholdTasks(8);

        assertEquals(completedTasksOld.size() + 1, completedTasksNew.size());
    }

    @Test
    public void testCompleteTask_recurringTask() {
        ArrayList<Task> completedTasksOld = CompletedTask.loadHouseholdTasks(8);
        IncompleteTask testTask = getRecurringTestTask();
        testTask.create(8);
        IncompleteTask.complete(testTask.getId(), 8, 8);
        ArrayList<Task> completedTasksNew = CompletedTask.loadHouseholdTasks(8);

        assertEquals(completedTasksOld.size() + 1, completedTasksNew.size());
    }

    @Test
    public void testCompleteTask_invalidUserId() {
        assertThrows(RuntimeException.class, () -> {
            IncompleteTask testTask = getOneTimeTestTask();
            testTask.create(8);

            IncompleteTask.complete(1, -1, 8);
        });
    }

    @Test
    public void testCompleteTask_invalidHouseId() {
        assertThrows(RuntimeException.class, () -> {
            IncompleteTask testTask = getOneTimeTestTask();
            testTask.create(8);

            IncompleteTask.complete(-1, 1, 8);
        });
    }

    @Test
    public void testDelete_oneTimeTask() {
        ArrayList<Task> incompleteTasksOld = IncompleteTask.loadHouseholdTasks(8);
        IncompleteTask testTask = getOneTimeTestTask();
        testTask.create(8);
        int returnVal = IncompleteTask.delete(testTask.getId());
        ArrayList<Task> incompleteTasksNew = IncompleteTask.loadHouseholdTasks(8);

        assertEquals(1, returnVal);
        assertEquals(incompleteTasksOld.size(), incompleteTasksNew.size());
    }

    @Test
    public void testDelete_recurringTask() {
        ArrayList<Task> incompleteTasksOld = IncompleteTask.loadHouseholdTasks(8);
        IncompleteTask testTask = getRecurringTestTask();
        testTask.create(8);
        int returnVal = IncompleteTask.delete(testTask.getId());
        ArrayList<Task> incompleteTasksNew = IncompleteTask.loadHouseholdTasks(8);

        assertEquals(incompleteTasksOld.size(), incompleteTasksNew.size());
    }

    @Test
    public void testDelete_invalidTaskId() {
        ArrayList<Task> incompleteTasksOld = IncompleteTask.loadHouseholdTasks(8);
        IncompleteTask testTask = getOneTimeTestTask();
        testTask.create(8);
        int returnVal = IncompleteTask.delete(-1);
        ArrayList<Task> incompleteTasksNew = IncompleteTask.loadHouseholdTasks(8);

        assertEquals(incompleteTasksOld.size() + 1, incompleteTasksNew.size());

        IncompleteTask.delete(testTask.getId());
    }

    @Test
    public void testUpdate() {
        IncompleteTask testTask = getOneTimeTestTask();
        testTask.create(8);
        String newName = "new name";
        String newDescription = "new description";
        testTask.setName(newName);
        testTask.setDescription(newDescription);
        ArrayList<Task> incompleteTasks = IncompleteTask.loadHouseholdTasks(8);

        int index = 0;
        while (incompleteTasks.get(index).getId() != testTask.getId())
            index++;

        assertNotEquals(newName, incompleteTasks.get(index).getName());
        assertNotEquals(newDescription, incompleteTasks.get(index).getDescription());

        testTask.update();
        incompleteTasks = IncompleteTask.loadHouseholdTasks(8);
        index = 0;
        while (incompleteTasks.get(index).getId() != testTask.getId())
            index++;

        assertEquals(newName, incompleteTasks.get(index).getName());
        assertEquals(newDescription, incompleteTasks.get(index).getDescription());
    }

    @Test
    public void testLoadHouseholdTasks_loadOneTask() {
        deleteHouseholdTasks(100);
        IncompleteTask testTask = getOneTimeTestTask();
        testTask.create(100);

        ArrayList<Task> incompleteTasks = IncompleteTask.loadHouseholdTasks(100);

        assertEquals(1, incompleteTasks.size());
        assertEquals(testTask.getId(), incompleteTasks.get(0).getId());

        IncompleteTask.delete(testTask.getId());
    }

    @Test
    public void testLoadHouseholdTasks_sortedByDeadline() {
        deleteHouseholdTasks(100);

        IncompleteTask testTask1 = getOneTimeTestTask();
        IncompleteTask testTask2 = getOneTimeTestTask();
        IncompleteTask testTask3 = getRecurringTestTask();

        testTask1.setDueDate(LocalDate.of(2021, 1, 1));
        testTask2.setDueDate(LocalDate.of(2021,2, 2));
        testTask3.setDueDate(LocalDate.of(2021,3, 3));

        testTask1.create(100);
        testTask2.create(100);
        testTask3.create(100);

        ArrayList<Task> testTasks = new ArrayList<>(Arrays.asList(testTask1, testTask2, testTask3));
        ArrayList<Task> testTasksFromDatabase = IncompleteTask.loadHouseholdTasks(100);

        assertEquals(testTasks.size(), testTasksFromDatabase.size());

        for (int i = 0; i < testTasks.size(); i++)
            assertEquals(testTasks.get(i).getId(), testTasksFromDatabase.get(i).getId());
    }

    @Test
    public void testLoadHouseholdTasks_inEmptyHousehold() {
        deleteHouseholdTasks(100);

        ArrayList<Task> incompleteTasks = IncompleteTask.loadHouseholdTasks(100);

        assertEquals(0, incompleteTasks.size());
    }

    private IncompleteTask getOneTimeTestTask() {
        LocalDate dueDate = LocalDate.of(2020, 1, 2);
        LocalTime dueTime = LocalTime.of(9, 10);
        ArrayList<String> users = new ArrayList<>(Arrays.asList("Bob", "Joe", "Phil"));
        return new IncompleteTask("Wash dishes", "Clean countertops when done",
                users, dueDate, dueTime, "NEVER");
    }

    private IncompleteTask getRecurringTestTask() {
        LocalDate dueDate = LocalDate.of(2020, 1, 2);
        LocalTime dueTime = LocalTime.of(9, 10);
        ArrayList<String> users = new ArrayList<>(Arrays.asList("Bob", "Joe", "Phil"));
        return new IncompleteTask("Wash dishes", "Clean countertops when done",
                users, dueDate, dueTime, "WEEKLY");
    }

    private void deleteHouseholdTasks(int houseId) {
        ArrayList<Task> incompleteTasks = IncompleteTask.loadHouseholdTasks(houseId);

        for (Task task : incompleteTasks)
            IncompleteTask.delete(task.getId());
    }
}
