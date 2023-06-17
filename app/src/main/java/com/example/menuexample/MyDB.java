package com.example.menuexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDB extends SQLiteOpenHelper {

    private  static final String DBName="myDatabase"; //File name
    private  static final String DBTableTeacher ="Teachers";
    private  static final String DBTableStudent ="Students";
    private  static final int DBversion=1;

    private  static final String TEACHER_ID="_id"; //Column I
    private  static final String TEACHER_NAME="name";//Column II
    private  static final String TEACHER_PASSWORD="password";//Column III

    private  static final String STUDENT_ID="_id"; //Column I
    private  static final String STUDENT_NAME="name";//Column II
    private  static final String STUDENT_SURNAME ="surname";//Column III
    private  static final String STUDENT_DEPARTMENT="department";//Column III


    private  static final String CREATE_TABLE1="CREATE TABLE " + DBTableTeacher + " (" + TEACHER_ID + " INTEGER PRIMARY KEY, " + TEACHER_NAME + " TEXT, " + TEACHER_PASSWORD + " TEXT) ;";
    private  static final String CREATE_TABLE2="CREATE TABLE " + DBTableStudent + " (" + STUDENT_ID + " INTEGER PRIMARY KEY, " + STUDENT_NAME + " TEXT, " + STUDENT_SURNAME + " TEXT, " + STUDENT_DEPARTMENT + " TEXT) ;";

    private  static final String DROP_TABLE1="DROP TABLE IF EXISTS " + DBTableTeacher;

    private  static final String DROP_TABLE2="DROP TABLE IF EXISTS " + DBTableStudent;

    private Context context;
    public MyDB(@Nullable Context context) {
        super(context, DBName, null, DBversion);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL(CREATE_TABLE1);
            db.execSQL(CREATE_TABLE2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        try {
            db.execSQL(DROP_TABLE1);
            db.execSQL(DROP_TABLE2);
            onCreate(db);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void addStudent(Student student){

        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues cv=new ContentValues();
        cv.put(STUDENT_ID, student.getId());
        cv.put(STUDENT_NAME,student.getName());
        cv.put(STUDENT_SURNAME,student.getSurname());
        cv.put(STUDENT_DEPARTMENT,student.getDepartment());

            try {
                db.insert(DBTableStudent, null, cv);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
    }

    void addTeacher(Teacher teacher){

        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues cv=new ContentValues();
        cv.put(TEACHER_NAME,teacher.getName());
        cv.put(TEACHER_PASSWORD,teacher.getPassword());

        try {
            db.insert(DBTableTeacher, null, cv);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    Cursor getAllStudent(){
        String readquery = "SELECT * FROM " + DBTableStudent;

        SQLiteDatabase db=this.getReadableDatabase();

        Cursor c=null;
        if (db!=null) {
            c = db.rawQuery(readquery, null);
        }

        return c;
    }

    Cursor getTeacher(){
        String readquery = "SELECT * FROM " + DBTableTeacher;

        SQLiteDatabase db= this.getReadableDatabase();

        Cursor c=null;
        if (db!=null) {
            c = db.rawQuery(readquery, null);
        }

        return c;
    }

    void delete(String id){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(DBTableStudent, STUDENT_ID + " = ?",new String[]{String.valueOf(id) });
        db.close();
    }

    // code to update the single contact
    void updateStu(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(STUDENT_ID, student.getId());
        values.put(STUDENT_NAME, student.getName());
        values.put(STUDENT_SURNAME, student.getSurname());
        values.put(STUDENT_DEPARTMENT, student.getDepartment());

        db.update(DBTableStudent, values, STUDENT_ID + "=?", new String[]{student.getId()});
        db.close();
    }
}