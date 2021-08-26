package com.example.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TasksList extends AppCompatActivity {

    public static final String TASK_TITLE = "task_title";
    public static final String TASK_BODY = "task_body";
    public static final String TASK_STATE = "task_state";
    private List<Task> tasksList;
    private static final String TAG = "Task";
    private TaskAdapter adapter;
    private TaskDao taskDao;
    private TaskDataBase db;
//    public List<Task> getTasksList() {
//
//        return tasksList;
//    }

    private Handler handler;
    private RecyclerView TaskRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_list);
//        RecyclerView TaskRecyclerView = findViewById(R.id.list_item);
        TaskRecyclerView = findViewById(R.id.list_item);

        handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public boolean handleMessage(@NonNull Message message) {
//                        TaskRecyclerView.getAdapter().notifyDataSetChanged();
                Objects.requireNonNull(TaskRecyclerView.getAdapter()).notifyDataSetChanged();
                return false;
            }
        });


//        Task task1 = new Task("Task 1", "Review German language", "in progress");
//        Task task2 = new Task("Task 2", "Review React js", "assigned");
//        Task task3 = new Task("Task 3", "edit on LinkedIn", "complete");
//        Task task4 = new Task("Task 4", "learned a new programing language", "new");
//
//
//        tasksList = new ArrayList<>();
//        tasksList.add(task1);
//        tasksList.add(task2);
//        tasksList.add(task3);
//        tasksList.add(task4);

//        db = Room.databaseBuilder(getApplicationContext(),
//                TaskDataBase.class, AddTaskActivity.TASK_ITEM).allowMainThreadQueries().build();
//
//        taskDao = db.taskDao();
//        tasksList = taskDao.findAll();

        tasksList = new ArrayList<>();
        getTaskDataFromAPI();
        adapter = new TaskAdapter(tasksList, new TaskAdapter.OnTaskItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                Intent goToDetailsIntent = new Intent(getApplicationContext(), TaskDetail.class);
                goToDetailsIntent.putExtra(TASK_TITLE, tasksList.get(position).getTitle());
                goToDetailsIntent.putExtra(TASK_BODY, tasksList.get(position).getBody());
                goToDetailsIntent.putExtra(TASK_STATE, tasksList.get(position).getState());
                startActivity(goToDetailsIntent);

            }
            @Override
            public void onDeleteItem(int position) {
                taskDao.delete(tasksList.get(position));
                tasksList.remove(position);
//                notifyDatasetChanged();
                Toast.makeText(TasksList.this, "Item deleted", Toast.LENGTH_SHORT).show();
            }


        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false);

        TaskRecyclerView.setLayoutManager(linearLayoutManager);
        TaskRecyclerView.setAdapter(adapter);
    }
//    private void notifyDatasetChanged() {
//        adapter.notifyDataSetChanged();
//    }
    @SuppressLint("NotifyDataSetChanged")
    private void  notifyDataSetChanged() {
        adapter.notifyDataSetChanged();
    }

    private void getTaskDataFromAPI() {
        List<Task> taskItemLists = new ArrayList<>();
        Amplify.API.query(ModelQuery.list(com.amplifyframework.datastore.generated.model.Task.class),
                response -> {
                    for (com.amplifyframework.datastore.generated.model.Task task : response.getData()) {
                        tasksList.add(new Task(task.getTitle(), task.getDescription(), task.getStatus()));
                        Log.i(TAG, "onCreate: the tasks are => " + task.getTitle());
                    }
                    handler.sendEmptyMessage(1);
                },
                error -> {
                    Log.e(TAG, "onCreate: Failed to get tasks => " + error.toString());
                    tasksList = showTasksSavedInDataBase();
                    handler.sendEmptyMessage(1);
                });
    }

    private List<Task> showTasksSavedInDataBase() {
        TaskDataBase taskDatabase = Room.databaseBuilder(this, TaskDataBase.class, "tasks")
                .allowMainThreadQueries().build();
        TaskDao taskDao = taskDatabase.taskDao();
        return taskDao.findAll();

    }
}