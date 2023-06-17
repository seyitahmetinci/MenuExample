package com.example.menuexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

public class StudentForm extends AppCompatActivity {

    EditText et_number, et_name, et_surname;
    RadioGroup programRadioGroup;
    Button saveButton, listButton, updateButton;

    private String selectedProgram;
    private ArrayList<Student> studentList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_form);

        MyDB db = new MyDB(this);

        studentList = new ArrayList<Student>();
        saveButton = findViewById(R.id.save_button);
        listButton = findViewById(R.id.list_button);
        updateButton = findViewById(R.id.buttonUpdate);
        et_number = findViewById(R.id.et_number);
        et_name = findViewById(R.id.et_name);
        et_surname = findViewById(R.id.et_surname);
        programRadioGroup = findViewById(R.id.GroupButton);

        RadioButton cmpeButton = (RadioButton) findViewById(R.id.cmpeButton);
        RadioButton cmseButton = (RadioButton) findViewById(R.id.cmseButton);
        RadioButton blgmButton = (RadioButton) findViewById(R.id.blgmButton);

        Intent intent = getIntent();
        Student student = (Student) intent.getSerializableExtra("Student");
        if (student != null) {
            et_number.setText(student.getId());
            et_name.setText(student.getName());
            et_surname.setText(student.getSurname());

            /* Seyit don't use bro please ajsdhasda
            if (student.getDepartment() == "CMPE") {
                cmpeButton.setChecked(true);
            } else if (Objects.equals(student.getDepartment(), "CMSE")) {
                cmseButton.setChecked(true);
            } else if (student.getDepartment() == "BLGM") {
                blgmButton.setChecked(true);
            }
             */

            if (student.getDepartment().equals("CMPE")) {
                cmpeButton.setChecked(true);
            } else if (student.getDepartment().equals("CMSE")) {
                cmseButton.setChecked(true);
            } else {
                blgmButton.setChecked(true);
            }
        }

        programRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.cmpeButton) {
                    selectedProgram = "CMPE";
                } else if (checkedId == R.id.cmseButton) {
                    selectedProgram = "CMSE";
                } else if (checkedId == R.id.blgmButton) {
                    selectedProgram = "BLGM";
                }
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    String id = et_number.getText().toString();
                    String name = et_name.getText().toString();
                    String surname = et_surname.getText().toString();
                    String department = "";
                    if (cmpeButton.isChecked()) {
                        department = "CMPE";
                    } else if (cmseButton.isChecked()) {
                        department = "CMSE";
                    } else if (blgmButton.isChecked()) {
                        department = "BLGM";
                    }

                    Student student = new Student(id, name, surname, department);
                    db.addStudent(student);

                    et_name.setText("");
                    et_number.setText("");
                    et_surname.setText("");
                    programRadioGroup.clearCheck();

                    Toast.makeText(StudentForm.this, "Student Added to DB", Toast.LENGTH_SHORT).show();
                } catch (NumberFormatException e) {
                    Toast.makeText(StudentForm.this, "Fill the Fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentForm.this, MainActivity.class);
                startActivity(intent);
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                student.setId(et_number.getText().toString());
                student.setName(et_name.getText().toString());
                student.setSurname(et_surname.getText().toString());
                student.setDepartment(selectedProgram);

                db.updateStu(student);
            }
        });
    }
}