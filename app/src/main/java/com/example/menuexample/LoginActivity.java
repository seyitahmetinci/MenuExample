package com.example.menuexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText editTextUsername, editTextPassword;
    Button buttonLogin, buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        MyDB db = new MyDB(this);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);

        buttonLogin = findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please fill a fields", Toast.LENGTH_SHORT).show();
                } else {
                    Cursor cursor=db.getTeacher();
                    Teacher teacher = null;
                    if (cursor.moveToFirst()){
                        do {
                            if (cursor.getString(1).equals(username)) {
                                teacher = new Teacher(
                                        cursor.getString(1),
                                        cursor.getString(2));
                            }
                        } while (cursor.moveToNext());
                    }
                    if (teacher != null) {
                        if (teacher.getPassword().equals(password)) {
                            Intent intent = new Intent(LoginActivity.this, StudentForm.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "Password is invalid", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Teacher is not found", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        buttonRegister = findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please fill a fields", Toast.LENGTH_SHORT).show();
                } else {
                    Teacher teacher = new Teacher(username, password);
                    db.addTeacher(teacher);
                    Toast.makeText(LoginActivity.this, "Teacher Created", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}