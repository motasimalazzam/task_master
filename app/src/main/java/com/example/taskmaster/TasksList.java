package com.example.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;

import java.util.ArrayList;
import java.util.List;

public class TasksList extends AppCompatActivity {

    private static final String TAG = "TeamTasks";
    private List<Task> tasks;
    private TaskAdapter adapter;

    private RecyclerView taskRecyclerView;
    private LinearLayoutManager linearLayoutManager;


    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_list);
        setTitle("Team Tasks Page");


        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message message) {
                notifyDataSetChanged();
                return false;
            }
        });


    }

    private void listItemDeleted() {
        adapter.notifyDataSetChanged();
    }


    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String teamName = sharedPreferences.getString("teamName", "");

        tasks = new ArrayList<>();

        if (teamName.equals("")) {
            getTasksDataFromCloud();
        } else {
            ((TextView) findViewById(R.id.textView13)).setText(teamName + " Tasks");
            getTeamTasksFromCloud(teamName);        }

        for (Task task : tasks) {
            Log.i(TAG, "team tasks: " + task.getTitle());
        }


        taskRecyclerView = findViewById(R.id.list_item);
        adapter = new TaskAdapter(tasks, new TaskAdapter.OnTaskItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                Intent goToDetailsIntent = new Intent(getApplicationContext(), TaskDetail.class);
                goToDetailsIntent.putExtra("taskTitle", tasks.get(position).getTitle());
                goToDetailsIntent.putExtra("taskBody", tasks.get(position).getBody());
                goToDetailsIntent.putExtra("taskState", tasks.get(position).getState());
                startActivity(goToDetailsIntent);
            }

            @Override
            public void onDeleteItem(int position) {

                tasks.remove(position);
                listItemDeleted();

            }
        });

        linearLayoutManager = new LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false);
        taskRecyclerView.setLayoutManager(linearLayoutManager);
        taskRecyclerView.setAdapter(adapter);


    }


    //    METHODS
    private void getTeamTasksFromCloud(String teamName) {
        Amplify.API.query(ModelQuery.list(com.amplifyframework.datastore.generated.model.Task.class),
                response -> {
                    for (com.amplifyframework.datastore.generated.model.Task task : response.getData()) {

                        if ((task.getTeam().getTeamName()).equals(teamName)) {
                            tasks.add(new Task(task.getTitle(), task.getBody(), task.getState()));
                            Log.i(TAG, "The Tasks From Cloud are: " + task.getTitle());
                            Log.i(TAG, "The Team From Cloud are: " + task.getTeam().getTeamName());
                        }
                    }
                    handler.sendEmptyMessage(1);
                },
                error -> Log.e(TAG, "Failed to get Tasks from Cloud: " + error.toString())
        );
    }

    private void getTasksDataFromCloud() {
        Amplify.API.query(ModelQuery.list(com.amplifyframework.datastore.generated.model.Task.class),
                response -> {
                    for (com.amplifyframework.datastore.generated.model.Task task : response.getData()) {
                        tasks.add(new Task(task.getTitle(), task.getBody(), task.getState()));
                        Log.i(TAG, "The Tasks From Cloud are: " + task.getTitle());
                    }
                    handler.sendEmptyMessage(1);
                },
                error -> Log.e(TAG, "Failed to get Tasks from From Cloud: " + error.toString())
        );
    }

    @SuppressLint("NotifyDataSetChanged")
    private void notifyDataSetChanged() {
        adapter.notifyDataSetChanged();
    }
}