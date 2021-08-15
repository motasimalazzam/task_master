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
        ((TextView) findViewById(R.id.textView7)).setText(intent.getExtras().getString("title"));

        String title = intent.getExtras().getString(TasksList.TASK_TITLE);
        TextView titleTextView = findViewById(R.id.textView7);
        titleTextView.setText(title);
        String body = intent.getExtras().getString(TasksList.TASK_BODY);
        TextView bodyTextView = findViewById(R.id.textView8);
        bodyTextView.setText(body);
        String state = intent.getExtras().getString(TasksList.TASK_STATE);
        TextView nameTextView = findViewById(R.id.textView10);
        nameTextView.setText(state);
    }


}