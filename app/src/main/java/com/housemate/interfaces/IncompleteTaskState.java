package com.housemate.interfaces;

import com.housemate.classes.IncompleteTask;

public interface IncompleteTaskState {
    boolean create(int houseId, IncompleteTask task);
}
