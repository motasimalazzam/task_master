package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Team;

import java.util.ArrayList;
import java.util.List;

public class AddTaskActivity extends AppCompatActivity {

    private static final String TAG = "AddTaskActivity";

    private TaskDao taskDao;

    private String teamId = "";

    private final List<Team> teams = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        setTitle("Add Task Page");
        getTeamsDataFromCloud();

        Spinner teamsList = findViewById(R.id.spinner);
        String[] teams = new String[]{"201-course", "301-course", "401-course"};
        ArrayAdapter<String> TeamsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, teams);
        teamsList.setAdapter(TeamsAdapter);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor preferenceEditor = sharedPreferences.edit();

        TaskDataBase database = Room.databaseBuilder(getApplicationContext(), TaskDataBase.class, "task_List")
                .allowMainThreadQueries().build();
        taskDao = database.taskDao();

        // ADD TASK BUTTON
        findViewById(R.id.button2).setOnClickListener(view -> {
            String taskTitle = ((EditText) findViewById(R.id.editTextTextPersonName2)).getText().toString();
            String taskBody = ((EditText) findViewById(R.id.editTextTextPersonName3)).getText().toString();
            String taskState = ((EditText) findViewById(R.id.editTextTextPersonName)).getText().toString();

            Spinner teamSpinner = (Spinner) findViewById(R.id.spinner);
            String teamName = teamSpinner.getSelectedItem().toString();

            preferenceEditor.putString("teamName", teamName);
            preferenceEditor.apply();

            Task newTask = new Task(taskTitle, taskBody, taskState);
            taskDao.insertOne(newTask);


            Log.i(TAG, "THE TEAM ID:  " + getTeamId(teamName));

            addTaskToCloud(taskTitle,
                    taskBody,
                    taskState,
                    new Team(getTeamId(teamName), teamName));

            Intent mainIntent = new Intent(AddTaskActivity.this, MainActivity.class);
            startActivity(mainIntent);

        });

    }

     //    METHODS

    public void addTaskToCloud(String taskTitle, String taskBody, String taskState, Team team) {
        com.amplifyframework.datastore.generated.model.Task task = com.amplifyframework.datastore.generated.model.Task.builder()
                .title(taskTitle)
                .body(taskBody)
                .state(taskState)
                .team(team)
                .build();

        Amplify.API.mutate(ModelMutation.create(task),
                success -> Log.i(TAG, "Saved item: " + task.getTitle()),
                error -> Log.e(TAG, "Could not save item to API", error));

        Toast toast = Toast.makeText(this, "Task added!", Toast.LENGTH_LONG);
        toast.show();
    }

    private void getTeamsDataFromCloud() {
        Amplify.API.query(ModelQuery.list(Team.class),
                response -> {
                    for (Team team : response.getData()) {
                        teams.add(team);
                        Log.i(TAG, "TEAM ID FROM CLOUD IS:  " + team.getTeamName() + "  " + team.getId());
                    }
                },
                error -> Log.e(TAG, "Failed to get TEAM ID FROM CLOUD: " + error.toString())
        );
    }


    public String getTeamId(String teamName) {
        for (Team team : teams) {
            if (team.getTeamName().equals(teamName)) {
                return team.getId();
            }
        }
        return "";
    }


}