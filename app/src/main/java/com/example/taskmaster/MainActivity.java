package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.Team;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private List<com.amplifyframework.datastore.generated.model.Task> tasks;
    private TaskAdapter adapter;

    private TaskDao taskDao;

    private Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        handler = new Handler(message -> {
            notifyDataSetChanged();
            return false;
        });

//        configureAmplify();


        TaskDataBase database = Room.databaseBuilder(getApplicationContext(), TaskDataBase.class, "task_List")
                .allowMainThreadQueries().build();
        taskDao = database.taskDao();


        // ADD TASK BUTTON
        findViewById(R.id.button).setOnClickListener(view -> {
            Intent addTaskButton = new Intent(MainActivity.this, AddTaskActivity.class);
            startActivity(addTaskButton);
        });

        // ALL TASK BUTTON
        findViewById(R.id.button3).setOnClickListener(view -> {
            Intent allTaskButton = new Intent(MainActivity.this, TasksList.class);
            startActivity(allTaskButton);
        });

        // SETTINGS BUTTON
        findViewById(R.id.button7).setOnClickListener(view -> {
            Intent settingsButton = new Intent(MainActivity.this, Settings.class);
            startActivity(settingsButton);
        });

        saveTeamToApi("201-course");
        saveTeamToApi("301-course");
        saveTeamToApi("401-course");

    }


      //    METHODS

    private void listItemDeleted() {
        adapter.notifyDataSetChanged();
    }


    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String username = sharedPreferences.getString("userName", "");
        String teamName = sharedPreferences.getString("teamName", "");

        CharSequence myUserTitle = sharedPreferences.getString("userNameAPI", "");
        setTitle(myUserTitle + " Profile");

        if (!username.equals("")) {
            ((TextView) findViewById(R.id.textView)).setText(username + "'s Tasks");
        }

        tasks = new ArrayList<>();
        if (teamName.equals("")) {
            getTasksDataFromCloud();
        } else {
            ((TextView) findViewById(R.id.teamName)).setText(teamName + " Tasks");
            getTeamTasksFromCloud(teamName);
        }

        Log.i(TAG, "onResume: tasks " + tasks);


        RecyclerView taskRecyclerView = findViewById(R.id.list);
        adapter = new TaskAdapter(tasks, new TaskAdapter.OnTaskItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                Intent goToDetailsIntent = new Intent(getApplicationContext(), TaskDetail.class);
                goToDetailsIntent.putExtra("taskTitle", tasks.get(position).getTitle());
                goToDetailsIntent.putExtra("taskBody", tasks.get(position).getBody());
                goToDetailsIntent.putExtra("taskState", tasks.get(position).getState());
                goToDetailsIntent.putExtra("taskFile", tasks.get(position).getFileName());

                startActivity(goToDetailsIntent);
            }

            @Override
            public void onDeleteItem(int position) {

//                taskDao.delete(tasks.get(position));
//                tasks.remove(position);
//                listItemDeleted();

            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false);
        taskRecyclerView.setLayoutManager(linearLayoutManager);
        taskRecyclerView.setAdapter(adapter);
    }


    private void configureAmplify() {
        // configure Amplify plugins
        try {
            Amplify.addPlugin(new AWSDataStorePlugin());
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.configure(getApplicationContext());
            Log.i(TAG, "Successfully initialized Amplify plugins");
        } catch (AmplifyException exception) {
            Log.e(TAG, "Failed to initialize Amplify plugins: " + exception.toString());
        }
    }

    private void getTasksDataFromCloud() {
        Amplify.API.query(ModelQuery.list(com.amplifyframework.datastore.generated.model.Task.class),
                response -> {
                    for (com.amplifyframework.datastore.generated.model.Task task : response.getData()) {
                        tasks.add(task);
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


    public void saveTeamToApi(String teamName) {
        Team team = Team.builder().teamName(teamName).build();

        Amplify.API.query(ModelQuery.list(Team.class, Team.TEAM_NAME.contains(teamName)),
                response -> {
                    List<Team> teams = (List<Team>) response.getData().getItems();

                    if (teams.isEmpty()) {
                        Amplify.API.mutate(ModelMutation.create(team),
                                success -> Log.i(TAG, "Saved Team: " + team.getTeamName()),
                                error -> Log.e(TAG, "Could not save Team: ", error));
                    }
                },
                error -> Log.e(TAG, "Failed to get Team from Cloud: " + error.toString())
        );

    }

    private void getTeamTasksFromCloud(String teamName) {
        Amplify.API.query(ModelQuery.list(com.amplifyframework.datastore.generated.model.Task.class),
                response -> {
                    for (com.amplifyframework.datastore.generated.model.Task task : response.getData()) {

                        if ((task.getTeam().getTeamName()).equals(teamName)) {
                            tasks.add(task);
                            Log.i(TAG, "The Tasks From Cloud are: " + task.getTitle());
                            Log.i(TAG, "The Team From Cloud are: " + task.getTeam().getTeamName());
                        }
                    }
                    handler.sendEmptyMessage(1);
                },
                error -> Log.e(TAG, "Failed to get Tasks from Cloud: " + error.toString())
        );
    }

    //    MENU
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

            if(id == R.id.signout){
                Amplify.Auth.signOut(
                        () -> {
                            Log.i("AuthQuickstart", "Signed out successfully");
                            Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                            startActivity(intent);
                        },
                        error -> Log.e("AuthQuickstart", error.toString())
                );
            }

            return super.onOptionsItemSelected(item);
        }


}