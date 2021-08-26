package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;

import java.util.HashMap;

public class AddTaskActivity extends AppCompatActivity {

    public static final String TASK_ITEM = "task-item";
    private static final String TAG = "AddTask";

    private TaskDao taskDao;
    private TaskDataBase database;
    private int taskItemImage;

    private static final HashMap<String, Integer> imageIconDatabase = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        getActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        Button addNewTask =findViewById(R.id.button2);
//        addNewTask.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast buttonToast= Toast.makeText(AddTaskActivity.this,"submitted!",Toast.LENGTH_SHORT);
//                buttonToast.show();
//            }
//        });

        imageIconDatabase.put("In Progress", R.drawable.ic_clipboard);
        imageIconDatabase.put("New Task", R.drawable.ic_resource_new);
        imageIconDatabase.put("Done", R.drawable.ic_thumbs_up);

        database = Room.databaseBuilder(getApplicationContext(), TaskDataBase.class, TASK_ITEM)
                .allowMainThreadQueries().build();

        taskDao = database.taskDao();
        Spinner spinner = findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tasks, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String text = (String) adapterView.getItemAtPosition(position);
                taskItemImage = imageIconDatabase.get(text);
                Log.i(TAG, "onItemSelected: " + text);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button addButton = findViewById(R.id.button2);
        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                EditText inputTitle = findViewById(R.id.editTextTextPersonName2);
                EditText inputBody = findViewById(R.id.editTextTextPersonName3);
                EditText inputState = findViewById(R.id.editTextTextPersonName);

                String title = inputTitle.getText().toString();
                String body = inputBody.getText().toString();
                String state = inputState.getText().toString();

//                create Task Item

                com.amplifyframework.datastore.generated.model.Task taskItem = com.amplifyframework.datastore.generated.model.Task.builder()
                        .title(title)
                        .description(body)
                        .status(state)
                        .build();

                if(isNetworkAvailable(getApplicationContext())){
                    Log.i(TAG, "onClick: the network is available");
                }else{
                    Log.i(TAG, "onClick: net down");
                }

                saveTaskToAPI(taskItem);
                TaskDataManger.getInstance().getData().add(new Task(taskItem.getTitle() , taskItem.getDescription(),taskItem.getStatus()));
                Toast.makeText(AddTaskActivity.this, "Task saved", Toast.LENGTH_SHORT).show();

                Task task = new Task(title, body, state);
                task.setImage(taskItemImage);
                taskDao.insertOne(task);
//                Toast.makeText(AddTaskActivity.this, "Item added", Toast.LENGTH_SHORT).show();

//                Intent mainIntent = new Intent(AddTaskActivity.this, MainActivity.class);
//                startActivity(mainIntent);

                Intent addTaskPage = new Intent(AddTaskActivity.this, TasksList.class);
                startActivity(addTaskPage);
            }
        });
    }

    public com.amplifyframework.datastore.generated.model.Task saveTaskToAPI(com.amplifyframework.datastore.generated.model.Task taskItem) {
        Amplify.API.mutate(ModelMutation.create(taskItem),
                success -> Log.i(TAG, "Saved item: " + taskItem.getTitle()),
                error -> Log.e(TAG, "Could not save item to API/dynamodb" + taskItem.getTitle()));
        return taskItem;

    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager =
                ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager
                .getActiveNetworkInfo().isConnected();
    }
}