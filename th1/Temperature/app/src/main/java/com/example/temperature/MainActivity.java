package com.example.temperature;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    EditText txt_fahrenheit;
    EditText txt_celcius;
    Button btn_to_cel;
    Button btn_to_fah;
    Button btn_clear;
    double cel, fah;
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

        txt_fahrenheit = (EditText) findViewById(R.id.Fahrenheit_edit);
        txt_celcius = (EditText) findViewById(R.id.Celcius_edit);
        btn_to_cel = (Button) findViewById(R.id.fah_to_cel);
        btn_to_fah = (Button) findViewById(R.id.cel_to_fah);
        btn_clear = (Button) findViewById(R.id.clear);

        btn_to_cel.setOnClickListener(this);
        btn_to_fah.setOnClickListener(this);
        btn_clear.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fah_to_cel){
            fah = Double.parseDouble("0" + txt_fahrenheit.getText().toString());
            cel = Double.parseDouble("0" + txt_celcius.getText().toString());
            if(fah == 0){
                txt_celcius.setText("0");
            }
            else{
                double to_fah = (fah - 32) * 5 * 1.0 / 9;
                txt_celcius.setText(Double.toString(to_fah));
            }

        }
        else if(v.getId() == R.id.cel_to_fah){
            cel = Double.parseDouble("0" + txt_celcius.getText().toString());
            fah = Double.parseDouble("0" + txt_fahrenheit.getText().toString());
            if(cel == 0){
                txt_fahrenheit.setText("0");
            }
            else{
                double to_fah = cel * (9 * 1.0 / 5) + 32;
                txt_fahrenheit.setText(Double.toString(to_fah));
            }
        }
        else{
            txt_fahrenheit.setText("");
            txt_celcius.setText("");
        }
    }
}