package com.example.project_cal;

import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import kotlin.collections.DoubleIterator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText text_a;
    private EditText text_b;
    private EditText text_kq;
    private Button btn_tong;
    private Button btn_hieu;
    private Button btn_tich;
    private Button btn_thuong;

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

        text_a = (EditText) findViewById(R.id.editText_A);
        text_b = (EditText) findViewById(R.id.editText_B);
        text_kq = (EditText) findViewById(R.id.editText_Kq);

        btn_tong = (Button) findViewById(R.id.tong);
        btn_hieu = (Button) findViewById(R.id.hieu);
        btn_tich = (Button) findViewById(R.id.tich);
        btn_thuong = (Button) findViewById(R.id.thuong);

        btn_tong.setOnClickListener(this);
        btn_hieu.setOnClickListener(this);
        btn_tich.setOnClickListener(this);
        btn_thuong.setOnClickListener(this);

    }

//    Pair<Double, Double> get_text(EditText a, EditText b){
//        double dbl_a = Double.parseDouble(a.getText().toString());
//        double dbl_b= Double.parseDouble(b.getText().toString());
//        return new Pair<>(dbl_a, dbl_a);
//    }

    void Display(String operator){
        double dbl_a = Double.parseDouble(text_a.getText().toString());
        double dbl_b= Double.parseDouble(text_b.getText().toString());
        switch (operator){
            case "+" : {
                double dbl_kq = dbl_a + dbl_b;
                String result = Double.toString(dbl_kq);
                text_kq.setText(result);
                break;
            }
            case "-" : {
                double dbl_kq = dbl_a - dbl_b;
                String result = Double.toString(dbl_kq);
                text_kq.setText(result);
                break;
            }
            case "*" : {
                double dbl_kq = dbl_a * dbl_b;
                String result = Double.toString(dbl_kq);
                text_kq.setText(result);
                break;
            }
            case "/" : {
                if(dbl_b == 0){
                    Toast toat = Toast.makeText(getApplicationContext(),"B != 0", Toast.LENGTH_LONG);
                    toat.show();
                }
                else{
                    double dbl_kq = dbl_a / dbl_b;
                    String result = Double.toString(dbl_kq);
                    text_kq.setText(result);
                    break;
                }
            }
            default:
                throw new IllegalStateException("Unexpected value: " + operator);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.tong){
            Display("+");
        } else if (v.getId() == R.id.hieu) {
            Display("-");
        }
        else if (v.getId() == R.id.tich){
            Display("*");
        }
        else{
            Display("/");
        }
    }
}