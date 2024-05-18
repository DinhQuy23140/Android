package com.example.yckq;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class xlkq_activity extends AppCompatActivity implements View.OnClickListener {
    EditText edt_A;
    EditText edt_B;
    Button btn_tong;
    Button btn_hieu;

    double a, b;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.xlkq_main);

        edt_A = (EditText) findViewById(R.id.edt_A);
        edt_B = (EditText) findViewById(R.id.edt_B);
        btn_tong = (Button) findViewById(R.id.btn_tong);
        btn_hieu = (Button) findViewById(R.id.btn_hieu);

        btn_tong.setOnClickListener(this);
        btn_hieu.setOnClickListener(this);
        intent = getIntent();
        a = intent.getDoubleExtra("a", 0);
        b = intent.getDoubleExtra("b", 0);
        edt_A.setText(a + "");
        edt_B.setText(b + "");

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_tong){
            double result = a + b;
            intent.putExtra("kq", result);
            setResult(33, intent);
            finish();
        }
        if (v.getId() == R.id.btn_hieu){
            double result = a - b;
            intent.putExtra("kq", result);
            setResult(34, intent);
            finish();
        }
    }
}
