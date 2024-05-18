package com.example.tipcalculator20;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, TextView.OnEditorActionListener{
    EditText edt_BillAmount;
    SeekBar seekBar_Percent;
    TextView tv_Tip;
    TextView tv_Total;
    private String billAmountString = "";
    private int tipPercent = 15;

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

        edt_BillAmount = (EditText) findViewById(R.id.edt_billAmount);
        seekBar_Percent = (SeekBar) findViewById(R.id.seekBar_Percent);
        tv_Tip = (TextView) findViewById(R.id.tv_Tip);
        tv_Total = (TextView) findViewById(R.id.tv_Total);
        seekBar_Percent.setOnSeekBarChangeListener(this);
        edt_BillAmount.setOnEditorActionListener(this);
    }



    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        tipPercent = progress;
        calculateAndDisplay();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    public void calculateAndDisplay() {

        // get the bill amount
        billAmountString = edt_BillAmount.getText().toString();
        float billAmount;
        if (billAmountString.equals("")) {
            billAmount = 0;
        }
        else {
            billAmount = Float.parseFloat(billAmountString);
        }

        // calculate tip and total
        float tipAmount = billAmount * tipPercent / 100;
        float totalAmount = billAmount + tipAmount;

        // display the other results with formatting
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        tv_Tip.setText(currency.format(tipAmount));
        tv_Total.setText(currency.format(totalAmount));
        seekBar_Percent.setProgress(tipPercent);
        NumberFormat percent = NumberFormat.getPercentInstance();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_UNSPECIFIED){
            calculateAndDisplay();
        }
        return false;
    }
}