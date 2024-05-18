package com.example.intent;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Child_activity extends AppCompatActivity {
    Button btn_back;
    TextView tv_result;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.child_activity);
        btn_back = (Button) findViewById(R.id.btn_back_main);
        tv_result = (TextView) findViewById(R.id.tv_result);
        Intent yourIntent = getIntent();
        Bundle bunle = yourIntent.getBundleExtra("packet");
        String str = bunle.getString("key");
        tv_result.setText(str);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Child_activity.this, MainActivity.class);
                startActivity(intent);
                //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    Slide slide = new Slide(Gravity.START);
//                    slide.setDuration(500);
//                    getWindow().setEnterTransition(slide);
//                }
            }
        });
    }
}
