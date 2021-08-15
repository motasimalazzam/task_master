package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void saveName(View view) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);// getter
        SharedPreferences.Editor preferenceEditor = preferences.edit();
        EditText address = findViewById(R.id.editTextTextPersonName3);
        preferenceEditor.putString("name", address.getText().toString());
        preferenceEditor.apply();

        Toast toast = Toast.makeText(this, "Name Saved!", Toast.LENGTH_LONG);
        toast.show();
    }
}