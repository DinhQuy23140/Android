package com.example.sharedpreferences;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText edt_A, edt_B, edt_Kq;
    Button btn_tong, btn_Clear;
    Boolean check = true;
    TextView tv_ls;
    String ls = "";

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

        edt_A = findViewById(R.id.edt_A);
        edt_B = findViewById(R.id.edt_B);
        edt_Kq = findViewById(R.id.edt_KQ);
        tv_ls = findViewById(R.id.edt_LS);
        btn_tong = findViewById(R.id.btn_Tong);
        btn_Clear = findViewById(R.id.btn_Clear);

        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        ls = sharedPreferences.getString("ls", "");
        tv_ls.setText(ls);

        edt_A.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (edt_A.getText().toString().isEmpty()) {
                        Toast.makeText(MainActivity.this, "Nhập số A", Toast.LENGTH_SHORT).show();
                        check = false;
                    }
                }
            }
        });

        edt_B.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (edt_B.getText().toString().isEmpty()) {
                        Toast.makeText(MainActivity.this, "Nhập số B", Toast.LENGTH_SHORT).show();
                        check = false;
                    }
                }
            }
        });

        btn_tong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check) {
                    int a = Integer.parseInt(edt_A.getText().toString());
                    int b = Integer.parseInt(edt_B.getText().toString());
                    int kq = a + b;
                    edt_Kq.setText(String.valueOf(kq));
                    ls += a + " + " + b + " = " + kq + "\n";
                    tv_ls.setText(ls);
                }
                else{
                    Toast.makeText(MainActivity.this, "Nhập số A và B", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_Clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ls = "";
                tv_ls.setText(ls);
            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("ls", ls);
        editor.commit();

    }
}