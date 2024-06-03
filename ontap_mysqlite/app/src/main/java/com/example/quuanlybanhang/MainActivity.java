package com.example.quuanlybanhang;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText edt_username, edt_password, edt_confirmpassword, edt_email;
    Button btn_signup;
    SQLiteDatabase db;
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

        edt_username = (EditText) findViewById(R.id.edt_username);
        edt_password = (EditText) findViewById(R.id.edt_pass);
        edt_confirmpassword = (EditText) findViewById(R.id.edt_confirm);
        edt_email = (EditText) findViewById(R.id.edt_email);
        btn_signup = (Button) findViewById(R.id.btn_signup);

        db = openOrCreateDatabase("user.db", MODE_PRIVATE, null);
        try{
            String sql = "CREATE TABLE user_tb (username TEXT , password TEXT, email TEXT)";
            db.execSQL(sql);
        }catch (Exception e){
            Log.e("Error", e.getMessage());
        }

        edt_username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(edt_username.getText().toString().isEmpty()){
                        showToast("Username is required");
                    }
                }
            }
        });

        edt_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(edt_password.getText().toString().isEmpty()){
                        showToast("Password is required");
                    }
                }
            }
        });

        edt_confirmpassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(edt_confirmpassword.getText().toString().isEmpty()){
                        showToast("Confirm Password is required");
                    }
                    else if(!edt_confirmpassword.getText().toString().equals(edt_password.getText().toString())){
                        showToast("Passwords do not match");
                    }
                }
            }
        });

        edt_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(edt_email.getText().toString().isEmpty()){
                        showToast("Email is required");
                    }
                    else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(edt_email.getText().toString()).matches()){
                        showToast("Email is not valid");
                    }
                }
            }
        });
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edt_username.getText().toString();
                String password = edt_password.getText().toString();
                String email = edt_email.getText().toString();
                ContentValues contentValues = new ContentValues();
                contentValues.put("username", username);
                contentValues.put("password", password);
                contentValues.put("email", email);
                if(db.insert("user_tb", null, contentValues) == -1){
                    showToast("User already exists");
                    return;
                }
                else{
                    showToast("User Registered");
                    Intent intent = new Intent(MainActivity.this, Login.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
    }

    public void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}