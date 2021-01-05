package com.example.myfundamentalsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.INotificationSideChannel;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        final EditText editTextName = findViewById(R.id.name);
        final EditText editTextEmail = findViewById(R.id.email);
        final EditText editTextBirthDate = findViewById(R.id.birth_date);
        Button buttonSubmit = findViewById(R.id.submit);
        final CheckBox checkBox = findViewById(R.id.checkbox);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                String email = editTextEmail.getText().toString();
                String date = editTextBirthDate.getText().toString().trim();
                boolean t_c = checkBox.isChecked();
                boolean validDate = false;
                boolean validEmail = email.trim().matches(emailPattern);
                int day=0,month=0, year=0;
                if (date.length()==10) {
                    try {
                        day = Integer.parseInt(date.substring(0, 2));
                        month = Integer.parseInt(date.substring(3, 5));
                        year = Integer.parseInt(date.substring(6, 10));
                        validDate= true;
                    } catch (NumberFormatException e) {
                        Toast.makeText(RegistrationActivity.this, "Nu ati introdus data corect." +
                                "Trebuie să folosiți numere", Toast.LENGTH_LONG).show();
                    }
                }
                 if (email.isEmpty() || name.isEmpty() || date.isEmpty() || !t_c){
                    Toast.makeText(RegistrationActivity.this,"Nu ati completat unul din campuri",
                            Toast.LENGTH_LONG).show();
                } else if (name.length() < 2) {
                    Toast.makeText(RegistrationActivity.this,"Nume prea scurt.",
                            Toast.LENGTH_LONG).show();
                } else if (!validEmail){//if email is not valid
                    Toast.makeText(RegistrationActivity.this,"Mail invalid",
                            Toast.LENGTH_LONG).show();
                }else if (!validDate) {
                    Toast.makeText(RegistrationActivity.this, "Nu ati introdus data corect.",
                            Toast.LENGTH_LONG).show();
                     editTextBirthDate.getText().clear();
                } else {
                    Intent intent = new Intent(RegistrationActivity.this,MainActivity.class);
                    intent.putExtra("name",name);
                    intent.putExtra("email",email);
                    intent.putExtra("day_birth",day);
                    intent.putExtra("day_birth",month);
                    intent.putExtra("day_birth",year);
                    startActivity(intent);
                }
            }
        });
    }
}