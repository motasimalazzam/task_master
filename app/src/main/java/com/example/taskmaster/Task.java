package com.example.taskmaster;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Task {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "taskImage")
    private  int image;
    @ColumnInfo(name = "taskTitle")
    private final String title;
    @ColumnInfo(name = "taskBody")
    private final String body;
    @ColumnInfo(name = "taskSate")
    private final String state ;


    public Task(String title, String body, String state) {
        this.title = title;
        this.body = body;
        this.state = state;
    }

    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getState() {
        return state;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
