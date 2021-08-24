package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TASK_TITLE = "task_title";
    public static final String TASK_BODY = "task_body";
    public static final String TASK_STATE = "task_state";
    private List<Task> tasksList;
    private TaskAdapter adapter;
    private TaskDao taskDao;
    private TaskDataBase db;

    public List<Task> getTasksList() {
        return tasksList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView TaskRecyclerView = findViewById(R.id.list);


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

        db = Room.databaseBuilder(getApplicationContext(),
                TaskDataBase.class, AddTaskActivity.TASK_ITEM).allowMainThreadQueries().build();

        taskDao = db.taskDao();
        tasksList = taskDao.findAll();

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
                notifyDatasetChanged();
                Toast.makeText(MainActivity.this, "Item deleted", Toast.LENGTH_SHORT).show();
            }


        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false);

        TaskRecyclerView.setLayoutManager(linearLayoutManager);
        TaskRecyclerView.setAdapter(adapter);

        Button addTaskButton =findViewById(R.id.button);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addTaskPage=new Intent(MainActivity.this,AddTaskActivity.class);
                startActivity(addTaskPage);
            }
        });

        Button showAllTasks= findViewById(R.id.button3);
        showAllTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showAllTasks=new Intent(MainActivity.this,TasksList.class);
                startActivity(showAllTasks);
            }
        });

    }
    private void notifyDatasetChanged() {
        adapter.notifyDataSetChanged();
    }

    public void getTask1(View view) {
        Intent taskDetail = new Intent(this,TaskDetail.class);
        taskDetail.putExtra("title", "Task1");
        startActivity(taskDetail);
    }

    public void getTask2(View view) {
        Intent taskDetail = new Intent(this,TaskDetail.class);
        taskDetail.putExtra("title", "Task2");
        startActivity(taskDetail);
    }

    public void getTask3(View view) {
        Intent taskDetail = new Intent(this,TaskDetail.class);
        taskDetail.putExtra("title", "Task3");
        startActivity(taskDetail);
    }

    public void getSettings(View view) {
        Intent settings = new Intent(this, Settings.class);
        MainActivity.this.startActivity(settings);
    }

    @Override
    public void onResume() {

        super.onResume();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        TextView address = findViewById(R.id.textView);

        address.setText(preferences.getString("name", "") + "'s Task");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.Add_Task) {
            Intent addTaskPage=new Intent(MainActivity.this,AddTaskActivity.class);
            startActivity(addTaskPage);
            return true;
        }

        if (id == R.id.allMenu) {
            Intent allTaskPage=new Intent(MainActivity.this,TasksList.class);
            startActivity(allTaskPage);
            return true;
        }

        if (id == R.id.settings) {
            Intent settingsPage=new Intent(MainActivity.this,Settings.class);
            startActivity(settingsPage);
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }

}