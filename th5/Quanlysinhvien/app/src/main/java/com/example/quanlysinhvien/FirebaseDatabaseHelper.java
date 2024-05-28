package com.example.quanlysinhvien;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseDatabaseHelper {
    private FirebaseDatabase mdatabase;
    private DatabaseReference mref;

    //constructor
    public FirebaseDatabaseHelper() {
        mdatabase = FirebaseDatabase.getInstance();
        mref = mdatabase.getReference("students");
    }

    public void addStudent(Student student){

        //get id node con push
        String id = mref.push().getKey();
        student.setId(id);
        //set value student vao node con
        mref.child(id).setValue(student);
    }

    public void updateStudent(String id, Student student){
        //set value student vao node con co id = id
        mref.child(id).setValue(student);
    }

    public void deleteStudent(String id){
        //xoa node con co id = id
        mref.child(id).removeValue();
    }


    public DatabaseReference getRef(){
        return mref;
    }

}
