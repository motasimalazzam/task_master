package com.example.taskmaster;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Task.class}, version = 1)
public abstract class TaskDataBase extends RoomDatabase {
    public abstract TaskDao taskDao();
}
