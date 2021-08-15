package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class TasksList extends AppCompatActivity {

    public static final String TASK_TITLE = "task_title";
    public static final String TASK_BODY = "task_body";
    public static final String TASK_STATE = "task_state";
    private List<Task> tasksList;
    private TaskAdapter adapter;

    public List<Task> getTasksList() {
        return tasksList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_list);
        RecyclerView TaskRecyclerView = findViewById(R.id.list_item);


        Task task1 = new Task("Task 1", "Review German language", "in progress");
        Task task2 = new Task("Task 2", "Review React js", "assigned");
        Task task3 = new Task("Task 3", "edit on LinkedIn", "complete");
        Task task4 = new Task("Task 4", "learned a new programing language", "new");


        tasksList = new ArrayList<>();
        tasksList.add(task1);
        tasksList.add(task2);
        tasksList.add(task3);
        tasksList.add(task4);

        adapter = new TaskAdapter(tasksList, new TaskAdapter.OnTaskItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                Intent goToDetailsIntent = new Intent(getApplicationContext(), TaskDetail.class);
                goToDetailsIntent.putExtra(TASK_TITLE, tasksList.get(position).getTitle());
                goToDetailsIntent.putExtra(TASK_BODY, tasksList.get(position).getBody());
                goToDetailsIntent.putExtra(TASK_STATE, tasksList.get(position).getState());
                startActivity(goToDetailsIntent);

            }


        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false);

        TaskRecyclerView.setLayoutManager(linearLayoutManager);
        TaskRecyclerView.setAdapter(adapter);
    }
}