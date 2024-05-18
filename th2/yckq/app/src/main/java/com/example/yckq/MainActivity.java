package com.example.yckq;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
        EditText edt_input_A;
        EditText edt_input_B;
        EditText edt_result;
        Button btn_send;

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

        edt_input_A = (EditText) findViewById(R.id.edt_input_A);
        edt_input_B = (EditText) findViewById(R.id.edt_input_B);
        edt_result = (EditText) findViewById(R.id.edt_result);

        btn_send = (Button) findViewById(R.id.btn_send);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, xlkq_activity.class);
                double a = Double.parseDouble("0" + edt_input_A.getText().toString());
                double b = Double.parseDouble("0" + edt_input_B.getText().toString());
                intent.putExtra("A", a);
                intent.putExtra("B", b);
                startActivityForResult(intent, 99);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 99 && resultCode == 33){
            double sum = data.getDoubleExtra("kq", 0);
            edt_result.setText("Tong 2 so: " + Double.toString(sum));
        }
        if (requestCode == 99 && resultCode == 34){
            double hieu = data.getDoubleExtra("kq", 0);
            edt_result.setText("Hieu 2 so: " + Double.toString(hieu));
        }
    }
}