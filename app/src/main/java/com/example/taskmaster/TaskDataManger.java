package com.example.taskmaster;

import java.util.ArrayList;
import java.util.List;

public class TaskDataManger {
    private static TaskDataManger instance = null;
    private List<Task> taskItems = new ArrayList<>();

    private TaskDataManger() {
    }

    public static TaskDataManger getInstance() {
        if (instance == null) {
            instance = new TaskDataManger();
        }

        return instance;
    }

    public void setData(List<Task> data) {
        taskItems = data;
    }

    public List<Task> getData() {
        return taskItems;
    }
}
