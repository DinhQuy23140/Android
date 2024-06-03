package com.example.university;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.university.crud.addStudent;
import com.example.university.crud.editStudent;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_add, btn_edit, btn_delete, btn_view;
    ListView lv_student;
    ArrayAdapter<String> adapter;
    ArrayList<String> list_student;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize Firebase
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        btn_add = (Button) findViewById(R.id.btn_add);
        btn_edit = (Button) findViewById(R.id.btn_edit);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        btn_view = (Button) findViewById(R.id.btn_view);
        lv_student = (ListView) findViewById(R.id.lv_student);
        list_student = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list_student);
        lv_student.setAdapter(adapter);

        btn_add.setOnClickListener(this);
        btn_edit.setOnClickListener(this);  

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_add){
            Intent intent = new Intent(MainActivity.this, addStudent.class);
            startActivity(intent);
        }
        else if(v.getId() == R.id.btn_edit){
            Intent intent = new Intent(MainActivity.this, editStudent.class);
            startActivity(intent);
        }
        else if(v.getId() == R.id.btn_delete){

        }
        else if(v.getId() == R.id.btn_view){

        }
    }
}