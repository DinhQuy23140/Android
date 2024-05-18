package com.example.bmi;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.badge.BadgeUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    EditText txt_name;
    EditText txt_c_cao;
    EditText txt_can_nang;
    EditText txt_bmi;
    EditText txt_result;
    Button btn_bmi;

    double bmi;
    String result;
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

        txt_name = (EditText) findViewById(R.id.txt_name);
        txt_c_cao = (EditText) findViewById(R.id.txt_c_cao);
        txt_can_nang = (EditText) findViewById(R.id.txt_can_nang);
        txt_bmi = (EditText) findViewById(R.id.txt_bmi);
        txt_result = (EditText) findViewById(R.id.txt_result);
        btn_bmi = (Button) findViewById(R.id.btn_bmi);

        btn_bmi.setOnClickListener(this);
    }

    boolean check_editext(int id, String text_edit){
        EditText edit = findViewById(id);
        if (edit.getText().toString() == ""){
            Toast.makeText(getApplicationContext(), "Vui long nhap: " + text_edit, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        double c_cao = Double.parseDouble("0" + txt_c_cao.getText().toString());
        double can_nang = Double.parseDouble("0" + txt_can_nang.getText().toString());
//        if(check_editext(R.id.c_cao, "Chieu cao") && check_editext(R.id.can_nang, "Can nang")){
//            bmi = can_nang / Math.pow(can_nang, 2);
//        }
//        else{
//            bmi = 0;
//        }
        if(c_cao == 0 || can_nang == 0) bmi = -1;
        else bmi = can_nang / Math.pow(c_cao, 2);
        if (bmi <= 0){
            result = "Thong tin nhap chua chinh xac";
        }
        else if( bmi < 18 && bmi > 0){
            result = "Nguoi gay";
        }
        else if(bmi >= 18 && bmi <24.9){
            result = "Nguoi binh thuong";
        }
        else if(bmi >= 25 && bmi <= 29.9){
            result = "Nguoi beo phi do I";
        }
        else if(bmi >= 30 && bmi <= 34.9){
            result = "Nguoi beo phi do II";
        }
        else{
            result = "Nguoi beo phi do III";
        }
        txt_bmi.setText(Double.toString(bmi));
        txt_result.setText(result);
    }
}