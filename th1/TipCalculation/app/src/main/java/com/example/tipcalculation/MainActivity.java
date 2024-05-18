package com.example.tipcalculation;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        Button Giam = findViewById(R.id.giam);
        Giam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get percent
                TextView tv_percent = findViewById(R.id.percent_view);
                String text_view = tv_percent.getText().toString();

                //edittext
                EditText edit = findViewById(R.id.editTextText);
                String getbill = edit.getText().toString();
                //tip
                TextView tv_tip = findViewById(R.id.tip_view);
                //total
                TextView total = findViewById(R.id.total_view);
                if(!text_view.isEmpty()){
                    //percent
                    StringBuilder sb = new StringBuilder(text_view);
                    sb.deleteCharAt(text_view.length()-1);
                    String new_str = sb.toString();
                    int percent = Integer.parseInt(new_str);
                    if(percent > 0){
                        percent--;
                        int bill = Integer.parseInt(getbill);

                        String output = "$" + Integer.toString(percent * bill / 100);
                        String total_str = "$" + Integer.toString(percent * bill / 100 + bill);
                        total.setText(total_str);
                        tv_tip.setText(output);
                    }
                    new_str = Integer.toString(percent) + "%";
                    tv_percent.setText(new_str);
                }
            }
        });

        Button Tang = findViewById(R.id.tang);
        Tang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv_percent = findViewById(R.id.percent_view);
                String text_view = tv_percent.getText().toString();

                EditText edit = findViewById(R.id.editTextText);
                String getbill = edit.getText().toString();
                TextView tv_tip = findViewById(R.id.tip_view);
                TextView total = findViewById(R.id.total_view);
                if(!text_view.isEmpty()){
                    StringBuilder sb = new StringBuilder(text_view);
                    sb.deleteCharAt(text_view.length()-1);
                    String new_str = sb.toString();
                    int percent = Integer.parseInt(new_str);
                    percent++;
                    int bill = Integer.parseInt(getbill);
                    String total_str = "$" + Integer.toString(percent * bill / 100 + bill);
                    total.setText(total_str);
                    String output = "$" + Integer.toString(percent * bill / 100);
                    tv_tip.setText(output);
                    new_str = Integer.toString(percent) + "%";
                    tv_percent.setText(new_str);
                }
            }
        });

        TextView edit_text = findViewById(R.id.editTextText);
        edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                EditText edit = findViewById(R.id.editTextText);
                String getbill = edit.getText().toString();
                TextView tv_tip = findViewById(R.id.tip_view);
                if(!getbill.isEmpty()){
                    TextView tv_percent = findViewById(R.id.percent_view);
                    String text_view = tv_percent.getText().toString();
                    if(!text_view.isEmpty()){
                        StringBuilder sb = new StringBuilder(text_view);
                        sb.deleteCharAt(text_view.length()-1);
                        String new_str = sb.toString();
                        int percent = Integer.parseInt(new_str);
                        int bill = Integer.parseInt(getbill);
                        String output = "$" + Integer.toString(percent * bill / 100);
                        tv_tip.setText(output);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edit_text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE){
                    //bill
                    EditText edit = findViewById(R.id.editTextText);
                    String getbill = edit.getText().toString();
                    //tip
                    TextView tip = findViewById(R.id.tip_view);
                    String tip_str = tip.getText().toString();
                    //total
                    TextView total = findViewById(R.id.total_view);
                    if(!tip_str.isEmpty()){
                        StringBuilder sb = new StringBuilder(tip_str);
                        sb.deleteCharAt(0);
                        String new_str = sb.toString();
                        int tip_int = Integer.parseInt(new_str);
                        int bill = Integer.parseInt(getbill);
                        String output = "$" + Integer.toString(tip_int + bill);
                        total.setText(output);
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                    return true;
                }
                return false;
            }
        });
    }
}