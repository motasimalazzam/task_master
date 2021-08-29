package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle("Settings Page");

        Spinner teamsList = findViewById(R.id.spinner_settings);
        String[] teams = new String[]{"","201-course", "301-course", "401-course"};
        ArrayAdapter<String> TeamsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, teams);
        teamsList.setAdapter(TeamsAdapter);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor preferenceEditor = sharedPreferences.edit();


        // SAVE BUTTON
        findViewById(R.id.button8).setOnClickListener(view -> {
            String username = ((EditText) findViewById(R.id.editTextTextPersonName3)).getText().toString();


            Spinner teamSpinner = (Spinner) findViewById(R.id.spinner_settings);
            String teamName = teamSpinner.getSelectedItem().toString();

            preferenceEditor.putString("userName", username);
            preferenceEditor.putString("teamName", teamName);
            preferenceEditor.apply();

            Toast toast = Toast.makeText(this, "Saved!", Toast.LENGTH_LONG);
            toast.show();

            Intent mainIntent = new Intent(Settings.this, MainActivity.class);
            startActivity(mainIntent);
        });

    }

}