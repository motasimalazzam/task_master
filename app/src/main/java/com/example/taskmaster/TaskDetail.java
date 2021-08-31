package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.core.Amplify;
import com.bumptech.glide.Glide;

import java.io.File;
import java.net.URL;

public class TaskDetail extends AppCompatActivity {

    private static final String TAG = "TaskDetail";
    private URL url =null;
    private Handler handler;

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

//        lab 37

        String fileName = intent.getExtras().getString("taskFile");

        ImageView imageView = findViewById(R.id.task_img);

        handler = new Handler(Looper.getMainLooper(),
                message -> {
                    Glide.with(getBaseContext())
                            .load(url.toString())
                            .placeholder(R.drawable.ic_clipboard)
                            .error(R.drawable.ic_clipboard)
                            .centerCrop()
                            .into(imageView);
                    return true;
                });

        getFileFromS3Storage(fileName);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String fileLink = String.format("<a href=\"%s\">download File</a> ", url);

        TextView link = findViewById(R.id.task_link);
        link.setText(Html.fromHtml(fileLink));
        link.setMovementMethod(LinkMovementMethod.getInstance());

    }

    private void getFileFromS3Storage(String key) {
        Amplify.Storage.downloadFile(
                key,
                new File(getApplicationContext().getFilesDir() + key),
                result -> {
                    Log.i(TAG, "Successfully downloaded: " + result.getFile().getAbsoluteFile());
                },
                error -> Log.e(TAG,  "Download Failure", error)
        );

        Amplify.Storage.getUrl(
                key,
                result -> {
                    Log.i(TAG, "Successfully generated: " + result.getUrl());
                    url= result.getUrl();
                    handler.sendEmptyMessage(1);
                },
                error -> Log.e(TAG, "URL generation failure", error)
        );
    }


}