package com.example.myfundamentalsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private static final String SHARED_PREFS = "prefs";
    private static final String SHARED_PREFS_REGISTERED = "alreadyRegistered";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);

        Intent incomingIntent = getIntent();
        String name = incomingIntent.getStringExtra("name");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(SHARED_PREFS_REGISTERED,true);
        editor.commit();

        boolean alreadyRegistred = sharedPreferences.getBoolean(SHARED_PREFS_REGISTERED,false);
        if (!alreadyRegistred) {
            Intent intent = new Intent(MainActivity.this,RegistrationActivity.class);
            startActivity(intent);
        }
    }
}