package com.example.quanlysinhvien;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText edtName, edtEmaiil;
    Button btnAdd, btnUpdate, btnDelete;
    ListView lvStudent;
    List<Student> listStudent;
    FirebaseDatabaseHelper firebaseDatahelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //bât khả năng lưu trữ cục bộ (trên thiết bị) của firebasedatabase khi không có kết nối mạng
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtName = (EditText) findViewById(R.id.edtName);
        edtEmaiil = (EditText) findViewById(R.id.edtEmail);
        btnAdd = (Button) findViewById(R.id.buttonAdd);
        btnUpdate = (Button) findViewById(R.id.buttonUpdate);
        btnDelete = (Button) findViewById(R.id.buttonDelete);
        lvStudent = (ListView) findViewById(R.id.lvStudents);
        firebaseDatahelper = new FirebaseDatabaseHelper();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStudent();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStudent();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteStudent();
            }
        });

        loadStudent();
    }

    private void addStudent(){
        String name = edtName.getText().toString();
        String email = edtEmaiil.getText().toString();
        //tao 1 id = key cua node trong firebase
        String id = firebaseDatahelper.getRef().push().getKey();
        Student student = new Student(id, name, email);
        firebaseDatahelper.addStudent(student);
    }
    private void updateStudent(){
        String id = "0";
        String name = edtName.getText().toString();
        String email = edtEmaiil.getText().toString();
        Student student = new Student(id, name, email);
        firebaseDatahelper.updateStudent(id, student);
    }

    private void deleteStudent(){
        String id = "0";
        firebaseDatahelper.deleteStudent(id);
    }

    private void loadStudent(){
        firebaseDatahelper.getRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listStudent.clear();
                for(DataSnapshot studentSnapshot : snapshot.getChildren()){
                    Student student = studentSnapshot.getValue(Student.class);
                    listStudent.add(student);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}