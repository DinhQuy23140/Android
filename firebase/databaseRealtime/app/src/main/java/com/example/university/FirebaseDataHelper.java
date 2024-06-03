package com.example.university;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class FirebaseDataHelper {
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    public FirebaseDataHelper() {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("khoa/");
    }

    public void add_Student(Student student){
        String root = "khoa";
        String khoa = student.getTenkhoa();
        String lop = student.getTenlop();
        myRef = database.getReference( root+"/"+khoa+"/"+lop);
        String id = myRef.push().getKey();
        student.setId(id);
        myRef.child(id).setValue(student);
    }

    public void edit_Student(Student student, String id){
        String root = "khoa";
        String khoa = student.getTenkhoa();
        String lop = student.getTenlop();
        myRef = database.getReference( root+"/"+khoa+"/"+lop);
        myRef.child(id).setValue(student);
    }
    public void deleteStudent(String id) {
        myRef.child(id).removeValue();
    }


    public DatabaseReference getMyRef() {
        return myRef;
    }
}
