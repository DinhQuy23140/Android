package com.example.quanlysinhvien;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText edtName, edtEmaiil;
    Button btnAdd, btnUpdate, btnDelete;
    ListView lvStudent;
    ArrayList<Student> listStudent;
    StudentAdapter studentAdapter;
    FirebaseDatabaseHelper firebaseDatahelper;
    DatabaseReference databaseReference;

    String idStudent;


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
        listStudent = new ArrayList<>();
        Student student = new Student("id3", "name3", "email3");
        listStudent.add(student);
        studentAdapter = new StudentAdapter(MainActivity.this, listStudent);
        lvStudent.setAdapter(studentAdapter);
        firebaseDatahelper = new FirebaseDatabaseHelper();
        databaseReference = FirebaseDatabase.getInstance().getReference();
//
//        // Check connection
        checkFirebaseConnection();
        readFromDatabase();

        edtName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(edtName.getText().toString().isEmpty()){
                        Toast.makeText(MainActivity.this, "Name is required", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        edtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchStudent();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtEmaiil.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(edtEmaiil.getText().toString().isEmpty()){
                        Toast.makeText(MainActivity.this, "Email is required", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
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

        lvStudent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Student student1 = listStudent.get(position);
                idStudent = student1.getId();
                edtName.setText(student1.getName());
                edtEmaiil.setText(student1.getEmail());
            }
        });


        loadStudent();
    }
    private void checkFirebaseConnection() {
        databaseReference.child("testConnection").setValue("Hello, Firebase!")
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "Connected to Firebase Realtime Database", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Failed to connect to Firebase", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void readFromDatabase() {
        databaseReference.child("testConnection").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String value = task.getResult().getValue(String.class);
                Toast.makeText(MainActivity.this, "Read value: " + value, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Failed to read value", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void searchStudent(){
        String name = edtName.getText().toString();
        firebaseDatahelper.getRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listStudent.clear();
                for(DataSnapshot studentSnapshot : snapshot.getChildren()){
                    Student student = studentSnapshot.getValue(Student.class);
                    if (student.getName().toLowerCase().contains(name.toLowerCase())){
                        listStudent.add(student);
                    }
                }
                studentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
        String id = idStudent;
        String name = edtName.getText().toString();
        String email = edtEmaiil.getText().toString();
        Student student = new Student(id, name, email);
        firebaseDatahelper.updateStudent(id, student);
    }

    private void deleteStudent(){
        String id = idStudent;
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
                studentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}