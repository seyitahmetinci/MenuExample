package com.example.menuexample;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ContextFragment extends Fragment {

    View view;

    ListView listView;
    ArrayList<Student> students = new ArrayList<Student>();
    ArrayList<String> studentList = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    MyDB db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_context, container, false);

        db = new MyDB(getActivity());
        Cursor cursor = db.getAllStudent();
        if (cursor.moveToNext()) {
            do {
                String id = cursor.getString(0);
                String name = cursor.getString(1);
                String surname = cursor.getString(2);
                String department = cursor.getString(3);

                Student student = new Student(id, name, surname, department);
                students.add(student);
                studentList.add(student.toString());
            } while (cursor.moveToNext());
        }

        listView = view.findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, studentList);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater menuInflater = new MenuInflater(getContext());
        menuInflater.inflate(R.menu.context, menu);
        menu.setHeaderTitle("Operations");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int listPosition = info.position;
        switch (item.getItemId()) {
            case R.id.menuDelete:
                String studentID = students.get(listPosition).getId();
                db.delete(studentID);
                students.remove(listPosition);
                studentList.remove(listPosition);
                adapter.notifyDataSetChanged();
                break;
            case R.id.menuUpdate:
                Intent intent = new Intent(getActivity(), StudentForm.class);
                Student student = students.get(listPosition);
                intent.putExtra("Student", student);
                startActivity(intent);
                getActivity().finish();
                break;
        }
        return super.onContextItemSelected(item);
    }
}