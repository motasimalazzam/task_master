package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class TaskDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        Intent intent = getIntent();
        String taskName = intent.getExtras().getString("taskTitle");
        String taskBody = intent.getExtras().getString("taskBody");
        String taskState = intent.getExtras().getString("taskState");

        ((TextView)findViewById(R.id.textView7)).setText(taskName);
        ((TextView)findViewById(R.id.textView8)).setText(taskBody);
        ((TextView)findViewById(R.id.textView10)).setText(taskState);
    }


}