package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                Intent showAllTasks=new Intent(MainActivity.this,AllTasksActivity.class);
                startActivity(showAllTasks);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.Add_Task) {
            Intent addTaskPage=new Intent(MainActivity.this,AddTaskActivity.class);
            startActivity(addTaskPage);
            return true;
        }

        if (id == R.id.allMenu) {
            Intent allTaskPage=new Intent(MainActivity.this,AllTasksActivity.class);
            startActivity(allTaskPage);
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }

}